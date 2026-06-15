package com.canfin.corebanking.customerservice.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Embeddable
public class D009040Key implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(length=6, nullable = false)
    private Integer tenantId = 0;

    @Column(length=6,nullable = false)
    private Integer branchCode = 0;

    @Column(nullable =true)
    private Date entryDate;

    @Column(nullable = false,length=8)
    private String batchCode = "";

    @Column(nullable = false,length=8)
    private Integer setNo = 0;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        D009040Key that = (D009040Key) o;
        return Objects.equals(tenantId, that.tenantId) && Objects.equals(branchCode, that.branchCode) && Objects.equals(entryDate, that.entryDate) && Objects.equals(batchCode, that.batchCode) && Objects.equals(setNo, that.setNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenantId, branchCode, entryDate, batchCode, setNo);
    }

    @Override
    public String toString() {
        return "D009040Key{" +
                "tenantId=" + tenantId +
                ", branchCode=" + branchCode +
                ", entryDate=" + entryDate +
                ", batchCode='" + batchCode + '\'' +
                ", setNo=" + setNo +
                '}';
    }
}
