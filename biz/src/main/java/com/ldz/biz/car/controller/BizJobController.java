package com.ldz.biz.car.controller;

import com.ldz.biz.car.service.BizCarWarnService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pub/job")
public class BizJobController {

    @Autowired
    private BizCarWarnService service;

    /**
     * 定时任务生成告警记录
     * @param data
     * @return
     */
    @RequestMapping(value="/jobsavenwarn", method={RequestMethod.POST,RequestMethod.GET})
    public ApiResponse<String> jobSaveWarn(String data){
        return service.jobSaveWarn(data);
    }
}
