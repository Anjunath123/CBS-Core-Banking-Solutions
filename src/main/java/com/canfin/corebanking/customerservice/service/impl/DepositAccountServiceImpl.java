package com.canfin.corebanking.customerservice.service.impl;

import com.canfin.corebanking.customerservice.constants.AppConstants;
import com.canfin.corebanking.customerservice.dto.ClosureRequest;
import com.canfin.corebanking.customerservice.dto.DepositAccountDto;
import com.canfin.corebanking.customerservice.dto.PreClosureRequest;
import com.canfin.corebanking.customerservice.dto.RenewalRequest;
import com.canfin.corebanking.customerservice.entity.Customer;
import com.canfin.corebanking.customerservice.entity.DepositAccount;
import com.canfin.corebanking.customerservice.entity.DepositAccountKey;
import com.canfin.corebanking.customerservice.enums.CustomerType;
import com.canfin.corebanking.customerservice.exception.OmniNGException;
import com.canfin.corebanking.customerservice.entity.SavingsAccount;
import com.canfin.corebanking.customerservice.entity.VoucherMst;
import com.canfin.corebanking.customerservice.repository.DepositAccountRepository;
import com.canfin.corebanking.customerservice.repository.SavingsAccountRepository;
import com.canfin.corebanking.customerservice.service.CustomerService;
import com.canfin.corebanking.customerservice.service.DepositAccountService;
import com.canfin.corebanking.customerservice.service.VoucherService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = OmniNGException.class, readOnly = true)
public class DepositAccountServiceImpl implements DepositAccountService {

    @Value("${CBS_BANK_TENANT}")
    private Integer tenantId;

    @Autowired
    private DepositAccountRepository depositAccountRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SavingsAccountRepository savingsAccountRepository;

    @Autowired
    private VoucherService voucherService;


    @Transactional
    public DepositAccountDto createFDAccount(DepositAccountDto request) throws OmniNGException {
        DepositAccount depositAccount=new DepositAccount();
        DepositAccountKey depositAccountKey=new DepositAccountKey();
        depositAccountKey.setTenantId(tenantId);
        depositAccountKey.setBranchCode(request.getBranchCode());
        String accountNumber=generateAccountNumber(tenantId,request.getBranchCode());
        depositAccountKey.setAccountNumber(accountNumber);
        depositAccount.setDepositAccountKey(depositAccountKey);
        BeanUtils.copyProperties(request,depositAccount);
        setMaturityDate(depositAccount);
        depositAccount.setIsActive(AppConstants.ACTIVE);
        depositAccount.setAuthStatus(AppConstants.AUTH_PENDING);

        Customer customer= customerService.findByAuthorizedCustomer(tenantId,request.getBranchCode(),request.getCustomerId(),AppConstants.ACTIVE, String.valueOf(CustomerType.APPROVED));
        if(isSeniorCitizen(customer)) {
            depositAccount.setIntRate(depositAccount.getIntRate() + AppConstants.SENIOR_CITIZEN_EXTRA_RATE);
        }
        depositAccount=depositAccountRepository.save(depositAccount);
        return mapToResponse(depositAccount);
    }


    @Transactional
    public DepositAccountDto approveFDAccount(String accountId, Integer branchCode) throws OmniNGException {
        DepositAccount depositAccount = depositAccountRepository.findByUniqueDepositRecord(tenantId, branchCode, accountId)
                .orElseThrow(() -> new OmniNGException("FD Account not found"));
        if(depositAccount.getAuthStatus().equalsIgnoreCase(AppConstants.AUTH_PENDING)) {

            // Step 1: Validate & debit the source savings account
            SavingsAccount sourceAccount = validateAndDebitSourceAccount(depositAccount);

            // Step 2: Approve the TD
            depositAccount.setAuthStatus(AppConstants.AUTH_APPROVED);
            depositAccount.setDepositStatus(3);
            depositAccount = depositAccountRepository.save(depositAccount);

            // Step 3: Generate DR/CR vouchers
            generateApprovalVouchers(depositAccount, sourceAccount);
        }
        return mapToResponse(depositAccount);
    }

    private SavingsAccount validateAndDebitSourceAccount(DepositAccount depositAccount) {
        String sourceAccountId = depositAccount.getDebitAccID();
        Integer branchCode = depositAccount.getDepositAccountKey().getBranchCode();
        Double depositAmount = depositAccount.getDepositAmount();

        if (sourceAccountId == null || sourceAccountId.trim().isEmpty()) {
            throw new OmniNGException("Debit Account ID is not set on the deposit.");
        }

        // 1. Check source account exists, is active and approved
        SavingsAccount sourceAccount = savingsAccountRepository.findActiveAccount(tenantId, branchCode, sourceAccountId, AppConstants.ACTIVE, AppConstants.AUTH_APPROVED)
                .orElseThrow(() -> new OmniNGException("Debit account " + sourceAccountId + " not found or not active/approved."));

        // 2. Calculate withdrawable balance = availableBalance - lienAmount - minimumBalance
        double lien = sourceAccount.getLienAmount() != null ? sourceAccount.getLienAmount() : 0.0;
        double minBal = sourceAccount.getMinimumBalance() != null ? sourceAccount.getMinimumBalance() : 0.0;
        double withdrawable = sourceAccount.getAvailableBalance() - lien - minBal;

        if (withdrawable < depositAmount) {
            throw new OmniNGException("Insufficient balance in account " + sourceAccountId + ". Available: " + withdrawable + ", Required: " + depositAmount);
        }

        // 3. Debit the source account
        sourceAccount.setAvailableBalance(sourceAccount.getAvailableBalance() - depositAmount);
        sourceAccount.setCurrentBalance(sourceAccount.getCurrentBalance() - depositAmount);
        savingsAccountRepository.save(sourceAccount);

        return sourceAccount;
    }

    private void generateApprovalVouchers(DepositAccount depositAccount, SavingsAccount sourceAccount) {
        Integer branchCode = depositAccount.getDepositAccountKey().getBranchCode();
        String tdAccountNumber = depositAccount.getDepositAccountKey().getAccountNumber();
        String sourceAccountId = depositAccount.getDebitAccID();
        Double depositAmount = depositAccount.getDepositAmount();

        List<VoucherMst> voucherList = new ArrayList<>();

        // Leg 1: DEBIT - Customer's savings/current account debited
        VoucherMst debitVoucher = voucherService.initForVoucherValues(
                tenantId, branchCode, depositAccount, AppConstants.ACTIVITY_CODE_TD_OPEN);
        debitVoucher.setDrCr("D");
        debitVoucher.setVcrAcctId(sourceAccountId);
        debitVoucher.setMainAcctId(tdAccountNumber);
        debitVoucher.setVcrModule(AppConstants.MODULE_CODE_SB);
        debitVoucher.setMainModule(AppConstants.MODULE_CODE_TD);
        debitVoucher.setVcrProductCode(sourceAccount.getProduct());
        debitVoucher.setVcrSchemeCode(sourceAccount.getScheme());
        debitVoucher.setFcyTrnAmt(depositAmount);
        debitVoucher.setLcyTrnAmt(depositAmount);
        debitVoucher.setNarration("By Transfer to TD A/c " + tdAccountNumber);
        debitVoucher.setParticulars("Dr " + sourceAccountId + " for TD Opening");
        voucherList.add(debitVoucher);

        // Leg 2: CREDIT - TD deposit account credited
        VoucherMst creditVoucher = voucherService.initForVoucherValues(
                tenantId, branchCode, depositAccount, AppConstants.ACTIVITY_CODE_TD_OPEN);
        creditVoucher.setDrCr("C");
        creditVoucher.setVcrAcctId(tdAccountNumber);
        creditVoucher.setMainAcctId(tdAccountNumber);
        creditVoucher.setVcrModule(AppConstants.MODULE_CODE_TD);
        creditVoucher.setMainModule(AppConstants.MODULE_CODE_TD);
        creditVoucher.setVcrProductCode(depositAccount.getProduct());
        creditVoucher.setVcrSchemeCode(depositAccount.getScheme());
        creditVoucher.setFcyTrnAmt(depositAmount);
        creditVoucher.setLcyTrnAmt(depositAmount);
        creditVoucher.setNarration("To TD Deposit A/c " + tdAccountNumber + " from " + sourceAccountId);
        creditVoucher.setParticulars("Cr " + tdAccountNumber + " TD Opening");
        voucherList.add(creditVoucher);

        voucherService.saveVoucherListForCentraliseTdAccr(voucherList);
    }


    public Map<String, Object> preClosureActivity(PreClosureRequest preClosureRequest) throws OmniNGException {
        Map<String,Object> returnMap=new HashMap<>();

        DepositAccount depositAccount = depositAccountRepository.getDepositAccountIsActiveAndAuthorize(tenantId, preClosureRequest.getBranchCode(), preClosureRequest.getAccountNumber(), AppConstants.ACTIVE, AppConstants.AUTH_APPROVED);
        if(null==depositAccount) {
            throw new OmniNGException("Deposit Account Not Found");
        }
        if(!depositAccount.getDepositStatus().equals(3)) {
            throw new OmniNGException("Deposit Account not Live :-" +preClosureRequest.getAccountNumber());
        }

        Customer customer= customerService.findByAuthorizedCustomer(tenantId,preClosureRequest.getBranchCode(),preClosureRequest.getCustomerId(),AppConstants.ACTIVE, String.valueOf(CustomerType.APPROVED));

        LocalDate openDate = depositAccount.getOpenDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        LocalDate maturityDate = depositAccount.getMaturityDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        LocalDate today = LocalDate.now();

        if (today.isAfter(maturityDate)) {
            throw new OmniNGException("TD already matured. Pre-closure not allowed");
        }

        long daysDiff = ChronoUnit.DAYS.between(openDate, today);
        long monthsDiff = ChronoUnit.MONTHS.between(openDate, today);

        double principal = depositAccount.getDepositAmount();
        double interestRate;

        if (preClosureRequest.isDeceased()) {
            interestRate = depositAccount.getIntRate();
            returnMap.put("closureType", "DECEASED");
        } else {
            if (monthsDiff < 3) {
                interestRate = 0.0;
            } else if (monthsDiff < 6) {
                interestRate = 4.0;
            } else {
                interestRate = depositAccount.getIntRate() - 2.0;
            }
            returnMap.put("closureType", "NORMAL");
        }
        double interest = (principal * interestRate * daysDiff) / (365 * 100);
        double maturityAmount = principal + interest;
        returnMap.put("accountNumber", depositAccount.getDepositAccountKey().getAccountNumber());
        returnMap.put("principal", principal);
        returnMap.put("interestRate", interestRate);
        returnMap.put("interest", interest);
        returnMap.put("maturityAmount", maturityAmount);
        return returnMap;
    }

    private String generateAccountNumber(Integer tenantId, Integer branchCode) {
        String maxAccountNumber = depositAccountRepository.findMaxAccountNumber(tenantId, branchCode);
        if (maxAccountNumber == null || maxAccountNumber.isEmpty()) {
            return String.format("%d%02d%06d", tenantId, branchCode, 1);
        }
        long nextNumber = Long.parseLong(maxAccountNumber) + 1;
        return String.valueOf(nextNumber);
    }

    private boolean isSeniorCitizen(Customer customer) {
        if(customer == null || customer.getDateOfBirth() == null) {
            return false;
        }
        long age = ChronoUnit.YEARS.between(customer.getDateOfBirth(), LocalDate.now());
        return age >= AppConstants.SENIOR_CITIZEN_AGE;
    }

    private void setMaturityDate(DepositAccount depositAccount) {
        if (depositAccount.getOpenDate() != null) {
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.setTime(depositAccount.getOpenDate());
            if (depositAccount.getDepositMonths() != null) {
                calendar.add(java.util.Calendar.MONTH, depositAccount.getDepositMonths());
            }
            if (depositAccount.getDepositDays() != null) {
                calendar.add(java.util.Calendar.DAY_OF_MONTH, depositAccount.getDepositDays());
            }
            depositAccount.setMaturityDate(calendar.getTime());
        }
    }

    @Transactional
    @Override
    public Map<String, Object> closureActivity(ClosureRequest closureRequest) throws OmniNGException {
        Map<String, Object> returnMap = new HashMap<>();

        DepositAccount depositAccount = depositAccountRepository.getDepositAccountIsActiveAndAuthorize(
                tenantId, closureRequest.getBranchCode(), closureRequest.getAccountNumber(),
                AppConstants.ACTIVE, AppConstants.AUTH_APPROVED);

        if (depositAccount == null) {
            throw new OmniNGException("Deposit Account Not Found");
        }
        if (!depositAccount.getDepositStatus().equals(3)) {
            throw new OmniNGException("Deposit Account not Live :- " + closureRequest.getAccountNumber());
        }

        LocalDate maturityDate = depositAccount.getMaturityDate().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate today = LocalDate.now();

        if (today.isBefore(maturityDate)) {
            throw new OmniNGException("TD not yet matured. Use Pre-Closure instead.");
        }

        double principal = depositAccount.getDepositAmount();
        double interest = depositAccount.getMatInterest() != null ? depositAccount.getMatInterest() : 0.0;
        double tds = depositAccount.getTdsAmtFcy() != null ? depositAccount.getTdsAmtFcy() : 0.0;
        double totalPayout = principal + interest - tds;

        depositAccount.setClosureDate(java.util.Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        depositAccount.setClosureReason(closureRequest.getClosureReason());
        depositAccount.setDepositStatus(5);
        depositAccount.setIsActive(AppConstants.INACTIVE);
        depositAccount.setPrinBalance(0.0);
        depositAccount.setTotalPayment(totalPayout);
        depositAccountRepository.save(depositAccount);

        returnMap.put("accountNumber", closureRequest.getAccountNumber());
        returnMap.put("principal", principal);
        returnMap.put("interest", interest);
        returnMap.put("tds", tds);
        returnMap.put("totalPayout", totalPayout);
        returnMap.put("closureDate", today.toString());
        returnMap.put("closureReason", closureRequest.getClosureReason());
        return returnMap;
    }

    @Transactional
    public DepositAccountDto renewDeposit(RenewalRequest renewalRequest) throws OmniNGException {
        DepositAccount depositAccount = depositAccountRepository.getDepositAccountIsActiveAndAuthorize(
                tenantId, renewalRequest.getBranchCode(), renewalRequest.getAccountNumber(),
                AppConstants.ACTIVE, AppConstants.AUTH_APPROVED);

        if (depositAccount == null) {
            throw new OmniNGException("Deposit Account Not Found");
        }
        if (!depositAccount.getDepositStatus().equals(3)) {
            throw new OmniNGException("Deposit Account not Live :- " + renewalRequest.getAccountNumber());
        }

        double renewalAmount = depositAccount.getDepositAmount() +
                (depositAccount.getMatInterest() != null ? depositAccount.getMatInterest() : 0.0);

        depositAccount.setDepositAmount(renewalAmount);
        depositAccount.setOpenDate(new java.util.Date());
        if (renewalRequest.getRenewalMonths() != null) {
            depositAccount.setDepositMonths(renewalRequest.getRenewalMonths());
        }
        if (renewalRequest.getRenewalDays() != null) {
            depositAccount.setDepositDays(renewalRequest.getRenewalDays());
        }
        if (renewalRequest.getNewIntRate() != null) {
            depositAccount.setIntRate(renewalRequest.getNewIntRate());
        }
        setMaturityDate(depositAccount);
        depositAccount.setDepositStatus(3);
        depositAccount.setRemarks(renewalRequest.getRemarks() != null ? renewalRequest.getRemarks() : "Full Renewal");
        depositAccount = depositAccountRepository.save(depositAccount);
        return mapToResponse(depositAccount);
    }

    @Transactional
    public Map<String, Object> partialRenewal(RenewalRequest renewalRequest) throws OmniNGException {
        Map<String, Object> returnMap = new HashMap<>();

        if (renewalRequest.getRenewalAmount() == null || renewalRequest.getRenewalAmount() <= 0) {
            throw new OmniNGException("Renewal amount is required and must be greater than 0");
        }

        DepositAccount depositAccount = depositAccountRepository.getDepositAccountIsActiveAndAuthorize(
                tenantId, renewalRequest.getBranchCode(), renewalRequest.getAccountNumber(),
                AppConstants.ACTIVE, AppConstants.AUTH_APPROVED);

        if (depositAccount == null) {
            throw new OmniNGException("Deposit Account Not Found");
        }
        if (!depositAccount.getDepositStatus().equals(3)) {
            throw new OmniNGException("Deposit Account not Live :- " + renewalRequest.getAccountNumber());
        }

        double totalMaturity = depositAccount.getDepositAmount() +
                (depositAccount.getMatInterest() != null ? depositAccount.getMatInterest() : 0.0);

        if (renewalRequest.getRenewalAmount() > totalMaturity) {
            throw new OmniNGException("Renewal amount cannot exceed maturity amount: " + totalMaturity);
        }

        double payoutAmount = totalMaturity - renewalRequest.getRenewalAmount();

        depositAccount.setDepositAmount(renewalRequest.getRenewalAmount());
        depositAccount.setOpenDate(new java.util.Date());
        if (renewalRequest.getRenewalMonths() != null) {
            depositAccount.setDepositMonths(renewalRequest.getRenewalMonths());
        }
        if (renewalRequest.getRenewalDays() != null) {
            depositAccount.setDepositDays(renewalRequest.getRenewalDays());
        }
        if (renewalRequest.getNewIntRate() != null) {
            depositAccount.setIntRate(renewalRequest.getNewIntRate());
        }
        setMaturityDate(depositAccount);
        depositAccount.setDepositStatus(3);
        depositAccount.setRemarks(renewalRequest.getRemarks() != null ? renewalRequest.getRemarks() : "Partial Renewal");
        depositAccount = depositAccountRepository.save(depositAccount);

        returnMap.put("renewedAccount", mapToResponse(depositAccount));
        returnMap.put("renewalAmount", renewalRequest.getRenewalAmount());
        returnMap.put("payoutAmount", payoutAmount);
        returnMap.put("newMaturityDate", depositAccount.getMaturityDate());
        return returnMap;
    }

    private DepositAccountDto mapToResponse(DepositAccount depositAccount) {
        DepositAccountDto response = new DepositAccountDto();
        response.setBranchCode(depositAccount.getDepositAccountKey().getBranchCode());
        response.setAccountNumber(depositAccount.getDepositAccountKey().getAccountNumber());
        BeanUtils.copyProperties(depositAccount, response);
        return response;
    }
}
