package com.ldz.biz.model;

import com.ldz.sys.model.SysYh;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * 优惠项管理表
 */
@Table(name = "reduce_management")
public class ReduceManagement implements Serializable {
    /**
     * 主键
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 优惠项名称
     */
    @Column(name = "reduce_name")
    private String reduceName;

    /**
     * 优惠金额
     */
    @Column(name = "reduce_amount")
    private Integer reduceAmount;

    /**
     * 备注
     */
    @Column(name = "REMARK")
    private String remark;

    /**
     * 团购限制人数
     */
    @Column(name = "GROUP_NUM")
    private Integer groupNum;

    /**
     * 创建人
     */
    @Column(name = "CJR")
    private String cjr;

    /**
     * 创建时间
     */
    @Column(name = "CJSJ")
    private String cjsj;

    private SysYh sysYh;

    private List<SysYh> sysYhs;

    public List<SysYh> getSysYhs() {
        return sysYhs;
    }

    public void setSysYhs(List<SysYh> sysYhs) {
        this.sysYhs = sysYhs;
    }

    public SysYh getSysYh() {
        return sysYh;
    }

    public void setSysYh(SysYh sysYh) {
        this.sysYh = sysYh;
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
    @Transient
    private  List<ReduceInstitution> institutions;


    public List<ReduceInstitution> getInstitutions() {
        return institutions;
    }

    public void setInstitutions(List<ReduceInstitution> institutions) {
        this.institutions = institutions;
    }

    /**
     * 优惠开始时间
     */
    @Column(name = "REDUCE_START_TIME")
    private String reduceStartTime;

    /**
     * 优惠结束时间
     */
    @Column(name = "REDUCE_END_TIME")
    private String reduceEndTime;

    /**
     * 机构名称
     */
    @Column(name = "JGMC")
    private String jgmc;

    /**
     * 机构代码
     */
    @Column(name = "JGDM")
    private String jgdm;

    /**
     * 审核级别
     */
    @Column(name = "VERIFY_LEVEL")
    private String verifyLevel;

    /**
     * 审核人
     */
    @Column(name = "VERIFIER")
    private String verifier;

    /**
     * 操作人
     */
    @Column(name = "OPERATER")
    private String operater;

    /**
     * 操作时间
     */
    @Column(name = "OPERATE_TIME")
    private String operateTime;
    private static final long serialVersionUID = 1L;

    @Transient
    private List<String> jgdms;

    public List<String> getJgdms() {
        return jgdms;
    }

    public void setJgdms(List<String> jgdms) {
        this.jgdms = jgdms;
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
     * 获取优惠项名称
     *
     * @return reduce_name - 优惠项名称
     */
    public String getReduceName() {
        return reduceName;
    }

    /**
     * 设置优惠项名称
     *
     * @param reduceName 优惠项名称
     */
    public void setReduceName(String reduceName) {
        this.reduceName = reduceName;
    }

    /**
     * 获取优惠金额
     *
     * @return reduce_amount - 优惠金额
     */
    public Integer getReduceAmount() {
        return reduceAmount;
    }

    /**
     * 设置优惠金额
     *
     * @param reduceAmount 优惠金额
     */
    public void setReduceAmount(Integer reduceAmount) {
        this.reduceAmount = reduceAmount;
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

    /**
     * 获取团购限制人数
     *
     * @return group_num - 团购限制人数
     */
    public Integer getGroupNum() {
        return groupNum;
    }

    /**
     * 设置团购限制人数
     *
     * @param groupNum 团购限制人数
     */
    public void setGroupNum(Integer groupNum) {
        this.groupNum = groupNum;
    }

    /**
     * 获取优惠开始时间
     *
     * @return reduce_start_time - 优惠开始时间
     */
    public String getReduceStartTime() {
        return reduceStartTime;
    }

    /**
     * 设置优惠开始时间
     *
     * @param reduceStartTime 优惠开始时间
     */
    public void setReduceStartTime(String reduceStartTime) {
        this.reduceStartTime = reduceStartTime;
    }

    /**
     * 获取优惠结束时间
     *
     * @return reduce_end_time - 优惠结束时间
     */
    public String getReduceEndTime() {
        return reduceEndTime;
    }

    /**
     * 设置优惠结束时间
     *
     * @param reduceEndTime 优惠结束时间
     */
    public void setReduceEndTime(String reduceEndTime) {
        this.reduceEndTime = reduceEndTime;
    }

    public String getJgmc() {
        return jgmc;
    }

    public void setJgmc(String jgmc) {
        this.jgmc = jgmc;
    }

    public String getJgdm() {
        return jgdm;
    }

    public void setJgdm(String jgdm) {
        this.jgdm = jgdm;
    }

    /**
     * 获取审核级别
     *
     * @return verify_level - 审核级别
     */
    public String getVerifyLevel() {
        return verifyLevel;
    }

    /**
     * 设置审核级别
     *
     * @param verifyLevel 审核级别
     */
    public void setVerifyLevel(String verifyLevel) {
        this.verifyLevel = verifyLevel;
    }

    /**
     * 获取审核人
     *
     * @return verifier - 审核人
     */
    public String getVerifier() {
        return verifier;
    }

    /**
     * 设置审核人
     *
     * @param verifier 审核人
     */
    public void setVerifier(String verifier) {
        this.verifier = verifier;
    }

    public enum InnerColumn {
        id("id"),
        reduceName("reduce_name"),
        reduceAmount("reduce_amount"),
        remark("remark"),
        groupNum("group_num"),
        cjr("cjr"),
        cjsj("cjsj"),
        reduceStartTime("reduce_start_time"),
        reduceEndTime("reduce_end_time"),
        jgmc("jgmc"),
        jgdm("jgdm"),
        verifyLevel("verify_level"),
        verifier("verifier");

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