package com.ldz.biz.car.controller;


import com.github.pagehelper.Page;
import com.ldz.biz.car.model.BizCarAnnualExam;
import com.ldz.biz.car.service.BizCarAnnualExamService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 车管车辆车管年审信息
 */
@RestController
@RequestMapping("/api/carns")
public class BizCarAnnualExamController extends BaseController<BizCarAnnualExam,String> {
    @Autowired
    private BizCarAnnualExamService service;

    @Override
    protected BaseService<BizCarAnnualExam, String> getBaseService() {
        return service;
    }


    /**
     * 按车辆ID查询该车的油改气列表
     * @param clId
     * @return
     */
    @RequestMapping(value="/clnslb", method={RequestMethod.POST})
    public ApiResponse<List<BizCarAnnualExam>> getCarAnnualExamList (String clId){
        return service.getCarAnnualExamList(clId);
    }
    /**
     * 备案人设置
     * @return
     */
    @RequestMapping(value="/clnsbar", method={RequestMethod.POST})
    public ApiResponse<String> updKeepOnRecord (BizCarAnnualExam exam){
        return service.updKeepOnRecord(exam);
    }
    /**
     * 分页接口
     */
    @PostMapping("/getPage")
    public ApiResponse<Map<String,Long>> pager(Page<BizCarAnnualExam> page){
        return service.getPager(page);
    }



}