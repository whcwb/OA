package com.ldz.biz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "biz_lc_jl_xy")
public class BizLcJlXy implements Serializable {
    @Id
    private String id;

    /**
     * 练车记录Id
     */
    @Column(name = "lc_jl_id")
    private String lcJlId;

    /**
     * 学员id
     */
    @Column(name = "xy_id")
    private String xyId;

    private String cjr;

    private String cjsj;

    @Column(name = "xy_xm")
    private String xyXm;

    @Column(name = "xy_dh")
    private String xyDh;

    @Column(name = "xy_jx")
    private String xyJx;

    @Column(name = "xy_lc_cs")
    private Integer xyLcCs;
    @Column(name = "xy_cl_bh")
    private String xyClBh;

    private static final long serialVersionUID = 1L;

    public String getXyClBh() {
        return xyClBh;
    }

    public void setXyClBh(String xyClBh) {
        this.xyClBh = xyClBh;
    }

    public String getXyJx() {
        return xyJx;
    }

    public void setXyJx(String xyJx) {
        this.xyJx = xyJx;
    }

    public Integer getXyLcCs() {
        return xyLcCs;
    }

    public void setXyLcCs(Integer xyLcCs) {
        this.xyLcCs = xyLcCs;
    }

    public String getXyDh() {
        return xyDh;
    }

    public void setXyDh(String xyDh) {
        this.xyDh = xyDh;
    }

    public String getXyXm() {
        return xyXm;
    }

    public void setXyXm(String xyXm) {
        this.xyXm = xyXm;
    }

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

    /**
     * 获取练车记录Id
     *
     * @return lc_jl_id - 练车记录Id
     */
    public String getLcJlId() {
        return lcJlId;
    }

    /**
     * 设置练车记录Id
     *
     * @param lcJlId 练车记录Id
     */
    public void setLcJlId(String lcJlId) {
        this.lcJlId = lcJlId;
    }

    /**
     * 获取学员id
     *
     * @return xy_id - 学员id
     */
    public String getXyId() {
        return xyId;
    }

    /**
     * 设置学员id
     *
     * @param xyId 学员id
     */
    public void setXyId(String xyId) {
        this.xyId = xyId;
    }

    /**
     * @return cjr
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
     * @return cjsj
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

    public enum InnerColumn {
        id("id"),
        lcJlId("lc_jl_id"),
        xyId("xy_id"),
        cjr("cjr"),
        cjsj("cjsj"),
        xyJx("xy_jx"),
        xyLcCs("xy_lc_cs");

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