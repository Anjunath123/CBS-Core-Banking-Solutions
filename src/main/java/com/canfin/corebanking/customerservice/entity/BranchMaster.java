package com.canfin.corebanking.customerservice.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "BRANCH_MASTER")
public class BranchMaster extends Base implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String branchCode;

    @Column(nullable = false, length = 100)
    private String branchName;

    @Column(length = 30)
    private String branchType;

    @Column(length = 20)
    private String ifscCode;

    @Column(length = 20)
    private String micrCode;

    @Column(length = 100)
    private String branchEmailId;

    // Branch Address
    @Column(length = 200)
    private String buildingName;

    @Column(length = 200)
    private String streetName;

    @Column(length = 200)
    private String landmark;

    @Column(length = 200)
    private String localityName;

    @Column(length = 10)
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
