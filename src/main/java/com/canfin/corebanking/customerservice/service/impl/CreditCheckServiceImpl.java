package com.canfin.corebanking.customerservice.service.impl;

import com.canfin.corebanking.customerservice.service.CreditCheckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CreditCheckServiceImpl implements CreditCheckService {

    private static final Logger logger = LoggerFactory.getLogger(CreditCheckServiceImpl.class);
    private static final int MIN_CIBIL_SCORE = 650;
    private static final double MAX_EMI_TO_INCOME_RATIO = 0.40; // 40% of income

    @Override
    public Integer getCibilScore(Long customerId) {
        // Simulated CIBIL API call - in production, integrate with actual bureau
        logger.info("Fetching CIBIL score for customerId={}", customerId);
        return 720 + (int) (customerId % 100); // Simulated score between 720-819
    }

    @Override
    public Double calculateEligibleLoanAmount(Double monthlyIncome, Double existingEmi, Double interestRate, Integer tenureMonths) {
        double maxEmi = (monthlyIncome * MAX_EMI_TO_INCOME_RATIO) - existingEmi;
        if (maxEmi <= 0) return 0D;

        double monthlyRate = interestRate / 12 / 100;
        // P = EMI * [(1+r)^n - 1] / [r * (1+r)^n]
        double factor = Math.pow(1 + monthlyRate, tenureMonths);
        return (double) Math.round(maxEmi * (factor - 1) / (monthlyRate * factor));
    }

    @Override
    public boolean isEligible(Integer cibilScore, Double monthlyIncome, Double requestedLoanAmount, Double existingEmi, Double interestRate, Integer tenureMonths) {
        if (cibilScore < MIN_CIBIL_SCORE) {
            logger.info("CIBIL score {} below minimum {}", cibilScore, MIN_CIBIL_SCORE);
            return false;
        }
        Double eligibleAmount = calculateEligibleLoanAmount(monthlyIncome, existingEmi, interestRate, tenureMonths);
        logger.info("Eligible amount={}, requested={}", eligibleAmount, requestedLoanAmount);
        return requestedLoanAmount <= eligibleAmount;
    }
}
