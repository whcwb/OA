package com.ldz.biz.controller;

import com.ldz.biz.model.BizLcFd;
import com.ldz.biz.service.BizLcFdService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bizlcfd")
public class BizLcFdController extends BaseController<BizLcFd, String> {
    @Autowired
    private BizLcFdService service;

    @Override
    protected BaseService<BizLcFd, String> getBaseService() {
        return service;
    }
}