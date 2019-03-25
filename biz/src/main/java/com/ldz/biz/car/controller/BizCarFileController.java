package com.ldz.biz.car.controller;


import com.ldz.biz.car.model.BizCarFile;
import com.ldz.biz.car.service.BizCarFileService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 车辆电子档案表
 */
@RestController
@RequestMapping("/api/cardzd")
public class BizCarFileController extends BaseController<BizCarFile,String> {
    @Autowired
    private BizCarFileService service;

    @Override
    protected BaseService<BizCarFile, String> getBaseService() {
        return service;
    }

//    /**
//     * 电子档案新增加
//     * @param entity
//     * @return
//     */
//    @Override
//    @RequestMapping(value="/save", method={RequestMethod.POST})
//    public ApiResponse<String> save(BizCarFile entity){
//        return service.validAndSaveERecord(entity);
//    }
}