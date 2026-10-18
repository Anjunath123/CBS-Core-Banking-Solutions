package com.canfin.corebanking.customerservice.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class DepositAccount implements Serializable {

    @EmbeddedId
    private DepositAccountKey depositAccountKey;

    @Column(nullable = true,length = 100)
    private String accountName="";

    @Column(nullable = true,length = 50)
    private String product="";

    @Column(nullable = true,length = 50)
    private String scheme="";

    @Column(nullable = true, length = 2)
    private Integer lockPeriod;

    //@Column(nullable = true, columnDefinition = "nvarchar(20)")
    @Column(nullable = true, length=20)
    private String modeOfOprn = "";

    @Column(nullable = true, length = 4)
    private Integer applicantType = 0;

    @Column(nullable = true)
    private String modeOfOprnDesc = "";

    @Column(nullable =true)
    private Date openDate;

    @Column(nullable =true)
    private Date certDate;

    @Column(nullable =true)
    private Date asOffdate;

    @Column(nullable =true)
    private Date lockEndDate;

    @Column(nullable =true)
    private Date nextPayOutDt;

    @Column(nullable =true)
    private Date effDate;

    //@Column(nullable = true, columnDefinition = "nvarchar(4)")
    @Column(nullable = true, length=4)
    private String currencyCode = "";

    @Column(nullable = true)
    private Double depositAmount = 0D;

    @Column(nullable = true,length=2 )
    private Integer depositMonths;

    @Column(nullable = true,length=2 )
    private Integer noInst;

    @Column(nullable = true, length=2)
    private Integer depositDays;

    //@Column(nullable = true, columnDefinition = "nvarchar(10)")
    @Column(nullable = true, length=10)
    private String depFreq = "";

    @Column(nullable = true,length=4)
    private Integer noOfUnits;

    @Column(nullable = true)
    private Double standardRate = 0D;

    @Column(nullable = true)
    private Double intRate = 0D;

    @Column(nullable = true)
    private Double offsetRate = 0D;

    @Column(nullable = true)
    private Double applRate = 0D;

    @Column(nullable =true)
    private Date maturityDate;

    @Column(nullable = true)
    private Double inClBalFcy = 0D;

    @Column(nullable = true)
    private Double unClBalFcy = 0D;

    @Column(nullable = true)
    private Double mainBalFcy = 0D;

    @Column(nullable = true)
    private Double intPrvdAmtFcy = 0D;

    @Column(nullable = true)
    private Double intPaidAmtFcy = 0D;

    @Column(nullable = true)
    private Double tdsAmtFcy = 0D;

    @Column(nullable = true)
    private Double mainBalLcy = 0D;

    @Column(nullable = true)
    private Double intPrvdAmtLcy = 0D;

    @Column(nullable = true)
    private Double intPaidAmtLcy = 0D;

    @Column(nullable = true)
    private Double tdsAmtLcy = 0D;

    @Column(nullable = true)
    private Double matAmount = 0D;

    @Column(nullable = true)
    private Double matInterest = 0D;

    @Column(nullable = true)
    private Double instOrPrincAmt = 0D;


    //@Column(nullable = true, columnDefinition = "nvarchar(1)")
    @Column(nullable = true, length=50)
    private String intPayFreq = "";

    @Column(nullable = true)
    private Double periodicInt = 0D;

    @Column(nullable = true)
    private Double brokenPeriodInt = 0D;

    //@Column(nullable = true, columnDefinition = "nvarchar(100)")
    @Column(nullable = true, length=100)
    private String spclInstr = "";


    //@Column(nullable = true, columnDefinition = "nvarchar(50)")
    @Column(nullable = true, length=50)
    private String agentCode = "";

    //@Column(nullable = true, columnDefinition = "nvarchar(50)")
    @Column(nullable = true, length=50)
    private String debitAccID ="";


    //@Column(nullable = true, columnDefinition = "nvarchar(50)")
    @Column(nullable = true, length=250)
    private String remarks = "";

    //@Column(nullable = true, columnDefinition = "nvarchar(50)")
    @Column(nullable = true, length=50)
    private String depSource = "";


    //@Column(nullable = true, columnDefinition = "nvarchar(250)")
    @Column(nullable = true, length=250)
    private String noticeAddress = "";


    @Column(nullable = true)
    private Double accPrvdAmtFcy = 0D;

    @Column(nullable = true)
    private Double lienAmount = 0D;

    @Column(nullable = true)
    private Double accPrvdAmtLcy = 0D;

    //@Column(nullable = true, columnDefinition = "nvarchar(10)")
    @Column(nullable = true, length=10)
    private String receiptNo = "";

    @Column(nullable = true, length=2)
    private Integer delayMonths;

    @Column(nullable = true)
    private Double delayPenalty = 0D;

    @Column(nullable = true, length=4)
    private Integer depositStatus ;

    @Column(nullable = true,length=1)
    private Integer receiptStatus ;

    @Column(nullable =true)
    private Date closureDate;

    //@Column(nullable = true, columnDefinition = "nvarchar(60)")
    @Column(nullable = true, length=60)
    private String closureReason = "";

    @Column(nullable = true)
    private Double prinBalance = 0D;

    @Column(nullable = true)
    private Double interestAmount = 0D;

    @Column(nullable = true)
    private Double taxAmount = 0D;

    @Column(nullable = true)
    private Double intPaid = 0D;

    @Column(nullable = true)
    private Double totalPayment = 0D;

    @Transient
    private Integer moduleCode = 0;

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = true,length = 1)
    private Integer isActive;

    @Column(nullable = true,length = 1)
    private String authStatus="";


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

    public DepositAccountKey getDepositAccountKey() {
        return depositAccountKey;
    }

    public void setDepositAccountKey(DepositAccountKey depositAccountKey) {
        this.depositAccountKey = depositAccountKey;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }
}
