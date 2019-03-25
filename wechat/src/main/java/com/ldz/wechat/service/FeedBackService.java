package com.ldz.wechat.service;

import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.FeedBack;

public interface FeedBackService extends BaseService<FeedBack, String> {
    ApiResponse<String > saveEntity(FeedBack feedBack);
}