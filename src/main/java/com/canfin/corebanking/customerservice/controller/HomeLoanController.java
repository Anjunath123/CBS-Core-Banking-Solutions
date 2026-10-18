package com.canfin.corebanking.customerservice.controller;

import com.canfin.corebanking.customerservice.dto.BaseResponse;
import com.canfin.corebanking.customerservice.dto.HomeLoanApplicationDto;
import com.canfin.corebanking.customerservice.dto.HomeLoanDisbursementDto;
import com.canfin.corebanking.customerservice.dto.HomeLoanEmiScheduleDto;
import com.canfin.corebanking.customerservice.service.HomeLoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/home-loan")
public class HomeLoanController {

    private static final Logger logger = LoggerFactory.getLogger(HomeLoanController.class);

    @Autowired
    private HomeLoanService homeLoanService;

    // STEP 1: Apply for Home Loan (Maker - USER)
    @PostMapping("/apply")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> applyForLoan(@Valid @RequestBody HomeLoanApplicationDto request) {
        logger.info("Home Loan application request for customerId={}", request.getCustomerId());
        HomeLoanApplicationDto result = homeLoanService.applyForLoan(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new BaseResponse<>(result, HttpStatus.CREATED.toString(), "Loan Application Submitted Successfully"));
    }

    // STEP 2: Verify & Check Eligibility (Checker - ADMIN)
    @PutMapping("/verify/{loanAccountNumber}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> verifyEligibility(@PathVariable String loanAccountNumber) {
        logger.info("Eligibility check for loan={}", loanAccountNumber);
        HomeLoanApplicationDto result = homeLoanService.verifyAndCheckEligibility(loanAccountNumber);
        return ResponseEntity.ok(new BaseResponse<>(result, HttpStatus.OK.toString(), "Eligibility Check Completed"));
    }

    // STEP 3: Approve Loan (Checker - ADMIN)
    @PutMapping("/approve/{loanAccountNumber}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> approveLoan(@PathVariable String loanAccountNumber) {
        logger.info("Loan approval for loan={}", loanAccountNumber);
        HomeLoanApplicationDto result = homeLoanService.approveLoan(loanAccountNumber);
        return ResponseEntity.ok(new BaseResponse<>(result, HttpStatus.OK.toString(), "Loan Approved & EMI Schedule Generated"));
    }

    // STEP 4: Disburse Loan (Checker - ADMIN)
    @PostMapping("/disburse")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> disburseLoan(@Valid @RequestBody HomeLoanDisbursementDto request) {
        logger.info("Loan disbursement for loan={}", request.getLoanAccountNumber());
        HomeLoanApplicationDto result = homeLoanService.disburseLoan(request);
        return ResponseEntity.ok(new BaseResponse<>(result, HttpStatus.OK.toString(), "Loan Disbursed Successfully"));
    }

    // STEP 5: Get EMI Schedule
    @GetMapping("/emi-schedule/{loanAccountNumber}")
    public ResponseEntity<?> getEmiSchedule(@PathVariable String loanAccountNumber) {
        List<HomeLoanEmiScheduleDto> schedule = homeLoanService.getEmiSchedule(loanAccountNumber);
        return ResponseEntity.ok(new BaseResponse<>(schedule, HttpStatus.OK.toString(), "EMI Schedule Retrieved"));
    }

    // STEP 6: Pay EMI
    @PostMapping("/pay-emi/{loanAccountNumber}")
    public ResponseEntity<?> payEmi(@PathVariable String loanAccountNumber) {
        logger.info("EMI payment for loan={}", loanAccountNumber);
        HomeLoanApplicationDto result = homeLoanService.payEmi(loanAccountNumber);
        return ResponseEntity.ok(new BaseResponse<>(result, HttpStatus.OK.toString(), "EMI Payment Processed"));
    }

    // STEP 9: Close Loan (Checker - ADMIN)
    @PutMapping("/close/{loanAccountNumber}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> closeLoan(@PathVariable String loanAccountNumber) {
        logger.info("Loan closure for loan={}", loanAccountNumber);
        HomeLoanApplicationDto result = homeLoanService.closeLoan(loanAccountNumber);
        return ResponseEntity.ok(new BaseResponse<>(result, HttpStatus.OK.toString(), "Loan Closed Successfully"));
    }

    // Get loan details
    @GetMapping("/{loanAccountNumber}")
    public ResponseEntity<?> getLoan(@PathVariable String loanAccountNumber) {
        HomeLoanApplicationDto result = homeLoanService.getLoanByAccountNumber(loanAccountNumber);
        return ResponseEntity.ok(new BaseResponse<>(result, HttpStatus.OK.toString(), "Loan Details Retrieved"));
    }

    // Get all loans by customer
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getLoansByCustomer(@PathVariable Long customerId) {
        List<HomeLoanApplicationDto> result = homeLoanService.getLoansByCustomerId(customerId);
        return ResponseEntity.ok(new BaseResponse<>(result, HttpStatus.OK.toString(), "Customer Loans Retrieved"));
    }
}
