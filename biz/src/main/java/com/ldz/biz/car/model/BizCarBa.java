package com.ldz.biz.car.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 汽车运管备案表
 */
@Table(name = "biz_car_ba")
public class BizCarBa implements Serializable {
    /**
     * id
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 车辆ID
     */
    @Column(name = "cl_id")
    private String clId;

    /**
     * 车牌号
     */
    private String cph;

    /**
     * 运输证号
     */
    private String yszh;

    /**
     * 登记日期
     */
    private String djrq;

    /**
     * 是否安装GPS  是/否 [ZDCLK1034] 
     */
    @Column(name = "gps_type")
    private String gpsType;

    /**
     * GPS号码
     */
    @Column(name = "gps_code")
    private String gpsCode;

    /**
     *  车辆备案_运营状态 [ZDCLK1033] 营运状态1、运营 2、注销 0、未登记
     */
    @Column(name = "yy_type")
    private String yyType;

    /**
     * 1、14年上线  0、不是   是/否 [ZDCLK1034]
     */
    @Column(name = "ysn_sx")
    private String ysnSx;

    /**
     * 更新(BU列)
     */
    private String gx;

    /**
     * 轴距
     */
    private Integer zj;

    /**
     * 车辆备案_卡机安装状态 [ZDCLK1035] 卡机安装状态 1、已安装 0、未安装
     */
    @Column(name = "kj_type")
    private String kjType;

    /**
     * 卡机安装时间
     */
    @Column(name = "kj_azsj")
    private String kjAzsj;

    /**
     * 卡机批次
     */
    @Column(name = "kj_pc")
    private String kjPc;

    /**
     * 明涛成功新证号
     */
    @Column(name = "new_code")
    private String newCode;

    /**
     * 新卡机
     */
    @Column(name = "new_kj")
    private String newKj;

    /**
     * 备注
     */
    private String bz;

    /**
     * 机构代码
     */
    private String jgdm;

    /**
     * 机构名称
     */
    private String jgmc;

    /**
     * 创建人
     */
    private String cjr;

    /**
     * 创建时间
     */
    private String cjsj;

    private static final long serialVersionUID = 1L;

    /**
     * 获取id
     *
     * @return id - id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取车辆ID
     *
     * @return cl_id - 车辆ID
     */
    public String getClId() {
        return clId;
    }

    /**
     * 设置车辆ID
     *
     * @param clId 车辆ID
     */
    public void setClId(String clId) {
        this.clId = clId;
    }

    /**
     * 获取车牌号
     *
     * @return cph - 车牌号
     */
    public String getCph() {
        return cph;
    }

    /**
     * 设置车牌号
     *
     * @param cph 车牌号
     */
    public void setCph(String cph) {
        this.cph = cph;
    }

    /**
     * 获取运输证号
     *
     * @return yszh - 运输证号
     */
    public String getYszh() {
        return yszh;
    }

    /**
     * 设置运输证号
     *
     * @param yszh 运输证号
     */
    public void setYszh(String yszh) {
        this.yszh = yszh;
    }

    /**
     * 获取登记日期
     *
     * @return djrq - 登记日期
     */
    public String getDjrq() {
        return djrq;
    }

    /**
     * 设置登记日期
     *
     * @param djrq 登记日期
     */
    public void setDjrq(String djrq) {
        this.djrq = djrq;
    }

    /**
     * 获取是否安装GPS  是/否 [ZDCLK1034] 
     *
     * @return gps_type - 是否安装GPS  是/否 [ZDCLK1034] 
     */
    public String getGpsType() {
        return gpsType;
    }

    /**
     * 设置是否安装GPS  是/否 [ZDCLK1034] 
     *
     * @param gpsType 是否安装GPS  是/否 [ZDCLK1034] 
     */
    public void setGpsType(String gpsType) {
        this.gpsType = gpsType;
    }

    /**
     * 获取GPS号码
     *
     * @return gps_code - GPS号码
     */
    public String getGpsCode() {
        return gpsCode;
    }

    /**
     * 设置GPS号码
     *
     * @param gpsCode GPS号码
     */
    public void setGpsCode(String gpsCode) {
        this.gpsCode = gpsCode;
    }

    /**
     * 获取 车辆备案_运营状态 [ZDCLK1033] 营运状态1、运营 2、注销 0、未登记
     *
     * @return yy_type -  车辆备案_运营状态 [ZDCLK1033] 营运状态1、运营 2、注销 0、未登记
     */
    public String getYyType() {
        return yyType;
    }

    /**
     * 设置 车辆备案_运营状态 [ZDCLK1033] 营运状态1、运营 2、注销 0、未登记
     *
     * @param yyType  车辆备案_运营状态 [ZDCLK1033] 营运状态1、运营 2、注销 0、未登记
     */
    public void setYyType(String yyType) {
        this.yyType = yyType;
    }

    /**
     * 获取1、14年上线  0、不是   是/否 [ZDCLK1034]
     *
     * @return ysn_sx - 1、14年上线  0、不是   是/否 [ZDCLK1034]
     */
    public String getYsnSx() {
        return ysnSx;
    }

    /**
     * 设置1、14年上线  0、不是   是/否 [ZDCLK1034]
     *
     * @param ysnSx 1、14年上线  0、不是   是/否 [ZDCLK1034]
     */
    public void setYsnSx(String ysnSx) {
        this.ysnSx = ysnSx;
    }

    /**
     * 获取更新(BU列)
     *
     * @return gx - 更新(BU列)
     */
    public String getGx() {
        return gx;
    }

    /**
     * 设置更新(BU列)
     *
     * @param gx 更新(BU列)
     */
    public void setGx(String gx) {
        this.gx = gx;
    }

    /**
     * 获取轴距
     *
     * @return zj - 轴距
     */
    public Integer getZj() {
        return zj;
    }

    /**
     * 设置轴距
     *
     * @param zj 轴距
     */
    public void setZj(Integer zj) {
        this.zj = zj;
    }

    /**
     * 获取车辆备案_卡机安装状态 [ZDCLK1035] 卡机安装状态 1、已安装 0、未安装
     *
     * @return kj_type - 车辆备案_卡机安装状态 [ZDCLK1035] 卡机安装状态 1、已安装 0、未安装
     */
    public String getKjType() {
        return kjType;
    }

    /**
     * 设置车辆备案_卡机安装状态 [ZDCLK1035] 卡机安装状态 1、已安装 0、未安装
     *
     * @param kjType 车辆备案_卡机安装状态 [ZDCLK1035] 卡机安装状态 1、已安装 0、未安装
     */
    public void setKjType(String kjType) {
        this.kjType = kjType;
    }

    /**
     * 获取卡机安装时间
     *
     * @return kj_azsj - 卡机安装时间
     */
    public String getKjAzsj() {
        return kjAzsj;
    }

    /**
     * 设置卡机安装时间
     *
     * @param kjAzsj 卡机安装时间
     */
    public void setKjAzsj(String kjAzsj) {
        this.kjAzsj = kjAzsj;
    }

    /**
     * 获取卡机批次
     *
     * @return kj_pc - 卡机批次
     */
    public String getKjPc() {
        return kjPc;
    }

    /**
     * 设置卡机批次
     *
     * @param kjPc 卡机批次
     */
    public void setKjPc(String kjPc) {
        this.kjPc = kjPc;
    }

    /**
     * 获取明涛成功新证号
     *
     * @return new_code - 明涛成功新证号
     */
    public String getNewCode() {
        return newCode;
    }

    /**
     * 设置明涛成功新证号
     *
     * @param newCode 明涛成功新证号
     */
    public void setNewCode(String newCode) {
        this.newCode = newCode;
    }

    /**
     * 获取新卡机
     *
     * @return new_kj - 新卡机
     */
    public String getNewKj() {
        return newKj;
    }

    /**
     * 设置新卡机
     *
     * @param newKj 新卡机
     */
    public void setNewKj(String newKj) {
        this.newKj = newKj;
    }

    /**
     * 获取备注
     *
     * @return bz - 备注
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

    /**
     * 获取机构代码
     *
     * @return jgdm - 机构代码
     */
    public String getJgdm() {
        return jgdm;
    }

    /**
     * 设置机构代码
     *
     * @param jgdm 机构代码
     */
    public void setJgdm(String jgdm) {
        this.jgdm = jgdm;
    }

    /**
     * 获取机构名称
     *
     * @return jgmc - 机构名称
     */
    public String getJgmc() {
        return jgmc;
    }

    /**
     * 设置机构名称
     *
     * @param jgmc 机构名称
     */
    public void setJgmc(String jgmc) {
        this.jgmc = jgmc;
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

    public enum InnerColumn {
        id("id"),
        clId("cl_id"),
        cph("cph"),
        yszh("yszh"),
        djrq("djrq"),
        gpsType("gps_type"),
        gpsCode("gps_code"),
        yyType("yy_type"),
        ysnSx("ysn_sx"),
        gx("gx"),
        zj("zj"),
        kjType("kj_type"),
        kjAzsj("kj_azsj"),
        kjPc("kj_pc"),
        newCode("new_code"),
        newKj("new_kj"),
        bz("bz"),
        jgdm("jgdm"),
        jgmc("jgmc"),
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