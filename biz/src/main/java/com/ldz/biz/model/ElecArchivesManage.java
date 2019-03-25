package com.ldz.biz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 电子档案管理表
 */
@Table(name = "elec_archives_manage")
public class ElecArchivesManage implements Serializable {
    /**
     * ID
     */
    @Id
    private String id;

    /**
     * 类型
     * 0、学员签名 1、身份证正面  2、身份证反面 3、学员头像
     */
    private String type;

    /**
     * 学员id
     */
    @Column(name = "trainee_id")
    private String traineeId;

    /**
     * 文件路径
     */
    @Column(name = "file_path")
    private String filePath;

    /**
     * 上传时间
     */
    @Column(name = "upload_time")
    private String uploadTime;
    /**
     * 创建人
     */
    @Column(name = "cjr")
    private String cjr;

    /**
     * 创建时间
     */
    @Column(name = "cjsj")
    private String cjsj;

    public String getCjr() {
        return cjr;
    }

    public void setCjr(String cjr) {
        this.cjr = cjr;
    }

    public String getCjsj() {
        return cjsj;
    }

    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }
    private static final long serialVersionUID = 1L;

    /**
     * 获取ID
     *
     * @return id - ID
     */
    public String getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取类型
     *
     * @return type - 类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类型
     *
     * @param type 类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取学员id
     *
     * @return trainee_id - 学员id
     */
    public String getTraineeId() {
        return traineeId;
    }

    /**
     * 设置学员id
     *
     * @param traineeId 学员id
     */
    public void setTraineeId(String traineeId) {
        this.traineeId = traineeId;
    }

    /**
     * 获取文件路径
     *
     * @return file_path - 文件路径
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * 设置文件路径
     *
     * @param filePath 文件路径
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * 获取上传时间
     *
     * @return upload_time - 上传时间
     */
    public String getUploadTime() {
        return uploadTime;
    }

    /**
     * 设置上传时间
     *
     * @param uploadTime 上传时间
     */
    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public enum InnerColumn {
        id("id"),
        type("type"),
        traineeId("trainee_id"),
        filePath("file_path"),
        cjr("cjr"),
        cjsj("cjsj"),
        uploadTime("upload_time");

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