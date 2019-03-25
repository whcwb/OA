package com.ldz.biz.car.service;

import com.ldz.biz.car.model.BizCarProperty;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

import java.util.List;

public interface BizCarPropertyService extends BaseService<BizCarProperty, java.lang.String> {

    ApiResponse<String> changeOfTitle(BizCarProperty entity);

    ApiResponse<String> cphTypeUpdat(BizCarProperty entity);
//clid,jbr,jbrDn,bfjl
    ApiResponse<String> vehicleScrapping(String clid, String jbr, String jbrDn, String bfjl,String propertyFileurl);

    ApiResponse<String> changeSell(BizCarProperty entity);

    ApiResponse<List<BizCarProperty>> getCarPropertyList(String clId);
}