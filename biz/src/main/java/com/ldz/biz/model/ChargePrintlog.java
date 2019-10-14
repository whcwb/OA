package com.ldz.biz.model;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "charge_printlog")
public class ChargePrintlog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 票据编号
     */
    private String pjbh;

    /**
     * 创建时间
     */
    private String cjsj;

    /**
     * 创建人
     */
    private String cjr;

    /**
     * 作废时间
     */
    private String zfsj;

    /**
     * 作废人
     */
    private String zfr;

    /**
     * 收费唯一标识
     */
    @Column(name = "charge_id")
    private String chargeId;

    private String jgdm;

    private String jgmc;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取票据编号
     *
     * @return pjbh - 票据编号
     */
    public String getPjbh() {
        return pjbh;
    }

    /**
     * 设置票据编号
     *
     * @param pjbh 票据编号
     */
    public void setPjbh(String pjbh) {
        this.pjbh = pjbh;
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
     * 获取作废时间
     *
     * @return zfsj - 作废时间
     */
    public String getZfsj() {
        return zfsj;
    }

    /**
     * 设置作废时间
     *
     * @param zfsj 作废时间
     */
    public void setZfsj(String zfsj) {
        this.zfsj = zfsj;
    }

    /**
     * 获取作废人
     *
     * @return zfr - 作废人
     */
    public String getZfr() {
        return zfr;
    }

    /**
     * 设置作废人
     *
     * @param zfr 作废人
     */
    public void setZfr(String zfr) {
        this.zfr = zfr;
    }

    /**
     * 获取收费唯一标识
     *
     * @return charge_id - 收费唯一标识
     */
    public String getChargeId() {
        return chargeId;
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

    /**
     * 设置收费唯一标识
     *
     * @param chargeId 收费唯一标识
     */
    public void setChargeId(String chargeId) {
        this.chargeId = chargeId;
    }

    public enum InnerColumn {
        id("id"),
        pjbh("pjbh"),
        cjsj("cjsj"),
        cjr("cjr"),
        zfsj("zfsj"),
        zfr("zfr"),
        chargeId("charge_id"),
        jgdm("jgdm"),
        jgmc("jgmc");

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