package com.ldz.biz.service;

import com.github.pagehelper.Page;
import com.ldz.biz.model.BizRk;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

public interface BizRkService extends BaseService<BizRk, String> {

    ApiResponse<String> getPc(Page<BizRk> page);
}