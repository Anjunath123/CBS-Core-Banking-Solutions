package com.canfin.corebanking.customerservice.entity;

import com.canfin.corebanking.customerservice.enums.DisbursementType;
import com.canfin.corebanking.customerservice.enums.LoanStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "home_loan")
public class HomeLoan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 32)
    private String loanAccountNumber;

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false, length = 5)
    private Integer branchCode;

    @Column(length = 100)
    private String applicantName;

    @Column(nullable = false)
    private Double loanAmount;

    @Column(nullable = false)
    private Double interestRate;

    @Column(nullable = false)
    private Integer tenureMonths;

    @Column(nullable = false)
    private Double emiAmount = 0D;

    @Column(nullable = false)
    private Double outstandingPrincipal = 0D;

    @Column(nullable = false)
    private Double totalInterestPaid = 0D;

    private Integer cibilScore;

    private Double monthlyIncome;

    private Double existingEmiTotal = 0D;

    @Column(length = 200)
    private String propertyAddress;

    private Double propertyValuation;

    @Enumerated(EnumType.STRING)
    private DisbursementType disbursementType;

    @Column(length = 32)
    private String beneficiaryAccountNumber;

    @Column(length = 100)
    private String beneficiaryName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanStatus loanStatus = LoanStatus.APPLIED;

    private Date applicationDate;

    private Date approvalDate;

    private Date disbursementDate;

    private Date closureDate;

    private Integer emisPaid = 0;

    private Double penaltyAmount = 0D;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLoanAccountNumber() { return loanAccountNumber; }
    public void setLoanAccountNumber(String loanAccountNumber) { this.loanAccountNumber = loanAccountNumber; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public Integer getBranchCode() { return branchCode; }
    public void setBranchCode(Integer branchCode) { this.branchCode = branchCode; }

    public String getApplicantName() { return applicantName; }
    public void setApplicantName(String applicantName) { this.applicantName = applicantName; }

    public Double getLoanAmount() { return loanAmount; }
    public void setLoanAmount(Double loanAmount) { this.loanAmount = loanAmount; }

    public Double getInterestRate() { return interestRate; }
    public void setInterestRate(Double interestRate) { this.interestRate = interestRate; }

    public Integer getTenureMonths() { return tenureMonths; }
    public void setTenureMonths(Integer tenureMonths) { this.tenureMonths = tenureMonths; }

    public Double getEmiAmount() { return emiAmount; }
    public void setEmiAmount(Double emiAmount) { this.emiAmount = emiAmount; }

    public Double getOutstandingPrincipal() { return outstandingPrincipal; }
    public void setOutstandingPrincipal(Double outstandingPrincipal) { this.outstandingPrincipal = outstandingPrincipal; }

    public Double getTotalInterestPaid() { return totalInterestPaid; }
    public void setTotalInterestPaid(Double totalInterestPaid) { this.totalInterestPaid = totalInterestPaid; }

    public Integer getCibilScore() { return cibilScore; }
    public void setCibilScore(Integer cibilScore) { this.cibilScore = cibilScore; }

    public Double getMonthlyIncome() { return monthlyIncome; }
    public void setMonthlyIncome(Double monthlyIncome) { this.monthlyIncome = monthlyIncome; }

    public Double getExistingEmiTotal() { return existingEmiTotal; }
    public void setExistingEmiTotal(Double existingEmiTotal) { this.existingEmiTotal = existingEmiTotal; }

    public String getPropertyAddress() { return propertyAddress; }
    public void setPropertyAddress(String propertyAddress) { this.propertyAddress = propertyAddress; }

    public Double getPropertyValuation() { return propertyValuation; }
    public void setPropertyValuation(Double propertyValuation) { this.propertyValuation = propertyValuation; }

    public DisbursementType getDisbursementType() { return disbursementType; }
    public void setDisbursementType(DisbursementType disbursementType) { this.disbursementType = disbursementType; }

    public String getBeneficiaryAccountNumber() { return beneficiaryAccountNumber; }
    public void setBeneficiaryAccountNumber(String beneficiaryAccountNumber) { this.beneficiaryAccountNumber = beneficiaryAccountNumber; }

    public String getBeneficiaryName() { return beneficiaryName; }
    public void setBeneficiaryName(String beneficiaryName) { this.beneficiaryName = beneficiaryName; }

    public LoanStatus getLoanStatus() { return loanStatus; }
    public void setLoanStatus(LoanStatus loanStatus) { this.loanStatus = loanStatus; }

    public Date getApplicationDate() { return applicationDate; }
    public void setApplicationDate(Date applicationDate) { this.applicationDate = applicationDate; }

    public Date getApprovalDate() { return approvalDate; }
    public void setApprovalDate(Date approvalDate) { this.approvalDate = approvalDate; }

    public Date getDisbursementDate() { return disbursementDate; }
    public void setDisbursementDate(Date disbursementDate) { this.disbursementDate = disbursementDate; }

    public Date getClosureDate() { return closureDate; }
    public void setClosureDate(Date closureDate) { this.closureDate = closureDate; }

    public Integer getEmisPaid() { return emisPaid; }
    public void setEmisPaid(Integer emisPaid) { this.emisPaid = emisPaid; }

    public Double getPenaltyAmount() { return penaltyAmount; }
    public void setPenaltyAmount(Double penaltyAmount) { this.penaltyAmount = penaltyAmount; }
}
