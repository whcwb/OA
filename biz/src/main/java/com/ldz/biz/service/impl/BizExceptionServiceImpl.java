package com.ldz.biz.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ldz.biz.constant.FeeType;
import com.ldz.biz.constant.Status;
import com.ldz.biz.model.*;
import com.ldz.biz.service.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.ldz.biz.mapper.BizExceptionMapper;
import com.ldz.biz.mapper.NotSchoolTestInfoMapper;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.model.SysYh;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.exception.RuntimeCheck;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

@Service
public class BizExceptionServiceImpl extends BaseServiceImpl<BizException, java.lang.String> implements BizExceptionService {

	@Autowired
	private BizExceptionMapper baseMapper;
	@Autowired
	private BizExceptionConfigService exceptionConfigService;
	@Autowired
	private TraineeInformationService traineeInfoService;
	@Autowired
	private TraineeTestInfoService testInfoService;
	@Autowired
	private NotSchoolTestInfoMapper schoolTestInfoMapper;
	@Autowired
	private ChargeManagementService chargeManagementService;

	
	@Override
	protected Mapper<BizException> getBaseMapper() {
		return baseMapper;
	}

	@Override
	public ApiResponse<List<BizExceptionConfig>> getAllConfig() {
		return ApiResponse.success(exceptionConfigService.findAll());
	}
	
	@Override
	public ApiResponse<String> saveException(BizException exception) {
		if (StringUtils.isBlank(exception.getSfzmhm())){
			return ApiResponse.fail("证件号码不能为空");
		}
		if (StringUtils.isBlank(exception.getCode())){
			return ApiResponse.fail("异常码不能为空");
		}
		String bz = exceptionConfigService.getExpNameByCode(exception.getCode());

		//给学员标记异常备注
		TraineeInformation traineeInfo = traineeInfoService.findByIdCardNo(exception.getSfzmhm());
		if (traineeInfo != null){
			SimpleCondition simpleCondition = new SimpleCondition(TraineeTestInfo.class);
			simpleCondition.eq(TraineeTestInfo.InnerColumn.traineeId, traineeInfo.getId());
			if(StringUtils.equals(exception.getCode(), "001")){
				bz += "( 报名时间:  " +traineeInfo.getRegistrationTime() +")";
			}else if (StringUtils.equals(exception.getCode(), "002")){
				bz += "( 报名审核时间:  " +traineeInfo.getInfoCheckTime() +")";
			}else if(StringUtils.equals(exception.getCode(), "003")){
				bz += "( 报名收费时间:  " +traineeInfo.getConfirmTime() +")";
			}else if(StringUtils.equals(exception.getCode(), "101") || StringUtils.equals(exception.getCode(), "102")){
				simpleCondition.eq(TraineeTestInfo.InnerColumn.subject, "科目一");
				simpleCondition.eq(TraineeTestInfo.InnerColumn.testTime, traineeInfo.getFirSubTestTime());
				List<TraineeTestInfo> infos = testInfoService.findByCondition(simpleCondition);
				String testPlace = "";
				if(CollectionUtils.isNotEmpty(infos)){
					testPlace = infos.get(0).getTestPlace();
				}
				bz += "( 考试时间:  " +traineeInfo.getFirSubTestTime() +" 考试地点: " + testPlace+ "   )";
			}else if(StringUtils.equals(exception.getCode(), "201")|| StringUtils.equals(exception.getCode(), "202")){
				simpleCondition.eq(TraineeTestInfo.InnerColumn.subject, "科目二");
				simpleCondition.eq(TraineeTestInfo.InnerColumn.testTime, traineeInfo.getSecSubTestTime());
				List<TraineeTestInfo> infos = testInfoService.findByCondition(simpleCondition);
				String testPlace = "";
				if(CollectionUtils.isNotEmpty(infos)){
					testPlace = infos.get(0).getTestPlace();
				}
				bz += "( 考试时间:  " +traineeInfo.getSecSubTestTime() +" 考试地点: " + testPlace+ "   )";
			}else if(StringUtils.equals(exception.getCode(), "301") || StringUtils.equals(exception.getCode(), "302")){
				simpleCondition.eq(TraineeTestInfo.InnerColumn.subject, "科目三");
				simpleCondition.eq(TraineeTestInfo.InnerColumn.testTime, traineeInfo.getThirdSubTestTime());
				List<TraineeTestInfo> infos = testInfoService.findByCondition(simpleCondition);
				String testPlace = "";
				if(CollectionUtils.isNotEmpty(infos)){
					testPlace = infos.get(0).getTestPlace();
				}
				bz += "( 考试时间:  " +traineeInfo.getThirdSubTestTime() +" 考试地点: " + testPlace+ "   )";
			}else if(StringUtils.equals(exception.getCode(), "402")){
				simpleCondition.eq(TraineeTestInfo.InnerColumn.subject, "科目四");
				simpleCondition.eq(TraineeTestInfo.InnerColumn.testTime, traineeInfo.getForthSubTestTime());
				List<TraineeTestInfo> infos = testInfoService.findByCondition(simpleCondition);
				String testPlace = "";
				if(CollectionUtils.isNotEmpty(infos)){
					testPlace = infos.get(0).getTestPlace();
				}
				bz += "( 考试时间:  " +traineeInfo.getForthSubTestTime() +" 考试地点: " + testPlace+ "   )";
			}else if(StringUtils.equals(exception.getCode(), "903")){
				bz += ("( 欠费金额: " + traineeInfo.getOweAmount() + " ) ");
				if(StringUtils.isNotBlank(traineeInfo.getReferrer())){
					bz +=",推荐人: "+ traineeInfo.getReferrer().split("-")[0];
				}
			}
			traineeInfo.setCode(exception.getCode());
			traineeInfo.setErrorMessage(bz);
			traineeInfoService.update(traineeInfo);
		}
		if(StringUtils.equals(exception.getCode(), "991")){
			// 非本校学员 查看本校信息中是否有数据
			SimpleCondition infoCondition = new SimpleCondition(TraineeInformation.class);
			infoCondition.eq(TraineeInformation.InnerColumn.idCardNo , exception.getSfzmhm());
			infoCondition.setOrderByClause(" registration_time desc ");
			List<TraineeInformation> informations = traineeInfoService.findByCondition(infoCondition);
			if(CollectionUtils.isNotEmpty(informations)){
				TraineeInformation information = informations.get(0);
				if(StringUtils.equals(information.getStatus(), Status.WIND)){
					bz += "( 学员已结业  , 车型: " + information.getCarType();
					// 学员结业 , 查询学员科目四考试合格的时间 如果此时已经合格 则为科四考试时间
					//  科目四合格状态
					String testResult = "20";
					if(StringUtils.isNotBlank(information.getForthSubTestTime()) && StringUtils.equals(information.getForthSub(),testResult)){
						bz += " 结业时间: " + information.getForthSubTestTime() ;
					}else  {
						 // 查询考试表里有没有科四的记录
						SimpleCondition testCondition = new SimpleCondition(TraineeTestInfo.class);
						testCondition.eq(TraineeTestInfo.InnerColumn.idCardNo, exception.getSfzmhm());
						testCondition.eq(TraineeTestInfo.InnerColumn.testResult, "00");
						testCondition.setOrderByClause(" id desc ");
						List<TraineeTestInfo> testInfos = testInfoService.findByCondition(testCondition);
						if(CollectionUtils.isNotEmpty(testInfos)){
							TraineeTestInfo testInfo = testInfos.get(0);
							bz += "结业时间: " + testInfo.getTestTime();
						}
					}
				}else if(StringUtils.equals(information.getStatus(), Status.QUIT)){
					bz += "( 学员已退学, 车型: " + information.getCarType();
					SimpleCondition statusCondition = new SimpleCondition(ChargeManagement.class);
					statusCondition.eq(ChargeManagement.InnerColumn.chargeCode, FeeType.DROP_OUT);
					statusCondition.eq(ChargeManagement.InnerColumn.idCardNo, exception.getSfzmhm());
					statusCondition.setOrderByClause(" id desc ");
					List<ChargeManagement> managements = chargeManagementService.findByCondition(statusCondition);
					if(CollectionUtils.isNotEmpty(managements)){
						ChargeManagement management = managements.get(0);
						bz += " 退学时间 : " + management.getCjsj().substring(0,10);
					}
				}else  {
					bz += "( 学员显示在办, 报名时间: " + traineeInfo.getRegistrationTime();
				}
			}else {
				bz += "( 系统中无此学员信息";
			}
			bz += " )";
		}
		//查看是否有相同的异常未处理，如果有就不再重复创建
		Example condition = new Example(BizException.class);
		condition.and()
				.andEqualTo(BizException.InnerColumn.sfzmhm.name(), exception.getSfzmhm())
				.andEqualTo(BizException.InnerColumn.code.name(), exception.getCode())
				.andEqualTo(BizException.InnerColumn.zt.name(), "00");
		BizException exist = baseMapper.selectOneByExample(condition);
		if (exist != null){
			exist.setKskm(exception.getKskm());
			exist.setLsh(exception.getLsh());
			
			update(exist);
		}else{
			SysYh user = getCurrentUser();
			exception.setId(String.valueOf(idGenerator.nextId()));
			exception.setCjsj(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
			if(user != null){
				exception.setCjr(user.getZh()+"-"+user.getXm());
			}else{
				exception.setCjr("系统");
			}
			exception.setBz(bz);
			exception.setZt("00");
			if(traineeInfo != null){
				exception.setBz1(traineeInfo.getJgmc());
				exception.setBz2(traineeInfo.getJgdm());
				exception.setBmsj(traineeInfo.getRegistrationTime());
				exception.setZjcx(traineeInfo.getCarType());
				exception.setXyid(traineeInfo.getId());
			}
			save(exception);
		}
		
		
		return ApiResponse.success();
	}
	
	@Override
	public List<BizException> findByCode(String code) {
		Example condition = new Example(BizException.class);
		condition.and().andEqualTo(BizException.InnerColumn.code.name(), code);
		condition.setOrderByClause(BizException.InnerColumn.cjsj.asc());
		return baseMapper.selectByExample(condition);
	}

	@Override
	public void clearException(BizException info, String code) {
		if (StringUtils.isBlank(info.getSfzmhm())){
			return;
		}
		if (StringUtils.isBlank(info.getCode())){
			return;
		}
		SysYh user = getCurrentUser();
		//1.查询在办学员信息，只对在办学员进行信息异常处理
		TraineeInformation traineeInfo = traineeInfoService.findByIdCardNo(info.getSfzmhm());
		if (traineeInfo != null){
			//查询学员是否有相关类型未处理的异常信息
			Example condition = new Example(BizException.class);
			condition.and()
					.andEqualTo(BizException.InnerColumn.sfzmhm.name(), info.getSfzmhm())
					.andEqualTo(BizException.InnerColumn.zt.name(), "00");
			List<BizException> exps = baseMapper.selectByExample(condition);
			if (CollectionUtils.isNotEmpty(exps)){
				BizException otherEntity = null;
				for (int i=0; i<exps.size(); i++){
					BizException entity = exps.get(i);
					//将相同类型的异常标记为已处理
					if (code.equals(entity.getCode())){
						entity.setGxsj(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
						entity.setGxr(user.getZh()+"-"+user.getXm());
						entity.setZt("10");
						
						baseMapper.updateByPrimaryKey(entity);
					}else{
						otherEntity = entity;
					}
				}
				//将学员主表信息异常也标记为已处理，如果学员同时有其他异常信息，则更新其他异常信息
				TraineeInformation information = new TraineeInformation();
				information.setId(traineeInfo.getId());
				if (code.equals(traineeInfo.getCode())){
					if (otherEntity == null){
//						information.setCode("");
//						information.setErrorMessage("");
						baseMapper.updateCode(information.getId());
					}else{
						TraineeInformation traineeInformation = new TraineeInformation();
						traineeInformation.setId(information.getId());
						traineeInformation.setCode(otherEntity.getCode());
						traineeInformation.setErrorMessage(otherEntity.getBz());
//						information.setCode(otherEntity.getCode());
//						information.setErrorMessage(otherEntity.getBz());
						traineeInfoService.update(traineeInformation);
					}
					

				}
			}
		}
	}

	@Override
	public void expJobSave(BizExceptionConfig config) {
		List<TraineeInformation> students = Lists.newArrayList();
		String kskm = "";
		String curYmd = DateTime.now().plusDays(config.getDays()).toString("yyyy-MM-dd") + " 00:00:00";
		if ("001".equals(config.getCode())){
			//报名审核.超过7天报名信息未审核
			students = baseMapper.getTraineeInfoByColumn("info_check_status", "='00'", "registration_time", curYmd);
		}else if ("002".equals(config.getCode())){
			//收费确认.超过7天报名已审核但未收费确认
			students = baseMapper.getTraineeInfoByColumn("charge_status", "='00'", "info_check_time", curYmd);
		}else if ("003".equals(config.getCode())){
			//受理确认.超过7天已收费确认但未受理确认
			students = baseMapper.getTraineeInfoByColumn("accept_status", "<>'20'", "confirm_time", curYmd);
		}else if ("101".equals(config.getCode())){
			kskm = "1";
			//科目一约考.即将考试还未缴科目一初考费
			students = baseMapper.getTraineeInfoByColumn("fir_sub_payment_time", " is  null", "fir_sub_test_time", config.getDays().toString());
		}else if ("201".equals(config.getCode())){
			kskm = "2";
			//科目二约考.即将考试还未缴科目二初考费
			students = baseMapper.getTraineeInfoByColumn("sec_sub_payment_time", " is  null", "sec_sub_test_time", config.getDays().toString());
		}else if ("301".equals(config.getCode())){
			kskm = "3";
			//科目三约考.即将考试还未缴科目三初考费
			students = baseMapper.getTraineeInfoByColumn("third_sub_payment_time", " is  null", "third_sub_test_time", config.getDays().toString());
		}else if ("102".equals(config.getCode())){
			kskm = "1";
			//科目一成绩确认.科目一考试成绩未确认
			students = baseMapper.getTraineeInfoByColumn("fir_sub", " not in ('30', '40')", "fir_sub_test_time", config.getDays().toString());
		}else if ("202".equals(config.getCode())){
			kskm = "2";
			//科目二成绩确认.科目二考试成绩未确认
			students = baseMapper.getTraineeInfoByColumn("sec_sub", " not in ('30', '40')", "sec_sub_test_time", config.getDays().toString());
		}else if ("302".equals(config.getCode())){
			kskm = "3";
			//科目三成绩确认.科目三考试成绩未确认
			students = baseMapper.getTraineeInfoByColumn("third_sub", " not in ('30', '40')", "third_sub_test_time", config.getDays().toString());
		}else if ("402".equals(config.getCode())){
			kskm = "4";
			//科目四成绩确认.科目四考试成绩未确认
			students = baseMapper.getTraineeInfoByColumn("forth_sub", " not in ('10', '20')", "forth_sub_test_time", config.getDays().toString());
		}
		
		for (int i=0; i<students.size(); i++){
			TraineeInformation item = students.get(i);
			BizException expItem = new BizException();
			expItem.setSfzmhm(item.getIdCardNo());
			expItem.setXm(item.getName());
			expItem.setLsh(item.getSerialNum());
			expItem.setCode(config.getCode());
			expItem.setKskm(kskm);
			
			saveException(expItem);
		}
	}
	
	@Override
	public ApiResponse<Map<String, Integer>> dashboard() {
		return ApiResponse.success(baseMapper.dashboard());
	}

	@Override
	public ApiResponse<String> updateException(String id) {
		BizException exception = findById(id);
		RuntimeCheck.ifTrue(exception.getZt().equals("10"), "此记录已经处理，请勿重复操作");
		Map<String, String> kmCode = new HashMap<>();
		kmCode.put("1", "科目一");
		kmCode.put("2", "科目二");
		kmCode.put("3", "科目三");
		kmCode.put("4", "科目四");
		exception.setZt("10");
		SimpleCondition condition  = new SimpleCondition(TraineeTestInfo.class);
		condition.eq(TraineeTestInfo.InnerColumn.subject, kmCode.get(exception.getKskm()));
		condition.eq(TraineeTestInfo.InnerColumn.idCardNo, exception.getSfzmhm());
		List<TraineeTestInfo> informations = testInfoService.findByCondition(condition);
		for (TraineeTestInfo information : informations) {
			NotSchoolTestInfo testInfo = new NotSchoolTestInfo();
			String s = information.getId();
			testInfoService.remove(s);
			BeanUtils.copyProperties(information,testInfo);
			schoolTestInfoMapper.insertSelective(testInfo);
		}
		update(exception);
		return ApiResponse.success();
	}

}