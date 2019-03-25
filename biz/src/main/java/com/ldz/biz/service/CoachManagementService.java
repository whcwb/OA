package com.ldz.biz.service;

import com.ldz.biz.model.CoachManagement;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

public interface CoachManagementService extends BaseService<CoachManagement, String> {

    ApiResponse<String> updateEntity(CoachManagement entity);

    ApiResponse<String> deleteEntity(String id);
    /**
     * 学员分配
     * @param trainees 学员列表
     * @param km        学员要培训的科目
     * @param coach     教练ID
     * @return
     */
    ApiResponse<String> traineeAllocation(String trainees, String km, String coach);
    void updateCoachTraineeCount();

}