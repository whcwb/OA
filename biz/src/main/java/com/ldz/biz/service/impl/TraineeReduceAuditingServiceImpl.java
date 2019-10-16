package com.ldz.biz.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ldz.biz.constant.Status;
import com.ldz.biz.mapper.TraineeInformationMapper;
import com.ldz.biz.model.ReduceManagement;
import com.ldz.biz.model.TraineeInformation;
import com.ldz.biz.service.ReduceManagementService;
import com.ldz.biz.service.TraineeReduceAuditingService;
import com.ldz.biz.service.TraineeStatusService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.sys.model.SysYh;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tk.mybatis.mapper.common.Mapper;

import javax.servlet.http.HttpServletRequest;
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
	 * 分页补充
	 * @param condition
	 * @return
	 */
	@Override
	public boolean fillPagerCondition(LimitedCondition condition){
		SysYh sysUser=getCurrentUser(true);
		String userId=sysUser.getYhid();
		SimpleCondition simpleCondition = new SimpleCondition(ReduceManagement.class);
//		simpleCondition.like(ReduceManagement.InnerColumn.verifier, userId);
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		//从前台获取特殊的组件
		String[] bmTime = request.getParameterValues("bmTime");
		if (bmTime!=null && bmTime.length == 2){
			DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
			//报名时间筛选，使用daterange日期组件，从前台传过来的日期需要加1天，才是Java日期格式
			if (StringUtils.isNotBlank(bmTime[0])){
				String startTime = bmTime[0].substring(0, 10) + " 00:00:00";
				startTime = DateTime.parse(startTime, dtf).plusDays(1).toString("yyyy-MM-dd HH:mm:ss");
				condition.gte(TraineeInformation.InnerColumn.registrationTime.name(), startTime);
			}
			if (StringUtils.isNotBlank(bmTime[1])){
				String endTime = bmTime[1].substring(0, 10) + " 23:59:59";
				endTime = DateTime.parse(endTime, dtf).plusDays(1).toString("yyyy-MM-dd HH:mm:ss");
				condition.lte(TraineeInformation.InnerColumn.registrationTime.name(), endTime);
			}
		}
		String[] jgdm = request.getParameterValues("bmd");
		if (jgdm != null && jgdm.length > 0){
			//报名点，是级联组件，从前台传过来的是一个数组，数组最后一个数据是最终选择的报名点数据
			String tmpJgdm = jgdm[jgdm.length - 1];
			if (StringUtils.isNotBlank(tmpJgdm)){
				condition.eq(TraineeInformation.InnerColumn.jgdm.name(), jgdm[jgdm.length - 1]);	
			}	
		}

		condition.setOrderByClause(" reduce_check_time desc , cjsj desc");
	/*	List<ReduceManagement> institutions = reduceManagementService.findByCondition(simpleCondition);
		List<String> list = institutions.stream().map(ReduceManagement::getId).collect(Collectors.toList());
		if(CollectionUtils.isNotEmpty(list)) {//如果本人没有可以审核的优惠项，就直接跳过下一步查询。直接结束
			condition.in(TraineeInformation.InnerColumn.reduceCode, list);
		}else{
			return false;
		}*/
		// condition.eq(TraineeInformation.InnerColumn.reduceStatus,"00");
		return true;
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
		SysYh sysUser=getCurrentUser(true);
		String userId=sysUser.getYhid();
		SimpleCondition simpleCondition = new SimpleCondition(ReduceManagement.class);
		simpleCondition.like(ReduceManagement.InnerColumn.verifier, userId);
		simpleCondition.lte(ReduceManagement.InnerColumn.reduceStartTime, DateUtils.getDateStr(new Date(),"yyyy-MM-dd"));
		simpleCondition.gte(ReduceManagement.InnerColumn.reduceEndTime, DateUtils.getDateStr(new Date(),"yyyy-MM-dd"));

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
		SysYh sysUser=getCurrentUser(true);
		String userId=sysUser.getYhid();
		SimpleCondition simpleCondition = new SimpleCondition(ReduceManagement.class);
		simpleCondition.like(ReduceManagement.InnerColumn.verifier, userId);
		simpleCondition.lte(ReduceManagement.InnerColumn.reduceStartTime, DateUtils.getDateStr(new Date(),"yyyy-MM-dd"));
		simpleCondition.gte(ReduceManagement.InnerColumn.reduceEndTime, DateUtils.getDateStr(new Date(),"yyyy-MM-dd"));

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
		RuntimeCheck.ifTrue(!StringUtils.equals(trainee.getStatus(),Status.SIGN_UP),"您好,该学员已缴费，不能撤销优惠审核。");

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

	@Override
	public ApiResponse<List<TraineeInformation>> getToEditReducePrice(Page<TraineeInformation> page) {

		ApiResponse<List<TraineeInformation>> result = new ApiResponse<>();

		SimpleCondition condition = new SimpleCondition(TraineeInformation.class);
		condition.eq(TraineeInformation.InnerColumn.status, Status.SIGN_UP);
		condition.and().andIsNotNull(TraineeInformation.InnerColumn.reduceCode.name());
		// condition.and().andIsNull(TraineeInformation.InnerColumn.reduceCheckTime.name());
		condition.and().andCondition( " confirm_time is null or confirm_time = ''");
		PageInfo<TraineeInformation> info = findPage(page, condition);
		result.setPage(info);
		return result;
	}

	@Override
	public ApiResponse<String> editReducePrice(String id, int price) {

		TraineeInformation information = findById(id);
		if(StringUtils.isBlank(information.getReduceCode())){
			return ApiResponse.fail("该学员没有优惠信息,不能修改价格");
		}

		int oldPrice = information.getReducePrice();
		information.setReducePrice(price);
		information.setReduceCheckTime("");
		information.setReduceStatus("00");
		information.setRealPay(information.getRegistrationFee() - price);

		int i = update(information);
		if(i>0){
			String status = "00";
			String type = "修改学员优惠金额";
			traineeStatusService.saveEntity(information,type,status,"修改学员优惠金额 "+ oldPrice + "->" + price);
		}else {
			return ApiResponse.fail("操作失败，请重新操作");
		}


		return ApiResponse.success();
	}

	@Override
	public ApiResponse<List<TraineeInformation>> getAllReduceTrain(Page<TraineeInformation> page) {

		return null;
	}
}