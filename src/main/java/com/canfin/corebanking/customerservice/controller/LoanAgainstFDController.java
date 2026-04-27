package com.canfin.corebanking.customerservice.controller;

import com.canfin.corebanking.customerservice.dto.BaseResponse;
import com.canfin.corebanking.customerservice.dto.LoanAgainstFDDto;
import com.canfin.corebanking.customerservice.service.LoanAgainstFDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/loan-against-fd")
public class LoanAgainstFDController {

    private static final Logger logger = LoggerFactory.getLogger(LoanAgainstFDController.class);

    @Autowired
    private LoanAgainstFDService loanAgainstFDService;

    @PostMapping("/create")
    public ResponseEntity<?> createLoan(@Valid @RequestBody LoanAgainstFDDto request) {
        logger.info("createLoanAgainstFD API request {}", request);
        LoanAgainstFDDto result = loanAgainstFDService.createLoanAgainstFD(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new BaseResponse<>(result, HttpStatus.CREATED.toString(), "Loan Against FD Created Successfully"));
    }

    @PutMapping("/approve")
    public ResponseEntity<?> approveLoan(@RequestParam String loanAccountNumber, @RequestParam Integer branchCode) {
        logger.info("approveLoanAgainstFD API request loanAccountNumber={}, branchCode={}", loanAccountNumber, branchCode);
        LoanAgainstFDDto result = loanAgainstFDService.approveLoanAgainstFD(loanAccountNumber, branchCode);
        BaseResponse<LoanAgainstFDDto> baseResponse = new BaseResponse<>();
        baseResponse.setData(result);
        baseResponse.setSuccessCode(HttpStatus.OK.toString());
        baseResponse.setSuccessMessage("Loan Against FD Approved Successfully");
        return ResponseEntity.ok(baseResponse);
    }
}
