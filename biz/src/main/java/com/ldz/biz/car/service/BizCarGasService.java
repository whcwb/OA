package com.ldz.biz.car.service;

import com.ldz.biz.car.model.BizCarGas;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

import java.util.List;

public interface BizCarGasService extends BaseService<BizCarGas, java.lang.String> {

    ApiResponse<List<BizCarGas>> getCarGasList(String clId);
}