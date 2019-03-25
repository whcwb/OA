package com.ldz.biz.car.service;

import com.ldz.biz.car.model.BizCar;
import com.ldz.biz.car.model.BizCarInsuranceJq;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

import java.util.List;
import java.util.Map;

public interface BizCarInsuranceJqService extends BaseService<BizCarInsuranceJq, java.lang.String> {

    ApiResponse<List<BizCarInsuranceJq>> getCarInsuranceList(String clId);

    ApiResponse<Map<String,String>> queryCarInsuranceSaveType(String clId);

    ApiResponse<String> validCarAndUpdate(BizCar entity);
}