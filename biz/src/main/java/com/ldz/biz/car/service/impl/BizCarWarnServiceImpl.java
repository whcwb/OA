package com.ldz.biz.car.service.impl;

import com.github.pagehelper.PageInfo;
import com.ldz.biz.car.mapper.BizCarAnnualExamMapper;
import com.ldz.biz.car.mapper.BizCarWarnMapper;
import com.ldz.biz.car.model.*;
import com.ldz.biz.car.service.*;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BizCarWarnServiceImpl extends BaseServiceImpl<BizCarWarn, String> implements BizCarWarnService {

	@Autowired
	private BizCarWarnMapper baseMapper;


	@Autowired
	private BizCarAnnualExamMapper carAnnualExamMapper;
	@Autowired
	private BizCarAnnualExamService carAnnualExamService;

	@Autowired
	private BizCarService bizCarService;

	@Autowired
	private BizCarService carService;

	@Autowired
	private BizCarBaService carBaService;
	@Autowired
	private BizCarGasService carGasService;
	@Override
	protected Mapper<BizCarWarn> getBaseMapper() {
		return baseMapper;
	}
	/**
	 * 分页补充
	 * 	queryExpireType	1、查询到期提醒 否则查询所有提醒
	 * 	queryWarnTypeList	类型(1、车辆年审提醒 2、运管年审提醒 3、改气年审提醒 4、续保提醒 5、报废提醒)
	 *
	 * @param condition
	 * @return
	 */
	@Override
	public boolean fillPagerCondition(LimitedCondition condition){
		HttpServletRequest requset = getRequset();
		//提醒类型(1、车辆年审提醒 2、运管年审提醒 3、改气年审提醒 4、续保提醒 5、报废提醒)
		String queryWarnType = requset.getParameter("queryWarnTypeList");
		if(StringUtils.isNotBlank(queryWarnType)){
			List<String> queryWarnTypeList = Arrays.asList(queryWarnType.split(","));
			if(queryWarnTypeList!=null&&queryWarnTypeList.size()>0){
				condition.in(BizCarWarn.InnerColumn.warnType, queryWarnTypeList);
			}
		}
		String queryExpireType = requset.getParameter("queryExpireType");
		if(StringUtils.equals(queryExpireType,"1")){
			condition.lte(BizCarWarn.InnerColumn.warnDate, DateUtils.getToday());
		}
		condition.setOrderByClause(BizCarWarn.InnerColumn.expiryDate.asc());
		return true;
	}
	@Override
	protected void afterPager(PageInfo<BizCarWarn> resultPage){
		List<BizCarWarn> list = resultPage.getList();
		if(CollectionUtils.isNotEmpty(list)){
			List<String > ids = list.stream().map(BizCarWarn::getClId).collect(Collectors.toList());

			SimpleCondition condition = new SimpleCondition(BizCar.class);
			condition.in(BizCar.InnerColumn.id.name(),ids);
			List<BizCar> clList = bizCarService.findByCondition(condition);

			Map<String,BizCar> userMap = clList.stream().collect(Collectors.toMap(BizCar::getId,p->p));

			for(BizCarWarn warn:list){
				String clId=warn.getClId();
				if (!userMap.containsKey(clId))continue;
				BizCar car = userMap.get(clId);
				if(car!=null){
					warn.setClCqr(car.getClCqr());
					warn.setClCqrDn(car.getClCqrDn());
					warn.setClCqrCode(car.getClCqrCode());
					warn.setCarDetailed(car);
				}
			}
		}
		return;
	}


	/**
	 * 计算出 车辆年审提醒、强制报废
	 * 按
	 * 初次登记日期
	 * 强制报废期
	 * 车辆_号牌种类 [ZDCLK1036] 1、学牌 2、地牌
	 * 准驾车型 [ZDCLK0040]    C1,C2,A1,A2,A3,B2
	 * 来计算出 车辆年审信息
	 * @param cl
	 */
	@Override
	public ApiResponse<String> batchAnnualSave(BizCar cl,BizCar findByCar){
		List<String> warnTypeList=new ArrayList<>();
//		1、数据非空验证
		String clId=cl.getId();
		if (StringUtils.isBlank(clId)){
			return ApiResponse.fail("车辆ID不能为空");
		}
		String ccdjrq=cl.getCcdjrq();//初次登记日期
		if (StringUtils.isBlank(ccdjrq)){
			return ApiResponse.fail("初次登记日期不能为空");
		}

		String hpzl=cl.getHpzl();
		if (StringUtils.isBlank(hpzl)){
			return ApiResponse.fail("号牌种类不能为空");
		}
		String pxcx=cl.getPxcx();
		if (StringUtils.isBlank(pxcx)){
			return ApiResponse.fail("培训车型不能为空");
		}
//		2、数据有效性验证
//		if (!(org.apache.commons.lang3.StringUtils.indexOf(",C1,C2,A1,A2,A3,B2",","+pxcx)>-1)){
//			return ApiResponse.fail("培训车型填写错误");
//		}
//		if (!(org.apache.commons.lang3.StringUtils.indexOf(",1,2",","+hpzl)>-1)){
//			return ApiResponse.fail("号牌种类填写错误");
//		}
		Date startDate=null;
		Date endDate=null;
		try {
			startDate=DateUtils.getDate(ccdjrq,"yyyy-MM-dd");
		}catch (Exception e){
			return ApiResponse.fail("请输入正确的初次登记日期 日期格式为：yyyy-MM-dd");
		}
		try {
			String qzbfq=cl.getQzbfq();
			if (StringUtils.isNotBlank(qzbfq)){
				endDate=DateUtils.getDate(qzbfq,"yyyy-MM-dd");
			}
		}catch (Exception e){
			return ApiResponse.fail("请输入正确的强制报废期 日期格式为：yyyy-MM-dd");
		}
//		3、计算出所有的年审提醒日期
//		规则：
// 			教练车 5年内1年一审 超过5年一年2审 2审的间隔为6个月
// 			普通小车 6年内2年一审 超过6年一年1审
// 			普通大车 一年1审
// 		其它规则：
// 			a)计算出来的提醒 截止日期 必须小于  强制报废期
// 			b)计算出来的提醒 截止日期 必须大于  当前时间
		int seq=0;
		String seqType=genId();
		List<BizCarWarn> batchList=new ArrayList<>();
		if(findByCar==null){
			findByCar=new BizCar();
		}
		if(!StringUtils.equals(ccdjrq,findByCar.getCcdjrq())) {
			if (StringUtils.equals(hpzl, "1")) {//1、学牌 2、地牌
//			学牌车 5年内1年1审  超过5年一年二审
				//遍历出从开始时间后20年，所有的年审记录

				for (int i = 0; i < 21 * 2 + 1; i++) {
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(startDate);
					int amount = i * 6;
					//前5年一年一审
					int year = 5;
					String warnPc = "1";//计算一年当中的批次。
					if (i / 2 > year) {
						warnPc = (i % 2 == 0 ? 1 : 2) + "";
					}
					if (i / 2 <= year && i % 2 == 1) {
						continue;
					}
					calendar.add(Calendar.MONTH, amount);// 把日期往后增加指定月份.整数往后推,负数往前移动
					String expiryDate = DateUtils.getDateStr(calendar.getTime(), "yyyy-MM-dd");//截止日期
					if (endDate != null && calendar.getTime().compareTo(endDate) > 0) {
//					年审时间大于截止日期 就跳出FOR
						break;
					}
					if (calendar.getTime().compareTo(new Date()) > 0) {//提醒日期要大于当前时间
						calendar.add(Calendar.DATE, -90);
						String warnDate = DateUtils.getDateStr(calendar.getTime(), "yyyy-MM-dd");//提醒日期

						BizCarWarn obd = new BizCarWarn();
						obd.setId(genId());
						obd.setClId(cl.getId());
						obd.setCph(cl.getCph());
						obd.setWarnDate(warnDate);
						obd.setExpiryYyyy(expiryDate.substring(0, 4));//截止年
						obd.setExpiryMm(expiryDate.substring(5, 7));//截止月
						obd.setWarnType("1");
						obd.setWarnPc(warnPc);
						obd.setExpiryDate(expiryDate);//截止日期
						obd.setWarnDispose("0");//提醒是否处理
//						obd.setSeq(seq + "");
//						obd.setSeqType(seqType);
						batchList.add(obd);
						seq++;
                        break;
					}
				}
			} else {//非学牌
				for (int i = 0; i < 21 + 1; i++) {
					if (org.apache.commons.lang3.StringUtils.indexOf(pxcx, "C") > -1) {//小车
						//						小车6年内2年一审  超过6年 1年一审  	大车一年一审
						if (i < 7 && i % 2 == 1) {
							continue;
						}
					}
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(startDate);
					calendar.add(Calendar.YEAR, i);//
					if (endDate != null && calendar.getTime().compareTo(endDate) > 0) {
						//					年审时间大于截止日期 就跳出FOR
						break;
					}
					if (calendar.getTime().compareTo(new Date()) > 0) {//提醒日期要大于当前时间
						String expiryDate = DateUtils.getDateStr(calendar.getTime(), "yyyy-MM-dd");//截止日期
						calendar.add(Calendar.DATE, -90);
						String warnDate = DateUtils.getDateStr(calendar.getTime(), "yyyy-MM-dd");//提醒日期
						BizCarWarn obd = new BizCarWarn();
						obd.setId(genId());
						obd.setClId(cl.getId());
						obd.setCph(cl.getCph());
						obd.setWarnDate(warnDate);
						obd.setExpiryYyyy(expiryDate.substring(0, 4));//截止年
						obd.setExpiryMm(expiryDate.substring(5, 7));//截止月
						obd.setWarnType("1");
						obd.setWarnPc("1");
						obd.setExpiryDate(expiryDate);//截止日期
						obd.setWarnDispose("0");//提醒是否处理
//						obd.setSeq(seq + "");
//						obd.setSeqType(seqType);
						batchList.add(obd);
						seq++;
						break;
					}
				}
			}
			warnTypeList.add("1");
		}
//		4、生成强制报废的提醒
		if(endDate!=null&&!StringUtils.equals(cl.getQzbfq(),findByCar.getQzbfq())){
			String qzbfq=cl.getQzbfq();

			BizCarWarn obd=new BizCarWarn();
			obd.setId(genId());
			obd.setClId(cl.getId());
			obd.setCph(cl.getCph());

			Calendar calendar = new GregorianCalendar();
			calendar.setTime(endDate);
			calendar.add(Calendar.MONTH, -6);//强制报废的提醒 为提前6个月
			String warnDate=DateUtils.getDateStr(calendar.getTime(), "yyyy-MM-dd");//提醒日期
			obd.setWarnDate(warnDate);
			obd.setExpiryYyyy(qzbfq.substring(0,4));//截止年
			obd.setExpiryMm(qzbfq.substring(5,7));//截止月
			obd.setWarnType("5");
			obd.setWarnPc("1");
			obd.setExpiryDate(qzbfq);//截止日期
			obd.setWarnDispose("0");//提醒是否处理
//			obd.setSeq(0+"");
//			obd.setSeqType(genId());
			batchList.add(obd);
			warnTypeList.add("5");
		}
//		5、删除原来的提醒
		if(warnTypeList!=null&&warnTypeList.size()>0){
			// 删除旧数据
			SimpleCondition condition = new SimpleCondition(BizCarWarn.class);
			condition.eq(BizCarWarn.InnerColumn.clId,cl.getId());
			condition.in(BizCarWarn.InnerColumn.warnType,warnTypeList);
			baseMapper.deleteByExample(condition);
		}

//		6、批量插入提醒
		if(batchList!=null&&batchList.size()>0){
			baseMapper.insertList(batchList);
		}

		return ApiResponse.success();
	}

	/**
	 * 车辆年审信息提醒设置
	 * @param entity
	 *
	 * @return
	 */
	@Override
	public ApiResponse<String> updNcWarn(BizCar entity){
		String clId=entity.getId();
		if (StringUtils.isBlank(clId)){
			return ApiResponse.fail("车辆ID不能为空");
		}
		String ccdjrq=entity.getCcdjrq();
		if (StringUtils.isBlank(ccdjrq)){
			return ApiResponse.fail("初次登记日期不能为空");
		}
		BizCar cl=carService.findById(clId);
		RuntimeCheck.ifTrue(cl==null, "车辆信息有误，请刷新页后重新尝试。");

		String hpzl=cl.getHpzl();
		if (StringUtils.isBlank(hpzl)){
			return ApiResponse.fail("号牌种类不能为空");
		}
		String pxcx=cl.getPxcx();
		if (StringUtils.isBlank(pxcx)){
			return ApiResponse.fail("培训车型不能为空");
		}
//		2、数据有效性验证
//		todo 后期需要从字典表中获取
		if (!(org.apache.commons.lang3.StringUtils.indexOf(",C1,C2,A1,A2,A3,B2",","+pxcx)>-1)){
			return ApiResponse.fail("培训车型填写错误");
		}
		if (!(org.apache.commons.lang3.StringUtils.indexOf(",1,2",","+hpzl)>-1)){
			return ApiResponse.fail("号牌种类填写错误");
		}
		Date startDate=null;
		Date endDate=null;
		try {
			startDate=DateUtils.getDate(ccdjrq,"yyyy-MM-dd");
		}catch (Exception e){
			return ApiResponse.fail("请输入正确的初次登记日期 日期格式为：yyyy-MM-dd");
		}
		try {
			String qzbfq=cl.getQzbfq();
			if (StringUtils.isNotBlank(qzbfq)){
				endDate=DateUtils.getDate(qzbfq,"yyyy-MM-dd");
			}
		}catch (Exception e){
			return ApiResponse.fail("请输入正确的强制报废期 日期格式为：yyyy-MM-dd");
		}
//		3、计算出所有的年审提醒日期
//		规则：
// 			教练车 5年内1年一审 超过5年一年2审 2审的间隔为6个月
// 			普通小车 6年内2年一审 超过6年一年1审
// 			普通大车 一年1审
// 		其它规则：
// 			a)计算出来的提醒 截止日期 必须小于  强制报废期
// 			b)计算出来的提醒 截止日期 必须大于  当前时间
		int seq=0;
		String seqType=genId();
		List<BizCarWarn> batchList=new ArrayList<>();
		if(StringUtils.equals(hpzl,"1")){//1、学牌 2、地牌
//			学牌车 5年内1年1审  超过5年一年二审
			//遍历出从开始时间后20年，所有的年审记录
			for(int i=0;i<21*2+1;i++){
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(startDate);
				int amount=i*6;
				//前5年一年一审
				int year=5;
				String warnPc="1";//计算一年当中的批次。
				if(i/2>year){
					warnPc=(i%2==0?1:2)+"";
				}
				if(i/2<=year&&i%2==1){
					continue;
				}
				calendar.add(Calendar.MONTH, amount);// 把日期往后增加指定月份.整数往后推,负数往前移动
				String expiryDate=DateUtils.getDateStr(calendar.getTime(), "yyyy-MM-dd");//截止日期
				if(endDate!=null&&calendar.getTime().compareTo(endDate)>0 ){
//					年审时间大于截止日期 就跳出FOR
					break;
				}
				if( calendar.getTime().compareTo(new Date())>0 ){//提醒日期要大于当前时间
					calendar.add(Calendar.DATE, -90);
					String warnDate=DateUtils.getDateStr(calendar.getTime(), "yyyy-MM-dd");//提醒日期

					BizCarWarn obd=new BizCarWarn();
					obd.setId(genId());
					obd.setClId(cl.getId());
					obd.setCph(cl.getCph());
					obd.setWarnDate(warnDate);
					obd.setExpiryYyyy(expiryDate.substring(0,4));//截止年
					obd.setExpiryMm(expiryDate.substring(5,7));//截止月
					obd.setWarnType("1");
					obd.setWarnPc(warnPc);
					obd.setExpiryDate(expiryDate);//截止日期
					obd.setWarnDispose("0");//提醒是否处理
//					obd.setSeq(seq+"");
//					obd.setSeqType(seqType);
					batchList.add(obd);
					seq++;
					break;
				}
			}
		}else{//非学牌
			for(int i=0;i<21+1;i++){
				if(org.apache.commons.lang3.StringUtils.indexOf(pxcx,"C")>-1){//小车
					//						小车6年内2年一审  超过6年 1年一审  	大车一年一审
					if(i<7&&i%2==1){
						continue;
					}
				}
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(startDate);
				calendar.add(Calendar.YEAR, i);//
				if(endDate!=null&&calendar.getTime().compareTo(endDate)>0 ){
					//					年审时间大于截止日期 就跳出FOR
					break;
				}
				if( calendar.getTime().compareTo(new Date())>0 ){//提醒日期要大于当前时间
					String expiryDate=DateUtils.getDateStr(calendar.getTime(), "yyyy-MM-dd");//截止日期
					calendar.add(Calendar.DATE, -90);
					String warnDate=DateUtils.getDateStr(calendar.getTime(), "yyyy-MM-dd");//提醒日期
					BizCarWarn obd=new BizCarWarn();
					obd.setId(genId());
					obd.setClId(cl.getId());
					obd.setCph(cl.getCph());
					obd.setWarnDate(warnDate);
					obd.setExpiryYyyy(expiryDate.substring(0,4));//截止年
					obd.setExpiryMm(expiryDate.substring(5,7));//截止月
					obd.setWarnType("1");
					obd.setWarnPc("1");
					obd.setExpiryDate(expiryDate);//截止日期
					obd.setWarnDispose("0");//提醒是否处理
					batchList.add(obd);
					seq++;
					break;
				}
			}
		}

//		5、删除原来的提醒 todo 历史的记录是否要删除。
		BizCarWarn delObd=new BizCarWarn();
		delObd.setClId(cl.getId());
		delObd.setWarnType("1");
		baseMapper.delete(delObd);
//		6、批量插入提醒
		if(batchList!=null&&batchList.size()>0){
			baseMapper.insertList(batchList);
		}

		cl.setCcdjrq(ccdjrq);
		carService.update(cl);

		updCarWarnDate(cl.getId());
		return ApiResponse.success();
	}

	/**
	 * 强制报废日期信息提醒设置
	 * @param entity
	 *
	 * @return
	 */
	@Override
	public ApiResponse<String> updQzbfrqWarn(BizCar entity){
		String clId=entity.getId();
		if (StringUtils.isBlank(clId)){
			return ApiResponse.fail("车辆ID不能为空");
		}

		String qzbfq=entity.getQzbfq();
		if (StringUtils.isBlank(qzbfq)){
			return ApiResponse.fail("强制报废期不能为空");
		}

		Date endDate=null;
		try {
			if (StringUtils.isNotBlank(qzbfq)){
				endDate=DateUtils.getDate(qzbfq,"yyyy-MM-dd");
			}
		}catch (Exception e){
			return ApiResponse.fail("请输入正确的强制报废期 日期格式为：yyyy-MM-dd");
		}

		BizCar cl=carService.findById(clId);
		RuntimeCheck.ifTrue(cl==null, "车辆信息有误，请刷新页后重新尝试。");

		List<BizCarWarn> batchList=new ArrayList<>();
//		4、生成强制报废的提醒
		if(endDate!=null){
			BizCarWarn obd=new BizCarWarn();
			obd.setId(genId());
			obd.setClId(cl.getId());
			obd.setCph(cl.getCph());

			Calendar calendar = new GregorianCalendar();
			calendar.setTime(endDate);
			calendar.add(Calendar.MONTH, -6);//强制报废的提醒 为提前6个月
			String warnDate=DateUtils.getDateStr(calendar.getTime(), "yyyy-MM-dd");//提醒日期
			obd.setWarnDate(warnDate);
			obd.setExpiryYyyy(qzbfq.substring(0,4));//截止年
			obd.setExpiryMm(qzbfq.substring(5,7));//截止月
			obd.setWarnType("5");
			obd.setWarnPc("1");
			obd.setExpiryDate(qzbfq);//截止日期
			obd.setWarnDispose("0");//提醒是否处理
			batchList.add(obd);

		}
//		5、删除原来的提醒
		BizCarWarn delObd=new BizCarWarn();
		delObd.setClId(cl.getId());
		delObd.setWarnType("5");
		baseMapper.delete(delObd);
//		6、批量插入提醒
		if(batchList!=null&&batchList.size()>0){
			baseMapper.insertList(batchList);
		}

		cl.setQzbfq(qzbfq);
		carService.update(cl);

		updCarWarnDate(cl.getId());
		return ApiResponse.success();
	}


	/**
	 * 车辆运营管理年审提醒设置
	 * @param entity
	 *
	 * @return
	 */
	@Override
	public ApiResponse<String> updYgNcWarn(BizCar entity){
		String clId=entity.getId();
		if (StringUtils.isBlank(clId)){
			return ApiResponse.fail("车辆ID不能为空");
		}


		String expiryDate=entity.getXcncsj();
		if (StringUtils.isBlank(expiryDate)){
			return ApiResponse.fail("下次年审时间不能为空");
		}
		Date endDate=null;
		try {
			endDate=DateUtils.getDate(expiryDate,"yyyy-MM-dd");
		}catch (Exception e){
			return ApiResponse.fail("请输入正确的下次年审时间 日期格式为：yyyy-MM-dd");
		}

		BizCar cl=carService.findById(clId);
		RuntimeCheck.ifTrue(cl==null, "车辆信息有误，请刷新页后重新尝试。");


		List<BizCarWarn> batchList=new ArrayList<>();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(endDate);
		BizCarWarn obd = new BizCarWarn();
		obd.setId(genId());
		obd.setClId(cl.getId());
		obd.setCph(cl.getCph());
		calendar.add(Calendar.DATE, -90);
		String warnDate = DateUtils.getDateStr(calendar.getTime(), "yyyy-MM-dd");//提醒日期
		obd.setWarnDate(warnDate);
		obd.setExpiryYyyy(expiryDate.substring(0, 4));//截止年
		obd.setExpiryMm(expiryDate.substring(5, 7));//截止月
		obd.setWarnType("2");
		obd.setWarnPc("1");
		obd.setExpiryDate(expiryDate);//截止日期
		obd.setWarnDispose("0");//提醒是否处理
		batchList.add(obd);

//		5、删除原来的提醒 todo 历史的记录是否要删除。
		BizCarWarn delObd=new BizCarWarn();
		delObd.setClId(cl.getId());
		delObd.setWarnType("2");
		baseMapper.delete(delObd);
//		6、批量插入提醒
		if(batchList!=null&&batchList.size()>0){
			baseMapper.insertList(batchList);
		}
		cl.setYyCdrq(entity.getYyCdrq());
		cl.setYgYszh(entity.getYgYszh());//运管运输证号
		cl.setYgGpsType(entity.getYgGpsType());//运管 是否安装GPS
		cl.setYgGpsCode(entity.getYgGpsCode());//运管 GPS号码
		//运管 车辆备案_运营状态 [ZDCLK1033] 营运状态1、运营 2、注销 0、未登记
		String ygYyTYPE="0";
		if(StringUtils.isNotBlank(entity.getYgYyType())){
			ygYyTYPE=entity.getYgYyType();
		}
		cl.setYgYyType(ygYyTYPE);
		cl.setYgYsnSx(entity.getYgYsnSx());//运管 1、14年上线  0、不是   是/否 [ZDCLK1034]
		cl.setYgGx(entity.getYgGx());
		cl.setYgZj(entity.getYgZj());
		cl.setYgKjType(entity.getYgKjType());//运管 车辆备案_卡机安装状态 [ZDCLK1035] 卡机安装状态 1、已安装 0、未安装
		cl.setYgKjAzsj(entity.getYgKjAzsj());//运管 卡机安装时间
		cl.setYgKjPc(entity.getYgKjPc());//卡机批次
		cl.setYgNewCode(entity.getYgNewCode());//明涛成功新证号
		cl.setYgNewKj(entity.getYgNewKj());//新卡机
		carService.update(cl);

		//存储运管备案信息
		BizCarBa bizCarBa=new BizCarBa();//运管备案
		bizCarBa.setId(genId());
		bizCarBa.setClId(cl.getId());//车辆ID
		bizCarBa.setCph(cl.getCph());//车牌号
		bizCarBa.setYszh(entity.getYgYszh());//运管运输证号
		bizCarBa.setDjrq(entity.getYyCdrq());//登记日期
		bizCarBa.setGpsType(entity.getYgGpsType());//是否安装GPS  是/否 [ZDCLK1034]
		bizCarBa.setGpsCode(entity.getYgGpsCode());//GPS号码
		bizCarBa.setYyType(ygYyTYPE);//车辆备案_运营状态 [ZDCLK1033] 营运状态1、运营 2、注销 0、未登记
		bizCarBa.setYsnSx(entity.getYgYsnSx());//运管 1、14年上线  0、不是   是/否 [ZDCLK1034]
		bizCarBa.setGx(entity.getYgGx());
		bizCarBa.setZj(entity.getYgZj());
		bizCarBa.setKjType(entity.getYgKjType());//运管 车辆备案_卡机安装状态 [ZDCLK1035] 卡机安装状态 1、已安装 0、未安装
		bizCarBa.setKjAzsj(entity.getYgKjAzsj());//运管 卡机安装时间
		bizCarBa.setKjPc(entity.getYgKjPc());//卡机批次
		bizCarBa.setNewCode(entity.getYgNewCode());//明涛成功新证号
		bizCarBa.setNewKj(entity.getYgNewKj());//新卡机
		carBaService.save(bizCarBa);

		updCarWarnDate(cl.getId());
		return ApiResponse.success();
	}

	/**
	 * 车辆油改气年审提醒设置
	 * 油改气，三年一审
	 * @param entity
	 *
	 * @return
	 */
	@Override
	public ApiResponse<String> updGxNcWarn(BizCar entity, BizCarGas carGas){
		String clId=entity.getId();
		if (StringUtils.isBlank(clId)){
			return ApiResponse.fail("车辆ID不能为空");
		}

		String expiryDate=entity.getXcncsj();
		if (StringUtils.isBlank(expiryDate)){
			return ApiResponse.fail("下次年审时间不能为空");
		}
		Date endDate=null;
		try {
			endDate=DateUtils.getDate(expiryDate,"yyyy-MM-dd");
		}catch (Exception e){
			return ApiResponse.fail("请输入正确的下次年审时间 日期格式为：yyyy-MM-dd");
		}


		BizCar cl=carService.findById(clId);
		RuntimeCheck.ifTrue(cl==null, "车辆信息有误，请刷新页后重新尝试。");

		List<BizCarWarn> batchList=new ArrayList<>();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(endDate);
		BizCarWarn obd = new BizCarWarn();
		obd.setId(genId());
		obd.setClId(cl.getId());
		obd.setCph(cl.getCph());
		calendar.add(Calendar.DATE, -90);
		String warnDate = DateUtils.getDateStr(calendar.getTime(), "yyyy-MM-dd");//提醒日期
		obd.setWarnDate(warnDate);
		obd.setExpiryYyyy(expiryDate.substring(0, 4));//截止年
		obd.setExpiryMm(expiryDate.substring(5, 7));//截止月
		obd.setWarnType("3");
		obd.setWarnPc("1");
		obd.setExpiryDate(expiryDate);//截止日期
		obd.setWarnDispose("0");//提醒是否处理
		batchList.add(obd);

//		5、删除原来的提醒 todo 历史的记录是否要删除。
		BizCarWarn delObd=new BizCarWarn();
		delObd.setClId(cl.getId());
		delObd.setWarnType("3");
		baseMapper.delete(delObd);
//		6、批量插入提醒
		if(batchList!=null&&batchList.size()>0){
			baseMapper.insertList(batchList);
		}
		//改气初登日期
		cl.setGxCdrq(entity.getGxCdrq());
		cl.setGxType("1");
		cl.setGxGasDd(entity.getGxGasDd());//改气地点
		cl.setGxGasGgzyxq(entity.getGxGasGgzyxq());;//改气合格证有效期
		cl.setGxGasBz(entity.getGxGasBz());//改气备注
		cl.setGxLxr(entity.getGxLxr());//改气 联系人
		cl.setGxLxDn(entity.getGxLxDn());//改气 联系人电话
		carService.update(cl);

//		BizCarGas gas=new BizCarGas();
//		gas.setId(genId());
//		gas.setClId(cl.getId());
//		gas.setCph(cl.getCph());
//		gas.setGasData(entity.getGxCdrq());
////		gas.setType("1");
//		gas.setGasDd(entity.getGxGasDd());//改气地点
//		gas.setGxGasGgzyxq(entity.getGxGasGgzyxq());;//改气合格证有效期
//		gas.setBz(entity.getGxGasBz());//改气备注
//		gas.setLxr(entity.getGxLxr());//改气 联系人
//		gas.setLxDn(entity.getGxLxDn());//改气 联系人电话
//		carGasService.save(gas);//
		updCarWarnDate(cl.getId());
		return ApiResponse.success();
	}
	/**
	 * 车辆油改气年审提醒设置
	 * 油改气，三年一审
	 * @param entity
	 *
	 * @return
	 */
	@Override
	public ApiResponse<String> updGxNcWarn(BizCar entity){
		return updGxNcWarn(entity,null);
	}

	/**
	 * 车辆续保提醒设置
	 * @param entity
	 *
	 * @return
	 */
	@Override
	public ApiResponse<String> updBxNcWarn(BizCar entity){
		String messageId="";
		String clId=entity.getId();
		if (StringUtils.isBlank(clId)){
			return ApiResponse.fail("车辆ID不能为空");
		}

		String bxCdrq=entity.getBxCdrq();
		if (StringUtils.isBlank(bxCdrq)){
			return ApiResponse.fail("保险初登日期不能为空");
		}

		Date date=null;
		try {
			if (StringUtils.isNotBlank(bxCdrq)){
				date=DateUtils.getDate(bxCdrq,"yyyy-MM-dd");
			}
		}catch (Exception e){
			return ApiResponse.fail("请输入正确的险初登日期 日期格式为：yyyy-MM-dd");
		}



		String expiryDate=entity.getXcncsj();
		if (StringUtils.isBlank(expiryDate)){
			return ApiResponse.fail("下次年审时间不能为空。");
		}
		Date endDate=null;
		try {
			endDate=DateUtils.getDate(expiryDate,"yyyy-MM-dd");
		}catch (Exception e){
			return ApiResponse.fail("请输入正确的下次年审时间 日期格式为：yyyy-MM-dd");
		}


		BizCar cl=carService.findById(clId);
		RuntimeCheck.ifTrue(cl==null, "车辆信息有误，请刷新页后重新尝试。");
		String id=genId();
		messageId=id;

		List<BizCarWarn> batchList=new ArrayList<>();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(endDate);
		BizCarWarn obd = new BizCarWarn();
		obd.setId(id);
		obd.setClId(cl.getId());
		obd.setCph(cl.getCph());
		calendar.add(Calendar.DATE, -90);
		String warnDate = DateUtils.getDateStr(calendar.getTime(), "yyyy-MM-dd");//提醒日期
		obd.setWarnDate(warnDate);
		obd.setExpiryYyyy(expiryDate.substring(0, 4));//截止年
		obd.setExpiryMm(expiryDate.substring(5, 7));//截止月
		obd.setWarnType("4");
		obd.setWarnPc("1");
		obd.setExpiryDate(expiryDate);//截止日期
		obd.setWarnDispose("0");//提醒是否处理
		batchList.add(obd);


//		5、删除原来的提醒 todo 历史的记录是否要删除。
		BizCarWarn delObd=new BizCarWarn();
		delObd.setClId(cl.getId());
		delObd.setWarnType("4");
		baseMapper.delete(delObd);
//		6、批量插入提醒
		if(batchList!=null&&batchList.size()>0){
			baseMapper.insertList(batchList);
		}
		//保险初登日期
		cl.setBxCdrq(entity.getBxCdrq());
		carService.update(cl);
		updCarWarnDate(cl.getId());
		return ApiResponse.success(messageId);

	}

	/**
	 * 更新车辆告警时间信息，更新车辆主表 总状态
	 * @param clId
	 *
	 * @return
	 */
	@Override
	public ApiResponse<String> updCarWarnDate(String clId){
//		车辆状态   ZDCLK1042
		/**
		 *  1   正常
		 *  10  车管正常
		 *  12  车管逾期未审
		 *  19	车管90天待审
		 *  18	车管60天待审
		 *  17	车管30天待审
		 *  20  运管正常
		 *  22  运管逾期未审
		 *  29	运管90天待审
		 *  28	运管60天待审
		 *  27	运管30天待审
		 *  30	改气正常
		 *  32  改气逾期未审
		 *  39	改气90天待审
		 *  40	保险正常
		 *  42  保险逾期未审
		 *  49	保险90天待审
		 *  50	报废正常
		 *  52  报废逾期未审
		 *  61	已转出
		 */
		String carType="";

		List<BizCarWarn> list=baseMapper.getNewestWarn(clId);
		if(list!=null&&list.size()>0){//  warnType  提醒类型(1、车辆年审提醒 2、运管年审提醒 3、改气年审提醒 4、续保提醒 5、报废提醒)
			for(BizCarWarn l:list){

				String  warnType=l.getWarnType();
				String expiryDate=l.getExpiryDate();//
				Date endDate=null;
				try {
					endDate=DateUtils.getDate(expiryDate,"yyyy-MM-dd");
				}catch (Exception e){
				}
				if(endDate!=null){
					if(StringUtils.equals(warnType,"1")){//车辆年审提醒
						if(DateUtils.daysBetween(new Date(),endDate)<0){
							carType+="12,";
						}else if(DateUtils.daysBetween(new Date(),endDate)>60){//车管90天待审
							carType+="19,";
						}else if(DateUtils.daysBetween(new Date(),endDate)>30){//车管90天待审
							carType+="18,";
						}else if(DateUtils.daysBetween(new Date(),endDate)>0){//车管90天待审
							carType+="17,";
						}
					}else if(StringUtils.equals(warnType,"2")){//运管年审提醒
						if(DateUtils.daysBetween(new Date(),endDate)<0){
							carType+="22,";
						}else if(DateUtils.daysBetween(new Date(),endDate)>60){//运管90天待审
							carType+="29,";
						}else if(DateUtils.daysBetween(new Date(),endDate)>30){//运管0天待审
							carType+="28,";
						}else if(DateUtils.daysBetween(new Date(),endDate)>0){//运管0天待审
							carType+="27,";
						}
					}else if(StringUtils.equals(warnType,"3")){//改气年审提醒
						if(DateUtils.daysBetween(new Date(),endDate)<0){
							carType+="32,";
						}else if(DateUtils.daysBetween(new Date(),endDate)>0){//改气90天待审
							carType+="39,";
						}
					}else if(StringUtils.equals(warnType,"4")){//续保提醒
						if(DateUtils.daysBetween(new Date(),endDate)<0){
							carType+="42,";
						}else if(DateUtils.daysBetween(new Date(),endDate)>0){//改气90天待审
							carType+="49,";
						}
					}else if(StringUtils.equals(warnType,"5")){//报废提醒
						if(DateUtils.daysBetween(new Date(),endDate)<0){
							carType+="52,";
						}
					}
				}
			}
		}

		if(StringUtils.isBlank(carType)){
			carType="1";
		}else{
			carType=carType.substring(0,carType.length()-1);
		}

		baseMapper.updCarWarnDate(clId,carType);
		return ApiResponse.success();
	}

	/**
	 * 定时任务-生成告警信息
	 * @param data
	 * @return
	 */
	@Override
	public ApiResponse<String> jobSaveWarn(String data){
		Map<String,String> nstype=new HashMap<>();
		nstype.put("1","001");
		nstype.put("2","002");
		nstype.put("3","003");
		if(StringUtils.isBlank(data)){
			data=DateUtils.getToday();
		}
		SimpleCondition condition = new SimpleCondition(BizCarWarn.class);
		condition.lte(BizCarWarn.InnerColumn.warnDate.name(),data);
		condition.eq(BizCarWarn.InnerColumn.warnDispose.name(),"0");
		List<BizCarWarn> list = this.findByCondition(condition);

		List<BizCarAnnualExam> nsList=new ArrayList<>();
		if(list!=null&&list.size()>0){
			List<String>warnIds=list.stream().map(BizCarWarn::getId).collect(Collectors.toList());
			Map<String,String> map =new HashMap<>();
			if(CollectionUtils.isNotEmpty(warnIds)) {
				SimpleCondition DuplicateRemoval = new SimpleCondition(BizCarAnnualExam.class);
				DuplicateRemoval.in(BizCarAnnualExam.InnerColumn.warnId, warnIds);

				List<BizCarAnnualExam> carAnnualExamsList = carAnnualExamService.findByCondition(DuplicateRemoval);
				for(BizCarAnnualExam o:carAnnualExamsList){
					if(StringUtils.isNotBlank(o.getWarnId())){
						map.put(o.getWarnId(),"1");
					}
				}
			}

			List<String > carIdList = list.stream().map(BizCarWarn::getClId).collect(Collectors.toList());

			SimpleCondition barCarCondition = new SimpleCondition(BizCar.class);
			barCarCondition.in(BizCar.InnerColumn.id.name(),carIdList);
			List<BizCar> clList = bizCarService.findByCondition(barCarCondition);

			Map<String,BizCar> carMap = clList.stream().collect(Collectors.toMap(BizCar::getId,p->p));

			for(BizCarWarn l:list){
				//1、车辆年审提醒 2、运管年审提醒 3、改气年审提醒 4、续保提醒 5、报废提醒
				String warnType=l.getWarnType();
				if(StringUtils.indexOf("1,2,3",warnType)>-1){
					String val=map.get(l.getId());
					if(StringUtils.isBlank(val)) {
						BizCarAnnualExam o = new BizCarAnnualExam();
						o.setWarnId(l.getId());
						o.setId(genId());
						o.setClId(l.getClId());
						o.setCph(l.getCph());


						o.setCjType("1");//是否采集 1采集 0未采集
						o.setNssj(l.getExpiryDate());//年审时间

						String nssj = "";
						SimpleCondition querCondition = new SimpleCondition(BizCarWarn.class);

						querCondition.eq(BizCarWarn.InnerColumn.clId.name(), l.getClId());
						querCondition.eq(BizCarWarn.InnerColumn.warnType.name(), warnType);
						querCondition.eq(BizCarWarn.InnerColumn.warnDispose.name(), "1");

						querCondition.setOrderByClause(BizCarWarn.InnerColumn.id.asc());
						List<BizCarWarn> querlist = this.findByCondition(querCondition);
						if (querlist != null && querlist.size() > 0) {
							nssj = querlist.get(0).getExpiryDate();
						}
						o.setNsz(nssj);//年审至

						o.setNslx(nstype.get(warnType));
						o.setEssj("");//二审时间
						o.setPc("");//批次
						o.setZt("0");//年审状态
						o.setCjsj(DateUtils.getNowTime());

						BizCar carBa=carMap.get(l.getClId());
						if(carBa!=null&&StringUtils.equals(warnType,"1")){
							o.setJsyid(carBa.getJsyid());//备案人ID
							o.setJsyxm(carBa.getJsyxm());//备案人姓名
							o.setJsysfzh(carBa.getJsysfzh());//备案人身份证号
							o.setJsylxdh(carBa.getJsylxdh());//备案人联系电话
							o.setCarQy(carBa.getCarQy());//车辆区域
							String jsycjsj=carBa.getJsycjsj();//备案人信息采集时间
							String cjType="0";
//							if(StringUtils.isNotBlank(jsycjsj)){//备案人信息采集时间
//								Date endTime=null;
//								try {
//									endTime=DateUtils.getDate(jsycjsj,"yyyy-MM-dd");
//									Calendar calendar = new GregorianCalendar();
//									calendar.setTime(endTime);
//									calendar.add(Calendar.MONTH, 3);
//									Date d1 = new Date();
//									if((calendar.getTime().getTime()-d1.getTime())>0){
//										cjType="1";
//									}
//								}catch (Exception e){}
//							}
							o.setCjType(cjType);//备案人是否采集
							o.setJsycjsj(jsycjsj);
							o.setJsydzda(carBa.getJsydzda());//备案人信息电子档案
							o.setJsybz(carBa.getJsybz());//备案人信息备注
						}
						nsList.add(o);
					}
				}else if(StringUtils.equals("4",warnType)){//保险

				}else if(StringUtils.equals("5",warnType)){//报废

				}
				//更新主表的状态。
				this.updCarWarnDate(l.getClId());
			}
		}

		if(nsList!=null&&nsList.size()>0){
			carAnnualExamMapper.insertList(nsList);
		}
		//年审信息中，采集人时间超过30天的，标记为无效，需要重新上传采集时间
		carAnnualExamMapper.updateCollectionMessage();
		//年审信息中，采集人时间超过30天的，标记为无效，需要重新上传采集时间
		carAnnualExamMapper.updateCarCollectionMessage();
		return ApiResponse.success();
	}
}