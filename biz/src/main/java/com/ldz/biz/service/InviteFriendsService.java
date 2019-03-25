package com.ldz.biz.service;

import com.ldz.biz.model.InviteFriends;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

public interface InviteFriendsService extends BaseService<InviteFriends, String> {

    ApiResponse<String> updateCallBack(InviteFriends obj);
}