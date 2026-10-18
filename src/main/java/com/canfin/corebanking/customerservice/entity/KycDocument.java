package com.canfin.corebanking.customerservice.entity;

import javax.persistence.*;

@Entity
public class KycDocument {

    @EmbeddedId
    private KycDocumentKey kycDocumentKey;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "tenantId", referencedColumnName = "tenantId", insertable = false, updatable = false),
            @JoinColumn(name = "branchCode", referencedColumnName = "branchCode", insertable = false, updatable = false),
            @JoinColumn(name = "customerId", referencedColumnName = "customerId", insertable = false, updatable = false)
    })
    private Customer customer;

    @Column(nullable = true,length =50)
    private String docType="";

    @Column(nullable = true,length =20)
    private String docIdNum="";

    @Column(nullable = true)
    private String issuedDate="";

    @Column(nullable = true)
    private String expiryDate="";

    @Column(nullable = true,length=250)
    private String remarks = "";

    @Column(nullable = true,length=100)
    private String nameAsInDocument = "";

    @Column(nullable = true,length=50)
    private String kycStatus="";

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

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

    public KycDocumentKey getKycDocumentKey() {
        return kycDocumentKey;
    }

    public void setKycDocumentKey(KycDocumentKey kycDocumentKey) {
        this.kycDocumentKey = kycDocumentKey;
    }

    public String getKycStatus() {
        return kycStatus;
    }

    public void setKycStatus(String kycStatus) {
        this.kycStatus = kycStatus;
    }
}
