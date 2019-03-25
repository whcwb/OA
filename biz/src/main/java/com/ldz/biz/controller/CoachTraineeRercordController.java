package com.ldz.biz.controller;
import com.ldz.biz.model.CoachTraineeRercord;
import com.ldz.biz.service.CoachTraineeRercordService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 教练学员分配记录
 */
@RestController
@RequestMapping("/api/coachtraineerercord")
public class CoachTraineeRercordController extends BaseController<CoachTraineeRercord,String> {
    @Autowired
    private CoachTraineeRercordService service;

    @Override
    protected BaseService<CoachTraineeRercord, String> getBaseService() {
        return service;
    }
}