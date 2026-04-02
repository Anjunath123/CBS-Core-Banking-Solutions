package com.canfin.corebanking.customerservice.service;

import com.canfin.corebanking.customerservice.entity.DepositAccount;
import com.canfin.corebanking.customerservice.entity.VoucherMst;

import java.util.List;

public interface VoucherService {

    VoucherMst initForVoucherValues(Integer tenantId, Integer branchCode, DepositAccount depositAccount, String activityCode);

    void saveVoucherListForCentraliseTdAccr(List<VoucherMst> voucherList);
}
