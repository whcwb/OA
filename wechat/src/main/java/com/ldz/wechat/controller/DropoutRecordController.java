package com.ldz.wechat.controller;

import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.DropoutRecord;
import com.ldz.wechat.service.DropoutRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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

    @Override
    @PostMapping("/save")
    public ApiResponse<String> save(DropoutRecord entity){
        return service.saveEntity(entity);
    }

}