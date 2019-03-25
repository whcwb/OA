package com.ldz.biz.service.impl;

import com.ldz.biz.mapper.YyCwjlMapper;
import com.ldz.biz.model.YyCwjl;
import com.ldz.biz.service.YyCwjlService;
import com.ldz.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class YyCwjlServiceImpl extends BaseServiceImpl<YyCwjl,String> implements YyCwjlService {
    @Autowired
    private YyCwjlMapper baseMapper;

    @Override
    protected Mapper<YyCwjl> getBaseMapper() {
        return baseMapper;
    }

    @Override
    public void updateZt(String trainId) {
        baseMapper.updateZt(trainId);
    }
}
