package com.ldz.biz.car.service.impl;

import com.ldz.biz.car.mapper.BizCarMapper;
import com.ldz.biz.car.model.BizCar;
import com.ldz.biz.car.service.BizCarMainService;
import com.ldz.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * 车辆管理公共业务 JOB 消息下发等
 */
@Service
public class BizCarMainServiceImpl extends BaseServiceImpl<BizCar, String> implements BizCarMainService {
	@Autowired
	private BizCarMapper baseMapper;
	
	@Override
	protected Mapper<BizCar> getBaseMapper() {
		return baseMapper;
	}


}