package com.canfin.corebanking.customerservice.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="D009040")
public class VoucherMst extends Base {
    private static final long serialVersionUID = 1701926931204630606L;

    @EmbeddedId
    private D009040Key d009040Key;

    @Column(nullable = true,length=200)
    private String payeeName = "";

    @Column(nullable = false, length = 8)
    private Integer mainScrollNo = 0;

    @Column(nullable = false, length = 8)
    private Integer refSetNo = 0;

    @Column(nullable = true)
    private Date postDate;


    @Column(nullable = true)
    private Date fundEffDate;


    @Column(nullable = true,length=8)
    private String activityCode = "";


    @Column(nullable = true,length=8)
    private String cashFlowType = "";


    @Column(nullable = true)
    private Date valueDate;


    @Column(nullable = true,length=2)
    private String bookType = "";


    @Column(nullable = true,length=1)
    private String drCr;


    @Column(nullable = true,length=32)
    private String vcrAcctId; // max. 32 Account Number


    @Column(nullable = true,length=32)
    private String mainAcctId = "";

    @Column(nullable = true, length = 4)
    private Integer mainModule = 0; //

    @Column(nullable = true, length = 4)
    private Integer vcrModule = 0; // ModuleCode


    @Column(nullable = true,length=8)
    private String productCode = "";


    @Column(nullable = true,length=8)
    private String schemeCode = "";


    @Column(nullable = true,length=3)
    private String trnCurrCode;

    @Column(nullable = true)
    private Double fcyTrnAmt = 0.0d;

    @Column(nullable = true)
    private Double lcyConvRate = 0.0d;

    @Column(nullable = true)
    private Double lcyTrnAmt = 0.0d;

    @Column(nullable = true, length = 8)
    private Integer instrBankCd = 0; // integer

    @Column(nullable = true, length = 8)
    private Integer instrBranchCd = 0; // integer

    @Column(nullable = true, length = 4)
    private Integer instrType = 0; // integer

    @Column(nullable = true, length = 12)
    private Integer instrNo = 0; // max. 12


    @Column(nullable = true)
    private Date instrDate;


    @Column(nullable = true,length=150)
    private String narration = "";

    /*@Column(nullable = true, columnDefinition = "nvarchar(20)")*/
    @Column(nullable = true,length=20)
    private String docType = "";

    /*@Column(nullable = true, columnDefinition = "nvarchar(60)")*/
    @Column(nullable = true,length=60)
    private String docId = "";

    /*@Column(nullable = true, columnDefinition = "nvarchar(150)")*/
    @Column(nullable = true,length=150)
    private String remitName = "";

    /*@Column(nullable = true, columnDefinition = "nvarchar(20)")*/
    @Column(nullable = true,length=20)
    private String remitDocType = "";

    /*@Column(nullable = true, columnDefinition = "nvarchar(60)")*/
    @Column(nullable = true,length=60)
    private String remitDocId = "";

    /*@Column(nullable = true, columnDefinition = "nvarchar(1)")*/
    @Column(nullable = true,length=1)
    private String shTotFlag = "";

    /*@Column(nullable = true, columnDefinition = "nvarchar(1)")*/
    @Column(nullable = true,length=1)
    private String shClrFlag = "";

    /*@Column(nullable = true, columnDefinition = "nvarchar(1)")*/
    @Column(nullable = true,length=1)
    private String acTotFlag = "";

    /*@Column(nullable = true, columnDefinition = "nvarchar(1)")*/
    @Column(nullable = true,length=1)
    private String acClrFlag = "";

    /*@Column(nullable = true, columnDefinition = "nvarchar(1)")*/
    @Column(nullable = true,length=1)
    private String entryFlag = "";

    /*@Column(nullable = true, columnDefinition = "nvarchar(1)")*/
    @Column(nullable = true,length=1)
    private String postFlag = "";

    /*@Column(nullable = true, columnDefinition = "nvarchar(1)")*/
    @Column(nullable = true,length=1)
    private String authFlag = "";

    /*@Column(nullable = true, columnDefinition = "nvarchar(1)")*/
    @Column(nullable = true,length=1)
    private String fundEffFlag = "";

    /*@Column(nullable = true, columnDefinition = "nvarchar(1)")*/
    @Column(nullable = true,length=1)
    private String canceledFlag = "";

    @Column(nullable = true)
    private Double partClearAmt = 0.0d;

    @Column(nullable = true, length = 10)
    private Integer memberCode = 0;

    /*@Column(nullable = true, columnDefinition = "nvarchar(20)")*/
    @Column(nullable = true,length=20)
    private Integer currStage = 0;

    /*@Column(nullable = true, columnDefinition = "nvarchar(1)")*/
    @Column(nullable = true,length=1)
    private String authStatus = "";



    @Column(nullable = true)
    private Long parentId = 0l;


    @Column(nullable = true,length=150)
    private String remarks = "";

    /*@Column(nullable = true, columnDefinition = "DATETIME")*/
    @Column(nullable = true)
    // @Temporal(TemporalType.TIMESTAMP)
    private Date fundSysDate = null;

    /*@Column(columnDefinition = "nvarchar(10) default ' '", nullable = true)*/
    @Column(length=10,nullable = true)
    private String checker1 = "";

    /*@Column(columnDefinition = "nvarchar(10) default ' '", nullable = true)*/
    @Column(length=10,nullable = true)
    private String checker2 = "";

    /*@Column(columnDefinition = "nvarchar(10) default ' '", nullable = true)*/
    @Column(length=10,nullable = true)
    private String checker3 = "";

    /*@Column(columnDefinition = "nvarchar(10) default ' '", nullable = true)*/
    @Column(length=10,nullable = true)
    private String checker4 = "";

    /*@Column(columnDefinition = "nvarchar(10) default ' '", nullable = true)*/
    @Column(length=10,nullable = true)
    private String checker5 = "";

    @Column(nullable = true,length=50)
    private String tokenNo = "N";

    @Column(nullable = false, length = 1)
    private Integer sysGenVcr = 0;

    /*@Column(nullable = true, columnDefinition = "varchar(60)")*/
    @Column(nullable = true,length=60)
    private String chargeType = "";

    //From DDF
    /*@Column(nullable = true, columnDefinition = "varchar(70)")*/
    @Column(nullable = true,length=70)
    private String particulars = "";

    /*@Column(columnDefinition = "nvarchar(10) default ' '", nullable = true)*/
    @Column(length=10,nullable = true)
    private String maker = "";


    // @Temporal(TemporalType.DATE)
    /*@Column(nullable = true, columnDefinition = "DATETIME")*/
    @Column(nullable = true)
    private Date makerDate;

    /*@Column(columnDefinition = "DATETIME", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)*/
    @Column(nullable = true)
    private Date makerTime;

    // @Temporal(TemporalType.DATE)
    /*@Column(nullable = true, columnDefinition = "DATETIME")*/
    @Column(nullable = true)
    private Date checkerDate;

    /*@Column(columnDefinition = "DATETIME", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)*/
    @Column(nullable = true)
    private Date checkerTime;

    @Column(nullable = false, length = 1)
    private Integer noAuthOver = 0;

    @Column(nullable = false, length = 1)
    private Integer noAuthPending = 0;

    @Column(nullable = false,length=2)
    private Integer updtChkId = 0;

    /*@Column(columnDefinition = "DATETIME", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)*/
    @Column(nullable = true)
    private Date postTime;

    @Transient
    private Double disbursementCharges = 0.00d;

    @Transient
    private Double netPenalOs = 0.00d;

    @Transient
    private String savingAccntNumber = "";

    @Transient
    private Double netIntAmounttOs = 0.00d;

    @Transient
    private Double insuranceAmount = 0.00d;

    @Transient
    private Double disbursementCessAmount = 0.00d;

    @Transient
    private Double disbursementServiceTax = 0.00d;

    @Transient
    private Double processingCharges = 0.00d;

    @Transient
    private Double processingChargesCessAmount = 0.00d;

    @Transient
    private Double processingChargesServiceTax = 0.00d;

    @Transient
    private Double penalIntRate = 0.00d;

    @Transient
    private Double lockingInterest = 0.00d;

    @Transient
    private Double agentCollectionAmt = 0.00d;

    @Transient
    private Date accountEndDate;

    @Transient
    private Double preclosureIntRate = 0.00d;

    @Transient
    private Double agentTotalCollection = 0d;

    @Transient
    private Double rateOfInterest = 0.00d;

    @Transient
    private String tdreceiptSrNo = "";

    @Transient
    private String exmType="";

    @Transient
    private String GenExpYN="";

    @Transient
    private String coreActivity="";

    @Transient
    private String rateType="";

    @Transient
    private Double exRate = 0.00d;

    @Transient
    private Double oExRate = 0.00d;

    @Transient
    private String errorMsg="";

    @Column(length = 20, nullable = true)
    private String costCenter="";

    @Column(length=5 , nullable=true)
    private String trancode="";

    @Transient
    private String screenFlag="";
    @Column(length=40,nullable = true)
    private String tranRefNum = "";

    @Column(nullable = true)
    private Date receiptEffectiveDate;

    @Column(nullable = true)
    private Double totalIntNormalRate=0.0d;

    @Column(nullable = true)
    private Double tdsNormalRate=0.0d;

    @Column(nullable = true,length=8)
    private String vcrProductCode = "";


    @Column(nullable = true,length=8)
    private String vcrSchemeCode = "";

    @Column(nullable = true)
    private Double partPaymentDuringMoratorium;

    @Column(nullable = true, length=32)
    private String oldAccountNo;

    @Column(nullable = true, length =32)
    private String newAccountNo;

    @Column(nullable = true)
    private Date dueDate;

    @Column(nullable = true, length =32)
    private String rmCode;

    @Column(nullable = true,  length=32)
    private String glCode = "";

    @Column(nullable = true,  length =32)
    private String parentGLCode = "";

    @Column(nullable = true, length = 50)
    private String utrNumber = "";

    @Column(nullable = true)
    private Date utrDate ;

    public D009040Key getD009040Key() {
        return d009040Key;
    }

    public void setD009040Key(D009040Key d009040Key) {
        this.d009040Key = d009040Key;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public Integer getMainScrollNo() {
        return mainScrollNo;
    }

    public void setMainScrollNo(Integer mainScrollNo) {
        this.mainScrollNo = mainScrollNo;
    }

    public Integer getRefSetNo() {
        return refSetNo;
    }

    public void setRefSetNo(Integer refSetNo) {
        this.refSetNo = refSetNo;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public Date getFundEffDate() {
        return fundEffDate;
    }

    public void setFundEffDate(Date fundEffDate) {
        this.fundEffDate = fundEffDate;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public String getCashFlowType() {
        return cashFlowType;
    }

    public void setCashFlowType(String cashFlowType) {
        this.cashFlowType = cashFlowType;
    }

    public Date getValueDate() {
        return valueDate;
    }

    public void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public String getDrCr() {
        return drCr;
    }

    public void setDrCr(String drCr) {
        this.drCr = drCr;
    }

    public String getVcrAcctId() {
        return vcrAcctId;
    }

    public void setVcrAcctId(String vcrAcctId) {
        this.vcrAcctId = vcrAcctId;
    }

    public String getMainAcctId() {
        return mainAcctId;
    }

    public void setMainAcctId(String mainAcctId) {
        this.mainAcctId = mainAcctId;
    }

    public Integer getMainModule() {
        return mainModule;
    }

    public void setMainModule(Integer mainModule) {
        this.mainModule = mainModule;
    }

    public Integer getVcrModule() {
        return vcrModule;
    }

    public void setVcrModule(Integer vcrModule) {
        this.vcrModule = vcrModule;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getTrnCurrCode() {
        return trnCurrCode;
    }

    public void setTrnCurrCode(String trnCurrCode) {
        this.trnCurrCode = trnCurrCode;
    }

    public Double getFcyTrnAmt() {
        return fcyTrnAmt;
    }

    public void setFcyTrnAmt(Double fcyTrnAmt) {
        this.fcyTrnAmt = fcyTrnAmt;
    }

    public Double getLcyConvRate() {
        return lcyConvRate;
    }

    public void setLcyConvRate(Double lcyConvRate) {
        this.lcyConvRate = lcyConvRate;
    }

    public Double getLcyTrnAmt() {
        return lcyTrnAmt;
    }

    public void setLcyTrnAmt(Double lcyTrnAmt) {
        this.lcyTrnAmt = lcyTrnAmt;
    }

    public Integer getInstrBankCd() {
        return instrBankCd;
    }

    public void setInstrBankCd(Integer instrBankCd) {
        this.instrBankCd = instrBankCd;
    }

    public Integer getInstrBranchCd() {
        return instrBranchCd;
    }

    public void setInstrBranchCd(Integer instrBranchCd) {
        this.instrBranchCd = instrBranchCd;
    }

    public Integer getInstrType() {
        return instrType;
    }

    public void setInstrType(Integer instrType) {
        this.instrType = instrType;
    }

    public Integer getInstrNo() {
        return instrNo;
    }

    public void setInstrNo(Integer instrNo) {
        this.instrNo = instrNo;
    }

    public Date getInstrDate() {
        return instrDate;
    }

    public void setInstrDate(Date instrDate) {
        this.instrDate = instrDate;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getRemitName() {
        return remitName;
    }

    public void setRemitName(String remitName) {
        this.remitName = remitName;
    }

    public String getRemitDocType() {
        return remitDocType;
    }

    public void setRemitDocType(String remitDocType) {
        this.remitDocType = remitDocType;
    }

    public String getRemitDocId() {
        return remitDocId;
    }

    public void setRemitDocId(String remitDocId) {
        this.remitDocId = remitDocId;
    }

    public String getShTotFlag() {
        return shTotFlag;
    }

    public void setShTotFlag(String shTotFlag) {
        this.shTotFlag = shTotFlag;
    }

    public String getShClrFlag() {
        return shClrFlag;
    }

    public void setShClrFlag(String shClrFlag) {
        this.shClrFlag = shClrFlag;
    }

    public String getAcTotFlag() {
        return acTotFlag;
    }

    public void setAcTotFlag(String acTotFlag) {
        this.acTotFlag = acTotFlag;
    }

    public String getAcClrFlag() {
        return acClrFlag;
    }

    public void setAcClrFlag(String acClrFlag) {
        this.acClrFlag = acClrFlag;
    }

    public String getEntryFlag() {
        return entryFlag;
    }

    public void setEntryFlag(String entryFlag) {
        this.entryFlag = entryFlag;
    }

    public String getPostFlag() {
        return postFlag;
    }

    public void setPostFlag(String postFlag) {
        this.postFlag = postFlag;
    }

    public String getAuthFlag() {
        return authFlag;
    }

    public void setAuthFlag(String authFlag) {
        this.authFlag = authFlag;
    }

    public String getFundEffFlag() {
        return fundEffFlag;
    }

    public void setFundEffFlag(String fundEffFlag) {
        this.fundEffFlag = fundEffFlag;
    }

    public String getCanceledFlag() {
        return canceledFlag;
    }

    public void setCanceledFlag(String canceledFlag) {
        this.canceledFlag = canceledFlag;
    }

    public Double getPartClearAmt() {
        return partClearAmt;
    }

    public void setPartClearAmt(Double partClearAmt) {
        this.partClearAmt = partClearAmt;
    }

    public Integer getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(Integer memberCode) {
        this.memberCode = memberCode;
    }

    public Integer getCurrStage() {
        return currStage;
    }

    public void setCurrStage(Integer currStage) {
        this.currStage = currStage;
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getFundSysDate() {
        return fundSysDate;
    }

    public void setFundSysDate(Date fundSysDate) {
        this.fundSysDate = fundSysDate;
    }

    public String getChecker1() {
        return checker1;
    }

    public void setChecker1(String checker1) {
        this.checker1 = checker1;
    }

    public String getChecker2() {
        return checker2;
    }

    public void setChecker2(String checker2) {
        this.checker2 = checker2;
    }

    public String getChecker3() {
        return checker3;
    }

    public void setChecker3(String checker3) {
        this.checker3 = checker3;
    }

    public String getChecker4() {
        return checker4;
    }

    public void setChecker4(String checker4) {
        this.checker4 = checker4;
    }

    public String getChecker5() {
        return checker5;
    }

    public void setChecker5(String checker5) {
        this.checker5 = checker5;
    }

    public String getTokenNo() {
        return tokenNo;
    }

    public void setTokenNo(String tokenNo) {
        this.tokenNo = tokenNo;
    }

    public Integer getSysGenVcr() {
        return sysGenVcr;
    }

    public void setSysGenVcr(Integer sysGenVcr) {
        this.sysGenVcr = sysGenVcr;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public Date getMakerDate() {
        return makerDate;
    }

    public void setMakerDate(Date makerDate) {
        this.makerDate = makerDate;
    }

    public Date getMakerTime() {
        return makerTime;
    }

    public void setMakerTime(Date makerTime) {
        this.makerTime = makerTime;
    }

    public Date getCheckerDate() {
        return checkerDate;
    }

    public void setCheckerDate(Date checkerDate) {
        this.checkerDate = checkerDate;
    }

    public Date getCheckerTime() {
        return checkerTime;
    }

    public void setCheckerTime(Date checkerTime) {
        this.checkerTime = checkerTime;
    }

    public Integer getNoAuthOver() {
        return noAuthOver;
    }

    public void setNoAuthOver(Integer noAuthOver) {
        this.noAuthOver = noAuthOver;
    }

    public Integer getNoAuthPending() {
        return noAuthPending;
    }

    public void setNoAuthPending(Integer noAuthPending) {
        this.noAuthPending = noAuthPending;
    }

    public Integer getUpdtChkId() {
        return updtChkId;
    }

    public void setUpdtChkId(Integer updtChkId) {
        this.updtChkId = updtChkId;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public Double getDisbursementCharges() {
        return disbursementCharges;
    }

    public void setDisbursementCharges(Double disbursementCharges) {
        this.disbursementCharges = disbursementCharges;
    }

    public Double getNetPenalOs() {
        return netPenalOs;
    }

    public void setNetPenalOs(Double netPenalOs) {
        this.netPenalOs = netPenalOs;
    }

    public String getSavingAccntNumber() {
        return savingAccntNumber;
    }

    public void setSavingAccntNumber(String savingAccntNumber) {
        this.savingAccntNumber = savingAccntNumber;
    }

    public Double getNetIntAmounttOs() {
        return netIntAmounttOs;
    }

    public void setNetIntAmounttOs(Double netIntAmounttOs) {
        this.netIntAmounttOs = netIntAmounttOs;
    }

    public Double getInsuranceAmount() {
        return insuranceAmount;
    }

    public void setInsuranceAmount(Double insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }

    public Double getDisbursementCessAmount() {
        return disbursementCessAmount;
    }

    public void setDisbursementCessAmount(Double disbursementCessAmount) {
        this.disbursementCessAmount = disbursementCessAmount;
    }

    public Double getDisbursementServiceTax() {
        return disbursementServiceTax;
    }

    public void setDisbursementServiceTax(Double disbursementServiceTax) {
        this.disbursementServiceTax = disbursementServiceTax;
    }

    public Double getProcessingCharges() {
        return processingCharges;
    }

    public void setProcessingCharges(Double processingCharges) {
        this.processingCharges = processingCharges;
    }

    public Double getProcessingChargesCessAmount() {
        return processingChargesCessAmount;
    }

    public void setProcessingChargesCessAmount(Double processingChargesCessAmount) {
        this.processingChargesCessAmount = processingChargesCessAmount;
    }

    public Double getProcessingChargesServiceTax() {
        return processingChargesServiceTax;
    }

    public void setProcessingChargesServiceTax(Double processingChargesServiceTax) {
        this.processingChargesServiceTax = processingChargesServiceTax;
    }

    public Double getPenalIntRate() {
        return penalIntRate;
    }

    public void setPenalIntRate(Double penalIntRate) {
        this.penalIntRate = penalIntRate;
    }

    public Double getLockingInterest() {
        return lockingInterest;
    }

    public void setLockingInterest(Double lockingInterest) {
        this.lockingInterest = lockingInterest;
    }

    public Double getAgentCollectionAmt() {
        return agentCollectionAmt;
    }

    public void setAgentCollectionAmt(Double agentCollectionAmt) {
        this.agentCollectionAmt = agentCollectionAmt;
    }

    public Date getAccountEndDate() {
        return accountEndDate;
    }

    public void setAccountEndDate(Date accountEndDate) {
        this.accountEndDate = accountEndDate;
    }

    public Double getPreclosureIntRate() {
        return preclosureIntRate;
    }

    public void setPreclosureIntRate(Double preclosureIntRate) {
        this.preclosureIntRate = preclosureIntRate;
    }

    public Double getAgentTotalCollection() {
        return agentTotalCollection;
    }

    public void setAgentTotalCollection(Double agentTotalCollection) {
        this.agentTotalCollection = agentTotalCollection;
    }

    public Double getRateOfInterest() {
        return rateOfInterest;
    }

    public void setRateOfInterest(Double rateOfInterest) {
        this.rateOfInterest = rateOfInterest;
    }

    public String getTdreceiptSrNo() {
        return tdreceiptSrNo;
    }

    public void setTdreceiptSrNo(String tdreceiptSrNo) {
        this.tdreceiptSrNo = tdreceiptSrNo;
    }

    public String getExmType() {
        return exmType;
    }

    public void setExmType(String exmType) {
        this.exmType = exmType;
    }

    public String getGenExpYN() {
        return GenExpYN;
    }

    public void setGenExpYN(String genExpYN) {
        GenExpYN = genExpYN;
    }

    public String getCoreActivity() {
        return coreActivity;
    }

    public void setCoreActivity(String coreActivity) {
        this.coreActivity = coreActivity;
    }

    public String getRateType() {
        return rateType;
    }

    public void setRateType(String rateType) {
        this.rateType = rateType;
    }

    public Double getExRate() {
        return exRate;
    }

    public void setExRate(Double exRate) {
        this.exRate = exRate;
    }

    public Double getoExRate() {
        return oExRate;
    }

    public void setoExRate(Double oExRate) {
        this.oExRate = oExRate;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public String getTrancode() {
        return trancode;
    }

    public void setTrancode(String trancode) {
        this.trancode = trancode;
    }

    public String getScreenFlag() {
        return screenFlag;
    }

    public void setScreenFlag(String screenFlag) {
        this.screenFlag = screenFlag;
    }

    public String getTranRefNum() {
        return tranRefNum;
    }

    public void setTranRefNum(String tranRefNum) {
        this.tranRefNum = tranRefNum;
    }

    public Date getReceiptEffectiveDate() {
        return receiptEffectiveDate;
    }

    public void setReceiptEffectiveDate(Date receiptEffectiveDate) {
        this.receiptEffectiveDate = receiptEffectiveDate;
    }

    public Double getTotalIntNormalRate() {
        return totalIntNormalRate;
    }

    public void setTotalIntNormalRate(Double totalIntNormalRate) {
        this.totalIntNormalRate = totalIntNormalRate;
    }

    public Double getTdsNormalRate() {
        return tdsNormalRate;
    }

    public void setTdsNormalRate(Double tdsNormalRate) {
        this.tdsNormalRate = tdsNormalRate;
    }

    public String getVcrProductCode() {
        return vcrProductCode;
    }

    public void setVcrProductCode(String vcrProductCode) {
        this.vcrProductCode = vcrProductCode;
    }

    public String getVcrSchemeCode() {
        return vcrSchemeCode;
    }

    public void setVcrSchemeCode(String vcrSchemeCode) {
        this.vcrSchemeCode = vcrSchemeCode;
    }

    public Double getPartPaymentDuringMoratorium() {
        return partPaymentDuringMoratorium;
    }

    public void setPartPaymentDuringMoratorium(Double partPaymentDuringMoratorium) {
        this.partPaymentDuringMoratorium = partPaymentDuringMoratorium;
    }

    public String getOldAccountNo() {
        return oldAccountNo;
    }

    public void setOldAccountNo(String oldAccountNo) {
        this.oldAccountNo = oldAccountNo;
    }

    public String getNewAccountNo() {
        return newAccountNo;
    }

    public void setNewAccountNo(String newAccountNo) {
        this.newAccountNo = newAccountNo;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getRmCode() {
        return rmCode;
    }

    public void setRmCode(String rmCode) {
        this.rmCode = rmCode;
    }

    public String getGlCode() {
        return glCode;
    }

    public void setGlCode(String glCode) {
        this.glCode = glCode;
    }

    public String getParentGLCode() {
        return parentGLCode;
    }

    public void setParentGLCode(String parentGLCode) {
        this.parentGLCode = parentGLCode;
    }

    public String getUtrNumber() {
        return utrNumber;
    }

    public void setUtrNumber(String utrNumber) {
        this.utrNumber = utrNumber;
    }

    public Date getUtrDate() {
        return utrDate;
    }

    public void setUtrDate(Date utrDate) {
        this.utrDate = utrDate;
    }
}
