package com.ldz.wechat.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.CoachManagement;
import com.ldz.wechat.model.CoachTraineeRercord;
import com.ldz.wechat.model.TraineeInformation;
import com.ldz.wechat.service.TraineeInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 学生基本信息
 */
@RestController
@RequestMapping("/api/traineeinformation")
public class TraineeInformationController extends BaseController<TraineeInformation,String> {
    @Autowired
    private TraineeInformationService service;



    @Override
    protected BaseService<TraineeInformation, String> getBaseService() {
        return service;
    }

    /**
     * 查询教练下的所有学员信息
     * @param id
     * @param status
     * @return
     */
    @RequestMapping(value = "/traineeList",method={RequestMethod.POST})
    public ApiResponse<PageInfo<Map>> TraineeList(@RequestParam(value = "status" ,required = false) String status ,
                                                  @RequestParam(value = "carType" ,required = false) String carType,
                                                  CoachTraineeRercord coachTraineeRercord,
                                                  Page<Map> page){
        //这里处理前台传入 的空字符串问题
        if("".equals(status)){
            status = null;
        }
        if("".equals(carType)){
            carType = null;
        }
        return service.getTraineeList(coachTraineeRercord,status,carType,page);
    }

    @RequestMapping("/myCoach")
    public ApiResponse<CoachManagement> getMyCoachInfo(@RequestParam("traineeId") String traineeId,@RequestParam("allotSub") String allotSub){
        return service.queryMyCoach(traineeId,allotSub);

    }

    /**
     * 查询分配时间最长的未结业的学员前十条
     * @param traineeId
     * @return
     */
    @RequestMapping("/top10")
    public ApiResponse<List<Map>> top10Trainee(@RequestParam("coachId") String coachId){
        return service.queryTop10Trainee(coachId);
    }


    @RequestMapping("/queryPager")
    public ApiResponse<List<TraineeInformation>> queryPager(String startTime ,String endTime ,Page<TraineeInformation> pager,TraineeInformation info){
        return service.queryPager(startTime,endTime,pager,info);
    }

    /**
     * 基本信息修改
     * @param entity
     * @return
     */
    @RequestMapping("/updateinfo2")
    public ApiResponse<String> cheakInfo(TraineeInformation entity){
        return service.updateEntity(entity);
    }

    /**
     * 用户基础资料审核
     * @param entity  id  PKID
     * @param entity  infoCheckStatus  基本信息审核状态  00: 未审核 10：审核通过 20：审核未通过
     * @return
     */
    @RequestMapping(value="/userInfoCheck", method={RequestMethod.POST})
    public ApiResponse<String> userInfoCheck(TraineeInformation entity){
        return service.userInfoCheck(entity);
    }

    /**
     *报名审核回退
     */
    @PostMapping("/revokeInfo")
    public ApiResponse<String> revokeInfo(String traineeId){
        return service.revokeInfo(traineeId);
    }

}