package com.ldz.wechat.controller;

import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.TraineeStatus;
import com.ldz.wechat.service.TraineeStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 学员状态
 */
@RestController
@RequestMapping("/api/traineestatus")
public class TraineeStatusController extends BaseController<TraineeStatus,String> {
    @Autowired
    private TraineeStatusService service;

    @Override
    protected BaseService<TraineeStatus, String> getBaseService() {
        return service;
    }

    /**
     * 账户信息状态跟踪
     * @param traineeStatus
     * @return
     */
    @RequestMapping("/queryList")
    public ApiResponse<List<TraineeStatus>> queryList(TraineeStatus traineeStatus){
        return service.queryList(traineeStatus);
    }
}