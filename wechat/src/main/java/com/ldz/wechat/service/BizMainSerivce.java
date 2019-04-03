package com.ldz.wechat.service;

import com.ldz.util.bean.ApiResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface BizMainSerivce {
    /**
     * 用户登陆短信下发
     * @param phone     手机号码
     * @param userrole     用户角色  1、学员  2、教练
     * @return
     */
    ApiResponse<String> sendSmsLogin(String phone, String userrole);

    /**
     * 通过验证码校验，并返回用户的ID
     * @param phone     手机号码
     * @param userrole  用户角色  1、学员  2、教练 3.boss
     * @param pollcode  验证号码
     * @return
     */
    ApiResponse<String> smsLoginGetUserId(String phone, String userrole, String pollcode);

    /**
     * 通过用户ID 获取用户信息
     * @param userId    用户ID
     * @param userrole  用户角色  1、学员  2、教练
     * @param openId    微信的OPEN_ID 非必填
     * @return 返回 登录信息
     */
    ApiResponse<Map<String,Object>> getUserInfo(String userId, String userrole, String openId);

    /**
     * 给用户发送消息
     * @param messageId
     * @return
     */
    ApiResponse<String> sendMesageToUser(String messageId);

    String loginByPassword(String phone, String password);

    ApiResponse<String> editPwd(String oldPwd, String newPwd, String newPwd1, HttpServletRequest request);
}
