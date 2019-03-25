package com.ldz.biz.car.service.impl;

import com.ldz.biz.car.mapper.BizCarSyrMapper;
import com.ldz.biz.car.model.BizCarSyr;
import com.ldz.biz.car.service.BizCarSyrService;
import com.ldz.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class BizCarSyrServiceImpl extends BaseServiceImpl<BizCarSyr, String> implements BizCarSyrService {

	@Autowired
	private BizCarSyrMapper baseMapper;
	
	@Override
	protected Mapper<BizCarSyr> getBaseMapper() {
		return baseMapper;
	}
}