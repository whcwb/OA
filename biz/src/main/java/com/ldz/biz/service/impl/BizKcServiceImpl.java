package com.ldz.biz.service.impl;

import com.github.pagehelper.PageInfo;
import com.ldz.biz.mapper.BizKcMapper;
import com.ldz.biz.model.BizCk;
import com.ldz.biz.model.BizKc;
import com.ldz.biz.model.BizRk;
import com.ldz.biz.model.Zgjbxx;
import com.ldz.biz.service.BizCkService;
import com.ldz.biz.service.BizKcService;
import com.ldz.biz.service.BizRkService;
import com.ldz.biz.service.ZgjbxxService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.sys.model.SysJg;
import com.ldz.sys.model.SysYh;
import com.ldz.sys.service.JgService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Service
public class BizKcServiceImpl extends BaseServiceImpl<BizKc, String> implements BizKcService {

	@Autowired
	private BizKcMapper baseMapper;
	@Autowired
	private BizRkService bizRkService;
	@Autowired
	private ZgjbxxService zgjbxxService;
	@Autowired
	private JgService jgService;
	@Autowired
	private BizCkService bizCkService;


	@Override
	protected Mapper<BizKc> getBaseMapper() {
		return baseMapper;
	}

	public boolean fillPagerCondition(LimitedCondition condition){

		return true;
	}

	public boolean fillQueryCondition(LimitedCondition condition) {

		return true;
	}

	public void afterPager(PageInfo<BizKc> resultPage) {
		if(CollectionUtils.isEmpty(resultPage.getList())){
			return;
		}
		List<BizKc> list = resultPage.getList();
		list.forEach(bizKc -> {
			SimpleCondition condition = new SimpleCondition(BizRk.class);
			condition.eq(BizRk.InnerColumn.kcId,bizKc.getId());
			condition.setOrderByClause( " cjsj desc" );
			List<BizRk> rks = bizRkService.findByCondition(condition);
			bizKc.setBizRks(rks);

			SimpleCondition simpleCondition = new SimpleCondition(BizCk.class);
			simpleCondition.eq(BizCk.InnerColumn.kcId,bizKc.getId());
			condition.setOrderByClause( " cjsj desc" );
			List<BizCk> cks = bizCkService.findByCondition(simpleCondition);
			bizKc.setBizCks(cks);


		});
	}

	public void afterQuery(List<BizKc> list) {
		if(CollectionUtils.isEmpty(list)){
			return;
		}
		list.forEach(bizKc -> {
			SimpleCondition condition = new SimpleCondition(BizRk.class);
			condition.eq(BizRk.InnerColumn.kcId,bizKc.getId());
			condition.setOrderByClause( " cjsj desc" );
			List<BizRk> rks = bizRkService.findByCondition(condition);
			bizKc.setBizRks(rks);

			SimpleCondition simpleCondition = new SimpleCondition(BizCk.class);
			simpleCondition.eq(BizCk.InnerColumn.kcId,bizKc.getId());
			condition.setOrderByClause( " cjsj desc" );
			List<BizCk> cks = bizCkService.findByCondition(simpleCondition);
			bizKc.setBizCks(cks);
		});
	}


    @Override
    public ApiResponse<String> saveEntity(BizKc entity) {
		SysYh currentUser = getCurrentUser();
		entity.setId(genId());
		entity.setZt("10");
		entity.setCjr(currentUser.getZh()+"-" + currentUser.getXm());
		entity.setCjsj(DateUtils.getNowTime());
		entity.setXgr(currentUser.getZh()+"-" + currentUser.getXm());
		entity.setXgSj(DateUtils.getNowTime());
        save(entity);

        // 保存入库记录
		BizRk bizRk = new BizRk();
		bizRk.setId(genId());
		bizRk.setCjr(currentUser.getZh()+"-" + currentUser.getXm());
		bizRk.setCjsj(DateUtils.getNowTime());
		bizRk.setKcId(entity.getId());
		bizRk.setRkSl(entity.getKcSl());
		bizRkService.save(bizRk);
		return ApiResponse.success();
    }

	@Override
	public ApiResponse<String> add(String id, Integer sl,String bz,float rkDj) {
		SysYh sysYh = getCurrentUser();
		BizKc bizKc = findById(id);
		RuntimeCheck.ifNull(bizKc, "该类库存不存在,请联系管理员");
		bizKc.setKcSl(bizKc.getKcSl() + sl);
		bizKc.setXgr(sysYh.getZh()+"-"+sysYh.getXm());
		bizKc.setXgSj(DateUtils.getNowTime());
		update(bizKc);

		String today = DateUtils.getToday("yyyy-MM-dd");
		SimpleCondition pcCondition = new SimpleCondition(BizRk.class);
		pcCondition.startWith(BizRk.InnerColumn.pc,today);
		pcCondition.setOrderByClause(" pc desc ");
		List<BizRk> rks = bizRkService.findByCondition(pcCondition);
		String pcBh = "";
		if(CollectionUtils.isNotEmpty(rks)){
			BizRk bizRk = rks.get(0);
			String pc = bizRk.getPc();
			String[] split = pc.split("-");
			int i = Integer.parseInt(split[3]) + 1;
			if(i < 10){
				pcBh =today + "-000" + i;
			}else if(i < 100){
				pcBh =today + "-00" + i;
			}else if(i<1000){
				pcBh =today + "-0" + i;
			}else{
				pcBh =today + "-" + i;
			}

		}else{
			pcBh = today + "-0001";
		}

		// 保存入库记录
		BizRk bizRk = new BizRk();
		bizRk.setId(genId());
		bizRk.setCjr(sysYh.getZh()+"-" + sysYh.getXm());
		bizRk.setCjsj(DateUtils.getNowTime());
		bizRk.setKcId(bizKc.getId());
		bizRk.setRkSl(sl);
		bizRk.setBz(bz);
		bizRk.setRkDj(rkDj);
		bizRk.setPc(pcBh);
		bizRkService.save(bizRk);


		return ApiResponse.success();
	}

	@Override
	public ApiResponse<String> handOut(String id, Integer sl, String zgId, String bz, String jgdm) {
		SysYh sysYh = getCurrentUser();
		BizKc bizKc = findById(id);
		Zgjbxx zgjbxx = zgjbxxService.findById(zgId);
		RuntimeCheck.ifNull(zgjbxx, "员工信息未找到,请联系管理员");
		RuntimeCheck.ifNull(bizKc, "该类库存不存在,请先添加该类库存");
		RuntimeCheck.ifTrue(bizKc.getKcSl() - sl < 0,"当前物品库存不足,请联系管理员");

		bizKc.setXgSj(DateUtils.getNowTime());
		bizKc.setXgr(sysYh.getZh()+"-" + sysYh.getXm());
		bizKc.setKcSl(bizKc.getKcSl() - sl);
		update(bizKc);

		// 保存出库记录
		BizCk bizCk = new BizCk();
		SysJg jg = jgService.findByOrgCode(jgdm);
		bizCk.setId(genId());
		bizCk.setCjr(sysYh.getZh()+"-" + sysYh.getXm());
		bizCk.setCjsj(DateUtils.getNowTime());
		bizCk.setKcId(bizKc.getId());
		bizCk.setLqSl(sl);
		bizCk.setLqr(zgjbxx.getXm() +"-" + zgjbxx.getId());
		bizCk.setBz(bz);
		bizCk.setJgdm(jgdm);
		bizCk.setJgmc(jg.getJgmc());
		bizCkService.save(bizCk);

		return ApiResponse.success();
	}

	@Override
	public void removeEntity(String id) {

		BizKc kc = findById(id);
		kc.setZt("20");
		update(kc);
	}

    @Override
    public ApiResponse<String> saveList(List<BizKc> kcs) {

		SysYh currentUser = getCurrentUser();
		String today = DateUtils.getToday("yyyy-MM-dd");
		SimpleCondition pcCondition = new SimpleCondition(BizRk.class);
		pcCondition.startWith(BizRk.InnerColumn.pc,today);
		pcCondition.setOrderByClause(" pc desc ");
		List<BizRk> rks = bizRkService.findByCondition(pcCondition);
		String pcBh = "";
		if(CollectionUtils.isNotEmpty(rks)){
			BizRk bizRk = rks.get(0);
			String pc = bizRk.getPc();
			String[] split = pc.split("-");
			int i = Integer.parseInt(split[3]) + 1;
			if(i < 10){
				pcBh =today + "-000" + i;
			}else if(i < 100){
				pcBh =today + "-00" + i;
			}else if(i<1000){
				pcBh =today + "-0" + i;
			}else{
				pcBh =today + "-" + i;
			}

		}else{
			pcBh = today + "-0001";
		}
		for (BizKc bizKc : kcs) {
			String kcMc = bizKc.getKcMc();
			RuntimeCheck.ifBlank(kcMc, "商品名称不能有空的");
			RuntimeCheck.ifBlank(bizKc.getKcLx(), "商品规格不能是空的");
			SimpleCondition condition = new SimpleCondition(BizKc.class);
			condition.eq(BizKc.InnerColumn.kcMc, kcMc);
			condition.eq(BizKc.InnerColumn.kcLx,bizKc.getKcLx());
			List<BizKc> byCondition = findByCondition(condition);
			if (CollectionUtils.isNotEmpty(byCondition)) {
				BizKc kc = byCondition.get(0);
				kc.setKcSl(kc.getKcSl() + bizKc.getKcSl());
				kc.setXgSj(DateUtils.getNowTime());
				kc.setXgr(currentUser.getZh() + "-" + currentUser.getXm());
				update(kc);

				// 保存入库记录
				BizRk bizRk = new BizRk();
				bizRk.setId(genId());
				bizRk.setCjr(currentUser.getZh() + "-" + currentUser.getXm());
				bizRk.setCjsj(DateUtils.getNowTime());
				bizRk.setKcId(kc.getId());
				bizRk.setRkSl(bizKc.getKcSl());
				bizRk.setPc(pcBh);
				bizRk.setRkDj(bizKc.getRkDj());
				bizRkService.save(bizRk);

			}else {

				bizKc.setId(genId());
				bizKc.setCjsj(DateUtils.getNowTime());
				bizKc.setCjr(currentUser.getZh()+"-"+currentUser.getXm());
				save(bizKc);

				// 保存入库记录
				BizRk bizRk = new BizRk();
				bizRk.setId(genId());
				bizRk.setCjr(currentUser.getZh() + "-" + currentUser.getXm());
				bizRk.setCjsj(DateUtils.getNowTime());
				bizRk.setKcId(bizKc.getId());
				bizRk.setRkSl(bizKc.getKcSl());
				bizRk.setPc(pcBh);
				bizRk.setRkDj(bizKc.getRkDj());
				bizRkService.save(bizRk);


			}
		}


		return ApiResponse.success();
    }

    @Override
    public ApiResponse<String> appendPc(List<BizKc> kcs,String pc) {
		if(StringUtils.isBlank(pc)){
			return ApiResponse.fail("请选择批次");
		}
		SysYh currentUser = getCurrentUser();
		for (BizKc bizKc : kcs) {
			String kcMc = bizKc.getKcMc();
			SimpleCondition condition = new SimpleCondition(BizKc.class);
			condition.eq(BizKc.InnerColumn.kcMc, kcMc);
			condition.eq(BizKc.InnerColumn.kcLx,bizKc.getKcLx());
			List<BizKc> byCondition = findByCondition(condition);
			if (CollectionUtils.isNotEmpty(byCondition)) {
				BizKc kc = byCondition.get(0);
				kc.setKcSl(kc.getKcSl() + bizKc.getKcSl());
				kc.setXgSj(DateUtils.getNowTime());
				kc.setXgr(currentUser.getZh() + "-" + currentUser.getXm());
				update(kc);

				// 保存入库记录
				BizRk bizRk = new BizRk();
				bizRk.setId(genId());
				bizRk.setCjr(currentUser.getZh() + "-" + currentUser.getXm());
				bizRk.setCjsj(DateUtils.getNowTime());
				bizRk.setKcId(kc.getId());
				bizRk.setRkSl(bizKc.getKcSl());
				bizRk.setPc(pc);
				bizRk.setRkDj(bizKc.getRkDj());
				bizRkService.save(bizRk);

			}else {
				BizKc kc = new BizKc();
				kc.setId(genId());
				kc.setKcSl(bizKc.getKcSl());
				kc.setCjsj(DateUtils.getNowTime());
				kc.setCjr(currentUser.getZh()+"-"+currentUser.getXm());
				kc.setKcMc(kcMc);
				save(kc);

				// 保存入库记录
				BizRk bizRk = new BizRk();
				bizRk.setId(genId());
				bizRk.setCjr(currentUser.getZh() + "-" + currentUser.getXm());
				bizRk.setCjsj(DateUtils.getNowTime());
				bizRk.setKcId(kc.getId());
				bizRk.setRkSl(bizKc.getKcSl());
				bizRk.setPc(pc);
				bizRk.setRkDj(bizKc.getRkDj());
				bizRkService.save(bizRk);


			}
		}

		return ApiResponse.success();
    }
}