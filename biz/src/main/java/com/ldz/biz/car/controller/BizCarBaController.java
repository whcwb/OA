package com.ldz.biz.car.controller;


import com.ldz.biz.car.model.BizCarBa;
import com.ldz.biz.car.service.BizCarBaService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 汽车运管备案表
 */
@RestController
@RequestMapping("/api/carba")
public class BizCarBaController extends BaseController<BizCarBa,String> {
    @Autowired
    private BizCarBaService service;

    @Override
    protected BaseService<BizCarBa, String> getBaseService() {
        return service;
    }

    /**
     * 按车辆ID查询该车的运管备案表列表
     * @param clId
     * @return
     */
    @RequestMapping(value="/clgxlb", method={RequestMethod.POST})
    public ApiResponse<List<BizCarBa>> getCarBaList (String clId){
        return service.getCarBaList(clId);
    }
}