package com.ldz.sys.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "SYS_PTRZ")
public class SysRz implements Serializable {
    @Id
    @Column(name = "RZ_ID")
    private String rzId;

    @Column(name = "CZLX")
    private String czlx;

    @Column(name = "CZSJ")
    private Date czsj;

    @Column(name = "CZR")
    private String czr;

    @Column(name = "DX_ID")
    private String dxId;

    @Column(name = "DXLX")
    private String dxlx;

    @Column(name = "CS")
    private String cs;

    @Column(name = "JG")
    private String jg;

    @Column(name = "ZXSJ")
    private Integer zxsj;

    @Column(name = "SM")
    private String sm;

    @Column(name = "FF")
    private String ff;

    private static final long serialVersionUID = 1L;

    /**
     * @return RZ_ID
     */
    public String getRzId() {
        return rzId;
    }

    /**
     * @param rzId
     */
    public void setRzId(String rzId) {
        this.rzId = rzId;
    }

    /**
     * @return CZLX
     */
    public String getCzlx() {
        return czlx;
    }

    /**
     * @param czlx
     */
    public void setCzlx(String czlx) {
        this.czlx = czlx;
    }

    /**
     * @return CZSJ
     */
    public Date getCzsj() {
        return czsj;
    }

    /**
     * @param czsj
     */
    public void setCzsj(Date czsj) {
        this.czsj = czsj;
    }

    /**
     * @return CZR
     */
    public String getCzr() {
        return czr;
    }

    /**
     * @param czr
     */
    public void setCzr(String czr) {
        this.czr = czr;
    }

    /**
     * @return DX_ID
     */
    public String getDxId() {
        return dxId;
    }

    /**
     * @param dxId
     */
    public void setDxId(String dxId) {
        this.dxId = dxId;
    }

    /**
     * @return DXLX
     */
    public String getDxlx() {
        return dxlx;
    }

    /**
     * @param dxlx
     */
    public void setDxlx(String dxlx) {
        this.dxlx = dxlx;
    }

    /**
     * @return CS
     */
    public String getCs() {
        return cs;
    }

    /**
     * @param cs
     */
    public void setCs(String cs) {
        this.cs = cs;
    }

    /**
     * @return JG
     */
    public String getJg() {
        return jg;
    }

    /**
     * @param jg
     */
    public void setJg(String jg) {
        this.jg = jg;
    }

    /**
     * @return ZXSJ
     */
    public Integer getZxsj() {
        return zxsj;
    }

    /**
     * @param zxsj
     */
    public void setZxsj(Integer zxsj) {
        this.zxsj = zxsj;
    }

    /**
     * @return SM
     */
    public String getSm() {
        return sm;
    }

    /**
     * @param sm
     */
    public void setSm(String sm) {
        this.sm = sm;
    }

    /**
     * @return FF
     */
    public String getFf() {
        return ff;
    }

    /**
     * @param ff
     */
    public void setFf(String ff) {
        this.ff = ff;
    }

    public enum InnerColumn {
        rzId("RZ_ID"),
        czlx("CZLX"),
        czsj("CZSJ"),
        czr("CZR"),
        dxId("DX_ID"),
        dxlx("DXLX"),
        cs("CS"),
        jg("JG"),
        zxsj("ZXSJ"),
        sm("SM"),
        ff("FF");

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