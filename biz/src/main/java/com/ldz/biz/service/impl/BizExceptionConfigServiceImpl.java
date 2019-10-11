package com.ldz.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldz.biz.mapper.BizExceptionConfigMapper;
import com.ldz.biz.model.BizExceptionConfig;
import com.ldz.biz.service.BizExceptionConfigService;
import com.ldz.sys.base.BaseServiceImpl;

import tk.mybatis.mapper.common.Mapper;

@Service
public class BizExceptionConfigServiceImpl extends BaseServiceImpl<BizExceptionConfig, java.lang.Long> implements BizExceptionConfigService {

	@Autowired
	private BizExceptionConfigMapper baseMapper;
	
	@Override
	protected Mapper<BizExceptionConfig> getBaseMapper() {
		return baseMapper;
	}
}