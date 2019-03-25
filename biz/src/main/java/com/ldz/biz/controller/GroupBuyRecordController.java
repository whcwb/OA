package com.ldz.biz.controller;
import com.ldz.biz.model.GroupBuyRecord;
import com.ldz.biz.service.GroupBuyRecordService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
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