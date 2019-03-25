package com.ldz.biz.car.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * 警告表
 */
@Table(name = "biz_car_warn")
public class BizCarWarn implements Serializable {
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
     * 提醒日期(yyyy_mm_dd)
     */
    @Column(name = "warn_date")
    private String warnDate;

    /**
     * 截止年
     */
    @Column(name = "expiry_yyyy")
    private String expiryYyyy;

    /**
     * 截止月
     */
    @Column(name = "expiry_mm")
    private String expiryMm;

    /**
     * 提醒类型(1、车辆年审提醒 2、运管年审提醒 3、改气年审提醒 4、续保提醒 5、报废提醒)
     */
    @Column(name = "warn_type")
    private String warnType;

    /**
     * 提醒批次 以年为单位，一年批几次
     */
    @Column(name = "warn_pc")
    private String warnPc;

    /**
     * 截止日期
     */
    @Column(name = "expiry_date")
    private String expiryDate;

    /**
     * 提醒是否处理 是/否 [ZDCLK1034]
     */
    @Column(name = "warn_dispose")
    private String warnDispose;

    /**
     * 处理编号
     */
    @Column(name = "dispose_code")
    private String disposeCode;

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

//    @Column(name = "seq_type")
//    private String seqType;
//
//    @Column(name = "seq")
//    private String seq;
    /**
     * 车辆产权人
     */
    @Transient
    private String clCqr;
    /**
     * 车辆产权人电话
     */
    @Transient
    private String clCqrDn;
    /**
     * 车辆产权人证件号
     */
    @Transient
    private String clCqrCode;



    @Transient
    private BizCar carDetailed;

    private static final long serialVersionUID = 1L;
//
//    public String getSeqType() {
//        return seqType;
//    }
//
//    public void setSeqType(String seqType) {
//        this.seqType = seqType;
//    }
//
//    public String getSeq() {
//        return seq;
//    }
//
//    public void setSeq(String seq) {
//        this.seq = seq;
//    }

    public String getDaFile() {
        return daFile;
    }

    public void setDaFile(String daFile) {
        this.daFile = daFile;
    }

    public String getDaCode() {
        return daCode;
    }

    public void setDaCode(String daCode) {
        this.daCode = daCode;
    }

    public String getClCqr() {
        return clCqr;
    }

    public void setClCqr(String clCqr) {
        this.clCqr = clCqr;
    }

    public String getClCqrDn() {
        return clCqrDn;
    }

    public void setClCqrDn(String clCqrDn) {
        this.clCqrDn = clCqrDn;
    }

    public String getClCqrCode() {
        return clCqrCode;
    }

    public void setClCqrCode(String clCqrCode) {
        this.clCqrCode = clCqrCode;
    }

    public BizCar getCarDetailed() {
        return carDetailed;
    }

    public void setCarDetailed(BizCar carDetailed) {
        this.carDetailed = carDetailed;
    }

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
     * 获取提醒日期(yyyy_mm_dd)
     *
     * @return warn_date - 提醒日期(yyyy_mm_dd)
     */
    public String getWarnDate() {
        return warnDate;
    }

    /**
     * 设置提醒日期(yyyy_mm_dd)
     *
     * @param warnDate 提醒日期(yyyy_mm_dd)
     */
    public void setWarnDate(String warnDate) {
        this.warnDate = warnDate;
    }

    /**
     * 获取提醒年
     *
     * @return warn_yyyy - 提醒年
     */
    public String getExpiryYyyy() {
        return expiryYyyy;
    }

    /**
     * 设置提醒年
     *
     * @param expiryYyyy 提醒年
     */
    public void setExpiryYyyy(String expiryYyyy) {
        this.expiryYyyy = expiryYyyy;
    }

    /**
     * 获取提醒月
     *
     * @return warn_mm - 提醒月
     */
    public String getExpiryMm() {
        return expiryMm;
    }

    /**
     * 设置提醒月
     *
     * @param expiryMm 提醒月
     */
    public void setExpiryMm(String expiryMm) {
        this.expiryMm = expiryMm;
    }

    /**
     * 获取提醒类型(1、车辆年审提醒 2、运管年审提醒 3、改气年审提醒 4、续保提醒 5、报废提醒)
     *
     * @return warn_type - 提醒类型(1、车辆年审提醒 2、运管年审提醒 3、改气年审提醒 4、续保提醒 5、报废提醒)
     */
    public String getWarnType() {
        return warnType;
    }

    /**
     * 设置提醒类型(1、车辆年审提醒 2、运管年审提醒 3、改气年审提醒 4、续保提醒 5、报废提醒)
     *
     * @param warnType 提醒类型(1、车辆年审提醒 2、运管年审提醒 3、改气年审提醒 4、续保提醒 5、报废提醒)
     */
    public void setWarnType(String warnType) {
        this.warnType = warnType;
    }

    /**
     * 获取提醒批次 以年为单位，一年批几次
     *
     * @return warn_pc - 提醒批次 以年为单位，一年批几次
     */
    public String getWarnPc() {
        return warnPc;
    }

    /**
     * 设置提醒批次 以年为单位，一年批几次
     *
     * @param warnPc 提醒批次 以年为单位，一年批几次
     */
    public void setWarnPc(String warnPc) {
        this.warnPc = warnPc;
    }

    /**
     * 获取截止日期
     *
     * @return expiry_date - 截止日期
     */
    public String getExpiryDate() {
        return expiryDate;
    }

    /**
     * 设置截止日期
     *
     * @param expiryDate 截止日期
     */
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    /**
     * 获取提醒是否处理 是/否 [ZDCLK1034]
     *
     * @return warn_dispose - 提醒是否处理 是/否 [ZDCLK1034]
     */
    public String getWarnDispose() {
        return warnDispose;
    }

    /**
     * 设置提醒是否处理 是/否 [ZDCLK1034]
     *
     * @param warnDispose 提醒是否处理 是/否 [ZDCLK1034]
     */
    public void setWarnDispose(String warnDispose) {
        this.warnDispose = warnDispose;
    }

    /**
     * 获取处理编号
     *
     * @return dispose_code - 处理编号
     */
    public String getDisposeCode() {
        return disposeCode;
    }

    /**
     * 设置处理编号
     *
     * @param disposeCode 处理编号
     */
    public void setDisposeCode(String disposeCode) {
        this.disposeCode = disposeCode;
    }

    public enum InnerColumn {
        id("id"),
        clId("cl_id"),
        cph("cph"),
        warnDate("warn_date"),
        expiryYyyy("expiry_yyyy"),
        expiryMm("expiry_mm"),
        warnType("warn_type"),
        warnPc("warn_pc"),
        expiryDate("expiry_date"),
        warnDispose("warn_dispose"),
        seqType("seq_type"),
        seq("seq"),
        disposeCode("dispose_code");

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