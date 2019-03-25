package com.ldz.biz.service;

import com.ldz.biz.model.ChargeItemManagement;
import com.ldz.biz.model.KeyValue;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

import java.util.List;
import java.util.Map;

public interface ChargeItemManagementService extends BaseService<ChargeItemManagement, String> {

    String findChargeName(String jgdm, String chargeCode,String carType);

    ApiResponse<String> updateEntity(ChargeItemManagement entity);

    ApiResponse<String> removeForId(String pkid);
    /**
     * 获取用户费用信息
     * @param classType        班型
     * @param chargeType		费用类型(0000 学费)
     * @param jgdm				报名点(机构代码)
     * @param carType			报名车型
     * @return
     */
    ChargeItemManagement getUserCharge(String classType, String chargeType, String jgdm, String carType);

    ApiResponse<List<ChargeItemManagement>> getChargeName(String type);

    ApiResponse<Map<String, List<KeyValue>>> getJgBx(String jgdm);
}