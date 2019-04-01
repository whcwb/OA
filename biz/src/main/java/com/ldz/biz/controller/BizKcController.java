package com.ldz.biz.controller;

import com.ldz.biz.model.BizKc;
import com.ldz.biz.service.BizKcService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.commonUtil.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/bizkc")
public class BizKcController extends BaseController<BizKc, String> {
    @Autowired
    private BizKcService service;

    @Override
    protected BaseService<BizKc, String> getBaseService() {
        return service;
    }
    @RequestMapping(value="/save", method={RequestMethod.POST})
    public ApiResponse<String> save(BizKc entity){
        return service.saveEntity(entity);
    }

    /**
     * 库存添加
     * @param id
     * @param sl
     * @return
     */
    @PostMapping("/add")
    public ApiResponse<String> add(String id, Integer sl,String bz,float rkDj){
        return service.add(id,sl,bz,rkDj);
    }

    /**
     * 出库
     * @param id 库存id
     * @param sl 数量
     * @param zgId 员工姓名
     * @param bz 备注
     * @return
     */
    @PostMapping("/handOut")
    public ApiResponse<String> handOut(String id, Integer sl,String zgId,String bz,String jgdm){
        return service.handOut(id,sl,zgId,bz,jgdm);
    }


    /**
     * 数据删除方法
     * 如果对数据要求高，请重写该方法或是不直接继承该类，防止数据泄露
     * @param id
     * @return
     */
    @RequestMapping(value="/remove/{pkid}", method={RequestMethod.POST})
    public ApiResponse<String> remove(@PathVariable("pkid")String id){
        service.remove(id);
        return ApiResponse.success();
    }

    /**
     * 批量入库
     */
    @PostMapping("/saveList")
    public ApiResponse<String> saveList(String kcs){
        List<BizKc> list = new ArrayList<>();
        if(StringUtils.isNotBlank(kcs)){
            list = JsonUtil.toList(kcs, BizKc.class);
        }
        return service.saveList(list);
    }

    /**
     * 批次入库补充
     */
    @PostMapping("/appendPc")
    public ApiResponse<String> appendPc(String kcs,String pc){
        List<BizKc> list = new ArrayList<>();
        if(StringUtils.isNotBlank(kcs)){
            list = JsonUtil.toList(kcs, BizKc.class);
        }
        return service.appendPc(list,pc);
    }




}