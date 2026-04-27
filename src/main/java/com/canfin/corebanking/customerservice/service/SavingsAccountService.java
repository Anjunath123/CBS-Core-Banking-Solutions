package com.canfin.corebanking.customerservice.service;

import com.canfin.corebanking.customerservice.dto.CreditDebitRequest;
import com.canfin.corebanking.customerservice.dto.SavingsAccountDto;
import com.canfin.corebanking.customerservice.exception.OmniNGException;

public interface SavingsAccountService {

    SavingsAccountDto createSavingsAccount(SavingsAccountDto request) throws OmniNGException;

    SavingsAccountDto approveSavingsAccount(String accountNumber, Integer branchCode) throws OmniNGException;

    SavingsAccountDto creditAmount(CreditDebitRequest request) throws OmniNGException;

    SavingsAccountDto getAccountDetails(String accountNumber, Integer branchCode) throws OmniNGException;
}
