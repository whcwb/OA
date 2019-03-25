package com.ldz.wechat.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.wechat.mapper.GroupBuyRecordMapper;
import com.ldz.wechat.model.GroupBuyRecord;
import com.ldz.wechat.service.GroupBuyRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class GroupBuyRecordServiceImpl extends BaseServiceImpl<GroupBuyRecord, String> implements GroupBuyRecordService {

	@Autowired
	private GroupBuyRecordMapper baseMapper;
	
	@Override
	protected Mapper<GroupBuyRecord> getBaseMapper() {
		return baseMapper;
	}
}