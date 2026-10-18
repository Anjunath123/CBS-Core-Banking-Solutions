package com.canfin.corebanking.customerservice.service;

import com.canfin.corebanking.customerservice.dto.DepositAccountDto;
import com.canfin.corebanking.customerservice.dto.PreClosureRequest;
import com.canfin.corebanking.customerservice.exception.OmniNGException;

import java.util.Map;

public interface DepositAccountService {

    DepositAccountDto createFDAccount(DepositAccountDto request) throws OmniNGException;

    public DepositAccountDto approveFDAccount(String accountId,Integer branchCode) throws OmniNGException;

    public Map<String,Object> preClosureActivity(PreClosureRequest preClosureRequest) throws OmniNGException;
}
