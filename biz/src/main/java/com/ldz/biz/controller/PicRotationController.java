package com.ldz.biz.controller;
import com.ldz.biz.model.PicRotation;
import com.ldz.biz.service.PicRotationService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户端 图片轮播表
 */
@RestController
@RequestMapping("/api/picrotation")
public class PicRotationController extends BaseController<PicRotation,String> {
    @Autowired
    private PicRotationService service;

    @Override
    protected BaseService<PicRotation, String> getBaseService() {
        return service;
    }


}