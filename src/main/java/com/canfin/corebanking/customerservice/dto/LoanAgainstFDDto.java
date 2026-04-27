package com.canfin.corebanking.customerservice.dto;

import javax.validation.constraints.*;
import java.util.Date;

public class LoanAgainstFDDto {

    @NotNull(message = "Branch code is required")
    private Integer branchCode;

    private String loanAccountNumber = "";

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotBlank(message = "FD Account Number is required")
    private String fdAccountNumber = "";

    @NotBlank(message = "Account name is required")
    private String accountName = "";

    @NotNull(message = "Loan amount is required")
    @DecimalMin(value = "0.01", message = "Loan amount must be greater than 0")
    private Double loanAmount = 0D;

    private Double fdDepositAmount = 0D;

    @NotNull(message = "Interest rate is required")
    @DecimalMin(value = "0.0", message = "Interest rate cannot be negative")
    private Double intRate = 0D;

    @NotNull(message = "Loan tenure is required")
    @Min(value = 1, message = "Loan tenure must be at least 1 month")
    private Integer loanTenureMonths;

    @NotNull(message = "Disbursement date is required")
    private Date disbursementDate;

    private Date maturityDate;

    @NotBlank(message = "Currency code is required")
    private String currencyCode = "";

    private Double outstandingAmount = 0D;
    private Double interestAccrued = 0D;
    private String remarks = "";

    @NotBlank(message = "Loan purpose is required")
    private String loanPurpose = "";

    @NotBlank(message = "Repayment mode is required")
    private String repaymentMode = "";

    @NotBlank(message = "Repayment account number is required")
    private String repaymentAccountNumber = "";

    private String authStatus = "";
    private Integer loanStatus;

    public Integer getBranchCode() { return branchCode; }
    public void setBranchCode(Integer branchCode) { this.branchCode = branchCode; }

    public String getLoanAccountNumber() { return loanAccountNumber; }
    public void setLoanAccountNumber(String loanAccountNumber) { this.loanAccountNumber = loanAccountNumber; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public String getFdAccountNumber() { return fdAccountNumber; }
    public void setFdAccountNumber(String fdAccountNumber) { this.fdAccountNumber = fdAccountNumber; }

    public String getAccountName() { return accountName; }
    public void setAccountName(String accountName) { this.accountName = accountName; }

    public Double getLoanAmount() { return loanAmount; }
    public void setLoanAmount(Double loanAmount) { this.loanAmount = loanAmount; }

    public Double getFdDepositAmount() { return fdDepositAmount; }
    public void setFdDepositAmount(Double fdDepositAmount) { this.fdDepositAmount = fdDepositAmount; }

    public Double getIntRate() { return intRate; }
    public void setIntRate(Double intRate) { this.intRate = intRate; }

    public Integer getLoanTenureMonths() { return loanTenureMonths; }
    public void setLoanTenureMonths(Integer loanTenureMonths) { this.loanTenureMonths = loanTenureMonths; }

    public Date getDisbursementDate() { return disbursementDate; }
    public void setDisbursementDate(Date disbursementDate) { this.disbursementDate = disbursementDate; }

    public Date getMaturityDate() { return maturityDate; }
    public void setMaturityDate(Date maturityDate) { this.maturityDate = maturityDate; }

    public String getCurrencyCode() { return currencyCode; }
    public void setCurrencyCode(String currencyCode) { this.currencyCode = currencyCode; }

    public Double getOutstandingAmount() { return outstandingAmount; }
    public void setOutstandingAmount(Double outstandingAmount) { this.outstandingAmount = outstandingAmount; }

    public Double getInterestAccrued() { return interestAccrued; }
    public void setInterestAccrued(Double interestAccrued) { this.interestAccrued = interestAccrued; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public String getLoanPurpose() { return loanPurpose; }
    public void setLoanPurpose(String loanPurpose) { this.loanPurpose = loanPurpose; }

    public String getRepaymentMode() { return repaymentMode; }
    public void setRepaymentMode(String repaymentMode) { this.repaymentMode = repaymentMode; }

    public String getRepaymentAccountNumber() { return repaymentAccountNumber; }
    public void setRepaymentAccountNumber(String repaymentAccountNumber) { this.repaymentAccountNumber = repaymentAccountNumber; }

    public String getAuthStatus() { return authStatus; }
    public void setAuthStatus(String authStatus) { this.authStatus = authStatus; }

    public Integer getLoanStatus() { return loanStatus; }
    public void setLoanStatus(Integer loanStatus) { this.loanStatus = loanStatus; }
}
