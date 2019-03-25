package com.ldz.biz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * 档案记录表
 */
@Table(name = "archives_record")
public class ArchivesRecord implements Serializable {
    /**
     * 主键
     */
    @Id
    private String id;

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
     * 学员流水号
     */
    @Column(name = "serial_num")
    private String serialNum;

    /**
     * 档案柜编码
     */
    @Column(name = "archives_code")
    private String archivesCode;

    /**
     * 入库时间
     */
    @Column(name = "in_time")
    private String inTime;

    /**
     * 出库时间
     */
    @Column(name = "out_time")
    private String outTime;

    /**
     * 入库人
     */
    @Column(name = "input_person")
    private String inputPerson;

    /**
     * 出库人
     */
    @Column(name = "output_person")
    private String outputPerson;

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
     * 档案状态  0、入库 1、出库
     */
    @Column(name = "stype")
    private String stype;
    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 身份证号
     */
    @Transient
    private String idCardNo;
    /**
     * 手机号码
     */
    @Transient
    private String phone;
    /**
     * 机构名称
     */
    @Transient
    private String jgmc;

    /**
     * 报名时间
     */
    @Transient
    private String registrationTime;

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getJgmc() {
        return jgmc;
    }

    public void setJgmc(String jgmc) {
        this.jgmc = jgmc;
    }

    public String getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(String registrationTime) {
        this.registrationTime = registrationTime;
    }

    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
     * 获取主键
     *
     * @return id - 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id;
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
     * 获取档案柜编码
     *
     * @return archives_code - 档案柜编码
     */
    public String getArchivesCode() {
        return archivesCode;
    }

    /**
     * 设置档案柜编码
     *
     * @param archivesCode 档案柜编码
     */
    public void setArchivesCode(String archivesCode) {
        this.archivesCode = archivesCode;
    }

    /**
     * 获取入库时间
     *
     * @return in_time - 入库时间
     */
    public String getInTime() {
        return inTime;
    }

    /**
     * 设置入库时间
     *
     * @param inTime 入库时间
     */
    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    /**
     * 获取出库时间
     *
     * @return out_time - 出库时间
     */
    public String getOutTime() {
        return outTime;
    }

    /**
     * 设置出库时间
     *
     * @param outTime 出库时间
     */
    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    /**
     * 获取入库人
     *
     * @return input_person - 入库人
     */
    public String getInputPerson() {
        return inputPerson;
    }

    /**
     * 设置入库人
     *
     * @param inputPerson 入库人
     */
    public void setInputPerson(String inputPerson) {
        this.inputPerson = inputPerson;
    }

    /**
     * 获取出库人
     *
     * @return output_person - 出库人
     */
    public String getOutputPerson() {
        return outputPerson;
    }

    /**
     * 设置出库人
     *
     * @param outputPerson 出库人
     */
    public void setOutputPerson(String outputPerson) {
        this.outputPerson = outputPerson;
    }

    public enum InnerColumn {
        id("id"),
        traineeId("trainee_id"),
        traineeName("trainee_name"),
        serialNum("serial_num"),
        archivesCode("archives_code"),
        inTime("in_time"),
        outTime("out_time"),
        inputPerson("input_person"),
        cjr("cjr"),
        stype("stype"),
        cjsj("cjsj"),
        outputPerson("output_person");

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