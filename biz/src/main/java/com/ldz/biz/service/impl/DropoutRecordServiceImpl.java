package com.ldz.biz.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.ldz.biz.service.DropoutRecordService;
import com.ldz.biz.mapper.DropoutRecordMapper;
import com.ldz.biz.model.DropoutRecord;

@Service
public class DropoutRecordServiceImpl extends BaseServiceImpl<DropoutRecord, String> implements DropoutRecordService {

	@Autowired
	private DropoutRecordMapper baseMapper;
	
	@Override
	protected Mapper<DropoutRecord> getBaseMapper() {
		return baseMapper;
	}
}