package com.ldz.biz.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * 好友邀请
 */
@Table(name = "invite_friends")
public class InviteFriends implements Serializable {
    /**
     * 主键
     */
    @Id
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
     * 学员电话
     */
    @Column(name = "trainee_phone")
    private String traineePhone;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private String createTime;

    /**
     * 被邀请人姓名
     */
    @Column(name = "invite_friend_name")
    private String inviteFriendName;

    /**
     * 被邀请人电话
     */
    @Column(name = "invite_friend_phone")
    private String inviteFriendPhone;

    /**
     * 被邀请人证件号
     */
    @Column(name = "invite_friend_no")
    private String inviteFriendNo;

    /**
     * 下级学员ID
     */
    @Column(name = "invite_friend_id")
    private String inviteFriendId;

    /**
     * 回访状态 [ZDCLK1022]   0、待回访   2、被邀请人没有报名意向  3、跟踪回访中
     */
    private String status;
    /**
     * 回访描述   call back
     */
    @Column(name = "BACK_DESCRIBE")
    private String backDescribe;

    /**
     * 回访人   call back
     */
    @Column(name = "REVISIT_USER")
    private String revisitUser;
    /**
     * 回访时间   call back
     */
    @Column(name = "REVISIT_DATE")
    private String revisitDate;

    public String getTraineePhone() {
        return traineePhone;
    }

    public void setTraineePhone(String traineePhone) {
        this.traineePhone = traineePhone;
    }

    public String getRevisitUser() {
        return revisitUser;
    }

    public void setRevisitUser(String revisitUser) {
        this.revisitUser = revisitUser;
    }

    public String getRevisitDate() {
        return revisitDate;
    }

    public void setRevisitDate(String revisitDate) {
        this.revisitDate = revisitDate;
    }

    private static final long serialVersionUID = 1L;

    public String getBackDescribe() {
        return backDescribe;
    }

    public void setBackDescribe(String backDescribe) {
        this.backDescribe = backDescribe;
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
     * 获取被邀请人姓名
     *
     * @return invite_friend_name - 被邀请人姓名
     */
    public String getInviteFriendName() {
        return inviteFriendName;
    }

    /**
     * 设置被邀请人姓名
     *
     * @param inviteFriendName 被邀请人姓名
     */
    public void setInviteFriendName(String inviteFriendName) {
        this.inviteFriendName = inviteFriendName;
    }

    /**
     * 获取被邀请人电话
     *
     * @return invite_friend_phone - 被邀请人电话
     */
    public String getInviteFriendPhone() {
        return inviteFriendPhone;
    }

    /**
     * 设置被邀请人电话
     *
     * @param inviteFriendPhone 被邀请人电话
     */
    public void setInviteFriendPhone(String inviteFriendPhone) {
        this.inviteFriendPhone = inviteFriendPhone;
    }

    /**
     * 获取被邀请人证件号
     *
     * @return invite_friend_no - 被邀请人证件号
     */
    public String getInviteFriendNo() {
        return inviteFriendNo;
    }

    /**
     * 设置被邀请人证件号
     *
     * @param inviteFriendNo 被邀请人证件号
     */
    public void setInviteFriendNo(String inviteFriendNo) {
        this.inviteFriendNo = inviteFriendNo;
    }

    /**
     * 获取下级学员ID
     *
     * @return invite_friend_id - 下级学员ID
     */
    public String getInviteFriendId() {
        return inviteFriendId;
    }

    /**
     * 设置下级学员ID
     *
     * @param inviteFriendId 下级学员ID
     */
    public void setInviteFriendId(String inviteFriendId) {
        this.inviteFriendId = inviteFriendId;
    }

    /**
     * 获取回访状态
     *
     * @return status - 回访状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置回访状态
     *
     * @param status 回访状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    public enum InnerColumn {
        id("id"),
        traineeId("trainee_id"),
        traineeName("trainee_name"),
        createTime("create_time"),
        inviteFriendName("invite_friend_name"),
        inviteFriendPhone("invite_friend_phone"),
        inviteFriendNo("invite_friend_no"),
        inviteFriendId("invite_friend_id"),
        status("status");

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