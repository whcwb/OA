package com.ldz.biz.controller;


import com.ldz.biz.model.ChargeManagement;
import com.ldz.biz.model.StudentAllModel;
import com.ldz.biz.service.DataStaService;
import com.ldz.util.bean.ApiResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationPid;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * 数据统计接口
 */
@RestController
@RequestMapping("/api/data")
public class DataStatisticsController {

    @Autowired
    private DataStaService service;

    /**
     * 获取总校分校报名费统计
     * @param startTime
     * @param endTime
     * @param lx 10直属队  20 挂靠队 30 承包队
     * @return
     */
    @PostMapping("/countBranchSignUp")
    public ApiResponse<List<ChargeManagement>> countBranch(@RequestParam(required = false) String[] lx,String startTime,String endTime,@RequestParam(required = false) String jgdm, @RequestParam(required = false)String carType){
        return service.countBranch(lx,startTime, endTime,jgdm,carType);
    }


    /**
     * 获取总收入
     */
    @PostMapping("/countAllIn")
    public ApiResponse<List<StudentAllModel>> countAllIn(@RequestParam(required = false) String jgdm, String startTime, String endTime,@RequestParam(required =  false) String lx[],@RequestParam(required = false) String carType) throws ParseException {
        return service.countAllIn(startTime,endTime,lx,carType,jgdm);
    }


    /**
     * 统计各个报名点， 年的招生人数
     */
    @PostMapping("/getAllStudentCount")
    public ApiResponse<List<StudentAllModel>> getAllStudentCount(String startTime, String endTime, @RequestParam(required = false) String jgdm, @RequestParam(required = false)String[] lx) throws ParseException {
        return service.getAllStudentCount(startTime, endTime, jgdm, lx);
    }

    /**
     * 统计各个报名点，某日的招生人数
     */
    @PostMapping("/getStudentCount")
    public ApiResponse<List<StudentAllModel>> getStudentCount(String startTime, String endTime, @RequestParam(required = false) String jgdm, @RequestParam(required = false)String[] lx) throws ParseException {
        return service.getStudentCount(startTime, endTime, jgdm, lx);
    }

    /**
     * 统计各个队的学费收入
     */
    @PostMapping("/getAllIn")
    public ApiResponse<List<StudentAllModel>> getAllIn(String startTime, String endTime) throws ParseException {
        return  service.getAllIn(startTime, endTime);

    }

    /**
     * 根据收费时间来统计某个时间段 收费的学员
     */
    @PostMapping("/getAllPayment")
    public ApiResponse<List<StudentAllModel>> getAllPayment(String startTime, String endTime, @RequestParam(required = false) String jgdm, @RequestParam(required = false)String[] lx){
        return service.getAllPayment(startTime, endTime, jgdm, lx);
    }



    /**
     *  学员合格率
     * @param jgdm 机构代码 没有查所有
     * @param startTime 开始时间 默认今天
     * @param endTime 结束时间 默认今天
     * @param km 科目
     */
    @PostMapping("/getPass")
    public ApiResponse<List<Map<String,String>>> getPass(String jgdm, String startTime, String endTime, String km){
        return service.getPass(jgdm,startTime,endTime,km);
    }

    /**
     *  收支 统计 接口
     */
    @PostMapping("/statisCharge")
    public ApiResponse<String> statisCharge(@RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "8") int pageSize, String start, String end, String idCard, String name, String jgdm){
        return service.statisCharge(pageNum, pageSize,start, end , idCard, name, jgdm);
    }





}
