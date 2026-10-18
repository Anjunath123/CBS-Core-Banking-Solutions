package com.canfin.corebanking.customerservice.dto;

import com.canfin.corebanking.customerservice.enums.DisbursementType;
import com.canfin.corebanking.customerservice.enums.LoanStatus;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

public class HomeLoanApplicationDto {

    private Long id;
    private String loanAccountNumber;

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Branch code is required")
    private Integer branchCode;

    @NotBlank(message = "Applicant name is required")
    private String applicantName;

    @NotNull(message = "Loan amount is required")
    @DecimalMin(value = "100000", message = "Minimum loan amount is 1,00,000")
    private Double loanAmount;

    @NotNull(message = "Interest rate is required")
    private Double interestRate;

    @NotNull(message = "Tenure in months is required")
    @Min(value = 12, message = "Minimum tenure is 12 months")
    @Max(value = 360, message = "Maximum tenure is 360 months")
    private Integer tenureMonths;

    private Double emiAmount;
    private Double outstandingPrincipal;
    private Integer cibilScore;

    @NotNull(message = "Monthly income is required")
    private Double monthlyIncome;

    private Double existingEmiTotal = 0D;

    @NotBlank(message = "Property address is required")
    private String propertyAddress;

    private Double propertyValuation;

    @NotNull(message = "Disbursement type is required")
    private DisbursementType disbursementType;

    private String beneficiaryAccountNumber;
    private String beneficiaryName;

    private LoanStatus loanStatus;
    private Date applicationDate;
    private Date approvalDate;
    private Date disbursementDate;
    private Integer emisPaid;
    private Double penaltyAmount;
    private Double totalInterestPaid;

    private List<HomeLoanEmiScheduleDto> emiSchedule;

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

    public Integer getEmisPaid() { return emisPaid; }
    public void setEmisPaid(Integer emisPaid) { this.emisPaid = emisPaid; }

    public Double getPenaltyAmount() { return penaltyAmount; }
    public void setPenaltyAmount(Double penaltyAmount) { this.penaltyAmount = penaltyAmount; }

    public Double getTotalInterestPaid() { return totalInterestPaid; }
    public void setTotalInterestPaid(Double totalInterestPaid) { this.totalInterestPaid = totalInterestPaid; }

    public List<HomeLoanEmiScheduleDto> getEmiSchedule() { return emiSchedule; }
    public void setEmiSchedule(List<HomeLoanEmiScheduleDto> emiSchedule) { this.emiSchedule = emiSchedule; }
}
