package com.ldz.biz.controller;


import com.ldz.biz.model.WxModuleSys;
import com.ldz.biz.service.WxModuleSysService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/wxmodulesys")
public class WxModuleSysController extends BaseController<WxModuleSys,String> {

    @Autowired
    private WxModuleSysService service;

    @Override
    protected BaseService<WxModuleSys, String> getBaseService() {
        return service;
    }

    @RequestMapping(value="/insertList", method={RequestMethod.POST})
    public ApiResponse<String> insertList(WxModuleSys entity){
        return service.insertList(entity);
    }

    @RequestMapping(value="/getUserModuleList", method={RequestMethod.POST})
    public ApiResponse<List<WxModuleSys>> getUserModuleList(String yhId){
        return service.getUserModuleList(yhId);
    }

}
