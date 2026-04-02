package com.canfin.corebanking.customerservice.dto;

public class CustomerBankInfoDto {
    
    private Integer srNo = 0;
    private Long customerId;
    private Integer branchCode;
    private String paymentMode = "";
    private String accountNumber = "";
    private String accountHolderName = "";
    private String ifscCode = "";
    private String micrCode = "";
    private String bankName = "";
    private String branchName = "";
    private Integer primaryAccount = 0;
    private String statusRefId = "";

    public Integer getSrNo() {
        return srNo;
    }

    public void setSrNo(Integer srNo) {
        this.srNo = srNo;
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
}
