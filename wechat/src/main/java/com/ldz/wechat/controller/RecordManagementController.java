package com.ldz.wechat.controller;

import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.wechat.model.RecordManagement;
import com.ldz.wechat.service.RecordManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 档案柜管理  新增的时候，需要做排查
 */
@RestController
@RequestMapping("/api/recordmanagement")
public class RecordManagementController extends BaseController<RecordManagement,String> {
    @Autowired
    private RecordManagementService service;

    @Override
    protected BaseService<RecordManagement, String> getBaseService() {
        return service;
    }
}