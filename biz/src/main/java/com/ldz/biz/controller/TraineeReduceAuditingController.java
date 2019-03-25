package com.ldz.biz.controller;

import com.github.pagehelper.Page;
import com.ldz.biz.model.TraineeInformation;
import com.ldz.biz.service.TraineeReduceAuditingService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

//默认分页  pager
    /**
     * 用户审核操作
     *
     * @param entity  id            学员主键
     * @param entity  reduceStatus  优惠信息审核状态  00:未审核 10：审核通过 20：审核未通过
     * @param entity  reduceRemark 优惠备注
     * @return
     */
    @RequestMapping(value="/userAuditing", method={RequestMethod.POST})
    public ApiResponse<String> setUserReduceAuditing(TraineeInformation entity){
        return service.setUserReduceAuditing(entity);
    }
    /**
     * 撤销学员审核操作
     *
     * @param entity  id            学员主键
     * @return
     */
    @RequestMapping(value="/recallUserAuditing", method={RequestMethod.POST})
    public ApiResponse<String> recallUserAuditing(TraineeInformation entity){
        return service.recallUserAuditing(entity);
    }

    /**
     * 获取可以修改优惠金额的学员列表
     * @param page
     * @return
     */
    @PostMapping("/getToEditReducePrice")
    public ApiResponse<List<TraineeInformation>> getToEditReducePrice(Page<TraineeInformation> page){
        return service.getToEditReducePrice(page);
    }

    /**
     * 修改学员的优惠金额
     * @param id
     * @param price
     * @return
     */
    @PostMapping("/editReducePrice")
    public ApiResponse<String> editReducePrice(String id,int price){
        return service.editReducePrice(id,price);
    }

    /**
     * 所有有优惠的学员信息
     */

    public ApiResponse<List<TraineeInformation>> getAllReduceTrain(Page<TraineeInformation> page){

        return service.getAllReduceTrain(page);
    }


}