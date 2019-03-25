package com.ldz.biz.car.service.impl;

import com.ldz.biz.car.mapper.BizCarUsageMapper;
import com.ldz.biz.car.model.BizCar;
import com.ldz.biz.car.model.BizCarUsage;
import com.ldz.biz.car.service.BizCarService;
import com.ldz.biz.car.service.BizCarUsageService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.model.SysYh;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class BizCarUsageServiceImpl extends BaseServiceImpl<BizCarUsage, java.lang.String> implements BizCarUsageService {

	@Autowired
	private BizCarUsageMapper baseMapper;

	@Autowired
	private BizCarService carService;

	@Override
	protected Mapper<BizCarUsage> getBaseMapper() {
		return baseMapper;
	}

	/**
	 * 使用人绑定  todo 这个功能以后不需要了
	 * @param entity
	 * clId     车辆ID
	 * syrId   使用人编号，从人事表中获取。非必填字段
	 * syrName   使用人姓名
	 * syrSzd   使用人所在地
	 * syrDn   使用人联系方式
	 * @return
	 */
	@Override
	public ApiResponse<String> validAndSaveUsage(BizCarUsage entity){
		SysYh user=getCurrentUser(true);
		RuntimeCheck.ifFalse(StringUtils.equals(user.getJgdm(),"100"), "您所在的角色无权做产权变更操作信息。");

//		1、非空验证
		RuntimeCheck.ifBlank(entity.getClId(), "请选择需要绑定使用人的车辆。");

		BizCar cl=carService.findById(entity.getClId());
		RuntimeCheck.ifTrue(cl==null, "车辆信息有误，请刷新页后重新尝试。");
//		RuntimeCheck.ifFalse(StringUtils.indexOf(cl.getJgdm(),user.getJgdm())>-1, "该车辆属于："+cl.getJgmc()+" 您无权为它变更使用人");
//
//		RuntimeCheck.ifTrue(StringUtils.equals(cl.getCarPropertyType(),"4"), "该车辆已经售出，不能绑定");
//		RuntimeCheck.ifTrue(StringUtils.equals(cl.getCarPropertyType(),"3"), "该车辆已经报废，不能绑定");
//		RuntimeCheck.ifTrue(StringUtils.equals(cl.getCarPropertyType(),"2"), "该车辆为非学牌车，不能绑定");

		entity.setId(genId());
		entity.setCph(cl.getCph());
		entity.setCjr(user.getZh()+"-"+user.getXm());
		entity.setCjsj(DateUtils.getNowTime());
		entity.setDelType("0");

		cl.setSyrName(entity.getSyrName());//使用人姓名
		cl.setSyrSzd(entity.getSyrSzd());//使用人所在地
		cl.setSyrDn(entity.getSyrDn());//使用人联系方式
		carService.update(cl);


		SimpleCondition condition = new SimpleCondition(BizCarUsage.class);
		condition.eq(BizCarUsage.InnerColumn.clId,entity.getClId());
		BizCarUsage management = new BizCarUsage();
		management.setDelType("1");

		baseMapper.updateByExample(management,condition);

		baseMapper.insertSelective(entity);
		return ApiResponse.success();
	}
}