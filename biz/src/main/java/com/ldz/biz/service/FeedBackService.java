package com.ldz.biz.service;

import com.ldz.biz.model.FeedBack;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

public interface FeedBackService extends BaseService<FeedBack, String> {

    ApiResponse<String> updateCallBack(FeedBack obj);
}