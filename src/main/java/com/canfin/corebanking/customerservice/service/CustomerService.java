package com.canfin.corebanking.customerservice.service;

import com.canfin.corebanking.customerservice.dto.CustomerDto;
import com.canfin.corebanking.customerservice.entity.Customer;
import com.canfin.corebanking.customerservice.exception.OmniNGException;

public interface CustomerService {

    public CustomerDto saveCustomer(CustomerDto request) throws OmniNGException;

    public CustomerDto  approveCustomer(Long customerId,Integer branchCode) throws OmniNGException;

    public Customer getUniqueCustomer(Integer tenantId, Integer branchCode, Long customerId) throws OmniNGException;

    public Customer findByAuthorizedCustomer(Integer tenantId, Integer branchCode, Long customerId,Integer isActive,String customerStatus) throws OmniNGException;

    public CustomerDto rejectCustomer(Long customerId,Integer branchCode) throws OmniNGException;

    public CustomerDto updateCustomer(CustomerDto request) throws OmniNGException;

    public CustomerDto getCustomerDetails(Integer branchCode,Long customerId) throws OmniNGException;

    public void deleteCustomer(Integer branchCode,Long customerId) throws OmniNGException;

    public java.util.List<CustomerDto> getApprovedCustomers(Integer branchCode) throws OmniNGException;
}
