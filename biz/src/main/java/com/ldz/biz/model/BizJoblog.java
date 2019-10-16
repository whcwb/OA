package com.ldz.biz.model;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "biz_joblog")
public class BizJoblog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 任务名称
     */
    private String job;

    /**
     * 运行时间
     */
    private String cjsj;

    /**
     * 运行时长。单位秒
     */
    private Long yxsc;

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
     * 获取任务名称
     *
     * @return job - 任务名称
     */
    public String getJob() {
        return job;
    }

    /**
     * 设置任务名称
     *
     * @param job 任务名称
     */
    public void setJob(String job) {
        this.job = job;
    }

    /**
     * 获取运行时间
     *
     * @return cjsj - 运行时间
     */
    public String getCjsj() {
        return cjsj;
    }

    /**
     * 设置运行时间
     *
     * @param cjsj 运行时间
     */
    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    /**
     * 获取运行时长。单位秒
     *
     * @return yxsc - 运行时长。单位秒
     */
    public Long getYxsc() {
        return yxsc;
    }

    /**
     * 设置运行时长。单位秒
     *
     * @param yxsc 运行时长。单位秒
     */
    public void setYxsc(Long yxsc) {
        this.yxsc = yxsc;
    }

    public enum InnerColumn {
        id("id"),
        job("job"),
        cjsj("cjsj"),
        yxsc("yxsc");

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