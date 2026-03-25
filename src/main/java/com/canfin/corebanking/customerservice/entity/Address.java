package com.canfin.corebanking.customerservice.entity;


import javax.persistence.*;

@Entity
public class Address {

    @EmbeddedId
    private AddressKey addressKey;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "tenantId", referencedColumnName = "tenantId", insertable = false, updatable = false),
            @JoinColumn(name = "branchCode", referencedColumnName = "branchCode", insertable = false, updatable = false),
            @JoinColumn(name = "customerId", referencedColumnName = "customerId", insertable = false, updatable = false)
    })
    private Customer customer;

    @Column(nullable = true)
    private String addressType ="";

    @Column(nullable = true,length=250)
    private String address1 = "";

    @Column(nullable = true,length=250)
    private String address2 = "";

    @Column(nullable = true,length=250)
    private String address3 = "";

    @Column(nullable = true, length=60)
    private String cityCode = "";

    @Column(nullable = true, length=60)
    private String stateCode = "";

    @Column(nullable = true, length=60)
    private String countryCode = "";

    @Column(nullable = true,  length=20)
    private String pinCode = "";

    @Column(nullable = true,  length=20)
    private String status="";

    public AddressKey getAddressKey() {
        return addressKey;
    }

    public void setAddressKey(AddressKey addressKey) {
        this.addressKey = addressKey;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
