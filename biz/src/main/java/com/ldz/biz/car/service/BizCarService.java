package com.ldz.biz.car.service;

import com.ldz.biz.car.model.BizCar;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

import java.util.List;
import java.util.Map;

public interface BizCarService extends BaseService<BizCar, java.lang.String> {
    
    public ApiResponse<String> validAndSaveCar(BizCar entity);

    public ApiResponse<String> validAndUpdateCar(BizCar entity);

    public ApiResponse<String> updERecode(BizCar entity);


    ApiResponse<String> getArchivesSeq();

    ApiResponse<String> updCarOwner(BizCar entity);

    ApiResponse<String> updCarRecordHolder(BizCar entity);

    ApiResponse<List<Map<String, String>>> getHolderHistory(String clId);

    ApiResponse<Map<String, Object>> getCarArchives(String clId);
}