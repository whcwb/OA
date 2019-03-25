package com.ldz.wechat.service.impl;


import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.wechat.mapper.ChargeManagementMapper;
import com.ldz.wechat.model.ChargeManagement;
import com.ldz.wechat.service.ChargeManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;


@Service
public class ChargeManagementServiceImpl extends BaseServiceImpl<ChargeManagement, String> implements ChargeManagementService {

	@Autowired
	private ChargeManagementMapper baseMapper;
	@Override
	protected Mapper<ChargeManagement> getBaseMapper() {
		return baseMapper;
	}



}