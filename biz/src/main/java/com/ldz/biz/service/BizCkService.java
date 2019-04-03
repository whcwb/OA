package com.ldz.biz.service;

import com.ldz.biz.model.BizCk;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

public interface BizCkService extends BaseService<BizCk, String> {


    ApiResponse<String> getPager(String kcMc, String kcLx, String lqr, Integer pageNum, Integer pageSize);
}