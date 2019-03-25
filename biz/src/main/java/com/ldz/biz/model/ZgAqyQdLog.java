package com.ldz.biz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "zg_aqy_qd_log")
public class ZgAqyQdLog implements Serializable {
    /**
     * 唯一标识
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 姓名
     */
    @Column(name = "XM")
    private String xm;

    private String jgdm;

    private String jgmc;

    /**
     * 安全员签到状态(字典[aqyzt] 00未签到 10已签到)
     */
    @Column(name = "aqy_qdzt")
    private String aqyQdzt;

    /**
     * 安全员签到时间
     */
    @Column(name = "aqy_qdsj")
    private String aqyQdsj;

    /**
     * 创建时间
     */
    @Column(name = "CJSJ")
    private String cjsj;

    /**
     * 备注
     */
    @Column(name = "BZ")
    private String bz;

    /**
     * 创建人
     */
    @Column(name = "CJR")
    private String cjr;
    /**
     * 科目
     */
    private String km;

    private static final long serialVersionUID = 1L;

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    /**
     * 获取唯一标识
     *
     * @return ID - 唯一标识
     */
    public String getId() {
        return id;
    }

    /**
     * 设置唯一标识
     *
     * @param id 唯一标识
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取姓名
     *
     * @return XM - 姓名
     */
    public String getXm() {
        return xm;
    }

    /**
     * 设置姓名
     *
     * @param xm 姓名
     */
    public void setXm(String xm) {
        this.xm = xm;
    }

    /**
     * @return jgdm
     */
    public String getJgdm() {
        return jgdm;
    }

    /**
     * @param jgdm
     */
    public void setJgdm(String jgdm) {
        this.jgdm = jgdm;
    }

    /**
     * @return jgmc
     */
    public String getJgmc() {
        return jgmc;
    }

    /**
     * @param jgmc
     */
    public void setJgmc(String jgmc) {
        this.jgmc = jgmc;
    }

    /**
     * 获取安全员签到状态(字典[aqyzt] 00未签到 10已签到)
     *
     * @return aqy_qdzt - 安全员签到状态(字典[aqyzt] 00未签到 10已签到)
     */
    public String getAqyQdzt() {
        return aqyQdzt;
    }

    /**
     * 设置安全员签到状态(字典[aqyzt] 00未签到 10已签到)
     *
     * @param aqyQdzt 安全员签到状态(字典[aqyzt] 00未签到 10已签到)
     */
    public void setAqyQdzt(String aqyQdzt) {
        this.aqyQdzt = aqyQdzt;
    }

    /**
     * 获取安全员签到时间
     *
     * @return aqy_qdsj - 安全员签到时间
     */
    public String getAqyQdsj() {
        return aqyQdsj;
    }

    /**
     * 设置安全员签到时间
     *
     * @param aqyQdsj 安全员签到时间
     */
    public void setAqyQdsj(String aqyQdsj) {
        this.aqyQdsj = aqyQdsj;
    }

    /**
     * 获取创建时间
     *
     * @return CJSJ - 创建时间
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
     * 获取备注
     *
     * @return BZ - 备注
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
     * @return CJR - 创建人
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

    public enum InnerColumn {
        id("ID"),
        xm("XM"),
        jgdm("jgdm"),
        jgmc("jgmc"),
        aqyQdzt("aqy_qdzt"),
        aqyQdsj("aqy_qdsj"),
        cjsj("CJSJ"),
        bz("BZ"),
        cjr("CJR");

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