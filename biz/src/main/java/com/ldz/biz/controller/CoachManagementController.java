package com.ldz.biz.controller;

import com.ldz.biz.model.CoachManagement;
import com.ldz.biz.service.CoachManagementService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 教练员信息管理
 */
@RestController
@RequestMapping("/api/coachmanagement")
public class CoachManagementController extends BaseController<CoachManagement,String> {
    @Autowired
    private CoachManagementService service;

    @Override
    protected BaseService<CoachManagement, String> getBaseService() {
        return service;
    }

    @Override
    @PostMapping("/save")
    public ApiResponse<String> save(CoachManagement entity){
        return service.validAndSave(entity);
    }

    @PostMapping("/update")
    public ApiResponse<String> update(CoachManagement entity){
        return service.updateEntity(entity);
    }

    @PostMapping("/remove/{pkid}")
    public ApiResponse<String> remove(@PathVariable("pkid")String id){
        return service.deleteEntity(id);
    }

    /**
     * 学员分配
     * @param trainees 学员列表
     * @param km        学员要培训的科目  01:科目一 02：科目二 03：科目三 04：科目四
     * @param coach     教练ID
     * @return
     */
    @PostMapping("/traineefp")
    public ApiResponse<String> traineeAllocation(@RequestParam(name="trainees", required = false) String trainees,
                                                 @RequestParam(name="km", required = false) String km,
                                                 @RequestParam(name="coach", required = false) String coach){

        return service.traineeAllocation(trainees,km,coach);
    }


}