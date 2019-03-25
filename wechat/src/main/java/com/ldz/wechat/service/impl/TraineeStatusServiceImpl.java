package com.ldz.wechat.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.model.SysYh;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.wechat.base.WxBaseController;
import com.ldz.wechat.mapper.TraineeStatusMapper;
import com.ldz.wechat.model.TraineeInformation;
import com.ldz.wechat.model.TraineeStatus;
import com.ldz.wechat.model.TraineeTestInfo;
import com.ldz.wechat.service.TraineeStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Service
public class TraineeStatusServiceImpl extends BaseServiceImpl<TraineeStatus, String> implements TraineeStatusService {

	@Autowired
	private TraineeStatusMapper baseMapper;
	
	@Override
	protected Mapper<TraineeStatus> getBaseMapper() {
		return baseMapper;
	}

	@Override
	public ApiResponse<List<TraineeStatus>> queryList(TraineeStatus status) {
		SimpleCondition condition = new SimpleCondition(TraineeStatus.class);
		condition.eq(TraineeStatus.InnerColumn.traineeId, status.getTraineeId());
		condition.setOrderByClause(TraineeTestInfo.InnerColumn.id.desc());
		List<TraineeStatus> list = this.findByCondition(condition);
		return ApiResponse.success(list);
	}

	@Override
	public int saveEntity(TraineeInformation traineeInfo, String type, String status, String remark){
		SysYh sysUser = WxBaseController.getBossCurrentUser(true);

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