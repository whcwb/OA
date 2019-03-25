package com.ldz.biz.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.ldz.biz.service.GroupBuyRecordService;
import com.ldz.biz.mapper.GroupBuyRecordMapper;
import com.ldz.biz.model.GroupBuyRecord;

@Service
public class GroupBuyRecordServiceImpl extends BaseServiceImpl<GroupBuyRecord, String> implements GroupBuyRecordService {

	@Autowired
	private GroupBuyRecordMapper baseMapper;
	
	@Override
	protected Mapper<GroupBuyRecord> getBaseMapper() {
		return baseMapper;
	}
}