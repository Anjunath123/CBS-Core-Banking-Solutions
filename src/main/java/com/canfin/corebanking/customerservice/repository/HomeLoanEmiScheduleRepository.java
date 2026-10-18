package com.canfin.corebanking.customerservice.repository;

import com.canfin.corebanking.customerservice.entity.HomeLoanEmiSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface HomeLoanEmiScheduleRepository extends JpaRepository<HomeLoanEmiSchedule, Long> {

    List<HomeLoanEmiSchedule> findByLoanAccountNumberOrderByInstallmentNumber(String loanAccountNumber);

    Optional<HomeLoanEmiSchedule> findByLoanAccountNumberAndInstallmentNumber(String loanAccountNumber, Integer installmentNumber);

    List<HomeLoanEmiSchedule> findByLoanAccountNumberAndPaidFalseAndDueDateBefore(String loanAccountNumber, Date date);

    Optional<HomeLoanEmiSchedule> findFirstByLoanAccountNumberAndPaidFalseOrderByInstallmentNumber(String loanAccountNumber);
}
