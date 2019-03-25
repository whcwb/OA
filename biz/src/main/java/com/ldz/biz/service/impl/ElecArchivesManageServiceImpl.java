package com.ldz.biz.service.impl;

import com.ldz.biz.mapper.ElecArchivesManageMapper;
import com.ldz.biz.model.ElecArchivesManage;
import com.ldz.biz.service.ElecArchivesManageService;
import com.ldz.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Service
public class ElecArchivesManageServiceImpl extends BaseServiceImpl<ElecArchivesManage, String> implements ElecArchivesManageService {

	@Autowired
	private ElecArchivesManageMapper baseMapper;
	
	@Override
	protected Mapper<ElecArchivesManage> getBaseMapper() {
		return baseMapper;
	}

	@Override
	public int insertList(List<ElecArchivesManage> list){
		return baseMapper.insertList(list);
	}

}