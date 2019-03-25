package com.ldz.biz.service.impl;

import com.ldz.biz.mapper.BizLcJlXyMapper;
import com.ldz.biz.model.BizLcJlXy;
import com.ldz.biz.service.BizLcJlXyService;
import com.ldz.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Service
public class BizLcJlXyServiceImpl extends BaseServiceImpl<BizLcJlXy, String> implements BizLcJlXyService {

	@Autowired
	private BizLcJlXyMapper baseMapper;
	
	@Override
	protected Mapper<BizLcJlXy> getBaseMapper() {
		return baseMapper;
	}

    @Override
    public void saveBatch(List<BizLcJlXy> list) {
        baseMapper.insertList(list);
    }
}