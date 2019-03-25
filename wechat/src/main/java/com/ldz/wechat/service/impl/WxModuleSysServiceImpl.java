package com.ldz.wechat.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.ldz.wechat.service.WxModuleSysService;
import com.ldz.wechat.mapper.WxModuleSysMapper;
import com.ldz.wechat.model.WxModuleSys;

@Service
public class WxModuleSysServiceImpl extends BaseServiceImpl<WxModuleSys, String> implements WxModuleSysService {

	@Autowired
	private WxModuleSysMapper baseMapper;
	
	@Override
	protected Mapper<WxModuleSys> getBaseMapper() {
		return baseMapper;
	}
}