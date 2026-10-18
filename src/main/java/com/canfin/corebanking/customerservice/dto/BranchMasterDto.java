package com.canfin.corebanking.customerservice.dto;

import javax.validation.constraints.NotBlank;

public class BranchMasterDto {

    private Long id;

    @NotBlank(message = "Branch Code is required")
    private String branchCode;

    @NotBlank(message = "Branch Name is required")
    private String branchName;

    private String branchType;
    private String ifscCode;
    private String micrCode;
    private String branchEmailId;

    // Branch Address
    private String buildingName;
    private String streetName;
    private String landmark;
    private String localityName;
    private String countryCode;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBranchCode() { return branchCode; }
    public void setBranchCode(String branchCode) { this.branchCode = branchCode; }

    public String getBranchName() { return branchName; }
    public void setBranchName(String branchName) { this.branchName = branchName; }

    public String getBranchType() { return branchType; }
    public void setBranchType(String branchType) { this.branchType = branchType; }

    public String getIfscCode() { return ifscCode; }
    public void setIfscCode(String ifscCode) { this.ifscCode = ifscCode; }

    public String getMicrCode() { return micrCode; }
    public void setMicrCode(String micrCode) { this.micrCode = micrCode; }

    public String getBranchEmailId() { return branchEmailId; }
    public void setBranchEmailId(String branchEmailId) { this.branchEmailId = branchEmailId; }

    public String getBuildingName() { return buildingName; }
    public void setBuildingName(String buildingName) { this.buildingName = buildingName; }

    public String getStreetName() { return streetName; }
    public void setStreetName(String streetName) { this.streetName = streetName; }

    public String getLandmark() { return landmark; }
    public void setLandmark(String landmark) { this.landmark = landmark; }

    public String getLocalityName() { return localityName; }
    public void setLocalityName(String localityName) { this.localityName = localityName; }

    public String getCountryCode() { return countryCode; }
    public void setCountryCode(String countryCode) { this.countryCode = countryCode; }
}
