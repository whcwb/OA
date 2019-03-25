package com.ldz.biz.service.impl;

import com.ldz.biz.mapper.ZgAqyQdLogMapper;
import com.ldz.biz.model.ZgAqyQdLog;
import com.ldz.biz.service.ZgAqyQdLogService;
import com.ldz.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class ZgAqyQdLogServiceImpl extends BaseServiceImpl<ZgAqyQdLog,String> implements ZgAqyQdLogService {
    @Autowired
    private ZgAqyQdLogMapper baseMapper;


    @Override
    protected Mapper<ZgAqyQdLog> getBaseMapper() {
        return baseMapper;
    }

}
