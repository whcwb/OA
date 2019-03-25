package com.ldz.biz.service.impl;

import com.ldz.biz.mapper.CoachValuationMapper;
import com.ldz.biz.mapper.RateDetailMapper;
import com.ldz.biz.model.CoachValuation;
import com.ldz.biz.model.RateDetail;
import com.ldz.biz.service.CoachValuationService;
import com.ldz.biz.service.RateDetailService;
import com.ldz.biz.service.WxOperateLogService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.model.SysYh;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.commonUtil.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class RateDetailServiceImpl extends BaseServiceImpl<RateDetail, String> implements RateDetailService {

	@Autowired
	private RateDetailMapper baseMapper;
	@Autowired
	private WxOperateLogService wxLog;

	@Override
	protected Mapper<RateDetail> getBaseMapper() {
		return baseMapper;
	}

	@Override
	public ApiResponse<String> removeUserRateDetail(String id){
		this.remove(id);
		return ApiResponse.success();
	}
}