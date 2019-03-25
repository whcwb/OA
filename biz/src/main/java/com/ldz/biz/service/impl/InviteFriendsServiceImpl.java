package com.ldz.biz.service.impl;

import com.ldz.biz.mapper.InviteFriendsMapper;
import com.ldz.biz.service.WxOperateLogService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.sys.model.SysYh;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.commonUtil.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.ldz.biz.service.InviteFriendsService;
import com.ldz.biz.model.InviteFriends;
import tk.mybatis.mapper.entity.Example;

@Service
public class InviteFriendsServiceImpl extends BaseServiceImpl<InviteFriends, String> implements InviteFriendsService {

	@Autowired
	private InviteFriendsMapper baseMapper;
	@Autowired
	private WxOperateLogService wxLog;

	@Override
	protected Mapper<InviteFriends> getBaseMapper() {
		return baseMapper;
	}

	/**
	 * 按指定字段进行排序
	 * @param condition
	 * @return
	 */

	@Override
	public boolean fillPagerCondition(LimitedCondition condition){
		condition.setOrderByClause("create_time desc");
		return true;
	}

	@Override
	public ApiResponse<String> updateCallBack(InviteFriends obj){
		SysYh currentUser = getCurrentUser();
		obj.setRevisitDate(DateUtils.getNowTime());
		obj.setRevisitUser(currentUser.getYhid()+"-"+currentUser.getXm());
		wxLog.saveEntity(currentUser,obj.getId(),obj.getStatus(),obj.getBackDescribe(),"2");
		return update(obj)>0?ApiResponse.success():ApiResponse.fail("数据库操作失败");
	}
}