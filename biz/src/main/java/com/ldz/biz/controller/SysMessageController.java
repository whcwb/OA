package com.ldz.biz.controller;

import com.github.pagehelper.Page;
import com.ldz.sys.model.SysMessage;
import com.ldz.sys.service.SysMessageService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 消息表下发
 */
@RestController
@RequestMapping("/api/message")
public class SysMessageController {
    @Autowired
    private SysMessageService service;


    @RequestMapping(value="/pager", method={RequestMethod.POST, RequestMethod.GET})
    public ApiResponse<List<SysMessage>> pager(SysMessage entity, Page< SysMessage> pager){
        return service.pager(pager);
    }

    /**
     * 消息重发
     * @param entity
     * @return
     */
    @RequestMapping(value="/repeat", method={RequestMethod.POST, RequestMethod.GET})
    public ApiResponse<String> repeatSendMessage(SysMessage entity){
        return service.repeatSendMessage(entity);
    }
    /**
     * 测试接口后期需要拿掉 todo
     * @param entity
     * @return
     */
    @RequestMapping(value="/add", method={RequestMethod.POST, RequestMethod.GET})
    public ApiResponse<String> add(SysMessage entity){
        service.sendMessage(entity,entity.getSendeeCode(),"ok6iq1SDeT5HObWrMthl0FOKygqM");
        return ApiResponse.success();
    }
}