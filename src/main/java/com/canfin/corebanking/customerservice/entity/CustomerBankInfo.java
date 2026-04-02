package com.canfin.corebanking.customerservice.entity;

import javax.persistence.*;

@Entity
public class CustomerBankInfo {

    @EmbeddedId
     private CustomerBankInfoKey customerBankInfoKey;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "tenantId", referencedColumnName = "tenantId", insertable = false, updatable = false),
            @JoinColumn(name = "branchCode", referencedColumnName = "branchCode", insertable = false, updatable = false),
            @JoinColumn(name = "customerId", referencedColumnName = "customerId", insertable = false, updatable = false)
    })
    private Customer customer;

    @Column(nullable = true, length = 20)
    private String paymentMode = "";

    @Column(nullable = true, length = 50)
    private String accountNumber = "";

    @Column(nullable = true, length = 200)
    private String accountHolderName = "";

    @Column(nullable = true, length = 11)
    private String ifscCode = "";

    @Column(nullable = true, length = 9)
    private String micrCode = "";

    @Column(nullable = true, length = 200)
    private String bankName = "";

    @Column(nullable = true, length = 200)
    private String branchName = "";

    @Column(nullable = true, length = 1)
    private Integer primaryAccount = 0;

    @Column(nullable = true)
    private String statusRefId = "";

    @Column(nullable = true,length =1)
    private Integer isActive;

    @Column(nullable = true,length =1)
    private String authStatus="";

    @Column(nullable = true,length = 50)
    private String status="";

    public CustomerBankInfoKey getCustomerBankInfoKey() {
        return customerBankInfoKey;
    }

    public void setCustomerBankInfoKey(CustomerBankInfoKey customerBankInfoKey) {
        this.customerBankInfoKey = customerBankInfoKey;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getMicrCode() {
        return micrCode;
    }

    public void setMicrCode(String micrCode) {
        this.micrCode = micrCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Integer getPrimaryAccount() {
        return primaryAccount;
    }

    public void setPrimaryAccount(Integer primaryAccount) {
        this.primaryAccount = primaryAccount;
    }

    public String getStatusRefId() {
        return statusRefId;
    }

    public void setStatusRefId(String statusRefId) {
        this.statusRefId = statusRefId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
