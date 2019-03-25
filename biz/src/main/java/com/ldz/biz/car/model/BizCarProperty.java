package com.ldz.biz.car.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 车辆产权明细表
 */
@Table(name = "biz_car_property")
public class BizCarProperty implements Serializable {
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
     * 类型  保留字段暂时没有使用。
     */
    private String lx;

    /**
     * 车款
     */
    private Float ck;

    /**
     * 质保
     */
    private String zb;

    /**
     * 异动次数
     */
    private String yd;

    /**
     * 原产权人
     */
    @Column(name = "past_cqr")
    private String pastCqr;

    /**
     * 原产权人电话
     */
    @Column(name = "past_cqr_dn")
    private String pastCqrDn;

    /**
     * 原质保金
     */
    @Column(name = "past_zb")
    private String pastZb;
    /**
     * 原车款
     */
    @Column(name = "past_ck")
    private String pastCk;
    /**
     * 原备注
     */
    @Column(name = "past_bz")
    private String pastBz;
    /**
     * 产权人
     */
    private String cqr;

    /**
     * 产权人所在地
     */
    @Column(name = "cqr_szd")
    private String cqrSzd;

    /**
     * 产权人联系方式
     */
    @Column(name = "cqr_dn")
    private String cqrDn;

    /**
     * 产权人证件号
     */
    @Column(name = "cqr_code")
    private String cqrCode;

    /**
     * 机构代码
     */
    private String jgdm;

    /**
     * 机构名称
     */
    private String jgmc;

    /**
     * 创建人
     */
    private String cjr;

    /**
     * 创建时间
     */
    private String cjsj;

    /**
     * 备注
     */
    private String bz;


    /**
     * 产权状态 1、内部产权变更 2、学牌转非学牌 3、车辆报废 4、车辆售卖
     */
    @Column(name = "property_type")
    private String propertyType;

    /**
     * 经办人
     */
    @Column(name = "jbr")
    private String jbr;
    /**
     * 经办人电话
     */
    @Column(name = "jbr_dn")
    private String jbrDn;
    /**
     * 产权协议电子档案
     */
    @Column(name = "property_fileurl")
    private String propertyFileurl;


    private static final long serialVersionUID = 1L;

    public String getPastCk() {
        return pastCk;
    }

    public void setPastCk(String pastCk) {
        this.pastCk = pastCk;
    }

    public String getPastCqrDn() {
        return pastCqrDn;
    }

    public void setPastCqrDn(String pastCqrDn) {
        this.pastCqrDn = pastCqrDn;
    }

    public String getPastZb() {
        return pastZb;
    }

    public void setPastZb(String pastZb) {
        this.pastZb = pastZb;
    }

    public String getPastBz() {
        return pastBz;
    }

    public void setPastBz(String pastBz) {
        this.pastBz = pastBz;
    }

    public String getPropertyFileurl() {
        return propertyFileurl;
    }

    public void setPropertyFileurl(String propertyFileurl) {
        this.propertyFileurl = propertyFileurl;
    }

    public String getJbr() {
        return jbr;
    }

    public void setJbr(String jbr) {
        this.jbr = jbr;
    }

    public String getJbrDn() {
        return jbrDn;
    }

    public void setJbrDn(String jbrDn) {
        this.jbrDn = jbrDn;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getPastCqr() {
        return pastCqr;
    }

    public void setPastCqr(String pastCqr) {
        this.pastCqr = pastCqr;
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
     * 获取类型
     *
     * @return lx - 类型
     */
    public String getLx() {
        return lx;
    }

    /**
     * 设置类型
     *
     * @param lx 类型
     */
    public void setLx(String lx) {
        this.lx = lx;
    }

    /**
     * 获取车款
     *
     * @return ck - 车款
     */
    public Float getCk() {
        return ck;
    }

    /**
     * 设置车款
     *
     * @param ck 车款
     */
    public void setCk(Float ck) {
        this.ck = ck;
    }

    /**
     * 获取质保
     *
     * @return zb - 质保
     */
    public String getZb() {
        return zb;
    }

    /**
     * 设置质保
     *
     * @param zb 质保
     */
    public void setZb(String zb) {
        this.zb = zb;
    }

    /**
     * 获取异动次数
     *
     * @return yd - 异动次数
     */
    public String getYd() {
        return yd;
    }

    /**
     * 设置异动次数
     *
     * @param yd 异动次数
     */
    public void setYd(String yd) {
        this.yd = yd;
    }

    /**
     * 获取产权人
     *
     * @return cqr - 产权人
     */
    public String getCqr() {
        return cqr;
    }

    /**
     * 设置产权人
     *
     * @param cqr 产权人
     */
    public void setCqr(String cqr) {
        this.cqr = cqr;
    }

    /**
     * 获取产权人所在地
     *
     * @return cqr_szd - 产权人所在地
     */
    public String getCqrSzd() {
        return cqrSzd;
    }

    /**
     * 设置产权人所在地
     *
     * @param cqrSzd 产权人所在地
     */
    public void setCqrSzd(String cqrSzd) {
        this.cqrSzd = cqrSzd;
    }

    /**
     * 获取产权人联系方式
     *
     * @return cqr_dn - 产权人联系方式
     */
    public String getCqrDn() {
        return cqrDn;
    }

    /**
     * 设置产权人联系方式
     *
     * @param cqrDn 产权人联系方式
     */
    public void setCqrDn(String cqrDn) {
        this.cqrDn = cqrDn;
    }

    /**
     * 获取产权人证件号
     *
     * @return cqr_code - 产权人证件号
     */
    public String getCqrCode() {
        return cqrCode;
    }

    /**
     * 设置产权人证件号
     *
     * @param cqrCode 产权人证件号
     */
    public void setCqrCode(String cqrCode) {
        this.cqrCode = cqrCode;
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
        lx("lx"),
        ck("ck"),
        zb("zb"),
        yd("yd"),
        cqr("cqr"),
        cqrSzd("cqr_szd"),
        cqrDn("cqr_dn"),
        cqrCode("cqr_code"),
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