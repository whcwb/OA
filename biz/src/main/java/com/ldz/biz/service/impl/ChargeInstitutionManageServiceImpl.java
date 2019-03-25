package com.ldz.biz.service.impl;

import com.ldz.biz.mapper.ChargeInstitutionManageMapper;
import com.ldz.biz.model.ChargeInstitutionManage;
import com.ldz.biz.service.ChargeInstitutionManageService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.model.SysJg;
import com.ldz.sys.model.SysYh;
import com.ldz.sys.service.JgService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.exception.RuntimeCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Service
public class ChargeInstitutionManageServiceImpl extends BaseServiceImpl<ChargeInstitutionManage, String> implements ChargeInstitutionManageService {

	@Autowired
	private ChargeInstitutionManageMapper baseMapper;
	@Autowired
	private JgService jgService;
	
	@Override
	protected Mapper<ChargeInstitutionManage> getBaseMapper() {
		return baseMapper;
	}

	@Override
	public ApiResponse<String> validAndSave(ChargeInstitutionManage entity){
		SysYh currentUser = getCurrentUser();
		// RuntimeCheck.ifBlank(entity.getChargeCode(), "收费项编码不能为空");
		RuntimeCheck.ifBlank(entity.getJgdm(), "机构代码不能为空");
		SysJg sysJg = jgService.findById(entity.getJgdm());
		if(ObjectUtils.isEmpty(sysJg)){
			return ApiResponse.fail("机构不存在");
		}
		entity.setJgmc(sysJg.getJgmc());
		entity.setCjr(currentUser.getZh()+"-"+currentUser.getXm());
		entity.setCjsj(DateUtils.getNowTime());
		entity.setId(genId());
		save(entity);
		return ApiResponse.saveSuccess();
	}

	@Override
	public ApiResponse<String> updateEntity(ChargeInstitutionManage entity) {
		SysYh currentUser = getCurrentUser();
		ChargeInstitutionManage byId = findById(entity.getId());
		RuntimeCheck.ifNull(byId, "未找到记录");
		entity.setOperater(currentUser.getZh()+"-"+currentUser.getXm());
		entity.setOpetateTime(DateUtils.getNowTime());
		update(entity);
		return ApiResponse.success();
	}

	@Override
	public void saveBatch(List<ChargeInstitutionManage> list) {
		baseMapper.insertList(list);
	}

	@Override
	public void deleteByChargeCode(String chargeCode){

		baseMapper.deleteByChargeCode(chargeCode);


	}

}