package com.ldz.biz.controller;
import com.ldz.biz.model.TrainingRecord;
import com.ldz.biz.service.TrainingRecordService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 培训记录
 */
@RestController
@RequestMapping("/api/trainingrecord")
public class TrainingRecordController extends BaseController<TrainingRecord,String> {
    @Autowired
    private TrainingRecordService service;

    @Override
    protected BaseService<TrainingRecord, String> getBaseService() {
        return service;
    }
}