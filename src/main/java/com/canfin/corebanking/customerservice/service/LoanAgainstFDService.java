package com.canfin.corebanking.customerservice.service;

import com.canfin.corebanking.customerservice.dto.LoanAgainstFDDto;
import com.canfin.corebanking.customerservice.exception.OmniNGException;

import java.util.List;

public interface LoanAgainstFDService {

    LoanAgainstFDDto createLoanAgainstFD(LoanAgainstFDDto request) throws OmniNGException;

    LoanAgainstFDDto approveLoanAgainstFD(String loanAccountNumber, Integer branchCode) throws OmniNGException;

    List<LoanAgainstFDDto> getAllLoanAgainstFD(Integer tenantId,Integer branchCode) throws OmniNGException;
}
