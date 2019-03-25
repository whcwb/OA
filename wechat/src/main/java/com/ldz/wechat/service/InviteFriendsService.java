package com.ldz.wechat.service;

import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.InviteFriends;

public interface InviteFriendsService extends BaseService<InviteFriends, String> {
    ApiResponse<String > saveEntity(InviteFriends inviteFriends);

}