package com.ldz.biz.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.sys.model.SysYh;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.ldz.biz.service.BizKcLbService;
import com.ldz.biz.mapper.BizKcLbMapper;
import com.ldz.biz.model.BizKcLb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BizKcLbServiceImpl extends BaseServiceImpl<BizKcLb, String> implements BizKcLbService {

	@Autowired
	private BizKcLbMapper baseMapper;
	
	@Override
	protected Mapper<BizKcLb> getBaseMapper() {
		return baseMapper;
	}


	@Override
	public ApiResponse<List<BizKcLb>> getAllLb() {
		List<BizKcLb> kcLbs = new ArrayList<>();
		LimitedCondition condition = getQueryCondition();
		List<BizKcLb> lbs = findByCondition(condition);
		if(CollectionUtils.isNotEmpty(lbs)){
			Map<String, BizKcLb> collect = lbs.stream().filter(lb -> StringUtils.isBlank(lb.getPid())).collect(Collectors.toMap(BizKcLb::getId, p -> p));
			if(collect.size() > 0){
				for (String s : collect.keySet()) {
					List<BizKcLb> child = new ArrayList<>();
					lbs.stream().filter(bizKcLb -> StringUtils.equals(s,bizKcLb.getPid())).forEach(bizKcLb -> {
						child.add(bizKcLb);
					});
					BizKcLb bizKcLb = collect.get(s);
					bizKcLb.setChildren(child);
					kcLbs.add(bizKcLb);
				}

			}
		}
		return ApiResponse.success(kcLbs);
	}

	@Override
	public ApiResponse<String> saveEntity(BizKcLb entity) {
		SysYh yh = getCurrentUser();
		RuntimeCheck.ifBlank(entity.getMc().trim() , "请输入名称");
		entity.setId(genId());
		entity.setCjsj(DateUtils.getNowTime());
		entity.setCjr(yh.getZh()+ "-" + yh.getXm());
		save(entity);
		return ApiResponse.success();
	}
}