package com.canfin.corebanking.customerservice.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RenewalRequest {

    @NotNull(message = "Branch code is required")
    private Integer branchCode;

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotBlank(message = "Account number is required")
    private String accountNumber;

    private Integer renewalMonths;

    private Integer renewalDays;

    private Double renewalAmount;

    private Double newIntRate;

    private String remarks;

    public Integer getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(Integer branchCode) {
        this.branchCode = branchCode;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Integer getRenewalMonths() {
        return renewalMonths;
    }

    public void setRenewalMonths(Integer renewalMonths) {
        this.renewalMonths = renewalMonths;
    }

    public Integer getRenewalDays() {
        return renewalDays;
    }

    public void setRenewalDays(Integer renewalDays) {
        this.renewalDays = renewalDays;
    }

    public Double getRenewalAmount() {
        return renewalAmount;
    }

    public void setRenewalAmount(Double renewalAmount) {
        this.renewalAmount = renewalAmount;
    }

    public Double getNewIntRate() {
        return newIntRate;
    }

    public void setNewIntRate(Double newIntRate) {
        this.newIntRate = newIntRate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
