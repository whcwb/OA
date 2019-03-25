package com.ldz.biz.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.ldz.biz.service.CoachValuationService;
import com.ldz.biz.mapper.CoachValuationMapper;
import com.ldz.biz.model.CoachValuation;

@Service
public class CoachValuationServiceImpl extends BaseServiceImpl<CoachValuation, String> implements CoachValuationService {

	@Autowired
	private CoachValuationMapper baseMapper;
	
	@Override
	protected Mapper<CoachValuation> getBaseMapper() {
		return baseMapper;
	}
}