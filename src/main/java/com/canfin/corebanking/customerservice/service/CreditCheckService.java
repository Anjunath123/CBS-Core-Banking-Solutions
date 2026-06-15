package com.canfin.corebanking.customerservice.service;

public interface CreditCheckService {

    Integer getCibilScore(Long customerId);

    Double calculateEligibleLoanAmount(Double monthlyIncome, Double existingEmi, Double interestRate, Integer tenureMonths);

    boolean isEligible(Integer cibilScore, Double monthlyIncome, Double requestedLoanAmount, Double existingEmi, Double interestRate, Integer tenureMonths);
}
