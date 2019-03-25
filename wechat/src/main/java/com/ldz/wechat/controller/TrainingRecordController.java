package com.ldz.wechat.controller;

import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.mapper.TrainingRecordMapper;
import com.ldz.wechat.model.TrainingRecord;
import com.ldz.wechat.service.TrainingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.PrivateKey;
import java.util.List;
import java.util.Map;

/**
 * 培训记录
 */
@RestController
@RequestMapping("/api/trainingrecord")
public class TrainingRecordController extends BaseController<TrainingRecord,String> {
    @Autowired
    private TrainingRecordService service;

    @Autowired
    private TrainingRecordMapper trainingRecordMapper;

    @Override
    protected BaseService<TrainingRecord, String> getBaseService() {
        return service;
    }

    /**
     * 查询培训次数
     * @param traineeId
     * @return
     */
    @RequestMapping("/countRecord")
    public ApiResponse<Integer> queryCountRecord(@RequestParam("traineeId") String traineeId){
        return ApiResponse.success(trainingRecordMapper.countTrainRecord(traineeId));
    }

    /**
     * 查询培训记录，按时间倒序
     * @param trainingRecord
     * @return
     */
    @GetMapping("/getRecordList")
    public ApiResponse<List<TrainingRecord>> queryList(TrainingRecord trainingRecord){
        return service.getList(trainingRecord);
    }

    /**
     * 查询每天的培训记录 按时间倒序
     * @param trainingRecord
     * @return
     */
    @GetMapping("/queryRecordList")
    public ApiResponse<Map> queryRecordList(@RequestParam("traineeId") String traineeId,@RequestParam(value = "sub",required = false) String sub){
        return service.queryRecordByDay(traineeId,sub);
    }

    /**
     * 学员确认培训
     * @param trainingRecord
     * @return
     */
    @PostMapping("/upDate")
    public ApiResponse<String> saveEntity(TrainingRecord trainingRecord){
        return service.saveEntity(trainingRecord);
    }

}