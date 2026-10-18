package com.canfin.corebanking.customerservice.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends Base implements Serializable {

    @EmbeddedId
    private CustomerKey customerKey;

    @Column(nullable = true,length = 50)
    private String memberFName="";

    @Column(nullable = true,length = 50)
    private String memberMName="";

    @Column(nullable = true,length = 50)
    private String memberLName="";

    @Column(nullable = true,length = 10)
    private String pan="";

    @Column(nullable = true,length = 30)
    private String customerType="";

    @Column(nullable = true,length = 30)
    private String custCategory="";

    @Column(nullable = false,length = 10)
    private String gender="";

    @Column(nullable = true)
    private Date dateOfBirth;

    @Column(nullable = true,length = 12)
    private String mobileNumber="";

    @Column(nullable = true,length = 12)
    private String email="";

    @Column(nullable = true,length = 20)
    private String residentStatus="";

    @Column(nullable = true,length = 1)
    private Integer isActive;

    @Column(nullable = true,length = 1)
    private String authStatus="";

    @Column(nullable = true,length = 20)
    private String customerStatus="";

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Address> addressList = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<KycDocument> kycDocumentList = new ArrayList<>();

    public void addAddress(Address address) {
        if(null==addressList){
            addressList=new ArrayList<>();
        }
        addressList.add(address);
        address.setCustomer(this);
    }

    public void addKycDocument(KycDocument kycDocument){
        if(null==kycDocumentList){
            kycDocumentList=new ArrayList<>();
        }
        kycDocumentList.add(kycDocument);
        kycDocument.setCustomer(this);
    }

    public CustomerKey getCustomerKey() {
        return customerKey;
    }

    public void setCustomerKey(CustomerKey customerKey) {
        this.customerKey = customerKey;
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

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustCategory() {
        return custCategory;
    }

    public void setCustCategory(String custCategory) {
        this.custCategory = custCategory;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

/*    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }*/

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    public List<KycDocument> getKycDocumentList() {
        return kycDocumentList;
    }

    public void setKycDocumentList(List<KycDocument> kycDocumentList) {
        this.kycDocumentList = kycDocumentList;
    }
}
