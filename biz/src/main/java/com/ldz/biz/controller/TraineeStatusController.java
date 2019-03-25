package com.ldz.biz.controller;
import com.ldz.biz.model.TraineeStatus;
import com.ldz.biz.service.TraineeStatusService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}