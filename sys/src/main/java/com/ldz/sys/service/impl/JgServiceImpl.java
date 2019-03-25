package com.ldz.sys.service.impl;

import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.bean.TreeNode;
import com.ldz.sys.constant.Dict;
import com.ldz.sys.mapper.SysPtjgMapper;
import com.ldz.sys.model.SysJg;
import com.ldz.sys.model.SysYh;
import com.ldz.sys.service.JgService;
import com.ldz.sys.util.OrgUtil;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.*;
import java.util.stream.Collectors;

/**
 * auther chenwei create at 2018/2/26
 */
@Service
public class JgServiceImpl extends BaseServiceImpl<SysJg, String> implements JgService {
	@Autowired
	private SysPtjgMapper ptjgMapper;

	@Autowired
	private JgService jgService;

	@Override
	protected Mapper<SysJg> getBaseMapper() {
		return ptjgMapper;
	}

	@Override
	public List<SysJg> getOrgTree(List<SysJg> orgList) {
		Map<String, SysJg> orgIdMap = orgList.stream().collect(Collectors.toMap(SysJg::getJgdm, p -> p));
		List<SysJg> root = new ArrayList<>();
		for (SysJg org : orgList) {
			if (StringUtils.isEmpty(org.getFjgdm()) || !orgIdMap.containsKey(org.getFjgdm())) {
				root.add(org);
				continue;
			}
			SysJg father = orgIdMap.get(org.getFjgdm());
			if (father == null)
				continue;
			if (father.getChildren() == null || father.getChildren().size() == 0) {
				List<SysJg> children = new ArrayList<>();
				children.add(org);
				father.setChildren(children);
			} else {
				father.getChildren().add(org);
			}
		}
		return root;
	}

	@Override
	public List<SysJg> getOrgTreeByOrgCodes(List<String> orgCodes) {
		List<SysJg> orgs = findIn(SysJg.InnerColumn.jgdm, orgCodes);
		return getOrgTree(orgs);
	}

	/**
	 * 根据机构层级获取机构列表
	 *
	 * @param level
	 *            机构层级
	 * @return 机构列表
	 */
	@Override
	public List<SysJg> findByLevel(Integer level) {
		RuntimeCheck.ifNull(level, "机构层级不能为空");
		return findEq(SysJg.InnerColumn.jgdj, level);
	}

	/**
	 * 新增机构
	 *
	 * @param entity
	 *            参数
	 * @return 操作结果
	 */
	@Override
	public ApiResponse<String> saveEntity(SysJg entity) {
		if (StringUtils.isEmpty(entity.getJglx())) {
			entity.setJglx("10");
		}
		Dict.OrgType orgType = Dict.OrgType.toEnum(entity.getJglx());
		RuntimeCheck.ifNull(orgType, "机构类型不存在");

		entity.setCjr(getOperateUser());
		entity.setCjsj(new Date());
		entity.setZt(Dict.CommonStatus.VALID.getCode());
		String orgCode = genOrgCode(entity);
		entity.setJgdm(orgCode);
		if (StringUtils.isEmpty(entity.getFjgdm())) { // 一级机构
			entity.setJgdj(1);
		} else {
			String fatherCode = entity.getFjgdm();
			int fatherLevel = OrgUtil.getLevel(fatherCode);
			entity.setJgdj(fatherLevel + 1);
		}
		ptjgMapper.insertSelective(entity);
		ApiResponse<String> res = new ApiResponse<>();
		res.setResult(orgCode);
		return res;
	}

	private String genOrgCode(SysJg org) {
		String fatherCode = org.getFjgdm();
		if (fatherCode == null)
			fatherCode = "";
		int level = fatherCode.length() / 3 + 1;
		SimpleCondition condition = new SimpleCondition(SysJg.class);
		condition.eq(SysJg.InnerColumn.jgdj, level);
		condition.setOrderByClause(SysJg.InnerColumn.jgdm.desc());
		List<SysJg> orgs = findByCondition(condition);
		if (orgs.size() == 0)
			return fatherCode + "001";
		String orgCode = orgs.get(0).getJgdm();
		String maxJgdm = orgCode.substring(orgCode.length() - 3);
		int max = Integer.parseInt(maxJgdm) + 1;
		return fatherCode + StringUtils.leftPad("" + max, 3, "0");
	}

	@Override
	public SysJg findByOrgCode(String orgCode) {
		List<SysJg> jgs = findEq(SysJg.InnerColumn.jgdm, orgCode);
		if (jgs.size() == 0)
			return null;
		return jgs.get(0);
	}

	/**
	 * 获取机构下面的所有子机构
	 *
	 * @param orgCode
	 * @return
	 */
	@Override
	public List<SysJg> findAllSubOrg(List<String> orgCode,String jgmc) {
		SimpleCondition condition = new SimpleCondition(SysJg.class);
		String sql = "";
		for (String s : orgCode) {
			if(org.apache.commons.lang3.StringUtils.isNotEmpty(sql)) {
				sql += " or jgdm like '" + s + "%'";
			}else{
				sql += " jgdm like '" + s + "%'";
			}
		}
		condition.and().andCondition( sql );
		if (StringUtils.isNotEmpty(jgmc)){
			condition.like(SysJg.InnerColumn.jgmc,jgmc);
		}
		condition.setOrderByClause(" px asc , jgdm asc");
		List<SysJg> sysJgs = ptjgMapper.selectByExample(condition);
		sysJgs.forEach(sysJg -> {
			if(StringUtils.isNotBlank(sysJg.getFjgdm())) {
				SysJg byOrgCode = findByOrgCode(sysJg.getFjgdm());
				sysJg.setFjgmc(byOrgCode.getJgmc());
			}
		});
		return sysJgs;
	}

	@Override
	public ApiResponse<List<SysJg>> getOrgTree(String jgmc) {
		ApiResponse<List<SysJg>> response = new ApiResponse<>();
		SysYh user = getCurrentUser();
		String jgdms = user.getJgdms();
		List<String> list = new ArrayList<>();
		list.add(user.getJgdm());
		if(org.apache.commons.lang3.StringUtils.isNotBlank(jgdms)){
			list.addAll( Arrays.asList(jgdms.split(",")));
		}
		List<SysJg> orgs = jgService.findAllSubOrg(list,jgmc);
		List<SysJg> orgTree = jgService.getOrgTree(orgs);
		response.setResult(orgTree);
		return response;
	}

	/**
	 * 根据机构代码获取当前机构的所有父机构
	 * @param orgCode
	 * @return
	 */
	@Override
	public ApiResponse<List<SysJg>> getOrgPath(String orgCode) {
		SysJg function = findById(orgCode);
		RuntimeCheck.ifNull(function,"未找到记录");
		List<SysJg> list = new ArrayList<>();
		list.add(function);
		findParent(function,list);
		list.sort(Comparator.comparingInt(SysJg::getJgdj));
		return ApiResponse.success(list);
	}

    @Override
    public List<SysJg> findSubOrg(String jgdm) {
        SimpleCondition condition = new SimpleCondition(SysJg.class);
        condition.eq(SysJg.InnerColumn.fjgdm, jgdm);
        condition.setOrderByClause(" px asc ");
		List<SysJg> byCondition = findByCondition(condition);
		return byCondition;
    }


    private void findParent(SysJg function,List<SysJg> result){
		if (function == null){
			return;
		}
		if (StringUtils.isEmpty(function.getFjgdm())){
			return;
		}
		SysJg father = findById(function.getFjgdm());
		if (father == null){
			return;
		}
		result.add(father);
		findParent(father,result);
	}

	@Override
	public ApiResponse<List<TreeNode>> getTree() {
		SysYh user = getCurrentUser();
		String jgdms = user.getJgdms();
		List<String> list = new ArrayList<>();
		list.add(user.getJgdm());
		if(org.apache.commons.lang3.StringUtils.isNotBlank(jgdms)){
			list.addAll( Arrays.asList(jgdms.split(",")));
		}
		List<SysJg> orgs = jgService.findAllSubOrg(list,null);
		List<TreeNode> treeNodes = convertToTreeNodeList(orgs);
		treeNodes = TreeNode.buildTree(treeNodes);
		return ApiResponse.success(treeNodes);
	}



	@Override
	public List<TreeNode> convertToTreeNodeList(List<SysJg> orgList){
		List<TreeNode> treeNodes = new ArrayList<>(orgList.size());
		for (SysJg jg : orgList) {
			treeNodes.add(convertToTreeNode(jg));
		}
		return treeNodes;
	}
	private TreeNode convertToTreeNode(SysJg org){
		TreeNode node = new TreeNode();
		node.setLabel(org.getJgmc());
		node.setValue(org.getJgdm());
		node.setFather(org.getFjgdm());
		node.setLx(org.getLx());
		return node;
	}


}
