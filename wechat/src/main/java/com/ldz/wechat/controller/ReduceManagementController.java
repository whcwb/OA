package com.ldz.wechat.controller;

import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.wechat.model.ReduceManagement;
import com.ldz.wechat.service.ReduceManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 优惠项管理
 */
@RestController
@RequestMapping("/api/reducemanagement")
public class ReduceManagementController extends BaseController<ReduceManagement,String> {
    @Autowired
    private ReduceManagementService service;

    @Override
    protected BaseService<ReduceManagement, String> getBaseService() {
        return service;
    }


}