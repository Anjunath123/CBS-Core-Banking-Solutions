package com.canfin.corebanking.customerservice.service;

import com.canfin.corebanking.customerservice.dto.HomeLoanApplicationDto;
import com.canfin.corebanking.customerservice.dto.HomeLoanDisbursementDto;
import com.canfin.corebanking.customerservice.dto.HomeLoanEmiScheduleDto;

import java.util.List;

public interface HomeLoanService {

    // STEP 1: Apply for loan
    HomeLoanApplicationDto applyForLoan(HomeLoanApplicationDto request);

    // STEP 2: Verify & check eligibility
    HomeLoanApplicationDto verifyAndCheckEligibility(String loanAccountNumber);

    // STEP 3: Approve loan
    HomeLoanApplicationDto approveLoan(String loanAccountNumber);

    // STEP 4: Disburse loan
    HomeLoanApplicationDto disburseLoan(HomeLoanDisbursementDto request);

    // STEP 5: Get EMI schedule
    List<HomeLoanEmiScheduleDto> getEmiSchedule(String loanAccountNumber);

    // STEP 6: Pay EMI
    HomeLoanApplicationDto payEmi(String loanAccountNumber);

    // STEP 9: Close loan
    HomeLoanApplicationDto closeLoan(String loanAccountNumber);

    // Query APIs
    HomeLoanApplicationDto getLoanByAccountNumber(String loanAccountNumber);

    List<HomeLoanApplicationDto> getLoansByCustomerId(Long customerId);
}
