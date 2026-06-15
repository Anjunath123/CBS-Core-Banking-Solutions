package com.canfin.corebanking.customerservice.service;

import com.canfin.corebanking.customerservice.dto.LoanAgainstFDDto;
import com.canfin.corebanking.customerservice.exception.OmniNGException;

public interface LoanAgainstFDService {

    LoanAgainstFDDto createLoanAgainstFD(LoanAgainstFDDto request) throws OmniNGException;

    LoanAgainstFDDto approveLoanAgainstFD(String loanAccountNumber, Integer branchCode) throws OmniNGException;
}
