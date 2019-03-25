package com.ldz.biz.car.controller;


import com.ldz.biz.car.model.BizCar;
import com.ldz.biz.car.model.BizCarInsuranceJq;
import com.ldz.biz.car.service.BizCarInsuranceJqService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 车辆保险
 */
@RestController
@RequestMapping("/api/carbx")
public class BizCarInsuranceController extends BaseController<BizCarInsuranceJq,String> {
    @Autowired
    private BizCarInsuranceJqService service;

    @Override
    protected BaseService<BizCarInsuranceJq, String> getBaseService() {
        return service;
    }


    /**
     * 按车辆ID查询该车的保险列表
     * @param clId
     * @return
     */
    @RequestMapping(value="/clbxlb", method={RequestMethod.POST})
    public ApiResponse<List<BizCarInsuranceJq>> getCarInsuranceList (String clId){
        return service.getCarInsuranceList(clId);
    }

    /**
     * 新增前，通过车辆ID查询该车年审的状态
     * @param clId
     * @return
     */
    @RequestMapping(value="/clbxzt", method={RequestMethod.POST})
    public ApiResponse<Map<String,String>> queryCarInsuranceSaveType (String clId){
        return service.queryCarInsuranceSaveType(clId);
    }

    /**
     * 保单修改，仅修改主表字段
     * @param entity
     * @return
     */

    @RequestMapping(value="/updates", method={RequestMethod.POST})
    public ApiResponse<String> updates(BizCar entity){
        return service.validCarAndUpdate(entity);
    }
}