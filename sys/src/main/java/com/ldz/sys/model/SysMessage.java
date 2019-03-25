package com.ldz.sys.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "SYS_MESSAGE")
public class SysMessage implements Serializable {
    /**
     * 主键
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 创建时间
     */
    @Column(name = "CREATION_TIME")
    private Date creationTime;

    /**
     * 短信正文
     */
    @Column(name = "MESSAGE")
    private String message;
    /**
     * 参数正文
     */
    @Column(name = "PARAMETER_BODY")
    private String parameterBody;

    /**
     * 下发消息类型 [ZDCLK1026] 1、短信 2、微信 3、站内信
     */
    @Column(name = "TYPE")
    private String type;
    /**
     * 标题
     */
    @Column(name = "TITLE")
    private String title;
    /**
     * 消息发送状态 [ZDCLK1027]0、未发送 1、已发送  2、发送失败
     */
    @Column(name = "STATUS")
    private String status;
    /**
     * 发送次数
     */
    @Column(name = "SEND_COUNT")
    private Integer sendCount;
    /**
     * 发送时间
     */
    @Column(name = "SEND_TIME")
    private Date sendTime;
    /**
     * 接收方编号(短信是手机号、微信是open_id)
     */
    @Column(name = "SENDEE_CODE")
    private String sendeeCode;
    /**
     * 业务编号(操作业务)
     * xy001  学员下发短信
     */
    @Column(name = "BIZ_ID")
    private String bizId;

    /**
     * 备注
     */
    @Column(name = "REMARK")
    private String remark;
    /**
     * 接收者ID
     */
    @Column(name = "USER_ID")
    private String userId;
    /**
     * 接收者姓名
     */
    @Column(name = "USER_NAME")
    private String userName;
    /**
     * 接收者的角色[ZDCLK1028] 1、学员 2、教练 3、管理员
     */
    @Column(name = "USER_ROLE")
    private String userRole;

    /**
     * 预约发送时间 yyyy-MM-dd HH:mm:ss
     */
    @Column(name = "ESTIMATED_SEND_TIME")
    private String estimatedSendTime;

    /**
     * 是否可见 0 不可见 1 可见  默认为1
     */
    @Column(name="visiable")
    private String visiable;

    /**
     * 模板id
     * @return
     */
    @Column(name="TEMP_ID")
    private String tempId;

    public String getParameterBody() {
        return parameterBody;
    }

    public void setParameterBody(String parameterBody) {
        this.parameterBody = parameterBody;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSendCount() {
        return sendCount;
    }

    public void setSendCount(Integer sendCount) {
        this.sendCount = sendCount;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getEstimatedSendTime() {
        return estimatedSendTime;
    }

    public void setEstimatedSendTime(String estimatedSendTime) {
        this.estimatedSendTime = estimatedSendTime;
    }

    public String getSendeeCode() {
        return sendeeCode;
    }

    public void setSendeeCode(String sendeeCode) {
        this.sendeeCode = sendeeCode;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getVisiable() {
        return visiable;
    }

    public void setVisiable(String visiable) {
        this.visiable = visiable;
    }

    public String getTempId() {
        return tempId;
    }

    public void setTempId(String tempId) {
        this.tempId = tempId;
    }

    public enum InnerColumn {
        id("ID"),
        creationTime("CREATION_TIME"),
        message("MESSAGE"),
        type("TYPE"),
        title("TITLE"),
        status("STATUS"),
        sendCount("SEND_COUNT"),
        sendTime("SEND_TIME"),
        visiable("VISIABLE"),
        userId("USER_ID"),
        estimatedSendTime("ESTIMATED_SEND_TIME"),
        tempId("TEMP_ID"),
        sendeeCode("SENDEE_CODE");

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