package com.ldz.wechat.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.service.TrainingRecordService;
import com.ldz.wechat.wxpkg.service.WechatService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpQrcodeService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangwei on 2018/9/25.
 */
@RequestMapping("/api/wechat")
@Controller
public class WeChatController {

    @Autowired
    private WechatService wechatService;

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private TrainingRecordService recordService;

    @Autowired
    private StringRedisTemplate redisDao;
    // 忽略当接收json字符串中没有bean结构中的字段时抛出异常问题
    private ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    /**
     * 教练给学员发送消息
     * @param message
     * @return
     */
    @RequestMapping("/sendMsg")
    public String sendMsg(WxMpTemplateMessage message){
        List<WxMpTemplateData> data = new ArrayList<>();
        data.add(new WxMpTemplateData("first", "专员您好，已为您分配到了新的学员"));
        data.add(new WxMpTemplateData("keyword1", "用户姓名"));
        data.add(new WxMpTemplateData("keyword2", "教练电话"));//教练电话
        data.add(new WxMpTemplateData("keyword3", "您的姓名"));
        data.add(new WxMpTemplateData("remark", "明细信息"));
        WxMpTemplateMessage msg = new WxMpTemplateMessage();
        msg.setToUser("oRPNG0hmdDONPTMQnUAveq7qNkeQ");//用户的OPEN_ID
        msg.setTemplateId("JjeCO5HE0TnA1bj9HWCIlkMNjm2mxO-1PgmM10xens0");//消息模板id
        //        msg.setUrl(wxJlDomain);
        msg.setData(data);
        String res = "";

        try {
            res = wechatService.sendTemplateMsg(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 教练获取二维码
     * 三小时失效
     * @param id
     * @return
     */
    @RequestMapping("/getQrCode")
    public ApiResponse<String> getQrCode(@RequestParam("id" ) String id){
        String qrCode = redisDao.boundValueOps("QRCode_"+id).get();
        if (StringUtils.isEmpty(qrCode)){
            try {
                if(StringUtils.isNotEmpty(id)){
                    WxMpQrcodeService wx = wxMpService.getQrcodeService();
                    WxMpQrCodeTicket wxMpQrCodeTicket = wx.qrCodeCreateTmpTicket(id, 3*60*60);
                    String ticket=wxMpQrCodeTicket.getTicket();
                    String qrcodesFile=wx.qrCodePictureUrl(ticket);
                    redisDao.boundValueOps("QRCode_"+id).set(qrcodesFile, 3, TimeUnit.HOURS);
                }
            }catch (WxErrorException e){
                e.printStackTrace();
            }
        }
        qrCode = redisDao.boundValueOps("QRCode_"+id).get();

        return ApiResponse.success(qrCode);
    }

}
