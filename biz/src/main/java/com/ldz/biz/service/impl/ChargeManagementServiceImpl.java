package com.ldz.biz.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ldz.biz.constant.FeeType;
import com.ldz.biz.mapper.ChargeManagementMapper;
import com.ldz.biz.model.ChargeItemManagement;
import com.ldz.biz.model.ChargeManagement;
import com.ldz.biz.model.TraineeInformation;
import com.ldz.biz.service.ChargeItemManagementService;
import com.ldz.biz.service.ChargeManagementService;
import com.ldz.biz.service.TraineeInformationService;
import com.ldz.biz.service.TraineeStatusService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.sys.model.SysJg;
import com.ldz.sys.model.SysYh;
import com.ldz.sys.model.SysZdxm;
import com.ldz.sys.service.JgService;
import com.ldz.sys.service.ZdxmService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.commonUtil.ExcelUtil;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.util.redis.RedisTemplateUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import tk.mybatis.mapper.common.Mapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
public class ChargeManagementServiceImpl extends BaseServiceImpl<ChargeManagement, String> implements ChargeManagementService {

	@Autowired
	private RedisTemplateUtil redisDao;

	@Autowired
	private ChargeManagementMapper baseMapper;
	@Autowired
	private ChargeItemManagementService itemManagementService;
	@Autowired
    private TraineeInformationService traineeInformationService;
	@Autowired
	private JgService jgService;
	@Autowired
	private TraineeStatusService statusService;
	@Autowired
	private ZdxmService zdxmService;
	@Override
	protected Mapper<ChargeManagement> getBaseMapper() {
		return baseMapper;
	}

	@Override
	public ApiResponse<String> saveInspect(ChargeManagement entity) {

		SysYh currentUser = getCurrentUser();

		RuntimeCheck.ifBlank(entity.getIdCardNo(), "身份证号不能为空");
		RuntimeCheck.ifBlank(entity.getChargeSource(), "体检收费驾校不能为空");
		RuntimeCheck.ifBlank(entity.getChargeType(), "收费类型不能为空");
		RuntimeCheck.ifBlank(entity.getTraineeName(), "学员姓名不能为空");
		SimpleCondition condition = new SimpleCondition(ChargeManagement.class);
		condition.eq(ChargeManagement.InnerColumn.chargeCode,FeeType.INSPECT);
		condition.eq(ChargeManagement.InnerColumn.idCardNo,entity.getIdCardNo());
		condition.and().andCondition(" zt != '20' and zt != '30'");
		List<ChargeManagement> managementList = findByCondition(condition);
		RuntimeCheck.ifTrue(CollectionUtils.isNotEmpty(managementList), "学员已缴费,请勿重复录入");
		// 查询体检费用
		ChargeItemManagement itemManagement = new ChargeItemManagement();
		itemManagement.setChargeCode(FeeType.INSPECT);
		List<ChargeItemManagement> managements = itemManagementService.findByEntity(itemManagement);
		ChargeItemManagement management = managements.get(0);
		entity.setId(genId());
		entity.setChargeFee(management.getAmount());
		entity.setChargeName(management.getChargeName());
		entity.setChargeTime(DateUtils.getNowTime());
		// 收入
		entity.setInOutType("00");
		entity.setReceiver(currentUser.getZh()+ "-" + currentUser.getXm());
		// 外校
		entity.setTraineeSource("10");
		entity.setChargeCode(management.getChargeCode());
		entity.setCjr(currentUser.getZh()+"-"+currentUser.getXm());
		entity.setCjsj(DateUtils.getNowTime());
		save(entity);

		return ApiResponse.saveSuccess();
	}

	@Override
	public ApiResponse<String> removeEntity(String id) {
		ChargeManagement management = findById(id);
		if(org.springframework.util.ObjectUtils.isEmpty(management)){
			return ApiResponse.fail("未找到该记录");
		}
		RuntimeCheck.ifTrue(StringUtils.isNotBlank(management.getPjbh()), "改收费已经打印了票据 , 无法撤回");
        if(StringUtils.containsAny(management.getChargeCode(), FeeType.SIGN_UP,FeeType.FIR_SUB,FeeType.SEC_SUB,FeeType.THIRD_SUB)){
		    return ApiResponse.fail("未找到该记录");
        }
		// 分期尾款
        if(management.getChargeCode().equals(FeeType.STAGING)){
            TraineeInformation information = traineeInformationService.findById(management.getTraineeId());
            information.setOweAmount(information.getOweAmount() + management.getChargeFee());
            information.setArrearage("10");
            traineeInformationService.update(information);
			statusService.saveEntity(information,"撤回分期还款","00","分期还款撤回");
        }
		remove(id);
		return ApiResponse.success();
	}

	@Override
	public ApiResponse<String> saveOtherCharge(ChargeManagement entity) {
		SysYh currentUser = getCurrentUser();

		/*RuntimeCheck.ifTrue(entity.getChargeFee() <=0 , "收支金额不能为小于0的整数");*/
		RuntimeCheck.ifBlank(entity.getChargeType(), "收款方式不能为空");
		RuntimeCheck.ifBlank(entity.getChargeCode(), "收费代码不能为空");
		ChargeItemManagement managements=null;
		if(StringUtils.equals(entity.getChargeCode(),FeeType.STAGING)){

			RuntimeCheck.ifBlank(entity.getTraineeId(), "学员id不能为空");
			TraineeInformation traineeInformation = traineeInformationService.findById(entity.getTraineeId());
			if(!ObjectUtils.isEmpty(traineeInformation)){
				if(StringUtils.isNotBlank(entity.getTraineeName()) && !StringUtils.equals(entity.getTraineeName(),traineeInformation.getName())){
					return ApiResponse.fail("学员姓名与当前记录不符");
				}
				if(StringUtils.equals(traineeInformation.getStatus(),"99")){
					return ApiResponse.fail("学员尚未确认缴纳学费,不能进行分期还款");
				}
				// 欠费金额
				int oweAmount = traineeInformation.getOweAmount();

				traineeInformation.setOweAmount(0);
				// 不欠费
				traineeInformation.setArrearage("00");
				traineeInformationService.update(traineeInformation);
				entity.setTraineeName(traineeInformation.getName());
				entity.setIdCardNo(traineeInformation.getIdCardNo());
				entity.setChargeFee(oweAmount);
				entity.setChargeSource(traineeInformation.getJgmc());
			}else{
				return ApiResponse.fail("系统中不存在该学员信息");
			}
		}else {
			managements = itemManagementService.findById(entity.getChargeCode());
			if(ObjectUtils.isEmpty(managements)){
				return ApiResponse.fail("不存在该费用项");
			}
			if(entity.getChargeFee()==null || entity.getChargeFee()<=0 ){
				entity.setChargeFee(managements.getAmount());
			}
			entity.setChargeCode(managements.getChargeCode());
		}

		if(StringUtils.isNotBlank(entity.getIdCardNo())){
			SimpleCondition condition = new SimpleCondition(TraineeInformation.class);
			condition.eq(TraineeInformation.InnerColumn.idCardNo,entity.getIdCardNo());
			condition.setOrderByClause(" registration_time desc ");
			List<TraineeInformation> traineeInformations = traineeInformationService.findByCondition(condition);

			if(CollectionUtils.isNotEmpty(traineeInformations)) {
				TraineeInformation traineeInformation = traineeInformations.get(0);
				if (StringUtils.isNotBlank(entity.getTraineeName()) && !StringUtils.equals(entity.getTraineeName(), traineeInformation.getName())) {
					return ApiResponse.fail("学员姓名与当前记录不符");
				}
				entity.setTraineeId(traineeInformation.getId());
				entity.setChargeSource(traineeInformation.getJgmc());
			}
		}
		entity.setReceiver(currentUser.getZh()+ "-" + currentUser.getXm());
		entity.setChargeTime(DateUtils.getNowTime());
		entity.setCjr(currentUser.getZh()+"-"+currentUser.getXm());
		entity.setCjsj(DateUtils.getNowTime());

		if(!ObjectUtils.isEmpty(managements)){
			entity.setChargeName(managements.getChargeName());
			entity.setInOutType(managements.getInOutType());
		}else if(entity.getChargeCode().equals(FeeType.STAGING)){
			entity.setChargeName("分期尾款");
			entity.setInOutType("00");
		}
		entity.setId(genId());
		save(entity);
		if(entity.getChargeCode().equalsIgnoreCase(FeeType.STAGING)){
			TraineeInformation traineeInformation = traineeInformationService.findById(entity.getTraineeId());
			statusService.saveEntity(traineeInformation,"分期尾款收费","00","分期尾款收费");
		}

		return ApiResponse.success();
	}

	@Override
	public ApiResponse<String> confirmCharge(Map<String,String> collect) {
		SysYh currentUser = getCurrentUser();
		Set<String> chargeIds = collect.keySet();

		SimpleCondition condition = new SimpleCondition(ChargeManagement.class);
		condition.in(ChargeManagement.InnerColumn.id,chargeIds);
		List<ChargeManagement> managements = findByCondition(condition);
		for (ChargeManagement management : managements) {
			management.setRemark(collect.get(management.getId()));
			management.setVerifier(currentUser.getZh()+ "-" + currentUser.getXm());
			management.setVerifyTime(DateUtils.getNowTime());
			update(management);
		}

		return ApiResponse.success();
	}

	@Override
	public ApiResponse<String> revokeCharge(List<String> collect) {
		SimpleCondition condition = new SimpleCondition(ChargeManagement.class);
		condition.in(ChargeManagement.InnerColumn.id,collect);
		List<ChargeManagement> managements = findByCondition(condition);
		if(CollectionUtils.isNotEmpty(managements)){
			for (ChargeManagement management : managements) {
				management.setVerifier("");
				management.setVerifyTime("");
				update(management);
			}
		}
		return ApiResponse.success();
	}

    @Override
    public ApiResponse<Map<String,Long>> countType(String chargeCode, String startTime, String endTime) {
        SimpleCondition condition = new SimpleCondition(ChargeManagement.class);
		if(StringUtils.isNotBlank(chargeCode)) {
			condition.eq(ChargeManagement.InnerColumn.chargeCode, chargeCode);
		}
		if(StringUtils.isBlank(startTime)){
			startTime = DateUtils.getDateStr(new Date(), "yyyy-MM-dd");
		}
		if(StringUtils.isBlank(endTime)){
			endTime = DateUtils.getNextTime();
		}
		condition.gte(ChargeManagement.InnerColumn.cjsj, startTime);
		condition.lte(ChargeManagement.InnerColumn.cjsj,endTime);
		condition.and().andCondition(" zt != '20' and zt != '30'");
		List<ChargeManagement> list = findByCondition(condition);
		Map<String, Long> collect = list.stream().collect(groupingBy(ChargeManagement::getChargeCode, counting()));
		Set<Map.Entry<String, Long>> set = collect.entrySet();
		for (Map.Entry<String, Long> entry : set) {
			String key = entry.getKey();
			if(StringUtils.equals(key, FeeType.SIGN_UP)){
				collect.put("报名费",entry.getValue());
			}else if(StringUtils.equals(key,FeeType.INSPECT)){
				collect.put("体检费",entry.getValue());
			}else if(StringUtils.equals(key, FeeType.FIR_SUB)){
				collect.put("科一初考费",entry.getValue());
			}else if(StringUtils.equals(key,FeeType.SEC_SUB)){
				collect.put("科二初考费",entry.getValue());
			}else if(StringUtils.equals(key,FeeType.THIRD_SUB)){
				collect.put("科三初考费",entry.getValue());
			}
			collect.remove(key);
		}

		return ApiResponse.success(collect);
    }

    @Override
    public ApiResponse<Map<String, Long>> countInOut() {
		LocalDate localDate = LocalDate.now();
		int year = localDate.getYear();
		String startTime = year + "-01-01 00:00:00";
		String endTime = year + "-12-31 23:59:59";
		SimpleCondition simpleCondition = new SimpleCondition(ChargeManagement.class);
		simpleCondition.gte(ChargeManagement.InnerColumn.cjsj, startTime);
		simpleCondition.lte(ChargeManagement.InnerColumn.cjsj, endTime);
		simpleCondition.eq(ChargeManagement.InnerColumn.inOutType,"00");
		List<ChargeManagement> list = findByCondition(simpleCondition);
		Map<String, Long> collect = list.stream().collect(Collectors.groupingBy(chargeManagement -> chargeManagement.getCjsj().substring(0, 7), summingLong(ChargeManagement::getChargeFee)));
		if(collect.get(year+"-01")==null){
			collect.put(year+"-01",0L);
		}
		if(collect.get(year+"-02")==null){
			collect.put(year+"-02",0L);
		}
		if(collect.get(year+"-03")==null){
			collect.put(year+"-03",0L);
		}
		if(collect.get(year+"-04")==null){
			collect.put(year+"-04",0L);
		}
		if(collect.get(year+"-05")==null){
			collect.put(year+"-05",0L);
		}
		if(collect.get(year+"-06")==null){
			collect.put(year+"-06",0L);
		}
		if(collect.get(year+"-07")==null){
			collect.put(year+"-07",0L);
		}
		if(collect.get(year+"-08")==null){
			collect.put(year+"-08",0L);
		}
		if(collect.get(year+"-09")==null){
			collect.put(year+"-09",0L);
		}
		if(collect.get(year+"-10")==null){
			collect.put(year+"-10",0L);
		}
		if(collect.get(year+"-11")==null){
			collect.put(year+"-11",0L);
		}
		if(collect.get(year+"-12")==null){
			collect.put(year+"-12",0L);
		}

		LinkedHashMap<String, Long> stringLongMap = sortMap(collect);
		Long l = 0L;
		for (Map.Entry<String, Long> entry : stringLongMap.entrySet()) {
			l += entry.getValue();
		}
		stringLongMap.put("total",l);
		return ApiResponse.success(stringLongMap);
    }

	@Override
	public ApiResponse<Map<String, Long>> countInType() {
		LocalDate localDate = LocalDate.now();
		int year = localDate.getYear();
		String startTime = year + "-01-01 00:00:00";
		String endTime = year + "-12-31 23:59:59";
		SimpleCondition simpleCondition = new SimpleCondition(ChargeManagement.class);
		simpleCondition.gte(ChargeManagement.InnerColumn.cjsj, startTime);
		simpleCondition.lte(ChargeManagement.InnerColumn.cjsj, endTime);
		simpleCondition.eq(ChargeManagement.InnerColumn.inOutType,"10");
		simpleCondition.and().andCondition(" zt != '20' and zt != '30'");
		List<ChargeManagement> list = findByCondition(simpleCondition);
		Map<String, Long> collect = list.stream().collect(Collectors.groupingBy(chargeManagement -> chargeManagement.getCjsj().substring(0, 7), summingLong(ChargeManagement::getChargeFee)));
		if(collect.get(year+"-01")==null){
			collect.put(year+"-01",0L);
		}
		if(collect.get(year+"-02")==null){
			collect.put(year+"-02",0L);
		}
		if(collect.get(year+"-03")==null){
			collect.put(year+"-03",0L);
		}
		if(collect.get(year+"-04")==null){
			collect.put(year+"-04",0L);
		}
		if(collect.get(year+"-05")==null){
			collect.put(year+"-05",0L);
		}
		if(collect.get(year+"-06")==null){
			collect.put(year+"-06",0L);
		}
		if(collect.get(year+"-07")==null){
			collect.put(year+"-07",0L);
		}
		if(collect.get(year+"-08")==null){
			collect.put(year+"-08",0L);
		}
		if(collect.get(year+"-09")==null){
			collect.put(year+"-09",0L);
		}
		if(collect.get(year+"-10")==null){
			collect.put(year+"-10",0L);
		}
		if(collect.get(year+"-11")==null){
			collect.put(year+"-11",0L);
		}
		if(collect.get(year+"-12")==null){
			collect.put(year+"-12",0L);
		}
		LinkedHashMap<String, Long> stringLongMap = sortMap(collect);
		Long l = 0L;
		for (Map.Entry<String, Long> entry : stringLongMap.entrySet()) {
			l += entry.getValue();
		}
		stringLongMap.put("total",l);
		return ApiResponse.success(stringLongMap);
	}

    @Override
    public ApiResponse<String> getReceiptNo(String[] ids , String num) {
		RuntimeCheck.ifBlank(num,"打印类型不能为空");
		String yyyymmdd = DateUtils.getDateStr(new Date(), "yyyyMMdd");
		String receiptNo = "";

		if(ids != null && ids.length > 0){


			SimpleCondition simpleCondition = new SimpleCondition(ChargeManagement.class);
			simpleCondition.in(ChargeManagement.InnerColumn.traineeId,Arrays.asList(ids));
			simpleCondition.like(ChargeManagement.InnerColumn.pjbh,num);
			simpleCondition.like(ChargeManagement.InnerColumn.pjbh,yyyymmdd);
			simpleCondition.and().andIsNotNull(ChargeManagement.InnerColumn.pjbh.name());
			simpleCondition.setOrderByClause(" pjbh desc");
			List<ChargeManagement> managementList = findByCondition(simpleCondition);
			if(CollectionUtils.isNotEmpty(managementList)){

				List<String> collect = managementList.stream().map(ChargeManagement::getPjbh).distinct().collect(toList());
				if(CollectionUtils.size(collect) > 1){
					return ApiResponse.fail("当前打印中有多个不同的票据编号");
				}
				String[] split = managementList.get(0).getPjbh().split("-");
				receiptNo = split[0] + "-" + split[1];
			}else{
                SimpleCondition condition = new SimpleCondition(ChargeManagement.class);
                condition.like(ChargeManagement.InnerColumn.pjbh, yyyymmdd);
                condition.like(ChargeManagement.InnerColumn.pjbh, num);
                condition.and().andIsNotNull(ChargeManagement.InnerColumn.pjbh.name());
                condition.setOrderByClause(" pjbh desc");
                List<ChargeManagement> managements = findByCondition(condition);
                if (CollectionUtils.isEmpty(managements)) {
                    receiptNo = yyyymmdd + "-" + "000" + 1;
                } else {
                    ChargeManagement management = managements.get(0);
                    String yy = management.getPjbh();
                    String[] split = yy.split("-");
                    Integer i = Integer.parseInt(split[1]);
                    int j = i + 1;
                    if (j >= 1000) {
                        receiptNo = yyyymmdd + "-" + j;
                    } else if (j >= 100) {
                        receiptNo = yyyymmdd + "-" + "0" + j;
                    } else if (j >= 10) {
                        receiptNo = yyyymmdd + "-" + "00" + j;
                    } else {
                        receiptNo = yyyymmdd + "-" + "000" + j;
                    }
                }
            }
			return ApiResponse.success(receiptNo);
		}else {


			SimpleCondition condition = new SimpleCondition(ChargeManagement.class);
			condition.like(ChargeManagement.InnerColumn.pjbh, yyyymmdd);
			condition.like(ChargeManagement.InnerColumn.pjbh, num);
			condition.and().andIsNotNull(ChargeManagement.InnerColumn.pjbh.name());
			condition.setOrderByClause(" pjbh desc");
			List<ChargeManagement> managements = findByCondition(condition);
			if (CollectionUtils.isEmpty(managements)) {
				receiptNo = yyyymmdd + "-" + "000" + 1;
			} else {
				ChargeManagement management = managements.get(0);
				String yy = management.getPjbh();
				String[] split = yy.split("-");
				Integer i = Integer.parseInt(split[1]);
				int j = i + 1;
				if (j >= 1000) {
					receiptNo = yyyymmdd + "-" + j;
				} else if (j >= 100) {
					receiptNo = yyyymmdd + "-" + "0" + j;
				} else if (j >= 10) {
					receiptNo = yyyymmdd + "-" + "00" + j;
				} else {
					receiptNo = yyyymmdd + "-" + "000" + j;
				}
			}
		}
//		redisDao.delete(yyyymmdd+ "-"+num);
//		String yy = (String) redisDao.boundValueOps(yyyymmdd+ "-"+num).get();
//		if(StringUtils.isBlank(yy)){
//			receiptNo = yyyymmdd+"-" + "000"+1;
//			redisDao.boundValueOps(yyyymmdd+ "-"+num).set(receiptNo,1, TimeUnit.DAYS);
//		}else {
//			receiptNo = yy;
//		}
		return ApiResponse.success(receiptNo);
    }

    @Override
    public ApiResponse<String> getPjbh(List<String> ids, String num, String pjbh) {

		RuntimeCheck.ifEmpty(ids,"请选择需要打印的记录");


		baseMapper.updatePjbh(ids,pjbh+"-"+num);

		return ApiResponse.success();
    }


	@Override
	public ApiResponse<List<Map<String, Long>>> findTodayCharge(Page<ChargeManagement> page) {
		List<Map<String,Long>> resList = new ArrayList<>();
		ApiResponse<List<Map<String,Long>>> result = new ApiResponse<>();
		LinkedHashMap<String,Long> inMap = new LinkedHashMap<>();
		LinkedHashMap<String, Long> outMap = new LinkedHashMap<>();
		// SimpleCondition condition= new SimpleCondition(ChargeManagement.class);
		LimitedCondition condition = getQueryCondition();
		condition.and().andCondition(" zt = '00' or zt = '10'");
		condition.setOrderByClause(" SUBSTR(IFNULL(pjbh,'9999999999'),1,13) asc , charge_time desc ");
		PageInfo<ChargeManagement> info = findPage(page, condition);
		info.getList().stream().forEach(chargeManagement -> {
			if(StringUtils.isNotBlank(chargeManagement.getTraineeId())) {
				TraineeInformation information = traineeInformationService.findById(chargeManagement.getTraineeId());
				SysJg jg = jgService.findByOrgCode(information.getJgdm());
				if(!ObjectUtils.isEmpty(information)){
					chargeManagement.setGlyxm(information.getGlyxm());
					chargeManagement.setJgPhone(jg.getLxdh1());
				}
			}
		});

		List<ChargeManagement> managements = findByCondition(condition);

		if(CollectionUtils.isNotEmpty(managements)){
			SimpleCondition simpleCondition = new SimpleCondition(SysZdxm.class);
			simpleCondition.eq(SysZdxm.InnerColumn.zdlmdm, "ZDCLK1024");
			List<SysZdxm> zdxms = zdxmService.findByCondition(simpleCondition);
			Map<String, String> collect = zdxms.stream().collect(Collectors.toMap(SysZdxm::getZddm, p -> p.getZdmc()));
			// 总支出
			Map<String, List<ChargeManagement>> map = managements.stream().filter(chargeManagement -> StringUtils.isNotBlank(chargeManagement.getInOutType())).collect(Collectors.groupingBy(ChargeManagement::getInOutType));
			if(map.get("10") != null) {
				long allOut = managements.stream().filter(chargeManagement -> chargeManagement.getInOutType().equals("10")).mapToLong(ChargeManagement::getChargeFee).sum();
				outMap.put("总支出", allOut);
				Map<String, Long> longMap = managements.stream().filter(chargeManagement -> chargeManagement.getInOutType().equals("10")).collect(Collectors.groupingBy(ChargeManagement::getChargeCode, Collectors.summingLong(ChargeManagement::getChargeFee)));
				Set<String> strings = longMap.keySet();
				if (CollectionUtils.isNotEmpty(strings)) {
					for (String s : strings) {
						outMap.put(collect.get(s), longMap.get(s));
					}
				}
			}else {
				outMap.put("总支出", 0L);
			}
			// 总收入
			if(map.get("00") != null){
				long allIn = managements.stream().filter(chargeManagement -> chargeManagement.getInOutType().equals("00")).mapToLong(ChargeManagement::getChargeFee).sum();
				inMap.put("总收入", allIn);
				Map<String, Long> longMap1 = managements.stream().filter(chargeManagement -> chargeManagement.getInOutType().equals("00")).collect(Collectors.groupingBy(ChargeManagement::getChargeCode, Collectors.summingLong(ChargeManagement::getChargeFee)));
				Set<String> keySet = longMap1.keySet();
				if(CollectionUtils.isNotEmpty(keySet)){
					for (String s : keySet) {
						inMap.put(collect.get(s),longMap1.get(s));
					}
				}
			}else{
				inMap.put("总收入",0L);
			}

		}

		if(CollectionUtils.size(inMap) == 0){
			inMap.put("总收入",0L);
		}
		if(CollectionUtils.size(outMap) == 0){
			outMap.put("总支出",0L);
		}
		resList.add(inMap);
		resList.add(outMap);
		result.setPage(info);

		result.setResult(resList);
		return result;
	}


	@Override
	public List<ChargeManagement> getBranchList(List<String> jgdms, String startTime, String endTime, String carType) {

		return baseMapper.getBranchList(jgdms,startTime,endTime,carType);
	}

	@Override
	public List<ChargeManagement> getAllIn(List<String> jgdms, String startTime, String endTime) {

		return baseMapper.getAllIn(jgdms,startTime,endTime);
	}

    @Override
    public Long countStaged(String time) {

		return baseMapper.countStaged(time);
    }

    @Override
    public void exportExcel(HttpServletRequest request, HttpServletResponse response, ChargeManagement entity) throws IOException {

			List<ChargeManagement> query = query(entity);
			List<Map<Integer,String>> data = new ArrayList<>();
			Map<Integer,String> map = new HashMap<>();
			map.put(0,"序号");
			map.put(1,"姓名");
			map.put(2, "报名点");
			map.put(3,"证件号码");
			map.put(4, "收费项");
			map.put(5,"金额");
			map.put(6, "收费时间");
			map.put(7,"收款方式");
			map.put(8,"收款/缴费人");
			map.put(9,"备注");
			map.put(10,"审核人");
			map.put(11,"审核时间");
			data.add(map);
			for ( int i = 0; i < query.size() ; i++) {
				ChargeManagement chargeManagement = query.get(i);
				Map<Integer,String> m = new HashMap<>();
				m.put(0,i+1+"");
				m.put(1,chargeManagement.getTraineeName());
				m.put(2,chargeManagement.getChargeSource());
				m.put(3,chargeManagement.getIdCardNo());
				m.put(4,chargeManagement.getChargeName());
				m.put(5,chargeManagement.getChargeFee()+"");
				m.put(6,chargeManagement.getChargeTime());
				m.put(7,chargeManagement.getChargeType());
				m.put(8,chargeManagement.getReceiver());
				m.put(9,chargeManagement.getRemark());
				m.put(10,chargeManagement.getVerifier());
				m.put(11,chargeManagement.getVerifyTime());

				data.add(m);

			}
			String fileName = java.net.URLEncoder.encode(DateFormatUtils.format(new Date(), "yyyy-MM-dd") + "审核记录.xls", "UTF-8");
			response.setContentType("application/msexcel");
			request.setCharacterEncoding("UTF-8");
			response.setHeader("pragma", "no-cache");
			response.addHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1") + ".xls");
			OutputStream out = response.getOutputStream();
			ExcelUtil.createSheet(out, "收费审核记录", data);

    }

    @Override
    public ApiResponse<String> rejectInspect(String id) {
		RuntimeCheck.ifBlank(id, "请选择需要驳回的记录");

		ChargeManagement chargeManagement = findById(id);
		RuntimeCheck.ifNull(chargeManagement , "未找到此记录");
		RuntimeCheck.ifTrue(chargeManagement.getZt().equals("10"), "此记录已驳回, 请勿重复操作");
		RuntimeCheck.ifTrue(chargeManagement.getZt().equals("20"),"此记录已退费, 不能驳回");
		RuntimeCheck.ifTrue(!chargeManagement.getZt().equals("00"), "此记录不支持驳回");
		RuntimeCheck.ifFalse(StringUtils.equals(chargeManagement.getChargeCode(),FeeType.INSPECT), "此记录不是体检记录");
		TraineeInformation information = traineeInformationService.findById(chargeManagement.getTraineeId());
		RuntimeCheck.ifTrue(information != null, "本校学员不支持驳回");
		// 设置费用状态为驳回
		SysYh currentUser = getCurrentUser();
		chargeManagement.setZt("10");
		chargeManagement.setBhr(currentUser.getZh() + "-" + currentUser.getXm());
		chargeManagement.setBhsj(DateUtils.getNowTime());
		update(chargeManagement);
		return ApiResponse.success();
    }

	@Override
	public ApiResponse<String> returnInspect(String id) {
		RuntimeCheck.ifBlank(id , "请选择需要退费的记录");
		ChargeManagement management = findById(id);
		RuntimeCheck.ifNull(management , "未找到此记录");
		RuntimeCheck.ifFalse(StringUtils.equals(management.getChargeCode(),FeeType.INSPECT), "此记录不是体检记录");
		RuntimeCheck.ifFalse(StringUtils.equals(management.getZt(),"10"), "此记录未被驳回");
		// 设置费用状态为退费
		SysYh currentUser = getCurrentUser();
		management.setZt("20");
		management.setTfr(currentUser.getZh() + "-" + currentUser.getXm());
		management.setTfsj(DateUtils.getNowTime());
		update(management);
//		statusService.saveEntity(traineeInformationService.findById(management.getTraineeId()), "体检退费确认", "00", "体检退费确认");
		return ApiResponse.success();
	}

    @Override
    public ApiResponse<String> warnInspect(String id, String reason) {
        RuntimeCheck.ifBlank(id , "请选择异常记录");
		ChargeManagement management = findById(id);
		RuntimeCheck.ifNull(management, "未找到记录");
		RuntimeCheck.ifFalse(StringUtils.equals(management.getChargeCode() , "0001"), "此记录不是体检费,不支持此操作");
		RuntimeCheck.ifTrue(StringUtils.equals(management.getZt(), "10"), "此记录已经被驳回 , 不能进行异常处理");
		RuntimeCheck.ifTrue(StringUtils.equals(management.getZt(), "20"), "此记录已经退费 , 不能进行异常处理");
		RuntimeCheck.ifTrue(StringUtils.equals(management.getZt(), "30"), "此记录已经异常处理, 请勿重复操作");
		RuntimeCheck.ifFalse(StringUtils.equals(management.getZt(), "00"), "不支持此操作");
		SysYh yh = getCurrentUser();
		management.setZt("30");
		management.setYcyy(reason);
		management.setYcr(yh.getZh() + "-" + yh.getXm());
		management.setYcsj(DateUtils.getNowTime());
		update(management);
		return ApiResponse.success();
    }


    /**
	 * 按照key的大小排序
	 * @param kv map集合
	 * @return 有序map
	 */
	private LinkedHashMap<String, Long> sortMap(Map<String, Long> kv) {
		List<Map.Entry<String, Long>> list = new ArrayList<Map.Entry<String, Long>>(kv.entrySet());
		list.sort(Comparator.comparing(Map.Entry::getKey));
		LinkedHashMap<String,Long> result = new LinkedHashMap<>();
		for (Map.Entry<String, Long> entry : list) {
			result.put(entry.getKey(), entry.getValue());

		}
		return result;
	}



    @Override
	public boolean fillPagerCondition(LimitedCondition condition){

		HttpServletRequest requset = getRequset();

		String zt = requset.getParameter("jfzt");
		if(StringUtils.isNotBlank(zt)){
			condition.in(ChargeManagement.InnerColumn.zt,zt.split(","));
			condition.setOrderByClause(" zt asc, bhsj desc ");
		}else{
			condition.and().andCondition(" zt != '20' and zt != '30'");
		}

		String inspect = requset.getParameter("inspect");
		if(StringUtils.equals(inspect,"1")){
			// 体检
			condition.eq(ChargeManagement.InnerColumn.chargeCode,FeeType.INSPECT);
			condition.like(ChargeManagement.InnerColumn.chargeTime, DateUtils.getDateStr(new Date(),"yyyy-MM-dd"));
		}else if(StringUtils.equals(inspect,"2")){
			condition.and().andCondition(" charge_code = 9999 or charge_code = 4999 ");
			condition.like(ChargeManagement.InnerColumn.chargeTime, DateUtils.getDateStr(new Date(),"yyyy-MM-dd"));
		}
		String verify = requset.getParameter("verify");
		// 查询需要审核的收费记录
		if(StringUtils.equals(verify,"1")){
			condition.and().andCondition(" verify_time is null or verify_time = ''");
		}else if(StringUtils.equals(verify, "2")){
			condition.like(ChargeManagement.InnerColumn.verifyTime, DateUtils.getDateStr(new Date(),"yyyy-MM-dd"));
		}

		return true;
	}

	@Override
	public void afterPager(PageInfo<ChargeManagement> resultInfo){
		if(CollectionUtils.isEmpty(resultInfo.getList())){
			return;
		}
		Integer totalFee = 0;
		Integer outFee = 0;
		HttpServletRequest requset = getRequset();
		LimitedCondition queryCondition = getQueryCondition();
		String zt = requset.getParameter("jfzt");
		if(StringUtils.isNotBlank(zt)){
			queryCondition.in(ChargeManagement.InnerColumn.zt,zt.split(","));

		}else{
			queryCondition.and().andCondition(" zt != '20' and zt != '30'");
		}
        List<ChargeManagement> managementList = findByCondition(queryCondition);
        try {
			totalFee = managementList.stream().filter(chargeManagement -> StringUtils.equals(chargeManagement.getInOutType(), "00")).mapToInt(value -> value.getChargeFee()).reduce((left, right) -> left + right).getAsInt();
		}catch (NoSuchElementException e){

		}
		try {
        	outFee = managementList.stream().filter(chargeManagement -> StringUtils.equals(chargeManagement.getInOutType(),"10")).mapToInt(value -> value.getChargeFee()).reduce((left, right) -> left+right).getAsInt();
		}catch (NoSuchElementException e){

		}
        List<ChargeManagement> list = resultInfo.getList();
		Map<String, TraineeInformation> collect = null;
		List<String> ids = list.stream().filter(m -> StringUtils.isNotBlank(m.getTraineeId())).map(ChargeManagement::getTraineeId).collect(toList());
		if(CollectionUtils.isNotEmpty(ids)){
			List<TraineeInformation> byIds = traineeInformationService.findIn(TraineeInformation.InnerColumn.id,ids);
			collect = byIds.stream().collect(toMap(TraineeInformation::getId, p -> p));
		}

		for (ChargeManagement management : list) {
			if(StringUtils.isNotBlank(management.getTraineeId())){
				if(collect != null){
					TraineeInformation information = collect.get(management.getTraineeId());
					management.setInformation(information);
				}
				management.setOutFee(outFee);
				management.setTotalFee(totalFee);

			}
		}
	}

	public boolean fillQueryCondition(LimitedCondition condition){
		HttpServletRequest requset = getRequset();

		String zt = requset.getParameter("jfzt");
		if(StringUtils.isNotBlank(zt)){
			condition.in(ChargeManagement.InnerColumn.zt,zt.split(","));
			condition.setOrderByClause(" zt asc, bhsj desc ");
		}else{
			condition.and().andCondition(" zt != '20' and zt != '30'");
		}

		String inspect = requset.getParameter("inspect");
		if(StringUtils.equals(inspect,"1")){
			// 体检
			condition.eq(ChargeManagement.InnerColumn.chargeCode,FeeType.INSPECT);
			condition.like(ChargeManagement.InnerColumn.chargeTime, DateUtils.getDateStr(new Date(),"yyyy-MM-dd"));
		}else if(StringUtils.equals(inspect,"2")){
			condition.and().andCondition(" charge_code = 9999 or charge_code = 4999 ");
			condition.like(ChargeManagement.InnerColumn.chargeTime, DateUtils.getDateStr(new Date(),"yyyy-MM-dd"));
		}
		String verify = requset.getParameter("verify");
		// 查询需要审核的收费记录
		if(StringUtils.equals(verify,"1")){
			condition.and().andCondition(" verify_time is null or verify_time = ''");
		}else if(StringUtils.equals(verify, "2")){
			condition.like(ChargeManagement.InnerColumn.verifyTime, DateUtils.getDateStr(new Date(),"yyyy-MM-dd"));
		}

		return true;
	}

	public void afterQuery(List<ChargeManagement> result){
		Integer totalFee = 0;
		Integer outFee = 0;
		HttpServletRequest requset = getRequset();
		LimitedCondition queryCondition = getQueryCondition();
		String zt = requset.getParameter("jfzt");
		if(StringUtils.isNotBlank(zt)){
			queryCondition.in(ChargeManagement.InnerColumn.zt,zt.split(","));
		}else{
			queryCondition.and().andCondition(" zt != '20' and zt != '30'");
		}
		List<ChargeManagement> managementList = findByCondition(queryCondition);
		try {
			totalFee = managementList.stream().filter(chargeManagement -> StringUtils.equals(chargeManagement.getInOutType(), "00")).mapToInt(value -> value.getChargeFee()).reduce((left, right) -> left + right).getAsInt();
		}catch (NoSuchElementException e){

		}
		try {
			outFee = managementList.stream().filter(chargeManagement -> StringUtils.equals(chargeManagement.getInOutType(),"10")).mapToInt(value -> value.getChargeFee()).reduce((left, right) -> left+right).getAsInt();
		}catch (NoSuchElementException e){

		}

		Map<String, TraineeInformation> collect = null;
		List<String> ids = result.stream().filter(m -> StringUtils.isNotBlank(m.getTraineeId())).map(ChargeManagement::getTraineeId).collect(toList());
		if(CollectionUtils.isNotEmpty(ids)){
			List<TraineeInformation> byIds = traineeInformationService.findIn(TraineeInformation.InnerColumn.id,ids);
			collect = byIds.stream().collect(toMap(TraineeInformation::getId, p -> p));
		}

		for (ChargeManagement management : result) {
			if(StringUtils.isNotBlank(management.getTraineeId())){
				if(collect == null){
					TraineeInformation information = collect.get(management.getTraineeId());
					management.setInformation(information);
				}
				management.setOutFee(outFee);
				management.setTotalFee(totalFee);

			}
		}
	}


}