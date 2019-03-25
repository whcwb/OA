package com.ldz.biz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * 微信操作流水日志
 */
@Table(name = "wx_operate_log")
public class WxOperateLog implements Serializable {
    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 类型：
     1、投诉管理日志
     2、邀请回访日志
     3、教练员删除日志
     4、意见反馈日志
     */
    @Column(name = "type")
    private String type;


    /**
     * 关联ID 用于确定这条日志属于那条记录
     */
    @Column(name = "operate_id")
    private String operateId;

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

    /**
     * 机构代码
     */
    @Column(name = "jgdm")
    private String jgdm;

    /**
     * 操作状态
     */
    @Column(name = "operate_type")
    private String operatetType;

    /**
     * 操作描述
     */
    @Column(name = "operate_describe")
    private String operateDescribe;


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

    public String getOperateId() {
        return operateId;
    }

    public void setOperateId(String operateId) {
        this.operateId = operateId;
    }

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getJgdm() {
        return jgdm;
    }

    public void setJgdm(String jgdm) {
        this.jgdm = jgdm;
    }

    public String getOperatetType() {
        return operatetType;
    }

    public void setOperatetType(String operatetType) {
        this.operatetType = operatetType;
    }

    public String getOperateDescribe() {
        return operateDescribe;
    }

    public void setOperateDescribe(String operateDescribe) {
        this.operateDescribe = operateDescribe;
    }

    public enum InnerColumn {
        id("id"),
        cjr("cjr"),
        type("type"),
        operateId("operate_id"),
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