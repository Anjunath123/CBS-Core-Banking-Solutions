package com.canfin.corebanking.customerservice.service.impl;

import com.canfin.corebanking.customerservice.constants.AppConstants;
import com.canfin.corebanking.customerservice.config.CacheConfig;
import com.canfin.corebanking.customerservice.dto.CreditDebitRequest;
import com.canfin.corebanking.customerservice.dto.SavingsAccountDto;
import com.canfin.corebanking.customerservice.entity.DepositAccountKey;
import com.canfin.corebanking.customerservice.entity.SavingsAccount;
import com.canfin.corebanking.customerservice.enums.CustomerType;
import com.canfin.corebanking.customerservice.exception.OmniNGException;
import com.canfin.corebanking.customerservice.repository.SavingsAccountRepository;
import com.canfin.corebanking.customerservice.service.CustomerService;
import com.canfin.corebanking.customerservice.service.SavingsAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = OmniNGException.class, readOnly = true)
public class SavingsAccountServiceImpl implements SavingsAccountService {

    private static final Logger logger = LoggerFactory.getLogger(SavingsAccountServiceImpl.class);

    @Value("${CBS_BANK_TENANT}")
    private Integer tenantId;

    @Autowired
    private SavingsAccountRepository savingsAccountRepository;

    @Autowired
    private CustomerService customerService;

    @Override
    @Transactional
    public SavingsAccountDto createSavingsAccount(SavingsAccountDto request) throws OmniNGException {
        SavingsAccount account = new SavingsAccount();
        DepositAccountKey key = new DepositAccountKey();
        key.setTenantId(tenantId);
        key.setBranchCode(request.getBranchCode());
        String accountNumber = generateAccountNumber(request.getBranchCode());
        key.setAccountNumber(accountNumber);
        account.setSavingsAccountKey(key);
        customerService.findByAuthorizedCustomer(tenantId, request.getBranchCode(), request.getCustomerId(), AppConstants.ACTIVE, CustomerType.APPROVED.toString());
        BeanUtils.copyProperties(request, account);
        account.setAvailableBalance(0D);
        account.setCurrentBalance(0D);
        account.setLienAmount(0D);
        account.setIsActive(AppConstants.ACTIVE);
        account.setAuthStatus(AppConstants.AUTH_PENDING);
        account.setAccountStatus(1);
        account = savingsAccountRepository.save(account);
        logger.info("Savings account created: {}", accountNumber);
        return mapToDto(account);
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheConfig.SAVINGS_ACCOUNT_CACHE, allEntries = true)
    public SavingsAccountDto approveSavingsAccount(String accountNumber, Integer branchCode) throws OmniNGException {
        SavingsAccount account = savingsAccountRepository.findByUniqueRecord(tenantId, branchCode, accountNumber)
                .orElseThrow(() -> new OmniNGException("Savings account not found: " + accountNumber));

        if (!account.getAuthStatus().equalsIgnoreCase(AppConstants.AUTH_PENDING)) {
            throw new OmniNGException("Account is not in pending state. Current status: " + account.getAuthStatus());
        }

        account.setAuthStatus(AppConstants.AUTH_APPROVED);
        account.setAccountStatus(3);
        account = savingsAccountRepository.save(account);
        logger.info("Savings account approved: {}", accountNumber);
        return mapToDto(account);
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheConfig.SAVINGS_ACCOUNT_CACHE, allEntries = true)
    public SavingsAccountDto creditAmount(CreditDebitRequest request) throws OmniNGException {
        SavingsAccount account = savingsAccountRepository.findActiveAccount(tenantId, request.getBranchCode(), request.getAccountNumber(), AppConstants.ACTIVE, AppConstants.AUTH_APPROVED)
                .orElseThrow(() -> new OmniNGException("Savings account " + request.getAccountNumber() + " not found or not active/approved."));

        account.setAvailableBalance(account.getAvailableBalance() + request.getAmount());
        account.setCurrentBalance(account.getCurrentBalance() + request.getAmount());
        account = savingsAccountRepository.save(account);

        logger.info("Credited {} to account {}. New balance: {}",
                request.getAmount(), request.getAccountNumber(), account.getAvailableBalance());
        return mapToDto(account);
    }

    @Override
    @Cacheable(value = CacheConfig.SAVINGS_ACCOUNT_CACHE, key = "#branchCode + '_' + #accountNumber")
    public SavingsAccountDto getAccountDetails(String accountNumber, Integer branchCode) throws OmniNGException {
        SavingsAccount account = savingsAccountRepository.findByUniqueRecord(tenantId, branchCode, accountNumber)
                .orElseThrow(() -> new OmniNGException("Savings account not found: " + accountNumber));
        return mapToDto(account);
    }

    private String generateAccountNumber(Integer branchCode) {
        String prefix = "SB";
        String maxAccountNumber = savingsAccountRepository.findMaxAccountNumber(tenantId, branchCode);
        if (maxAccountNumber == null || maxAccountNumber.isEmpty()) {
            return prefix + String.format("%d%02d%06d", tenantId, branchCode, 1);
        }
        String numericPart = maxAccountNumber.replaceAll("[^0-9]", "");
        long nextNumber = Long.parseLong(numericPart) + 1;
        return prefix + nextNumber;
    }

    private SavingsAccountDto mapToDto(SavingsAccount account) {
        SavingsAccountDto dto = new SavingsAccountDto();
        dto.setBranchCode(account.getSavingsAccountKey().getBranchCode());
        dto.setAccountNumber(account.getSavingsAccountKey().getAccountNumber());
        BeanUtils.copyProperties(account, dto);
        return dto;
    }
}
