package com.ldz.wechat.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.model.SysYh;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.wechat.base.WxBaseController;
import com.ldz.wechat.mapper.TraineeInformationMapper;
import com.ldz.wechat.model.ReduceManagement;
import com.ldz.wechat.model.TraineeInformation;
import com.ldz.wechat.service.ReduceManagementService;
import com.ldz.wechat.service.TraineeReduceAuditingService;
import com.ldz.wechat.service.TraineeStatusService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TraineeReduceAuditingServiceImpl extends BaseServiceImpl<TraineeInformation, String> implements TraineeReduceAuditingService {

	@Autowired
	private TraineeInformationMapper baseMapper;

	@Autowired
	private TraineeStatusService traineeStatusService;//学员状态表

	@Autowired
	private ReduceManagementService reduceManagementService;

	@Override
	protected Mapper<TraineeInformation> getBaseMapper() {
		return baseMapper;
	}

	/**
	 * 用户审核操作
	 *
	 * @param entity  id            学员主键
	 * @param entity  reduceStatus  优惠信息审核状态  00:未审核 10：审核通过 20：审核未通过
	 * @param entity  reduceRemark 优惠备注
	 * @return
	 * 	您没有需要审核的优惠项  			当前登录用户没有审核优惠项的权力
	 * 	您好，该学员的优惠项，您无权审核		当前学员的优惠项，当前登录用户无权审核
	 * 	您好,该学员已经审核，无需再次审核	已经审核过了。无需再次审核
	 */
	@Override
	public ApiResponse<String> setUserReduceAuditing(TraineeInformation entity){
		//		1、参数非空验证
		RuntimeCheck.ifBlank(entity.getId(), "请选择学员");
		RuntimeCheck.ifBlank(entity.getReduceStatus(), "请确定审核状态");
		RuntimeCheck.ifFalse(StringUtils.equals(entity.getReduceStatus(),"10")||StringUtils.equals(entity.getReduceStatus(),"20"),"请填写正确的审校状态");
//		2、查询出该用户所有可以审核的优惠编码
		SysYh sysUser = WxBaseController.getBossCurrentUser(true);
		String userId=sysUser.getYhid();
		SimpleCondition simpleCondition = new SimpleCondition(ReduceManagement.class);
//		simpleCondition.like(ReduceManagement.InnerColumn.verifier, userId);
//		simpleCondition.lte(ReduceManagement.InnerColumn.reduceStartTime, DateUtils.getDateStr(new Date(),"yyyy-MM-dd"));
//		simpleCondition.gte(ReduceManagement.InnerColumn.reduceEndTime, DateUtils.getDateStr(new Date(),"yyyy-MM-dd"));

		List<ReduceManagement> institutions = reduceManagementService.findByCondition(simpleCondition);
		List<String> list = institutions.stream().map(ReduceManagement::getId).collect(Collectors.toList());
		RuntimeCheck.ifTrue(CollectionUtils.isEmpty(list),"您没有需要审核的优惠项");
//		3、查询出该学员信息
		TraineeInformation trainee=findById(entity.getId());
		RuntimeCheck.ifTrue(trainee==null,"您好,学员不存在，请核实");
		String reduceCode=trainee.getReduceCode();
		RuntimeCheck.ifFalse(list.contains(reduceCode),"您好，该学员的优惠项，您无权审核");
		RuntimeCheck.ifTrue(!StringUtils.equals(trainee.getReduceStatus(),"00"),"您好,该学员已经审核，无需再次审核。");

//		4、操作数据库  todo 优惠信息的审核，是否需要增加审核人这个字段
		trainee.setReduceStatus(entity.getReduceStatus());//审核状态
		trainee.setReduceRemark(entity.getReduceRemark());//审核描述
		trainee.setReduceCheckTime(DateUtils.getNowTime());//审核时间
		trainee.setReduceVerifier(sysUser.getZh()+"-" + sysUser.getXm());
		if(StringUtils.equals(trainee.getReduceStatus(),"10")){
			trainee.setRealPay(trainee.getRegistrationFee() - trainee.getReducePrice());
		}

		int i=update(trainee);

		if(i>0){
			//学员状态表新增
			String status="00";//10：审核通过 20：审核未通过
			if(StringUtils.equals(entity.getReduceStatus(),"20")){
				status="10";
			}
			String type="学员优惠审核";
			traineeStatusService.saveEntity(entity,type,status,"学员优惠审核");
		}


		return i>0?ApiResponse.success():ApiResponse.fail();
	}

	@Override
	public ApiResponse<String> recallUserAuditing(TraineeInformation entity){
//		1、参数非空验证
		RuntimeCheck.ifBlank(entity.getId(), "请选择学员");
//		2、查询出该用户所有可以审核的优惠编码
		SysYh sysUser = WxBaseController.getBossCurrentUser(true);
		String userId=sysUser.getYhid();
		SimpleCondition simpleCondition = new SimpleCondition(ReduceManagement.class);
//		simpleCondition.like(ReduceManagement.InnerColumn.verifier, userId);
//		simpleCondition.lte(ReduceManagement.InnerColumn.reduceStartTime, DateUtils.getDateStr(new Date(),"yyyy-MM-dd"));
//		simpleCondition.gte(ReduceManagement.InnerColumn.reduceEndTime, DateUtils.getDateStr(new Date(),"yyyy-MM-dd"));

		List<ReduceManagement> institutions = reduceManagementService.findByCondition(simpleCondition);
		List<String> list = institutions.stream().map(ReduceManagement::getId).collect(Collectors.toList());
		RuntimeCheck.ifTrue(CollectionUtils.isEmpty(list),"您没有需要撤销审核的优惠项");
		//		3、查询出该学员信息
		TraineeInformation trainee=findById(entity.getId());
		RuntimeCheck.ifTrue(trainee==null,"您好,学员不存在，请核实");
		String reduceCode=trainee.getReduceCode();
		RuntimeCheck.ifFalse(list.contains(reduceCode),"您好，该学员的优惠项，您无权审核");
		//reduceStatus  优惠信息审核状态  00:未审核 10：审核通过 20：审核未通过
		RuntimeCheck.ifTrue(!StringUtils.equals(trainee.getReduceStatus(),"10"),"您好,该学员未审核通过，无需撤销审核。");
		RuntimeCheck.ifTrue(StringUtils.equals(trainee.getChargeStatus(),"10"),"您好,该学员已缴费，不能撤销优惠审核。");
		RuntimeCheck.ifTrue(!StringUtils.equals(trainee.getStatus(),"99"),"您好,该学员已缴费，不能撤销优惠审核。");

		//		4、操作数据库
		trainee.setReduceStatus("00");//审核状态
		trainee.setReduceRemark(null);//审核描述
		trainee.setReduceCheckTime(null);//审核时间
		trainee.setRealPay(trainee.getRegistrationFee() );
		trainee.setReduceVerifier(null);
		int i=baseMapper.updateByPrimaryKey(trainee);

		if(i>0){
			//学员状态表新增
			String status="00";//10：审核通过 20：审核未通过
			String type="学员优惠撤销审核";
			traineeStatusService.saveEntity(entity,type,status,"学员优惠撤销审核");
		}
		return i>0?ApiResponse.success():ApiResponse.fail();
	}
}