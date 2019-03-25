package com.ldz.biz.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.ldz.biz.service.TrainingRecordService;
import com.ldz.biz.mapper.TrainingRecordMapper;
import com.ldz.biz.model.TrainingRecord;

@Service
public class TrainingRecordServiceImpl extends BaseServiceImpl<TrainingRecord, String> implements TrainingRecordService {

	@Autowired
	private TrainingRecordMapper baseMapper;
	
	@Override
	protected Mapper<TrainingRecord> getBaseMapper() {
		return baseMapper;
	}
}