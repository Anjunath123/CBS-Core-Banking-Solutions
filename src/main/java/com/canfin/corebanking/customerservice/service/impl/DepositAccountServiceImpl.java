package com.canfin.corebanking.customerservice.service.impl;

import com.canfin.corebanking.customerservice.constants.AppConstants;
import com.canfin.corebanking.customerservice.dto.DepositAccountDto;
import com.canfin.corebanking.customerservice.dto.PreClosureRequest;
import com.canfin.corebanking.customerservice.entity.Customer;
import com.canfin.corebanking.customerservice.entity.DepositAccount;
import com.canfin.corebanking.customerservice.entity.DepositAccountKey;
import com.canfin.corebanking.customerservice.enums.CustomerType;
import com.canfin.corebanking.customerservice.exception.OmniNGException;
import com.canfin.corebanking.customerservice.repository.DepositAccountRepository;
import com.canfin.corebanking.customerservice.service.CustomerService;
import com.canfin.corebanking.customerservice.service.DepositAccountService;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
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


    @Transactional
    public DepositAccountDto createFDAccount(DepositAccountDto request) throws OmniNGException {
        DepositAccount depositAccount=new DepositAccount();
        Mapper mapper=new DozerBeanMapper();
        DepositAccountKey depositAccountKey=new DepositAccountKey();
        depositAccountKey.setTenantId(tenantId);
        depositAccountKey.setBranchCode(request.getBranchCode());
        String accountNumber=generateAccountNumber(tenantId,request.getBranchCode());
        depositAccountKey.setAccountNumber(accountNumber);
        depositAccount.setDepositAccountKey(depositAccountKey);
        mapper.map(request,depositAccount);
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
            depositAccount.setAuthStatus(AppConstants.AUTH_APPROVED);
            depositAccount.setDepositStatus(3);
            depositAccount = depositAccountRepository.save(depositAccount);
        }
        return mapToResponse(depositAccount);
    }


    public Map<String, Object> preClosureActivity(PreClosureRequest preClosureRequest) throws OmniNGException {
        Map<String,Object> returnMap=new HashMap<>();
        
        DepositAccount depositAccount = depositAccountRepository.getDepositAccountIsActiveAndAuthorize(tenantId, preClosureRequest.getBranchCode(), preClosureRequest.getAccountNumber(), AppConstants.ACTIVE, AppConstants.AUTH_APPROVED);
        if(null==depositAccount) {
            throw new OmniNGException("Deposit Account Not Found");
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
        LocalDate dob = customer.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long age = ChronoUnit.YEARS.between(dob, LocalDate.now());
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

    private DepositAccountDto mapToResponse(DepositAccount depositAccount){
        DepositAccountDto response=new DepositAccountDto();
        Mapper mapper=new DozerBeanMapper();
        response.setBranchCode(depositAccount.getDepositAccountKey().getBranchCode());
        response.setAccountNumber(depositAccount.getDepositAccountKey().getAccountNumber());
        mapper.map(depositAccount,response);
        return response;
    }
}
