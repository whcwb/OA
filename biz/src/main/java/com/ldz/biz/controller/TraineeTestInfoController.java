package com.ldz.biz.controller;

import com.ldz.biz.model.TraineeTestInfo;
import com.ldz.biz.service.FileUploadRecordService;
import com.ldz.biz.service.TraineeTestInfoService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.commonUtil.ExcelReader;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 学员考试信息
 */
@RestController
@RequestMapping("/api/traineetestinfo")
public class TraineeTestInfoController extends BaseController<TraineeTestInfo,String> {
    @Autowired
    private TraineeTestInfoService service;

    @Autowired
    private StringRedisTemplate redisDao;

    @Autowired
    private FileUploadRecordService recordService;


    @Override
    protected BaseService<TraineeTestInfo, String> getBaseService() {
        return service;
    }

    /**
     * 约考信息导入
     *
     * @return
     */
    @RequestMapping(value="/impDestineExcel", method={RequestMethod.POST})
    public ApiResponse<Map<String,Object>> impDestineExcel(MultipartHttpServletRequest request, HttpServletResponse response){
        Map<String,Object> ret=new HashMap<>();
        MultipartFile file = request.getFile("file");
        RuntimeCheck.ifNull(file,"请选择需要xls文件上传");
        // 获取上传的文件的名称
        String filename = file.getOriginalFilename();
        if(filename.endsWith(".xls") ){//|| filename.endsWith(".xlsx")
            ExcelReader er = new ExcelReader();

            try {
                List<Map<Integer, String>> list = er.readExcelContent(file.getInputStream(),0); //读取Excel数据内容
                RuntimeCheck.ifFalse(list!=null&&list.size()>0&&list.get(0).size() >= 9,"请选择正确的约考清单");
                RuntimeCheck.ifFalse(StringUtils.equals(list.get(0).get(0),"学员姓名"),"请上传正确的约考清单");
                List<Map<Integer, String>> collect = list.stream().filter(m -> StringUtils.isNotBlank(m.get(2))).collect(Collectors.toList());
                ret=service.impDestineExcel(collect,filename.substring(0,filename.lastIndexOf(".")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            RuntimeCheck.ifNull(file,"请选择xls文件上传");
        }

        return ApiResponse.success(ret);
    }

    /**
     * 成绩导入
     *
     * @return
     */
     //@RequestMapping(value="/impResultExcel", method={RequestMethod.POST})
    public ApiResponse<Map<String,Object>> impResultExcel(MultipartHttpServletRequest request, HttpServletResponse response){
        Map<String,Object> ret=new HashMap<>();
        MultipartFile file = request.getFile("file");
        RuntimeCheck.ifNull(file,"请选择需要xls文件上传");
        // 获取上传的文件的名称
        String filename = file.getOriginalFilename();
        if(filename.endsWith(".xls") ){//|| filename.endsWith(".xlsx")
            ExcelReader er = new ExcelReader();

            try {
                List<Map<Integer,String>> list = er.readExcelContent(file.getInputStream(),0); //读取Excel数据内容
                RuntimeCheck.ifFalse(list!=null&&list.size()>0&&list.get(0).size() >= 10,"请选择正确的考试结果清单");
                RuntimeCheck.ifFalse(StringUtils.equals(list.get(0).get(0),"学员姓名"),"请上传正确的导入信息");
                RuntimeCheck.ifFalse(StringUtils.equals(list.get(0).get(10),"考试结果"),"导入文件中未找到考试结果字段，请核实");
                ret=service.impResultExcel(list,filename.substring(0,filename.lastIndexOf(".")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            RuntimeCheck.ifNull(file,"请选择xls文件上传");
        }

        return ApiResponse.success(ret);
    }

    /**
     * 成绩导入新模板
     */
    @PostMapping("/impResultExcel")
    public ApiResponse<Map<String,Object>> newImpResultExcel(MultipartHttpServletRequest request, HttpServletResponse response){

        Map<String,Object> ret=new HashMap<>();
        MultipartFile file = request.getFile("file");
        RuntimeCheck.ifNull(file,"请选择需要xls文件上传");
        // 获取上传的文件的名称
        String filename = file.getOriginalFilename();
        if(filename.endsWith(".xls") ){//|| filename.endsWith(".xlsx")
            ExcelReader er = new ExcelReader();

            try {
                List<Map<Integer,String>> list = er.readExcelContent(file.getInputStream(),0); //读取Excel数据内容
                RuntimeCheck.ifFalse(list!=null&&list.size()>3&&list.get(0).size() >= 22,"请选择正确的考试结果清单");
                RuntimeCheck.ifFalse(StringUtils.equals(list.get(2).get(2),"姓名"),"请保证学员姓名不为空");
                RuntimeCheck.ifFalse(StringUtils.equals(list.get(2).get(16),"是否合格"),"导入文件中未找到是否合格字段，请核实");
                // 先获取前两行
                List<Map<Integer,String>> filList = new ArrayList<>();
                filList.add(list.get(0));
                filList.add(list.get(1));
                List<Map<Integer, String>> collect = list.stream().filter(m -> StringUtils.isNotBlank(m.get(5))).collect(Collectors.toList());
                filList.addAll(collect);
                ret=service.newImpResultExcel(filList,filename.substring(0,filename.lastIndexOf(".")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            RuntimeCheck.ifNull(file,"请选择xls文件上传");
        }

        return ApiResponse.success(ret);

    }

    /**
     * 查询一个月内预约 或导入的文件列表
     */
    @PostMapping("/getFileId")
    public ApiResponse<String> getFileId(String lx,int pageNum, int pageSize){
        return recordService.getFileId(lx,pageNum,pageSize);
    }






}