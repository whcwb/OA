package com.ldz.biz.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

@Table(name = "biz_kc")
@JsonIgnoreProperties(ignoreUnknown = true)
public class BizKc implements Serializable {
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 库存数量
     */
    @Column(name = "KC_SL")
    private Integer kcSl;

    /**
     * 类型
     */
    @Column(name = "KC_LX")
    private String kcLx;

    /**
     * 名称
     */
    @Column(name = "KC_MC")
    private String kcMc;

    /**
     * 创建时间
     */
    @Column(name = "CJSJ")
    private String cjsj;

    /**
     * 创建人
     */
    @Column(name = "CJR")
    private String cjr;

    /**
     * 备注
     */
    @Column(name = "BZ")
    private String bz;

    /**
     * 修改时间
     */
    @Column(name = "XG_SJ")
    private String xgSj;

    /**
     * 修改人
     */
    @Column(name = "XGR")
    private String xgr;

    @Column(name = "ZT")
    private String zt;

    @Transient
    private float rkDj;


    /**
     * 入库记录
     */
    @Transient
    private List<BizRk> bizRks;
    /**
     * 出库记录
     */
    @Transient
    private List<BizCk> bizCks;






    private static final long serialVersionUID = 1L;




    public float getRkDj() {
        return rkDj;
    }

    public void setRkDj(float rkDj) {
        this.rkDj = rkDj;
    }

    public List<BizRk> getBizRks() {
        return bizRks;
    }

    public void setBizRks(List<BizRk> bizRks) {
        this.bizRks = bizRks;
    }

    public List<BizCk> getBizCks() {
        return bizCks;
    }

    public void setBizCks(List<BizCk> bizCks) {
        this.bizCks = bizCks;
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

    public String getXgSj() {
        return xgSj;
    }

    public void setXgSj(String xgSj) {
        this.xgSj = xgSj;
    }

    public String getXgr() {
        return xgr;
    }

    public void setXgr(String xgr) {
        this.xgr = xgr;
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
     * 获取库存数量
     *
     * @return KC_SL - 库存数量
     */
    public Integer getKcSl() {
        return kcSl;
    }

    /**
     * 设置库存数量
     *
     * @param kcSl 库存数量
     */
    public void setKcSl(Integer kcSl) {
        this.kcSl = kcSl;
    }

    /**
     * 获取类型
     *
     * @return KC_LX - 类型
     */
    public String getKcLx() {
        return kcLx;
    }

    /**
     * 设置类型
     *
     * @param kcLx 类型
     */
    public void setKcLx(String kcLx) {
        this.kcLx = kcLx;
    }

    /**
     * 获取名称
     *
     * @return KC_MC - 名称
     */
    public String getKcMc() {
        return kcMc;
    }

    /**
     * 设置名称
     *
     * @param kcMc 名称
     */
    public void setKcMc(String kcMc) {
        this.kcMc = kcMc;
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
        kcSl("KC_SL"),
        kcLx("KC_LX"),
        kcMc("KC_MC"),
        cjsj("CJSJ"),
        cjr("CJR"),
        bz("BZ"),
        zt("ZT");

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