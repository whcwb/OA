package com.ldz.biz.controller;

import com.ldz.biz.model.BizKcLb;
import com.ldz.biz.service.BizKcLbService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/kclb")
public class BizKcLbController extends BaseController<BizKcLb, String> {
    @Autowired
    private BizKcLbService service;

    @Override
    protected BaseService<BizKcLb, String> getBaseService() {
        return service;
    }

    @PostMapping("/save")
    public ApiResponse<String> save(BizKcLb entity){
        return service.saveEntity(entity);
    }

    @PostMapping("/getAllLb")
    public ApiResponse<List<BizKcLb>> getAllLb(){
        return service.getAllLb();
    }


}