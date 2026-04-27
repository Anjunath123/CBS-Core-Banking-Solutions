package com.canfin.corebanking.customerservice.repository;

import com.canfin.corebanking.customerservice.entity.D009040Key;
import com.canfin.corebanking.customerservice.entity.VoucherMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface VoucherMstRepository extends JpaRepository<VoucherMst, D009040Key> {

    @Query("SELECT COALESCE(MAX(v.d009040Key.setNo), 0) FROM VoucherMst v WHERE v.d009040Key.tenantId = ?1 AND v.d009040Key.branchCode = ?2 AND v.d009040Key.entryDate = ?3 AND v.d009040Key.batchCode = ?4")
    Integer findMaxSetNo(Integer tenantId, Integer branchCode, Date entryDate, String batchCode);
}
