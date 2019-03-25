package com.ldz.biz.car.service.impl;

import com.ldz.biz.car.mapper.BizCarGasMapper;
import com.ldz.biz.car.model.BizCar;
import com.ldz.biz.car.model.BizCarGas;
import com.ldz.biz.car.service.BizCarGasService;
import com.ldz.biz.car.service.BizCarService;
import com.ldz.biz.car.service.BizCarWarnService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

@Service
public class BizCarGasServiceImpl extends BaseServiceImpl<BizCarGas, java.lang.String> implements BizCarGasService {

	@Autowired
	private BizCarGasMapper baseMapper;

	@Autowired
	private BizCarService carService;

	@Autowired
	private BizCarWarnService carWarnService;
	
	@Override
	protected Mapper<BizCarGas> getBaseMapper() {
		return baseMapper;
	}


	@Override
	public  ApiResponse<List<BizCarGas>> getCarGasList(String clId){
		RuntimeCheck.ifBlank(clId, "请选择车辆的车辆。");
		SimpleCondition condition = new SimpleCondition(BizCarGas.class);
		condition.eq(BizCarGas.InnerColumn.clId,clId);
		condition.setOrderByClause(BizCarGas.InnerColumn.id.desc());
		List<BizCarGas> list=this.findByCondition(condition);
		return ApiResponse.success(list);
	}

	@Override
	public ApiResponse<String> validAndSave(BizCarGas entity){
		if(StringUtils.isBlank(entity.getClId())&&StringUtils.isNotBlank(entity.getId())){
			entity.setClId(entity.getId());
		}
		RuntimeCheck.ifBlank(entity.getClId(), "请选择正确的车辆。");


		String gxbfq=entity.getGasData();
		if (StringUtils.isBlank(gxbfq)){
			return ApiResponse.fail("改气时间日期不能为空");
		}

		Date date=null;
		try {
			date= DateUtils.getDate(gxbfq,"yyyy-MM-dd");
		}catch (Exception e){
			return ApiResponse.fail("请输入正确的改气时间日期 日期格式为：yyyy-MM-dd");
		}

		String xcncsj=entity.getXcncsj();
		Date endDate=null;
		try {
			endDate= DateUtils.getDate(xcncsj,"yyyy-MM-dd");
		}catch (Exception e){
			return ApiResponse.fail("请输入正确的 下次年审时间 日期格式为：yyyy-MM-dd");
		}

		BizCar cl=carService.findById(entity.getClId());
		RuntimeCheck.ifNull(cl, "请选择车辆的车辆。");

		SimpleCondition simpleCondition = new SimpleCondition(BizCarGas.class);
		simpleCondition.lte(BizCarGas.InnerColumn.clId, entity.getClId());
		Integer integer = countByCondition(simpleCondition);
		if(integer!=null&&integer>0){
			//修改
			baseMapper.updateByPrimaryKey(entity);
		}else{
			//新增
			entity.setId(genId());
			baseMapper.insert(entity);
		}

		BizCar warn=new BizCar();
		warn.setId(entity.getClId());
		warn.setGxCdrq(gxbfq);//改气初登日期
		warn.setGxGasGgzyxq(entity.getGxGasGgzyxq());//改气合格证有效期
		warn.setXcncsj(xcncsj);//下次年审时间
		carWarnService.updGxNcWarn(warn);

		BizCar updCl=new BizCar();
		updCl.setId(entity.getClId());
		String gxType="0";
		if(StringUtils.isNotBlank(gxbfq)){
			gxType="1";
		}
		updCl.setGxCdrq(gxbfq);//改气初登日期
		updCl.setGxType(gxType);//是否改气1、已改气0未改气
		updCl.setGxGasGgzyxq(entity.getGxGasGgzyxq());//改气合格证有效期
		updCl.setGxGasDd(entity.getGasDd());//改气地点
		updCl.setGxLxr(entity.getLxr());//改气联系人
		updCl.setGxLxDn(entity.getLxDn());//改气联系人电话
		updCl.setGxGasBz(entity.getBz());//改气备注
		carService.update(updCl);
		return ApiResponse.success();
	}


}