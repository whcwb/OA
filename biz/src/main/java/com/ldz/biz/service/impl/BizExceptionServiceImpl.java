package com.ldz.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldz.biz.mapper.BizExceptionMapper;
import com.ldz.biz.model.BizException;
import com.ldz.biz.service.BizExceptionService;
import com.ldz.sys.base.BaseServiceImpl;

import tk.mybatis.mapper.common.Mapper;

@Service
public class BizExceptionServiceImpl extends BaseServiceImpl<BizException, java.lang.String> implements BizExceptionService {

	@Autowired
	private BizExceptionMapper baseMapper;
	
	@Override
	protected Mapper<BizException> getBaseMapper() {
		return baseMapper;
	}
}