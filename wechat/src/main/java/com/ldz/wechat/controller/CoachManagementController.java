package com.ldz.wechat.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.mapper.CoachManagementMapper;
import com.ldz.wechat.model.CoachManagement;
import com.ldz.wechat.service.CoachManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 教练员信息管理
 */
@RestController
@RequestMapping("/api/coachmanagement")
public class CoachManagementController extends BaseController<CoachManagement,String> {
    @Autowired
    private CoachManagementService service;

    @Autowired
    private CoachManagementMapper mapper;

    @Override
    protected BaseService<CoachManagement, String> getBaseService() {
        return service;
    }

    @GetMapping("/getMyRank")
    public ApiResponse<Map> getMyRank(String coachId){
        Map repMap = mapper.getMyRank(coachId);
        return ApiResponse.success(repMap);
    }

    /**
     * 查询教练排名
     * @param page
     * @return
     */
    @RequestMapping("/queryPager")
    public ApiResponse<PageInfo<Map>> queryPager(Page<Map> page){
        return service.queryPage(page);
    }

    /**
     *查询教练列表，用于学生端查询,不做机构鉴别
     * @param page
     * @param coachManagement
     * @return
     */
    @RequestMapping("/pager_V2")
    public ApiResponse<PageInfo<CoachManagement>> getPager(int pageNum,int pageSize,CoachManagement coachManagement,String orderBy){
        return service.getCoachPage(pageNum,pageSize,coachManagement,orderBy);

    }

}