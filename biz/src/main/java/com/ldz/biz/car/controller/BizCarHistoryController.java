package com.ldz.biz.car.controller;


import com.ldz.biz.car.model.BizCarHistory;
import com.ldz.biz.car.service.BizCarHistoryService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 车辆历史表
 */
@RestController
@RequestMapping("/api/carls")
public class BizCarHistoryController extends BaseController<BizCarHistory,String> {
    @Autowired
    private BizCarHistoryService service;

    @Override
    protected BaseService<BizCarHistory, String> getBaseService() {
        return service;
    }

}