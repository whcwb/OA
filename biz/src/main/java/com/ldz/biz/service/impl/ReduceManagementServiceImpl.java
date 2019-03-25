package com.ldz.biz.service.impl;

import com.github.pagehelper.PageInfo;
import com.ldz.biz.mapper.ReduceManagementMapper;
import com.ldz.biz.model.ReduceInstitution;
import com.ldz.biz.model.ReduceManagement;
import com.ldz.biz.model.ValueLabelModel;
import com.ldz.biz.service.ReduceInstitutionService;
import com.ldz.biz.service.ReduceManagementService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.sys.model.SysJg;
import com.ldz.sys.model.SysYh;
import com.ldz.sys.service.JgService;
import com.ldz.sys.service.YhService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReduceManagementServiceImpl extends BaseServiceImpl<ReduceManagement, String> implements ReduceManagementService {

	@Autowired
	private ReduceManagementMapper baseMapper;
	@Autowired
	private ReduceInstitutionService institutionService;
	@Autowired
	private JgService jgService;
	@Autowired
	private YhService yhService;

	@Override
	protected Mapper<ReduceManagement> getBaseMapper() {
		return baseMapper;
	}

	/**
	 * 分页补充
	 * @param condition
	 * @return
	 */
	@Override
	public boolean fillPagerCondition(LimitedCondition condition){
		HttpServletRequest requset = getRequset();
		String group = requset.getParameter("group");
		if(StringUtils.equals(group, "2")){  // 查询团购
			condition.gt(ReduceManagement.InnerColumn.groupNum, 1);
		}else if(StringUtils.equals(group, "1")){
			condition.lte(ReduceManagement.InnerColumn.groupNum, 1);
		}

		String mc = requset.getParameter("mc");
		if(StringUtils.isNotBlank(mc)){
			SimpleCondition simpleCondition = new SimpleCondition(ReduceInstitution.class);
			simpleCondition.like(ReduceInstitution.InnerColumn.jgmc, mc);
			List<ReduceInstitution> institutions = institutionService.findByCondition(simpleCondition);
			List<String> list = institutions.stream().map(ReduceInstitution::getReduceId).collect(Collectors.toList());
			if(CollectionUtils.isNotEmpty(list)) {
				condition.in(ReduceManagement.InnerColumn.id, list);
			}
		}

		return true;
	}



	/**
	 * 新增优惠项
	 * @param entity
	 * @return
	 */
	@Override
	public ApiResponse<String> validAndSave(ReduceManagement entity){
		SysYh currentUser = getCurrentUser();
		List<String> jgdms = entity.getJgdms();
		RuntimeCheck.ifBlank(entity.getReduceName(), "优惠名称不能为空");
		RuntimeCheck.ifBlank(entity.getReduceStartTime(), "优惠开始时间不能为空");
		RuntimeCheck.ifBlank(entity.getReduceEndTime(), "优惠结束时间不能为空");
		RuntimeCheck.ifTrue(entity.getReduceAmount() <=0 , "优惠金额不能小于或等于0");
		RuntimeCheck.ifTrue( StringUtils.isBlank(entity.getVerifier()), "审核人必须有");

		ReduceManagement reduceManagement = new ReduceManagement();
		BeanUtils.copyProperties(entity,reduceManagement);
		reduceManagement.setCjr(currentUser.getZh()+"-"+currentUser.getXm());
		reduceManagement.setCjsj(DateUtils.getNowTime());
		SysJg sysJg = jgService.findById(currentUser.getJgdm());
		reduceManagement.setJgmc(sysJg.getJgmc());
		reduceManagement.setJgdm(sysJg.getJgdm());
		reduceManagement.setGroupNum(1);
		reduceManagement.setId(genId());
		int i = save(reduceManagement);
		List<ReduceInstitution> manages = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(jgdms)){
			for(String jgdm : jgdms){
				ReduceInstitution manage = new ReduceInstitution();
				SysJg jg = jgService.findById(jgdm);
				SysJg jg1 = jgService.findById(jgdm.substring(0, jgdm.length() - 3));
				manage.setId(genId());
				manage.setCjsj(DateUtils.getNowTime());
				manage.setCjr(currentUser.getZh()+"-"+currentUser.getXm());
				manage.setJgmc(jg1.getJgmc()+ "/" + jg.getJgmc());
				manage.setReduceId(reduceManagement.getId());
				manage.setJgdm(jgdm);
				manages.add(manage);

			}
		}
		if(CollectionUtils.isNotEmpty(manages)){
			institutionService.saveBatch(manages);
		}


		return i == 1 ? ApiResponse.saveSuccess():ApiResponse.fail();
	}

	@Override
	public ApiResponse<String> saveGroup(ReduceManagement entity) {
		SysYh currentUser = getCurrentUser();
		List<String> jgdms = entity.getJgdms();
		RuntimeCheck.ifBlank(entity.getReduceName(), "优惠名称不能为空");
		RuntimeCheck.ifBlank(entity.getReduceStartTime(), "优惠开始时间不能为空");
		RuntimeCheck.ifBlank(entity.getReduceEndTime(), "优惠结束时间不能为空");
		RuntimeCheck.ifTrue(entity.getReduceAmount() <=0 , "优惠金额不能小于或等于0");
		RuntimeCheck.ifTrue( StringUtils.isBlank(entity.getVerifier()), "审核人不能为空");
		RuntimeCheck.ifTrue(entity.getGroupNum() <=1 , "团购限定人数必须是大于1的整数");

		ReduceManagement reduceManagement = new ReduceManagement();
		BeanUtils.copyProperties(entity,reduceManagement);
		reduceManagement.setCjr(currentUser.getZh()+"-"+currentUser.getXm());
		reduceManagement.setCjsj(DateUtils.getNowTime());
		SysJg sysJg = jgService.findById(currentUser.getJgdm());
		reduceManagement.setJgmc(sysJg.getJgmc());
		reduceManagement.setJgdm(sysJg.getJgdm());
		reduceManagement.setId(genId());
		int i = save(reduceManagement);
		List<ReduceInstitution> manages = new ArrayList<>();

		if(CollectionUtils.isNotEmpty(jgdms)){
			for(String jgdm : jgdms){
				ReduceInstitution manage = new ReduceInstitution();
				SysJg jg = jgService.findById(jgdm);
				SysJg jg1 = jgService.findById(jgdm.substring(0, jgdm.length() - 3));
				manage.setId(genId());
				manage.setCjsj(DateUtils.getNowTime());
				manage.setCjr(currentUser.getZh()+"-"+currentUser.getXm());
				manage.setJgmc(jg1.getJgmc()+ "/" + jg.getJgmc());
				manage.setReduceId(reduceManagement.getId());
				manage.setJgdm(jgdm);
				manages.add(manage);

			}
		}
		if(CollectionUtils.isNotEmpty(manages)){
			institutionService.saveBatch(manages);
		}
		return i==1 ? ApiResponse.saveSuccess():ApiResponse.fail();
	}

	@Override
	protected void afterPager(PageInfo<ReduceManagement> resultPage){
		if(CollectionUtils.isEmpty(resultPage.getList())){
			return;
		}
		List<ReduceManagement> pageList = resultPage.getList();
		ReduceInstitution reduceInstitution = new ReduceInstitution();
		for (ReduceManagement reduceManagement : pageList){
			String id = reduceManagement.getId();
			reduceInstitution.setReduceId(id);
			List<ReduceInstitution> entity = institutionService.findByEntity(reduceInstitution);



			String verifier = reduceManagement.getVerifier();
			if(verifier.contains(",")) {
				List<String> strings = Arrays.asList(verifier.split(","));
				SimpleCondition condition = new SimpleCondition(SysYh.class);
				condition.in(SysYh.InnerColumn.yhid,strings);
				List<SysYh> yh = yhService.findByCondition(condition);
				reduceManagement.setSysYh(yh.get(0));
				reduceManagement.setSysYhs(yh);
			}else{
				SysYh yh = yhService.findById(verifier);
				reduceManagement.setSysYh(yh);
				reduceManagement.setSysYhs(Arrays.asList(yh));
			}





			HttpServletRequest requset = getRequset();
			String mc = requset.getParameter("mc");
			if(StringUtils.isNotBlank(mc)){
				List<ReduceInstitution> collect = entity.stream().filter(reduceInstitution1 -> reduceInstitution1.getJgmc().contains(mc)).collect(Collectors.toList());
				entity.removeAll(collect);
				collect.addAll(entity);
				entity = collect;
			}
			reduceManagement.setInstitutions(entity);
		}


	}

	@Override
	public ApiResponse<String> updateEntity(ReduceManagement entity) {
		SysYh currentUser = getCurrentUser();
		ReduceManagement management = findById(entity.getId());
		RuntimeCheck.ifNull(management, "未找到该记录");
		institutionService.deleteByReduceId(entity.getId());
		List<String> jgdms = entity.getJgdms();
		List<ReduceInstitution> institutions = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(jgdms)){
			for (String jgdm : jgdms) {
				ReduceInstitution institution = new ReduceInstitution();
				institution.setId(genId());
				institution.setReduceId(entity.getId());
				institution.setJgdm(jgdm);
				SysJg jg = jgService.findById(jgdm);
				SysJg jg1 = jgService.findById(jgdm.substring(0, jgdm.length() - 3));
				institution.setJgmc(jg1.getJgmc()+ "/" + jg.getJgmc());
				institution.setOperater(currentUser.getZh()+"-"+currentUser.getXm());
				institution.setOperateTime(DateUtils.getNowTime());
				institutions.add(institution);
			}
		}
		if(CollectionUtils.isNotEmpty(institutions)){
			institutionService.saveBatch(institutions);
		}
		entity.setOperateTime(DateUtils.getNowTime());
		entity.setOperater(currentUser.getZh()+"-"+currentUser.getXm());
		update(entity);
		return ApiResponse.success();
	}
	/**
	 * 获取机构下所有的优惠列表
	 * @param jgdm  机构代码
	 * @return
	 */
	@Override
	public ApiResponse<List<ReduceManagement>> getJgReduce(String jgdm){
		RuntimeCheck.ifBlank(jgdm, "机构代码不能为空");
//		SimpleCondition condition = new SimpleCondition(ReduceManagement.class);
//		//开始时间 和 结束时间
//		condition.lte(ReduceManagement.InnerColumn.reduceStartTime,DateUtils.getNowTime());
//		condition.gte(ReduceManagement.InnerColumn.reduceEndTime,DateUtils.getNowTime());
//
//		condition.startWith(ReduceManagement.InnerColumn.jgdm,jgdm);
//		List<ReduceManagement> list =findByCondition(condition);
        List<ReduceManagement> list = baseMapper.getJgReduce(DateUtils.getNowTime(),DateUtils.getNowTime(),jgdm);

		return ApiResponse.success(list);
	}

	/**
	 * 查询用户的职务和对应的人
	 * @return
	 */
	@Override
	public ApiResponse<List<ValueLabelModel>> findValueLabel() {
		List<String> lxs = baseMapper.findAllZw();
		List<ValueLabelModel> models = new ArrayList<>();
		// 获取所有职务
		for (String s : lxs) {
			ValueLabelModel valueLabelModel  = new ValueLabelModel();
			valueLabelModel.setValue(s);
			String zdxm = baseMapper.getZdxm("ZDCLK0003", s);
			valueLabelModel.setLabel(zdxm);
			valueLabelModel.setTitle(zdxm);
			List<ValueLabelModel.ValueLabel> labelModels = baseMapper.findIdAndXm(s);
			valueLabelModel.setChildren(labelModels);
			models.add(valueLabelModel);
		}

		return ApiResponse.success(models);
	}

	@Override
	public ApiResponse<String> removeEntity(String id) {

		baseMapper.deleteByPrimaryKey(id);
		institutionService.deleteByReduceId(id);
		return ApiResponse.success();
	}

}