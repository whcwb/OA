package com.ldz.biz.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.ldz.biz.constant.FeeType;
import com.ldz.biz.constant.Status;
import com.ldz.biz.mapper.ArchivesRecordMapper;
import com.ldz.biz.mapper.CoachTraineeRercordMapper;
import com.ldz.biz.mapper.TraineeInformationMapper;
import com.ldz.biz.model.*;
import com.ldz.biz.service.*;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.sys.model.SysJg;
import com.ldz.sys.model.SysMessage;
import com.ldz.sys.model.SysYh;
import com.ldz.sys.service.JgService;
import com.ldz.sys.service.SysMessageService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.*;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.support.nativejdbc.OracleJdbc4NativeJdbcExtractor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tk.mybatis.mapper.common.Mapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class TraineeInformationServiceImpl extends BaseServiceImpl<TraineeInformation, String> implements TraineeInformationService {

    @Autowired
    private StringRedisTemplate redisDao;
    @Autowired
    private ZgjbxxService zgjbxxService;
    @Autowired
    private TraineeInformationMapper baseMapper;
    @Autowired
    private JgService jgService;
    @Autowired
    private ZgTjjlService zgTjjlService;
    @Autowired
    private ChargeItemManagementService chargeItemManagementService;
    @Autowired
    private BizExceptionService exceptionService;
    @Autowired
    private ReduceManagementService reduceManagementService;
    @Autowired
    private TraineeTestInfoService traineeTestInfoService;
    @Autowired// 优惠项管理表与机构关联表
    private ReduceInstitutionService reduceInstitutionService;
    @Autowired
    private ChargeManagementService chargeManagementService;
    @Autowired // 学员状态表
    private TraineeStatusService traineeStatusService;

    @Autowired //电子档案表
    private ElecArchivesManageService elecService;
    @Autowired
    private ArchivesRecordMapper archivesRecordMapper;
    @Autowired
    private CoachTraineeRercordMapper coachTraineeRercordMapper;
    //	学员图片地址  学员图片地址 headImg--头像   elecSign--电子签名  cardFront-身份证正面  cardBack-身份证反面
    @Value("${staticPath}")
    private String traineeImgFileUrl;
    @Value("${qqj.time}")
    private String time;
    @Value("${qqj.rs}")
    private Integer rs;
    @Value("${qqj.jg}")
    private Integer qqJg;
    @Autowired
    private SysMessageService messageService;//消息下发
    private ExecutorService threadPool = Executors.newSingleThreadExecutor();

    @Override
    protected Mapper<TraineeInformation> getBaseMapper() {
        return baseMapper;
    }

    /**
     * 分页补充
     *
     * @param condition
     * @return
     */
    @Override
    public boolean fillPagerCondition(LimitedCondition condition) {

        HttpServletRequest request = getRequset();

        String firSubTrainStatus = request.getParameter("firSubTrainStatus");
        String secSubTrainStatus = request.getParameter("secSubTrainStatus");
        String thirdSubTrainStatus = request.getParameter("thirdSubTrainStatus");
        if (StringUtils.equals(firSubTrainStatus, "20")) {
            condition.and().andCondition(" fir_sub_train_status = '' or fir_sub_train_status is null");
        }
        if (StringUtils.equals(secSubTrainStatus, "20")) {
            condition.and().andCondition(" sec_sub_train_status is null or sec_sub_train_status = ''");
        }
        if (StringUtils.equals(thirdSubTrainStatus, "20")) {
            condition.and().andCondition(" third_sub_train_status is null or third_sub_train_status = ''");
        }

        String sign = request.getParameter("sign");
        // 查询需要缴纳报名费的学员信息
        if (StringUtils.equals(sign, "1")) {
            condition.eq(TraineeInformation.InnerColumn.status, Status.SIGN_UP);
            // 收费时间为空
            condition.and().andCondition(" confirm_time is null or confirm_time = ''");
            // 信息审核通过
            condition.eq(TraineeInformation.InnerColumn.infoCheckStatus.name(), "10");
            // 未收费
            condition.eq(TraineeInformation.InnerColumn.chargeStatus, "00");
        } else if (StringUtils.equals(sign, "2")) {
            // 科目二需要交费的学员  ： 只要有科目二的考试时间 ， 且 交费时间是空的 ， 就需要交费
            condition.and().andCondition(" sec_sub_test_time is not null or sec_sub_test_time != ''");
            condition.and().andCondition(" sec_sub_payment_time is null or sec_sub_payment_time = '' ");
            condition.and().andNotEqualTo(TraineeInformation.InnerColumn.classType.name(), "60");

        } else if (StringUtils.equals(sign, "3")) { // 查询科目三需要缴纳考试费的学员
            // 科三同理 ： 只要有 科三的考试时间 并且缴费时间是空 则需要交费
            condition.and().andNotEqualTo(TraineeInformation.InnerColumn.classType.name(), "60");
            condition.and().andCondition("  third_sub_test_time is not null or third_sub_test_time != '' ");
            condition.and().andCondition("third_sub_payment_time is null or third_sub_payment_time = ''"); // 考试费未缴纳
        } else if (StringUtils.equals(sign, "4")) { // 查询今日已缴纳报名费的学员
            condition.like(TraineeInformation.InnerColumn.confirmTime.name(), DateUtils.getDateStr(new Date(), "yyyy-MM-dd"));
            condition.eq(TraineeInformation.InnerColumn.chargeStatus.name(), "10");
        } else if (StringUtils.equals(sign, "7")) { // 查询科目一代缴费学员
            //  科目一 也按照已经预约的来缴费
            condition.and().andCondition(" fir_sub_test_time is not null or fir_sub_test_time != '' ");
            condition.and().andCondition("fir_sub_payment_time is  null or fir_sub_payment_time = '' ");
            condition.and().andNotEqualTo(TraineeInformation.InnerColumn.classType.name(), "60");
//            condition.eq(TraineeInformation.InnerColumn.acceptStatus, "20");
        } else if (StringUtils.equals(sign, "9")) {
            condition.in(TraineeInformation.InnerColumn.status, Arrays.asList("10", "20", "30"));
            condition.and().andCondition(" ( fir_sub_test_num = 0 or sec_sub_test_num = 1 or third_sub_test_num = 1 ) ");
            condition.and().andCondition("  ( (fir_sub is null or fir_sub = '') or sec_sub = '10' or third_sub = '10'  ) ");
            condition.and().andCondition(" ( (fir_sub_payment_time is  null or fir_sub_payment_time = '' ) or (sec_sub_payment_time is null or sec_sub_payment_time = '') or (third_sub_payment_time is null or third_sub_payment_time = '') ) ");
        } else if (StringUtils.equals(sign, "10")) {
            condition.and().andCondition(" fir_sub_payment_time like '%" + DateUtils.getDateStr(new Date(), "yyyy-MM-dd") + "%' or sec_sub_payment_time like '%" + DateUtils.getDateStr(new Date(), "yyyy-MM-dd") + "%' or third_sub_payment_time like '%" + DateUtils.getDateStr(new Date(), "yyyy-MM-dd") + "%'");
        }

//		状态筛选  学员当前状态 99:报名中 00: 受理中  10：科一学习中 20：科二学习中 30：科三学习中 40：科四学习中 50：结业 60：退学
        String statuss = request.getParameter("statusArray");
        if (StringUtils.isNotEmpty(statuss)) {
            List<String> stringList = new ArrayList<>();
            String[] statusArray = statuss.split(",");
            for (String type : statusArray) {
                if (StringUtils.isNotEmpty(type)) {
                    if (StringUtils.equals(type, "0")) {
                        type = "00";
                    }
                    stringList.add(type);
                }
            }
            if (stringList != null && stringList.size() > 0) {
                condition.in(TraineeInformation.InnerColumn.status, stringList);
            }
        }

        String carTypes = request.getParameter("carTypeArray");
        if (StringUtils.isNotBlank(carTypes)) {
            List<String> carTypeList = Arrays.asList(carTypes.split(","));
            condition.in(TraineeInformation.InnerColumn.carType, carTypeList);
        }

//		有效期  365(一年过期)  182(半年过期)  90(3个月过期) 30(1个月过期)
        String termOfValidity = request.getParameter("termOfValidity");
        if (StringUtils.isNotEmpty(termOfValidity)) {
            termOfValidity = StringUtils.trim(termOfValidity);
            if (MathUtil.isNumeric(termOfValidity)) {
                condition.and().andCondition("  DATEDIFF(INDATE_END_TIME,DATE_FORMAT(NOW(),'%Y-%m-%d')) <= " + termOfValidity + " ");
            }
        }

//		车型  1、大车，2、小车
        String carModel = request.getParameter("carModel");
        if (StringUtils.isNotEmpty(carModel)) {
            if (StringUtils.equals(carModel, "2")) {//小車
                condition.startWith(TraineeInformation.InnerColumn.carType, "C%");
            } else if (StringUtils.equals(carModel, "1")) {//大車
                condition.and().andNotLike(TraineeInformation.InnerColumn.carType.name(), "C%");
            }
        }

//		报名费  1已缴清，0未缴清
        String entryFeeType = request.getParameter("entryFeeType");
        if (StringUtils.isNotEmpty(entryFeeType)) {
//			arrearage		是否欠费 /* 00: 不欠费 10： 欠费 */
//			charge_status   收费状态 /* 00:未收费 10：已收费 */

            if (StringUtils.equals(entryFeeType, "1")) {
                condition.eq(TraineeInformation.InnerColumn.arrearage, "00");
                condition.eq(TraineeInformation.InnerColumn.chargeStatus, "10");
            } else if (StringUtils.equals(entryFeeType, "0")) {// 状态为欠费 或者是未收费
                condition.and().andCondition(" ( arrearage = '10' or charge_status='00' ) ");
            }


        }

//		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //从前台获取特殊的组件
        String[] bmTime = request.getParameterValues("bmTime");
        if (bmTime != null && bmTime.length == 2) {
            DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
            //报名时间筛选，使用daterange日期组件，从前台传过来的日期需要加1天，才是Java日期格式
            if (StringUtils.isNotBlank(bmTime[0])) {
                String startTime = bmTime[0].substring(0, 10) + " 00:00:00";
                startTime = DateTime.parse(startTime, dtf).plusDays(1).toString("yyyy-MM-dd HH:mm:ss");
                condition.gte(TraineeInformation.InnerColumn.registrationTime.name(), startTime);
            }
            if (StringUtils.isNotBlank(bmTime[1])) {
                String endTime = bmTime[1].substring(0, 10) + " 23:59:59";
                endTime = DateTime.parse(endTime, dtf).plusDays(1).toString("yyyy-MM-dd HH:mm:ss");
                condition.lte(TraineeInformation.InnerColumn.registrationTime.name(), endTime);
            }
        }
        String[] jgdm = request.getParameterValues("bmd");
        if (jgdm != null && jgdm.length > 0) {
            //报名点，是级联组件，从前台传过来的是一个数组，数组最后一个数据是最终选择的报名点数据
            String tmpJgdm = jgdm[jgdm.length - 1];
            if (StringUtils.isNotBlank(tmpJgdm)) {
                condition.eq(TraineeInformation.InnerColumn.jgdm.name(), jgdm[jgdm.length - 1]);
            }
        }
        //分配科目二教练员
        String secondSubjectCoachFlag = request.getParameter("secondSubjectCoachFlag");
        if ("1".equals(secondSubjectCoachFlag)) {
            condition.and().andIsNull(TraineeInformation.InnerColumn.secondSubjectCoach.name());
        } else if ("2".equals(secondSubjectCoachFlag)) {
            condition.and().andIsNotNull(TraineeInformation.InnerColumn.secondSubjectCoach.name());
        }
        //分配科目三教练员
        String thirdSubjectCoachFlag = request.getParameter("thirdSubjectCoachFlag");
        if ("1".equals(thirdSubjectCoachFlag)) {
            condition.and().andIsNull(TraineeInformation.InnerColumn.thirdSubjectCoach.name());
        } else if ("2".equals(thirdSubjectCoachFlag)) {
            condition.and().andIsNotNull(TraineeInformation.InnerColumn.thirdSubjectCoach.name());
        }
        // 今日还清贷款的学员
        String arr = request.getParameter("arr");
        if (StringUtils.equals(arr, "1")) {
            SimpleCondition simpleCondition = new SimpleCondition(ChargeManagement.class);
            simpleCondition.eq(ChargeManagement.InnerColumn.chargeCode, FeeType.STAGING);
            simpleCondition.like(ChargeManagement.InnerColumn.cjsj, DateUtils.getDateStr(new Date(), "yyyy-MM-dd"));
            List<ChargeManagement> byCondition = chargeManagementService.findByCondition(simpleCondition);
            if (CollectionUtils.isNotEmpty(byCondition)) {
                List<String> collect = byCondition.stream().map(ChargeManagement::getTraineeId).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(collect)) {
                    condition.in(TraineeInformation.InnerColumn.id, collect);
                }
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public void afterPager(PageInfo<TraineeInformation> resultPage) {
        if (CollectionUtils.isEmpty(resultPage.getList())) {
            return;
        }
        List<TraineeInformation> list = resultPage.getList();
        Set<String> trainIds = list.stream().map(TraineeInformation::getId).collect(Collectors.toSet());
        SimpleCondition simpleCondition = new SimpleCondition(TraineeTestInfo.class);
        simpleCondition.in(TraineeTestInfo.InnerColumn.traineeId, trainIds);
        simpleCondition.setOrderByClause(" cjsj asc");
        List<TraineeTestInfo> testInfoList = traineeTestInfoService.findByCondition(simpleCondition);
        Map<String, List<TraineeTestInfo>> map = testInfoList.stream().collect(Collectors.groupingBy(TraineeTestInfo::getTraineeId));
        for (TraineeInformation traineeInformation : list) {
            traineeInformation.setTestInfos(map.get(traineeInformation.getId()));
        }

        HttpServletRequest requset = getRequset();
        String sign = requset.getParameter("sign");
        String arr = requset.getParameter("arr");
        Integer totalFee = 0;
        if (StringUtils.containsAny(sign, "4", "5", "6", "8") || StringUtils.equals(arr, "1")) {
            SimpleCondition condition = new SimpleCondition(ChargeManagement.class);

            condition.like(ChargeManagement.InnerColumn.chargeTime.name(), DateUtils.getDateStr(new Date(), "yyyy-MM-dd"));
            if (StringUtils.equals(sign, "4")) {
                condition.eq(ChargeManagement.InnerColumn.chargeCode.name(), FeeType.SIGN_UP);
            } else if (StringUtils.equals(sign, "5")) {
                condition.eq(ChargeManagement.InnerColumn.chargeCode.name(), FeeType.SEC_SUB);
            } else if (StringUtils.equals(sign, "6")) {
                condition.eq(ChargeManagement.InnerColumn.chargeCode.name(), FeeType.THIRD_SUB);
            } else if (StringUtils.equals(sign, "8")) {
                condition.eq(ChargeManagement.InnerColumn.chargeCode.name(), FeeType.FIR_SUB);
            } else if (StringUtils.equals("9", sign)) {
                condition.in(ChargeManagement.InnerColumn.chargeCode.name(), Arrays.asList(FeeType.THIRD_SUB, FeeType.SEC_SUB, FeeType.FIR_SUB));
            } else if (StringUtils.equals(arr, "1")) {
                condition.eq(ChargeManagement.InnerColumn.chargeCode.name(), FeeType.STAGING);
            }
            List<ChargeManagement> managementList = chargeManagementService.findByCondition(condition);
            if (CollectionUtils.isNotEmpty(managementList)) {
                totalFee = managementList.stream().mapToInt(ChargeManagement::getChargeFee).reduce(Integer::sum).getAsInt();
            }
            condition.in(ChargeManagement.InnerColumn.traineeId.name(), trainIds);
            List<ChargeManagement> chargeManagementList = chargeManagementService.findByCondition(condition);
            Map<String, List<ChargeManagement>> collect = chargeManagementList.stream().collect(Collectors.groupingBy(ChargeManagement::getTraineeId));
            for (TraineeInformation traineeInformation : list) {
                List<ChargeManagement> managements = collect.get(traineeInformation.getId());
                ChargeManagement chargeManagement = null;
                if (CollectionUtils.isNotEmpty(managements)) {
                    chargeManagement = managements.get(0);
//                    totalFee += chargeManagement.getChargeFee();
                }
//                traineeInformation.setTotalFee(totalFee);
                traineeInformation.setTotalFee(totalFee);
                traineeInformation.setChargeRecord(chargeManagement);
            }
        }

        if (StringUtils.containsAny(sign, "2", "3", "5", "6", "7", "8", "9", "10")) {
            SimpleCondition condition = new SimpleCondition(TraineeTestInfo.class);
            condition.in(TraineeTestInfo.InnerColumn.traineeId, trainIds);
            if (StringUtils.containsAny(sign, "2", "5")) {
                condition.eq(TraineeTestInfo.InnerColumn.subject, "科目二");
            } else if (StringUtils.containsAny(sign, "3", "6")) {
                condition.eq(TraineeTestInfo.InnerColumn.subject, "科目三");
            } else if (StringUtils.containsAny(sign, "7", "8")) {
                condition.eq(TraineeTestInfo.InnerColumn.subject, "科目一");
            }
            condition.setOrderByClause(" test_time desc ");
            List<TraineeTestInfo> testInfos = traineeTestInfoService.findByCondition(condition);
            Map<String, List<TraineeTestInfo>> collect = testInfos.stream().collect(Collectors.groupingBy(TraineeTestInfo::getTraineeId));

            for (TraineeInformation traineeInformation : list) {
                testInfos = collect.get(traineeInformation.getId());
                if (CollectionUtils.isNotEmpty(testInfos)) {
                    TraineeTestInfo testInfo = testInfos.get(0);
                    if (DateUtils.getDateStr(new Date(), "yyyy-Mm-dd").compareTo(testInfo.getTestTime()) <= 0) {
                        traineeInformation.setTestInfo(testInfo);
                    }
                }
            }
        }

        if (StringUtils.equals(sign, "4")) {
            Set<String> collect = list.stream().map(TraineeInformation::getJgdm).collect(Collectors.toSet());
            List<SysJg> sysJgs = jgService.findIn(SysJg.InnerColumn.jgdm, collect);
            Map<String, List<SysJg>> listMap = sysJgs.stream().collect(Collectors.groupingBy(SysJg::getJgdm));
            for (TraineeInformation traineeInformation : list) {
                sysJgs = listMap.get(traineeInformation.getJgdm());
                if (CollectionUtils.isNotEmpty(sysJgs)) {
                    SysJg jg = sysJgs.get(0);
                    traineeInformation.setJgPhone(jg.getLxdh1());
                }
            }
        }


//		//将该用户的身份证正面、反面 地址反馈出来 目前客户没有这个需求，所以此代码暂时注掉
//		if(CollectionUtils.isNotEmpty(list)){
//	    	for(TraineeInformation t:list){
//				SimpleCondition elecCondition = new SimpleCondition(ElecArchivesManage.class);
//				elecCondition.eq(ElecArchivesManage.InnerColumn.traineeId,t.getId());
//				elecCondition.setOrderByClause(ElecArchivesManage.InnerColumn.id.desc());
//				List<ElecArchivesManage> elecArchivesManageList=elecService.findByCondition(elecCondition);
//				if(elecArchivesManageList!=null&&elecArchivesManageList.size()>0){
//					for(ElecArchivesManage elec:elecArchivesManageList){
//						//类型  0、学员签名 1、身份证正面  2、身份证反面 3、学员头像
//						if(StringUtils.equals(elec.getType(),"1")){
//							t.setCardFront(elec.getFilePath());
//						}else if(StringUtils.equals(elec.getType(),"2")){
//							t.setCardBack(elec.getFilePath());
//						}
//					}
//				}
//			}
//		}

    }

    @Override
    public ApiResponse<List<TraineeInformation>> baseAuditingPager(Page<TraineeInformation> pager) {
        ApiResponse<List<TraineeInformation>> result = new ApiResponse<>();
        LimitedCondition condition = getQueryCondition();
        if (!fillPagerCondition(condition)) {
            return new ApiResponse<List<TraineeInformation>>().emptyPage();
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //从前台获取特殊的组件
        String[] bmTime = request.getParameterValues("bmTime");
        if (bmTime != null && bmTime.length == 2) {
            DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
            //报名时间筛选，使用daterange日期组件，从前台传过来的日期需要加1天，才是Java日期格式
            if (StringUtils.isNotBlank(bmTime[0])) {
                String startTime = bmTime[0].substring(0, 10) + " 00:00:00";
                startTime = DateTime.parse(startTime, dtf).plusDays(1).toString("yyyy-MM-dd HH:mm:ss");
                condition.gte(TraineeInformation.InnerColumn.registrationTime.name(), startTime);
            }
            if (StringUtils.isNotBlank(bmTime[1])) {
                String endTime = bmTime[1].substring(0, 10) + " 23:59:59";
                endTime = DateTime.parse(endTime, dtf).plusDays(1).toString("yyyy-MM-dd HH:mm:ss");
                condition.lte(TraineeInformation.InnerColumn.registrationTime.name(), endTime);
            }
        }
        condition.notIn(TraineeInformation.InnerColumn.status, Arrays.asList("50","60"));
        String[] jgdm = request.getParameterValues("bmd");
        if (jgdm != null && jgdm.length > 0) {
            //报名点，是级联组件，从前台传过来的是一个数组，数组最后一个数据是最终选择的报名点数据
            String tmpJgdm = jgdm[jgdm.length - 1];
            if (StringUtils.isNotBlank(tmpJgdm)) {
                condition.eq(TraineeInformation.InnerColumn.jgdm.name(), jgdm[jgdm.length - 1]);
            }
        }

        PageInfo<TraineeInformation> resultPage = findPage(pager, condition);
        afterPager(resultPage);
        result.setPage(resultPage);
        return result;
    }


    @Override
    public ApiResponse<List<TraineeInformation>> traineeAcceptancePager(Page<TraineeInformation> pager) {
        ApiResponse<List<TraineeInformation>> result = new ApiResponse<>();
        LimitedCondition condition = getQueryCondition();
        if (!fillPagerCondition(condition)) {
            return new ApiResponse<List<TraineeInformation>>().emptyPage();
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //从前台获取特殊的组件
        String[] bmTime = request.getParameterValues("bmTime");
        if (bmTime != null && bmTime.length == 2) {
            DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
            //报名时间筛选，使用daterange日期组件，从前台传过来的日期需要加1天，才是Java日期格式
            if (StringUtils.isNotBlank(bmTime[0])) {
                String startTime = bmTime[0].substring(0, 10) + " 00:00:00";
                startTime = DateTime.parse(startTime, dtf).plusDays(1).toString("yyyy-MM-dd HH:mm:ss");
                condition.gte(TraineeInformation.InnerColumn.registrationTime.name(), startTime);
            }
            if (StringUtils.isNotBlank(bmTime[1])) {
                String endTime = bmTime[1].substring(0, 10) + " 23:59:59";
                endTime = DateTime.parse(endTime, dtf).plusDays(1).toString("yyyy-MM-dd HH:mm:ss");
                condition.lte(TraineeInformation.InnerColumn.registrationTime.name(), endTime);
            }
        }

        String[] jgdm = request.getParameterValues("bmd");
        if (jgdm != null && jgdm.length > 0) {
            //报名点，是级联组件，从前台传过来的是一个数组，数组最后一个数据是最终选择的报名点数据
            String tmpJgdm = jgdm[jgdm.length - 1];
            if (StringUtils.isNotBlank(tmpJgdm)) {
                condition.eq(TraineeInformation.InnerColumn.jgdm.name(), jgdm[jgdm.length - 1]);
            }
        }
        condition.notIn(TraineeInformation.InnerColumn.status, Arrays.asList(Status.WIND, Status.QUIT));
        condition.setOrderByClause(" registration_time desc");
//        condition.eq(TraineeInformation.InnerColumn.status.name(), "00");//学员当前状态 99:报名中 00: 受理中  10：科一学习中 20：科二学习中 30：科三学习中 40：科四学习中 50：结业 60：退学
//        condition.eq(TraineeInformation.InnerColumn.chargeStatus.name(), "10");//收费状态 00:未收费 10：已收费
//        condition.eq(TraineeInformation.InnerColumn.acceptStatus.name(), "10");//受理状态  00：未受理 10：受理中 20：已受理
        condition.and().andIsNull(TraineeInformation.InnerColumn.serialNum.name());//学员流水号
        condition.eq(TraineeInformation.InnerColumn.infoCheckStatus, "10");

        PageInfo<TraineeInformation> resultPage = findPage(pager, condition);
        if (CollectionUtils.isNotEmpty(resultPage.getList())) {
            afterPager(resultPage);
            result.setPage(resultPage);
            return result;
        } else {
            LimitedCondition condition1 = getQueryCondition();
            condition1.setOrderByClause(" registration_time desc");
            PageInfo<TraineeInformation> page = findPage(pager, condition1);
            if (CollectionUtils.isNotEmpty(page.getList())) {
                result.setPage(page);
                result.setMessage("当前学员已经有流水号");
            } else {
                result.setCode(500);
                result.setPage(new PageInfo());
                result.setMessage("该学员信息不在系统中");
            }
            return result;
        }
    }


    @Override
    public ApiResponse<List<TraineeInformation>> traineeAcceptanceByIdCard(Page<TraineeInformation> pager) {
        HttpServletRequest requset = getRequset();
        String idCardNoLike = requset.getParameter("idCardNoLike");
        RuntimeCheck.ifBlank(idCardNoLike, "用户身份证号不能为空");
        ApiResponse<List<TraineeInformation>> result1 = new ApiResponse<>();
        SimpleCondition simpleCondition = new SimpleCondition(TraineeInformation.class);
        simpleCondition.eq(TraineeInformation.InnerColumn.idCardNo, idCardNoLike);
        PageInfo<TraineeInformation> page = findPage(pager, simpleCondition);
        afterPager(page);
        result1.setPage(page);
        return result1;
    }


    /**
     * 受理确认接口
     *
     * @param entity id 主键
     * @param entity serialNum 受理流水号
     * @return
     */
    @Override
    public ApiResponse<String> updateTraineeAcceptanceAuditing(TraineeInformation entity) {
        SysYh user = getCurrentUser();
        if (StringUtils.isBlank(entity.getId())) {
            return ApiResponse.fail("请选择要确认受理的学员");
        }
        if (StringUtils.isBlank(entity.getSerialNum())) {
            return ApiResponse.fail("受理流水号不能为空");
        }
        TraineeInformation obj = findById(entity.getId());
        if (obj == null) {
            // 受理学员未找到 记录异常信息
            BizException exception = new BizException();
            exception.setLsh(entity.getSerialNum());
            exception.setCjsj(DateUtils.getNowTime());
            exception.setCjr(user.getZh() + " -" + user.getXm());
            exception.setCode("990");
            exception.setId(genId());
            exception.setSfzmhm(entity.getIdCardNo());
            exceptionService.saveException(exception);
            return ApiResponse.success();
        }
        obj.setSerialNum(entity.getSerialNum());
        if (StringUtils.equals(obj.getStatus(), "00")) {
            obj.setStatus("10");
        }
        obj.setAcceptStatus("20");
        obj.setAcceptTime(DateUtils.getNowTime());
        obj.setAcceptor(user.getZh() + "-" + user.getXm());
        int i = update(obj);
        if (i > 0) {
            //  2、受理确认：【明涛驾校】尊敬的某某先生/女士：您的报名受理已成功，您可通过“交管12123”预约科目一考试，考场请选择“新农科目一考场”。如需帮助，请联系报名负责人或致电客服热线：400-133-2133。
            // 插入将约考成功的消息插入消息表
            SysMessage message = new SysMessage();
            message.setTitle("您已约考成功");
            //学员姓名(0)|学习驾驶证明编号(1)|身份证明号码(2)|考试科目(3)|考试车型(4)|预约日期(5)|约考日期(6)|考试场地(7)|考试场次(8)|手机号码(9)|null(10)|
            //		科目一预约成功确认：【明涛驾校】尊敬的某某先生/女士：您预约的    年   月  日的科目一考试已受理成功，考试时间：上午8:30—10:30，下午13:30—15:00，请准时参加考试，预祝您考试顺利。缺考将视为不及格，再次约考需缴纳补考费。巩固练习请选择“服务学员”—“模拟考试”。如需帮助，请联系报名负责人或致电客服热线：400-133-2133。
            //学员{userName}您好，你已经成功约考{yhkm}(考试科目)  {yksj}(约考日期) {ykcx}(考试车型)
            String messageBody = "";
            String userXb = "先生";//性别 /* 00: 女  10: 男*/
            if (!StringUtils.equals(obj.getGender(), "10")) {
                userXb = "女士";
            }
            messageBody = "{\"first\":\"尊敬的" + obj.getName() + userXb + "：\",\"keyword1\":\"受理确认\",\"keyword2\":\"报名受理已成功\",\"remark\":\"您的报名受理已成功，您可通过“交管12123”预约科目一考试，考场请选择“新农科目一考场”。如需帮助，请联系报名负责人或致电客服热线：400-133-2133。\"}";
            message.setParameterBody(messageBody);//参数
            message.setBizId("xy001");//业务ID
            message.setUserId(obj.getId());//接收者USER_ID
            message.setUserName(obj.getName());//接收者USER_ID
            message.setUserRole("1");//1、学员 2、教练 3、管理员
            messageService.sendMessage(message, obj.getOpenId(), obj.getPhone());

            //学员状态表新增
            String status = "00";
            String type = "受理确认";
            traineeStatusService.saveEntity(obj, type, status, "受理确认");
            BizException exception = new BizException();
            exception.setXm(obj.getName());
            exception.setSfzmhm(obj.getIdCardNo());
            exception.setCode("003");
            exception.setLsh(obj.getSerialNum());
            exceptionService.clearException(exception, "003");
            exception.setCode("904");
            exceptionService.clearException(exception, exception.getCode());
            return ApiResponse.success();
        } else {
            return ApiResponse.fail("操作失败请重新尝试");
        }
    }


    /**
     * 学员报名接口
     *
     * @param entity
     * @return
     */
    @Override
    public ApiResponse<String> validAndSave(TraineeInformation entity) {
        SysYh sysUser = getCurrentUser(true);

//		1、参数非空验证
        RuntimeCheck.ifBlank(entity.getName(), "用户姓名不能为空");
        RuntimeCheck.ifBlank(entity.getPhone(), "手机号码不能为空");
        RuntimeCheck.ifFalse(ParameterVerification.isPhoneValid(entity.getPhone()), "请填写正确的手机号");
        RuntimeCheck.ifBlank(entity.getIdCardNo(), "证件号码不能为空");
        entity.setName(entity.getName().trim());
        String repeat = getRequestParamterAsString("repeat");
        TraineeInformation info = null;
        SimpleCondition condition1 = new SimpleCondition(TraineeInformation.class);
        condition1.eq(TraineeInformation.InnerColumn.idCardNo, entity.getIdCardNo());
        List<TraineeInformation> informationList = findByCondition(condition1);
        for (TraineeInformation traineeInformation : informationList) {

            if (!StringUtils.equals(traineeInformation.getStatus(), "50") && !StringUtils.equals(traineeInformation.getStatus(), "60")) {
                if (StringUtils.isBlank(repeat)) {
                    return ApiResponse.fail("该学员当前处于其他业务下，不能报名");
                } else {
                    if (StringUtils.equals(traineeInformation.getStatus(), "99")) {
                        return ApiResponse.fail("该学员还未缴费 , 不能进行重学操作");
                    }
                    info = traineeInformation;
                    break;
                }
            }
        }
        if (info == null && StringUtils.isNotBlank(repeat)) {
            return ApiResponse.fail("该学员当前没有学车 , 请勿选择重学操作");
        }

        // 查看推荐人名额是否已满
        if (StringUtils.isNotBlank(entity.getReferrer()) && StringUtils.contains(entity.getReferrer(), "-")) {

            if (StringUtils.isNotBlank(entity.getReduceCode())) {

                ReduceManagement reduce = reduceManagementService.findById(entity.getReduceCode());
                if (StringUtils.startsWith(reduce.getReduceName(), "亲情价")) {
                    String[] split = entity.getReferrer().split("-");
                    String id = split[1];
                    String today = DateUtils.getToday("yyyy-MM");
                    String[] split1 = today.split("-");
                    Zgjbxx zgjbxx = zgjbxxService.findById(id);
                    if (today.compareTo(split1[0] + "-01") >= 0 && today.compareTo(split1[0] + "-06") <= 0) {
                        Integer count = zgTjjlService.countByTime(split1[0] + "-01-01 00:00:00", split1[0] + "-06-30 23:59:59", zgjbxx.getId());
                        if (zgjbxx.getsRs() == null || zgjbxx.getsRs() == 0) {
                            return ApiResponse.fail("当前推荐人没有推荐名额,请联系管理员");
                        } else if (zgjbxx.getsRs() <= count) {
                            return ApiResponse.fail("当前推荐人上半年名额已满,请联系管理员");
                        }
                    } else {
                        Integer count = zgTjjlService.countByTime(split1[0] + "-07-01 00:00:00", split1[0] + "-12-31 23:59:59", zgjbxx.getId());
                        if (zgjbxx.getxRs() == null || zgjbxx.getxRs() == 0) {
                            return ApiResponse.fail("当前推荐人没有推荐名额,请联系管理员");
                        } else if (zgjbxx.getxRs() <= count) {
                            return ApiResponse.fail("当前推荐人下半年名额已满,请联系管理员");
                        }
                    }
                }
            }
        }

        Map<String, String> ret = null;
        try {
            ret = ParameterVerification.IDCardValidate((entity.getIdCardNo()).toUpperCase());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (ret != null && ret.size() > 0 && StringUtils.equals(ret.get("code"), "200")) {
            entity.setBirDay(ret.get("birDay"));
        }
        RuntimeCheck.ifBlank(entity.getJgdm(), "请选择机构不能为空");
        String jgdm = entity.getJgdm();
        String jgdms = sysUser.getJgdms();
        String jgdm1 = sysUser.getJgdm();
        RuntimeCheck.ifTrue(!StringUtils.contains(jgdms, jgdm) && !StringUtils.startsWith(jgdm, jgdm1), "您不能为新增非本机构的学员");

        //这里还需要检查一下机构代码是否存在，防止WEB页面给假的数值 来做SQL注入。
        SysJg jg = jgService.findById(jgdm);
        RuntimeCheck.ifNull(jg, "机构不能为空");
        entity.setGlyxm(jg.getGlyxm());
        RuntimeCheck.ifBlank(entity.getClassType(), "班型不能为空");
        RuntimeCheck.ifBlank(entity.getCarType(), "车型不能为空");
        RuntimeCheck.ifBlank(entity.getInstallment(), "请确定是否分期");

        entity.setCarType(entity.getCarType().toUpperCase());//车型字段转大写


        if (StringUtils.equals(entity.getInstallment(), "00")) {
//				1、全款用户不存在分期和欠费状态
            RuntimeCheck.ifFalse(entity.getOweAmount() == null || entity.getOweAmount() == 0, "未分期用户，不存在欠费金额");
            entity.setArrearage("00");
        } else if (StringUtils.equals(entity.getInstallment(), "10")) {
//            RuntimeCheck.ifFalse(StringUtils.indexOf(entity.getCarType(), "C") == -1, "分期目前仅支持大车");
            RuntimeCheck.ifFalse(entity.getOweAmount() != null && entity.getOweAmount() > 0, "分期用户，欠费金额不能小于0");
            entity.setArrearage("10");

        } else {
            RuntimeCheck.ifFalse(false, "请填写正确的分期状态");
        }
//			2、检查当前用户是否已经学车如果已经学车就把本次学车的对象返回
        TraineeInformation findUser = this.queryUserStudyType(entity.getIdCardNo(), entity.getCarType());
        if (findUser != null) {
            String findUserStatus = findUser.getStatus();// 50：结业 60：退学
            //
            if (StringUtils.indexOf(",50,60", findUserStatus) < 0 && StringUtils.isBlank(repeat)) {
                RuntimeCheck.ifTrue(true, "该学员车型：" + entity.getCarType() + "正在学习，无需申请");
            }
        }
//			3、获取当前用户报名的费用
        ChargeItemManagement chargeItemManagement = null;
        HttpServletRequest requset = getRequset();
        String qqj = requset.getParameter("qqj");
        if (StringUtils.isNotBlank(qqj)) {
            RuntimeCheck.ifBlank(entity.getReferrer(), "推荐人不能为空");
            chargeItemManagement = new ChargeItemManagement();
            chargeItemManagement.setAmount(qqJg);

        } else {
//			班型、费用类型(0000 学费 )、报名点(机构)、报名车型
            chargeItemManagement = chargeItemManagementService.getUserCharge(entity.getClassType(), FeeType.SIGN_UP, entity.getJgdm(), entity.getCarType());

        }

        if (entity.getRealPay() > 0) {

            if (StringUtils.equals(jg.getJglx(), "10") || StringUtils.isNotBlank(repeat)) {

                chargeItemManagement = new ChargeItemManagement();
                chargeItemManagement.setAmount(entity.getRealPay());
            } else {
                return ApiResponse.fail("当前报名点不是直属队，不支持手输金额，请联系管理员");
            }
        }


        if (chargeItemManagement != null) {
            entity.setRegistrationFee(chargeItemManagement.getAmount());
            entity.setRegistrationTime(DateUtils.getNowTime());
        } else {
//            RuntimeCheck.ifTrue(true,"该报名点下未找到收费项，请联系管理员对该机构增加收费项 费用类型：学费、班型:"+entity.getClassType()+"、车型:"+entity.getCarType()+"收费项");
            RuntimeCheck.ifTrue(true, "该报名点下未找到收费项，请联系管理员对该机构增加收费项");
        }
//			4、通过优惠代码检查优惠是否存在
        String reduceCode = entity.getReduceCode();//优惠编码
        if (StringUtils.isNotEmpty(reduceCode)) {
            SimpleCondition condition = new SimpleCondition(ReduceInstitution.class);
            condition.startWith(ReduceInstitution.InnerColumn.jgdm.name(), entity.getJgdm());
            condition.eq(ReduceInstitution.InnerColumn.reduceId.name(), reduceCode);
            Integer coun = reduceInstitutionService.countByCondition(condition);
            RuntimeCheck.ifTrue(coun == null || coun == 0, "您好,该优惠卷不属于本机构，无法设置优惠！");

            ReduceManagement reduceManagement = reduceManagementService.findById(reduceCode);
            RuntimeCheck.ifTrue(reduceManagement == null, "您好,该优惠卷不存在，请核实");
            String reduceStartTime = reduceManagement.getReduceStartTime();
            String reduceEndTime = reduceManagement.getReduceEndTime();

            RuntimeCheck.ifTrue(reduceEndTime.compareTo(DateUtils.getDateStr(new Date(), "yyyy-MM-dd")) < 0 && reduceStartTime.compareTo(DateUtils.getDateStr(new Date(), "yyyy-MM-dd")) < 0, "您好,该优惠卷已过期，请核实");
            entity.setReduceStatus("00");//优惠信息审核状态
            entity.setReduceName(reduceManagement.getReduceName());//获取优惠项名称
            entity.setReducePrice(reduceManagement.getReduceAmount());//获取优惠金额
        } else {
            entity.setReduceCode(null);
            entity.setReduceName(null);
            entity.setReducePrice(null);
        }
//        学员当前状态 99:报名中 00: 受理中  10：科一学习中 20：科二学习中 30：科三学习中 40：科四学习中 50：结业 60：退学

        entity.setStatus(Status.SIGN_UP);

        if (entity.getArrearage().equals("10")) {
            entity.setRealPay(entity.getRegistrationFee() - entity.getOweAmount());
        } else {
            entity.setRealPay(entity.getRegistrationFee());
        }
        entity.setInfoCheckStatus("00");
        entity.setChargeStatus("00");//收费状态

        entity.setReduceCheckTime(null);//优惠信息审核时间
        entity.setInfoCheckTime(null);
        entity.setAcceptStatus("00");
        entity.setAcceptTime(null);

        entity.setFirSub(null);
        entity.setFirSubPaymentTime(null);
        entity.setFirSubTestTime(null);
        entity.setFirSubTestNum(0);
        entity.setFirSubTrainStatus(null);

        entity.setSecSub(null);
        entity.setSecSubPaymentTime(null);
        entity.setSecSubTestTime(null);
        entity.setSecSubTestNum(0);
        entity.setSecSubTrainStatus(null);


        entity.setThirdSub(null);
        entity.setThirdSubPaymentTime(null);
        entity.setThirdSubTestTime(null);
        entity.setThirdSubTestNum(0);
        entity.setThirdSubTrainStatus(null);

        entity.setForthSub(null);
        entity.setRecordLib(null);
        if (StringUtils.isNotEmpty(entity.getAddress())) {
            if (StringUtils.indexOf(entity.getAddress(), "武汉") > -1) {
                entity.setSource("00");//本地
            } else {
                entity.setSource("10");//外地

            }
        }

        entity.setId(genId());

        //电子档案
        List<ElecArchivesManage> elecList = new ArrayList<>();
//		将base64图片信息存放到磁盘中   学员图片地址 headImg--头像   elecSign--电子签名  cardFront-身份证正面  cardBack-身份证反面
        if (!traineeImgFileUrl.endsWith("/")) {
            traineeImgFileUrl += "/";
        }
//		将头像保存到磁盘中
        String traineeHeadBase64Date = entity.getHeadImg();
        if (StringUtils.isNotEmpty(traineeHeadBase64Date)) {

            String fileUrl = "head_img/" + (UUID.randomUUID().toString()).replaceAll("-", "") + ".jpg";
            FileUtil.fileExistsDir(traineeImgFileUrl + "head_img/");//创建目录
            Boolean type = Base64TestUtil.generateImage(traineeHeadBase64Date, traineeImgFileUrl + fileUrl);
            if (type) {
                entity.setHeadImg("/" + fileUrl);
                ElecArchivesManage elec = new ElecArchivesManage();
                elec.setId(genId());
                elec.setType("3");
                elec.setTraineeId(entity.getId());
                elec.setFilePath("/" + fileUrl);
                elec.setUploadTime(DateUtils.getNowTime());
                elec.setCjr(sysUser.getZh() + "-" + sysUser.getXm());
                elec.setCjsj(DateUtils.getNowTime());
                elecList.add(elec);
            } else {
                entity.setHeadImg(null);
            }
        }
//		电子签名保存到磁盘中
        String elecSignBase64Date = entity.getElecSign();
        if (StringUtils.isNotEmpty(elecSignBase64Date)) {
            String fileUrl = "elec_sign/" + (UUID.randomUUID().toString()).replaceAll("-", "") + ".jpg";
            FileUtil.fileExistsDir(traineeImgFileUrl + "elec_sign/");//创建目录
            Boolean type = Base64TestUtil.generateImage(elecSignBase64Date, traineeImgFileUrl + fileUrl);
            if (type) {
                entity.setElecSign("/" + fileUrl);

                ElecArchivesManage elec = new ElecArchivesManage();
                elec.setId(genId());
                elec.setType("0");
                elec.setTraineeId(entity.getId());
                elec.setFilePath("/" + fileUrl);
                elec.setUploadTime(DateUtils.getNowTime());
                elec.setCjr(sysUser.getZh() + "-" + sysUser.getXm());
                elec.setCjsj(DateUtils.getNowTime());
                elecList.add(elec);

            } else {
                entity.setElecSign(null);
            }


        }
//		身份证正面
        String cardFrontBase64Date = entity.getCardFront();
        if (StringUtils.isNotEmpty(cardFrontBase64Date)) {
            String fileUrl = "card_front/" + (UUID.randomUUID().toString()).replaceAll("-", "") + ".jpg";
            FileUtil.fileExistsDir(traineeImgFileUrl + "card_front/");//创建目录
            Boolean type = Base64TestUtil.generateImage(cardFrontBase64Date, traineeImgFileUrl + fileUrl);
            if (type) {
                ElecArchivesManage elec = new ElecArchivesManage();
                elec.setId(genId());
                elec.setType("1");
                elec.setTraineeId(entity.getId());
                elec.setFilePath("/" + fileUrl);
                elec.setUploadTime(DateUtils.getNowTime());
                elec.setCjr(sysUser.getZh() + "-" + sysUser.getXm());
                elec.setCjsj(DateUtils.getNowTime());
                elecList.add(elec);
            }
        }

//		身份证反面
        String cardBackBase64Date = entity.getCardBack();
        if (StringUtils.isNotEmpty(cardBackBase64Date)) {
            String fileUrl = "card_back/" + (UUID.randomUUID().toString()).replaceAll("-", "") + ".jpg";
            FileUtil.fileExistsDir(traineeImgFileUrl + "card_back/");//创建目录
            Boolean type = Base64TestUtil.generateImage(cardBackBase64Date, traineeImgFileUrl + fileUrl);
            if (type) {
                ElecArchivesManage elec = new ElecArchivesManage();
                elec.setId(genId());
                elec.setType("2");
                elec.setTraineeId(entity.getId());
                elec.setFilePath("/" + fileUrl);
                elec.setUploadTime(DateUtils.getNowTime());
                elec.setCjr(sysUser.getZh() + "-" + sysUser.getXm());
                elec.setCjsj(DateUtils.getNowTime());
                elecList.add(elec);
            }
        }


        entity.setModifier(sysUser.getZh() + "-" + sysUser.getXm());
        entity.setModifyTime(DateUtils.getNowTime());
        entity.setCjr(sysUser.getZh() + "-" + sysUser.getXm());
        entity.setCjsj(DateUtils.getNowTime());

        entity.setSecondSubjectCoach(null);
        entity.setThirdSubjectCoach(null);

        if (StringUtils.equals(entity.getClassType(), "60")) {
            entity.setFirSubPaymentTime(entity.getRegistrationTime());
            entity.setSecSubPaymentTime(entity.getRegistrationTime());
            entity.setThirdSubPaymentTime(entity.getRegistrationTime());
        }

        int i = baseMapper.insertSelective(entity);
        if (info != null && StringUtils.isNotBlank(repeat)) {
            String status = info.getStatus();
            info.setStatus("70");
            update(info);
            traineeStatusService.saveEntity(info, "学员重新报名 未修改前状态 ," + status, "00", "重新报名");

        }
        if (i > 0) {
            if (StringUtils.isNotBlank(entity.getReferrer()) && StringUtils.contains(entity.getReferrer(), "-")) {

                if (StringUtils.isNotBlank(entity.getReduceCode())) {

                    ReduceManagement reduce = reduceManagementService.findById(entity.getReduceCode());
                    if (StringUtils.startsWith(reduce.getReduceName(), "亲情价")) {
                        String[] split = entity.getReferrer().split("-");
                        String id = split[1];
                        Zgjbxx zgjbxx = zgjbxxService.findById(id);
                        ZgTjjl zgTjjl = new ZgTjjl();
                        zgTjjl.setCjr(sysUser.getZh() + "-" + sysUser.getXm());
                        zgTjjl.setCjsj(DateUtils.getNowTime());
                        zgTjjl.setId(genId());
                        zgTjjl.setZgId(zgjbxx.getId());
                        zgTjjl.setTraineeId(entity.getId());
                        zgTjjlService.save(zgTjjl);
                    }
                }

            }

            //学员状态表新增
            String status = "00";
            String type = "学员新增";
            traineeStatusService.saveEntity(entity, type, status, "学员新增");
            if (elecList != null && elecList.size() > 0) {
                elecService.insertList(elecList);
            }


            return ApiResponse.success();
        } else {
            return ApiResponse.fail("操作失败请重新尝试");
        }
    }

    /**
     * 检查当前用户是否已经学车如果已经学车就把本次学车的对象返回
     *
     * @param idCardNo 学员证件号
     * @param carType  车型
     * @return
     */
    @Override
    public TraineeInformation queryUserStudyType(String idCardNo, String carType) {
        SimpleCondition condition = new SimpleCondition(TraineeInformation.class);
        condition.eq(TraineeInformation.InnerColumn.idCardNo, idCardNo);
        if (StringUtils.isNotEmpty(carType)) {
            condition.eq(TraineeInformation.InnerColumn.carType, carType);
        }
        // condition.and().andNotEqualTo(TraineeInformation.InnerColumn.status.name(), "60");
        // condition.and().andNotEqualTo(TraineeInformation.InnerColumn.status.name(), "50");
        condition.setOrderByClause(TraineeInformation.InnerColumn.id.desc());
        List<TraineeInformation> orgs = findByCondition(condition);
        if (orgs != null && orgs.size() > 0) {
            return orgs.get(0);
        } else {
            return null;
        }


    }

    @Override
    public ApiResponse<String> userInfoCheck(TraineeInformation entity) {
        TraineeInformation newEntity = new TraineeInformation();

        if ((!StringUtils.equals(entity.getInfoCheckStatus(), "10")) && (!StringUtils.equals(entity.getInfoCheckStatus(), "20"))) {
            return ApiResponse.fail("操作失败，请填写正确的审核状态");
        }
        TraineeInformation find = findById(entity.getId());
        RuntimeCheck.ifNull(find, "未找到该学员信息，请核实");
        RuntimeCheck.ifFalse(StringUtils.equals(find.getInfoCheckStatus(), "00"), "该学员已经审核过，无需审核");
        newEntity.setId(find.getId());
        newEntity.setInfoCheckStatus("10");
        newEntity.setInfoCheckTime(DateUtils.getNowTime());
        SysYh currentUser = getCurrentUser();
        newEntity.setModifier(currentUser.getZh() + "-" + currentUser.getXm());
        newEntity.setModifyTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        newEntity.setInfoChecker(currentUser.getZh() + "-" + currentUser.getXm());
        int i = update(newEntity);

        if (i > 0) {
            //学员状态表新增
            String status = "00";//10：审核通过 20：审核未通过
            if (StringUtils.equals(newEntity.getInfoCheckStatus(), "20")) {
                status = "10";
            }
            String type = "学员基础资料审核";
            traineeStatusService.saveEntity(find, type, status, "学员基础资料审核");
        }
        // 用户基础资料审核业务异常清理
        BizException exception = new BizException();
        exception.setXm(find.getName());
        exception.setSfzmhm(find.getIdCardNo());
        exception.setCode("001");
        exceptionService.clearException(exception, "001");
        return i > 0 ? ApiResponse.success() : ApiResponse.fail("操作失败，請重新尝试");
    }

    @Override
    public ApiResponse<String> confirmSignUp(String traineeId, String chargeType, String remark) {

        SysYh currentUser = getCurrentUser();
        RuntimeCheck.ifBlank(traineeId, "学员id不能为空");
        String s = redisDao.boundValueOps("SignUp_" + traineeId).get();
        if (StringUtils.isNotBlank(s)) {
            return ApiResponse.fail("请勿重复操作");
        }
        redisDao.boundValueOps("SignUp_" + traineeId).set("1", 2, TimeUnit.SECONDS);
        TraineeInformation information = findById(traineeId);
        // 确定学员基本信息审核是否通过
        RuntimeCheck.ifTrue(StringUtils.equals(information.getInfoCheckStatus(), "00"), "该学员的基本信息还未审核");
        RuntimeCheck.ifTrue(StringUtils.equals(information.getInfoCheckStatus(), "20"), "该学员的基本信息未通过审核");
        RuntimeCheck.ifBlank(chargeType, "收款方式不能为空");
        SimpleCondition condition = new SimpleCondition(ChargeManagement.class);
        condition.eq(ChargeManagement.InnerColumn.chargeCode, FeeType.SIGN_UP);
        condition.eq(ChargeManagement.InnerColumn.traineeId, information.getId());
        List<ChargeManagement> managements = chargeManagementService.findByCondition(condition);
        if (CollectionUtils.isNotEmpty(managements)) {
            return ApiResponse.fail("当前学员已经确认收取报名费，请勿重复操作");
        }


        information.setChargeStatus("10"); //收费确认
        information.setConfirmTime(DateUtils.getNowTime());
        if (StringUtils.equals("99", information.getStatus()) || StringUtils.isBlank(information.getSerialNum())) {
            information.setStatus("00");  // 收费完成，进入受理状态
        }
        if(StringUtils.equals(information.getAcceptStatus(), "00") || StringUtils.isBlank(information.getAcceptStatus())){
            information.setAcceptStatus("10"); // 受理状态进入受理中
        }
        information.setConfirmer(currentUser.getZh() + "-" + currentUser.getXm());
//        information.setRemark(remark);
        update(information);

        String chargeName = chargeItemManagementService.findChargeName(information.getJgdm(), "0000-" + information.getClassType(), information.getCarType());
        ChargeManagement chargeManagement = new ChargeManagement(); // 收款记录
        chargeManagement.setChargeCode(FeeType.SIGN_UP); // 收费项代码
        chargeManagement.setChargeFee(information.getRealPay()); // 收款金额
        chargeManagement.setChargeSource(information.getJgmc()); // 报名点
        chargeManagement.setChargeName(chargeName); // 收费名称
        chargeManagement.setChargeTime(information.getConfirmTime()); // 收款时间
        chargeManagement.setChargeType(chargeType); // 收款方式
        chargeManagement.setIdCardNo(information.getIdCardNo()); // 身份证号
        chargeManagement.setInOutType("00"); // 收支类型  00:收入 10：支出
        chargeManagement.setReceiver(currentUser.getZh() + "-" + currentUser.getXm()); // 收款人
        chargeManagement.setTraineeId(information.getId()); // 学员id
        chargeManagement.setTraineeName(information.getName()); // 学员姓名
        chargeManagement.setTraineeSource("00"); // 学员来源
        chargeManagement.setId(genId());
        chargeManagement.setCjsj(information.getConfirmTime());
        chargeManagement.setCjr(currentUser.getZh() + "-" + currentUser.getXm());
        chargeManagement.setRemark(remark);
        chargeManagementService.save(chargeManagement);
        traineeStatusService.saveEntity(information, "报名收费确认", "00", "报名收费确认");

        //		6、插入将约考成功的消息插入消息表
        SysMessage message = new SysMessage();
        message.setTitle("报名缴费");
        String messageBody = "";
        String userXb = "先生";//性别 /* 00: 女  10: 男*/
        if (!StringUtils.equals(information.getGender(), "10")) {
            userXb = "女士";
        }
//        1、报名缴费：【明涛驾校】尊敬的某某先生/女士：您的报名、缴费手续已完成，资料正在审核，受理中，请耐心等待！如需帮助，请致电客服热线：400-133-2133。
        messageBody = "{\"first\":\"尊敬的" + information.getName() + userXb + "：\",\"keyword1\":\"报名缴费成功\",\"keyword2\":\"报名缴费成功\",\"remark\":\"您的报名、缴费手续已完成，资料正在审核，受理中，请耐心等待！如需帮助，请致电客服热线：400-133-2133\"}";
        message.setParameterBody(messageBody);//参数
        message.setBizId("xy001");//业务ID
        message.setUserId(information.getId());//接收者USER_ID
        message.setUserName(information.getName());//接收者USER_ID
        message.setUserRole("1");//1、学员 2、教练 3、管理员
        messageService.sendMessage(message, information.getOpenId(), information.getPhone());

        BizException exception = new BizException();
        exception.setCode("002");
        exception.setSfzmhm(information.getIdCardNo());
        exception.setXm(information.getName());
        exceptionService.clearException(exception, exception.getCode());
        exception.setCode("902");
        exceptionService.clearException(exception, exception.getCode());

        return ApiResponse.success();

    }

    /**
     * 考试费用缴费确认
     *
     * @param traineeId
     * @return
     */
    @Override
    public ApiResponse<String> confirmTest(String traineeId, String remark) {
        SysYh currentUser = getCurrentUser();

        TraineeInformation information = findById(traineeId);

        RuntimeCheck.ifNull(information, "该学员信息不存在");

        // 首先确认学员的约考信息
        if (StringUtils.equals(information.getStatus(), "20") || StringUtils.equals(information.getStatus(), "30") || StringUtils.equals(information.getStatus(), "10")) {
            String chargeCode = "";
            ChargeManagement management = new ChargeManagement(); // 收费记录
            // 获取学员当前状态在科目二 还是科目三
            if (StringUtils.equals(information.getStatus(), "20")) {
                chargeCode = FeeType.SEC_SUB;
                if (!StringUtils.contains(information.getCarType(), "C")) {
                    if (StringUtils.equals(information.getArrearage(), "10")) {
                        return ApiResponse.fail("学员学费未付清");
                    }
                    if (StringUtils.equals(information.getSecSubTrainStatus(), "10")) {
                        return ApiResponse.fail("学员科目二培训未合格");
                    }
                }

                // 判断科目二是否已约考 ， 考试次数 ， 是否已缴费
                /*if (information.getSecSubTestNum() > 1) {
                    return ApiResponse.fail("该学员已经参加过考试");
                }
                if (!StringUtils.equals(information.getSecSub(), "10")) {
                    return ApiResponse.fail("该学员还未约考，或已缴费");
                }*/
                if (StringUtils.isNotBlank(information.getSecSubPaymentTime())) {
                    return ApiResponse.fail("该学员已于" + information.getSecSubPaymentTime() + "交过考试费");
                }
                information.setSecSub("20"); // 已缴费
                information.setSecSubPaymentTime(DateUtils.getNowTime()); // 缴费时间
                information.setSecSubTrainStatus("00"); // 科目二培训合格
                management.setChargeCode(chargeCode);
                management.setChargeTime(information.getSecSubPaymentTime());
            } else if (StringUtils.equals(information.getStatus(), "30")) { // 科目三
                chargeCode = FeeType.THIRD_SUB;
                if (!StringUtils.contains(information.getCarType(), "C")) {
                    if (StringUtils.equals(information.getThirdSubTrainStatus(), "10")) {
                        return ApiResponse.fail("学员科目三培训未合格");
                    }
                }
                // 判断科目二是否已约考 ， 考试次数 ， 是否已缴费
               /* if (information.getThirdSubTestNum() > 1) {
                    return ApiResponse.fail("该学员已经参加过考试");
                }*/
                /*if (!StringUtils.equals(information.getThirdSub(), "10")) {
                    return ApiResponse.fail("该学员还未约考，或已缴费");
                }*/
                if (StringUtils.isNotBlank(information.getThirdSubPaymentTime())) {
                    return ApiResponse.fail("该学员已于" + information.getThirdSubPaymentTime() + "交过考试费");
                }

                information.setThirdSub("20"); // 已缴费
                information.setThirdSubPaymentTime(DateUtils.getNowTime()); // 缴费时间
                information.setThirdSubTrainStatus("00"); // 科目三培训合格
                management.setChargeTime(information.getThirdSubPaymentTime()); // 缴费时间
                management.setChargeCode(chargeCode);
            } else if (StringUtils.equals(information.getStatus(), "10")) {
                chargeCode = FeeType.FIR_SUB;
                if (!StringUtils.equals(information.getAcceptStatus(), "20")) {
                    return ApiResponse.fail("学员未受理成功");
                }
                information.setFirSub("00");
                information.setFirSubPaymentTime(DateUtils.getNowTime());
                management.setChargeTime(information.getFirSubPaymentTime());
                management.setChargeCode(chargeCode);
            }
            information.setRemark(remark);
            information.setModifier(currentUser.getXm());
            information.setModifyTime(DateUtils.getNowTime());
            update(information);
            SimpleCondition condition1 = new SimpleCondition(ChargeItemManagement.class);
            condition1.eq(ChargeItemManagement.InnerColumn.chargeCode, chargeCode);
            List<ChargeItemManagement> managements = chargeItemManagementService.findByCondition(condition1);// 收费项
            if (CollectionUtils.isNotEmpty(managements)) {
                ChargeItemManagement itemManagement = managements.get(0);
                management.setChargeName(itemManagement.getChargeName()); // 缴费项名称
                management.setChargeFee(itemManagement.getAmount());

            }
            management.setTraineeSource("00"); // 本校
            management.setTraineeName(information.getName()); // 学员姓名
            management.setTraineeId(information.getId()); // 学员id
            management.setReceiver(currentUser.getZh() + "-" + currentUser.getXm()); // 缴费人
            management.setInOutType("10"); // 支出
            management.setIdCardNo(information.getIdCardNo());
            management.setChargeType("10"); // 在线支付
            management.setChargeSource(information.getJgmc());  // 报名点
            management.setChargeCode(chargeCode);
            management.setId(genId());
            management.setCjsj(DateUtils.getNowTime());
            management.setCjr(currentUser.getZh() + "-" + currentUser.getXm());
            chargeManagementService.save(management);
            SimpleCondition condition = new SimpleCondition(TraineeTestInfo.class);
            condition.eq(TraineeTestInfo.InnerColumn.traineeId, traineeId);
            condition.eq(TraineeTestInfo.InnerColumn.payStatus, "10");
            List<TraineeTestInfo> testInfos = traineeTestInfoService.findByCondition(condition);
            testInfos.forEach(traineeTestInfo -> {
                traineeTestInfo.setPayStatus("00");
                traineeTestInfoService.update(traineeTestInfo);
            });
            if (StringUtils.equals(information.getStatus(), "10")) {
                traineeStatusService.saveEntity(information, "科目一考试缴费确认", "00", "科目一考试缴费确认");
            } else if (StringUtils.equals(information.getStatus(), "20")) {
                traineeStatusService.saveEntity(information, "科目二考试缴费确认", "00", "科目二考试缴费确认");
            } else if (StringUtils.equals(information.getStatus(), "30")) {
                traineeStatusService.saveEntity(information, "科目三考试缴费确认", "00", "科目三考试缴费确认");
            }
        } else {
            return ApiResponse.fail("学员当前状态不需要缴考试费用");
        }
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<String> confirmTestTo(String traineeId, String remark, String km) {
        // 查询是否已经缴费
        RuntimeCheck.ifBlank(traineeId, "请选择缴费学员");
        RuntimeCheck.ifBlank(km, "请选择缴费科目");
        SimpleCondition chacondition = new SimpleCondition(ChargeManagement.class);
        chacondition.eq(ChargeManagement.InnerColumn.traineeId, traineeId);
        if (StringUtils.equals(km, "10")) {
            chacondition.eq(ChargeManagement.InnerColumn.chargeCode, FeeType.FIR_SUB);
        } else if (StringUtils.equals(km, "20")) {
            chacondition.eq(ChargeManagement.InnerColumn.chargeCode, FeeType.SEC_SUB);
        } else if (StringUtils.equals(km, "30")) {
            chacondition.eq(ChargeManagement.InnerColumn.chargeCode, FeeType.THIRD_SUB);
        } else {
            return ApiResponse.success();
        }
        List<ChargeManagement> list = chargeManagementService.findByCondition(chacondition);
        if (CollectionUtils.isNotEmpty(list)) {
            return ApiResponse.success();
        }

        SysYh currentUser = getCurrentUser();
        TraineeInformation information = findById(traineeId);
        String chargeCode = "";
        ChargeManagement management = new ChargeManagement();
        if (StringUtils.equals(km, "10")) {
            // 科目一缴费
            chargeCode = FeeType.FIR_SUB;
            // 判断学员科目一的状态是否为  已经考试或者已经 约考 ， 如果没有则将科目一的状态改成已缴费
            if (!StringUtils.equals(information.getFirSub(), "30") && !StringUtils.equals(information.getFirSub(), "40") && StringUtils.equals(information.getFirSub(), "20")) {
                information.setFirSub("00");
            }
            information.setFirSubPaymentTime(DateUtils.getNowTime());
            management.setChargeTime(information.getFirSubPaymentTime());
            management.setChargeCode(chargeCode);
        } else if (StringUtils.equals(km, "20")) {
            // 科目二缴费
            chargeCode = FeeType.SEC_SUB;

            if (StringUtils.isNotBlank(information.getThirdSubPaymentTime())) {
                return ApiResponse.fail("该学员已于" + information.getThirdSubPaymentTime() + "交过考试费");
            }
            if (!StringUtils.equals(information.getSecSub(), "30") && !StringUtils.equals(information.getSecSub(), "40")) {
                information.setSecSub("20"); // 已缴费
            }
            information.setSecSubPaymentTime(DateUtils.getNowTime()); // 缴费时间
            management.setChargeCode(chargeCode);
            management.setChargeTime(information.getSecSubPaymentTime());
        } else if (StringUtils.equals(km, "30")) {
            chargeCode = FeeType.THIRD_SUB;
            // 科目三缴费时 需要先交科目二的费用 ， 判断科目二是否已经缴纳费用
            RuntimeCheck.ifTrue(StringUtils.isBlank(information.getSecSubPaymentTime()), "请先缴纳科目二的考试费用");
            if (StringUtils.isNotBlank(information.getThirdSubPaymentTime())) {
                return ApiResponse.fail("该学员已于" + information.getThirdSubPaymentTime() + "交过考试费");
            }
            if (!StringUtils.equals(information.getThirdSub(), "30") && !StringUtils.equals(information.getThirdSub(), "40")) {
                information.setThirdSub("20"); // 已缴费
            }
            information.setThirdSubPaymentTime(DateUtils.getNowTime()); // 缴费时间
            management.setChargeTime(information.getThirdSubPaymentTime()); // 缴费时间
        }

        information.setModifier(currentUser.getXm());
        information.setModifyTime(DateUtils.getNowTime());
        update(information);
        SimpleCondition condition1 = new SimpleCondition(ChargeItemManagement.class);
        condition1.eq(ChargeItemManagement.InnerColumn.chargeCode, chargeCode);
        List<ChargeItemManagement> managements = chargeItemManagementService.findByCondition(condition1);// 收费项
        if (CollectionUtils.isNotEmpty(managements)) {
            ChargeItemManagement itemManagement = managements.get(0);
            management.setChargeName(itemManagement.getChargeName()); // 缴费项名称
            management.setChargeFee(itemManagement.getAmount());
        }
        management.setTraineeSource("00"); // 本校
        management.setTraineeName(information.getName()); // 学员姓名
        management.setTraineeId(information.getId()); // 学员id
        management.setReceiver(currentUser.getZh() + "-" + currentUser.getXm()); // 缴费人
        management.setInOutType("10"); // 支出
        management.setIdCardNo(information.getIdCardNo());
        management.setChargeType("10"); // 在线支付
        management.setChargeSource(information.getJgmc());  // 报名点
        management.setChargeCode(chargeCode);
        management.setId(genId());
        management.setCjsj(DateUtils.getNowTime());
        management.setRemark(remark);
        management.setCjr(currentUser.getZh() + "-" + currentUser.getXm());
        chargeManagementService.save(management);
        SimpleCondition condition = new SimpleCondition(TraineeTestInfo.class);
        condition.eq(TraineeTestInfo.InnerColumn.traineeId, traineeId);
        condition.eq(TraineeTestInfo.InnerColumn.payStatus, "10");
        List<TraineeTestInfo> testInfos = traineeTestInfoService.findByCondition(condition);
        testInfos.forEach(traineeTestInfo -> {
            traineeTestInfo.setPayStatus("00");
            traineeTestInfoService.update(traineeTestInfo);
        });
        if (StringUtils.equals(km, "10")) {
            // 科目一缴费异常处理
            BizException exception = new BizException();
            exception.setXm(information.getName());
            exception.setKskm("1");
            exception.setSfzmhm(information.getIdCardNo());
            exception.setCode("101");
            exception.setLsh(information.getSerialNum());
            exceptionService.clearException(exception, exception.getCode());
            exception.setCode("103");
            exceptionService.clearException(exception, exception.getCode());
            traineeStatusService.saveEntity(information, "科目一考试缴费确认", "00", "科目一考试缴费确认");
        } else if (StringUtils.equals(km, "20")) {
            BizException exception = new BizException();
            exception.setXm(information.getName());
            exception.setKskm("2");
            exception.setSfzmhm(information.getIdCardNo());
            exception.setCode("201");
            exception.setLsh(information.getSerialNum());
            exceptionService.clearException(exception, exception.getCode());
            exception.setCode("203");
            exceptionService.clearException(exception, exception.getCode());
            traineeStatusService.saveEntity(information, "科目二考试缴费确认", "00", "科目二考试缴费确认");
        } else if (StringUtils.equals(km, "30")) {
            BizException exception = new BizException();
            exception.setXm(information.getName());
            exception.setKskm("3");
            exception.setSfzmhm(information.getIdCardNo());
            exception.setCode("301");
            exception.setLsh(information.getSerialNum());
            exceptionService.clearException(exception, exception.getCode());
            exception.setCode("303");
            exceptionService.clearException(exception, exception.getCode());
            traineeStatusService.saveEntity(information, "科目三考试缴费确认", "00", "科目三考试缴费确认");
        }

        return ApiResponse.success();
    }

    @Override
    public ApiResponse<String> revokeSignUp(String traineeId) {
        TraineeInformation information = findById(traineeId);
        if (StringUtils.equals(information.getAcceptStatus(), "20")) {
            return ApiResponse.fail("当前学员已经受理成功，不能撤回");
        }
        if (StringUtils.isNotBlank(information.getConfirmTime()) && information.getConfirmTime().compareTo(DateUtils.getDateStr(new Date(), "yyyy-MM-dd")) < 0) {
            return ApiResponse.fail("不是今天的缴费记录，不能撤回");
        }
        // 删掉收费记录
        ChargeManagement chargeManagement = new ChargeManagement();
        chargeManagement.setTraineeId(information.getId());
        chargeManagement.setChargeTime(information.getConfirmTime());
        chargeManagement.setChargeCode(FeeType.SIGN_UP);
        chargeManagementService.remove(chargeManagement);
        information.setConfirmTime(null);
        information.setChargeStatus("00");
        information.setStatus(Status.SIGN_UP);
        information.setAcceptStatus("00");
        baseMapper.updateByPrimaryKey(information);
        traineeStatusService.saveEntity(information, "报名收费撤回", "00", "报名收费撤回");
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<String> revokeTest(String traineeId, String chargeId) {

        TraineeInformation information = findById(traineeId);
        if (ObjectUtils.isEmpty(information)) {
            return ApiResponse.fail("未找到该学员的信息");
        }

        ChargeManagement management = chargeManagementService.findById(chargeId);
        ChargeManagement chargeManagement = new ChargeManagement();
        if (StringUtils.equals(management.getChargeCode(), FeeType.FIR_SUB)) {
            // 科目一缴费撤回
            if (StringUtils.equals(information.getFirSub(), "30") || StringUtils.equals(information.getFirSub(), "40")) {
                // 科一已考试 , 不能撤回
                return ApiResponse.fail("该学员已经完成科目学习,不能撤回");
            }
            chargeManagement.setChargeCode(FeeType.FIR_SUB);
            chargeManagement.setChargeTime(information.getFirSubPaymentTime());
            chargeManagement.setTraineeId(information.getId());
            chargeManagementService.remove(chargeManagement);
            information.setFirSubPaymentTime(null);
            information.setFirSub(null);
            baseMapper.updateByPrimaryKey(information);
            traineeStatusService.saveEntity(information, "科目一缴费撤回", "00", "科目一缴费撤回");
        } else if (StringUtils.equals(management.getChargeCode(), FeeType.SEC_SUB)) {
            // 科目二缴费撤回
            if (StringUtils.equals(information.getSecSub(), "30") || StringUtils.equals(information.getSecSub(), "40")) {
                // 科二已考试 , 不能撤回
                return ApiResponse.fail("该学员已经完成科目学习,不能撤回");
            }
            //
            chargeManagement.setChargeCode(FeeType.SEC_SUB);
            chargeManagement.setChargeTime(information.getSecSubPaymentTime());
            chargeManagement.setTraineeId(information.getId());
            chargeManagementService.remove(chargeManagement);
            information.setSecSubPaymentTime(null);
            information.setSecSub("10");
            baseMapper.updateByPrimaryKey(information);
            traineeStatusService.saveEntity(information, "科目二缴费撤回", "00", "科目二缴费撤回");
        } else if (StringUtils.equals(management.getChargeCode(), FeeType.THIRD_SUB)) {
            // 科目三缴费撤回
            if (StringUtils.equals(information.getThirdSub(), "30") || StringUtils.equals(information.getThirdSub(), "40")) {
                // 科三已考试 , 不能撤回
                return ApiResponse.fail("该学员已经完成科目学习,不能撤回");
            }
            //
            chargeManagement.setChargeCode(FeeType.THIRD_SUB);
            chargeManagement.setChargeTime(information.getThirdSubPaymentTime());
            chargeManagement.setTraineeId(information.getId());
            chargeManagementService.remove(chargeManagement);
            information.setThirdSubPaymentTime(null);
            information.setThirdSub("10");
            baseMapper.updateByPrimaryKey(information);
            traineeStatusService.saveEntity(information, "科目二缴费撤回", "00", "科目二缴费撤回");
        }

        // 判断当前学员是否处于 20 ， 30 状态
        /*if (StringUtils.equals("20", information.getStatus())) {
            if (!StringUtils.equals(management.getChargeCode(), FeeType.SEC_SUB)) {
                return ApiResponse.fail("该学员已完成科目学习，不能撤回");
            }
            chargeManagement.setChargeCode(FeeType.SEC_SUB);
            chargeManagement.setChargeTime(information.getSecSubPaymentTime());
            chargeManagement.setTraineeId(information.getId());
            chargeManagementService.remove(chargeManagement);
            information.setSecSubPaymentTime(null);
            if (!StringUtils.containsAny(information.getSecSub(), "30", "40")) {
                information.setSecSub("10");
            } else {
                return ApiResponse.fail("该学员已经完成考试，不能撤回");
            }
            baseMapper.updateByPrimaryKey(information);
            traineeStatusService.saveEntity(information, "科目二缴费撤回", "00", "科目二缴费撤回");
        } else if (StringUtils.equals("30", information.getStatus())) {
            if (!StringUtils.equals(management.getChargeCode(), FeeType.THIRD_SUB)) {
                return ApiResponse.fail("该学员已完成科目学习，不能撤回");
            }
            chargeManagement.setChargeCode(FeeType.THIRD_SUB);
            chargeManagement.setChargeTime(information.getThirdSubPaymentTime());
            chargeManagement.setTraineeId(information.getId());
            chargeManagementService.remove(chargeManagement);
            information.setThirdSubPaymentTime(null);
            if (!StringUtils.containsAny(information.getThirdSub(), "30", "40")) {
                information.setThirdSub("10");
            } else {
                return ApiResponse.fail("该学员已经完成考试，不能撤回");
            }
            baseMapper.updateByPrimaryKey(information);
            traineeStatusService.saveEntity(information, "科目三缴费撤回", "00", "科目三缴费撤回");
        } else if (StringUtils.equals("10", information.getStatus())) {
            if (!StringUtils.equals(management.getChargeCode(), FeeType.FIR_SUB)) {
                return ApiResponse.fail("该学员已完成科目学习，不能撤回");
            }
            chargeManagement.setChargeCode(FeeType.FIR_SUB);
            chargeManagement.setChargeTime(information.getFirSubPaymentTime());
            chargeManagement.setTraineeId(information.getId());
            chargeManagementService.remove(chargeManagement);
            information.setFirSubPaymentTime(null);
            if (!StringUtils.containsAny(information.getFirSub(), "30", "40")) {
                information.setFirSub(null);
            } else {
                return ApiResponse.fail("该学员已经完成考试，不能撤回");
            }

            baseMapper.updateByPrimaryKey(information);
            traineeStatusService.saveEntity(information, "科目一缴费撤回", "00", "科目一缴费撤回");
        } else {
            return ApiResponse.fail("未找到该学员的考试缴费信息");
        }*/

        return ApiResponse.success();
    }


    /**
     * 科目培训状态修改
     *
     * @param id       主鍵ID
     * @param type     培训状态  00 成功 10 失败
     * @param subjects 科目 1、科目一 2、科目二 3、科目三
     * @return
     */
    @Override
    public ApiResponse<String> updateTrainType(String id, String type, String subjects) {
        RuntimeCheck.ifBlank(id, "请选择学员ID");
        RuntimeCheck.ifBlank(type, "培训状态不能为空");
        RuntimeCheck.ifBlank(subjects, "培训科目不能为空");
        RuntimeCheck.ifFalse(StringUtils.equals(type, "00") || StringUtils.equals(type, "10"), "培训状态不存在，请核实该状态");
        RuntimeCheck.ifTrue(StringUtils.equals(subjects, "4"), "科目四没有培训状态");
        RuntimeCheck.ifFalse(StringUtils.equals(subjects, "1") || StringUtils.equals(subjects, "2") || StringUtils.equals(subjects, "3") || StringUtils.equals(subjects, "4"), "科目不存在，请核实该状态");

        TraineeInformation obj = findById(id);
        RuntimeCheck.ifNull(obj, "该学员不存在，请核实");
        RuntimeCheck.ifTrue(StringUtils.equals(obj.getStatus(), "50"), "该学员已结业,无需修改状态");
        RuntimeCheck.ifTrue(StringUtils.equals(obj.getStatus(), "60"), "该学员退学,无需修改状态");
        if (StringUtils.equals(subjects, "1")) {
            obj.setFirSubTrainStatus(type);
            BizException exception = new BizException();
            exception.setCode("121");
            exception.setLsh(obj.getSerialNum());
            exception.setSfzmhm(obj.getIdCardNo());
            exception.setKskm("1");
            exception.setXm(obj.getName());
            exceptionService.clearException(exception, exception.getCode());
        } else if (StringUtils.equals(subjects, "2")) {
            obj.setSecSubTrainStatus(type);
            BizException exception = new BizException();
            exception.setCode("221");
            exception.setLsh(obj.getSerialNum());
            exception.setSfzmhm(obj.getIdCardNo());
            exception.setKskm("2");
            exception.setXm(obj.getName());
            exceptionService.clearException(exception, exception.getCode());
        } else if (StringUtils.equals(subjects, "3")) {
            obj.setThirdSubTrainStatus(type);
            BizException exception = new BizException();
            exception.setCode("321");
            exception.setLsh(obj.getSerialNum());
            exception.setSfzmhm(obj.getIdCardNo());
            exception.setKskm("3");
            exception.setXm(obj.getName());
            exceptionService.clearException(exception, exception.getCode());
        }
        update(obj);
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<Integer> countReg(String startTime, String endTime) {
        if (StringUtils.isBlank(startTime)) {
            startTime = DateUtils.getDateStr(new Date(), "yyyy-MM-dd");
        }
        if (StringUtils.isBlank(endTime)) {
            endTime = DateUtils.getNextTime();
        }
        SimpleCondition simpleCondition = new SimpleCondition(TraineeInformation.class);
        simpleCondition.lte(TraineeInformation.InnerColumn.registrationTime, endTime);
        simpleCondition.gte(TraineeInformation.InnerColumn.registrationTime, startTime);
        Integer integer = countByCondition(simpleCondition);
        return ApiResponse.success(integer);
    }

    @Override
    public ApiResponse<Integer> countPay(String sub, String testTime) {
        RuntimeCheck.ifBlank(sub, "科目代码不能为空");
        if (StringUtils.isBlank(testTime)) {
            testTime = DateUtils.getDateStr(new Date(), "yyyy-MM-dd");
        }

        SimpleCondition condition = new SimpleCondition(TraineeInformation.class);
        if (StringUtils.equals(sub, "10")) { // 科目一
            condition.like(TraineeInformation.InnerColumn.firSubTestTime, testTime);
        } else if (StringUtils.equals(sub, "20")) {
            //  科目二
            condition.like(TraineeInformation.InnerColumn.secSubTestTime, testTime);
        } else if (StringUtils.equals(sub, "30")) {
            // 科目三
            condition.like(TraineeInformation.InnerColumn.thirdSubTestTime, testTime);
        } else {
            ApiResponse<Integer> api = new ApiResponse<>(0);
            api.setCode(500);
            api.setMessage("不存在该科目");
            return api;
        }
        Integer integer = countByCondition(condition);
        return ApiResponse.success(integer);
    }

    @Override
    public ApiResponse<Map<String, Integer>> countStu(String startTime, String endTime) {
        if (StringUtils.isBlank(startTime)) {
            startTime = DateUtils.getDateStr(new Date(), "yyyy-MM-dd");
        } else {
            startTime = startTime + " 00:00:00";
        }
        if (StringUtils.isBlank(endTime)) {
            endTime = DateUtils.getNextTime();
        } else {
            endTime = endTime + " 23:59:59";
        }
        Map<String, Integer> map = new HashMap<>();


        SimpleCondition condition2 = new SimpleCondition(TraineeInformation.class);
        condition2.like(TraineeInformation.InnerColumn.carType, "C");
        condition2.eq(TraineeInformation.InnerColumn.status, "10");
        condition2.eq(TraineeInformation.InnerColumn.acceptStatus, "20");
        condition2.lte(TraineeInformation.InnerColumn.acceptTime, endTime);
        condition2.gte(TraineeInformation.InnerColumn.acceptTime, startTime);
        Integer accpeted = countByCondition(condition2);
        map.put("小车已受理学员", accpeted);

        SimpleCondition condition = new SimpleCondition(TraineeInformation.class);
        condition.and().andCondition(" car_type like '%A%' or car_type like '%B%'");
        condition.eq(TraineeInformation.InnerColumn.status, "10");
        condition.eq(TraineeInformation.InnerColumn.acceptStatus, "20");
        condition.lte(TraineeInformation.InnerColumn.acceptTime, endTime);
        condition.gte(TraineeInformation.InnerColumn.acceptTime, startTime);
        Integer bigAccepted = countByCondition(condition);
        map.put("大车已受理学员", bigAccepted);

        SimpleCondition condition3 = new SimpleCondition(TraineeInformation.class);
        condition3.eq(TraineeInformation.InnerColumn.status, "20");
        condition3.eq(TraineeInformation.InnerColumn.firSub, "40");
        condition3.lte(TraineeInformation.InnerColumn.firSubTestTime, endTime);
        condition3.gte(TraineeInformation.InnerColumn.firSubTestTime, startTime);
        Integer firSuc = countByCondition(condition3);
        map.put("科目一合格学员", firSuc);

        SimpleCondition condition6 = new SimpleCondition(TraineeInformation.class);
        condition6.lte(TraineeInformation.InnerColumn.registrationTime, endTime);
        condition6.gte(TraineeInformation.InnerColumn.registrationTime, startTime);
        condition6.like(TraineeInformation.InnerColumn.carType, "C");
        Integer c = countByCondition(condition6);
        map.put("小车报名人数", c);

        SimpleCondition condition7 = new SimpleCondition(TraineeInformation.class);
        condition7.and().andCondition(" car_type like '%A%' or car_type like '%B%'");
        condition7.lte(TraineeInformation.InnerColumn.registrationTime, endTime);
        condition7.gte(TraineeInformation.InnerColumn.registrationTime, startTime);
        Integer ab = countByCondition(condition7);
        map.put("大车报名人数", ab);

        SimpleCondition condition8 = new SimpleCondition(TraineeInformation.class);
        condition8.eq(TraineeInformation.InnerColumn.status, "30");
        condition8.eq(TraineeInformation.InnerColumn.secSub, "40");
        condition8.lte(TraineeInformation.InnerColumn.secSubTestTime, endTime);
        condition8.gte(TraineeInformation.InnerColumn.secSubTestTime, startTime);
        Integer sec = countByCondition(condition8);
        map.put("科目二合格人数", sec);

        SimpleCondition condition10 = new SimpleCondition(TraineeInformation.class);
        condition10.eq(TraineeInformation.InnerColumn.status, "40");
        condition10.eq(TraineeInformation.InnerColumn.thirdSub, "40");
        condition10.lte(TraineeInformation.InnerColumn.thirdSubTestTime, endTime);
        condition10.gte(TraineeInformation.InnerColumn.thirdSubTestTime, startTime);
        Integer third = countByCondition(condition10);
        map.put("科目三合格人数", third);

        return ApiResponse.success(map);
    }

    @Override
    public ApiResponse<List<EChart>> countTest(String startTime, String endTime) {


        if (StringUtils.isBlank(startTime)) {
            startTime = DateUtils.getDateStr(new Date(), "yyyy-MM-dd");
        } else {
            startTime = startTime + " 00:00:00";
        }
        if (StringUtils.isBlank(endTime)) {
            endTime = DateUtils.getNextTime();
        } else {
            endTime = endTime + " 23:59:59";
        }
        List<EChart> eCharts = new ArrayList<>();

        SimpleCondition condition = new SimpleCondition(TraineeInformation.class);
        condition.eq(TraineeInformation.InnerColumn.status, Status.SIGN_UP);
        Integer paied = countByCondition(condition);
        EChart eChart = new EChart("待缴费学员", paied);
        eCharts.add(eChart);


        SimpleCondition condition1 = new SimpleCondition(TraineeInformation.class);
        condition1.eq(TraineeInformation.InnerColumn.status, "00");
        condition1.and().andCondition(" accept_status != '20'");
        Integer accepting = countByCondition(condition1);
        EChart eChart1 = new EChart("待受理学员", accepting);
        eCharts.add(eChart1);

        SimpleCondition condition5 = new SimpleCondition(TraineeInformation.class);
        condition5.eq(TraineeInformation.InnerColumn.status, "00");
        condition5.lte(TraineeInformation.InnerColumn.confirmTime.name(), DateUtils.getNextTime());
        condition5.gte(TraineeInformation.InnerColumn.confirmTime.name(), DateUtils.getDateStr(new Date(), "yyyy-MM-dd"));
        Integer regi = countByCondition(condition5);
        EChart eChart2 = new EChart("今日缴纳报名费学员", regi);
        eCharts.add(eChart2);

        SimpleCondition simpleCondition = new SimpleCondition(TraineeInformation.class);
        simpleCondition.eq(TraineeInformation.InnerColumn.status, "10");
        simpleCondition.like(TraineeInformation.InnerColumn.carType, "C");
        simpleCondition.like(TraineeInformation.InnerColumn.acceptTime, DateUtils.getDateStr(new Date(), "yyyy-MM-dd"));
        Integer integer = countByCondition(simpleCondition);
        EChart eChart3 = new EChart("今日小车受理人数", integer);
        eCharts.add(eChart3);

        SimpleCondition simpleCondition1 = new SimpleCondition(TraineeInformation.class);
        simpleCondition1.eq(TraineeInformation.InnerColumn.status, "10");
        simpleCondition1.and().andCondition(" car_type like '%A%' or car_type like '%B%'");
        simpleCondition1.like(TraineeInformation.InnerColumn.acceptTime, DateUtils.getDateStr(new Date(), "yyyy-MM-dd"));
        Integer count = countByCondition(simpleCondition1);
        EChart eChart4 = new EChart("今日大车受理人数", count);
        eCharts.add(eChart4);

        SimpleCondition condition4 = new SimpleCondition(TraineeInformation.class);
        condition4.lte(TraineeInformation.InnerColumn.firSubTestTime, DateUtils.getNextTime());
        condition4.gte(TraineeInformation.InnerColumn.firSubTestTime, DateUtils.getDateStr(new Date(), "yyyy-MM-dd"));
        Integer firTest = countByCondition(condition4);
        EChart eChart5 = new EChart("今日科一约考学员", firTest);
        eCharts.add(eChart5);

        SimpleCondition condition9 = new SimpleCondition(TraineeInformation.class);
        condition9.lte(TraineeInformation.InnerColumn.secSubTestTime, DateUtils.getDateStr(new Date(), "yyyy-MM-dd"));
        condition9.gte(TraineeInformation.InnerColumn.secSubTestTime, DateUtils.getNextTime());
        Integer secTest = countByCondition(condition9);
        EChart eChart6 = new EChart("今日科二约考学员", secTest);
        eCharts.add(eChart6);

        SimpleCondition condition11 = new SimpleCondition(TraineeInformation.class);
        condition11.gte(TraineeInformation.InnerColumn.thirdSubTestTime, DateUtils.getDateStr(new Date(), "yyyy-MM-dd"));
        condition11.lte(TraineeInformation.InnerColumn.thirdSubTestTime, DateUtils.getNextTime());
        Integer thirdTest = countByCondition(condition11);
        EChart eChart7 = new EChart("今日科三约考人数", thirdTest);
        eCharts.add(eChart7);

        SimpleCondition condition12 = new SimpleCondition(TraineeInformation.class);
        condition12.eq(TraineeInformation.InnerColumn.status, "50");
        Integer end = countByCondition(condition12);
        EChart eChart8 = new EChart("已结业人数", end);
        eCharts.add(eChart8);

        return ApiResponse.success(eCharts);
    }

    @Override
    public ApiResponse<TraineeInformation> checkInspect(String name, String idCardNo, String checkTerm) {
        ApiResponse<TraineeInformation> response = new ApiResponse<>();
        RuntimeCheck.ifBlank(idCardNo, "证件号码不能为空");
        SimpleCondition simpleCondition = new SimpleCondition(TraineeInformation.class);
        simpleCondition.eq(TraineeInformation.InnerColumn.idCardNo, idCardNo);
        simpleCondition.notIn(TraineeInformation.InnerColumn.status, Arrays.asList("99", "50", "60"));
        List<TraineeInformation> informationList = findByCondition(simpleCondition);
        if (CollectionUtils.isNotEmpty(informationList)) {
            response.setMessage("该学员已缴费");
            TraineeInformation information = informationList.get(0);
            ChargeManagement management = new ChargeManagement();
            management.setChargeTime(information.getRegistrationTime());
            management.setChargeCode(FeeType.INSPECT);
            management.setZt("00");
            management.setTraineeId(information.getId());
            List<ChargeManagement> list = new ArrayList<>();
            list.add(management);
            information.setManagements(list);
            response.setResult(information);
            return response;
        } else {
            SimpleCondition simpleCondition1 = new SimpleCondition(ChargeManagement.class);
            simpleCondition1.eq(ChargeManagement.InnerColumn.idCardNo, idCardNo);
            simpleCondition1.eq(ChargeManagement.InnerColumn.chargeCode, FeeType.INSPECT);
            simpleCondition1.setOrderByClause(" cjsj desc");
            List<ChargeManagement> managementList = chargeManagementService.findByCondition(simpleCondition1);
            if (CollectionUtils.isNotEmpty(managementList)) {
                ChargeManagement chargeManagement = managementList.get(0);
                response.setMessage("该学员已缴费");
                TraineeInformation traineeInformation = new TraineeInformation();
                traineeInformation.setName(chargeManagement.getTraineeName());
                traineeInformation.setJgmc(chargeManagement.getChargeSource());
                traineeInformation.setIdCardNo(chargeManagement.getIdCardNo());
                traineeInformation.setRegistrationTime(chargeManagement.getChargeTime());
                traineeInformation.setManagements(managementList);
                response.setResult(traineeInformation);
                return response;
            } else {
                response.setCode(500);
                response.setMessage("没有该学员的缴费信息");
                return response;
            }
        }

    }

    @Override
    public ApiResponse<Map<String, Integer>> countNewStu(String startTime, String endTime) {
        LocalDate localDate = LocalDate.now();
        if (StringUtils.isBlank(startTime)) {
            LocalDate date = localDate.dayOfMonth().withMinimumValue();
            startTime = date.toString();
        }
        if (StringUtils.isBlank(endTime)) {
            endTime = localDate.plusMonths(1).dayOfMonth().withMinimumValue().toString();
        }

        Map<String, Integer> map = new HashMap<>();
        SimpleCondition simpleCondition = new SimpleCondition(TraineeInformation.class);
        simpleCondition.and().andCondition(" jgdm like '100001%'");
        simpleCondition.like(TraineeInformation.InnerColumn.carType, "C");
        simpleCondition.lte(TraineeInformation.InnerColumn.registrationTime, endTime);
        simpleCondition.gte(TraineeInformation.InnerColumn.registrationTime, startTime);
        Integer count = countByCondition(simpleCondition);
        SysJg sysJg = jgService.findByOrgCode("100001");
        map.put(sysJg.getJgmc() + "小车报名人数", count);

        SimpleCondition condition = new SimpleCondition(TraineeInformation.class);
        condition.and().andCondition(" jgdm like '100001%'");
        condition.and().andCondition(" car_type like '%A%' or car_type like '%B%'");
        condition.lte(TraineeInformation.InnerColumn.registrationTime, endTime);
        condition.gte(TraineeInformation.InnerColumn.registrationTime, startTime);
        Integer count1 = countByCondition(condition);
        map.put(sysJg.getJgmc() + "大车报名人数", count1);

        SimpleCondition simpleCondition1 = new SimpleCondition(TraineeInformation.class);
        simpleCondition1.and().andCondition(" jgdm like '100002%'");
        simpleCondition1.like(TraineeInformation.InnerColumn.carType, "C");
        simpleCondition1.lte(TraineeInformation.InnerColumn.registrationTime, endTime);
        simpleCondition1.gte(TraineeInformation.InnerColumn.registrationTime, startTime);
        Integer count2 = countByCondition(simpleCondition1);
        SysJg sysJg1 = jgService.findByOrgCode("100002");
        map.put(sysJg1.getJgmc() + "小车报名数", count2);

        SimpleCondition condition1 = new SimpleCondition(TraineeInformation.class);
        condition1.and().andCondition(" jgdm like '100002%'");
        condition1.and().andCondition(" car_type like '%A%' or car_type like '%B%'");
        condition1.lte(TraineeInformation.InnerColumn.registrationTime, endTime);
        condition1.gte(TraineeInformation.InnerColumn.registrationTime, startTime);
        Integer count3 = countByCondition(condition1);
        map.put(sysJg1.getJgmc() + "大车报名数", count3);

        SimpleCondition simpleCondition2 = new SimpleCondition(TraineeInformation.class);
        simpleCondition2.and().andCondition(" jgdm like '100003%'");
        simpleCondition2.like(TraineeInformation.InnerColumn.carType, "C");
        simpleCondition2.lte(TraineeInformation.InnerColumn.registrationTime, endTime);
        simpleCondition2.gte(TraineeInformation.InnerColumn.registrationTime, startTime);
        Integer count4 = countByCondition(simpleCondition2);
        SysJg sysJg2 = jgService.findByOrgCode("100003");
        map.put(sysJg2.getJgmc() + "小车报名数", count4);


        SimpleCondition condition2 = new SimpleCondition(TraineeInformation.class);
        condition2.and().andCondition(" jgdm like '100003%'");
        condition2.and().andCondition(" car_type like '%A%' or car_type like '%B%'");
        condition2.lte(TraineeInformation.InnerColumn.registrationTime, endTime);
        condition2.gte(TraineeInformation.InnerColumn.registrationTime, startTime);
        Integer count5 = countByCondition(condition2);
        map.put(sysJg2.getJgmc() + "大车报名数", count5);

        return ApiResponse.success(map);
    }

    @Override
    public Map<String, Object> impAcceptanceExcel(List<Map<Integer, String>> list, String fileName) {
        Map<String, Object> retMap = new HashMap<>();
        String key = genId();
        retMap.put("key", key);
        List<Map<Integer, String>> resultList = new ArrayList<>();
        List<Map<String, String>> webList = new ArrayList<>();
        long succeedCount = 0;
        long errorCount = 0;
        String errorKey = genId();
        String sucKey = genId();
        String sucName = System.currentTimeMillis() + "-suc.xls";
        List<Map<Integer, String>> sucList = new ArrayList<>();
        List<Map<Integer, String>> errorList = new ArrayList<>();

        // 查询学员
        List<String> collect = list.stream().map(m -> m.get(0)).collect(Collectors.toList());
        SimpleCondition condition = new SimpleCondition(TraineeInformation.class);
        condition.in(TraineeInformation.InnerColumn.idCardNo, collect);
        condition.notIn(TraineeInformation.InnerColumn.status, Arrays.asList("50", "60"));
        List<TraineeInformation> informations = findByCondition(condition);
        Map<String, List<TraineeInformation>> listMap = informations.stream().collect(Collectors.groupingBy(TraineeInformation::getIdCardNo));

        for (Map<Integer, String> map : list) {

            Map<String, String> webMap = new HashMap<>();
            int mapSize = map.size() + 1;
            if (StringUtils.equals(map.get(0), "学员身份证")) {
                map.put(map.size(), "学员姓名");
                map.put(mapSize, "处理结果");
                map.put(mapSize + 1, "处理备注");
                resultList.add(map);
                sucList.add(map);
                errorList.add(map);
            } else {
                //学员身份证(0)|学员流水号(1)
                webMap.put("idCardNo", map.get(0));
                webMap.put("serialNum", map.get(1)); // 学习驾驶证明编号

                String traineeId = "1";
                String traineeName = "";
                List<TraineeInformation> informationList = listMap.get(map.get(0));
                if (CollectionUtils.isNotEmpty(informationList)) {
                    traineeId = informationList.get(0).getId();
                    traineeName = informationList.get(0).getName();
                }
                map.put(map.size(), traineeName);
                webMap.put("userName", traineeName);
                TraineeInformation traineeInformation = new TraineeInformation();
                traineeInformation.setId(traineeId);
                traineeInformation.setSerialNum(map.get(1));
                ApiResponse<String> destineExcel = this.updateTraineeAcceptanceAuditing(traineeInformation);
                if (destineExcel.isSuccess()) {
                    String retMessage = "处理成功";
                    succeedCount++;
                    map.put(mapSize, "成功");
                    map.put(mapSize + 1, retMessage);
                    webMap.put("success", "1");
                    webMap.put("message", retMessage);
                    sucList.add(map);
                } else {
                    errorCount++;
                    map.put(mapSize, "处理失败");
                    map.put(mapSize + 1, destineExcel.getMessage());
                    webMap.put("success", "0");
                    webMap.put("message", destineExcel.getMessage());
                    resultList.add(map);
                    errorList.add(map);
                }
            }
            if (webMap.size() > 0) {
                webList.add(webMap);
            }
        }

        retMap.put("errorCount", errorCount);
        retMap.put("succeedCount", succeedCount);
        retMap.put("list", webList);
        retMap.put("sucKey", sucKey);
        retMap.put("errorKey", errorKey);

        //放到redis中去
        redisDao.boundValueOps(sucKey).set(JsonUtil.toJson(sucList), 1, TimeUnit.DAYS);

        redisDao.boundValueOps(sucKey + "_name").set(sucName, 1, TimeUnit.DAYS);

        redisDao.boundValueOps(errorKey).set(JsonUtil.toJson(errorList), 1, TimeUnit.DAYS);

        redisDao.boundValueOps(errorKey + "_name").set(sucName, 1, TimeUnit.DAYS);
        //放到redis中去
        redisDao.boundValueOps(key).set(JsonUtil.toJson(resultList), 1, TimeUnit.DAYS);

        redisDao.boundValueOps(key + "_name").set(fileName, 1, TimeUnit.DAYS);

        return retMap;
    }

    @Override
    public ApiResponse<String> revokeInfo(String traineeId) {
        TraineeInformation entity = baseMapper.selectByPrimaryKey(traineeId);
        RuntimeCheck.ifNull(entity, "未找到该学员信息，请核实");
        RuntimeCheck.ifFalse(StringUtils.equals(entity.getStatus(), Status.SIGN_UP), "该学员已报名，无法回退");
        entity.setInfoCheckStatus("00");
        entity.setInfoCheckTime(DateUtils.getNowTime());
        SysYh currentUser = getCurrentUser();
        entity.setModifier(currentUser.getZh() + "-" + currentUser.getXm());
        entity.setModifyTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        int i = update(entity);

        if (i > 0) {
            //学员状态表新增
            String status = "00";//10：审核通过 20：审核未通过
//            if (StringUtils.equals(entity.getInfoCheckStatus(), "20")) {
//                status = "10";
//            }
            String type = "学员基础资料回退";
            traineeStatusService.saveEntity(entity, type, status, "学员基础资料回退");
        }

        return i > 0 ? ApiResponse.success() : ApiResponse.fail("操作失败，請重新尝试");
    }

    @Override
    public ApiResponse<String> updateEntity(TraineeInformation entity) {

        RuntimeCheck.ifBlank(entity.getId(), "请选择学员");
        TraineeInformation information = findById(entity.getId());
        if (StringUtils.containsAny(information.getStatus(), "50", "60")) {
            return ApiResponse.fail("该学员处于结业或退学状态，不可修改");
        }
        validAndUpdate(entity);
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<String> dropOut(String id, int fee) {
        SysYh currentUser = getCurrentUser();

        RuntimeCheck.ifBlank(id, "请选择需要退学的学员");
        RuntimeCheck.ifTrue(fee < 0, "退费金额需要大于0");
        TraineeInformation information = findById(id);
        if (StringUtils.equals(information.getStatus(), "60")) {
            return ApiResponse.fail("学员已经退学,请勿重复操作,可前往收费查询页面查看");
        }

        String oldStatus = information.getStatus();

        information.setStatus("60");
        validAndUpdate(information);

        ChargeManagement management = new ChargeManagement();
        management.setId(genId());
        management.setCjsj(DateUtils.getNowTime());
        management.setTraineeSource("00");
        management.setChargeSource(information.getJgmc());
        management.setIdCardNo(information.getIdCardNo());
        management.setTraineeName(information.getName());
        management.setTraineeId(information.getId());
        management.setReceiver(currentUser.getZh() + "-" + currentUser.getXm());
        management.setChargeTime(DateUtils.getNowTime());
        management.setChargeFee(fee);
        management.setChargeCode(FeeType.DROP_OUT);
        management.setInOutType("10");
        management.setChargeType("10");
        management.setChargeName("退学金额");
        management.setCjr(currentUser.getZh() + "-" + currentUser.getXm());
        int save = chargeManagementService.save(management);
        if (save > 0) {
            traineeStatusService.saveEntity(information, "学员退学", "00", oldStatus);
        }
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<Long> getJrDjBmfZ(Page<TraineeInformation> page) {
//        long t = System.currentTimeMillis();
        ApiResponse<Long> result = new ApiResponse<>();
        LimitedCondition condition = getQueryCondition();
        condition.eq(TraineeInformation.InnerColumn.infoCheckStatus, "10");
        condition.and().andCondition(" confirm_time is null or confirm_time = ''");
        condition.setOrderByClause(" registration_time desc");
        PageInfo<TraineeInformation> pageInfo = findPage(page, condition);
       /* long realPay = baseMapper.countRealPay();
        result.setResult(realPay);*/
        Integer count = countByCondition(condition);
        page.setPageNum(1);
        page.setPageSize(9999);
        PageInfo<TraineeInformation> info = findPage(page, condition);
        if (CollectionUtils.isNotEmpty(info.getList())) {
            long asLong = info.getList().stream().mapToLong(TraineeInformation::getRealPay).reduce((i1, i2) -> i1 + i2).getAsLong();
            result.setResult(asLong);
        }
        pageInfo.getList().forEach(traineeInformation -> {
            SysJg byOrgCode = jgService.findByOrgCode(traineeInformation.getJgdm());
            if (!ObjectUtils.isEmpty(byOrgCode)) {
                traineeInformation.setJgPhone(byOrgCode.getLxdh1());
                traineeInformation.setJgLx(byOrgCode.getLx());
            }
        });

        result.setPage(pageInfo);
//        long s = System.currentTimeMillis();
//        System.out.println(s-t);
        return result;
    }

    @Override
    public ApiResponse<Long> getYjBmf(Page<TraineeInformation> page, String pj) {
        String confirmTimeGte = getRequestParamterAsString("confirmTimeGte");
        String confirmTimeLte = getRequestParamterAsString("confirmTimeLte");

        SysYh currentUser = getCurrentUser();
        List<String> trainIds = new ArrayList<>();
        List<ChargeManagement> managements1;
        SimpleCondition condition1 = new SimpleCondition(ChargeManagement.class);
        condition1.gte(ChargeManagement.InnerColumn.cjsj, confirmTimeGte);
        condition1.lte(ChargeManagement.InnerColumn.cjsj, confirmTimeLte);
        condition1.eq(ChargeManagement.InnerColumn.chargeCode, FeeType.SIGN_UP);
        if (StringUtils.equals(currentUser.getZw(), "财务主管") || currentUser.getJgdm().equals("100")) {
        } else {
            condition1.eq(ChargeManagement.InnerColumn.receiver, currentUser.getZh() + "-" + currentUser.getXm());
        }
        if (StringUtils.isNotBlank(pj)) {
            if (StringUtils.equals(pj, "10")) {
                condition1.and().andIsNotNull(ChargeManagement.InnerColumn.pjbh.name());
                condition1.setOrderByClause(" pjbh desc ");
            } else if (StringUtils.equals(pj, "20")) {
                condition1.and().andCondition(" pjbh ='' or pjbh is null");
            }

        }
        managements1 = chargeManagementService.findByCondition(condition1);
        if (CollectionUtils.isNotEmpty(managements1)) {
            trainIds = managements1.stream().map(ChargeManagement::getTraineeId).collect(Collectors.toList());
        }
        ApiResponse<Long> result = new ApiResponse<>();
        LimitedCondition condition = getQueryCondition();
        condition.setOrderByClause(" confirm_time desc ");

        if (CollectionUtils.isNotEmpty(trainIds)) {
            condition.in(TraineeInformation.InnerColumn.id, trainIds);
        } else {
            return result;
        }

        // condition.like(TraineeInformation.InnerColumn.confirmTime,DateUtils.getDateStr(new Date(),"yyyy-MM-dd"));
        PageInfo<TraineeInformation> pageInfo = findPage(page, condition);
        Page<ChargeManagement> page1 = new Page<>();
        page1.setPageNum(page.getPageNum());
        page1.setPageSize(page.getPageSize());
        if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
            condition1.in(ChargeManagement.InnerColumn.traineeId, pageInfo.getList().stream().map(TraineeInformation::getId).collect(Collectors.toSet()));
            List<ChargeManagement> page2 = chargeManagementService.findByCondition(condition1);
            List<ChargeManagement> managements = page2;
            List<String> strings = managements.stream().map(ChargeManagement::getTraineeId).collect(Collectors.toList());
            List<TraineeInformation> list = findIn(TraineeInformation.InnerColumn.id, strings);
            pageInfo.setList(list);
            List<String> jgdms = pageInfo.getList().stream().map(TraineeInformation::getJgdm).collect(Collectors.toList());
            List<SysJg> jgs = jgService.findIn(SysJg.InnerColumn.jgdm, jgdms);
            if (CollectionUtils.isNotEmpty(jgs)) {
                Map<String, SysJg> collect = jgs.stream().collect(Collectors.toMap(SysJg::getJgdm, s -> s));
                pageInfo.getList().forEach(traineeInformation -> {
                    SysJg byOrgCode = collect.get(traineeInformation.getJgdm());
                    if (!ObjectUtils.isEmpty(byOrgCode)) {
                        traineeInformation.setJgPhone(byOrgCode.getLxdh1());
                        traineeInformation.setJgLx(byOrgCode.getLx());
                    }
                });
            }

            List<TraineeInformation> info = findByCondition(condition);

            if (CollectionUtils.isNotEmpty(info)) {
                long asLong = info.stream().mapToLong(TraineeInformation::getRealPay).reduce((i1, i2) -> i1 + i2).getAsLong();
                result.setResult(asLong);
            }

            for (TraineeInformation traineeInformation : pageInfo.getList()) {
                managements.stream().filter(chargeManagement -> chargeManagement.getTraineeId().equals(traineeInformation.getId())).forEach(chargeManagement -> traineeInformation.setChargeRecord(chargeManagement));
            }
            if (StringUtils.equals(pj, "10")) {
                List<TraineeInformation> collect1 = pageInfo.getList().stream().sorted((o1, o2) -> o2.getChargeRecord().getPjbh().compareTo(o1.getChargeRecord().getPjbh())).collect(Collectors.toList());
                pageInfo.setList(collect1);

            }
        }

        result.setPage(pageInfo);
        return result;
    }

    @Override
    public ApiResponse<String> editReduceAndRealPay(String traineeId, String reduceId, int price, String chargeId) {
        RuntimeCheck.ifBlank(traineeId, "请选择需要修改的学员");
        RuntimeCheck.ifBlank(reduceId, "请选择优惠项");
        SysYh sysUser = getCurrentUser();
        TraineeInformation information = findById(traineeId);
        if (ObjectUtils.isEmpty(information)) {
            return ApiResponse.fail("未找到该学员的信息");
        }
        if (information.getRegistrationFee() < price) {
            return ApiResponse.fail("优惠金额不能大于报名费");
        }

        ReduceManagement reduceManagement = reduceManagementService.findById(reduceId);
        if (ObjectUtils.isEmpty(reduceManagement)) {
            return ApiResponse.fail("未找到该优惠项");
        }
        if (StringUtils.startsWith(reduceManagement.getReduceName(), "亲情价")) {
            if (StringUtils.isNotBlank(information.getReferrer()) && StringUtils.contains(information.getReferrer(), "-")) {
                String[] split = information.getReferrer().split("-");
                String id = split[1];
                String name = split[0];
                String today = DateUtils.getToday("yyyy-MM");
                String[] split1 = today.split("-");
                Zgjbxx zgjbxx = zgjbxxService.findById(id);
                if (today.compareTo(split1[0] + "-01") >= 0 && today.compareTo(split1[0] + "-06") <= 0) {
                    Integer count = zgTjjlService.countByTime(split1[0] + "-01-01 00:00:00", split1[0] + "-06-30 23:59:59", zgjbxx.getId());
                    if (zgjbxx.getsRs() == null || zgjbxx.getsRs() == 0) {
                        return ApiResponse.fail("当前推荐人没有推荐名额,请联系管理员");
                    } else if (zgjbxx.getsRs() <= count) {
                        return ApiResponse.fail("当前推荐人上半年名额已满,请联系管理员");
                    }
                } else {
                    Integer count = zgTjjlService.countByTime(split1[0] + "-07-01 00:00:00", split1[0] + "-12-31 23:59:59", zgjbxx.getId());
                    if (zgjbxx.getxRs() == null || zgjbxx.getxRs() == 0) {
                        return ApiResponse.fail("当前推荐人没有推荐名额,请联系管理员");
                    } else if (zgjbxx.getxRs() <= count) {
                        return ApiResponse.fail("当前推荐人下半年名额已满,请联系管理员");
                    }
                }
            }
        }
        String oldName = information.getReduceName();
        String oldPrice = information.getReducePrice() + "";
        if (StringUtils.isBlank(information.getReduceStatus())) {
            information.setReduceStatus("10");
            information.setReduceCheckTime(DateUtils.getNowTime());
        } else if (StringUtils.equals(information.getReduceStatus(), "10")) {
            information.setReduceCheckTime(DateUtils.getNowTime());
        }
        information.setReducePrice(price);
        information.setReduceName(reduceManagement.getReduceName());
        information.setReduceCode(reduceManagement.getId());
        information.setRealPay(information.getRegistrationFee() - information.getOweAmount() - price);
        information.setReduceVerifier(sysUser.getZh() + "-" + sysUser.getXm());
        int update = update(information);

        if (StringUtils.isNotBlank(information.getReferrer()) && StringUtils.contains(information.getReferrer(), "-")) {

            if (StringUtils.isNotBlank(information.getReduceCode())) {

                ReduceManagement reduce = reduceManagementService.findById(information.getReduceCode());
                if (StringUtils.startsWith(reduce.getReduceName(), "亲情价")) {
                    String[] split = information.getReferrer().split("-");
                    String id = split[1];
                    Zgjbxx zgjbxx = zgjbxxService.findById(id);
                    ZgTjjl zgTjjl = new ZgTjjl();
                    zgTjjl.setCjr(sysUser.getZh() + "-" + sysUser.getXm());
                    zgTjjl.setCjsj(DateUtils.getNowTime());
                    zgTjjl.setId(genId());
                    zgTjjl.setZgId(zgjbxx.getId());
                    zgTjjl.setTraineeId(information.getId());
                    zgTjjlService.save(zgTjjl);
                }
            }
        }

        if (update > 0) {
            traineeStatusService.saveEntity(information, "修改学员优惠项", "00", "修改学员优惠（" + oldName + ":" + oldPrice + "）->" + "(" + information.getReduceName() + ":" + information.getReducePrice() + ")");
        } else {
            return ApiResponse.fail("修改失败，请重试");
        }
        if (StringUtils.isNotBlank(chargeId)) {
            ChargeManagement chargeManagement = chargeManagementService.findById(chargeId);
            chargeManagement.setChargeFee(information.getRealPay());
            chargeManagementService.update(chargeManagement);
            traineeStatusService.saveEntity(information, "修改学员优惠项", "00", "学员确认收费后修改优惠金额");
        }


        return ApiResponse.success();
    }

    @Override
    public ApiResponse<String> editCarType(String id, String carType, String inOutType, Integer fee, String chargeType, String remark) {

        SysYh currentUser = getCurrentUser();

        TraineeInformation information = findById(id);
        RuntimeCheck.ifNull(information, "未找到学员信息");
        RuntimeCheck.ifBlank(carType, "修改车型不能为空");
        RuntimeCheck.ifBlank(inOutType, "收支类型不能为空");
        RuntimeCheck.ifTrue(fee < 0, "费用不能小于0");
        RuntimeCheck.ifBlank(chargeType, "收费类型不能为空");
       /* if(StringUtils.isBlank(information.getSerialNum())){
            return ApiResponse.fail("学员还未受理 ，不支持此操作 ");
        }*/

        if (StringUtils.containsAny(information.getStatus(), "50", "60")) {
            return ApiResponse.fail("学员已经退学或结业，不能修改");
        }

        if (StringUtils.equals(information.getCarType(), carType)) {
            return ApiResponse.fail("学员已经是" + carType + "车型，不需要修改");
        }
        if (!StringUtils.containsAny(carType, "A1", "A2", "A3", "B2", "C1", "C2")) {
            return ApiResponse.fail("所选车型有误，请重新选择");
        }

        String oldCar = information.getCarType();
        ChargeManagement management = new ChargeManagement();
        // management.setRemark(" 修改车型 : " + information.getCarType() + " -> " + carType + " - " +  remark);

        information.setCarType(carType);
        information.setModifier(currentUser.getZh() + "-" + currentUser.getXm());
        information.setModifyTime(DateUtils.getNowTime());
        validAndUpdate(information);


        management.setId(genId());
        management.setCjr(currentUser.getZh() + "-" + currentUser.getXm());
        management.setCjsj(DateUtils.getNowTime());
        management.setChargeCode(FeeType.CHA_CAR_TYPE);
        management.setChargeName("车型修改费用");
        management.setChargeFee(fee);
        management.setChargeTime(DateUtils.getNowTime());
        management.setInOutType(inOutType);
        management.setChargeType(chargeType);
        management.setReceiver(currentUser.getZh() + "-" + currentUser.getXm());
        management.setTraineeId(id);
        management.setTraineeName(information.getName());
        management.setIdCardNo(information.getIdCardNo());
        management.setChargeSource(information.getJgmc());
        management.setTraineeSource("00");
        chargeManagementService.save(management);

        traineeStatusService.saveEntity(information, "修改学员车型：" + oldCar + "->" + carType, "00", "修改学员车型");

        return ApiResponse.success();
    }

    @Override
    public ApiResponse<Long> getStag(Page<TraineeInformation> page) {
        ApiResponse<Long> result = new ApiResponse<>();
        LimitedCondition condition = getQueryCondition();
        condition.eq(TraineeInformation.InnerColumn.arrearage, "10");
        condition.in(TraineeInformation.InnerColumn.status, Arrays.asList("00", "10", "20", "30", "40", "50"));
        PageInfo<TraineeInformation> pageInfo = findPage(page, condition);

        List<TraineeInformation> list = findByCondition(condition);
        Long stag = list.stream().mapToLong(TraineeInformation::getOweAmount).sum();

        result.setResult(stag);
        result.setPage(pageInfo);
        return result;
    }


    @Override
    public ApiResponse<Long> getStaged(Page<TraineeInformation> page, String timeGte, String timeLte) {
        ApiResponse<Long> result = new ApiResponse<>();
        SimpleCondition simpleCondition = new SimpleCondition(ChargeManagement.class);
        simpleCondition.eq(ChargeManagement.InnerColumn.chargeCode, FeeType.STAGING);
        simpleCondition.gte(ChargeManagement.InnerColumn.cjsj, timeGte);
        simpleCondition.lte(ChargeManagement.InnerColumn.cjsj, timeLte);
        simpleCondition.setOrderByClause(" cjsj desc");

        Page<ChargeManagement> managementPage = new Page<>();
        managementPage.setPageSize(page.getPageSize());
        managementPage.setPageNum(page.getPageNum());

        // PageHelper.startPage(page.getPageNum(),page.getPageSize());
        PageInfo<ChargeManagement> page1 = chargeManagementService.findPage(managementPage, simpleCondition);
        List<String> list = page1.getList().stream().map(ChargeManagement::getTraineeId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(list)) {

            LimitedCondition condition = getQueryCondition();
            condition.in(TraineeInformation.InnerColumn.id, list);
            PageInfo<TraineeInformation> pageInfo = findPage(page, condition);
            List<TraineeInformation> informationList = pageInfo.getList();
            List<TraineeInformation> informations = new ArrayList<>();

            list.forEach(s -> informationList.stream().filter(traineeInformation -> traineeInformation.getId().equals(s)).forEach(traineeInformation -> informations.add(traineeInformation)));
            informations.forEach(traineeInformation -> page1.getList().stream().filter(chargeManagement -> chargeManagement.getTraineeId().equals(traineeInformation.getId())).forEach(chargeManagement -> {
                SysJg byOrgCode = jgService.findByOrgCode(traineeInformation.getJgdm());
                traineeInformation.setJgPhone(byOrgCode.getLxdh1());
                traineeInformation.setChargeRecord(chargeManagement);
            }));
            pageInfo.setList(informations);

            Long staged = chargeManagementService.countStaged(time);
            result.setResult(staged);
            result.setPage(pageInfo);
        } else {
            result.setPage(new PageInfo());
            result.setResult(0L);
        }
        return result;
    }

    @Override
    public ApiResponse<String> getAllAppointed(Page<TraineeInformation> page) {

        ApiResponse<String> result = new ApiResponse<>();
        LimitedCondition condition = getQueryCondition();

        String dateStr = DateUtils.getDateStr(new Date(), "yyyy-MM-dd");
        condition.and().andCondition("  fir_sub_test_time >= " + dateStr + " or sec_sub_test_time >= " + dateStr + " or third_sub_test_time >= " + dateStr);
        PageInfo<TraineeInformation> info = findPage(page, condition);
        result.setPage(info);

        return result;
    }

    @Override
    public ApiResponse<String> udpateToIns(TraineeInformation entity) {

        RuntimeCheck.ifTrue(entity.getOweAmount() <= 0, "欠费金额不能小于或等于0");
        RuntimeCheck.ifBlank(entity.getId(), "请选择需要修改的学员");

        TraineeInformation information = findById(entity.getId());
        if (StringUtils.equals(information.getInstallment(), "10")) {
            return ApiResponse.fail("学员已经是分期，不能再次分期");
        } else {
            information.setRealPay(information.getRealPay() - entity.getOweAmount());
            information.setInstallment("10");
            information.setArrearage("10");
            information.setOweAmount(entity.getOweAmount());
            update(information);
            traineeStatusService.saveEntity(information, "修改学员为分期", "00", "修改学员为分期");
        }

        return ApiResponse.success();
    }


    @Override
    public ApiResponse<String> removeEntity(String id) {
        TraineeInformation information = findById(id);
        remove(id);
        ChargeManagement chargeManagement = new ChargeManagement();
        chargeManagement.setTraineeId(id);
        chargeManagementService.remove(chargeManagement);
        traineeStatusService.saveEntity(information, "删除学员信息", "00", "删除学员信息");
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<String> editClassType(String id, String classType, String inOutType, Integer fee, String chargeType, String remark) {
        SysYh currentUser = getCurrentUser();

        TraineeInformation information = findById(id);
        RuntimeCheck.ifNull(information, "未找到学员信息");
        RuntimeCheck.ifBlank(classType, "修改班型不能为空");
        RuntimeCheck.ifBlank(inOutType, "收支类型不能为空");
        RuntimeCheck.ifTrue(fee < 0, "费用不能小于0");
        RuntimeCheck.ifBlank(chargeType, "收费类型不能为空");
        if (StringUtils.isBlank(information.getSerialNum())) {
            return ApiResponse.fail("学员还未受理 ，不支持此操作 ");
        }

        if (StringUtils.containsAny(information.getStatus(), "50", "60")) {
            return ApiResponse.fail("学员已经退学或结业，不能修改");
        }

        if (StringUtils.equals(information.getClassType(), classType)) {
            return ApiResponse.fail("当前班型已经是该班型，不需要修改");
        }
        if (!StringUtils.containsAny(classType, "10", "20", "30", "40", "50")) {
            return ApiResponse.fail("所选车型有误，请重新选择");
        }

        String oldClassType = information.getCarType();
        if (StringUtils.equals(oldClassType, "10")) {
            oldClassType = "普通班";
        } else if (StringUtils.equals(oldClassType, "20")) {
            oldClassType = "VIP班";
        } else if (StringUtils.equals(oldClassType, "30")) {
            oldClassType = "超级VIP班";
        } else if (StringUtils.equals(oldClassType, "40")) {
            oldClassType = "挂靠班";
        } else if (StringUtils.equals(oldClassType, "50")) {
            oldClassType = "承包班";
        }
        ChargeManagement management = new ChargeManagement();
        // management.setRemark(" 修改车型 : " + information.getCarType() + " -> " + carType + " - " +  remark);

        information.setClassType(classType);
        information.setModifier(currentUser.getZh() + "-" + currentUser.getXm());
        information.setModifyTime(DateUtils.getNowTime());
        validAndUpdate(information);


        management.setId(genId());
        management.setCjr(currentUser.getZh() + "-" + currentUser.getXm());
        management.setCjsj(DateUtils.getNowTime());
        management.setChargeCode(FeeType.CHA_CLA_TYPE);
        management.setChargeName("修改班型费用");
        management.setChargeFee(fee);
        management.setChargeTime(DateUtils.getNowTime());
        management.setInOutType(inOutType);
        management.setChargeType(chargeType);
        management.setReceiver(currentUser.getZh() + "-" + currentUser.getXm());
        management.setTraineeId(id);
        management.setTraineeName(information.getName());
        management.setIdCardNo(information.getIdCardNo());
        management.setChargeSource(information.getJgmc());
        management.setTraineeSource("00");
        chargeManagementService.save(management);
        if (StringUtils.equals(classType, "10")) {
            classType = "普通班";
        } else if (StringUtils.equals(classType, "20")) {
            classType = "VIP班";
        } else if (StringUtils.equals(classType, "30")) {
            classType = "超级VIP班";
        } else if (StringUtils.equals(classType, "40")) {
            classType = "挂靠班";
        } else if (StringUtils.equals(classType, "50")) {
            classType = "承包班";
        }
        traineeStatusService.saveEntity(information, "修改学员班型：" + oldClassType + "->" + classType, "00", "修改学员车型");

        return ApiResponse.success();
        /*return null;*/
    }

    @Override
    public ApiResponse<String> editRegistrationFee(String id, Integer registrationFee) {
        TraineeInformation information = findById(id);
        RuntimeCheck.ifNull(information, "请选择学员");

        int oldreg = information.getRegistrationFee();
        int oweAmount = information.getOweAmount() == null ? 0 : information.getOweAmount();
        int reducePrice = information.getReducePrice() == null ? 0 : information.getReducePrice();
        int realPay = registrationFee - oweAmount - reducePrice;

        RuntimeCheck.ifTrue(realPay < 0, "金额有误,请重新输入");

        information.setRegistrationFee(registrationFee);
        information.setRealPay(realPay);
        update(information);
        SimpleCondition condition = new SimpleCondition(ChargeManagement.class);
        condition.eq(ChargeManagement.InnerColumn.chargeCode, FeeType.SIGN_UP);
        condition.eq(ChargeManagement.InnerColumn.traineeId, information.getId());
        List<ChargeManagement> managements = chargeManagementService.findByCondition(condition);
        if (CollectionUtils.isNotEmpty(managements)) {
            ChargeManagement chargeManagement = managements.get(0);
            chargeManagement.setChargeFee(realPay);
            chargeManagementService.update(chargeManagement);
        }
        traineeStatusService.saveEntity(information, "修改报名金额: " + oldreg + "->" + registrationFee, "00", "修改报名费金额");
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<String> degradeCarType(String id) {
        TraineeInformation info = findById(id);
        RuntimeCheck.ifNull(info, "请选择学员");
        SysYh user = getCurrentUser();

        // RuntimeCheck.ifTrue( StringUtils.equals(sysJg.getJglx(), "10"), "当前学员不是总校学员,不能降级为C2,请联系管理员" );
        info.setCarType("C2");
        update(info);

        ChargeManagement management = new ChargeManagement();
        management.setChargeFee(0);
        management.setChargeType("10");
        management.setId(genId());
        management.setTraineeSource("00");
        management.setTraineeName(info.getName());
        management.setIdCardNo(info.getIdCardNo());
        management.setReceiver(user.getZh() + "-" + user.getXm());
        management.setCjsj(DateUtils.getNowTime());
        management.setChargeSource(info.getJgmc());
        management.setCjr(user.getZh() + "-" + user.getXm());
        management.setInOutType("00");
        management.setChargeName("变更车型");
        management.setChargeTime(DateUtils.getNowTime());
        management.setChargeCode(FeeType.CHA_CAR_TYPE);
        management.setTraineeId(info.getId());
        chargeManagementService.save(management);

        traineeStatusService.saveEntity(info, "C1直降C2", "00", "C1降C2");

        return ApiResponse.success();
    }

    @Override
    public ApiResponse<String> getAppointed(Page<TraineeInformation> entity) {
        ApiResponse<String> result = new ApiResponse<>();
        LimitedCondition condition = getQueryCondition();
        String statu = getRequestParamterAsString("statu");
        if (StringUtils.isBlank(statu)) {
            condition.and().andCondition(" status ='10' or status='20' or status = '30'");
        } else {
            condition.eq(TraineeInformation.InnerColumn.status, statu);
        }
        condition.and().andCondition(" status !='99' and status!='00' and status!='50' and status!='60'");
        condition.and().andCondition(" fir_Sub = '20' or sec_Sub = '10' or third_Sub = '10' or forth_sub = '00'");
        PageInfo<TraineeInformation> page = findPage(entity, condition);
        page.getList().forEach(traineeInformation -> {
            String id = traineeInformation.getId();
            SimpleCondition simpleCondition = new SimpleCondition(TraineeTestInfo.class);
            simpleCondition.eq(TraineeTestInfo.InnerColumn.traineeId, id);
            List<TraineeTestInfo> testInfos = traineeTestInfoService.findByCondition(simpleCondition);
            if (CollectionUtils.isNotEmpty(testInfos)) {
                traineeInformation.setTestInfo(testInfos.get(0));
            }

        });
        result.setPage(page);
        return result;
    }

    @Override
    public ApiResponse<String> getAppointing(Page<TraineeInformation> entity) {
        ApiResponse<String> result = new ApiResponse<>();
        String statu = getRequestParamterAsString("statu");
        LimitedCondition condition = getQueryCondition();
        condition.and().andCondition(" status !='99' and status!='00' and status!='50' and status!='60'");

        if (StringUtils.isNotBlank(statu)) {
            condition.eq(TraineeInformation.InnerColumn.status, statu);
            if (StringUtils.equals(statu, "10")) {
                condition.and().andCondition(" fir_sub !='20'");
            } else if (StringUtils.equals(statu, "20")) {
                condition.and().andCondition(" sec_sub !='10'");
            } else if (StringUtils.equals(statu, "30")) {
                condition.and().andCondition(" third_sub !='10'");
            }
        } else {
            condition.and().andCondition(" (status ='10' and fir_sub !='20') or (status='20' and sec_sub != '10') or (status = '30' and third_sub !='10') or status ='40'");
        }

        PageInfo<TraineeInformation> page = findPage(entity, condition);
        result.setPage(page);

        return result;
    }

    @Override
    public ApiResponse<String> deleteReduce(String id) {

        TraineeInformation information = findById(id);
        RuntimeCheck.ifNull(information, "未找到该学员");
        information.setReduceCheckTime(null);
        information.setReduceStatus(null);
        information.setReducePrice(null);
        information.setReduceName(null);
        information.setReduceVerifier(null);
        information.setReduceCode(null);
        information.setReduceRemark(null);
        information.setRealPay(information.getRegistrationFee() - information.getOweAmount());
        update(information);
        traineeStatusService.saveEntity(information, "删除学员的优惠", "00", "删除学员优惠");
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<TraineeInformation> getAllInfo(String id) {
        TraineeInformation information = findById(id);
        // 缴费记录信息
        SimpleCondition condition = new SimpleCondition(ChargeManagement.class);
        condition.eq(ChargeManagement.InnerColumn.traineeId, id);
        condition.setOrderByClause(" cjsj desc");
        List<ChargeManagement> managements = chargeManagementService.findByCondition(condition);

        // 状态记录信息
        SimpleCondition statusCondition = new SimpleCondition(TraineeStatus.class);
        statusCondition.eq(TraineeStatus.InnerColumn.traineeId, id);
        statusCondition.setOrderByClause(" cjsj desc");
        List<TraineeStatus> statuses = traineeStatusService.findByCondition(statusCondition);

        // 考试信息
        SimpleCondition testCondition = new SimpleCondition(TraineeTestInfo.class);
        testCondition.eq(TraineeTestInfo.InnerColumn.traineeId, id);
        testCondition.setOrderByClause(" cjsj desc");
        List<TraineeTestInfo> infos = traineeTestInfoService.findByCondition(testCondition);

        information.setManagements(managements);
        information.setStatuses(statuses);
        information.setTestInfos(infos);


        return ApiResponse.success(information);
    }

    @Override
    public ApiResponse<String> dropOutRevert(String id) {
        TraineeInformation information = findById(id);
        RuntimeCheck.ifNull(information, "请选择学员");

        SimpleCondition condition = new SimpleCondition(TraineeStatus.class);
        condition.like(TraineeStatus.InnerColumn.type, "学员退学");
        condition.eq(TraineeStatus.InnerColumn.traineeId, id);
        condition.setOrderByClause(" cjsj desc");
        List<TraineeStatus> list = traineeStatusService.findByCondition(condition);

        if (CollectionUtils.isNotEmpty(list)) {
            TraineeStatus traineeStatus = list.get(0);
            if (StringUtils.equals(traineeStatus.getRemark(), "学员退学")) {
                return ApiResponse.fail("此数据为历史数据,无法对当前记录进行撤回");
            } else {
                information.setStatus(traineeStatus.getRemark());
                update(information);
                ChargeManagement chargeManagement = new ChargeManagement();
                chargeManagement.setChargeCode(FeeType.DROP_OUT);
                chargeManagement.setTraineeId(id);
                chargeManagementService.remove(chargeManagement);
                traineeStatusService.saveEntity(information, "退学撤回", "00", "退学撤回");
            }
        }

        return ApiResponse.success();
    }

    @Override
    public ApiResponse<List<TraineeInformation>> getAppoint(String carType) {
        HttpServletRequest request = getRequset();
        SimpleCondition condition = new SimpleCondition(TraineeInformation.class);
        String[] tableColumns = request.getParameterValues("tableColumns");
        if (tableColumns != null && tableColumns.length != 0) {
            try {
                condition.selectProperties(tableColumns);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        condition.gte(TraineeInformation.InnerColumn.thirdSubTestTime, DateUtils.getNowTime());
        condition.eq(TraineeInformation.InnerColumn.status, "30");
        condition.eq(TraineeInformation.InnerColumn.carType, carType);
        condition.and().andCondition(" third_sub = '10' or third_sub = '20'");
        List<TraineeInformation> list = findByCondition(condition);
        list.forEach(traineeInformation -> {
            SimpleCondition condition1 = new SimpleCondition(TraineeTestInfo.class);
            condition1.eq(TraineeTestInfo.InnerColumn.traineeId, traineeInformation.getId());
            condition1.setOrderByClause(" cjsj desc ");
            List<TraineeTestInfo> byCondition = traineeTestInfoService.findByCondition(condition1);
            if (CollectionUtils.isNotEmpty(byCondition)) {
                traineeInformation.setTestInfo(byCondition.get(0));
            }
        });
        return ApiResponse.success(list);
    }

    @Override
    public ApiResponse<String> editCarTypeNoCharge(String id, String carType) {
        TraineeInformation information = findById(id);
        RuntimeCheck.ifNull(information, "未找到学院信息");
        String oldCar = information.getCarType();
        RuntimeCheck.ifBlank(carType, "请选择修改的车型");
        information.setCarType(carType);
        update(information);
        traineeStatusService.saveEntity(information, "修改学员车型：" + oldCar + "->" + carType, "00", "修改学员车型");
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<List<Map<String, String>>> confirmSignUpList(String ids, String bz) {
        RuntimeCheck.ifBlank(ids, "请选择学员");
        List<Map<String, String>> list = new ArrayList<>();
        List<String> trainIdsList = Arrays.asList(ids.split(","));
        String[] split = bz.split(",", -1);
        for (int i = 0; i < trainIdsList.size(); i++) {
            String id = trainIdsList.get(i);
            String remark = split[i];
            Map<String, String> map = new HashMap<>();
            TraineeInformation information = findById(id);
            ApiResponse<String> response = confirmSignUp(id, "10", remark);
            if (information != null && response.getCode() != 200) {
                map.put("name", information.getName());
                map.put("reason", response.getMessage());
                list.add(map);
            }
        }
        return ApiResponse.success(list);
    }

    @Override
    public TraineeInformation findByIdCardNo(String idCardNo) {
        SimpleCondition condition = new SimpleCondition(TraineeInformation.class);
        condition.eq(TraineeInformation.InnerColumn.idCardNo, idCardNo);
        condition.in(TraineeInformation.InnerColumn.status, Arrays.asList("99", "00", "10", "20", "30", "40"));
        List<TraineeInformation> list = findByCondition(condition);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public void exportResult(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
        LimitedCondition condition = getQueryCondition();
        condition.setOrderByClause(" jgdm asc ");
        String statusArray = getRequestParamterAsString("statusArray");
        if (StringUtils.isNotBlank(statusArray)) {
            List<String> list = Arrays.asList(statusArray.split(","));
            condition.in(TraineeInformation.InnerColumn.status, list);
        }
        String carTypeArray = getRequestParamterAsString("carTypeArray");
        if (StringUtils.isNotBlank(carTypeArray)) {
            List<String> list = Arrays.asList(carTypeArray.split(","));
            condition.in(TraineeInformation.InnerColumn.carType, list);
        }
        List<TraineeInformation> dataList = findByCondition(condition);
        List<Map<String, String>> data = new ArrayList<>();

        int seq = 1;
        for (TraineeInformation l : dataList) {
            Map<String, String> m = new HashMap<>();
            m.put("seq", seq + "");//序列
            m.put("name", l.getName());//姓名
            m.put("idCardNo", l.getIdCardNo());//证件号码
            List<String> list = Arrays.asList(l.getJgmc().split("/"));
            m.put("jgmc", CollectionUtils.size(list) == 2 ? list.get(0) : list.size() == 1 ? list.get(0) : list.size() == 3 ? list.get(1) : "成功驾校"); //报名点
            String serialNum = l.getSerialNum();
            m.put("serialNum", org.apache.commons.lang.StringUtils.isBlank(serialNum) ? "待受理" : serialNum);//流水号
            String gender = l.getGender();
            m.put("gender", org.apache.commons.lang.StringUtils.equals(gender, "10") ? "男" : "女");//性别
            String source = l.getSource();
            m.put("source", org.apache.commons.lang.StringUtils.equals(source, "00") ? "本地" : "外地");//本地/外地
            String carType = l.getCarType();
            if (org.apache.commons.lang.StringUtils.isEmpty(carType)) {
                carType = "-";
            }
            m.put("carType", carType);//车型
            m.put("registrationTime", l.getRegistrationTime());//l.getRegistrationTime()   报名时间
            String status = l.getStatus();
            m.put("status", org.apache.commons.lang.StringUtils.isNotEmpty(status) ? statusMap.get(status) : "-");
            m.put("recordLib", l.getRecordLib());
            m.put("indateEndTime", l.getIndateEndTime());//有效期
            m.put("secondSubjectCoach", l.getSecondSubjectCoach());//科二教练员
            m.put("thirdSubjectCoach", l.getThirdSubjectCoach());//科三教练员
            String chargeStatus = l.getChargeStatus();
            String chargeName = "未结清";
            if (org.apache.commons.lang.StringUtils.isNotEmpty(chargeStatus)) {
                if (org.apache.commons.lang.StringUtils.equals(chargeStatus, "10") && org.apache.commons.lang.StringUtils.equals(l.getArrearage(), "00")) {
                    chargeName = "已结清";
                }
            } else {
                chargeName = "-";
            }
            m.put("chargeStatus", chargeName);//报名费
            String firTypeName = "已缴";
            if (org.apache.commons.lang.StringUtils.equals(l.getAcceptStatus(), "10") && org.apache.commons.lang.StringUtils.isEmpty(l.getFirSubPaymentTime())) {  //todo
                firTypeName = "待缴";
            }
            m.put("firType", firTypeName);//科一初考费
            String secTypeName = "已缴";
            if (org.apache.commons.lang.StringUtils.equals(l.getFirSub(), "40") && org.apache.commons.lang.StringUtils.isEmpty(l.getSecSubPaymentTime())) {
                secTypeName = "待缴";
            }
            m.put("secType", secTypeName);//科二初考费
            String thirdTypeName = "已缴";
            if (org.apache.commons.lang.StringUtils.equals(l.getSecSub(), "40") && org.apache.commons.lang.StringUtils.isEmpty(l.getThirdSubPaymentTime())) {
                thirdTypeName = "待缴";
            }
            m.put("thirdType", thirdTypeName);//科三初考费
            String firSubName = "-";
            if (org.apache.commons.lang.StringUtils.equals(l.getFirSub(), "00")) {
                firSubName = "已缴费";
            } else if (org.apache.commons.lang.StringUtils.equals(l.getFirSub(), "10")) {
                firSubName = "学习中";
            } else if (org.apache.commons.lang.StringUtils.equals(l.getFirSub(), "20")) {
                firSubName = "已约考";
            } else if (org.apache.commons.lang.StringUtils.equals(l.getFirSub(), "30")) {
                firSubName = "不合格";
            } else if (org.apache.commons.lang.StringUtils.equals(l.getFirSub(), "40")) {
                firSubName = "合格";
            }
            m.put("firSub", firSubName);//科一状态
            String firSubTestTime = l.getFirSubTestTime();
            if (org.apache.commons.lang.StringUtils.isEmpty(firSubTestTime)) {
                firSubTestTime = "-";
            }
            m.put("firSubTestTime", firSubTestTime);//科一考试时间
            m.put("firSubTestNum", l.getFirSubTestNum() == null ? 0 + "" : l.getFirSubTestNum() + "");//科一考试次数
            String secSubName = "-";
            if (org.apache.commons.lang.StringUtils.equals(l.getSecSub(), "00")) {
                secSubName = "已缴费";
            } else if (org.apache.commons.lang.StringUtils.equals(l.getSecSub(), "10")) {
                secSubName = "学习中";
            } else if (org.apache.commons.lang.StringUtils.equals(l.getSecSub(), "20")) {
                secSubName = "已约考";
            } else if (org.apache.commons.lang.StringUtils.equals(l.getSecSub(), "30")) {
                secSubName = "不合格";
            } else if (org.apache.commons.lang.StringUtils.equals(l.getSecSub(), "40")) {
                secSubName = "合格";
            }

            m.put("secSub", secSubName);//科二状态
            String secSubTestTime = l.getSecSubTestTime();
            if (org.apache.commons.lang.StringUtils.isEmpty(secSubTestTime)) {
                secSubTestTime = "-";
            }
            m.put("secSubTestTime", secSubTestTime);//科二考试时间
            m.put("secSubTestNum", l.getSecSubTestNum() == null ? 0 + "" : l.getSecSubTestNum() + "");//科二考试次数
            String thirdSubName = "-";
            if (org.apache.commons.lang.StringUtils.equals(l.getThirdSub(), "00")) {
                thirdSubName = "已缴费";
            } else if (org.apache.commons.lang.StringUtils.equals(l.getThirdSub(), "10")) {
                thirdSubName = "学习中";
            } else if (org.apache.commons.lang.StringUtils.equals(l.getThirdSub(), "20")) {
                thirdSubName = "已约考";
            } else if (org.apache.commons.lang.StringUtils.equals(l.getThirdSub(), "30")) {
                thirdSubName = "不合格";
            } else if (org.apache.commons.lang.StringUtils.equals(l.getThirdSub(), "40")) {
                thirdSubName = "合格";
            }
            m.put("thirdSub", thirdSubName);//科三状态
            String thirdSubTestTime = l.getThirdSubTestTime();
            if (org.apache.commons.lang.StringUtils.isEmpty(thirdSubTestTime)) {
                thirdSubTestTime = "-";
            }
            m.put("thirdSubTestTime", thirdSubTestTime);//科三考试时间
            m.put("thirdSubTestNum", l.getThirdSubTestNum() == null ? 0 + "0" : l.getThirdSubTestNum() + "");//科三考试次数
            String forthSubName = "-";
            if (org.apache.commons.lang.StringUtils.equals(l.getForthSub(), "00")) {
                forthSubName = "已约考";
            } else if (org.apache.commons.lang.StringUtils.equals(l.getForthSub(), "20")) {
                forthSubName = "合格";

            }
            m.put("forthSub", forthSubName);//科四状态
            m.put("remark", StringUtils.equals(l.getRemark(), "999999") ? "历史数据" : l.getRemark());//备注

            data.add(m);
            seq++;
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