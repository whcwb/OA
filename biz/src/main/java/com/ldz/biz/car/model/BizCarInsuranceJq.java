package com.ldz.biz.car.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * 车辆保险表
 */
@Table(name = "biz_car_insurance_jq")
public class BizCarInsuranceJq implements Serializable {
    /**
     * id
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 车辆id
     */
    @Column(name = "cl_id")
    private String clId;

    /**
     * 车牌号
     */
    private String cph;

    /**
     * 保险日期
     */
    private String bxrq;

    /**
     * 保险至
     */
    private String bxz;

    /**
     * 保险电话
     */
    private String bxdh;

    /**
     * 保单编号
     */
    private String bdzbbh;

    /**
     * 保单副本编号
     */
    private String bdfbbh;

    /**
     * 保单位置
     */
    @Column(name = "bd_wz")
    private String bdWz;

    /**
     * 备注
     */
    private String bz;

    /**
     * 保单数量
     */
    @Column(name = "bd_count")
    private String bdCount;

    /**
     * 创建人
     */
    private String cjr;

    /**
     * 创建时间
     */
    private String cjsj;

    /**
     * 保险联系人
     */
    private String bxlxr;
    /**
     * 保单电子档案路径
     */
    @Column(name = "da_file")
    private String  propertyFileurl;

    /**
     * 投保公司
     */
    private String tbgs;
    /**
     * 经办人
     */
    private String jbr;
    /**
     * 经办人
     */
    @Column(name = "jbr_dn")
    private String jbrDn;
    /**
     * 告警ID
     */
    @Transient
    private String warnId;

    /**
     * 下次年审时间
     */
    @Transient
    private String xcncsj;

    private static final long serialVersionUID = 1L;

    public String getXcncsj() {
        return xcncsj;
    }

    public void setXcncsj(String xcncsj) {
        this.xcncsj = xcncsj;
    }

    public String getJbrDn() {
        return jbrDn;
    }

    public void setJbrDn(String jbrDn) {
        this.jbrDn = jbrDn;
    }

    public String getJbr() {
        return jbr;
    }

    public void setJbr(String jbr) {
        this.jbr = jbr;
    }

    public String getTbgs() {
        return tbgs;
    }

    public void setTbgs(String tbgs) {
        this.tbgs = tbgs;
    }

    public String getPropertyFileurl() {
        return propertyFileurl;
    }

    public void setPropertyFileurl(String propertyFileurl) {
        this.propertyFileurl = propertyFileurl;
    }

    public String getBxlxr() {
        return bxlxr;
    }

    public void setBxlxr(String bxlxr) {
        this.bxlxr = bxlxr;
    }

    public String getWarnId() {
        return warnId;
    }

    public void setWarnId(String warnId) {
        this.warnId = warnId;
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
     * 获取车辆id
     *
     * @return cl_id - 车辆id
     */
    public String getClId() {
        return clId;
    }

    /**
     * 设置车辆id
     *
     * @param clId 车辆id
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
     * 获取保险日期
     *
     * @return bxrq - 保险日期
     */
    public String getBxrq() {
        return bxrq;
    }

    /**
     * 设置保险日期
     *
     * @param bxrq 保险日期
     */
    public void setBxrq(String bxrq) {
        this.bxrq = bxrq;
    }

    /**
     * 获取保险至
     *
     * @return bxz - 保险至
     */
    public String getBxz() {
        return bxz;
    }

    /**
     * 设置保险至
     *
     * @param bxz 保险至
     */
    public void setBxz(String bxz) {
        this.bxz = bxz;
    }

    /**
     * 获取保险电话
     *
     * @return bxdh - 保险电话
     */
    public String getBxdh() {
        return bxdh;
    }

    /**
     * 设置保险电话
     *
     * @param bxdh 保险电话
     */
    public void setBxdh(String bxdh) {
        this.bxdh = bxdh;
    }

    /**
     * 获取保单正本编号
     *
     * @return bdzbbh - 保单正本编号
     */
    public String getBdzbbh() {
        return bdzbbh;
    }

    /**
     * 设置保单正本编号
     *
     * @param bdzbbh 保单正本编号
     */
    public void setBdzbbh(String bdzbbh) {
        this.bdzbbh = bdzbbh;
    }

    /**
     * 获取保单副本编号
     *
     * @return bdfbbh - 保单副本编号
     */
    public String getBdfbbh() {
        return bdfbbh;
    }

    /**
     * 设置保单副本编号
     *
     * @param bdfbbh 保单副本编号
     */
    public void setBdfbbh(String bdfbbh) {
        this.bdfbbh = bdfbbh;
    }

    /**
     * 获取保单位置
     *
     * @return bd_wz - 保单位置
     */
    public String getBdWz() {
        return bdWz;
    }

    /**
     * 设置保单位置
     *
     * @param bdWz 保单位置
     */
    public void setBdWz(String bdWz) {
        this.bdWz = bdWz;
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
     * 获取保单数量
     *
     * @return bd_count - 保单数量
     */
    public String getBdCount() {
        return bdCount;
    }

    /**
     * 设置保单数量
     *
     * @param bdCount 保单数量
     */
    public void setBdCount(String bdCount) {
        this.bdCount = bdCount;
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
        bxrq("bxrq"),
        bxz("bxz"),
        bxdh("bxdh"),
        bdzbbh("bdzbbh"),
        bdfbbh("bdfbbh"),
        bdWz("bd_wz"),
        bz("bz"),
        bdCount("bd_count"),
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