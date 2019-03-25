package com.ldz.wechat.controller;

import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.PicRotation;
import com.ldz.wechat.service.PicRotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 轮播图
 */
@RestController
@RequestMapping("/api/picRotation")
public class PicRotationController extends BaseController<PicRotation,String> {
    @Autowired
    private PicRotationService service;
    @Override
    protected BaseService<PicRotation, String> getBaseService() {
        return service;
    }
    /**
     * 查询轮播图
     */
    @RequestMapping("/queryPic")
    public ApiResponse<List<PicRotation>> queryPic(PicRotation picRotation){
        return service.queryPic(picRotation);
    }
}
