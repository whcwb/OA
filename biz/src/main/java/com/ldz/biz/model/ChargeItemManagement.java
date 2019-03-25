package com.ldz.biz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * 收费项管理表
 */
@Table(name = "charge_item_management")
public class ChargeItemManagement implements Serializable {
    /**
     * 唯一标识
     */
    @Id
    private String id;

    /**
     * 机构名称
     */
    @Column(name = "jgmc")
    private String jgmc;

    /**
     * 收费项名
     */
    @Column(name = "charge_name")
    private String chargeName;

    /**
     * 车型
     */
    @Column(name = "car_type")
    private String carType;

    @Transient
    private List<ChargeInstitutionManage> institutions;

    /**
     * 金额
     */
    private Integer amount;

    /**
     * 收支类型
     */
    @Column(name = "in_out_type")
    private String inOutType;


    /**
     * 状态
     */
    private String status;

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
     * 收费代码
     */
    @Column(name = "charge_code")
    private String chargeCode;

    @Transient
    private  List<String> jgdms;

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


    public List<ChargeInstitutionManage> getInstitutions() {
        return institutions;
    }

    public void setInstitutions(List<ChargeInstitutionManage> institutions) {
        this.institutions = institutions;
    }

    /**
     * 获取唯一标识
     *
     * @return id - 唯一标识
     */
    public String getId() {
        return id;
    }

    /**
     * 设置唯一标识
     *
     * @param id 唯一标识
     */
    public void setId(String id) {
        this.id = id;
    }

    public String getJgmc() {
        return jgmc;
    }

    public void setJgmc(String jgmc) {
        this.jgmc = jgmc;
    }

    /**
     * 获取收费项名
     *
     * @return charge_name - 收费项名
     */
    public String getChargeName() {
        return chargeName;
    }

    /**
     * 设置收费项名
     *
     * @param chargeName 收费项名
     */
    public void setChargeName(String chargeName) {
        this.chargeName = chargeName;
    }

    /**
     * 获取车型
     *
     * @return car_type - 车型
     */
    public String getCarType() {
        return carType;
    }

    /**
     * 设置车型
     *
     * @param carType 车型
     */
    public void setCarType(String carType) {
        this.carType = carType;
    }

    /**
     * 获取金额
     *
     * @return amount - 金额
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * 设置金额
     *
     * @param amount 金额
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * 获取收支类型
     *
     * @return in_out_type - 收支类型
     */
    public String getInOutType() {
        return inOutType;
    }

    /**
     * 设置收支类型
     *
     * @param inOutType 收支类型
     */
    public void setInOutType(String inOutType) {
        this.inOutType = inOutType;
    }

    /**
     * 获取状态
     *
     * @return status - 状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态
     *
     * @param status 状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    public String getChargeCode() {
        return chargeCode;
    }

    public void setChargeCode(String chargeCode) {
        this.chargeCode = chargeCode;
    }

    public enum InnerColumn {
        id("id"),
        jgmc("jgmc"),
        chargeName("charge_name"),
        cjr("cjr"),
        cjsj("cjsj"),
        carType("car_type"),
        amount("amount"),
        inOutType("in_out_type"),
        chargeCode("charge_code"),
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