package com.canfin.corebanking.customerservice.controller;

import com.canfin.corebanking.customerservice.dto.*;
import com.canfin.corebanking.customerservice.service.DepositAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/activity")
public class MaturityActivityController {

    private static final Logger logger = LoggerFactory.getLogger(MaturityActivityController.class);

    @Autowired
    private DepositAccountService depositAccountService;

    @PostMapping("/preClosureActivity")
    public ResponseEntity<?> preClosureActivity(@RequestBody PreClosureRequest preClosureRequest) {
        logger.info("preClosureActivity API request {}", preClosureRequest);
        BaseResponse<Map<String, Object>> responseMap = new BaseResponse<>();
        Map<String, Object> returnMap = depositAccountService.preClosureActivity(preClosureRequest);
        if (null != returnMap) {
            responseMap.setData(returnMap);
            responseMap.setSuccessMessage("Pre-Closure data fetched successfully");
            responseMap.setSuccessCode(HttpStatus.OK.toString());
        }
        return ResponseEntity.ok(responseMap);
    }

    @PostMapping("/closure")
    public ResponseEntity<?> closureActivity(@Valid @RequestBody ClosureRequest closureRequest) {
        logger.info("closureActivity API request {}", closureRequest);
        Map<String, Object> returnMap = depositAccountService.closureActivity(closureRequest);
        return ResponseEntity.ok(new BaseResponse<>(returnMap, HttpStatus.OK.toString(), "Deposit closed successfully"));
    }

    @PostMapping("/renewal")
    public ResponseEntity<?> renewDeposit(@Valid @RequestBody RenewalRequest renewalRequest) {
        logger.info("renewDeposit API request {}", renewalRequest);
        DepositAccountDto result = depositAccountService.renewDeposit(renewalRequest);
        return ResponseEntity.ok(new BaseResponse<>(result, HttpStatus.OK.toString(), "Deposit renewed successfully"));
    }

    @PostMapping("/partialRenewal")
    public ResponseEntity<?> partialRenewal(@Valid @RequestBody RenewalRequest renewalRequest) {
        logger.info("partialRenewal API request {}", renewalRequest);
        Map<String, Object> returnMap = depositAccountService.partialRenewal(renewalRequest);
        return ResponseEntity.ok(new BaseResponse<>(returnMap, HttpStatus.OK.toString(), "Partial renewal completed successfully"));
    }
}
