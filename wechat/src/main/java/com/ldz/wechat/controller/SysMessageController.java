package com.ldz.wechat.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ldz.sys.model.SysMessage;
import com.ldz.sys.service.SysMessageService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/wechatMessage")
public class SysMessageController {

    @Autowired
    private SysMessageService service;

    /**
     * 查询我的消息列表
     * @param entity
     * @return
     */
    @RequestMapping(value="/query", method={RequestMethod.POST, RequestMethod.GET})
    public ApiResponse<List<SysMessage>> query(SysMessage entity, Page<SysMessage> pager){
        ApiResponse<List<SysMessage>> result = new ApiResponse<>();
        SimpleCondition condition = new SimpleCondition(SysMessage.class);
        condition.setOrderByClause(SysMessage.InnerColumn.id.desc());
        condition.eq(SysMessage.InnerColumn.userId,entity.getUserId());
        //只查可见的
        condition.eq(SysMessage.InnerColumn.visiable,"1");
        PageInfo<SysMessage> page = service.findPage(pager, condition);
        result.setPage(page);
        return result;
    }

}
