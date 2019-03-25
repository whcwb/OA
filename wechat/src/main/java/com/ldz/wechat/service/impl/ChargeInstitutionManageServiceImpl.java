package com.ldz.wechat.service.impl;


import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.wechat.mapper.ChargeInstitutionManageMapper;
import com.ldz.wechat.model.ChargeInstitutionManage;
import com.ldz.wechat.service.ChargeInstitutionManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class ChargeInstitutionManageServiceImpl extends BaseServiceImpl<ChargeInstitutionManage, String> implements ChargeInstitutionManageService {

	@Autowired
	private ChargeInstitutionManageMapper baseMapper;
	
	@Override
	protected Mapper<ChargeInstitutionManage> getBaseMapper() {
		return baseMapper;
	}



}