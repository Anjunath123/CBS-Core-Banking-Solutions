package com.canfin.corebanking.customerservice.entity;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CustomerBankInfoKey extends CustomerKey implements Serializable {

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
        if (o == null || getClass() != o.getClass()) return false;
        CustomerBankInfoKey that = (CustomerBankInfoKey) o;
        return Objects.equals(srNo, that.srNo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(srNo);
    }

    @Override
    public String toString() {
        return "CustomerBankInfoKey{" +
                "srNo=" + srNo +
                '}';
    }
}
