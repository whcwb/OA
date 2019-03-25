package com.ldz.wechat.service.impl;


import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.base.WxBaseServiceImpl;
import com.ldz.wechat.mapper.ArchivesRecordMapper;
import com.ldz.wechat.model.ArchivesRecord;
import com.ldz.wechat.model.TraineeInformation;
import com.ldz.wechat.service.ArchivesRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class ArchivesRecordServiceImpl extends WxBaseServiceImpl<ArchivesRecord, String> implements ArchivesRecordService {

	@Autowired
	private ArchivesRecordMapper baseMapper;

	@Override
	protected Mapper<ArchivesRecord> getBaseMapper() {
		return baseMapper;
	}


	@Override
	public ApiResponse<String> validAndSave(ArchivesRecord entity){
		//这里代码测试使用
		TraineeInformation currentUser = getTraineeCurrentUser(true);
		entity.setCjsj(currentUser.getId());
		return ApiResponse.saveSuccess();
	}
}