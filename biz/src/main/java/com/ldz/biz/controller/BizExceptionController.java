package com.ldz.biz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ldz.biz.model.BizException;
import com.ldz.biz.model.BizExceptionConfig;
import com.ldz.biz.service.BizExceptionService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

@RestController
@RequestMapping("/api/exception")
public class BizExceptionController extends BaseController<BizException, String> {
    @Autowired
    private BizExceptionService service;

    @Override
    protected BaseService<BizException, String> getBaseService() {
        return service;
    }

    /**
     * 加载所有异常配置参数信息
     * @return
     */
    @GetMapping("/loadConfig")
    public ApiResponse<List<BizExceptionConfig>> loadConfig(){
        return service.getAllConfig();
    }

}