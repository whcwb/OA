package com.ldz.wechat.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.model.SysYh;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.wechat.base.WxBaseController;
import com.ldz.wechat.mapper.ComplainRecordMapper;
import com.ldz.wechat.model.CoachManagement;
import com.ldz.wechat.model.ComplainRecord;
import com.ldz.wechat.model.TraineeInformation;
import com.ldz.wechat.service.CoachManagementService;
import com.ldz.wechat.service.ComplainRecordService;
import com.ldz.wechat.service.TraineeInformationService;
import com.ldz.wechat.service.WxOperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class ComplainRecordServiceImpl extends BaseServiceImpl<ComplainRecord, String> implements ComplainRecordService {

	@Autowired
	private ComplainRecordMapper baseMapper;

	@Autowired
	private  CoachManagementService coachManagementService;
	@Autowired
	private TraineeInformationService traineeInformationService;
	@Autowired
	private WxOperateLogService wxOperateLogService;

	@Override
	protected Mapper<ComplainRecord> getBaseMapper() {
		return baseMapper;
	}

	@Override
	public ApiResponse<String> saveEntity(ComplainRecord complainRecord) {
		String coachId=complainRecord.getCoachId();
		RuntimeCheck.ifBlank(coachId, "请确定要投诉的教练不能为空");
		CoachManagement coachManagement=coachManagementService.findById(coachId);
		RuntimeCheck.ifNull(coachManagement, "教练信息有误，投诉失败");
		String traineeId=complainRecord.getTraineeId();
		TraineeInformation traineeInformation=traineeInformationService.findById(traineeId);
		RuntimeCheck.ifNull(traineeInformation, "学生信息有误，投诉失败");

		complainRecord.setCoachPhone(coachManagement.getPhone());//教练电话
		complainRecord.setCoachHeadImg(coachManagement.getHeadImg());//教练头像
		complainRecord.setTraineeName(traineeInformation.getName());//学员姓名
		complainRecord.setTraineeHeadImg(traineeInformation.getHeadImg());//学员头像
		complainRecord.setTraineePhone(traineeInformation.getPhone());//学员电话

		complainRecord.setId(genId());
		complainRecord.setCjsj(DateUtils.getNowTime());
		complainRecord.setComplainTime(DateUtils.getNowTime());
		baseMapper.insert(complainRecord);
		return ApiResponse.saveSuccess();
	}

	@Override
	public ApiResponse<String> updateCallBack(ComplainRecord obj){
		SysYh currentUser = WxBaseController.getBossCurrentUser(true);
		obj.setOperateTime(DateUtils.getNowTime());
		obj.setOperater(currentUser.getYhid());
		obj.setOperaterName(currentUser.getXm());
		//审核完成设置为可见
		obj.setVisiable("1");
		wxOperateLogService.saveEntity(currentUser,obj.getId(),obj.getComplainStatus(),obj.getFeedback(),"1");
		return this.update(obj)>0?ApiResponse.success():ApiResponse.fail("数据库操作失败");
	}
}