package com.ldz.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ldz.biz.constant.FeeType;
import com.ldz.biz.mapper.ChargeManagementMapper;
import com.ldz.biz.model.ChargeManagement;
import com.ldz.biz.model.StudentAllModel;
import com.ldz.biz.model.TraineeInformation;
import com.ldz.biz.model.TraineeTestInfo;
import com.ldz.biz.service.ChargeManagementService;
import com.ldz.biz.service.DataStaService;
import com.ldz.biz.service.TraineeInformationService;
import com.ldz.biz.service.TraineeTestInfoService;
import com.ldz.sys.model.SysJg;
import com.ldz.sys.service.JgService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.commonUtil.ExcelUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class DataStaServiceImpl implements DataStaService {

    @Autowired
    private JgService jgService;
    @Autowired
    private ChargeManagementService managementService;
    @Autowired
    private TraineeInformationService informationService;
    @Autowired
    private ChargeManagementMapper chargeManagementMapper;
    @Autowired
    private TraineeTestInfoService testInfoService;

    /**
     * 获取总校分校报名费统计
     *
     * @param startTime
     * @param endTime
     * @param lx        10直属队  20 挂靠队 30 承包队
     * @return
     */
    @Override
    public ApiResponse<List<ChargeManagement>> countBranch(String[] lx, String startTime, String endTime, String jgdm, String carType) {
        // 初始化时间
        if (StringUtils.isBlank(startTime)) {
            startTime = DateUtils.getDateStr(new Date(), "yyyy-MM-dd") + " 00:00:00";
        } else {
            startTime += " 00:00:00";
        }
        if (StringUtils.isBlank(endTime)) {
            endTime = DateUtils.getDateStr(new Date(), "yyyy-MM-dd") + " 23:59:59";
        } else {
            endTime += " 23:59:59";
        }
        // 查询需要统计的机构
        SimpleCondition condition = new SimpleCondition(SysJg.class);
        if (lx != null && lx.length > 0) {
            condition.in(SysJg.InnerColumn.lx, Arrays.asList(lx));
        }
        if (StringUtils.isNotBlank(jgdm)) {
            condition.eq(SysJg.InnerColumn.jgdm, jgdm);
        }
        List<SysJg> jgs = jgService.findByCondition(condition);
        if (CollectionUtils.isNotEmpty(jgs)) {
            List<String> jgdms = jgs.stream().map(SysJg::getJgdm).collect(Collectors.toList());

            List<ChargeManagement> branchList = managementService.getBranchList(jgdms, startTime, endTime, carType);
            return ApiResponse.success(branchList);
        } else {
            return ApiResponse.success(new ArrayList<>());
        }
    }

    /**
     * 获取总收入
     */
    @Override
    public ApiResponse<List<StudentAllModel>> countAllIn(String startTime, String endTime, String[] lx, String carType, String jgdm) throws ParseException {
        // 时间初始话
        if (StringUtils.isBlank(startTime)) {
            startTime = DateUtils.getDateStr(new Date(), "yyyy-MM-dd") + " 00:00:00";
        } else {
            startTime += " 00:00:00";
        }
        if (StringUtils.isBlank(endTime)) {
            endTime = DateUtils.getDateStr(new Date(), "yyyy-MM-dd") + " 23:59:59";
        } else {
            endTime += " 23:59:59";
        }

        List<StudentAllModel> models = new ArrayList<>();

        // 获取所有机构代码对应的机构名称
        SimpleCondition condition = new SimpleCondition(SysJg.class);
        condition.eq(SysJg.InnerColumn.lx, "10");

        List<SysJg> all = jgService.findByCondition(condition);

        Map<String, String> jgMap = all.stream().collect(Collectors.toMap(SysJg::getJgdm, SysJg::getJgmc));

        List<String> monthBetween = DateUtils.getMonthBetween(startTime, endTime);
        SimpleCondition simpleCondition = new SimpleCondition(SysJg.class);

        if (StringUtils.isNotBlank(jgdm)) {
            simpleCondition.eq(SysJg.InnerColumn.jgdm, jgdm);
        }
        List<SysJg> jgs = jgService.findByCondition(simpleCondition);

        if (CollectionUtils.isNotEmpty(jgs)) {
            // 查询机构下所有的缴费记录
            List<String> jgdms = jgs.stream().map(SysJg::getJgdm).collect(Collectors.toList());
            List<ChargeManagement> allIn = managementService.getAllIn(jgdms, startTime, endTime);
            Map<String, List<ChargeManagement>> collect = allIn.stream().collect(Collectors.groupingBy(ChargeManagement::getJgdm, TreeMap::new, Collectors.toList()));
            Set<String> set = collect.keySet();


            HashMap<String, Integer> mainMap = new LinkedHashMap<>();
            HashMap<String, Integer> otherMap = new LinkedHashMap<>();
            for (String s : set) {

                // 根据每个机构代码计算每个机构的总收入
                List<ChargeManagement> managementList = collect.get(s);
                HashMap<String, Integer> map = new LinkedHashMap<>();
                StudentAllModel allModel = new StudentAllModel();
                List<StudentAllModel.StuAll> stuAlls = new ArrayList<>();


                for (int i = 0; i < monthBetween.size(); i++) {
                    // 根据 1-12月份统计
                    StudentAllModel.StuAll stuAll = new StudentAllModel.StuAll();
//                    int allCount = 0;
                    int main = 0;
                    int other = 0;
                    String s1 = monthBetween.get(i);
                    int total = 0;
                    if (mainMap.containsKey(s1)) {
                        main = mainMap.get(s1);
                    } else {
                        mainMap.put(s1, main);
                    }
                    if (otherMap.containsKey(s1)) {
                        other = otherMap.get(s1);
                    } else {
                        otherMap.put(s1, other);
                    }


                    for (int j = managementList.size() - 1; j >= 0; j--) {
                        ChargeManagement management = managementList.get(j);
                        if (StringUtils.startsWith(management.getChargeTime(), s1)) {
                            if (jgMap.containsKey(management.getJgdm())) {
                                main += management.getChargeFee();
                                total += management.getChargeFee();
                            } else {
                                other += management.getChargeFee();
                            }
                        }
                    }
                    stuAll.setCount(total);
                    stuAll.setTime(s1);
                    stuAlls.add(stuAll);

                    mainMap.put(s1, main);
                    otherMap.put(s1, other);
                    map.put(s1, total);

                }

                if (jgMap.containsKey(s)) {
                    allModel.setStu(stuAlls);
                    allModel.setJgmc(jgMap.get(s));
                    if (CollectionUtils.isNotEmpty(stuAlls)) {
                        allModel.setAll(stuAlls.stream().mapToLong(StudentAllModel.StuAll::getCount).sum());
                    }

                    models.add(allModel);
                }
            }
            StudentAllModel allModel = new StudentAllModel();
            List<StudentAllModel.StuAll> stuAlls = new ArrayList<>();
            int zxAll = 0;
            for (String s : mainMap.keySet()) {


                StudentAllModel.StuAll stuAll = new StudentAllModel.StuAll();
                stuAll.setTime(s);
                stuAll.setCount(mainMap.get(s));
                zxAll += mainMap.get(s);
                stuAlls.add(stuAll);

            }
            allModel.setAll(zxAll);
            allModel.setJgmc("总校");
            allModel.setStu(stuAlls);
            models.add(allModel);
            List<StudentAllModel.StuAll> otherModels = new ArrayList<>();
            StudentAllModel otherAll = new StudentAllModel();
            int fxAll = 0;
            for (String s : otherMap.keySet()) {

                StudentAllModel.StuAll stuAll = new StudentAllModel.StuAll();
                stuAll.setTime(s);
                stuAll.setCount(otherMap.get(s));
                otherModels.add(stuAll);
                fxAll += otherMap.get(s);

            }
            otherAll.setAll(fxAll);
            otherAll.setJgmc("分校");
            otherAll.setStu(otherModels);
            models.add(otherAll);

            return ApiResponse.success(models);
        } else {
            return ApiResponse.success(new ArrayList<>());
        }


    }


    @Override
    public ApiResponse<List<StudentAllModel>> getAllStudentCount(String startTime, String endTime, String jgdm, String[] lx) throws ParseException {


        if (StringUtils.isBlank(startTime)) {
            startTime = DateUtils.getDateStr(new Date(), "yyyy") + " 00:00:00";
        } else {
            startTime += " 00:00:00";
        }
        if (StringUtils.isBlank(endTime)) {
            endTime = DateUtils.getDateStr(new Date(), "yyyy") + " 23:59:59";
        } else {
            endTime += " 23:59:59";
        }

        List<String> monthBetween = DateUtils.getMonthBetween(startTime.substring(0, 4) + "-01", endTime.substring(0, 4) + "-12");
        List<StudentAllModel> models = new ArrayList<>();

        List<SysJg> all = jgService.findAll();
        Map<String, String> jgMap = all.stream().collect(Collectors.toMap(SysJg::getJgdm, SysJg::getJgmc));

        SimpleCondition condition = new SimpleCondition(TraineeInformation.class);
        condition.gte(TraineeInformation.InnerColumn.registrationTime, startTime);
        condition.lte(TraineeInformation.InnerColumn.registrationTime, endTime);
        if (StringUtils.isNotBlank(jgdm)) {
            condition.eq(TraineeInformation.InnerColumn.jgdm, jgdm);
        }
        if (lx != null && lx.length > 0) {
            SimpleCondition simpleCondition = new SimpleCondition(SysJg.class);
            simpleCondition.in(SysJg.InnerColumn.lx, Arrays.asList(lx));
            List<SysJg> jgs = jgService.findByCondition(simpleCondition);
            if (CollectionUtils.isNotEmpty(jgs)) {
                List<String> jgdms = jgs.stream().map(SysJg::getJgdm).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(jgdms)) {
                    condition.in(TraineeInformation.InnerColumn.jgdm, jgdms);
                }
            }
        }
        condition.setOrderByClause("  jgdm asc ");
        List<TraineeInformation> informationList = informationService.findByCondition(condition);
        Map<String, List<TraineeInformation>> collect = informationList.stream().collect(Collectors.groupingBy(TraineeInformation::getJgdm, TreeMap::new, Collectors.toList()));
        Set<String> set = collect.keySet();
        HashMap<String, Integer> map = new LinkedHashMap<>();
        for (String s : set) {
            StudentAllModel allModel = new StudentAllModel();
            List<StudentAllModel.StuAll> stuAlls = new ArrayList<>();
            int dropOut = 0;

            List<TraineeInformation> traineeInformatics = collect.get(s);

            for (int j = 0; j < monthBetween.size(); j++) {
                int test = 0;
                String s1 = monthBetween.get(j);
                int total = 0;
                if (map.containsKey(s1)) {
                    test = map.get(s1);
                } else {
                    map.put(s1, 0);
                }


                for (int i = traineeInformatics.size() - 1; i >= 0; i--) {
                    TraineeInformation traineeInformation = traineeInformatics.get(i);
                    if (StringUtils.startsWith(traineeInformation.getRegistrationTime(), s1)) {

                        if (StringUtils.equals(traineeInformation.getStatus(), "60")) {
                            dropOut++;
                        }
                        test++;
                        total++;
                        traineeInformatics.remove(i);
                    }
                }
                StudentAllModel.StuAll stuAll = new StudentAllModel.StuAll();
                stuAll.setTime(s1);
                stuAll.setCount(total);
                stuAlls.add(stuAll);
                if (j == monthBetween.size() - 1) {
                    int to = 0;

                    for (StudentAllModel.StuAll a : stuAlls) {
                        to += a.getCount();
                    }

                    allModel.setAll(to);
                    allModel.setDropOut(dropOut);
                    allModel.setRealAll(to - dropOut);

                }
                allModel.setStu(stuAlls);
                allModel.setJgmc(jgMap.get(s));
                map.put(s1, test);


            }

            models.add(allModel);
        }
        if (CollectionUtils.isNotEmpty(models)) {
            StudentAllModel model = new StudentAllModel();
            List<StudentAllModel.StuAll> stuAlls = new ArrayList<>();
            model.setJgmc("合计");
            for (String s : map.keySet()) {

                StudentAllModel.StuAll stuAll = new StudentAllModel.StuAll();
                stuAll.setTime(s);
                stuAll.setCount(map.get(s));
                stuAlls.add(stuAll);
                model.setStu(stuAlls);
            }
            long asInt = models.stream().mapToLong(StudentAllModel::getAll).reduce((integer, integer2) -> integer + integer2).getAsLong();
            long asInt1 = models.stream().mapToLong(StudentAllModel::getDropOut).reduce((integer, integer2) -> integer + integer2).getAsLong();
            long asInt2 = models.stream().mapToLong(StudentAllModel::getRealAll).reduce((integer, integer2) -> integer + integer2).getAsLong();
            model.setAll(asInt);
            model.setDropOut(asInt1);
            model.setRealAll(asInt2);
            models.add(model);
        }
        return ApiResponse.success(models);
    }

    @Override
    public ApiResponse<List<StudentAllModel>> getStudentCount(String startTime, String endTime, String jgdm, String[] lx) {
        if (StringUtils.isBlank(startTime)) {
            startTime = DateUtils.getDateStr(new Date(), "yyyy") + "-01-01 00:00:00";
        } else {
            startTime += " 00:00:00";
        }
        if (StringUtils.isBlank(endTime)) {
            endTime = DateUtils.getDateStr(new Date(), "yyyy") + "-12-31 23:59:59";
        } else {
            endTime += " 23:59:59";
        }

        List<StudentAllModel> models = new ArrayList<>();

        List<SysJg> all = jgService.findAll();
        Map<String, String> jgMap = all.stream().collect(Collectors.toMap(SysJg::getJgdm, SysJg::getJgmc));

        SimpleCondition condition = new SimpleCondition(TraineeInformation.class);
        condition.gte(TraineeInformation.InnerColumn.registrationTime, startTime);
        condition.lte(TraineeInformation.InnerColumn.registrationTime, endTime);
        if (StringUtils.isNotBlank(jgdm)) {
            condition.eq(TraineeInformation.InnerColumn.jgdm, jgdm);
        }
        if (lx != null && lx.length > 0) {
            SimpleCondition simpleCondition = new SimpleCondition(SysJg.class);
            simpleCondition.in(SysJg.InnerColumn.lx, Arrays.asList(lx));
            List<SysJg> jgs = jgService.findByCondition(simpleCondition);
            if (CollectionUtils.isNotEmpty(jgs)) {
                List<String> jgdms = jgs.stream().map(SysJg::getJgdm).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(jgdms)) {
                    condition.in(TraineeInformation.InnerColumn.jgdm, jgdms);
                }
            }
        }
        condition.setOrderByClause("  jgdm asc ");
        List<TraineeInformation> informationList = informationService.findByCondition(condition);
        Map<String, List<TraineeInformation>> collect = informationList.stream().collect(Collectors.groupingBy(TraineeInformation::getJgdm, TreeMap::new, Collectors.toList()));
        Set<String> set = collect.keySet();
        HashMap<String, Integer> map = new LinkedHashMap<>();
        for (String s : set) {

            List<TraineeInformation> traineeInformatics = collect.get(s);
            Map<String, Long> count1 = traineeInformatics.stream().collect(Collectors.groupingBy(TraineeInformation::getCarType, Collectors.counting()));
            StudentAllModel allModel = new StudentAllModel();
            long count = traineeInformatics.stream().count();
            allModel.setAll(count);
            List<StudentAllModel.StuAll> stuAlls = new ArrayList<>();
            Long a1 = count1.get("A1");
            Long a2 = count1.get("A2");
            Long a3 = count1.get("A3");
            Long b2 = count1.get("B2");
            Long c1 = count1.get("C1");
            Long c2 = count1.get("C2");

            allModel.setJgmc(jgMap.get(s));
            allModel.setA1(a1 == null ? 0L : a1);
            allModel.setA2(a2 == null ? 0L : a2);
            allModel.setA3(a3 == null ? 0L : a3);
            allModel.setB2(b2 == null ? 0L : b2);
            allModel.setC1(c1 == null ? 0L : c1);
            allModel.setC2(c2 == null ? 0L : c2);


            models.add(allModel);
        }
        if (CollectionUtils.isNotEmpty(models)) {
            StudentAllModel allModel = new StudentAllModel();
            allModel.setJgmc("合计");
            allModel.setA1(models.stream().mapToLong(StudentAllModel::getA1).reduce((left, right) -> left + right).getAsLong());
            allModel.setA2(models.stream().mapToLong(StudentAllModel::getA2).reduce((left, right) -> left + right).getAsLong());
            allModel.setA3(models.stream().mapToLong(StudentAllModel::getA3).reduce((left, right) -> left + right).getAsLong());
            allModel.setB2(models.stream().mapToLong(StudentAllModel::getB2).reduce((left, right) -> left + right).getAsLong());
            allModel.setC1(models.stream().mapToLong(StudentAllModel::getC1).reduce((left, right) -> left + right).getAsLong());
            allModel.setC2(models.stream().mapToLong(StudentAllModel::getC2).reduce((left, right) -> left + right).getAsLong());
            allModel.setAll(models.stream().mapToLong(StudentAllModel::getAll).reduce((left, right) -> left + right).getAsLong());
            models.add(allModel);
        }
        return ApiResponse.success(models);
    }

    @Override
    public ApiResponse<List<StudentAllModel>> getAllIn(String startTime, String endTime) throws ParseException {

        List<StudentAllModel> models = new ArrayList<>();

        if (StringUtils.isBlank(startTime)) {
            startTime = DateUtils.getDateStr(new Date(), "yyyy") + "-01-01 00:00:00";
        } else {
            startTime = startTime + " 00:00:00";
        }
        if (StringUtils.isBlank(endTime)) {
            endTime = DateUtils.getDateStr(new Date(), "yyyy") + "-12-31 23:59:59";
        } else {
            endTime = endTime + " 23:59:59";
        }
        List<String> monthBetween = DateUtils.getMonthBetween(startTime.substring(0, 4) + "-01", endTime.substring(0, 4) + "-12");


        List<ChargeManagement> managements = chargeManagementMapper.getAllIn2(startTime, endTime);

        if (CollectionUtils.isNotEmpty(managements)) {
            LinkedHashMap<String, List<ChargeManagement>> collect = managements.stream().collect(Collectors.groupingBy(ChargeManagement::getJgdm, LinkedHashMap::new, Collectors.toList()));
            Set<String> jgdms = managements.stream().map(ChargeManagement::getJgdm).collect(Collectors.toSet());
            List<SysJg> jgs = jgService.findIn(SysJg.InnerColumn.jgdm, jgdms);
            Map<String, String> jgMap = jgs.stream().collect(Collectors.toMap(SysJg::getJgdm, SysJg::getJgmc));

            for (String s : collect.keySet()) {
                StudentAllModel allModel = new StudentAllModel();
                List<StudentAllModel.StuAll> stuAlls = new ArrayList<>();
                allModel.setJgmc(jgMap.get(s));

                // 当前机构的所有报名费
                List<ChargeManagement> chargeManagements = collect.get(s);
                long j = 0L;
                for (String mon : monthBetween) {
                    Long i = 0L;
                    Long count = 0L;
                    StudentAllModel.StuAll stuAll = new StudentAllModel.StuAll();
                    stuAll.setTime(mon);
                    for (ChargeManagement chargeManagement : chargeManagements) {

                        if (chargeManagement.getCjsj().startsWith(mon)) {
                            if (chargeManagement.getChargeCode().equals("0000")) {
                                count += chargeManagement.getChargeFee();
                                i++;
                            } else if (chargeManagement.getChargeCode().equals("0003")) {
                                j++;
                            }

                        }
                    }
                    stuAll.setCount(count);
                    stuAll.setSignUp(i);
                    stuAlls.add(stuAll);

                }
                StudentAllModel.StuAll stuAll = new StudentAllModel.StuAll();
                stuAll.setTime("退学人数");
                stuAll.setCount(j);
                stuAll.setSignUp(0L);
                stuAlls.add(stuAll);
                allModel.setAll(chargeManagements.stream().mapToLong(ChargeManagement::getChargeFee).sum());
                allModel.setStu(stuAlls);
                models.add(allModel);
            }
        }
        if (CollectionUtils.isNotEmpty(models)) {
            List<List<StudentAllModel.StuAll>> collect = models.stream().map(StudentAllModel::getStu).collect(Collectors.toList());
            List<StudentAllModel.StuAll> models1 = collect.stream().filter(CollectionUtils::isNotEmpty).reduce(new ArrayList<>(), (all, item) -> {
                all.addAll(item);
                return all;
            });
            LinkedHashMap<String, Long> collect1 = models1.stream().collect(Collectors.groupingBy(StudentAllModel.StuAll::getTime, LinkedHashMap::new, Collectors.summingLong(StudentAllModel.StuAll::getCount)));
            LinkedHashMap<String, Long> collect2 = models1.stream().collect(Collectors.groupingBy(StudentAllModel.StuAll::getTime, LinkedHashMap::new, Collectors.summingLong(StudentAllModel.StuAll::getSignUp)));
            long sum = models.stream().mapToLong(StudentAllModel::getAll).sum();
            StudentAllModel allModel = new StudentAllModel();
            allModel.setAll(sum);
            allModel.setJgmc("合计");
            List<StudentAllModel.StuAll> stuAlls = new ArrayList<>();
            for (String s : collect1.keySet()) {
                StudentAllModel.StuAll stuAll = new StudentAllModel.StuAll();
                Long aLong = collect1.get(s);
                stuAll.setCount(aLong);
                stuAll.setTime(s);
                stuAll.setSignUp(collect2.get(s));
                stuAlls.add(stuAll);
            }
            allModel.setStu(stuAlls);
            models.add(allModel);
        }

        return ApiResponse.success(models);
    }

    @Override
    public ApiResponse<List<StudentAllModel>> getAllPayment(String startTime, String endTime, String jgdm, String... lx) {
        if (StringUtils.isBlank(startTime)) {
            startTime = DateUtils.getDateStr(new Date(), "yyyy") + "-01-01 00:00:00";
        } else {
            startTime += " 00:00:00";
        }
        if (StringUtils.isBlank(endTime)) {
            endTime = DateUtils.getDateStr(new Date(), "yyyy") + "-12-31 23:59:59";
        } else {
            endTime += " 23:59:59";
        }

        List<StudentAllModel> models = new ArrayList<>();

        List<SysJg> all = jgService.findAll();
        Map<String, String> jgMap = all.stream().collect(Collectors.toMap(SysJg::getJgdm, SysJg::getJgmc));

        SimpleCondition chargeCondition = new SimpleCondition(ChargeManagement.class);
        chargeCondition.eq(ChargeManagement.InnerColumn.chargeCode, FeeType.SIGN_UP);
        chargeCondition.lte(ChargeManagement.InnerColumn.chargeTime, endTime);
        chargeCondition.gte(ChargeManagement.InnerColumn.chargeTime, startTime);
        List<ChargeManagement> chargeManagements = managementService.findByCondition(chargeCondition);
        List<String> list = chargeManagements.stream().map(ChargeManagement::getTraineeId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(list)) {
            return ApiResponse.success(models);
        }

        SimpleCondition condition = new SimpleCondition(TraineeInformation.class);
        condition.in(TraineeInformation.InnerColumn.id, list);
        if (StringUtils.isNotBlank(jgdm)) {
            condition.eq(TraineeInformation.InnerColumn.jgdm, jgdm);
        }
        if (lx != null && lx.length > 0) {
            SimpleCondition simpleCondition = new SimpleCondition(SysJg.class);
            simpleCondition.in(SysJg.InnerColumn.lx, Arrays.asList(lx));
            List<SysJg> jgs = jgService.findByCondition(simpleCondition);
            if (CollectionUtils.isNotEmpty(jgs)) {
                List<String> jgdms = jgs.stream().map(SysJg::getJgdm).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(jgdms)) {
                    condition.in(TraineeInformation.InnerColumn.jgdm, jgdms);
                }
            }
        }
        condition.setOrderByClause("  jgdm asc ");
        List<TraineeInformation> informationList = informationService.findByCondition(condition);
        if (CollectionUtils.isEmpty(informationList)) {
            return ApiResponse.success(models);
        }
        Map<String, List<TraineeInformation>> collect = informationList.stream().collect(Collectors.groupingBy(TraineeInformation::getJgdm, TreeMap::new, Collectors.toList()));
        Set<String> set = collect.keySet();
        for (String s : set) {

            List<TraineeInformation> traineeInformatics = collect.get(s);
            Map<String, Long> count1 = traineeInformatics.stream().collect(Collectors.groupingBy(TraineeInformation::getCarType, Collectors.counting()));
            StudentAllModel allModel = new StudentAllModel();
            long count = traineeInformatics.stream().count();
            allModel.setAll(count);
            Long a1 = count1.get("A1");
            Long a2 = count1.get("A2");
            Long a3 = count1.get("A3");
            Long b2 = count1.get("B2");
            Long c1 = count1.get("C1");
            Long c2 = count1.get("C2");

            allModel.setJgmc(jgMap.get(s));
            allModel.setA1(a1 == null ? 0L : a1);
            allModel.setA2(a2 == null ? 0L : a2);
            allModel.setA3(a3 == null ? 0L : a3);
            allModel.setB2(b2 == null ? 0L : b2);
            allModel.setC1(c1 == null ? 0L : c1);
            allModel.setC2(c2 == null ? 0L : c2);


            models.add(allModel);
        }
        if (CollectionUtils.isNotEmpty(models)) {
            StudentAllModel allModel = new StudentAllModel();
            allModel.setJgmc("合计");
            allModel.setA1(models.stream().mapToLong(StudentAllModel::getA1).reduce((left, right) -> left + right).getAsLong());
            allModel.setA2(models.stream().mapToLong(StudentAllModel::getA2).reduce((left, right) -> left + right).getAsLong());
            allModel.setA3(models.stream().mapToLong(StudentAllModel::getA3).reduce((left, right) -> left + right).getAsLong());
            allModel.setB2(models.stream().mapToLong(StudentAllModel::getB2).reduce((left, right) -> left + right).getAsLong());
            allModel.setC1(models.stream().mapToLong(StudentAllModel::getC1).reduce((left, right) -> left + right).getAsLong());
            allModel.setC2(models.stream().mapToLong(StudentAllModel::getC2).reduce((left, right) -> left + right).getAsLong());
            allModel.setAll(models.stream().mapToLong(StudentAllModel::getAll).reduce((left, right) -> left + right).getAsLong());
            models.add(allModel);
        }
        return ApiResponse.success(models);
    }

    @Override
    public ApiResponse<List<Map<String, String>>> getPass(String jgdm, String startTime, String endTime, String km) {
        Map<String, String> map = new HashMap<>();
        map.put("km1", "科目一");
        map.put("km2", "科目二");
        map.put("km3", "科目三");
        map.put("km4", "科目四");
        Map<String, String> kmMap = new HashMap<>();
        kmMap.put("科目一", "km1");
        kmMap.put("科目二", "km2");
        kmMap.put("科目三", "km3");
        kmMap.put("科目四", "km4");

        List<Map<String, String>> data = new ArrayList<>();
        if (StringUtils.isBlank(startTime)) {
            startTime = DateUtils.getDateStr(new Date(), "yyyy-MM-dd");
        }
        if (StringUtils.isBlank(endTime)) {
            endTime = DateUtils.getDateStr(new Date(), "yyyy-MM-dd");
        }
        if (StringUtils.isBlank(jgdm)) {
            jgdm = null;
        }
        if (StringUtils.isBlank(km)) {
            km = null;
        } else {
            km = map.get(km);
        }
        List<TraineeTestInfo> info = testInfoService.getInfo(jgdm, startTime, endTime, km);
        LinkedHashMap<String, List<TraineeTestInfo>> collect = info.stream().collect(Collectors.groupingBy(TraineeTestInfo::getJgdm, LinkedHashMap::new, Collectors.toList()));
        // 计算合格率
        for (String s : collect.keySet()) {
            LinkedHashMap<String, String> dataMap = new LinkedHashMap<>();
            SysJg jg = jgService.findByOrgCode(s);
            List<TraineeTestInfo> testInfos = collect.get(s);
            // 找出所有合格的人数
            long count = testInfos.stream().filter(traineeTestInfo -> StringUtils.equals(traineeTestInfo.getTestResult(), "00")).count();
            String v = formatValue((double) count / testInfos.size() * 100) + "%";
            dataMap.put("jgmc", jg.getJgmc());
            dataMap.put("px", jg.getPx() + "");
            dataMap.put("kmAll", v);
            dataMap.put("kmAllPass", count + "");
            dataMap.put("all", testInfos.size() + "");
            // 科目分组
            LinkedHashMap<String, List<TraineeTestInfo>> listMap = testInfos.stream().collect(Collectors.groupingBy(TraineeTestInfo::getSubject, LinkedHashMap::new, Collectors.toList()));

            for (String key : kmMap.keySet()) {
                if (listMap.containsKey(key)) {
                    int size = listMap.get(key).size();
                    long count1 = listMap.get(key).stream().filter(traineeTestInfo -> StringUtils.equals(traineeTestInfo.getTestResult(), "00")).count();
                    String lv = formatValue((double) count1 / size * 100) + "%";
                    dataMap.put(kmMap.get(key), lv);
                    dataMap.put(kmMap.get(key) + "Pass", count1 + "");
                    dataMap.put(kmMap.get(key) + "all", size + "");
                } else {
                    dataMap.put(kmMap.get(key), "0%");
                    dataMap.put(kmMap.get(key) + "Pass", "0");
                    dataMap.put(kmMap.get(key) + "all", "0");
                }

            }
            data.add(dataMap);
        }
        data.sort(Comparator.comparing(stringStringMap -> Integer.parseInt(stringStringMap.get("px"))));
        return ApiResponse.success(data);
    }

    @Override
    public ApiResponse<String> statisCharge(int pageNum, int pageSize, String start, String end, String idCard, String name, String jgdm) {
        if (StringUtils.isBlank(start)) {
            start = DateTime.now().toString("yyyy-MM-dd") + " 00:00:00";
        }
        if (StringUtils.isBlank(end)) {
            end = DateTime.now().toString("yyyy-MM-dd") + " 23:59:59";
        }

        SimpleCondition condition = new SimpleCondition(TraineeInformation.class);
        condition.gte(TraineeInformation.InnerColumn.confirmTime, start);
        condition.lte(TraineeInformation.InnerColumn.confirmTime, end);
        if (StringUtils.isNotBlank(idCard)) {
            condition.like(TraineeInformation.InnerColumn.idCardNo, idCard);
        }
        if (StringUtils.isNotBlank(name)) {
            condition.like(TraineeInformation.InnerColumn.name, name);
        }
        if (StringUtils.isNotBlank(jgdm)) {
            condition.eq(TraineeInformation.InnerColumn.jgdm, jgdm);
        }
        // 查询当前时间段报名的学员的所有收支信息
        PageInfo<TraineeInformation> info = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> informationService.findByCondition(condition));
        if (CollectionUtils.isEmpty(info.getList())) {
            return ApiResponse.success();
        }
        List<String> trainIds = info.getList().stream().map(TraineeInformation::getId).collect(Collectors.toList());
        // 查询学员的 考试支出金额
        SimpleCondition simpleCondition = new SimpleCondition(ChargeManagement.class);
        simpleCondition.in(ChargeManagement.InnerColumn.chargeCode, Arrays.asList(FeeType.FIR_SUB, FeeType.SEC_SUB, FeeType.THIRD_SUB));
        simpleCondition.in(ChargeManagement.InnerColumn.traineeId, trainIds);
        List<ChargeManagement> managements = chargeManagementMapper.selectByExample(simpleCondition);
        Map<String, List<ChargeManagement>> listMap = managements.stream().collect(Collectors.groupingBy(ChargeManagement::getTraineeId));

        info.getList().forEach(traineeInformation -> {
            if (listMap.containsKey(traineeInformation.getId())) {
                List<ChargeManagement> list = listMap.get(traineeInformation.getId());
                // 科目一支出
                int firPay = list.stream().filter(chargeManagement -> StringUtils.equals(chargeManagement.getChargeCode(), FeeType.FIR_SUB)).mapToInt(ChargeManagement::getChargeFee).sum();
                traineeInformation.setFirPay(firPay);
                // 科目二支出
                int secPay = list.stream().filter(chargeManagement -> StringUtils.equals(chargeManagement.getChargeCode(), FeeType.SEC_SUB)).mapToInt(ChargeManagement::getChargeFee).sum();
                traineeInformation.setSecPay(secPay);
                // 科目三支出
                int thirdPay = list.stream().filter(chargeManagement -> StringUtils.equals(chargeManagement.getChargeCode(), FeeType.THIRD_SUB)).mapToInt(ChargeManagement::getChargeFee).sum();
                traineeInformation.setThirdPay(thirdPay);
                traineeInformation.setTotal(firPay + secPay + thirdPay);
            } else {
                traineeInformation.setFirPay(0);
                traineeInformation.setSecPay(0);
                traineeInformation.setThirdPay(0);
                traineeInformation.setTotal(0);
            }
        });
        ApiResponse<String> response = new ApiResponse<>();
        response.setPage(info);
        List<TraineeInformation> list = informationService.findByCondition(condition);
        if(CollectionUtils.isNotEmpty(list)){
            // 实际总收费
            int sum = list.stream().mapToInt(TraineeInformation::getRealPay).sum();
             trainIds = info.getList().stream().map(TraineeInformation::getId).collect(Collectors.toList());
            // 查询学员的 考试支出金额
            simpleCondition = new SimpleCondition(ChargeManagement.class);
            simpleCondition.in(ChargeManagement.InnerColumn.chargeCode, Arrays.asList(FeeType.FIR_SUB, FeeType.SEC_SUB, FeeType.THIRD_SUB));
            simpleCondition.in(ChargeManagement.InnerColumn.traineeId, trainIds);
            managements = chargeManagementMapper.selectByExample(simpleCondition);
            int owe = list.stream().mapToInt(TraineeInformation::getOweAmount).sum();
            int firPay = 0;
            int secPay = 0;
            int thirdPay = 0;
            int fees = 0;
            if(CollectionUtils.isNotEmpty(managements)){
                fees = managements.stream().mapToInt(ChargeManagement::getChargeFee).sum();
                firPay = managements.stream().filter(chargeManagement -> StringUtils.equals(chargeManagement.getChargeCode(), FeeType.FIR_SUB)).mapToInt(ChargeManagement::getChargeFee).sum();
                secPay = managements.stream().filter(chargeManagement -> StringUtils.equals(chargeManagement.getChargeCode(), FeeType.SEC_SUB)).mapToInt(ChargeManagement::getChargeFee).sum();
                thirdPay = managements.stream().filter(chargeManagement -> StringUtils.equals(chargeManagement.getChargeCode(), FeeType.THIRD_SUB)).mapToInt(ChargeManagement::getChargeFee).sum();
            }
            response.setMessage(sum + "," + owe + "," + fees + "," + firPay + "," + secPay + "," + thirdPay);
        }else{
            response.setMessage("0,0,0,0,0,0");
        }
        return response;
    }

    @Override
    public void exportStatisCharge(String start, String end, String idCard, String name, String jgdm, HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (StringUtils.isBlank(start)) {
            start = DateTime.now().toString("yyyy-MM-dd") + " 00:00:00";
        }
        if (StringUtils.isBlank(end)) {
            end = DateTime.now().toString("yyyy-MM-dd") + " 23:59:59";
        }
        SimpleCondition condition = new SimpleCondition(TraineeInformation.class);
        condition.gte(TraineeInformation.InnerColumn.confirmTime, start);
        condition.lte(TraineeInformation.InnerColumn.confirmTime, end);
        if (StringUtils.isNotBlank(idCard)) {
            condition.like(TraineeInformation.InnerColumn.idCardNo, idCard);
        }
        if (StringUtils.isNotBlank(name)) {
            condition.like(TraineeInformation.InnerColumn.name, name);
        }
        if (StringUtils.isNotBlank(jgdm)) {
            condition.eq(TraineeInformation.InnerColumn.jgdm, jgdm);
        }
        List<Map<Integer,String>> dataList = new ArrayList<>();
        Map<Integer, String> titleMap = new HashMap<>();
        titleMap.put(0, "序号");
        titleMap.put(1, "姓名");
        titleMap.put(2, "身份证号");
        titleMap.put(3, "报名点");
        titleMap.put(4, "车型");
        titleMap.put(5, "收费时间");
        titleMap.put(6, "收费金额");
        titleMap.put(7, "欠费金额");
        titleMap.put(8, "科目一支出");
        titleMap.put(9, "科目二支出");
        titleMap.put(10, "科目三支出");
        titleMap.put(11,"考试支出合计");
        dataList.add(titleMap);


        List<TraineeInformation> informations = informationService.findByCondition(condition);

        if (CollectionUtils.isNotEmpty(informations)) {
            Set<String> jgdms = informations.stream().map(TraineeInformation::getJgdm).collect(Collectors.toSet());
            List<SysJg> jgs = jgService.findIn(SysJg.InnerColumn.jgdm, jgdms);
            Map<String, String> jgMap = jgs.stream().collect(Collectors.toMap(SysJg::getJgdm, p -> p.getJgmc()));

            List<String> trainIds = informations.stream().map(TraineeInformation::getId).collect(Collectors.toList());
            // 查询学员的 考试支出金额
            SimpleCondition simpleCondition = new SimpleCondition(ChargeManagement.class);
            simpleCondition.in(ChargeManagement.InnerColumn.chargeCode, Arrays.asList(FeeType.FIR_SUB, FeeType.SEC_SUB, FeeType.THIRD_SUB));
            simpleCondition.in(ChargeManagement.InnerColumn.traineeId, trainIds);
            List<ChargeManagement> managements = chargeManagementMapper.selectByExample(simpleCondition);
            Map<String, List<ChargeManagement>> listMap = managements.stream().collect(Collectors.groupingBy(ChargeManagement::getTraineeId));
             AtomicInteger i = new AtomicInteger(0);
            informations.forEach(traineeInformation ->  {
               i.getAndIncrement();
                Map<Integer,String> dataMap = new HashMap<>();
                if (listMap.containsKey(traineeInformation.getId())) {
                    List<ChargeManagement> list = listMap.get(traineeInformation.getId());
                    // 科目一支出
                    int firPay = list.stream().filter(chargeManagement -> StringUtils.equals(chargeManagement.getChargeCode(), FeeType.FIR_SUB)).mapToInt(ChargeManagement::getChargeFee).sum();
                    traineeInformation.setFirPay(firPay);
                    // 科目二支出
                    int secPay = list.stream().filter(chargeManagement -> StringUtils.equals(chargeManagement.getChargeCode(), FeeType.SEC_SUB)).mapToInt(ChargeManagement::getChargeFee).sum();
                    traineeInformation.setSecPay(secPay);
                    // 科目三支出
                    int thirdPay = list.stream().filter(chargeManagement -> StringUtils.equals(chargeManagement.getChargeCode(), FeeType.THIRD_SUB)).mapToInt(ChargeManagement::getChargeFee).sum();
                    traineeInformation.setThirdPay(thirdPay);
                    traineeInformation.setTotal(firPay + secPay + thirdPay);
                } else {
                    traineeInformation.setFirPay(0);
                    traineeInformation.setSecPay(0);
                    traineeInformation.setThirdPay(0);
                    traineeInformation.setTotal(0);
                }
                dataMap.put(0, i +"");
                dataMap.put(1, traineeInformation.getName());
                dataMap.put(2, traineeInformation.getIdCardNo());
                dataMap.put(3, jgMap.get(traineeInformation.getJgdm()));
                dataMap.put(4, traineeInformation.getCarType());
                dataMap.put(5, traineeInformation.getConfirmTime());
                dataMap.put(6, traineeInformation.getRealPay() + "");
                dataMap.put(7, traineeInformation.getOweAmount() + "");
                dataMap.put(8, traineeInformation.getFirPay() + "");
                dataMap.put(9, traineeInformation.getSecPay() + "");
                dataMap.put(10, traineeInformation.getThirdPay() + "");
                dataMap.put(11 , traineeInformation.getTotal() + "");
                dataList.add(dataMap);
            });
        }
        response.setContentType("application/msexcel");
        request.setCharacterEncoding("UTF-8");
        response.setHeader("pragma", "no-cache");
        response.addHeader("Content-Disposition", "attachment; filename=" + new String("收支统计导出".getBytes(StandardCharsets.UTF_8), "ISO8859-1") + ".xls");
        ServletOutputStream outputStream = response.getOutputStream();
        ExcelUtil.createSheet(outputStream, "收支统计", dataList);
    }

    public static double formatValue(double val) {

        BigDecimal bg = new BigDecimal(val).setScale(2, RoundingMode.UP);
        return bg.doubleValue();

    }


}
