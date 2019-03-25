package com.ldz.biz.model;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

@Table(name = "biz_lc_fd")
public class BizLcFd implements Serializable {
    @Id
    private String id;

    /**
     * 返点时间
     */
    private String cjsj;

    /**
     * 返点人
     */
    private String cjr;

    /**
     * 返点总金额
     */
    private Integer fdje;

    /**
     * 返点数量
     */
    private Integer fdsl;

    @Transient
    private List<BizLcJl> jlList;

    private static final long serialVersionUID = 1L;


    public List<BizLcJl> getJlList() {
        return jlList;
    }

    public void setJlList(List<BizLcJl> jlList) {
        this.jlList = jlList;
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
     * 获取返点时间
     *
     * @return cjsj - 返点时间
     */
    public String getCjsj() {
        return cjsj;
    }

    /**
     * 设置返点时间
     *
     * @param cjsj 返点时间
     */
    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    /**
     * 获取返点人
     *
     * @return cjr - 返点人
     */
    public String getCjr() {
        return cjr;
    }

    /**
     * 设置返点人
     *
     * @param cjr 返点人
     */
    public void setCjr(String cjr) {
        this.cjr = cjr;
    }

    /**
     * 获取返点总金额
     *
     * @return fdje - 返点总金额
     */
    public Integer getFdje() {
        return fdje;
    }

    /**
     * 设置返点总金额
     *
     * @param fdje 返点总金额
     */
    public void setFdje(Integer fdje) {
        this.fdje = fdje;
    }

    /**
     * 获取返点数量
     *
     * @return fdsl - 返点数量
     */
    public Integer getFdsl() {
        return fdsl;
    }

    /**
     * 设置返点数量
     *
     * @param fdsl 返点数量
     */
    public void setFdsl(Integer fdsl) {
        this.fdsl = fdsl;
    }

    public enum InnerColumn {
        id("id"),
        cjsj("cjsj"),
        cjr("cjr"),
        fdje("fdje"),
        fdsl("fdsl");

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