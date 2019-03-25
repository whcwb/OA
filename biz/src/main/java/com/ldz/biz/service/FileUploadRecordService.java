package com.ldz.biz.service;

import com.ldz.biz.model.FileUploadRecord;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

public interface FileUploadRecordService extends BaseService<FileUploadRecord, String> {

    ApiResponse<String> getFileId(String lx,int pageNum, int pageSize);
}