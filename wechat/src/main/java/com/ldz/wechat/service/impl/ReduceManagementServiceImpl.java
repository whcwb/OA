package com.ldz.wechat.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.wechat.mapper.ReduceManagementMapper;
import com.ldz.wechat.model.ReduceManagement;
import com.ldz.wechat.service.ReduceManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class ReduceManagementServiceImpl extends BaseServiceImpl<ReduceManagement, String> implements ReduceManagementService {

	@Autowired
	private ReduceManagementMapper baseMapper;


	@Override
	protected Mapper<ReduceManagement> getBaseMapper() {
		return baseMapper;
	}



}