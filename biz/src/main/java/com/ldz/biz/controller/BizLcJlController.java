package com.ldz.biz.controller;


import com.ldz.biz.model.BizLcJl;
import com.ldz.biz.model.LcJlModel;
import com.ldz.biz.service.BizLcJlService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lcjl")
public class BizLcJlController extends BaseController<BizLcJl, String> {
    @Autowired
    private BizLcJlService service;

    @Override
    protected BaseService<BizLcJl, String> getBaseService() {
        return service;
    }

    /**
     * 新增一条练车记录
     * @param entity
     * @return
     */
    @RequestMapping(value="/save", method={RequestMethod.POST})
    public ApiResponse<String> save(BizLcJl entity){
        return service.saveEntity(entity);
    }
    /**
     * 手动结束一条练车记录
     */
    @PostMapping("/updateJssj")
    public ApiResponse<BizLcJl> updateJssj(String id,String km) throws ParseException {
        return service.updateJssj(id,km);
    }

    @RequestMapping("statistics")
    public ApiResponse<List<Map<String,Object>>> statistics(){
        return ApiResponse.success(service.statistics());
    }

    /**
     * 根据卡号查询最近的一条记录
     */
    @PostMapping("/getJl")
    public ApiResponse<BizLcJl> getJl(String cardNo){
        return service.getJl(cardNo);
    }

    /**
     * 驾校统计
     *
     * @return
     */
    @RequestMapping("/jxtj")
    public ApiResponse<List<Map<String,Object>> > drivingSchoolStatistics(){
        return ApiResponse.success(service.drivingSchoolStatistics());
    }

    /**
     * 练车排队登记
     */
    @PostMapping("/saveJl")
    public ApiResponse<String> saveJl(BizLcJl entity,String appoint){
        return service.saveJl(entity,appoint);
    }

    /**
     * 排队记录开始练车
     */
    @PostMapping("/kslc")
    public ApiResponse<String> kslc(BizLcJl entity){
        return service.kslc(entity);
    }


    /**
     * 查询当前记录是否已经返点
     */
    @PostMapping("/getFdZt")
    public ApiResponse<BizLcJl> getFdZt(String id){
        return service.getFdZt(id);
    }

    /**
     * 训练记录返点记录接口
     */
    @PostMapping("/updateFdZt")
    public ApiResponse<String> updateFdZt(String id,int fdJe){
        return service.updateFdZt(id, fdJe);
    }

    /**
     * 科目三模训 工作日志
     */
    @PostMapping("/getAllLog")
    public ApiResponse<List<LcJlModel>> getAllLog(){
        return service.getAllLog();
    }

    /**
     * 安全员工作日志
     */
    @PostMapping("/getOneLog")
    public ApiResponse<LcJlModel> getOneLog(String zgId){
        return service.getOneLog(zgId);
    }

    /**
     * 教练训练统计
     */
    @PostMapping("/jlTj")
    public ApiResponse<List<LcJlModel>> getJlTj(){
        return service.getJlTj();
    }

    @PostMapping("/updateXysl")
    public ApiResponse<String> updateXysl(String id , Integer xySl){
        return service.updateXysl(id,xySl);
    }


}