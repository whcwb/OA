package com.ldz.biz.car.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ldz.biz.car.mapper.BizCarAnnualExamMapper;
import com.ldz.biz.car.model.BizCar;
import com.ldz.biz.car.model.BizCarAnnualExam;
import com.ldz.biz.car.model.BizCarWarn;
import com.ldz.biz.car.service.BizCarAnnualExamService;
import com.ldz.biz.car.service.BizCarService;
import com.ldz.biz.car.service.BizCarWarnService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.sys.model.SysYh;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BizCarAnnualExamServiceImpl extends BaseServiceImpl<BizCarAnnualExam, String> implements BizCarAnnualExamService {

	@Autowired
	private BizCarAnnualExamMapper baseMapper;

	@Autowired
	private BizCarService carService;

	@Autowired
	private BizCarWarnService carWarnService;

	
	@Override
	protected Mapper<BizCarAnnualExam> getBaseMapper() {
		return baseMapper;
	}


	@Override
	public ApiResponse<String> updKeepOnRecord(BizCarAnnualExam exam){
		BizCarAnnualExam fin=this.findById(exam.getId());
		RuntimeCheck.ifNull(fin,"年审记录有误，请刷新页面后重新尝试");
		RuntimeCheck.ifFalse(StringUtils.equals(fin.getNslx(),"001"),"非车管年审业务不需要设置备案信息");
		RuntimeCheck.ifBlank(exam.getJsyxm(),"请填写备份人姓名");
		RuntimeCheck.ifBlank(exam.getJsysfzh(),"请填写备案人身份证号");
		RuntimeCheck.ifBlank(exam.getJsylxdh(),"请填写备案人联系电话");
//		RuntimeCheck.ifBlank(exam.getJsycjsj(),"请填写备案人信息采集时间");
		String jsycjsj=exam.getJsycjsj();
		Date endDate=null;
		try {
			endDate=DateUtils.getDate(jsycjsj,"yyyy-MM-dd");
		}catch (Exception e){
//			return ApiResponse.fail("请输入正确的备案人信息采集时间 日期格式为：yyyy-MM-dd");
		}

		fin.setJsyxm(exam.getJsyxm());
		fin.setJsysfzh(exam.getJsysfzh());
		fin.setJsylxdh(exam.getJsylxdh());

		fin.setJsydzda(exam.getJsydzda());
		fin.setJsysfzzm(exam.getJsysfzzm());//备案人身份证正面
		fin.setJsysfzfm(exam.getJsysfzfm());//备案人身份证反面
		fin.setJsyjszzm(exam.getJsyjszzm());//备案人驾驶证正面
		fin.setJsyjszfm(exam.getJsyjszfm());//备案人驾驶证反面
		fin.setJsyzjcx(exam.getJsyzjcx());//备案人准架车型
		//计算备案人性别
		String jsyxb=exam.getJsyxb();
		if(StringUtils.isNotBlank(jsyxb)){
			try {
				Map<Integer,String> xbMap=new HashMap<>();
				xbMap.put(0,"00");
				xbMap.put(1,"10");
				String zjhm=fin.getJsysfzh();
				String xb=StringUtils.substring(zjhm,16,17);
				if(StringUtils.isNotBlank(xb)){
					int i = Integer.parseInt(xb);
					jsyxb=xbMap.get(i%2);
				}
			}catch (Exception e){}
		}
		fin.setJsyxb(jsyxb);//备案人性别

		fin.setJsybz(exam.getJsybz());
		if(endDate!=null){
			fin.setJsycjsj(DateUtils.getDateStr(endDate,"yyyy-MM-dd"));
			fin.setCjType("1");
		}else{
			fin.setJsycjsj(null);
			fin.setCjType("0");
		}
		baseMapper.updateByPrimaryKeySelective(fin);

		//获取车辆ID
		BizCar car=carService.findById(fin.getClId());
		car.setJsyid(fin.getJsyid());//备案人ID
		car.setJsyxm(fin.getJsyxm());//备案人姓名
		car.setJsysfzh(fin.getJsysfzh());//备案人身份证号
		car.setJsylxdh(fin.getJsylxdh());//备案人联系电话
		car.setCjType(fin.getCjType());//备案人是否采集
		car.setCjType(fin.getCjType());//备案人是否采集
		car.setJsycjsj(fin.getJsycjsj());//备案人信息采集时间
		car.setJsydzda(fin.getJsydzda());//备案人信息电子档案
		car.setJsybz(fin.getJsybz());//备案人信息备注
		car.setJsysfzzm(fin.getJsysfzzm());//备案人身份证正面
		car.setJsysfzfm(fin.getJsysfzfm());//备案人身份证反面
		car.setJsyjszzm(fin.getJsyjszzm());//备案人驾驶证正面
		car.setJsyjszfm(fin.getJsyjszfm());//备案人驾驶证反面

		car.setJsyzjcx(fin.getJsyzjcx());//备案人准架车型
		car.setJsyxb(fin.getJsyxb());//备案人性别

		carService.update(car);

		return ApiResponse.success();
	}
	@Override
	public ApiResponse<String> validAndUpdate(BizCarAnnualExam exam){
		SysYh user=getCurrentUser(true);
		RuntimeCheck.ifBlank(exam.getId(),"请选择记录");
		RuntimeCheck.ifBlank(exam.getXcncsj(),"请填写下次年审时间");
		Date endDate=null;
		try {
			endDate=DateUtils.getDate(exam.getXcncsj(),"yyyy-MM-dd");
		}catch (Exception e){
			return ApiResponse.fail("请输入正确的下次年审时间 日期格式为：yyyy-MM-dd");
		}
		RuntimeCheck.ifNull(endDate,"请选择下次年审时间");

		BizCarAnnualExam obd=this.findById(exam.getId());
		RuntimeCheck.ifNull(obd,"请选择要年审的记录");
		RuntimeCheck.ifTrue(StringUtils.equals(obd.getZt(),"1"),"本次年审已通过，无需重复年审。");
		String nslx=obd.getNslx();//年审类型  003	改气年审   002	运管年审   001	车管年审
		if(StringUtils.equals("001",nslx)) {//车管年审
			RuntimeCheck.ifFalse(StringUtils.equals(obd.getCjType(),"1"),"您好,采集人信息未采集，请在采集完成后，再进行年审操作");
		}

		BizCar car=carService.findById(obd.getClId());
//		exam.setJsyid(car.getSyrId());
//		exam.setJsyxm(car.getSyrName());
//		exam.setJsylxdh(car.getSyrDn());
//		exam.setJsysfzh(car.getsyr);
		exam.setZt("1");
		exam.setCjr(user.getZh()+"-"+user.getXm());
		exam.setCjsj(DateUtils.getNowTime());
		int i=baseMapper.updateByPrimaryKeySelective(exam);

		BizCarWarn warn =new BizCarWarn();
		warn.setId(genId());
		warn.setClId(obd.getClId());
		warn.setCph(car.getCph());
		warn.setWarnDispose("0");

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(endDate);
		calendar.add(Calendar.DATE, -90);
		String warnDate = DateUtils.getDateStr(calendar.getTime(), "yyyy-MM-dd");//提醒日期

		String expiryDate = DateUtils.getDateStr(endDate, "yyyy-MM-dd");//截止日期
		warn.setExpiryDate(expiryDate);
		warn.setExpiryYyyy(expiryDate.substring(0, 4));//截止年
		warn.setExpiryMm(expiryDate.substring(5, 7));//截止月
		warn.setWarnDate(warnDate);

		if(i>0){

			if(StringUtils.equals("001",nslx)){//车管年审
				car.setNsNsz(obd.getNsz());//年审至
				car.setNsNssj(obd.getNssj());//年审时间
				car.setNsJsyid(exam.getJsyid());//驾驶员ID
				car.setNsJsyxm(exam.getJsyxm());//驾驶员姓名
				car.setNsJsylxdh(exam.getJsylxdh());//驾驶员联系电话
				car.setNsZt("1");//年审状态
				car.setCjType("0");
				car.setJsycjsj("");//清空  备案人信息采集时间
				warn.setWarnType("1");

			}else if(StringUtils.equals("002",nslx)){//运管年审
				warn.setWarnType("2");
			}else if(StringUtils.equals("003",nslx)){//改气年审
				warn.setWarnType("3");
			}
			carService.update(car);
		}
		BizCarWarn delWarn =new BizCarWarn();
		delWarn.setClId(obd.getClId());
		delWarn.setWarnType(warn.getWarnType());
		delWarn.setWarnDispose("1");
		carWarnService.remove(delWarn);

		BizCarWarn carWarn=new BizCarWarn();
		carWarn.setId(obd.getWarnId());
		carWarn.setWarnDispose("1");
		carWarnService.update(carWarn);

		carWarnService.save(warn);

		carWarnService.updCarWarnDate(obd.getClId());


		return ApiResponse.success();
	}

	@Override
	public ApiResponse<List<BizCarAnnualExam>> getCarAnnualExamList(String clId){
		RuntimeCheck.ifBlank(clId, "请选择车辆的车辆。");
		SimpleCondition condition = new SimpleCondition(BizCarAnnualExam.class);
		condition.eq(BizCarAnnualExam.InnerColumn.clId,clId);
		condition.setOrderByClause(BizCarAnnualExam.InnerColumn.id.desc());
		List<BizCarAnnualExam> list=this.findByCondition(condition);
		if(list==null||list.size()<1){
			return ApiResponse.success(new Page<>());
		}
		return ApiResponse.success(list);

	}

	@Override
	public ApiResponse<Map<String, Long>> getPager(Page<BizCarAnnualExam> page) {
		ApiResponse<Map<String,Long>> result = new ApiResponse<>();

		LimitedCondition queryCondition = getQueryCondition();
		PageInfo<BizCarAnnualExam> pageInfo = findPage(page, queryCondition);

		result.setPage(pageInfo);

		SimpleCondition condition = new SimpleCondition(BizCarAnnualExam.class);
		// 所有未年审的车辆
		condition.and().andCondition(" zt != '1' ");
		List<BizCarAnnualExam> exams = findByCondition(condition);
		Map<String, Long> longMap = null;
		if(CollectionUtils.isNotEmpty(exams)) {

			longMap = exams.stream().collect(Collectors.groupingBy(BizCarAnnualExam::getNslx, Collectors.counting()));
			if (!longMap.containsKey("001")) {
				longMap.put("001", 0L);
			}
			if (!longMap.containsKey("002")) {
				longMap.put("002", 0L);
			}
			if (!longMap.containsKey("003")) {
				longMap.put("003", 0L);
			}

		}else {
			longMap = new HashMap<>();
			longMap.put("001",0L);
			longMap.put("002",0L);
			longMap.put("003",0L);
		}
		result.setResult(longMap);
		return result;
	}





}