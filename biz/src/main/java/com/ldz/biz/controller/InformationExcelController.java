package com.ldz.biz.controller;

import com.github.pagehelper.Page;
import com.google.common.collect.Maps;
import com.ldz.biz.constant.Status;
import com.ldz.biz.model.TraineeInformation;
import com.ldz.biz.service.TraineeInformationService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.commonUtil.ExcelUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@RestController
@RequestMapping("/api/traineeinformation")
public class InformationExcelController  {

    @Autowired
    private TraineeInformationService informationService;

    /**
     * 导出结果
     *
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/exportResult", method = {RequestMethod.GET})
    public void export(HttpServletRequest request, HttpServletResponse response, Page<TraineeInformation> pager) throws IOException {
//        String userId = request.getParameter("userId");
//        SysYh byId = yhService.findById(userId);
//        request.setAttribute("orgCode",byId.getJgdm());
        Map<String, String> statusMap = new HashMap<>();
        statusMap.put(Status.SIGN_UP, "报名中");
        statusMap.put("00", "受理中");
//        statusMap.put("0", "受理中");
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
        tableName.put("referrer","推荐人");
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
                m.put("referrer",l.getReferrer());
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


}
