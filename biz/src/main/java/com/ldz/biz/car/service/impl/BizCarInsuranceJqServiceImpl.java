package com.ldz.biz.car.service.impl;

import com.ldz.biz.car.mapper.BizCarInsuranceJqMapper;
import com.ldz.biz.car.model.BizCar;
import com.ldz.biz.car.model.BizCarInsuranceJq;
import com.ldz.biz.car.model.BizCarWarn;
import com.ldz.biz.car.service.BizCarInsuranceJqService;
import com.ldz.biz.car.service.BizCarService;
import com.ldz.biz.car.service.BizCarWarnService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.*;

@Service
public class BizCarInsuranceJqServiceImpl extends BaseServiceImpl<BizCarInsuranceJq, java.lang.String> implements BizCarInsuranceJqService {

	@Autowired
	private BizCarInsuranceJqMapper baseMapper;
	@Autowired
	private BizCarService carService;

	@Autowired
	private BizCarWarnService warnService;

	@Override
	protected Mapper<BizCarInsuranceJq> getBaseMapper() {
		return baseMapper;
	}

	@Override
	public ApiResponse<String> validAndSave(BizCarInsuranceJq exam){
		RuntimeCheck.ifBlank(exam.getClId(),"请选择车辆");
		RuntimeCheck.ifBlank(exam.getBxrq(),"请填写保险起保日期");
		RuntimeCheck.ifBlank(exam.getBxz(),"请填写保险终保日期");
		RuntimeCheck.ifBlank(exam.getBxdh(),"请填写保险联系人电话");
		RuntimeCheck.ifBlank(exam.getBdCount(),"请填写保单数量");

		BizCar cl=carService.findById(exam.getClId());
		RuntimeCheck.ifNull(cl, "该车辆不存在，请重新选择");
		RuntimeCheck.ifTrue(StringUtils.equals(cl.getCarPropertyType(),"4"), "该车辆已经售出，不能续保");


		Date startTime=null;
		Date endTime=null;
		try {
			startTime=DateUtils.getDate(exam.getBxrq(),"yyyy-MM-dd");
		}catch (Exception e){
			return ApiResponse.fail("请输入正确的保险起保日期 日期格式为：yyyy-MM-dd");
		}
		try {
			endTime=DateUtils.getDate(exam.getBxz(),"yyyy-MM-dd");
		}catch (Exception e){
			return ApiResponse.fail("请输入正确的保险终保日期 日期格式为：yyyy-MM-dd");
		}

		String warnId=exam.getWarnId();//告警ID
		if(StringUtils.isEmpty(warnId)){
			BizCar warnCl=new BizCar();
			warnCl.setId(exam.getClId());
			warnCl.setBxCdrq(exam.getBxrq());
			warnCl.setXcncsj(exam.getBxz());
			ApiResponse<String> warnAdd=warnService.updBxNcWarn(warnCl);
			if(!warnAdd.isSuccess()){
				return warnAdd;
			}
		}
		exam.setCph(cl.getCph());
		exam.setId(genId());
		baseMapper.insertSelective(exam);

		if(StringUtils.isEmpty(warnId)) {
			//更新状态表
			BizCarWarn updWarn = new BizCarWarn();
			updWarn.setId(warnId);
			updWarn.setWarnDispose("1");
			updWarn.setDisposeCode(exam.getId());
			updWarn.setDaFile(exam.getPropertyFileurl());
			warnService.update(updWarn);
		}
		//更新车辆主表
		BizCar updCar=new BizCar();
		updCar.setId(exam.getClId());
		updCar.setWarnBxDate(exam.getBxz());//保险下一次过期时间
		updCar.setBxBxrq(exam.getBxrq());//保险日期
		updCar.setBxBxz(exam.getBxz());//保险至
		updCar.setBxBxdh(exam.getBxdh());//保险电话
		updCar.setBxBdzbbh(exam.getBdzbbh());//保单正本编号
		updCar.setBxBdfbbh(exam.getBdfbbh());//保单副本编号
		updCar.setBxBdWz(exam.getBdWz());//保单位置
		updCar.setBxBz(exam.getBz());//备注
		updCar.setBxDaFile(exam.getPropertyFileurl());//保单电子档案 路径
		updCar.setBxlxr(exam.getBxlxr());//保单联系人
		updCar.setBxTbgs(exam.getTbgs());//保单投保公司
		updCar.setBxBdCount(exam.getBdCount());//保单数量
        updCar.setBxJbr(exam.getJbr());
        updCar.setBxJbrDn(exam.getJbrDn());
		carService.update(updCar);

		//更新告警信息表
		warnService.updCarWarnDate(exam.getClId());

		return ApiResponse.success();
	}

	/**
	 * 获取车辆保险信息列表
	 *@param  clId	车辆ID	必填
	 * @return
	 */
	@Override
	public ApiResponse<List<BizCarInsuranceJq>> getCarInsuranceList(String clId){

		RuntimeCheck.ifBlank(clId, "请选择车辆的车辆。");
		SimpleCondition condition = new SimpleCondition(BizCarInsuranceJq.class);
		condition.eq(BizCarInsuranceJq.InnerColumn.clId,clId);
		condition.setOrderByClause(BizCarInsuranceJq.InnerColumn.id.desc());
		List<BizCarInsuranceJq> list=this.findByCondition(condition);
		return ApiResponse.success(list);
	}

	/**
	 * 按车辆ID查询该车的保险列表
	 * 新增前，通过车辆ID查询该车年审的状态
	 * @param clId
	 * @return
	 */
	@Override
	public ApiResponse<Map<String,String>> queryCarInsuranceSaveType(String clId){
		ApiResponse<Map<String,String>> res = new ApiResponse<>();
		RuntimeCheck.ifBlank(clId, "请选择车辆的车辆。");
		Map<String,String> retMap=new HashMap<>();

		BizCar cl=carService.findById(clId);
		RuntimeCheck.ifTrue(StringUtils.equals(cl.getCarPropertyType(),"4"), "该车辆已经售出，不能变更产权");
		RuntimeCheck.ifTrue(StringUtils.equals(cl.getCarPropertyType(),"3"), "该车辆已经报废，不能变更产权");

		Date endDate=null;
		try {
			String qzbfq=cl.getQzbfq();
			if (org.apache.commons.lang.StringUtils.isNotBlank(qzbfq)){
				endDate= DateUtils.getDate(qzbfq,"yyyy-MM-dd");
			}
		}catch (Exception e){

		}
		RuntimeCheck.ifTrue(endDate!=null&&endDate.compareTo(new Date())<0 , "该车辆强制报废日期为:"+cl.getQzbfq()+"，该车不需要变更保险。");
		String bxCdrq=cl.getBxCdrq();//保险初登日期

		String bxrq="";//保险开始日期
		String bxz="";//保险结束日期
		String bdCount="";//保单数量
		String warnId="";//告警ID
		if(StringUtils.isNotEmpty(bxCdrq)){//
			SimpleCondition warnCondition = new SimpleCondition(BizCarWarn.class);
			warnCondition.eq(BizCarWarn.InnerColumn.warnType,"4");
			warnCondition.eq(BizCarWarn.InnerColumn.clId,clId);
			warnCondition.eq(BizCarWarn.InnerColumn.warnDispose,"0");
//			warnCondition.lte(BizCarWarn.InnerColumn.warnDate, DateUtils.getToday());

			warnCondition.setOrderByClause(BizCarWarn.InnerColumn.expiryDate.asc());
			List<BizCarWarn> bxWarn = warnService.findByCondition(warnCondition);
			if(bxWarn!=null&&bxWarn.size()>0){
				bxrq=bxWarn.get(0).getExpiryDate();
				warnId=bxWarn.get(0).getId();
			}else{
//				bxrq=DateUtils.getToday();
			}
		}else{//
			bxrq=DateUtils.getToday();
		}
		Date startDate=null;
		try {
			startDate=DateUtils.getDate(bxrq,"yyyy-MM-dd");
		}catch (Exception e){
			e.printStackTrace();
			res.setCode(500);
			res.setMessage("获取保单开始时间失败,请核实该车辆保单是否已全部审核通过。");
			return res;
		}
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(startDate);
		calendar.add(Calendar.YEAR, 1);// 把日期往后增加指定月份.整数往后推,负数往前移动
		bxz=DateUtils.getDateStr(calendar.getTime(), "yyyy-MM-dd");
		retMap.put("bxrq",bxrq);//保险开始日期
		retMap.put("bxz",bxz);//保险结束日期

		SimpleCondition warnCondition = new SimpleCondition(BizCarWarn.class);
		warnCondition.eq(BizCarWarn.InnerColumn.warnType,"1");
		warnCondition.eq(BizCarWarn.InnerColumn.clId,clId);
		warnCondition.lte(BizCarWarn.InnerColumn.expiryDate, bxz);
		warnCondition.gte(BizCarWarn.InnerColumn.expiryDate, bxrq);

		List<BizCarWarn> nsWarn = warnService.findByCondition(warnCondition);
		long nsCount=0;
		if(nsWarn!=null){
			nsCount=nsWarn.size();
		}
		bdCount=nsCount+"";
		retMap.put("bdCount",0+"");//保单数量
		retMap.put("warnId",warnId);//告警ID
		res.setResult(retMap);
		return res;
	}

	/**
	 * 保单修改，仅修改主表字段
	 * @param entity
	 * @return
	 */
	@Override
	public ApiResponse<String> validCarAndUpdate(BizCar entity){
		RuntimeCheck.ifBlank(entity.getId(), "请选择车辆的车辆。");

		BizCar cl=carService.findById(entity.getId());
		String bxCdrq=cl.getBxCdrq();
		RuntimeCheck.ifBlank(bxCdrq, "该保险还没有续保，请在继保后才能编辑保险信息。");
		cl.setBxBxrq(entity.getBxBxrq());//保险日期
		cl.setBxBxz(entity.getBxBxz());//保险至
		cl.setBxBxdh(entity.getBxBxdh());//保险电话
		cl.setBxBdzbbh(entity.getBxBdzbbh());//保单正本编号
		cl.setBxBdfbbh(entity.getBxBdfbbh());//保单副本编号
		cl.setBxBdWz(entity.getBxBdWz());//保单位置
		cl.setBxBz(entity.getBxBz());//备注
		cl.setBxDaFile(entity.getBxDaFile());//路径
		cl.setBxlxr(entity.getBxlxr());//保单联系人
		cl.setBxTbgs(entity.getBxTbgs());//保单投保公司
		cl.setBxBdCount(entity.getBxBdCount());//保单数量
		carService.update(cl);

        SimpleCondition warnCondition = new SimpleCondition(BizCarWarn.class);
        warnCondition.eq(BizCarWarn.InnerColumn.clId,entity.getId());
        warnCondition.setOrderByClause(BizCarWarn.InnerColumn.id.desc());
		List<BizCarInsuranceJq> list=findByCondition(warnCondition);
        if(list!=null&&list.size()>0){
            BizCarInsuranceJq fil=list.get(0);
			fil.setBxrq(entity.getBxBxrq());//保险日期
			fil.setBxz(entity.getBxBxz());//保险至
			fil.setBxdh(entity.getBxBxdh());//保险电话
			fil.setBdzbbh(entity.getBxBdzbbh());//保单正本编号
			fil.setBdfbbh(entity.getBxBdfbbh());//保单副本编号
			fil.setBdWz(entity.getBxBdWz());//保单位置
			fil.setBz(entity.getBxBz());//备注
			fil.setPropertyFileurl(entity.getBxDaFile());//电子档案路径
			fil.setBxlxr(entity.getBxlxr());//保单联系人
			fil.setTbgs(entity.getBxTbgs());//保单投保公司
			fil.setBdCount(entity.getBxBdCount());//保单数量
			update(fil);
        }

		return ApiResponse.success();

	}


}