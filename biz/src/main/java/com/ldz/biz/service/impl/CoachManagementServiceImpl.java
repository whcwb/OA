package com.ldz.biz.service.impl;

import com.ldz.biz.mapper.CoachManagementMapper;
import com.ldz.biz.mapper.CoachTraineeRercordMapper;
import com.ldz.biz.mapper.TraineeInformationMapper;
import com.ldz.biz.model.CoachManagement;
import com.ldz.biz.model.CoachTraineeRercord;
import com.ldz.biz.model.TraineeInformation;
import com.ldz.biz.model.Zgjbxx;
import com.ldz.biz.service.CoachManagementService;
import com.ldz.biz.service.TraineeInformationService;
import com.ldz.biz.service.ZgjbxxService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.sys.model.SysJg;
import com.ldz.sys.model.SysMessage;
import com.ldz.sys.model.SysYh;
import com.ldz.sys.service.JgService;
import com.ldz.sys.service.SysMessageService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class CoachManagementServiceImpl extends BaseServiceImpl<CoachManagement, String> implements CoachManagementService {

	@Autowired
	private CoachManagementMapper baseMapper;

	@Autowired
	private TraineeInformationMapper traineeMapper;

	@Autowired
	private CoachTraineeRercordMapper coachTraineeRercordMapper;//分配记录表
	@Autowired
	private SysMessageService messageService;//消息下发
	@Autowired
	private ZgjbxxService zgjbxxService;


	@Autowired
	private TraineeInformationService traineeInformationService;

	@Autowired
	private JgService jgService;
	
	@Override
	protected Mapper<CoachManagement> getBaseMapper() {
		return baseMapper;
	}

	@Override
	public boolean fillPagerCondition(LimitedCondition condition){
		condition.eq(CoachManagement.InnerColumn.coachStatus,"00");

		HttpServletRequest requset = getRequset();
		//获取车型
		String cx = requset.getParameter("cx");
		if(StringUtils.isNotEmpty(cx)&&cx.length()<3){
//			//因为从前台转过来的字符串，需要直接拼接到SQL中执行，为了防止SQL注入的风险，所以给入参进行验证,必须是以下字符串才可以进行SQL验证
//			if(StringUtils.indexOf("A1、A2、A3、B1、B2、C1、C2、C3、C4、D、E、F、M、N、P",cx.toUpperCase())>-1){//StringUtils.substring(cx,0,1)
//				condition.and().andCondition(" STRCMP('"+cx.toUpperCase()+"', DRIVING_TYPE) > -1 ");//查询出所有满足条件车型
//			}
			condition.like(CoachManagement.InnerColumn.drivingType,cx.toUpperCase());
		}
		//coach_sub  培训科目  01:科目一 02：科目二 03：科目三 04：科目四
		String km = requset.getParameter("km");
		if(StringUtils.isNotEmpty(km)){
			condition.like(CoachManagement.InnerColumn.coachSub,km);
		}
//		//教练证有效时间，
//		condition.lte(CoachManagement.InnerColumn.coachStartTime,DateUtils.getToday());//<= new Date
//		condition.gte(CoachManagement.InnerColumn.coachEndTime,DateUtils.getToday());// endTime>= new Date
		return true;
	}

	@Override
	public boolean fillQueryCondition(LimitedCondition condition){
		condition.eq(CoachManagement.InnerColumn.coachStatus,"00");

		HttpServletRequest requset = getRequset();
		//获取车型
		String cx = requset.getParameter("cx");
		if(StringUtils.isNotEmpty(cx)&&cx.length()<3){
//			//因为从前台转过来的字符串，需要直接拼接到SQL中执行，为了防止SQL注入的风险，所以给入参进行验证,必须是以下字符串才可以进行SQL验证
//			if(StringUtils.indexOf("A1、A2、A3、B1、B2、C1、C2、C3、C4、D、E、F、M、N、P",cx.toUpperCase())>-1){//StringUtils.substring(cx,0,1)
//				condition.and().andCondition(" STRCMP('"+cx.toUpperCase()+"', DRIVING_TYPE) > -1 ");//查询出所有满足条件车型
//			}
			condition.like(CoachManagement.InnerColumn.drivingType,cx.toUpperCase());
		}
		//coach_sub  培训科目  01:科目一 02：科目二 03：科目三 04：科目四
		String km = requset.getParameter("km");
		if(StringUtils.isNotEmpty(km)){
			condition.like(CoachManagement.InnerColumn.coachSub,km);
		}
//		//教练证有效时间，
//		condition.lte(CoachManagement.InnerColumn.coachStartTime,DateUtils.getToday());//<= new Date
//		condition.gte(CoachManagement.InnerColumn.coachEndTime,DateUtils.getToday());// endTime>= new Date
		return true;
	}

	@Override
	public ApiResponse<String> validAndSave(CoachManagement entity){
		SysYh currentUser = getCurrentUser();
		CoachManagement coach = new CoachManagement();
		RuntimeCheck.ifBlank(entity.getArea(), "所属区域不能为空");
		RuntimeCheck.ifBlank(entity.getCoachName(), "姓名不能为空");
		RuntimeCheck.ifBlank(entity.getCoachSub(), "培训科目不能为空");
		RuntimeCheck.ifBlank(entity.getJgdm(), "机构代码不能为空");
		// RuntimeCheck.ifBlank(entity.getJgmc(), "机构名称不能为空");
		RuntimeCheck.ifBlank(entity.getCoachNum(), "教练证编号不能为空");
		RuntimeCheck.ifBlank(entity.getCoachStartTime(), "教练证开始时间不能为空");
		RuntimeCheck.ifBlank(entity.getCoachEndTime(), "教练证有效结束时间不能为空");
		RuntimeCheck.ifBlank(entity.getDrivingType(), "准驾类型不能为空");
		RuntimeCheck.ifBlank(entity.getPhone(), "电话号码不能为空");
		RuntimeCheck.ifTrue(entity.getDrivingYears() <= 0, "驾龄不能为0");
		RuntimeCheck.ifBlank(entity.getLicenseStartTime(), "驾驶证有效期不能为空");
		RuntimeCheck.ifBlank(entity.getLicenseEndTime(), "驾驶证有效期结束时间不能为空");
		RuntimeCheck.ifBlank(entity.getRecordNum(), "档案编号不能为空");
		RuntimeCheck.ifBlank(entity.getIdCardNo(), "身份证号不能为空");

		BeanUtils.copyProperties(entity,coach);

		SysJg sysJg = jgService.findById(coach.getJgdm());
		SysJg pjg = jgService.findById(coach.getJgdm().substring(0, coach.getJgdm().length() - 3));
		coach.setJgmc(pjg.getJgmc() + "/" + sysJg.getJgmc());
		coach.setCoachStatus("00");
		coach.setCjr(currentUser.getZh()+"-"+currentUser.getXm());
		coach.setCjsj(DateUtils.getNowTime());
		coach.setId(genId());
		save(coach);
		// RuntimeCheck.ifBlank(entity);
		return ApiResponse.saveSuccess();
	}

	@Override
	public ApiResponse<String> updateEntity(CoachManagement entity) {
		SysYh currentUser = getCurrentUser();
		CoachManagement byId = findById(entity.getId());
		RuntimeCheck.ifNull(byId, "未找到该记录");
		if(StringUtils.isNotBlank(entity.getJgdm()) && !StringUtils.equals(entity.getJgdm(),byId.getJgdm())){
			SysJg sysJg = jgService.findById(entity.getJgdm());
			SysJg jg = jgService.findById(entity.getJgdm().substring(0, entity.getJgdm().length() - 3));
			entity.setJgmc(jg.getJgmc()+ "/" + sysJg.getJgmc());
		}
		SimpleCondition condition = new SimpleCondition(Zgjbxx.class);
		condition.eq(Zgjbxx.InnerColumn.jlId, byId.getId());
		List<Zgjbxx> zgjbxxes = zgjbxxService.findByCondition(condition);
		if(CollectionUtils.isNotEmpty(zgjbxxes)){
			Zgjbxx zgjbxx = zgjbxxes.get(0);
			zgjbxx.setXm(entity.getCoachName());
			zgjbxx.setXb(entity.getGender().equalsIgnoreCase("10")?"男":"女");
			zgjbxx.setSjhm(entity.getPhone());
			zgjbxx.setSfzh(entity.getIdCardNo());
			zgjbxx.setJlzh(entity.getCoachNum());
			zgjbxx.setZjcx1(entity.getDrivingType());
			zgjbxxService.update(zgjbxx);

		}
		entity.setOperater(currentUser.getZh()+"-"+currentUser.getXm());
		entity.setOperateTime(DateUtils.getNowTime());
		update(entity);

		return ApiResponse.success();
	}

	@Override
	public ApiResponse<String> deleteEntity(String id) {
		CoachManagement management = findById(id);
		management.setCoachStatus("10");
		update(management);
		SimpleCondition condition = new SimpleCondition(Zgjbxx.class);
		condition.eq(Zgjbxx.InnerColumn.jlId,management.getId());
		List<Zgjbxx> byCondition = zgjbxxService.findByCondition(condition);
		if(CollectionUtils.isNotEmpty(byCondition)){
			Zgjbxx coachManagement = byCondition.get(0);
			coachManagement.setJlZt("20");
			zgjbxxService.update(coachManagement);
		}
		return ApiResponse.success();
	}
	/**
	 * 学员分配 coach_trainee_rercord（学员分配表）
	 * 1、学员要与该教练进行进行比对  教练必须满足学员的科目、教练的权限
	 * 1-1、该学员需要在有效期内
	 * 1-2、该学员必须已经缴费
	 * 1-3、该学员不等于退学
	 * 1-4、该演员不等于结业
	 *
	 * 2、学员如果已经分配了教练，就需要将原有教练标记为20(已转教练)
	 * @param trainees 学员列表
	 * @param km        学员要培训的科目
	 * @param coach     教练ID
	 * @return
	 */
	@Override
	public ApiResponse<String> traineeAllocation(String trainees, String km, String coach){
		SysYh sysUser=getCurrentUser();
		RuntimeCheck.ifBlank(trainees, "分配的学员不能为空");
		RuntimeCheck.ifBlank(km, "请确定分配的科目");
		RuntimeCheck.ifBlank(coach, "请选择分配的教练");
		RuntimeCheck.ifFalse(StringUtils.indexOf("01、02、03、04",km)>-1,"科目编码不正确，请核实");

		SimpleCondition condition = new SimpleCondition(CoachManagement.class);
		condition.eq(CoachManagement.InnerColumn.coachStatus,"00");//在职
		condition.eq(CoachManagement.InnerColumn.id,coach);//教练员ID
		condition.like(CoachManagement.InnerColumn.coachSub,km);//科目

		//教练证有效时间
//		condition.lte(CoachManagement.InnerColumn.coachStartTime,DateUtils.getToday());//<= new Date
//		condition.gte(CoachManagement.InnerColumn.coachEndTime,DateUtils.getToday());// endTime>= new Date
		condition.setOrderByClause(CoachManagement.InnerColumn.id.desc());

		List<CoachManagement> orgs = findByCondition(condition);
		CoachManagement coachManagement=null;
		if(orgs!=null&&orgs.size()>0){
			coachManagement=orgs.get(0);
		}else{
			return ApiResponse.fail("该教练不存在，或者该教练员不教此科目。");
		}

		List<String> trainee=Arrays.asList(trainees.split(","));
		List<String> changeTraineeLog=new ArrayList<>();//更换教练员，需要将原来的学员标记为退学
		List<CoachTraineeRercord> coachTraineeRercords=new ArrayList<>();//批量插入"教练学员分配记录表"
		List<String> updateTraineeTypeList=new ArrayList<>();//所有已经分配完成的用户

		String drivingType=coachManagement.getDrivingType();//该教练的准驾车型
//		1、该学员需要在有效期内
//		2、该学员必须已经缴费
//		3、该学员不等于退学
//		4、该演员不等于结业
		SimpleCondition traineeCondition = new SimpleCondition(TraineeInformation.class);


		traineeCondition.in(TraineeInformation.InnerColumn.id, trainee);
//		该学员不等于退学、该演员不等于结业
		traineeCondition.and().andNotEqualTo(TraineeInformation.InnerColumn.status.name(),"60");
		traineeCondition.and().andNotEqualTo(TraineeInformation.InnerColumn.status.name(),"50");
//		该学员需要在有效期内
//		traineeCondition.lte(TraineeInformation.InnerColumn.indateStartTime.name(),DateUtils.getToday());//<= new Date
//		traineeCondition.gte(TraineeInformation.InnerColumn.indateEndTime.name(),DateUtils.getToday());// endTime>= new Date
//		该学员必须已经缴费
		traineeCondition.eq(TraineeInformation.InnerColumn.chargeStatus,"10");//收费状态 00:未收费 10：已收费

//		该学员必须要在该教练准驾车型内
//		traineeCondition.and().andCondition(" STRCMP( CAR_TYPE ,'"+drivingType.toUpperCase()+"') > -1 ");//查询出所有满足条件车型


////		该学员如果已经分配过教练，就不能再次分配
//		traineeCondition.and().andCondition(" (SELECT COUNT(1) FROM COACH_TRAINEE_RERCORD R WHERE R.TRAINEE_ID=ID AND R.ALLOT_SUB='"+km+"' AND R.STATUS='00')=0 ");//查询出所有满足条件车型

		traineeCondition.setOrderByClause(TraineeInformation.InnerColumn.id.desc());
		List<TraineeInformation> traineeList =traineeInformationService.findByCondition(traineeCondition);
		String km2Code=null,km2Name=null,km3Code=null,km3Name=null;
		Map<String,TraineeInformation> traineeInformationMap=new HashMap<>();
		if(traineeList!=null&&traineeList.size()>0){
			for(TraineeInformation t:traineeList) {
				//验证当前要分配的用户，是否在该教练的准驾车型内
				if (StringUtils.indexOf(drivingType.toUpperCase(),t.getCarType()) > -1) {
//					获取当前教练员的姓名和编码
					if (StringUtils.equals(km, "02")) {
						km2Code = coachManagement.getId();
						km2Name = coachManagement.getCoachName();
//						当前用户如果是 更换教练 将该分配记录 标记为20 (已转教练)
						if (StringUtils.isNotEmpty(t.getSecondSubjectCoach())) {
							changeTraineeLog.add(t.getId());
						}
					} else if (StringUtils.equals(km, "03")) {
						km3Code = coachManagement.getId();
						km3Name = coachManagement.getCoachName();
//						当前用户如果是 更换教练 将该分配记录 标记为20 (已转教练)
						if (StringUtils.isNotEmpty(t.getThirdSubjectCoach())) {
							changeTraineeLog.add(t.getId());
						}
					}
					CoachTraineeRercord c = new CoachTraineeRercord();
					c.setId(genId());
					c.setCoachId(coachManagement.getId());//教练id
					c.setCoachName(coachManagement.getCoachName());//教练姓名
					c.setTraineeId(t.getId());//学员id
					c.setTraineeName(t.getName());//学员姓名
					c.setIdCardNo(t.getIdCardNo());//学员身份证号
					c.setAllotTime(DateUtils.getNowTime());//分配时间
					c.setStatus("00");//学员状态  00:学习中 10：学习结束 20:已转教练 30：已退学
					c.setAllotSub(km);//分配科目
					c.setCjr(sysUser.getZh() + "-" + sysUser.getXm());
					c.setCjsj(DateUtils.getNowTime());
					c.setModifier(sysUser.getZh() + "-" + sysUser.getXm());
					c.setModifyTime(DateUtils.getNowTime());

					coachTraineeRercords.add(c);

					updateTraineeTypeList.add(t.getId());
					traineeInformationMap.put(t.getId(),t);
				}
			}
        }else {//如果该学员已经有了教练，还要再分配教练，就是要做更换教练的操作
            return ApiResponse.fail("学员未完成分配，请核实此批学员是否已经完成缴费");
        }

		if(changeTraineeLog!=null&&changeTraineeLog.size()>0){
			coachTraineeRercordMapper.changeCoach(changeTraineeLog,km,sysUser.getZh()+"-"+sysUser.getXm(),DateUtils.getNowTime());
		}
		if(coachTraineeRercords!=null&&coachTraineeRercords.size()>0){
			coachTraineeRercordMapper.insertList(coachTraineeRercords);
		}

		//更新教练员状态表
		baseMapper.updateCoachTraineeCount(DateUtils.getToday());//DateUtils.getToday()
		if(StringUtils.indexOf("02、03",km)>-1){
			if(updateTraineeTypeList!=null&&updateTraineeTypeList.size()>0){
				//更新学员列表状态
				traineeMapper.usetCoach(updateTraineeTypeList,km2Code,km2Name,km3Code,km3Name);

				for(String t:updateTraineeTypeList){
					TraineeInformation traineeInfo=traineeInformationMap.get(t);
					if(traineeInfo!=null){
						SysMessage message=new SysMessage();
						message.setTitle("您已约考成功");
						String messageBody="";
						String userXb="先生";//性别 /* 00: 女  10: 男*/
						if(!StringUtils.equals(traineeInfo.getGender(),"10")){
							userXb="女士";
						}
	//				6、分配教练确认：【明涛驾校】尊敬的某某先生/女士：您分配的科目二教练为某某，联系电话：13000000000，并可通过“学员助手”登录系统查看教练、培训状态。培训完成后，您可通过“交管12123”预约科目二考试，考场请选择“檀树科目二考场”。培训中如有任何问题可留言建议或致电客服热线：400-133-2133。
	//				10、分配教练确认：【明涛驾校】尊敬的某某先生/女士：您分配的科目三教练为某某，联系电话：13000000000，并可通过“学员助手”登录系统查看教练、培训状态。培训完成后，您可通过“交管12123”预约科目三考试，考场请选择“新农科目三考场”。培训中如有任何问题可留言建议或致电客服热线：400-133-2133。
						if(StringUtils.equals(km,"02")){
							messageBody="{\"first\":\"尊敬的"+traineeInfo.getName()+userXb+"：\",\"keyword1\":\"分配科目二教练成功\",\"keyword2\":\"教练分配成功\",\"remark\":\"您分配的科目二教练"+coachManagement.getCoachName()+"，联系电话："+coachManagement.getPhone()+"，并可通过“学员助手”登录系统查看教练、培训状态。培训完成后，您可通过“交管12123”预约科目二考试，考场请选择“檀树科目二考场”。培训中如有任何问题可留言建议或致电客服热线：400-133-2133。\"}";
						}else if(StringUtils.equals(km,"03")){
							messageBody="{\"first\":\"尊敬的"+traineeInfo.getName()+userXb+"：\",\"keyword1\":\"分配科目三教练成功\",\"keyword2\":\"教练分配成功\",\"remark\":\"您分配的科目三教练"+coachManagement.getCoachName()+"，联系电话："+coachManagement.getPhone()+"，并可通过“学员助手”登录系统查看教练、培训状态。培训完成后，您可通过“交管12123”预约科目三考试，考场请选择“新农科目三考场”。培训中如有任何问题可留言建议或致电客服热线：400-133-2133。\"}";
						}
						message.setParameterBody(messageBody);//参数
						message.setBizId("xy001");//业务ID
						message.setUserId(traineeInfo.getId());//接收者USER_ID
						message.setUserName(traineeInfo.getName());//接收者USER_ID
						message.setUserRole("1");//1、学员 2、教练 3、管理员
						if(StringUtils.isNotEmpty(messageBody)){
							messageService.sendMessage(message,traineeInfo.getOpenId(),traineeInfo.getPhone());
						}
					}
				}
			}
		}


		return ApiResponse.success();
	}

	@Override
	public void updateCoachTraineeCount(){
		baseMapper.updateCoachTraineeCount(DateUtils.getToday());//DateUtils.getToday()

	}
}