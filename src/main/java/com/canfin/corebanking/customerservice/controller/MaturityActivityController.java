package com.canfin.corebanking.customerservice.controller;

import com.canfin.corebanking.customerservice.dto.BaseResponse;
import com.canfin.corebanking.customerservice.dto.PreClosureRequest;
import com.canfin.corebanking.customerservice.service.DepositAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/activity")
public class MaturityActivityController {

    @Autowired
    private DepositAccountService depositAccountService;

    @GetMapping("/preClosureActivity")
    public ResponseEntity<?> preClosureActivity(@RequestBody PreClosureRequest preClosureRequest){
        BaseResponse<Map<String, Object>> responseMap = new BaseResponse<>();
        Map<String,Object> returnMap=depositAccountService.preClosureActivity(preClosureRequest);
        if(null!=returnMap){
            responseMap.setData(returnMap);
            responseMap.setSuccessMessage("Data fetched Successfully");
            responseMap.setSuccessCode(HttpStatus.OK.toString());
        }
        return  ResponseEntity.ok(responseMap);
    }


}
