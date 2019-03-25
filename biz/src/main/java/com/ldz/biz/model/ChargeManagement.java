package com.ldz.biz.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * 收费记录管理表
 */
@Table(name = "charge_management")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChargeManagement implements Serializable {
    /**
     * 唯一标识
     */
    @Id
    private String id;

    /**
     * 收费项
     */
    @Column(name = "charge_code")
    private String chargeCode;

    /**
     * 收费项名称
     */
    @Column(name = "charge_name")
    private String chargeName;

    /**
     * 收支类型
     */
    @Column(name = "in_out_type")
    private String inOutType;

    /**
     * 收费金额
     */
    @Column(name = "charge_fee")
    private Integer chargeFee;

    /**
     * 收费时间
     */
    @Column(name = "charge_time")
    private String chargeTime;

    /**
     * 收款方式
     */
    @Column(name = "charge_type")
    private String chargeType;

    /**
     * 收款人
     */
    private String receiver;

    /**
     * 学员id
     */
    @Column(name = "trainee_id")
    private String traineeId;

    /**
     * 学员姓名
     */
    @Column(name = "trainee_name")
    private String traineeName;

    /**
     * 学员身份证号
     */
    @Column(name = "id_card_no")
    private String idCardNo;

    /**
     * 收费来源
     */
    @Column(name = "charge_source")
    private String chargeSource;

    /**
     * 学员身份
     */
    @Column(name = "trainee_source")
    private String traineeSource;

    /**
     * 确认人
     */
    private String verifier;

    /**
     * 确认时间
     */
    @Column(name = "verify_time")
    private String verifyTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 银行流水号
     */
    @Column(name = "bank_serial_num")
    private String bankSerialNum;

    /**
     * 对账标记
     */
    @Column(name = "check_status")
    private String checkStatus;

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

    @Column(name = "pjbh")
    private String pjbh;

    /**
     * 状态
     */
    @Column(name = "zt")
    private String zt;

    @Transient
    private Integer totalFee;

    @Transient
    private Integer outFee;
    @Transient
    private String carType;
    @Transient
    private TraineeInformation information;
    @Transient
    private String jgdm;
    @Transient
    private String glyxm;
    @Transient
    private String jgPhone;
    /**
     * 驳回人
     */
    private String bhr;
    /**
     * 驳回时间
     */
    private String bhsj;
    /**
     * 退费人
     */
    private String tfr;

    /**
     * 退费时间
     */
    private String tfsj;

    /**
     * 异常原因操作人
     */
    private String ycr;

    /**
     * 异常操作时间
     */
    private String ycsj;

    /**
     * 异常操作原因
     */
    private String ycyy;


    public String getYcr() {
        return ycr;
    }

    public void setYcr(String ycr) {
        this.ycr = ycr;
    }

    public String getYcsj() {
        return ycsj;
    }

    public void setYcsj(String ycsj) {
        this.ycsj = ycsj;
    }

    public String getYcyy() {
        return ycyy;
    }

    public void setYcyy(String ycyy) {
        this.ycyy = ycyy;
    }

    public String getTfr() {
        return tfr;
    }

    public void setTfr(String tfr) {
        this.tfr = tfr;
    }

    public String getTfsj() {
        return tfsj;
    }

    public void setTfsj(String tfsj) {
        this.tfsj = tfsj;
    }

    public String getBhr() {
        return bhr;
    }

    public void setBhr(String bhr) {
        this.bhr = bhr;
    }

    public String getBhsj() {
        return bhsj;
    }

    public void setBhsj(String bhsj) {
        this.bhsj = bhsj;
    }

    public String getJgPhone() {
        return jgPhone;
    }

    public void setJgPhone(String jgPhone) {
        this.jgPhone = jgPhone;
    }

    public String getGlyxm() {
        return glyxm;
    }

    public void setGlyxm(String glyxm) {
        this.glyxm = glyxm;
    }

    public String getJgdm() {
        return jgdm;
    }

    public void setJgdm(String jgdm) {
        this.jgdm = jgdm;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public Integer getOutFee() {
        return outFee;
    }

    public void setOutFee(Integer outFee) {
        this.outFee = outFee;
    }

    public String getPjbh() {
        return pjbh;
    }

    public void setPjbh(String pjbh) {
        this.pjbh = pjbh;
    }

    public TraineeInformation getInformation() {
        return information;
    }

    public void setInformation(TraineeInformation information) {
        this.information = information;
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
    private static final long serialVersionUID = 1L;

    /**
     * 获取唯一标识
     *
     * @return id - 唯一标识
     */
    public String getId() {
        return id;
    }

    /**
     * 设置唯一标识
     *
     * @param id 唯一标识
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取收费项
     *
     * @return charge_code - 收费项
     */
    public String getChargeCode() {
        return chargeCode;
    }

    /**
     * 设置收费项
     *
     * @param chargeCode 收费项
     */
    public void setChargeCode(String chargeCode) {
        this.chargeCode = chargeCode;
    }

    /**
     * 获取收费项名称
     *
     * @return charge_name - 收费项名称
     */
    public String getChargeName() {
        return chargeName;
    }

    /**
     * 设置收费项名称
     *
     * @param chargeName 收费项名称
     */
    public void setChargeName(String chargeName) {
        this.chargeName = chargeName;
    }

    /**
     * 获取收支类型
     *
     * @return in_out_type - 收支类型
     */
    public String getInOutType() {
        return inOutType;
    }

    /**
     * 设置收支类型
     *
     * @param inOutType 收支类型
     */
    public void setInOutType(String inOutType) {
        this.inOutType = inOutType;
    }

    /**
     * 获取收费金额
     *
     * @return charge_fee - 收费金额
     */
    public Integer getChargeFee() {
        return chargeFee;
    }

    /**
     * 设置收费金额
     *
     * @param chargeFee 收费金额
     */
    public void setChargeFee(Integer chargeFee) {
        this.chargeFee = chargeFee;
    }

    /**
     * 获取收费时间
     *
     * @return charge_time - 收费时间
     */
    public String getChargeTime() {
        return chargeTime;
    }

    /**
     * 设置收费时间
     *
     * @param chargeTime 收费时间
     */
    public void setChargeTime(String chargeTime) {
        this.chargeTime = chargeTime;
    }

    /**
     * 获取收款方式
     *
     * @return charge_type - 收款方式
     */
    public String getChargeType() {
        return chargeType;
    }

    /**
     * 设置收款方式
     *
     * @param chargeType 收款方式
     */
    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    /**
     * 获取收款人
     *
     * @return receiver - 收款人
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * 设置收款人
     *
     * @param receiver 收款人
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    /**
     * 获取学员id
     *
     * @return trainee_id - 学员id
     */
    public String getTraineeId() {
        return traineeId;
    }

    /**
     * 设置学员id
     *
     * @param traineeId 学员id
     */
    public void setTraineeId(String traineeId) {
        this.traineeId = traineeId;
    }

    /**
     * 获取学员姓名
     *
     * @return trainee_name - 学员姓名
     */
    public String getTraineeName() {
        return traineeName;
    }

    /**
     * 设置学员姓名
     *
     * @param traineeName 学员姓名
     */
    public void setTraineeName(String traineeName) {
        this.traineeName = traineeName;
    }

    /**
     * 获取学员身份证号
     *
     * @return id_card_no - 学员身份证号
     */
    public String getIdCardNo() {
        return idCardNo;
    }

    /**
     * 设置学员身份证号
     *
     * @param idCardNo 学员身份证号
     */
    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    /**
     * 获取收费来源
     *
     * @return charge_source - 收费来源
     */
    public String getChargeSource() {
        return chargeSource;
    }

    /**
     * 设置收费来源
     *
     * @param chargeSource 收费来源
     */
    public void setChargeSource(String chargeSource) {
        this.chargeSource = chargeSource;
    }

    /**
     * 获取学员身份
     *
     * @return trainee_source - 学员身份
     */
    public String getTraineeSource() {
        return traineeSource;
    }

    /**
     * 设置学员身份
     *
     * @param traineeSource 学员身份
     */
    public void setTraineeSource(String traineeSource) {
        this.traineeSource = traineeSource;
    }

    /**
     * 获取确认人
     *
     * @return verifier - 确认人
     */
    public String getVerifier() {
        return verifier;
    }

    /**
     * 设置确认人
     *
     * @param verifier 确认人
     */
    public void setVerifier(String verifier) {
        this.verifier = verifier;
    }

    /**
     * 获取确认时间
     *
     * @return verify_time - 确认时间
     */
    public String getVerifyTime() {
        return verifyTime;
    }

    /**
     * 设置确认时间
     *
     * @param verifyTime 确认时间
     */
    public void setVerifyTime(String verifyTime) {
        this.verifyTime = verifyTime;
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
     * 获取银行流水号
     *
     * @return bank_serial_num - 银行流水号
     */
    public String getBankSerialNum() {
        return bankSerialNum;
    }

    /**
     * 设置银行流水号
     *
     * @param bankSerialNum 银行流水号
     */
    public void setBankSerialNum(String bankSerialNum) {
        this.bankSerialNum = bankSerialNum;
    }

    /**
     * 获取对账标记
     *
     * @return check_status - 对账标记
     */
    public String getCheckStatus() {
        return checkStatus;
    }

    /**
     * 设置对账标记
     *
     * @param checkStatus 对账标记
     */
    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

    public enum InnerColumn {
        id("id"),
        chargeCode("charge_code"),
        chargeName("charge_name"),
        inOutType("in_out_type"),
        chargeFee("charge_fee"),
        chargeTime("charge_time"),
        chargeType("charge_type"),
        receiver("receiver"),
        traineeId("trainee_id"),
        traineeName("trainee_name"),
        idCardNo("id_card_no"),
        chargeSource("charge_source"),
        traineeSource("trainee_source"),
        verifier("verifier"),
        verifyTime("verify_time"),
        pjbh("pjbh"),
        remark("remark"),
        bankSerialNum("bank_serial_num"),
        cjr("cjr"),
        cjsj("cjsj"),
        checkStatus("check_status"),
        zt("zt"),
        bhr("bhr"),
        bhsj("bhsj");

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