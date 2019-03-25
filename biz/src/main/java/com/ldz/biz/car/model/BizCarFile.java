package com.ldz.biz.car.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 车辆文件记录表
 * 主要保存文件明细
 */
@Table(name = "biz_car_file")
public class BizCarFile implements Serializable {
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
     * 文件类型
     */
    @Column(name = "file_type")
    private String fileType;

    /**
     * 文件编号(车牌号码_类型)
     */
    @Column(name = "file_code")
    private String fileCode;

    /**
     * 文件序号
     */
    private String seq;

    /**
     * 文件地址
     */
    @Column(name = "file_url")
    private String fileUrl;


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
     * 获取文件类型
     *
     * @return file_type - 文件类型
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * 设置文件类型
     *
     * @param fileType 文件类型
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * 获取文件编号(车牌号码_类型)
     *
     * @return file_code - 文件编号(车牌号码_类型)
     */
    public String getFileCode() {
        return fileCode;
    }

    /**
     * 设置文件编号(车牌号码_类型)
     *
     * @param fileCode 文件编号(车牌号码_类型)
     */
    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    /**
     * 获取文件序号
     *
     * @return seq - 文件序号
     */
    public String getSeq() {
        return seq;
    }

    /**
     * 设置文件序号
     *
     * @param seq 文件序号
     */
    public void setSeq(String seq) {
        this.seq = seq;
    }

    /**
     * 获取文件地址
     *
     * @return file_url - 文件地址
     */
    public String getFileUrl() {
        return fileUrl;
    }

    /**
     * 设置文件地址
     *
     * @param fileUrl 文件地址
     */
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
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
        fileType("file_type"),
        fileCode("file_code"),
        seq("seq"),
        fileUrl("file_url"),
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