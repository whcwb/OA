package com.ldz.biz.service;

import com.ldz.biz.model.BizKcLb;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

import java.util.List;

public interface BizKcLbService extends BaseService<BizKcLb, String> {


    ApiResponse<List<BizKcLb>> getAllLb();

    ApiResponse<String> saveEntity(BizKcLb entity);
}