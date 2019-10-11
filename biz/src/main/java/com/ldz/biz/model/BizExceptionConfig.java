package com.ldz.biz.model;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "biz_exception_config")
public class BizExceptionConfig implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 异常名称
     */
    private String name;

    /**
     * 异常代码类型
     */
    private String code;

    /**
     * 计划任务判断天数。几天前使用负数即可
     */
    private Integer days;

    /**
     * 异常具体描述信息
     */
    private String bz;

    /**
     * 创建时间
     */
    private String cjsj;

    /**
     * 创建人
     */
    private String cjr;

    /**
     * 机构代码
     */
    private String jgdm;

    /**
     * 机构名称
     */
    private String jgmc;

    /**
     * 备注1
     */
    private String bz1;

    /**
     * 备注2
     */
    private String bz2;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取异常名称
     *
     * @return name - 异常名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置异常名称
     *
     * @param name 异常名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取异常代码类型
     *
     * @return code - 异常代码类型
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置异常代码类型
     *
     * @param code 异常代码类型
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取计划任务判断天数。几天前使用负数即可
     *
     * @return days - 计划任务判断天数。几天前使用负数即可
     */
    public Integer getDays() {
        return days;
    }

    /**
     * 设置计划任务判断天数。几天前使用负数即可
     *
     * @param days 计划任务判断天数。几天前使用负数即可
     */
    public void setDays(Integer days) {
        this.days = days;
    }

    /**
     * 获取异常具体描述信息
     *
     * @return bz - 异常具体描述信息
     */
    public String getBz() {
        return bz;
    }

    /**
     * 设置异常具体描述信息
     *
     * @param bz 异常具体描述信息
     */
    public void setBz(String bz) {
        this.bz = bz;
    }

    /**
     * 获取创建时间
     *
     * @return cjsj - 创建时间
     */
    public String getCjsj() {
        return cjsj;
    }

    /**
     * 设置创建时间
     *
     * @param cjsj 创建时间
     */
    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    /**
     * 获取创建人
     *
     * @return cjr - 创建人
     */
    public String getCjr() {
        return cjr;
    }

    /**
     * 设置创建人
     *
     * @param cjr 创建人
     */
    public void setCjr(String cjr) {
        this.cjr = cjr;
    }

    /**
     * 获取机构代码
     *
     * @return jgdm - 机构代码
     */
    public String getJgdm() {
        return jgdm;
    }

    /**
     * 设置机构代码
     *
     * @param jgdm 机构代码
     */
    public void setJgdm(String jgdm) {
        this.jgdm = jgdm;
    }

    /**
     * 获取机构名称
     *
     * @return jgmc - 机构名称
     */
    public String getJgmc() {
        return jgmc;
    }

    /**
     * 设置机构名称
     *
     * @param jgmc 机构名称
     */
    public void setJgmc(String jgmc) {
        this.jgmc = jgmc;
    }

    /**
     * 获取备注1
     *
     * @return bz1 - 备注1
     */
    public String getBz1() {
        return bz1;
    }

    /**
     * 设置备注1
     *
     * @param bz1 备注1
     */
    public void setBz1(String bz1) {
        this.bz1 = bz1;
    }

    /**
     * 获取备注2
     *
     * @return bz2 - 备注2
     */
    public String getBz2() {
        return bz2;
    }

    /**
     * 设置备注2
     *
     * @param bz2 备注2
     */
    public void setBz2(String bz2) {
        this.bz2 = bz2;
    }

    public enum InnerColumn {
        id("id"),
        name("name"),
        code("code"),
        days("days"),
        bz("bz"),
        cjsj("cjsj"),
        cjr("cjr"),
        jgdm("jgdm"),
        jgmc("jgmc"),
        bz1("bz1"),
        bz2("bz2");

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