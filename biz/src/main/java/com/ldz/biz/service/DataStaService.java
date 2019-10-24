package com.ldz.biz.service;

import com.ldz.biz.model.ChargeManagement;
import com.ldz.biz.model.StudentAllModel;
import com.ldz.util.bean.ApiResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface DataStaService {

    /**
     * 获取分校报名费统计
     * @param startTime
     * @param endTime
     * @Param lx 总校和分校
     * @return
     */
    ApiResponse<List<ChargeManagement>> countBranch(String[] lx,String startTime, String endTime, String jgdm, String carType);

    /**
     * 分校总收入和总校总收入 ， 以及别的总收入 按月统计
     * @param startTime
     * @param endTime
     * @param lx
     * @param carType
     * @param jgdm
     * @return
     */
    ApiResponse<List<StudentAllModel>> countAllIn(String startTime, String endTime, String[] lx, String carType, String jgdm) throws ParseException;



    ApiResponse<List<StudentAllModel>> getAllStudentCount(String startTime, String endTime, String jgdm, String[] lx) throws ParseException;

    ApiResponse<List<StudentAllModel>> getStudentCount(String startTime, String endTime, String jgdm, String[] lx) throws ParseException;

    /**
     * 统计每个队的学费
     * @param startTime
     * @param endTime
     * @return
     */
    ApiResponse<List<StudentAllModel>> getAllIn(String startTime, String endTime) throws ParseException;

    ApiResponse<List<StudentAllModel>> getAllPayment(String startTime, String endTime, String jgdm, String... lx);

    ApiResponse<List<Map<String, String>>> getPass(String jgdm, String startTime, String endTime, String km);

    ApiResponse<String> statisCharge(int pageNum, int pageSize, String start, String end, String idCard, String name, String jgdm);

    void exportStatisCharge(String start, String end, String idCard, String name, String jgdm, HttpServletRequest request, HttpServletResponse response) throws IOException;



    /* *//**
     * 获取所有总校下的大车报名费统计
     * @param carType
     * @param startTime
     * @param endTime
     * @return
     *//*
    ApiResponse<List<ChargeManagement>> countUnder(String carType, String startTime, String endTime, String jgdm);*/
}
