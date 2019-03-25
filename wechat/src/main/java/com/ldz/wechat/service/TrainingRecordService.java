package com.ldz.wechat.service;

import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.TrainingRecord;

import java.util.List;
import java.util.Map;

public interface TrainingRecordService extends BaseService<TrainingRecord, String> {
        ApiResponse<List<TrainingRecord>> getList (TrainingRecord record);
        ApiResponse<Map> queryRecordByDay(String traineeId,String sub);
        ApiResponse<String> saveEntity(TrainingRecord trainingRecord);
        String afterScan(String eventKey,String openId,String userId);

        void afterScanWeb(String coachId);
}