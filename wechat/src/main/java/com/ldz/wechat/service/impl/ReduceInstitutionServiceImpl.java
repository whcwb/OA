package com.ldz.wechat.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.wechat.mapper.ReduceInstitutionMapper;
import com.ldz.wechat.model.ReduceInstitution;
import com.ldz.wechat.service.ReduceInstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class ReduceInstitutionServiceImpl extends BaseServiceImpl<ReduceInstitution, String> implements ReduceInstitutionService {

	@Autowired
	private ReduceInstitutionMapper baseMapper;
	
	@Override
	protected Mapper<ReduceInstitution> getBaseMapper() {
		return baseMapper;
	}




}