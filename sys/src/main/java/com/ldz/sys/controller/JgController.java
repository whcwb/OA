package com.ldz.sys.controller;

import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.sys.bean.TreeNode;
import com.ldz.sys.model.SysJg;
import com.ldz.sys.model.SysYh;
import com.ldz.sys.service.JgService;
import com.ldz.util.bean.ApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/jg")
public class JgController extends BaseController<SysJg, String> {
	@Autowired
	private JgService jgService;

	@Override
	protected BaseService<SysJg, String> getBaseService() {
		return jgService;
	}

	@RequestMapping("getCurrentUserOrgTree")
	public ApiResponse<List<SysJg>> getCurrentUserOrgTree(){
		SysYh currentUser = getCurrentUser();
		String jgdms = currentUser.getJgdms();
		List<String> list = new ArrayList<>();
		list.add(currentUser.getJgdm());
		if(StringUtils.isNotBlank(jgdms)){
			 list.addAll( Arrays.asList(jgdms.split(",")));
		}
		List<SysJg> orgList =  jgService.findAllSubOrg(list,null);
		List<SysJg> orgTree = jgService.getOrgTree(orgList);
		return ApiResponse.success(orgTree);
	}
	@RequestMapping("getCurrentOrgTree")
	public ApiResponse<List<TreeNode>> getCurrentOrgTree(){
		SysYh currentUser = getCurrentUser();
		String jgdms = currentUser.getJgdms();
		List<String> list = new ArrayList<>();
		list.add(currentUser.getJgdm());
		if(StringUtils.isNotBlank(jgdms)){
			list.addAll(Arrays.asList(jgdms.split(",")));
		}
		List<SysJg> orgList =  jgService.findAllSubOrg(list,null);
		List<TreeNode> orgNode = jgService.convertToTreeNodeList(orgList);
		List<TreeNode> orgTree = TreeNode.buildTree(orgNode);
		return ApiResponse.success(orgTree);
	}

	@RequestMapping("add")
	public ApiResponse<String> add(SysJg entity) {
		return jgService.saveEntity(entity);
	}

	@RequestMapping("getOrgTree")
	public ApiResponse<List<SysJg>> getOrgTree(String jgmc) {
		return jgService.getOrgTree(jgmc);
	}

	@RequestMapping("getTree")
	public ApiResponse<List<TreeNode>> getTree(){
		return jgService.getTree();
	}


	@GetMapping("getOrgPath")
	public ApiResponse<List<SysJg>> getOrgPath(@RequestParam(value = "orgCode",required = false) String orgCode){
		return jgService.getOrgPath(orgCode);
	}

	@Override
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public ApiResponse<String> save(SysJg jg) {
		return jgService.saveEntity(jg);
	}

	@RequestMapping("getSubOrgList")
	public ApiResponse<List<SysJg>> getSubOrgList(String jgmc){
		SysYh user = getCurrentUser();
		String jgdms = user.getJgdms();
		List<String> list = new ArrayList<>();
		list.add(user.getJgdm());
		if(StringUtils.isNotBlank(jgdms)){
			list.addAll( Arrays.asList(jgdms.split(",")));
		}
		List<SysJg> orgList = jgService.findAllSubOrg(list,jgmc);
		return ApiResponse.success(orgList);
	}

	@PostMapping("/getSubOrg")
	public ApiResponse<SysJg> getSubOrg(@RequestParam(required = false) String jgdm){
		if(StringUtils.isBlank(jgdm)){
			SysYh sysYh = getCurrentUser();
			jgdm = sysYh.getJgdm();
		}
		SysJg byOrgCode = jgService.findByOrgCode(jgdm);
		List<SysJg> orgList = jgService.findSubOrg(jgdm);
		byOrgCode.setChildren(orgList);
		return ApiResponse.success(byOrgCode);
	}

}
