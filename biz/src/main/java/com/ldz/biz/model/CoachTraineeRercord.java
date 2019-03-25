package com.ldz.biz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 教练学员分配记录表
 */
@Table(name = "coach_trainee_rercord")
public class CoachTraineeRercord implements Serializable {
    /**
     * 主键
     */
    @Id
    @Column(name = "pkid")
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
     * 学员身份证号
     */
    @Column(name = "id_card_no")
    private String idCardNo;

    /**
     * 分配时间
     */
    @Column(name = "allot_time")
    private String allotTime;

    /**
     * 学员状态  00:学习中 10：学习结束 20:已转教练 30：已退学
     */
    private String status;

    /**
     * 分配科目
     */
    @Column(name = "allot_sub")
    private String allotSub;

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
     * 修改人
     */
    private String modifier;

    /**
     * 修改时间
     */
    @Column(name = "modify_time")
    private String modifyTime;

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

    public String getModifier() {
        return modifier;
    }

    public  void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

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
     * 获取分配时间
     *
     * @return allot_time - 分配时间
     */
    public String getAllotTime() {
        return allotTime;
    }

    /**
     * 设置分配时间
     *
     * @param allotTime 分配时间
     */
    public void setAllotTime(String allotTime) {
        this.allotTime = allotTime;
    }

    /**
     * 学员状态  00:学习中 10：学习结束 20:已转教练 30：已退学
     *
     * @return status - 学员状态  00:学习中 10：学习结束 20:已转教练 30：已退学
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置学员状态  00:学习中 10：学习结束 20:已转教练 30：已退学
     *
     * @param status 学员状态  00:学习中 10：学习结束 20:已转教练 30：已退学
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取分配科目
     *
     * @return allot_sub - 分配科目
     */
    public String getAllotSub() {
        return allotSub;
    }

    /**
     * 设置分配科目
     *
     * @param allotSub 分配科目
     */
    public void setAllotSub(String allotSub) {
        this.allotSub = allotSub;
    }

    public enum InnerColumn {
        id("id"),
        coachId("coach_id"),
        coachName("coach_name"),
        traineeId("trainee_id"),
        traineeName("trainee_name"),
        idCardNo("id_card_no"),
        allotTime("allot_time"),
        status("status"),
        cjr("cjr"),
        cjsj("cjsj"),
        allotSub("allot_sub");

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