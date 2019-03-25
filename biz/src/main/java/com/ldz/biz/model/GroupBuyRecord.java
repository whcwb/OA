package com.ldz.biz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 团购记录表
 */
@Table(name = "group_buy_record")
public class GroupBuyRecord implements Serializable {
    /**
     * 团购编号
     */
    @Id
    @Column(name = "group_code")
    private String groupCode;

    /**
     * 团购成员
     */
    private String name;

    /**
     * 学员身份证号
     */
    @Column(name = "id_card_no")
    private String idCardNo;

    /**
     * 团购时间
     */
    @Column(name = "group_time")
    private String groupTime;

    /**
     * 是否合规
     */
    private String qualify;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 操作时间
     */
    @Column(name = "operate_time")
    private String operateTime;
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
     * 获取团购编号
     *
     * @return group_code - 团购编号
     */
    public String getGroupCode() {
        return groupCode;
    }

    /**
     * 设置团购编号
     *
     * @param groupCode 团购编号
     */
    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    /**
     * 获取团购成员
     *
     * @return name - 团购成员
     */
    public String getName() {
        return name;
    }

    /**
     * 设置团购成员
     *
     * @param name 团购成员
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取学员身份证号
     *
     * @return id_card_no - 学员身份证号
     */
    public String getIdCardNo() {
        return idCardNo;
    }

    /**
     * 设置学员身份证号
     *
     * @param idCardNo 学员身份证号
     */
    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    /**
     * 获取团购时间
     *
     * @return group_time - 团购时间
     */
    public String getGroupTime() {
        return groupTime;
    }

    /**
     * 设置团购时间
     *
     * @param groupTime 团购时间
     */
    public void setGroupTime(String groupTime) {
        this.groupTime = groupTime;
    }

    /**
     * 获取是否合规
     *
     * @return qualify - 是否合规
     */
    public String getQualify() {
        return qualify;
    }

    /**
     * 设置是否合规
     *
     * @param qualify 是否合规
     */
    public void setQualify(String qualify) {
        this.qualify = qualify;
    }

    /**
     * 获取操作人
     *
     * @return operator - 操作人
     */
    public String getOperator() {
        return operator;
    }

    /**
     * 设置操作人
     *
     * @param operator 操作人
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * 获取操作时间
     *
     * @return operate_time - 操作时间
     */
    public String getOperateTime() {
        return operateTime;
    }

    /**
     * 设置操作时间
     *
     * @param operateTime 操作时间
     */
    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public enum InnerColumn {
        groupCode("group_code"),
        name("name"),
        idCardNo("id_card_no"),
        groupTime("group_time"),
        qualify("qualify"),
        cjr("cjr"),
        cjsj("cjsj"),
        operator("operator"),
        operateTime("operate_time");

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