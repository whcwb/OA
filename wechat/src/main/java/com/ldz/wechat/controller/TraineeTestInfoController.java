package com.ldz.wechat.controller;

import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.mapper.TraineeTestInfoMapper;
import com.ldz.wechat.model.TraineeTestInfo;
import com.ldz.wechat.service.TraineeTestInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 学员考试信息
 */
@RestController
@RequestMapping("/api/traineetestinfo")
public class TraineeTestInfoController extends BaseController<TraineeTestInfo,String> {
    @Autowired
    private TraineeTestInfoService service;

    @Autowired
    private TraineeTestInfoMapper traineeTestInfoMapper;

    @Override
    protected BaseService<TraineeTestInfo, String> getBaseService() {
        return service;
    }

    /**
     * 查询考试信息，时间排序
     * @param traineeTestInfo
     * @return
     */
    @RequestMapping("/getList")
    public ApiResponse<List<TraineeTestInfo>> getList(TraineeTestInfo traineeTestInfo){
        return service.queryList(traineeTestInfo);
    }

    /**
     * 考试次数
     * @param traineeId
     * @return
     */
    @RequestMapping("/countTestInfo")
    public ApiResponse<Integer> countTestInfo(@RequestParam("traineeId") String traineeId){
      return ApiResponse.success(traineeTestInfoMapper.countTraineeTestInfo(traineeId));
    }

}