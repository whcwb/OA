package com.ldz.biz.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 微信菜单用户关系表
 */
@Table(name = "wx_module_sys")
public class WxModuleSys implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    /**
     * 菜单id
     */
    @Column(name = "module_id")
    private String moduleId;
    /**
     * 用户id
     */
    @Column(name = "yh_id")
    private String yhId;

    private String cjsj;

    private String cjr;

    private static final long serialVersionUID = 1L;
    @Transient
    private String moduleIds;

    public String getModuleIds() {
        return moduleIds;
    }

    public void setModuleIds(String moduleIds) {
        this.moduleIds = moduleIds;
    }

    /**
     * @return id
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
     * @return module_id
     */
    public String getModuleId() {
        return moduleId;
    }

    /**
     * @param moduleId
     */
    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    /**
     * @return yh_id
     */
    public String getYhId() {
        return yhId;
    }

    /**
     * @param yhId
     */
    public void setYhId(String yhId) {
        this.yhId = yhId;
    }

    /**
     * @return cjsj
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
     * @return cjr
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

    public enum InnerColumn {
        id("id"),
        moduleId("module_id"),
        yhId("yh_id"),
        cjsj("cjsj"),
        cjr("cjr");

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