package com.ldz.wechat.service;

import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.StudentAllModel;

import java.text.ParseException;
import java.util.List;

public interface DataStaService {


    ApiResponse<List<StudentAllModel>> getStudentCount(String startTime, String endTime, String jgdm, String[] lx) throws ParseException;




}
