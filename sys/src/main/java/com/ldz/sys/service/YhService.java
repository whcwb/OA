package com.ldz.sys.service;

import com.ldz.sys.base.BaseService;
import com.ldz.sys.model.SysFw;
import com.ldz.sys.model.SysYh;
import com.ldz.util.bean.ApiResponse;

import java.util.List;

public interface YhService extends BaseService<SysYh, String> {

	/**
	 * 新增用户
	 * @param user 用户
	 * @return 执行结果
	 */
//	ApiResponse<String> addUser(SysYh user);

	ApiResponse<String> saveEntity(SysYh entity);

	/**
	 * 修改登录密码
	 * @param userId 用户id
	 * @param oldPwd 旧密码
	 * @param newPwd 新密码
	 * @return 操作结果
	 */
	ApiResponse<String> mdfPwd(String userId, String oldPwd, String newPwd);

    ApiResponse<String> updateEntity(SysYh user);

    ApiResponse<String> updateSession(SysYh user);

	/**
	 * 重置密码
	 * @param userId
	 * @return
	 */
	ApiResponse<String> resetPassword(String userId);

	/**
	 * 通过手机号查询用户
	 * @param phone
	 * @return
	 */
	SysYh queryByPhone(String phone);

    ApiResponse<String> bindUKey(SysYh user);

    ApiResponse<String> unBindUKey(SysYh user);

	ApiResponse<String> updateLoginType(SysYh user);

    ApiResponse<String> initUserRole();
}
