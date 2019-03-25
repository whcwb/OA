package com.ldz.biz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by wangwei on 2018/9/18.
 * 教练评价表，只用于每天评价，不打分
 */

@Table(name = "rate_detail")
public class RateDetail implements Serializable {

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
     * 学员头像
     */
    @Column(name = "head_img")
    private String headImg;

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
     * 分值，为0-5
     */
    @Column(name="rate")
    private String rate;

    /**
     * 评论
     */
    @Column(name="comment")
    private String comment;

    @Column(name="cjsj")
    private String cjsj;

    /**
     * 是否可见 0 不可见 1 可见  默认为0
     */
    @Column(name="visiable")
    private String visiable;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTraineeId() {
        return traineeId;
    }

    public void setTraineeId(String traineeId) {
        this.traineeId = traineeId;
    }

    public String getCoachId() {
        return coachId;
    }

    public void setCoachId(String coachId) {
        this.coachId = coachId;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCjsj() {
        return cjsj;
    }

    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    public String getTraineeName() {
        return traineeName;
    }

    public void setTraineeName(String traineeName) {
        this.traineeName = traineeName;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getVisiable() {
        return visiable;
    }

    public void setVisiable(String visiable) {
        this.visiable = visiable;
    }

    public enum InnerColumn {
        traineeId("trainee_id"),
        coachId("coach_id"),
        traineeName("trainee_name"),
        coachName("coach_name"),
        rate("rate"),
        comment("comment"),
        headImg("head_img"),
        visiable("visiable"),
        cjsj("cjsj");


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
