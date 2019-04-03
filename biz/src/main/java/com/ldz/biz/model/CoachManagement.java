package com.ldz.biz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 教练员信息管理
 */
@Table(name = "coach_management")
public class CoachManagement implements Serializable {
    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 教练姓名
     */
    @Column(name = "coach_name")
    private String coachName;

    /**
     * 性别
     */
    private String gender;

    @Column(name = "head_img")
    private String headImg;

    /**
     * 准驾车型
     */
    @Column(name = "driving_type")
    private String drivingType;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 教练评级
     */
    @Column(name = "coach_rate")
    private String coachRate;

    /**
     * 培训科目
     */
    @Column(name = "coach_sub")
    private String coachSub;

    /**
     * 学员数量
     */
    @Column(name = "trainee_num")
    private Integer traineeNum;

    /**
     * 驾龄
     */
    @Column(name = "driving_years")
    private Integer drivingYears;

    /**
     * 驾驶证有效期开始时间
     */
    @Column(name = "license_start_time")
    private String licenseStartTime;

    /**
     * 驾驶证有效期结束时间
     */
    @Column(name = "license_end_time")
    private String licenseEndTime;

    /**
     * 教练证编号
     */
    @Column(name = "coach_num")
    private String coachNum;

    /**
     * 教练证开始时间
     */
    @Column(name = "coach_start_time")
    private String coachStartTime;

    /**
     * 教练证结束时间
     */
    @Column(name = "coach_end_time")
    private String coachEndTime;

    /**
     * 教练状态
     */
    @Column(name = "coach_status")
    private String coachStatus;

    /**
     * 档案编号
     */
    @Column(name = "record_num")
    private String recordNum;

    /**
     * 微信openId
     */
    @Column(name = "open_id")
    private String openId;

    /**
     * 机构代码
     */
    @Column(name = "jgdm")
    private String jgdm;

    /**
     * 机构名称
     */
    @Column(name = "jgmc")
    private String jgmc;

    /**
     * 所属区域
     */
    private String area;

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
     * 密码
     * @return
     */
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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
    /**
     * 备注
     */
    private String remark;

    /**
     * 操作人
     */
    @Column(name = "operater")
    private String operater;

    /**
     * 操作时间
     */
    @Column(name = "operate_time")
    private String operateTime;

    /**
     * 身份证号
     * @return
     */
    @Column(name = "id_card_no")
    private String idCardNo;

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getOperater() {
        return operater;
    }

    public void setOperater(String operater) {
        this.operater = operater;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    private static final long serialVersionUID = 1L;

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
     * 获取教练姓名
     *
     * @return coach_name - 教练姓名
     */
    public String getCoachName() {
        return coachName;
    }

    /**
     * 设置教练姓名
     *
     * @param coachName 教练姓名
     */
    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    /**
     * 获取性别
     *
     * @return gender - 性别
     */
    public String getGender() {
        return gender;
    }

    /**
     * 设置性别
     *
     * @param gender 性别
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return head_img
     */
    public String getHeadImg() {
        return headImg;
    }

    /**
     * @param headImg
     */
    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    /**
     * 获取准驾车型
     *
     * @return driving_type - 准驾车型
     */
    public String getDrivingType() {
        return drivingType;
    }

    /**
     * 设置准驾车型
     *
     * @param drivingType 准驾车型
     */
    public void setDrivingType(String drivingType) {
        this.drivingType = drivingType;
    }

    /**
     * 获取手机号码
     *
     * @return phone - 手机号码
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置手机号码
     *
     * @param phone 手机号码
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取教练评级
     *
     * @return coach_rate - 教练评级
     */
    public String getCoachRate() {
        return coachRate;
    }

    /**
     * 设置教练评级
     *
     * @param coachRate 教练评级
     */
    public void setCoachRate(String coachRate) {
        this.coachRate = coachRate;
    }

    /**
     * 获取培训科目
     *
     * @return coach_sub - 培训科目
     */
    public String getCoachSub() {
        return coachSub;
    }

    /**
     * 设置培训科目
     *
     * @param coachSub 培训科目
     */
    public void setCoachSub(String coachSub) {
        this.coachSub = coachSub;
    }

    /**
     * 获取学员数量
     *
     * @return trainee_num - 学员数量
     */
    public Integer getTraineeNum() {
        return traineeNum;
    }

    /**
     * 设置学员数量
     *
     * @param traineeNum 学员数量
     */
    public void setTraineeNum(Integer traineeNum) {
        this.traineeNum = traineeNum;
    }

    /**
     * 获取驾龄
     *
     * @return driving_years - 驾龄
     */
    public Integer getDrivingYears() {
        return drivingYears;
    }

    /**
     * 设置驾龄
     *
     * @param drivingYears 驾龄
     */
    public void setDrivingYears(Integer drivingYears) {
        this.drivingYears = drivingYears;
    }

    /**
     * 获取驾驶证有效期开始时间
     *
     * @return license_start_time - 驾驶证有效期开始时间
     */
    public String getLicenseStartTime() {
        return licenseStartTime;
    }

    /**
     * 设置驾驶证有效期开始时间
     *
     * @param licenseStartTime 驾驶证有效期开始时间
     */
    public void setLicenseStartTime(String licenseStartTime) {
        this.licenseStartTime = licenseStartTime;
    }

    /**
     * 获取驾驶证有效期结束时间
     *
     * @return license_end_time - 驾驶证有效期结束时间
     */
    public String getLicenseEndTime() {
        return licenseEndTime;
    }

    /**
     * 设置驾驶证有效期结束时间
     *
     * @param licenseEndTime 驾驶证有效期结束时间
     */
    public void setLicenseEndTime(String licenseEndTime) {
        this.licenseEndTime = licenseEndTime;
    }

    /**
     * 获取教练证编号
     *
     * @return coach_num - 教练证编号
     */
    public String getCoachNum() {
        return coachNum;
    }

    /**
     * 设置教练证编号
     *
     * @param coachNum 教练证编号
     */
    public void setCoachNum(String coachNum) {
        this.coachNum = coachNum;
    }

    /**
     * 获取教练证开始时间
     *
     * @return coach_start_time - 教练证开始时间
     */
    public String getCoachStartTime() {
        return coachStartTime;
    }

    /**
     * 设置教练证开始时间
     *
     * @param coachStartTime 教练证开始时间
     */
    public void setCoachStartTime(String coachStartTime) {
        this.coachStartTime = coachStartTime;
    }

    /**
     * 获取教练证结束时间
     *
     * @return coach_end_time - 教练证结束时间
     */
    public String getCoachEndTime() {
        return coachEndTime;
    }

    /**
     * 设置教练证结束时间
     *
     * @param coachEndTime 教练证结束时间
     */
    public void setCoachEndTime(String coachEndTime) {
        this.coachEndTime = coachEndTime;
    }

    /**
     * 获取教练状态
     *
     * @return coach_status - 教练状态
     */
    public String getCoachStatus() {
        return coachStatus;
    }

    /**
     * 设置教练状态
     *
     * @param coachStatus 教练状态
     */
    public void setCoachStatus(String coachStatus) {
        this.coachStatus = coachStatus;
    }

    /**
     * 获取档案编号
     *
     * @return record_num - 档案编号
     */
    public String getRecordNum() {
        return recordNum;
    }

    /**
     * 设置档案编号
     *
     * @param recordNum 档案编号
     */
    public void setRecordNum(String recordNum) {
        this.recordNum = recordNum;
    }

    /**
     * 获取微信openId
     *
     * @return open_id - 微信openId
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * 设置微信openId
     *
     * @param openId 微信openId
     */
    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getJgdm() {
        return jgdm;
    }

    public void setJgdm(String jgdm) {
        this.jgdm = jgdm;
    }

    public String getJgmc() {
        return jgmc;
    }

    public void setJgmc(String jgmc) {
        this.jgmc = jgmc;
    }

    /**
     * 获取所属区域
     *
     * @return area - 所属区域
     */
    public String getArea() {
        return area;
    }

    /**
     * 设置所属区域
     *
     * @param area 所属区域
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public enum InnerColumn {
        id("id"),
        coachName("coach_name"),
        gender("gender"),
        headImg("head_img"),
        drivingType("driving_type"),
        phone("phone"),
        coachRate("coach_rate"),
        coachSub("coach_sub"),
        traineeNum("trainee_num"),
        drivingYears("driving_years"),
        licenseStartTime("license_start_time"),
        licenseEndTime("license_end_time"),
        coachNum("coach_num"),
        coachStartTime("coach_start_time"),
        coachEndTime("coach_end_time"),
        coachStatus("coach_status"),
        recordNum("record_num"),
        openId("open_id"),
        jgdm("jgdm"),
        jgmc("jgmc"),
        area("area"),
        cjr("cjr"),
        cjsj("cjsj"),
        idCardNo("id_card_no"),
        remark("remark");

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