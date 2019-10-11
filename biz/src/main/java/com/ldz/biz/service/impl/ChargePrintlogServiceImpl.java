package com.ldz.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldz.biz.mapper.ChargePrintlogMapper;
import com.ldz.biz.model.ChargePrintlog;
import com.ldz.biz.service.ChargePrintlogService;
import com.ldz.sys.base.BaseServiceImpl;

import tk.mybatis.mapper.common.Mapper;

@Service
public class ChargePrintlogServiceImpl extends BaseServiceImpl<ChargePrintlog, java.lang.Long> implements ChargePrintlogService {

	@Autowired
	private ChargePrintlogMapper baseMapper;
	
	@Override
	protected Mapper<ChargePrintlog> getBaseMapper() {
		return baseMapper;
	}
}