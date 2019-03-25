package com.ldz.biz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 培训记录表
 */
@Table(name = "training_record")
public class TrainingRecord implements Serializable {
    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 教练id
     */
    @Column(name = "coach_id")
    private String coachId;

    /**
     * 教练姓名
     */
    @Column(name = "coach_name")
    private String coachName;

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
     * 培训开始时间
     */
    @Column(name = "train_start_time")
    private String trainStartTime;

    /**
     * 培训结束时间
     */
    @Column(name = "train_end_time")
    private String trainEndTime;

    /**
     * 培训时长
     */
    @Column(name = "train_times")
    private Integer trainTimes;

    /**
     * 车型
     */
    @Column(name = "car_type")
    private String carType;

    /**
     * 操作时间
     */
    @Column(name = "operate_time")
    private String operateTime;
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
     * 培训科目 01：科一 02：科二 03：科三
     */
    @Column(name = "sub")
    private String sub;

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
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
    /**
     * 操作人
     */
    private String operator;

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
     * 获取教练id
     *
     * @return coach_id - 教练id
     */
    public String getCoachId() {
        return coachId;
    }

    /**
     * 设置教练id
     *
     * @param coachId 教练id
     */
    public void setCoachId(String coachId) {
        this.coachId = coachId;
    }

    /**
     * 获取教练姓名
     *
     * @return coach_name - 教练姓名
     */
    public String getCoachName() {
        return coachName;
    }

    /**
     * 设置教练姓名
     *
     * @param coachName 教练姓名
     */
    public void setCoachName(String coachName) {
        this.coachName = coachName;
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
     * 获取培训开始时间
     *
     * @return train_start_time - 培训开始时间
     */
    public String getTrainStartTime() {
        return trainStartTime;
    }

    /**
     * 设置培训开始时间
     *
     * @param trainStartTime 培训开始时间
     */
    public void setTrainStartTime(String trainStartTime) {
        this.trainStartTime = trainStartTime;
    }

    /**
     * 获取培训结束时间
     *
     * @return train_end_time - 培训结束时间
     */
    public String getTrainEndTime() {
        return trainEndTime;
    }

    /**
     * 设置培训结束时间
     *
     * @param trainEndTime 培训结束时间
     */
    public void setTrainEndTime(String trainEndTime) {
        this.trainEndTime = trainEndTime;
    }

    /**
     * 获取培训时长
     *
     * @return train_times - 培训时长
     */
    public Integer getTrainTimes() {
        return trainTimes;
    }

    /**
     * 设置培训时长
     *
     * @param trainTimes 培训时长
     */
    public void setTrainTimes(Integer trainTimes) {
        this.trainTimes = trainTimes;
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
        id("id"),
        coachId("coach_id"),
        coachName("coach_name"),
        traineeId("trainee_id"),
        traineeName("trainee_name"),
        trainStartTime("train_start_time"),
        trainEndTime("train_end_time"),
        trainTimes("train_times"),
        carType("car_type"),
        cjr("cjr"),
        cjsj("cjsj"),
        operateTime("operate_time"),
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