package com.ldz.wechat.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.ldz.wechat.service.WxModuleService;
import com.ldz.wechat.mapper.WxModuleMapper;
import com.ldz.wechat.model.WxModule;

@Service
public class WxModuleServiceImpl extends BaseServiceImpl<WxModule, String> implements WxModuleService {

	@Autowired
	private WxModuleMapper baseMapper;
	
	@Override
	protected Mapper<WxModule> getBaseMapper() {
		return baseMapper;
	}
}