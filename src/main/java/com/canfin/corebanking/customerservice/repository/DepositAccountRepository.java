package com.canfin.corebanking.customerservice.repository;


import com.canfin.corebanking.customerservice.entity.DepositAccount;
import com.canfin.corebanking.customerservice.entity.DepositAccountKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepositAccountRepository extends JpaRepository<DepositAccount,DepositAccountKey> {
    
    @Query("SELECT MAX(d.depositAccountKey.accountNumber) FROM DepositAccount d WHERE d.depositAccountKey.tenantId = ?1 AND d.depositAccountKey.branchCode = ?2")
    String findMaxAccountNumber(Integer tenantId, Integer branchCode);

    @Query("SELECT d FROM DepositAccount d WHERE d.depositAccountKey.tenantId = ?1 AND d.depositAccountKey.branchCode = ?2 AND d.depositAccountKey.accountNumber = ?3")
    Optional<DepositAccount> findByUniqueDepositRecord(Integer tenantId, Integer branchCode, String accountId);

    @Query("SELECT d FROM DepositAccount d WHERE d.depositAccountKey.tenantId = ?1 AND d.depositAccountKey.branchCode = ?2 AND d.depositAccountKey.accountNumber = ?3 AND d.isActive = ?4 AND d.authStatus = ?5")
    DepositAccount getDepositAccountIsActiveAndAuthorize(Integer tenantId, Integer branchCode, String accountId,Integer isActive,String authStatus);

}
