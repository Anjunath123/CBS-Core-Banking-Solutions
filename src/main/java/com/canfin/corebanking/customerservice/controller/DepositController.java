package com.canfin.corebanking.customerservice.controller;


import com.canfin.corebanking.customerservice.dto.ApproveTDRequestDto;
import com.canfin.corebanking.customerservice.dto.BaseResponse;
import com.canfin.corebanking.customerservice.dto.DepositAccountDto;
import com.canfin.corebanking.customerservice.service.DepositAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/v1/deposit")
public class DepositController {

    private static final Logger logger= (Logger) LoggerFactory.getLogger(DepositController.class);

    @Autowired
    private DepositAccountService depositAccountService;

    @PostMapping(value="/createFDAccount")
    public ResponseEntity<?> createTermDeposit(@Valid @RequestBody DepositAccountDto request){
        logger.info("createTermDeposit API request {}", request);
        DepositAccountDto depositAccountDto=depositAccountService.createFDAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<DepositAccountDto>(depositAccountDto,
                HttpStatus.CREATED.toString(), "Deposit Created Successfully"));
    }

    @PutMapping("/approveTDAccount")
    public ResponseEntity<?> approveTDAccount(@RequestBody ApproveTDRequestDto request){
        logger.info("approveTDAccount API request {}", request);
        BaseResponse<DepositAccountDto> baseResponse=new BaseResponse<>();
        DepositAccountDto depositAccountDto=depositAccountService.approveFDAccount(request.getAccountNumber(),request.getBranchCode());
        baseResponse.setData(depositAccountDto);
        baseResponse.setSuccessCode(HttpStatus.OK.toString());
        baseResponse.setSuccessMessage("FD Account Approved Successfully");
        return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
    }

}
