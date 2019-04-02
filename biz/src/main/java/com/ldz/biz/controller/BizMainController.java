package com.ldz.biz.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.ldz.biz.constant.FeeType;
import com.ldz.biz.constant.Status;
import com.ldz.biz.mapper.ChargeManagementMapper;
import com.ldz.biz.model.*;
import com.ldz.biz.service.*;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.commonUtil.ExcelUtil;
import com.ldz.util.commonUtil.HttpUtil;
import com.ldz.util.commonUtil.JsonUtil;
import javafx.scene.chart.XYChart;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
/**
 * 处理用户登陆、登出、查询字典信息等相关访问接口
 *
 * @author Lee
 * @date 2017年8月12日
 */
@RestController
@RequestMapping("/pub")
public class BizMainController {
    @Value("${staticPath}")
    private String path;
    @Autowired
    private TraineeTestInfoService testInfoService;

    @Autowired
    private StringRedisTemplate redisDao;

    @Autowired
    private ChargeManagementService service;

    @Autowired
    private TraineeInformationService informationService;

    @Autowired
    private ChargeManagementMapper chargeManagementMapper;

    @Autowired
    private BizLcJlService jlService;

    @Autowired
    private DataStaService staService;

    @Autowired
    private BizCkService ckService;

    @Autowired
    private BizKcService kcService;



    @RequestMapping(value = "/getTime", method = {RequestMethod.GET,RequestMethod.POST})
    public ApiResponse<String> getTime(String type){
        ApiResponse<String> res = new ApiResponse<>();
        res.setMessage("操作成功");
        res.setResult(DateUtils.getDateStr(new Date(), type));
        res.setCode(200);
        return res;
    }

    /**
     * 获取报号API的 token
     * @return
     */
    @RequestMapping(value = "/getvoicetkey", method = {RequestMethod.GET,RequestMethod.POST})
    public ApiResponse<String> getVoicetKey(){
        String access_token="";
        try {
            String result=redisDao.boundValueOps("getvoicetkey_").get();
            if(StringUtils.isBlank(result)){
                String url="https://openapi.baidu.com/oauth/2.0/token?grant_type=client_credentials&client_id=NfM00GIoLVzHoFDyKiRP85Vq&client_secret=Xxatb330uVgXGue7EYkh05LKGEX15WTe";
                result=HttpUtil.get(url);
                if(StringUtils.isNotBlank(result)){
                    redisDao.boundValueOps("getvoicetkey_").set(result, 15, TimeUnit.DAYS);//设备邀请码，为1天过期
                }
            }
            System.out.println("--------------\n"+result);
            Map<?, ?> bean = JsonUtil.toBean(result, Map.class);
        //        "access_token": "24.54f59fd63ed8b30c76483560ae6ef23b.2592000.1548657983.282335-10267277",
           try {
               access_token= (String) bean.get("access_token");
           }catch (Exception e){e.printStackTrace();}
        }catch (Exception e){e.printStackTrace();}
        if(StringUtils.isNotBlank(access_token)){
            ApiResponse<String> res = new ApiResponse<>();
            res.setMessage("操作成功");
            res.setResult(access_token);
            res.setCode(200);
            return res;
        }else{
            return ApiResponse.fail("获取token失败");
        }
    }

    /**
     * 服务器当前时间的时间戳
     * @return
     */
    @RequestMapping(value = "/getTimeMillis", method = {RequestMethod.GET,RequestMethod.POST})
    public ApiResponse<Long> getDateTime(){
        long time=System.currentTimeMillis();
        return ApiResponse.success(time);
    }
    /**
     * 导出结果
     *
     * @param key      需要下载的key值
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/exportResult/{key}", method = {RequestMethod.GET})
    public void export(@PathVariable("key") String key, HttpServletRequest request, HttpServletResponse response) throws IOException {

        String data = redisDao.boundValueOps(key).get();
        String fileName = redisDao.boundValueOps(key + "_name").get();

        if (org.apache.commons.lang3.StringUtils.isEmpty(data)) {
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            OutputStream ps = response.getOutputStream();
            ps.write("您好,您所导出结果只有一天的有效期，请确认您的地址是否过期".getBytes("UTF-8"));
            return;
        }
        List<Map<Integer, String>> dataList = JsonUtil.toList(data, Map.class);

        if (org.apache.commons.lang3.StringUtils.isEmpty(fileName)) {
            fileName = DateUtils.getToday("yyyyMMddHHmmss");
        }
//        fileName += "处理结果";
        response.setContentType("application/msexcel");
        request.setCharacterEncoding("UTF-8");
        response.setHeader("pragma", "no-cache");
        response.addHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1") + ".xls");
        OutputStream out = response.getOutputStream();
        ExcelUtil.createSheet(out, "结果导出", dataList);
    }


    /**
     * 导出结果
     *
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/chargemanagement/exportResult", method = {RequestMethod.GET})
    public void exportInfo(HttpServletRequest request, HttpServletResponse response, Page<ChargeManagement> pager) throws IOException {

        pager.setPageSize(9999);
        ApiResponse<List<ChargeManagement>> pagerList = service.pager(pager);
        List<Map<Integer, String>> data = new ArrayList<>();
        Map<Integer, String> map = new HashMap<>();
        map.put(0, "序列");
        map.put(1, "姓名");
        map.put(2, "证件号");
        map.put(3, "驾校");
        map.put(4, "费用类型");
        map.put(5,"收支类型");
        map.put(6, "收费记录");
        map.put(7, "时间");
        map.put(8, "操作人");
        data.add(map);
        if (pagerList != null && pagerList.isSuccess()) {
            List<ChargeManagement> dataList = pagerList.getPage().getList();
            if (dataList != null) {
                int i = 1;
                for (ChargeManagement l : dataList) {
                    Map<Integer, String> m = new HashMap<>();
                    m.put(0, i + "");
                    m.put(1, l.getTraineeName());//姓名
                    m.put(2, l.getIdCardNo());//证件号码
                    m.put(3, l.getChargeSource());//驾校
                    m.put(4, l.getChargeName());//费用类型
                    String inOutType = l.getInOutType();
                    m.put(5, StringUtils.equals(inOutType, "10") ? ("支：" ) : ("收：" ));
                    m.put(6,l.getChargeFee()+"");
                    m.put(7, l.getCjsj());
                    m.put(8, l.getCjr());
                    data.add(m);
                }
            }
        }
        String fileName = java.net.URLEncoder.encode(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + ".xls", "UTF-8");
        response.setContentType("application/msexcel");
        request.setCharacterEncoding("UTF-8");
        response.setHeader("pragma", "no-cache");
        response.addHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1") + ".xls");
        OutputStream out = response.getOutputStream();
        ExcelUtil.createSheet(out, "收费结果导出", data);
    }


    /**
     * 考试缴费导出excel
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/exportTestCharge")
    public void exportTestCharge(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false) String jgmcLike, @RequestParam(required = false) String nameLike, @RequestParam(required = false) String idCardNoLike, @RequestParam(required = false) String sign,
                                 @RequestParam(required = false) String firSubPaymentTimeLike, @RequestParam(required = false) String secSubPaymentTimeLike, @RequestParam(required = false) String thirdSubPaymentTimeLike) throws IOException {


        SimpleCondition condition = new SimpleCondition(TraineeInformation.class);
        if (StringUtils.isNotBlank(jgmcLike)) {
            condition.like(TraineeInformation.InnerColumn.jgmc, jgmcLike);
        }
        if (StringUtils.isNotBlank(nameLike)) {
            condition.like(TraineeInformation.InnerColumn.name, nameLike);
        }
        if (StringUtils.isNotBlank(idCardNoLike)) {
            condition.like(TraineeInformation.InnerColumn.idCardNo, idCardNoLike);
        }
        if(StringUtils.isNotBlank(firSubPaymentTimeLike)){
            condition.like(TraineeInformation.InnerColumn.firSubPaymentTime,firSubPaymentTimeLike);
        }
        if(StringUtils.isNotBlank(secSubPaymentTimeLike)){
            condition.like(TraineeInformation.InnerColumn.secSubPaymentTime,secSubPaymentTimeLike);
        }
        if(StringUtils.isNotBlank(thirdSubPaymentTimeLike)){
            condition.like(TraineeInformation.InnerColumn.thirdSubPaymentTime,thirdSubPaymentTimeLike);
        }

        /*if (StringUtils.isNotBlank(sign)) {
            if (org.apache.commons.lang.StringUtils.equals(sign, "8")) {
                // 科目一考试缴费
                condition.like(TraineeInformation.InnerColumn.firSubPaymentTime, DateUtils.getDateStr(new Date(), "yyyy-MM-dd"));
            } else if (org.apache.commons.lang.StringUtils.equals(sign, "5")) {
                // 科目二考试缴费
                condition.like(TraineeInformation.InnerColumn.secSubPaymentTime, DateUtils.getDateStr(new Date(), "yyyy-MM-dd"));
            } else if (org.apache.commons.lang.StringUtils.equals(sign, "6")) {
                // 科目三考试缴费
                condition.like(TraineeInformation.InnerColumn.thirdSubPaymentTime, DateUtils.getDateStr(new Date(), "yyyy-MM-dd"));
            }
        }*/

        List<TraineeInformation> list = informationService.findByCondition(condition);
//        List<TraineeInformation> list = page.getList();


        List<Map<Integer, String>> data = new ArrayList<>();
        Map<Integer, String> map = new HashMap<>();
        map.put(0, "姓名");
        map.put(1, "证件号码");
        map.put(2, "车型");
        map.put(3, "报名点");
        map.put(4, "实付金额");
        map.put(5, "交费方式");
        map.put(6, "考试时间");
        map.put(7, "考试地点");
        map.put(8, "交费时间");
        map.put(9, "备注");
        data.add(map);
        for (TraineeInformation traineeInformation : list) {
            Map<Integer, String> m = new HashMap<>();
            m.put(0, traineeInformation.getName());
            m.put(1, traineeInformation.getIdCardNo());
            m.put(2, traineeInformation.getCarType());
            if (org.apache.commons.lang3.StringUtils.isNotBlank(traineeInformation.getJgmc())) {
                List<String> asList = Arrays.asList(traineeInformation.getJgmc().split("/"));
                if (CollectionUtils.isNotEmpty(asList)) {
                    for (String s : asList) {
                        if (org.apache.commons.lang.StringUtils.contains(s, "队")) {
                            m.put(3, s);
                        }
                    }

                }
            }

            SimpleCondition simpleCondition = new SimpleCondition(ChargeManagement.class);
            simpleCondition.eq(ChargeManagement.InnerColumn.traineeId.name(), traineeInformation.getId());

            SimpleCondition testCondition = new SimpleCondition(TraineeTestInfo.class);
            testCondition.eq(TraineeTestInfo.InnerColumn.traineeId, traineeInformation.getId());


            if (org.apache.commons.lang3.StringUtils.equals(sign, "5")) {
                simpleCondition.eq(ChargeManagement.InnerColumn.chargeCode.name(), FeeType.SEC_SUB);

                testCondition.eq(TraineeTestInfo.InnerColumn.subject, "科目二");
            } else if (org.apache.commons.lang3.StringUtils.equals(sign, "6")) {
                simpleCondition.eq(ChargeManagement.InnerColumn.chargeCode.name(), FeeType.THIRD_SUB);

                testCondition.eq(TraineeTestInfo.InnerColumn.subject, "科目三");
            } else if (org.apache.commons.lang3.StringUtils.equals(sign, "8")) {
                simpleCondition.eq(ChargeManagement.InnerColumn.chargeCode.name(), FeeType.FIR_SUB);

                testCondition.eq(TraineeTestInfo.InnerColumn.subject, "科目一");
            }
            testCondition.setOrderByClause(" test_time desc");
            List<ChargeManagement> managements = service.findByCondition(simpleCondition);
            ChargeManagement chargeManagement = null;
            if (CollectionUtils.isNotEmpty(managements)) {
                chargeManagement = managements.get(0);
            }
            if (chargeManagement != null) {
                m.put(4, chargeManagement.getChargeFee() + "");
                m.put(5, org.apache.commons.lang.StringUtils.equals(chargeManagement.getChargeType(), "00") ? "现金" : org.apache.commons.lang.StringUtils.equals(chargeManagement.getChargeType(), "10") ? "扫码" : "POS机");
                m.put(9, chargeManagement.getRemark());
            }

            List<TraineeTestInfo> testInfos = testInfoService.findByCondition(testCondition);
            if (CollectionUtils.isNotEmpty(testInfos)) {
                TraineeTestInfo testInfo = testInfos.get(0);
                if (DateUtils.getDateStr(new Date(), "yyyy-Mm-dd").compareTo(testInfo.getTestTime()) <= 0) {
                    m.put(6, testInfo.getTestTime());
                    m.put(7, testInfo.getTestPlace());
                    m.put(8, testInfo.getCjsj());
                }

            }
            data.add(m);
        }

        List<Map<Integer, String>> collect = data.stream().sorted((o1, o2) -> o2.get(3).compareTo(o1.get(3))).collect(Collectors.toList());
        String fileName = java.net.URLEncoder.encode(DateFormatUtils.format(new Date(), "yyyy-MM-dd") + "考试收费", "UTF-8");
        response.setContentType("application/msexcel");
        request.setCharacterEncoding("UTF-8");
        response.setHeader("pragma", "no-cache");
        response.addHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1") + ".xls");
        OutputStream out = response.getOutputStream();
        ExcelUtil.createSheet(out, "考试收费导出", collect);
    }


    /**
     * 导出结果
     *
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/exportResult", method = {RequestMethod.GET})
    public void export(HttpServletRequest request, HttpServletResponse response, Page<TraineeInformation> pager) throws IOException {


        Map<String, String> statusMap = new HashMap<>();
        statusMap.put(Status.SIGN_UP, "报名中");
        statusMap.put("00", "受理中");
        statusMap.put("0", "受理中");
        statusMap.put("10", "科目一学习中");
        statusMap.put("20", "科目二学习中");
        statusMap.put("30", "科目三学习中");
        statusMap.put("40", "科目四学习中");
        statusMap.put("50", "结业");
        statusMap.put("60", "退学");

        pager.setPageSize(99999);
        ApiResponse<List<TraineeInformation>> pagerList = informationService.pager(pager);
        Map<String, String> tableName = Maps.newLinkedHashMap();
        tableName.put("name", "姓名");
        tableName.put("idCardNo", "证件号码");
        tableName.put("jgmc", "报名点");
        tableName.put("serialNum", "流水号");
        tableName.put("gender", "性别");
        tableName.put("source", "本地/外地");
        tableName.put("carType", "车型");
        tableName.put("registrationTime", "报名时间");
        tableName.put("status", "状态");
        tableName.put("recordLib", "档案存放");
        tableName.put("indateEndTime", "有效期");
        tableName.put("secondSubjectCoach", "科二教练员");
        tableName.put("thirdSubjectCoach", "科三教练员");
        tableName.put("chargeStatus", "报名费");
        tableName.put("firType", "科一初考费");
        tableName.put("secType", "科二初考费");
        tableName.put("thirdType", "科三初考费");
        tableName.put("firSub", "科一状态");
        tableName.put("firSubTestTime", "科一考试时间");
        tableName.put("firSubTestNum", "科一考试次数");
        tableName.put("secSub", "科二状态");
        tableName.put("secSubTestTime", "科二考试时间");
        tableName.put("secSubTestNum", "科二考试次数");
        tableName.put("thirdSub", "科三状态");
        tableName.put("thirdSubTestTime", "科三考试时间");
        tableName.put("thirdSubTestNum", "科三考试次数");
        tableName.put("forthSub", "科四状态");
        tableName.put("remark", "备注");
        List<Map<String, String>> data = new ArrayList<>();
        if (pagerList != null && pagerList.isSuccess()) {
            List<TraineeInformation> dataList = pagerList.getPage().getList();
            int seq = 1;
            for (TraineeInformation l : dataList) {
                Map<String, String> m = new HashMap<>();
                m.put("seq", seq + "");//序列
                m.put("name", l.getName());//姓名
                m.put("idCardNo", l.getIdCardNo());//证件号码
                m.put("jgmc", l.getJgmc());//报名点
                String serialNum = l.getSerialNum();
                m.put("serialNum", StringUtils.isBlank(serialNum) ? "待受理" : serialNum);//流水号
                String gender = l.getGender();
                m.put("gender", StringUtils.equals(gender, "10") ? "男" : "女");//性别
                String source = l.getSource();
                m.put("source", StringUtils.equals(source, "00") ? "本地" : "外地");//本地/外地
                String carType = l.getCarType();
                if (StringUtils.isEmpty(carType)) {
                    carType = "-";
                }
                m.put("carType", carType);//车型
                m.put("registrationTime", l.getRegistrationTime());//l.getRegistrationTime()   报名时间
                String status = l.getStatus();
                m.put("status", StringUtils.isNotEmpty(status) ? statusMap.get(status) : "-");
                m.put("recordLib", l.getRecordLib());
                m.put("indateEndTime", l.getIndateEndTime());//有效期
                m.put("secondSubjectCoach", l.getSecondSubjectCoach());//科二教练员
                m.put("thirdSubjectCoach", l.getThirdSubjectCoach());//科三教练员
                String chargeStatus = l.getChargeStatus();
                String chargeName = "未结清";
                if (StringUtils.isNotEmpty(chargeStatus)) {
                    if (StringUtils.equals(chargeStatus, "10") && StringUtils.equals(l.getArrearage(), "00")) {
                        chargeName = "已结清";
                    }
                } else {
                    chargeName = "-";
                }
                m.put("chargeStatus", chargeName);//报名费
                String firTypeName = "已缴";
                if (StringUtils.equals(l.getAcceptStatus(), "10") && StringUtils.isEmpty(l.getFirSubPaymentTime())) {  //todo
                    firTypeName = "待缴";
                }
                m.put("firType", firTypeName);//科一初考费
                String secTypeName = "已缴";
                if (StringUtils.equals(l.getFirSub(), "40") && StringUtils.isEmpty(l.getSecSubPaymentTime())) {
                    secTypeName = "待缴";
                }
                m.put("secType", secTypeName);//科二初考费
                String thirdTypeName = "已缴";
                if (StringUtils.equals(l.getSecSub(), "40") && StringUtils.isEmpty(l.getThirdSubPaymentTime())) {
                    thirdTypeName = "待缴";
                }
                m.put("thirdType", thirdTypeName);//科三初考费
                String firSubName = "-";
                if (StringUtils.equals(l.getFirSub(), "00")) {
                    firSubName = "已缴费";
                } else if (StringUtils.equals(l.getFirSub(), "10")) {
                    firSubName = "学习中";
                } else if (StringUtils.equals(l.getFirSub(), "20")) {
                    firSubName = "已约考";
                } else if (StringUtils.equals(l.getFirSub(), "30")) {
                    firSubName = "不合格";
                } else if (StringUtils.equals(l.getFirSub(), "40")) {
                    firSubName = "合格";
                }
                m.put("firSub", firSubName);//科一状态
                String firSubTestTime = l.getFirSubTestTime();
                if (StringUtils.isEmpty(firSubTestTime)) {
                    firSubTestTime = "-";
                }
                m.put("firSubTestTime", firSubTestTime);//科一考试时间
                m.put("firSubTestNum", l.getFirSubTestNum() == null ? 0 + "" : l.getFirSubTestNum() + "");//科一考试次数
                String secSubName = "-";
                if (StringUtils.equals(l.getSecSub(), "00")) {
                    secSubName = "已缴费";
                } else if (StringUtils.equals(l.getSecSub(), "10")) {
                    secSubName = "学习中";
                } else if (StringUtils.equals(l.getSecSub(), "20")) {
                    secSubName = "已约考";
                } else if (StringUtils.equals(l.getSecSub(), "30")) {
                    secSubName = "不合格";
                } else if (StringUtils.equals(l.getSecSub(), "40")) {
                    secSubName = "合格";
                }

                m.put("secSub", secSubName);//科二状态
                String secSubTestTime = l.getSecSubTestTime();
                if (StringUtils.isEmpty(secSubTestTime)) {
                    secSubTestTime = "-";
                }
                m.put("secSubTestTime", secSubTestTime);//科二考试时间
                m.put("secSubTestNum", l.getSecSubTestNum() == null ? 0 + "" : l.getSecSubTestNum() + "");//科二考试次数
                String thirdSubName = "-";
                if (StringUtils.equals(l.getThirdSub(), "00")) {
                    thirdSubName = "已缴费";
                } else if (StringUtils.equals(l.getThirdSub(), "10")) {
                    thirdSubName = "学习中";
                } else if (StringUtils.equals(l.getThirdSub(), "20")) {
                    thirdSubName = "已约考";
                } else if (StringUtils.equals(l.getThirdSub(), "30")) {
                    thirdSubName = "不合格";
                } else if (StringUtils.equals(l.getThirdSub(), "40")) {
                    thirdSubName = "合格";
                }
                m.put("thirdSub", thirdSubName);//科三状态
                String thirdSubTestTime = l.getThirdSubTestTime();
                if (StringUtils.isEmpty(thirdSubTestTime)) {
                    thirdSubTestTime = "-";
                }
                m.put("thirdSubTestTime", thirdSubTestTime);//科三考试时间
                m.put("thirdSubTestNum", l.getThirdSubTestNum() == null ? 0 + "0" : l.getThirdSubTestNum() + "");//科三考试次数
                String forthSubName = "-";
                if (StringUtils.equals(l.getForthSub(), "00")) {
                    forthSubName = "已约考";
                } else if (StringUtils.equals(l.getForthSub(), "20")) {
                    forthSubName = "合格";

                }
                m.put("forthSub", forthSubName);//科四状态
                m.put("remark", l.getRemark());//备注

                data.add(m);
                seq++;
            }
        }


//        为excel表格增加选项卡
        List<Map<String, Object>> sheetList = new ArrayList<>();
//        全部选项卡
        Map<String, Object> sheelMap = new HashMap<>();
        sheelMap.put("name", "全部");
        sheelMap.put("sheelMap", tableName);
        sheetList.add(sheelMap);
//        基础选项卡
        Map<String, Object> sheelMapInfo = new HashMap<>();
        Map<String, String> tableNameInfo = Maps.newLinkedHashMap();
        tableNameInfo.put("name", "姓名");
        tableNameInfo.put("idCardNo", "证件号码");
        tableNameInfo.put("jgmc", "报名点");
        tableNameInfo.put("serialNum", "流水号");
        tableNameInfo.put("gender", "性别");
        tableNameInfo.put("source", "本地/外地");
        tableNameInfo.put("carType", "车型");
        tableNameInfo.put("registrationTime", "报名时间");
        tableNameInfo.put("status", "状态");
        tableNameInfo.put("recordLib", "档案存放");
        tableNameInfo.put("indateEndTime", "有效期");
        tableNameInfo.put("secondSubjectCoach", "科二教练员");
        tableNameInfo.put("thirdSubjectCoach", "科三教练员");
        tableNameInfo.put("remark", "备注");
        sheelMapInfo.put("name", "基本信息");
        sheelMapInfo.put("sheelMap", tableNameInfo);
        sheetList.add(sheelMapInfo);

//        缴费数据
        Map<String, Object> sheelMapPay = new HashMap<>();
        Map<String, String> tableNamePay = Maps.newLinkedHashMap();
        tableNamePay.put("seq", "序列");
        tableNamePay.put("name", "姓名");
        tableNamePay.put("idCardNo", "证件号码");
        tableNamePay.put("jgmc", "报名点");
        tableNamePay.put("serialNum", "流水号");
        tableNamePay.put("gender", "性别");
        tableNamePay.put("source", "本地/外地");
        tableNamePay.put("carType", "车型");
        tableNamePay.put("chargeStatus", "报名费");
        tableNamePay.put("firType", "科一初考费");
        tableNamePay.put("secType", "科二初考费");
        tableNamePay.put("thirdType", "科三初考费");

        tableNamePay.put("remark", "备注");
        sheelMapPay.put("name", "缴费数据");
        sheelMapPay.put("sheelMap", tableNamePay);
        sheetList.add(sheelMapPay);

        //        考试数据
        Map<String, Object> sheelMapExa = new HashMap<>();
        Map<String, String> tableNameExa = Maps.newLinkedHashMap();
        tableNameExa.put("name", "姓名");
        tableNameExa.put("idCardNo", "证件号码");
        tableNameExa.put("jgmc", "报名点");
        tableNameExa.put("serialNum", "流水号");
        tableNameExa.put("gender", "性别");
        tableNameExa.put("source", "本地/外地");
        tableNameExa.put("carType", "车型");

        tableNameExa.put("firSub", "科一状态");
        tableNameExa.put("firSubTestTime", "科一考试时间");
        tableNameExa.put("firSubTestNum", "科一考试次数");
        tableNameExa.put("secSub", "科二状态");
        tableNameExa.put("secSubTestTime", "科二考试时间");
        tableNameExa.put("secSubTestNum", "科二考试次数");
        tableNameExa.put("thirdSub", "科三状态");
        tableNameExa.put("thirdSubTestTime", "科三考试时间");
        tableNameExa.put("thirdSubTestNum", "科三考试次数");
        tableNameExa.put("forthSub", "科四状态");
        tableNameExa.put("remark", "备注");


        sheelMapExa.put("name", "考试数据");
        sheelMapExa.put("sheelMap", tableNameExa);
        sheetList.add(sheelMapExa);


        String fileName = java.net.URLEncoder.encode(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + ".xls", "UTF-8");
        response.setContentType("application/msexcel");
        request.setCharacterEncoding("UTF-8");
        response.setHeader("pragma", "no-cache");
        response.addHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1") + ".xls");
        OutputStream out = response.getOutputStream();
        ExcelUtil.createSheetArray(out, data, sheetList);
    }







    @GetMapping("/getNdbb")
    public void excelSignUp(HttpServletRequest request,HttpServletResponse response,String startTime, String endTime) throws ParseException, IOException {
        List<StudentAllModel> models = new ArrayList<>();

        if(org.apache.commons.lang3.StringUtils.isBlank(startTime)){
            startTime = DateUtils.getDateStr(new Date(),"yyyy") + "-01-01 00:00:00";
        }else{
            startTime = startTime + " 00:00:00";
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(endTime)){
            endTime = DateUtils.getDateStr(new Date(),"yyyy") + "-12-31 23:59:59";
        }else {
            endTime =endTime + " 23:59:59";
        }
        List<String> monthBetween = DateUtils.getMonthBetween(startTime.substring(0,4)+"-01", endTime.substring(0,4)+"-12");


        List<ChargeManagement> managements = chargeManagementMapper.getAllIn2( startTime, endTime);
        List<Map<Integer,String>> data = new ArrayList<>();
        Map<Integer,String> map = new HashMap<>();
        map.put(0,"机构");
        map.put(1, "1月");
        map.put(2,"人数");
        map.put(3,"2月");
        map.put(4,"人数");
        map.put(5,"3月");
        map.put(6,"人数");
        map.put(7,"4月");
        map.put(8,"人数");
        map.put(9,"5月");
        map.put(10,"人数");
        map.put(11,"6月");
        map.put(12, "人数");
        map.put(13,"7月");
        map.put(14,"人数");
        map.put(15,"8月");
        map.put(16,"人数");
        map.put(17,"9月");
        map.put(18,"人数");
        map.put(19,"10月");
        map.put(20,"人数");
        map.put(21,"11月");
        map.put(22,"人数");
        map.put(23,"12月");
        map.put(24,"人数");
        map.put(25,"合计");
        data.add(map);

        if(CollectionUtils.isNotEmpty(managements)){
            LinkedHashMap<String, List<ChargeManagement>> collect = managements.stream().collect(Collectors.groupingBy(ChargeManagement::getChargeSource, LinkedHashMap::new, Collectors.toList()));


            for (String s : collect.keySet()) {
                Map<Integer,String> m = new HashMap<>();

                StudentAllModel allModel = new StudentAllModel();
                List<StudentAllModel.StuAll> stuAlls = new ArrayList<>();

                m.put(0,s.split("/")[0]);
                // 当前机构的所有报名费
                List<ChargeManagement> chargeManagements = collect.get(s);

                for (String mon : monthBetween) {
                    Long i = 0L;
                    Long count = 0L;
                    StudentAllModel.StuAll  stuAll = new StudentAllModel.StuAll();
                    stuAll.setTime(mon);
                    for (ChargeManagement chargeManagement : chargeManagements) {

                        if(chargeManagement.getCjsj().startsWith(mon)){
                            count+=chargeManagement.getChargeFee();
                            i++;
                        }
                    }
                    stuAll.setCount(count);
                    stuAll.setSignUp(i);
                    stuAlls.add(stuAll);
                    if(StringUtils.contains(mon,"-01")){
                        m.put(1,count+"");
                        m.put(2,i+"");
                    }else if(StringUtils.contains(mon,"-02")){
                        m.put(3,count+"");
                        m.put(4,i+"");
                    }else if(StringUtils.contains(mon,"-03")){
                        m.put(5,count+"");
                        m.put(6,i+"");
                    }else if(StringUtils.contains(mon,"-04")){
                        m.put(7,count+"");
                        m.put(8,i+"");
                    }else if(StringUtils.contains(mon,"-05")){
                        m.put(9,count+"");
                        m.put(10,i+"");
                    }else if(StringUtils.contains(mon,"-06")){
                        m.put(11,count+"");
                        m.put(12,i+"");
                    }else if(StringUtils.contains(mon,"-07")){
                        m.put(13,count+"");
                        m.put(14,i+"");
                    }else if(StringUtils.contains(mon,"-08")){
                        m.put(15,count+"");
                        m.put(16,i+"");
                    }else if(StringUtils.contains(mon,"-09")){
                        m.put(17,count+"");
                        m.put(18,i+"");
                    }else if(StringUtils.contains(mon,"-10")){
                        m.put(19,count+"");
                        m.put(20,i+"");
                    }else if(StringUtils.contains(mon,"-11")){
                        m.put(21,count+"");
                        m.put(22,i+"");
                    }else if(StringUtils.contains(mon,"-12")){
                        m.put(23,count+"");
                        m.put(24,i+"");
                    }


                }
                allModel.setAll(chargeManagements.stream().mapToLong(ChargeManagement::getChargeFee).sum());
                allModel.setStu(stuAlls);
                models.add(allModel);
                m.put(25,chargeManagements.stream().mapToLong(ChargeManagement::getChargeFee).sum() + "");
                data.add(m);
            }
        }
        if(CollectionUtils.isNotEmpty(models)){

            Map<Integer,String> m = new HashMap<>();

            List<List<StudentAllModel.StuAll>> collect = models.stream().map(StudentAllModel::getStu).collect(Collectors.toList());
            List<StudentAllModel.StuAll> models1 =  collect.stream().filter(item ->CollectionUtils.isNotEmpty(item)).reduce(new ArrayList<>(),(all,item)-> {all.addAll(item);return all;});
            LinkedHashMap<String, Long> collect1 = models1.stream().collect(Collectors.groupingBy(StudentAllModel.StuAll::getTime, LinkedHashMap::new, Collectors.summingLong(StudentAllModel.StuAll::getCount)));
            LinkedHashMap<String, Long> collect2 = models1.stream().collect(Collectors.groupingBy(StudentAllModel.StuAll::getTime, LinkedHashMap::new, Collectors.summingLong(StudentAllModel.StuAll::getSignUp)));
            long sum = models.stream().mapToLong(StudentAllModel::getAll).sum();
            m.put(0,"合计");
            m.put(25,sum+"");
            for (String s : collect1.keySet()) {
                Long aLong = collect1.get(s);
                if(StringUtils.contains(s,"-01")){
                    m.put(1,aLong+"");
                    m.put(2,collect2.get(s)+"");
                }else if(StringUtils.contains(s,"-02")){
                    m.put(3,aLong+"");
                    m.put(4,collect2.get(s)+"");
                }else if(StringUtils.contains(s,"-03")){
                    m.put(5,aLong+"");
                    m.put(6,collect2.get(s)+"");
                }else if(StringUtils.contains(s,"-04")){
                    m.put(7,aLong+"");
                    m.put(8,collect2.get(s)+"");
                }else if(StringUtils.contains(s,"-05")){
                    m.put(9,aLong+"");
                    m.put(10,collect2.get(s)+"");
                }else if(StringUtils.contains(s,"-06")){
                    m.put(11,aLong+"");
                    m.put(12,collect2.get(s)+"");
                }else if(StringUtils.contains(s,"-07")){
                    m.put(13,aLong+"");
                    m.put(14,collect2.get(s)+"");
                }else if(StringUtils.contains(s,"-08")){
                    m.put(15,aLong+"");
                    m.put(16,collect2.get(s)+"");
                }else if(StringUtils.contains(s,"-09")){
                    m.put(17,aLong+"");
                    m.put(18,collect2.get(s)+"");
                }else if(StringUtils.contains(s,"-10")){
                    m.put(19,aLong+"");
                    m.put(20,collect2.get(s)+"");
                }else if(StringUtils.contains(s,"-11")){
                    m.put(21,aLong+"");
                    m.put(22,collect2.get(s)+"");
                }else if(StringUtils.contains(s,"-12")){
                    m.put(23,aLong+"");
                    m.put(24,collect2.get(s)+"");
                }
            }
            data.add(m);
        }

        String fileName = java.net.URLEncoder.encode(DateFormatUtils.format(new Date(), "yyyy-MM-dd") + "年度报表", "UTF-8");
        response.setContentType("application/msexcel");
        request.setCharacterEncoding("UTF-8");
        response.setHeader("pragma", "no-cache");
        response.addHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1") + ".xls");
        OutputStream out = response.getOutputStream();
        ExcelUtil.createSheet(out, "年度报表", data);

    }


    /**
     * excel导出
     */
    @GetMapping("/exportExcel")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response,ChargeManagement entity) throws IOException {
        service.exportExcel(request, response, entity);
    }

    /**
     * 科目待缴费学员导出
     */
    @GetMapping("/exportTestExcel")
    public void exportTestExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SimpleCondition condition = new SimpleCondition(TraineeInformation.class);
        String sign = request.getParameter("sign");
        String jgmcLike = request.getParameter("jgmcLike");
        String idCardNoLike = request.getParameter("idCardNoLike");
        String jgdm = request.getParameter("jgdm");
        String firSubTestTimeLike = request.getParameter("firSubTestTimeLike");
        String secSubTestTimeLike = request.getParameter("secSubTestTimeLike");
        String thirdSubTestTimeLike = request.getParameter("thirdSubTestTimeLike");
        if(StringUtils.isNotBlank(jgmcLike)){
            condition.like(TraineeInformation.InnerColumn.jgmc,jgmcLike);
        }
        if(StringUtils.isNotBlank(jgdm) ){
            condition.eq(TraineeInformation.InnerColumn.jgdm,jgdm);
        }
        if(StringUtils.isNotBlank(idCardNoLike)){
            condition.like(TraineeInformation.InnerColumn.idCardNo,idCardNoLike);
        }
        List<Map<Integer,String>> data = new ArrayList<>();
        if(StringUtils.equals(sign,"7")){
            condition.and().andCondition(" (status='10' and (fir_sub_test_num =0 or fir_sub_test_num =1)  and (fir_sub is null or fir_sub = '')) or fir_sub='30' and fir_sub='40'");
            condition.and().andCondition("fir_sub_payment_time is  null or fir_sub_payment_time = '' ");
            condition.eq(TraineeInformation.InnerColumn.acceptStatus, "20");
            if(StringUtils.isNotBlank(firSubTestTimeLike)){
                condition.like(TraineeInformation.InnerColumn.firSubTestTime,firSubTestTimeLike);
            }
        }else if(StringUtils.equals(sign,"2")){
            condition.and().andCondition(" (sec_sub = '10' and status='20' and sec_sub_test_num = 1) or sec_sub='30' or sec_sub = '40'");
            condition.and().andCondition("sec_sub_payment_time is null or sec_sub_payment_time = ''");
            if(StringUtils.isNotBlank(secSubTestTimeLike)){
                condition.like(TraineeInformation.InnerColumn.secSubTestTime,secSubTestTimeLike);
            }
        }else if(StringUtils.equals(sign,"3")){
            condition.and().andCondition(" (third_sub_test_num =1 and third_sub = '10' and status='30') or third_sub='30' or third_sub='40'");
            condition.and().andCondition("third_sub_payment_time is null or third_sub_payment_time = ''"); // 考试费未缴纳
            if(StringUtils.isNotBlank(thirdSubTestTimeLike)){
                condition.like(TraineeInformation.InnerColumn.thirdSubTestTime,thirdSubTestTimeLike);
            }
        }
        Map<Integer,String> map = new HashMap<>();
        map.put(0,"序号");
        map.put(1,"姓名");
        map.put(2,"证件号码");
        map.put(3,"车型");
        map.put(4,"报名点");
        map.put(5,"金额");
        map.put(6,"考试科目");
        map.put(7,"考试时间");
        map.put(8,"机构代码");
        data.add(map);
        List<Map<Integer,String>> coll = new ArrayList<>();
        List<TraineeInformation> informations = informationService.findByCondition(condition);
        for(int i = 0; i<informations.size() ; i++){
            TraineeInformation information = informations.get(i);
            Map<Integer,String> m = new HashMap<>();
            m.put(0,i+1+"");
            m.put(1,information.getName());
            m.put(2,information.getIdCardNo());
            m.put(3,information.getCarType());
            m.put(4,information.getJgmc());
            m.put(8,information.getJgdm());
            if(StringUtils.equals(sign, "7")){
                m.put(5,"120");
                m.put(6,"科目一");
                if(StringUtils.isNotBlank(information.getFirSubTestTime())) {
                    if (DateUtils.getDateStr(new Date(), "yyyy-MM-dd").compareTo(information.getFirSubTestTime().substring(0, 10)) <= 0) {
                        m.put(7, information.getFirSubTestTime());
                    } else {
                        m.put(7, "");
                    }
                }else{
                    m.put(7, "");
                }
            }else if(StringUtils.equals(sign, "2")){
                m.put(5,"150");
                m.put(6,"科目二");
                if(StringUtils.isNotBlank(information.getSecSubTestTime())){
                    if(DateUtils.getDateStr(new Date(),"yyyy-MM-dd").compareTo( information.getSecSubTestTime().substring(0,10)) <= 0){
                        m.put(7,information.getSecSubTestTime());
                    }else{
                        m.put(7,"");
                    }
                }else{
                    m.put(7,"");
                }
            }else if(StringUtils.equals(sign , "3")){
                m.put(5,"232");
                m.put(6,"科目三");
                if(StringUtils.isNotBlank(information.getThirdSubTestTime())) {
                    if (DateUtils.getDateStr(new Date(), "yyyy-MM-dd").compareTo(information.getThirdSubTestTime().substring(0, 10)) <= 0) {
                        m.put(7, information.getThirdSubTestTime());
                    } else {
                        m.put(7, "");
                    }
                }else{
                    m.put(7,"");
                }
            }

            coll.add(m);


        }
        List<Map<Integer, String>> collect = coll.stream().sorted((o1, o2) -> o1.get(8).compareTo(o2.get(8))).collect(Collectors.toList());
        data.addAll(collect);
        String fileName = java.net.URLEncoder.encode((sign.equals("7") ? "科目一": sign.equals("2")? "科目二": sign.equals("3") ? "科目三":"") + "考试待缴", "UTF-8");
        response.setContentType("application/msexcel");
        request.setCharacterEncoding("UTF-8");
        response.setHeader("pragma", "no-cache");
        response.addHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1") + ".xls");
        OutputStream out = response.getOutputStream();
        ExcelUtil.createSheet(out, (sign.equals("7") ? "科目一": sign.equals("2")? "科目二": sign.equals("3") ? "科目三":"") + "考试代缴", data);
    }


    /**
     * 明细统计 Excel导出
     */
    @GetMapping("/pagerExcel")
    public void pagerExcel(Page<BizLcJl> page, HttpServletRequest request, HttpServletResponse response) throws IOException {
        jlService.pagerExcel(page,request, response);
    }

    /**
     *  驾校统计 Excel 导出
     */
    @GetMapping("/jxtjExcel")
    public void jxtjExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String time = DateUtils.getDateStr(new Date(), "yyyy-MM-dd");
        String fileName = time + "驾校统计";
        List<Map<String, Object>> maps = jlService.drivingSchoolStatistics();
        List<Map<Integer,String>> data = new ArrayList<>();
        Map<Integer,String> map = new HashMap<>();
        map.put(0,"序号");
        map.put(1,"驾校");
        map.put(2,"时长");
        map.put(3,"收费（元）");
        data.add(map);
        long zj = 0;
        for (int i = 0 ; i<maps.size(); i++){
            Map<String, Object> objectMap = maps.get(i);
            Map<Integer,String> dataMap = new HashMap<>();
            dataMap.put(0,i+1+"");
            dataMap.put(1,objectMap.get("jlJx")+"");
            dataMap.put(2,objectMap.get("lcSc")+"");
            dataMap.put(3,objectMap.get("lcFy")+"");
            zj += ((BigDecimal)objectMap.get("lcFy")).longValue();
            data.add(dataMap);
        }
        Map<Integer,String> dataMap = new HashMap<>();
        dataMap.put(0,"合计:");
        dataMap.put(1,"");
        dataMap.put(2, "");
        dataMap.put(3,zj+"");
        data.add(dataMap);
        response.setContentType("application/msexcel");
        request.setCharacterEncoding("UTF-8");
        response.setHeader("pragma", "no-cache");
        response.addHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1") + ".xls");
        OutputStream out = response.getOutputStream();
        ExcelUtil.createSheet(out,"驾校统计",data);
    }

    /**
     * 教练统计 Excel 导出
     */
    @GetMapping("/jlExcel")
    public void jlExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String time = DateUtils.getDateStr(new Date(), "yyyy-MM-dd");
        String fileName = time + "教练统计";

        ApiResponse<List<LcJlModel>> jlTj = jlService.getJlTj();
        List<LcJlModel> result = jlTj.getResult();
        List<Map<Integer,String>> data = new ArrayList<>();
        Map<Integer,String> map = new HashMap<>();
        map.put(0,"姓名");
        map.put(1,"驾校");
        map.put(2,"时长");
        map.put(3,"收费（元）");
        data.add(map);
        long zj = 0;
        for(int i = 0 ; i < result.size(); i++){
            LcJlModel model = result.get(i);
            Map<Integer,String> dataMap = new HashMap<>();
            dataMap.put(0,model.getJlXm());
            dataMap.put(1,model.getJlJx());
            Integer l = model.getSc();
            if (l / 60 == 0) {
                dataMap.put(2, l + "分");
            } else {
                dataMap.put(2, (l / 60) + "时" + (l % 60) + "分");
            }
            dataMap.put(3,model.getZj()+"");
            zj += model.getZj();
            data.add(dataMap);
        }
        Map<Integer,String> dataMap = new HashMap<>();
        dataMap.put(0,"合计:");
        dataMap.put(1,"");
        dataMap.put(2, "");
        dataMap.put(3,zj+"");
        data.add(dataMap);
        response.setContentType("application/msexcel");
        request.setCharacterEncoding("UTF-8");
        response.setHeader("pragma", "no-cache");
        response.addHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1") + ".xls");
        OutputStream out = response.getOutputStream();
        ExcelUtil.createSheet(out,"教练统计",data);
    }

    /**
     * 科三安全员日志 Excel导出
     */
    @GetMapping("/aqyExcel")
    public void aqyExcel( HttpServletRequest request, HttpServletResponse response) throws WriteException, IOException {
        String time = DateUtils.getDateStr(new Date(), "yyyy-MM-dd");
        String fileName = time + "安全员工作日志";
        ApiResponse<List<LcJlModel>> allLog = jlService.getAllLog();
        List<LcJlModel> result = allLog.getResult();
        WritableCellFormat cellFormat = new WritableCellFormat();
        cellFormat.setAlignment(Alignment.CENTRE);
        cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
        cellFormat.setBorder(jxl.format.Border.ALL, BorderLineStyle.THIN);
        response.setContentType("application/msexcel");
        request.setCharacterEncoding("UTF-8");
        response.setHeader("pragma", "no-cache");
        response.addHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1") + ".xls");
        OutputStream out = response.getOutputStream();


        WritableWorkbook workbook = Workbook.createWorkbook(out);
        WritableSheet sheet = workbook.createSheet("工作日志", 0);

        sheet.mergeCells(0,0,0,1);
        sheet.mergeCells(1,0,1,1);
        sheet.mergeCells(2,0,2,1);
        sheet.mergeCells(3,0,4,0);
        sheet.mergeCells(5,0,6,0);
        sheet.mergeCells(7,0,8,0);
        sheet.mergeCells(9,0,10,0);
        sheet.mergeCells(11,0,12,0);
        sheet.mergeCells(13,0,14,0);
        sheet.mergeCells(15,0,15,1);
        sheet.addCell(new Label(0,0,"序号",cellFormat));
        sheet.addCell(new Label(1,0,"姓名",cellFormat));
        sheet.addCell(new Label(2,0,"车号",cellFormat));
        sheet.addCell(new Label(3,0,"第一趟",cellFormat));
        sheet.addCell(new Label(3,1,"人数",cellFormat));
        sheet.addCell(new Label(4,1,"金额",cellFormat));
        sheet.addCell(new Label(5,0,"第二趟",cellFormat));
        sheet.addCell(new Label(5,1,"人数",cellFormat));
        sheet.addCell(new Label(6,1,"金额",cellFormat));
        sheet.addCell(new Label(7,0,"第三趟",cellFormat));
        sheet.addCell(new Label(7,1,"人数",cellFormat));
        sheet.addCell(new Label(8,1,"金额",cellFormat));
        sheet.addCell(new Label(9,0,"第四趟",cellFormat));
        sheet.addCell(new Label(9,1,"人数",cellFormat));
        sheet.addCell(new Label(10,1,"金额",cellFormat));
        sheet.addCell(new Label(11,0,"第五趟",cellFormat));
        sheet.addCell(new Label(11,1,"人数",cellFormat));
        sheet.addCell(new Label(12,1,"金额",cellFormat));
        sheet.addCell(new Label(13,0,"第六趟",cellFormat));
        sheet.addCell(new Label(13,1,"人数",cellFormat));
        sheet.addCell(new Label(14,1,"金额",cellFormat));
        sheet.addCell(new Label(15,0,"合计",cellFormat));


        for(int i = 0; i < result.size() ; i++){
            LcJlModel lcJlModel = result.get(i);
            sheet.addCell(new Label(0,i+2,i+1+"",cellFormat));
            sheet.addCell(new Label(1,i+2,lcJlModel.getZgXm(),cellFormat));
            sheet.addCell(new Label(2,i+2,lcJlModel.getClBh(),cellFormat));
            for (int j = 0 ; j<lcJlModel.getJls().size();j++) {
                BizLcJl bizLcJl = lcJlModel.getJls().get(j);
                sheet.addCell(new Label(j*2+3,i+2,bizLcJl.getXySl()+"",cellFormat));
                sheet.addCell(new Label(j*2+4,i+2,bizLcJl.getLcFy()+"",cellFormat));
            }
            sheet.addCell(new Label(15,i+2,lcJlModel.getZj()+"",cellFormat));
        }
        sheet.mergeCells(0,result.size()+2,14,result.size()+2 );
        sheet.addCell(new Label(0,result.size()+2,"合计",cellFormat));
        sheet.addCell(new Label(15,result.size()+2,result.size()==0?(0+""):(result.stream().mapToInt(LcJlModel::getZj).sum()+""),cellFormat));
        workbook.write();
        workbook.close();
    }

    @GetMapping("/countBranchExcel")
    public void countBranchSignUp(String[] lx , String startTime , String endTime, String jgdm, String carType , HttpServletRequest request , HttpServletResponse response) throws IOException {
        String fileName = DateUtils.getDateStr(new Date(), "yyyy-MM-dd") + "- 学费报表";

        ApiResponse<List<ChargeManagement>> listApiResponse = staService.countBranch(lx, startTime, endTime, jgdm, carType);
        List<ChargeManagement> result = listApiResponse.getResult();
        Map<String, List<ChargeManagement>> collect = result.stream().collect(Collectors.groupingBy(ChargeManagement::getChargeSource));
        Set<String> keySet = collect.keySet();

        List<List<Map<String,String>>> dataList = new ArrayList<>();
        List<Map<String,Object>> sheetMap = new ArrayList<>();
        if(collect.size() <= 0){
            Map<String,Object> sheet = new HashMap<>();
            sheet.put("name","无记录");
            Map<String,String> tableMap = new HashMap<>();
            tableMap.put("#","序号");
            tableMap.put("xm", "姓名");
            tableMap.put("sfzh", "身份证号");
            tableMap.put("pjbh", "票据编号");
            tableMap.put("srje", "收入金额");
            tableMap.put("srxm", "收入项目");
            tableMap.put("cx", "车型");
            tableMap.put("bmd", "报名点");
            tableMap.put("sj", "时间");
            tableMap.put("cjr","创建人");
            tableMap.put("cjsj", "创建时间");
            sheet.put("sheelMap",tableMap);
            sheetMap.add(sheet);
            List<Map<String,String>> data = new ArrayList<>();
            dataList.add(data);
        }

        for (String s : keySet) {
            List<Map<String,String>> data = new ArrayList<>();
            Map<String,Object> sheet = new HashMap<>();
            sheet.put("name",s.split("/")[0]);
            Map<String,String> tableMap = new HashMap<>();
            tableMap.put("#","序号");
            tableMap.put("xm", "姓名");
            tableMap.put("sfzh", "身份证号");
            tableMap.put("pjbh", "票据编号");
            tableMap.put("srje", "收入金额");
            tableMap.put("srxm", "收入项目");
            tableMap.put("cx", "车型");
            tableMap.put("bmd", "报名点");
            tableMap.put("sj", "时间");
            tableMap.put("cjr","创建人");
            tableMap.put("cjsj", "创建时间");
            sheet.put("sheelMap",tableMap);
            sheetMap.add(sheet);
            List<ChargeManagement> chargeManagements = collect.get(s);

            for (int i = 0 ; i < chargeManagements.size() ; i ++) {
                ChargeManagement management = chargeManagements.get(i);
                Map<String,String> dataMap = new HashMap<>();
                dataMap.put("#", i+1+"");
                dataMap.put("xm",management.getTraineeName());
                dataMap.put("sfzh",management.getIdCardNo());
                dataMap.put("pjbh",management.getPjbh().substring(0, management.getPjbh().length() - 5));
                dataMap.put("srje",management.getChargeFee() +"");
                dataMap.put("srxm",management.getChargeName());
                dataMap.put("cx",management.getCarType());
                dataMap.put("bmd",management.getChargeSource());
                dataMap.put("sj",management.getChargeTime());
                dataMap.put("cjr",management.getCjr());
                dataMap.put("cjsj",management.getCjsj());
                data.add(dataMap);
            }
            dataList.add(data);

        }

        response.setContentType("application/msexcel");
        request.setCharacterEncoding("UTF-8");
        response.setHeader("pragma", "no-cache");
        response.addHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1") + ".xls");
        OutputStream out = response.getOutputStream();
        ExcelUtil.createSheetArrayAndDataArray(out,dataList,sheetMap);

    }
    /**
     * 招生统计 Excel 导出
     */
    @GetMapping("/getAllPaymentExcel")
    public void getAllPaymentExcel(String startTime, String endTime, String jgdm, @RequestParam(required = false) String lx, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ApiResponse<List<StudentAllModel>> allPayment = staService.getAllPayment(startTime, endTime, jgdm, lx);
        List<StudentAllModel> result = allPayment.getResult();
        List<Map<Integer, String>> data = new ArrayList<>();
        String fileName = "";
        if(org.apache.commons.lang3.StringUtils.equals(startTime,endTime)){
            fileName = startTime + "招生统计";
        }else {
            fileName = startTime + "-" + endTime + "招生统计.xls";
        }

        Map<Integer, String> map = new HashMap<>();
        map.put(0, "机构");
        map.put(1,"总人数");
        map.put(2,"A1");
        map.put(3,"A2");
        map.put(4,"A3");
        map.put(5,"B2");
        map.put(6,"C1");
        map.put(7,"C2");
        data.add(map);

        for (StudentAllModel model : result) {
            Map<Integer,String> dataMap = new HashMap<>();
            dataMap.put(0, model.getJgmc());
            dataMap.put(1, model.getAll() +"");
            dataMap.put(2, model.getA1() + "");
            dataMap.put(3, model.getA2() + "");
            dataMap.put(4, model.getA3() + "");
            dataMap.put(5, model.getB2() + "");
            dataMap.put(6, model.getC1() + "");
            dataMap.put(7, model.getC2() + "");
            data.add(dataMap);
        }


        response.setContentType("application/msexcel");
        request.setCharacterEncoding("UTF-8");
        response.setHeader("pragma", "no-cache");
        response.addHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1") + ".xls");
        OutputStream out = response.getOutputStream();
        ExcelUtil.createSheet(out, "招生统计", data);
    }

    /**
     * 学员合格率Excel导出
     */
    @GetMapping("/getPassExcel")
    public void getPassExcel(String jgdm, String startTime, String endTime, String km, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ApiResponse<List<Map<String, String>>> pass = staService.getPass(jgdm, startTime, endTime, km);
        if(org.apache.commons.lang3.StringUtils.isBlank(startTime)){
            startTime = DateUtils.getDateStr(new Date(), "yyyy-MM-dd");
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(endTime)){
            endTime = DateUtils.getDateStr(new Date(), "yyyy-MM-dd");
        }
        String fileName = "";
        if(org.apache.commons.lang3.StringUtils.equals(startTime,endTime)){
            fileName = startTime + "合格率.xls";
        }else {
            fileName = startTime + " - " + endTime + "合格率.xls";
        }
        List<Map<Integer,String>> data = new ArrayList<>();
        Map<Integer, String> map = new HashMap<>();
        map.put(0, "机构");
        map.put(1, "总人数");
        map.put(2, "总合格人数");
        map.put(3, "总合格率");
        map.put(4, "科目一总人数");
        map.put(5, "科目一合格人数");
        map.put(6, "科目一合格率");
        map.put(7, "科目二总人数");
        map.put(8, "科目二合格人数");
        map.put(9, "科目二合格率");
        map.put(10, "科目三总人数");
        map.put(11, "科目三合格人数");
        map.put(12, "科目三合格率");
        map.put(13, "科目四总人数");
        map.put(14, "科目四合格人数");
        map.put(15, "科目四合格率");
        data.add(map);
        if(pass.getCode() == 200){
            List<Map<String, String>> result = pass.getResult();
            for (Map<String, String> m : result) {
                Map<Integer,String> dataMap = new HashMap<>();
                dataMap.put(0, m.get("jgmc"));
                dataMap.put(1, m.get("all"));
                dataMap.put(2, m.get("kmAllPass"));
                dataMap.put(3, m.get("kmAll"));
                dataMap.put(4, m.get("km1all"));
                dataMap.put(5, m.get("km1Pass"));
                dataMap.put(6, m.get("km1"));
                dataMap.put(7, m.get("km2all"));
                dataMap.put(8, m.get("km2Pass"));
                dataMap.put(9, m.get("km2"));
                dataMap.put(10, m.get("km3all"));
                dataMap.put(11, m.get("km3Pass"));
                dataMap.put(12, m.get("km3"));
                dataMap.put(13, m.get("km4all"));
                dataMap.put(14, m.get("km4Pass"));
                dataMap.put(15, m.get("km4"));
                data.add(dataMap);
            }
        }



        response.setContentType("application/msexcel");
        request.setCharacterEncoding("UTF-8");
        response.setHeader("pragma", "no-cache");
        response.addHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1") + ".xls");
        OutputStream out = response.getOutputStream();
        ExcelUtil.createSheet(out, "合格率" , data);
    }

    @GetMapping("/exportBigCarExcel")
    public void exportBigCarExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String,String> m = new HashMap<>();
        m.put("s00","待受理");
        m.put("s10","科目一");
        m.put("s20","科目二");
        m.put("s30", "科目三");
        m.put("s40", "科目四");
        m.put("100","已缴费");
        m.put("110", "已缴费未约考");
        m.put("120", "已约考");
        m.put("130", "不合格");
        m.put("140", "合格");
        m.put("200", "未约考");
        m.put("210", "已约考");
        m.put("220", "约考并缴费");
        m.put("230", "不合格");
        m.put("240", "合格");
        m.put("400", "已约考");
        m.put("410", "不合格");
        m.put("420", "合格");

        SimpleCondition condition = new SimpleCondition(TraineeInformation.class);
        condition.in(TraineeInformation.InnerColumn.carType, "A1","A2","A3","B2");
        condition.in(TraineeInformation.InnerColumn.status,"00","10","20","30","40");
        condition.setOrderByClause(" status asc");
        List<TraineeInformation> list = informationService.findByCondition(condition);
        List<Map<Integer,String>> data = new ArrayList<>();
        Map<Integer,String> map = new HashMap<>();
        map.put(0, "队号");
        map.put(1,"姓名");
        map.put(2, "身份证号");
        map.put(3,"车型");
        map.put(4, "流水号");
        map.put(5, "当前状态");
        map.put(6, "科一");
        map.put(7, "科二");
        map.put(8, "科三");
        map.put(9, "科四");
        data.add(map);
        for (TraineeInformation traineeInformation : list) {
            Map<Integer,String> dataMap = new HashMap<>();
            dataMap.put(0,traineeInformation.getJgmc());
            dataMap.put(1, traineeInformation.getName());
            dataMap.put(2, traineeInformation.getIdCardNo());
            dataMap.put(3, traineeInformation.getCarType());
            dataMap.put(4, traineeInformation.getSerialNum());
            dataMap.put(5, m.get("s"+traineeInformation.getStatus()));
            dataMap.put(6, StringUtils.isBlank(traineeInformation.getFirSub())?"无数据":m.get("1"+traineeInformation.getFirSub()));
            dataMap.put(7, StringUtils.isBlank(traineeInformation.getSecSub())?"无数据":m.get("2"+traineeInformation.getSecSub()));
            dataMap.put(8, StringUtils.isBlank(traineeInformation.getThirdSub())?"无数据":m.get("2"+traineeInformation.getThirdSub()));
            dataMap.put(9, StringUtils.isBlank(traineeInformation.getForthSub())?"无数据":m.get("4"+traineeInformation.getForthSub()));
            data.add(dataMap);
        }

        response.setContentType("application/msexcel");
        request.setCharacterEncoding("UTF-8");
        response.setHeader("pragma", "no-cache");
        response.addHeader("Content-Disposition", "attachment; filename=" + new String("大车".getBytes("utf-8"), "ISO8859-1") + ".xls");
        OutputStream out = response.getOutputStream();
        ExcelUtil.createSheet(out, "大车情况" , data);
    }

    @GetMapping("exportCK")
    public void exportCK(String kcMc, String kcLx, String lqr, HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<BizCk> bizCks = new ArrayList<>();
        List<Map<Integer,String>> data = new ArrayList<>();
        Map<Integer, String> map = new HashMap<>();

        map.put(0, "物品名称");
        map.put(1, "物品规格");
        map.put(2, "领取人");
        map.put(3, "领取部门");
        map.put(4, "领取时间");
        map.put(5, "领取数量");
        map.put(6, "操作人");
        data.add(map);

        SimpleCondition condition = new SimpleCondition(BizCk.class);
        SimpleCondition kcCondition = new SimpleCondition(BizKc.class);
        condition.setOrderByClause(" cjsj desc ");
        if (org.apache.commons.lang3.StringUtils.isNotBlank(kcMc)) {
            kcCondition.like(BizKc.InnerColumn.kcMc, kcMc);
        }
        if (org.apache.commons.lang3.StringUtils.isNotBlank(kcLx)) {
            kcCondition.like(BizKc.InnerColumn.kcLx, kcLx);
        }
        if (org.apache.commons.lang3.StringUtils.isNotBlank(lqr)) {
            condition.like(BizCk.InnerColumn.lqr, lqr);
        }
        if (org.apache.commons.lang3.StringUtils.isNotBlank(kcLx) || org.apache.commons.lang3.StringUtils.isNotBlank(kcMc)) {
            List<BizKc> kcs = kcService.findByCondition(kcCondition);
            if (CollectionUtils.isNotEmpty(kcs)) {
                List<String> kcIds = kcs.stream().map(BizKc::getId).collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(kcIds)){
                    condition.in(BizCk.InnerColumn.kcId, kcIds);
                    bizCks = ckService.findByCondition(condition);
                }
            }
        }else{
            bizCks = ckService.findByCondition(condition);
        }
        if (CollectionUtils.isNotEmpty(bizCks)){

            List<String> kcIds = bizCks.stream().map(BizCk::getKcId).collect(Collectors.toList());
            List<BizKc> kcs = kcService.findByIds(kcIds);
            if (CollectionUtils.isNotEmpty(kcs)) {
                Map<String, List<BizKc>> collect = kcs.stream().collect(Collectors.groupingBy(BizKc::getId));
                bizCks.forEach(bizCk -> {
                    Map<Integer, String> dataMap = new HashMap<>();
                    if (collect.containsKey(bizCk.getKcId())) {
                        bizCk.setKc(collect.get(bizCk.getKcId()).get(0));
                        dataMap.put(0, bizCk.getKc().getKcMc());
                        dataMap.put(1, bizCk.getKc().getKcLx());
                    }
                    dataMap.put(2, bizCk.getLqr().split("-")[0]);
                    dataMap.put(3, bizCk.getJgmc());
                    dataMap.put(4, bizCk.getCjsj().substring(0,16));
                    dataMap.put(5, bizCk.getLqSl()+"");
                    dataMap.put(6, bizCk.getCjr().split("-")[1]);
                    data.add(dataMap);
                });
            }
        }
        response.setContentType("application/msexcel");
        request.setCharacterEncoding("UTF-8");
        response.setHeader("pragma", "no-cache");
        response.addHeader("Content-Disposition", "attachment; filename=" + new String("出库流水".getBytes("utf-8"), "ISO8859-1") + ".xls");
        OutputStream out = response.getOutputStream();
        ExcelUtil.createSheet(out, "出库流水" , data);
    }


}
