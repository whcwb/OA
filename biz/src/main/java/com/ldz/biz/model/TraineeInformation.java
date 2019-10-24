package com.ldz.biz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * 学生基本信息表
 */
@Table(name = "trainee_information")
public class TraineeInformation implements Serializable {
    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 学员姓名
     */
    private String name;

    /**
     * 身份证号
     */
    @Column(name = "id_card_no")
    private String idCardNo;
    /**
     * 学员照片
     */
    @Column(name = "head_img")
    private String headImg;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 性别
     */
    private String gender;

    /**
     * 来源
     */
    private String source;

    /**
     * 地址
     */
    private String address;

    /**
     * 学员当前状态
     * 学员当前状态 99:报名中 00: 受理中  10：科一学习中 20：科二学习中 30：科三学习中 40：科四学习中 50：结业 60：退学
     */
    private String status;

    /**
     * 证件有效期开始时间
     */
    @Column(name = "id_card_start_time")
    private String idCardStartTime;

    /**
     * 证件有效期结束时间
     */
    @Column(name = "id_card_end_time")
    private String idCardEndTime;

    /**
     * 机构代码
     */
    @Column(name = "jgdm")
    private String jgdm;

    /**
     * 机构名称
     */
    @Column(name = "jgmc")
    private String jgmc;

    /**
     * 班型
     */
    @Column(name = "class_type")
    private String classType;

    /**
     * 车型
     */
    @Column(name = "car_type")
    private String carType;

    /**
     * 报名时间
     */
    @Column(name = "registration_time")
    private String registrationTime;

    /**
     * 报名费用
     */
    @Column(name = "registration_fee")
    private Integer registrationFee;

    /**
     * 优惠金额
     */
    @Column(name = "reduce_price")
    private Integer reducePrice;

    /**
     * 优惠编码
     */
    @Column(name = "reduce_code")
    private String reduceCode;

    /**
     * 优惠名称
     */
    @Column(name = "reduce_name")
    private String reduceName;

    /**
     * 优惠备注
     */
    @Column(name = "reduce_remark")
    private String reduceRemark;

    /**
     * 欠费金额
     */
    @Column(name = "owe_amount")
    private Integer oweAmount;

    /**
     * 实付金额
     */
    @Column(name = "real_pay")
    private Integer realPay;

    /**
     * 是否分期   是否分期  00: 不分期 10：分期
     */
    private String installment;

    /**
     * 是否欠费
     */
    private String arrearage;

    /**
     * 基本信息审核状态
     */
    @Column(name = "info_check_status")
    private String infoCheckStatus;

    /**
     * 基本信息审核时间
     */
    @Column(name = "info_check_time")
    private String infoCheckTime;

    /**
     * 优惠信息审核状态
     */
    @Column(name = "reduce_status")
    private String reduceStatus;

    /**
     * 优惠信息审核时间
     */
    @Column(name = "reduce_check_time")
    private String reduceCheckTime;

//    /**
//     * 收费代码
//     */
//    @Column(name = "charge_code")
//    private String chargeCode;

    /**
     * 收费状态
     * 收费状态 00:未收费 10：已收费
     */
    @Column(name = "charge_status")
    private String chargeStatus;

    /**
     * 收费确认时间
     */
    @Column(name = "confirm_time")
    private String confirmTime;

    /**
     * 受理状态  00：未受理 10：受理中 20：已受理
     *
     */
    @Column(name = "accept_status")
    private String acceptStatus;

    /**
     * 受理成功时间
     */
    @Column(name = "accept_time")
    private String acceptTime;

    /**
     * 学员流水号
     */
    @Column(name = "serial_num")
    private String serialNum;

    /**
     * 科目一状态
     * 00：已缴费 10：培训 20: 已约考 30：不合格 40：合格
     */
    @Column(name = "fir_sub")
    private String firSub;

    /**
     * 科目一初考缴费时间
     */
    @Column(name = "fir_sub_payment_time")
    private String firSubPaymentTime;

    /**
     * 科目一约考时间
     */
    @Column(name = "fir_sub_test_time")
    private String firSubTestTime;

    /**
     * 科目一考试次数
     */
    @Column(name = "fir_sub_test_num")
    private Integer firSubTestNum;

    /**
     * 科目一培训状态
     * 00：合格 10：不合格
     */
    @Column(name = "fir_sub_train_status")
    private String firSubTrainStatus;

    /**
     * 科目二状态
     * 00: 培训 10：已约考 20：已缴费 30：不合格 40：合格
     */
    @Column(name = "sec_sub")
    private String secSub;

    /**
     * 科目二初考缴费时间
     */
    @Column(name = "sec_sub_payment_time")
    private String secSubPaymentTime;

    /**
     * 科目二约考时间
     */
    @Column(name = "sec_sub_test_time")
    private String secSubTestTime;

    /**
     * 科目二考试次数
     */
    @Column(name = "sec_sub_test_num")
    private Integer secSubTestNum;

    /**
     * 科目二培训状态
     */
    @Column(name = "sec_sub_train_status")
    private String secSubTrainStatus;

    /**
     * 科目三状态
     * 00: 培训 10：已约考 20：已缴费 30：不合格 40：合格
     */
    @Column(name = "third_sub")
    private String thirdSub;

    /**
     * 科目三初考缴费时间
     */
    @Column(name = "third_sub_payment_time")
    private String thirdSubPaymentTime;

    /**
     * 科目三约考时间
     */
    @Column(name = "third_sub_test_time")
    private String thirdSubTestTime;

    /**
     * 科目三考试次数
     */
    @Column(name = "third_sub_test_num")
    private Integer thirdSubTestNum;

    /**
     * 科目三培训状态
     */
    @Column(name = "third_sub_train_status")
    private String thirdSubTrainStatus;

    /**
     * 科目四 考试时间
     */
    @Column(name = "forth_sub_test_time")
    private String forthSubTestTime;

    /**
     * 科目四状态
     * 00：已约考 :10: 不合格 20：合格
     */
    @Column(name = "forth_sub")
    private String forthSub;

    /**
     * 档案所在库
     */
    @Column(name = "record_lib")
    private String recordLib;
    /**
     * 创建人
     */
    @Column(name = "cjr")
    private String cjr;

    /**
     * 创建时间
     */
    @Column(name = "cjsj")
    private String cjsj;

    /**
     * 优惠审核人
     * @return
     */
    @Column(name = "reduce_verifier")
    private String reduceVerifier;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 修改时间
     */
    @Column(name = "modify_Time")
    private String modifyTime;

    /**
     * 学员有效期开始时间
     */
    @Column(name = "indate_start_time")
    private String indateStartTime;

    /**
     * 学员有效期结束时间
     */
    @Column(name = "indate_end_time")
    private String indateEndTime;

    /**
     * 科二教练员
     */
    @Column(name = "second_subject_coach")
    private String secondSubjectCoach;

    /**
     * 科三教练员
     */
    @Column(name = "third_subject_coach")
    private String thirdSubjectCoach;

    /**
     * 推荐人
     */
    private String referrer;

    /**
     * 备注
     */
    private String remark;

    /**
     * 微信opne_id
     */
    @Column(name = "open_id")
    private String openId;

    /**
     * 电子签名
     */
    @Column(name = "elec_sign")
    private String elecSign;

    /**
     * 出生日期
     */
    @Column(name = "bir_day")
    private String birDay;

    /**
     * 机构负责人
     */
    @Column(name = "glyxm")
    private String glyxm;

    /**
     * 科目二练车次数
     */
    @Column(name = "sub_to_num")
    private Integer subToNum;

    /**
     * 科目三练车次数
     */
    @Column(name = "sub_three_num")
    private Integer subThreeNum;

    /**
     * 信息审核人
     */
    @Column(name = "info_checker")
    private String infoChecker;

    /**
     * 收费人
     */
    @Column(name = "confirmer")
    private String confirmer;

    /**
     * 受理确认人
     */
    @Column(name = "acceptor")
    private String acceptor;


    @Transient
    private ChargeManagement chargeRecord;

    @Transient
    private TraineeTestInfo testInfo;

    /**
     * 机构电话
     */
    @Transient
    private String jgPhone;

    /**
     * 机构类型
     */
    @Transient
    private String jgLx;

    @Transient
    private List<ChargeManagement> managements;

    @Transient
    private List<TraineeTestInfo> testInfos;

    @Transient
    private List<TraineeStatus> statuses;

    private String code;

    @Column(name = "error_message")
    private String errorMessage;

    @Transient
    private String testTime;

    @Transient
    private Integer firPay;

    @Transient
    private Integer secPay;

    @Transient
    private Integer thirdPay;

    @Transient
    private Integer total;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getFirPay() {
        return firPay;
    }

    public void setFirPay(Integer firPay) {
        this.firPay = firPay;
    }

    public Integer getSecPay() {
        return secPay;
    }

    public void setSecPay(Integer secPay) {
        this.secPay = secPay;
    }

    public Integer getThirdPay() {
        return thirdPay;
    }

    public void setThirdPay(Integer thirdPay) {
        this.thirdPay = thirdPay;
    }

    public String getTestTime() {
        return testTime;
    }

    public void setTestTime(String testTime) {
        this.testTime = testTime;
    }

    private static final long serialVersionUID = 1L;


    public String getForthSubTestTime() {
        return forthSubTestTime;
    }

    public void setForthSubTestTime(String forthSubTestTime) {
        this.forthSubTestTime = forthSubTestTime;
    }

    public String getInfoChecker() {
        return infoChecker;
    }

    public void setInfoChecker(String infoChecker) {
        this.infoChecker = infoChecker;
    }

    public String getConfirmer() {
        return confirmer;
    }

    public void setConfirmer(String confirmer) {
        this.confirmer = confirmer;
    }

    public String getAcceptor() {
        return acceptor;
    }

    public void setAcceptor(String acceptor) {
        this.acceptor = acceptor;
    }

    /**
     * 身份证 正面
     */
    @Transient
    private String cardFront;

    /**
     * 身份证 反面
     */
    @Transient
    private String cardBack;

    @Transient
    private Integer totalFee;

    /**
     * 欠费总金额
     */
    private long totalOwe;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getJgLx() {
        return jgLx;
    }

    public void setJgLx(String jgLx) {
        this.jgLx = jgLx;
    }


    public long getTotalOwe() {
        return totalOwe;
    }

    public void setTotalOwe(long totalOwe) {
        this.totalOwe = totalOwe;
    }

    public String getReduceVerifier() {
        return reduceVerifier;
    }

    public void setReduceVerifier(String reduceVerifier) {
        this.reduceVerifier = reduceVerifier;
    }

    public String getCjr() {
        return cjr;
    }

    public void setCjr(String cjr) {
        this.cjr = cjr;
    }

    public String getCjsj() {
        return cjsj;
    }

    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public String getJgPhone() {
        return jgPhone;
    }

    public void setJgPhone(String jgPhone) {
        this.jgPhone = jgPhone;
    }

    public List<ChargeManagement> getManagements() {
        return managements;
    }

    public void setManagements(List<ChargeManagement> managements) {
        this.managements = managements;
    }

    public List<TraineeStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<TraineeStatus> statuses) {
        this.statuses = statuses;
    }

    public List<TraineeTestInfo> getTestInfos() {
        return testInfos;
    }

    public void setTestInfos(List<TraineeTestInfo> testInfos) {
        this.testInfos = testInfos;
    }

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public String getId() {
        return id;
    }

    public String getCardFront() {
        return cardFront;
    }

    public void setCardFront(String cardFront) {
        this.cardFront = cardFront;
    }

    public String getCardBack() {
        return cardBack;
    }

    public void setCardBack(String cardBack) {
        this.cardBack = cardBack;
    }

    public String getGlyxm() {
        return glyxm;
    }

    public void setGlyxm(String glyxm) {
        this.glyxm = glyxm;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id;
    }

    public TraineeTestInfo getTestInfo() {
        return testInfo;
    }

    public void setTestInfo(TraineeTestInfo testInfo) {
        this.testInfo = testInfo;
    }

    public ChargeManagement getChargeRecord() {
        return chargeRecord;
    }

    public void setChargeRecord(ChargeManagement chargeRecord) {
        this.chargeRecord = chargeRecord;
    }

    /**
     * 获取学员姓名
     *
     * @return name - 学员姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置学员姓名
     *
     * @param name 学员姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getBirDay() {
        return birDay;
    }

    public void setBirDay(String birDay) {
        this.birDay = birDay;
    }

    /**
     * 获取身份证号
     *
     * @return id_card_no - 身份证号
     */
    public String getIdCardNo() {
        return idCardNo;
    }

    /**
     * 设置身份证号
     *
     * @param idCardNo 身份证号
     */
    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    /**
     * @return head_img
     */
    public String getHeadImg() {
        return headImg;
    }

    /**
     * @param headImg
     */
    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    /**
     * 获取手机号码
     *
     * @return phone - 手机号码
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置手机号码
     *
     * @param phone 手机号码
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

//    public String getChargeCode() {
//        return chargeCode;
//    }
//
//    public void setChargeCode(String chargeCode) {
//        this.chargeCode = chargeCode;
//    }

    /**
     * 获取性别
     *
     * @return gender - 性别
     */
    public String getGender() {
        return gender;
    }

    /**
     * 设置性别
     *
     * @param gender 性别
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * 获取来源
     *
     * @return source - 来源
     */
    public String getSource() {
        return source;
    }

    /**
     * 设置来源
     *
     * @param source 来源
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * 获取地址
     *
     * @return address - 地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置地址
     *
     * @param address 地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取学员当前状态
     *
     * @return status - 学员当前状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置学员当前状态
     *
     * @param status 学员当前状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取证件有效期开始时间
     *
     * @return id_card_start_time - 证件有效期开始时间
     */
    public String getIdCardStartTime() {
        return idCardStartTime;
    }

    /**
     * 设置证件有效期开始时间
     *
     * @param idCardStartTime 证件有效期开始时间
     */
    public void setIdCardStartTime(String idCardStartTime) {
        this.idCardStartTime = idCardStartTime;
    }

    /**
     * 获取证件有效期结束时间
     *
     * @return id_card_end_time - 证件有效期结束时间
     */
    public String getIdCardEndTime() {
        return idCardEndTime;
    }

    /**
     * 设置证件有效期结束时间
     *
     * @param idCardEndTime 证件有效期结束时间
     */
    public void setIdCardEndTime(String idCardEndTime) {
        this.idCardEndTime = idCardEndTime;
    }


    /**
     * 获取班型
     *
     * @return class_type - 班型
     */
    public String getClassType() {
        return classType;
    }

    /**
     * 设置班型
     *
     * @param classType 班型
     */
    public void setClassType(String classType) {
        this.classType = classType;
    }

    /**
     * 获取车型
     *
     * @return car_type - 车型
     */
    public String getCarType() {
        return carType;
    }

    /**
     * 设置车型
     *
     * @param carType 车型
     */
    public void setCarType(String carType) {
        this.carType = carType;
    }

    /**
     * 获取报名时间
     *
     * @return registration_time - 报名时间
     */
    public String getRegistrationTime() {
        return registrationTime;
    }

    /**
     * 设置报名时间
     *
     * @param registrationTime 报名时间
     */
    public void setRegistrationTime(String registrationTime) {
        this.registrationTime = registrationTime;
    }

    /**
     * 获取报名费用
     *
     * @return registration_fee - 报名费用
     */
    public Integer getRegistrationFee() {
        return registrationFee;
    }

    /**
     * 设置报名费用
     *
     * @param registrationFee 报名费用
     */
    public void setRegistrationFee(Integer registrationFee) {
        this.registrationFee = registrationFee;
    }

    /**
     * 获取优惠金额
     *
     * @return reduce_price - 优惠金额
     */
    public Integer getReducePrice() {
        return reducePrice;
    }

    /**
     * 设置优惠金额
     *
     * @param reducePrice 优惠金额
     */
    public void setReducePrice(Integer reducePrice) {
        this.reducePrice = reducePrice;
    }

    /**
     * 获取优惠编码
     *
     * @return reduce_code - 优惠编码
     */
    public String getReduceCode() {
        return reduceCode;
    }

    /**
     * 设置优惠编码
     *
     * @param reduceCode 优惠编码
     */
    public void setReduceCode(String reduceCode) {
        this.reduceCode = reduceCode;
    }

    /**
     * 获取优惠名称
     *
     * @return reduce_name - 优惠名称
     */
    public String getReduceName() {
        return reduceName;
    }

    /**
     * 设置优惠名称
     *
     * @param reduceName 优惠名称
     */
    public void setReduceName(String reduceName) {
        this.reduceName = reduceName;
    }

    /**
     * 获取优惠备注
     *
     * @return reduce_remark - 优惠备注
     */
    public String getReduceRemark() {
        return reduceRemark;
    }

    /**
     * 设置优惠备注
     *
     * @param reduceRemark 优惠备注
     */
    public void setReduceRemark(String reduceRemark) {
        this.reduceRemark = reduceRemark;
    }

    /**
     * 获取欠费金额
     *
     * @return owe_amount - 欠费金额
     */
    public Integer getOweAmount() {
        return oweAmount;
    }

    /**
     * 设置欠费金额
     *
     * @param oweAmount 欠费金额
     */
    public void setOweAmount(Integer oweAmount) {
        this.oweAmount = oweAmount;
    }

    /**
     * 获取实付金额
     *
     * @return real_pay - 实付金额
     */
    public Integer getRealPay() {
        return realPay;
    }

    /**
     * 设置实付金额
     *
     * @param realPay 实付金额
     */
    public void setRealPay(Integer realPay) {
        this.realPay = realPay;
    }

    /**
     * 获取是否分期
     *
     * @return installment - 是否分期
     */
    public String getInstallment() {
        return installment;
    }

    /**
     * 设置是否分期
     *
     * @param installment 是否分期
     */
    public void setInstallment(String installment) {
        this.installment = installment;
    }

    /**
     * 获取是否欠费
     *
     * @return arrearage - 是否欠费
     */
    public String getArrearage() {
        return arrearage;
    }

    /**
     * 设置是否欠费
     *
     * @param arrearage 是否欠费
     */
    public void setArrearage(String arrearage) {
        this.arrearage = arrearage;
    }

    /**
     * 获取基本信息审核状态
     *
     * @return info_check_status - 基本信息审核状态
     */
    public String getInfoCheckStatus() {
        return infoCheckStatus;
    }

    /**
     * 设置基本信息审核状态
     *
     * @param infoCheckStatus 基本信息审核状态
     */
    public void setInfoCheckStatus(String infoCheckStatus) {
        this.infoCheckStatus = infoCheckStatus;
    }

    /**
     * 获取基本信息审核时间
     *
     * @return info_check_time - 基本信息审核时间
     */
    public String getInfoCheckTime() {
        return infoCheckTime;
    }

    /**
     * 设置基本信息审核时间
     *
     * @param infoCheckTime 基本信息审核时间
     */
    public void setInfoCheckTime(String infoCheckTime) {
        this.infoCheckTime = infoCheckTime;
    }

    /**
     * 获取优惠信息审核状态
     *
     * @return reduce_status - 优惠信息审核状态
     */
    public String getReduceStatus() {
        return reduceStatus;
    }

    /**
     * 设置优惠信息审核状态
     *
     * @param reduceStatus 优惠信息审核状态
     */
    public void setReduceStatus(String reduceStatus) {
        this.reduceStatus = reduceStatus;
    }

    /**
     * 获取优惠信息审核时间
     *
     * @return reduce_check_time - 优惠信息审核时间
     */
    public String getReduceCheckTime() {
        return reduceCheckTime;
    }

    /**
     * 设置优惠信息审核时间
     *
     * @param reduceCheckTime 优惠信息审核时间
     */
    public void setReduceCheckTime(String reduceCheckTime) {
        this.reduceCheckTime = reduceCheckTime;
    }

    /**
     * 获取收费状态
     *
     * @return charge_status - 收费状态
     */
    public String getChargeStatus() {
        return chargeStatus;
    }

    public String getJgdm() {
        return jgdm;
    }

    public void setJgdm(String jgdm) {
        this.jgdm = jgdm;
    }

    public String getJgmc() {
        return jgmc;
    }

    public void setJgmc(String jgmc) {
        this.jgmc = jgmc;
    }

    /**
     * 设置收费状态
     *
     * @param chargeStatus 收费状态
     */
    public void setChargeStatus(String chargeStatus) {
        this.chargeStatus = chargeStatus;
    }

    /**
     * 获取收费确认时间
     *
     * @return confirm_time - 收费确认时间
     */
    public String getConfirmTime() {
        return confirmTime;
    }

    /**
     * 设置收费确认时间
     *
     * @param confirmTime 收费确认时间
     */
    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }

    /**
     * 获取受理状态
     *
     * @return accept_status - 受理状态
     */
    public String getAcceptStatus() {
        return acceptStatus;
    }

    /**
     * 设置受理状态
     *
     * @param acceptStatus 受理状态
     */
    public void setAcceptStatus(String acceptStatus) {
        this.acceptStatus = acceptStatus;
    }

    /**
     * 获取受理成功时间
     *
     * @return accept_time - 受理成功时间
     */
    public String getAcceptTime() {
        return acceptTime;
    }

    /**
     * 设置受理成功时间
     *
     * @param acceptTime 受理成功时间
     */
    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }

    /**
     * 获取学员流水号
     *
     * @return serial_num - 学员流水号
     */
    public String getSerialNum() {
        return serialNum;
    }

    /**
     * 设置学员流水号
     *
     * @param serialNum 学员流水号
     */
    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    /**
     * 获取科目一状态
     *
     * @return fir_sub - 科目一状态
     */
    public String getFirSub() {
        return firSub;
    }

    /**
     * 设置科目一状态
     *
     * @param firSub 科目一状态
     */
    public void setFirSub(String firSub) {
        this.firSub = firSub;
    }

    /**
     * 获取科目一初考缴费时间
     *
     * @return fir_sub_payment_time - 科目一初考缴费时间
     */
    public String getFirSubPaymentTime() {
        return firSubPaymentTime;
    }

    /**
     * 设置科目一初考缴费时间
     *
     * @param firSubPaymentTime 科目一初考缴费时间
     */
    public void setFirSubPaymentTime(String firSubPaymentTime) {
        this.firSubPaymentTime = firSubPaymentTime;
    }

    /**
     * 获取科目一约考时间
     *
     * @return fir_sub_test_time - 科目一约考时间
     */
    public String getFirSubTestTime() {
        return firSubTestTime;
    }

    /**
     * 设置科目一约考时间
     *
     * @param firSubTestTime 科目一约考时间
     */
    public void setFirSubTestTime(String firSubTestTime) {
        this.firSubTestTime = firSubTestTime;
    }

    /**
     * 获取科目一考试次数
     *
     * @return fir_sub_test_num - 科目一考试次数
     */
    public Integer getFirSubTestNum() {
        return firSubTestNum;
    }

    /**
     * 设置科目一考试次数
     *
     * @param firSubTestNum 科目一考试次数
     */
    public void setFirSubTestNum(Integer firSubTestNum) {
        this.firSubTestNum = firSubTestNum;
    }

    /**
     * 获取科目一培训状态
     *
     * @return fir_sub_train_status - 科目一培训状态
     */
    public String getFirSubTrainStatus() {
        return firSubTrainStatus;
    }

    /**
     * 设置科目一培训状态
     *
     * @param firSubTrainStatus 科目一培训状态
     */
    public void setFirSubTrainStatus(String firSubTrainStatus) {
        this.firSubTrainStatus = firSubTrainStatus;
    }

    /**
     * 获取科目二状态 00: 培训 10：已约考 20：已缴费 30：不合格 40：合格
     * @return sec_sub - 科目二状态
     */
    public String getSecSub() {
        return secSub;
    }

    /**
     * 设置科目二状态
     *
     * @param secSub 科目二状态
     */
    public void setSecSub(String secSub) {
        this.secSub = secSub;
    }

    /**
     * 获取科目二初考缴费时间
     *
     * @return sec_sub_payment_time - 科目二初考缴费时间
     */
    public String getSecSubPaymentTime() {
        return secSubPaymentTime;
    }

    /**
     * 设置科目二初考缴费时间
     *
     * @param secSubPaymentTime 科目二初考缴费时间
     */
    public void setSecSubPaymentTime(String secSubPaymentTime) {
        this.secSubPaymentTime = secSubPaymentTime;
    }

    /**
     * 获取科目二约考时间
     *
     * @return sec_sub_test_time - 科目二约考时间
     */
    public String getSecSubTestTime() {
        return secSubTestTime;
    }

    /**
     * 设置科目二约考时间
     *
     * @param secSubTestTime 科目二约考时间
     */
    public void setSecSubTestTime(String secSubTestTime) {
        this.secSubTestTime = secSubTestTime;
    }

    /**
     * 获取科目二考试次数
     *
     * @return sec_sub_test_num - 科目二考试次数
     */
    public Integer getSecSubTestNum() {
        return secSubTestNum;
    }

    /**
     * 设置科目二考试次数
     *
     * @param secSubTestNum 科目二考试次数
     */
    public void setSecSubTestNum(Integer secSubTestNum) {
        this.secSubTestNum = secSubTestNum;
    }

    /**
     * 获取科目二培训状态
     *
     * @return sec_sub_train_status - 科目二培训状态
     */
    public String getSecSubTrainStatus() {
        return secSubTrainStatus;
    }

    /**
     * 设置科目二培训状态
     *
     * @param secSubTrainStatus 科目二培训状态
     */
    public void setSecSubTrainStatus(String secSubTrainStatus) {
        this.secSubTrainStatus = secSubTrainStatus;
    }

    /**
     * 获取科目三状态
     *
     * @return third_sub - 科目三状态
     */
    public String getThirdSub() {
        return thirdSub;
    }

    /**
     * 设置科目三状态
     *
     * @param thirdSub 科目三状态
     */
    public void setThirdSub(String thirdSub) {
        this.thirdSub = thirdSub;
    }

    /**
     * 获取科目三初考缴费时间
     *
     * @return third_sub_payment_time - 科目三初考缴费时间
     */
    public String getThirdSubPaymentTime() {
        return thirdSubPaymentTime;
    }

    /**
     * 设置科目三初考缴费时间
     *
     * @param thirdSubPaymentTime 科目三初考缴费时间
     */
    public void setThirdSubPaymentTime(String thirdSubPaymentTime) {
        this.thirdSubPaymentTime = thirdSubPaymentTime;
    }

    /**
     * 获取科目三约考时间
     *
     * @return third_sub_test_time - 科目三约考时间
     */
    public String getThirdSubTestTime() {
        return thirdSubTestTime;
    }

    /**
     * 设置科目三约考时间
     *
     * @param thirdSubTestTime 科目三约考时间
     */
    public void setThirdSubTestTime(String thirdSubTestTime) {
        this.thirdSubTestTime = thirdSubTestTime;
    }

    /**
     * 获取科目三考试次数
     *
     * @return third_sub_test_num - 科目三考试次数
     */
    public Integer getThirdSubTestNum() {
        return thirdSubTestNum;
    }

    /**
     * 设置科目三考试次数
     *
     * @param thirdSubTestNum 科目三考试次数
     */
    public void setThirdSubTestNum(Integer thirdSubTestNum) {
        this.thirdSubTestNum = thirdSubTestNum;
    }

    /**
     * 获取科目三培训状态
     *
     * @return third_sub_train_status - 科目三培训状态
     */
    public String getThirdSubTrainStatus() {
        return thirdSubTrainStatus;
    }

    /**
     * 设置科目三培训状态
     *
     * @param thirdSubTrainStatus 科目三培训状态
     */
    public void setThirdSubTrainStatus(String thirdSubTrainStatus) {
        this.thirdSubTrainStatus = thirdSubTrainStatus;
    }

    /**
     * 获取科目四状态
     *
     * @return forth_sub - 科目四状态
     */
    public String getForthSub() {
        return forthSub;
    }

    /**
     * 设置科目四状态
     *
     * @param forthSub 科目四状态
     */
    public void setForthSub(String forthSub) {
        this.forthSub = forthSub;
    }

    /**
     * 获取档案所在库
     *
     * @return record_lib - 档案所在库
     */
    public String getRecordLib() {
        return recordLib;
    }

    /**
     * 设置档案所在库
     *
     * @param recordLib 档案所在库
     */
    public void setRecordLib(String recordLib) {
        this.recordLib = recordLib;
    }


    /**
     * 获取修改人
     *
     * @return modifier - 修改人
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * 设置修改人
     *
     * @param modifier 修改人
     */
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    /**
     * 获取修改时间
     *
     * @return modify_Time - 修改时间
     */
    public String getModifyTime() {
        return modifyTime;
    }

    /**
     * 设置修改时间
     *
     * @param modifyTime 修改时间
     */
    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * 获取学员有效期开始时间
     *
     * @return indate_start_time - 学员有效期开始时间
     */
    public String getIndateStartTime() {
        return indateStartTime;
    }

    /**
     * 设置学员有效期开始时间
     *
     * @param indateStartTime 学员有效期开始时间
     */
    public void setIndateStartTime(String indateStartTime) {
        this.indateStartTime = indateStartTime;
    }

    /**
     * 获取学员有效期结束时间
     *
     * @return indate_end_time - 学员有效期结束时间
     */
    public String getIndateEndTime() {
        return indateEndTime;
    }

    /**
     * 设置学员有效期结束时间
     *
     * @param indateEndTime 学员有效期结束时间
     */
    public void setIndateEndTime(String indateEndTime) {
        this.indateEndTime = indateEndTime;
    }

    /**
     * 获取科二教练员
     *
     * @return second_subject_coach - 科二教练员
     */
    public String getSecondSubjectCoach() {
        return secondSubjectCoach;
    }

    /**
     * 设置科二教练员
     *
     * @param secondSubjectCoach 科二教练员
     */
    public void setSecondSubjectCoach(String secondSubjectCoach) {
        this.secondSubjectCoach = secondSubjectCoach;
    }

    /**
     * 获取科三教练员
     *
     * @return third_subject_coach - 科三教练员
     */
    public String getThirdSubjectCoach() {
        return thirdSubjectCoach;
    }

    /**
     * 设置科三教练员
     *
     * @param thirdSubjectCoach 科三教练员
     */
    public void setThirdSubjectCoach(String thirdSubjectCoach) {
        this.thirdSubjectCoach = thirdSubjectCoach;
    }

    /**
     * 获取推荐人
     *
     * @return referrer - 推荐人
     */
    public String getReferrer() {
        return referrer;
    }

    /**
     * 设置推荐人
     *
     * @param referrer 推荐人
     */
    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取微信opne_id
     *
     * @return open_id - 微信opne_id
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * 设置微信opne_id
     *
     * @param openId 微信opne_id
     */
    public void setOpenId(String openId) {
        this.openId = openId;
    }

    /**
     * 获取电子签名
     *
     * @return elec_sign - 电子签名
     */
    public String getElecSign() {
        return elecSign;
    }

    /**
     * 设置电子签名
     *
     * @param elecSign 电子签名
     */
    public void setElecSign(String elecSign) {
        this.elecSign = elecSign;
    }


    public Integer getSubToNum() {
        return subToNum;
    }

    public void setSubToNum(Integer subToNum) {
        this.subToNum = subToNum;
    }

    public Integer getSubThreeNum() {
        return subThreeNum;
    }

    public void setSubThreeNum(Integer subThreeNum) {
        this.subThreeNum = subThreeNum;
    }

    public enum InnerColumn {
        id("id"),
        name("name"),
        idCardNo("id_card_no"),
        headImg("head_img"),
        phone("phone"),
        gender("gender"),
        source("source"),
        address("address"),
        status("status"),
        idCardStartTime("id_card_start_time"),
        idCardEndTime("id_card_end_time"),
        jgdm("jgdm"),
        jgmc("jgmc"),
        classType("class_type"),
        carType("car_type"),
        registrationTime("registration_time"),
        registrationFee("registration_fee"),
        reducePrice("reduce_price"),
        reduceCode("reduce_code"),
        reduceName("reduce_name"),
        reduceRemark("reduce_remark"),
        oweAmount("owe_amount"),
        realPay("real_pay"),
        installment("installment"),
        arrearage("arrearage"),
        infoCheckStatus("info_check_status"),
        infoCheckTime("info_check_time"),
        reduceStatus("reduce_status"),
        reduceCheckTime("reduce_check_time"),
        chargeStatus("charge_status"),
        confirmTime("confirm_time"),
        acceptStatus("accept_status"),
        acceptTime("accept_time"),
        serialNum("serial_num"),
        firSub("fir_sub"),
        firSubPaymentTime("fir_sub_payment_time"),
        firSubTestTime("fir_sub_test_time"),
        firSubTestNum("fir_sub_test_num"),
        firSubTrainStatus("fir_sub_train_status"),
        secSub("sec_sub"),
        secSubPaymentTime("sec_sub_payment_time"),
        secSubTestTime("sec_sub_test_time"),
        secSubTestNum("sec_sub_test_num"),
        secSubTrainStatus("sec_sub_train_status"),
        thirdSub("third_sub"),
        thirdSubPaymentTime("third_sub_payment_time"),
        thirdSubTestTime("third_sub_test_time"),
        thirdSubTestNum("third_sub_test_num"),
        thirdSubTrainStatus("third_sub_train_status"),
        forthSub("forth_sub"),
        recordLib("record_lib"),
        cjr("cjr"),
        cjsj("cjsj"),
        modifier("modifier"),
        modifyTime("modify_Time"),
        indateStartTime("indate_start_time"),
        indateEndTime("indate_end_time"),
        secondSubjectCoach("second_subject_coach"),
        thirdSubjectCoach("third_subject_coach"),
        referrer("referrer"),
        remark("remark"),
        openId("open_id"),
        elecSign("elec_sign"),
        chargeCode("charge_code"),
        glyxm("glyxm"),
        subToNum("sub_to_num"),
        subThreeNum("sub_three_num");

        private final String column;

        public String value() {
            return this.column;
        }

        public String getValue() {
            return this.column;
        }

        InnerColumn(String column) {
            this.column = column;
        }

        public String desc() {
            return this.column + " DESC";
        }

        public String asc() {
            return this.column + " ASC";
        }
    }
}