package com.ldz.biz.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
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
	
	@Override
	public ApiResponse<String> saveException(BizException exception) {
		if (StringUtils.isBlank(exception.getXm())){
			return ApiResponse.fail("学员姓名不能为空");
		}
		if (StringUtils.isBlank(exception.getSfzmhm())){
			return ApiResponse.fail("证件号码不能为空");
		}
		if (StringUtils.isBlank(exception.getCode())){
			return ApiResponse.fail("异常码不能为空");
		}
		
		exception.setId(String.valueOf(idGenerator.nextId()));
		exception.setCjsj(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
		exception.setBz(exceptionConfigService.getExpNameByCode(exception.getCode()));
		exception.setZt("00");
		save(exception);
		
		return ApiResponse.success();
	}
}