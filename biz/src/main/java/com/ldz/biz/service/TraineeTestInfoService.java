package com.ldz.biz.service;

import com.ldz.biz.model.TraineeInformation;
import com.ldz.biz.model.TraineeTestInfo;
import com.ldz.sys.base.BaseService;
import com.ldz.sys.model.SysYh;
import com.ldz.util.bean.ApiResponse;

import java.util.List;
import java.util.Map;

public interface TraineeTestInfoService extends BaseService<TraineeTestInfo, String> {

    Map<String,Object> impDestineExcel(List<Map<Integer, String>> list, String fileName);

    Map<String,Object> impResultExcel(List<Map<Integer,String>> list, String fileName);

    Map<String, Object> newImpResultExcel(List<Map<Integer, String>> list, String substring);

    ApiResponse<String> newUpdateResultExcel(Map<Integer, String> map, SysYh sysUser, TraineeInformation information);

    List<TraineeTestInfo> getInfo(String jgdm, String startTime, String endTime, String km);

}