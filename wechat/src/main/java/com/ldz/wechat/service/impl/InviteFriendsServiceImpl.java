package com.ldz.wechat.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.commonUtil.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.ldz.wechat.service.InviteFriendsService;
import com.ldz.wechat.mapper.InviteFriendsMapper;
import com.ldz.wechat.model.InviteFriends;

@Service
public class InviteFriendsServiceImpl extends BaseServiceImpl<InviteFriends, String> implements InviteFriendsService {

	@Autowired
	private InviteFriendsMapper baseMapper;
	
	@Override
	protected Mapper<InviteFriends> getBaseMapper() {
		return baseMapper;
	}

    @Override
    public ApiResponse<String> saveEntity(InviteFriends inviteFriends) {
        inviteFriends.setCreateTime(DateUtils.getNowTime());
        inviteFriends.setId(genId());
        this.save(inviteFriends);
		return ApiResponse.saveSuccess();
    }
}