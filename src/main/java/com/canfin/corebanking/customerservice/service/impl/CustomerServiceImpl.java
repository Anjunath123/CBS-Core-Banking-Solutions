package com.canfin.corebanking.customerservice.service.impl;


import com.canfin.corebanking.customerservice.constants.AppConstants;
import com.canfin.corebanking.customerservice.dto.AddressDto;
import com.canfin.corebanking.customerservice.dto.CustomerDto;
import com.canfin.corebanking.customerservice.dto.KycDocumentDto;
import com.canfin.corebanking.customerservice.entity.*;
import com.canfin.corebanking.customerservice.enums.CustomerType;
import com.canfin.corebanking.customerservice.enums.Status;
import com.canfin.corebanking.customerservice.exception.OmniNGException;
import com.canfin.corebanking.customerservice.repository.CustomerRepository;
import com.canfin.corebanking.customerservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = OmniNGException.class)
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Value("${CBS_BANK_TENANT}")
    private Integer tenantId;



    @Override
    public CustomerDto saveCustomer(CustomerDto request) throws OmniNGException {
        Customer customer = new Customer();
        CustomerKey customerKey = new CustomerKey();
        Mapper mapperObj = new DozerBeanMapper();
        customerKey.setTenantId(tenantId);
        customerKey.setBranchCode(request.getBranchCode());
        Long customerId=generateCustomerId(tenantId,request.getBranchCode());
        customerKey.setCustomerId(customerId);
        customer.setCustomerKey(customerKey);
        mapperObj.map(request,customer);

        //Addresses
        if(request.getAddresses() != null && !request.getAddresses().isEmpty()) {
            AtomicInteger srNo = new AtomicInteger(1);
             request.getAddresses().forEach(addressDto -> {
                    Address address = new Address();
                    AddressKey addressKey = new AddressKey();
                    addressKey.setTenantId(tenantId);
                    addressKey.setBranchCode(request.getBranchCode());
                    addressKey.setCustomerId(customerId);
                    addressKey.setSrNo(srNo.getAndIncrement());
                    address.setAddressKey(addressKey);
                    address.setStatus(Status.PENDING.toString());
                    mapperObj.map(addressDto, address);
                    address.setStatus(Status.PENDING.toString());
                    customer.addAddress(address);
                });
        }

        //KYC Documents
        if(request.getKycDocumentDtoList() != null && !request.getKycDocumentDtoList().isEmpty()){
            AtomicInteger srNo=new AtomicInteger(1);
            request.getKycDocumentDtoList().forEach(kycDocumentDto -> {
                    KycDocument kycDocument=new KycDocument();
                    KycDocumentKey kycDocumentKey=new KycDocumentKey();
                    kycDocumentKey.setTenantId(tenantId);
                    kycDocumentKey.setBranchCode(request.getBranchCode());
                    kycDocumentKey.setCustomerId(customerId);
                    kycDocumentKey.setSrNo(srNo.getAndIncrement());
                    kycDocument.setKycDocumentKey(kycDocumentKey);
                    mapperObj.map(kycDocumentDto, kycDocument);
                    kycDocument.setKycStatus(Status.PENDING.toString());
                    customer.addKycDocument(kycDocument);
                });
         }

        customer.setIsActive(AppConstants.ACTIVE);
        customer.setCreateAt(new Date());
        customer.setAuthStatus(AppConstants.AUTH_PENDING);
        customer.setCustomerStatus(CustomerType.PENDING.toString());
        Customer saveCustomer = customerRepository.save(customer);
        return mapToResponse(saveCustomer);
    }

    @Override
    public CustomerDto approveCustomer(Long customerId, Integer branchCode) throws OmniNGException {
        Customer customer =getUniqueCustomer(tenantId, branchCode, customerId);

        if(customer.getCustomerStatus().equalsIgnoreCase(CustomerType.APPROVED.toString())) {
            throw new OmniNGException("Customer already approved");
        }
        if(customer.getCustomerStatus().equalsIgnoreCase(CustomerType.REJECTED.toString())){
            throw new OmniNGException("Customer already rejected");
        }
        if(!customer.getCustomerStatus().equalsIgnoreCase(CustomerType.PENDING.toString())) {
            throw new OmniNGException("Invalid customer status");
        }
        
        customer.setCustomerStatus(CustomerType.APPROVED.toString());
        customer.setAuthStatus(AppConstants.AUTH_APPROVED);
        customer.setLastModifiedDate(new Date());
        
        if(null!=customer.getAddressList() && !customer.getAddressList().isEmpty()){
            customer.getAddressList().forEach(address -> 
                address.setStatus(Status.VERIFIED.toString())
            );
        }

        if(null!=customer.getKycDocumentList() && !customer.getKycDocumentList().isEmpty()){
            customer.getKycDocumentList().forEach(kycDocument -> 
                kycDocument.setKycStatus(Status.VERIFIED.toString())
            );
        }
        Customer approvedCustomer = customerRepository.save(customer);
        return mapToResponse(approvedCustomer);
    }

    @Override
    @Transactional(readOnly = true)
    public Customer getUniqueCustomer(Integer tenantId, Integer branchCode, Long customerId) throws OmniNGException {
        return customerRepository.getUniqueCustomer(tenantId, branchCode, customerId,AppConstants.ACTIVE)
                .orElseThrow(() -> new OmniNGException("Customer not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Customer findByAuthorizedCustomer(Integer tenantId, Integer branchCode, Long customerId, Integer isActive, String customerStatus) throws OmniNGException {
        return customerRepository.findByAuthorizeCustomer(tenantId, branchCode, customerId, isActive, customerStatus)
                .orElseThrow(() -> new OmniNGException("Customer not authorized"));
    }

    @Override
    public CustomerDto rejectCustomer(Long customerId, Integer branchCode) throws OmniNGException {
        Customer customer =getUniqueCustomer(tenantId, branchCode, customerId);
        if(customer.getCustomerStatus().equalsIgnoreCase(CustomerType.APPROVED.toString())) {
            throw new OmniNGException("Customer already approved");
        }
        if(customer.getCustomerStatus().equalsIgnoreCase(CustomerType.REJECTED.toString())){
            throw new OmniNGException("Customer already rejected");
        }
        if(!customer.getCustomerStatus().equalsIgnoreCase(CustomerType.PENDING.toString())) {
            throw new OmniNGException("Invalid customer status");
        }
        customer.setCustomerStatus(CustomerType.REJECTED.toString());
        customer.setAuthStatus(AppConstants.AUTH_REJECTED);
        customer.setLastModifiedDate(new Date());

        if(null!=customer.getAddressList() && !customer.getAddressList().isEmpty()){
            customer.getAddressList().forEach(address ->
                    address.setStatus(Status.REJECTED.toString()));
        }

        if(null!=customer.getKycDocumentList() && !customer.getKycDocumentList().isEmpty()){
            customer.getKycDocumentList().forEach(kycDocument ->
                    kycDocument.setKycStatus(Status.REJECTED.toString()));
        }
        Customer rejectedCustomer = customerRepository.save(customer);
        return mapToResponse(rejectedCustomer);
    }

    @Override
    public CustomerDto updateCustomer(CustomerDto request) throws OmniNGException {
        
        if(null==request.getCustomerId()) {
            throw new OmniNGException("Customer Id is required");
        }
        Customer customer =getUniqueCustomer(tenantId, request.getBranchCode(), request.getCustomerId());
        if(customer.getCustomerStatus().equalsIgnoreCase(CustomerType.APPROVED.toString())) {
            throw new OmniNGException("Customer already approved. so cannot be update Customer Details");
        }

        Mapper mapperObj = new DozerBeanMapper();
        Long customerId = request.getCustomerId();
        Integer branchCode = request.getBranchCode();
        mapperObj.map(request, customer);
        customer.setLastModifiedDate(new Date());

        // Update addresses
        if(request.getAddresses() != null) {
            customer.getAddressList().clear();
            AtomicInteger addrSrNo = new AtomicInteger(1);
            request.getAddresses().forEach(addressDto -> {
                Address address = new Address();
                AddressKey addressKey = new AddressKey();
                addressKey.setTenantId(tenantId);
                addressKey.setBranchCode(branchCode);
                addressKey.setCustomerId(customerId);
                addressKey.setSrNo(addrSrNo.getAndIncrement());
                address.setAddressKey(addressKey);
                mapperObj.map(addressDto, address);
                address.setStatus(Status.PENDING.toString());
                customer.addAddress(address);
            });
        }

        // Update KYC documents
        if(request.getKycDocumentDtoList() != null) {
            customer.getKycDocumentList().clear();
            AtomicInteger kycSrNo = new AtomicInteger(1);
            request.getKycDocumentDtoList().forEach(kycDocumentDto -> {
                KycDocument kycDocument = new KycDocument();
                KycDocumentKey kycDocumentKey = new KycDocumentKey();
                kycDocumentKey.setTenantId(tenantId);
                kycDocumentKey.setBranchCode(branchCode);
                kycDocumentKey.setCustomerId(customerId);
                kycDocumentKey.setSrNo(kycSrNo.getAndIncrement());
                kycDocument.setKycDocumentKey(kycDocumentKey);
                mapperObj.map(kycDocumentDto, kycDocument);
                kycDocument.setKycStatus(Status.PENDING.toString());
                customer.addKycDocument(kycDocument);
            });
        }
        Customer updatedCustomer = customerRepository.save(customer);
        return mapToResponse(updatedCustomer);
    }

    private CustomerDto mapToResponse(Customer customer) {
        CustomerDto response = new CustomerDto();
        Mapper mapperObj = new DozerBeanMapper();
        response.setCustomerId(customer.getCustomerKey().getCustomerId());
        response.setBranchCode(customer.getCustomerKey().getBranchCode());
        response.setTenantId(customer.getCustomerKey().getTenantId());
        mapperObj.map(customer, response);
        
        if(customer.getAddressList() != null && !customer.getAddressList().isEmpty()) {
            List<AddressDto> addressDtos = customer.getAddressList().stream()
                .map(address -> {
                    AddressDto addressDto = new AddressDto();
                    addressDto.setSrNo(address.getAddressKey().getSrNo());
                    addressDto.setBranchCode(address.getAddressKey().getBranchCode());
                    addressDto.setCustomerId(address.getAddressKey().getCustomerId());
                    mapperObj.map(address, addressDto);
                    return addressDto;
                })
                .collect(Collectors.toList());
            response.setAddresses(addressDtos);
        }

        if(null!=customer.getKycDocumentList() && !customer.getKycDocumentList().isEmpty()){
            List<KycDocumentDto> kycDocumentDtoList=customer.getKycDocumentList().stream()
                    .map(kycDocument -> {
                        KycDocumentDto kycDocumentDto=new KycDocumentDto();
                        kycDocumentDto.setSrNo(kycDocument.getKycDocumentKey().getSrNo());
                        mapperObj.map(kycDocument, kycDocumentDto);
                        return kycDocumentDto;
                    })
                    .collect(Collectors.toList());
                response.setKycDocumentDtoList(kycDocumentDtoList);
        }

        
        return response;
    }
    
    @Transactional(readOnly = true)
    private Long generateCustomerId(Integer tenantId, Integer branchCode) {
        Long customerId=customerRepository.getMaxCustomerId(tenantId,branchCode);
        if(customerId==null){
            customerId=Long.valueOf(tenantId.toString()+branchCode.toString()+"1");
        }else{
            customerId=customerId+1;
        }
        return customerId;
    }
}