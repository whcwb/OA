package com.ldz.biz.service;

import com.ldz.biz.model.BizLcWxjl;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

public interface BizLcWxjlService extends BaseService<BizLcWxjl, String> {

    ApiResponse<String> saveEntity(BizLcWxjl entity);
}