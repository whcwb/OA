package com.ldz.biz.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ldz.biz.mapper.BizLcJlMapper;
import com.ldz.biz.model.*;
import com.ldz.biz.service.*;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.sys.model.SysYh;
import com.ldz.sys.model.SysZdxm;
import com.ldz.sys.service.ZdxmService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.commonUtil.ExcelUtil;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import tk.mybatis.mapper.common.Mapper;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BizLcJlServiceImpl extends BaseServiceImpl<BizLcJl, String> implements BizLcJlService {

    @Autowired
    private BizLcJlMapper baseMapper;
    @Autowired
    private BizLcWxjlService wxjlService;
    @Autowired
    private BizLcClService clService;
    @Autowired
    private ZgjbxxService zgjbxxService;
    @Autowired
    private BizLcJlXyService xyService;
    @Autowired
    private ZdxmService zdxmService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BizLcFdService fdService;

    @Override
    protected Mapper<BizLcJl> getBaseMapper() {
        return baseMapper;
    }

    @Override
    public void afterQuery(List<BizLcJl> result) {
        if (CollectionUtils.isEmpty(result)) {
            return;
        }
        result.stream().forEach(bizLcJl -> {
            SimpleCondition condition = new SimpleCondition(BizLcJlXy.class);
            condition.eq(BizLcJlXy.InnerColumn.lcJlId, bizLcJl.getId());
            List<BizLcJlXy> xies = xyService.findByCondition(condition);
            bizLcJl.setXyList(xies);
        });
    }

    @Override
    public void afterPager(PageInfo<BizLcJl> pageInfo) {
        if (CollectionUtils.isEmpty(pageInfo.getList())) {
            return;
        }
        Set<String> jlIds = pageInfo.getList().stream().map(BizLcJl::getJlId).collect(Collectors.toSet());
        List<BizLcWxjl> coachs = wxjlService.findIn(BizLcWxjl.InnerColumn.id,jlIds);
        Map<String,BizLcWxjl> coachMap = coachs.stream().collect(Collectors.toMap(BizLcWxjl::getId,p->p));
        pageInfo.getList().stream().forEach(bizLcJl -> {
            SimpleCondition condition = new SimpleCondition(BizLcJlXy.class);
            condition.eq(BizLcJlXy.InnerColumn.lcJlId, bizLcJl.getId());
            List<BizLcJlXy> xies = xyService.findByCondition(condition);
            bizLcJl.setXyList(xies);
            if(bizLcJl.getLcLx().equals("10")){
                SimpleCondition djcondition = new SimpleCondition(SysZdxm.class);
                djcondition.eq(SysZdxm.InnerColumn.zdlmdm, "ZDCLK1045");
                if (bizLcJl.getLcKm().equals("2")) {
                    djcondition.eq(SysZdxm.InnerColumn.zddm, "k2" + bizLcJl.getJlCx());
                } else {

                    djcondition.eq(SysZdxm.InnerColumn.zddm, "k3" + bizLcJl.getJlCx());
                }
                List<SysZdxm> items = zdxmService.findByCondition(djcondition);
                if(CollectionUtils.isNotEmpty(items)){
                    SysZdxm management = items.get(0);
                    bizLcJl.setLcDj(Float.parseFloat(StringUtils.isBlank(management.getBy4()) ? "0" : management.getBy4()));
                }

            }
            if (StringUtils.isNotEmpty(bizLcJl.getJlId())){
                BizLcWxjl coach = coachMap.get(bizLcJl.getJlId());
                if (coach != null){
                    bizLcJl.setJlDh(coach.getJlLxdh());
                }
            }
        });
    }


    @Override
    public ApiResponse<String> saveEntity(BizLcJl entity) {
        SysYh currentUser = getCurrentUser();
        //  判断所有必填字段
        RuntimeCheck.ifBlank(entity.getJlId(), "请选择教练");
        // 本校外校没有了
        entity.setJlLx("10");
        RuntimeCheck.ifBlank(entity.getCardNo(), "请刷卡");

        SimpleCondition jlCondition = new SimpleCondition(BizLcJl.class);
        jlCondition.eq(BizLcJl.InnerColumn.cardNo, entity.getCardNo());
        jlCondition.and().andCondition(" jssj is null or jssj = ''");
        jlCondition.setOrderByClause(" kssj desc ");
        List<BizLcJl> jls = findByCondition(jlCondition);
        if (CollectionUtils.isNotEmpty(jls)) {
            return ApiResponse.fail("当前卡号已在训练中,请勿重复刷卡");
        }

        // 先获取绑定车辆
        List<BizLcCl> lcCls = clService.findEq(BizLcCl.InnerColumn.cardNo, entity.getCardNo());
        BizLcCl lcCl = null;
        if (CollectionUtils.isNotEmpty(lcCls)) {
            lcCl = lcCls.get(0);
        } else {
            RuntimeCheck.ifBlank(entity.getLcClId(), "此卡未绑定车辆，请手动选择车辆");
            // 练车车辆的详细信息
            lcCl = clService.findById(entity.getLcClId());

        }
        RuntimeCheck.ifTrue(lcCl.getClZt().equalsIgnoreCase("01"), lcCl.getClBh() + "号车辆已经在训练中");
        if(StringUtils.isNotBlank(entity.getKm())){
            RuntimeCheck.ifFalse(lcCl.getClKm().equalsIgnoreCase(entity.getKm()), "此卡号已绑定科目"+(lcCl.getClKm().equals("2")?"二":"三")+"-" + lcCl.getClBh() + "车,不能在科目"+(entity.getKm().equals("2")?"二":"三")+"窗口发车");
        }
        entity.setLcClId(lcCl.getId());


        entity.setLcLx(StringUtils.isBlank(entity.getLcLx()) ? "00" : entity.getLcLx());


        LocalDateTime now = LocalDateTime.now();
        String nowTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        entity.setCjsj(nowTime);
        entity.setKssj(nowTime);
        entity.setCjr(currentUser.getZh() + "-" + currentUser.getXm());
        entity.setLcYj(entity.getLcYj() == null ? 0 : entity.getLcYj());


        BizLcWxjl wxjl = wxjlService.findById(entity.getJlId());
        entity.setJlXm(wxjl.getJlXm());
        entity.setJlJx(wxjl.getJlJx());


        entity.setClBh(lcCl.getClBh());
        if (StringUtils.equals(lcCl.getClKm(), "3")) {
            RuntimeCheck.ifBlank(entity.getZgId(), "请选择安全员");
            SimpleCondition aqyCondition = new SimpleCondition(BizLcJl.class);
            aqyCondition.eq(BizLcJl.InnerColumn.zgId, entity.getZgId());
            aqyCondition.and().andCondition(" jssj is null or jssj = ''");
            List<BizLcJl> lcJls = findByCondition(aqyCondition);
            RuntimeCheck.ifTrue(CollectionUtils.isNotEmpty(lcJls), "当前安全员正在训练中 , 请选择其他安全员");
            Zgjbxx zgjbxx = zgjbxxService.findById(entity.getZgId());
            if (ObjectUtils.isEmpty(zgjbxx)) {
                return ApiResponse.fail("没有找到该安全员的信息");
            }
            entity.setZgXm(zgjbxx.getXm());
            lcCl.setZgXm(zgjbxx.getXm());
        }

        entity.setLcKm(lcCl.getClKm());
        entity.setJlCx(lcCl.getClCx());
        // 安全员信息
        entity.setKc(lcCl.getClKc());
        entity.setFdZt("30");
        entity.setId(genId());

        // 修改车辆为使用中
        lcCl.setClZt("01");
        lcCl.setXgr(currentUser.getZh() + "-" + currentUser.getXm());
        lcCl.setXgsj(DateUtils.getNowTime());
        lcCl.setZgId(entity.getZgId());

        clService.update(lcCl);

        // 查询是否有关联到学员
        if (StringUtils.isNotBlank(entity.getXyIds())) {
            List<BizLcJlXy> xies = new ArrayList<>();
            // 关联到学员
            List<String> list = Arrays.asList(entity.getXyIds().split(","));

            String xyDh = getRequestParamterAsString("xyDh");
            List<String> xyDhs = new ArrayList<>();
            if (StringUtils.isNotBlank(xyDh)) {
                xyDhs = Arrays.asList(xyDh.split(","));
            }

            for (int i = 0; i < list.size(); i++) {
                String s = list.get(i);
                if (StringUtils.isBlank(s)) {
                    continue;
                }
                BizLcJlXy jlXy = new BizLcJlXy();
                if (CollectionUtils.isNotEmpty(xyDhs)) {
                    jlXy.setXyDh(xyDhs.get(i));
                }
                jlXy.setXyJx(wxjl.getJlJx());
                jlXy.setId(genId());
                jlXy.setXyXm(s);
                jlXy.setLcJlId(entity.getId());
                jlXy.setCjr(currentUser.getZh() + "-" + currentUser.getXm());
                jlXy.setCjsj(DateUtils.getNowTime());
                jlXy.setId(genId());
                xyService.save(jlXy);
            }
            if (CollectionUtils.isNotEmpty(xies)) {
                if (entity.getXySl() == null || entity.getXySl() == 0) {
                    entity.setXySl(xies.size());
                }
            }

        }
        save(entity);
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<BizLcJl> updateJssj(String id, String km) throws ParseException {
        SysYh yh = getCurrentUser();
        SimpleCondition jlCondition = new SimpleCondition(BizLcJl.class);
//        jlCondition.eq(BizLcJl.InnerColumn.cardNo,id);
        jlCondition.and().andCondition(" card_no = '" + id + "' or id = '" + id + "'");
        jlCondition.and().andCondition(" jssj is null or jssj = ''");

        jlCondition.setOrderByClause(" kssj desc ");
        List<BizLcJl> jls = findByCondition(jlCondition);
        RuntimeCheck.ifTrue(CollectionUtils.isEmpty(jls), "当前卡号没有练车记录");

        BizLcJl lcJl = jls.get(0);

        RuntimeCheck.ifTrue(StringUtils.equals(km, "2") && !StringUtils.equals(km, lcJl.getLcKm()), "请前往科目二窗口还车");
        RuntimeCheck.ifTrue(StringUtils.equals(km, "3") && !StringUtils.equals(km, lcJl.getLcKm()), "请前往科目三窗口还车");

        String s = DateUtils.getNowTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date end = dateFormat.parse(s);
        String kssj = lcJl.getKssj();
        Date start = dateFormat.parse(kssj);

        // 计算实际时长  (所有车辆免费前五分钟)
        int lcSc = (int) ((end.getTime() - start.getTime()) / (1000 * 60) - 5) < 0 ? 0 : (int) ((end.getTime() - start.getTime()) / (1000 * 60) - 5);

        // 计算练车费用
        SimpleCondition condition = new SimpleCondition(SysZdxm.class);
        condition.eq(SysZdxm.InnerColumn.zdlmdm, "ZDCLK1045");
        if (lcJl.getLcKm().equals("2")) {
            condition.eq(SysZdxm.InnerColumn.zddm, "k2" + lcJl.getJlCx());
        } else {

            condition.eq(SysZdxm.InnerColumn.zddm, "k3" + lcJl.getJlCx());
        }
        List<SysZdxm> items = zdxmService.findByCondition(condition);

        RuntimeCheck.ifTrue(CollectionUtils.isEmpty(items), "此车型没有设置练车费,请先设置该车型的练车费");
        SysZdxm management = items.get(0);

        // 每小时的费用

        if (StringUtils.equals(lcJl.getLcLx(), "00")) {
            String hour = management.getZdmc();
            String by3 = management.getBy3();
            int h = lcSc / 60;
            int m = lcSc % 60;
            // 小时能除尽的按小时计费
            float hv = Float.parseFloat(hour) * h;
            // 不能除尽的按分钟算
            float mv = m * Float.parseFloat(by3);
            // 总费用
            int v = Math.round(hv + mv);
            lcJl.setLcDj(Float.parseFloat(management.getZdmc()));
            lcJl.setLcFy(v);
        } else if(StringUtils.equals(lcJl.getLcLx(), "10")){
            lcJl.setLcFy(Integer.parseInt(StringUtils.isBlank(management.getBy4()) ? "0" : management.getBy4()));
            lcJl.setLcDj(Float.parseFloat(StringUtils.isBlank(management.getBy4()) ? "0" : management.getBy4()));
        } else {
            SimpleCondition lmCondition = new SimpleCondition(SysZdxm.class);
            lmCondition.eq(SysZdxm.InnerColumn.zdlmdm,"ZDCLK1048");
            lmCondition.eq(SysZdxm.InnerColumn.zddm,lcJl.getLcLx());
            List<SysZdxm> zdxms = zdxmService.findByCondition(lmCondition);
            RuntimeCheck.ifEmpty(zdxms, "此练车项没有设置练车费用， 请联系管理员设置练车费用");
            String zdmc = zdxms.get(0).getZdmc();
            String[] split = zdmc.split("-");
            RuntimeCheck.ifTrue(split.length < 3, "此练车项设置练车费用有误， 请重新设置练车费用");
            int anInt = Integer.parseInt(split[2]);
            lcJl.setLcFy(anInt);
            lcJl.setLcDj(anInt);
        }

        lcJl.setSc((int) (end.getTime() - start.getTime()) / (1000 * 60));
        lcJl.setJssj(s);
        lcJl.setXgr(yh.getZh() + "-" + yh.getXm());
        lcJl.setXgsj(DateUtils.getNowTime());
        if(StringUtils.equals(lcJl.getLcLx(),"00") && !StringUtils.equals(lcJl.getJlXm(), "自学")){
            lcJl.setFdZt("00");
        }else {
            lcJl.setFdZt("40");
        }
        update(lcJl);

        // 更新这辆车的状态
        BizLcCl lcCl = clService.findById(lcJl.getLcClId());
        lcCl.setClZt("00");
        lcCl.setZgId(null);
        lcCl.setZgXm(null);
        lcCl.setXgr(yh.getZh() + "-" + yh.getXm());
        lcCl.setXgsj(DateUtils.getNowTime());
        clService.update(lcCl);
        return ApiResponse.success(lcJl);

    }

    @Override
    public List<Map<String, Object>> drivingSchoolStatistics() {
        Map<String, Object> ret = new HashMap<>();
        String lcKm = getRequestParamterAsString("lcKm");
        RuntimeCheck.ifBlank(lcKm, "请选择科目");
        String tjsj = getRequestParamterAsString("tjsj");
        if (StringUtils.isEmpty(tjsj)) tjsj = DateUtils.getToday() + " 00:00:00," + DateUtils.getToday() + " 23:59:59";
        String[] sj = tjsj.split(",");
        String kssj = sj[0];
        String jssj = sj[1];
        String sql = "SELECT jl_jx,sum(sc) as sc,sum(lc_fy) as fy  from biz_lc_jl where 1=1 ";
        sql += " and kssj >= '" + kssj + "' and jssj <= '" + jssj + "'";
        sql += " and lc_km ='" + lcKm + "'";
        sql += " GROUP BY jl_jx ";
        System.out.println(sql);
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        List<Map<String, Object>> retList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            for (Map m : list) {
                Map<String, Object> retMap = new HashMap<>();
                retMap.put("jlJx", m.get("jl_jx"));
                String sc = m.get("sc").toString();
                long l = 0;
                try {
                    l = Long.parseLong(sc);
                } catch (Exception e) {
                }
                if (l / 60 == 0) {
                    retMap.put("lcSc", l + "分");
                } else {
                    retMap.put("lcSc", (l / 60) + "时" + (l % 60) + "分");
                }
                retMap.put("lcFy", m.get("fy"));
                retList.add(retMap);
            }
        }
        return retList;
    }

    @Override
    public List<Map<String, Object>> statistics() {
        String cjsjIn = getRequestParamterAsString("cjsjIn");
        String clId = getRequestParamterAsString("clId");
        if (StringUtils.isEmpty(cjsjIn))
            cjsjIn = DateUtils.getToday() + " 00:00:00," + DateUtils.getToday() + " 23:59:59";
        String[] sj = cjsjIn.split(",");
        String kssj = sj[0];
        String jssj = sj[1];
        String sql = "select lc_cl_id,cl_bh,sum(xy_sl) sum_xysl,sum(sc) sum_sc,sum(lc_fy) sum_lcfy from biz_lc_jl where 1=1 ";
        if (StringUtils.isNotEmpty(clId)) {
            sql += " and lc_cl_id = '" + clId + "'";
        }
        sql += " and cjsj >= '" + kssj + "' and cjsj <= '" + jssj + "'";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }

    @Override
    public ApiResponse<BizLcJl> getJl(String cardNo) {
        SimpleCondition jlCondition = new SimpleCondition(BizLcJl.class);
        jlCondition.eq(BizLcJl.InnerColumn.cardNo, cardNo);
        jlCondition.and().andCondition(" jssj is null or jssj = ''");
        jlCondition.setOrderByClause(" kssj desc ");
        List<BizLcJl> jls = findByCondition(jlCondition);
        RuntimeCheck.ifTrue(CollectionUtils.isEmpty(jls), "当前卡号没有练车记录");
        BizLcJl lcJl = jls.get(0);
        String lcClId = lcJl.getLcClId();
        BizLcCl cl = clService.findById(lcClId);
        lcJl.setLcCl(cl);

        return ApiResponse.success(lcJl);
    }

    @Override
    public ApiResponse<String> saveJl(BizLcJl entity, String appoint) {
        SysYh currentUser = getCurrentUser();
        RuntimeCheck.ifBlank(appoint, "请填写预约信息");
        String[] split = appoint.split("-");
        RuntimeCheck.ifTrue(split.length <= 0, "请选择预约车型");
        //  判断所有必填字段
        RuntimeCheck.ifBlank(entity.getJlId(), "请选择教练");
        for (String s : split) {
            List<String> appoints = Arrays.asList(s.split(",", -1));
            RuntimeCheck.ifTrue(appoints.size() < 2, "请完整填写预约信息");
            String num = appoints.get(1);
            String cx = appoints.get(0);
            String xySl = "0";
            if (appoints.size() == 3 && StringUtils.isNotBlank(appoints.get(2))) {
                xySl = appoints.get(2);
            }
            if (StringUtils.isBlank(cx)) {
                continue;
            }
            if (StringUtils.isBlank(num) || StringUtils.equals(num,"null")) {
                continue;
            } else {
                int anInt = Integer.parseInt(num);
                for (int i = 0; i < anInt; i++) {
                    saveAppoint(entity, currentUser, cx, xySl);
                }
            }
        }
        entity.setJlLx("10");

        return ApiResponse.success();
    }

    private void saveAppoint(BizLcJl entity, SysYh currentUser, String cx, String xySl) {
        LocalDateTime now = LocalDateTime.now();
        String nowTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        entity.setId(genId());
        entity.setZgId(null);
        entity.setCjsj(nowTime);
        entity.setCjr(currentUser.getZh() + "-" + currentUser.getXm());
        entity.setLcLx(StringUtils.isBlank(entity.getLcLx()) ? "00" : entity.getLcLx());
        // 教练信息
        BizLcWxjl wxjl = wxjlService.findById(entity.getJlId());
        entity.setJlXm(wxjl.getJlXm());
        entity.setJlJx(wxjl.getJlJx());
        entity.setJlCx(cx);
        entity.setXySl(Integer.parseInt(xySl));
        // 查询是否有关联到学员
        if (StringUtils.isNotBlank(entity.getXyIds())) {
            List<BizLcJlXy> xies = new ArrayList<>();
            // 关联l到学员
            List<String> list = Arrays.asList(entity.getXyIds().split(","));

            String xyDh = getRequestParamterAsString("xyDh");
            List<String> xyDhs = new ArrayList<>();
            if (StringUtils.isNotBlank(xyDh)) {
                xyDhs = Arrays.asList(xyDh.split(","));
            }

            for (int i = 0; i < list.size(); i++) {
                String s = list.get(i);
                if (StringUtils.isBlank(s)) {
                    continue;
                }
                BizLcJlXy jlXy = new BizLcJlXy();
                if (CollectionUtils.isNotEmpty(xyDhs)) {
                    jlXy.setXyDh(xyDhs.get(i));
                }
                jlXy.setXyJx(wxjl.getJlJx());
                jlXy.setId(genId());
                jlXy.setXyXm(s);
                jlXy.setLcJlId(entity.getId());
                jlXy.setCjr(currentUser.getZh() + "-" + currentUser.getXm());
                jlXy.setCjsj(DateUtils.getNowTime());
                jlXy.setId(genId());
                xyService.save(jlXy);
            }

            if (CollectionUtils.isNotEmpty(xies)) {
                if (entity.getXySl() == null || entity.getXySl() == 0) {
                    entity.setXySl(xies.size());
                }
            }
        }
        entity.setFdZt("30");
        save(entity);
    }


    /**
     * 开始练车的备用接口
     */
    @Override
    public ApiResponse<String> kslc(BizLcJl entity) {
        SysYh currentUser = getCurrentUser();
        BizLcJl lcJl = findById(entity.getId());
        RuntimeCheck.ifNull(lcJl, "未找到预约记录");
        //  判断所有必填字段
        RuntimeCheck.ifBlank(entity.getJlId(), "请选择教练");
        RuntimeCheck.ifBlank(entity.getCardNo(), "请刷卡");
        entity.setJlLx("10");

        SimpleCondition jlCondition = new SimpleCondition(BizLcJl.class);
        jlCondition.eq(BizLcJl.InnerColumn.cardNo, entity.getCardNo());
        jlCondition.and().andCondition(" jssj is null or jssj = ''");
        jlCondition.setOrderByClause(" kssj desc ");
        List<BizLcJl> jls = findByCondition(jlCondition);
        if (CollectionUtils.isNotEmpty(jls)) {
            return ApiResponse.fail("当前卡号已在训练中,请勿重复刷卡");
        }

        // 先获取绑定车辆
        List<BizLcCl> lcCls = clService.findEq(BizLcCl.InnerColumn.cardNo, entity.getCardNo());
        BizLcCl lcCl = null;
        if (CollectionUtils.isNotEmpty(lcCls)) {
            lcCl = lcCls.get(0);
        } else {
            RuntimeCheck.ifBlank(entity.getLcClId(), "此卡未绑定车辆，请手动选择车辆");
            // 练车车辆的详细信息
            lcCl = clService.findById(entity.getLcClId());

        }
        RuntimeCheck.ifTrue(lcCl.getClZt().equalsIgnoreCase("01"), "当前车辆已经在训练中");
        if(StringUtils.isNotBlank(entity.getKm())){
            RuntimeCheck.ifFalse(lcCl.getClKm().equalsIgnoreCase(entity.getKm()), "此卡号已绑定科目"+(lcCl.getClKm().equals("2")?"二":"三")+" - " + lcCl.getClBh() + "车,不能在科目"+(entity.getKm().equals("2")?"二":"三")+"窗口发车");
        }
        RuntimeCheck.ifFalse(StringUtils.equals(lcCl.getClCx(), lcJl.getJlCx()), "所选车辆车型与预约车型不一致");
        entity.setClBh(lcCl.getClBh());
        entity.setLcClId(lcCl.getId());

        entity.setLcLx(StringUtils.isBlank(entity.getLcLx()) ? "00" : entity.getLcLx());


        LocalDateTime now = LocalDateTime.now();
        String nowTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        entity.setKssj(nowTime);
        entity.setLcYj(entity.getLcYj() == null ? 0 : entity.getLcYj());
        // 根据教练是本校还是外校来选择教练

        BizLcWxjl wxjl = wxjlService.findById(entity.getJlId());
        entity.setJlXm(wxjl.getJlXm());
        entity.setJlJx(wxjl.getJlJx());


        if (StringUtils.equals(lcCl.getClKm(), "3")) {
            RuntimeCheck.ifBlank(entity.getZgId(), "请选择安全员");
            SimpleCondition aqyCondition = new SimpleCondition(BizLcJl.class);
            aqyCondition.eq(BizLcJl.InnerColumn.zgId, entity.getZgId());
            aqyCondition.and().andCondition(" jssj is null or jssj = ''");
            List<BizLcJl> lcJls = findByCondition(aqyCondition);
            RuntimeCheck.ifTrue(CollectionUtils.isNotEmpty(lcJls), "当前安全员正在训练中 , 请选择其他安全员");
            Zgjbxx zgjbxx = zgjbxxService.findById(entity.getZgId());
            if (ObjectUtils.isEmpty(zgjbxx)) {
                return ApiResponse.fail("没有找到该安全员的信息");
            }
            entity.setZgXm(zgjbxx.getXm());
            lcCl.setZgXm(zgjbxx.getXm());
        }
        entity.setLcKm(lcCl.getClKm());
        entity.setJlCx(lcCl.getClCx());
        // 安全员信息
        entity.setXgsj(DateUtils.getNowTime());
        entity.setXgr(currentUser.getZh() + "-" + currentUser.getXm());
        entity.setFdZt("30");
        entity.setKc(lcCl.getClKc());
        // 修改车辆为使用中
        lcCl.setClZt("01");
        lcCl.setXgr(currentUser.getZh() + "-" + currentUser.getXm());
        lcCl.setXgsj(DateUtils.getNowTime());
        lcCl.setZgId(entity.getZgId());

        clService.update(lcCl);

        update(entity);
        return ApiResponse.success();
    }


    @Override
    public ApiResponse<String> updateFdZt(String id , int fdJe) {

        RuntimeCheck.ifBlank(id, "请选择已返点的记录");
        SysYh user = getCurrentUser();
        List<String> list = Arrays.asList(id.split(","));
        RuntimeCheck.ifEmpty(list, "请选择需要返点的记录");
        BizLcFd bizLcFd = new BizLcFd();
        bizLcFd.setFdje(fdJe);
        bizLcFd.setCjr(user.getZh() + "-" + user.getXm());
        bizLcFd.setCjsj(DateUtils.getNowTime());
        bizLcFd.setFdsl(list.size());
        bizLcFd.setId(genId());
        fdService.save(bizLcFd);
        list.forEach(s -> {
            BizLcJl lcJl = findById(s);
            lcJl.setFdZt("10");
            lcJl.setFdsj(DateUtils.getNowTime());
            lcJl.setFdr(user.getZh() + "-" + user.getXm());
            lcJl.setFdId(bizLcFd.getId());
            update(lcJl);
        });

        return ApiResponse.success();
    }

    @Override
    public ApiResponse<BizLcJl> getFdZt(String id) {
        BizLcJl lcJl = findById(id);
        RuntimeCheck.ifNull(lcJl, "未找到该记录");
        if (!StringUtils.equals(lcJl.getFdZt(), "10")) {
            lcJl.setFdZt("20");
            update(lcJl);
        }
        return ApiResponse.success(lcJl);
    }

    @Override
    public ApiResponse<List<LcJlModel>> getAllLog() {
        List<LcJlModel> list = new ArrayList<>();
        LimitedCondition condition = getQueryCondition();
       /* String cjsjGte = getRequestParamterAsString("cjsjGte");
        String cjsjLte = getRequestParamterAsString("cjsjLte");
        if(StringUtils.isBlank(cjsjGte)){
            condition.gte(BizLcJl.InnerColumn.cjsj, DateUtils.getDateStr(new Date(),"yyyy-MM-dd")+ " 00:00:00");
        }
        if(StringUtils.isBlank(cjsjLte)){
            condition.lte(BizLcJl.InnerColumn.cjsj, DateUtils.getDateStr(new Date(),"yyyy-MM-dd") + " 23:59:59");
        }*/
        /*String cjsjLike = getRequestParamterAsString("cjsjLike");
        if(StringUtils.isBlank(cjsjLike)){
            condition.like(BizLcJl.InnerColumn.cjsj,DateUtils.getDateStr(new Date(), "yyyy-MM-dd"));
        }*/
        condition.and().andIsNotNull(BizLcJl.InnerColumn.lcFy.name());
        List<BizLcJl> jls = findByCondition(condition);
        if (CollectionUtils.isNotEmpty(jls)) {
            // 根据安全员分组
            Map<String, List<BizLcJl>> collect = jls.stream().filter(bizLcJl -> StringUtils.isNotBlank(bizLcJl.getJssj())).collect(Collectors.groupingBy(BizLcJl::getZgId));
            for (String s : collect.keySet()) {
                List<BizLcJl> bizLcJls = collect.get(s);
                Map<String, List<BizLcJl>> listMap = bizLcJls.stream().collect(Collectors.groupingBy(BizLcJl::getClBh));
                // 可能存在一个安全员多辆车的问题
                for (String clBh : listMap.keySet()) {
                    List<BizLcJl> jls1 = listMap.get(clBh);
                    jls1.sort(Comparator.comparing(BizLcJl::getKssj));
                    // 可能存在跑出大于6趟的情况
                    int i = jls1.size() / 6;
                    int k = jls1.size() % 6;
                    for(int j = 0 ; j <= i ; j++){
                        LcJlModel lcJlModel = new LcJlModel();
                        List<BizLcJl> lcJls = new ArrayList<>();
                        if(j == i){
                            if(k == 0){
                                break;
                            }
                            lcJls.addAll(jls1.subList(j*6 , j * 6 + k));
                        }else{
                            lcJls.addAll(jls1.subList(j*6,(j+1)*6));
                        }
                        lcJlModel.setClBh(clBh);
                        lcJlModel.setJls(lcJls);
                        lcJlModel.setZgXm(listMap.get(clBh).get(0).getZgXm());
                        lcJlModel.setZj(lcJls.stream().mapToInt(BizLcJl::getLcFy).sum());
                        list.add(lcJlModel);
                    }

                }
            }
        }
        return ApiResponse.success(list);
    }

    @Override
    public ApiResponse<LcJlModel> getOneLog(String zgId) {
        RuntimeCheck.ifBlank(zgId, "请选择安全员");
        // 首先查询当天所有的安全员记录
        LimitedCondition condition = getQueryCondition();
        /*String cjsjLike = getRequestParamterAsString("cjsjLike");
        if(StringUtils.isBlank(cjsjLike)){
            condition.like(BizLcJl.InnerColumn.cjsj,DateUtils.getDateStr(new Date(), "yyyy-MM-dd"));
        }*/
       /* String cjsjGte = getRequestParamterAsString("cjsjGte");
        String cjsjLte = getRequestParamterAsString("cjsjLte");
        if(StringUtils.isBlank(cjsjGte)){
            condition.gte(BizLcJl.InnerColumn.cjsj, DateUtils.getDateStr(new Date(),"yyyy-MM-dd")+ " 00:00:00");
        }
        if(StringUtils.isBlank(cjsjLte)){
            condition.lte(BizLcJl.InnerColumn.cjsj, DateUtils.getDateStr(new Date(),"yyyy-MM-dd") + " 23:59:59");
        }*/
        condition.eq(BizLcJl.InnerColumn.zgId, zgId);
        condition.and().andIsNotNull(BizLcJl.InnerColumn.lcFy.name());
        List<BizLcJl> jls = findByCondition(condition);
        LcJlModel model = new LcJlModel();
        if (CollectionUtils.isEmpty(jls)) {
            return ApiResponse.success(model);
        }

        // 所有有记录的安全员
        // 所有的记录id
        List<String> list = jls.stream().map(BizLcJl::getId).collect(Collectors.toList());
        SimpleCondition xyCondition = new SimpleCondition(BizLcJlXy.class);
        xyCondition.in(BizLcJlXy.InnerColumn.lcJlId, list);
        List<BizLcJlXy> xies = xyService.findByCondition(xyCondition);

        model.setZgXm(jls.get(0).getZgXm());
        if (CollectionUtils.isNotEmpty(xies)) {
            xies.sort(Comparator.comparing(BizLcJlXy::getCjsj));
            model.setXyList(xies);
        }

        return ApiResponse.success(model);
    }

    @Override
    public ApiResponse<List<LcJlModel>> getJlTj() {
        List<LcJlModel> list = new ArrayList<>();
        LimitedCondition queryCondition = getQueryCondition();
        /*String cjsjGte = getRequestParamterAsString("cjsjGte");
        String cjsjLte = getRequestParamterAsString("cjsjLte");
        if(StringUtils.isBlank(cjsjGte)){
            queryCondition.gte(BizLcJl.InnerColumn.cjsj, DateUtils.getDateStr(new Date(),"yyyy-MM-dd")+ " 00:00:00");
        }
        if(StringUtils.isBlank(cjsjLte)){
            queryCondition.lte(BizLcJl.InnerColumn.cjsj, DateUtils.getDateStr(new Date(),"yyyy-MM-dd") + " 23:59:59");
        }*/
        queryCondition.and().andIsNotNull(BizLcJl.InnerColumn.lcFy.name());
        List<BizLcJl> lcJls = findByCondition(queryCondition);
        if (CollectionUtils.isNotEmpty(lcJls)) {
            Map<String, List<BizLcJl>> collect = lcJls.stream().collect(Collectors.groupingBy(BizLcJl::getJlXm));
            for (String s : collect.keySet()) {
                LcJlModel model = new LcJlModel();
                List<BizLcJl> jlList = collect.get(s);
                // 累计时长
                int sum = jlList.stream().mapToInt(BizLcJl::getSc).sum();
                // 累计费用
                int sum1 = jlList.stream().mapToInt(BizLcJl::getLcFy).sum();
                if (StringUtils.equals(jlList.get(0).getJlLx(), "00")) {
                    model.setJlXm(jlList.get(0).getJlXm() + "_" + jlList.get(0).getJlJx());
                    model.setJlJx("明涛驾校");
                } else {
                    model.setJlXm(jlList.get(0).getJlXm());
                    model.setJlJx(jlList.get(0).getJlJx());
                }
                model.setSc(sum);
                model.setZj(sum1);
                list.add(model);
            }
        }

        return ApiResponse.success(list);
    }

    @Override
    public void pagerExcel(Page<BizLcJl> page, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String time = DateUtils.getDateStr(new Date(), "yyyy-MM-dd");
        String fileName = time + "-明细统计";
        LimitedCondition condition = getQueryCondition();
        PageInfo<BizLcJl> info = findPage(page, condition);
        List<BizLcJl> list = info.getList();
        List<Map<Integer, String>> data = new ArrayList<>();
        Map<Integer, String> map = new HashMap<>();
        map.put(0, "#");
        map.put(1, "车辆编号");
        map.put(2, "开始时间");
        map.put(3, "结束时间");
        map.put(4, "安全员姓名");
        map.put(5, "教练姓名");
        map.put(6, "时长");
        map.put(7, "驾校/队号");
        map.put(8, "学员数量");
        map.put(9, "计费类型");
        map.put(10, "练车费用");
        data.add(map);
        for (int i = 0; i < list.size(); i++) {
            BizLcJl jl = list.get(i);
            Map<Integer, String> dataMap = new HashMap<>();
            dataMap.put(0, i + 1 + "");
            dataMap.put(1, jl.getClBh());
            if(StringUtils.isNotBlank(jl.getKssj())){
                dataMap.put(2, jl.getKssj().substring(0,jl.getKssj().length()-3));
            }else{
                dataMap.put(2," - ");
            }
            if(StringUtils.isNotBlank(jl.getJssj())){
                dataMap.put(3, jl.getJssj().substring(0,jl.getJssj().length()-3));
            }else{
                dataMap.put(3,"-");
            }

            dataMap.put(4, jl.getZgXm());
            dataMap.put(5, jl.getJlXm());
            dataMap.put(6, jl.getSc() + "");
            dataMap.put(7, jl.getJlJx());
            dataMap.put(8, jl.getXySl() + "");
            dataMap.put(9, jl.getLcLx().equals("00") ? "计时" : "按次");
            dataMap.put(10, jl.getLcFy() == null ? "0" : jl.getLcFy() + "");
            data.add(dataMap);
        }
        Map<Integer, String> dataMap = new HashMap<>();
        dataMap.put(0, data.size() + "");
        dataMap.put(1, "合计:");
        dataMap.put(2, "");
        dataMap.put(3, "");
        dataMap.put(4, "");
        dataMap.put(5, "");
        dataMap.put(6, "");
        dataMap.put(7, "");
        dataMap.put(8, "");
        dataMap.put(9, "");
        dataMap.put(10, list.size() == 0 ? "0" : list.stream().filter(bizLcJl -> bizLcJl.getLcFy() != null).mapToInt(BizLcJl::getLcFy).sum() + "");
        data.add(dataMap);

        response.setContentType("application/msexcel");
        request.setCharacterEncoding("UTF-8");
        response.setHeader("pragma", "no-cache");
        response.addHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1") + ".xls");
        ServletOutputStream outputStream = response.getOutputStream();
        ExcelUtil.createSheet(outputStream, "明细统计", data);
    }

    @Override
    public ApiResponse<String> updateXysl(String id, Integer xySl) {
        BizLcJl jl = findById(id);
        RuntimeCheck.ifNull(jl, "未找到练车记录");
        jl.setXySl(xySl);
        update(jl);
        return ApiResponse.success();
    }


}