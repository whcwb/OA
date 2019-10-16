package com.ldz.biz.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.ExcelUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ldz.biz.model.BizException;
import com.ldz.biz.model.BizExceptionConfig;
import com.ldz.biz.service.BizExceptionService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/exception")
public class BizExceptionController extends BaseController<BizException, String> {
    @Autowired
    private BizExceptionService service;

    @Override
    protected BaseService<BizException, String> getBaseService() {
        return service;
    }

    /**
     * 加载所有异常配置参数信息
     * @return
     */
    @GetMapping("/loadConfig")
    public ApiResponse<List<BizExceptionConfig>> loadConfig(){
        return service.getAllConfig();
    }

    @GetMapping("/dashboard")
    public ApiResponse<Map<String, Integer>> dashboard(){
        return service.dashboard();
    }

    /**
     * 导出异常Excel
     */
    @GetMapping("/export")
    public void exportException(String xmLike, String sfzmhm, String code, String kskm, String zt, String bz2, HttpServletRequest request, HttpServletResponse response) throws IOException {
        SimpleCondition condition = new SimpleCondition(BizException.class);
        if (StringUtils.isNotBlank(xmLike)) {
            condition.like(BizException.InnerColumn.xm, xmLike);
        }
        if (StringUtils.isNotBlank(sfzmhm)) {
            condition.eq(BizException.InnerColumn.sfzmhm, sfzmhm);
        }
        if (StringUtils.isNotBlank(code)) {
            condition.eq(BizException.InnerColumn.code, code);
        }
        if (StringUtils.isNotBlank(kskm)) {
            condition.eq(BizException.InnerColumn.kskm, kskm);
        }
        if (StringUtils.isNotBlank(zt)) {
            condition.eq(BizException.InnerColumn.zt, zt);
        }
        if (StringUtils.isNotBlank(bz2)) {
            condition.eq(BizException.InnerColumn.bz2, bz2);
        }
        condition.setOrderByClause(" bz2 asc, cjsj desc, zt asc");
        List<BizException> exceptions = service.findByCondition(condition);
        List<Map<Integer, String>> dataList = new ArrayList<>();
        Map<Integer, String> titleMap = new HashMap<>();
        titleMap.put(0, "序号");
        titleMap.put(1, "姓名");
        titleMap.put(2, "证件号码");
        titleMap.put(3, "报名点");
        titleMap.put(4, "异常时间");
        titleMap.put(5, "异常描述");
        titleMap.put(6, "异常状态");
        dataList.add(titleMap);
        AtomicInteger i = new AtomicInteger();
        exceptions.forEach(e -> {
                    i.getAndIncrement();
                    Map<Integer, String> dataMap = new HashMap<>();
                    dataMap.put(0, i+"");
                    dataMap.put(1,e.getXm());
                    dataMap.put(2,e.getSfzmhm());
                    dataMap.put(3,e.getBz1());
                    dataMap.put(4,e.getCjsj());
                    dataMap.put(5, e.getBz());
                    dataMap.put(6, StringUtils.equals(e.getZt(), "00")?"未处理":"已处理");
                    dataList.add(dataMap);
                }
        );
        response.setContentType("application/msexcel");
        request.setCharacterEncoding("UTF-8");
        response.setHeader("pragma", "no-cache");
        response.addHeader("Content-Disposition", "attachment; filename=" + new String("异常导出".getBytes("utf-8"), "ISO8859-1") + ".xls");
        OutputStream out = response.getOutputStream();
        ExcelUtil.createSheet(out, "异常导出", dataList);

    }

    /**
     * 非本校学员异常处理接口
     */
    @PostMapping("/updateException")
    public ApiResponse<String> updateException(String id){
        return service.updateException(id);
    }


}