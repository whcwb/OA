package com.ldz.sys.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.sys.mapper.SysMessageBizMapper;
import com.ldz.sys.mapper.SysMessageMapper;
import com.ldz.sys.model.SysMessage;
import com.ldz.sys.model.SysMessageBiz;
import com.ldz.sys.service.SysMessageService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.util.redis.RedisTemplateUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/5/10.
 */
@Service
public class SysMessageServiceImpl extends BaseServiceImpl<SysMessage,String> implements SysMessageService{
    Logger payInfo = LoggerFactory.getLogger("access_info");

    @Autowired
    private SysMessageMapper entityMapper;

    @Autowired
    private SysMessageBizMapper bizMapper;
//    @Autowired
//    private RedisTemplate redisDao;

    @Autowired
    private RedisTemplateUtil redisTemplate;

    @Autowired
    @Qualifier("redisOtherDB")
    private RedisTemplateUtil redisDaoOtherDB;

    AsyncEventBus eventBus = new AsyncEventBus(Executors.newFixedThreadPool(1));
    public SysMessageServiceImpl() {
        eventBus.register(this);
    }


    @Override
    protected Mapper<SysMessage> getBaseMapper() {
        return entityMapper;
    }

    /**
     * 按指定字段进行排序
     * @param condition
     * @return
     */
    @Override
    public boolean fillPagerCondition(LimitedCondition condition){
        condition.setOrderByClause(" CREATION_TIME desc");
        return true;
    }

    @Subscribe
    public  void sendObject(Map<String,Object> map){
        payInfo.debug("进入异步通知开始 Async begin---");
        try {
            SysMessage entity= (SysMessage) map.get("entity");
            String phone= (String) map.get("phone");
            String openId= (String) map.get("openId");
            try {
                entity.setId(genId());//生成的ID
                entity.setCreationTime(new Date());//创建时间
                entity.setStatus(0+"");
                entity.setSendCount(0);
                entity.setSendTime(null);//消息发送时间
                String estimatedSendTime=entity.getEstimatedSendTime();//预约下发时间
                if(StringUtils.equals(entity.getType(),"3")){
                    entity.setStatus(1+"");
                    entity.setSendCount(1);
                    entity.setSendTime(new Date());//消息发送时间
                    if(StringUtils.isEmpty(estimatedSendTime)){
                        estimatedSendTime=DateUtils.getNowTime();
                    }
                    entity.setEstimatedSendTime(estimatedSendTime);
                }else{
                    //计算预约时间
                    Date estimated=new Date();
                    if(StringUtils.isNotEmpty(estimatedSendTime)){
                        estimated=DateUtils.getDate(estimatedSendTime,"yyyy-MM-dd HH:mm:ss");
                    }
                    String beginTimeChan="09:00";
                    String endTimeChan="22:00";
                    //设置消息下发时间段
                    SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
                    Date now =null;
                    Date beginTime = null;
                    Date endTime = null;
                    try {
                        now = df.parse(df.format(estimated));
                        beginTime = df.parse(beginTimeChan);
                        endTime = df.parse(endTimeChan);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Boolean flag = DateUtils.belongCalendar(now, beginTime, endTime);
                    if(flag){
                        entity.setEstimatedSendTime(DateUtils.getDateStr(estimated,"yyyy-MM-dd HH:mm:ss"));
                    }else{
                        try {
                            now = df.parse(df.format(estimated));
                            beginTime = df.parse("00:00");
                            endTime = df.parse("08:59");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        flag = DateUtils.belongCalendar(now, beginTime, endTime);
                        if(flag){
                            entity.setEstimatedSendTime(DateUtils.getDateStr(estimated,"yyyy-MM-dd")+" 09:00:00");
                        }else{
                            entity.setEstimatedSendTime(DateUtils.getDateStr(DateUtils.moveTime(new Date(),1),"yyyy-MM-dd")+" 09:00:00");

                        }
                    }
                }
                try {
                    //消息验证
                    ApiResponse<String> verifEntiy=this.addVerifyEntity(entity,openId,phone);
                    if(!verifEntiy.isSuccess()){
                        entity.setStatus(2+"");
                        entity.setSendCount(1);
                        entity.setSendTime(new Date());//消息发送时间
                        entity.setRemark(verifEntiy.getMessage());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                //保存消息
                int i=entityMapper.insert(entity);
                //当状态为短信、微信时，调用消息下发。
                if(StringUtils.equals(entity.getType(),"1")||StringUtils.equals(entity.getType(),"2")){
                    if(i>0&&StringUtils.equals(entity.getStatus(),"0")){//向redis写入标记 由订阅了预约发送和立刻下发的接口，进行处理
                        long interval = (DateUtils.getDate(entity.getEstimatedSendTime(),"yyyy-MM-dd HH:mm:ss").getTime() - new Date().getTime())/1000;
                        if(interval>0){//预约发送的消息
                            redisDaoOtherDB.boundValueOps("sendMessageEstimated"+entity.getEstimatedSendTime().replaceAll(" ","").replaceAll(":","")).set(entity.getEstimatedSendTime(), interval, TimeUnit.SECONDS);
                        }else{
                            //该消息立刻下发
                            redisTemplate.convertAndSend("now_send_message", entity.getId());
                        }
                    }
                }
            }catch (Exception e){e.printStackTrace();}
        }catch (Exception e){}
        payInfo.debug("进入异步通知开始 Async END---");
    }


    /**
     * 发送消息
     * @param entity
     * @param openId
     * @param phone
     */
    public void sendMessage(SysMessage entity, String openId, String phone) {
        Map<String,Object> sendMap=new HashMap<String,Object>();
        sendMap.put("entity",entity);
        sendMap.put("openId",openId);
        sendMap.put("phone",phone);//产品类型
        eventBus.post(sendMap);

    }
    public ApiResponse<String> addVerifyEntity(SysMessage sysMessage, String openId, String phone){
        String status=sysMessage.getStatus();//消息发送状态 [ZDCLK1027]0、未发送 1、已发送  2、发送失败
        if(!StringUtils.equals(status,"0")){
            return ApiResponse.fail("该消息没有处于 未发送 状态，发送失败。");
        }
        String bizId=sysMessage.getBizId();//业务ID
        if(bizId==null){
            return ApiResponse.fail("该消息没有业务ID，发送失败。");
        }
        String userId=sysMessage.getUserId();//接收者ID
        if((StringUtils.isEmpty(userId))){
            return ApiResponse.fail("该消息 未填写接收者ID，发送失败。");
        }

        SysMessageBiz biz=bizMapper.selectByPrimaryKey(bizId);
        if(biz==null){
            return ApiResponse.fail("该消息 业务ID不存在，发送失败。");
        }else{
            String validType=biz.getValidType();
            if(!StringUtils.equals(validType,"1")){
                return ApiResponse.fail("该消息 业务ID已经停用，发送失败。");
            }

            //接收者的角色[ZDCLK1028] 1、学员 2、教练 3、管理员
            sysMessage.setUserRole(biz.getUserRole());
            //是否可见 0 不可见 1 可见
            sysMessage.setVisiable(biz.getVisiable());
            //模板id
            sysMessage.setTempId(biz.getTempId());
            //业务类型 [ZDCLK1026] 1、短信 2、微信
            sysMessage.setType(biz.getBizType());
            if(StringUtils.equals(biz.getBizType(),"1")){
                if(StringUtils.isEmpty(phone)){
                    return ApiResponse.fail("该消息 手机号码为空，发送失败。");
                }else{
                    sysMessage.setSendeeCode(phone);
                }
            }else if(StringUtils.equals(biz.getBizType(),"2")){
                if(StringUtils.isEmpty(openId)){
                    return ApiResponse.fail("该消息 微信的openId为空，发送失败。");
                }else{
                    sysMessage.setSendeeCode(openId);
                }
            }



            //发送的消息
            //模板必填参数
            String tempParameter=biz.getTempParameter();
            String tempContent=biz.getTempContent();
            String message=tempContent;
            if(StringUtils.isNotEmpty(tempParameter)){

                String parameterBody=sysMessage.getParameterBody();
                if(StringUtils.isEmpty(parameterBody)){
                    return ApiResponse.fail("该消息 没有填写参数[parameterBody]，发送失败。");
                }
                Map tempParameterMap = (Map) JSON.parse(tempParameter);
                Map<String,String> parameterBodyMap = (Map<String,String>) JSON.parse(parameterBody);
                for (Object map : tempParameterMap.entrySet()) {
                    String key=((Map.Entry) map).getKey().toString();//key
                    String val=parameterBodyMap.get(key);
                    if(StringUtils.isEmpty(val)){
                        return ApiResponse.fail("该消息 没有填写参数:"+key+" ，发送失败。");
                    }
                    message=message.replaceAll("\\{\\{"+key+".DATA\\}\\}",val+"\n");
                }
                sysMessage.setMessage(message);
            }else {
                sysMessage.setMessage(tempContent);
                sysMessage.setParameterBody(tempContent);
            }
        }

        return ApiResponse.success();
    }


    /**
     * 消息重发
     * @param entity
     * @return
     */
    @Override
    public  ApiResponse<String> repeatSendMessage(SysMessage entity){
        RuntimeCheck.ifBlank(entity.getId(),"请选择需要重发的消息");
        SysMessage message=findById(entity.getId());
        RuntimeCheck.ifNull(message,"请选择需要重发的消息");
        RuntimeCheck.ifTrue(StringUtils.equals(message.getStatus(),"1"),"该消息已经发送成功，无需再次发送");//0、未发送 1、已发送  2、发送失败

        //计算预约时间
        String estimatedSendTime=entity.getEstimatedSendTime();//预约下发时间
        try {
            Date estimated=new Date();
            if(StringUtils.isNotEmpty(estimatedSendTime)){
                estimated=DateUtils.getDate(estimatedSendTime,"yyyy-MM-dd HH:mm:ss");
            }
            String beginTimeChan="09:00";
            String endTimeChan="22:00";
            //设置消息下发时间段
            SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
            Date now =null;
            Date beginTime = null;
            Date endTime = null;
            try {
                now = df.parse(df.format(estimated));
                beginTime = df.parse(beginTimeChan);
                endTime = df.parse(endTimeChan);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Boolean flag = DateUtils.belongCalendar(now, beginTime, endTime);
            if(flag){
                message.setEstimatedSendTime(DateUtils.getDateStr(estimated,"yyyy-MM-dd HH:mm:ss"));
            }else{
                try {
                    now = df.parse(df.format(estimated));
                    beginTime = df.parse("00:00");
                    endTime = df.parse("08:59");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                flag = DateUtils.belongCalendar(now, beginTime, endTime);
                if(flag){
                    message.setEstimatedSendTime(DateUtils.getDateStr(estimated,"yyyy-MM-dd")+" 09:00:00");
                }else{
                    message.setEstimatedSendTime(DateUtils.getDateStr(DateUtils.moveTime(new Date(),1),"yyyy-MM-dd")+" 09:00:00");

                }
            }

            message.setStatus("0");
            int i=this.update(message);
            if(i>0){//向redis写入标记 由订阅了预约发送和立刻下发的接口，进行处理
                long interval = (DateUtils.getDate(estimatedSendTime,"yyyy-MM-dd HH:mm:ss").getTime() - new Date().getTime())/1000;
                if(interval>0){//预约发送的消息
                    redisDaoOtherDB.boundValueOps("sendMessageEstimated"+entity.getEstimatedSendTime().replaceAll(" ","").replaceAll(":","")).set(entity.getEstimatedSendTime(), interval, TimeUnit.SECONDS);
                }else{
                    //该消息立刻下发
                    redisTemplate.convertAndSend("now_send_message", entity.getId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ApiResponse.success();
    }

    /**
     * 立刻下发消息
     * @param taskId 任务ID
     */
    @Override
    public  void nowSendMessage(String taskId){
        SysMessage sysMessage= this.findById(taskId);
        if(sysMessage!=null){
            String status=sysMessage.getStatus();//消息发送状态 [ZDCLK1027]0、未发送 1、已发送  2、发送失败
            String sendeeCode=sysMessage.getSendeeCode();//接收方编号
            if(StringUtils.equals(status,"0")&&StringUtils.isNotEmpty(sendeeCode)){
                String type=sysMessage.getType();//业务类型 [ZDCLK1026] 1、短信 2、微信
                if(StringUtils.equals(type,"1")){
                    //短信队列  sms_send_message_lis
//                    redisTemplate.opsForList().leftPush("sms_send_message_lis",taskId);
                }else if(StringUtils.equals(type,"2")){
                    redisTemplate.opsForList().leftPush("wx_send_message_lis",taskId);
                }
            }
        }

    }
    /**
     * 延时下发
     * @param sendDate 发送的时间
     */
    @Override
    public  void expireMessage(String sendDate){
        System.out.println("1111111111111111111");
        if(StringUtils.isNotEmpty(sendDate)){
            sendDate=sendDate.replaceAll("-","");
            try {
                sendDate=DateUtils.getDateStr(DateUtils.getDate(sendDate,"yyyyMMddHHmmss"),"yyyy-MM-dd HH:mm:ss");
            } catch (ParseException e) {
                sendDate=null;
                e.printStackTrace();
            }
            if(StringUtils.isNotEmpty(sendDate)){
                SimpleCondition condition = new SimpleCondition(SysMessage.class);
                condition.eq(SysMessage.InnerColumn.estimatedSendTime,sendDate);
                List<SysMessage> list=this.findByCondition(condition);
                if(CollectionUtils.isNotEmpty(list)){
                    for(SysMessage message:list){
                        System.out.println("message.getId():"+message.getId());
                        this.nowSendMessage(message.getId());
                    }
                }
            }
        }







    }

}
