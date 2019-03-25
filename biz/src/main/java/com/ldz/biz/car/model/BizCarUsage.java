package com.ldz.biz.car.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 车辆使用人表
 */
@Table(name = "biz_car_usage")
public class BizCarUsage implements Serializable {
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
     * 使用人姓名
     */
    @Column(name = "syr_name")
    private String syrName;

    /**
     * 使用人编号
     */
    @Column(name = "syr_id")
    private String syrId;

    /**
     * 类型
     */
    private String lx;

    /**
     * 使用人所在地
     */
    @Column(name = "syr_szd")
    private String syrSzd;

    /**
     * 使用人联系方式
     */
    @Column(name = "syr_dn")
    private String syrDn;

    /**
     * 逻辑删除状态 [ZDCLK1040] 1未删除 0已经删除 
     */
    @Column(name = "del_type")
    private String delType;

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
     * 获取使用人姓名
     *
     * @return syr_name - 使用人姓名
     */
    public String getSyrName() {
        return syrName;
    }

    /**
     * 设置使用人姓名
     *
     * @param syrName 使用人姓名
     */
    public void setSyrName(String syrName) {
        this.syrName = syrName;
    }

    /**
     * 获取使用人编号
     *
     * @return syr_id - 使用人编号
     */
    public String getSyrId() {
        return syrId;
    }

    /**
     * 设置使用人编号
     *
     * @param syrId 使用人编号
     */
    public void setSyrId(String syrId) {
        this.syrId = syrId;
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
     * 获取使用人所在地
     *
     * @return syr_szd - 使用人所在地
     */
    public String getSyrSzd() {
        return syrSzd;
    }

    /**
     * 设置使用人所在地
     *
     * @param syrSzd 使用人所在地
     */
    public void setSyrSzd(String syrSzd) {
        this.syrSzd = syrSzd;
    }

    /**
     * 获取使用人联系方式
     *
     * @return syr_dn - 使用人联系方式
     */
    public String getSyrDn() {
        return syrDn;
    }

    /**
     * 设置使用人联系方式
     *
     * @param syrDn 使用人联系方式
     */
    public void setSyrDn(String syrDn) {
        this.syrDn = syrDn;
    }

    /**
     * 获取逻辑删除状态 [ZDCLK1040] 1未删除 0已经删除 
     *
     * @return del_type - 逻辑删除状态 [ZDCLK1040] 1未删除 0已经删除 
     */
    public String getDelType() {
        return delType;
    }

    /**
     * 设置逻辑删除状态 [ZDCLK1040] 1未删除 0已经删除 
     *
     * @param delType 逻辑删除状态 [ZDCLK1040] 1未删除 0已经删除 
     */
    public void setDelType(String delType) {
        this.delType = delType;
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
        syrName("syr_name"),
        syrId("syr_id"),
        lx("lx"),
        syrSzd("syr_szd"),
        syrDn("syr_dn"),
        delType("del_type"),
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