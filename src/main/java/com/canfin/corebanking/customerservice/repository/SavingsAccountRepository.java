package com.canfin.corebanking.customerservice.repository;

import com.canfin.corebanking.customerservice.entity.DepositAccountKey;
import com.canfin.corebanking.customerservice.entity.SavingsAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SavingsAccountRepository extends JpaRepository<SavingsAccount, DepositAccountKey> {

    @Query("SELECT s FROM SavingsAccount s WHERE s.savingsAccountKey.tenantId = ?1 AND s.savingsAccountKey.branchCode = ?2 AND s.savingsAccountKey.accountNumber = ?3 AND s.isActive = ?4 AND s.authStatus = ?5")
    Optional<SavingsAccount> findActiveAccount(Integer tenantId, Integer branchCode, String accountNumber, Integer isActive, String authStatus);

    @Query("SELECT s FROM SavingsAccount s WHERE s.savingsAccountKey.tenantId = ?1 AND s.savingsAccountKey.branchCode = ?2 AND s.savingsAccountKey.accountNumber = ?3")
    Optional<SavingsAccount> findByUniqueRecord(Integer tenantId, Integer branchCode, String accountNumber);

    @Query("SELECT MAX(s.savingsAccountKey.accountNumber) FROM SavingsAccount s WHERE s.savingsAccountKey.tenantId = ?1 AND s.savingsAccountKey.branchCode = ?2")
    String findMaxAccountNumber(Integer tenantId, Integer branchCode);
}
