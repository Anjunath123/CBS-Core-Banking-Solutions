package com.canfin.corebanking.customerservice.service.impl;

import com.canfin.corebanking.customerservice.dto.HomeLoanApplicationDto;
import com.canfin.corebanking.customerservice.dto.HomeLoanDisbursementDto;
import com.canfin.corebanking.customerservice.dto.HomeLoanEmiScheduleDto;
import com.canfin.corebanking.customerservice.entity.HomeLoan;
import com.canfin.corebanking.customerservice.entity.HomeLoanEmiSchedule;
import com.canfin.corebanking.customerservice.entity.HomeLoanTransaction;
import com.canfin.corebanking.customerservice.entity.SavingsAccount;
import com.canfin.corebanking.customerservice.enums.LoanStatus;
import com.canfin.corebanking.customerservice.exception.OmniNGException;
import com.canfin.corebanking.customerservice.repository.HomeLoanEmiScheduleRepository;
import com.canfin.corebanking.customerservice.repository.HomeLoanRepository;
import com.canfin.corebanking.customerservice.repository.HomeLoanTransactionRepository;
import com.canfin.corebanking.customerservice.repository.SavingsAccountRepository;
import com.canfin.corebanking.customerservice.service.CreditCheckService;
import com.canfin.corebanking.customerservice.service.HomeLoanService;
import com.canfin.corebanking.customerservice.service.SavingsAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class HomeLoanServiceImpl implements HomeLoanService {

    private static final Logger logger = LoggerFactory.getLogger(HomeLoanServiceImpl.class);
    private static final double PENALTY_RATE = 0.02; // 2% penalty on EMI amount

    @Autowired
    private HomeLoanRepository homeLoanRepository;

    @Value("${CBS_BANK_TENANT}")
    private Integer tenantId;


    @Autowired
    private HomeLoanEmiScheduleRepository emiScheduleRepository;

    @Autowired
    private HomeLoanTransactionRepository transactionRepository;

    @Autowired
    private CreditCheckService creditCheckService;

    @Autowired
    private SavingsAccountRepository repository;

    // STEP 1: Apply for Home Loan
    @Override
    @Transactional
    public HomeLoanApplicationDto applyForLoan(HomeLoanApplicationDto request) {
        logger.info("Home Loan Application received for customerId={}", request.getCustomerId());

        HomeLoan loan = new HomeLoan();
        loan.setLoanAccountNumber(generateLoanAccountNumber());
        loan.setCustomerId(request.getCustomerId());
        loan.setBranchCode(request.getBranchCode());
        loan.setApplicantName(request.getApplicantName());
        loan.setLoanAmount(request.getLoanAmount());
        loan.setInterestRate(request.getInterestRate());
        loan.setTenureMonths(request.getTenureMonths());
        loan.setMonthlyIncome(request.getMonthlyIncome());
        loan.setExistingEmiTotal(request.getExistingEmiTotal() != null ? request.getExistingEmiTotal() : 0D);
        loan.setPropertyAddress(request.getPropertyAddress());
        loan.setPropertyValuation(request.getPropertyValuation());
        loan.setDisbursementType(request.getDisbursementType());
        loan.setLoanStatus(LoanStatus.APPLIED);
        loan.setApplicationDate(new Date());
        loan.setOutstandingPrincipal(request.getLoanAmount());

        // Calculate EMI
        double emi = calculateEmi(request.getLoanAmount(), request.getInterestRate(), request.getTenureMonths());
        loan.setEmiAmount(emi);

        HomeLoan saved = homeLoanRepository.save(loan);
        logger.info("Loan application created with accountNumber={}", saved.getLoanAccountNumber());
        return toDto(saved);
    }

    // STEP 2: Verification & Eligibility Check
    @Override
    @Transactional
    public HomeLoanApplicationDto verifyAndCheckEligibility(String loanAccountNumber) {
        HomeLoan loan = findLoan(loanAccountNumber);

        if (loan.getLoanStatus() != LoanStatus.APPLIED) {
            throw new OmniNGException("Loan is not in APPLIED state. Current: " + loan.getLoanStatus());
        }

        loan.setLoanStatus(LoanStatus.UNDER_VERIFICATION);

        // Credit check API call
        Integer cibilScore = creditCheckService.getCibilScore(loan.getCustomerId());
        loan.setCibilScore(cibilScore);

        boolean eligible = creditCheckService.isEligible(
                cibilScore, loan.getMonthlyIncome(), loan.getLoanAmount(),
                loan.getExistingEmiTotal(), loan.getInterestRate(), loan.getTenureMonths());

        if (eligible) {
            loan.setLoanStatus(LoanStatus.ELIGIBLE);
            logger.info("Loan {} is ELIGIBLE. CIBIL={}", loanAccountNumber, cibilScore);
        } else {
            loan.setLoanStatus(LoanStatus.NOT_ELIGIBLE);
            logger.info("Loan {} is NOT ELIGIBLE. CIBIL={}", loanAccountNumber, cibilScore);
        }

        return toDto(homeLoanRepository.save(loan));
    }

    // STEP 3: Loan Approval
    @Override
    @Transactional
    public HomeLoanApplicationDto approveLoan(String loanAccountNumber) {
        HomeLoan loan = findLoan(loanAccountNumber);

        if (loan.getLoanStatus() != LoanStatus.ELIGIBLE) {
            throw new OmniNGException("Loan must be ELIGIBLE to approve. Current: " + loan.getLoanStatus());
        }

        loan.setLoanStatus(LoanStatus.APPROVED);
        loan.setApprovalDate(new Date());

        // Generate EMI schedule
        generateEmiSchedule(loan);

        logger.info("Loan {} APPROVED. EMI=₹{}, Tenure={} months", loanAccountNumber, loan.getEmiAmount(), loan.getTenureMonths());
        return toDto(homeLoanRepository.save(loan));
    }

    // STEP 4: Loan Disbursement
    @Override
    @Transactional
    public HomeLoanApplicationDto disburseLoan(HomeLoanDisbursementDto request) {
        HomeLoan loan = findLoan(request.getLoanAccountNumber());

        if (loan.getLoanStatus() != LoanStatus.APPROVED) {
            throw new OmniNGException("Loan must be APPROVED to disburse. Current: " + loan.getLoanStatus());
        }

        loan.setBeneficiaryAccountNumber(request.getBeneficiaryAccountNumber());
        loan.setBeneficiaryName(request.getBeneficiaryName());
        loan.setLoanStatus(LoanStatus.DISBURSED);
        loan.setDisbursementDate(new Date());

        // Log disbursement transaction
        logTransaction(loan.getLoanAccountNumber(), "DISBURSEMENT", loan.getLoanAmount(),
                "Disbursed to " + request.getBeneficiaryName() + " A/C: " + request.getBeneficiaryAccountNumber(), true);
        SavingsAccount savingsAccount= repository.findByUniqueRecord(tenantId,loan.getBranchCode(),loan.getBeneficiaryAccountNumber()).get();
        Double availableBal=savingsAccount.getAvailableBalance();
        savingsAccount.setAvailableBalance(availableBal+loan.getLoanAmount());
        repository.save(savingsAccount);

        logger.info("Loan {} DISBURSED ₹{} to {}", loan.getLoanAccountNumber(), loan.getLoanAmount(), request.getBeneficiaryAccountNumber());
        return toDto(homeLoanRepository.save(loan));
    }

    // STEP 5: Get EMI Schedule
    @Override
    public List<HomeLoanEmiScheduleDto> getEmiSchedule(String loanAccountNumber) {
        findLoan(loanAccountNumber); // validate loan exists
        return emiScheduleRepository.findByLoanAccountNumberOrderByInstallmentNumber(loanAccountNumber)
                .stream().map(this::toEmiDto).collect(Collectors.toList());
    }

    // STEP 6: Pay EMI (simulates auto-debit / manual payment)
    @Override
    @Transactional
    public HomeLoanApplicationDto payEmi(String loanAccountNumber) {
        HomeLoan loan = findLoan(loanAccountNumber);

        if (loan.getLoanStatus() != LoanStatus.DISBURSED && loan.getLoanStatus() != LoanStatus.ACTIVE) {
            throw new OmniNGException("Loan not in active/disbursed state for EMI payment");
        }

        // Find next unpaid EMI
        HomeLoanEmiSchedule nextEmi = emiScheduleRepository
                .findFirstByLoanAccountNumberAndPaidFalseOrderByInstallmentNumber(loanAccountNumber)
                .orElseThrow(() -> new OmniNGException("All EMIs are already paid"));

        // Check if overdue - apply penalty (STEP 8)
        if (nextEmi.getDueDate().before(new Date())) {
            double penalty = Math.round(nextEmi.getEmiAmount() * PENALTY_RATE * 100.0) / 100.0;
            nextEmi.setPenaltyApplied(penalty);
            loan.setPenaltyAmount(loan.getPenaltyAmount() + penalty);
            logTransaction(loanAccountNumber, "PENALTY", penalty, "Late payment penalty for installment " + nextEmi.getInstallmentNumber(), true);
        }

        // Mark EMI as paid
        nextEmi.setPaid(true);
        nextEmi.setPaidDate(new Date());
        emiScheduleRepository.save(nextEmi);

        // Update loan balances
        loan.setOutstandingPrincipal(loan.getOutstandingPrincipal() - nextEmi.getPrincipalComponent());
        loan.setTotalInterestPaid(loan.getTotalInterestPaid() + nextEmi.getInterestComponent());
        loan.setEmisPaid(loan.getEmisPaid() + 1);
        loan.setLoanStatus(LoanStatus.ACTIVE);

        logTransaction(loanAccountNumber, "EMI_PAYMENT", nextEmi.getEmiAmount(),
                "EMI #" + nextEmi.getInstallmentNumber() + " paid", true);

        // Check if all EMIs paid → auto close
        if (loan.getEmisPaid().equals(loan.getTenureMonths())) {
            loan.setLoanStatus(LoanStatus.CLOSED);
            loan.setClosureDate(new Date());
            loan.setOutstandingPrincipal(0D);
            logTransaction(loanAccountNumber, "CLOSURE", 0D, "Loan fully repaid and closed", true);
        }

        logger.info("EMI #{} paid for loan {}. Outstanding=₹{}", nextEmi.getInstallmentNumber(), loanAccountNumber, loan.getOutstandingPrincipal());
        return toDto(homeLoanRepository.save(loan));
    }

    // STEP 9: Manual Loan Closure (pre-closure)
    @Override
    @Transactional
    public HomeLoanApplicationDto closeLoan(String loanAccountNumber) {
        HomeLoan loan = findLoan(loanAccountNumber);

        if (loan.getLoanStatus() == LoanStatus.CLOSED) {
            throw new OmniNGException("Loan is already closed");
        }

        loan.setLoanStatus(LoanStatus.CLOSED);
        loan.setClosureDate(new Date());
        logTransaction(loanAccountNumber, "CLOSURE", loan.getOutstandingPrincipal(),
                "Loan pre-closed. Settlement amount: " + loan.getOutstandingPrincipal(), true);
        loan.setOutstandingPrincipal(0D);

        logger.info("Loan {} CLOSED", loanAccountNumber);
        return toDto(homeLoanRepository.save(loan));
    }

    @Override
    public HomeLoanApplicationDto getLoanByAccountNumber(String loanAccountNumber) {
        return toDto(findLoan(loanAccountNumber));
    }

    @Override
    public List<HomeLoanApplicationDto> getLoansByCustomerId(Long customerId) {
        return homeLoanRepository.findByCustomerId(customerId)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    // --- Helper Methods ---

    private HomeLoan findLoan(String loanAccountNumber) {
        return homeLoanRepository.findByLoanAccountNumber(loanAccountNumber)
                .orElseThrow(() -> new OmniNGException("Loan not found: " + loanAccountNumber));
    }

    private double calculateEmi(Double principal, Double annualRate, Integer tenureMonths) {
        double monthlyRate = annualRate / 12 / 100;
        double factor = Math.pow(1 + monthlyRate, tenureMonths);
        return Math.round(principal * monthlyRate * factor / (factor - 1));
    }

    private void generateEmiSchedule(HomeLoan loan) {
        double principal = loan.getLoanAmount();
        double monthlyRate = loan.getInterestRate() / 12 / 100;
        double emi = loan.getEmiAmount();
        double outstanding = principal;

        // First EMI on 1st of next month
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        List<HomeLoanEmiSchedule> schedules = new ArrayList<>();
        for (int i = 1; i <= loan.getTenureMonths(); i++) {
            double interestComponent = Math.round(outstanding * monthlyRate);
            double principalComponent = emi - interestComponent;

            // Last EMI adjustment to clear outstanding exactly
            if (i == loan.getTenureMonths()) {
                principalComponent = outstanding;
                interestComponent = Math.round(outstanding * monthlyRate);
                emi = principalComponent + interestComponent;
            }

            outstanding = Math.round(outstanding - principalComponent);
            if (outstanding < 0) outstanding = 0;

            HomeLoanEmiSchedule schedule = new HomeLoanEmiSchedule();
            schedule.setLoanAccountNumber(loan.getLoanAccountNumber());
            schedule.setInstallmentNumber(i);
            schedule.setDueDate(cal.getTime());
            schedule.setEmiAmount(emi);
            schedule.setPrincipalComponent(principalComponent);
            schedule.setInterestComponent(interestComponent);
            schedule.setOutstandingAfterPayment(outstanding);
            schedules.add(schedule);

            // Next month 1st
            cal.add(Calendar.MONTH, 1);
        }
        emiScheduleRepository.saveAll(schedules);
    }

    private void logTransaction(String loanAccountNumber, String type, Double amount, String description, boolean success) {
        HomeLoanTransaction txn = new HomeLoanTransaction();
        txn.setLoanAccountNumber(loanAccountNumber);
        txn.setTransactionType(type);
        txn.setAmount(amount);
        txn.setTransactionDate(new Date());
        txn.setDescription(description);
        txn.setSuccess(success);
        transactionRepository.save(txn);
    }

    private String generateLoanAccountNumber() {
        return "HL" + System.currentTimeMillis();
    }

    private HomeLoanApplicationDto toDto(HomeLoan entity) {
        HomeLoanApplicationDto dto = new HomeLoanApplicationDto();
        dto.setId(entity.getId());
        dto.setLoanAccountNumber(entity.getLoanAccountNumber());
        dto.setCustomerId(entity.getCustomerId());
        dto.setBranchCode(entity.getBranchCode());
        dto.setApplicantName(entity.getApplicantName());
        dto.setLoanAmount(entity.getLoanAmount());
        dto.setInterestRate(entity.getInterestRate());
        dto.setTenureMonths(entity.getTenureMonths());
        dto.setEmiAmount(entity.getEmiAmount());
        dto.setOutstandingPrincipal(entity.getOutstandingPrincipal());
        dto.setCibilScore(entity.getCibilScore());
        dto.setMonthlyIncome(entity.getMonthlyIncome());
        dto.setExistingEmiTotal(entity.getExistingEmiTotal());
        dto.setPropertyAddress(entity.getPropertyAddress());
        dto.setPropertyValuation(entity.getPropertyValuation());
        dto.setDisbursementType(entity.getDisbursementType());
        dto.setBeneficiaryAccountNumber(entity.getBeneficiaryAccountNumber());
        dto.setBeneficiaryName(entity.getBeneficiaryName());
        dto.setLoanStatus(entity.getLoanStatus());
        dto.setApplicationDate(entity.getApplicationDate());
        dto.setApprovalDate(entity.getApprovalDate());
        dto.setDisbursementDate(entity.getDisbursementDate());
        dto.setEmisPaid(entity.getEmisPaid());
        dto.setPenaltyAmount(entity.getPenaltyAmount());
        dto.setTotalInterestPaid(entity.getTotalInterestPaid());
        return dto;
    }

    private HomeLoanEmiScheduleDto toEmiDto(HomeLoanEmiSchedule entity) {
        HomeLoanEmiScheduleDto dto = new HomeLoanEmiScheduleDto();
        dto.setId(entity.getId());
        dto.setLoanAccountNumber(entity.getLoanAccountNumber());
        dto.setInstallmentNumber(entity.getInstallmentNumber());
        dto.setDueDate(entity.getDueDate());
        dto.setEmiAmount(entity.getEmiAmount());
        dto.setPrincipalComponent(entity.getPrincipalComponent());
        dto.setInterestComponent(entity.getInterestComponent());
        dto.setOutstandingAfterPayment(entity.getOutstandingAfterPayment());
        dto.setPaid(entity.isPaid());
        dto.setPaidDate(entity.getPaidDate());
        dto.setPenaltyApplied(entity.getPenaltyApplied());
        return dto;
    }
}
