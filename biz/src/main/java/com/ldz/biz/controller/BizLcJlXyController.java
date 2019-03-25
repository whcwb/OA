package com.ldz.biz.controller;

import com.ldz.biz.model.BizLcJlXy;
import com.ldz.biz.service.BizLcJlXyService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lcjlxy")
public class BizLcJlXyController extends BaseController<BizLcJlXy, String> {
    @Autowired
    private BizLcJlXyService service;

    @Override
    protected BaseService<BizLcJlXy, String> getBaseService() {
        return service;
    }
}