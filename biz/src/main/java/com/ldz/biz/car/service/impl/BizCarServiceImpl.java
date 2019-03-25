package com.ldz.biz.car.service.impl;

import com.ldz.biz.car.mapper.BizCarMapper;
import com.ldz.biz.car.model.*;
import com.ldz.biz.car.service.*;
import com.ldz.biz.service.CoachManagementService;
import com.ldz.biz.service.ZgjbxxService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.sys.model.SysJg;
import com.ldz.sys.model.SysYh;
import com.ldz.sys.service.JgService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BizCarServiceImpl extends BaseServiceImpl<BizCar, java.lang.String> implements BizCarService {

	@Autowired
	private BizCarMapper baseMapper;

	@Autowired
	private BizCarAnnualExamService carAnnualExamService;

	@Autowired
	private BizCarSyrService bizCarSyrService;//车辆所有人



	@Autowired
	private CoachManagementService jlService;//教练员

	@Autowired
	private JgService jgService;

	@Autowired
	private BizCarWarnService warnService;//提醒模板

	@Autowired
	private BizCarWarnService warnSservice;

	@Autowired
	private BizCarUsageService carUsageService;

	@Autowired
	private ZgjbxxService zgjbxxService;

	@Autowired
	private BizCarHistoryService carHistoryService;


	/**
	 * 分页补充
	 * queryWarnType  提醒类型(1、车辆年审提醒 2、运管年审提醒 3、改气年审提醒 4、续保提醒 5、报废提醒)
	 * queryWarnDay  90 天内 -1 表示查询过期的 为空表示该值无效 查询所有
	 *
	 * @param condition
	 * @return
	 */
	@Override
	public boolean fillPagerCondition(LimitedCondition condition){

		HttpServletRequest requset = getRequset();
		//提醒类型(1、车辆年审提醒 2、运管年审提醒 3、改气年审提醒 4、续保提醒 5、报废提醒)
		String queryWarnType = requset.getParameter("queryWarnType");
		//提醒提前通知的天数 queryWarnDay  90 天内 -1 表示查询过期的 为空表示查询所有
		String queryWarnDay = requset.getParameter("queryWarnDay");
		//	车辆状态   ZDCLK1042
		String carTypes=requset.getParameter("carType");
		if(StringUtils.isNotEmpty(carTypes)){
			String [] carTypeArray=carTypes.split(",");
			String whereSql="";
			for (String typ:carTypeArray){
				int typeInt=-1;
				try {
					//把字符串强制转换为数字
					typeInt=Integer.valueOf(typ);
					//如果是数字，返回True
				} catch (Exception e) {}
				if(typeInt>-1){
					whereSql+=" or car_type like  '%"+typeInt+"%' ";
				}
			}

			if(StringUtils.isNotEmpty(whereSql)){
				condition.and().andCondition("( 1=1"+whereSql+" )");
			}
		}

		String ccdjrq = requset.getParameter("ccdjrq");//初次登记日期
		// TODO: 2018/11/26   是否做改气操作，这里需要做一下验证
//		if(StringUtils.equals(ccdjrq,"1")){
//			condition.in(BizCar.InnerColumn.id, list);
//		}else if(StringUtils.equals(ccdjrq,"0")){
//
//		}
		String qzbfq = requset.getParameter("qzbfq");//强制报废期
		String bxCdrq = requset.getParameter("bxCdrq");//保险初登日期
		String yyCdrq = requset.getParameter("yyCdrq");//运管初登日期
		String gxCdrq = requset.getParameter("gxCdrq");//改气初登日期

		if(StringUtils.isNotBlank(queryWarnType)){
			SimpleCondition simpleCondition = new SimpleCondition(BizCarWarn.class);

			simpleCondition.lte(BizCarWarn.InnerColumn.warnDate, DateUtils.getToday());
			simpleCondition.eq(BizCarWarn.InnerColumn.warnType, queryWarnType);
			simpleCondition.eq(BizCarWarn.InnerColumn.warnDispose, "0");//查询所有未处理的告警信息
			if(StringUtils.isNotEmpty(queryWarnDay)){
				Integer queryWarnDays=null;
				try {
					queryWarnDays=Integer.parseInt(queryWarnDay);
				}catch (Exception e){}
				if(queryWarnDays!=null&&queryWarnDays>-1){
//					condition.and().andCondition(" charge_code = 9999 or charge_code = 4999 ");
					simpleCondition.and().andCondition(" (TIMESTAMPDIFF(DAY , now(),expiry_date)>0 and TIMESTAMPDIFF(DAY , now(),expiry_date)< "+queryWarnDays+") ");
				}else if(queryWarnDays==-1){
//					simpleCondition.and().andCondition(" (TIMESTAMPDIFF(DAY ,expiry_date, now())<0 and TIMESTAMPDIFF(DAY ,expiry_date, now())< "+queryWarnDays+") ");
					simpleCondition.and().andCondition(" (TIMESTAMPDIFF(DAY , now(),expiry_date)<0 ) ");
				}
			}

			List<BizCarWarn> institutions = warnSservice.findByCondition(simpleCondition);
			List<String> list = institutions.stream().map(BizCarWarn::getClId).collect(Collectors.toList());
			if(CollectionUtils.isNotEmpty(list)) {
				condition.in(BizCar.InnerColumn.id, list);
			}
		}
		return true;
	}


	
	@Override
	protected Mapper<BizCar> getBaseMapper() {
		return baseMapper;
	}


	/**
	 * 获取档案最新的序列
	 * @return
	 */
	@Override
	public ApiResponse<String> getArchivesSeq(){
		String seqId="";
		String year=DateUtils.getToday("yyyy")+"-";
		SimpleCondition condition = new SimpleCondition(BizCar.class);
		condition.like(BizCar.InnerColumn.dah,year);
		condition.setOrderByClause(BizCar.InnerColumn.dah.desc());
		List<BizCar> list=this.findByCondition(condition);
		if(list!=null&&list.size()>0){
			String seq=list.get(0).getDah();
			seq=seq.replaceAll(year,"");
			try {
				Integer id=Integer.parseInt(seq);
				seqId=String.format("%04d",(id+1));//0 表示补齐的字符  4 长度为4位 d 表示整数。
			}catch (Exception e){}
		}
		if(StringUtils.isEmpty(seqId)){
			seqId="0001";
		}
		return ApiResponse.success(year+seqId);
	}
	/**
	 * 新增操作
	 * @param entity
	 * @return
	 */
	@Override
	public ApiResponse<String> validAndSaveCar(BizCar entity) {

		SysYh currentUser=getCurrentUser(true);
		RuntimeCheck.ifFalse(StringUtils.equals(currentUser.getJgdm(),"100"), "您所在的角色无权新增车辆。");
//		1、非空验证
		RuntimeCheck.ifBlank(entity.getCph(), "请填写车牌号");
		RuntimeCheck.ifBlank(entity.getCarSyr(), "请选择机动车所有人");
		RuntimeCheck.ifBlank(entity.getCarQy(), "请选择机动车所属区域");
		RuntimeCheck.ifBlank(entity.getDah(), "请填写档案号");
		RuntimeCheck.ifBlank(entity.getHpzl(), "请选择号牌种类");
		RuntimeCheck.ifBlank(entity.getSyxz(), "请选择使用性质");
		RuntimeCheck.ifBlank(entity.getCcdjrq(), "请输入初次登记日期");
//		RuntimeCheck.ifBlank(entity.getQzbfq(), "请输入强制报废期");

//		2、字典项有效验证  todo 后期需要从字典表中获取
//		RuntimeCheck.ifFalse(StringUtils.indexOf(",1,2",","+entity.getHpzl())>-1, "号牌种类填写错误，请重新填写");
//		RuntimeCheck.ifFalse(StringUtils.indexOf(",01,02",","+entity.getSyxz())>-1, "使用性质填写错误，请重新填写");

		String hpzl= entity.getHpzl();//车辆_号牌种类 [ZDCLK1036] 1、学牌 2、地牌
        if(StringUtils.equals(hpzl,"1")){
            RuntimeCheck.ifBlank(entity.getPxcx(), "请选择培训车型");
            RuntimeCheck.ifFalse(StringUtils.indexOf(",C1,C2,A1,A2,A3,B2",","+entity.getPxcx())>-1, "培训车型填写错误，请重新填写");
        }
		try {
            DateUtils.getDate(entity.getCcdjrq(),"yyyy-MM-dd");
		}catch (Exception e){
			e.printStackTrace();
			RuntimeCheck.ifTrue(true,"请输入正确的初次登记日期 日期格式为：yyyy-MM-dd");
		}
		try {
			if(StringUtils.isNotEmpty(entity.getQzbfq())){
				DateUtils.getDate(entity.getQzbfq(),"yyyy-MM-dd");
			}
		}catch (Exception e){
			e.printStackTrace();
			RuntimeCheck.ifTrue(true,"请输入正确的强制报废期 日期格式为：yyyy-MM-dd");
		}
//		3、车牌号、档案号去重验证
		SimpleCondition validateCondition = new SimpleCondition(BizCar.class);
		validateCondition.and().andEqualTo(BizCar.InnerColumn.cph.name(), entity.getCph());//请填写车牌号
//		validateCondition.or().andEqualTo(BizCar.InnerColumn.dah.name(), entity.getDah());//请填写档案号
		List<BizCar> validateList=this.findByCondition(validateCondition);
		if(validateList!=null&&validateList.size()>0){
			for(BizCar vCar:validateList){
				RuntimeCheck.ifTrue(StringUtils.equals(vCar.getCph(),entity.getCph()), "车牌号已经存在，请重新填写");
//				RuntimeCheck.ifTrue(StringUtils.equals(vCar.getDah(),entity.getDah()), "档案号已经存在，请重新填写");
			}
		}

//		4、获取所有机构代码对应的机构名称
		SimpleCondition condition = new SimpleCondition(SysJg.class);
		condition.eq(SysJg.InnerColumn.zt,"10");
		List<SysJg> all = jgService.findByCondition(condition);
		Map<String, String> jgMap = all.stream().collect(Collectors.toMap(SysJg::getJgdm, SysJg::getJgmc));

		entity.setCjType("0");
		entity.setId(genId());
		entity.setJgdm(currentUser.getJgdm());
		entity.setJgmc(jgMap.get(currentUser.getJgdm()));
		entity.setCjr(currentUser.getZh()+"-"+currentUser.getXm());
		entity.setCjsj(DateUtils.getNowTime());
//		车辆产权状态 [ZDCLK1041]  1、学牌车 2、非学牌车 3、已报销车 4、已售出
		String carPropertyType="2";
		if(StringUtils.equals(entity.getHpzl(),"1")){//1、学牌 2、地牌
			carPropertyType="1";
		}
		entity.setCarPropertyType(carPropertyType);

//		5、通过 初次登记日期 核算出所有的预警信息
//            baseMapper.insertSelective(entity);
//			warnService.updCarWarnDate(entity.getId());
        ApiResponse<String> warnType=warnService.batchAnnualSave(entity,null);
        if(warnType.isSuccess()){
            baseMapper.insertSelective(entity);
            warnService.updCarWarnDate(entity.getId());
        }else{
            return warnType;
        }
		return ApiResponse.success();
	}

	@Override
	public ApiResponse<String> validAndUpdateCar(BizCar entity){
		SysYh user=getCurrentUser(true);
		RuntimeCheck.ifFalse(StringUtils.equals(user.getJgdm(),"100"), "您所在的角色无权修改车辆信息。");
//		1、非空验证
		RuntimeCheck.ifBlank(entity.getCph(), "请填写车牌号"); //todo 车牌号和档案号是禁止修改的。
		RuntimeCheck.ifBlank(entity.getCarSyr(), "请选择机动车所有人");
		RuntimeCheck.ifBlank(entity.getCarQy(), "请选择机动车所属区域");
//		RuntimeCheck.ifBlank(entity.getDah(), "请填写档案号");
		RuntimeCheck.ifBlank(entity.getHpzl(), "请选择号牌种类");
		RuntimeCheck.ifBlank(entity.getSyxz(), "请选择使用性质");
		RuntimeCheck.ifBlank(entity.getCcdjrq(), "请输入初次登记日期");
//		RuntimeCheck.ifBlank(entity.getQzbfq(), "请输入强制报废期");

//		2、字典项有效验证  todo 后期需要从字典表中获取
//		RuntimeCheck.ifFalse(StringUtils.indexOf(",1,2",","+entity.getHpzl())>-1, "号牌种类填写错误，请重新填写");
//		RuntimeCheck.ifFalse(StringUtils.indexOf(",1,2,3",","+entity.getSyxz())>-1, "使用性质填写错误，请重新填写");

		String hpzl= entity.getHpzl();//车辆_号牌种类 [ZDCLK1036] 1、学牌 2、地牌
		if(StringUtils.equals(hpzl,"1")){
			RuntimeCheck.ifBlank(entity.getPxcx(), "请选择培训车型");
			RuntimeCheck.ifFalse(StringUtils.indexOf(",C1,C2,A1,A2,A3,B2",","+entity.getPxcx())>-1, "培训车型填写错误，请重新填写");
		}
		try {
			if(StringUtils.isNotEmpty(entity.getCcdjrq())){
				DateUtils.getDate(entity.getCcdjrq(),"yyyy-MM-dd");
			}
		}catch (Exception e){
			e.printStackTrace();
			RuntimeCheck.ifTrue(true,"请输入正确的初次登记日期 日期格式为：yyyy-MM-dd");
		}
		try {
			if(StringUtils.isNotEmpty(entity.getQzbfq())){
				DateUtils.getDate(entity.getQzbfq(),"yyyy-MM-dd");
			}
		}catch (Exception e){
			e.printStackTrace();
			RuntimeCheck.ifTrue(true,"请输入正确的强制报废期 日期格式为：yyyy-MM-dd");
		}

//		3、验证当前用户是否有权限修改这台车数据。
		BizCar findBy=this.findById(entity.getId());
		RuntimeCheck.ifNull(findBy, "该车辆不存在，请核实");
		RuntimeCheck.ifFalse(StringUtils.indexOf(findBy.getJgdm(),user.getJgdm())>-1, "该车辆属于："+findBy.getJgmc()+" 您无权编辑它");
//		RuntimeCheck.ifTrue(StringUtils.equals(findBy.getCarPropertyType(),"2"), "该车辆已经是，不能做编辑操作。");
		RuntimeCheck.ifTrue(StringUtils.equals(findBy.getCarPropertyType(),"3"), "该车辆已经报废，不能做编辑操作。");
		RuntimeCheck.ifTrue(StringUtils.equals(findBy.getCarPropertyType(),"4"), "该车辆已经售出，不能做编辑操作。");

//		4、车牌号、档案号去重验证
		SimpleCondition validateCondition = new SimpleCondition(BizCar.class);
		validateCondition.and().andEqualTo(BizCar.InnerColumn.cph.name(), entity.getCph());//请填写车牌号
//		validateCondition.or().andEqualTo(BizCar.InnerColumn.dah.name(), entity.getDah());//请填写档案号
		List<BizCar> validateList=this.findByCondition(validateCondition);
		if(validateList!=null&&validateList.size()>0){
			for(BizCar vCar:validateList){
				if(!StringUtils.equals(vCar.getId(),entity.getId())){
					RuntimeCheck.ifTrue(StringUtils.equals(vCar.getCph(),entity.getCph()), "车牌号已经存在，请重新填写");
//					RuntimeCheck.ifTrue(StringUtils.equals(vCar.getDah(),entity.getDah()), "档案号已经存在，请重新填写");
				}
			}
		}

//		车辆产权状态 [ZDCLK1041]  1、学牌车 2、非学牌车 3、已报销车 4、已售出
		String carPropertyType="2";
		if(StringUtils.equals(entity.getHpzl(),"1")){//1、学牌 2、地牌
			carPropertyType="1";
		}
		entity.setCarPropertyType(carPropertyType);
		entity.setCph(findBy.getCph());//车辆编辑时，车牌号不能被编辑
		entity.setCarSyr(findBy.getCarSyr());//机动车所有人


//		baseMapper.updateByPrimaryKeySelective(entity);
//		warnService.updCarWarnDate(entity.getId());
		ApiResponse<String> warnType=warnService.batchAnnualSave(entity,findBy);
		if(warnType.isSuccess()){
			baseMapper.updateByPrimaryKeySelective(entity);
			warnService.updCarWarnDate(entity.getId());
		}else{
			return warnType;
		}

		return ApiResponse.success();
	}

	/**
	 * 修改车辆电子档
	 * @param entity
	 * @return
	 */
	public ApiResponse<String> updERecode(BizCar entity){
		List<BizCarFile> carFilesList=new ArrayList<>();

		SysYh user=getCurrentUser(true);
		RuntimeCheck.ifFalse(StringUtils.equals(user.getJgdm(),"100"), "您所在的角色无权修改车辆信息。");
//		1、非空验证
		RuntimeCheck.ifBlank(entity.getId(), "该车辆不存在，请核实");

		BizCar findBy=this.findById(entity.getId());
		RuntimeCheck.ifNull(findBy, "该车辆不存在，请核实");
		RuntimeCheck.ifFalse(StringUtils.indexOf(findBy.getJgdm(),user.getJgdm())>-1, "该车辆属于："+findBy.getJgmc()+" 您无权编辑它");

//		2、电子证验证
//		2-1、电子证验证 检查机动机电子证
		findBy.setDzdDjzCode(entity.getDzdDjzCode());//机动车登记证书编号
		if(StringUtils.isNotEmpty(entity.getDzdDjzFileurl())){
//			carFilesList
			findBy.setDzdDjzFileurl(entity.getDzdDjzFileurl());
			findBy.setDzdDjzType("1");
		}else{
			findBy.setDzdDjzFileurl("");
			findBy.setDzdDjzType("0");
		}
//		2-2、电子证验证 检查机动机电子证
		findBy.setDzdWszmCode(entity.getDzdWszmCode());//购置税完税证明证号
		findBy.setDzdWszmPh(entity.getDzdWszmPh());//购置税完税证明票号
		if(StringUtils.isNotEmpty(entity.getDzdWszmFileurl())){
			findBy.setDzdWszmFileurl(entity.getDzdWszmFileurl());//购置税完税证明票号电子档路径
			findBy.setDzdWszmType("1");
		}else{
			findBy.setDzdWszmType("0");
			findBy.setDzdWszmFileurl("");
		}
//		2-3、电子证验证 发票状态
		findBy.setDzdFpCode(entity.getDzdFpCode());
		if(StringUtils.isNotEmpty(entity.getDzdFpFileurl())){
			findBy.setDzdFpFileurl(entity.getDzdFpFileurl());
			findBy.setDzdFpType("1");
		}else{
			findBy.setDzdFpFileurl("");
			findBy.setDzdFpType("0");
		}
//		2-4、电子证验证 出厂合格证明
		if(StringUtils.isNotEmpty(entity.getDzdDrysFileurl())){
			findBy.setDzdDrysFileurl(entity.getDzdDrysFileurl());
			findBy.setDzdDrysFileurl("1");
		}else{
			findBy.setDzdDrysFileurl("");
			findBy.setDzdDrysFileurl("0");
		}
//		2-5、电子证验证 行驶证扫描件状态
		if(StringUtils.isNotEmpty(entity.getDzdXszfyFileurl())){
			findBy.setDzdXszfyFileurl(entity.getDzdXszfyFileurl());
			findBy.setDzdXszfyType("1");
		}else{
			findBy.setDzdXszfyFileurl("");
			findBy.setDzdXszfyType("0");
		}


		baseMapper.updateByPrimaryKeySelective(findBy);
		return ApiResponse.success();
	}


	/**
	 * 车辆所有人变更
	 * 1、将主表，复制到历史表中
	 * 2、清除车辆主表的所有信息
	 * 3、
	 * @param entity
	 * @return
	 */
	public ApiResponse<String> updCarOwner(BizCar entity){
//		1、检查权限
		SysYh user=getCurrentUser(true);
		RuntimeCheck.ifFalse(StringUtils.equals(user.getJgdm(),"100"), "您所在的角色无权做车辆所有人变更。");
//		2、非空验证
		RuntimeCheck.ifBlank(entity.getId(), "请选择需要变更所有人的车辆。");
		RuntimeCheck.ifBlank(entity.getCarSyr(), "请确认变更后的机动车所有人。");

//		3、验证当前用户是否有权限修改这台车数据。
		BizCar findBy=this.findById(entity.getId());
		RuntimeCheck.ifNull(findBy, "该车辆不存在，请核实。");
		RuntimeCheck.ifTrue(StringUtils.equals(findBy.getCarPropertyType(),"4"), "该车辆已经售出，不能做所有人变更");
		RuntimeCheck.ifTrue(StringUtils.equals(findBy.getCarPropertyType(),"3"), "该车辆已经报废，不能做所有人变更");

		String carHistoryId=genId();
		findBy.setCarHistoryId(carHistoryId);
//		4、将车辆主表信息，复制到历史表中
		baseMapper.saveHistoryId(entity.getId(),carHistoryId,user.getZh()+"-"+user.getXm(),DateUtils.getNowTime());
//		5、清除备案人、运管、改气

		findBy.setCarSyr(entity.getCarSyr());//机动车所有人
		findBy.setSrydzlx(entity.getSrydzlx());//所有人_身份证件类型
		findBy.setSrycode(entity.getSrycode());//所有人_证件号码

		//运管
		findBy.setYgYszh(null);
		findBy.setWarnYgnsDate(null);//运管运输证号
		findBy.setYgGpsType(null);//是否安装GPS
		findBy.setYgGpsCode(null);//设置运管 GPS号码
		findBy.setYgYyType(null);//车辆备案_运营状态
		findBy.setYgYsnSx(null);//运管 1、14年上线
		findBy.setYgGx(null);//运管 更新(BU列)
		findBy.setYgZj(null);//运管 轴距
		findBy.setYgKjType(null);//车辆备案_卡机安装状态
		findBy.setYgKjAzsj(null);//运管 卡机安装时间
		findBy.setYgKjPc(null);//卡机批次
		findBy.setYgNewCode(null);//明涛成功新证号
		findBy.setYgNewKj(null);//新卡机
		// 改气
		findBy.setGxType(null);//是否改气
		findBy.setGxGasDd(null);//改气地点
		findBy.setGxGasGgzyxq(null);//改气合格证有效期
		findBy.setGxGasBz(null);//改气备注
		findBy.setGxLxr(null);//改气 联系人
		findBy.setGxLxDn(null);//改气 联系人电话

		//备案人
		findBy.setJsyid(null);//备案人ID
		findBy.setJsyxm(null);//备案人姓名
		findBy.setJsysfzh(null);//备案人身份证号
		findBy.setJsylxdh(null);//备案人联系电话
		findBy.setCjType("0");//备案人是否采集
		findBy.setJsycjsj(null);//备案人信息采集时间
		findBy.setJsydzda(null);//备案人信息电子档案
		findBy.setJsybz(null);//备案人信息备注

		findBy.setJsysfzzm(null);//备案人身份证正面
		findBy.setJsysfzfm(null);//备案人身份证反面
		findBy.setJsyjszzm(null);//备案人驾驶证正面
		findBy.setJsyjszfm(null);//备案人驾驶证反面

		findBy.setJsyzjcx(null);//备案人准架车型
		findBy.setJsyxb(null);//备案人性别

		baseMapper.updateByPrimaryKey(findBy);
		return ApiResponse.success();
	}

	@Override
	public ApiResponse<String> updCarRecordHolder(BizCar entity){
//		1、检查权限
		SysYh user=getCurrentUser(true);
		RuntimeCheck.ifFalse(StringUtils.equals(user.getJgdm(),"100"), "您所在的角色无权做车辆备案人变更。");
//		2、非空验证
		RuntimeCheck.ifBlank(entity.getId(), "请选择需要变更备案人的车辆。");
		RuntimeCheck.ifBlank(entity.getJsyxm(), "请填写备案人姓名。");
//		RuntimeCheck.ifBlank(entity.getJsysfzh(), "请填写备案人身份证号。");
//		RuntimeCheck.ifBlank(entity.getJsylxdh(), "请填写备案人联系电话。");
//		RuntimeCheck.ifBlank(entity.getJsydzda(), "请填写备案人信息电子档案。");

//		3、验证当前用户是否有权限修改这台车数据。
		BizCar findBy=this.findById(entity.getId());
		RuntimeCheck.ifNull(findBy, "该车辆不存在，请核实。");
		RuntimeCheck.ifTrue(StringUtils.equals(findBy.getCarPropertyType(),"4"), "该车辆已经售出，无需设置备案人");
		RuntimeCheck.ifTrue(StringUtils.equals(findBy.getCarPropertyType(),"3"), "该车辆已经报废，无需设置备案人");

		findBy.setJsyid(entity.getJsyid());//备案人ID
		findBy.setJsyxm(entity.getJsyxm());//备案人姓名
		findBy.setJsysfzh(entity.getJsysfzh());//备案人身份证号
		findBy.setJsylxdh(entity.getJsylxdh());//备案人联系电话
		findBy.setJsydzda(entity.getJsydzda());//备案人信息电子档案
		findBy.setJsybz(entity.getJsybz());//备案人信息备注

		findBy.setJsysfzzm(entity.getJsysfzzm());//备案人身份证正面
		findBy.setJsysfzfm(entity.getJsysfzfm());//备案人身份证反面
		findBy.setJsyjszzm(entity.getJsyjszzm());//备案人驾驶证正面
		findBy.setJsyjszfm(entity.getJsyjszfm());//备案人驾驶证反面

		//计算备案人性别
		String jsyxb=entity.getJsyxb();
		if(org.apache.commons.lang.StringUtils.isNotBlank(jsyxb)){
			try {
				Map<Integer,String> xbMap=new HashMap<>();
				xbMap.put(0,"00");
				xbMap.put(1,"10");
				String zjhm=entity.getJsysfzh();
				String xb= org.apache.commons.lang.StringUtils.substring(zjhm,16,17);
				if(org.apache.commons.lang.StringUtils.isNotBlank(xb)){
					int i = Integer.parseInt(xb);
					jsyxb=xbMap.get(i%2);
				}
			}catch (Exception e){}
		}
		findBy.setJsyxb(jsyxb);//备案人性别

		findBy.setJsyzjcx(entity.getJsyzjcx());//备案人准架车型
		this.update(findBy);
		//车辆年审信息备案人信息同步
		SimpleCondition condition = new SimpleCondition(BizCarAnnualExam.class);
		condition.eq(BizCarAnnualExam.InnerColumn.clId,entity.getId());
		condition.eq(BizCarAnnualExam.InnerColumn.nslx,"001");
		condition.and().andNotEqualTo(BizCarAnnualExam.InnerColumn.zt.name(),"1");
		condition.setOrderByClause(BizCarAnnualExam.InnerColumn.id.desc());
		List<BizCarAnnualExam> list=carAnnualExamService.findByCondition(condition);
		if(list!=null||list.size()>0){
			for(BizCarAnnualExam l:list){
				l.setJsyid(entity.getJsyid());//备案人ID
				l.setJsyxm(entity.getJsyxm());//备案人姓名
				l.setJsysfzh(entity.getJsysfzh());//备案人身份证号
				l.setJsylxdh(entity.getJsylxdh());//备案人联系电话
				l.setJsydzda(entity.getJsydzda());//备案人信息电子档案
				l.setJsybz(entity.getJsybz());//备案人信息备注
				carAnnualExamService.update(l);
			}
		}
		return ApiResponse.success();
	}

	@Override
	public ApiResponse<List<Map<String, String>>> getHolderHistory(String clId){
		List<Map<String, String>> retList=new ArrayList<>();
		RuntimeCheck.ifBlank(clId, "请选择需要变更所有人的车辆。");

		BizCar findBy=this.findById(clId);
		RuntimeCheck.ifNull(findBy, "该车辆不存在，请核实。");

		String dah=findBy.getDah();//档案号

		SimpleCondition condition = new SimpleCondition(BizCarHistory.class);
		condition.eq(BizCarHistory.InnerColumn.dah, dah);
		condition.setOrderByClause(BizCarHistory.InnerColumn.id.asc());
		List<BizCarHistory> list = carHistoryService.findByCondition(condition);

		//车辆所有人变更时间
		String carSyrBgsj=findBy.getCcdjrq();//初次登记日期
		if(list!=null&&list.size()>0){
			for(BizCarHistory l:list){
				Map<String, String> mapa=new HashMap<>();
				mapa.put("cph",l.getCph());//车牌号
				mapa.put("dah",l.getDah());//档案号
				mapa.put("carSyrBgsj",carSyrBgsj);//车辆所有人变更时间
				mapa.put("carSyr",l.getCarSyr());//机动车所有人
				mapa.put("carSryCode",l.getSrycode());//所有人_证件号码
				mapa.put("carSryZjType",l.getSrydzlx());//所有人_身份证件类型
				retList.add(mapa);
				carSyrBgsj=l.getCarSyrBgsj();
				if(StringUtils.isEmpty(carSyrBgsj)){
					carSyrBgsj="";
				}else{
					if(carSyrBgsj.length()>=10){
						carSyrBgsj=carSyrBgsj.substring(0,10);
					}
				}
			}
		}
		Map<String, String> map=new HashMap<>();
		map.put("cph",findBy.getCph());//车牌号
		map.put("dah",findBy.getDah());//档案号
		map.put("carSyr",findBy.getCarSyr());//机动车所有人
		map.put("carSryCode",findBy.getSrycode());//所有人_证件号码
		map.put("carSryZjType",findBy.getSrydzlx());//所有人_身份证件类型
		map.put("carSyrBgsj",carSyrBgsj);//ccdjrq
		retList.add(map);

		return ApiResponse.success(retList);
	}

	@Override
	public ApiResponse<Map<String, Object>> getCarArchives(String clId){
//		List<Map<String, String>> retList=new ArrayList<>();
		Map<String, Object> retMap=new HashMap<>();
		RuntimeCheck.ifBlank(clId, "请选择需要变更所有人的车辆。");

		BizCar findBy=this.findById(clId);
		RuntimeCheck.ifNull(findBy, "该车辆不存在，请核实。");
		retMap.put("car",findBy);
		Map<String, String> fileMap=new HashMap<>();//图片地址
//		行驶证
		String xszzm="";//行驶证正面
		String xszfm="";//行驶证反面
		if(StringUtils.isNotEmpty(findBy.getDzdXszfyFileurl())){
			String dzdXszfyFileurl=findBy.getDzdXszfyFileurl();//行驶证扫描件状态 电子档路径
			String[] xsz=StringUtils.split(dzdXszfyFileurl, ',');
			if(xsz!=null&&xsz.length>0){
				xszzm=xsz[0];
				if(xsz.length>1){
					xszfm=xsz[1];
				}
			}
		}

		String yyzzUrl="";//营业执照地址
		String frzZmUrl="";//法人证件正面
		String frzBmUrl="";//法人证件背面
		String wtrzjBmUrl="";//委托人证件背面
		String wtrzjZmUrl="";//委托人证件正面
		String wtrzjQmUrl="";//委托人签名

		List<BizCarSyr> syrList = bizCarSyrService.findAll();
		Map<String, BizCarSyr> syrMap =null;
		if(syrList!=null&&syrList.size()>0){
			syrMap = syrList.stream().collect(Collectors.toMap(BizCarSyr::getId, p -> p));
		}

		String carSyr=findBy.getCarSyr();//机动车所有人
		String carQy=findBy.getCarQy();//所属区域
		if(StringUtils.isNotEmpty(carSyr)&&syrMap!=null){
			BizCarSyr syr=syrMap.get(carSyr);
			if(syr!=null){
				yyzzUrl=syr.getYyzzUrl();//营业执照地址
				frzZmUrl=syr.getFrzZmUrl();//法人证件正面
				frzBmUrl=syr.getFrzBmUrl();//法人证件背面
				wtrzjBmUrl=syr.getWtrzjBmUrl();//委托人证件背面
				wtrzjZmUrl=syr.getWtrzjZmUrl();//委托人证件正面
				wtrzjQmUrl=syr.getWtrzjQmUrl();//委托人签名
			}
		}

		fileMap.put("xszzm",xszzm);//行驶证正面
		fileMap.put("xszfm",xszfm);//行驶证反面

		fileMap.put("jsysfzzm",findBy.getJsysfzzm());//备案人身份证正面
		fileMap.put("jsysfzfm",findBy.getJsysfzfm());//备案人身份证反面
		fileMap.put("jsyjszzm",findBy.getJsyjszzm());//备案人驾驶证正面
		fileMap.put("jsyjszfm",findBy.getJsyjszfm());//备案人驾驶证反面

		fileMap.put("yyzzUrl",yyzzUrl);//营业执照地址
		fileMap.put("frzZmUrl",frzZmUrl);//法人证件正面
		fileMap.put("frzBmUrl",frzBmUrl);//法人证件背面
		fileMap.put("wtrzjBmUrl",wtrzjBmUrl);//委托人证件背面
		fileMap.put("wtrzjZmUrl",wtrzjZmUrl);//委托人证件正面
		fileMap.put("wtrzjQmUrl",wtrzjQmUrl);//委托人签名


		retMap.put("fileUrl",fileMap);

		List<Map<String, String>> retList=new ArrayList<>();
		String dah=findBy.getDah();//档案号

		SimpleCondition condition = new SimpleCondition(BizCarHistory.class);
		condition.eq(BizCarHistory.InnerColumn.dah, dah);
		condition.setOrderByClause(BizCarHistory.InnerColumn.id.asc());
		List<BizCarHistory> list = carHistoryService.findByCondition(condition);

		String jyxkzh="";//经营许可证号
		String jjlx="";//经济类型
		String jyzzfs="";//经营组织方式
		String zzdj="";//资质等级
		String yjfw="";//经营范围
		String dlyszh="";//道路运输证号
		String whzq="";//二级维护周期

		//车辆所有人变更时间
		String carSyrBgsj=findBy.getCcdjrq();//初次登记日期
		if(list!=null&&list.size()>0){
			for(BizCarHistory l:list){
				Map<String, String> mapa=new HashMap<>();
				mapa.put("cph",l.getCph());//车牌号
				mapa.put("dah",l.getDah());//档案号
				mapa.put("carSyrBgsj",carSyrBgsj);//车辆所有人变更时间
				String carSyr1=l.getCarSyr();


				if(StringUtils.isNotEmpty(carSyr1)){
					BizCarSyr syr=syrMap.get(carSyr1);
					if(syr!=null){
						jyxkzh=syr.getJyxkzh();//经营许可证号
						jjlx=syr.getJjlx();//经济类型
						jyzzfs=syr.getJyzzfs();//经营组织方式
						zzdj=syr.getZzdj();//资质等级
						yjfw=syr.getYjfw();//经营范围
						dlyszh=syr.getDlyszh();//道路运输证号
						whzq=syr.getWhzq();//二级维护周期
					}
				}
				mapa.put("jyxkzh",jyxkzh);//经营许可证
				mapa.put("jjlx",jjlx);//经济类型
				mapa.put("jyzzfs",jyzzfs);//经营组织方
				mapa.put("zzdj",zzdj);//资质等级
				mapa.put("yjfw",yjfw);//经营范围
				mapa.put("dlyszh",dlyszh);//道路运输证
				mapa.put("whzq",whzq);//二级维护周期

				mapa.put("carSyr",l.getCarSyr());//机动车所有人
				mapa.put("carSryCode",l.getSrycode());//所有人_证件号码
				mapa.put("carSryZjType",l.getSrydzlx());//所有人_身份证件类型
				retList.add(mapa);
				carSyrBgsj=l.getCarSyrBgsj();
				if(StringUtils.isEmpty(carSyrBgsj)){
					carSyrBgsj="";
				}else{
					if(carSyrBgsj.length()>=10){
						carSyrBgsj=carSyrBgsj.substring(0,10);
					}
				}
			}
		}
		Map<String, String> map=new HashMap<>();
		map.put("cph",findBy.getCph());//车牌号
		map.put("dah",findBy.getDah());//档案号
		map.put("carSyr",findBy.getCarSyr());//机动车所有人
		map.put("carSryCode",findBy.getSrycode());//所有人_证件号码
		map.put("carSryZjType",findBy.getSrydzlx());//所有人_身份证件类型
		map.put("carSyrBgsj",carSyrBgsj);//ccdjrq

		if(StringUtils.isNotEmpty(findBy.getCarSyr())){
			BizCarSyr syr=syrMap.get(findBy.getCarSyr());
			if(syr!=null){
				jyxkzh=syr.getJyxkzh();//经营许可证号
				jjlx=syr.getJjlx();//经济类型
				jyzzfs=syr.getJyzzfs();//经营组织方式
				zzdj=syr.getZzdj();//资质等级
				yjfw=syr.getYjfw();//经营范围
				dlyszh=syr.getDlyszh();//道路运输证号
				whzq=syr.getWhzq();//二级维护周期
			}
		}
		map.put("jyxkzh",jyxkzh);//经营许可证
		map.put("jjlx",jjlx);//经济类型
		map.put("jyzzfs",jyzzfs);//经营组织方
		map.put("zzdj",zzdj);//资质等级
		map.put("yjfw",yjfw);//经营范围
		map.put("dlyszh",dlyszh);//道路运输证
		map.put("whzq",whzq);//二级维护周期

		retList.add(map);

		retMap.put("retList",retList);

		return ApiResponse.success(retMap);
	}
}