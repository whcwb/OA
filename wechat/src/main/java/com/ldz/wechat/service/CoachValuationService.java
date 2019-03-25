package com.ldz.wechat.service;

import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.CoachValuation;

public interface CoachValuationService extends BaseService<CoachValuation, String> {
    ApiResponse<String> saveEntity(CoachValuation coachValuation);

}