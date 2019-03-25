package com.ldz.biz.service;

import com.github.pagehelper.Page;
import com.ldz.biz.model.ZgTjjl;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

public interface ZgTjjlService extends BaseService<ZgTjjl,String> {
    int countByTime(String startTime, String endTime, String zgId);

    ApiResponse<String> getReferrerRecord(Page<ZgTjjl> page);
}
