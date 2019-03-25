package com.ldz.wechat.service;

import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.DropoutRecord;

public interface DropoutRecordService extends BaseService<DropoutRecord, String> {
    ApiResponse<String> saveEntity(DropoutRecord dropoutRecord );

}