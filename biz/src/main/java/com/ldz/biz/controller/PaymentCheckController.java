package com.ldz.biz.controller;

import com.ldz.biz.model.ChargeManagement;
import com.ldz.biz.model.TraineeInformation;
import com.ldz.biz.service.TraineeInformationService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.JsonUtil;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/pub/paycheck")
public class PaymentCheckController {

    @Autowired
    private TraineeInformationService informationService;

    @Autowired
    private StringRedisTemplate redisDao;

    /**
     * 账单查询
     * @param file 队长文件为docx
     * @return 处理结果
     * @throws IOException
     */
    @PostMapping("/check")
    public ApiResponse<Map<String,Object>> paymentCheck(MultipartFile file) throws IOException {
//        String name = file.getName();
        Map<String, Object> map = new HashMap<>();
        InputStream fis = file.getInputStream();

        XWPFDocument xdoc = new XWPFDocument(fis);
        Iterator<XWPFTable> iterator = xdoc.getTablesIterator();

        RuntimeCheck.ifNull(iterator, "对账文件不正确，请检查");

        int[] kmje = new int[]{120, 150, 230};

        List<Map> dataList = new ArrayList<>();

        List<Map<Integer,String>> listData = new ArrayList<>();
        Map<Integer,String> mm = new HashMap<>();
        mm.put(0,"姓名");
        mm.put(1, "身份证号");
        mm.put(2,"报名点");
        mm.put(3,"缴费金额");
        listData.add(mm);

        String key = System.currentTimeMillis()+"";


        int sucess = 0;
        int fail = 0;
        int chuKao = 0;
        int buKao = 0;
        while (iterator.hasNext()) {
            XWPFTable table = iterator.next();
            int numberOfRows = table.getNumberOfRows();
            for (int i = 0; i < numberOfRows; i++) {

                Map<String,String> dataMap = new HashMap<>();
                XWPFTableRow row = table.getRow(i);
                //缴费人身份证号码
                String sfzmhm = row.getCell(4).getText();
                Float jfje = Float.parseFloat(row.getCell(6).getText());
                if(StringUtils.equals(sfzmhm, "42011619900520245X")){
                    System.out.println("a");
                }
                SimpleCondition condition = new SimpleCondition(TraineeInformation.class);
                condition.eq(TraineeInformation.InnerColumn.idCardNo, sfzmhm);
                condition.setOrderByClause(" registration_time desc");
                List<TraineeInformation> list = informationService.findByCondition(condition);
                SimpleCondition chCondition = new SimpleCondition(ChargeManagement.class);
                chCondition.eq(ChargeManagement.InnerColumn.idCardNo,sfzmhm);

                // 新建一个读取word文档 , 直接生成excel  , 不进行匹配 , 将word装换为excel
                Map<Integer,String> daMap = new HashMap<>();
                if(CollectionUtils.isEmpty(list)){
                    dataMap.put("xm","");
                    dataMap.put("zjhm",sfzmhm);
                    dataMap.put("bmd","");
                    dataMap.put("zt","0");
                    dataMap.put("jg", "系统中未找到该学员");
                    dataList.add(dataMap);
                    fail++;

                    daMap.put(0,"");
                    daMap.put(1,sfzmhm);
                    daMap.put(2,"未找到学员信息");
                    daMap.put(3,jfje+"");
                    listData.add(daMap);

                    continue;
                }
                TraineeInformation information = list.get(0);
                dataMap.put("xm",information.getName());
                dataMap.put("zjhm",information.getIdCardNo());
                dataMap.put("bmd",information.getJgmc());
                dataMap.put("zt","1");

                daMap.put(0,information.getName());
                daMap.put(1,information.getIdCardNo());
                daMap.put(2, information.getJgmc());
                daMap.put(3, jfje+ "");
                listData.add(daMap);


                //缴费金额
                /*
                 * 科目一：120
                 * 科目二：150
                 * 科目三：230、232
                 * 如果金额是232表示是科目三初考缴费（2块钱制证费），230表示科目三补考缴费
                 * */

                dataMap.put("je",jfje+"");

                if(StringUtils.equals(information.getStatus(),"50")){
                    dataMap.put("zt","0");
                    dataMap.put("jg", "失败:学员已经结业");
                    dataList.add(dataMap);
                    fail++;
                    continue;
                }
                if(StringUtils.equals(information.getStatus(),"60")){
                    dataMap.put("zt","0");
                    dataMap.put("jg", "失败:学员已经退学");
                    dataList.add(dataMap);
                    fail++;
                    continue;
                }
                if(StringUtils.equals(information.getStatus(), "99")){
                    dataMap.put("zt", "0");
                    dataMap.put("jg", "失败:学员尚未确认缴纳报名费");
                    dataList.add(dataMap);
                    fail++;
                    continue;
                }
                if (jfje.intValue() > 232) {
                    //计算属于科目几缴费
                    int kmdm = 0;
                    for (int t = 0; t < kmje.length; t++) {
                        int tKm = jfje.intValue() % kmje[t];
                        if (tKm == 0) {
                            kmdm = t + 1;
                            break;
                        }
                    }
                    if (kmdm == 0 && (jfje.intValue() - 2) % 230 == 0){
                        kmdm = 3;
                    }
                    dataMap.put("km", kmdm == 1? "科目一":kmdm == 2? "科目二":"科目三");
                    if(kmdm == 1){
                        /*if(!StringUtils.equals(information.getStatus(),"10")){
                            dataMap.put("zt","0");
                            dataMap.put("jg","学员当前科目状态与银行缴费对应科目不一致");
                            dataList.add(dataMap);
                            fail++;
                            continue;
                        }*/
                        chCondition.eq(ChargeManagement.InnerColumn.chargeCode, "9998");
                    }else if(kmdm == 2){
                        /*if(!StringUtils.equals(information.getStatus(),"20")){
                            dataMap.put("zt","0");
                            dataMap.put("jg","学员当前科目状态与银行缴费对应科目不一致");
                            dataList.add(dataMap);
                            fail++;
                            continue;
                        }*/
                        chCondition.eq(ChargeManagement.InnerColumn.chargeCode, "9997");
                    }else if(kmdm == 3){
                        /*if(!StringUtils.equals(information.getStatus(),"30")){
                            dataMap.put("zt","0");
                            dataMap.put("jg","学员当前科目状态与银行缴费对应科目不一致");
                            dataList.add(dataMap);
                            fail++;
                            continue;
                        }*/
                        chCondition.eq(ChargeManagement.InnerColumn.chargeCode, "9996");
                    }else{
                        dataMap.put("zt", "0");
                        dataMap.put("jg", "失败:缴费记录无法判断科目");
                        dataList.add(dataMap);
                        fail++;
                        continue;
                    }
                   /* List<ChargeManagement> managementList = managementService.findByCondition(chCondition);
                    if(CollectionUtils.isEmpty(managementList)){
                        dataMap.put("zt","0");
                        dataMap.put("jg", "系统中未找到缴费记录");
                        dataList.add(dataMap);
                        continue;
                    }*/
                    //缴费金额大于单科最大金额值，说明是同一科初考和补考费用补缴
                    //整除缴费金额，即可知道是哪一个单科费用缴费
                    int tmpJe = jfje.intValue() % 10;
                    if (tmpJe == 2) {
                        //剩余2说明是科目三缴费记录，初考和补考一起缴的
                       /* if(StringUtils.isBlank(information.getThirdSubPaymentTime())){
                            dataMap.put("jg", "此学员未在考试缴费页面进行确认操作");
                            dataMap.put("zt","0");
                            dataList.add(dataMap);
                            continue;
                        }*/
                        dataMap.put("jg", "科三补考费");
                        buKao += jfje;
                    } else {
                        int tKm = jfje.intValue() % 150;
                        if(tKm == 0){
                            // 科目二
                            /*if(StringUtils.isBlank(information.getSecSubPaymentTime())){
                                dataMap.put("jg", "此学员未在考试缴费页面进行确认操作");
                                dataMap.put("zt","0");
                                dataList.add(dataMap);
                                continue;
                            }*/
                            dataMap.put("jg","科目二补考费");
                            buKao += jfje;
                        }
                        tKm = jfje.intValue() % 120;
                        if(tKm == 0){
                            // 科目一
                           /* if(StringUtils.isBlank(information.getFirSubPaymentTime())) {
                                dataMap.put("jg","此学员未在考试缴费页面进行确认操作");
                                dataMap.put("zt", "0");
                                dataList.add(dataMap);
                                continue;
                            }*/
                            dataMap.put("jg", "科目一补考费");
                            buKao+=jfje;
                        }
                        tKm = jfje.intValue() % 230;
                        if(tKm == 0){
                            // 科目三
                            /*if(StringUtils.isBlank(information.getThirdSubPaymentTime())){
                                dataMap.put("jg","此学员未在考试缴费页面进行确认操作");
                                dataMap.put("zt", "0");
                                dataList.add(dataMap);
                                continue;
                            }*/
                            dataMap.put("jg", "科目三补考费");
                            buKao += jfje;
                        }
//                        System.out.println(tKm);
                    }
//                    System.out.println(jfje.intValue() + "===" + tmpJe);
                } else {
                    if (jfje.intValue() == 120) {
                        dataMap.put("km","科目一");
                        //科目一缴费比对
                        if(information.getFirSubTestNum() > 1){

                            dataMap.put("jg", "科目一补考费");
                            buKao += jfje;
                        }else{
                            dataMap.put("jg", "科目一初考费");
                            chuKao += jfje;
                        }
                    } else if (jfje.intValue() == 150) {
                        dataMap.put("km", "科目二");
                        //科目二缴费比对
                        if(information.getSecSubTestNum() > 1){
                            dataMap.put("jg", "科目二补考费");
                            buKao += jfje;
                        }else{
                            chuKao += jfje;
                            dataMap.put("jg", "科目二初考费");
                        }
                    } else if (jfje.intValue() == 230) {
                        dataMap.put("km", "科目三");
                        if(information.getThirdSubTestNum() > 1){
                            dataMap.put("jg", "科目三补考费");
                            buKao += jfje;
                        }else{
                            dataMap.put("jg", "科目三初考费");
                            chuKao += jfje;
                        }
                        //科目三补考缴费比对
                    } else if (jfje.intValue() == 232) {
                        //科目三初考缴费
                        dataMap.put("km", "科目三");
                        dataMap.put("jg", "科目三初考费");
                        chuKao += jfje;

                    }
                }
                sucess++;
                dataList.add(dataMap);
//                System.out.println("行数：" + sfzmhm + "==" + jfje.intValue());
            }
            //System.out.println(doc1);
            fis.close();
        }
        map.put("suc",sucess);
        map.put("fail",fail);
        map.put("data",dataList);
        map.put("chuKao",chuKao);
        map.put("buKao", buKao);
        map.put("zs", listData.size() -1);

        map.put("key",key);
        redisDao.boundValueOps(key).set(JsonUtil.toJson(listData),1, TimeUnit.DAYS);
        return ApiResponse.success(map);
    }







}
