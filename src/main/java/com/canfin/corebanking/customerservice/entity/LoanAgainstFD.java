package com.canfin.corebanking.customerservice.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class LoanAgainstFD implements Serializable {

    @EmbeddedId
    private LoanAgainstFDKey loanAgainstFDKey;

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false, length = 32)
    private String fdAccountNumber = "";

    @Column(nullable = true, length = 100)
    private String accountName = "";

    @Column(nullable = true)
    private Double loanAmount = 0D;

    @Column(nullable = true)
    private Double fdDepositAmount = 0D;

    @Column(nullable = true)
    private Double intRate = 0D;

    @Column(nullable = true, length = 4)
    private Integer loanTenureMonths;

    @Column(nullable = true)
    private Date disbursementDate;

    @Column(nullable = true)
    private Date maturityDate;

    @Column(nullable = true, length = 4)
    private String currencyCode = "";

    @Column(nullable = true)
    private Double outstandingAmount = 0D;

    @Column(nullable = true)
    private Double interestAccrued = 0D;

    @Column(nullable = true, length = 250)
    private String remarks = "";

    @Column(nullable = true, length = 50)
    private String loanPurpose = "";

    @Column(nullable = true, length = 50)
    private String repaymentMode = "";

    @Column(nullable = true, length = 32)
    private String repaymentAccountNumber = "";

    @Column(nullable = true, length = 1)
    private String authStatus = "";

    @Column(nullable = true, length = 1)
    private Integer isActive;

    @Column(nullable = true)
    private Integer loanStatus;

    public LoanAgainstFDKey getLoanAgainstFDKey() { return loanAgainstFDKey; }
    public void setLoanAgainstFDKey(LoanAgainstFDKey loanAgainstFDKey) { this.loanAgainstFDKey = loanAgainstFDKey; }

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

    public Integer getIsActive() { return isActive; }
    public void setIsActive(Integer isActive) { this.isActive = isActive; }

    public Integer getLoanStatus() { return loanStatus; }
    public void setLoanStatus(Integer loanStatus) { this.loanStatus = loanStatus; }
}
