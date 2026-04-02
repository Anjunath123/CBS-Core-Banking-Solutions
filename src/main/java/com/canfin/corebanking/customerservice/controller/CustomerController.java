package com.canfin.corebanking.customerservice.controller;


import com.canfin.corebanking.customerservice.dto.BaseResponse;
import com.canfin.corebanking.customerservice.dto.CustomerDto;
import com.canfin.corebanking.customerservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private  CustomerService customerService;


    @PostMapping(value="/saveCustomer", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> saveCustomer(@Valid @RequestBody CustomerDto request) {
        logger.info("inside saveCustomer API: {}", request);
        BaseResponse<CustomerDto> baseResponse=new BaseResponse<>();
        CustomerDto response = customerService.saveCustomer(request);
        baseResponse.setData(response);
        baseResponse.setSuccessCode(HttpStatus.CREATED.toString());
        baseResponse.setSuccessMessage("Customer Registered Successfully");
        return ResponseEntity.ok(baseResponse);
    }


    @PostMapping(value="/updateCustomer", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateCustomer(@Valid @RequestBody CustomerDto request) {
        logger.info("inside updateCustomer API: {}", request);
        BaseResponse<CustomerDto> baseResponse=new BaseResponse<>();
        CustomerDto response = customerService.updateCustomer(request);
        baseResponse.setData(response);
        baseResponse.setSuccessCode(HttpStatus.OK.toString());
        baseResponse.setSuccessMessage("Customer Updated Successfully");
        return ResponseEntity.ok(baseResponse);
    }

    @PutMapping(value="/approveCustomer", produces = "application/json")
    public ResponseEntity<?> approveCustomer(@RequestParam Long customerId,
                                             @RequestParam Integer branchCode) {
        BaseResponse<CustomerDto> baseResponse=new BaseResponse<>();
        CustomerDto response = customerService.approveCustomer(customerId,branchCode);
        baseResponse.setData(response);
        baseResponse.setSuccessCode(HttpStatus.OK.toString());
        baseResponse.setSuccessMessage("Customer Approved Successfully");
        return ResponseEntity.ok(baseResponse);
    }

    @PutMapping(value="/rejectCustomer", produces = "application/json")
    public ResponseEntity<?> rejectCustomer(@RequestParam Long customerId,
                                            @RequestParam Integer branchCode){
        BaseResponse<CustomerDto> baseResponse=new BaseResponse<>();
        CustomerDto response = customerService.rejectCustomer(customerId,branchCode);
        baseResponse.setData(response);
        baseResponse.setSuccessCode(HttpStatus.OK.toString());
        baseResponse.setSuccessMessage("Customer Rejected Successfully");
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping(value="/getCustomer", produces = "application/json")
    public ResponseEntity<?> fetchCustomerDetails(@RequestParam Long customerId,
                                                  @RequestParam Integer branchCode){
        BaseResponse<CustomerDto> baseResponse=new BaseResponse<>();
        CustomerDto response = customerService.getCustomerDetails(branchCode,customerId);
        baseResponse.setData(response);
        baseResponse.setSuccessCode(HttpStatus.OK.toString());
        baseResponse.setSuccessMessage("Customer Fetched Successfully");
        return ResponseEntity.ok(baseResponse);
    }

    @DeleteMapping(value="/deleteCustomer", produces = "application/json")
    public ResponseEntity<String> deleteCustomer(@RequestParam Long customerId,
                                                 @RequestParam Integer branchCode){

        customerService.deleteCustomer(branchCode, customerId);
        return ResponseEntity.ok("message: Customer Deleted Successfully");
    }

}



