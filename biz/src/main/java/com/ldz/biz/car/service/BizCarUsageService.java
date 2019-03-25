package com.ldz.biz.car.service;

import com.ldz.biz.car.model.BizCarUsage;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

public interface BizCarUsageService extends BaseService<BizCarUsage, java.lang.String> {

    ApiResponse<String> validAndSaveUsage(BizCarUsage entity);
}