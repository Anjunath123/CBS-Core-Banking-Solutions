package com.canfin.corebanking.customerservice.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    private Integer tenantId;
    private Long customerId;
    private Integer branchCode;
    private String memberFName="";
    private String memberMName="";
    private String memberLName="";
    private String pan="";
    private String custCategory="";
    private String customerType="";
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;
    private String gender="";
    private String residentStatus="";
    private Integer isActive;
    private String authStatus="";
    private String customerStatus="";
    private String dateOfBirthStr="";

    private List<AddressDto> addresses=new ArrayList<>();

    private List<KycDocumentDto> kycDocumentDtoList=new ArrayList<>();

    private List<CustomerBankInfoDto> customerBankInfoDtoList=new ArrayList<>();

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Integer getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(Integer branchCode) {
        this.branchCode = branchCode;
    }

    public String getMemberFName() {
        return memberFName;
    }

    public void setMemberFName(String memberFName) {
        this.memberFName = memberFName;
    }

    public String getMemberMName() {
        return memberMName;
    }

    public void setMemberMName(String memberMName) {
        this.memberMName = memberMName;
    }

    public String getMemberLName() {
        return memberLName;
    }

    public void setMemberLName(String memberLName) {
        this.memberLName = memberLName;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getCustCategory() {
        return custCategory;
    }

    public void setCustCategory(String custCategory) {
        this.custCategory = custCategory;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

/*    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }*/

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getResidentStatus() {
        return residentStatus;
    }

    public void setResidentStatus(String residentStatus) {
        this.residentStatus = residentStatus;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }

    public String getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(String customerStatus) {
        this.customerStatus = customerStatus;
    }

    public String getDateOfBirthStr() {
        return dateOfBirthStr;
    }

    public void setDateOfBirthStr(String dateOfBirthStr) {
        this.dateOfBirthStr = dateOfBirthStr;
    }

    public List<AddressDto> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressDto> addresses) {
        this.addresses = addresses;
    }

    public List<KycDocumentDto> getKycDocumentDtoList() {
        return kycDocumentDtoList;
    }

    public void setKycDocumentDtoList(List<KycDocumentDto> kycDocumentDtoList) {
        this.kycDocumentDtoList = kycDocumentDtoList;
    }

    public List<CustomerBankInfoDto> getCustomerBankInfoDtoList() {
        return customerBankInfoDtoList;
    }

    public void setCustomerBankInfoDtoList(List<CustomerBankInfoDto> customerBankInfoDtoList) {
        this.customerBankInfoDtoList = customerBankInfoDtoList;
    }
}
