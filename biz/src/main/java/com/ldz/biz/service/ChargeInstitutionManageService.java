package com.ldz.biz.service;

import com.ldz.biz.model.ChargeInstitutionManage;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

import java.util.List;

public interface ChargeInstitutionManageService extends BaseService<ChargeInstitutionManage, String> {

    ApiResponse<String> updateEntity(ChargeInstitutionManage entity);

    void saveBatch(List<ChargeInstitutionManage> list);

    void deleteByChargeCode(String chargeCode);
}