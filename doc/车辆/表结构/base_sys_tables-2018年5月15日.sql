/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2018/11/14 18:19:38                          */
/*==============================================================*/


drop table if exists biz_car;

drop table if exists biz_car_annual_exam;

drop table if exists biz_car_ba;

drop table if exists biz_car_e_record;

drop table if exists biz_car_gas;

drop table if exists biz_car_insurance_jq;

drop table if exists biz_car_property;

drop table if exists biz_car_usage;

drop table if exists biz_car_warn;


DROP TABLE IF EXISTS biz_car;
CREATE TABLE biz_car (
  id varchar(32) NOT NULL COMMENT 'id',
  cph varchar(16) DEFAULT NULL COMMENT '车牌号',
  jgdm varchar(32) DEFAULT NULL COMMENT '机构代码',
  jgmc varchar(32) DEFAULT NULL COMMENT '机构名称',
  cjr varchar(32) DEFAULT NULL COMMENT '创建人',
  cjsj varchar(32) DEFAULT NULL COMMENT '创建时间',
  dah varchar(32) DEFAULT NULL COMMENT '档案号',
  pxcx varchar(6) DEFAULT NULL COMMENT '培训车型  准驾车型 [ZDCLK0040] ',
  hpzl varchar(6) DEFAULT NULL COMMENT '号牌种类 车辆_号牌种类 [ZDCLK1036] 1、学牌 2、地牌 ',
  syxz varchar(6) DEFAULT NULL COMMENT '使用性质  [ZDCLK1037] 1、教练 2、非教练 3、非营运',
  car_property_type varchar(6) DEFAULT NULL COMMENT '车辆产权状态 [ZDCLK1041] 1、学牌车 2、非学牌车 3、已报废车 4、已售出',
  clpp varchar(32) DEFAULT NULL COMMENT '车辆品牌',
  cllx varchar(32) DEFAULT NULL COMMENT '车辆类型',
  ppxh varchar(64) DEFAULT NULL COMMENT '品牌型号',
  clxh varchar(16) DEFAULT NULL COMMENT '车辆型号',
  ccdjrq varchar(20) DEFAULT NULL COMMENT '初次登记日期',
  qzbfq varchar(20) DEFAULT NULL COMMENT '强制报废期',
  clsbm varchar(20) DEFAULT NULL COMMENT '车辆识别码',
  fdjh varchar(20) DEFAULT NULL COMMENT '发动机号',
  fdjxh varchar(20) DEFAULT NULL COMMENT '发动机型号',
  rlzl varchar(2) DEFAULT NULL COMMENT '燃料种类 车辆燃油类型 [ZDCLK1038] 1、汽油 2、柴油',
  pl int(11) DEFAULT NULL COMMENT '排量',
  gl int(11) DEFAULT NULL COMMENT '功率',
  zzcmc varchar(20) DEFAULT NULL COMMENT '制造厂名称',
  csys varchar(6) DEFAULT NULL COMMENT '车身颜色',
  qlj int(11) DEFAULT NULL COMMENT '前轮距',
  hlj int(11) DEFAULT NULL COMMENT '后轮距',
  ltsl int(11) DEFAULT NULL COMMENT '轮胎数量',
  ltgg varchar(16) DEFAULT NULL COMMENT '轮胎规格',
  bhps int(11) DEFAULT NULL COMMENT '板簧片数',
  zj int(11) DEFAULT NULL COMMENT '轴距',
  wkc int(11) DEFAULT NULL COMMENT '外廓长',
  wkk int(11) DEFAULT NULL COMMENT '外廓宽',
  wkg int(11) DEFAULT NULL COMMENT '外廓高',
  zzl int(11) DEFAULT NULL COMMENT '总质量',
  hdzk int(11) DEFAULT NULL COMMENT '核定载客',
  byxz varchar(6) DEFAULT NULL COMMENT '保用性质',
  clhdfs varchar(6) DEFAULT NULL COMMENT '车辆获得方式',
  ccrq varchar(20) DEFAULT NULL COMMENT '出厂日期',
  syr_name varchar(32) DEFAULT NULL COMMENT '使用人姓名',
  syr_szd varchar(32) DEFAULT NULL COMMENT '使用人所在地',
  syr_dn varchar(128) DEFAULT NULL COMMENT '使用人联系方式',
  syxx_id varchar(32) DEFAULT NULL COMMENT '使用信息id',
  nsrq varchar(20) DEFAULT NULL COMMENT '年审日期',
  ns_id varchar(32) DEFAULT NULL COMMENT '年审id',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='车辆基础表';



-- ----------------------------
-- Records of biz_car
-- ----------------------------

-- ----------------------------
-- Table structure for biz_car_annual_exam
-- ----------------------------
DROP TABLE IF EXISTS biz_car_annual_exam;
CREATE TABLE biz_car_annual_exam (
  id varchar(32) NOT NULL COMMENT 'id',
  cl_id varchar(32) DEFAULT NULL COMMENT '车辆id',
  cph varchar(16) DEFAULT NULL COMMENT '车牌号',
  nsz varchar(32) DEFAULT NULL COMMENT '年审至',
  nssj varchar(20) DEFAULT NULL COMMENT '年审时间',
  essj varchar(20) DEFAULT NULL COMMENT '二审时间',
  pc varchar(6) DEFAULT NULL COMMENT '批次',
  jsyId varchar(32) DEFAULT NULL COMMENT '驾驶员ID',
  jsyxm varchar(12) DEFAULT NULL COMMENT '驾驶员姓名',
  jsysfzh varchar(18) DEFAULT NULL COMMENT '驾驶员身份证号',
  jsylxdh varchar(16) DEFAULT NULL COMMENT '驾驶员联系电话',
  zt varchar(6) DEFAULT NULL COMMENT '年审状态',
  jgdm varchar(32) DEFAULT NULL COMMENT '机构代码',
  jgmc varchar(32) DEFAULT NULL COMMENT '机构名称',
  cjr varchar(32) DEFAULT NULL COMMENT '创建人',
  cjsj varchar(32) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='车辆年审信息';

-- ----------------------------
-- Table structure for biz_car_ba
-- ----------------------------
DROP TABLE IF EXISTS biz_car_ba;
CREATE TABLE biz_car_ba (
  id varchar(32) NOT NULL COMMENT 'id',
  cl_id varchar(32) DEFAULT NULL COMMENT '车辆ID',
  cph varchar(16) DEFAULT NULL COMMENT '车牌号',
  yszh varchar(32) DEFAULT NULL COMMENT '运输证号',
  djrq varchar(32) DEFAULT NULL COMMENT '登记日期',
  gps_type varchar(2) DEFAULT NULL COMMENT '是否安装GPS  是/否 [ZDCLK1034] ',
  gps_code varchar(32) DEFAULT NULL COMMENT 'GPS号码',
  yy_type varchar(2) DEFAULT NULL COMMENT ' 车辆备案_运营状态 [ZDCLK1033] 营运状态1、运营 2、注销 0、未登记',
  ysn_sx varchar(2) DEFAULT NULL COMMENT '1、14年上线  0、不是   是/否 [ZDCLK1034]',
  gx varchar(32) DEFAULT NULL COMMENT '更新(BU列)',
  zj int(11) DEFAULT NULL COMMENT '轴距',
  kj_type varchar(2) DEFAULT NULL COMMENT '车辆备案_卡机安装状态 [ZDCLK1035] 卡机安装状态 1、已安装 0、未安装',
  kj_azsj varchar(32) DEFAULT NULL COMMENT '卡机安装时间',
  kj_pc varchar(32) DEFAULT NULL COMMENT '卡机批次',
  new_code varchar(32) DEFAULT NULL COMMENT '明涛成功新证号',
  new_kj varchar(2) DEFAULT NULL COMMENT '新卡机',
  bz varchar(255) DEFAULT NULL COMMENT '备注',
  jgdm varchar(32) DEFAULT NULL COMMENT '机构代码',
  jgmc varchar(32) DEFAULT NULL COMMENT '机构名称',
  cjr varchar(32) DEFAULT NULL COMMENT '创建人',
  cjsj varchar(32) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='汽车运管备案表';


-- ----------------------------
-- Table structure for biz_car_e_record
-- ----------------------------
DROP TABLE IF EXISTS biz_car_e_record;
CREATE TABLE biz_car_e_record (
  id varchar(32) NOT NULL COMMENT 'id',
  cl_id varchar(32) DEFAULT NULL COMMENT '车辆ID',
  cph varchar(16) DEFAULT NULL COMMENT '车牌号',
  djz_type varchar(2) DEFAULT NULL COMMENT '机动车登记证书状态  有/无 [ZDCLK1039] 1、有 0、无',
  djz_code varchar(32) DEFAULT NULL COMMENT '机动车登记证书编号',
  wszm_type varchar(2) DEFAULT NULL COMMENT '购置税完税证明状态  有/无 [ZDCLK1039] 1、有 0、无',
  wszm_code varchar(32) DEFAULT NULL COMMENT '购置税完税证明证号',
  wszm_ph varchar(32) DEFAULT NULL COMMENT '购置税完税证明票号',
  fp_type varchar(2) DEFAULT NULL COMMENT '发票状态  有/无 [ZDCLK1039] 1、有 0、无',
  fp_code varchar(32) DEFAULT NULL COMMENT '发票号码',
  cchg_type varchar(2) DEFAULT NULL COMMENT '出厂合格证明 有/无 [ZDCLK1039] 1、有 0、无',
  jyhg_type varchar(2) DEFAULT NULL COMMENT '检验合格证明  有/无 [ZDCLK1039] 1、有 0、无',
  xlhg_type varchar(2) DEFAULT NULL COMMENT '修理合格证明  有/无 [ZDCLK1039] 1、有 0、无',
  xszfy_type varchar(2) DEFAULT NULL COMMENT '行驶证扫描件状态 有/无 [ZDCLK1039] 1、有 0、无',
  bz varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='车辆电子档案基本信息（扫描件）';



-- ----------------------------
-- Table structure for biz_car_property
-- ----------------------------
DROP TABLE IF EXISTS biz_car_property;
CREATE TABLE biz_car_property (
  id varchar(32) NOT NULL DEFAULT '' COMMENT 'id',
  cl_id varchar(32) DEFAULT NULL COMMENT '车辆ID',
  cph varchar(16) DEFAULT NULL COMMENT '车牌号',
  lx varchar(6) DEFAULT NULL COMMENT '类型',
  ck float DEFAULT NULL COMMENT '车款',
  zb varchar(6) DEFAULT NULL COMMENT '质保',
  yd varchar(6) DEFAULT NULL COMMENT '异动次数',
  cqr varchar(32) DEFAULT NULL COMMENT '产权人',
  property_type varchar(2) DEFAULT NULL COMMENT '产权状态 1、内部产权变更 2、学牌转非学牌 3、车辆报废 4、车辆售卖',
  cqr_szd varchar(32) DEFAULT NULL COMMENT '产权人所在地',
  cqr_dn varchar(128) DEFAULT NULL COMMENT '产权人联系方式',
  cqr_code varchar(128) DEFAULT NULL COMMENT '产权人证件号',
  jgdm varchar(32) DEFAULT NULL COMMENT '机构代码',
  jgmc varchar(32) DEFAULT NULL COMMENT '机构名称',
  cjr varchar(32) DEFAULT NULL COMMENT '创建人',
  jbr varchar(32) DEFAULT NULL COMMENT '经办人',
  jbr_dn varchar(32) DEFAULT NULL COMMENT '经办人电话',
  cjsj varchar(32) DEFAULT NULL COMMENT '创建时间',
  bz varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='车辆产权明细表';

-- ----------------------------
-- Records of biz_car_property
-- ----------------------------

-- ----------------------------
-- Table structure for biz_car_usage
-- ----------------------------
DROP TABLE IF EXISTS biz_car_usage;
CREATE TABLE biz_car_usage (
  id varchar(32) NOT NULL COMMENT 'id',
  cl_id varchar(32) DEFAULT NULL COMMENT '车辆ID',
  cph varchar(16) DEFAULT NULL COMMENT '车牌号',
  syr_name varchar(32) DEFAULT NULL COMMENT '使用人姓名',
  syr_id varchar(32) DEFAULT NULL COMMENT '使用人编号',
  lx varchar(6) DEFAULT NULL COMMENT '类型',
  syr_szd varchar(32) DEFAULT NULL COMMENT '使用人所在地',
  syr_dn varchar(128) DEFAULT NULL COMMENT '使用人联系方式',
  del_type varchar(2) DEFAULT NULL COMMENT '逻辑删除状态 [ZDCLK1040] 1未删除 0已经删除 ',
  jgdm varchar(32) DEFAULT NULL COMMENT '机构代码',
  jgmc varchar(32) DEFAULT NULL COMMENT '机构名称',
  cjr varchar(32) DEFAULT NULL COMMENT '创建人',
  cjsj varchar(32) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='车辆使用人表';

-- ----------------------------
-- Records of biz_car_usage
-- ----------------------------


-- ----------------------------
-- Table structure for biz_car_insurance_jq
-- ----------------------------
DROP TABLE IF EXISTS biz_car_insurance_jq;
CREATE TABLE biz_car_insurance_jq (
  id varchar(32) NOT NULL COMMENT 'id',
  cl_id varchar(32) DEFAULT NULL COMMENT '车辆id',
  cph varchar(16) DEFAULT NULL COMMENT '车牌号',
  bxrq varchar(20) DEFAULT NULL COMMENT '保险日期',
  bxz varchar(20) DEFAULT NULL COMMENT '保险至',
  bxdh varchar(20) DEFAULT NULL COMMENT '保险电话',
  bdzbbh varchar(16) DEFAULT NULL COMMENT '保单正本编号',
  bdfbbh varchar(16) DEFAULT NULL COMMENT '保单副本编号',
  bd_wz varchar(255) DEFAULT NULL COMMENT '保单位置',
  bz varchar(255) DEFAULT NULL COMMENT '备注',
  bd_count varchar(10) DEFAULT NULL COMMENT '保单数量',
  jgdm varchar(32) DEFAULT NULL COMMENT '机构代码',
  jgmc varchar(32) DEFAULT NULL COMMENT '机构名称',
  cjr varchar(32) DEFAULT NULL COMMENT '创建人',
  cjsj varchar(32) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='车辆保险表';

-- ----------------------------
-- Records of biz_car_insurance_jq
-- ----------------------------


-- ----------------------------
-- Table structure for biz_car_gas
-- ----------------------------
DROP TABLE IF EXISTS biz_car_gas;
CREATE TABLE biz_car_gas (
  id varchar(32) NOT NULL COMMENT 'id',
  cl_id varchar(32) DEFAULT NULL COMMENT '车辆ID',
  cph varchar(16) DEFAULT NULL COMMENT '车牌号',
  yszh varchar(32) DEFAULT NULL COMMENT '运输证号',
  bz varchar(255) DEFAULT NULL COMMENT '备注',
  jgdm varchar(32) DEFAULT NULL COMMENT '机构代码',
  jgmc varchar(32) DEFAULT NULL COMMENT '机构名称',
  cjr varchar(32) DEFAULT NULL COMMENT '创建人',
  cjsj varchar(32) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='油改气记录表';

-- ----------------------------
-- Records of biz_car_gas
-- ----------------------------

-- ----------------------------
-- Table structure for biz_car_file
-- ----------------------------
DROP TABLE IF EXISTS biz_car_file;
CREATE TABLE biz_car_file (
  id varchar(32) NOT NULL COMMENT 'id',
  cl_id varchar(32) DEFAULT NULL COMMENT '车辆ID',
  cph varchar(16) DEFAULT NULL COMMENT '车牌号',
  file_type varchar(32) DEFAULT NULL COMMENT '文件类型',
  file_code varchar(32) DEFAULT NULL COMMENT '文件编号(车牌号码_类型)',
  seq varchar(32) DEFAULT NULL COMMENT '文件序号',
  file_url varchar(255) DEFAULT NULL COMMENT '文件地址',
  jgdm varchar(32) DEFAULT NULL COMMENT '机构代码',
  jgmc varchar(32) DEFAULT NULL COMMENT '机构名称',
  cjr varchar(32) DEFAULT NULL COMMENT '创建人',
  cjsj varchar(32) DEFAULT NULL COMMENT '创建时间',
  
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='车辆文件记录表';

-- ----------------------------
-- Records of biz_car_file
-- ----------------------------



/*==============================================================*/
/* Table: biz_car_warn                                          */
/*==============================================================*/
create table biz_car_warn
(
   id                   varchar(32) not null comment 'id',
   cl_id                varchar(32) comment '车辆ID',
   cph                  varchar(16) comment '车牌号',
   warn_date            varchar(32) comment '提醒日期(yyyy_mm_dd)',
   expiry_yyyy            varchar(16) comment '截止年',
   expiry_mm              varchar(16) comment '截止月',
   warn_type            varchar(32) comment '提醒类型(1、车辆年审提醒 2、运管年审提醒 3、改气年审提醒 4、续保提醒 5、报废提醒)',
   warn_pc              char(10) comment '提醒批次 以年为单位，一年批几次',
      expiry_date          varchar(32) comment '截止日期',
   warn_dispose         varchar(2) comment '提醒是否处理 是/否 [ZDCLK1034]',
   dispose_code         varchar(32) comment '处理编号',
   PRIMARY KEY (id)
);
alter table biz_car_warn comment '车辆提醒表';



ALTER TABLE `biz_car_property`
ADD COLUMN `past_cqr`  varchar(32) NULL COMMENT '原产权人' AFTER `yd`;

ALTER TABLE `biz_car_property`
ADD COLUMN `bz`  varchar(255) NULL COMMENT '备注' AFTER `cjsj`;


ALTER TABLE `biz_car_property`
ADD COLUMN `jbr`  varchar(32) NULL COMMENT '经办人' AFTER `property_type`,
ADD COLUMN `jbr_dn`  varchar(32) NULL COMMENT '经办人电话' AFTER `jbr`;



