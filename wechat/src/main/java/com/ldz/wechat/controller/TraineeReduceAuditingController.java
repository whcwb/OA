package com.ldz.wechat.controller;

import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.TraineeInformation;
import com.ldz.wechat.service.TraineeReduceAuditingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 学生优惠审核信息
 */
@RestController
@RequestMapping("/api/traineereduceauditing")
public class TraineeReduceAuditingController extends BaseController<TraineeInformation,String> {
    @Autowired
    private TraineeReduceAuditingService service;


    @Override
    protected BaseService<TraineeInformation, String> getBaseService() {
        return service;
    }


    /**
     * 用户审核操作
     *
     * @param entity  id            学员主键
     * @param entity  reduceStatus  优惠信息审核状态  00:未审核 10：审核通过 20：审核未通过
     * @param entity  reduceRemark 优惠备注
     * @return
     */
    @RequestMapping(value="/userAuditing2")
    public ApiResponse<String> setUserReduceAuditing(TraineeInformation entity){
        return service.setUserReduceAuditing(entity);
    }

    /**
     * 撤销学员审核操作
     *
     * @param entity  id            学员主键
     * @return
     */
    @RequestMapping(value="/recallInfo")
    public ApiResponse<String> recallUserAuditing(TraineeInformation entity){
        return service.recallUserAuditing(entity);
    }

}