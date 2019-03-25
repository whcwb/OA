package com.ldz.biz.controller;
import com.ldz.biz.model.DropoutRecord;
import com.ldz.biz.service.DropoutRecordService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 退学记录
 */
@RestController
@RequestMapping("/api/dropoutrecord")
public class DropoutRecordController extends BaseController<DropoutRecord,String> {
    @Autowired
    private DropoutRecordService service;

    @Override
    protected BaseService<DropoutRecord, String> getBaseService() {
        return service;
    }
}