package com.ldz.biz.car.service;

import com.ldz.biz.car.model.BizCar;
import com.ldz.biz.car.model.BizCarGas;
import com.ldz.biz.car.model.BizCarWarn;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

public interface BizCarWarnService extends BaseService<BizCarWarn, String> {

    ApiResponse<String> batchAnnualSave(BizCar entity,BizCar findBy);

    ApiResponse<String> updNcWarn(BizCar entity);

    ApiResponse<String> updQzbfrqWarn(BizCar entity);

    ApiResponse<String> updYgNcWarn(BizCar entity);

    ApiResponse<String> updGxNcWarn(BizCar entity);
    ApiResponse<String> updGxNcWarn(BizCar entity,BizCarGas carGas);

    ApiResponse<String> updBxNcWarn(BizCar entity);

    ApiResponse<String> updCarWarnDate(String clId);

    ApiResponse<String> jobSaveWarn(String data);
}