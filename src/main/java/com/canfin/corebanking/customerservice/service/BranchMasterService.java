package com.canfin.corebanking.customerservice.service;

import com.canfin.corebanking.customerservice.dto.BranchMasterDto;
import java.util.List;
import java.util.Map;

public interface BranchMasterService {
    BranchMasterDto saveBranch(BranchMasterDto dto);
    BranchMasterDto updateBranch(BranchMasterDto dto);
    BranchMasterDto getBranchByCode(String branchCode);
    List<BranchMasterDto> getAllBranches();
    void deleteBranch(String branchCode);
    Map<String, Object> getBranchesPaginated(int page, int size);
}
