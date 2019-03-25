package com.ldz.biz.car.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * 油改气记录表
 */
@Table(name = "biz_car_gas")
public class BizCarGas implements Serializable {
    /**
     * id
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 车辆ID
     */
    @Column(name = "cl_id")
    private String clId;

    /**
     * 车牌号
     */
    private String cph;

    /**
     * 改气时间
     */
    @Column(name = "gas_data")
    private String gasData;
    /**
     * 改气地点
     */
    @Column(name = "gas_dd")
    private String gasDd;
    /**
     * 备注
     */
    private String bz;


    /**
     * 创建人
     */
    private String cjr;

    /**
     * 创建时间
     */
    private String cjsj;
    /**
     * 联系人
     */
    private String lxr;
    /**
     * 改气合格证有效期
     */
    @Column(name = "gx_gas_ggzyxq")
    private String gxGasGgzyxq;
    /**
     * 联系人电话
     */
    @Column(name = "lx_dn")
    private String lxDn;

    /**
     * 下次年审时间
     */
    @Transient
    private String xcncsj;
    private static final long serialVersionUID = 1L;

    public String getGxGasGgzyxq() {
        return gxGasGgzyxq;
    }

    public void setGxGasGgzyxq(String gxGasGgzyxq) {
        this.gxGasGgzyxq = gxGasGgzyxq;
    }

    public String getGasData() {
        return gasData;
    }

    public void setGasData(String gasData) {
        this.gasData = gasData;
    }

    public String getGasDd() {
        return gasDd;
    }

    public void setGasDd(String gasDd) {
        this.gasDd = gasDd;
    }

    public String getLxr() {
        return lxr;
    }

    public void setLxr(String lxr) {
        this.lxr = lxr;
    }

    public String getLxDn() {
        return lxDn;
    }

    public void setLxDn(String lxDn) {
        this.lxDn = lxDn;
    }

    public String getXcncsj() {
        return xcncsj;
    }

    public void setXcncsj(String xcncsj) {
        this.xcncsj = xcncsj;
    }

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
     * 获取车辆ID
     *
     * @return cl_id - 车辆ID
     */
    public String getClId() {
        return clId;
    }

    /**
     * 设置车辆ID
     *
     * @param clId 车辆ID
     */
    public void setClId(String clId) {
        this.clId = clId;
    }

    /**
     * 获取车牌号
     *
     * @return cph - 车牌号
     */
    public String getCph() {
        return cph;
    }

    /**
     * 设置车牌号
     *
     * @param cph 车牌号
     */
    public void setCph(String cph) {
        this.cph = cph;
    }


    /**
     * 获取备注
     *
     * @return bz - 备注
     */
    public String getBz() {
        return bz;
    }

    /**
     * 设置备注
     *
     * @param bz 备注
     */
    public void setBz(String bz) {
        this.bz = bz;
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
        clId("cl_id"),
        cph("cph"),
        yszh("yszh"),
        bz("bz"),
        jgdm("jgdm"),
        jgmc("jgmc"),
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