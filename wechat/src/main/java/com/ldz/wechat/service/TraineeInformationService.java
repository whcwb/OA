package com.ldz.wechat.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.CoachManagement;
import com.ldz.wechat.model.CoachTraineeRercord;
import com.ldz.wechat.model.TraineeInformation;

import java.util.List;
import java.util.Map;

public interface TraineeInformationService extends BaseService<TraineeInformation, String> {


    TraineeInformation queryPhone(String phone);

    ApiResponse<PageInfo<Map>> getTraineeList(CoachTraineeRercord coachTraineeRercord,String status,String carType,Page page);

    ApiResponse<CoachManagement> queryMyCoach(String traineeId,String sub);

    ApiResponse<List<Map>> queryTop10Trainee(String traineeId);

    ApiResponse<List<TraineeInformation>> queryPager(String startTime ,String endTime ,Page<TraineeInformation> pager,TraineeInformation info);

    ApiResponse<String> updateEntity(TraineeInformation entity);

    ApiResponse<String> userInfoCheck(TraineeInformation entity);

    /**
     * 报名信息回退
     * @param traineeId
     * @return
     */
    ApiResponse<String> revokeInfo(String traineeId);


}