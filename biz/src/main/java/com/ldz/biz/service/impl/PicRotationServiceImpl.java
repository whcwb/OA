package com.ldz.biz.service.impl;

import com.ldz.biz.model.TraineeInformation;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.commonUtil.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.ldz.biz.service.PicRotationService;
import com.ldz.biz.mapper.PicRotationMapper;
import com.ldz.biz.model.PicRotation;

@Service
public class PicRotationServiceImpl extends BaseServiceImpl<PicRotation, String> implements PicRotationService {

	@Autowired
	private PicRotationMapper baseMapper;
	
	@Override
	protected Mapper<PicRotation> getBaseMapper() {
		return baseMapper;
	}
	@Override
	public boolean fillPagerCondition(LimitedCondition condition){
		condition.setOrderByClause(" id desc");
		return true;
	}

	@Override
	public ApiResponse<String> validAndSave(PicRotation entity) {
		if(entity!=null){
			entity.setId(genId());
			entity.setCjsj(DateUtils.getNowTime());
			entity.setCjr(getOperateUser());
			baseMapper.insertSelective(entity);
			return ApiResponse.success();
		}else{
			return ApiResponse.fail();
		}
	}
}