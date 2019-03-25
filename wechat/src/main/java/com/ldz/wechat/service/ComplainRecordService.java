package com.ldz.wechat.service;

import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.ComplainRecord;
public interface ComplainRecordService extends BaseService<ComplainRecord, String> {
    ApiResponse<String> saveEntity(ComplainRecord complainRecord );
    ApiResponse<String> updateCallBack(ComplainRecord obj);
}