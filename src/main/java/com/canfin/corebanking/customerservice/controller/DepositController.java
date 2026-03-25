package com.canfin.corebanking.customerservice.controller;


import com.canfin.corebanking.customerservice.dto.BaseResponse;
import com.canfin.corebanking.customerservice.dto.DepositAccountDto;
import com.canfin.corebanking.customerservice.service.DepositAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/deposit")
public class DepositController {

    //private static final Logger logger= (Logger) LoggerFactory.getLogger(DepositController.class);

    @Autowired
    private DepositAccountService depositAccountService;

    @RequestMapping("/createFDAccount")
    public ResponseEntity<?> createTermDeposit(@Valid @RequestBody DepositAccountDto request){
        BaseResponse<DepositAccountDto> baseResponse=new BaseResponse<>();
        DepositAccountDto depositAccountDto=depositAccountService.createFDAccount(request);
        if(null!=depositAccountDto){
            baseResponse.setData(depositAccountDto);
            baseResponse.setSuccessCode(HttpStatus.CREATED.toString());
            baseResponse.setSuccessMessage("Deposit Created Successfully");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(baseResponse);
    }

    @PutMapping("/approveTDAccount")
    public ResponseEntity<?> approveTDAccount(@RequestParam Integer branchCode,
                                              @RequestParam String accountNumber){
        BaseResponse<DepositAccountDto> baseResponse=new BaseResponse<>();
        DepositAccountDto depositAccountDto=depositAccountService.approveFDAccount(accountNumber,branchCode);
        if(null!=depositAccountDto){
            baseResponse.setData(depositAccountDto);
            baseResponse.setSuccessCode(HttpStatus.OK.toString());
            baseResponse.setSuccessMessage("FD Account Approved Successfully");
        }
        return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
    }

}
