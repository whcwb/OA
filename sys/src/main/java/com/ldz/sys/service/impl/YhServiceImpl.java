package com.ldz.sys.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.sys.constant.Dict;
import com.ldz.sys.mapper.SysClkPtyhMapper;
import com.ldz.sys.mapper.SysJsGnMapper;
import com.ldz.sys.mapper.SysYhJsMapper;
import com.ldz.sys.model.*;
import com.ldz.sys.service.GnService;
import com.ldz.sys.service.JgService;
import com.ldz.sys.service.JsService;
import com.ldz.sys.service.YhService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.Des;
import com.ldz.util.commonUtil.EncryptUtil;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tk.mybatis.mapper.common.Mapper;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class YhServiceImpl extends BaseServiceImpl<SysYh, String> implements YhService {
	Logger log = LogManager.getLogger(this);
	@Value("${resePwd:111111}")
	private String resePwd;
	@Autowired
	private SysClkPtyhMapper baseMapper;
	@Autowired
	private GnService gnService;
	@Autowired
	private JgService jgService;
	@Autowired
	private JsService jsService;
	@Autowired
	private SysJsGnMapper jsGnMapper;
	@Autowired
	private SysYhJsMapper yhJsMapper;

	@Override
	protected Class<SysYh> getEntityCls(){
		return SysYh.class;
	}

	@Override
	protected Mapper<SysYh> getBaseMapper() {
		return baseMapper;
	}

	/**
	 * 新增用户
	 *
	 * @param user 用户
	 * @return 执行结果
	 */
	@Override
	public ApiResponse<String> validAndSave(SysYh user) {
		RuntimeCheck.ifBlank(user.getZh(),"账号不能为空");
		RuntimeCheck.ifBlank(user.getXm(),"姓名不能为空");
		RuntimeCheck.ifBlank(user.getSjh(),"手机号不能为空");
		RuntimeCheck.ifFalse(StringUtils.isAlphanumeric(user.getZh()),"登陆名只能是数字和字母组成！");
		boolean exists = ifExists(SysYh.InnerColumn.zh.name(),user.getZh());
		RuntimeCheck.ifTrue(exists,"登陆名已存在，请更换别的登陆名！");
//		SysJg org = jgService.findByOrgCode(user.getJgdm());
//		RuntimeCheck.ifNull(org,"机构不存在");

		SysYh currentUser = getCurrentUser();
		user.setYhid(String.valueOf(idGenerator.nextId()));
		user.setMm(EncryptUtil.encryptUserPwd(user.getMm()));
		user.setCjr(currentUser.getYhid());
		user.setCjsj(new Date());
		user.setZt(Dict.UserStatus.VALID.getCode());
		user.setRoleId("ur"+user.getYhid());
		if (StringUtils.isEmpty(user.getJgdm())){
			user.setJgdm(currentUser.getJgdm());
		}
		baseMapper.insertSelective(user);


//		initUserRole();
		List<SysJs> sysJs = jsService.findEq(SysJs.InnerColumn.jsId, "ur" + user.getYhid());

		if(CollectionUtils.isEmpty(sysJs)){
			SysJs role = new SysJs();
			role.setJsId("ur"+user.getYhid());
			role.setCjsj(new Date());
			role.setJgdm(user.getJgdm());
			role.setJsmc(user.getXm()+"-角色");
			role.setJslx("40");
			jsService.save(role);

			SysYhJs yhJs = new SysYhJs();
			yhJs.setJsId(role.getJsId());
			yhJs.setYhId(user.getYhid());
			yhJs.setYhjsId(genId());
			yhJsMapper.insertSelective(yhJs);
		}

//		initUserRole();
		// 如果用户类型为管理员，则默认拥有系统管理权限
//		if ("00".equals(user.getLx())){
//			addAdminPermission(user);
//		}
		ApiResponse<String> r = new ApiResponse<>();
		r.setResult(user.getYhid());
		return r;
	}

	private void addAdminPermission(SysYh user){
		// 添加机构管理员角色
		Date now = new Date();
		SysJs adminRole = new SysJs();
		SysYh currentUser = getCurrentUser();
		SysJg jg = jgService.findByOrgCode(currentUser.getJgdm());
		adminRole.setCjr(currentUser.getZh());
		adminRole.setJgdm(user.getJgdm());
		adminRole.setCjsj(now);
		adminRole.setJsId(genId());
		adminRole.setJslx("00");
		adminRole.setJsmc(jg.getJgmc()+"-机构管理员");
		adminRole.setZt("00");
		jsService.saveEntity(adminRole);

		SysYhJs yhJs = new SysYhJs();
		yhJs.setCjr(currentUser.getZh());
		yhJs.setCjsj(now);
		yhJs.setJsId(adminRole.getJsId());
		yhJs.setYhId(user.getYhid());
		yhJs.setYhjsId(genId());
		yhJsMapper.insertSelective(yhJs);

		List<String> permissionList = Arrays.asList("system-user","system-role","system-framework");
		List<SysGn> functinos = gnService.findIn(SysGn.InnerColumn.gndm,permissionList);
		for (SysGn functino : functinos) {
			SysJsGn jsGn = new SysJsGn();
			jsGn.setCjr(currentUser.getZh());
			jsGn.setCjsj(now);
			jsGn.setFwdm("1");
			jsGn.setGndm(functino.getGndm());
			jsGn.setFgndm(functino.getFjd());
			jsGn.setJsdm(adminRole.getJsId());
			jsGn.setId(genId());
			jsGnMapper.insertSelective(jsGn);
		}
		gnService.cachePermission(Arrays.asList(adminRole.getJsId()));
	}

	@Override
	public ApiResponse<String> saveEntity(SysYh entity) {
		RuntimeCheck.ifBlank(entity.getZh(),"请先输入登陆名！");
		RuntimeCheck.ifBlank(entity.getMm(),"请先输入登陆密码！");
		RuntimeCheck.ifFalse(StringUtils.isAlphanumeric(entity.getZh()),"登陆名只能是数字和字母组成！");

		SimpleCondition condition = new SimpleCondition(SysYh.class);
		condition.eq(SysYh.InnerColumn.zh.name(), entity.getZh());
		Integer count = this.countByCondition(condition);
		RuntimeCheck.ifTrue(count > 0,"登陆名已存在，请更换别的登陆名！");

		entity.setYhid(genId());
		entity.setMm(EncryptUtil.encryptUserPwd(entity.getMm()));
		entity.setZt(Dict.UserStatus.VALID.getCode());
		entity.setSjh(entity.getSjh().trim());
		entity.setCjsj(new Date());
		if (StringUtils.isEmpty(entity.getJgdm())){
			SysYh user = getCurrentUser();
			entity.setJgdm(user.getJgdm());
		}
		this.save(entity);
		return ApiResponse.saveSuccess();
	}

	/**
	 * 修改登录密码
	 *
	 * @param userId 用户id
	 * @param oldPwd 旧密码
	 * @param newPwd 新密码
	 * @return 操作结果
	 */
	@Override
	public ApiResponse<String> mdfPwd(String userId, String oldPwd, String newPwd) {
		SysYh user = baseMapper.selectByPrimaryKey(userId);
		String newEncrypt;
		if (user == null) return ApiResponse.fail("用户不存在");
		try {
			String encrypt = Des.encrypt(oldPwd);
			if (!encrypt.equals(user.getMm())){
				return ApiResponse.fail("密码错误");
			}
			newEncrypt = Des.encrypt(newPwd);
		} catch (Exception e) {
			log.error("加密失败 oldPwd={},newPwd={}",oldPwd,newPwd);
			return ApiResponse.fail("加密失败");
		}
		user.setMm(newEncrypt);
		baseMapper.updateByPrimaryKeySelective(user);
		return ApiResponse.success();
	}



	@Override
	public ApiResponse<String> updateEntity(SysYh user) {
		RuntimeCheck.ifBlank(user.getZh(),"账号不能为空");
		RuntimeCheck.ifBlank(user.getXm(),"姓名不能为空");
		RuntimeCheck.ifBlank(user.getJgdm(),"请选择机构");
		RuntimeCheck.ifBlank(user.getSjh(),"手机号不能为空");
		RuntimeCheck.ifFalse(StringUtils.isAlphanumeric(user.getZh()),"登陆名只能是数字和字母组成！");
		SysJg org = jgService.findByOrgCode(user.getJgdm());
		RuntimeCheck.ifNull(org,"机构不存在");

		LimitedCondition condition= new LimitedCondition(SysYh.class);
		condition.eq(SysYh.InnerColumn.zh, user.getZh());
		List<SysYh> list=baseMapper.selectByExample(condition);

		if(list==null||list.size()==0){
			RuntimeCheck.ifTrue(true,"该账户不存在，或该账户不属于本机构，您无法修改！");
		}

		baseMapper.updateByPrimaryKeySelective(user);
		return updateSession(user);
	}

	@Override
	public ApiResponse<String> updateSession(SysYh user) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		request.setAttribute("userInfo",user);
		request.setAttribute("orgCode",user.getJgdm());
		return ApiResponse.success();
	}

	/**
	 * 重置密码
	 * @param userId
	 * @return
	 */
	@Override
	public ApiResponse<String> resetPassword(String userId){
		SysYh sysUser = getCurrentUser(true);
		RuntimeCheck.ifTrue(StringUtils.equals(sysUser.getYhid(),userId),"您不能为本人重置密码，请联系管理员进行重置密码。");
		SysYh user = baseMapper.selectByPrimaryKey(userId);
		if (user == null) return ApiResponse.fail("用户不存在");
		RuntimeCheck.ifFalse(user.getJgdm().indexOf(sysUser.getJgdm())==0,"您不能为非本机构的人员重置密码");
		String newEncrypt=null;
		try {
			newEncrypt = Des.encrypt(resePwd);
		} catch (Exception e) {
			log.error("加密失败 newEncrypt={}",resePwd);
			return ApiResponse.fail("加密失败");
		}

		user.setMm(newEncrypt);
		baseMapper.updateByPrimaryKeySelective(user);
		return ApiResponse.success();
	}

	@Override
	public SysYh queryByPhone(String phone) {
		SimpleCondition condition = new SimpleCondition(SysYh.class);
		condition.eq(SysYh.InnerColumn.sjh, phone);
		condition.setOrderByClause(SysYh.InnerColumn.yhid.desc());
		List<SysYh> list = this.findByCondition(condition);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public ApiResponse<String> bindUKey(SysYh updateUser) {
		ApiResponse<String> result = new ApiResponse<>();
		if (StringUtils.isEmpty(updateUser.getUkey())){
			return ApiResponse.fail("请先插入UKey！");
		}
		if (StringUtils.isEmpty(updateUser.getUkeyPwd())){
			return ApiResponse.fail("UKey密码无效，请重新操作！");
		}
		List<SysYh> exists = findEq(SysYh.InnerColumn.ukey,updateUser.getUkey());
		if (exists.size() != 0){
			return ApiResponse.fail("UKey已颁发，不能再次颁发！");
		}
		//为登陆用户颁发UKey
		SysYh existUser = this.findById(updateUser.getYhid());
		if (StringUtils.isEmpty(existUser.getUkey())){
			existUser.setUkey(updateUser.getUkey());
			existUser.setUkeyPwd(updateUser.getUkeyPwd());
			result.setMessage("UKey颁发成功！");
			baseMapper.updateByPrimaryKeySelective(existUser);
		}else{
			return ApiResponse.fail("用户已经颁发过UKey，不能重复颁发！");
		}
		return result;
	}

	@Override
	public ApiResponse<String> unBindUKey(SysYh user) {
		if (StringUtils.isEmpty(user.getUkey())){
			return ApiResponse.fail("请先插入UKey！");
		}
		SimpleCondition condition = new SimpleCondition(SysYh.class);
		condition.eq(SysYh.InnerColumn.yhid,user.getYhid());
		condition.eq(SysYh.InnerColumn.ukey,user.getUkey());
		List<SysYh> exists = findByCondition(condition);
		if (exists.size() == 0){
			return ApiResponse.fail("UKey不匹配，解绑失败");
		}
		SysYh exist = exists.get(0);
		exist.setUkey(null);
		exist.setUkeyPwd(null);
		baseMapper.updateByPrimaryKey(exist);
		return ApiResponse.success();
	}

	@Override
	public ApiResponse<String> updateLoginType(SysYh user) {
		RuntimeCheck.ifBlank(user.getYhid(),"请选择用户");
		RuntimeCheck.ifBlank(user.getLoginType(),"请选择登录方式");
		SysYh exist = baseMapper.selectByPrimaryKey(user.getYhid());
		RuntimeCheck.ifNull(exist,"用户不存在");
		exist.setLoginType(user.getLoginType());
		baseMapper.updateByPrimaryKeySelective(exist);
		return ApiResponse.success();
	}

    @Override
    public ApiResponse<String> initUserRole() {
		log.debug("initUserRole");
		SimpleCondition condition = new SimpleCondition(SysYh.class);
		condition.and().andIsNull(SysYh.InnerColumn.roleId.name());
		List<SysYh> userList = baseMapper.selectByExample(condition);
		log.debug("userList.size:"+userList.size());
		if (userList.size() == 0)return ApiResponse.success();
		Date now = new Date();

		for (SysYh sysYh : userList) {

			List<SysJs> sysJs = jsService.findEq(SysJs.InnerColumn.jsId, "ur" + sysYh.getYhid());

		if(CollectionUtils.isEmpty(sysJs)){
			SysJs role = new SysJs();
			role.setJsId("ur"+sysYh.getYhid());
			role.setCjsj(now);
			role.setJgdm(sysYh.getJgdm());
			role.setJsmc(sysYh.getXm()+"-角色");
			role.setJslx("40");
			jsService.save(role);

			SysYhJs yhJs = new SysYhJs();
			yhJs.setJsId(role.getJsId());
			yhJs.setYhId(sysYh.getYhid());
			yhJs.setYhjsId(genId());
			yhJsMapper.insertSelective(yhJs);

			sysYh.setRoleId(role.getJsId());
			baseMapper.updateByPrimaryKeySelective(sysYh);
		}


		}
        return ApiResponse.success();
    }


}
