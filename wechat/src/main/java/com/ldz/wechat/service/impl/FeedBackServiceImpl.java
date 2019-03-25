package com.ldz.wechat.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.commonUtil.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.ldz.wechat.service.FeedBackService;
import com.ldz.wechat.mapper.FeedBackMapper;
import com.ldz.wechat.model.FeedBack;

@Service
public class FeedBackServiceImpl extends BaseServiceImpl<FeedBack, String> implements FeedBackService {

	@Autowired
	private FeedBackMapper baseMapper;
	
	@Override
	protected Mapper<FeedBack> getBaseMapper() {
		return baseMapper;
	}

	@Override
	public ApiResponse<String> saveEntity(FeedBack feedBack) {
		feedBack.setId(genId());
		feedBack.setCreateTime(DateUtils.getNowTime());
		this.save(feedBack);
		return ApiResponse.saveSuccess();
	}
}