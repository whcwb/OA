package com.ldz.biz.service.impl;

import com.github.pagehelper.PageInfo;
import com.ldz.biz.mapper.BizLcFdMapper;
import com.ldz.biz.model.BizLcFd;
import com.ldz.biz.model.BizLcJl;
import com.ldz.biz.service.BizLcFdService;
import com.ldz.biz.service.BizLcJlService;
import com.ldz.sys.base.BaseServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Service
public class BizLcFdServiceImpl extends BaseServiceImpl<BizLcFd, String> implements BizLcFdService {

	@Autowired
	private BizLcFdMapper baseMapper;
	@Autowired
	private BizLcJlService jlService;
	
	@Override
	protected Mapper<BizLcFd> getBaseMapper() {
		return baseMapper;
	}

	@Override
	public  void afterPager(PageInfo<BizLcFd> pageInfo){
		if(CollectionUtils.isEmpty(pageInfo.getList())){
			return;
		}
		List<BizLcFd> lcFds = pageInfo.getList();
		lcFds.stream().forEach(bizLcFd -> {
			List<BizLcJl> jlList = jlService.findEq(BizLcJl.InnerColumn.fdId, bizLcFd.getId());
			bizLcFd.setJlList(jlList);
		});
	}

	@Override
	public void afterQuery(List<BizLcFd> result){
		if(CollectionUtils.isEmpty(result)){
			return;
		}

		result.stream().forEach(bizLcFd -> {
			List<BizLcJl> jlList = jlService.findEq(BizLcJl.InnerColumn.fdId, bizLcFd.getId());
			bizLcFd.setJlList(jlList);
		});
	}

}