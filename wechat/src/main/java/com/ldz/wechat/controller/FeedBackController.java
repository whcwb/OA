package com.ldz.wechat.controller;

import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.FeedBack;
import com.ldz.wechat.service.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/feedBack")
public class FeedBackController extends BaseController<FeedBack,String>{
    @Autowired
    private FeedBackService service;
    @Override
    protected BaseService<FeedBack, String> getBaseService() {
        return service;
    }

    @PostMapping("/saveEntity")
    public ApiResponse<String> saveEntity(FeedBack feedBack){
        return service.saveEntity(feedBack);
    }
}
