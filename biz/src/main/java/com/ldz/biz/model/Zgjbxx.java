package com.ldz.biz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

@Table(name = "zgjbxx")
public class Zgjbxx implements Serializable {
    /**
     * 唯一标识
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 姓名
     */
    @Column(name = "XM")
    private String xm;

    @Column(name = "DABH1")
    private String dabh1;


    /**
     * 性别
     */
    @Column(name = "XB")
    private String xb;

    /**
     * 手机号码
     */
    @Column(name = "SJHM")
    private String sjhm;

    /**
     * 集团号
     */
    @Column(name = "JTH")
    private String jth;

    /**
     * 文化程度
     */
    @Column(name = "WHCD")
    private String whcd;

    /**
     * 状态
     */
    @Column(name = "ZT")
    private String zt;

    /**
     * 入校时间
     */
    @Column(name = "RXSJ")
    private String rxsj;

    /**
     * 工作地
     */
    @Column(name = "GZD")
    private String gzd;

    /**
     * 工作岗位
     */
    @Column(name = "GZGW")
    private String gzgw;

    /**
     * 隶属
     */
    @Column(name = "LS")
    private String ls;

    /**
     * 备注
     */
    @Column(name = "BZ")
    private String bz;

    /**
     * 离职日期
     */
    @Column(name = "LZRQ")
    private String lzrq;

    /**
     * 系统备案
     */
    @Column(name = "XTBA")
    private String xtba;

    /**
     * 驾校
     */
    @Column(name = "JX")
    private String jx;

    /**
     * 身份证号
     */
    @Column(name = "SFZH")
    private String sfzh;

    /**
     * 年龄
     */
    @Column(name = "NL")
    private String nl;

    /**
     * 出生日期
     */
    @Column(name = "CSRQ")
    private String csrq;

    /**
     * 月份
     */
    @Column(name = "YF")
    private String yf;

    /**
     * 居住地址
     */
    @Column(name = "JZDZ")
    private String jzdz;

    /**
     * 身份证件有效期
     */
    @Column(name = "ZJYXQ")
    private String zjyxq;

    /**
     * 档案编号
     */
    @Column(name = "DABH")
    private String dabh;

    /**
     * 准驾车型
     */
    @Column(name = "ZJCX")
    private String zjcx;

    /**
     * 初领日期
     */
    @Column(name = "CLRQ")
    private String clrq;

    /**
     * 驾驶证件有效期
     */
    @Column(name = "ZJYXQ1")
    private String zjyxq1;

    /**
     * 教练证号
     */
    @Column(name = "JLZH")
    private String jlzh;

    /**
     * 领用人
     */
    @Column(name = "LYR")
    private String lyr;

    /**
     * 准教车型
     */
    @Column(name = "ZJCX1")
    private String zjcx1;

    /**
     * 教练证初领日期
     */
    @Column(name = "CLRQ1")
    private String clrq1;

    /**
     * 教练证有效期
     */
    @Column(name = "ZJYXQ2")
    private String zjyxq2;

    /**
     * 有效年限
     */
    @Column(name = "YXNX")
    private String yxnx;

    /**
     * 明涛教练员卡
     */
    @Column(name = "MTJLYK")
    private String mtjlyk;

    /**
     * 成功教练员卡
     */
    @Column(name = "CGJLYK")
    private String cgjlyk;

    /**
     * 领用人1
     */
    @Column(name = "LYR1")
    private String lyr1;

    @Column(name = "CJSJ")
    private String cjsj;

    /**
     * 照片 -- 红
     * @return
     */
    @Column(name = "HONG")
    private String hong;

    /**
     * 照片--白
     * @return
     */
    @Column(name = "BAI")
    private String bai;

    /**
     * 照片 -- 蓝
     * @return
     */
    @Column(name = "LAN")
    private String lan;

    /**
     * 推荐名额
     * @return
     */
    @Column(name = "TJRS")
    private Integer tjrs;
    /**
     * 已用名额
     */
    @Column(name = "YYME")
    private Integer yyme;

    /**
     * 在职状态
     */
    @Column(name = "ZZZT")
    private String zzzt;
    /**
     * 上半年推荐名额
     */
    @Column(name = "S_RS")
    private Integer sRs;
    /**
     * 下半年推荐名额
     */
    @Column(name = "X_RS")
    private Integer xRs;
    /**
     * 驾驶证正本
     */
    @Column(name = "JXZ_ON")
    private String jxzOn;
    /**
     * 驾驶证副本
     */
    @Column(name = "JXZ_DOWN")
    private String jxzDown;
    /**
     * 身份证正面
     */
    @Column(name = "ZJ_ON")
    private String zjOn;
    /**
     * 身份证反面
     */
    @Column(name = "ZJ_DOWN")
    private String zjDown;
    /**
     * 教练证
     */
    @Column(name = "JLZ_ON")
    private String jlzOn;
    /**
     * 教练表 id
     */
    @Column(name = "JL_ID")
    private String jlId;

    /**
     * 教练状态
     */
    @Column(name = "JL_ZT")
    private String jlZt;

    private String jgdm;

    /**
     * 安全员签到状态(字典[aqyzt] 00休息 10上班)
     */
    @Column(name = "aqy_qdzt")
    private String aqyQdzt;
    /**
     * 安全员签到时间
     */
    @Column(name = "aqy_qdsj")
    private String aqyQdsj;

    /**
     * 科目
     */
    private String km;

    /**
     * 上半年已用人数
     */
    @Transient
    private int sRsed;
    /**
     * 下半年已用人数
     */
    @Transient
    private int xRsed;

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    private  String jgmc;

    public String getAqyQdzt() {
        return aqyQdzt;
    }

    public void setAqyQdzt(String aqyQdzt) {
        this.aqyQdzt = aqyQdzt;
    }

    public String getAqyQdsj() {
        return aqyQdsj;
    }

    public void setAqyQdsj(String aqyQdsj) {
        this.aqyQdsj = aqyQdsj;
    }

    public String getJgmc() {
        return jgmc;
    }

    public void setJgmc(String jgmc) {
        this.jgmc = jgmc;
    }

    public String getJgdm() {
        return jgdm;
    }

    public void setJgdm(String jgdm) {
        this.jgdm = jgdm;
    }

    public String getJlzOn() {
        return jlzOn;
    }

    public void setJlzOn(String jlzOn) {
        this.jlzOn = jlzOn;
    }

    public String getJlId() {
        return jlId;
    }

    public void setJlId(String jlId) {
        this.jlId = jlId;
    }

    public String getJlZt() {
        return jlZt;
    }

    public void setJlZt(String jlZt) {
        this.jlZt = jlZt;
    }

    public String getJxzOn() {
        return jxzOn;
    }

    public void setJxzOn(String jxzOn) {
        this.jxzOn = jxzOn;
    }

    public String getJxzDown() {
        return jxzDown;
    }

    public void setJxzDown(String jxzDown) {
        this.jxzDown = jxzDown;
    }

    public String getZjOn() {
        return zjOn;
    }

    public void setZjOn(String zjOn) {
        this.zjOn = zjOn;
    }

    public String getZjDown() {
        return zjDown;
    }

    public void setZjDown(String zjDown) {
        this.zjDown = zjDown;
    }

    public int getsRsed() {
        return sRsed;
    }

    public void setsRsed(int sRsed) {
        this.sRsed = sRsed;
    }

    public int getxRsed() {
        return xRsed;
    }

    public void setxRsed(int xRsed) {
        this.xRsed = xRsed;
    }

    public Integer getsRs() {
        return sRs;
    }

    public void setsRs(Integer sRs) {
        this.sRs = sRs;
    }

    public Integer getxRs() {
        return xRs;
    }

    public void setxRs(Integer xRs) {
        this.xRs = xRs;
    }

    public String getZzzt() {
        return zzzt;
    }

    public void setZzzt(String zzzt) {
        this.zzzt = zzzt;
    }

    public Integer getYyme() {
        return yyme;
    }

    public void setYyme(Integer yyme) {
        this.yyme = yyme;
    }

    public Integer getTjrs() {
        return tjrs;
    }

    public void setTjrs(Integer tjrs) {
        this.tjrs = tjrs;
    }

    public String getHong() {
        return hong;
    }

    public void setHong(String hong) {
        this.hong = hong;
    }

    public String getBai() {
        return bai;
    }

    public void setBai(String bai) {
        this.bai = bai;
    }

    public String getLan() {
        return lan;
    }

    public void setLan(String lan) {
        this.lan = lan;
    }

    public String getCjsj() {
        return cjsj;
    }

    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    private static final long serialVersionUID = 1L;


    public String getDabh1() {
        return dabh1;
    }

    public void setDabh1(String dabh1) {
        this.dabh1 = dabh1;
    }

    /**
     * 获取唯一标识
     *
     * @return ID - 唯一标识
     */
    public String getId() {
        return id;
    }

    /**
     * 设置唯一标识
     *
     * @param id 唯一标识
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取姓名
     *
     * @return XM - 姓名
     */
    public String getXm() {
        return xm;
    }

    /**
     * 设置姓名
     *
     * @param xm 姓名
     */
    public void setXm(String xm) {
        this.xm = xm;
    }

    /**
     * 获取性别
     *
     * @return XB - 性别
     */
    public String getXb() {
        return xb;
    }

    /**
     * 设置性别
     *
     * @param xb 性别
     */
    public void setXb(String xb) {
        this.xb = xb;
    }

    /**
     * 获取手机号码
     *
     * @return SJHM - 手机号码
     */
    public String getSjhm() {
        return sjhm;
    }

    /**
     * 设置手机号码
     *
     * @param sjhm 手机号码
     */
    public void setSjhm(String sjhm) {
        this.sjhm = sjhm;
    }

    /**
     * 获取集团号
     *
     * @return JTH - 集团号
     */
    public String getJth() {
        return jth;
    }

    /**
     * 设置集团号
     *
     * @param jth 集团号
     */
    public void setJth(String jth) {
        this.jth = jth;
    }

    /**
     * 获取文化程度
     *
     * @return WHCD - 文化程度
     */
    public String getWhcd() {
        return whcd;
    }

    /**
     * 设置文化程度
     *
     * @param whcd 文化程度
     */
    public void setWhcd(String whcd) {
        this.whcd = whcd;
    }

    /**
     * 获取状态
     *
     * @return ZT - 状态
     */
    public String getZt() {
        return zt;
    }

    /**
     * 设置状态
     *
     * @param zt 状态
     */
    public void setZt(String zt) {
        this.zt = zt;
    }

    /**
     * 获取入校时间
     *
     * @return RXSJ - 入校时间
     */
    public String getRxsj() {
        return rxsj;
    }

    /**
     * 设置入校时间
     *
     * @param rxsj 入校时间
     */
    public void setRxsj(String rxsj) {
        this.rxsj = rxsj;
    }

    /**
     * 获取工作地
     *
     * @return GZD - 工作地
     */
    public String getGzd() {
        return gzd;
    }

    /**
     * 设置工作地
     *
     * @param gzd 工作地
     */
    public void setGzd(String gzd) {
        this.gzd = gzd;
    }

    /**
     * 获取工作岗位
     *
     * @return GZGW - 工作岗位
     */
    public String getGzgw() {
        return gzgw;
    }

    /**
     * 设置工作岗位
     *
     * @param gzgw 工作岗位
     */
    public void setGzgw(String gzgw) {
        this.gzgw = gzgw;
    }

    /**
     * 获取隶属
     *
     * @return LS - 隶属
     */
    public String getLs() {
        return ls;
    }

    /**
     * 设置隶属
     *
     * @param ls 隶属
     */
    public void setLs(String ls) {
        this.ls = ls;
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

    /**
     * 获取离职日期
     *
     * @return LZRQ - 离职日期
     */
    public String getLzrq() {
        return lzrq;
    }

    /**
     * 设置离职日期
     *
     * @param lzrq 离职日期
     */
    public void setLzrq(String lzrq) {
        this.lzrq = lzrq;
    }

    /**
     * 获取系统备案
     *
     * @return XTBA - 系统备案
     */
    public String getXtba() {
        return xtba;
    }

    /**
     * 设置系统备案
     *
     * @param xtba 系统备案
     */
    public void setXtba(String xtba) {
        this.xtba = xtba;
    }

    /**
     * 获取驾校
     *
     * @return JX - 驾校
     */
    public String getJx() {
        return jx;
    }

    /**
     * 设置驾校
     *
     * @param jx 驾校
     */
    public void setJx(String jx) {
        this.jx = jx;
    }

    /**
     * 获取身份证号
     *
     * @return SFZH - 身份证号
     */
    public String getSfzh() {
        return sfzh;
    }

    /**
     * 设置身份证号
     *
     * @param sfzh 身份证号
     */
    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }

    /**
     * 获取年龄
     *
     * @return NL - 年龄
     */
    public String getNl() {
        return nl;
    }

    /**
     * 设置年龄
     *
     * @param nl 年龄
     */
    public void setNl(String nl) {
        this.nl = nl;
    }

    /**
     * 获取出生日期
     *
     * @return CSRQ - 出生日期
     */
    public String getCsrq() {
        return csrq;
    }

    /**
     * 设置出生日期
     *
     * @param csrq 出生日期
     */
    public void setCsrq(String csrq) {
        this.csrq = csrq;
    }

    /**
     * 获取月份
     *
     * @return YF - 月份
     */
    public String getYf() {
        return yf;
    }

    /**
     * 设置月份
     *
     * @param yf 月份
     */
    public void setYf(String yf) {
        this.yf = yf;
    }

    /**
     * 获取居住地址
     *
     * @return JZDZ - 居住地址
     */
    public String getJzdz() {
        return jzdz;
    }

    /**
     * 设置居住地址
     *
     * @param jzdz 居住地址
     */
    public void setJzdz(String jzdz) {
        this.jzdz = jzdz;
    }

    /**
     * 获取身份证件有效期
     *
     * @return ZJYXQ - 身份证件有效期
     */
    public String getZjyxq() {
        return zjyxq;
    }

    /**
     * 设置身份证件有效期
     *
     * @param zjyxq 身份证件有效期
     */
    public void setZjyxq(String zjyxq) {
        this.zjyxq = zjyxq;
    }

    /**
     * 获取档案编号
     *
     * @return DABH - 档案编号
     */
    public String getDabh() {
        return dabh;
    }

    /**
     * 设置档案编号
     *
     * @param dabh 档案编号
     */
    public void setDabh(String dabh) {
        this.dabh = dabh;
    }

    /**
     * 获取准驾车型
     *
     * @return ZJCX - 准驾车型
     */
    public String getZjcx() {
        return zjcx;
    }

    /**
     * 设置准驾车型
     *
     * @param zjcx 准驾车型
     */
    public void setZjcx(String zjcx) {
        this.zjcx = zjcx;
    }

    /**
     * 获取初领日期
     *
     * @return CLRQ - 初领日期
     */
    public String getClrq() {
        return clrq;
    }

    /**
     * 设置初领日期
     *
     * @param clrq 初领日期
     */
    public void setClrq(String clrq) {
        this.clrq = clrq;
    }

    /**
     * 获取驾驶证件有效期
     *
     * @return ZJYXQ1 - 驾驶证件有效期
     */
    public String getZjyxq1() {
        return zjyxq1;
    }

    /**
     * 设置驾驶证件有效期
     *
     * @param zjyxq1 驾驶证件有效期
     */
    public void setZjyxq1(String zjyxq1) {
        this.zjyxq1 = zjyxq1;
    }

    /**
     * 获取教练证号
     *
     * @return JLZH - 教练证号
     */
    public String getJlzh() {
        return jlzh;
    }

    /**
     * 设置教练证号
     *
     * @param jlzh 教练证号
     */
    public void setJlzh(String jlzh) {
        this.jlzh = jlzh;
    }

    /**
     * 获取领用人
     *
     * @return LYR - 领用人
     */
    public String getLyr() {
        return lyr;
    }

    /**
     * 设置领用人
     *
     * @param lyr 领用人
     */
    public void setLyr(String lyr) {
        this.lyr = lyr;
    }

    /**
     * 获取准教车型
     *
     * @return ZJCX1 - 准教车型
     */
    public String getZjcx1() {
        return zjcx1;
    }

    /**
     * 设置准教车型
     *
     * @param zjcx1 准教车型
     */
    public void setZjcx1(String zjcx1) {
        this.zjcx1 = zjcx1;
    }

    /**
     * 获取教练证初领日期
     *
     * @return CLRQ1 - 教练证初领日期
     */
    public String getClrq1() {
        return clrq1;
    }

    /**
     * 设置教练证初领日期
     *
     * @param clrq1 教练证初领日期
     */
    public void setClrq1(String clrq1) {
        this.clrq1 = clrq1;
    }

    /**
     * 获取教练证有效期
     *
     * @return ZJYXQ2 - 教练证有效期
     */
    public String getZjyxq2() {
        return zjyxq2;
    }

    /**
     * 设置教练证有效期
     *
     * @param zjyxq2 教练证有效期
     */
    public void setZjyxq2(String zjyxq2) {
        this.zjyxq2 = zjyxq2;
    }

    /**
     * 获取有效年限
     *
     * @return YXNX - 有效年限
     */
    public String getYxnx() {
        return yxnx;
    }

    /**
     * 设置有效年限
     *
     * @param yxnx 有效年限
     */
    public void setYxnx(String yxnx) {
        this.yxnx = yxnx;
    }

    /**
     * 获取明涛教练员卡
     *
     * @return MTJLYK - 明涛教练员卡
     */
    public String getMtjlyk() {
        return mtjlyk;
    }

    /**
     * 设置明涛教练员卡
     *
     * @param mtjlyk 明涛教练员卡
     */
    public void setMtjlyk(String mtjlyk) {
        this.mtjlyk = mtjlyk;
    }

    /**
     * 获取成功教练员卡
     *
     * @return CGJLYK - 成功教练员卡
     */
    public String getCgjlyk() {
        return cgjlyk;
    }

    /**
     * 设置成功教练员卡
     *
     * @param cgjlyk 成功教练员卡
     */
    public void setCgjlyk(String cgjlyk) {
        this.cgjlyk = cgjlyk;
    }

    /**
     * 获取领用人1
     *
     * @return LYR1 - 领用人1
     */
    public String getLyr1() {
        return lyr1;
    }

    /**
     * 设置领用人1
     *
     * @param lyr1 领用人1
     */
    public void setLyr1(String lyr1) {
        this.lyr1 = lyr1;
    }

    public enum InnerColumn {
        id("ID"),
        xm("XM"),
        xb("XB"),
        sjhm("SJHM"),
        jth("JTH"),
        whcd("WHCD"),
        zt("ZT"),
        rxsj("RXSJ"),
        gzd("GZD"),
        gzgw("GZGW"),
        ls("LS"),
        bz("BZ"),
        lzrq("LZRQ"),
        xtba("XTBA"),
        jx("JX"),
        sfzh("SFZH"),
        nl("NL"),
        csrq("CSRQ"),
        yf("YF"),
        jzdz("JZDZ"),
        zjyxq("ZJYXQ"),
        dabh("DABH"),
        zjcx("ZJCX"),
        clrq("CLRQ"),
        zjyxq1("ZJYXQ1"),
        jlzh("JLZH"),
        lyr("LYR"),
        zjcx1("ZJCX1"),
        clrq1("CLRQ1"),
        zjyxq2("ZJYXQ2"),
        yxnx("YXNX"),
        mtjlyk("MTJLYK"),
        cgjlyk("CGJLYK"),
        lyr1("LYR1"),
        cjsj("CJSJ"),
        dabh1("DABH1"),
        bai("BAI"),
        hong("HONG"),
        lan("LAN"),
        tjrs("TJRS"),
        yyme("YYME"),
        zzzt("ZZZT"),
        jlId("JL_ID"),
        jlZt("JL_ZT"),
        jlzOn("JLZ_ON"),
        aqyQdzt("aqy_qdzt");

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