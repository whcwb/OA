package com.ldz.biz.service;


import com.ldz.biz.model.Zgjbxx;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

import java.util.List;

public interface ZgjbxxService extends BaseService<Zgjbxx, String> {

    ApiResponse<String> saveEntity(Zgjbxx entity);

    ApiResponse<String> updateEntity(Zgjbxx entity);

    ApiResponse<List<Zgjbxx>> getAqy();

    ApiResponse<String> setSecurityOfficerSignin(Zgjbxx entity);

    void clearAqyQd();
}