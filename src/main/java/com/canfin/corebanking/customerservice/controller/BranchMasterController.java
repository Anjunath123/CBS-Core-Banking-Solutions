package com.canfin.corebanking.customerservice.controller;

import com.canfin.corebanking.customerservice.dto.BaseResponse;
import com.canfin.corebanking.customerservice.dto.BranchMasterDto;
import com.canfin.corebanking.customerservice.service.BranchMasterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/branch-master")
@CrossOrigin
public class BranchMasterController {

    private static final Logger logger = LoggerFactory.getLogger(BranchMasterController.class);

    @Autowired
    private BranchMasterService branchMasterService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> saveBranch(@Valid @RequestBody BranchMasterDto request) {
        logger.info("Save Branch Master: {}", request.getBranchCode());
        BaseResponse<BranchMasterDto> baseResponse = new BaseResponse<>();
        BranchMasterDto response = branchMasterService.saveBranch(request);
        baseResponse.setData(response);
        baseResponse.setSuccessCode(HttpStatus.CREATED.toString());
        baseResponse.setSuccessMessage("Branch Created Successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(baseResponse);
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateBranch(@Valid @RequestBody BranchMasterDto request) {
        logger.info("Update Branch Master: {}", request.getBranchCode());
        BaseResponse<BranchMasterDto> baseResponse = new BaseResponse<>();
        BranchMasterDto response = branchMasterService.updateBranch(request);
        baseResponse.setData(response);
        baseResponse.setSuccessCode(HttpStatus.OK.toString());
        baseResponse.setSuccessMessage("Branch Updated Successfully");
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping(value = "/{branchCode}", produces = "application/json")
    public ResponseEntity<?> getBranch(@PathVariable String branchCode) {
        BaseResponse<BranchMasterDto> baseResponse = new BaseResponse<>();
        BranchMasterDto response = branchMasterService.getBranchByCode(branchCode);
        baseResponse.setData(response);
        baseResponse.setSuccessCode(HttpStatus.OK.toString());
        baseResponse.setSuccessMessage("Branch Fetched Successfully");
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<?> getAllBranches() {
        BaseResponse<List<BranchMasterDto>> baseResponse = new BaseResponse<>();
        List<BranchMasterDto> response = branchMasterService.getAllBranches();
        baseResponse.setData(response);
        baseResponse.setSuccessCode(HttpStatus.OK.toString());
        baseResponse.setSuccessMessage("Branches Fetched Successfully");
        return ResponseEntity.ok(baseResponse);
    }

    @DeleteMapping(value = "/{branchCode}", produces = "application/json")
    public ResponseEntity<?> deleteBranch(@PathVariable String branchCode) {
        branchMasterService.deleteBranch(branchCode);
        BaseResponse<String> baseResponse = new BaseResponse<>();
        baseResponse.setSuccessCode(HttpStatus.OK.toString());
        baseResponse.setSuccessMessage("Branch Deleted Successfully");
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping(value = "/lookup", produces = "application/json")
    public ResponseEntity<?> branchLookup(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "5") int size) {
        BaseResponse<Map<String, Object>> baseResponse = new BaseResponse<>();
        Map<String, Object> response = branchMasterService.getBranchesPaginated(page, size);
        baseResponse.setData(response);
        baseResponse.setSuccessCode(HttpStatus.OK.toString());
        baseResponse.setSuccessMessage("Branches Fetched Successfully");
        return ResponseEntity.ok(baseResponse);
    }
}
