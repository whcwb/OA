package com.ldz.biz.car.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 车辆所有人表
 */
@Table(name = "biz_car_syr")
public class BizCarSyr implements Serializable {
    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 车主名称
     */
    @Column(name = "syr_mc")
    private String syrMc;

    /**
     * 经营许可证号
     */
    private String jyxkzh;

    /**
     * 经济类型
     */
    private String jjlx;

    /**
     * 经营组织方式
     */
    private String jyzzfs;

    /**
     * 资质等级
     */
    private String zzdj;

    /**
     * 经营范围
     */
    private String yjfw;

    /**
     * 道路运输证号
     */
    private String dlyszh;

    /**
     * 二级维护周期
     */
    private String whzq;

    /**
     * 营业执照地址
     */
    @Column(name = "yyzz_url")
    private String yyzzUrl;

    /**
     * 法人证件正面
     */
    @Column(name = "frz_zm_url")
    private String frzZmUrl;

    /**
     * 法人证件背面
     */
    @Column(name = "frz_bm_url")
    private String frzBmUrl;

    /**
     * 委托人证件正面
     */
    @Column(name = "wtrzj_zm_url")
    private String wtrzjZmUrl;

    /**
     * 委托人证件背面
     */
    @Column(name = "wtrzj_bm_url")
    private String wtrzjBmUrl;
    /**
     * 委托人签名
     */
    @Column(name = "wtrzj_qm_url")
    private String wtrzjQmUrl;


    private static final long serialVersionUID = 1L;

    public String getWtrzjQmUrl() {
        return wtrzjQmUrl;
    }

    public void setWtrzjQmUrl(String wtrzjQmUrl) {
        this.wtrzjQmUrl = wtrzjQmUrl;
    }

    public String getWtrzjZmUrl() {
        return wtrzjZmUrl;
    }

    public void setWtrzjZmUrl(String wtrzjZmUrl) {
        this.wtrzjZmUrl = wtrzjZmUrl;
    }

    public String getWtrzjBmUrl() {
        return wtrzjBmUrl;
    }

    public void setWtrzjBmUrl(String wtrzjBmUrl) {
        this.wtrzjBmUrl = wtrzjBmUrl;
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
     * 获取车主名称
     *
     * @return syr_mc - 车主名称
     */
    public String getSyrMc() {
        return syrMc;
    }

    /**
     * 设置车主名称
     *
     * @param syrMc 车主名称
     */
    public void setSyrMc(String syrMc) {
        this.syrMc = syrMc;
    }

    /**
     * 获取经营许可证号
     *
     * @return jyxkzh - 经营许可证号
     */
    public String getJyxkzh() {
        return jyxkzh;
    }

    /**
     * 设置经营许可证号
     *
     * @param jyxkzh 经营许可证号
     */
    public void setJyxkzh(String jyxkzh) {
        this.jyxkzh = jyxkzh;
    }

    /**
     * 获取经济类型
     *
     * @return jjlx - 经济类型
     */
    public String getJjlx() {
        return jjlx;
    }

    /**
     * 设置经济类型
     *
     * @param jjlx 经济类型
     */
    public void setJjlx(String jjlx) {
        this.jjlx = jjlx;
    }

    /**
     * 获取经营组织方式
     *
     * @return jyzzfs - 经营组织方式
     */
    public String getJyzzfs() {
        return jyzzfs;
    }

    /**
     * 设置经营组织方式
     *
     * @param jyzzfs 经营组织方式
     */
    public void setJyzzfs(String jyzzfs) {
        this.jyzzfs = jyzzfs;
    }

    /**
     * 获取资质等级
     *
     * @return zzdj - 资质等级
     */
    public String getZzdj() {
        return zzdj;
    }

    /**
     * 设置资质等级
     *
     * @param zzdj 资质等级
     */
    public void setZzdj(String zzdj) {
        this.zzdj = zzdj;
    }

    /**
     * 获取经营范围
     *
     * @return yjfw - 经营范围
     */
    public String getYjfw() {
        return yjfw;
    }

    /**
     * 设置经营范围
     *
     * @param yjfw 经营范围
     */
    public void setYjfw(String yjfw) {
        this.yjfw = yjfw;
    }

    /**
     * 获取道路运输证号
     *
     * @return dlyszh - 道路运输证号
     */
    public String getDlyszh() {
        return dlyszh;
    }

    /**
     * 设置道路运输证号
     *
     * @param dlyszh 道路运输证号
     */
    public void setDlyszh(String dlyszh) {
        this.dlyszh = dlyszh;
    }

    /**
     * 获取二级维护周期
     *
     * @return whzq - 二级维护周期
     */
    public String getWhzq() {
        return whzq;
    }

    /**
     * 设置二级维护周期
     *
     * @param whzq 二级维护周期
     */
    public void setWhzq(String whzq) {
        this.whzq = whzq;
    }

    /**
     * 获取营业执照地址
     *
     * @return yyzz_url - 营业执照地址
     */
    public String getYyzzUrl() {
        return yyzzUrl;
    }

    /**
     * 设置营业执照地址
     *
     * @param yyzzUrl 营业执照地址
     */
    public void setYyzzUrl(String yyzzUrl) {
        this.yyzzUrl = yyzzUrl;
    }

    /**
     * 获取法人证件正面
     *
     * @return frz_zm_url - 法人证件正面
     */
    public String getFrzZmUrl() {
        return frzZmUrl;
    }

    /**
     * 设置法人证件正面
     *
     * @param frzZmUrl 法人证件正面
     */
    public void setFrzZmUrl(String frzZmUrl) {
        this.frzZmUrl = frzZmUrl;
    }

    /**
     * 获取法人证件背面
     *
     * @return frz_bm_url - 法人证件背面
     */
    public String getFrzBmUrl() {
        return frzBmUrl;
    }

    /**
     * 设置法人证件背面
     *
     * @param frzBmUrl 法人证件背面
     */
    public void setFrzBmUrl(String frzBmUrl) {
        this.frzBmUrl = frzBmUrl;
    }

    public enum InnerColumn {
        id("id"),
        syrMc("syr_mc"),
        jyxkzh("jyxkzh"),
        jjlx("jjlx"),
        jyzzfs("jyzzfs"),
        zzdj("zzdj"),
        yjfw("yjfw"),
        dlyszh("dlyszh"),
        whzq("whzq"),
        yyzzUrl("yyzz_url"),
        frzZmUrl("frz_zm_url"),
        frzBmUrl("frz_bm_url");

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