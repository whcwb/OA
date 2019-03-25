package com.ldz.biz.controller;



import com.ldz.biz.model.ChargeInstitutionManage;
import com.ldz.biz.service.ChargeInstitutionManageService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 收费机构管理
 */
@RestController
@RequestMapping("/api/chargeinstitutionmanage")
public class ChargeInstitutionManageController extends BaseController<ChargeInstitutionManage,String> {
    @Autowired
    private ChargeInstitutionManageService service;

    @Override
    protected BaseService<ChargeInstitutionManage, String> getBaseService() {
        return service;
    }

    @Override
    @PostMapping("/save")
    public ApiResponse<String> save(ChargeInstitutionManage entity){
        return service.validAndSave(entity);
    }

    @PostMapping("/update")
    public ApiResponse<String> update(ChargeInstitutionManage entity){
        return service.updateEntity(entity);
    }
}