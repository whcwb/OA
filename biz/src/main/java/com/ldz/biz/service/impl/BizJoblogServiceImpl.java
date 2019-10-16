package com.ldz.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldz.biz.mapper.BizJoblogMapper;
import com.ldz.biz.model.BizJoblog;
import com.ldz.biz.service.BizJoblogService;
import com.ldz.sys.base.BaseServiceImpl;

import tk.mybatis.mapper.common.Mapper;

@Service
public class BizJoblogServiceImpl extends BaseServiceImpl<BizJoblog, java.lang.Long> implements BizJoblogService {

	@Autowired
	private BizJoblogMapper baseMapper;
	
	@Override
	protected Mapper<BizJoblog> getBaseMapper() {
		return baseMapper;
	}
}