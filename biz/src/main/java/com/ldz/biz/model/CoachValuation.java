package com.ldz.biz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 教练评分表 用于考试完成评分
 */
@Table(name = "coach_valuation")
public class CoachValuation implements Serializable {
    /**
     * ID
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
     * 评价内容
     */
    private String content;

    /**
     * 评价时间
     */
    @Column(name = "valuate_time")
    private String valuateTime;

    /**
     * 教练评分
     */
    @Column(name = "coach_score")
    private String coachScore;
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
     * 获取评价内容
     *
     * @return content - 评价内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置评价内容
     *
     * @param content 评价内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取评价时间
     *
     * @return valuate_time - 评价时间
     */
    public String getValuateTime() {
        return valuateTime;
    }

    /**
     * 设置评价时间
     *
     * @param valuateTime 评价时间
     */
    public void setValuateTime(String valuateTime) {
        this.valuateTime = valuateTime;
    }

    /**
     * 获取教练评分
     *
     * @return coach_score - 教练评分
     */
    public String getCoachScore() {
        return coachScore;
    }

    /**
     * 设置教练评分
     *
     * @param coachScore 教练评分
     */
    public void setCoachScore(String coachScore) {
        this.coachScore = coachScore;
    }

    public enum InnerColumn {
        id("id"),
        coachId("coach_id"),
        coachName("coach_name"),
        traineeId("trainee_id"),
        traineeName("trainee_name"),
        content("content"),
        valuateTime("valuate_time"),
        cjr("cjr"),
        cjsj("cjsj"),
        coachScore("coach_score");

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