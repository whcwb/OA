package com.ldz.biz.service.impl;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ldz.biz.mapper.BizRkMapper;
import com.ldz.biz.model.BizKc;
import com.ldz.biz.model.BizRk;
import com.ldz.biz.service.BizKcService;
import com.ldz.biz.service.BizRkService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BizRkServiceImpl extends BaseServiceImpl<BizRk, String> implements BizRkService {

	@Autowired
	private BizRkMapper baseMapper;
	@Autowired
	private BizKcService kcService;
	
	@Override
	protected Mapper<BizRk> getBaseMapper() {
		return baseMapper;
	}


	@Override
	public void afterPager(PageInfo<BizRk> pageInfo){
		if(CollectionUtils.isNotEmpty(pageInfo.getList())){
			return;
		}
		List<BizRk> rks = pageInfo.getList();

		rks.forEach(bizRk -> {
			String kcId = bizRk.getKcId();
			BizKc bizKc = kcService.findById(kcId);
			bizRk.setBizKc(bizKc);
		});

	}

    @Override
    public ApiResponse<String> getPc(Page<BizRk> page) {

		ApiResponse<String> result = new ApiResponse<>();
		PageInfo<String> resultPage = PageHelper.startPage(page.getPageNum(), page.getPageSize()).doSelectPageInfo(new ISelect() {
			@Override
			public void doSelect() {
				baseMapper.getAllPCByPage();
			}
		});
		List<BizRk> list = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(resultPage.getList())){
			resultPage.getList().forEach(pc -> {
				BizRk bizRk = new BizRk();
				bizRk.setPc(pc);
				SimpleCondition simpleCondition = new SimpleCondition(BizRk.class);
				simpleCondition.eq(BizRk.InnerColumn.pc,pc);
				simpleCondition.setOrderByClause(" cjsj desc");
				List<BizRk> rks = findByCondition(simpleCondition);
				if(CollectionUtils.isNotEmpty(rks)){
					SimpleCondition condition = new SimpleCondition(BizKc.class);
					condition.in(BizKc.InnerColumn.id,rks.stream().map(BizRk::getKcId).collect(Collectors.toList()));
					List<BizKc> kcs = kcService.findByCondition(condition);
					Map<String, BizKc> collect = kcs.stream().collect(Collectors.toMap(BizKc::getId, bizKc -> bizKc));
					double total = rks.stream().mapToDouble(rk-> rk.getRkSl() * rk.getRkDj()).sum();
					rks.stream().forEach(bizRk1 -> bizRk1.setBizKc(collect.get(bizRk1.getKcId())));
					bizRk.setTotal(total);
				}
				bizRk.setBizRkList(rks);
				list.add(bizRk);
			});
		}
		PageInfo<BizRk> pageInfo = new PageInfo<>();
		BeanUtils.copyProperties(resultPage,pageInfo,"list");
		pageInfo.setList(list);
		result.setPage(pageInfo);
		return result;
    }
}