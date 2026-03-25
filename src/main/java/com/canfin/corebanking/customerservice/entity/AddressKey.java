package com.canfin.corebanking.customerservice.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AddressKey extends CustomerKey implements Serializable {

    @Column(nullable = false)
    private Integer srNo = 0;

    public Integer getSrNo() {
        return srNo;
    }

    public void setSrNo(Integer srNo) {
        this.srNo = srNo;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AddressKey)) return false;
        if (!super.equals(o)) return false;
        AddressKey that = (AddressKey) o;
        return Objects.equals(srNo, that.srNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), srNo);
    }

    @Override
    public String toString() {
        return "AddressKey{" +
                "srNo=" + srNo +
                '}';
    }
}
