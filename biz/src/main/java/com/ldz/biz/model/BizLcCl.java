package com.ldz.biz.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

@Table(name = "biz_lc_cl")
@Getter
@Setter
public class BizLcCl implements Serializable {
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 车辆编号
     */
    @Column(name = "cl_bh")
    private String clBh;

    /**
     * 车辆所属考场
     */
    @Column(name = "cl_kc")
    private String clKc;

    /**
     * 车辆状态  00 空闲中 01使用中 02 维修中 03 保养中
     */
    @Column(name = "cl_zt")
    private String clZt;

    /**
     * 车牌号码
     */
    @Column(name = "cl_hm")
    private String clHm;

    /**
     * 安全员id
     */
    @Column(name = "zg_id")
    private String zgId;

    /**
     * 安全员姓名
     */
    @Column(name = "zg_xm")
    private String zgXm;

    /**
     * 科目  02 科目二  03 科目三
     */
    @Column(name = "cl_km")
    private String clKm;

    private String xgr;

    private String xgsj;

    private String cjsj;

    private String cjr;

    /**
     * 车型
     *
     */
    @Column(name = "cl_cx")
    private String clCx;

    @Column(name = "cl_img")
    private String clImg;

    @Column(name = "card_no")
    private String cardNo;

    /**
     * 练车记录(正在训练的)
     */
    @Transient
    private BizLcJl lcJl;
    /**
     * 已训练时长
     */
    @Transient
    private String dqsc;
    /**
     * 以训练总价
     */
    @Transient
    private int zj;
    /**
     * 当前单价(小时)
     */
    @Transient
    private float dj;
    /**
     * 当前单价(分钟)
     */
    @Transient
    private float dj1;

    @Transient
    private String yhsc;

    /**
     * 是否强制替换卡号 有值则替换
     */
    @Transient
    private String th;


    private static final long serialVersionUID = 1L;



    public enum InnerColumn {
        id("ID"),
        clBh("cl_bh"),
        clKc("cl_kc"),
        clZt("cl_zt"),
        clHm("cl_hm"),
        zgId("zg_id"),
        zgXm("zg_xm"),
        clKm("cl_km"),
        cjsj("cjsj"),
        cjr("cjr"),
        clCx("cl_cx"),
        cardNo("card_no");

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