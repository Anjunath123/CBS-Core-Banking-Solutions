package com.canfin.corebanking.customerservice.dto;

public class ApproveTDRequestDto {

    private String accountNumber="";

    private Integer branchCode;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Integer getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(Integer branchCode) {
        this.branchCode = branchCode;
    }
}
