package com.ldz.biz.controller;

import com.ldz.biz.model.BizCk;
import com.ldz.biz.service.BizCkService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bizck")
public class BizCkController extends BaseController<BizCk, String> {
    @Autowired
    private BizCkService service;

    @Override
    protected BaseService<BizCk, String> getBaseService() {
        return service;
    }
}