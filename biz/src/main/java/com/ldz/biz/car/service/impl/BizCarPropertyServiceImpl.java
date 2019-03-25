package com.ldz.biz.car.service.impl;

import com.ldz.biz.car.mapper.BizCarPropertyMapper;
import com.ldz.biz.car.mapper.BizCarWarnMapper;
import com.ldz.biz.car.model.BizCar;
import com.ldz.biz.car.model.BizCarProperty;
import com.ldz.biz.car.model.BizCarWarn;
import com.ldz.biz.car.service.BizCarPropertyService;
import com.ldz.biz.car.service.BizCarService;
import com.ldz.biz.car.service.BizCarWarnService;
import com.ldz.biz.mapper.ChargeManagementMapper;
import com.ldz.biz.model.ChargeManagement;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.model.SysJg;
import com.ldz.sys.model.SysYh;
import com.ldz.sys.service.JgService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BizCarPropertyServiceImpl extends BaseServiceImpl<BizCarProperty, java.lang.String> implements BizCarPropertyService {

	@Autowired
	private BizCarPropertyMapper baseMapper;

	@Autowired
	private BizCarService carService;

	@Autowired
	private JgService jgService;
	/**
	 * 财务明细
	 */
	@Autowired
	private ChargeManagementMapper chargeManagementMapper;
	@Autowired
	private BizCarWarnService carWarnService;

    @Autowired
    private BizCarWarnMapper carWarnMapper;

	
	@Override
	protected Mapper<BizCarProperty> getBaseMapper() {
		return baseMapper;
	}

	/**
	 * 产权变更
	 * @param entity
	 * clId	车辆ID	必填
	 * jgdm		机构代码(选择的队号)		必填
	 * ck	车款 正整数 	非必填
	 * zb	质保金 正整数	必填
	 * cqr	产权人	必填
	 * cqrCode	产权人证件号	必填
	 * @return
	 */
	@Override
	public ApiResponse<String> changeOfTitle(BizCarProperty entity){
		SysYh user=getCurrentUser(true);
		RuntimeCheck.ifFalse(StringUtils.equals(user.getJgdm(),"100"), "您所在的角色无权做产权变更操作信息。");

//		1、非空验证
		RuntimeCheck.ifBlank(entity.getClId(), "请选择需要变更产权的车辆。");
//		RuntimeCheck.ifBlank(entity.getJgdm(), "请选择变更后的队");
		if(StringUtils.isEmpty(entity.getJgdm())){
			entity.setJgdm("100");
		}
		RuntimeCheck.ifBlank(entity.getCqr(), "请填写产权人");
		RuntimeCheck.ifBlank(entity.getCqrDn(), "请填写产权人联系方式");
		RuntimeCheck.ifBlank(entity.getCqrCode(), "请填写产权人证件号");

		RuntimeCheck.ifTrue(entity.getCk()<0, "车款不能为负数");
		RuntimeCheck.ifBlank(entity.getZb(), "请填写质保金");
		long zbj=-1;
		try {
			zbj=Long.parseLong(entity.getZb());
		}catch (Exception e){
			e.printStackTrace();
		}
		RuntimeCheck.ifTrue(zbj<0, "质保金必须是正整数");

		BizCar cl=carService.findById(entity.getClId());
		RuntimeCheck.ifTrue(cl==null, "车辆信息有误，请刷新页后重新尝试。");
		RuntimeCheck.ifFalse(StringUtils.indexOf(cl.getJgdm(),user.getJgdm())>-1, "该车辆属于："+cl.getJgmc()+" 您无权为它变更产权");

		RuntimeCheck.ifTrue(StringUtils.equals(cl.getCarPropertyType(),"4"), "该车辆已经售出，不能变更产权");
		RuntimeCheck.ifTrue(StringUtils.equals(cl.getCarPropertyType(),"3"), "该车辆已经报废，不能变更产权");
		RuntimeCheck.ifTrue(StringUtils.equals(cl.getCarPropertyType(),"2"), "该车辆为非学牌车，不能变更产权");

		Date endDate=null;
		try {
			String qzbfq=cl.getQzbfq();
			if (org.apache.commons.lang.StringUtils.isNotBlank(qzbfq)){
				endDate=DateUtils.getDate(qzbfq,"yyyy-MM-dd");
			}
		}catch (Exception e){
			return ApiResponse.fail("该车辆强制报废期有误，不能变更产权");
		}

//		RuntimeCheck.ifNull(endDate, "该车辆强制报废日期为空，该车暂不能变更产权。");
//		RuntimeCheck.ifTrue(endDate.compareTo(new Date())<0 , "该车辆强制报废日期为:"+cl.getQzbfq()+"，该车不能变更产权。");

		SimpleCondition jgCondition = new SimpleCondition(SysJg.class);
		jgCondition.eq(SysJg.InnerColumn.zt,"10");
		List<SysJg> all = jgService.findByCondition(jgCondition);
		Map<String, String> jgMap = all.stream().collect(Collectors.toMap(SysJg::getJgdm, SysJg::getJgmc));
		String jgmc=jgMap.get(entity.getJgdm());
//		RuntimeCheck.ifBlank(jgmc, "您选择的队，在系统中不存在，请核实");

		entity.setJgmc(jgmc);
		entity.setId(genId());
		entity.setCph(cl.getCph());
		entity.setCjr(user.getZh()+"-"+user.getXm());
		entity.setCjsj(DateUtils.getNowTime());

		String pastCqrDn="";
		String pastZb="";
		String pastCk="";
		String pastBz="";
		String pastCqr="集团";
		String yd="1";//异动次数
		String lastZbj="";//上一次的质保金;
		BizCarProperty lastProperty=null;
		//获取原产权人 和上次的异动次数
		SimpleCondition querCondition = new SimpleCondition(BizCarProperty.class);
		querCondition.eq(BizCarProperty.InnerColumn.clId, entity.getClId());//车辆ID
		querCondition.setOrderByClause(BizCarProperty.InnerColumn.id.desc());
		List<BizCarProperty> querList=this.findByCondition(querCondition);
		if(querList!=null&&querList.size()>0){
			lastProperty=querList.get(0);
			pastCqr=lastProperty.getCqr();

			pastCqrDn=lastProperty.getCqrDn();
			pastZb=lastProperty.getZb();
			pastCk=lastProperty.getCk()+"";
			pastBz=lastProperty.getBz();
			String lastYd=lastProperty.getYd();
			if(StringUtils.isNotEmpty(lastYd)){
				long lastyd=0;
				try {
					lastyd=Long.parseLong(lastYd);
				}catch (Exception e){}
				yd=""+(lastyd+1);
			}

			lastZbj=lastProperty.getZb();
		}

		entity.setPastCqr(pastCqr);//原产权人

		entity.setPastCqrDn(pastCqrDn);//原产权人电话
		entity.setPastZb(pastZb);//原质保金
		entity.setPastCk(pastCk);//原车款

		entity.setPastBz(pastBz);//原备注

		entity.setYd(yd);//异动次数
//		1000	退车辆质保金
//		1001	收车辆质保金

		List<ChargeManagement> chargeManagementList=new ArrayList<>();
		if(lastProperty!=null){
			ChargeManagement o=new ChargeManagement();
			o.setId(genId());
			o.setChargeCode("1000");
			o.setChargeName("退车辆质保金");
			o.setInOutType("10");
			try {
				Integer chargeFee=Integer.parseInt(lastZbj);
				o.setChargeFee(chargeFee);//
			}catch (Exception e){}
			o.setChargeTime(DateUtils.getNowTime());
			o.setReceiver(lastProperty.getCqr());
			o.setRemark("车辆产权内部变更 车辆:"+cl.getCph()+"。更换产权由"+lastProperty.getJgmc()+"更换到："+entity.getJgmc()+" 退质保金:"+lastProperty.getZb());
			chargeManagementList.add(o);
		}
		ChargeManagement o=new ChargeManagement();
		o.setId(genId());
		o.setChargeCode("1001");
		o.setChargeName("收车辆质保金");
		o.setInOutType("00");
		try {
			Integer newZbj=Integer.parseInt(entity.getZb());
			o.setChargeFee(newZbj);
		}catch (Exception e){}
		o.setChargeTime(DateUtils.getNowTime());
		o.setReceiver(user.getZh()+ "-" + user.getXm());
		if(lastProperty==null){
			o.setRemark("车辆产权内部变更 车辆:"+cl.getCph()+"。更换产权由"+"-"+"更换到："+entity.getJgmc()+" 收保金:"+entity.getZb());

		}else{
			o.setRemark("车辆产权内部变更 车辆:"+cl.getCph()+"。更换产权由"+lastProperty.getJgmc()+"更换到："+entity.getJgmc()+" 收保金:"+entity.getZb());
		}
		chargeManagementList.add(o);
		if(chargeManagementList!=null&&chargeManagementList.size()>0){
			for(ChargeManagement list:chargeManagementList){
				chargeManagementMapper.insert(list);
			}
		}

		entity.setJbr(user.getXm());
		entity.setJbrDn(user.getSjh());


		cl.setJgdm(entity.getJgdm());
		cl.setJgmc(entity.getJgmc());
		cl.setClCqr(entity.getCqr());//车辆产权人
		cl.setClCqrDn(entity.getCqrDn());//车辆产权人手机号
		cl.setClCqrCode(entity.getCqrCode());//车辆产权人证件号

		cl.setClCqrJl(entity.getCk()+"");//车辆产权变更金额
		cl.setClCqrJbr(entity.getJbr());//车辆产权变更经办人
		cl.setClCqrJbrDn(entity.getJbrDn());//车辆产权变更经办人
		cl.setClCqrbz(entity.getBz());//车辆产权变更备注

		cl.setDzdCqbgUrl(entity.getPropertyFileurl());//产权内部变更电子档案

		carService.update(cl);

		entity.setPropertyType("1");
		baseMapper.insertSelective(entity);


		return ApiResponse.success();
	}

	/**
	 * 学牌车变为非学牌车（号牌种类 变更）
	 * @param entity
	 * clId	车辆ID	必填
	 * bz	备注		必填
	 * @return
	 */
	@Override
	public ApiResponse<String> cphTypeUpdat(BizCarProperty entity){
		SysYh user=getCurrentUser(true);
		RuntimeCheck.ifFalse(StringUtils.equals(user.getJgdm(),"100"), "您所在的角色无权做产权变更操作信息。");

//		1、非空验证
		RuntimeCheck.ifBlank(entity.getClId(), "请选择需要变更产权的车辆。");
		RuntimeCheck.ifBlank(entity.getBz(), "您好，请在备注中填写该车新的车牌号。");
//		2、车辆有效性验证
		BizCar cl=carService.findById(entity.getClId());
		RuntimeCheck.ifTrue(cl==null, "车辆信息有误，请刷新页后重新尝试。");
		RuntimeCheck.ifFalse(StringUtils.indexOf(cl.getJgdm(),user.getJgdm())>-1, "该车辆属于："+cl.getJgmc()+" 您无权为它变更产权");

        //		RuntimeCheck.ifTrue(StringUtils.equals(findBy.getCarPropertyType(),"2"), "该车辆已经是，不能做编辑操作。");
        RuntimeCheck.ifTrue(StringUtils.equals(cl.getCarPropertyType(),"3"), "该车辆已经报废，不能做 学牌车变为非学牌车 业务。");
        RuntimeCheck.ifTrue(StringUtils.equals(cl.getCarPropertyType(),"4"), "该车辆已经售出，不能做 学牌车变为非学牌车 业务。");

		RuntimeCheck.ifFalse(StringUtils.equals(cl.getCarPropertyType(),"1"), "该车辆不是学牌车，不需要做 学牌车变为非学牌车 业务");

		Date endDate=null;
		try {
			String qzbfq=cl.getQzbfq();
			if (org.apache.commons.lang.StringUtils.isNotBlank(qzbfq)){
				endDate=DateUtils.getDate(qzbfq,"yyyy-MM-dd");
			}
		}catch (Exception e){

		}
		RuntimeCheck.ifTrue(endDate!=null&&endDate.compareTo(new Date())<0 , "该车辆强制报废日期为:"+cl.getQzbfq()+"，该车不能变更产权。");

		entity.setJgdm(user.getJgdm());

		SimpleCondition jgCondition = new SimpleCondition(SysJg.class);
		jgCondition.eq(SysJg.InnerColumn.zt,"10");
		List<SysJg> all = jgService.findByCondition(jgCondition);
		Map<String, String> jgMap = all.stream().collect(Collectors.toMap(SysJg::getJgdm, SysJg::getJgmc));
		String jgmc=jgMap.get(user.getJgdm());
		entity.setJgmc(jgmc);
		entity.setId(genId());
		entity.setCph(cl.getCph());
		entity.setCjr(user.getZh()+"-"+user.getXm());
		entity.setCjsj(DateUtils.getNowTime());
		entity.setCk((float) 0);
		entity.setZb("0");//质保

		String pastCqrDn="";
		String pastZb="";
		String pastCk="";
		String pastBz="";
		String pastCqr="集团";
		String yd="1";//异动次数
		String lastZbj="";//上一次的质保金;
		BizCarProperty lastProperty=null;

		//获取原产权人 和上次的异动次数
		SimpleCondition querCondition = new SimpleCondition(BizCarProperty.class);
		querCondition.eq(BizCarProperty.InnerColumn.clId, entity.getClId());//车辆ID
		querCondition.setOrderByClause(BizCarProperty.InnerColumn.id.desc());
		List<BizCarProperty> querList=this.findByCondition(querCondition);
		if(querList!=null&&querList.size()>0){
			lastProperty=querList.get(0);
			pastCqr=lastProperty.getCqr();

			pastCqrDn=lastProperty.getCqrDn();
			pastZb=lastProperty.getZb();
			pastCk=lastProperty.getCk()+"";
			pastBz=lastProperty.getBz();

			String lastYd=lastProperty.getYd();
			if(StringUtils.isNotEmpty(lastYd)){
				long lastyd=0;
				try {
					lastyd=Long.parseLong(lastYd);
				}catch (Exception e){}
				yd=""+(lastyd+1);
			}

			lastZbj=lastProperty.getZb();
		}
		entity.setPastCqr(pastCqr);//原产权人

		entity.setPastCqrDn(pastCqrDn);//原产权人电话
		entity.setPastZb(pastZb);//原质保金
		entity.setPastCk(pastCk);//原车款
		entity.setPastBz(pastBz);//原备注


		entity.setYd(yd);//异动次数

		List<ChargeManagement> chargeManagementList=new ArrayList<>();
		if(lastProperty!=null&&StringUtils.isNotEmpty(lastProperty.getZb())){
			//退质保金
			ChargeManagement o=new ChargeManagement();
			o.setId(genId());
			o.setChargeCode("1000");
			o.setChargeName("退车辆质保金");
			o.setInOutType("10");
			try {
				Integer chargeFee=Integer.parseInt(lastZbj);
				o.setChargeFee(chargeFee);//
			}catch (Exception e){}
			o.setChargeTime(DateUtils.getNowTime());
			o.setReceiver(lastProperty.getCqr());
			o.setRemark("学牌车变为非学牌车 车辆:"+cl.getCph()+"。更换产权由"+lastProperty.getJgmc()+" 变更为:本集团 退质保金:"+lastProperty.getZb());
			chargeManagementList.add(o);

		}

		if(chargeManagementList!=null&&chargeManagementList.size()>0){
			for(ChargeManagement list:chargeManagementList){
				chargeManagementMapper.insert(list);
			}
		}
		entity.setCqr("");
		entity.setCqrDn("");
		entity.setCqrCode("");

		cl.setJgdm(user.getJgdm());
		cl.setJgmc(jgmc);
		cl.setHpzl("2");//地牌
		cl.setSyxz("2");//非教练
		cl.setCarPropertyType("2");//车辆产权状态 [ZDCLK1041] 1、学牌车 2、非学牌车 3、已报废车 4、已售出
		cl.setCarType("71");//车辆状态   ZDCLK1042

		cl.setClCqr(entity.getCqr());//车辆产权人
		cl.setClCqrDn(entity.getCqrDn());//车辆产权人手机号
		cl.setClCqrCode(entity.getCqrCode());//车辆产权人证件号
		cl.setDzdCpbgUrl(entity.getPropertyFileurl());//车牌变更 电子档案
		carService.update(cl);

		entity.setPropertyType("2");
		baseMapper.insertSelective(entity);

        // 删除该车还未生效的旧数据
        SimpleCondition condition = new SimpleCondition(BizCarWarn.class);
        condition.eq(BizCarWarn.InnerColumn.clId,cl.getId());
        condition.eq(BizCarWarn.InnerColumn.warnDispose,"0");
        carWarnMapper.deleteByExample(condition);

		return ApiResponse.success();
	}

	/**
	 * 车辆报废
	 *
	 * @param bfjl		报废金额
	 * @param jbr		经办人
	 * @param jbrDn		经办时间
	 * @param clid
	 * clId	车辆ID	必填
	 * @return
	 */
	@Override
	public ApiResponse<String> vehicleScrapping(String clid, String jbr, String jbrDn, String bfjl,String propertyFileurl){
		SysYh user=getCurrentUser(true);
		RuntimeCheck.ifFalse(StringUtils.equals(user.getJgdm(),"100"), "您所在的角色无权做产权变更操作信息。");

//		1、非空验证
		RuntimeCheck.ifBlank(clid, "请选择需要变更产权的车辆。");
		RuntimeCheck.ifBlank(jbr, "请填写经办人姓名。");
		RuntimeCheck.ifBlank(jbrDn, "请经办人联系方式。");
		RuntimeCheck.ifBlank(bfjl, "请填写车辆报废金额。");
		long bfFee=-1;
		try {
			bfFee=Long.parseLong(bfjl);
		}catch (Exception e){
			e.printStackTrace();
		}
		RuntimeCheck.ifTrue(bfFee<1, "报废金额必须是大于1的正整数");

		BizCarProperty entity =new BizCarProperty();
		entity.setClId(clid);
		entity.setPropertyFileurl(propertyFileurl);//产权协议电子档案


//		2、车辆有效性验证
		BizCar cl=carService.findById(clid);
		RuntimeCheck.ifTrue(cl==null, "车辆信息有误，请刷新页后重新尝试。");
		RuntimeCheck.ifTrue(StringUtils.equals(cl.getCarPropertyType(),"3"), "该车辆已经报废，不能做报废操作。");
		RuntimeCheck.ifTrue(StringUtils.equals(cl.getCarPropertyType(),"4"), "该车辆已经售出，不能再次做报废操作。");


		RuntimeCheck.ifFalse(StringUtils.indexOf(cl.getJgdm(),user.getJgdm())>-1, "该车辆属于："+cl.getJgmc()+" 您无权为它变更产权");

//		3、查看车辆是否属于其它车辆，如果车辆还属于其它车队，需要退还质保金
		String pastCqrDn="";
		String pastZb="";
		String pastCk="";
		String pastBz="";
		String pastCqr="集团";
		String yd="1";//异动次数
		String lastZbj="";//上一次的质保金;
		BizCarProperty lastProperty=null;

		//获取原产权人 和上次的异动次数
		SimpleCondition querCondition = new SimpleCondition(BizCarProperty.class);
		querCondition.eq(BizCarProperty.InnerColumn.clId, clid);//车辆ID
		querCondition.setOrderByClause(BizCarProperty.InnerColumn.id.desc());
		List<BizCarProperty> querList=this.findByCondition(querCondition);
		if(querList!=null&&querList.size()>0){
			lastProperty=querList.get(0);
			pastCqr=lastProperty.getCqr();
			pastCqrDn=lastProperty.getCqrDn();
			pastZb=lastProperty.getZb();
			pastCk=lastProperty.getCk()+"";
			pastBz=lastProperty.getBz();

			String lastYd=lastProperty.getYd();
			if(StringUtils.isNotEmpty(lastYd)){
				long lastyd=0;
				try {
					lastyd=Long.parseLong(lastYd);
				}catch (Exception e){}
				yd=""+(lastyd+1);
			}

			lastZbj=lastProperty.getZb();
		}


		entity.setJgdm(user.getJgdm());

		SimpleCondition jgCondition = new SimpleCondition(SysJg.class);
		jgCondition.eq(SysJg.InnerColumn.zt,"10");
		List<SysJg> all = jgService.findByCondition(jgCondition);
		Map<String, String> jgMap = all.stream().collect(Collectors.toMap(SysJg::getJgdm, SysJg::getJgmc));
		String jgmc=jgMap.get(user.getJgdm());
		entity.setJgmc(jgmc);
		entity.setId(genId());
		entity.setCph(cl.getCph());
		entity.setCjr(user.getZh()+"-"+user.getXm());
		entity.setCjsj(DateUtils.getNowTime());
		entity.setCk((float) 0);//车款

		entity.setPastCqr(pastCqr);//原产权人

		entity.setPastCqrDn(pastCqrDn);//原产权人电话
		entity.setPastZb(pastZb);//原质保金
		entity.setPastCk(pastCk);//原车款
		entity.setPastBz(pastBz);//原备注


		entity.setYd(yd);//异动次数

		List<ChargeManagement> chargeManagementList=new ArrayList<>();
		if(lastProperty!=null&&StringUtils.isNotEmpty(lastProperty.getZb())){
			//退质保金
			ChargeManagement o=new ChargeManagement();
			o.setId(genId());
			o.setChargeCode("1000");
			o.setChargeName("退车辆质保金");
			o.setInOutType("10");
			try {
				Integer chargeFee=Integer.parseInt(lastZbj);
				o.setChargeFee(chargeFee);//
			}catch (Exception e){}
			o.setChargeTime(DateUtils.getNowTime());
			o.setReceiver(lastProperty.getCqr());
			o.setRemark("车辆报废操作 车辆:"+cl.getCph()+"。更换产权由"+lastProperty.getJgmc()+" 变更为:本集团 退质保金:"+lastProperty.getZb());
			chargeManagementList.add(o);

		}

		if(chargeManagementList!=null&&chargeManagementList.size()>0){
			for(ChargeManagement list:chargeManagementList){
				chargeManagementMapper.insert(list);
			}
		}


		entity.setCqr("");
		entity.setCqrDn("");
		entity.setCqrCode("");


		cl.setJgdm(user.getJgdm());
		cl.setJgmc(jgmc);
		cl.setCarPropertyType("3");//车辆产权状态 [ZDCLK1041] 1、学牌车 2、非学牌车 3、已报废车 4、已售出
		cl.setCarType("50");//车辆状态   ZDCLK1042
		cl.setBfType("1");//报废状态
		cl.setBfFee(bfjl);
		cl.setBfDate(DateUtils.getNowTime());

		cl.setClCqr(entity.getCqr());//车辆产权人
		cl.setClCqrDn(entity.getCqrDn());//车辆产权人手机号
		cl.setClCqrCode(entity.getCqrCode());//车辆产权人证件号

		cl.setDzdClbfgUrl(entity.getPropertyFileurl());//车辆报废 电子档案

		carService.update(cl);

		entity.setPropertyType("3");
		baseMapper.insertSelective(entity);

		// 车辆报废后需要将消息关闭掉。
		BizCarWarn carWarn=new BizCarWarn();
		carWarn.setClId(entity.getClId());
		carWarn.setWarnType("4");
		List<BizCarWarn> warnList=carWarnService.findByEntity(carWarn);
		if(warnList!=null&&warnList.size()>0){
			BizCarWarn upWarn=warnList.get(0);
			upWarn.setWarnDispose("1");
			upWarn.setDisposeCode(entity.getId());
			carWarnService.update(upWarn);
		}

        // 删除该车还未生效的旧数据
        SimpleCondition condition = new SimpleCondition(BizCarWarn.class);
        condition.eq(BizCarWarn.InnerColumn.clId,cl.getId());
        condition.eq(BizCarWarn.InnerColumn.warnDispose,"0");
        carWarnMapper.deleteByExample(condition);
		return ApiResponse.success();
	}

	/**
	 * 车辆售卖
	 *@param entity
	 *  ck		车款
	 *  jbr		经办人
	 *  jbrDn		经办时间
	 *  cqr		产权人
	 *  cqrDn		产权人联系方式
	 *  cqrCode		产权人证件号
	 *  jbr		经办人
	 *  jbrDn		经办人电话
	 *  clId	车辆ID	必填
	 * @return
	 */
	@Override
	public  ApiResponse<String> changeSell(BizCarProperty entity){
		SysYh user=getCurrentUser(true);
		RuntimeCheck.ifFalse(StringUtils.equals(user.getJgdm(),"100"), "您所在的角色无权做产权变更操作信息。");

//		1、非空验证
		RuntimeCheck.ifBlank(entity.getClId(), "请选择车辆的车辆。");
		RuntimeCheck.ifBlank(entity.getCqr(), "请填写产权人");
		RuntimeCheck.ifBlank(entity.getCqrDn(), "请填写产权人联系方式");
		RuntimeCheck.ifBlank(entity.getCqrCode(), "请填写产权人证件号");
		RuntimeCheck.ifTrue(entity.getCk()<0, "车款不能为负数");

//		2、车辆有效性验证
		BizCar cl=carService.findById(entity.getClId());
		RuntimeCheck.ifTrue(cl==null, "车辆信息有误，请刷新页后重新尝试。");
		RuntimeCheck.ifFalse(StringUtils.indexOf(cl.getJgdm(),user.getJgdm())>-1, "该车辆属于："+cl.getJgmc()+" 您无权为它变更产权");

		RuntimeCheck.ifTrue(StringUtils.equals(cl.getCarPropertyType(),"3"), "该车辆已经报废，不能做售出操作。");
//		RuntimeCheck.ifTrue(StringUtils.equals(cl.getCarPropertyType(),"1"), "该车辆是学牌车，不能做售出操作。请将该车做 学牌转非学牌车 操作。");
		RuntimeCheck.ifTrue(StringUtils.equals(cl.getCarPropertyType(),"4"), "该车辆已经售出，不能再次做售出操作。");

		Date endDate=null;
		try {
			String qzbfq=cl.getQzbfq();
			if (org.apache.commons.lang.StringUtils.isNotBlank(qzbfq)){
				endDate=DateUtils.getDate(qzbfq,"yyyy-MM-dd");
			}
		}catch (Exception e){

		}
		RuntimeCheck.ifTrue(endDate!=null&&endDate.compareTo(new Date())<0 , "该车辆强制报废日期为:"+cl.getQzbfq()+"，该车离强制报废期很近，不能售出。");


		entity.setJgdm(user.getJgdm());

		SimpleCondition jgCondition = new SimpleCondition(SysJg.class);
		jgCondition.eq(SysJg.InnerColumn.zt,"10");
		List<SysJg> all = jgService.findByCondition(jgCondition);
		Map<String, String> jgMap = all.stream().collect(Collectors.toMap(SysJg::getJgdm, SysJg::getJgmc));
		String jgmc=jgMap.get(user.getJgdm());
		entity.setJgmc(jgmc);
		entity.setId(genId());
		entity.setCph(cl.getCph());
		entity.setCjr(user.getZh()+"-"+user.getXm());
		entity.setCjsj(DateUtils.getNowTime());
		entity.setZb("0");//质保
		entity.setYd(null);


		cl.setCarPropertyType("4");//车辆产权状态 [ZDCLK1041] 1、学牌车 2、非学牌车 3、已报废车 4、已售出
		cl.setCarType("61");//车辆状态   ZDCLK1042

		cl.setClCqr(entity.getCqr());//车辆产权人
		cl.setClCqrDn(entity.getCqrDn());//车辆产权人手机号
		cl.setClCqrCode(entity.getCqrCode());//车辆产权人证件号

		cl.setDzdClbmgUrl(entity.getPropertyFileurl());//车辆变卖 电子档案
		carService.update(cl);


		entity.setPropertyType("4");
		baseMapper.insertSelective(entity);

        // 删除该车还未生效的旧数据
        SimpleCondition condition = new SimpleCondition(BizCarWarn.class);
        condition.eq(BizCarWarn.InnerColumn.clId,cl.getId());
        condition.eq(BizCarWarn.InnerColumn.warnDispose,"0");
        carWarnMapper.deleteByExample(condition);
		return ApiResponse.success();
	}

	/**
	 * 获取车辆产权信息列表
	 *@param  clId	车辆ID	必填
	 * @return
	 */
	@Override
	public  ApiResponse<List<BizCarProperty>> getCarPropertyList(String clId){
		RuntimeCheck.ifBlank(clId, "请选择车辆的车辆。");
		SimpleCondition condition = new SimpleCondition(BizCarProperty.class);
		condition.eq(BizCarProperty.InnerColumn.clId,clId);
		condition.setOrderByClause(BizCarProperty.InnerColumn.id.desc());
		List<BizCarProperty> list=this.findByCondition(condition);
		if(list==null||list.size()<1){
			list=new ArrayList<BizCarProperty>();
		}
		return ApiResponse.success(list);

	}
}