package com.ldz.biz.service;

import com.github.pagehelper.Page;
import com.ldz.biz.model.ChargeManagement;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ChargeManagementService extends BaseService<ChargeManagement, String> {



    ApiResponse<String> saveInspect(ChargeManagement entity);

    ApiResponse<String> removeEntity(String id);

    ApiResponse<String> saveOtherCharge(ChargeManagement entity);

    ApiResponse<String> confirmCharge(Map<String,String> chargeIds);


    ApiResponse<String> revokeCharge(List<String> collect);

    ApiResponse<Map<String,Long>> countType(String type, String startTime, String endTime);

    ApiResponse<Map<String, Long>> countInOut();

    ApiResponse<Map<String, Long>> countInType();



    ApiResponse<String> getReceiptNo(String[] ids,String num);

    ApiResponse<String> getPjbh(List<String> ids, String num, String pjbh);

    ApiResponse<List<Map<String, Long>>> findTodayCharge(Page<ChargeManagement> page);

    /**
     * 获取分校的报名费记录
     */
    List<ChargeManagement> getBranchList(List<String> jgdms, String startTime, String endTime, String carType);

    List<ChargeManagement> getAllIn(List<String> jgdms, String startTime, String endTime);

    Long countStaged(String time);


    void exportExcel(HttpServletRequest request, HttpServletResponse response, ChargeManagement entity) throws IOException;


    ApiResponse<String> rejectInspect(String id);

    ApiResponse<String> returnInspect(String id);


    ApiResponse<String> warnInspect(String id, String reason);

    ApiResponse<String> removePjbh(String pjbh);
}