package com.ldz.sys.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 消息业务表
 */
@Table(name = "sys_message_biz")
public class SysMessageBiz implements Serializable {
    /**
     * 主键
     */
    @Id
    @Column(name = "BIZ_ID")
    private String bizId;

    /**
     * 业务类型 [ZDCLK1026] 1、短信 2、微信
     */
    @Column(name = "BIZ_TYPE")
    private String bizType;
    /**
     * 模板id
     */
    @Column(name = "TEMP_ID")
    private String tempId;
    /**
     * 模板内容
     */
    @Column(name = "TEMP_CONTENT")
    private String tempContent;

    /**
     * 模板必填参数
     */
    @Column(name = "TEMP_PARAMETER")
    private String tempParameter;

    /**
     * 是否可见 0 不可见 1 可见
     */
    @Column(name = "VISIABLE")
    private String visiable;
    /**
     * 接收者的角色[ZDCLK1028] 1、学员 2、教练 3、管理员
     */
    @Column(name = "USER_ROLE")
    private String userRole;
    /**
     * 模板是否生效 1生效 0失效
     */
    @Column(name = "VALID_TYPE")
    private String validType;


    private static final long serialVersionUID = 1L;

    public String getValidType() {
        return validType;
    }

    public void setValidType(String validType) {
        this.validType = validType;
    }

    public String getTempId() {
        return tempId;
    }

    public void setTempId(String tempId) {
        this.tempId = tempId;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getTempContent() {
        return tempContent;
    }

    public void setTempContent(String tempContent) {
        this.tempContent = tempContent;
    }

    public String getTempParameter() {
        return tempParameter;
    }

    public void setTempParameter(String tempParameter) {
        this.tempParameter = tempParameter;
    }

    public String getVisiable() {
        return visiable;
    }

    public void setVisiable(String visiable) {
        this.visiable = visiable;
    }

    public enum InnerColumn {
        id("id"),
        traineeId("trainee_id"),
        traineeName("trainee_name"),
        serialNum("serial_num"),
        archivesCode("archives_code"),
        inTime("in_time"),
        outTime("out_time"),
        inputPerson("input_person"),
        cjr("cjr"),
        stype("stype"),
        cjsj("cjsj"),
        outputPerson("output_person");

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