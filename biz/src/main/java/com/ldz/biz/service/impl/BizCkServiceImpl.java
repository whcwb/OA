package com.ldz.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ldz.biz.mapper.BizCkMapper;
import com.ldz.biz.model.BizCk;
import com.ldz.biz.model.BizKc;
import com.ldz.biz.service.BizCkService;
import com.ldz.biz.service.BizKcService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BizCkServiceImpl extends BaseServiceImpl<BizCk, String> implements BizCkService {

	@Autowired
	private BizCkMapper baseMapper;
	@Autowired
	private BizKcService kcService;
	
	@Override
	protected Mapper<BizCk> getBaseMapper() {
		return baseMapper;
	}
	@Override
	public boolean fillPagerCondition(LimitedCondition condition){

		return true;
	}

	@Override
	public void afterPager(PageInfo<BizCk> result){
		if (CollectionUtils.isEmpty(result.getList())) {
			return;
		}

	}


	@Override
	public ApiResponse<String> getPager(String kcMc, String kcLx, String lqr, Integer pageNum, Integer pageSize) {
		SimpleCondition condition = new SimpleCondition(BizCk.class);
		SimpleCondition kcCondition = new SimpleCondition(BizKc.class);
		condition.setOrderByClause(" cjsj desc ");
		if (StringUtils.isNotBlank(kcMc)) {
			kcCondition.like(BizKc.InnerColumn.kcMc, kcMc);
		}
		if (StringUtils.isNotBlank(kcLx)) {
			kcCondition.like(BizKc.InnerColumn.kcLx, kcLx);
		}
		if (StringUtils.isNotBlank(lqr)) {
			condition.like(BizCk.InnerColumn.lqr, lqr);
		}
		if (StringUtils.isNotBlank(kcLx) || StringUtils.isNotBlank(kcMc)) {
			List<BizKc> kcs = kcService.findByCondition(kcCondition);
			if (CollectionUtils.isNotEmpty(kcs)) {
				List<String> kcIds = kcs.stream().map(BizKc::getId).collect(Collectors.toList());
				if(CollectionUtils.isEmpty(kcIds)){
					return ApiResponse.success();
				}
				condition.in(BizCk.InnerColumn.kcId, kcIds);
			}else{
				return ApiResponse.success();
			}
		}

		PageInfo<BizCk> info = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> {
			findByCondition(condition);
		});
		if (CollectionUtils.isNotEmpty(info.getList())){
			List<BizCk> cks = info.getList();
			List<String> kcIds = cks.stream().map(BizCk::getKcId).collect(Collectors.toList());
			List<BizKc> kcs = kcService.findByIds(kcIds);
			if (CollectionUtils.isNotEmpty(kcs)) {
				Map<String, List<BizKc>> collect = kcs.stream().collect(Collectors.groupingBy(BizKc::getId));
				cks.forEach(bizCk -> {
					if (collect.containsKey(bizCk.getKcId())) {
						bizCk.setKc(collect.get(bizCk.getKcId()).get(0));
					}
				});
			}
		}
		ApiResponse<String> api = new ApiResponse<>();
		api.setPage(info);

		return api;
	}
}