package com.ldz.biz.car.controller;


import com.ldz.biz.car.model.BizCarUsage;
import com.ldz.biz.car.service.BizCarUsageService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 车辆使用人表
 */
@RestController
@RequestMapping("/api/carsyr")
public class BizCarUsageController extends BaseController<BizCarUsage,String> {
    @Autowired
    private BizCarUsageService service;

    @Override
    protected BaseService<BizCarUsage, String> getBaseService() {
        return service;
    }

    /**
     * 使用人绑定
     * @param entity
     * clId     车辆ID
     * syrId   使用人编号，从人事表中获取。非必填字段
     * syrName   使用人姓名
     * syrSzd   使用人所在地
     * syrDn   使用人联系方式
     * @return
     */
    @Override
    @RequestMapping(value="/save", method={RequestMethod.POST})
    public ApiResponse<String> save(BizCarUsage entity){
        return service.validAndSaveUsage(entity);
    }

}