package com.ldz.biz.car.controller;


import com.ldz.biz.car.model.BizCarSyr;
import com.ldz.biz.car.service.BizCarSyrService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * /api/clsyr/getAll
 * 车辆所有人
 */
@RestController
@RequestMapping("/api/clsyr")
public class BizCarSyrController extends BaseController<BizCarSyr,String> {
    @Autowired
    private BizCarSyrService service;

    @Override
    protected BaseService<BizCarSyr, String> getBaseService() {
        return service;
    }

    @Override
    @RequestMapping(value="/getAll", method={RequestMethod.GET,RequestMethod.POST})
    public ApiResponse<List<BizCarSyr>> getAll(){
        return ApiResponse.success(getBaseService().findAll());
    }

}
