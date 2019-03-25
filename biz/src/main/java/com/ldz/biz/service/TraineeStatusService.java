package com.ldz.biz.service;

import com.ldz.biz.model.TraineeInformation;
import com.ldz.biz.model.TraineeStatus;
import com.ldz.sys.base.BaseService;

public interface TraineeStatusService extends BaseService<TraineeStatus, String> {

//    int saveEntity(TraineeStatus addTraineeStatus);

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