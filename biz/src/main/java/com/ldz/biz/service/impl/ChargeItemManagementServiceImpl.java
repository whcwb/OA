package com.ldz.biz.service.impl;

import com.github.pagehelper.PageInfo;
import com.ldz.biz.constant.FeeType;
import com.ldz.biz.mapper.ChargeItemManagementMapper;
import com.ldz.biz.model.ChargeInstitutionManage;
import com.ldz.biz.model.ChargeItemManagement;
import com.ldz.biz.model.KeyValue;
import com.ldz.biz.service.ChargeInstitutionManageService;
import com.ldz.biz.service.ChargeItemManagementService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.sys.model.SysJg;
import com.ldz.sys.model.SysYh;
import com.ldz.sys.model.SysZdxm;
import com.ldz.sys.service.JgService;
import com.ldz.sys.service.ZdxmService;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChargeItemManagementServiceImpl extends BaseServiceImpl<ChargeItemManagement, String> implements ChargeItemManagementService {

	@Autowired
	private ChargeItemManagementMapper baseMapper;
	@Autowired
	private JgService jgService;
	@Autowired
	private ChargeInstitutionManageService manageService;
	@Autowired
	private ZdxmService zdxmService;
	@Override
	protected Mapper<ChargeItemManagement> getBaseMapper() {
		return baseMapper;
	}

	@Override
	public boolean fillPagerCondition(LimitedCondition condition){
		HttpServletRequest requset = getRequset();
		String mc = requset.getParameter("mc");
		if(StringUtils.isNotBlank(mc)){
			SimpleCondition condition1 = new SimpleCondition(ChargeInstitutionManage.class);
			condition1.like(ChargeInstitutionManage.InnerColumn.jgmc, mc);
			List<ChargeInstitutionManage> manages = manageService.findByCondition(condition1);
			List<String> list = manages.stream().map(ChargeInstitutionManage::getChargeId).collect(Collectors.toList());
			if(CollectionUtils.isNotEmpty(list)) {
				condition.in(ChargeItemManagement.InnerColumn.id, list);
			}
		}
		return true;
	}

	@Override
	public String findChargeName(String jgdm, String chargeCode,String carType){
		return baseMapper.findChargeName(jgdm, chargeCode,carType);
	}


	/**
	 * 收费项新增
	 * @param entity
	 * @return
	 */
	@Override
	public ApiResponse<String> validAndSave(ChargeItemManagement entity){
		SysYh currentUser = getCurrentUser();
		List<String> jgdms =  entity.getJgdms();
		if(StringUtils.startsWith(entity.getChargeCode(), FeeType.SIGN_UP) && CollectionUtils.isEmpty(jgdms)){
			return ApiResponse.fail("报名费需要选择相应报名点");
		}
		RuntimeCheck.ifBlank(entity.getInOutType(), "收支类型不能为空");
		if(entity.getInOutType().equals("00")) {
			RuntimeCheck.ifTrue(entity.getAmount() < 0, "金额不能为小于0的整数");
		}
		RuntimeCheck.ifBlank(entity.getChargeName(), "名称不能为空");

		ChargeItemManagement itemManagement = new ChargeItemManagement();
		BeanUtils.copyProperties(entity, itemManagement);
		itemManagement.setCjr(currentUser.getZh()+"-"+currentUser.getXm());  // 创建人
		itemManagement.setCjsj(DateUtils.getNowTime()); // 创建时间
		itemManagement.setStatus("00");  // 设置状态为有效

		itemManagement.setId(genId());
		List<ChargeInstitutionManage> manages = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(jgdms)){

			for(String jgdm : jgdms){
				ChargeInstitutionManage manage = new ChargeInstitutionManage();
				SysJg jg = jgService.findById(jgdm);
				SysJg pJg = jgService.findById(jgdm.substring(0, jgdm.length() - 3));
				manage.setId(genId());
				manage.setCjsj(DateUtils.getNowTime());
				manage.setCjr(currentUser.getZh()+"-"+currentUser.getXm());
				manage.setJgmc(pJg.getJgmc()+ "/" + jg.getJgmc());
				manage.setChargeId(itemManagement.getId());
				manage.setJgdm(jgdm);
				manages.add(manage);
			}

		}


		if(CollectionUtils.isNotEmpty(manages)) {
			manageService.saveBatch(manages);
		}
		if(manages.size() > 0 ){
			StringBuffer buf = new StringBuffer();
			for(int i = 0 ; i < manages.size() -1 ; i++){
				if(i<=2){
					String mc = manages.get(i).getJgmc();
					buf.append(mc).append(",");
				}
			}
			itemManagement.setJgmc(buf.toString());
		}
		int i = save(itemManagement);
		return ApiResponse.success();
	}


	@Override
	protected void afterPager(PageInfo<ChargeItemManagement> resultPage){
		if(CollectionUtils.isEmpty(resultPage.getList())){
			return;
		}
		List<ChargeItemManagement> list = resultPage.getList();
		ChargeInstitutionManage m = new ChargeInstitutionManage();
		for(ChargeItemManagement itemManagement : list){
			String chargeCode = itemManagement.getId();
			m.setChargeId(chargeCode);
			List<ChargeInstitutionManage> manages = manageService.findByEntity(m);
			HttpServletRequest requset = getRequset();
			String mc = requset.getParameter("mc");
			if(StringUtils.isNotBlank(mc)) {
				List<ChargeInstitutionManage> collect = manages.stream().filter(chargeInstitutionManage -> chargeInstitutionManage.getJgmc().contains(mc)).collect(Collectors.toList());
				manages.removeAll(collect);
				collect.addAll(manages);
				manages = collect;
			}
			itemManagement.setInstitutions(manages);
		}
	}


	@Override
	public ApiResponse<String> updateEntity(ChargeItemManagement entity) {
		SysYh sysYh = getCurrentUser();
		ChargeItemManagement byId = findById(entity.getId());
		RuntimeCheck.ifNull(byId, "未找到该记录");
		manageService.deleteByChargeCode(entity.getId());
		List<String> jgdms =  entity.getJgdms();
		if(CollectionUtils.isNotEmpty(jgdms)){
			List<ChargeInstitutionManage> manages = new ArrayList<>();
			for(String jgdm : jgdms){
				ChargeInstitutionManage manage = new ChargeInstitutionManage();
				SysJg jg = jgService.findById(jgdm);
				SysJg pJg = jgService.findById(jgdm.substring(0, jgdm.length() - 3));
				manage.setId(genId());
				manage.setCjsj(byId.getCjsj());
				manage.setCjr(byId.getCjr());
				manage.setJgmc(pJg.getJgmc() + "/" + jg.getJgmc());
				manage.setChargeId(entity.getId());
				manage.setJgdm(jgdm);
				manages.add(manage);
			}
			manageService.saveBatch(manages);
		}
		entity.setOperater(sysYh.getXm());
		entity.setOperateTime(DateUtils.getNowTime());
		update(entity);
		return ApiResponse.success();
	}

	@Override
	public ApiResponse<String> removeForId(String pkid) {
		baseMapper.deleteByPrimaryKey(pkid);
		 manageService.deleteByChargeCode(pkid);

		return ApiResponse.success();
	}

	/**
	 * 获取用户费用信息
	 * @param classType        班型
	 * @param chargeType		费用类型(0000 学费)
	 * @param jgdm				报名点(机构代码)
	 * @param carType			报名车型
	 * @return
	 */
	@Override
	public  ChargeItemManagement getUserCharge(String classType, String chargeType, String jgdm, String carType) {
		String chargeCode=chargeType+"-"+classType;
		List<ChargeItemManagement> list=baseMapper.getUserCharge(chargeCode,jgdm,carType);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

    @Override
    public ApiResponse<List<ChargeItemManagement>> getChargeName(String type) {
        SimpleCondition condition = new SimpleCondition(ChargeItemManagement.class);
        if(StringUtils.equals(type,"1")) {
        	condition.eq(ChargeItemManagement.InnerColumn.chargeCode,FeeType.OTHER_IN);
//			condition.and().andCondition(" charge_code = '4999'");
		}else if(StringUtils.equals(type,"2")){
        	condition.eq(ChargeItemManagement.InnerColumn.chargeCode, FeeType.OTHER_OUT);
		}
		List<ChargeItemManagement> managements = findByCondition(condition);
		return ApiResponse.success(managements);
    }

	@Override
	public ApiResponse<Map<String, List<KeyValue>>> getJgBx(String jgdm) {

		SimpleCondition condition = new SimpleCondition(ChargeInstitutionManage.class);
		condition.eq(ChargeInstitutionManage.InnerColumn.jgdm,jgdm);
		List<ChargeInstitutionManage> institutionManageList = manageService.findByCondition(condition);
		List<String> list = institutionManageList.stream().map(ChargeInstitutionManage::getChargeId).collect(Collectors.toList());
		SimpleCondition simpleCondition = new SimpleCondition(ChargeItemManagement.class);
		simpleCondition.in(ChargeItemManagement.InnerColumn.id,list);
		simpleCondition.startWith(ChargeItemManagement.InnerColumn.chargeCode,FeeType.SIGN_UP);
		List<ChargeItemManagement> managementList = findByCondition(simpleCondition);
		Map<String,List<KeyValue>> map = new HashMap<>();
		List<String> chargeNames = managementList.stream().map(ChargeItemManagement::getChargeName).collect(Collectors.toList()).stream().map(s -> s.split("-")[1]).distinct().collect(Collectors.toList());
		List<KeyValue> keyValues = new ArrayList<>();
		List<SysZdxm> zdclk1002 = zdxmService.findEq(SysZdxm.InnerColumn.zdlmdm, "ZDCLK1002");
		if(CollectionUtils.isNotEmpty(zdclk1002)){
			Map<String, String> collect = zdclk1002.stream().collect(Collectors.toMap(SysZdxm::getZdmc, p -> p.getZddm()));

			for (String chargeName : chargeNames) {
				String dm = collect.get(chargeName);
				KeyValue eChart = new KeyValue(dm,chargeName);
				keyValues.add(eChart);
			}
			keyValues.sort(Comparator.comparing(KeyValue::getKey));
		}

		map.put("classType",keyValues);
		List<String> carTypes = managementList.stream().map(ChargeItemManagement::getCarType).map(s -> Arrays.asList(s.split(","))).reduce(new ArrayList<>(), (all, item) -> {
			all.addAll(item);
			return all;
		}).stream().distinct().collect(Collectors.toList());
		List<KeyValue> values = new ArrayList<>();

		for(String s:carTypes){
			KeyValue keyValue = new KeyValue(s,s);
			values.add(keyValue);
		}

		map.put("carType",values);
		return ApiResponse.success(map);
	}

}