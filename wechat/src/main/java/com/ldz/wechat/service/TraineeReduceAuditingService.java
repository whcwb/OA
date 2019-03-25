package com.ldz.wechat.service;

import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.TraineeInformation;

public interface TraineeReduceAuditingService extends BaseService<TraineeInformation, String> {

    /**
     * 用户审核操作
     *
     * @param entity  id            学员主键
     * @param entity  reduceStatus  优惠信息审核状态  00:未审核 10：审核通过 20：审核未通过
     * @param entity  reduceRemark 优惠备注
     * @return
     */
    ApiResponse<String> setUserReduceAuditing(TraineeInformation entity);

    /**
     * 撤销学员审核操作
     *
     * @param entity  id            学员主键
     * @param entity  reduceStatus  优惠信息审核状态  00:未审核 10：审核通过 20：审核未通过
     * @param entity  reduceRemark 优惠备注
     * @return
     */
    ApiResponse<String> recallUserAuditing(TraineeInformation entity);
}