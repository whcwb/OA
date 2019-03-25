package com.ldz.wechat.service;

import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.TraineeTestInfo;
import com.ldz.wechat.model.TrainingRecord;

import java.util.List;

public interface TraineeTestInfoService extends BaseService<TraineeTestInfo, String> {

    ApiResponse<List<TraineeTestInfo>> queryList (TraineeTestInfo info);

}