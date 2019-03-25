package com.ldz.biz.service.impl;

import com.ldz.biz.mapper.WxModuleSysMapper;
import com.ldz.biz.model.WxModuleSys;
import com.ldz.biz.service.WxModuleSysService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.model.SysYh;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class WxModuleSysServiceImpl extends BaseServiceImpl<WxModuleSys, String> implements WxModuleSysService {

	@Autowired
	private WxModuleSysMapper baseMapper;
	
	@Override
	protected Mapper<WxModuleSys> getBaseMapper() {
		return baseMapper;
	}

	@Override
	public ApiResponse<List<WxModuleSys>> getUserModuleList(String userId){
		SimpleCondition simpleCondition = new SimpleCondition(WxModuleSys.class);
		simpleCondition.eq(WxModuleSys.InnerColumn.yhId,userId);
		simpleCondition.setOrderByClause(WxModuleSys.InnerColumn.id.asc());
		List<WxModuleSys> list=this.findByCondition(simpleCondition);
		return ApiResponse.success(list);
	}

	@Override
	public  ApiResponse<String> insertList(WxModuleSys entity){
		String moduleIds=entity.getModuleIds();

		WxModuleSys delObd=new WxModuleSys();
		delObd.setYhId(entity.getYhId());
		baseMapper.delete(delObd);

		if(StringUtils.isNotEmpty(moduleIds)){
			List<String> moduleIdList = new ArrayList<>();
			if (StringUtils.isNotBlank(moduleIds)){
				moduleIdList = Arrays.asList(moduleIds.split(","));
			}
			List<WxModuleSys> list = new ArrayList<>();
			for(String module:moduleIdList){
				WxModuleSys obd=new WxModuleSys();
				obd.setId(genId());
				obd.setModuleId(module);
				obd.setYhId(entity.getYhId());
				SysYh user = getCurrentUser();
				obd.setCjsj(DateUtils.getNowTime());
				obd.setCjr(user.getYhid());
				list.add(obd);
			}

			if(list!=null&&list.size()>0){
				baseMapper.insertList(list);
			}
		}
		return ApiResponse.success();
	}
}