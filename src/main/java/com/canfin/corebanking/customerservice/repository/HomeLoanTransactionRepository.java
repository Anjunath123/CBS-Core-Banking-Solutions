package com.canfin.corebanking.customerservice.repository;

import com.canfin.corebanking.customerservice.entity.HomeLoanTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomeLoanTransactionRepository extends JpaRepository<HomeLoanTransaction, Long> {

    List<HomeLoanTransaction> findByLoanAccountNumberOrderByTransactionDateDesc(String loanAccountNumber);
}
