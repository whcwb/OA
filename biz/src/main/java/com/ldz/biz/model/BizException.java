package com.ldz.biz.model;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "biz_exception")
public class BizException implements Serializable {
    @Id
    private String id;

    /**
     * 学员姓名
     */
    private String xm;

    /**
     * 身份证号
     */
    private String sfzmhm;

    /**
     * 流水号
     */
    private String lsh;

    /**
     * 异常代码
     */
    private String code;

    /**
     * 考试科目
     */
    private String kskm;

    /**
     * 异常描述
     */
    private String bz;

    /**
     * 创建时间
     */
    private String cjsj;

    /**
     * 创建人
     */
    private String cjr;

    /**
     * 异常状态.00:未处理；10:已处理
     */
    private String zt;

    /**
     * 处理时间
     */
    private String gxsj;

    /**
     * 处理人
     */
    private String gxr;

    /**
     * 备注1
     */
    private String bz1;

    /**
     * 备注2
     */
    private String bz2;

    private String zjcx;

    private String bmsj;

    private String xyid;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    public String getXyid() {
        return xyid;
    }

    public void setXyid(String xyid) {
        this.xyid = xyid;
    }

    public String getZjcx() {
        return zjcx;
    }

    public void setZjcx(String zjcx) {
        this.zjcx = zjcx;
    }

    public String getBmsj() {
        return bmsj;
    }

    public void setBmsj(String bmsj) {
        this.bmsj = bmsj;
    }

    /**
     * 获取学员姓名
     *
     * @return xm - 学员姓名
     */
    public String getXm() {
        return xm;
    }

    /**
     * 设置学员姓名
     *
     * @param xm 学员姓名
     */
    public void setXm(String xm) {
        this.xm = xm;
    }

    /**
     * 获取身份证号
     *
     * @return sfzmhm - 身份证号
     */
    public String getSfzmhm() {
        return sfzmhm;
    }

    /**
     * 设置身份证号
     *
     * @param sfzmhm 身份证号
     */
    public void setSfzmhm(String sfzmhm) {
        this.sfzmhm = sfzmhm;
    }

    /**
     * 获取流水号
     *
     * @return lsh - 流水号
     */
    public String getLsh() {
        return lsh;
    }

    /**
     * 设置流水号
     *
     * @param lsh 流水号
     */
    public void setLsh(String lsh) {
        this.lsh = lsh;
    }

    /**
     * 获取异常代码
     *
     * @return code - 异常代码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置异常代码
     *
     * @param code 异常代码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取考试科目
     *
     * @return kskm - 考试科目
     */
    public String getKskm() {
        return kskm;
    }

    /**
     * 设置考试科目
     *
     * @param kskm 考试科目
     */
    public void setKskm(String kskm) {
        this.kskm = kskm;
    }

    /**
     * 获取异常描述
     *
     * @return bz - 异常描述
     */
    public String getBz() {
        return bz;
    }

    /**
     * 设置异常描述
     *
     * @param bz 异常描述
     */
    public void setBz(String bz) {
        this.bz = bz;
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
     * 获取异常状态.00:未处理；10:已处理
     *
     * @return zt - 异常状态.00:未处理；10:已处理
     */
    public String getZt() {
        return zt;
    }

    /**
     * 设置异常状态.00:未处理；10:已处理
     *
     * @param zt 异常状态.00:未处理；10:已处理
     */
    public void setZt(String zt) {
        this.zt = zt;
    }

    /**
     * 获取处理时间
     *
     * @return gxsj - 处理时间
     */
    public String getGxsj() {
        return gxsj;
    }

    /**
     * 设置处理时间
     *
     * @param gxsj 处理时间
     */
    public void setGxsj(String gxsj) {
        this.gxsj = gxsj;
    }

    /**
     * 获取处理人
     *
     * @return gxr - 处理人
     */
    public String getGxr() {
        return gxr;
    }

    /**
     * 设置处理人
     *
     * @param gxr 处理人
     */
    public void setGxr(String gxr) {
        this.gxr = gxr;
    }

    /**
     * 获取备注1
     *
     * @return bz1 - 备注1
     */
    public String getBz1() {
        return bz1;
    }

    /**
     * 设置备注1
     *
     * @param bz1 备注1
     */
    public void setBz1(String bz1) {
        this.bz1 = bz1;
    }

    /**
     * 获取备注2
     *
     * @return bz2 - 备注2
     */
    public String getBz2() {
        return bz2;
    }

    /**
     * 设置备注2
     *
     * @param bz2 备注2
     */
    public void setBz2(String bz2) {
        this.bz2 = bz2;
    }

    public enum InnerColumn {
        id("id"),
        xm("xm"),
        sfzmhm("sfzmhm"),
        lsh("lsh"),
        code("code"),
        kskm("kskm"),
        bz("bz"),
        cjsj("cjsj"),
        cjr("cjr"),
        zt("zt"),
        gxsj("gxsj"),
        gxr("gxr"),
        bz1("bz1"),
        bz2("bz2");

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