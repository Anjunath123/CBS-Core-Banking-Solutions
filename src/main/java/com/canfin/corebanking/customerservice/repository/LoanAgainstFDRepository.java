package com.canfin.corebanking.customerservice.repository;

import com.canfin.corebanking.customerservice.entity.LoanAgainstFD;
import com.canfin.corebanking.customerservice.entity.LoanAgainstFDKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanAgainstFDRepository extends JpaRepository<LoanAgainstFD, LoanAgainstFDKey> {

    @Query("SELECT MAX(l.loanAgainstFDKey.loanAccountNumber) FROM LoanAgainstFD l WHERE l.loanAgainstFDKey.tenantId = ?1 AND l.loanAgainstFDKey.branchCode = ?2")
    String findMaxLoanAccountNumber(Integer tenantId, Integer branchCode);

    @Query("SELECT l FROM LoanAgainstFD l WHERE l.loanAgainstFDKey.tenantId = ?1 AND l.loanAgainstFDKey.branchCode = ?2 AND l.loanAgainstFDKey.loanAccountNumber = ?3")
    Optional<LoanAgainstFD> findByUniqueLoanRecord(Integer tenantId, Integer branchCode, String loanAccountNumber);

    @Query("SELECT l FROM LoanAgainstFD l WHERE l.loanAgainstFDKey.tenantId = ?1 AND l.loanAgainstFDKey.branchCode = ?2 AND l.fdAccountNumber = ?3 AND l.isActive = ?4")
    Optional<LoanAgainstFD> findActiveLoanByFdAccount(Integer tenantId, Integer branchCode, String fdAccountNumber, Integer isActive);
}
