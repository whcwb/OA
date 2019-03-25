package com.ldz.biz.service;

import com.ldz.biz.model.BizLcJlXy;
import com.ldz.sys.base.BaseService;

import java.util.List;

public interface BizLcJlXyService extends BaseService<BizLcJlXy, String> {
    void saveBatch(List<BizLcJlXy> list);
}