package com.ldz.wechat.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.wechat.mapper.CoachManagementMapper;
import com.ldz.wechat.mapper.CoachValuationMapper;
import com.ldz.wechat.model.CoachValuation;
import com.ldz.wechat.model.TraineeInformation;
import com.ldz.wechat.service.CoachValuationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

@Service
public class CoachValuationServiceImpl extends BaseServiceImpl<CoachValuation, String> implements CoachValuationService {

	@Autowired
	private CoachValuationMapper baseMapper;

	@Autowired
	private CoachManagementMapper coachManagementMapper;
	
	@Override
	protected Mapper<CoachValuation> getBaseMapper() {
		return baseMapper;
	}

	@Override
	public ApiResponse<String> saveEntity(CoachValuation coachValuation) {
		coachValuation.setId(genId());
		coachValuation.setValuateTime(DateUtils.getNowTime());
		this.save(coachValuation);
		coachManagementMapper.updateCoach(coachValuation.getCoachId());
		return ApiResponse.saveSuccess();
	}
}