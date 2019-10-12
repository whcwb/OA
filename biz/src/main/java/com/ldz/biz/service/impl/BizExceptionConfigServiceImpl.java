package com.ldz.biz.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldz.biz.mapper.BizExceptionConfigMapper;
import com.ldz.biz.model.BizExceptionConfig;
import com.ldz.biz.service.BizExceptionConfigService;
import com.ldz.sys.base.BaseServiceImpl;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

@Service
public class BizExceptionConfigServiceImpl extends BaseServiceImpl<BizExceptionConfig, java.lang.Long> implements BizExceptionConfigService {

	@Autowired
	private BizExceptionConfigMapper baseMapper;
	
	@Override
	protected Mapper<BizExceptionConfig> getBaseMapper() {
		return baseMapper;
	}
	
	@Override
	public List<BizExceptionConfig> findAll() {
		Example condition = new Example(BizExceptionConfig.class);
		condition.setOrderByClause(" order by code, name");
		return baseMapper.selectByExample(condition);
	}

	@Override
	public String getExpNameByCode(String code) {
		if (StringUtils.isEmpty(code)){
			return code;
		}
		
		BizExceptionConfig entity = new BizExceptionConfig();
		entity.setCode(code);
		BizExceptionConfig expConfig = baseMapper.selectOne(entity);
		if (expConfig == null){
			return code;
		}
		
		return expConfig.getBz();
	}
}