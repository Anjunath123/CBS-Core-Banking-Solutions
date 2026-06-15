package com.canfin.corebanking.customerservice.repository;

import com.canfin.corebanking.customerservice.entity.HomeLoan;
import com.canfin.corebanking.customerservice.enums.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HomeLoanRepository extends JpaRepository<HomeLoan, Long> {

    Optional<HomeLoan> findByLoanAccountNumber(String loanAccountNumber);

    List<HomeLoan> findByCustomerId(Long customerId);

    List<HomeLoan> findByLoanStatus(LoanStatus loanStatus);

    boolean existsByLoanAccountNumber(String loanAccountNumber);
}
