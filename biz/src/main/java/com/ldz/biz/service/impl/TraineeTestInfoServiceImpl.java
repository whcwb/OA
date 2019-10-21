package com.ldz.biz.service.impl;

import com.google.common.collect.Maps;
import com.ldz.biz.mapper.TraineeTestInfoMapper;
import com.ldz.biz.model.*;
import com.ldz.biz.service.*;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.model.SysMessage;
import com.ldz.sys.model.SysYh;
import com.ldz.sys.service.SysMessageService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.commonUtil.JsonUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import tk.mybatis.mapper.common.Mapper;

import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class TraineeTestInfoServiceImpl extends BaseServiceImpl<TraineeTestInfo, String> implements TraineeTestInfoService {

    @Autowired
    private StringRedisTemplate redisDao;
    @Autowired
    private YyCwjlService yyCwjlService;
    @Autowired
    private TraineeTestInfoMapper baseMapper;
    @Autowired
    private FileUploadRecordService recordService;
    @Autowired
    private TraineeInformationService traineeInformationService;//学员列表
    @Autowired
    private TraineeStatusService traineeStatusService;//学员状态表

    @Autowired
    private CoachTraineeRercordService coachTraineeRercordService;//教练学员分配表
    @Autowired
    private CoachManagementService coachManagementService;//教练表
    @Autowired
    private BizExceptionService exceptionService;

    @Autowired
    private SysMessageService messageService;//消息下发

    @Override
    protected Mapper<TraineeTestInfo> getBaseMapper() {
        return baseMapper;
    }

    /**
     * 考试成绩导入
     *
     * @param list
     * @param fileName
     * @return
     */
    @Override
    public Map<String, Object> impResultExcel(List<Map<Integer, String>> list, String fileName) {
        Map<String, Object> retMap = new HashMap<>();
        String key = genId();
        retMap.put("key", key);
        SysYh sysUser = getCurrentUser();
        List<Map<Integer, String>> resultList = new ArrayList<>();
        List<Map<String, String>> webList = new ArrayList<>();
        long succeedCount = 0;
        long errorCount = 0;
        for (Map<Integer, String> map : list) {
            Map<String, String> webMap = new HashMap<>();
            int mapSize = map.size();
            if (StringUtils.equals(map.get(0), "学员姓名")) {
                map.put(mapSize, "处理结果");
                map.put(mapSize + 1, "处理备注");
            } else {
//				学员姓名(0)|学习驾驶证明编号(1)|身份证明号码(2)|考试科目(3)|考试车型(4)|预约日期(5)|约考日期(6)|考试场地(7)|考试场次(8)|手机号码(9)|考试结果(10)|考试合格，考试结果他们可能不填或是填写合格，所以合格判断需要判断两个情况，考试结果为空也默认算合格(11)|(12)|(13)|(14)|(15)|null(16)|
                webMap.put("name", map.get(0));
                webMap.put("certificateNumber", map.get(1));//学习驾驶证明编号
                webMap.put("idCardNo", map.get(2));//身份证明号码
                webMap.put("subject", map.get(3));//考试科目
                webMap.put("carModel", map.get(4));//考试车型
                webMap.put("yyDate", map.get(5));//预约日期
                webMap.put("ykDate", map.get(6));//约考日期
                webMap.put("examinationSite", map.get(7));//考试场地
                webMap.put("examinationField", map.get(8));//考试场次
                webMap.put("dn", map.get(9));//手机号码

                ApiResponse<String> destineExcel = this.updateResultExcel(map, sysUser);

                if (StringUtils.isEmpty(map.get(10))) {
                    map.put(10, "合格");//合格
                }
                webMap.put("testResults", map.get(10));//考试结果

                if (destineExcel.isSuccess()) {
                    succeedCount++;
                    map.put(mapSize, "成功");
                    map.put(mapSize + 1, "处理成功");
                    webMap.put("success", "1");
                    webMap.put("message", "处理成功");
//					traineeInfo.getJgmc()+"@sfgeeq@"+trainStatus
                    String jgmc = "";//机构名称
                    String trainStatus = "";//培训状态
                    String subTestNums = "";//考试次数
                    String message = destineExcel.getMessage();
                    if (StringUtils.isNotEmpty(message)) {
                        String[] messages = message.split("@sfgeeq@");
                        jgmc = messages[0];
                        if (messages.length >= 3) {
                            trainStatus = messages[1];
                            subTestNums = messages[2];
                        }
                    }
                    webMap.put("jgmc", jgmc);
                    webMap.put("trainStatus", trainStatus);
                    webMap.put("subTestNums", subTestNums);
                } else {
                    errorCount++;
                    map.put(mapSize, "处理失败");
                    map.put(mapSize + 1, destineExcel.getMessage());
                    webMap.put("success", "0");
                    webMap.put("message", destineExcel.getMessage());
                }
            }
            resultList.add(map);
            if (webMap.size() > 0) {
                webList.add(webMap);
            }
        }

        retMap.put("errorCount", errorCount);
        retMap.put("succeedCount", succeedCount);
        retMap.put("list", webList);
        //放到redis中去
        redisDao.boundValueOps(key).set(JsonUtil.toJson(resultList), 1, TimeUnit.DAYS);
        redisDao.boundValueOps(key + "_name").set(fileName, 1, TimeUnit.DAYS);
//		更新教练名下所有学员的数量
        coachManagementService.updateCoachTraineeCount();
        return retMap;
    }

    @Override
    public Map<String, Object> newImpResultExcel(List<Map<Integer, String>> list, String fileName) {
        Map<String, Object> retMap = new HashMap<>();
        String key = genId();
        retMap.put("key", key);
        SysYh sysUser = getCurrentUser();
        List<Map<Integer, String>> resultList = new ArrayList<>();
        List<Map<String, String>> webList = new ArrayList<>();

        String errorKey = genId();
        List<Map<Integer, String>> errorList = new ArrayList<>();
        List<Map<Integer, String>> sucList = new ArrayList<>();

        Set<String> idCards = list.stream().map(integerStringMap -> integerStringMap.get(5)).collect(Collectors.toSet());
        SimpleCondition condition = new SimpleCondition(TraineeInformation.class);
        condition.in(TraineeInformation.InnerColumn.idCardNo, idCards);
        condition.notIn(TraineeInformation.InnerColumn.status, Arrays.asList("50", "60"));
        List<TraineeInformation> informations = traineeInformationService.findByCondition(condition);
        Map<String, List<TraineeInformation>> infoMap = informations.stream().collect(Collectors.groupingBy(TraineeInformation::getIdCardNo));
        List<TraineeInformation> resultMap = new ArrayList<>();
        int mapSize = 0;
        for (int i = 2; i < list.size(); i++) {
            Map<String, String> webMap = new HashMap<>();
            Map<Integer, String> map = list.get(i);
            mapSize = map.size();
            if (StringUtils.equals(map.get(2), "姓名")) {
                map.put(mapSize, "处理结果");
                map.put(mapSize + 1, "处理备注");
                map.put(mapSize + 2, "报名点");
                sucList.add(map);
                errorList.add(map);
            } else {
//				学员姓名(0)|学习驾驶证明编号(1)|身份证明号码(2)|考试科目(3)|考试车型(4)|预约日期(5)|约考日期(6)|考试场地(7)|考试场次(8)|手机号码(9)|考试结果(10)|考试合格，考试结果他们可能不填或是填写合格，所以合格判断需要判断两个情况，考试结果为空也默认算合格(11)|(12)|(13)|(14)|(15)|null(16)|
                webMap.put("name", map.get(2));
                webMap.put("certificateNumber", "");//学习驾驶证明编号
                webMap.put("idCardNo", map.get(5));//身份证明号码
                webMap.put("subject", map.get(4));//考试科目
                webMap.put("carModel", map.get(9));//考试车型
                webMap.put("yyDate", "");//预约日期
                if (StringUtils.isNotBlank(map.get(15)) && map.get(15).length() >= 10) {
                    webMap.put("ykDate", map.get(15).substring(0, 10));//约考日期
                } else {
                    webMap.put("ykDate", map.get(15));//约考日期
                }
                webMap.put("examinationSite", map.get(3));//考试场地
                webMap.put("examinationField", "");//考试场次
                webMap.put("dn", "");//手机号码
                TraineeInformation information = null;
                List<TraineeInformation> traineeInformations = infoMap.get(map.get(5));
                if (CollectionUtils.isNotEmpty(traineeInformations)) {
                    information = traineeInformations.get(0);
                }
                ApiResponse<String> destineExcel = this.newUpdateResultExcel(map, sysUser, information);

                if (StringUtils.isEmpty(map.get(16))) {
                    map.put(16, "合格");//合格
                }
                webMap.put("testResults", map.get(16));//考试结果

                if (destineExcel.isSuccess()) {
                    map.put(mapSize, "成功");
                    map.put(mapSize + 1, "处理成功");
                    webMap.put("success", "1");
                    webMap.put("message", "处理成功");
//					traineeInfo.getJgmc()+"@sfgeeq@"+trainStatus
                    String jgmc = "";//机构名称
                    String trainStatus = "";//培训状态
                    String subTestNums = "";//考试次数
                    String message = destineExcel.getMessage();
                    if (StringUtils.isNotEmpty(message)) {
                        if (!StringUtils.equals(message, "操作成功")) {
                            String[] messages = message.split("@sfgeeq@");
                            jgmc = messages[0];
                            if (StringUtils.isNotBlank(jgmc)) {
                                jgmc = jgmc.replaceAll("[\\u4E00-\\u9FA5]+", "").replaceAll("/", "").trim();
                            }
                            if (messages.length >= 3) {
                                trainStatus = messages[1];
                                subTestNums = messages[2];
                            }
                        }
                    }
                    if(information !=  null){
                        map.put(mapSize + 2, information.getJgmc().replaceAll("[\\u4E00-\\u9FA5]+", "").replaceAll("/", "").trim());
                        webMap.put("jgmc", information.getJgmc().replaceAll("[\\u4E00-\\u9FA5]+", "").replaceAll("/", "").trim());
                    }else{
                        map.put(mapSize + 2,"");
                        webMap.put("jgmc","");
                    }

                    webMap.put("trainStatus", trainStatus);
                    webMap.put("subTestNums", subTestNums);
                    sucList.add(map);
                    if(information == null){
                        information = new TraineeInformation();
                        information.setIdCardNo(map.get(5));
                        information.setName(map.get(2));
                        information.setJgmc("非本校学员");
                    }
                    TraineeTestInfo testinfo = new TraineeTestInfo();
                    testinfo.setTestTime(map.get(15));
                    testinfo.setTestPlace(map.get(3));
                    testinfo.setSubject(map.get(4));
                    information.setTestInfos(Arrays.asList(testinfo));
                    resultMap.add(information);
                } else {
                    map.put(mapSize, "处理失败");
                    map.put(mapSize + 1, destineExcel.getMessage());
                    webMap.put("success", "0");
                    webMap.put("message", destineExcel.getMessage());
                    String message = destineExcel.getMessage();
                    if (StringUtils.isNotEmpty(message)) {
                        String[] messages = message.split("@sfgeeq@");
                        map.put(mapSize + 2, messages[0]);
                    }
                    errorList.add(map);
                }
            }
            resultList.add(map);
            if (webMap.size() > 0) {
                webList.add(webMap);
            }
        }

        String testTime = list.get(3).get(15);
        if (StringUtils.isNotBlank(testTime) && testTime.length() >= 10) {
            testTime = testTime.substring(0, 10);//约考日期
        }
        condition = new SimpleCondition(TraineeTestInfo.class);
        condition.like(TraineeTestInfo.InnerColumn.testTime, appendZero(testTime));
        condition.like(TraineeTestInfo.InnerColumn.subject, list.get(3).get(4));
        condition.and().andCondition(" test_result is null or test_result = ''");
        condition.and().andCondition(" trainee_id is not null and trainee_id != '' ");
        List<TraineeTestInfo> testInfoList = findByCondition(condition); // 缺考学员按不合格处理

        int size = resultList.get(1).size();
        for (int i = 0; i < testInfoList.size(); i++) {
            Map<Integer, String> listMap = new HashMap<>();
            Map<String, String> webMap = new HashMap<>();
            TraineeTestInfo testInfo = testInfoList.get(i);
            listMap.put(1, "");
            listMap.put(2, testInfo.getTraineeName());
            listMap.put(3, testInfo.getTestPlace());
            listMap.put(4, testInfo.getSubject());
            listMap.put(5, testInfo.getIdCardNo());
            listMap.put(6, "");
            listMap.put(7, "");
            listMap.put(8, "");
            TraineeInformation information = traineeInformationService.findById(testInfo.getTraineeId());
            if (information != null) {
                listMap.put(9, information.getCarType());
            }
            listMap.put(10, "");
            listMap.put(11, "");
            listMap.put(12, "");
            listMap.put(13, "");
            listMap.put(14, "");
            listMap.put(15, testInfo.getTestTime());
            listMap.put(16, "不合格");
            listMap.put(17, "");
            listMap.put(18, "");
            listMap.put(19, "");
            listMap.put(20, "");
            listMap.put(21, "");
            listMap.put(size - 3, "处理成功");
            listMap.put(size - 2, "缺考记录按不合格处理");
            ApiResponse<String> destineExcel = this.newUpdateResultExcel(listMap, sysUser, information);

            webMap.put("name", listMap.get(2));
            webMap.put("certificateNumber", "");//学习驾驶证明编号
            webMap.put("idCardNo", listMap.get(5));//身份证明号码
            webMap.put("subject", listMap.get(4));//考试科目
            webMap.put("carModel", listMap.get(9));//考试车型
            webMap.put("yyDate", "");//预约日期
            if (StringUtils.isNotBlank(listMap.get(15)) && listMap.get(15).length() >= 10) {
                webMap.put("ykDate", listMap.get(15).substring(0, 10));//约考日期
            } else {
                webMap.put("ykDate", listMap.get(15));//约考日期
            }
            webMap.put("examinationSite", listMap.get(3));//考试场地
            webMap.put("examinationField", "");//考试场次
            webMap.put("dn", "");//手机号码
            webMap.put("testResults", "不合格");//考试结果

            if (destineExcel.isSuccess()) {
                webMap.put("success", "1");
                webMap.put("message", "处理成功，缺考以不合格处理");
                String jgmc = "";//机构名称
                String trainStatus = "";//培训状态
                String subTestNums = "";//考试次数
                String message = destineExcel.getMessage();
                if (StringUtils.isNotEmpty(message) && !StringUtils.equals(message, "操作成功")) {
                    String[] messages = message.split("@sfgeeq@");
                    jgmc = messages[0];
                    if (StringUtils.isNotBlank(jgmc)) {
                        jgmc = jgmc.replaceAll("[\\u4E00-\\u9FA5]+", "").replaceAll("/", "").trim();
                    }
                    if (messages.length >= 3) {
                        trainStatus = messages[1];
                        subTestNums = messages[2];
                    }
                }


                webMap.put("trainStatus", trainStatus);
                webMap.put("subTestNums", subTestNums);
                listMap.put(size - 1, jgmc);
                sucList.add(listMap);
                if(information == null){
                    information = new TraineeInformation();
                    information.setIdCardNo(listMap.get(5));
                    information.setName(listMap.get(2));
                    information.setJgmc("非本校学员");
                }
                TraineeTestInfo testinfo = new TraineeTestInfo();
                testinfo.setTestTime(listMap.get(15));
                testinfo.setTestPlace(listMap.get(3));
                testinfo.setSubject(listMap.get(4));
                information.setTestInfos(Arrays.asList(testinfo));
                resultMap.add(information);
            } else {
                webMap.put("success", "0");
                webMap.put("message", destineExcel.getMessage());
            }
            resultList.add(listMap);

            if (webMap.size() > 0) {
                webList.add(webMap);
            }
        }

        retMap.put("errorCount", errorList.size() - 1);

        retMap.put("list", resultMap);
        retMap.put("errorKey", errorKey);

        int size1 = errorList.get(0).size();
        errorList.stream().sorted(Comparator.comparing(o2 -> o2.get(size1 - 1)));
        List<Map<Integer, String>> collect = new ArrayList<>();
        if (CollectionUtils.size(sucList) >= 2) {
            Map<Integer, String> integerStringMap = sucList.get(0);
            collect.add(integerStringMap);
            List<Map<Integer, String>> maps = sucList.subList(1, sucList.size());
            maps.forEach(integerStringMap1 -> {
                integerStringMap1.put(size1-1,integerStringMap1.get(size1 -1).replace("A", "998"));
                integerStringMap1.put(size1-1,integerStringMap1.get(size1 -1).replace("B", "999"));
            });
            List<Map<Integer, String>> collect1 = maps.stream().filter(integerStringMap1 -> StringUtils.isNotBlank(integerStringMap1.get(size1 - 1))).sorted(Comparator.comparing(o -> Integer.parseInt(o.get(size1 - 1)))).collect(Collectors.toList());
            collect1.forEach(integerStringMap1 -> {
                integerStringMap1.put(size1-1,integerStringMap1.get(size1 -1).replace("998", "A"));
                integerStringMap1.put(size1-1,integerStringMap1.get(size1 -1).replace("999", "B"));
            });
            List<Map<Integer, String>> mapList = maps.stream().filter(m -> StringUtils.isBlank(m.get(size1 - 1))).collect(Collectors.toList());
            collect.addAll(collect1);
            collect.addAll(mapList);
        }
        // 将 报名点的记录放在第一条 ， 其余依次往后排
        List<Map<Integer,String>> tempList = new ArrayList<>();
        for (Map<Integer, String> integerStringMap : collect) {
            Map<Integer,String> tempMap = new HashMap<>();
            for (Map.Entry<Integer, String> entry : integerStringMap.entrySet()) {
                Integer entryKey = entry.getKey();
                String value = entry.getValue();
                if(entryKey < (size1-1)){
                    tempMap.put(entryKey+1, value);
                }else if (entryKey == (size1- 1)){
                    tempMap.put(0,value);
                }else{
                    tempMap.put(entryKey, value);
                }
            }
            tempList.add(tempMap);
        }
        collect = tempList;
        collect.forEach(integerStringMap -> {
            if(StringUtils.isBlank(integerStringMap.get(0))){
                integerStringMap.put(0, "非本校学院");
            }
        });
        //  放到redis中去
        retMap.put("succeedCount", collect.size() - 1);
        redisDao.boundValueOps(errorKey).set(JsonUtil.toJson(errorList), 30, TimeUnit.DAYS);

        redisDao.boundValueOps(errorKey + "_name").set(DateUtils.getToday("yyyy-MM-dd") + "-test-fail", 30, TimeUnit.DAYS);

        //放到redis中去
        redisDao.boundValueOps(key).set(JsonUtil.toJson(collect), 30, TimeUnit.DAYS);

        redisDao.boundValueOps(key + "_name").set(DateUtils.getToday("yyyy-MM-dd") + "-test-suc", 30, TimeUnit.DAYS);
//		更新教练名下所有学员的数量
        coachManagementService.updateCoachTraineeCount();

        if (CollectionUtils.size(collect) > 1) {
            FileUploadRecord uploadRecord = new FileUploadRecord();
            uploadRecord.setId(genId());
            uploadRecord.setSucKey(key);
            uploadRecord.setCjr(sysUser.getZh() + "-" + sysUser.getXm());
            uploadRecord.setCjsj(DateUtils.getNowTime());
            uploadRecord.setLx("20");
            recordService.save(uploadRecord);
        }
        return retMap;
    }

    @Override
    public ApiResponse<String> newUpdateResultExcel(Map<Integer, String> map, SysYh sysUser, TraineeInformation information) {
        //		1、非空验证
        Map<String, String> kmMap = new HashMap<>();
        kmMap.put("科目一", "10");
        kmMap.put("科目二", "20");
        kmMap.put("科目三", "30");
        kmMap.put("科目四", "40");
        kmMap.put("科目三安全文明常识考试", "40");
        kmMap.put("10", "1");
        kmMap.put("20", "2");
        kmMap.put("30", "3");
        kmMap.put("40", "4");
        //		1、有效性验证
//        if (StringUtils.isBlank(map.get(2))) {
//            return ApiResponse.fail("学员姓名不能为空");
//        }

        if (StringUtils.isBlank(map.get(5))) {
            return ApiResponse.fail("身份证明号码不能为空");
        }
        if (StringUtils.isBlank(map.get(4))) {
            return ApiResponse.fail("考试科目不能为空");
        }
        String kmCode = kmMap.get(map.get(4).replaceAll(" ", ""));

        if (StringUtils.isBlank(kmCode)) {
            return ApiResponse.fail("考试科目:" + map.get(4) + " 转换考试科目编码异常，请联系管理人员");
        }
       /* if (StringUtils.isBlank(map.get(9))) {
            return ApiResponse.fail("考试车型不能为空");
        }*/

        if (StringUtils.isBlank(map.get(15))) {
            return ApiResponse.fail("约考日期不能为空");
        }

        // 如果是科目四 并且合格的话 会将状态改为 结业
//		2、查找到学员ID
        if (information == null) {
            if (StringUtils.equals(kmCode, "40")) {
                SimpleCondition condition = new SimpleCondition(TraineeInformation.class);
                condition.eq(TraineeInformation.InnerColumn.idCardNo, map.get(5));
                condition.setOrderByClause(" id  desc ");
                List<TraineeInformation> list = traineeInformationService.findByCondition(condition);
                if (CollectionUtils.isNotEmpty(list)) {
                    information = list.get(0);
                }
            }
            if (information == null) {
                // 未找到学员 记录异常 ， 不抛出异常
                BizException exception = new BizException();
                exception.setSfzmhm(map.get(5));
                exception.setId(genId());
                exception.setCode("991");
                exception.setCjr(sysUser.getZh() + "-" + sysUser.getXm());
                exception.setCjsj(DateUtils.getNowTime());
                exception.setKskm(kmMap.get(kmCode));
                exception.setXm(map.get(2));
                exceptionService.saveException(exception);
            }
        } else if (StringUtils.equals(information.getStatus(), "99")) {
            // 未找到学员 记录异常 ， 不抛出异常
            BizException exception = new BizException();
            exception.setSfzmhm(map.get(5));
            exception.setId(genId());
            exception.setCode("002");
            exception.setCjr(sysUser.getZh() + "-" + sysUser.getXm());
            exception.setCjsj(DateUtils.getNowTime());
            exception.setKskm(kmMap.get(kmCode));
            exception.setXm(map.get(2));
            exceptionService.saveException(exception);
        } else if (StringUtils.equals(information.getArrearage(), "10")) {
            BizException exception = new BizException();
            exception.setSfzmhm(map.get(5));
            exception.setId(genId());
            exception.setCode("903");
            exception.setCjr(sysUser.getZh() + "-" + sysUser.getXm());
            exception.setCjsj(DateUtils.getNowTime());
            exception.setKskm(kmMap.get(kmCode));
            exception.setXm(map.get(2));
            exceptionService.saveException(exception);
        }
        if (information != null) {
//		3、学员ID有效性验证，必须未结业和未退学的，

            if (StringUtils.isBlank(information.getSerialNum())) {
                BizException exception = new BizException();
                exception.setSfzmhm(map.get(5));
                exception.setId(genId());
                exception.setCode("003");
                exception.setCjr(sysUser.getZh() + "-" + sysUser.getXm());
                exception.setCjsj(DateUtils.getNowTime());
                exception.setKskm(kmMap.get(kmCode));
                exception.setXm(map.get(2));
                exceptionService.saveException(exception);
            }

            String trainStatus = "";
            String subTestNums = "";
            //		4、找到约考记录
            SimpleCondition condition = new SimpleCondition(TraineeTestInfo.class);
            condition.eq(TraineeTestInfo.InnerColumn.idCardNo, map.get(5));
//            condition.eq(TraineeTestInfo.InnerColumn.traineeId, information.getId());//学员ID
            condition.eq(TraineeTestInfo.InnerColumn.subject, map.get(4));//科目
//            condition.and().andCondition(" test_result is null or test_result =''");
            if (StringUtils.isNotBlank(map.get(15)) && map.get(15).length() > 10) {
                condition.like(TraineeTestInfo.InnerColumn.testTime, appendZero(map.get(15).substring(0, 10)));//约考时间
            } else {
                condition.like(TraineeTestInfo.InnerColumn.testTime, appendZero(map.get(15)));
            }
            condition.setOrderByClause(TraineeTestInfo.InnerColumn.id.desc());
            List<TraineeTestInfo> orgs = findByCondition(condition);
            TraineeTestInfo obj;

            if (orgs != null && orgs.size() > 0) {
                obj = orgs.get(0);
                if (StringUtils.equals(kmCode, "10")) {
                    trainStatus = information.getFirSubTrainStatus() + "";
                    subTestNums = information.getFirSubTestNum() + "";
                } else if (StringUtils.equals(kmCode, "20")) {
                    trainStatus = information.getSecSubTrainStatus() + "";
                    subTestNums = information.getSecSubTestNum() + "";
                } else if (StringUtils.equals(kmCode, "30")) {
                    trainStatus = information.getThirdSubTrainStatus() + "";
                    subTestNums = information.getThirdSubTestNum() + "";
                }
            } else {
                return ApiResponse.fail("未找到学员的约考记录 ， 请检查约考时间，身份证号码 ， 考试科目是否正确");
            }
            if (!StringUtils.equals(information.getClassType(), "60")) {
                if (StringUtils.equals(kmCode, "10") && StringUtils.isBlank(information.getFirSubPaymentTime()) && information.getFirSubTestNum() <= 1) {
                    BizException exception = new BizException();
                    exception.setSfzmhm(map.get(5));
                    exception.setId(genId());
                    exception.setCode("101");
                    exception.setCjr(sysUser.getZh() + "-" + sysUser.getXm());
                    exception.setCjsj(DateUtils.getNowTime());
                    exception.setKskm(kmMap.get(kmCode));
                    exception.setXm(map.get(2));
                    exceptionService.saveException(exception);
                } else if (StringUtils.equals(kmCode, "20") && StringUtils.isBlank(information.getSecSubPaymentTime()) && information.getSecSubTestNum() <= 1) {
                    BizException exception = new BizException();
                    exception.setSfzmhm(map.get(5));
                    exception.setId(genId());
                    exception.setCode("201");
                    exception.setCjr(sysUser.getZh() + "-" + sysUser.getXm());
                    exception.setCjsj(DateUtils.getNowTime());
                    exception.setKskm(kmMap.get(kmCode));
                    exception.setXm(map.get(2));
                    exceptionService.saveException(exception);
                } else if (StringUtils.equals(kmCode, "30") && StringUtils.isBlank(information.getThirdSubPaymentTime()) && information.getThirdSubTestNum() <= 1) {
                    BizException exception = new BizException();
                    exception.setSfzmhm(map.get(5));
                    exception.setId(genId());
                    exception.setCode("301");
                    exception.setCjr(sysUser.getZh() + "-" + sysUser.getXm());
                    exception.setCjsj(DateUtils.getNowTime());
                    exception.setKskm(kmMap.get(kmCode));
                    exception.setXm(map.get(2));
                    exceptionService.saveException(exception);
                }
            }

            Date date = null;
            try {
                date = DateUtils.getDate(obj.getTestTime(), "yyyy-MM-dd");
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //		5、修改考试表状态
            String testResult = "00";//00 合格  10不合格
            if (StringUtils.isNotEmpty(map.get(16))) {
                if (StringUtils.equals(map.get(16).trim(), "不合格") || StringUtils.equals(map.get(16).trim(), "缺考")) {
                    testResult = "10";
                } else if (StringUtils.equals(map.get(16).trim(), "合格")) {
                    obj.setPayStatus("00");
                } else {
                    return ApiResponse.fail("考试结果状态不对。考试结果：" + map.get(16));
                }
            }
            if (CollectionUtils.isNotEmpty(orgs)) {
                for (TraineeTestInfo org : orgs) {
                    org.setTestResult(testResult);
                    org.setOperator(sysUser.getZh() + "-" + sysUser.getXm());
                    org.setOperateTime(DateUtils.getNowTime());
                    update(org);
                }
            }
            obj.setTestResult(testResult);//考试结果

            TraineeTestInfo info = findById(obj.getId());
            int i;
            if (ObjectUtils.isEmpty(info)) {
                i = save(obj);
            } else {
                obj.setOperator(sysUser.getZh() + "-" + sysUser.getXm());
                obj.setOperateTime(DateUtils.getNowTime());
                i = update(obj);
            }

            if (i == 0) {
                return ApiResponse.fail("修改数据库失败");
            }
            String messageBody = "";
            String userXb = "先生";//性别 /* 00: 女  10: 男*/
            if (!StringUtils.equals(information.getGender(), "10")) {
                userXb = "女士";
            }
//		6、修改用户表状态
            if (StringUtils.equals(testResult, "00")) {//成功
                if (StringUtils.equals(kmCode, "10")) {
                    //科目一成功后，系统自动跳到下一个科目中
                    // 判断当前状态是否需要修改 , 可能成绩导入会在后面
                    if (StringUtils.equals(information.getStatus(), "10")) {
                        information.setStatus("20");
                    }
                    //科目一成功后，将状态修改为合格
                    information.setFirSub("40");//00：已缴费 10：培训 20: 已约考 30：不合格 40：合格
//				合格后，设置  学员有效期开始时间和结束时间
                    information.setIndateStartTime(obj.getTestTime());//约考时间
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    calendar.add(Calendar.YEAR, 3);
                    date = calendar.getTime();
                    information.setIndateEndTime(DateUtils.getDateStr(date, "yyyy-MM-dd"));
                    messageBody = "{\"first\":\"尊敬的" + information.getName() + userXb + "：\",\"keyword1\":\"科目一成绩合格确认\",\"keyword2\":\"科目一考试成绩已合格\",\"remark\":\"恭喜您，科目一考试成绩已合格。培训请联系报名负责人，并可通过“学员助手”登录系统查看培训、考试状态。如需帮助，请致电客服热线：400-133-2133。\"}";

                } else if (StringUtils.equals(kmCode, "20")) {
                    // 科目二考试考试合格后，查看科目三是否合格
                    if (StringUtils.equals(information.getThirdSub(), "40")) {
                        information.setStatus("40");
                    } else {
                        information.setStatus("30");
                    }
                    information.setSecSub("40");//00: 培训 10：已约考 20：已缴费 30：不合格 40：合格
                    messageBody = "{\"first\":\"尊敬的" + information.getName() + userXb + "：\",\"keyword1\":\"科目二成绩合格确认\",\"keyword2\":\"科目二考试成绩已合格\",\"remark\":\"恭喜您，科目二考试成绩已合格。科目三培训请联系报名负责人，并可通过“学员助手”登录系统查看培训、考试状态。如需帮助，请致电客服热线：400-133-2133。\"}";
                } else if (StringUtils.equals(kmCode, "30")) {
                    if (StringUtils.equals(information.getSecSub(), "40")) {
                        information.setStatus("40");
                    } else {
                        information.setStatus("20");
                    }
                    information.setThirdSub("40");//00: 培训 10：已约考 20：已缴费 30：不合格 40：合格
                    messageBody = "{\"first\":\"尊敬的" + information.getName() + userXb + "：\",\"keyword1\":\"科目三成绩合格确认\",\"keyword2\":\"科目三考试成绩已合格\",\"remark\":\"恭喜您，科目三考试成绩已合格。您可通过“交管12123”预约科目三安全文明常识考试，考场请选择“新农科目一考场”。如需帮助，请联系报名负责人或致电客服热线：400-133-2133。\"}";
                } else if (StringUtils.equals(kmCode, "40")) {
                    information.setForthSub("20");//00：已约考 :10: 不合格 20：合格
                    information.setStatus("50");
                    messageBody = "{\"first\":\"尊敬的" + information.getName() + userXb + "：\",\"keyword1\":\"科目三安全文明常识成绩合格确认\",\"keyword2\":\"科目三安全文明常识成绩已合格\",\"remark\":\"恭喜您，顺利结业拿证，关注明涛驾校微信公众号，掌握驾车安全小常识，并可通过“学员助手”登录系统，邀请好友学车，报名成功更有红包惊喜。如需帮助，请致电客服热线：400-133-2133。\"}";
                }

            } else {
                if (StringUtils.equals(kmCode, "10")) {
                    information.setFirSub("30");
                    messageBody = "{\"first\":\"尊敬的" + information.getName() + userXb + "：\",\"keyword1\":\"科目一成绩不合格确认\",\"keyword2\":\"科目一考试成绩不合格\",\"remark\":\"很遗憾，科目一考试成绩不合格。再次预约、缴纳补考费请联系报名负责人，如需理论培优练习、帮助可致电客服热线：400-133-2133。\"}";
                } else if (StringUtils.equals(kmCode, "20")) {
                    information.setSecSub("30");
                    messageBody = "{\"first\":\"尊敬的" + information.getName() + userXb + "：\",\"keyword1\":\"科目二成绩不合格确认\",\"keyword2\":\"科目二考试成绩不合格\",\"remark\":\"很遗憾，科目二考试成绩不合格。再次预约、缴纳补考费、培训请联系报名负责人，如需帮助可致电客服热线：400-133-2133。\"}";
                } else if (StringUtils.equals(kmCode, "30")) {
                    information.setThirdSub("30");
                    messageBody = "{\"first\":\"尊敬的" + information.getName() + userXb + "：\",\"keyword1\":\"科目三成绩不合格确认\",\"keyword2\":\"科目三考试成绩不合格\",\"remark\":\"很遗憾，科目三考试成绩不合格。再次预约、缴纳补考费、培训请联系报名负责人，如需帮助可致电客服热线：400-133-2133。\"}";
                } else if (StringUtils.equals(kmCode, "40")) {
                    information.setForthSub("10");
                    messageBody = "{\"first\":\"尊敬的" + information.getName() + userXb + "：\",\"keyword1\":\"科目三安全文明常识成绩不合格确认\",\"keyword2\":\"科目三安全文明常识考试成绩不合格\",\"remark\":\"很遗憾，科目三安全文明常识考试成绩不合格。再次预约请联系报名负责人，如需理论培优练习、帮助可致电客服热线：400-133-2133。\"}";
                }
            }
            traineeInformationService.update(information);

            //		7、插入将约考成功的消息插入消息表
            // 如果是第二次导入则不处理
            if (!StringUtils.equals(obj.getTestResult(), info.getTestResult())) {
                SysMessage message = new SysMessage();
                message.setTitle("考试成绩导入");
                message.setParameterBody(messageBody);//参数
                message.setBizId("xy001");//业务ID
                message.setUserId(information.getId());//接收者USER_ID
                message.setUserName(information.getName());//接收者USER_ID
                message.setUserRole("1");//1、学员 2、教练 3、管理员
                messageService.sendMessage(message, information.getOpenId(), information.getPhone());
            }

//		7、插入学员状态表(学员日志表)
            String status = "00";
            if (StringUtils.equals(testResult, "10")) { // 考试不成功
                status = "10";
            }
            String type = map.get(3) + "约考成绩导入";
            traineeStatusService.saveEntity(information, type, status, "excel考试成绩批量导入");

//		8、将教练学员分配表(coach_trainee_rercord) 对应的科目设置为合格
            SimpleCondition coachTraineeRercordCondition = new SimpleCondition(CoachTraineeRercord.class);
            coachTraineeRercordCondition.eq(CoachTraineeRercord.InnerColumn.traineeId, information.getId());
            coachTraineeRercordCondition.eq(CoachTraineeRercord.InnerColumn.status, "00");
            coachTraineeRercordCondition.eq(CoachTraineeRercord.InnerColumn.allotSub, "0" + StringUtils.substring(kmCode, 0, 1));//科目
            List<CoachTraineeRercord> coachTraineeRercordList = coachTraineeRercordService.findByCondition(coachTraineeRercordCondition);
            if (coachTraineeRercordList != null && coachTraineeRercordList.size() > 0) {
                CoachTraineeRercord coachTraineeRercord = coachTraineeRercordList.get(0);
                coachTraineeRercord.setStatus("10");
                coachTraineeRercord.setModifier(sysUser.getZh() + "-" + sysUser.getXm());
                coachTraineeRercord.setModifyTime(DateUtils.getNowTime());
                coachTraineeRercordService.update(coachTraineeRercord);
            }
            BizException exception = new BizException();
            exception.setSfzmhm(map.get(5));
            exception.setKskm(kmMap.get(kmCode));
            exception.setXm(map.get(2));
            if(StringUtils.equals(kmCode,"科目一")){
                exception.setCode("102");
                exceptionService.clearException(exception, exception.getCode());
            }else if(StringUtils.equals(kmCode, "科目二")){
                exception.setCode("202");
                exceptionService.clearException(exception, exception.getCode());
            }else if(StringUtils.equals(kmCode, "科目三")){
                exception.setCode("302");
                exceptionService.clearException(exception, exception.getCode());
            }
            return ApiResponse.success(information.getJgmc() + "@sfgeeq@" + trainStatus + "@sfgeeq@" + subTestNums);
        } else {
            //		4、找到约考记录
            SimpleCondition condition = new SimpleCondition(TraineeTestInfo.class);
            condition.eq(TraineeTestInfo.InnerColumn.idCardNo, map.get(5));
//            condition.eq(TraineeTestInfo.InnerColumn.traineeId, information.getId());//学员ID
            condition.eq(TraineeTestInfo.InnerColumn.subject, map.get(4));//科目
            condition.and().andCondition(" test_result is null or test_result =''");
            if (StringUtils.isNotBlank(map.get(15)) && map.get(15).length() > 10) {
                condition.like(TraineeTestInfo.InnerColumn.testTime, appendZero(map.get(15).substring(0, 10)));//约考时间
            } else {
                condition.like(TraineeTestInfo.InnerColumn.testTime, appendZero(map.get(15)));
            }
            condition.setOrderByClause(TraineeTestInfo.InnerColumn.id.desc());
            List<TraineeTestInfo> orgs = findByCondition(condition);
            //		5、修改考试表状态
            String testResult = "00"; //00 合格  10不合格
            if (StringUtils.isNotEmpty(map.get(16))) {
                if (StringUtils.equals(map.get(16).trim(), "不合格") || StringUtils.equals(map.get(16).trim(), "缺考")) {
                    testResult = "10";
                }else if(StringUtils.equals(map.get(16).trim(), "合格")){
                    testResult = "00";
                } else {
                    return ApiResponse.fail("考试结果状态不对。考试结果：" + map.get(16));
                }
            }
            if (CollectionUtils.isNotEmpty(orgs)) {
                for (TraineeTestInfo org : orgs) {
                    org.setTestResult(testResult);
                    org.setOperator(sysUser.getZh() + "-" + sysUser.getXm());
                    org.setOperateTime(DateUtils.getNowTime());
                    update(org);
                }
            }

            return ApiResponse.success();
        }

    }

    @Override
    public List<TraineeTestInfo> getInfo(String jgdm, String startTime, String endTime, String km) {
        return baseMapper.getInfo(jgdm, startTime, endTime, km);
    }


    /**
     * 约考信息导入
     *
     * @param list
     * @param fileName
     * @return
     */
    @Override
    public Map<String, Object> impDestineExcel(List<Map<Integer, String>> list, String fileName) {
        Map<String, Object> retMap = new HashMap<>();
        String key = genId();
        retMap.put("key", key);
        SysYh sysUser = getCurrentUser();
        List<Map<Integer, String>> resultList = new ArrayList<>();
        List<Map<String, String>> webList = new ArrayList<>();
        String errorKey = genId();
        List<Map<Integer, String>> errorList = new ArrayList<>();
        // 根据身份证号查询到所有的在办学员信息
        SimpleCondition condition = new SimpleCondition(TraineeInformation.class);
        Set<String> idCards = list.stream().map(m -> m.get(2)).collect(Collectors.toSet());
        condition.in(TraineeInformation.InnerColumn.idCardNo, idCards);
        condition.notIn(TraineeInformation.InnerColumn.status, Arrays.asList("50", "60"));
        List<TraineeInformation> infos = traineeInformationService.findByCondition(condition);
        Map<String, List<TraineeInformation>> listMap = infos.stream().collect(Collectors.groupingBy(TraineeInformation::getIdCardNo));
        int mapSize = 0;
        List<TraineeInformation> resultMap = new ArrayList<>();
        for (Map<Integer, String> map : list) {
            Map<Integer, String> tableNameInfo = Maps.newLinkedHashMap();
            Map<String, String> webMap = new HashMap<>();
            mapSize = map.size();
            if (StringUtils.equals(map.get(0), "学员姓名")) {
                map.put(mapSize, "处理结果");
                map.put(mapSize + 1, "处理备注");
                map.put(mapSize + 2, "报名点");
                map.put(mapSize + 3, "推荐人");
                map.put(mapSize + 4, "报名时间");
                map.put(mapSize + 5, "报名金额");
                map.put(mapSize + 6, "实收金额");
                map.put(mapSize + 7, "欠费金额");
                map.put(mapSize + 8, "初考/补考");
                resultList.add(map);
                errorList.add(map);
            } else {
                TraineeInformation information = null;
                List<TraineeInformation> informations = listMap.get(map.get(2));
                if (CollectionUtils.isNotEmpty(informations)) {
                    information = informations.get(0);
                    map.put(mapSize + 2, information.getJgmc());
                    if (StringUtils.isNotBlank(information.getReferrer())) {
                        map.put(mapSize + 3, information.getReferrer().split("-")[0]);
                    }
                }
                //学员姓名(0)|学习驾驶证明编号(1)|身份证明号码(2)|考试科目(3)|考试车型(4)|预约日期(5)|约考日期(6)|考试场地(7)|考试场次(8)|手机号码(9)|null(10)|
                webMap.put("name", map.get(0));
                webMap.put("certificateNumber", map.get(1));//学习驾驶证明编号
                webMap.put("idCardNo", map.get(2));//身份证明号码
                webMap.put("subject", map.get(3));//考试科目
                webMap.put("carModel", map.get(4));//考试车型
                webMap.put("yyDate", map.get(5));//预约日期
                webMap.put("ykDate", map.get(6));//约考日期
                webMap.put("examinationSite", map.get(7));//考试场地
                webMap.put("examinationField", map.get(8));//考试场次
                webMap.put("dn", map.get(9));//手机号码
                tableNameInfo.put(0, map.get(0));
                tableNameInfo.put(1, map.get(1));//学习驾驶证明编号
                tableNameInfo.put(2, map.get(2));//身份证明号码
                tableNameInfo.put(3, map.get(3));//考试科目
                tableNameInfo.put(4, map.get(4));//考试车型
                tableNameInfo.put(5, map.get(5));//预约日期
                tableNameInfo.put(6, map.get(6));//约考日期
                tableNameInfo.put(7, map.get(7));//考试场地
                tableNameInfo.put(8, map.get(8));//考试场次
                tableNameInfo.put(9, map.get(9));//手机号码

                ApiResponse<String> destineExcel = this.updateDestineExcel(map, sysUser, information);

                if (destineExcel.isSuccess()) {
                    map.put(mapSize, "成功");
                    map.put(mapSize + 1, "处理成功");
                    webMap.put("success", "1");
                    webMap.put("message", "处理成功");
                    String jgmc = "";//机构名称
                    String trainStatus = "";//培训状态
                    String subTestNums = "";//考试次数
                    String reffer = ""; // 推荐人
                    String registrationTime = ""; // 报名时间
                    String regFee = "";
                    String realFee = "";
                    String arFee = "";
                    String message = destineExcel.getMessage();
                    if (StringUtils.isNotEmpty(message)) {
                        if (!message.equals("操作成功")) {
                            String[] messages = message.split("@sfgeeq@", -1);
                            jgmc = messages[0];
                            if (StringUtils.isNotBlank(jgmc)) {
                                jgmc = jgmc.replaceAll("[\\u4E00-\\u9FA5]+", "").replaceAll("/", "").trim();
                            }
                            if (messages.length >= 3) {
                                trainStatus = messages[1];
                                subTestNums = messages[2];
                                reffer = messages[3];
                                registrationTime = messages[4];
                                regFee = messages[5];
                                realFee = messages[6];
                                arFee = messages[7];
                            }
                        }
                    }
                    map.put(mapSize + 2, jgmc);
                    map.put(mapSize + 3, reffer);
                    map.put(mapSize + 4, registrationTime);
                    map.put(mapSize + 5, regFee);
                    map.put(mapSize + 6, realFee);
                    map.put(mapSize + 7, arFee);
                    if (StringUtils.isNotBlank(subTestNums)) {
                        map.put(mapSize + 8, Integer.parseInt(subTestNums) <= 0 ? "初考" : "补考");
                    }
                    webMap.put("jgmc", jgmc);
                    webMap.put("trainStatus", trainStatus);
                    webMap.put("subTestNums", subTestNums);
                    webMap.put("reffer", reffer);
                    webMap.put("registrationTime", registrationTime);
                    webMap.put("regFee", regFee);
                    webMap.put("realFee", realFee);
                    webMap.put("arFee", arFee);
                    resultList.add(map);
                    TraineeTestInfo testInfo = new TraineeTestInfo();
                    testInfo.setSubject(map.get(3));
                    testInfo.setTestPlace(map.get(7));
                    testInfo.setTestTime(map.get(6));
                    if(information == null){
                        information = new TraineeInformation();
                        information.setIdCardNo(map.get(2));
                        information.setName(map.get(0));
                        information.setJgmc("非本校学员");
                    }
                    information.setTestInfos(Arrays.asList(testInfo));
                    resultMap.add(information);
                } else {
                    webMap.put("success", "0");
                    webMap.put("message", destineExcel.getMessage());
                    tableNameInfo.put(mapSize, "处理失败");
                    tableNameInfo.put(mapSize + 1, destineExcel.getMessage());
                    errorList.add(tableNameInfo);
                }
            }
            if (webMap.size() > 0) {
                webList.add(webMap);
            }
        }

        retMap.put("errorCount", errorList.size() - 1);
        retMap.put("list", resultMap);
        retMap.put("errorKey", errorKey);
        int finalMapSize = mapSize;

        redisDao.boundValueOps(errorKey).set(JsonUtil.toJson(errorList), 30, TimeUnit.DAYS);
        redisDao.boundValueOps(errorKey + "_name").set(DateUtils.getToday("yyyy-MM-dd") + "-appoint-fail", 30, TimeUnit.DAYS);
        List<Map<Integer, String>> subList = resultList.subList(1, resultList.size());
        subList.forEach(integerStringMap -> {
            integerStringMap.put(finalMapSize+2,integerStringMap.get(finalMapSize + 2).replace("A", "998"));
            integerStringMap.put(finalMapSize+2,integerStringMap.get(finalMapSize + 2).replace("B", "999"));
        });
        List<Map<Integer, String>> maps = subList.stream().filter(integerStringMap -> StringUtils.isNotBlank(integerStringMap.get(finalMapSize + 2))).sorted(Comparator.comparing(o -> Integer.parseInt(o.get(finalMapSize + 2)))).collect(Collectors.toList());
        maps.forEach(integerStringMap -> {
            integerStringMap.put(finalMapSize+2,integerStringMap.get(finalMapSize + 2).replace("998", "A"));
            integerStringMap.put(finalMapSize+2,integerStringMap.get(finalMapSize + 2).replace("999", "B"));
        });
        List<Map<Integer, String>> mapList = subList.stream().filter(m -> StringUtils.isBlank(m.get(finalMapSize + 2))).collect(Collectors.toList());
        Map<Integer, String> map = resultList.get(0);
        resultList = new ArrayList<>();
        resultList.add(map);
        resultList.addAll(maps);
        resultList.addAll(mapList);

        // 将 报名点的记录放在第一条 ， 其余依次往后排
        List<Map<Integer,String>> tempList = new ArrayList<>();
        for (Map<Integer, String> integerStringMap : resultList) {
            Map<Integer,String> tempMap = new HashMap<>();
            for (Map.Entry<Integer, String> entry : integerStringMap.entrySet()) {
                Integer entryKey = entry.getKey();
                String value = entry.getValue();
                if(entryKey < (finalMapSize + 2)){
                    tempMap.put(entryKey+1, value);
                }else if (entryKey == (finalMapSize + 2)){
                    tempMap.put(0,value);
                }else{
                    tempMap.put(entryKey, value);
                }
            }
            tempList.add(tempMap);
        }
        resultList = tempList;
        resultList.forEach(integerStringMap -> {
            if(StringUtils.isBlank(integerStringMap.get(0))){
                integerStringMap.put(0, "非本校学院");
            }
        });
        //放到redis中去
        retMap.put("succeedCount", resultList.size() - 1);
        redisDao.boundValueOps(key).set(JsonUtil.toJson(resultList), 30, TimeUnit.DAYS);

        redisDao.boundValueOps(key + "_name").set(DateUtils.getToday("yyyy-MM-dd") + "-appoint-suc", 30, TimeUnit.DAYS);

        if (CollectionUtils.size(resultList) > 1) {
            FileUploadRecord uploadRecord = new FileUploadRecord();
            uploadRecord.setId(genId());
            uploadRecord.setSucKey(key);
            uploadRecord.setCjr(sysUser.getZh() + "-" + sysUser.getXm());
            uploadRecord.setCjsj(DateUtils.getNowTime());
            uploadRecord.setLx("10");
            recordService.save(uploadRecord);
        }

        return retMap;
    }

    /**
     * 约考信息处理
     * //学员姓名(0)|学习驾驶证明编号(1)|身份证明号码(2)|考试科目(3)|考试车型(4)|预约日期(5)|约考日期(6)|考试场地(7)|考试场次(8)|手机号码(9)|null(10)|
     *
     * @param map
     * @param sysUser
     * @return
     */
    private ApiResponse<String> updateDestineExcel(Map<Integer, String> map, SysYh sysUser, TraineeInformation information) {
        Map<String, String> kmMap = new HashMap<>();
        kmMap.put("科目一", "10");
        kmMap.put("科目二", "20");
        kmMap.put("科目三", "30");
        kmMap.put("科目四", "40");
        kmMap.put("科目三安全文明常识考试", "40");
        kmMap.put("10", "1");
        kmMap.put("20", "2");
        kmMap.put("30", "3");
        kmMap.put("40", "4");
        // 预约错误记录
        YyCwjl cwjl = new YyCwjl();
        cwjl.setId(genId());
        cwjl.setCjsj(DateUtils.getNowTime());
        cwjl.setCjr(sysUser.getZh() + "-" + sysUser.getXm());
//		1、有效性验证
  /*      if (StringUtils.isBlank(map.get(0))) {
            return ApiResponse.fail("学员姓名不为空");
        }*/

        if (StringUtils.isBlank(map.get(2))) {
            return ApiResponse.fail("身份证明号码不为空");
        }
        if (StringUtils.isBlank(map.get(3))) {
            return ApiResponse.fail("考试科目不为空");
        }
        String kmCode = kmMap.get(map.get(3).replaceAll(" ", ""));

        if (StringUtils.isBlank(kmCode)) {
            return ApiResponse.fail("考试科目:" + map.get(3) + " 转换考试科目编码异常，请联系管理人员");
        }
    /*    if (StringUtils.isBlank(map.get(4))) {
            return ApiResponse.fail("考试车型不为空");
        }*/

       /* if (StringUtils.isBlank(map.get(5))) {
            return ApiResponse.fail("预约日期不为空");
        }*/

        if (StringUtils.isBlank(map.get(6))) {
            return ApiResponse.fail("约考日期不能为空");
        }
        if (StringUtils.isBlank(map.get(7))) {
            return ApiResponse.fail("考试场地不能为空");
        }
    /*    if (StringUtils.isBlank(map.get(8))) {
            return ApiResponse.fail("考试场次不为空");
        }*/
        String trainStatus = "";
        String subTestNums = "";
//		2、查找到学员ID
        if (information == null) {
            // 未找到学员 记录异常 ， 不抛出异常
            BizException exception = new BizException();
            exception.setSfzmhm(map.get(2));
            exception.setId(genId());
            exception.setCode("991");
            exception.setCjr(sysUser.getZh() + "-" + sysUser.getXm());
            exception.setCjsj(DateUtils.getNowTime());
            exception.setKskm(kmMap.get(kmCode));
            exception.setXm(map.get(0));
            exceptionService.saveException(exception);
        } else if (StringUtils.equals(information.getStatus(), "99")) {
            BizException exception = new BizException();
            exception.setSfzmhm(map.get(2));
            exception.setId(genId());
            exception.setCode("002");
            exception.setCjr(sysUser.getZh() + "-" + sysUser.getXm());
            exception.setCjsj(DateUtils.getNowTime());
            exception.setKskm(kmMap.get(kmCode));
            exception.setXm(map.get(0));
            exceptionService.saveException(exception);
        } else if (StringUtils.equals(information.getArrearage(), "10")) {
            BizException exception = new BizException();
            exception.setSfzmhm(map.get(2));
            exception.setId(genId());
            exception.setCode("903");
            exception.setCjr(sysUser.getZh() + "-" + sysUser.getXm());
            exception.setCjsj(DateUtils.getNowTime());
            exception.setKskm(kmMap.get(kmCode));
            exception.setXm(map.get(0));
            exceptionService.saveException(exception);
        }

        String regFee = "";
        String realFee = "";
        String arFee = "";
        if (information != null) {

            if (information.getCarType().equals("A1") || information.getCarType().equals("A2") || information.getCarType().equals("A3") || information.getCarType().equals("B2")) {
                regFee = information.getRegistrationFee() + "";
                realFee = information.getRealPay() + "";
                arFee = information.getOweAmount() + "";
            }
            cwjl.setTraineeId(information.getId());
            cwjl.setTraineeName(information.getName());
            cwjl.setZt("00");
//		3、学员ID有效性验证，必须未结业和未退学的，约考科目必须没有合格 非小车，需要检查本科目培训状态是否合格

            if (StringUtils.isBlank(information.getSerialNum())) {
                BizException exception = new BizException();
                exception.setSfzmhm(map.get(2));
                exception.setId(genId());
                exception.setCode("003");
                exception.setCjr(sysUser.getZh() + "-" + sysUser.getXm());
                exception.setCjsj(DateUtils.getNowTime());
                exception.setKskm(kmMap.get(kmCode));
                exception.setXm(map.get(0));
                exceptionService.saveException(exception);
            }


            //大车，需要判断培训，没有培训合格的大车，不能进行约考
            if (StringUtils.indexOf(information.getCarType(), "C") == -1) {
                if (StringUtils.equals(kmCode, "10")) {//科目一
//                    if (StringUtils.equals(information.getFirSubTrainStatus(), "10")) {
//                        cwjl.setReason("该学员科目一培训处理不合格，不能进行约考");
//                        yyCwjlService.save(cwjl);
//                        BizException exception = new BizException();
//                        exception.setSfzmhm(map.get(2));
//                        exception.setId(genId());
//                        exception.setCode("121");
//                        exception.setCjr(sysUser.getZh() + "-" + sysUser.getXm());
//                        exception.setCjsj(DateUtils.getNowTime());
//                        exception.setKskm(kmCode);
//                        exception.setXm(map.get(0));
//                        exceptionService.saveException(exception);
//                    }
                } else if (StringUtils.equals(kmCode, "20")) {//科目二
//                    if (StringUtils.equals(information.getSecSubTrainStatus(), "10")) {
//                        cwjl.setReason("该学员科目二培训处理不合格，不能进行约考");
//                        yyCwjlService.save(cwjl);
//                        BizException exception = new BizException();
//                        exception.setSfzmhm(map.get(2));
//                        exception.setId(genId());
//                        exception.setCode("221");
//                        exception.setCjr(sysUser.getZh() + "-" + sysUser.getXm());
//                        exception.setCjsj(DateUtils.getNowTime());
//                        exception.setKskm(kmCode);
//                        exception.setXm(map.get(0));
//                        exceptionService.saveException(exception);
//                    }
                } else if (StringUtils.equals(kmCode, "30")) {//科目三
                    if (StringUtils.equals(information.getThirdSubTrainStatus(), "10")) {
                        cwjl.setReason("该学员科目三培训处理不合格，不能进行约考");
                        yyCwjlService.save(cwjl);
                        BizException exception = new BizException();
                        exception.setSfzmhm(map.get(2));
                        exception.setId(genId());
                        exception.setCode("321");
                        exception.setCjr(sysUser.getZh() + "-" + sysUser.getXm());
                        exception.setCjsj(DateUtils.getNowTime());
                        exception.setKskm(kmMap.get(kmCode));
                        exception.setXm(map.get(0));
                        exceptionService.saveException(exception);
                    }
                }
            }
            Date date = null;
            try {
                date = DateUtils.getDate(map.get(6), "yyyy-MM-dd");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (date == null) {
                cwjl.setReason("约考时间格式错误，绝才时间格式为：yyyy-MM-dd ");
                yyCwjlService.save(cwjl);
                return ApiResponse.fail("约考时间格式错误，绝才时间格式为：yyyy-MM-dd ");
            }

//		4、将学员ID、科目、考试场地、约考时间。查询数据库判断当前约考信息有没有重复
            SimpleCondition condition = new SimpleCondition(TraineeTestInfo.class);
            if (information != null) {
                condition.eq(TraineeTestInfo.InnerColumn.traineeId, information.getId());//学员ID
            }
            condition.eq(TraineeTestInfo.InnerColumn.subject, map.get(3));//科目
            condition.eq(TraineeTestInfo.InnerColumn.testTime, appendZero(map.get(6)));//约考时间
            condition.setOrderByClause(TraineeTestInfo.InnerColumn.id.desc());
            List<TraineeTestInfo> orgs = findByCondition(condition);
            if (orgs != null && orgs.size() > 0) {
                if (StringUtils.equals(kmCode, "10")) {//科目一
                    information.setFirSubTestTime(map.get(6));//科目一约考时间
                    Integer firSubTestNum = information.getFirSubTestNum();
                    if (firSubTestNum == null) {
                        firSubTestNum = 0;
                    }
                    information.setFirSubTestNum(firSubTestNum);//科目一考试次数
                    information.setFirSub("20");
                    subTestNums = firSubTestNum - 1 + "";
                    trainStatus = information.getFirSubTrainStatus();//科目一培训状态
                } else if (StringUtils.equals(kmCode, "20")) { //科目二
                    information.setStatus("20");
                    information.setSecSub("10");//科目二状态
                    information.setSecSubTestTime(map.get(6));//科目二约考时间
                    Integer subTestNum = information.getSecSubTestNum();
                    if (subTestNum == null) {
                        subTestNum = 0;
                    }
                    information.setSecSubTestNum(subTestNum);//科目二考试次数
                    subTestNums = subTestNum - 1 + "";
                    trainStatus = information.getSecSubTrainStatus();//科目二培训状态

                } else if (StringUtils.equals(kmCode, "30")) {//科目三
                    information.setStatus("30");
                    information.setThirdSub("10");//科目三状态
                    information.setThirdSubTestTime(map.get(6));//科目三约考时间
                    Integer subTestNum = information.getThirdSubTestNum();
                    if (subTestNum == null) {
                        subTestNum = 0;
                    }
                    information.setThirdSubTestNum(subTestNum);//科目三考试次数
                    subTestNums = subTestNum - 1 + "";
                    trainStatus = information.getThirdSubTrainStatus();//科目三培训状态
                } else if (StringUtils.equals(kmCode, "40")) {//科目四
                    information.setStatus("40");
                    information.setForthSub("00");//科目四状态
                }

                return ApiResponse.success(information.getJgmc() + "@sfgeeq@" + trainStatus + "@sfgeeq@" + subTestNums + "@sfgeeq@" + information.getReferrer() + "@sfgeeq@" + information.getRegistrationTime() + "@sfgeeq@" + regFee + "@sfgeeq@" + realFee + "@sfgeeq@" + arFee);
            }
//		5、将约考信息插入约考表
            TraineeTestInfo addEntity = new TraineeTestInfo();
            addEntity.setId(genId());
            addEntity.setTraineeId(information.getId());
            addEntity.setTraineeName(information.getName());
            addEntity.setIdCardNo(information.getIdCardNo());//身份证号码
            addEntity.setSubject(map.get(3));//科目
            addEntity.setTestPlace(map.get(7) + "-" + map.get(8));//考试场地
            addEntity.setTestTime(map.get(6));//约考时间
            addEntity.setOperateTime(DateUtils.getNowTime());//操作时间
            addEntity.setRemark("excel约考信息批量导入");//备注
            addEntity.setOperator(sysUser.getYhid());//操作人
            addEntity.setOperateTime(DateUtils.getNowTime());//操作時間
            addEntity.setCjr(sysUser.getZh() + "-" + sysUser.getXm());
            addEntity.setCjsj(DateUtils.getNowTime());
            if (!StringUtils.equals(map.get(3), "科目四")) {
                addEntity.setPayStatus("10");//00已缴费 10未缴费。
            } else {
                addEntity.setPayStatus("00");
            }
            int i = save(addEntity);
            if (i == 0) {
                return ApiResponse.fail("入库失败");
            }

//		6、插入将约考成功的消息插入消息表
            SysMessage message = new SysMessage();
            message.setTitle("您已约考成功");
            //学员姓名(0)|学习驾驶证明编号(1)|身份证明号码(2)|考试科目(3)|考试车型(4)|预约日期(5)|约考日期(6)|考试场地(7)|考试场次(8)|手机号码(9)|null(10)|
//		科目一预约成功确认：【明涛驾校】尊敬的某某先生/女士：您预约的    年   月  日的科目一考试已受理成功，考试时间：上午8:30—10:30，下午13:30—15:00，请准时参加考试，预祝您考试顺利。缺考将视为不及格，再次约考需缴纳补考费。巩固练习请选择“服务学员”—“模拟考试”。如需帮助，请联系报名负责人或致电客服热线：400-133-2133。
            //学员{userName}您好，你已经成功约考{yhkm}(考试科目)  {yksj}(约考日期) {ykcx}(考试车型)
            String messageBody = "{\"userName\":\"" + map.get(0) + "\",\"yksj\":\"" + map.get(6) + "\",\"ykkm\":\"" + map.get(6) + "\",\"ykcx\":\"" + map.get(4) + "\"}";
            String userXb = "先生";//性别 /* 00: 女  10: 男*/
            if (!StringUtils.equals(information.getGender(), "10")) {
                userXb = "女士";
            }
            String kmCodeName = map.get(3);
            if (StringUtils.equals(kmCode, "40")) {
                kmCodeName = "科目三安全文明常识";
            }
            messageBody = "{\"first\":\"尊敬的" + information.getName() + userXb + "：\",\"keyword1\":\"" + kmCodeName + "预约成功\",\"keyword2\":\"预约成功\",\"remark\":\"您预约的" + map.get(6) + "的" + kmCodeName + "考试已受理成功，考试时间：上午8:30—10:30，下午13:30—15:00，请准时参加考试，预祝您考试顺利。缺考将视为不及格，再次约考需缴纳补考费。巩固练习请选择“服务学员”—“模拟考试”。如需帮助，请联系报名负责人或致电客服热线：400-133-2133\"}";
            message.setParameterBody(messageBody);//参数
            message.setBizId("xy001");//业务ID
            message.setUserId(information.getId());//接收者USER_ID
            message.setUserName(information.getName());//接收者USER_ID
            message.setUserRole("1");//1、学员 2、教练 3、管理员
            messageService.sendMessage(message, information.getOpenId(), information.getPhone());

//		7、修改学员表
            if (StringUtils.equals(kmCode, "10")) {//科目一
                information.setFirSubTestTime(map.get(6));//科目一约考时间
                Integer firSubTestNum = information.getFirSubTestNum();
                if (firSubTestNum == null) {
                    firSubTestNum = 0;
                }
                information.setFirSubTestNum(firSubTestNum + 1);//科目一考试次数
                information.setFirSub("20");
                subTestNums = firSubTestNum + "";
                trainStatus = information.getFirSubTrainStatus();//科目一培训状态
            } else if (StringUtils.equals(kmCode, "20")) {//科目二
                information.setStatus("20");
                information.setSecSub("10");//科目二状态
                information.setSecSubTestTime(map.get(6));//科目二约考时间
                Integer subTestNum = information.getSecSubTestNum();
                if (subTestNum == null) {
                    subTestNum = 0;
                }
                information.setSecSubTestNum(subTestNum + 1);//科目二考试次数
                subTestNums = subTestNum + "";
                trainStatus = information.getSecSubTrainStatus();//科目二培训状态

            } else if (StringUtils.equals(kmCode, "30")) {//科目三
                information.setStatus("30");
                information.setThirdSub("10");//科目三状态
                information.setThirdSubTestTime(map.get(6));//科目三约考时间
                Integer subTestNum = information.getThirdSubTestNum();
                if (subTestNum == null) {
                    subTestNum = 0;
                }
                information.setThirdSubTestNum(subTestNum + 1);//科目三考试次数
                subTestNums = subTestNum + "";
                trainStatus = information.getThirdSubTrainStatus();//科目三培训状态
            } else if (StringUtils.equals(kmCode, "40")) {//科目四
                information.setForthSubTestTime(map.get(6));
                information.setStatus("40");
                information.setForthSub("00");//科目四状态
            }
            traineeInformationService.update(information);

            yyCwjlService.updateZt(information.getId());

//		8、插入学员状态表(学员日志表)
            String status = "00";
            String type = map.get(3) + "约考";
            traineeStatusService.saveEntity(information, type, status, "excel约考信息批量导入" + addEntity.toString());
            BizException exception = new BizException();
            exception.setLsh(information.getSerialNum());
            exception.setSfzmhm(information.getIdCardNo());
            exception.setKskm(kmMap.get(kmCode));
            exception.setXm(information.getName());
            if (StringUtils.equals(kmCode, "10")) {
                exception.setCode("102");
            } else if (StringUtils.equals(kmCode, "20")) {
                exception.setCode("202");
            } else if (StringUtils.equals(kmCode, "30")) {
                exception.setCode("302");
            }
            exceptionService.clearException(exception, exception.getCode());
            return ApiResponse.success(information.getJgmc() + "@sfgeeq@" + trainStatus + "@sfgeeq@" + subTestNums + "@sfgeeq@" + information.getReferrer() + "@sfgeeq@" + information.getRegistrationTime() + "@sfgeeq@" + regFee + "@sfgeeq@" + realFee + "@sfgeeq@" + arFee);
        } else {
            SimpleCondition condition = new SimpleCondition(TraineeTestInfo.class);
            condition.eq(TraineeTestInfo.InnerColumn.subject, map.get(3));//科目
            condition.eq(TraineeTestInfo.InnerColumn.testTime, appendZero(map.get(6)));//约考时间
            condition.setOrderByClause(TraineeTestInfo.InnerColumn.id.desc());
            List<TraineeTestInfo> orgs = findByCondition(condition);
            if(CollectionUtils.isNotEmpty(orgs)){
                return ApiResponse.success();
            }
            // 如果学员信息为空 ， 直接将约考信息插入约考信息表中
            TraineeTestInfo testInfo = new TraineeTestInfo();
            testInfo.setId(genId());
            testInfo.setTraineeId("");
            testInfo.setTraineeName(map.get(0));
            testInfo.setIdCardNo(map.get(2));//身份证号码
            testInfo.setSubject(map.get(3));//科目
            testInfo.setTestPlace(map.get(7) + "-" + map.get(8));//考试场地
            testInfo.setTestTime(map.get(6));//约考时间
            testInfo.setOperateTime(DateUtils.getNowTime()); //操作时间
            testInfo.setRemark("excel约考信息批量导入"); //备注
            testInfo.setOperator(sysUser.getZh() + "-" + sysUser.getXm()); //操作人
            testInfo.setOperateTime(DateUtils.getNowTime()); //操作时间
            testInfo.setCjr(sysUser.getZh() + "-" + sysUser.getXm());
            testInfo.setCjsj(DateUtils.getNowTime());
            if (!StringUtils.equals(map.get(3), "科目四")) {
                testInfo.setPayStatus("10");//00已缴费 10未缴费。
            } else {
                testInfo.setPayStatus("00");
            }
            save(testInfo);
            return ApiResponse.success();
        }
    }

    /**
     * 考试成绩导入
     * 学员姓名(0)|学习驾驶证明编号(1)|身份证明号码(2)|考试科目(3)|考试车型(4)|预约日期(5)|约考日期(6)|考试场地(7)|考试场次(8)|手机号码(9)|考试结果(10)|考试合格，考试结果他们可能不填或是填写合格，所以合格判断需要判断两个情况，考试结果为空也默认算合格(11)|(12)|(13)|(14)|(15)|null(16)|
     *
     * @param map
     * @param sysUser
     * @return
     */
    private ApiResponse<String> updateResultExcel(Map<Integer, String> map, SysYh sysUser) {
//		1、非空验证
        Map<String, String> kmMap = new HashMap<>();
        kmMap.put("科目一", "10");
        kmMap.put("科目二", "20");
        kmMap.put("科目三", "30");
        kmMap.put("科目四", "40");
//		1、有效性验证
        if (StringUtils.isBlank(map.get(0))) {
            return ApiResponse.fail("学员姓名不为空");
        }

        if (StringUtils.isBlank(map.get(2))) {
            return ApiResponse.fail("身份证明号码不为空");
        }
        if (StringUtils.isBlank(map.get(3))) {
            return ApiResponse.fail("考试科目不为空");
        }
        String kmCode = kmMap.get(map.get(3));

        if (StringUtils.isBlank(kmCode)) {
            return ApiResponse.fail("考试科目:" + map.get(3) + " 转换考试科目编码异常，请联系管理人员");
        }
       /* if (StringUtils.isBlank(map.get(4))) {
            return ApiResponse.fail("考试车型不为空");
        }*/

        if (StringUtils.isBlank(map.get(5))) {
            return ApiResponse.fail("预约日期不为空");
        }

        if (StringUtils.isBlank(map.get(6))) {
            return ApiResponse.fail("约考日期不为空");
        }
        if (StringUtils.isBlank(map.get(7))) {
            return ApiResponse.fail("考试场地不为空");
        }
        if (StringUtils.isBlank(map.get(8))) {
            return ApiResponse.fail("考试场次不为空");
        }
//		2、查找到学员ID
        TraineeInformation traineeInfo = traineeInformationService.queryUserStudyType(map.get(2), map.get(4));
        if (traineeInfo == null) {
            return ApiResponse.fail("系统中，未找到该学员，请核实该学员是否是外校学员！");
        }

//		3、学员ID有效性验证，必须未结业和未退学的，
        String findUserStatus = traineeInfo.getStatus();
        if (StringUtils.equals(findUserStatus, "99")) {
            return ApiResponse.fail("学员未确认交费, 请在收费页面确认缴费");
        }
        if (StringUtils.equals(findUserStatus, "60")) {
            return ApiResponse.fail("学员已退学");
        }
        if (StringUtils.equals(findUserStatus, "50")) {
            return ApiResponse.fail("该学员已结业，无需约考");
        }
        if (!StringUtils.equals(traineeInfo.getName(), map.get(0))) {
            return ApiResponse.fail("系统学员姓名:" + traineeInfo.getName() + " ,数据匹配失败！");
        }

        String trainStatus = "";
        String subTestNums = "";
        if (StringUtils.equals(kmCode, "10")) {//科目一
            if (StringUtils.equals(traineeInfo.getFirSub(), "30") || StringUtils.equals(traineeInfo.getFirSub(), "40")) {
                return ApiResponse.fail("该学员科目一已完成成绩导入，无需再次导入。状态值：" + traineeInfo.getFirSub());
            }
            trainStatus = traineeInfo.getFirSubTrainStatus();//科目一培训状态
            subTestNums = traineeInfo.getFirSubTestNum() + "";
        } else if (StringUtils.equals(kmCode, "20")) {//科目二
            if (StringUtils.equals(traineeInfo.getSecSub(), "30") || StringUtils.equals(traineeInfo.getSecSub(), "40")) {
                return ApiResponse.fail("该学员科目二已完成成绩导入，无需再次导入。状态值：" + traineeInfo.getSecSub());
            }
            trainStatus = traineeInfo.getSecSubTrainStatus();//科目二培训状态
            subTestNums = traineeInfo.getSecSubTestNum() + "";
        } else if (StringUtils.equals(kmCode, "30")) {//科目三
            if (StringUtils.equals(traineeInfo.getThirdSub(), "30") || StringUtils.equals(traineeInfo.getThirdSub(), "40")) {
                return ApiResponse.fail("该学员科目二已完成成绩导入，无需再次导入。状态值：" + traineeInfo.getThirdSub());
            }
            trainStatus = traineeInfo.getThirdSubTrainStatus();//科目三培训状态
            subTestNums = traineeInfo.getThirdSubTestNum() + "";
        } else if (StringUtils.equals(kmCode, "40")) {//科目四
            if (StringUtils.equals(traineeInfo.getForthSub(), "10") || StringUtils.equals(traineeInfo.getForthSub(), "20")) {
                return ApiResponse.fail("该学员科目二已完成成绩导入，无需再次导入。状态值：" + traineeInfo.getForthSub());
            }
        }
//		4、找到约考记录
        SimpleCondition condition = new SimpleCondition(TraineeTestInfo.class);
        condition.eq(TraineeTestInfo.InnerColumn.traineeId, traineeInfo.getId());//学员ID
        condition.eq(TraineeTestInfo.InnerColumn.subject, map.get(3));//科目
        // condition.eq(TraineeTestInfo.InnerColumn.testPlace, map.get(7)+"-"+map.get(8));//考试场地
        condition.eq(TraineeTestInfo.InnerColumn.testTime, appendZero(map.get(6)));//约考时间
        condition.and().andIsNull(TraineeTestInfo.InnerColumn.testResult.name());//没有考试成功

        condition.setOrderByClause(TraineeTestInfo.InnerColumn.id.desc());
        List<TraineeTestInfo> orgs = findByCondition(condition);
        TraineeTestInfo obj;
        if (orgs != null && orgs.size() > 0) {
            obj = orgs.get(0);
        } else {
            return ApiResponse.fail("考试信息表中，未找到本人的约考记录。");
        }

        Date date = null;
        try {
            date = DateUtils.getDate(obj.getTestTime(), "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }

//		5、修改考试表状态
        String testResult = "00";//00已缴费  10未缴费
        if (StringUtils.isNotEmpty(map.get(10))) {
            if (StringUtils.equals(map.get(10), "不合格") || StringUtils.equals(map.get(10), "缺考")) {
                testResult = "10";
            } else if (StringUtils.equals(map.get(10), "合格")) {

            } else {
                return ApiResponse.fail("考试结果状态不对。考试结果：" + map.get(10));
            }
        }
        obj.setTestResult(testResult);//考试结果
        int i = update(obj);
        if (i == 0) {
            return ApiResponse.fail("修改数据库失败");
        }
        String messageBody = "";
        String userXb = "先生";//性别 /* 00: 女  10: 男*/
        if (!StringUtils.equals(traineeInfo.getGender(), "10")) {
            userXb = "女士";
        }


//		6、修改用户表状态
        if (StringUtils.equals(testResult, "00")) {//成功
            if (StringUtils.equals(kmCode, "10")) {
                //科目一成功后，系统自动跳到下一个科目中
                traineeInfo.setStatus("20");
                //科目一成功后，将状态修改为合格
                traineeInfo.setFirSub("40");//00：已缴费 10：培训 20: 已约考 30：不合格 40：合格
//				合格后，设置  学员有效期开始时间和结束时间
                traineeInfo.setIndateStartTime(obj.getTestTime());//约考时间
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.YEAR, 3);
                date = calendar.getTime();
                traineeInfo.setIndateEndTime(DateUtils.getDateStr(date, "yyyy-MM-dd"));

                //		4、科目一成绩合格确认：【明涛驾校】尊敬的某某先生/女士：恭喜您，科目一考试成绩已合格。培训请联系报名负责人，并可通过“学员助手”登录系统查看培训、考试状态。如需帮助，请致电客服热线：400-133-2133。
                messageBody = "{\"first\":\"尊敬的" + traineeInfo.getName() + userXb + "：\",\"keyword1\":\"科目一成绩合格确认\",\"keyword2\":\"科目一考试成绩已合格\",\"remark\":\"恭喜您，科目一考试成绩已合格。培训请联系报名负责人，并可通过“学员助手”登录系统查看培训、考试状态。如需帮助，请致电客服热线：400-133-2133。\"}";
            } else if (StringUtils.equals(kmCode, "20")) {
                // 科目二考试考试合格后，查看科目三是否合格
                if (StringUtils.equals(traineeInfo.getThirdSub(), "40")) {
                    traineeInfo.setStatus("40");
                } else {
                    traineeInfo.setStatus("30");
                }
                traineeInfo.setStatus("30");
                traineeInfo.setSecSub("40");//00: 培训 10：已约考 20：已缴费 30：不合格 40：合格

                //		8、科目二成绩合格确认：【明涛驾校】尊敬的某某先生/女士：恭喜您，科目二考试成绩已合格。科目三培训请联系报名负责人，并可通过“学员助手”登录系统查看培训、考试状态。如需帮助，请致电客服热线：400-133-2133。
                messageBody = "{\"first\":\"尊敬的" + traineeInfo.getName() + userXb + "：\",\"keyword1\":\"科目二成绩合格确认\",\"keyword2\":\"科目二考试成绩已合格\",\"remark\":\"恭喜您，科目二考试成绩已合格。科目三培训请联系报名负责人，并可通过“学员助手”登录系统查看培训、考试状态。如需帮助，请致电客服热线：400-133-2133。\"}";
            } else if (StringUtils.equals(kmCode, "30")) {
                if (StringUtils.equals(traineeInfo.getSecSub(), "40")) {
                    traineeInfo.setStatus("40");
                } else {
                    traineeInfo.setStatus("20");
                }
                traineeInfo.setThirdSub("40");//00: 培训 10：已约考 20：已缴费 30：不合格 40：合格

                //		12、科目三成绩合格确认：【明涛驾校】尊敬的某某先生/女士：恭喜您，科目三考试成绩已合格。您可通过“交管12123”预约科目三安全文明常识考试，考场请选择“新农科目一考场”。如需帮助，请联系报名负责人或致电客服热线：400-133-2133。
                messageBody = "{\"first\":\"尊敬的" + traineeInfo.getName() + userXb + "：\",\"keyword1\":\"科目三成绩合格确认\",\"keyword2\":\"科目三考试成绩已合格\",\"remark\":\"恭喜您，科目三考试成绩已合格。您可通过“交管12123”预约科目三安全文明常识考试，考场请选择“新农科目一考场”。如需帮助，请联系报名负责人或致电客服热线：400-133-2133。\"}";
            } else if (StringUtils.equals(kmCode, "40")) {
                traineeInfo.setForthSub("20");//00：已约考 :10: 不合格 20：合格
                traineeInfo.setStatus("50");

//				15、科目三安全文明常识成绩合格确认：【明涛驾校】尊敬的某某先生/女士：恭喜您，顺利结业拿证，关注明涛驾校微信公众号，掌握驾车安全小常识，并可通过“学员助手”登录系统，邀请好友学车，报名成功更有红包惊喜。如需帮助，请致电客服热线：400-133-2133。
                messageBody = "{\"first\":\"尊敬的" + traineeInfo.getName() + userXb + "：\",\"keyword1\":\"科目三安全文明常识成绩合格确认\",\"keyword2\":\"科目三安全文明常识成绩已合格\",\"remark\":\"恭喜您，顺利结业拿证，关注明涛驾校微信公众号，掌握驾车安全小常识，并可通过“学员助手”登录系统，邀请好友学车，报名成功更有红包惊喜。如需帮助，请致电客服热线：400-133-2133。\"}";
            }
        } else {
            if (StringUtils.equals(kmCode, "10")) {
                traineeInfo.setFirSub("30");

//		5、科目一成绩不合格确认：【明涛驾校】尊敬的某某先生/女士：很遗憾，科目一考试成绩不合格。再次预约、缴纳补考费请联系报名负责人，如需理论培优练习、帮助可致电客服热线：400-133-2133。
                messageBody = "{\"first\":\"尊敬的" + traineeInfo.getName() + userXb + "：\",\"keyword1\":\"科目一成绩不合格确认\",\"keyword2\":\"科目一考试成绩不合格\",\"remark\":\"很遗憾，科目一考试成绩不合格。再次预约、缴纳补考费请联系报名负责人，如需理论培优练习、帮助可致电客服热线：400-133-2133。\"}";
            } else if (StringUtils.equals(kmCode, "20")) {
//		9、科目二成绩不合格确认：【明涛驾校】尊敬的某某先生/女士：很遗憾，科目二考试成绩不合格。再次预约、缴纳补考费、培训请联系报名负责人，如需帮助可致电客服热线：400-133-2133。
                traineeInfo.setSecSub("30");
                messageBody = "{\"first\":\"尊敬的" + traineeInfo.getName() + userXb + "：\",\"keyword1\":\"科目二成绩不合格确认\",\"keyword2\":\"科目二考试成绩不合格\",\"remark\":\"很遗憾，科目二考试成绩不合格。再次预约、缴纳补考费、培训请联系报名负责人，如需帮助可致电客服热线：400-133-2133。\"}";
            } else if (StringUtils.equals(kmCode, "30")) {
//		13、科目三成绩不合格确认：【明涛驾校】尊敬的某某先生/女士：很遗憾，科目三考试成绩不合格。再次预约、缴纳补考费、培训请联系报名负责人，如需帮助可致电客服热线：400-133-2133。
                traineeInfo.setSecSub("30");
                messageBody = "{\"first\":\"尊敬的" + traineeInfo.getName() + userXb + "：\",\"keyword1\":\"科目三成绩不合格确认\",\"keyword2\":\"科目三考试成绩不合格\",\"remark\":\"很遗憾，科目三考试成绩不合格。再次预约、缴纳补考费、培训请联系报名负责人，如需帮助可致电客服热线：400-133-2133。\"}";
            } else if (StringUtils.equals(kmCode, "40")) {
                traineeInfo.setForthSub("10");
//		16、科目三安全文明常识成绩不合格确认：【明涛驾校】尊敬的某某先生/女士：很遗憾，科目三安全文明常识考试成绩不合格。再次预约请联系报名负责人，如需理论培优练习、帮助可致电客服热线：400-133-2133。
                messageBody = "{\"first\":\"尊敬的" + traineeInfo.getName() + userXb + "：\",\"keyword1\":\"科目三安全文明常识成绩不合格确认\",\"keyword2\":\"科目三安全文明常识考试成绩不合格\",\"remark\":\"很遗憾，科目三安全文明常识考试成绩不合格。再次预约请联系报名负责人，如需理论培优练习、帮助可致电客服热线：400-133-2133。\"}";
            }
        }
        traineeInformationService.update(traineeInfo);

//		7、插入将约考成功的消息插入消息表
        SysMessage message = new SysMessage();
        message.setTitle("考试成绩导入");

        message.setParameterBody(messageBody);//参数
        message.setBizId("xy001");//业务ID
        message.setUserId(traineeInfo.getId());//接收者USER_ID
        message.setUserName(traineeInfo.getName());//接收者USER_ID
        message.setUserRole("1");//1、学员 2、教练 3、管理员
        messageService.sendMessage(message, traineeInfo.getOpenId(), traineeInfo.getPhone());

//		7、插入学员状态表(学员日志表)
        String status = "00";
        if (StringUtils.equals(testResult, "10")) {//考试不成功
            status = "10";
        }
        String type = map.get(3) + "约考成绩导入";
        traineeStatusService.saveEntity(traineeInfo, type, status, "excel考试成绩批量导入");

//		8、将教练学员分配表(coach_trainee_rercord) 对应的科目设置为合格
        SimpleCondition coachTraineeRercordCondition = new SimpleCondition(CoachTraineeRercord.class);
        coachTraineeRercordCondition.eq(CoachTraineeRercord.InnerColumn.traineeId, traineeInfo.getId());
        coachTraineeRercordCondition.eq(CoachTraineeRercord.InnerColumn.status, "00");
        coachTraineeRercordCondition.eq(CoachTraineeRercord.InnerColumn.allotSub, "0" + StringUtils.substring(kmCode, 0, 1));//科目
        List<CoachTraineeRercord> coachTraineeRercordList = coachTraineeRercordService.findByCondition(coachTraineeRercordCondition);
        if (coachTraineeRercordList != null && coachTraineeRercordList.size() > 0) {
            CoachTraineeRercord coachTraineeRercord = coachTraineeRercordList.get(0);
            coachTraineeRercord.setStatus("10");
            coachTraineeRercord.setModifier(sysUser.getZh() + "-" + sysUser.getXm());
            coachTraineeRercord.setModifyTime(DateUtils.getNowTime());
            coachTraineeRercordService.update(coachTraineeRercord);
        }
        return ApiResponse.success(traineeInfo.getJgmc() + "@sfgeeq@" + trainStatus + "@sfgeeq@" + subTestNums);
    }


    public String appendZero(String data) {

        String[] split = data.split("-");
        if (split.length < 3) {
            return data;
        }

        if (split[1].length() < 2) {
            split[1] = "0" + split[1];
        }

        if (split[2].length() < 2) {
            split[2] = "0" + split[2];
        }

        return split[0] + "-" + split[1] + "-" + split[2];
    }



}