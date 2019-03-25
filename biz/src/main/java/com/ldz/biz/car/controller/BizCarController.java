package com.ldz.biz.car.controller;


import com.ldz.biz.car.model.BizCar;
import com.ldz.biz.car.service.BizCarService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 车辆基础表
 */
@RestController
@RequestMapping("/api/car")
public class BizCarController extends BaseController<BizCar,String> {
    @Autowired
    private BizCarService service;

    @Override
    protected BaseService<BizCar, String> getBaseService() {
        return service;
    }
    /**
     * 获取最新的档案号
     */
    @PostMapping("/getdaseq")
    public ApiResponse<String> getArchivesSeq(){
        return service.getArchivesSeq();
    }


    /**
     * 车辆基础资料新增
     * @param entity
     * @return
     */
    @Override
    @RequestMapping(value="/save", method={RequestMethod.POST})
    public ApiResponse<String> save(BizCar entity){
        return service.validAndSaveCar(entity);
    }
    /**
     * 车辆基础资料修改
     * @param entity
     * @return
     */
    @Override
    @RequestMapping(value="/update", method={RequestMethod.POST})
    public ApiResponse<String> update(BizCar entity){
        return service.validAndUpdateCar(entity);
    }


    /**
     * 电子档案修改
     * @param entity
     *
     * @return
     */
    @RequestMapping(value="/dzda", method={RequestMethod.POST})
    public ApiResponse<String> updERecode(BizCar entity){
        return service.updERecode(entity);
    }

    /**
     * 获取车辆基本信息
     * @param clId
     * @return
     */
    @Override
    @RequestMapping(value="/get", method={RequestMethod.POST})
    public ApiResponse<BizCar> get(String clId){
        return ApiResponse.success(service.findById(clId));
    }


    /**
     * 车辆所有人变更
     * @param entity
     * @return
     */
    @RequestMapping(value="/setryrbg", method={RequestMethod.POST})
    public ApiResponse<String> updCarOwner(BizCar entity){
        return service.updCarOwner(entity);
    }
    /**
     * 车辆年审备案人修改
     * @param entity   RecordHolder
     * @return
     */
    @RequestMapping(value="/setnsba", method={RequestMethod.POST})
    public ApiResponse<String> updCarRecordHolder(BizCar entity){
        return service.updCarRecordHolder(entity);
    }

    /**
     * 获取车辆所有人历史信息
     * @param clId  车辆ID
     * @return
     */
    @RequestMapping(value="/getsyrlx", method={RequestMethod.POST})
    public ApiResponse<List<Map<String,String>>> getHolderHistory(String clId){
        return service.getHolderHistory(clId);
    }

    /**
     * 获取车辆档案
     * @param clId  车辆ID
     *
     * @return
     */
    @RequestMapping(value="/getCarArchives", method={RequestMethod.POST})
    public ApiResponse<Map<String,Object>> getCarArchives(String clId){
        return service.getCarArchives(clId);
    }
    @RequestMapping(value="/remove/{pkid}", method={RequestMethod.POST})
    public ApiResponse<String> remove(@PathVariable("pkid")String id){
        return ApiResponse.success();
    }

    @RequestMapping(value="/removeIds", method={RequestMethod.POST})
    public ApiResponse<String> remove(String[] ids){
        return ApiResponse.success();
    }



}