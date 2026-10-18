package com.canfin.corebanking.customerservice.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LoanAgainstFDKey implements Serializable {

    @Column(nullable = false, length = 5)
    private Integer tenantId;

    @Column(nullable = false, length = 5)
    private Integer branchCode;

    @Column(nullable = false, length = 32)
    private String loanAccountNumber = "";

    public Integer getTenantId() { return tenantId; }
    public void setTenantId(Integer tenantId) { this.tenantId = tenantId; }

    public Integer getBranchCode() { return branchCode; }
    public void setBranchCode(Integer branchCode) { this.branchCode = branchCode; }

    public String getLoanAccountNumber() { return loanAccountNumber; }
    public void setLoanAccountNumber(String loanAccountNumber) { this.loanAccountNumber = loanAccountNumber; }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LoanAgainstFDKey)) return false;
        LoanAgainstFDKey that = (LoanAgainstFDKey) o;
        return Objects.equals(tenantId, that.tenantId) && Objects.equals(branchCode, that.branchCode) && Objects.equals(loanAccountNumber, that.loanAccountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenantId, branchCode, loanAccountNumber);
    }
}
