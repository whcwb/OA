package com.ldz.biz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "biz_lc_wxjl")
public class BizLcWxjl implements Serializable {
    @Id
    private String id;

    /**
     * 教练姓名
     */
    @Column(name = "jl_xm")
    private String jlXm;

    /**
     * 教练所属驾校
     */
    @Column(name = "jl_jx")
    private String jlJx;

    private String cjr;

    private String cjsj;

    @Column(name = "jl_lxdh")
    private String jlLxdh;

    private static final long serialVersionUID = 1L;

    public String getJlLxdh() {
        return jlLxdh;
    }

    public void setJlLxdh(String jlLxdh) {
        this.jlLxdh = jlLxdh;
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
     * 获取教练姓名
     *
     * @return jl_xm - 教练姓名
     */
    public String getJlXm() {
        return jlXm;
    }

    /**
     * 设置教练姓名
     *
     * @param jlXm 教练姓名
     */
    public void setJlXm(String jlXm) {
        this.jlXm = jlXm;
    }

    /**
     * 获取教练所属驾校
     *
     * @return jl_jx - 教练所属驾校
     */
    public String getJlJx() {
        return jlJx;
    }

    /**
     * 设置教练所属驾校
     *
     * @param jlJx 教练所属驾校
     */
    public void setJlJx(String jlJx) {
        this.jlJx = jlJx;
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
        jlXm("jl_xm"),
        jlJx("jl_jx"),
        cjr("cjr"),
        cjsj("cjsj"),
        jlLxdh("jl_lxdh");

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