package com.ldz.wechat.controller;

import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.wechat.model.ChargeItemManagement;
import com.ldz.wechat.service.ChargeItemManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 收费项管理
 */
@RestController
@RequestMapping("/api/chargeitemmanagement")
public class ChargeItemManagementController extends BaseController<ChargeItemManagement,String> {
    @Autowired
    private ChargeItemManagementService service;

    @Override
    protected BaseService<ChargeItemManagement,String> getBaseService() {
        return service;
    }



}