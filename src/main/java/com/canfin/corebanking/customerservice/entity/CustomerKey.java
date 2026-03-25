package com.canfin.corebanking.customerservice.entity;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Objects;


@Embeddable
@MappedSuperclass
public class CustomerKey implements Serializable {

    @Column(nullable = false,length =5)
    private Integer tenantId;

    @Column(nullable = false,length =5)
    private Integer branchCode;

    @Column(nullable = false,length =10)
    private Long customerId;

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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerKey)) return false;
        CustomerKey that = (CustomerKey) o;
        return Objects.equals(tenantId, that.tenantId) && Objects.equals(branchCode, that.branchCode) && Objects.equals(customerId, that.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenantId, branchCode, customerId);
    }

    @Override
    public String toString() {
        return "CustomerKey{" +
                "tenantId=" + tenantId +
                ", branchCode=" + branchCode +
                ", customerId=" + customerId +
                '}';
    }
}
