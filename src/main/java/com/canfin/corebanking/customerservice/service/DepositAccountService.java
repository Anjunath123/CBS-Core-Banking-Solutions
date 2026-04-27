package com.canfin.corebanking.customerservice.service;

import com.canfin.corebanking.customerservice.dto.ClosureRequest;
import com.canfin.corebanking.customerservice.dto.DepositAccountDto;
import com.canfin.corebanking.customerservice.dto.PreClosureRequest;
import com.canfin.corebanking.customerservice.dto.RenewalRequest;
import com.canfin.corebanking.customerservice.exception.OmniNGException;

import java.util.Map;

public interface DepositAccountService {

    DepositAccountDto createFDAccount(DepositAccountDto request) throws OmniNGException;

    DepositAccountDto approveFDAccount(String accountId, Integer branchCode) throws OmniNGException;

    Map<String, Object> preClosureActivity(PreClosureRequest preClosureRequest) throws OmniNGException;

    Map<String, Object> closureActivity(ClosureRequest closureRequest) throws OmniNGException;

    DepositAccountDto renewDeposit(RenewalRequest renewalRequest) throws OmniNGException;

    Map<String, Object> partialRenewal(RenewalRequest renewalRequest) throws OmniNGException;
}
