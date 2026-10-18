package com.canfin.corebanking.customerservice.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "SavingsAccount")
public class SavingsAccount implements Serializable {

    @EmbeddedId
    private DepositAccountKey savingsAccountKey;

    @Column(nullable = true, length = 100)
    private String accountName = "";

    @Column(nullable = true, length = 50)
    private String product = "";

    @Column(nullable = true, length = 50)
    private String scheme = "";

    @Column(nullable = true)
    private Double availableBalance = 0D;

    @Column(nullable = true)
    private Double currentBalance = 0D;

    @Column(nullable = true)
    private Double lienAmount = 0D;

    @Column(nullable = true)
    private Double minimumBalance = 0D;

    @Column(nullable = true, length = 4)
    private String currencyCode = "";

    @Column(nullable = true)
    private Date openDate;

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = true, length = 1)
    private Integer isActive;

    @Column(nullable = true, length = 1)
    private String authStatus = "";

    @Column(nullable = true, length = 4)
    private Integer accountStatus;

    public DepositAccountKey getSavingsAccountKey() {
        return savingsAccountKey;
    }

    public void setSavingsAccountKey(DepositAccountKey savingsAccountKey) {
        this.savingsAccountKey = savingsAccountKey;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public Double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(Double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public Double getLienAmount() {
        return lienAmount;
    }

    public void setLienAmount(Double lienAmount) {
        this.lienAmount = lienAmount;
    }

    public Double getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(Double minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }

    public Integer getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Integer accountStatus) {
        this.accountStatus = accountStatus;
    }
}
