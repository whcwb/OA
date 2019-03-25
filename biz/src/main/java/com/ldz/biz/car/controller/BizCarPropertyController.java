package com.ldz.biz.car.controller;


import com.ldz.biz.car.model.BizCarProperty;
import com.ldz.biz.car.service.BizCarPropertyService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 车辆产权明细表
 */
@RestController
@RequestMapping("/api/carcq")
public class BizCarPropertyController extends BaseController<BizCarProperty,String> {
    @Autowired
    private BizCarPropertyService service;

    @Override
    protected BaseService<BizCarProperty, String> getBaseService() {
        return service;
    }

//

    /**
     * 按车辆ID查询该车的产权列表
     * @param clId
     * @return
     */
    @RequestMapping(value="/clcqlb", method={RequestMethod.POST})
    public ApiResponse<List<BizCarProperty>>  getCarPropertyList (String clId){
        return service.getCarPropertyList(clId);
    }


//1. 车辆产权内部变更(从A队卖给B队)  质保金
//2. 学牌车变为非学牌车（退 某一队质保金。将车辆修改为非学牌车）
//3. 车辆报废  财务要收补贴款
//4. 车辆变卖 车辆收到因收款是多少。
    /**
     * 车辆产权内部变更
     * @param entity
     * @return
     */
    @RequestMapping(value="/cqbg", method={RequestMethod.POST})
    public ApiResponse<String>  changeOfTitle (BizCarProperty entity){
        return service.changeOfTitle(entity);
    }
    /**
     *
     * 学牌车变为非学牌车（号牌种类 变更）
     * 学牌变为非学时，会更换车牌号。
     * @param entity
     * @return
     */
    @RequestMapping(value="/cphbg", method={RequestMethod.POST})
    public ApiResponse<String>  cphTypeUpdat (BizCarProperty entity){
        return service.cphTypeUpdat(entity);
    }
//
    /**
     * 车辆报废  财务要收补贴款
     * @param id    车辆ID
     * @param jbr   经办人
     * @param jbrDn 经办人手机号码
     * @param bfjl  报废金额
     * @param propertyFileurl  产权协议电子档案路径
     * @return
     */
    @RequestMapping(value="/clbf", method={RequestMethod.POST})
    public ApiResponse<String>  vehicleScrapping (String id,String jbr,String jbrDn,String bfjl,String propertyFileurl){
        return service.vehicleScrapping(id,jbr,jbrDn,bfjl,propertyFileurl);
    }


    /**
     * 车辆变卖 车辆收到因收款是多少。
     * @param entity
     * @return
     */
    @RequestMapping(value="/clcs", method={RequestMethod.POST})
    public ApiResponse<String>  changeSell (BizCarProperty entity){
        return service.changeSell(entity);
    }

}