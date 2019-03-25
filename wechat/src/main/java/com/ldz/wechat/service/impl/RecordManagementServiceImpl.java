package com.ldz.wechat.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.wechat.mapper.RecordManagementMapper;
import com.ldz.wechat.model.RecordManagement;
import com.ldz.wechat.service.RecordManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class RecordManagementServiceImpl extends BaseServiceImpl<RecordManagement, String> implements RecordManagementService {

	@Autowired
	private RecordManagementMapper baseMapper;


	@Override
	protected Mapper<RecordManagement> getBaseMapper() {
		return baseMapper;
	}



}

