package com.ldz.biz.service;

import com.ldz.biz.model.ElecArchivesManage;
import com.ldz.sys.base.BaseService;

import java.util.List;


public interface ElecArchivesManageService extends BaseService<ElecArchivesManage, String> {
    int insertList(List<ElecArchivesManage> list);
}