package com.ldz.biz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 投诉记录表
 */
@Table(name = "complain_record")
public class ComplainRecord implements Serializable {
    /**
     * ID
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
     * 投诉内容
     */
    @Column(name = "complain_content")
    private String complainContent;

    /**
     * 投诉时间
     */
    @Column(name = "complain_time")
    private String complainTime;

    /**
     * 反馈人
     */
    private String operater;
    /**
     * 反馈人姓名
     */
    @Column(name = "OPERATER_NAME")
    private String operaterName;
    /**
     * 反馈时间
     */
    @Column(name = "operate_time")
    private String operateTime;

    /**
     * 反馈内容
     */
    private String feedback;
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
     * 教练电话
     */
    @Column(name = "coach_phone")
    private String coachPhone;

    /**
     * 教练头像
     */
    @Column(name = "coach_head_img")
    private String coachHeadImg;

    /**
     * 学员头像
     */
    @Column(name = "trainee_head_img")
    private String traineeHeadImg;

    /**
     * 学员电话
     */
    @Column(name = "trainee_phone")
    private String traineePhone;

    /**
     * 投诉附件
     */
    @Column(name = "complain_file")
    private String complainFile;

    /**
     * 投诉状态
     */
    @Column(name = "complain_status")
    private String complainStatus;

    /**
     * 是否可见 0 不可见 1 可见  默认为0
     */
    @Column(name="visiable")
    private String visiable;

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

    public String getOperaterName() {
        return operaterName;
    }

    public void setOperaterName(String operaterName) {
        this.operaterName = operaterName;
    }

    /**
     * 获取ID
     *
     * @return id - ID
     */
    public String getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
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
     * 获取投诉内容
     *
     * @return complain_content - 投诉内容
     */
    public String getComplainContent() {
        return complainContent;
    }

    /**
     * 设置投诉内容
     *
     * @param complainContent 投诉内容
     */
    public void setComplainContent(String complainContent) {
        this.complainContent = complainContent;
    }

    /**
     * 获取投诉时间
     *
     * @return complain_time - 投诉时间
     */
    public String getComplainTime() {
        return complainTime;
    }

    /**
     * 设置投诉时间
     *
     * @param complainTime 投诉时间
     */
    public void setComplainTime(String complainTime) {
        this.complainTime = complainTime;
    }

    /**
     * 获取反馈人
     *
     * @return operater - 反馈人
     */
    public String getOperater() {
        return operater;
    }

    /**
     * 设置反馈人
     *
     * @param operater 反馈人
     */
    public void setOperater(String operater) {
        this.operater = operater;
    }

    /**
     * 获取反馈时间
     *
     * @return operate_time - 反馈时间
     */
    public String getOperateTime() {
        return operateTime;
    }

    /**
     * 设置反馈时间
     *
     * @param operateTime 反馈时间
     */
    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    /**
     * 获取反馈内容
     *
     * @return feedback - 反馈内容
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * 设置反馈内容
     *
     * @param feedback 反馈内容
     */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
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
     * 获取教练电话
     *
     * @return coach_phone - 教练电话
     */
    public String getCoachPhone() {
        return coachPhone;
    }

    /**
     * 设置教练电话
     *
     * @param coachPhone 教练电话
     */
    public void setCoachPhone(String coachPhone) {
        this.coachPhone = coachPhone;
    }

    /**
     * 获取教练头像
     *
     * @return coach_head_img - 教练头像
     */
    public String getCoachHeadImg() {
        return coachHeadImg;
    }

    /**
     * 设置教练头像
     *
     * @param coachHeadImg 教练头像
     */
    public void setCoachHeadImg(String coachHeadImg) {
        this.coachHeadImg = coachHeadImg;
    }

    /**
     * 获取学员头像
     *
     * @return trainee_head_img - 学员头像
     */
    public String getTraineeHeadImg() {
        return traineeHeadImg;
    }

    /**
     * 设置学员头像
     *
     * @param traineeHeadImg 学员头像
     */
    public void setTraineeHeadImg(String traineeHeadImg) {
        this.traineeHeadImg = traineeHeadImg;
    }

    /**
     * 获取学员电话
     *
     * @return trainee_phone - 学员电话
     */
    public String getTraineePhone() {
        return traineePhone;
    }

    /**
     * 设置学员电话
     *
     * @param traineePhone 学员电话
     */
    public void setTraineePhone(String traineePhone) {
        this.traineePhone = traineePhone;
    }

    /**
     * 获取投诉附件
     *
     * @return complain_file - 投诉附件
     */
    public String getComplainFile() {
        return complainFile;
    }

    /**
     * 设置投诉附件
     *
     * @param complainFile 投诉附件
     */
    public void setComplainFile(String complainFile) {
        this.complainFile = complainFile;
    }

    /**
     * 获取投诉状态
     *
     * @return complain_status - 投诉状态
     */
    public String getComplainStatus() {
        return complainStatus;
    }

    /**
     * 设置投诉状态
     *
     * @param complainStatus 投诉状态
     */
    public void setComplainStatus(String complainStatus) {
        this.complainStatus = complainStatus;
    }

    public enum InnerColumn {
        id("id"),
        traineeId("trainee_id"),
        traineeName("trainee_name"),
        complainContent("complain_content"),
        complainTime("complain_time"),
        operater("operater"),
        operateTime("operate_time"),
        feedback("feedback"),
        cjr("cjr"),
        cjsj("cjsj"),
        coachId("coach_id"),
        coachName("coach_name"),
        coachPhone("coach_phone"),
        coachHeadImg("coach_head_img"),
        traineeHeadImg("trainee_head_img"),
        traineePhone("trainee_phone"),
        complainFile("complain_file"),
        complainStatus("complain_status");

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