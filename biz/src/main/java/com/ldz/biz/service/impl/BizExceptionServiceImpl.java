package com.ldz.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldz.biz.mapper.BizExceptionMapper;
import com.ldz.biz.model.BizException;
import com.ldz.biz.model.BizExceptionConfig;
import com.ldz.biz.service.BizExceptionConfigService;
import com.ldz.biz.service.BizExceptionService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;

import tk.mybatis.mapper.common.Mapper;

@Service
public class BizExceptionServiceImpl extends BaseServiceImpl<BizException, java.lang.String> implements BizExceptionService {

	@Autowired
	private BizExceptionMapper baseMapper;
	@Autowired
	private BizExceptionConfigService exceptionConfigService;
	
	@Override
	protected Mapper<BizException> getBaseMapper() {
		return baseMapper;
	}

	@Override
	public ApiResponse<List<BizExceptionConfig>> getAllConfig() {
		return ApiResponse.success(exceptionConfigService.findAll());
	}
}