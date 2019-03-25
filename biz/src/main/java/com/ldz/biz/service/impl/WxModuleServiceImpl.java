package com.ldz.biz.service.impl;

import com.ldz.biz.mapper.WxModuleMapper;
import com.ldz.biz.model.WxModule;
import com.ldz.biz.service.WxModuleService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.model.SysYh;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.commonUtil.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class WxModuleServiceImpl extends BaseServiceImpl<WxModule, String> implements WxModuleService {

	@Autowired
	private WxModuleMapper baseMapper;
	
	@Override
	protected Mapper<WxModule> getBaseMapper() {
		return baseMapper;
	}

	@Override
	public ApiResponse<String> validAndSave(WxModule entity) {
		entity.setId(genId());
		SysYh user = getCurrentUser();
		entity.setCjsj(DateUtils.getNowTime());
		entity.setCjr(user.getYhid());
		baseMapper.insertSelective(entity);
		return ApiResponse.success();
	}
}