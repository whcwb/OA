package com.ldz.biz.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 意见反馈（针对驾校的）
 */
@Table(name = "feed_back")
public class FeedBack implements Serializable {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 学员ID
     */
    @Column(name = "trainee_id")
    private String traineeId;

    /**
     * 学员姓名
     */
    @Column(name = "trainee_name")
    private String traineeName;

    /**
     * 学员头像
     */
    @Column(name = "head_img")
    private String headImg;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private String createTime;

    /**
     * 意见反馈内容
     */
    private String content;

    /**
     * 反馈回复(驾校，对这条反馈的回复)
     */
    private String reply;
    /**
     * 处理人
     */
    @Column(name="OPERATE_TIME")
    private String  operateTime;
    /**
     * 处理时间
     */
    @Column(name="OPERATE_USER")
    private String  operateUser;

    /**
     * 处理人姓名
     */
    @Column(name="OPERATE_USER_NAME")
    private String  operateUserName;

    private static final long serialVersionUID = 1L;

    public String getOperateUserName() {
        return operateUserName;
    }

    public void setOperateUserName(String operateUserName) {
        this.operateUserName = operateUserName;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperateUser() {
        return operateUser;
    }

    public void setOperateUser(String operateUser) {
        this.operateUser = operateUser;
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

    /**
     * 获取学员ID
     *
     * @return trainee_id - 学员ID
     */
    public String getTraineeId() {
        return traineeId;
    }

    /**
     * 设置学员ID
     *
     * @param traineeId 学员ID
     */
    public void setTraineeId(String traineeId) {
        this.traineeId = traineeId;
    }

    /**
     * 获取学员姓名
     *
     * @return trainee_name - 学员姓名
     */
    public String getTraineeName() {
        return traineeName;
    }

    /**
     * 设置学员姓名
     *
     * @param traineeName 学员姓名
     */
    public void setTraineeName(String traineeName) {
        this.traineeName = traineeName;
    }

    /**
     * 获取学员头像
     *
     * @return head_img - 学员头像
     */
    public String getHeadImg() {
        return headImg;
    }

    /**
     * 设置学员头像
     *
     * @param headImg 学员头像
     */
    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取意见反馈内容
     *
     * @return content - 意见反馈内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置意见反馈内容
     *
     * @param content 意见反馈内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取反馈回复(驾校，对这条反馈的回复)
     *
     * @return reply - 反馈回复(驾校，对这条反馈的回复)
     */
    public String getReply() {
        return reply;
    }

    /**
     * 设置反馈回复(驾校，对这条反馈的回复)
     *
     * @param reply 反馈回复(驾校，对这条反馈的回复)
     */
    public void setReply(String reply) {
        this.reply = reply;
    }

    public enum InnerColumn {
        id("id"),
        traineeId("trainee_id"),
        traineeName("trainee_name"),
        headImg("head_img"),
        createTime("create_time"),
        content("content"),
        reply("reply");

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