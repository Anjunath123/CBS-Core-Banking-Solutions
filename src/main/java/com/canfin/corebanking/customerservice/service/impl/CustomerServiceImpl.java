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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.canfin.corebanking.customerservice.dto.CustomerBankInfoDto;

import java.time.LocalDateTime;
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
        customerKey.setTenantId(tenantId);
        customerKey.setBranchCode(request.getBranchCode());
        Long customerId=generateCustomerId(tenantId,request.getBranchCode());
        customerKey.setCustomerId(customerId);
        customer.setCustomerKey(customerKey);
        BeanUtils.copyProperties(request,customer);

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
                    BeanUtils.copyProperties(addressDto, address);
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
                    BeanUtils.copyProperties(kycDocumentDto, kycDocument);
                    kycDocument.setKycStatus(Status.PENDING.toString());
                    customer.addKycDocument(kycDocument);
                });
         }

        if(null!=request.getCustomerBankInfoDtoList() && !request.getCustomerBankInfoDtoList().isEmpty()){
            List<CustomerBankInfoDto> bankList = request.getCustomerBankInfoDtoList();
            //✅ Rule 1: Max 3 accounts
            if(bankList!=null && bankList.size()>3){
                 throw new OmniNGException("Customer Cannot be Adding More than 3 Bank Accounts");
            }
            // ✅ Rule 2: At least one primary account (isPrimary = 1)
            boolean hasPrimary = bankList.stream()
                    .anyMatch(dto -> dto.getPrimaryAccount() != null && dto.getPrimaryAccount() == 1);
            if (!hasPrimary) {
                throw new OmniNGException("At least one primary bank account is required");
            }
            // ✅ Rule 3: Only one primary account allowed
            long primaryCount = bankList.stream()
                    .filter(dto -> dto.getPrimaryAccount() != null && dto.getPrimaryAccount() == 1)
                    .count();
            if (primaryCount > 1) {
                throw new OmniNGException("Only one primary bank account is allowed");
            }
            AtomicInteger srNo=new AtomicInteger(1);
            request.getCustomerBankInfoDtoList().forEach(customerBankInfoDto -> {
                CustomerBankInfo customerBankInfo=new CustomerBankInfo();
                CustomerBankInfoKey customerBankInfoKey=new CustomerBankInfoKey();
                customerBankInfoKey.setSrNo(customerBankInfoDto.getSrNo());
                customerBankInfoKey.setTenantId(tenantId);
                customerBankInfoKey.setBranchCode(request.getBranchCode());
                customerBankInfoKey.setCustomerId(customerId);
                customerBankInfo.setCustomerBankInfoKey(customerBankInfoKey);
                BeanUtils.copyProperties(customerBankInfoDto, customerBankInfo);
                customerBankInfo.setStatus(Status.PENDING.toString());
                customer.addCustomerBankInfo(customerBankInfo);
            });
        }

        customer.setIsActive(AppConstants.ACTIVE);
        customer.setCreateAt(LocalDateTime.now());
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
        customer.setLastModifiedDate(LocalDateTime.now());
        
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

        if(null!=customer.getCustomerBankInfoList() && !customer.getCustomerBankInfoList().isEmpty()){
            customer.getCustomerBankInfoList().forEach(customerBankInfo ->
                    customerBankInfo.setStatus(Status.VERIFIED.toString())
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
        customer.setLastModifiedDate(LocalDateTime.now());

        if(null!=customer.getAddressList() && !customer.getAddressList().isEmpty()){
            customer.getAddressList().forEach(address ->
                    address.setStatus(Status.REJECTED.toString()));
        }

        if(null!=customer.getKycDocumentList() && !customer.getKycDocumentList().isEmpty()){
            customer.getKycDocumentList().forEach(kycDocument ->
                    kycDocument.setKycStatus(Status.REJECTED.toString()));
        }

        if(null!=customer.getCustomerBankInfoList() && !customer.getCustomerBankInfoList().isEmpty()){
            customer.getCustomerBankInfoList().forEach(customerBankInfo ->
                    customerBankInfo.setStatus(Status.REJECTED.toString())
            );
        }

        Customer rejectedCustomer = customerRepository.save(customer);
        return mapToResponse(rejectedCustomer);
    }

    @Override
    public CustomerDto updateCustomer(CustomerDto request) throws OmniNGException {
        Customer customer =getUniqueCustomer(tenantId, request.getBranchCode(), request.getCustomerId());
        if(customer.getCustomerStatus().equalsIgnoreCase(CustomerType.APPROVED.toString())) {
            throw new OmniNGException("Customer already approved. so cannot be update Customer Details");
        }

        Long customerId = request.getCustomerId();
        Integer branchCode = request.getBranchCode();
        BeanUtils.copyProperties(request, customer);
        customer.setLastModifiedDate(LocalDateTime.now());

        // Update addresses
        if(request.getAddresses() != null  && !request.getAddresses().isEmpty()) {
                        AtomicInteger addrSrNo = new AtomicInteger(1);
            request.getAddresses().forEach(addressDto -> {
                Address address = new Address();
                AddressKey addressKey = new AddressKey();
                addressKey.setTenantId(tenantId);
                addressKey.setBranchCode(branchCode);
                addressKey.setCustomerId(customerId);
                addressKey.setSrNo(addrSrNo.getAndIncrement());
                address.setAddressKey(addressKey);
                BeanUtils.copyProperties(addressDto, address);
                address.setStatus(Status.PENDING.toString());
                customer.addAddress(address);
            });
        }

        // Update KYC documents
        if(request.getKycDocumentDtoList() != null && !request.getKycDocumentDtoList().isEmpty()) {
            AtomicInteger kycSrNo = new AtomicInteger(1);
            request.getKycDocumentDtoList().forEach(kycDocumentDto -> {
                KycDocument kycDocument = new KycDocument();
                KycDocumentKey kycDocumentKey = new KycDocumentKey();
                kycDocumentKey.setTenantId(tenantId);
                kycDocumentKey.setBranchCode(branchCode);
                kycDocumentKey.setCustomerId(customerId);
                kycDocumentKey.setSrNo(kycSrNo.getAndIncrement());
                kycDocument.setKycDocumentKey(kycDocumentKey);
                BeanUtils.copyProperties(kycDocumentDto, kycDocument);
                kycDocument.setKycStatus(Status.PENDING.toString());
                customer.addKycDocument(kycDocument);
            });
        }

        // Update CustomerBankInfo
        if(request.getCustomerBankInfoDtoList() != null && !request.getCustomerBankInfoDtoList().isEmpty()) {
            List<CustomerBankInfoDto> bankList = request.getCustomerBankInfoDtoList();
            if(bankList.size() > 3) {
                throw new OmniNGException("Customer Cannot be Adding More than 3 Bank Accounts");
            }
            boolean hasPrimary = bankList.stream()
                    .anyMatch(dto -> dto.getPrimaryAccount() != null && dto.getPrimaryAccount() == 1);
            if (!hasPrimary) {
                throw new OmniNGException("At least one primary bank account is required");
            }
            long primaryCount = bankList.stream()
                    .filter(dto -> dto.getPrimaryAccount() != null && dto.getPrimaryAccount() == 1)
                    .count();
            if (primaryCount > 1) {
                throw new OmniNGException("Only one primary bank account is allowed");
            }
            request.getCustomerBankInfoDtoList().forEach(customerBankInfoDto -> {
                CustomerBankInfo customerBankInfo = new CustomerBankInfo();
                CustomerBankInfoKey customerBankInfoKey = new CustomerBankInfoKey();
                customerBankInfoKey.setSrNo(customerBankInfoDto.getSrNo());
                customerBankInfoKey.setTenantId(tenantId);
                customerBankInfoKey.setBranchCode(branchCode);
                customerBankInfoKey.setCustomerId(customerId);
                customerBankInfo.setCustomerBankInfoKey(customerBankInfoKey);
                BeanUtils.copyProperties(customerBankInfoDto, customerBankInfo);
                customerBankInfo.setStatus(Status.PENDING.toString());
                customer.addCustomerBankInfo(customerBankInfo);
            });
        }
        customer.setIsActive(AppConstants.ACTIVE);
        customer.setAuthStatus(AppConstants.AUTH_PENDING);
        customer.setCustomerStatus(CustomerType.PENDING.toString());
        Customer updatedCustomer = customerRepository.save(customer);
        return mapToResponse(updatedCustomer);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerDto getCustomerDetails(Integer branchCode, Long customerId) throws OmniNGException {
        Customer customer = getUniqueCustomer(tenantId, branchCode, customerId);
        return mapToResponse(customer);
    }

    @Override
    public void deleteCustomer(Integer branchCode, Long customerId) throws OmniNGException {
        Customer customer = getUniqueCustomer(tenantId, branchCode, customerId);
        if(customer.getCustomerStatus().equalsIgnoreCase(CustomerType.APPROVED.toString())) {
            throw new OmniNGException("Customer already approved. so cannot be delete Customer");
        }
        customerRepository.delete(customer);
    }

    private CustomerDto mapToResponse(Customer customer) {
        CustomerDto response = new CustomerDto();
        response.setCustomerId(customer.getCustomerKey().getCustomerId());
        response.setBranchCode(customer.getCustomerKey().getBranchCode());
        response.setTenantId(customer.getCustomerKey().getTenantId());
        BeanUtils.copyProperties(customer, response);
        
        if(customer.getAddressList() != null && !customer.getAddressList().isEmpty()) {
            List<AddressDto> addressDtos = customer.getAddressList().stream()
                .map(address -> {
                    AddressDto addressDto = new AddressDto();
                    addressDto.setSrNo(address.getAddressKey().getSrNo());
                    addressDto.setBranchCode(address.getAddressKey().getBranchCode());
                    addressDto.setCustomerId(address.getAddressKey().getCustomerId());
                    BeanUtils.copyProperties(address, addressDto);
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
                        BeanUtils.copyProperties(kycDocument, kycDocumentDto);
                        return kycDocumentDto;
                    })
                    .collect(Collectors.toList());
                response.setKycDocumentDtoList(kycDocumentDtoList);
        }

        if(null!=customer.getCustomerBankInfoList() && !customer.getCustomerBankInfoList().isEmpty()){
            List<CustomerBankInfoDto> customerBankInfoDtoList = customer.getCustomerBankInfoList().stream()
                    .map(bankInfo -> {
                        CustomerBankInfoDto dto = new CustomerBankInfoDto();
                        dto.setSrNo(bankInfo.getCustomerBankInfoKey().getSrNo());
                        dto.setCustomerId(bankInfo.getCustomerBankInfoKey().getCustomerId());
                        dto.setBranchCode(bankInfo.getCustomerBankInfoKey().getBranchCode());
                        BeanUtils.copyProperties(bankInfo, dto);
                        return dto;
                    })
                    .collect(Collectors.toList());
            response.setCustomerBankInfoDtoList(customerBankInfoDtoList);
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