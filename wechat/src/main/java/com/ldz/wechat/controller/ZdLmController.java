package com.ldz.wechat.controller;

import com.github.pagehelper.Page;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.sys.mapper.SysZdlmMapper;
import com.ldz.sys.model.SysZdlm;
import com.ldz.sys.service.ZdlmService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 平台字典
 */
@RestController("testzd")
@RequestMapping("/pub/zd")
public class ZdLmController extends BaseController<SysZdlm, String> {
    @Autowired
    private ZdlmService zdlmService;

    @Autowired
    private SysZdlmMapper zdlmMapper;

    @Override
    protected BaseService<SysZdlm, String> getBaseService() {
        return zdlmService;
    }

    /**
     * 字典列表
     * @param page
     * @param entity
     * @return
     */
    @RequestMapping(value="/queryAll", method={RequestMethod.POST})
    public ApiResponse<Map> queryAll(Page<SysZdlm> page, SysZdlm entity) {
        page.setPageSize(10000);
        SimpleCondition condition = new SimpleCondition(SysZdlm.class);
        ApiResponse<List<SysZdlm>>  o =  zdlmService.pager(page);
        List<SysZdlm> reList = o.getPage().getList();
        Map<String,SysZdlm> zdlmMap = reList.stream().collect(Collectors.toMap(SysZdlm::getLmdm,p->p));
        return ApiResponse.success(zdlmMap);
    }


}
