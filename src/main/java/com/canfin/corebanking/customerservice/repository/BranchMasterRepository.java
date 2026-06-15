package com.canfin.corebanking.customerservice.repository;

import com.canfin.corebanking.customerservice.entity.BranchMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BranchMasterRepository extends JpaRepository<BranchMaster, Long> {
    Optional<BranchMaster> findByBranchCode(String branchCode);
    boolean existsByBranchCode(String branchCode);
}
