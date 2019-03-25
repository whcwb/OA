package com.ldz.biz.controller;

import com.github.pagehelper.Page;
import com.ldz.biz.model.CoachValuation;
import com.ldz.biz.model.RateDetail;
import com.ldz.biz.model.WxOperateLog;
import com.ldz.biz.service.CoachValuationService;
import com.ldz.biz.service.RateDetailService;
import com.ldz.biz.service.WxOperateLogService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.exception.RuntimeCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 教练评价表
 */
@RestController
@RequestMapping("/api/ratedetail")
public class RateDetailController {
    @Autowired
    private RateDetailService service;

    @Autowired
    private WxOperateLogService wxLogService;

    @RequestMapping(value="/pager", method={RequestMethod.POST, RequestMethod.GET})
    public ApiResponse<List<RateDetail>> pager(RateDetail entity, Page<RateDetail> pager){
        return service.pager(pager);
    }

    @RequestMapping(value="/remove/{pkid}", method={RequestMethod.POST, RequestMethod.GET})
    public ApiResponse<String> removeUserRateDetail(@PathVariable("pkid") String id){

        return service.removeUserRateDetail(id);
    }



}