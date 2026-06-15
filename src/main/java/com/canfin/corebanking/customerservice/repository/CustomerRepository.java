package com.canfin.corebanking.customerservice.repository;


import com.canfin.corebanking.customerservice.entity.Customer;
import com.canfin.corebanking.customerservice.entity.CustomerKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, CustomerKey> {

    @Query("SELECT MAX(c.customerKey.customerId) FROM Customer c WHERE c.customerKey.tenantId=:tenantId and c.customerKey.branchCode=:branchCode")
    Long getMaxCustomerId(@Param("tenantId") Integer tenantId, @Param("branchCode") Integer branchCode);

    @Query("SELECT c FROM Customer c WHERE c.customerKey.tenantId=:tenantId and c.customerKey.branchCode=:branchCode and c.customerKey.customerId=:customerId and c.isActive=:isActive")
    Optional<Customer> getUniqueCustomer(@Param("tenantId") Integer tenantId,
                                         @Param("branchCode") Integer branchCode,
                                         @Param("customerId") Long customerId,
                                         @Param("isActive") Integer isActive);

    @Query("SELECT c FROM Customer c WHERE c.customerKey.tenantId=:tenantId and c.customerKey.branchCode=:branchCode and c.customerKey.customerId=:customerId and c.isActive=:isActive and c.customerStatus=:customerStatus")
    Optional<Customer>  findByAuthorizeCustomer(@Param("tenantId") Integer tenantId,
                                                @Param("branchCode") Integer branchCode,
                                                @Param("customerId") Long customerId,
                                                @Param("isActive") Integer isActive,
                                                @Param("customerStatus") String customerStatus);

    @Query("SELECT c FROM Customer c WHERE c.customerKey.tenantId=:tenantId and c.customerKey.branchCode=:branchCode and c.isActive=:isActive and c.customerStatus=:customerStatus")
    List<Customer> findApprovedCustomers(@Param("tenantId") Integer tenantId,
                                         @Param("branchCode") Integer branchCode,
                                         @Param("isActive") Integer isActive,
                                         @Param("customerStatus") String customerStatus);
}
