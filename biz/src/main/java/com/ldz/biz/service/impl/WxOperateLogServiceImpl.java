package com.ldz.biz.service.impl;

import com.ldz.biz.mapper.WxOperateLogMapper;
import com.ldz.biz.model.ArchivesRecord;
import com.ldz.biz.model.RecordManagement;
import com.ldz.biz.model.TraineeInformation;
import com.ldz.biz.model.WxOperateLog;
import com.ldz.biz.service.ArchivesRecordService;
import com.ldz.biz.service.RecordManagementService;
import com.ldz.biz.service.TraineeInformationService;
import com.ldz.biz.service.WxOperateLogService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.model.SysYh;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;


@Service
public class WxOperateLogServiceImpl extends BaseServiceImpl<WxOperateLog, String> implements WxOperateLogService {

	@Autowired
	private WxOperateLogMapper baseMapper;

	@Override
	protected Mapper<WxOperateLog> getBaseMapper() {
		return baseMapper;
	}

	@Override
	public int saveEntity(SysYh user, String operateId, String operateType, String operateDescribe,String type){
		WxOperateLog entity=new WxOperateLog();
		entity.setId(genId());
		entity.setType(type);
		entity.setOperateId(operateId);
		entity.setCjr(user.getYhid()+"-"+user.getXm());
		entity.setCjsj(DateUtils.getNowTime());
		entity.setJgdm(user.getJgdm());
		entity.setOperatetType(operateType);
		entity.setOperateDescribe(operateDescribe);
		return baseMapper.insert(entity);
	}

}