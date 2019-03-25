package com.ldz.wechat.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.wechat.mapper.ElecArchivesManageMapper;
import com.ldz.wechat.model.ElecArchivesManage;
import com.ldz.wechat.service.ElecArchivesManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class ElecArchivesManageServiceImpl extends BaseServiceImpl<ElecArchivesManage, String> implements ElecArchivesManageService {

	@Autowired
	private ElecArchivesManageMapper baseMapper;
	
	@Override
	protected Mapper<ElecArchivesManage> getBaseMapper() {
		return baseMapper;
	}



}