package com.ldz.biz.car.service;

import com.ldz.biz.car.model.BizCarBa;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

import java.util.List;

public interface BizCarBaService extends BaseService<BizCarBa, java.lang.String> {

    ApiResponse<List<BizCarBa>> getCarBaList(String clId);

}