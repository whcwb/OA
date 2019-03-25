package com.ldz.biz.service.impl;

import com.ldz.biz.mapper.BizCkMapper;
import com.ldz.biz.model.BizCk;
import com.ldz.biz.service.BizCkService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class BizCkServiceImpl extends BaseServiceImpl<BizCk, String> implements BizCkService {

	@Autowired
	private BizCkMapper baseMapper;
	
	@Override
	protected Mapper<BizCk> getBaseMapper() {
		return baseMapper;
	}
	@Override
	public boolean fillPagerCondition(LimitedCondition condition){
		return true;
	}
}