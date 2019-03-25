package com.ldz.wechat.controller;

import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.wechat.model.ChargeManagement;
import com.ldz.wechat.service.ChargeManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 收费记录管理
 */
@RestController
@RequestMapping("/api/chargemanagement")
public class ChargeManagementController extends BaseController<ChargeManagement,String> {
    @Autowired
    private ChargeManagementService service;

    @Override
    protected BaseService<ChargeManagement, String> getBaseService() {
        return service;
    }


}