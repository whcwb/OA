package com.ldz.biz.controller;


import com.github.pagehelper.Page;
import com.ldz.biz.model.BizLcCl;
import com.ldz.biz.service.BizLcClService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/api/lccl")
public class BizLcClController extends BaseController<BizLcCl, String> {
    @Autowired
    private BizLcClService service;

    @Override
    protected BaseService<BizLcCl, String> getBaseService() {
        return service;
    }

    @PostMapping("/save")
    public ApiResponse<String> save(BizLcCl entity){
        return service.saveEntity(entity);
    }

    @PostMapping("/getCar")
    public ApiResponse<String> getCar(Page<BizLcCl> page) throws ParseException {
        return service.getCar(page);
    }

    @PostMapping("/update")
    public ApiResponse<String> update(BizLcCl entity){
        return service.updateEntity(entity);
    }

    @PostMapping("/updateCardNo")
    public ApiResponse<String> updateCardNo(String id, String cardNo, @RequestParam(required = false) String th){
        return service.updateCardNo(id, cardNo, th);
    }

    @PostMapping("/carInfo")
    public ApiResponse<BizLcCl> getCarInfo(String cardNo, String km){
        return service.getCarInfo(cardNo, km);
    }

}