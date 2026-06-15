package com.canfin.corebanking.customerservice.dto;

import java.util.Date;

public class HomeLoanEmiScheduleDto {

    private Long id;
    private String loanAccountNumber;
    private Integer installmentNumber;
    private Date dueDate;
    private Double emiAmount;
    private Double principalComponent;
    private Double interestComponent;
    private Double outstandingAfterPayment;
    private boolean paid;
    private Date paidDate;
    private Double penaltyApplied;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLoanAccountNumber() { return loanAccountNumber; }
    public void setLoanAccountNumber(String loanAccountNumber) { this.loanAccountNumber = loanAccountNumber; }

    public Integer getInstallmentNumber() { return installmentNumber; }
    public void setInstallmentNumber(Integer installmentNumber) { this.installmentNumber = installmentNumber; }

    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }

    public Double getEmiAmount() { return emiAmount; }
    public void setEmiAmount(Double emiAmount) { this.emiAmount = emiAmount; }

    public Double getPrincipalComponent() { return principalComponent; }
    public void setPrincipalComponent(Double principalComponent) { this.principalComponent = principalComponent; }

    public Double getInterestComponent() { return interestComponent; }
    public void setInterestComponent(Double interestComponent) { this.interestComponent = interestComponent; }

    public Double getOutstandingAfterPayment() { return outstandingAfterPayment; }
    public void setOutstandingAfterPayment(Double outstandingAfterPayment) { this.outstandingAfterPayment = outstandingAfterPayment; }

    public boolean isPaid() { return paid; }
    public void setPaid(boolean paid) { this.paid = paid; }

    public Date getPaidDate() { return paidDate; }
    public void setPaidDate(Date paidDate) { this.paidDate = paidDate; }

    public Double getPenaltyApplied() { return penaltyApplied; }
    public void setPenaltyApplied(Double penaltyApplied) { this.penaltyApplied = penaltyApplied; }
}
