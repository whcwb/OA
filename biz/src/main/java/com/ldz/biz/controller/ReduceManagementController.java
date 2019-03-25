package com.ldz.biz.controller;

import com.ldz.biz.model.ReduceManagement;
import com.ldz.biz.model.ValueLabelModel;
import com.ldz.biz.service.ReduceManagementService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 优惠项管理
 */
@RestController
@RequestMapping("/api/reducemanagement")
public class ReduceManagementController extends BaseController<ReduceManagement,String> {
    @Autowired
    private ReduceManagementService service;

    @Override
    protected BaseService<ReduceManagement, String> getBaseService() {
        return service;
    }

    /**
     * 新增优惠项
     */
    @Override
    @PostMapping("/save")
    public ApiResponse<String> save(ReduceManagement entity){
        return service.validAndSave(entity);
    }

    /**
     * 新增团购优惠
     */
    @PostMapping("/saveGroup")
    public ApiResponse<String> saveGroup(ReduceManagement entity){
        return service.saveGroup(entity);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public ApiResponse<String> update(ReduceManagement entity){
        return service.updateEntity(entity);
    }

    @RequestMapping(value="/remove/{pkid}", method={RequestMethod.POST})
    public ApiResponse<String> remove(@PathVariable("pkid")String id){
        return service.removeEntity(id);
    }

    /**
     * 查询用户的职务及姓名结构
     */
    @GetMapping("/getValueLabel")
    public ApiResponse<List<ValueLabelModel>> findValueLabel(){
        return service.findValueLabel();
    }
}