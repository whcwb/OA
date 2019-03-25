package com.ldz.biz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 预约错误记录
 */
@Table(name = "YY_CWJL")
public class YyCwjl {


    @Id
    private String id;

    /**
     * 学员姓名
     */
    @Column(name = "TRAINEE_NAME")
    private String traineeName;

    /**
     * 预约科目
     */
    @Column(name = "SUB")
    private String sub;

    /**
     * 错误原因
     */
    @Column(name = "REASON")
    private String reason;

    /**
     * 处理状态
     */
    @Column(name = "ZT")
    private String zt;

    /**
     * 学员id
     */
    @Column(name = "TRAINEE_ID")
    private String traineeId;

    /**
     * 创建时间
     */
    @Column(name = "CJSJ")
    private String cjsj;

    /**
     * 创建人
     */
    @Column(name = "CJR")
    private String cjr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTraineeName() {
        return traineeName;
    }

    public void setTraineeName(String traineeName) {
        this.traineeName = traineeName;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

    public String getTraineeId() {
        return traineeId;
    }

    public void setTraineeId(String traineeId) {
        this.traineeId = traineeId;
    }

    public String getCjsj() {
        return cjsj;
    }

    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    public String getCjr() {
        return cjr;
    }

    public void setCjr(String cjr) {
        this.cjr = cjr;
    }


    public enum InnerColumn {
        id("ID"),
        traineeId("TRAINEE_ID"),
        cjsj("CJSJ"),
        cjr("CJR"),
        reason("REASON"),
        sub("SUB"),
        zt("ZT"),
        traineeName("TRAINEE_NAME");

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
