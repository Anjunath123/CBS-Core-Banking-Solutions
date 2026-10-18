package com.canfin.corebanking.customerservice.dto;

public class KycDocumentDto {

    private Integer srNo;

    private String docType="";

    private String docIdNum="";

    private String issuedDate="";
    private String expiryDate="";

    private String remarks = "";

    private String nameAsInDocument = "";

    private String kycStatus="";

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDocIdNum() {
        return docIdNum;
    }

    public void setDocIdNum(String docIdNum) {
        this.docIdNum = docIdNum;
    }

    public String getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(String issuedDate) {
        this.issuedDate = issuedDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getNameAsInDocument() {
        return nameAsInDocument;
    }

    public void setNameAsInDocument(String nameAsInDocument) {
        this.nameAsInDocument = nameAsInDocument;
    }

    public String getKycStatus() {
        return kycStatus;
    }

    public void setKycStatus(String kycStatus) {
        this.kycStatus = kycStatus;
    }

    public Integer getSrNo() {
        return srNo;
    }

    public void setSrNo(Integer srNo) {
        this.srNo = srNo;
    }
}
