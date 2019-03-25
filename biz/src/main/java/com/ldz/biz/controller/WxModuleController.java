package com.ldz.biz.controller;


import com.ldz.biz.model.WxModule;
import com.ldz.biz.service.WxModuleService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wxmodule")
public class WxModuleController extends BaseController<WxModule,String> {

    @Autowired
    private WxModuleService service;

    @Override
    protected BaseService<WxModule, String> getBaseService() {
        return service;
    }



}
