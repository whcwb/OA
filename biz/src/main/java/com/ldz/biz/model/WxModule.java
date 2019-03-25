package com.ldz.biz.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 微信权限菜单
 */
@Table(name = "wx_module")
public class WxModule implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String title;

    @Column(name = "jump_url")
    private String jumpUrl;

    /**
     * 类型  1 查询 2 处理
     */
    private String type;

    private String icon;

    private String cjsj;

    private String cjr;

    /**
     * 请求接口集合
     */
    @Column(name = "req_api")
    private String reqApi;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return jump_url
     */
    public String getJumpUrl() {
        return jumpUrl;
    }

    /**
     * @param jumpUrl
     */
    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    /**
     * 获取类型  1 查询 2 处理
     *
     * @return type - 类型  1 查询 2 处理
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类型  1 查询 2 处理
     *
     * @param type 类型  1 查询 2 处理
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * @return cjsj
     */
    public String getCjsj() {
        return cjsj;
    }

    /**
     * @param cjsj
     */
    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    /**
     * @return cjr
     */
    public String getCjr() {
        return cjr;
    }

    /**
     * @param cjr
     */
    public void setCjr(String cjr) {
        this.cjr = cjr;
    }

    /**
     * 获取请求接口集合
     *
     * @return req_api - 请求接口集合
     */
    public String getReqApi() {
        return reqApi;
    }

    /**
     * 设置请求接口集合
     *
     * @param reqApi 请求接口集合
     */
    public void setReqApi(String reqApi) {
        this.reqApi = reqApi;
    }

    public enum InnerColumn {
        id("id"),
        title("title"),
        jumpUrl("jump_url"),
        type("type"),
        icon("icon"),
        cjsj("cjsj"),
        cjr("cjr"),
        reqApi("req_api");

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