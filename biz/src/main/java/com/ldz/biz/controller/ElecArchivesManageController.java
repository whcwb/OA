package com.ldz.biz.controller;
import com.ldz.biz.model.ElecArchivesManage;
import com.ldz.biz.service.ElecArchivesManageService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 电子档案管理
 */
@RestController
@RequestMapping("/api/elecarchivesmanage")
public class ElecArchivesManageController extends BaseController<ElecArchivesManage,String> {
    @Autowired
    private ElecArchivesManageService service;

    @Override
    protected BaseService<ElecArchivesManage, String> getBaseService() {
        return service;
    }
}