package com.ldz.sys.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 平台用户表
 */
@Getter
@Setter
@Table(name = "SYS_CLK_PTYH")
public class SysYh implements Serializable {
    /**
     * 用户ID
     */
    @Id
    @Column(name = "YHID")
    private String yhid;
    /**
     * 账户
     */
    @Column(name = "ZH")
    private String zh;
    /**
     * 密码
     */
    @Column(name = "MM")
    private String mm;
    /**
     * 手机号
     */
    @Column(name = "SJH")
    private String sjh;
    /**
     * 创建人
     */
    @Column(name = "CJR")
    private String cjr;
    /**
     * 创建时间
     */
    @Column(name = "CJSJ")
    private Date cjsj;
    /**
     * 修改人
     */
    @Column(name = "XGR")
    private String xgr;
    /**
     * 修改时间
     */
    @Column(name = "XGSJ")
    private Date xgsj;
    /**
     * 状态
     */
    @Column(name = "ZT")
    private String zt;
    /**
     * 机构代码
     */
    @Column(name = "JGDM")
    private String jgdm;
    /**
     * 用户姓名
     */
    @Column(name = "XM")
    private String xm;
    /**
     * 类型
     */
    @Column(name = "LX")
    private String lx;
    /**
     * 性别
     */
    @Column(name = "XB")
    private String xb;
    /**
     * 证件号码
     */
    @Column(name = "ZJHM")
    private String zjhm;
    /**
     * 密码有效期
     */
    @Column(name = "MMYXQ")
    private Date mmyxq;
    /**
     * 职务
     */
    @Column(name = "ZW")
    private String zw;
    /**
     * 角色id
     */
    @Column(name = "js_id")
    private String roleId;

    @Column(name = "JGDMS")
    private String jgdms;
    @Column(name = "login_type")
    private String loginType; // 1-验证码 2-PKI登录
    //UKey设备ID
    @Column(length = 64)
    private String ukey;
    //UKey主密钥，保存在增强密钥一中
    @Column(length = 64)
    private String ukeyPwd;

    private static final long serialVersionUID = 1L;


    public enum InnerColumn {
        yhid("YHID"),
        zh("ZH"),
        mm("MM"),
        sjh("SJH"),
        cjr("CJR"),
        cjsj("CJSJ"),
        xgr("XGR"),
        xgsj("XGSJ"),
        zt("ZT"),
        jgdm("JGDM"),
        xm("XM"),
        lx("LX"),
        ukey("ukey"),
        roleId("js_id"),
        xb("XB"),
        zjhm("ZJHM"),
        mmyxq("MMYXQ"),
        zw("ZW"),
        jgdms("JGDMS");

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