package com.ldz.wechat.controller;

import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.CoachValuation;
import com.ldz.wechat.model.ComplainRecord;
import com.ldz.wechat.service.CoachValuationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 教练评价
 */
@RestController
@RequestMapping("/api/coachvaluation")
public class CoachValuationController extends BaseController<CoachValuation,String> {
    @Autowired
    private CoachValuationService service;

    @Override
    protected BaseService<CoachValuation, String> getBaseService() {
        return service;
    }

    /**
     *
     * @param coachValuation
     * @return
     */
    @RequestMapping("/saveEntity")
    public ApiResponse<String> saveEetity(CoachValuation coachValuation){
        return service.saveEntity(coachValuation);
    }



}