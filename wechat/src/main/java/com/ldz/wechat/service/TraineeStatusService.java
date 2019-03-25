package com.ldz.wechat.service;

import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.TraineeInformation;
import com.ldz.wechat.model.TraineeStatus;

import java.util.List;

public interface TraineeStatusService extends BaseService<TraineeStatus, String> {
    ApiResponse<List<TraineeStatus>> queryList(TraineeStatus status);

    /**
     *
     * @param traineeInfo  学员信息
     * @param type          00 成功 10 失败
     * @param status        类型
     * @param remark        备注
     * @return
     */
    int saveEntity(TraineeInformation traineeInfo, String type, String status, String remark);
}