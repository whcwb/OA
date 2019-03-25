package com.ldz.wechat.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.wechat.mapper.CoachTraineeRercordMapper;
import com.ldz.wechat.model.CoachTraineeRercord;
import com.ldz.wechat.service.CoachTraineeRercordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class CoachTraineeRercordServiceImpl extends BaseServiceImpl<CoachTraineeRercord, String> implements CoachTraineeRercordService {

	@Autowired
	private CoachTraineeRercordMapper baseMapper;
	
	@Override
	protected Mapper<CoachTraineeRercord> getBaseMapper() {
		return baseMapper;
	}
}