package com.canfin.corebanking.customerservice.service.impl;

import com.canfin.corebanking.customerservice.constants.AppConstants;
import com.canfin.corebanking.customerservice.dto.LoanAgainstFDDto;
import com.canfin.corebanking.customerservice.entity.*;
import com.canfin.corebanking.customerservice.enums.CustomerType;
import com.canfin.corebanking.customerservice.exception.OmniNGException;
import com.canfin.corebanking.customerservice.repository.DepositAccountRepository;
import com.canfin.corebanking.customerservice.repository.LoanAgainstFDRepository;
import com.canfin.corebanking.customerservice.repository.SavingsAccountRepository;
import com.canfin.corebanking.customerservice.service.CustomerService;
import com.canfin.corebanking.customerservice.service.LoanAgainstFDService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = OmniNGException.class, readOnly = true)
public class LoanAgainstFDServiceImpl implements LoanAgainstFDService {

    private static final Double MAX_LOAN_PERCENTAGE = 90.0;
    private static final Double LOAN_INTEREST_MARKUP = 2.0;

    @Value("${CBS_BANK_TENANT}")
    private Integer tenantId;

    @Autowired
    private LoanAgainstFDRepository loanAgainstFDRepository;

    @Autowired
    private DepositAccountRepository depositAccountRepository;

    @Autowired
    private SavingsAccountRepository savingsAccountRepository;

    @Autowired
    private CustomerService customerService;

    @Override
    @Transactional
    public LoanAgainstFDDto createLoanAgainstFD(LoanAgainstFDDto request) throws OmniNGException {
        // Validate customer
        customerService.findByAuthorizedCustomer(tenantId, request.getBranchCode(),
                request.getCustomerId(), AppConstants.ACTIVE, String.valueOf(CustomerType.APPROVED));

        // Validate FD account exists and is active/approved
        DepositAccount fdAccount = depositAccountRepository.getDepositAccountIsActiveAndAuthorize(
                tenantId, request.getBranchCode(), request.getFdAccountNumber(),
                AppConstants.ACTIVE, AppConstants.AUTH_APPROVED);
        if (fdAccount == null) {
            throw new OmniNGException("FD Account not found or not active/approved: " + request.getFdAccountNumber());
        }
        if (!fdAccount.getDepositStatus().equals(3)) {
            throw new OmniNGException("FD Account is not Live: " + request.getFdAccountNumber());
        }

        // Check no existing active loan against this FD
        loanAgainstFDRepository.findActiveLoanByFdAccount(tenantId, request.getBranchCode(),
                request.getFdAccountNumber(), AppConstants.ACTIVE).ifPresent(l -> {
            throw new OmniNGException("An active loan already exists against FD: " + request.getFdAccountNumber());
        });

        // Validate loan amount <= 90% of FD deposit
        double maxLoan = fdAccount.getDepositAmount() * MAX_LOAN_PERCENTAGE / 100;
        if (request.getLoanAmount() > maxLoan) {
            throw new OmniNGException("Loan amount cannot exceed " + MAX_LOAN_PERCENTAGE + "% of FD amount. Max allowed: " + maxLoan);
        }

        // Create loan
        LoanAgainstFD loan = new LoanAgainstFD();
        LoanAgainstFDKey key = new LoanAgainstFDKey();
        key.setTenantId(tenantId);
        key.setBranchCode(request.getBranchCode());
        key.setLoanAccountNumber(generateLoanAccountNumber(tenantId, request.getBranchCode()));
        loan.setLoanAgainstFDKey(key);

        BeanUtils.copyProperties(request, loan);
        loan.setFdDepositAmount(fdAccount.getDepositAmount());
        loan.setIntRate(fdAccount.getIntRate() + LOAN_INTEREST_MARKUP);
        loan.setOutstandingAmount(request.getLoanAmount());
        loan.setInterestAccrued(0D);
        loan.setIsActive(AppConstants.ACTIVE);
        loan.setAuthStatus(AppConstants.AUTH_PENDING);
        loan.setLoanStatus(1); // 1=Pending

        // Set maturity date
        Calendar cal = Calendar.getInstance();
        cal.setTime(request.getDisbursementDate());
        cal.add(Calendar.MONTH, request.getLoanTenureMonths());
        loan.setMaturityDate(cal.getTime());

        // Mark lien on FD
        fdAccount.setLienAmount(request.getLoanAmount());
        depositAccountRepository.save(fdAccount);

        loan = loanAgainstFDRepository.save(loan);
        return mapToResponse(loan);
    }

    @Override
    @Transactional
    public LoanAgainstFDDto approveLoanAgainstFD(String loanAccountNumber, Integer branchCode) throws OmniNGException {
        LoanAgainstFD loan = loanAgainstFDRepository.findByUniqueLoanRecord(tenantId, branchCode, loanAccountNumber)
                .orElseThrow(() -> new OmniNGException("Loan account not found"));

        if (!loan.getAuthStatus().equalsIgnoreCase(AppConstants.AUTH_PENDING)) {
            throw new OmniNGException("Loan is not in pending state");
        }

        // Credit loan amount to repayment (savings) account
        SavingsAccount savingsAccount = savingsAccountRepository.findActiveAccount(
                tenantId, branchCode, loan.getRepaymentAccountNumber(),
                AppConstants.ACTIVE, AppConstants.AUTH_APPROVED)
                .orElseThrow(() -> new OmniNGException("Repayment account not found or not active"));

        savingsAccount.setAvailableBalance(savingsAccount.getAvailableBalance() + loan.getLoanAmount());
        savingsAccount.setCurrentBalance(savingsAccount.getCurrentBalance() + loan.getLoanAmount());
        savingsAccountRepository.save(savingsAccount);

        loan.setAuthStatus(AppConstants.AUTH_APPROVED);
        loan.setLoanStatus(3); // 3=Active/Disbursed
        loan = loanAgainstFDRepository.save(loan);

        return mapToResponse(loan);
    }

    private String generateLoanAccountNumber(Integer tenantId, Integer branchCode) {
        String maxNumber = loanAgainstFDRepository.findMaxLoanAccountNumber(tenantId, branchCode);
        if (maxNumber == null || maxNumber.isEmpty()) {
            return String.format("LFD%d%02d%06d", tenantId, branchCode, 1);
        }
        String numericPart = maxNumber.replaceAll("[^0-9]", "");
        long next = Long.parseLong(numericPart) + 1;
        return "LFD" + next;
    }

    private LoanAgainstFDDto mapToResponse(LoanAgainstFD loan) {
        LoanAgainstFDDto dto = new LoanAgainstFDDto();
        dto.setBranchCode(loan.getLoanAgainstFDKey().getBranchCode());
        dto.setLoanAccountNumber(loan.getLoanAgainstFDKey().getLoanAccountNumber());
        BeanUtils.copyProperties(loan, dto);
        return dto;
    }
}
