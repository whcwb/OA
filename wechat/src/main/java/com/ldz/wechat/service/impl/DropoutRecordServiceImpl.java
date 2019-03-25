package com.ldz.wechat.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.wechat.mapper.DropoutRecordMapper;
import com.ldz.wechat.model.DropoutRecord;
import com.ldz.wechat.service.DropoutRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class DropoutRecordServiceImpl extends BaseServiceImpl<DropoutRecord, String> implements DropoutRecordService {

	@Autowired
	private DropoutRecordMapper baseMapper;
	
	@Override
	protected Mapper<DropoutRecord> getBaseMapper() {
		return baseMapper;
	}

	@Override
	public ApiResponse<String> saveEntity(DropoutRecord dropoutRecord) {
		dropoutRecord.setId(genId());
		dropoutRecord.setCjsj(DateUtils.getNowTime());
		baseMapper.insert(dropoutRecord);
		return ApiResponse.saveSuccess();
	}
}