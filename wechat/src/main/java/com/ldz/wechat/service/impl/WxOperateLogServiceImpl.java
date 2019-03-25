package com.ldz.wechat.service.impl;


import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.model.SysYh;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.wechat.mapper.WxOperateLogMapper;
import com.ldz.wechat.model.WxOperateLog;
import com.ldz.wechat.service.WxOperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;


@Service
public class WxOperateLogServiceImpl extends BaseServiceImpl<WxOperateLog, String> implements WxOperateLogService {

	@Autowired
	private WxOperateLogMapper baseMapper;

	@Override
	protected Mapper<WxOperateLog> getBaseMapper() {
		return baseMapper;
	}

	@Override
	public int saveEntity(SysYh user, String operateId, String operateType, String operateDescribe,String type){
		WxOperateLog entity=new WxOperateLog();
		entity.setId(genId());
		entity.setType(type);
		entity.setOperateId(operateId);
		entity.setCjr(user.getYhid()+"-"+user.getXm());
		entity.setCjsj(DateUtils.getNowTime());
		entity.setJgdm(user.getJgdm());
		entity.setOperatetType(operateType);
		entity.setOperateDescribe(operateDescribe);
		return baseMapper.insert(entity);
	}

}