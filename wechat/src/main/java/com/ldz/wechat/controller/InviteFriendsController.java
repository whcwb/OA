package com.ldz.wechat.controller;

import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.FeedBack;
import com.ldz.wechat.model.InviteFriends;
import com.ldz.wechat.service.FeedBackService;
import com.ldz.wechat.service.InviteFriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 邀请好友
 */
@RestController
@RequestMapping("/api/invitefriend")
public class InviteFriendsController extends BaseController<InviteFriends,String>{
    @Autowired
    private InviteFriendsService service;
    @Override
    protected BaseService<InviteFriends, String> getBaseService() {
        return service;
    }

    @PostMapping("/saveEntity")
    public ApiResponse<String> saveEntity(InviteFriends inviteFriends){
        return service.saveEntity(inviteFriends);
    }
}
