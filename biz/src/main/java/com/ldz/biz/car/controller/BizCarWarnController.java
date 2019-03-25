package com.ldz.biz.car.controller;


import com.ldz.biz.car.model.BizCar;
import com.ldz.biz.car.model.BizCarWarn;
import com.ldz.biz.car.service.BizCarWarnService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping("/api/carwarn")
public class BizCarWarnController extends BaseController<BizCarWarn,String> {
    @Autowired
    private BizCarWarnService service;

    @Override
    protected BaseService<BizCarWarn, String> getBaseService() {
        return service;
    }

    /**
     * 车辆年审信息提醒设置
     * 车辆-年审初次登记日期设置
     *
     *  教练车 5年内1年一审 超过5年一年2审 2审的间隔为6个月
     *  普通小车 6年内2年一审 超过6年一年1审
     *  普通大车 一年1审
     * @param entity
     * @return
     */
    @RequestMapping(value="/nccdrq", method={RequestMethod.POST})
    public ApiResponse<String> updNcWarn(BizCar entity){
        return service.updNcWarn(entity);
    }


    /**
     * 车辆运营管理年审提醒设置
     * 一年一提醒
     * @param entity
     * @return
     */
    @RequestMapping(value="/ygnccdrq", method={RequestMethod.POST})
    public ApiResponse<String> updYgNcWarn(BizCar entity){
        return service.updYgNcWarn(entity);
    }


    /**
     * 车辆油改气年审提醒设置
     * 3年一审
     * @param entity
     * @return
     */
    @RequestMapping(value="/gxnccdrq", method={RequestMethod.POST})
    public ApiResponse<String> updGxNcWarn(BizCar entity){
        return service.updGxNcWarn(entity);
    }
    /**
     * 车辆续保提醒设置
     * 一年一审
     * @param entity
     * @return
     */
    @RequestMapping(value="/bxnccdrq", method={RequestMethod.POST})
    public ApiResponse<String> updBxNcWarn(BizCar entity){
        return service.updBxNcWarn(entity);
    }

    /**
     * 车辆强制报废设置
     * @param entity
     * @return
     */
    @RequestMapping(value="/xzbfrq", method={RequestMethod.POST})
    public ApiResponse<String> updQzbfrqWarn(BizCar entity){
        return service.updQzbfrqWarn(entity);
    }

    /**
     * 更新主表中的告警接口
     * @param clId 需要同步的车辆
     * @return
     */
    @RequestMapping(value="/clgjgx", method={RequestMethod.POST})
    public ApiResponse<String> updCarWarnDate(String clId){
        return service.updCarWarnDate(clId);
    }

//    /**
//     * 定时任务生成告警记录
//     * @param data
//     * @return
//     */
//    @RequestMapping(value="/jobsavenwarn", method={RequestMethod.POST,RequestMethod.GET})
//    public ApiResponse<String> jobSaveWarn(String data){
//        return service.jobSaveWarn(data);
//    }



//    1、更新主表中的告警接口。  updCarWarnDate
//    2、查询时，获取小于当天的告警记录
//    3、按告警类型设置年审确认、运管确认、改气确认、续保确认。
//    4、

}