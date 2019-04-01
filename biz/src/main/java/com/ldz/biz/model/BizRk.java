package com.ldz.biz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

@Table(name = "biz_rk")
public class BizRk implements Serializable {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "CJSJ")
    private String cjsj;

    @Column(name = "CJR")
    private String cjr;

    /**
     * 库存id
     */
    @Column(name = "KC_ID")
    private String kcId;

    /**
     * 入库数量
     */
    @Column(name = "RK_SL")
    private Integer rkSl;

    /**
     * 备注
     */
    @Column(name = "BZ")
    private String bz;

    /**
     *  入库单价
     */
    @Column(name = "RK_DJ")
    private float rkDj;

    /**
     * 批次编号
     */
    @Column(name = "PC")
    private String pc;


    @Transient
    private List<BizRk> bizRkList;

    @Transient
    private BizKc bizKc;

    @Transient
    private Double total;



    private static final long serialVersionUID = 1L;

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public BizKc getBizKc() {
        return bizKc;
    }

    public void setBizKc(BizKc bizKc) {
        this.bizKc = bizKc;
    }

    public List<BizRk> getBizRkList() {
        return bizRkList;
    }

    public void setBizRkList(List<BizRk> bizRkList) {
        this.bizRkList = bizRkList;
    }

    public float getRkDj() {
        return rkDj;
    }

    public void setRkDj(float rkDj) {
        this.rkDj = rkDj;
    }

    public String getPc() {
        return pc;
    }

    public void setPc(String pc) {
        this.pc = pc;
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
     * 获取库存id
     *
     * @return KC_ID - 库存id
     */
    public String getKcId() {
        return kcId;
    }

    /**
     * 设置库存id
     *
     * @param kcId 库存id
     */
    public void setKcId(String kcId) {
        this.kcId = kcId;
    }

    /**
     * 获取入库数量
     *
     * @return RK_SL - 入库数量
     */
    public Integer getRkSl() {
        return rkSl;
    }

    /**
     * 设置入库数量
     *
     * @param rkSl 入库数量
     */
    public void setRkSl(Integer rkSl) {
        this.rkSl = rkSl;
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
        cjsj("CJSJ"),
        cjr("CJR"),
        kcId("KC_ID"),
        rkSl("RK_SL"),
        bz("BZ"),
        rkDj("RK_DJ"),
        pc("PC");

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":\"")
                .append(id).append('\"');
        sb.append(",\"cjsj\":\"")
                .append(cjsj).append('\"');
        sb.append(",\"cjr\":\"")
                .append(cjr).append('\"');
        sb.append(",\"kcId\":\"")
                .append(kcId).append('\"');
        sb.append(",\"rkSl\":")
                .append(rkSl);
        sb.append(",\"bz\":\"")
                .append(bz).append('\"');
        sb.append(",\"rkDj\":")
                .append(rkDj);
        sb.append(",\"pc\":\"")
                .append(pc).append('\"');
        sb.append(",\"bizRkList\":")
                .append(bizRkList);
        sb.append('}');
        return sb.toString();
    }
}