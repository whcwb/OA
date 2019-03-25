package com.ldz.biz.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 *  客户端 图片轮播表
 */
@Table(name = "pic_rotation")
public class PicRotation implements Serializable {
    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     *  客户端类型 [ZDCLK1021]   1、学员端 2、教练端   必填
     */
    private String type;

    /**
     * 文件路径 
     */
    @Column(name = "pic_url")
    private String picUrl;

    /**
     * 图片跳转url
     */
    @Column(name = "jump_url")
    private String jumpUrl;

    /**
     * 图片排序 默认为1
     */
    @Column(name = "pic_sort")
    private Integer picSort;

    @Column(name = "cjsj")
    private String cjsj;

    /**
     * 创建人
     */
    @Column(name = "CJR")
    private String cjr;
    /**
     * 是否生效[ZDCLK1025] 1生效 0失效
     */
    @Column(name = "effective")
    private String effective ;

    private static final long serialVersionUID = 1L;

    public String getEffective() {
        return effective;
    }

    public void setEffective(String effective) {
        this.effective = effective;
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
     * 获取标题
     *
     * @return title - 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取客户端类型 1、学员端 2、教练端   必填
     *
     * @return type - 客户端类型 1、学员端 2、教练端   必填
     */
    public String getType() {
        return type;
    }

    /**
     * 设置客户端类型 1、学员端 2、教练端   必填
     *
     * @param type 客户端类型 1、学员端 2、教练端   必填
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取文件路径 
     *
     * @return pic_url - 文件路径 
     */
    public String getPicUrl() {
        return picUrl;
    }

    /**
     * 设置文件路径 
     *
     * @param picUrl 文件路径 
     */
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    /**
     * 获取图片跳转url
     *
     * @return jump_url - 图片跳转url
     */
    public String getJumpUrl() {
        return jumpUrl;
    }

    /**
     * 设置图片跳转url
     *
     * @param jumpUrl 图片跳转url
     */
    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    /**
     * 获取图片排序 默认为1
     *
     * @return pic_sort - 图片排序 默认为1
     */
    public Integer getPicSort() {
        return picSort;
    }

    /**
     * 设置图片排序 默认为1
     *
     * @param picSort 图片排序 默认为1
     */
    public void setPicSort(Integer picSort) {
        this.picSort = picSort;
    }

    public enum InnerColumn {
        id("id"),
        title("title"),
        type("type"),
        picUrl("pic_url"),
        jumpUrl("jump_url"),
        picSort("pic_sort");

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