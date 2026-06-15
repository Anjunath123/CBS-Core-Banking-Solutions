package com.canfin.corebanking.customerservice.service.impl;

import com.canfin.corebanking.customerservice.constants.AppConstants;
import com.canfin.corebanking.customerservice.entity.D009040Key;
import com.canfin.corebanking.customerservice.entity.DepositAccount;
import com.canfin.corebanking.customerservice.entity.VoucherMst;
import com.canfin.corebanking.customerservice.repository.VoucherMstRepository;
import com.canfin.corebanking.customerservice.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class VoucherServiceImpl implements VoucherService {

    private static final Logger logger = LoggerFactory.getLogger(VoucherServiceImpl.class);

    @Autowired
    private VoucherMstRepository voucherMstRepository;

    @Override
    public VoucherMst initForVoucherValues(Integer tenantId, Integer branchCode, DepositAccount depositAccount, String activityCode) {
        VoucherMst voucher = new VoucherMst();

        D009040Key key = new D009040Key();
        key.setTenantId(tenantId);
        key.setBranchCode(branchCode);
        key.setEntryDate(new Date());
        key.setBatchCode(AppConstants.VOUCHER_BATCH_CODE_TD);
        key.setSetNo(0);
        voucher.setD009040Key(key);

        voucher.setActivityCode(activityCode);
        voucher.setProductCode(depositAccount.getProduct());
        voucher.setSchemeCode(depositAccount.getScheme());
        voucher.setTrnCurrCode(depositAccount.getCurrencyCode());
        voucher.setBookType("NR");
        voucher.setValueDate(new Date());
        voucher.setPostDate(new Date());
        voucher.setFundEffDate(new Date());
        voucher.setPayeeName(depositAccount.getAccountName());
        voucher.setLcyConvRate(1.0d);

        voucher.setEntryFlag("Y");
        voucher.setPostFlag("Y");
        voucher.setAuthFlag("Y");
        voucher.setFundEffFlag("Y");
        voucher.setCanceledFlag("N");
        voucher.setShTotFlag("Y");
        voucher.setShClrFlag("Y");
        voucher.setAcTotFlag("Y");
        voucher.setAcClrFlag("Y");
        voucher.setAuthStatus(AppConstants.AUTH_APPROVED);
        voucher.setSysGenVcr(1);

        voucher.setCreatedBy("SYSTEM");
        voucher.setCreateAt(LocalDateTime.now());
        voucher.setCreatedTime(LocalDateTime.now());

        return voucher;
    }

    @Override
    @Transactional
    public void saveVoucherListForCentraliseTdAccr(List<VoucherMst> voucherList) {
        if (voucherList == null || voucherList.isEmpty()) {
            return;
        }

        D009040Key firstKey = voucherList.get(0).getD009040Key();
        Integer maxSetNo = voucherMstRepository.findMaxSetNo(
                firstKey.getTenantId(), firstKey.getBranchCode(),
                firstKey.getEntryDate(), firstKey.getBatchCode());

        int setNo = (maxSetNo != null ? maxSetNo : 0);

        for (VoucherMst voucher : voucherList) {
            setNo++;
            voucher.getD009040Key().setSetNo(setNo);
            voucher.setMainScrollNo(setNo);
            logger.info("Saving voucher setNo={} drCr={} vcrAcctId={} mainAcctId={} amount={}",
                    setNo, voucher.getDrCr(), voucher.getVcrAcctId(),
                    voucher.getMainAcctId(), voucher.getFcyTrnAmt());
        }

        voucherMstRepository.saveAll(voucherList);
        logger.info("Saved {} voucher entries for batch {}", voucherList.size(), firstKey.getBatchCode());
    }
}
