package com.ldz.biz.car.service.impl;

import com.ldz.biz.car.mapper.BizCarFileMapper;
import com.ldz.biz.car.model.BizCarFile;
import com.ldz.biz.car.service.BizCarFileService;
import com.ldz.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class BizCarFileServiceImpl extends BaseServiceImpl<BizCarFile, java.lang.String> implements BizCarFileService {

	@Autowired
	private BizCarFileMapper baseMapper;
	
	@Override
	protected Mapper<BizCarFile> getBaseMapper() {
		return baseMapper;
	}
}