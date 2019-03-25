package com.ldz.biz.controller;

import com.ldz.biz.model.ChargeItemManagement;
import com.ldz.biz.model.KeyValue;
import com.ldz.biz.service.ChargeItemManagementService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 收费项管理
 */
@RestController
@RequestMapping("/api/chargeitemmanagement")
public class ChargeItemManagementController extends BaseController<ChargeItemManagement,String> {
    @Autowired
    private ChargeItemManagementService service;

    @Override
    protected BaseService<ChargeItemManagement,String> getBaseService() {
        return service;
    }

    @Override
    @RequestMapping(value="/save", method={RequestMethod.POST})
    public ApiResponse<String> save(ChargeItemManagement entity){
        return service.validAndSave(entity);
    }

    @PostMapping("/update")
    public ApiResponse<String> update(ChargeItemManagement entity){
        return service.updateEntity(entity);
    }

    @PostMapping("/remove/{pkid}")
    public ApiResponse<String> remove(@PathVariable("pkid") String pkid){
        return service.removeForId(pkid);
    }

    @PostMapping("/getChargeName")
    public ApiResponse<List<ChargeItemManagement>> getChargeName(String type){
        return service.getChargeName(type);
    }

    /**
     * 获取每个机构的车型和班型
     */
    @PostMapping("/getJgBx")
    public ApiResponse<Map<String,List<KeyValue>>> getJgBx(String jgdm){
        return service.getJgBx(jgdm);
    }

}