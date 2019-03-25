package com.ldz.biz.car.controller;


import com.ldz.biz.car.model.BizCarGas;
import com.ldz.biz.car.service.BizCarGasService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 油改气记录表
 */
@RestController
@RequestMapping("/api/cargas")
public class BizCarGasController extends BaseController<BizCarGas,String> {
    @Autowired
    private BizCarGasService service;

    @Override
    protected BaseService<BizCarGas, String> getBaseService() {
        return service;
    }

    /**
     * 按车辆ID查询该车的油改气列表
     * @param clId
     * @return
     */
    @RequestMapping(value="/clgxlb", method={RequestMethod.POST})
    public ApiResponse<List<BizCarGas>> getCarGasList (String clId){
        return service.getCarGasList(clId);
    }



}