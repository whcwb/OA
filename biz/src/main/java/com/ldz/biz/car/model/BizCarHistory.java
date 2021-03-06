package com.ldz.biz.car.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 车辆历史表
 */
@Table(name = "biz_car_history")
public class BizCarHistory implements Serializable {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 车牌号
     */
    private String cph;

    /**
     * 机构代码
     */
    private String jgdm;

    /**
     * 机构名称
     */
    private String jgmc;

    /**
     * 创建人
     */
    private String cjr;

    /**
     * 创建时间
     */
    private String cjsj;

    /**
     * 档案号
     */
    private String dah;

    /**
     * 所属区域
     */
    @Column(name = "car_qy")
    private String carQy;

    /**
     * 机动车所有人
     */
    @Column(name = "car_syr")
    private String carSyr;

    /**
     * 所有人_证件号码
     */
    @Column(name = "car_sry_code")
    private String srycode;

    /**
     * 所有人_身份证件类型
     */
    @Column(name = "car_sry_zj_type")
    private String srydzlx;

    /**
     * 核定载质量
     */
    private Integer hdzl;

    /**
     * 驾驶室载客数
     */
    private Integer jsszks;

    /**
     * 准牵引总质量
     */
    private Integer zqyzzl;

    /**
     * 内廓高
     */
    private Integer nkg;

    /**
     * 内廓宽
     */
    private Integer nkk;

    /**
     * 内廓长
     */
    private Integer nkc;

    /**
     * 转向形式
     */
    private String zxfs;

    /**
     * 国产/进口 字典[CD]
     */
    private String clcd;

    /**
     * 登记机关
     */
    private String djjg;

    /**
     * 培训车型  准驾车型 [ZDCLK0040] 
     */
    private String pxcx;

    /**
     * 号牌种类 车辆_号牌种类 [ZDCLK1036] 1、学牌 2、地牌 
     */
    private String hpzl;

    /**
     * 使用性质  [ZDCLK1037] 1、教练 2、非教练 3、非营运
     */
    private String syxz;

    /**
     * 车辆产权状态  1、学牌车 2、非学牌车 3、已报销车 4、已售出
     */
    @Column(name = "car_property_type")
    private String carPropertyType;

    /**
     * 车辆品牌
     */
    private String clpp;

    /**
     * 车辆类型
     */
    private String cllx;

    /**
     * 品牌型号
     */
    private String ppxh;

    /**
     * 车辆型号
     */
    private String clxh;

    /**
     * 初次登记日期
     */
    private String ccdjrq;

    /**
     * 发证日期(发证日期和初登记日期是一样的)
     */
    private String fzrq;

    /**
     * 强制报废期
     */
    private String qzbfq;

    /**
     * 保险初登日期
     */
    @Column(name = "bx_cdrq")
    private String bxCdrq;

    /**
     * 运管初登日期
     */
    @Column(name = "yy_cdrq")
    private String yyCdrq;

    /**
     * 改气初登日期
     */
    @Column(name = "gx_cdrq")
    private String gxCdrq;

    /**
     * 车辆识别码
     */
    private String clsbm;

    /**
     * 发动机号
     */
    private String fdjh;

    /**
     * 发动机型号
     */
    private String fdjxh;

    /**
     * 燃料种类 车辆燃油类型 [ZDCLK1038] 1、汽油 2、柴油
     */
    private String rlzl;

    /**
     * 排量
     */
    private Integer pl;

    /**
     * 功率
     */
    private Integer gl;

    /**
     * 制造厂名称
     */
    private String zzcmc;

    /**
     * 车身颜色
     */
    private String csys;

    /**
     * 前轮距
     */
    private Integer qlj;

    /**
     * 后轮距
     */
    private Integer hlj;

    /**
     * 轮胎数量
     */
    private Integer ltsl;

    /**
     * 轮胎规格
     */
    private String ltgg;

    /**
     * 板簧片数
     */
    private Integer bhps;

    /**
     * 轴距
     */
    private Integer zj;

    /**
     * 外廓长
     */
    private Integer wkc;

    /**
     * 外廓宽
     */
    private Integer wkk;

    /**
     * 外廓高
     */
    private Integer wkg;

    /**
     * 总质量
     */
    private Integer zzl;

    /**
     * 核定载客
     */
    private Integer hdzk;

    /**
     * 保用性质
     */
    private String byxz;

    /**
     * 车辆获得方式
     */
    private String clhdfs;

    /**
     * 出厂日期
     */
    private String ccrq;

    /**
     * 发证机关
     */
    private String fzjg;

    /**
     * 使用人ID
     */
    @Column(name = "syr_id")
    private String syrId;

    /**
     * 使用人姓名
     */
    @Column(name = "syr_name")
    private String syrName;

    /**
     * 使用人所在地
     */
    @Column(name = "syr_szd")
    private String syrSzd;

    /**
     * 使用人联系方式
     */
    @Column(name = "syr_dn")
    private String syrDn;

    /**
     * 使用信息id
     */
    @Column(name = "syxx_id")
    private String syxxId;

    /**
     * 年审日期
     */
    private String nsrq;

    /**
     * 年审id
     */
    @Column(name = "ns_id")
    private String nsId;

    /**
     * 报废状态   是/否 [ZDCLK1034] 1、已报废 0 未报废
     */
    @Column(name = "bf_type")
    private String bfType;

    /**
     * 报废金额
     */
    @Column(name = "bf_fee")
    private String bfFee;

    /**
     * 报废时间
     */
    @Column(name = "bf_date")
    private String bfDate;

    /**
     * 车辆售卖时间
     */
    @Column(name = "sm_date")
    private String smDate;

    /**
     * 车辆售卖金额
     */
    @Column(name = "sm_fee")
    private String smFee;

    /**
     * 车辆产权人ID
     */
    @Column(name = "cl_cqr_id")
    private String clCqrId;

    /**
     * 车辆产权人
     */
    @Column(name = "cl_cqr")
    private String clCqr;

    /**
     * 车辆产权人电话
     */
    @Column(name = "cl_cqr_dn")
    private String clCqrDn;

    /**
     * 车辆产权人证件号
     */
    @Column(name = "cl_cqr_code")
    private String clCqrCode;

    /**
     * 车辆产权变更经办人电话
     */
    @Column(name = "cl_cqr_jbr_dn")
    private String clCqrJbrDn;

    /**
     * 车辆产权变更经办人
     */
    @Column(name = "cl_cqr_jbr")
    private String clCqrJbr;

    /**
     * 车辆产权变更备注
     */
    @Column(name = "cl_cqr_bz")
    private String clCqrBz;

    /**
     * 车辆产权变更金额
     */
    @Column(name = "cl_cqr_jl")
    private String clCqrJl;

    /**
     * 机动车登记证书状态  有/无 [ZDCLK1039] 1、有 0、无
     */
    @Column(name = "dzd_djz_type")
    private String dzdDjzType;

    /**
     * 机动车登记证书编号
     */
    @Column(name = "dzd_djz_code")
    private String dzdDjzCode;

    /**
     * 机动车登记证书 电子档路径
     */
    @Column(name = "dzd_djz_fileurl")
    private String dzdDjzFileurl;

    /**
     * 购置税完税证明状态  有/无 [ZDCLK1039] 1、有 0、无
     */
    @Column(name = "dzd_wszm_type")
    private String dzdWszmType;

    /**
     * 购置税完税证明证号
     */
    @Column(name = "dzd_wszm_code")
    private String dzdWszmCode;

    /**
     * 购置税完税证明票号
     */
    @Column(name = "dzd_wszm_ph")
    private String dzdWszmPh;

    /**
     * 购置税完税证明票号电子档路径
     */
    @Column(name = "dzd_wszm_fileurl")
    private String dzdWszmFileurl;

    /**
     * 发票状态  有/无 [ZDCLK1039] 1、有 0、无
     */
    @Column(name = "dzd_fp_type")
    private String dzdFpType;

    /**
     * 发票号码
     */
    @Column(name = "dzd_fp_code")
    private String dzdFpCode;

    /**
     * 发票号码 电子档路径
     */
    @Column(name = "dzd_fp_fileurl")
    private String dzdFpFileurl;

    /**
     * 道路运输许可证 有/无 [ZDCLK1039] 1、有 0、无
     */
    @Column(name = "dzd_drys_type")
    private String dzdDrysType;

    /**
     * 行驶证扫描件状态 有/无 [ZDCLK1039] 1、有 0、无
     */
    @Column(name = "dzd_xszfy_type")
    private String dzdXszfyType;

    /**
     * 行驶证扫描件状态 电子档路径
     */
    @Column(name = "dzd_xszfy_fileurl")
    private String dzdXszfyFileurl;

    /**
     * 运管年审 下一次过期时间
     */
    @Column(name = "warn_ygns_date")
    private String warnYgnsDate;

    /**
     * 保险 下一次过期时间
     */
    @Column(name = "warn_bx_date")
    private String warnBxDate;

    /**
     * 车辆管年审 下一次过期时间
     */
    @Column(name = "warn_nssj_date")
    private String warnNssjDate;

    /**
     * 改气年审 下一次过期时间
     */
    @Column(name = "warn_gx_date")
    private String warnGxDate;

    /**
     * 上一次运管审核时间
     */
    @Column(name = "yg_last_data")
    private String ygLastData;

    /**
     *  运管运输证号
     */
    @Column(name = "yg_yszh")
    private String ygYszh;

    /**
     * 运管 是否安装GPS  是/否 [ZDCLK1034] 
     */
    @Column(name = "yg_gps_type")
    private String ygGpsType;

    /**
     * 运管 GPS号码
     */
    @Column(name = "yg_gps_code")
    private String ygGpsCode;

    /**
     * 运管 车辆备案_运营状态 [ZDCLK1033] 营运状态1、运营 2、注销 0、未登记
     */
    @Column(name = "yg_yy_type")
    private String ygYyType;

    /**
     * 运管 1、14年上线  0、不是   是/否 [ZDCLK1034]
     */
    @Column(name = "yg_ysn_sx")
    private String ygYsnSx;

    /**
     * 运管 更新(BU列)
     */
    @Column(name = "yg_gx")
    private String ygGx;

    /**
     * 运管 轴距
     */
    @Column(name = "yg_zj")
    private Integer ygZj;

    /**
     * 运管 车辆备案_卡机安装状态 [ZDCLK1035] 卡机安装状态 1、已安装 0、未安装
     */
    @Column(name = "yg_kj_type")
    private String ygKjType;

    /**
     * 运管 卡机安装时间
     */
    @Column(name = "yg_kj_azsj")
    private String ygKjAzsj;

    /**
     * 卡机批次
     */
    @Column(name = "yg_kj_pc")
    private String ygKjPc;

    /**
     * 明涛成功新证号
     */
    @Column(name = "yg_new_code")
    private String ygNewCode;

    /**
     * 新卡机
     */
    @Column(name = "yg_new_kj")
    private String ygNewKj;

    /**
     * 上一次改气审核时间
     */
    @Column(name = "gx_last_data")
    private String gxLastData;

    /**
     * 改气合格证有效期
     */
    @Column(name = "gx_gas_ggzyxq")
    private String gxGasGgzyxq;

    /**
     * 改气 联系人电话
     */
    @Column(name = "gx_lx_dn")
    private String gxLxDn;

    /**
     * 改气 联系人
     */
    @Column(name = "gx_lxr")
    private String gxLxr;

    /**
     * 改气备注
     */
    @Column(name = "gx_gas_bz")
    private String gxGasBz;

    /**
     * 改气地点
     */
    @Column(name = "gx_gas_dd")
    private String gxGasDd;

    /**
     * 是否改气 1、已改气 0未改气
     */
    @Column(name = "gx_type")
    private String gxType;

    /**
     * 上一次保险审核时间
     */
    @Column(name = "bx_last_data")
    private String bxLastData;

    /**
     * 保险 保险日期
     */
    @Column(name = "bx_bxrq")
    private String bxBxrq;

    /**
     * 保险 保险至
     */
    @Column(name = "bx_bxz")
    private String bxBxz;

    /**
     * 保险 保险电话
     */
    @Column(name = "bx_bxdh")
    private String bxBxdh;

    /**
     * 保险 保单编号
     */
    @Column(name = "bx_bdzbbh")
    private String bxBdzbbh;

    /**
     * 保险 保单副本编号
     */
    @Column(name = "bx_bdfbbh")
    private String bxBdfbbh;

    /**
     * 保险 保单位置
     */
    @Column(name = "bx_bd_wz")
    private String bxBdWz;

    /**
     * 保险 备注
     */
    @Column(name = "bx_bz")
    private String bxBz;

    /**
     * 保单电子档路径
     */
    @Column(name = "bx_da_file")
    private String bxDaFile;

    /**
     * 投保公司
     */
    @Column(name = "bx_tbgs")
    private String bxTbgs;

    /**
     * 保单联系人
     */
    @Column(name = "bx_bxlxr")
    private String bxBxlxr;

    /**
     * 保险 保单数量
     */
    @Column(name = "bx_bd_count")
    private String bxBdCount;

    /**
     * 上一次车管年审时间
     */
    @Column(name = "ns_last_data")
    private String nsLastData;

    /**
     * 车管年审 年审至
     */
    @Column(name = "ns_nsz")
    private String nsNsz;

    /**
     * 车管年审 年审时间
     */
    @Column(name = "ns_nssj")
    private String nsNssj;

    /**
     * 车管年审 二审时间
     */
    @Column(name = "ns_essj")
    private String nsEssj;

    /**
     * 车管年审 批次
     */
    @Column(name = "ns_pc")
    private String nsPc;

    /**
     * 车管年审 驾驶员ID
     */
    @Column(name = "ns_jsyId")
    private String nsJsyid;

    /**
     * 车管年审 驾驶员姓名
     */
    @Column(name = "ns_jsyxm")
    private String nsJsyxm;

    /**
     * 车管年审 驾驶员身份证号
     */
    @Column(name = "ns_jsysfzh")
    private String nsJsysfzh;

    /**
     * 车管年审 驾驶员联系电话
     */
    @Column(name = "ns_jsylxdh")
    private String nsJsylxdh;

    /**
     * 车管年审 年审状态
     */
    @Column(name = "ns_zt")
    private String nsZt;

    /**
     * 车辆状态、ZDCLK1042  1、正常  10、车管正常  12、车管逾期未审  19、车管90天待审  18、车管60天待审  17、车管30天待审  20、运管正常  22、运管逾期未审  29、运管90天待审  28、运管60天待审  27、运管30天待审  30、改气正常  32、改气逾期未审  39、改气90天待审  40、保险正常  42、保险逾期未审  49、保险90天待审  50、报废正常  52、报废逾期未审  61、已转出  71、车牌已变更
     */
    @Column(name = "car_type")
    private String carType;

    /**
     * 保险经办人
     */
    @Column(name = "bx_jbr")
    private String bxJbr;

    /**
     * 保险经办人电话
     */
    @Column(name = "bx_jbr_dn")
    private String bxJbrDn;

    /**
     * 产权内部变更电子档案
     */
    @Column(name = "dzd_cqbg_url")
    private String dzdCqbgUrl;

    /**
     * 车牌变更 电子档案 
     */
    @Column(name = "dzd_cpbg_url")
    private String dzdCpbgUrl;

    /**
     * 车辆报废 电子档案
     */
    @Column(name = "dzd_clbf_url")
    private String dzdClbfUrl;

    /**
     * 车辆变卖 电子档案
     */
    @Column(name = "dzd_clbm_url")
    private String dzdClbmUrl;

    /**
     * 车辆保险 电子档案
     */
    @Column(name = "dzd_clb_url")
    private String dzdClbUrl;

    /**
     * 备案人ID
     */
    private String jsyid;

    /**
     * 备案人姓名
     */
    private String jsyxm;

    /**
     * 备案人身份证号
     */
    private String jsysfzh;

    /**
     * 备案人联系电话
     */
    private String jsylxdh;

    /**
     * 备案人是否采集(1已采集  0未采集)
     */
    @Column(name = "cj_type")
    private String cjType;

    /**
     * 备案人信息采集时间
     */
    private String jsycjsj;

    /**
     * 备案人信息电子档案
     */
    private String jsydzda;

    /**
     * 备案人信息备注
     */
    private String jsybz;

    /**
     * 车辆历史表（biz_car_history）主键。默认为空
     */
    @Column(name = "car_history_id")
    private String carHistoryId;

    /**
     * 车辆所有人变更时间
     */
    @Column(name = "car_syr_bgsj")
    private String carSyrBgsj;

    /**
     * 车辆所有人变更操作人
     */
    @Column(name = "car_syr_bgczr")
    private String carSyrBgczr;

    /**
     * 出厂合格证明 电子档路径
     */
    @Column(name = "dzd_drys_fileurl")
    private String dzdDrysFileurl;

    private static final long serialVersionUID = 1L;

    /**
     * 获取id
     *
     * @return id - id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取车牌号
     *
     * @return cph - 车牌号
     */
    public String getCph() {
        return cph;
    }

    /**
     * 设置车牌号
     *
     * @param cph 车牌号
     */
    public void setCph(String cph) {
        this.cph = cph;
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
     * 获取档案号
     *
     * @return dah - 档案号
     */
    public String getDah() {
        return dah;
    }

    /**
     * 设置档案号
     *
     * @param dah 档案号
     */
    public void setDah(String dah) {
        this.dah = dah;
    }

    /**
     * 获取所属区域
     *
     * @return car_qy - 所属区域
     */
    public String getCarQy() {
        return carQy;
    }

    /**
     * 设置所属区域
     *
     * @param carQy 所属区域
     */
    public void setCarQy(String carQy) {
        this.carQy = carQy;
    }

    /**
     * 获取机动车所有人
     *
     * @return car_syr - 机动车所有人
     */
    public String getCarSyr() {
        return carSyr;
    }

    /**
     * 设置机动车所有人
     *
     * @param carSyr 机动车所有人
     */
    public void setCarSyr(String carSyr) {
        this.carSyr = carSyr;
    }

    public String getSrycode() {
        return srycode;
    }

    public void setSrycode(String srycode) {
        this.srycode = srycode;
    }

    public String getSrydzlx() {
        return srydzlx;
    }

    public void setSrydzlx(String srydzlx) {
        this.srydzlx = srydzlx;
    }

    /**
     * 获取核定载质量
     *
     * @return hdzl - 核定载质量
     */
    public Integer getHdzl() {
        return hdzl;
    }

    /**
     * 设置核定载质量
     *
     * @param hdzl 核定载质量
     */
    public void setHdzl(Integer hdzl) {
        this.hdzl = hdzl;
    }

    /**
     * 获取驾驶室载客数
     *
     * @return jsszks - 驾驶室载客数
     */
    public Integer getJsszks() {
        return jsszks;
    }

    /**
     * 设置驾驶室载客数
     *
     * @param jsszks 驾驶室载客数
     */
    public void setJsszks(Integer jsszks) {
        this.jsszks = jsszks;
    }

    /**
     * 获取准牵引总质量
     *
     * @return zqyzzl - 准牵引总质量
     */
    public Integer getZqyzzl() {
        return zqyzzl;
    }

    /**
     * 设置准牵引总质量
     *
     * @param zqyzzl 准牵引总质量
     */
    public void setZqyzzl(Integer zqyzzl) {
        this.zqyzzl = zqyzzl;
    }

    /**
     * 获取内廓高
     *
     * @return nkg - 内廓高
     */
    public Integer getNkg() {
        return nkg;
    }

    /**
     * 设置内廓高
     *
     * @param nkg 内廓高
     */
    public void setNkg(Integer nkg) {
        this.nkg = nkg;
    }

    /**
     * 获取内廓宽
     *
     * @return nkk - 内廓宽
     */
    public Integer getNkk() {
        return nkk;
    }

    /**
     * 设置内廓宽
     *
     * @param nkk 内廓宽
     */
    public void setNkk(Integer nkk) {
        this.nkk = nkk;
    }

    /**
     * 获取内廓长
     *
     * @return nkc - 内廓长
     */
    public Integer getNkc() {
        return nkc;
    }

    /**
     * 设置内廓长
     *
     * @param nkc 内廓长
     */
    public void setNkc(Integer nkc) {
        this.nkc = nkc;
    }

    /**
     * 获取转向形式
     *
     * @return zxfs - 转向形式
     */
    public String getZxfs() {
        return zxfs;
    }

    /**
     * 设置转向形式
     *
     * @param zxfs 转向形式
     */
    public void setZxfs(String zxfs) {
        this.zxfs = zxfs;
    }

    /**
     * 获取国产/进口 字典[CD]
     *
     * @return clcd - 国产/进口 字典[CD]
     */
    public String getClcd() {
        return clcd;
    }

    /**
     * 设置国产/进口 字典[CD]
     *
     * @param clcd 国产/进口 字典[CD]
     */
    public void setClcd(String clcd) {
        this.clcd = clcd;
    }

    /**
     * 获取登记机关
     *
     * @return djjg - 登记机关
     */
    public String getDjjg() {
        return djjg;
    }

    /**
     * 设置登记机关
     *
     * @param djjg 登记机关
     */
    public void setDjjg(String djjg) {
        this.djjg = djjg;
    }

    /**
     * 获取培训车型  准驾车型 [ZDCLK0040] 
     *
     * @return pxcx - 培训车型  准驾车型 [ZDCLK0040] 
     */
    public String getPxcx() {
        return pxcx;
    }

    /**
     * 设置培训车型  准驾车型 [ZDCLK0040] 
     *
     * @param pxcx 培训车型  准驾车型 [ZDCLK0040] 
     */
    public void setPxcx(String pxcx) {
        this.pxcx = pxcx;
    }

    /**
     * 获取号牌种类 车辆_号牌种类 [ZDCLK1036] 1、学牌 2、地牌 
     *
     * @return hpzl - 号牌种类 车辆_号牌种类 [ZDCLK1036] 1、学牌 2、地牌 
     */
    public String getHpzl() {
        return hpzl;
    }

    /**
     * 设置号牌种类 车辆_号牌种类 [ZDCLK1036] 1、学牌 2、地牌 
     *
     * @param hpzl 号牌种类 车辆_号牌种类 [ZDCLK1036] 1、学牌 2、地牌 
     */
    public void setHpzl(String hpzl) {
        this.hpzl = hpzl;
    }

    /**
     * 获取使用性质  [ZDCLK1037] 1、教练 2、非教练 3、非营运
     *
     * @return syxz - 使用性质  [ZDCLK1037] 1、教练 2、非教练 3、非营运
     */
    public String getSyxz() {
        return syxz;
    }

    /**
     * 设置使用性质  [ZDCLK1037] 1、教练 2、非教练 3、非营运
     *
     * @param syxz 使用性质  [ZDCLK1037] 1、教练 2、非教练 3、非营运
     */
    public void setSyxz(String syxz) {
        this.syxz = syxz;
    }

    /**
     * 获取车辆产权状态  1、学牌车 2、非学牌车 3、已报销车 4、已售出
     *
     * @return car_property_type - 车辆产权状态  1、学牌车 2、非学牌车 3、已报销车 4、已售出
     */
    public String getCarPropertyType() {
        return carPropertyType;
    }

    /**
     * 设置车辆产权状态  1、学牌车 2、非学牌车 3、已报销车 4、已售出
     *
     * @param carPropertyType 车辆产权状态  1、学牌车 2、非学牌车 3、已报销车 4、已售出
     */
    public void setCarPropertyType(String carPropertyType) {
        this.carPropertyType = carPropertyType;
    }

    /**
     * 获取车辆品牌
     *
     * @return clpp - 车辆品牌
     */
    public String getClpp() {
        return clpp;
    }

    /**
     * 设置车辆品牌
     *
     * @param clpp 车辆品牌
     */
    public void setClpp(String clpp) {
        this.clpp = clpp;
    }

    /**
     * 获取车辆类型
     *
     * @return cllx - 车辆类型
     */
    public String getCllx() {
        return cllx;
    }

    /**
     * 设置车辆类型
     *
     * @param cllx 车辆类型
     */
    public void setCllx(String cllx) {
        this.cllx = cllx;
    }

    /**
     * 获取品牌型号
     *
     * @return ppxh - 品牌型号
     */
    public String getPpxh() {
        return ppxh;
    }

    /**
     * 设置品牌型号
     *
     * @param ppxh 品牌型号
     */
    public void setPpxh(String ppxh) {
        this.ppxh = ppxh;
    }

    /**
     * 获取车辆型号
     *
     * @return clxh - 车辆型号
     */
    public String getClxh() {
        return clxh;
    }

    /**
     * 设置车辆型号
     *
     * @param clxh 车辆型号
     */
    public void setClxh(String clxh) {
        this.clxh = clxh;
    }

    /**
     * 获取初次登记日期
     *
     * @return ccdjrq - 初次登记日期
     */
    public String getCcdjrq() {
        return ccdjrq;
    }

    /**
     * 设置初次登记日期
     *
     * @param ccdjrq 初次登记日期
     */
    public void setCcdjrq(String ccdjrq) {
        this.ccdjrq = ccdjrq;
    }

    /**
     * 获取发证日期(发证日期和初登记日期是一样的)
     *
     * @return fzrq - 发证日期(发证日期和初登记日期是一样的)
     */
    public String getFzrq() {
        return fzrq;
    }

    /**
     * 设置发证日期(发证日期和初登记日期是一样的)
     *
     * @param fzrq 发证日期(发证日期和初登记日期是一样的)
     */
    public void setFzrq(String fzrq) {
        this.fzrq = fzrq;
    }

    /**
     * 获取强制报废期
     *
     * @return qzbfq - 强制报废期
     */
    public String getQzbfq() {
        return qzbfq;
    }

    /**
     * 设置强制报废期
     *
     * @param qzbfq 强制报废期
     */
    public void setQzbfq(String qzbfq) {
        this.qzbfq = qzbfq;
    }

    /**
     * 获取保险初登日期
     *
     * @return bx_cdrq - 保险初登日期
     */
    public String getBxCdrq() {
        return bxCdrq;
    }

    /**
     * 设置保险初登日期
     *
     * @param bxCdrq 保险初登日期
     */
    public void setBxCdrq(String bxCdrq) {
        this.bxCdrq = bxCdrq;
    }

    /**
     * 获取运管初登日期
     *
     * @return yy_cdrq - 运管初登日期
     */
    public String getYyCdrq() {
        return yyCdrq;
    }

    /**
     * 设置运管初登日期
     *
     * @param yyCdrq 运管初登日期
     */
    public void setYyCdrq(String yyCdrq) {
        this.yyCdrq = yyCdrq;
    }

    /**
     * 获取改气初登日期
     *
     * @return gx_cdrq - 改气初登日期
     */
    public String getGxCdrq() {
        return gxCdrq;
    }

    /**
     * 设置改气初登日期
     *
     * @param gxCdrq 改气初登日期
     */
    public void setGxCdrq(String gxCdrq) {
        this.gxCdrq = gxCdrq;
    }

    /**
     * 获取车辆识别码
     *
     * @return clsbm - 车辆识别码
     */
    public String getClsbm() {
        return clsbm;
    }

    /**
     * 设置车辆识别码
     *
     * @param clsbm 车辆识别码
     */
    public void setClsbm(String clsbm) {
        this.clsbm = clsbm;
    }

    /**
     * 获取发动机号
     *
     * @return fdjh - 发动机号
     */
    public String getFdjh() {
        return fdjh;
    }

    /**
     * 设置发动机号
     *
     * @param fdjh 发动机号
     */
    public void setFdjh(String fdjh) {
        this.fdjh = fdjh;
    }

    /**
     * 获取发动机型号
     *
     * @return fdjxh - 发动机型号
     */
    public String getFdjxh() {
        return fdjxh;
    }

    /**
     * 设置发动机型号
     *
     * @param fdjxh 发动机型号
     */
    public void setFdjxh(String fdjxh) {
        this.fdjxh = fdjxh;
    }

    /**
     * 获取燃料种类 车辆燃油类型 [ZDCLK1038] 1、汽油 2、柴油
     *
     * @return rlzl - 燃料种类 车辆燃油类型 [ZDCLK1038] 1、汽油 2、柴油
     */
    public String getRlzl() {
        return rlzl;
    }

    /**
     * 设置燃料种类 车辆燃油类型 [ZDCLK1038] 1、汽油 2、柴油
     *
     * @param rlzl 燃料种类 车辆燃油类型 [ZDCLK1038] 1、汽油 2、柴油
     */
    public void setRlzl(String rlzl) {
        this.rlzl = rlzl;
    }

    /**
     * 获取排量
     *
     * @return pl - 排量
     */
    public Integer getPl() {
        return pl;
    }

    /**
     * 设置排量
     *
     * @param pl 排量
     */
    public void setPl(Integer pl) {
        this.pl = pl;
    }

    /**
     * 获取功率
     *
     * @return gl - 功率
     */
    public Integer getGl() {
        return gl;
    }

    /**
     * 设置功率
     *
     * @param gl 功率
     */
    public void setGl(Integer gl) {
        this.gl = gl;
    }

    /**
     * 获取制造厂名称
     *
     * @return zzcmc - 制造厂名称
     */
    public String getZzcmc() {
        return zzcmc;
    }

    /**
     * 设置制造厂名称
     *
     * @param zzcmc 制造厂名称
     */
    public void setZzcmc(String zzcmc) {
        this.zzcmc = zzcmc;
    }

    /**
     * 获取车身颜色
     *
     * @return csys - 车身颜色
     */
    public String getCsys() {
        return csys;
    }

    /**
     * 设置车身颜色
     *
     * @param csys 车身颜色
     */
    public void setCsys(String csys) {
        this.csys = csys;
    }

    /**
     * 获取前轮距
     *
     * @return qlj - 前轮距
     */
    public Integer getQlj() {
        return qlj;
    }

    /**
     * 设置前轮距
     *
     * @param qlj 前轮距
     */
    public void setQlj(Integer qlj) {
        this.qlj = qlj;
    }

    /**
     * 获取后轮距
     *
     * @return hlj - 后轮距
     */
    public Integer getHlj() {
        return hlj;
    }

    /**
     * 设置后轮距
     *
     * @param hlj 后轮距
     */
    public void setHlj(Integer hlj) {
        this.hlj = hlj;
    }

    /**
     * 获取轮胎数量
     *
     * @return ltsl - 轮胎数量
     */
    public Integer getLtsl() {
        return ltsl;
    }

    /**
     * 设置轮胎数量
     *
     * @param ltsl 轮胎数量
     */
    public void setLtsl(Integer ltsl) {
        this.ltsl = ltsl;
    }

    /**
     * 获取轮胎规格
     *
     * @return ltgg - 轮胎规格
     */
    public String getLtgg() {
        return ltgg;
    }

    /**
     * 设置轮胎规格
     *
     * @param ltgg 轮胎规格
     */
    public void setLtgg(String ltgg) {
        this.ltgg = ltgg;
    }

    /**
     * 获取板簧片数
     *
     * @return bhps - 板簧片数
     */
    public Integer getBhps() {
        return bhps;
    }

    /**
     * 设置板簧片数
     *
     * @param bhps 板簧片数
     */
    public void setBhps(Integer bhps) {
        this.bhps = bhps;
    }

    /**
     * 获取轴距
     *
     * @return zj - 轴距
     */
    public Integer getZj() {
        return zj;
    }

    /**
     * 设置轴距
     *
     * @param zj 轴距
     */
    public void setZj(Integer zj) {
        this.zj = zj;
    }

    /**
     * 获取外廓长
     *
     * @return wkc - 外廓长
     */
    public Integer getWkc() {
        return wkc;
    }

    /**
     * 设置外廓长
     *
     * @param wkc 外廓长
     */
    public void setWkc(Integer wkc) {
        this.wkc = wkc;
    }

    /**
     * 获取外廓宽
     *
     * @return wkk - 外廓宽
     */
    public Integer getWkk() {
        return wkk;
    }

    /**
     * 设置外廓宽
     *
     * @param wkk 外廓宽
     */
    public void setWkk(Integer wkk) {
        this.wkk = wkk;
    }

    /**
     * 获取外廓高
     *
     * @return wkg - 外廓高
     */
    public Integer getWkg() {
        return wkg;
    }

    /**
     * 设置外廓高
     *
     * @param wkg 外廓高
     */
    public void setWkg(Integer wkg) {
        this.wkg = wkg;
    }

    /**
     * 获取总质量
     *
     * @return zzl - 总质量
     */
    public Integer getZzl() {
        return zzl;
    }

    /**
     * 设置总质量
     *
     * @param zzl 总质量
     */
    public void setZzl(Integer zzl) {
        this.zzl = zzl;
    }

    /**
     * 获取核定载客
     *
     * @return hdzk - 核定载客
     */
    public Integer getHdzk() {
        return hdzk;
    }

    /**
     * 设置核定载客
     *
     * @param hdzk 核定载客
     */
    public void setHdzk(Integer hdzk) {
        this.hdzk = hdzk;
    }

    /**
     * 获取保用性质
     *
     * @return byxz - 保用性质
     */
    public String getByxz() {
        return byxz;
    }

    /**
     * 设置保用性质
     *
     * @param byxz 保用性质
     */
    public void setByxz(String byxz) {
        this.byxz = byxz;
    }

    /**
     * 获取车辆获得方式
     *
     * @return clhdfs - 车辆获得方式
     */
    public String getClhdfs() {
        return clhdfs;
    }

    /**
     * 设置车辆获得方式
     *
     * @param clhdfs 车辆获得方式
     */
    public void setClhdfs(String clhdfs) {
        this.clhdfs = clhdfs;
    }

    /**
     * 获取出厂日期
     *
     * @return ccrq - 出厂日期
     */
    public String getCcrq() {
        return ccrq;
    }

    /**
     * 设置出厂日期
     *
     * @param ccrq 出厂日期
     */
    public void setCcrq(String ccrq) {
        this.ccrq = ccrq;
    }

    /**
     * 获取发证机关
     *
     * @return fzjg - 发证机关
     */
    public String getFzjg() {
        return fzjg;
    }

    /**
     * 设置发证机关
     *
     * @param fzjg 发证机关
     */
    public void setFzjg(String fzjg) {
        this.fzjg = fzjg;
    }

    /**
     * 获取使用人ID
     *
     * @return syr_id - 使用人ID
     */
    public String getSyrId() {
        return syrId;
    }

    /**
     * 设置使用人ID
     *
     * @param syrId 使用人ID
     */
    public void setSyrId(String syrId) {
        this.syrId = syrId;
    }

    /**
     * 获取使用人姓名
     *
     * @return syr_name - 使用人姓名
     */
    public String getSyrName() {
        return syrName;
    }

    /**
     * 设置使用人姓名
     *
     * @param syrName 使用人姓名
     */
    public void setSyrName(String syrName) {
        this.syrName = syrName;
    }

    /**
     * 获取使用人所在地
     *
     * @return syr_szd - 使用人所在地
     */
    public String getSyrSzd() {
        return syrSzd;
    }

    /**
     * 设置使用人所在地
     *
     * @param syrSzd 使用人所在地
     */
    public void setSyrSzd(String syrSzd) {
        this.syrSzd = syrSzd;
    }

    /**
     * 获取使用人联系方式
     *
     * @return syr_dn - 使用人联系方式
     */
    public String getSyrDn() {
        return syrDn;
    }

    /**
     * 设置使用人联系方式
     *
     * @param syrDn 使用人联系方式
     */
    public void setSyrDn(String syrDn) {
        this.syrDn = syrDn;
    }

    /**
     * 获取使用信息id
     *
     * @return syxx_id - 使用信息id
     */
    public String getSyxxId() {
        return syxxId;
    }

    /**
     * 设置使用信息id
     *
     * @param syxxId 使用信息id
     */
    public void setSyxxId(String syxxId) {
        this.syxxId = syxxId;
    }

    /**
     * 获取年审日期
     *
     * @return nsrq - 年审日期
     */
    public String getNsrq() {
        return nsrq;
    }

    /**
     * 设置年审日期
     *
     * @param nsrq 年审日期
     */
    public void setNsrq(String nsrq) {
        this.nsrq = nsrq;
    }

    /**
     * 获取年审id
     *
     * @return ns_id - 年审id
     */
    public String getNsId() {
        return nsId;
    }

    /**
     * 设置年审id
     *
     * @param nsId 年审id
     */
    public void setNsId(String nsId) {
        this.nsId = nsId;
    }

    /**
     * 获取报废状态   是/否 [ZDCLK1034] 1、已报废 0 未报废
     *
     * @return bf_type - 报废状态   是/否 [ZDCLK1034] 1、已报废 0 未报废
     */
    public String getBfType() {
        return bfType;
    }

    /**
     * 设置报废状态   是/否 [ZDCLK1034] 1、已报废 0 未报废
     *
     * @param bfType 报废状态   是/否 [ZDCLK1034] 1、已报废 0 未报废
     */
    public void setBfType(String bfType) {
        this.bfType = bfType;
    }

    /**
     * 获取报废金额
     *
     * @return bf_fee - 报废金额
     */
    public String getBfFee() {
        return bfFee;
    }

    /**
     * 设置报废金额
     *
     * @param bfFee 报废金额
     */
    public void setBfFee(String bfFee) {
        this.bfFee = bfFee;
    }

    /**
     * 获取报废时间
     *
     * @return bf_date - 报废时间
     */
    public String getBfDate() {
        return bfDate;
    }

    /**
     * 设置报废时间
     *
     * @param bfDate 报废时间
     */
    public void setBfDate(String bfDate) {
        this.bfDate = bfDate;
    }

    /**
     * 获取车辆售卖时间
     *
     * @return sm_date - 车辆售卖时间
     */
    public String getSmDate() {
        return smDate;
    }

    /**
     * 设置车辆售卖时间
     *
     * @param smDate 车辆售卖时间
     */
    public void setSmDate(String smDate) {
        this.smDate = smDate;
    }

    /**
     * 获取车辆售卖金额
     *
     * @return sm_fee - 车辆售卖金额
     */
    public String getSmFee() {
        return smFee;
    }

    /**
     * 设置车辆售卖金额
     *
     * @param smFee 车辆售卖金额
     */
    public void setSmFee(String smFee) {
        this.smFee = smFee;
    }

    /**
     * 获取车辆产权人ID
     *
     * @return cl_cqr_id - 车辆产权人ID
     */
    public String getClCqrId() {
        return clCqrId;
    }

    /**
     * 设置车辆产权人ID
     *
     * @param clCqrId 车辆产权人ID
     */
    public void setClCqrId(String clCqrId) {
        this.clCqrId = clCqrId;
    }

    /**
     * 获取车辆产权人
     *
     * @return cl_cqr - 车辆产权人
     */
    public String getClCqr() {
        return clCqr;
    }

    /**
     * 设置车辆产权人
     *
     * @param clCqr 车辆产权人
     */
    public void setClCqr(String clCqr) {
        this.clCqr = clCqr;
    }

    /**
     * 获取车辆产权人电话
     *
     * @return cl_cqr_dn - 车辆产权人电话
     */
    public String getClCqrDn() {
        return clCqrDn;
    }

    /**
     * 设置车辆产权人电话
     *
     * @param clCqrDn 车辆产权人电话
     */
    public void setClCqrDn(String clCqrDn) {
        this.clCqrDn = clCqrDn;
    }

    /**
     * 获取车辆产权人证件号
     *
     * @return cl_cqr_code - 车辆产权人证件号
     */
    public String getClCqrCode() {
        return clCqrCode;
    }

    /**
     * 设置车辆产权人证件号
     *
     * @param clCqrCode 车辆产权人证件号
     */
    public void setClCqrCode(String clCqrCode) {
        this.clCqrCode = clCqrCode;
    }

    /**
     * 获取车辆产权变更经办人电话
     *
     * @return cl_cqr_jbr_dn - 车辆产权变更经办人电话
     */
    public String getClCqrJbrDn() {
        return clCqrJbrDn;
    }

    /**
     * 设置车辆产权变更经办人电话
     *
     * @param clCqrJbrDn 车辆产权变更经办人电话
     */
    public void setClCqrJbrDn(String clCqrJbrDn) {
        this.clCqrJbrDn = clCqrJbrDn;
    }

    /**
     * 获取车辆产权变更经办人
     *
     * @return cl_cqr_jbr - 车辆产权变更经办人
     */
    public String getClCqrJbr() {
        return clCqrJbr;
    }

    /**
     * 设置车辆产权变更经办人
     *
     * @param clCqrJbr 车辆产权变更经办人
     */
    public void setClCqrJbr(String clCqrJbr) {
        this.clCqrJbr = clCqrJbr;
    }

    /**
     * 获取车辆产权变更备注
     *
     * @return cl_cqr_bz - 车辆产权变更备注
     */
    public String getClCqrBz() {
        return clCqrBz;
    }

    /**
     * 设置车辆产权变更备注
     *
     * @param clCqrBz 车辆产权变更备注
     */
    public void setClCqrBz(String clCqrBz) {
        this.clCqrBz = clCqrBz;
    }

    /**
     * 获取车辆产权变更金额
     *
     * @return cl_cqr_jl - 车辆产权变更金额
     */
    public String getClCqrJl() {
        return clCqrJl;
    }

    /**
     * 设置车辆产权变更金额
     *
     * @param clCqrJl 车辆产权变更金额
     */
    public void setClCqrJl(String clCqrJl) {
        this.clCqrJl = clCqrJl;
    }

    /**
     * 获取机动车登记证书状态  有/无 [ZDCLK1039] 1、有 0、无
     *
     * @return dzd_djz_type - 机动车登记证书状态  有/无 [ZDCLK1039] 1、有 0、无
     */
    public String getDzdDjzType() {
        return dzdDjzType;
    }

    /**
     * 设置机动车登记证书状态  有/无 [ZDCLK1039] 1、有 0、无
     *
     * @param dzdDjzType 机动车登记证书状态  有/无 [ZDCLK1039] 1、有 0、无
     */
    public void setDzdDjzType(String dzdDjzType) {
        this.dzdDjzType = dzdDjzType;
    }

    /**
     * 获取机动车登记证书编号
     *
     * @return dzd_djz_code - 机动车登记证书编号
     */
    public String getDzdDjzCode() {
        return dzdDjzCode;
    }

    /**
     * 设置机动车登记证书编号
     *
     * @param dzdDjzCode 机动车登记证书编号
     */
    public void setDzdDjzCode(String dzdDjzCode) {
        this.dzdDjzCode = dzdDjzCode;
    }

    /**
     * 获取机动车登记证书 电子档路径
     *
     * @return dzd_djz_fileurl - 机动车登记证书 电子档路径
     */
    public String getDzdDjzFileurl() {
        return dzdDjzFileurl;
    }

    /**
     * 设置机动车登记证书 电子档路径
     *
     * @param dzdDjzFileurl 机动车登记证书 电子档路径
     */
    public void setDzdDjzFileurl(String dzdDjzFileurl) {
        this.dzdDjzFileurl = dzdDjzFileurl;
    }

    /**
     * 获取购置税完税证明状态  有/无 [ZDCLK1039] 1、有 0、无
     *
     * @return dzd_wszm_type - 购置税完税证明状态  有/无 [ZDCLK1039] 1、有 0、无
     */
    public String getDzdWszmType() {
        return dzdWszmType;
    }

    /**
     * 设置购置税完税证明状态  有/无 [ZDCLK1039] 1、有 0、无
     *
     * @param dzdWszmType 购置税完税证明状态  有/无 [ZDCLK1039] 1、有 0、无
     */
    public void setDzdWszmType(String dzdWszmType) {
        this.dzdWszmType = dzdWszmType;
    }

    /**
     * 获取购置税完税证明证号
     *
     * @return dzd_wszm_code - 购置税完税证明证号
     */
    public String getDzdWszmCode() {
        return dzdWszmCode;
    }

    /**
     * 设置购置税完税证明证号
     *
     * @param dzdWszmCode 购置税完税证明证号
     */
    public void setDzdWszmCode(String dzdWszmCode) {
        this.dzdWszmCode = dzdWszmCode;
    }

    /**
     * 获取购置税完税证明票号
     *
     * @return dzd_wszm_ph - 购置税完税证明票号
     */
    public String getDzdWszmPh() {
        return dzdWszmPh;
    }

    /**
     * 设置购置税完税证明票号
     *
     * @param dzdWszmPh 购置税完税证明票号
     */
    public void setDzdWszmPh(String dzdWszmPh) {
        this.dzdWszmPh = dzdWszmPh;
    }

    /**
     * 获取购置税完税证明票号电子档路径
     *
     * @return dzd_wszm_fileurl - 购置税完税证明票号电子档路径
     */
    public String getDzdWszmFileurl() {
        return dzdWszmFileurl;
    }

    /**
     * 设置购置税完税证明票号电子档路径
     *
     * @param dzdWszmFileurl 购置税完税证明票号电子档路径
     */
    public void setDzdWszmFileurl(String dzdWszmFileurl) {
        this.dzdWszmFileurl = dzdWszmFileurl;
    }

    /**
     * 获取发票状态  有/无 [ZDCLK1039] 1、有 0、无
     *
     * @return dzd_fp_type - 发票状态  有/无 [ZDCLK1039] 1、有 0、无
     */
    public String getDzdFpType() {
        return dzdFpType;
    }

    /**
     * 设置发票状态  有/无 [ZDCLK1039] 1、有 0、无
     *
     * @param dzdFpType 发票状态  有/无 [ZDCLK1039] 1、有 0、无
     */
    public void setDzdFpType(String dzdFpType) {
        this.dzdFpType = dzdFpType;
    }

    /**
     * 获取发票号码
     *
     * @return dzd_fp_code - 发票号码
     */
    public String getDzdFpCode() {
        return dzdFpCode;
    }

    /**
     * 设置发票号码
     *
     * @param dzdFpCode 发票号码
     */
    public void setDzdFpCode(String dzdFpCode) {
        this.dzdFpCode = dzdFpCode;
    }

    /**
     * 获取发票号码 电子档路径
     *
     * @return dzd_fp_fileurl - 发票号码 电子档路径
     */
    public String getDzdFpFileurl() {
        return dzdFpFileurl;
    }

    /**
     * 设置发票号码 电子档路径
     *
     * @param dzdFpFileurl 发票号码 电子档路径
     */
    public void setDzdFpFileurl(String dzdFpFileurl) {
        this.dzdFpFileurl = dzdFpFileurl;
    }

    /**
     * 获取道路运输许可证 有/无 [ZDCLK1039] 1、有 0、无
     *
     * @return dzd_drys_type - 道路运输许可证 有/无 [ZDCLK1039] 1、有 0、无
     */
    public String getDzdDrysType() {
        return dzdDrysType;
    }

    /**
     * 设置道路运输许可证 有/无 [ZDCLK1039] 1、有 0、无
     *
     * @param dzdDrysType 道路运输许可证 有/无 [ZDCLK1039] 1、有 0、无
     */
    public void setDzdDrysType(String dzdDrysType) {
        this.dzdDrysType = dzdDrysType;
    }

    /**
     * 获取行驶证扫描件状态 有/无 [ZDCLK1039] 1、有 0、无
     *
     * @return dzd_xszfy_type - 行驶证扫描件状态 有/无 [ZDCLK1039] 1、有 0、无
     */
    public String getDzdXszfyType() {
        return dzdXszfyType;
    }

    /**
     * 设置行驶证扫描件状态 有/无 [ZDCLK1039] 1、有 0、无
     *
     * @param dzdXszfyType 行驶证扫描件状态 有/无 [ZDCLK1039] 1、有 0、无
     */
    public void setDzdXszfyType(String dzdXszfyType) {
        this.dzdXszfyType = dzdXszfyType;
    }

    /**
     * 获取行驶证扫描件状态 电子档路径
     *
     * @return dzd_xszfy_fileurl - 行驶证扫描件状态 电子档路径
     */
    public String getDzdXszfyFileurl() {
        return dzdXszfyFileurl;
    }

    /**
     * 设置行驶证扫描件状态 电子档路径
     *
     * @param dzdXszfyFileurl 行驶证扫描件状态 电子档路径
     */
    public void setDzdXszfyFileurl(String dzdXszfyFileurl) {
        this.dzdXszfyFileurl = dzdXszfyFileurl;
    }

    /**
     * 获取运管年审 下一次过期时间
     *
     * @return warn_ygns_date - 运管年审 下一次过期时间
     */
    public String getWarnYgnsDate() {
        return warnYgnsDate;
    }

    /**
     * 设置运管年审 下一次过期时间
     *
     * @param warnYgnsDate 运管年审 下一次过期时间
     */
    public void setWarnYgnsDate(String warnYgnsDate) {
        this.warnYgnsDate = warnYgnsDate;
    }

    /**
     * 获取保险 下一次过期时间
     *
     * @return warn_bx_date - 保险 下一次过期时间
     */
    public String getWarnBxDate() {
        return warnBxDate;
    }

    /**
     * 设置保险 下一次过期时间
     *
     * @param warnBxDate 保险 下一次过期时间
     */
    public void setWarnBxDate(String warnBxDate) {
        this.warnBxDate = warnBxDate;
    }

    /**
     * 获取车辆管年审 下一次过期时间
     *
     * @return warn_nssj_date - 车辆管年审 下一次过期时间
     */
    public String getWarnNssjDate() {
        return warnNssjDate;
    }

    /**
     * 设置车辆管年审 下一次过期时间
     *
     * @param warnNssjDate 车辆管年审 下一次过期时间
     */
    public void setWarnNssjDate(String warnNssjDate) {
        this.warnNssjDate = warnNssjDate;
    }

    /**
     * 获取改气年审 下一次过期时间
     *
     * @return warn_gx_date - 改气年审 下一次过期时间
     */
    public String getWarnGxDate() {
        return warnGxDate;
    }

    /**
     * 设置改气年审 下一次过期时间
     *
     * @param warnGxDate 改气年审 下一次过期时间
     */
    public void setWarnGxDate(String warnGxDate) {
        this.warnGxDate = warnGxDate;
    }

    /**
     * 获取上一次运管审核时间
     *
     * @return yg_last_data - 上一次运管审核时间
     */
    public String getYgLastData() {
        return ygLastData;
    }

    /**
     * 设置上一次运管审核时间
     *
     * @param ygLastData 上一次运管审核时间
     */
    public void setYgLastData(String ygLastData) {
        this.ygLastData = ygLastData;
    }

    /**
     * 获取 运管运输证号
     *
     * @return yg_yszh -  运管运输证号
     */
    public String getYgYszh() {
        return ygYszh;
    }

    /**
     * 设置 运管运输证号
     *
     * @param ygYszh  运管运输证号
     */
    public void setYgYszh(String ygYszh) {
        this.ygYszh = ygYszh;
    }

    /**
     * 获取运管 是否安装GPS  是/否 [ZDCLK1034] 
     *
     * @return yg_gps_type - 运管 是否安装GPS  是/否 [ZDCLK1034] 
     */
    public String getYgGpsType() {
        return ygGpsType;
    }

    /**
     * 设置运管 是否安装GPS  是/否 [ZDCLK1034] 
     *
     * @param ygGpsType 运管 是否安装GPS  是/否 [ZDCLK1034] 
     */
    public void setYgGpsType(String ygGpsType) {
        this.ygGpsType = ygGpsType;
    }

    /**
     * 获取运管 GPS号码
     *
     * @return yg_gps_code - 运管 GPS号码
     */
    public String getYgGpsCode() {
        return ygGpsCode;
    }

    /**
     * 设置运管 GPS号码
     *
     * @param ygGpsCode 运管 GPS号码
     */
    public void setYgGpsCode(String ygGpsCode) {
        this.ygGpsCode = ygGpsCode;
    }

    /**
     * 获取运管 车辆备案_运营状态 [ZDCLK1033] 营运状态1、运营 2、注销 0、未登记
     *
     * @return yg_yy_type - 运管 车辆备案_运营状态 [ZDCLK1033] 营运状态1、运营 2、注销 0、未登记
     */
    public String getYgYyType() {
        return ygYyType;
    }

    /**
     * 设置运管 车辆备案_运营状态 [ZDCLK1033] 营运状态1、运营 2、注销 0、未登记
     *
     * @param ygYyType 运管 车辆备案_运营状态 [ZDCLK1033] 营运状态1、运营 2、注销 0、未登记
     */
    public void setYgYyType(String ygYyType) {
        this.ygYyType = ygYyType;
    }

    /**
     * 获取运管 1、14年上线  0、不是   是/否 [ZDCLK1034]
     *
     * @return yg_ysn_sx - 运管 1、14年上线  0、不是   是/否 [ZDCLK1034]
     */
    public String getYgYsnSx() {
        return ygYsnSx;
    }

    /**
     * 设置运管 1、14年上线  0、不是   是/否 [ZDCLK1034]
     *
     * @param ygYsnSx 运管 1、14年上线  0、不是   是/否 [ZDCLK1034]
     */
    public void setYgYsnSx(String ygYsnSx) {
        this.ygYsnSx = ygYsnSx;
    }

    /**
     * 获取运管 更新(BU列)
     *
     * @return yg_gx - 运管 更新(BU列)
     */
    public String getYgGx() {
        return ygGx;
    }

    /**
     * 设置运管 更新(BU列)
     *
     * @param ygGx 运管 更新(BU列)
     */
    public void setYgGx(String ygGx) {
        this.ygGx = ygGx;
    }

    /**
     * 获取运管 轴距
     *
     * @return yg_zj - 运管 轴距
     */
    public Integer getYgZj() {
        return ygZj;
    }

    /**
     * 设置运管 轴距
     *
     * @param ygZj 运管 轴距
     */
    public void setYgZj(Integer ygZj) {
        this.ygZj = ygZj;
    }

    /**
     * 获取运管 车辆备案_卡机安装状态 [ZDCLK1035] 卡机安装状态 1、已安装 0、未安装
     *
     * @return yg_kj_type - 运管 车辆备案_卡机安装状态 [ZDCLK1035] 卡机安装状态 1、已安装 0、未安装
     */
    public String getYgKjType() {
        return ygKjType;
    }

    /**
     * 设置运管 车辆备案_卡机安装状态 [ZDCLK1035] 卡机安装状态 1、已安装 0、未安装
     *
     * @param ygKjType 运管 车辆备案_卡机安装状态 [ZDCLK1035] 卡机安装状态 1、已安装 0、未安装
     */
    public void setYgKjType(String ygKjType) {
        this.ygKjType = ygKjType;
    }

    /**
     * 获取运管 卡机安装时间
     *
     * @return yg_kj_azsj - 运管 卡机安装时间
     */
    public String getYgKjAzsj() {
        return ygKjAzsj;
    }

    /**
     * 设置运管 卡机安装时间
     *
     * @param ygKjAzsj 运管 卡机安装时间
     */
    public void setYgKjAzsj(String ygKjAzsj) {
        this.ygKjAzsj = ygKjAzsj;
    }

    /**
     * 获取卡机批次
     *
     * @return yg_kj_pc - 卡机批次
     */
    public String getYgKjPc() {
        return ygKjPc;
    }

    /**
     * 设置卡机批次
     *
     * @param ygKjPc 卡机批次
     */
    public void setYgKjPc(String ygKjPc) {
        this.ygKjPc = ygKjPc;
    }

    /**
     * 获取明涛成功新证号
     *
     * @return yg_new_code - 明涛成功新证号
     */
    public String getYgNewCode() {
        return ygNewCode;
    }

    /**
     * 设置明涛成功新证号
     *
     * @param ygNewCode 明涛成功新证号
     */
    public void setYgNewCode(String ygNewCode) {
        this.ygNewCode = ygNewCode;
    }

    /**
     * 获取新卡机
     *
     * @return yg_new_kj - 新卡机
     */
    public String getYgNewKj() {
        return ygNewKj;
    }

    /**
     * 设置新卡机
     *
     * @param ygNewKj 新卡机
     */
    public void setYgNewKj(String ygNewKj) {
        this.ygNewKj = ygNewKj;
    }

    /**
     * 获取上一次改气审核时间
     *
     * @return gx_last_data - 上一次改气审核时间
     */
    public String getGxLastData() {
        return gxLastData;
    }

    /**
     * 设置上一次改气审核时间
     *
     * @param gxLastData 上一次改气审核时间
     */
    public void setGxLastData(String gxLastData) {
        this.gxLastData = gxLastData;
    }

    /**
     * 获取改气合格证有效期
     *
     * @return gx_gas_ggzyxq - 改气合格证有效期
     */
    public String getGxGasGgzyxq() {
        return gxGasGgzyxq;
    }

    /**
     * 设置改气合格证有效期
     *
     * @param gxGasGgzyxq 改气合格证有效期
     */
    public void setGxGasGgzyxq(String gxGasGgzyxq) {
        this.gxGasGgzyxq = gxGasGgzyxq;
    }

    /**
     * 获取改气 联系人电话
     *
     * @return gx_lx_dn - 改气 联系人电话
     */
    public String getGxLxDn() {
        return gxLxDn;
    }

    /**
     * 设置改气 联系人电话
     *
     * @param gxLxDn 改气 联系人电话
     */
    public void setGxLxDn(String gxLxDn) {
        this.gxLxDn = gxLxDn;
    }

    /**
     * 获取改气 联系人
     *
     * @return gx_lxr - 改气 联系人
     */
    public String getGxLxr() {
        return gxLxr;
    }

    /**
     * 设置改气 联系人
     *
     * @param gxLxr 改气 联系人
     */
    public void setGxLxr(String gxLxr) {
        this.gxLxr = gxLxr;
    }

    /**
     * 获取改气备注
     *
     * @return gx_gas_bz - 改气备注
     */
    public String getGxGasBz() {
        return gxGasBz;
    }

    /**
     * 设置改气备注
     *
     * @param gxGasBz 改气备注
     */
    public void setGxGasBz(String gxGasBz) {
        this.gxGasBz = gxGasBz;
    }

    /**
     * 获取改气地点
     *
     * @return gx_gas_dd - 改气地点
     */
    public String getGxGasDd() {
        return gxGasDd;
    }

    /**
     * 设置改气地点
     *
     * @param gxGasDd 改气地点
     */
    public void setGxGasDd(String gxGasDd) {
        this.gxGasDd = gxGasDd;
    }

    /**
     * 获取是否改气 1、已改气 0未改气
     *
     * @return gx_type - 是否改气 1、已改气 0未改气
     */
    public String getGxType() {
        return gxType;
    }

    /**
     * 设置是否改气 1、已改气 0未改气
     *
     * @param gxType 是否改气 1、已改气 0未改气
     */
    public void setGxType(String gxType) {
        this.gxType = gxType;
    }

    /**
     * 获取上一次保险审核时间
     *
     * @return bx_last_data - 上一次保险审核时间
     */
    public String getBxLastData() {
        return bxLastData;
    }

    /**
     * 设置上一次保险审核时间
     *
     * @param bxLastData 上一次保险审核时间
     */
    public void setBxLastData(String bxLastData) {
        this.bxLastData = bxLastData;
    }

    /**
     * 获取保险 保险日期
     *
     * @return bx_bxrq - 保险 保险日期
     */
    public String getBxBxrq() {
        return bxBxrq;
    }

    /**
     * 设置保险 保险日期
     *
     * @param bxBxrq 保险 保险日期
     */
    public void setBxBxrq(String bxBxrq) {
        this.bxBxrq = bxBxrq;
    }

    /**
     * 获取保险 保险至
     *
     * @return bx_bxz - 保险 保险至
     */
    public String getBxBxz() {
        return bxBxz;
    }

    /**
     * 设置保险 保险至
     *
     * @param bxBxz 保险 保险至
     */
    public void setBxBxz(String bxBxz) {
        this.bxBxz = bxBxz;
    }

    /**
     * 获取保险 保险电话
     *
     * @return bx_bxdh - 保险 保险电话
     */
    public String getBxBxdh() {
        return bxBxdh;
    }

    /**
     * 设置保险 保险电话
     *
     * @param bxBxdh 保险 保险电话
     */
    public void setBxBxdh(String bxBxdh) {
        this.bxBxdh = bxBxdh;
    }

    /**
     * 获取保险 保单编号
     *
     * @return bx_bdzbbh - 保险 保单编号
     */
    public String getBxBdzbbh() {
        return bxBdzbbh;
    }

    /**
     * 设置保险 保单编号
     *
     * @param bxBdzbbh 保险 保单编号
     */
    public void setBxBdzbbh(String bxBdzbbh) {
        this.bxBdzbbh = bxBdzbbh;
    }

    /**
     * 获取保险 保单副本编号
     *
     * @return bx_bdfbbh - 保险 保单副本编号
     */
    public String getBxBdfbbh() {
        return bxBdfbbh;
    }

    /**
     * 设置保险 保单副本编号
     *
     * @param bxBdfbbh 保险 保单副本编号
     */
    public void setBxBdfbbh(String bxBdfbbh) {
        this.bxBdfbbh = bxBdfbbh;
    }

    /**
     * 获取保险 保单位置
     *
     * @return bx_bd_wz - 保险 保单位置
     */
    public String getBxBdWz() {
        return bxBdWz;
    }

    /**
     * 设置保险 保单位置
     *
     * @param bxBdWz 保险 保单位置
     */
    public void setBxBdWz(String bxBdWz) {
        this.bxBdWz = bxBdWz;
    }

    /**
     * 获取保险 备注
     *
     * @return bx_bz - 保险 备注
     */
    public String getBxBz() {
        return bxBz;
    }

    /**
     * 设置保险 备注
     *
     * @param bxBz 保险 备注
     */
    public void setBxBz(String bxBz) {
        this.bxBz = bxBz;
    }

    /**
     * 获取保单电子档路径
     *
     * @return bx_da_file - 保单电子档路径
     */
    public String getBxDaFile() {
        return bxDaFile;
    }

    /**
     * 设置保单电子档路径
     *
     * @param bxDaFile 保单电子档路径
     */
    public void setBxDaFile(String bxDaFile) {
        this.bxDaFile = bxDaFile;
    }

    /**
     * 获取投保公司
     *
     * @return bx_tbgs - 投保公司
     */
    public String getBxTbgs() {
        return bxTbgs;
    }

    /**
     * 设置投保公司
     *
     * @param bxTbgs 投保公司
     */
    public void setBxTbgs(String bxTbgs) {
        this.bxTbgs = bxTbgs;
    }

    /**
     * 获取保单联系人
     *
     * @return bx_bxlxr - 保单联系人
     */
    public String getBxBxlxr() {
        return bxBxlxr;
    }

    /**
     * 设置保单联系人
     *
     * @param bxBxlxr 保单联系人
     */
    public void setBxBxlxr(String bxBxlxr) {
        this.bxBxlxr = bxBxlxr;
    }

    /**
     * 获取保险 保单数量
     *
     * @return bx_bd_count - 保险 保单数量
     */
    public String getBxBdCount() {
        return bxBdCount;
    }

    /**
     * 设置保险 保单数量
     *
     * @param bxBdCount 保险 保单数量
     */
    public void setBxBdCount(String bxBdCount) {
        this.bxBdCount = bxBdCount;
    }

    /**
     * 获取上一次车管年审时间
     *
     * @return ns_last_data - 上一次车管年审时间
     */
    public String getNsLastData() {
        return nsLastData;
    }

    /**
     * 设置上一次车管年审时间
     *
     * @param nsLastData 上一次车管年审时间
     */
    public void setNsLastData(String nsLastData) {
        this.nsLastData = nsLastData;
    }

    /**
     * 获取车管年审 年审至
     *
     * @return ns_nsz - 车管年审 年审至
     */
    public String getNsNsz() {
        return nsNsz;
    }

    /**
     * 设置车管年审 年审至
     *
     * @param nsNsz 车管年审 年审至
     */
    public void setNsNsz(String nsNsz) {
        this.nsNsz = nsNsz;
    }

    /**
     * 获取车管年审 年审时间
     *
     * @return ns_nssj - 车管年审 年审时间
     */
    public String getNsNssj() {
        return nsNssj;
    }

    /**
     * 设置车管年审 年审时间
     *
     * @param nsNssj 车管年审 年审时间
     */
    public void setNsNssj(String nsNssj) {
        this.nsNssj = nsNssj;
    }

    /**
     * 获取车管年审 二审时间
     *
     * @return ns_essj - 车管年审 二审时间
     */
    public String getNsEssj() {
        return nsEssj;
    }

    /**
     * 设置车管年审 二审时间
     *
     * @param nsEssj 车管年审 二审时间
     */
    public void setNsEssj(String nsEssj) {
        this.nsEssj = nsEssj;
    }

    /**
     * 获取车管年审 批次
     *
     * @return ns_pc - 车管年审 批次
     */
    public String getNsPc() {
        return nsPc;
    }

    /**
     * 设置车管年审 批次
     *
     * @param nsPc 车管年审 批次
     */
    public void setNsPc(String nsPc) {
        this.nsPc = nsPc;
    }

    /**
     * 获取车管年审 驾驶员ID
     *
     * @return ns_jsyId - 车管年审 驾驶员ID
     */
    public String getNsJsyid() {
        return nsJsyid;
    }

    /**
     * 设置车管年审 驾驶员ID
     *
     * @param nsJsyid 车管年审 驾驶员ID
     */
    public void setNsJsyid(String nsJsyid) {
        this.nsJsyid = nsJsyid;
    }

    /**
     * 获取车管年审 驾驶员姓名
     *
     * @return ns_jsyxm - 车管年审 驾驶员姓名
     */
    public String getNsJsyxm() {
        return nsJsyxm;
    }

    /**
     * 设置车管年审 驾驶员姓名
     *
     * @param nsJsyxm 车管年审 驾驶员姓名
     */
    public void setNsJsyxm(String nsJsyxm) {
        this.nsJsyxm = nsJsyxm;
    }

    /**
     * 获取车管年审 驾驶员身份证号
     *
     * @return ns_jsysfzh - 车管年审 驾驶员身份证号
     */
    public String getNsJsysfzh() {
        return nsJsysfzh;
    }

    /**
     * 设置车管年审 驾驶员身份证号
     *
     * @param nsJsysfzh 车管年审 驾驶员身份证号
     */
    public void setNsJsysfzh(String nsJsysfzh) {
        this.nsJsysfzh = nsJsysfzh;
    }

    /**
     * 获取车管年审 驾驶员联系电话
     *
     * @return ns_jsylxdh - 车管年审 驾驶员联系电话
     */
    public String getNsJsylxdh() {
        return nsJsylxdh;
    }

    /**
     * 设置车管年审 驾驶员联系电话
     *
     * @param nsJsylxdh 车管年审 驾驶员联系电话
     */
    public void setNsJsylxdh(String nsJsylxdh) {
        this.nsJsylxdh = nsJsylxdh;
    }

    /**
     * 获取车管年审 年审状态
     *
     * @return ns_zt - 车管年审 年审状态
     */
    public String getNsZt() {
        return nsZt;
    }

    /**
     * 设置车管年审 年审状态
     *
     * @param nsZt 车管年审 年审状态
     */
    public void setNsZt(String nsZt) {
        this.nsZt = nsZt;
    }

    /**
     * 获取车辆状态、ZDCLK1042  1、正常  10、车管正常  12、车管逾期未审  19、车管90天待审  18、车管60天待审  17、车管30天待审  20、运管正常  22、运管逾期未审  29、运管90天待审  28、运管60天待审  27、运管30天待审  30、改气正常  32、改气逾期未审  39、改气90天待审  40、保险正常  42、保险逾期未审  49、保险90天待审  50、报废正常  52、报废逾期未审  61、已转出  71、车牌已变更
     *
     * @return car_type - 车辆状态、ZDCLK1042  1、正常  10、车管正常  12、车管逾期未审  19、车管90天待审  18、车管60天待审  17、车管30天待审  20、运管正常  22、运管逾期未审  29、运管90天待审  28、运管60天待审  27、运管30天待审  30、改气正常  32、改气逾期未审  39、改气90天待审  40、保险正常  42、保险逾期未审  49、保险90天待审  50、报废正常  52、报废逾期未审  61、已转出  71、车牌已变更
     */
    public String getCarType() {
        return carType;
    }

    /**
     * 设置车辆状态、ZDCLK1042  1、正常  10、车管正常  12、车管逾期未审  19、车管90天待审  18、车管60天待审  17、车管30天待审  20、运管正常  22、运管逾期未审  29、运管90天待审  28、运管60天待审  27、运管30天待审  30、改气正常  32、改气逾期未审  39、改气90天待审  40、保险正常  42、保险逾期未审  49、保险90天待审  50、报废正常  52、报废逾期未审  61、已转出  71、车牌已变更
     *
     * @param carType 车辆状态、ZDCLK1042  1、正常  10、车管正常  12、车管逾期未审  19、车管90天待审  18、车管60天待审  17、车管30天待审  20、运管正常  22、运管逾期未审  29、运管90天待审  28、运管60天待审  27、运管30天待审  30、改气正常  32、改气逾期未审  39、改气90天待审  40、保险正常  42、保险逾期未审  49、保险90天待审  50、报废正常  52、报废逾期未审  61、已转出  71、车牌已变更
     */
    public void setCarType(String carType) {
        this.carType = carType;
    }

    /**
     * 获取保险经办人
     *
     * @return bx_jbr - 保险经办人
     */
    public String getBxJbr() {
        return bxJbr;
    }

    /**
     * 设置保险经办人
     *
     * @param bxJbr 保险经办人
     */
    public void setBxJbr(String bxJbr) {
        this.bxJbr = bxJbr;
    }

    /**
     * 获取保险经办人电话
     *
     * @return bx_jbr_dn - 保险经办人电话
     */
    public String getBxJbrDn() {
        return bxJbrDn;
    }

    /**
     * 设置保险经办人电话
     *
     * @param bxJbrDn 保险经办人电话
     */
    public void setBxJbrDn(String bxJbrDn) {
        this.bxJbrDn = bxJbrDn;
    }

    /**
     * 获取产权内部变更电子档案
     *
     * @return dzd_cqbg_url - 产权内部变更电子档案
     */
    public String getDzdCqbgUrl() {
        return dzdCqbgUrl;
    }

    /**
     * 设置产权内部变更电子档案
     *
     * @param dzdCqbgUrl 产权内部变更电子档案
     */
    public void setDzdCqbgUrl(String dzdCqbgUrl) {
        this.dzdCqbgUrl = dzdCqbgUrl;
    }

    /**
     * 获取车牌变更 电子档案 
     *
     * @return dzd_cpbg_url - 车牌变更 电子档案 
     */
    public String getDzdCpbgUrl() {
        return dzdCpbgUrl;
    }

    /**
     * 设置车牌变更 电子档案 
     *
     * @param dzdCpbgUrl 车牌变更 电子档案 
     */
    public void setDzdCpbgUrl(String dzdCpbgUrl) {
        this.dzdCpbgUrl = dzdCpbgUrl;
    }

    /**
     * 获取车辆报废 电子档案
     *
     * @return dzd_clbf_url - 车辆报废 电子档案
     */
    public String getDzdClbfUrl() {
        return dzdClbfUrl;
    }

    /**
     * 设置车辆报废 电子档案
     *
     * @param dzdClbfUrl 车辆报废 电子档案
     */
    public void setDzdClbfUrl(String dzdClbfUrl) {
        this.dzdClbfUrl = dzdClbfUrl;
    }

    /**
     * 获取车辆变卖 电子档案
     *
     * @return dzd_clbm_url - 车辆变卖 电子档案
     */
    public String getDzdClbmUrl() {
        return dzdClbmUrl;
    }

    /**
     * 设置车辆变卖 电子档案
     *
     * @param dzdClbmUrl 车辆变卖 电子档案
     */
    public void setDzdClbmUrl(String dzdClbmUrl) {
        this.dzdClbmUrl = dzdClbmUrl;
    }

    /**
     * 获取车辆保险 电子档案
     *
     * @return dzd_clb_url - 车辆保险 电子档案
     */
    public String getDzdClbUrl() {
        return dzdClbUrl;
    }

    /**
     * 设置车辆保险 电子档案
     *
     * @param dzdClbUrl 车辆保险 电子档案
     */
    public void setDzdClbUrl(String dzdClbUrl) {
        this.dzdClbUrl = dzdClbUrl;
    }

    /**
     * 获取备案人ID
     *
     * @return jsyid - 备案人ID
     */
    public String getJsyid() {
        return jsyid;
    }

    /**
     * 设置备案人ID
     *
     * @param jsyid 备案人ID
     */
    public void setJsyid(String jsyid) {
        this.jsyid = jsyid;
    }

    /**
     * 获取备案人姓名
     *
     * @return jsyxm - 备案人姓名
     */
    public String getJsyxm() {
        return jsyxm;
    }

    /**
     * 设置备案人姓名
     *
     * @param jsyxm 备案人姓名
     */
    public void setJsyxm(String jsyxm) {
        this.jsyxm = jsyxm;
    }

    /**
     * 获取备案人身份证号
     *
     * @return jsysfzh - 备案人身份证号
     */
    public String getJsysfzh() {
        return jsysfzh;
    }

    /**
     * 设置备案人身份证号
     *
     * @param jsysfzh 备案人身份证号
     */
    public void setJsysfzh(String jsysfzh) {
        this.jsysfzh = jsysfzh;
    }

    /**
     * 获取备案人联系电话
     *
     * @return jsylxdh - 备案人联系电话
     */
    public String getJsylxdh() {
        return jsylxdh;
    }

    /**
     * 设置备案人联系电话
     *
     * @param jsylxdh 备案人联系电话
     */
    public void setJsylxdh(String jsylxdh) {
        this.jsylxdh = jsylxdh;
    }

    /**
     * 获取备案人是否采集(1已采集  0未采集)
     *
     * @return cj_type - 备案人是否采集(1已采集  0未采集)
     */
    public String getCjType() {
        return cjType;
    }

    /**
     * 设置备案人是否采集(1已采集  0未采集)
     *
     * @param cjType 备案人是否采集(1已采集  0未采集)
     */
    public void setCjType(String cjType) {
        this.cjType = cjType;
    }

    /**
     * 获取备案人信息采集时间
     *
     * @return jsycjsj - 备案人信息采集时间
     */
    public String getJsycjsj() {
        return jsycjsj;
    }

    /**
     * 设置备案人信息采集时间
     *
     * @param jsycjsj 备案人信息采集时间
     */
    public void setJsycjsj(String jsycjsj) {
        this.jsycjsj = jsycjsj;
    }

    /**
     * 获取备案人信息电子档案
     *
     * @return jsydzda - 备案人信息电子档案
     */
    public String getJsydzda() {
        return jsydzda;
    }

    /**
     * 设置备案人信息电子档案
     *
     * @param jsydzda 备案人信息电子档案
     */
    public void setJsydzda(String jsydzda) {
        this.jsydzda = jsydzda;
    }

    /**
     * 获取备案人信息备注
     *
     * @return jsybz - 备案人信息备注
     */
    public String getJsybz() {
        return jsybz;
    }

    /**
     * 设置备案人信息备注
     *
     * @param jsybz 备案人信息备注
     */
    public void setJsybz(String jsybz) {
        this.jsybz = jsybz;
    }

    /**
     * 获取车辆历史表（biz_car_history）主键。默认为空
     *
     * @return car_history_id - 车辆历史表（biz_car_history）主键。默认为空
     */
    public String getCarHistoryId() {
        return carHistoryId;
    }

    /**
     * 设置车辆历史表（biz_car_history）主键。默认为空
     *
     * @param carHistoryId 车辆历史表（biz_car_history）主键。默认为空
     */
    public void setCarHistoryId(String carHistoryId) {
        this.carHistoryId = carHistoryId;
    }

    /**
     * 获取车辆所有人变更时间
     *
     * @return car_syr_bgsj - 车辆所有人变更时间
     */
    public String getCarSyrBgsj() {
        return carSyrBgsj;
    }

    /**
     * 设置车辆所有人变更时间
     *
     * @param carSyrBgsj 车辆所有人变更时间
     */
    public void setCarSyrBgsj(String carSyrBgsj) {
        this.carSyrBgsj = carSyrBgsj;
    }

    /**
     * 获取车辆所有人变更操作人
     *
     * @return car_syr_bgczr - 车辆所有人变更操作人
     */
    public String getCarSyrBgczr() {
        return carSyrBgczr;
    }

    /**
     * 设置车辆所有人变更操作人
     *
     * @param carSyrBgczr 车辆所有人变更操作人
     */
    public void setCarSyrBgczr(String carSyrBgczr) {
        this.carSyrBgczr = carSyrBgczr;
    }

    /**
     * 获取出厂合格证明 电子档路径
     *
     * @return dzd_drys_fileurl - 出厂合格证明 电子档路径
     */
    public String getDzdDrysFileurl() {
        return dzdDrysFileurl;
    }

    /**
     * 设置出厂合格证明 电子档路径
     *
     * @param dzdDrysFileurl 出厂合格证明 电子档路径
     */
    public void setDzdDrysFileurl(String dzdDrysFileurl) {
        this.dzdDrysFileurl = dzdDrysFileurl;
    }

    public enum InnerColumn {
        id("id"),
        cph("cph"),
        jgdm("jgdm"),
        jgmc("jgmc"),
        cjr("cjr"),
        cjsj("cjsj"),
        dah("dah"),
        carQy("car_qy"),
        carSyr("car_syr"),
        carSryCode("car_sry_code"),
        carSryZjType("car_sry_zj_type"),
        hdzl("hdzl"),
        jsszks("jsszks"),
        zqyzzl("zqyzzl"),
        nkg("nkg"),
        nkk("nkk"),
        nkc("nkc"),
        zxfs("zxfs"),
        clcd("clcd"),
        djjg("djjg"),
        pxcx("pxcx"),
        hpzl("hpzl"),
        syxz("syxz"),
        carPropertyType("car_property_type"),
        clpp("clpp"),
        cllx("cllx"),
        ppxh("ppxh"),
        clxh("clxh"),
        ccdjrq("ccdjrq"),
        fzrq("fzrq"),
        qzbfq("qzbfq"),
        bxCdrq("bx_cdrq"),
        yyCdrq("yy_cdrq"),
        gxCdrq("gx_cdrq"),
        clsbm("clsbm"),
        fdjh("fdjh"),
        fdjxh("fdjxh"),
        rlzl("rlzl"),
        pl("pl"),
        gl("gl"),
        zzcmc("zzcmc"),
        csys("csys"),
        qlj("qlj"),
        hlj("hlj"),
        ltsl("ltsl"),
        ltgg("ltgg"),
        bhps("bhps"),
        zj("zj"),
        wkc("wkc"),
        wkk("wkk"),
        wkg("wkg"),
        zzl("zzl"),
        hdzk("hdzk"),
        byxz("byxz"),
        clhdfs("clhdfs"),
        ccrq("ccrq"),
        fzjg("fzjg"),
        syrId("syr_id"),
        syrName("syr_name"),
        syrSzd("syr_szd"),
        syrDn("syr_dn"),
        syxxId("syxx_id"),
        nsrq("nsrq"),
        nsId("ns_id"),
        bfType("bf_type"),
        bfFee("bf_fee"),
        bfDate("bf_date"),
        smDate("sm_date"),
        smFee("sm_fee"),
        clCqrId("cl_cqr_id"),
        clCqr("cl_cqr"),
        clCqrDn("cl_cqr_dn"),
        clCqrCode("cl_cqr_code"),
        clCqrJbrDn("cl_cqr_jbr_dn"),
        clCqrJbr("cl_cqr_jbr"),
        clCqrBz("cl_cqr_bz"),
        clCqrJl("cl_cqr_jl"),
        dzdDjzType("dzd_djz_type"),
        dzdDjzCode("dzd_djz_code"),
        dzdDjzFileurl("dzd_djz_fileurl"),
        dzdWszmType("dzd_wszm_type"),
        dzdWszmCode("dzd_wszm_code"),
        dzdWszmPh("dzd_wszm_ph"),
        dzdWszmFileurl("dzd_wszm_fileurl"),
        dzdFpType("dzd_fp_type"),
        dzdFpCode("dzd_fp_code"),
        dzdFpFileurl("dzd_fp_fileurl"),
        dzdDrysType("dzd_drys_type"),
        dzdXszfyType("dzd_xszfy_type"),
        dzdXszfyFileurl("dzd_xszfy_fileurl"),
        warnYgnsDate("warn_ygns_date"),
        warnBxDate("warn_bx_date"),
        warnNssjDate("warn_nssj_date"),
        warnGxDate("warn_gx_date"),
        ygLastData("yg_last_data"),
        ygYszh("yg_yszh"),
        ygGpsType("yg_gps_type"),
        ygGpsCode("yg_gps_code"),
        ygYyType("yg_yy_type"),
        ygYsnSx("yg_ysn_sx"),
        ygGx("yg_gx"),
        ygZj("yg_zj"),
        ygKjType("yg_kj_type"),
        ygKjAzsj("yg_kj_azsj"),
        ygKjPc("yg_kj_pc"),
        ygNewCode("yg_new_code"),
        ygNewKj("yg_new_kj"),
        gxLastData("gx_last_data"),
        gxGasGgzyxq("gx_gas_ggzyxq"),
        gxLxDn("gx_lx_dn"),
        gxLxr("gx_lxr"),
        gxGasBz("gx_gas_bz"),
        gxGasDd("gx_gas_dd"),
        gxType("gx_type"),
        bxLastData("bx_last_data"),
        bxBxrq("bx_bxrq"),
        bxBxz("bx_bxz"),
        bxBxdh("bx_bxdh"),
        bxBdzbbh("bx_bdzbbh"),
        bxBdfbbh("bx_bdfbbh"),
        bxBdWz("bx_bd_wz"),
        bxBz("bx_bz"),
        bxDaFile("bx_da_file"),
        bxTbgs("bx_tbgs"),
        bxBxlxr("bx_bxlxr"),
        bxBdCount("bx_bd_count"),
        nsLastData("ns_last_data"),
        nsNsz("ns_nsz"),
        nsNssj("ns_nssj"),
        nsEssj("ns_essj"),
        nsPc("ns_pc"),
        nsJsyid("ns_jsyId"),
        nsJsyxm("ns_jsyxm"),
        nsJsysfzh("ns_jsysfzh"),
        nsJsylxdh("ns_jsylxdh"),
        nsZt("ns_zt"),
        carType("car_type"),
        bxJbr("bx_jbr"),
        bxJbrDn("bx_jbr_dn"),
        dzdCqbgUrl("dzd_cqbg_url"),
        dzdCpbgUrl("dzd_cpbg_url"),
        dzdClbfUrl("dzd_clbf_url"),
        dzdClbmUrl("dzd_clbm_url"),
        dzdClbUrl("dzd_clb_url"),
        jsyid("jsyid"),
        jsyxm("jsyxm"),
        jsysfzh("jsysfzh"),
        jsylxdh("jsylxdh"),
        cjType("cj_type"),
        jsycjsj("jsycjsj"),
        jsydzda("jsydzda"),
        jsybz("jsybz"),
        carHistoryId("car_history_id"),
        carSyrBgsj("car_syr_bgsj"),
        carSyrBgczr("car_syr_bgczr"),
        dzdDrysFileurl("dzd_drys_fileurl");

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