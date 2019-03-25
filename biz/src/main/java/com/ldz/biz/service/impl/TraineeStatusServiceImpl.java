package com.ldz.biz.service.impl;

import com.ldz.biz.mapper.TraineeStatusMapper;
import com.ldz.biz.model.TraineeInformation;
import com.ldz.biz.model.TraineeStatus;
import com.ldz.biz.service.TraineeStatusService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.model.SysYh;
import com.ldz.util.commonUtil.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class TraineeStatusServiceImpl extends BaseServiceImpl<TraineeStatus, String> implements TraineeStatusService {

	@Autowired
	private TraineeStatusMapper baseMapper;
	
	@Override
	protected Mapper<TraineeStatus> getBaseMapper() {
		return baseMapper;
	}

	@Override
	public int saveEntity(TraineeInformation traineeInfo, String type, String status, String remark){
		SysYh sysUser = getCurrentUser();

		TraineeStatus addTraineeStatus=new TraineeStatus();

		addTraineeStatus.setId(genId());
		addTraineeStatus.setTraineeId(traineeInfo.getId());//學員ID
		addTraineeStatus.setTraineeName(traineeInfo.getName());//學員姓名
		addTraineeStatus.setIdCardNo(traineeInfo.getIdCardNo());//身份证号码
		addTraineeStatus.setOperator(sysUser.getYhid());//操作人
		addTraineeStatus.setOperateTime(DateUtils.getNowTime());//操作時間
		addTraineeStatus.setCjr(sysUser.getZh()+"-"+sysUser.getXm());
		addTraineeStatus.setCjsj(DateUtils.getNowTime());
		addTraineeStatus.setRemark(remark);//备注
		addTraineeStatus.setType(type);
		addTraineeStatus.setStatus(status);//状态
		return save(addTraineeStatus);
	}
}