package com.ldz.wechat.service.impl;


import com.ldz.sys.model.SysJg;
import com.ldz.sys.service.JgService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.wechat.model.StudentAllModel;
import com.ldz.wechat.model.TraineeInformation;
import com.ldz.wechat.service.ChargeManagementService;
import com.ldz.wechat.service.DataStaService;
import com.ldz.wechat.service.TraineeInformationService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DataStaServiceImpl implements DataStaService {

    @Autowired
    private JgService jgService;
    @Autowired
    private ChargeManagementService managementService;
    @Autowired
    private TraineeInformationService informationService;

    @Override
    public ApiResponse<List<StudentAllModel>> getStudentCount(String startTime, String endTime, String jgdm, String[] lx)  {
        if(StringUtils.isBlank(startTime)){
            startTime = DateUtils.getDateStr(new Date(),"yyyy") + " 00:00:00";
        }else{
            startTime += " 00:00:00";
        }
        if(StringUtils.isBlank(endTime)){
            endTime = DateUtils.getDateStr(new Date(), "yyyy") + " 23:59:59";
        }else{
            endTime += " 23:59:59";
        }

        Map<String, Map<String, Integer>> result = new LinkedHashMap<>();
        List<StudentAllModel> models = new ArrayList<>();

        List<SysJg> all = jgService.findAll();
        Map<String, String> jgMap = all.stream().collect(Collectors.toMap(SysJg::getJgdm, SysJg::getJgmc));

        SimpleCondition condition = new SimpleCondition(TraineeInformation.class);
        condition.gte(TraineeInformation.InnerColumn.registrationTime,startTime);
        condition.lte(TraineeInformation.InnerColumn.registrationTime,endTime);
        if(StringUtils.isNotBlank(jgdm)){
            condition.eq(TraineeInformation.InnerColumn.jgdm,jgdm);
        }
        if(lx!=null && lx.length > 0){
            SimpleCondition simpleCondition = new SimpleCondition(SysJg.class);
            simpleCondition.in(SysJg.InnerColumn.lx,Arrays.asList(lx));
            List<SysJg> jgs = jgService.findByCondition(simpleCondition);
            if(CollectionUtils.isNotEmpty(jgs)) {
                List<String> jgdms = jgs.stream().map(SysJg::getJgdm).collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(jgdms)){
                    condition.in(TraineeInformation.InnerColumn.jgdm,jgdms);
                }
            }
        }
        condition.setOrderByClause("  jgdm asc ");
        List<TraineeInformation> informationList = informationService.findByCondition(condition);
        Map<String, List<TraineeInformation>> collect = informationList.stream().collect(Collectors.groupingBy(TraineeInformation::getJgdm,TreeMap::new,Collectors.toList()));
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
            Long  c2 = count1.get("C2");

            allModel.setJgmc(jgMap.get(s));
            allModel.setA1(a1==null? 0L:a1);
            allModel.setA2(a2==null? 0L:a2);
            allModel.setA3(a3==null? 0L:a3);
            allModel.setB2(b2==null? 0L:b2);
            allModel.setC1(c1==null? 0L:c1);
            allModel.setC2(c2==null? 0L:c2);



            models.add(allModel);
        }
        if(CollectionUtils.isNotEmpty(models)) {
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



}
