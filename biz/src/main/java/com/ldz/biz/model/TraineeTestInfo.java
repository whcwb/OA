package com.ldz.biz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * 学员考试信息表
 */
@Table(name = "trainee_test_info")
public class TraineeTestInfo implements Serializable {
    /**
     * 唯一标识
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 学员id
     */
    @Column(name = "trainee_id")
    private String traineeId;

    /**
     * 学员名称
     */
    @Column(name = "trainee_name")
    private String traineeName;

    /**
     * 学员身份证号
     */
    @Column(name = "id_card_no")
    private String idCardNo;

    /**
     * 科目
     */
    private String subject;

    /**
     * 考试场地
     */
    @Column(name = "test_place")
    private String testPlace;

    /**
     * 考试结果
     */
    @Column(name = "test_result")
    private String testResult;

    /**
     * 备注
     */
    private String remark;

    /**
     * 约考时间
     */
    @Column(name = "test_time")
    private String testTime;

    /**
     * 缴费状态
     */
    @Column(name = "pay_status")
    private String payStatus;

    /**
     * 操作时间
     */
    @Column(name = "operate_time")
    private String operateTime;

    /**
     * 操作人
     */
    private String operator;
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



    @Transient
    private String jgdm;

    public String getJgdm() {
        return jgdm;
    }

    public void setJgdm(String jgdm) {
        this.jgdm = jgdm;
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
     * @return ID - 唯一标识
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
     * 获取学员名称
     *
     * @return trainee_name - 学员名称
     */
    public String getTraineeName() {
        return traineeName;
    }

    /**
     * 设置学员名称
     *
     * @param traineeName 学员名称
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
     * 获取科目
     *
     * @return subject - 科目
     */
    public String getSubject() {
        return subject;
    }

    /**
     * 设置科目
     *
     * @param subject 科目
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * 获取考试场地
     *
     * @return test_place - 考试场地
     */
    public String getTestPlace() {
        return testPlace;
    }

    /**
     * 设置考试场地
     *
     * @param testPlace 考试场地
     */
    public void setTestPlace(String testPlace) {
        this.testPlace = testPlace;
    }

    /**
     * 获取考试结果
     *
     * @return test_result - 考试结果
     */
    public String getTestResult() {
        return testResult;
    }

    /**
     * 设置考试结果
     *
     * @param testResult 考试结果
     */
    public void setTestResult(String testResult) {
        this.testResult = testResult;
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
     * 获取约考时间
     *
     * @return test_time - 约考时间
     */
    public String getTestTime() {
        return testTime;
    }

    /**
     * 设置约考时间
     *
     * @param testTime 约考时间
     */
    public void setTestTime(String testTime) {
        this.testTime = testTime;
    }

    /**
     * 获取缴费状态
     *
     * @return pay_status - 缴费状态
     */
    public String getPayStatus() {
        return payStatus;
    }

    /**
     * 设置缴费状态
     *
     * @param payStatus 缴费状态
     */
    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    /**
     * 获取操作时间
     *
     * @return operate_time - 操作时间
     */
    public String getOperateTime() {
        return operateTime;
    }

    /**
     * 设置操作时间
     *
     * @param operateTime 操作时间
     */
    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    /**
     * 获取操作人
     *
     * @return operator - 操作人
     */
    public String getOperator() {
        return operator;
    }

    /**
     * 设置操作人
     *
     * @param operator 操作人
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    public enum InnerColumn {
        id("ID"),
        traineeId("trainee_id"),
        traineeName("trainee_name"),
        idCardNo("id_card_no"),
        subject("subject"),
        testPlace("test_place"),
        testResult("test_result"),
        remark("remark"),
        testTime("test_time"),
        payStatus("pay_status"),
        operateTime("operate_time"),
        cjr("cjr"),
        cjsj("cjsj"),
        operator("operator");

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