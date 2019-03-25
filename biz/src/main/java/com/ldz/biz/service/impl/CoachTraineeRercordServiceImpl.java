package com.ldz.biz.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.ldz.biz.service.CoachTraineeRercordService;
import com.ldz.biz.mapper.CoachTraineeRercordMapper;
import com.ldz.biz.model.CoachTraineeRercord;

@Service
public class CoachTraineeRercordServiceImpl extends BaseServiceImpl<CoachTraineeRercord, String> implements CoachTraineeRercordService {

	@Autowired
	private CoachTraineeRercordMapper baseMapper;
	
	@Override
	protected Mapper<CoachTraineeRercord> getBaseMapper() {
		return baseMapper;
	}
}