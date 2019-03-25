package com.ldz.biz.car.service;

import com.github.pagehelper.Page;
import com.ldz.biz.car.model.BizCarAnnualExam;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

import java.util.List;
import java.util.Map;

public interface BizCarAnnualExamService extends BaseService<BizCarAnnualExam, String> {

    ApiResponse<List<BizCarAnnualExam>> getCarAnnualExamList(String clId);

    ApiResponse<Map<String, Long>> getPager(Page<BizCarAnnualExam> page);

    ApiResponse<String> updKeepOnRecord(BizCarAnnualExam exam);
}