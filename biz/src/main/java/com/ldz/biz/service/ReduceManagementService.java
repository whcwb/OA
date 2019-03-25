package com.ldz.biz.service;

import com.ldz.biz.model.ReduceManagement;
import com.ldz.biz.model.ValueLabelModel;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

import java.util.List;

public interface ReduceManagementService extends BaseService<ReduceManagement, String> {

    ApiResponse<String> saveGroup(ReduceManagement entity);

    ApiResponse<String> updateEntity(ReduceManagement entity);
    /**
     * 获取机构下所有的优惠列表
     * @param jgdm  机构代码
     * @return
     */
    ApiResponse<List<ReduceManagement>> getJgReduce(String jgdm);

    ApiResponse<List<ValueLabelModel>> findValueLabel();

    ApiResponse<String> removeEntity(String id);
}