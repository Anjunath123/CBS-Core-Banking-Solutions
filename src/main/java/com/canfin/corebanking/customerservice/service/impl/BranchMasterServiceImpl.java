package com.canfin.corebanking.customerservice.service.impl;

import com.canfin.corebanking.customerservice.dto.BranchMasterDto;
import com.canfin.corebanking.customerservice.entity.BranchMaster;
import com.canfin.corebanking.customerservice.exception.OmniNGException;
import com.canfin.corebanking.customerservice.repository.BranchMasterRepository;
import com.canfin.corebanking.customerservice.service.BranchMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BranchMasterServiceImpl implements BranchMasterService {

    @Autowired
    private BranchMasterRepository branchMasterRepository;

    @Override
    public BranchMasterDto saveBranch(BranchMasterDto dto) {
        if (branchMasterRepository.existsByBranchCode(dto.getBranchCode())) {
            throw new OmniNGException("Branch with code " + dto.getBranchCode() + " already exists");
        }
        BranchMaster entity = toEntity(dto);
        return toDto(branchMasterRepository.save(entity));
    }

    @Override
    public BranchMasterDto updateBranch(BranchMasterDto dto) {
        BranchMaster entity = branchMasterRepository.findByBranchCode(dto.getBranchCode())
                .orElseThrow(() -> new OmniNGException("Branch not found with code: " + dto.getBranchCode()));
        mapDtoToEntity(dto, entity);
        return toDto(branchMasterRepository.save(entity));
    }

    @Override
    public BranchMasterDto getBranchByCode(String branchCode) {
        BranchMaster entity = branchMasterRepository.findByBranchCode(branchCode)
                .orElseThrow(() -> new OmniNGException("Branch not found with code: " + branchCode));
        return toDto(entity);
    }

    @Override
    public List<BranchMasterDto> getAllBranches() {
        return branchMasterRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteBranch(String branchCode) {
        BranchMaster entity = branchMasterRepository.findByBranchCode(branchCode)
                .orElseThrow(() -> new OmniNGException("Branch not found with code: " + branchCode));
        branchMasterRepository.delete(entity);
    }

    @Override
    public Map<String, Object> getBranchesPaginated(int page, int size) {
        Page<BranchMaster> branchPage = branchMasterRepository.findAll(PageRequest.of(page, size));
        Map<String, Object> result = new HashMap<>();
        result.put("branches", branchPage.getContent().stream().map(this::toDto).collect(Collectors.toList()));
        result.put("currentPage", branchPage.getNumber());
        result.put("totalPages", branchPage.getTotalPages());
        result.put("totalRecords", branchPage.getTotalElements());
        return result;
    }

    private BranchMaster toEntity(BranchMasterDto dto) {
        BranchMaster entity = new BranchMaster();
        mapDtoToEntity(dto, entity);
        return entity;
    }

    private void mapDtoToEntity(BranchMasterDto dto, BranchMaster entity) {
        entity.setBranchCode(dto.getBranchCode());
        entity.setBranchName(dto.getBranchName());
        entity.setBranchType(dto.getBranchType());
        entity.setIfscCode(dto.getIfscCode());
        entity.setMicrCode(dto.getMicrCode());
        entity.setBranchEmailId(dto.getBranchEmailId());
        entity.setBuildingName(dto.getBuildingName());
        entity.setStreetName(dto.getStreetName());
        entity.setLandmark(dto.getLandmark());
        entity.setLocalityName(dto.getLocalityName());
        entity.setCountryCode(dto.getCountryCode());
    }

    private BranchMasterDto toDto(BranchMaster entity) {
        BranchMasterDto dto = new BranchMasterDto();
        dto.setId(entity.getId());
        dto.setBranchCode(entity.getBranchCode());
        dto.setBranchName(entity.getBranchName());
        dto.setBranchType(entity.getBranchType());
        dto.setIfscCode(entity.getIfscCode());
        dto.setMicrCode(entity.getMicrCode());
        dto.setBranchEmailId(entity.getBranchEmailId());
        dto.setBuildingName(entity.getBuildingName());
        dto.setStreetName(entity.getStreetName());
        dto.setLandmark(entity.getLandmark());
        dto.setLocalityName(entity.getLocalityName());
        dto.setCountryCode(entity.getCountryCode());
        return dto;
    }
}
