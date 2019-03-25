package com.ldz.wechat.service;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.CoachManagement;

import java.util.Map;

public interface CoachManagementService extends BaseService<CoachManagement, String> {


    CoachManagement queryPhone(String phone);

    CoachManagement queryBySenceUrl(String url);

    ApiResponse<PageInfo<Map>> queryPage(Page<Map> page);

    ApiResponse<PageInfo<CoachManagement>> getCoachPage(int pageNum,int pageSize,CoachManagement coachManagement,String orderBy);
}