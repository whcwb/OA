package com.ldz.biz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 收费机构管理
 */
@Table(name = "charge_institution_manage")
public class ChargeInstitutionManage implements Serializable {
    /**
     * ID
     */
    @Id
    private String id;

    /**
     * 机构代码
     */
    @Column(name = "jgdm")
    private String jgdm;

    /**
     * 机构名称
     */
    @Column(name = "jgmc")
    private String jgmc;

    /**
     * 收费项id
     */
    @Column(name = "charge_id")
    private String chargeId;
    /**
     * 操作人
     */
    private String operater;

    /**
     * 操作时间
     */
    @Column(name = "opetate_time")
    private String opetateTime;

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

    public String getJgdm() {
        return jgdm;
    }

    public void setJgdm(String jgdm) {
        this.jgdm = jgdm;
    }

    public String getJgmc() {
        return jgmc;
    }

    public void setJgmc(String jgmc) {
        this.jgmc = jgmc;
    }

    public String getChargeId() {
        return chargeId;
    }

    public void setChargeId(String chargeId) {
        this.chargeId = chargeId;
    }

    /**
     * 获取操作人
     *
     * @return operater - 操作人
     */
    public String getOperater() {
        return operater;
    }

    /**
     * 设置操作人
     *
     * @param operater 操作人
     */
    public void setOperater(String operater) {
        this.operater = operater;
    }

    /**
     * 获取操作时间
     *
     * @return opetate_time - 操作时间
     */
    public String getOpetateTime() {
        return opetateTime;
    }

    /**
     * 设置操作时间
     *
     * @param opetateTime 操作时间
     */
    public void setOpetateTime(String opetateTime) {
        this.opetateTime = opetateTime;
    }

    public enum InnerColumn {
        id("id"),
        jgdm("jgdm"),
        jgmc("jgmc"),
        chargeId("charge_id"),
        operater("operater"),
        cjr("cjr"),
        cjsj("cjsj"),
        opetateTime("opetate_time");

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":\"")
                .append(id).append('\"');
        sb.append(",\"jgdm\":\"")
                .append(jgdm).append('\"');
        sb.append(",\"jgmc\":\"")
                .append(jgmc).append('\"');
        sb.append(",\"chargeId\":\"")
                .append(chargeId).append('\"');
        sb.append(",\"operater\":\"")
                .append(operater).append('\"');
        sb.append(",\"opetateTime\":\"")
                .append(opetateTime).append('\"');
        sb.append(",\"cjr\":\"")
                .append(cjr).append('\"');
        sb.append(",\"cjsj\":\"")
                .append(cjsj).append('\"');
        sb.append('}');
        return sb.toString();
    }
}