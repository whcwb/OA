package com.ldz.biz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "file_upload_record")
public class FileUploadRecord implements Serializable {
    @Id
    private String id;

    /**
     * 文件key
     */
    @Column(name = "SUC_KEY")
    private String sucKey;

    @Column(name = "CJR")
    private String cjr;

    @Column(name = "CJSJ")
    private String cjsj;

    /**
     * 文件类型   10 预约  20 考试
     */
    @Column(name = "LX")
    private String lx;

    private static final long serialVersionUID = 1L;

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

    public String getSucKey() {
        return sucKey;
    }

    public void setSucKey(String sucKey) {
        this.sucKey = sucKey;
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
     * 获取文件类型   10 预约  20 考试
     *
     * @return LX - 文件类型   10 预约  20 考试
     */
    public String getLx() {
        return lx;
    }

    /**
     * 设置文件类型   10 预约  20 考试
     *
     * @param lx 文件类型   10 预约  20 考试
     */
    public void setLx(String lx) {
        this.lx = lx;
    }

    public enum InnerColumn {
        id("ID"),
        sucKey("SUC_KEY"),
        cjr("CJR"),
        cjsj("CJSJ"),
        lx("LX");

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