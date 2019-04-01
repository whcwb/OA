package com.ldz.biz.service;

import com.ldz.biz.model.BizKc;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

import java.util.List;

public interface BizKcService extends BaseService<BizKc, String> {

    ApiResponse<String> saveEntity(BizKc entity);

    ApiResponse<String> add(String id, Integer sl, String bz,float rkDj);

    ApiResponse<String> handOut(String id, Integer sl, String zgId, String bz, String jgdm);

    void removeEntity(String id);

    ApiResponse<String> saveList(List<BizKc> kcs);

    ApiResponse<String> appendPc(List<BizKc> kcs,String pc);
}