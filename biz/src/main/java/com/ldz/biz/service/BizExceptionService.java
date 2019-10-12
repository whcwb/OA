package com.ldz.biz.service;

import java.util.List;

import com.ldz.biz.model.BizException;
import com.ldz.biz.model.BizExceptionConfig;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

public interface BizExceptionService extends BaseService<BizException, java.lang.String> {

	public ApiResponse<List<BizExceptionConfig>> getAllConfig();
}