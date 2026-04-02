package com.canfin.corebanking.customerservice.dto;

import javax.persistence.Transient;
import javax.validation.constraints.*;
import java.util.Date;

public class DepositAccountDto {

    @NotNull(message = "Branch code is required")
    private Integer branchCode;

    private String accountNumber="";

    @NotBlank(message = "Account name is required")
    private String accountName="";

    @NotBlank(message = "Product is required")
    private String product="";

    @NotBlank(message = "Scheme is required")
    private String scheme="";

    private Integer lockPeriod;


    @NotBlank(message = "Mode of operation is required")
    private String modeOfOprn = "";

    private Integer applicantType = 0;


    private String modeOfOprnDesc = "";


    @NotNull(message = "Open date is required")
    private Date openDate;


    private Date certDate;


    private Date asOffdate;


    private Date lockEndDate;


    private Date nextPayOutDt;


    private Date effDate;


    @NotBlank(message = "Currency code is required")
    private String currencyCode = "";


    @NotNull(message = "Deposit amount is required")
    @DecimalMin(value = "0.01", message = "Deposit amount must be greater than 0")
    private Double depositAmount = 0D;


    @NotNull(message = "Deposit months is required")
    @Min(value = 0, message = "Deposit months cannot be negative")
    private Integer depositMonths;


    private Integer noInst;


    @Min(value = 0, message = "Deposit days cannot be negative")
    private Integer depositDays;


    private String depFreq = "";


    private Integer noOfUnits;


    private Double standardRate = 0D;


    @NotNull(message = "Interest rate is required")
    @DecimalMin(value = "0.0", message = "Interest rate cannot be negative")
    private Double intRate = 0D;


    private Double offsetRate = 0D;


    private Double applRate = 0D;


    private Date maturityDate;


    private Double inClBalFcy = 0D;


    private Double unClBalFcy = 0D;


    private Double mainBalFcy = 0D;


    private Double intPrvdAmtFcy = 0D;

    
    private Double intPaidAmtFcy = 0D;

    
    private Double tdsAmtFcy = 0D;

    
    private Double mainBalLcy = 0D;

    
    private Double intPrvdAmtLcy = 0D;

    
    private Double intPaidAmtLcy = 0D;

    
    private Double tdsAmtLcy = 0D;

    
    private Double matAmount = 0D;

    
    private Double matInterest = 0D;

    
    private Double instOrPrincAmt = 0D;


    @NotBlank(message = "Interest payment frequency is required")
    private String intPayFreq = "";

    
    private Double periodicInt = 0D;

    
    private Double brokenPeriodInt = 0D;

   
    private String spclInstr = "";


   
    private String agentCode = "";

    
    @NotBlank(message = "Debit account ID is required")
    private String debitAccID ="";


    
    private String remarks = "";

    @NotBlank(message = "Deposit source is required")
    private String depSource = "";


    
    private String noticeAddress = "";


    
    private Double accPrvdAmtFcy = 0D;

    
    private Double lienAmount = 0D;

    
    private Double accPrvdAmtLcy = 0D;

    
    private String receiptNo = "";

    
    private Integer delayMonths;

    
    private Double delayPenalty = 0D;

    
    private Integer depositStatus ;

    
    private Integer receiptStatus ;

    
    private Date closureDate;

   
    private String closureReason = "";

    
    private Double prinBalance = 0D;

    
    private Double interestAmount = 0D;

    
    private Double taxAmount = 0D;

    
    private Double intPaid = 0D;

    
    private Double totalPayment = 0D;

    @Transient
    private Integer moduleCode = 0;

    @NotNull(message = "Customer ID is required")
    private Long customerId;


    public Integer getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(Integer branchCode) {
        this.branchCode = branchCode;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public Integer getLockPeriod() {
        return lockPeriod;
    }

    public void setLockPeriod(Integer lockPeriod) {
        this.lockPeriod = lockPeriod;
    }

    public String getModeOfOprn() {
        return modeOfOprn;
    }

    public void setModeOfOprn(String modeOfOprn) {
        this.modeOfOprn = modeOfOprn;
    }

    public Integer getApplicantType() {
        return applicantType;
    }

    public void setApplicantType(Integer applicantType) {
        this.applicantType = applicantType;
    }

    public String getModeOfOprnDesc() {
        return modeOfOprnDesc;
    }

    public void setModeOfOprnDesc(String modeOfOprnDesc) {
        this.modeOfOprnDesc = modeOfOprnDesc;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public Date getCertDate() {
        return certDate;
    }

    public void setCertDate(Date certDate) {
        this.certDate = certDate;
    }

    public Date getAsOffdate() {
        return asOffdate;
    }

    public void setAsOffdate(Date asOffdate) {
        this.asOffdate = asOffdate;
    }

    public Date getLockEndDate() {
        return lockEndDate;
    }

    public void setLockEndDate(Date lockEndDate) {
        this.lockEndDate = lockEndDate;
    }

    public Date getNextPayOutDt() {
        return nextPayOutDt;
    }

    public void setNextPayOutDt(Date nextPayOutDt) {
        this.nextPayOutDt = nextPayOutDt;
    }

    public Date getEffDate() {
        return effDate;
    }

    public void setEffDate(Date effDate) {
        this.effDate = effDate;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(Double depositAmount) {
        this.depositAmount = depositAmount;
    }

    public Integer getDepositMonths() {
        return depositMonths;
    }

    public void setDepositMonths(Integer depositMonths) {
        this.depositMonths = depositMonths;
    }

    public Integer getNoInst() {
        return noInst;
    }

    public void setNoInst(Integer noInst) {
        this.noInst = noInst;
    }

    public Integer getDepositDays() {
        return depositDays;
    }

    public void setDepositDays(Integer depositDays) {
        this.depositDays = depositDays;
    }

    public String getDepFreq() {
        return depFreq;
    }

    public void setDepFreq(String depFreq) {
        this.depFreq = depFreq;
    }

    public Integer getNoOfUnits() {
        return noOfUnits;
    }

    public void setNoOfUnits(Integer noOfUnits) {
        this.noOfUnits = noOfUnits;
    }

    public Double getStandardRate() {
        return standardRate;
    }

    public void setStandardRate(Double standardRate) {
        this.standardRate = standardRate;
    }

    public Double getIntRate() {
        return intRate;
    }

    public void setIntRate(Double intRate) {
        this.intRate = intRate;
    }

    public Double getOffsetRate() {
        return offsetRate;
    }

    public void setOffsetRate(Double offsetRate) {
        this.offsetRate = offsetRate;
    }

    public Double getApplRate() {
        return applRate;
    }

    public void setApplRate(Double applRate) {
        this.applRate = applRate;
    }

    public Date getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(Date maturityDate) {
        this.maturityDate = maturityDate;
    }

    public Double getInClBalFcy() {
        return inClBalFcy;
    }

    public void setInClBalFcy(Double inClBalFcy) {
        this.inClBalFcy = inClBalFcy;
    }

    public Double getUnClBalFcy() {
        return unClBalFcy;
    }

    public void setUnClBalFcy(Double unClBalFcy) {
        this.unClBalFcy = unClBalFcy;
    }

    public Double getMainBalFcy() {
        return mainBalFcy;
    }

    public void setMainBalFcy(Double mainBalFcy) {
        this.mainBalFcy = mainBalFcy;
    }

    public Double getIntPrvdAmtFcy() {
        return intPrvdAmtFcy;
    }

    public void setIntPrvdAmtFcy(Double intPrvdAmtFcy) {
        this.intPrvdAmtFcy = intPrvdAmtFcy;
    }

    public Double getIntPaidAmtFcy() {
        return intPaidAmtFcy;
    }

    public void setIntPaidAmtFcy(Double intPaidAmtFcy) {
        this.intPaidAmtFcy = intPaidAmtFcy;
    }

    public Double getTdsAmtFcy() {
        return tdsAmtFcy;
    }

    public void setTdsAmtFcy(Double tdsAmtFcy) {
        this.tdsAmtFcy = tdsAmtFcy;
    }

    public Double getMainBalLcy() {
        return mainBalLcy;
    }

    public void setMainBalLcy(Double mainBalLcy) {
        this.mainBalLcy = mainBalLcy;
    }

    public Double getIntPrvdAmtLcy() {
        return intPrvdAmtLcy;
    }

    public void setIntPrvdAmtLcy(Double intPrvdAmtLcy) {
        this.intPrvdAmtLcy = intPrvdAmtLcy;
    }

    public Double getIntPaidAmtLcy() {
        return intPaidAmtLcy;
    }

    public void setIntPaidAmtLcy(Double intPaidAmtLcy) {
        this.intPaidAmtLcy = intPaidAmtLcy;
    }

    public Double getTdsAmtLcy() {
        return tdsAmtLcy;
    }

    public void setTdsAmtLcy(Double tdsAmtLcy) {
        this.tdsAmtLcy = tdsAmtLcy;
    }

    public Double getMatAmount() {
        return matAmount;
    }

    public void setMatAmount(Double matAmount) {
        this.matAmount = matAmount;
    }

    public Double getMatInterest() {
        return matInterest;
    }

    public void setMatInterest(Double matInterest) {
        this.matInterest = matInterest;
    }

    public Double getInstOrPrincAmt() {
        return instOrPrincAmt;
    }

    public void setInstOrPrincAmt(Double instOrPrincAmt) {
        this.instOrPrincAmt = instOrPrincAmt;
    }

    public String getIntPayFreq() {
        return intPayFreq;
    }

    public void setIntPayFreq(String intPayFreq) {
        this.intPayFreq = intPayFreq;
    }

    public Double getPeriodicInt() {
        return periodicInt;
    }

    public void setPeriodicInt(Double periodicInt) {
        this.periodicInt = periodicInt;
    }

    public Double getBrokenPeriodInt() {
        return brokenPeriodInt;
    }

    public void setBrokenPeriodInt(Double brokenPeriodInt) {
        this.brokenPeriodInt = brokenPeriodInt;
    }

    public String getSpclInstr() {
        return spclInstr;
    }

    public void setSpclInstr(String spclInstr) {
        this.spclInstr = spclInstr;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getDebitAccID() {
        return debitAccID;
    }

    public void setDebitAccID(String debitAccID) {
        this.debitAccID = debitAccID;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDepSource() {
        return depSource;
    }

    public void setDepSource(String depSource) {
        this.depSource = depSource;
    }

    public String getNoticeAddress() {
        return noticeAddress;
    }

    public void setNoticeAddress(String noticeAddress) {
        this.noticeAddress = noticeAddress;
    }

    public Double getAccPrvdAmtFcy() {
        return accPrvdAmtFcy;
    }

    public void setAccPrvdAmtFcy(Double accPrvdAmtFcy) {
        this.accPrvdAmtFcy = accPrvdAmtFcy;
    }

    public Double getLienAmount() {
        return lienAmount;
    }

    public void setLienAmount(Double lienAmount) {
        this.lienAmount = lienAmount;
    }

    public Double getAccPrvdAmtLcy() {
        return accPrvdAmtLcy;
    }

    public void setAccPrvdAmtLcy(Double accPrvdAmtLcy) {
        this.accPrvdAmtLcy = accPrvdAmtLcy;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public Integer getDelayMonths() {
        return delayMonths;
    }

    public void setDelayMonths(Integer delayMonths) {
        this.delayMonths = delayMonths;
    }

    public Double getDelayPenalty() {
        return delayPenalty;
    }

    public void setDelayPenalty(Double delayPenalty) {
        this.delayPenalty = delayPenalty;
    }

    public Integer getDepositStatus() {
        return depositStatus;
    }

    public void setDepositStatus(Integer depositStatus) {
        this.depositStatus = depositStatus;
    }

    public Integer getReceiptStatus() {
        return receiptStatus;
    }

    public void setReceiptStatus(Integer receiptStatus) {
        this.receiptStatus = receiptStatus;
    }

    public Date getClosureDate() {
        return closureDate;
    }

    public void setClosureDate(Date closureDate) {
        this.closureDate = closureDate;
    }

    public String getClosureReason() {
        return closureReason;
    }

    public void setClosureReason(String closureReason) {
        this.closureReason = closureReason;
    }

    public Double getPrinBalance() {
        return prinBalance;
    }

    public void setPrinBalance(Double prinBalance) {
        this.prinBalance = prinBalance;
    }

    public Double getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(Double interestAmount) {
        this.interestAmount = interestAmount;
    }

    public Double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Double getIntPaid() {
        return intPaid;
    }

    public void setIntPaid(Double intPaid) {
        this.intPaid = intPaid;
    }

    public Double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(Double totalPayment) {
        this.totalPayment = totalPayment;
    }

    public Integer getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(Integer moduleCode) {
        this.moduleCode = moduleCode;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
