package com.canfin.corebanking.customerservice.controller;

import com.canfin.corebanking.customerservice.dto.BaseResponse;
import com.canfin.corebanking.customerservice.dto.CreditDebitRequest;
import com.canfin.corebanking.customerservice.dto.SavingsAccountDto;
import com.canfin.corebanking.customerservice.service.SavingsAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/savings")
public class SavingsAccountController {

    private static final Logger logger = LoggerFactory.getLogger(SavingsAccountController.class);

    @Autowired
    private SavingsAccountService savingsAccountService;

    @PostMapping("/create")
    public ResponseEntity<?> createSavingsAccount(@Valid @RequestBody SavingsAccountDto request) {
        logger.info("createSavingsAccount API request for customer {}", request.getCustomerId());
        SavingsAccountDto response = savingsAccountService.createSavingsAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new BaseResponse<>(response, HttpStatus.CREATED.toString(), "Savings account created successfully"));
    }

    @PutMapping("/approve")
    public ResponseEntity<?> approveSavingsAccount(@RequestParam String accountNumber, @RequestParam Integer branchCode) {
        logger.info("approveSavingsAccount API request for account {}", accountNumber);
        SavingsAccountDto response = savingsAccountService.approveSavingsAccount(accountNumber, branchCode);
        return ResponseEntity.ok(new BaseResponse<>(response, HttpStatus.OK.toString(), "Savings account approved successfully"));
    }

    @PostMapping("/credit")
    public ResponseEntity<?> creditAmount(@Valid @RequestBody CreditDebitRequest request) {
        logger.info("creditAmount API request for account {} amount {}", request.getAccountNumber(), request.getAmount());
        SavingsAccountDto response = savingsAccountService.creditAmount(request);
        return ResponseEntity.ok(new BaseResponse<>(response, HttpStatus.OK.toString(), "Amount credited successfully"));
    }

    @GetMapping("/balance")
    public ResponseEntity<?> getBalance(@RequestParam String accountNumber, @RequestParam Integer branchCode) {
        logger.info("getBalance API request for account {}", accountNumber);
        SavingsAccountDto response = savingsAccountService.getAccountDetails(accountNumber, branchCode);
        return ResponseEntity.ok(new BaseResponse<>(response, HttpStatus.OK.toString(), "Balance fetched successfully"));
    }
}
