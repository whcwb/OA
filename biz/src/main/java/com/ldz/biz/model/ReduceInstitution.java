package com.ldz.biz.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 收費项与机构关联表
 */
@Table(name = "reduce_institution")
public class ReduceInstitution implements Serializable {
    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private String id;

    /**
     * 机构代码
     */
    private String jgdm;

    /**
     * 机构名称
     */
    private String jgmc;

    /**
     * 优惠项id
     */
    @Column(name = "reduce_id")
    private String reduceId;

    /**
     * 操作人
     */
    private String operater;

    /**
     * 操作时间
     */
    @Column(name = "operate_time")
    private String operateTime;

    /**
     * 创建人
     */
    private String cjr;

    /**
     * 创建时间
     */
    private String cjsj;

    private static final long serialVersionUID = 1L;

    /**
     * 获取id
     *
     * @return id - id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取机构代码
     *
     * @return jgdm - 机构代码
     */
    public String getJgdm() {
        return jgdm;
    }

    /**
     * 设置机构代码
     *
     * @param jgdm 机构代码
     */
    public void setJgdm(String jgdm) {
        this.jgdm = jgdm;
    }

    /**
     * 获取机构名称
     *
     * @return jgmc - 机构名称
     */
    public String getJgmc() {
        return jgmc;
    }

    /**
     * 设置机构名称
     *
     * @param jgmc 机构名称
     */
    public void setJgmc(String jgmc) {
        this.jgmc = jgmc;
    }

    /**
     * 获取优惠项id
     *
     * @return reduce_id - 优惠项id
     */
    public String getReduceId() {
        return reduceId;
    }

    /**
     * 设置优惠项id
     *
     * @param reduceId 优惠项id
     */
    public void setReduceId(String reduceId) {
        this.reduceId = reduceId;
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
     * 获取创建人
     *
     * @return cjr - 创建人
     */
    public String getCjr() {
        return cjr;
    }

    /**
     * 设置创建人
     *
     * @param cjr 创建人
     */
    public void setCjr(String cjr) {
        this.cjr = cjr;
    }

    /**
     * 获取创建时间
     *
     * @return cjsj - 创建时间
     */
    public String getCjsj() {
        return cjsj;
    }

    /**
     * 设置创建时间
     *
     * @param cjsj 创建时间
     */
    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    public enum InnerColumn {
        id("id"),
        jgdm("jgdm"),
        jgmc("jgmc"),
        reduceId("reduce_id"),
        operater("operater"),
        operateTime("operate_time"),
        cjr("cjr"),
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