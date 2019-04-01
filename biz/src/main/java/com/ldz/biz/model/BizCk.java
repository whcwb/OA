package com.ldz.biz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

@Table(name = "biz_ck")
public class BizCk implements Serializable {
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 领取人
     */
    @Column(name = "LQR")
    private String lqr;

    @Column(name = "CJR")
    private String cjr;

    @Column(name = "CJSJ")
    private String cjsj;

    /**
     * 出库数量
     */
    @Column(name = "LQ_SL")
    private Integer lqSl;

    /**
     * 备注
     */
    @Column(name = "BZ")
    private String bz;

    /**
     * 库存id
     */
    @Column(name = "KC_ID")
    private String kcId;

    /**
     * 领用部门代码
     */
    private String jgdm;

    /**
     * 领用部门名称
     */
    private  String jgmc;

    @Transient
    private BizKc kc;


    private static final long serialVersionUID = 1L;

    public String getJgdm() {
        return jgdm;
    }

    public BizKc getKc() {
        return kc;
    }

    public void setKc(BizKc kc) {
        this.kc = kc;
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

    public String getKcId() {
        return kcId;
    }

    public void setKcId(String kcId) {
        this.kcId = kcId;
    }

    /**
     * @return ID
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

    /**
     * 获取领取人
     *
     * @return LQR - 领取人
     */
    public String getLqr() {
        return lqr;
    }

    /**
     * 设置领取人
     *
     * @param lqr 领取人
     */
    public void setLqr(String lqr) {
        this.lqr = lqr;
    }

    /**
     * @return CJR
     */
    public String getCjr() {
        return cjr;
    }

    /**
     * @param cjr
     */
    public void setCjr(String cjr) {
        this.cjr = cjr;
    }

    /**
     * @return CJSJ
     */
    public String getCjsj() {
        return cjsj;
    }

    /**
     * @param cjsj
     */
    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    /**
     * 获取出库数量
     *
     * @return LQ_SL - 出库数量
     */
    public Integer getLqSl() {
        return lqSl;
    }

    /**
     * 设置出库数量
     *
     * @param lqSl 出库数量
     */
    public void setLqSl(Integer lqSl) {
        this.lqSl = lqSl;
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

    public enum InnerColumn {
        id("ID"),
        lqr("LQR"),
        cjr("CJR"),
        cjsj("CJSJ"),
        lqSl("LQ_SL"),
        bz("BZ"),
        kcId("KC_ID");

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