package com.ldz.biz.controller;

import com.github.pagehelper.Page;
import com.ldz.biz.model.BizRk;
import com.ldz.biz.service.BizRkService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bizrk")
public class BizRkController extends BaseController<BizRk, String> {
    @Autowired
    private BizRkService service;

    @Override
    protected BaseService<BizRk, String> getBaseService() {
        return service;
    }

    @PostMapping("/getPc")
    public ApiResponse<String> getPc(Page<BizRk> page){
        return service.getPc(page);
    }

}