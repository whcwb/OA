package com.ldz.biz.service;

import com.ldz.biz.model.ComplainRecord;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

public interface ComplainRecordService extends BaseService<ComplainRecord, String> {

    ApiResponse<String> updateCallBack(ComplainRecord obj);
}