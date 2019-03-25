package com.ldz.wechat.service;

import com.github.pagehelper.Page;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.GroupBuyRecord;
import com.ldz.wechat.model.RateDetail;

import java.util.List;

public interface RateDetailService extends BaseService<RateDetail, String> {

    ApiResponse<String> saveDetail(RateDetail entity);

    ApiResponse<List<RateDetail>> queryPager(Page<RateDetail> pager, RateDetail entity);
}