package com.ldz.biz.service;

import com.ldz.biz.model.CoachValuation;
import com.ldz.biz.model.RateDetail;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

public interface RateDetailService extends BaseService<RateDetail, String> {

    ApiResponse<String> removeUserRateDetail(String id);
}