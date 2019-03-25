package com.ldz.wechat.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.wechat.mapper.TraineeTestInfoMapper;
import com.ldz.wechat.model.TraineeInformation;
import com.ldz.wechat.model.TraineeTestInfo;
import com.ldz.wechat.model.TrainingRecord;
import com.ldz.wechat.service.TraineeTestInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Service
public class TraineeTestInfoServiceImpl extends BaseServiceImpl<TraineeTestInfo, String> implements TraineeTestInfoService {

	@Autowired
	private TraineeTestInfoMapper baseMapper;


	@Override
	protected Mapper<TraineeTestInfo> getBaseMapper() {
		return baseMapper;
	}


	@Override
	public ApiResponse<List<TraineeTestInfo>> queryList(TraineeTestInfo info) {
		SimpleCondition condition = new SimpleCondition(TraineeTestInfo.class);
		condition.eq(TraineeTestInfo.InnerColumn.traineeId, info.getTraineeId());
		condition.setOrderByClause(TraineeTestInfo.InnerColumn.testTime.desc());
		List<TraineeTestInfo> list = this.findByCondition(condition);
		return ApiResponse.success(list);
	}
}