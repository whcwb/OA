package com.ldz.biz.car.service.impl;

import com.ldz.biz.car.mapper.BizCarBaMapper;
import com.ldz.biz.car.model.BizCarBa;
import com.ldz.biz.car.service.BizCarBaService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.exception.RuntimeCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Service
public class BizCarBaServiceImpl extends BaseServiceImpl<BizCarBa, java.lang.String> implements BizCarBaService {

	@Autowired
	private BizCarBaMapper baseMapper;
	
	@Override
	protected Mapper<BizCarBa> getBaseMapper() {
		return baseMapper;
	}


	@Override
	public ApiResponse<List<BizCarBa>> getCarBaList(String clId){
		RuntimeCheck.ifBlank(clId, "请选择车辆的车辆。");
		SimpleCondition condition = new SimpleCondition(BizCarBa.class);
		condition.eq(BizCarBa.InnerColumn.clId,clId);
		condition.setOrderByClause(BizCarBa.InnerColumn.id.desc());
		List<BizCarBa> list=this.findByCondition(condition);
		return ApiResponse.success(list);
	}
}