package com.ldz.biz.controller;

import com.github.pagehelper.Page;
import com.ldz.biz.model.CoachValuation;
import com.ldz.biz.service.CoachValuationService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 教练评分
 */
@RestController
@RequestMapping("/api/coachvaluation")
public class CoachValuationController {
    @Autowired
    private CoachValuationService service;

    @RequestMapping(value="/pager", method={RequestMethod.POST, RequestMethod.GET})
    public ApiResponse<List<CoachValuation>> pager(CoachValuation entity, Page<CoachValuation> pager){
        return service.pager(pager);
    }


}