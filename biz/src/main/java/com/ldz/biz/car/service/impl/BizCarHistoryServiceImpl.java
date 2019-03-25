package com.ldz.biz.car.service.impl;

import com.ldz.biz.car.mapper.BizCarHistoryMapper;
import com.ldz.biz.car.model.BizCarHistory;
import com.ldz.biz.car.service.BizCarHistoryService;
import com.ldz.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class BizCarHistoryServiceImpl extends BaseServiceImpl<BizCarHistory, String> implements BizCarHistoryService {

	@Autowired
	private BizCarHistoryMapper baseMapper;
	
	@Override
	protected Mapper<BizCarHistory> getBaseMapper() {
		return baseMapper;
	}
}