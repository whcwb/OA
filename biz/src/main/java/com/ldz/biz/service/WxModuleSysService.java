package com.ldz.biz.service;

import com.ldz.biz.model.WxModuleSys;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

import java.util.List;

public interface WxModuleSysService extends BaseService<WxModuleSys, String> {

    ApiResponse<String> insertList(WxModuleSys entity);


    ApiResponse<List<WxModuleSys>> getUserModuleList(String userId);
}