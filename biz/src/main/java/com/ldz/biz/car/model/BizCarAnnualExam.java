package com.ldz.biz.car.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * 车辆年审信息
 */
@Getter
@Setter
@Table(name = "biz_car_annual_exam")
public class BizCarAnnualExam implements Serializable {
    /**
     * id
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 车辆id
     */
    @Column(name = "cl_id")
    private String clId;

    /**
     * 车辆_所属区域
     */
    @Column(name = "car_qy")
    private String carQy;

    /**
     * 车牌号
     */
    private String cph;

    /**
     * 年审至
     */
    private String nsz;

    /**
     * 年审时间
     */
    private String nssj;
    /**
     * 年审类型  003	改气年审   002	运管年审   001	车管年审
     */
    private String nslx;

    /**
     * 二审时间
     */
    private String essj;

    /**
     * 批次
     */
    private String pc;

    /**
     * 备案人ID
     */
    @Column(name = "jsyId")
    private String jsyid;

    /**
     * 备案人姓名
     */
    private String jsyxm;

    /**
     * 备案人身份证号
     */
    private String jsysfzh;

    /**
     * 备案人联系电话
     */
    private String jsylxdh;

    /**
     * 备案人是否采集
     */
    @Column(name = "cj_type")
    private String cjType;
    /**
     * 备案人信息采集时间
     */
    private String jsycjsj;

    /**
     * 备案人信息电子档案
     */
    private String jsydzda;
    /**
     * 备案人身份证正面
     */
    private String jsysfzzm;
    /**
     * 备案人身份证反面
     */
    private String jsysfzfm;
    /**
     * 备案人驾驶证正面
     */
    private String jsyjszzm;
    /**
     * 备案人驾驶证反面
     */
    private String jsyjszfm;

    /**
     * 备案人性别  性别 [ZDCLK0017]  00女  10男
     */
    private String jsyxb;
    /**
     * 备案人准架车型 准驾车型 [ZDCLK0040]
     */
    private String jsyzjcx;


    /**
     * 备案人信息备注
     */
    private String jsybz;


    /**
     * 年审状态
     */
    private String zt; // dizt:nszt 0:未年审，1：已年审，2：临期年审，3：逾期未年审

    /**
     * 电子档案路径
     */
    @Column(name = "da_file")
    private String daFile;
    /**
     * 电子档案路径
     */
    @Column(name = "da_code")
    private String daCode;

    /**
     * 创建人
     */
    private String cjr;

    /**
     * 创建时间
     */
    private String cjsj;
    /**
     * 备注
     */
    private String bz;

    @Column(name = "warn_id")
    private String warnId;

    /**
     * 下次年审时间
     */
    @Transient
    private String xcncsj;


    private static final long serialVersionUID = 1L;

    public enum InnerColumn {
        id("id"),
        clId("cl_id"),
        cph("cph"),
        nsz("nsz"),
        nssj("nssj"),
        nslx("nslx"),
        essj("essj"),
        pc("pc"),
        jsyid("jsyId"),
        jsyxm("jsyxm"),
        jsysfzh("jsysfzh"),
        jsylxdh("jsylxdh"),
        warnId("warn_id"),
        zt("zt"),
        cjr("cjr"),
        cjsj("cjsj");

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