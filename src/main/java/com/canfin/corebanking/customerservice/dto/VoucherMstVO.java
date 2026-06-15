package com.canfin.corebanking.customerservice.dto;

import java.util.Date;

public class VoucherMstVO {

    private Integer tenantId = 0;
    private Integer branchCode = 0;
    private Date entryDate;
    private String batchCode = "";
    private Integer setNo = 0;

    private String payeeName = "";
    private Integer mainScrollNo = 0;
    private Integer refSetNo = 0;
    private Date postDate;
    private Date fundEffDate;
    private String activityCode = "";
    private String cashFlowType = "";
    private Date valueDate;
    private String bookType = "";
    private String drCr;
    private String vcrAcctId;
    private String mainAcctId = "";
    private Integer mainModule = 0;
    private Integer vcrModule = 0;
    private String productCode = "";
    private String schemeCode = "";
    private String trnCurrCode;
    private Double fcyTrnAmt = 0.0d;
    private Double lcyConvRate = 0.0d;
    private Double lcyTrnAmt = 0.0d;
    private Integer instrBankCd = 0;
    private Integer instrBranchCd = 0;
    private Integer instrType = 0;
    private Integer instrNo = 0;
    private Date instrDate;
    private String narration = "";
    private String docType = "";
    private String docId = "";
    private String remitName = "";
    private String remitDocType = "";
    private String remitDocId = "";
    private String shTotFlag = "";
    private String shClrFlag = "";
    private String acTotFlag = "";
    private String acClrFlag = "";
    private String entryFlag = "";
    private String postFlag = "";
    private String authFlag = "";
    private String fundEffFlag = "";
    private String canceledFlag = "";
    private Double partClearAmt = 0.0d;
    private Integer memberCode = 0;
    private Integer currStage = 0;
    private String authStatus = "";
    private Long parentId = 0L;
    private String remarks = "";
    private Date fundSysDate;
    private String checker1 = "";
    private String checker2 = "";
    private String checker3 = "";
    private String checker4 = "";
    private String checker5 = "";
    private String tokenNo = "N";
    private Integer sysGenVcr = 0;
    private String chargeType = "";
    private String particulars = "";
    private String maker = "";
    private Date makerDate;
    private Date makerTime;
    private Date checkerDate;
    private Date checkerTime;
    private Integer noAuthOver = 0;
    private Integer noAuthPending = 0;
    private Integer updtChkId = 0;
    private Date postTime;
    private String costCenter = "";
    private String trancode = "";
    private String tranRefNum = "";
    private Date receiptEffectiveDate;
    private Double totalIntNormalRate = 0.0d;
    private Double tdsNormalRate = 0.0d;
    private String vcrProductCode = "";
    private String vcrSchemeCode = "";
    private Double partPaymentDuringMoratorium;
    private String oldAccountNo;
    private String newAccountNo;
    private Date dueDate;
    private String rmCode;
    private String glCode = "";
    private String parentGLCode = "";
    private String utrNumber = "";
    private Date utrDate;

    // Base fields
    private Long activityId = 0L;
    private String description = "";
    private String createdBy = "";
    private Date createdTime;
    private String lastModifiedBy = "";
    private Date lastModifiedDate;
    private Date lastModifiedTime;

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(Integer branchCode) {
        this.branchCode = branchCode;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public Integer getSetNo() {
        return setNo;
    }

    public void setSetNo(Integer setNo) {
        this.setNo = setNo;
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

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }
}
