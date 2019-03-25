package com.ldz.biz.controller;


import com.ldz.biz.model.Zgjbxx;
import com.ldz.biz.service.ZgjbxxService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/zgjbxx")
public class ZgJbxxController extends BaseController<Zgjbxx,String> {

    @Autowired
    private ZgjbxxService service;

    @Override
    protected BaseService<Zgjbxx, String> getBaseService() {
        return service;
    }

    @PostMapping("/save")
    public ApiResponse<String> save(Zgjbxx entity){
        return service.saveEntity(entity);
    }

    @PostMapping("/update")
    public ApiResponse<String> update(Zgjbxx entity){
        return service.updateEntity(entity);
    }

    @PostMapping("/getAqy")
    public ApiResponse<List<Zgjbxx>> getAqy(){
        return service.getAqy();
    }

    /**
     * 安全员签到
     * id   员工ID
     * aqyQdzt  签到状态 (字典[aqyzt] 00休息 10上班)
     * @return
     */
    @PostMapping("/setaqrqd")
    public ApiResponse<String> setSecurityOfficerSignin(Zgjbxx entity){
        return service.setSecurityOfficerSignin(entity);
    }


}
