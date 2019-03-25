package com.ldz.wechat.controller;

import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.wechat.model.GroupBuyRecord;
import com.ldz.wechat.service.GroupBuyRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 团购记录
 */
@RestController
@RequestMapping("/api/groupbuyrecord")
public class GroupBuyRecordController extends BaseController<GroupBuyRecord,String> {
    @Autowired
    private GroupBuyRecordService service;

    @Override
    protected BaseService<GroupBuyRecord, String> getBaseService() {
        return service;
    }
}