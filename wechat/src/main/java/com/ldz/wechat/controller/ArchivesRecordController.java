package com.ldz.wechat.controller;


import com.ldz.sys.base.BaseService;
import com.ldz.wechat.base.WxBaseController;
import com.ldz.wechat.model.ArchivesRecord;
import com.ldz.wechat.service.ArchivesRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 档案记录
 */
@RestController
@RequestMapping("/api/archivesrecord")
public class ArchivesRecordController extends WxBaseController<ArchivesRecord,String> {
    @Autowired
    private ArchivesRecordService service;

    @Override
    protected BaseService<ArchivesRecord, String> getBaseService() {
        return service;
    }

}