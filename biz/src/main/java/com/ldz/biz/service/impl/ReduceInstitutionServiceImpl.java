package com.ldz.biz.service.impl;

import com.ldz.biz.mapper.ReduceInstitutionMapper;
import com.ldz.biz.model.ReduceInstitution;
import com.ldz.biz.service.ReduceInstitutionService;
import com.ldz.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Service
public class ReduceInstitutionServiceImpl extends BaseServiceImpl<ReduceInstitution, String> implements ReduceInstitutionService {

	@Autowired
	private ReduceInstitutionMapper baseMapper;
	
	@Override
	protected Mapper<ReduceInstitution> getBaseMapper() {
		return baseMapper;
	}


	@Override
	public void saveBatch(List<ReduceInstitution> reduceInstitutions) {
		baseMapper.insertList(reduceInstitutions);
	}

	@Override
	public void deleteByReduceId(String reduceId){
		ReduceInstitution reduceInstitution = new ReduceInstitution();
		reduceInstitution.setReduceId(reduceId);
		baseMapper.delete(reduceInstitution);
	}

}