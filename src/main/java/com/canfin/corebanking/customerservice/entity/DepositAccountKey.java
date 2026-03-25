package com.canfin.corebanking.customerservice.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class DepositAccountKey implements Serializable {

    @Column(nullable = false,length =5)
    private Integer tenantId;

    @Column(nullable = false,length =5)
    private Integer branchCode;

    @Column(nullable = false,length =32)
    private String accountNumber="";

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

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DepositAccountKey)) return false;
        DepositAccountKey that = (DepositAccountKey) o;
        return Objects.equals(tenantId, that.tenantId) && Objects.equals(branchCode, that.branchCode) && Objects.equals(accountNumber, that.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenantId, branchCode, accountNumber);
    }
}
