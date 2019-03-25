package com.ldz.biz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 职工推荐记录表
 */
@Table(name = "ZG_TJJL")
public class ZgTjjl {
    @Id
    private String id;

    /**
     * 学员id
     */
    @Column(name = "trainee_id")
    private String traineeId;

    /**
     * 职工id
     */
    @Column(name = "ZG_ID")
    private String zgId;

    @Column(name = "CJSJ")
    private String cjsj;

    @Column(name = "CJR")
    private String cjr;

    @Column(name = "Remark")
    private String remark;

    @Transient
    private Zgjbxx zgjbxx;

    @Transient
    private TraineeInformation information;

    public Zgjbxx getZgjbxx() {
        return zgjbxx;
    }

    public void setZgjbxx(Zgjbxx zgjbxx) {
        this.zgjbxx = zgjbxx;
    }

    public TraineeInformation getInformation() {
        return information;
    }

    public void setInformation(TraineeInformation information) {
        this.information = information;
    }

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

    public String getZgId() {
        return zgId;
    }

    public void setZgId(String zgId) {
        this.zgId = zgId;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public enum InnerColumn {
        id("ID"),
        traineeId("TRAINEE_ID"),
        cjsj("CJSJ"),
        cjr("CJR"),
        remark("REMARK"),
        zgId("ZG_ID");

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
