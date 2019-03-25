package com.ldz.wechat.controller;


import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.wechat.model.ChargeInstitutionManage;
import com.ldz.wechat.service.ChargeInstitutionManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 收费机构管理
 */
@RestController
@RequestMapping("/api/chargeinstitutionmanage")
public class ChargeInstitutionManageController extends BaseController<ChargeInstitutionManage,String> {
    @Autowired
    private ChargeInstitutionManageService service;

    @Override
    protected BaseService<ChargeInstitutionManage, String> getBaseService() {
        return service;
    }


}