package com.ldz.wechat.controller;

import com.github.pagehelper.Page;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.RateDetail;
import com.ldz.wechat.model.TraineeInformation;
import com.ldz.wechat.service.RateDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by wangwei on 2018/9/18.
 *
 * 详细评价
 */
@RestController
@RequestMapping("/api/ratedetail")
public class RateDetailController extends BaseController<RateDetail,String> {
    @Autowired
    private RateDetailService service;

    @Override
    protected BaseService<RateDetail, String> getBaseService() {
        return service;
    }


    @PostMapping("/saveEntity")
    public ApiResponse<String> saveEntityDetail(RateDetail entity){

        return service.saveDetail(entity);
    }

    @PostMapping("/queryPager")
    public ApiResponse<List<RateDetail>> queryPager(Page<RateDetail> pager, RateDetail entity){
        return service.queryPager(pager,entity);
    }
}
