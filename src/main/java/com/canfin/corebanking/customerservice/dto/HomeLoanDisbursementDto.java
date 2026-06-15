package com.canfin.corebanking.customerservice.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class HomeLoanDisbursementDto {

    @NotBlank(message = "Loan account number is required")
    private String loanAccountNumber;

    @NotBlank(message = "Beneficiary account number is required")
    private String beneficiaryAccountNumber;

    @NotBlank(message = "Beneficiary name is required")
    private String beneficiaryName;

    // Getters and Setters
    public String getLoanAccountNumber() { return loanAccountNumber; }
    public void setLoanAccountNumber(String loanAccountNumber) { this.loanAccountNumber = loanAccountNumber; }

    public String getBeneficiaryAccountNumber() { return beneficiaryAccountNumber; }
    public void setBeneficiaryAccountNumber(String beneficiaryAccountNumber) { this.beneficiaryAccountNumber = beneficiaryAccountNumber; }

    public String getBeneficiaryName() { return beneficiaryName; }
    public void setBeneficiaryName(String beneficiaryName) { this.beneficiaryName = beneficiaryName; }
}
