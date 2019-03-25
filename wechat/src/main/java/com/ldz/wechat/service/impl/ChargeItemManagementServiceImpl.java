package com.ldz.wechat.service.impl;


import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.wechat.mapper.ChargeItemManagementMapper;
import com.ldz.wechat.model.ChargeItemManagement;
import com.ldz.wechat.service.ChargeItemManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class ChargeItemManagementServiceImpl extends BaseServiceImpl<ChargeItemManagement, String> implements ChargeItemManagementService {

	@Autowired
	private ChargeItemManagementMapper baseMapper;

	@Override
	protected Mapper<ChargeItemManagement> getBaseMapper() {
		return baseMapper;
	}



}