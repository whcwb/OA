/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2018/8/27 9:46:43                            */
/*==============================================================*/



/*==============================================================*/
/* Table: archives_record                                       */
/*==============================================================*/
create table archives_record
(
   id                   varchar(32) not null comment '主键',
   trainee_id           varchar(32) comment '学员id',
   trainee_name         varchar(30) comment '学员姓名',
   serial_num           varchar(20) comment '学员流水号',
   archives_code        varchar(32) comment '档案柜编码',
   in_time              varchar(20) comment '入库时间',
   out_time             varchar(20) comment '出库时间',
   input_person         varchar(64) comment '入库人',
   output_person        varchar(64) comment '出库人',
   primary key (id)
);

alter table archives_record comment '档案记录表';

/*==============================================================*/
/* Table: charge_institution_manage                             */
/*==============================================================*/
create table charge_institution_manage
(
   id                   varchar(32) comment 'ID',
   institution_code     varchar(32) comment '机构代码',
   institution_name     varchar(32) comment '机构名称',
   charge_code          varchar(32) comment '收费项代码',
   operater             varchar(64) comment '操作人',
   opetate_time         varchar(20) comment '操作时间'
);

alter table charge_institution_manage comment '收费机构管理';

/*==============================================================*/
/* Table: charge_item_management                                */
/*==============================================================*/
create table charge_item_management
(
   id                   varchar(32) not null comment '唯一标识',
   institution_name     varchar(32) comment '机构名称',
   charge_name          varchar(20) comment '收费项名',
   car_type             varchar(10) comment '车型',
   amount               int comment '金额',
   in_out_type          varchar(4) comment '收支类型',
   status               varchar(4) comment '状态',
   creater              varchar(32) comment '创建人',
   create_time          varchar(20) comment '创建时间',
   primary key (id)
);

alter table charge_item_management comment '收费项管理表';

/*==============================================================*/
/* Table: charge_management                                     */
/*==============================================================*/
create table charge_management
(
   id                   varchar(32) not null comment '唯一标识',
   charge_code          varchar(32) comment '收费项',
   charge_name          varchar(20) comment '收费项名称',
   in_out_type          varchar(4) comment '收支类型',
   charge_fee           int comment '收费金额',
   charge_time          varchar(20) comment '收费时间',
   charge_type          varchar(4) comment '收款方式',
   receiver             varchar(64) comment '收款人',
   trainee_id           varchar(32) comment '学员id',
   trainee_name         varchar(20) comment '学员姓名',
   id_card_no           varchar(20) comment '学员身份证号',
   charge_source        varchar(32) comment '收费来源',
   trainee_source       varchar(10) comment '学员身份',
   verifier             varchar(32) comment '确认人',
   verify_time          varchar(20) comment '确认时间',
   remark               varchar(200) comment '备注',
   bank_serial_num      varchar(100) comment '银行流水号',
   check_status         varchar(4) comment '对账标记',
   primary key (id)
);

alter table charge_management comment '收费管理表';

/*==============================================================*/
/* Table: coach_management                                      */
/*==============================================================*/
create table coach_management
(
   id                   varchar(32) not null comment '主键',
   coach_name           varchar(32) comment '教练姓名',
   gender               varchar(4) comment '性别',
   head_img             varchar(200),
   driving_type         varchar(10) comment '准驾车型',
   phone                varchar(20) comment '手机号码',
   coach_rate           varchar(10) comment '教练评级',
   coach_sub            varchar(10) comment '培训科目',
   trainee_num          int comment '学员数量',
   driving_years        int comment '驾龄',
   license_start_time   varchar(20) comment '驾驶证有效期开始时间',
   license_end_time     varchar(20) comment '驾驶证有效期结束时间',
   coach_num            varchar(64) comment '教练证编号',
   coach_start_time     varchar(20) comment '教练证开始时间',
   coach_end_time       varchar(20) comment '教练证结束时间',
   coach_status         varchar(4) comment '教练状态',
   record_num           varchar(64) comment '档案编号',
   open_id              varchar(64) comment '微信openId',
   institution_code     varchar(32) comment '机构代码',
   institution_name     varchar(20) comment '机构名称',
   area                 varchar(20) comment '所属区域',
   remark               varchar(200) comment '备注',
   primary key (id)
);

alter table coach_management comment '教练员信息管理';

/*==============================================================*/
/* Table: coach_trainee_rercord                                 */
/*==============================================================*/
create table coach_trainee_rercord
(
   id                   varchar(32) not null comment '主键',
   coach_id             varchar(32) comment '教练id',
   coach_name           varchar(30) comment '教练姓名',
   trainee_id           varchar(32) comment '学员id',
   trainee_name         varchar(30) comment '学员姓名',
   id_card_no           varchar(20) comment '学员身份证号',
   allot_time           varchar(20) comment '分配时间',
   status               varchar(4) comment '是否合格',
   allot_sub            varchar(10) comment '分配科目',
   primary key (id)
);

alter table coach_trainee_rercord comment '教练学员分配记录表';

/*==============================================================*/
/* Table: coach_valuation                                       */
/*==============================================================*/
create table coach_valuation
(
   id                   varchar(32) comment 'ID',
   coach_id             varchar(32) comment '教练id',
   coach_name           varchar(32) comment '教练姓名',
   trainee_id           varchar(32) comment '学员id',
   trainee_name         varchar(32) comment '学员姓名',
   content              varchar(200) comment '评价内容',
   valuate_time         varchar(20) comment '评价时间',
   coach_score          varchar(4) comment '教练评分'
);

alter table coach_valuation comment '教练评价表';

/*==============================================================*/
/* Table: complain_record                                       */
/*==============================================================*/
create table complain_record
(
   id                   varchar(32) comment 'ID',
   trainee_id           varchar(32) comment '学员id',
   trainee_name         varchar(32) comment '学员姓名',
   complain_content     varchar(200) comment '投诉内容',
   complain_time        varchar(20) comment '投诉时间',
   operater             varchar(64) comment '反馈人',
   operate_time         varchar(20) comment '反馈时间',
   feedback             varchar(200) comment '反馈内容'
);

alter table complain_record comment '投诉记录表';

/*==============================================================*/
/* Table: dropout_record                                        */
/*==============================================================*/
create table dropout_record
(
   id                   varchar(32) comment 'ID',
   trainee_id           varchar(32) comment '学员id',
   trainee_name         varchar(32) comment '学员姓名',
   id_card_no           varchar(20) comment '学员身份证号',
   reason               varchar(200) comment '原因',
   creator              varchar(64) comment '创建人',
   create_time          varchar(20) comment '创建时间',
   operator             varchar(64) comment '操作人',
   operate_time         varchar(20) comment '操作时间',
   status               varchar(4) comment '状态',
   dropout_fee          int comment '退费金额',
   remark               varchar(200) comment '备注'
);

/*==============================================================*/
/* Table: elec_archives_manage                                  */
/*==============================================================*/
create table elec_archives_manage
(
   id                   varchar(32) comment 'ID',
   type                 varchar(4) comment '类型',
   trainee_id           varchar(32) comment '学员id',
   file_path            varchar(200) comment '文件路径',
   upload_time          varchar(20) comment '上传时间'
);

alter table elec_archives_manage comment '电子档案管理表';

/*==============================================================*/
/* Table: group_buy_record                                      */
/*==============================================================*/
create table group_buy_record
(
   group_code           varchar(32) not null comment '团购编号',
   name                 varchar(30) comment '团购成员',
   id_card_no           varchar(20) comment '学员身份证号',
   group_time           varchar(20) comment '团购时间',
   qualify              varchar(4) comment '是否合规',
   operator             varchar(64) comment '操作人',
   operate_time         varchar(20) comment '操作时间',
   primary key (group_code)
);

alter table group_buy_record comment '团购记录表';

/*==============================================================*/
/* Table: record_management                                     */
/*==============================================================*/
create table record_management
(
   id                   varchar(32) not null comment '主键',
   position             varchar(100) comment '位置',
   capacity             int comment '容量',
   num                  int comment '现有数量',
   update_time          varchar(20) comment '修改时间',
   updater              varchar(64) comment '修改人',
   sub                  varchar(10) comment '科目',
   archives_code        varchar(32) comment '编码',
   primary key (id)
);

alter table record_management comment '档案柜管理表';

/*==============================================================*/
/* Table: reduce_management                                     */
/*==============================================================*/
create table reduce_management
(
   id                   varchar(32) not null comment '主键',
   reduce_name          varchar(32) comment '优惠项名称',
   reduce_amount        int comment '优惠金额',
   remark               varchar(200) comment '备注',
   group_num            int comment '团购限制人数',
   creater              varchar(32) comment '创建人',
   create_time          varchar(20) comment '创建时间',
   reduce_start_time    varchar(20) comment '优惠开始时间',
   reduce_end_time      varchar(20) comment '优惠结束时间',
   institution_name     varchar(20) comment '机构名称',
   institution_code     varchar(32) comment '机构代码',
   verify_level         varchar(4) comment '审核级别',
   verifier             varchar(32) comment '审核人',
   primary key (id)
);

alter table reduce_management comment '优惠项管理表';

/*==============================================================*/
/* Table: trainee_information                                   */
/*==============================================================*/
create table trainee_information
(
   id                   varchar(32) not null comment '主键',
   name                 varchar(32) comment '学员姓名',
   id_card_no           varchar(20) comment '身份证号',
   head_img             varchar(200),
   phone                varchar(20) comment '手机号码',
   gender               varchar(4) comment '性别',
   source               varchar(4) comment '来源',
   address              varchar(100) comment '地址',
   status               varchar(4) comment '学员当前状态',
   id_card_start_time   varchar(20) comment '证件有效期开始时间',
   id_card_end_time     varchar(30) comment '证件有效期结束时间',
   institution_code     varchar(32) comment '机构代码',
   institution_name     varchar(20) comment '机构名称',
   class_type           varchar(4) comment '班型',
   car_type             varchar(4) comment '车型',
   registration_time    varchar(20) comment '报名时间',
   registration_fee     int comment '报名费用',
   reduce_price         int comment '优惠金额',
   reduce_code          varchar(32) comment '优惠编码',
   reduce_name          varchar(20) comment '优惠名称',
   reduce_remark        varchar(100) comment '优惠备注',
   owe_amount           int comment '欠费金额',
   real_pay             int comment '实付金额',
   installment          varchar(4) comment '是否分期',
   arrearage            varchar(4) comment '是否欠费',
   info_check_status    varchar(4) comment '基本信息审核状态',
   info_check_time      varchar(20) comment '基本信息审核时间',
   reduce_status        varchar(4) comment '优惠信息审核状态',
   reduce_check_time    varchar(20) comment '优惠信息审核时间',
   charge_status        varchar(4) comment '收费状态',
   confirm_time         varchar(20) comment '收费确认时间',
   accept_status        varchar(4) comment '受理状态',
   accept_time          varchar(20) comment '受理成功时间',
   serial_num           varchar(20) comment '学员流水号',
   fir_sub              varchar(4) comment '科目一状态',
   fir_sub_payment_time varchar(20) comment '科目一初考缴费时间',
   fir_sub_test_time    varchar(20) comment '科目一约考时间',
   fir_sub_test_num     int comment '科目一考试次数',
   fir_sub_train_status varchar(4) comment '科目一培训状态',
   sec_sub              varchar(4) comment '科目二状态',
   sec_sub_payment_time varchar(20) comment '科目二初考缴费时间',
   sec_sub_test_time    varchar(20) comment '科目二约考时间',
   sec_sub_test_num     int comment '科目二考试次数',
   sec_sub_train_status varchar(4) comment '科目二培训状态',
   third_sub            varchar(4) comment '科目三状态',
   third_sub_payment_time varchar(20) comment '科目三初考缴费时间',
   third_sub_test_time  varchar(20) comment '科目三约考时间',
   third_sub_test_num   int comment '科目三考试次数',
   third_sub_train_status varchar(4) comment '科目三培训状态',
   forth_sub            varchar(4) comment '科目四状态',
   record_lib           varchar(32) comment '档案所在库',
   creator              varchar(20) comment '创建人',
   create_Time          varchar(20) comment '创建时间',
   modifier             varchar(64) comment '修改人',
   modify_Time          varchar(20) comment '修改时间',
   indate_start_time    varchar(20) comment '学员有效期开始时间',
   indate_end_time      varchar(20) comment '学员有效期结束时间',
   second_subject_coach varchar(32) comment '科二教练员',
   third_subject_coach  varchar(32) comment '科三教练员',
   referrer             varchar(32) comment '推荐人',
   remark               varchar(200) comment '备注',
   open_id              varchar(64) comment '微信opne_id',
   elec_sign            varchar(2000) comment '电子签名',
   primary key (id)
);

alter table trainee_information comment '学生基本信息表';

/*==============================================================*/
/* Table: trainee_status                                        */
/*==============================================================*/
create table trainee_status
(
   id                   varchar(32) not null comment '唯一标识',
   trainee_id           varchar(32) comment '学员id',
   trainee_name         varchar(30) comment '学员姓名',
   id_card_no           varchar(20) comment '学员身份证',
   operator             varchar(64) comment '操作人',
   status               varchar(4) comment '状态',
   type                 varchar(20) comment '类型',
   remark               varchar(200) comment '备注',
   operate_time         varchar(20) comment '操作时间',
   primary key (id)
);

alter table trainee_status comment '学员状态表';

/*==============================================================*/
/* Table: trainee_test_info                                     */
/*==============================================================*/
create table trainee_test_info
(
   ID                   varchar(32) not null comment '唯一标识',
   trainee_id           varchar(32) comment '学员id',
   trainee_name         varchar(30) comment '学员名称',
   id_card_no           varchar(20) comment '学员身份证号',
   subject              varchar(4) comment '科目',
   test_place           varchar(32) comment '考试场地',
   test_result          varchar(4) comment '考试结果',
   remark               varchar(200) comment '备注',
   test_time            varchar(20) comment '约考时间',
   pay_status           varchar(4) comment '缴费状态',
   operate_time         varchar(20) comment '操作时间',
   operator             varchar(64) comment '操作人',
   primary key (ID)
);

alter table trainee_test_info comment '学员考试信息表';

/*==============================================================*/
/* Table: training_record                                       */
/*==============================================================*/
create table training_record
(
   id                   varchar(32) not null comment '主键',
   coach_id             varchar(32) comment '教练id',
   coach_name           varchar(30) comment '教练姓名',
   trainee_id           varchar(32) comment '学员id',
   trainee_name         varchar(30) comment '学员姓名',
   train_start_time     varchar(20) comment '培训开始时间',
   train_end_time       varchar(20) comment '培训结束时间',
   train_times          int comment '培训时长',
   car_type             varchar(4) comment '车型',
   operate_time         varchar(20) comment '操作时间',
   operator             varchar(64) comment '操作人',
   primary key (id)
);

alter table training_record comment '培训记录表';

/*
Navicat MySQL Data Transfer

Source Server         : 47.98.39.45
Source Server Version : 50636
Source Host           : 47.98.39.45:9097
Source Database       : oa

Target Server Type    : MYSQL
Target Server Version : 50636
File Encoding         : 65001

Date: 2018-08-27 11:51:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_clk_ptjs
-- ----------------------------
CREATE TABLE `sys_clk_ptjs` (
  `JS_ID` varchar(32) NOT NULL COMMENT '角色id',
  `JSMC` varchar(32) DEFAULT NULL COMMENT '角色名称',
  `JSLX` varchar(32) DEFAULT NULL COMMENT '角色类型',
  `CJSJ` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `CJR` varchar(64) DEFAULT NULL COMMENT '创建人',
  `XGR` varchar(64) DEFAULT NULL COMMENT '修改人',
  `XGSJ` datetime(6) DEFAULT NULL COMMENT '修改时间',
  `ZT` char(2) DEFAULT NULL COMMENT '状态',
  `JGDM` varchar(20) DEFAULT NULL COMMENT '机构代码',
  `SM` text COMMENT '说明',
  PRIMARY KEY (`JS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_clk_ptyh
-- ----------------------------

CREATE TABLE `sys_clk_ptyh` (
  `YHID` varchar(32) NOT NULL COMMENT '用户id',
  `ZH` varchar(32) DEFAULT NULL COMMENT '账号',
  `MM` varchar(32) DEFAULT NULL COMMENT '密码',
  `SJH` varchar(32) DEFAULT NULL COMMENT '手机号',
  `CJR` varchar(64) DEFAULT NULL COMMENT '创建人',
  `CJSJ` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `XGR` varchar(64) DEFAULT NULL COMMENT '修改人',
  `XGSJ` datetime(6) DEFAULT NULL COMMENT '修改时间',
  `ZT` char(2) DEFAULT NULL COMMENT '状态',
  `JGDM` varchar(20) DEFAULT NULL COMMENT '机构代码',
  `XM` varchar(32) DEFAULT NULL COMMENT '姓名',
  `LX` char(2) DEFAULT NULL COMMENT '类型',
  `XB` char(1) DEFAULT NULL COMMENT '性别',
  `ZJHM` varchar(32) DEFAULT NULL COMMENT '证件号码',
  `MMYXQ` datetime(6) DEFAULT NULL COMMENT '密码有效期',
  `ZW` varchar(32) DEFAULT NULL COMMENT '职务',
  PRIMARY KEY (`YHID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='平台用户表';


INSERT INTO `sys_clk_ptyh` VALUES ('1', 'admini', '4DA3BB20330A34F4', '手机号', 'cjr', '2018-03-16 06:52:00.000000', null, null, '01', '100', '超级管理员', 'su', '1', '证件号码', null, '管理员');
-- ----------------------------
-- Table structure for sys_fwgn
-- ----------------------------

CREATE TABLE `sys_fwgn` (
  `GNDM` varchar(32) NOT NULL COMMENT '功能代码',
  `GNMC` varchar(32) DEFAULT NULL COMMENT '功能名称',
  `FWDM` varchar(32) DEFAULT NULL COMMENT '服务代码',
  `CJSJ` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `CJR` varchar(64) DEFAULT NULL COMMENT '创建人',
  `XGSJ` datetime(6) DEFAULT NULL COMMENT '修改时间',
  `XGR` varchar(64) DEFAULT NULL COMMENT '修改人',
  `ZT` char(2) DEFAULT NULL COMMENT '状态',
  `BZ` varchar(255) DEFAULT NULL COMMENT '备注',
  `URL` varchar(255) DEFAULT NULL COMMENT 'URL',
  `FJD` varchar(32) DEFAULT NULL COMMENT '父节点',
  `TZDZ` varchar(32) DEFAULT NULL COMMENT '跳转地址',
  `TB` varchar(32) DEFAULT NULL COMMENT '图标',
  `API_QZ` varchar(64) DEFAULT NULL COMMENT 'API前缀',
  `API_HZ` varchar(64) DEFAULT NULL COMMENT 'API后缀',
  `PX` double DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`GNDM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `sys_fwgn` VALUES ('system', '系统管理', '1', '2018-04-09 00:00:00.000000', '初始导入', '2018-05-03 14:47:00.000000', '1-超级管理员', '00', null, 'system', null, null, 'navicon-round', '/', null, '1');
INSERT INTO `sys_fwgn` VALUES ('system-advertising', '活动管理', '1', '2018-04-09 00:00:00.000000', '初始导入', '2018-04-28 11:18:00.000000', '1-超级管理员', '00', null, 'system-advertising', 'system', null, 'images', '/api/hd/', null, '11');
INSERT INTO `sys_fwgn` VALUES ('system-daily', '日志管理', '1', '2018-04-09 00:00:00.000000', '初始导入', '2018-04-28 11:00:03.000000', '1-超级管理员', '00', null, 'system-daily', 'system', null, 'android-create', '/api/rz/', null, '7');
INSERT INTO `sys_fwgn` VALUES ('system-dictionary', '字典管理', '1', '2018-04-09 00:00:00.000000', '初始导入', '2018-04-28 10:58:04.000000', '1-超级管理员', '00', null, 'system-dictionary', 'system', null, 'ios-keypad', '/api/zd/', null, '4');
INSERT INTO `sys_fwgn` VALUES ('system-framework', '组织机构', '1', '2018-04-09 00:00:00.000000', '初始导入', '2018-04-28 10:57:00.000000', '1-超级管理员', '00', null, 'system-framework', 'system', null, 'flag', '/api/gn/', null, '3');
INSERT INTO `sys_fwgn` VALUES ('system-function', '功能管理', '1', '2018-04-09 00:00:00.000000', '初始导入', '2018-04-28 10:58:00.000000', '1-超级管理员', '00', null, 'system-function', 'system', null, 'ios-cog', '/api/gn/', null, '6');
INSERT INTO `sys_fwgn` VALUES ('system-ITSM', '服务管理', '1', '2018-04-09 00:00:00.000000', '初始导入', '2018-04-28 10:59:00.000000', '1-超级管理员', '00', null, 'system-ITSM', 'system', null, 'ios-briefcase', '/api/fw/', null, '5');
INSERT INTO `sys_fwgn` VALUES ('system-notice', '设备终端', '1', '2018-04-09 00:00:00.000000', '初始导入', '2018-04-28 11:16:00.000000', '1-超级管理员', '00', null, 'system-notice', 'system', null, 'android-camera', '/api/zdgl/', null, '9');
INSERT INTO `sys_fwgn` VALUES ('system-role', '角色管理', '1', '2018-04-23 00:00:00.000000', '1', '2018-04-27 17:24:02.000000', '1-超级管理员', '00', ' ', 'system-role', 'system', null, 'ios-people', '/api/js/', null, '2');
INSERT INTO `sys_fwgn` VALUES ('system-suggestions', '意见反馈', '1', '2018-04-09 00:00:00.000000', '初始导入', '2018-04-28 11:16:05.000000', '1-超级管理员', '00', null, 'system-suggestions', 'system', null, 'ios-list-outline', '/api/yj/', null, '8');
INSERT INTO `sys_fwgn` VALUES ('system-user', '用户管理', '1', '2018-04-09 00:00:00.000000', '初始导入', '2018-04-27 17:23:04.000000', '1-超级管理员', '00', null, 'system-user', 'system', null, 'person-stalker', '/api/yh/', null, '1');


-- ----------------------------
-- Table structure for sys_fwgn_copy
-- ----------------------------

CREATE TABLE `sys_fwgn_copy` (
  `GNDM` varchar(32) NOT NULL COMMENT '功能代码',
  `GNMC` varchar(32) DEFAULT NULL COMMENT '功能名称',
  `FWDM` varchar(32) DEFAULT NULL COMMENT '服务代码',
  `CJSJ` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `CJR` varchar(64) DEFAULT NULL COMMENT '创建人',
  `XGSJ` datetime(6) DEFAULT NULL COMMENT '修改时间',
  `XGR` varchar(64) DEFAULT NULL COMMENT '修改人',
  `ZT` char(2) DEFAULT NULL COMMENT '状态',
  `BZ` varchar(255) DEFAULT NULL COMMENT '备注',
  `URL` varchar(255) DEFAULT NULL COMMENT 'URL',
  `FJD` varchar(32) DEFAULT NULL COMMENT '父节点',
  `TZDZ` varchar(32) DEFAULT NULL COMMENT '跳转地址',
  `TB` varchar(32) DEFAULT NULL COMMENT '图标',
  `API_QZ` varchar(64) DEFAULT NULL COMMENT 'API前缀',
  `API_HZ` varchar(64) DEFAULT NULL COMMENT 'API后缀',
  PRIMARY KEY (`GNDM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_hdyx
-- ----------------------------

CREATE TABLE `sys_hdyx` (
  `HD_ID` varchar(32) NOT NULL COMMENT '活动id',
  `KSSJ` datetime(6) DEFAULT NULL COMMENT '活动时间开始',
  `JSSJ` datetime(6) DEFAULT NULL COMMENT '活动时间结束',
  `HDLX` varchar(32) DEFAULT NULL COMMENT '活动类型(00 微信、10智能站牌)',
  `JGDM` varchar(20) DEFAULT NULL COMMENT '机构代码',
  `ZT` char(2) DEFAULT NULL COMMENT '状态(00未开始 10 已开始  20 已结束)',
  `CJR` varchar(64) DEFAULT NULL COMMENT '创建人',
  `CJSJ` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `XGR` varchar(64) DEFAULT NULL COMMENT '修改人',
  `XGSJ` datetime(6) DEFAULT NULL COMMENT '修改时间',
  `HDBT` varchar(32) DEFAULT NULL COMMENT '活动标题',
  `URL` varchar(255) DEFAULT NULL COMMENT 'URL',
  `WJLX` varchar(32) DEFAULT NULL COMMENT '文件类型,图片、视频',
  `WZ` varchar(32) DEFAULT NULL COMMENT '智能站牌位置(10上 20中 30下)',
  PRIMARY KEY (`HD_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='活动营销';

-- ----------------------------
-- Table structure for sys_hsgs
-- ----------------------------

CREATE TABLE `sys_hsgs` (
  `ID` varchar(32) NOT NULL COMMENT 'ID',
  `CX` char(2) DEFAULT NULL COMMENT '车型',
  `LX` char(2) DEFAULT NULL COMMENT '类型',
  `NR` varchar(32) DEFAULT NULL COMMENT '内容',
  `JE` decimal(10,2) DEFAULT NULL COMMENT '金额',
  `CJR` varchar(64) DEFAULT NULL COMMENT '创建人',
  `CJSJ` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `XGR` varchar(64) DEFAULT NULL COMMENT '修改人',
  `XGSJ` datetime(6) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='核算公式';

-- ----------------------------
-- Table structure for sys_jgsqlb
-- ----------------------------

CREATE TABLE `sys_jgsqlb` (
  `ID` varchar(32) NOT NULL COMMENT 'ID',
  `JGDM` varchar(20) DEFAULT NULL COMMENT '机构代码',
  `FWDM` varchar(32) DEFAULT NULL COMMENT '服务代码',
  `GNDM` varchar(32) DEFAULT NULL COMMENT '功能代码',
  `CJSJ` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `CJR` varchar(64) DEFAULT NULL COMMENT '创建人',
  `XGSJ` datetime(6) DEFAULT NULL COMMENT '修改时间',
  `XGR` varchar(64) DEFAULT NULL COMMENT '修改人',
  `YXQ` datetime(6) DEFAULT NULL COMMENT '有效期'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `sys_jgsqlb` VALUES ('443091727192948736', '100', '1', 'FleetManagement', '2018-05-07 16:48:01.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443091727465578496', '100', '1', 'VehicleMonitoring', '2018-05-07 16:48:01.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443091727771762688', '100', '1', 'abnormal', '2018-05-07 16:48:01.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443091726173732864', '100', '1', 'system', '2018-05-07 16:48:01.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443091727046148096', '100', '1', 'system-user', '2018-05-07 16:48:01.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443091728732258304', '100', '1', 'system-role', '2018-05-07 16:48:01.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443091728845504512', '100', '1', 'VehicleScheduling', '2018-05-07 16:48:01.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443091728954556416', '100', '1', 'vehicle-management', '2018-05-07 16:48:01.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443091729067802624', '100', '1', 'system-framework', '2018-05-07 16:48:01.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443091729483038720', '100', '1', 'system-dictionary', '2018-05-07 16:48:01.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443091729596284928', '100', '1', 'ElectronicFence', '2018-05-07 16:48:01.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443091731169148928', '100', '1', 'DriverManagement', '2018-05-07 16:48:01.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443091731290783744', '100', '1', 'system-ITSM', '2018-05-07 16:48:01.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443091731399835648', '100', '1', 'OverspeedLimit', '2018-05-07 16:48:01.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443091731504693248', '100', '1', 'mergeVideo', '2018-05-07 16:48:01.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443091731609550848', '100', '1', 'CloudPhoto', '2018-05-07 16:48:01.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443091731722797056', '100', '1', 'system-function', '2018-05-07 16:48:01.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443091732045758464', '100', '1', 'CloudVideo', '2018-05-07 16:48:01.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443091732456800256', '100', '1', 'OrderManagement', '2018-05-07 16:48:01.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443091732561657856', '100', '1', 'Establish', '2018-05-07 16:48:01.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443091732670709760', '100', '1', 'ToExamine', '2018-05-07 16:48:01.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443091732796538880', '100', '1', 'Consult', '2018-05-07 16:48:01.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443091732901396480', '100', '1', 'Assignment', '2018-05-07 16:48:01.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443091733018836992', '100', '1', 'Confirm', '2018-05-07 16:48:01.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443091734679781376', '100', '1', 'ShuttleBus', '2018-05-07 16:48:01.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443091735225040896', '100', '1', 'dz_Confirm', '2018-05-07 16:48:01.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443091736755961856', '100', '1', 'system-daily', '2018-05-07 16:48:01.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443091737028591616', '100', '1', 'ScManage', '2018-05-07 16:48:01.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443091737464799232', '100', '1', 'system-suggestions', '2018-05-07 16:48:01.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443091737582239744', '100', '1', 'Sc_SiteMaintenance', '2018-05-07 16:48:01.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443091737762594816', '100', '1', 'system-notice', '2018-05-07 16:48:01.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443091738840530944', '100', '1', 'Sc_LineMaintenance', '2018-05-07 16:48:01.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443091739083800576', '100', '1', 'system-propelling', '2018-05-07 16:48:01.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443091739230601216', '100', '1', 'Sc_Scheduling', '2018-05-07 16:48:01.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443091739465482240', '100', '1', 'system-advertising', '2018-05-07 16:48:01.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443091739570339840', '100', '1', 'OperationMonitoring', '2018-05-07 16:48:01.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443091739696168960', '100', '1', 'SiteMaintenance', '2018-05-07 16:48:01.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443091740274982912', '100', '1', 'LineMaintenance', '2018-05-07 16:48:01.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443091740375646208', '100', '1', 'Scheduling', '2018-05-07 16:48:01.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443091740480503808', '100', '1', 'BusMonitor', '2018-05-07 16:48:01.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443161825626816512', '100', '11', '11', '2018-05-07 21:26:05.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443514721631469568', '100', '1', 'FinancialSettlement', '2018-05-08 20:49:00.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443515028474167296', '100', '1', 'Reimbursement', '2018-05-08 20:50:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443737871900213248', '100', '1', 'ReceivablesManagement', '2018-05-09 11:35:05.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443738321554767872', '100', '1', 'PaymentManagement', '2018-05-09 11:37:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443738576300015616', '100', '1', 'AccountingFormula', '2018-05-09 11:38:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443739121047830528', '100', '1', 'Echarts', '2018-05-09 11:40:05.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443739895245045760', '100', '1', 'SafeDriving', '2018-05-09 11:43:05.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443740102347194368', '100', '1', 'OrderStatistics', '2018-05-09 11:44:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443740291619356672', '100', '1', 'BusStatistics', '2018-05-09 11:45:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443740475124350976', '100', '1', 'TrafficStatistics', '2018-05-09 11:46:01.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443740677700845568', '100', '1', 'TerminalAnomaly', '2018-05-09 11:47:00.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443740845934379008', '100', '1', 'VehicleDetails', '2018-05-09 11:47:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443741009910693888', '100', '1', 'OverspeedStatistics', '2018-05-09 11:48:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443741217797177344', '100', '1', 'VehicleAccident', '2018-05-09 11:49:01.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443741714767675392', '100', '1', 'CollectionStatistics', '2018-05-09 11:51:00.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443741898012622848', '100', '1', 'PaymentStatistics', '2018-05-09 11:51:05.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443742210559574016', '100', '1', 'ReimbursementStatistics', '2018-05-09 11:53:00.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443783929988644864', '100', '1', 'TemporaryCarManagement', '2018-05-09 14:38:05.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443784412799172608', '100', '1', 'UnitManagement', '2018-05-09 14:40:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443784655611625472', '100', '1', 'VehicleManagement', '2018-05-09 14:41:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('443785382023135232', '100', '1', 'AccidentManagement', '2018-05-09 14:44:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('446624800085901312', '100', '1', 'AccidentManagement', '2018-05-17 10:47:03.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498167198777344', '100038', null, 'system-framework', '2018-06-13 16:40:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498167198777345', '100038', null, 'system-suggestions', '2018-06-13 16:40:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498167198777346', '100038', null, 'system-notice', '2018-06-13 16:40:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('455497116333113344', '100002', null, 'mergeVideo', '2018-06-10 22:22:05.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('455497116333113345', '100002', null, 'mergeVideo', '2018-06-10 22:22:05.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498167198777347', '100038', null, 'system-propelling', '2018-06-13 16:40:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498167198777348', '100038', null, 'system-advertising', '2018-06-13 16:40:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498167198777349', '100038', null, 'VehicleScheduling', '2018-06-13 16:40:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498167265886208', '100038', null, 'mergeVideo', '2018-06-13 16:40:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498167265886209', '100038', null, 'CloudPhoto', '2018-06-13 16:40:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498167265886210', '100038', null, 'abnormal', '2018-06-13 16:40:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498167265886211', '100038', null, 'OrderManagement', '2018-06-13 16:40:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498167265886212', '100038', null, 'Establish', '2018-06-13 16:40:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('455691543127261184', '100038029', null, 'BusMonitor', '2018-06-11 11:15:03.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('455691543223730176', '100038029', null, 'VehicleMonitoring', '2018-06-11 11:15:03.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('455691543316004864', '100038029', null, 'BusMonitor', '2018-06-11 11:15:03.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('455691543412473856', '100038029', null, 'VehicleScheduling', '2018-06-11 11:15:03.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('455691543513137152', '100038029', null, 'DriverManagement', '2018-06-11 11:15:03.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('455691543605411840', '100038029', null, 'OrderManagement', '2018-06-11 11:15:03.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('455691543697686528', '100038029', null, 'dz_Confirm', '2018-06-11 11:15:03.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('455691542938517504', '100038029', null, 'OperationMonitoring', '2018-06-11 11:15:03.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('455691543030792192', '100038029', null, 'VehicleMonitoring', '2018-06-11 11:15:03.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('455691543789961216', '100038029', null, 'dz_Confirm', '2018-06-11 11:15:03.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('455691543882235904', '100038029', null, 'DriverManagement', '2018-06-11 11:15:03.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('455691543978704896', '100038029', null, 'OrderManagement', '2018-06-11 11:15:03.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('455691544070979584', '100038029', null, 'dz_Confirm', '2018-06-11 11:15:03.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('455691544171642880', '100038029', null, 'dz_Confirm', '2018-06-11 11:15:03.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498106121322498', '100014', null, 'system-framework', '2018-06-13 16:40:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498106188431360', '100014', null, 'system-suggestions', '2018-06-13 16:40:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498106188431361', '100014', null, 'system-notice', '2018-06-13 16:40:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498106188431362', '100014', null, 'system-propelling', '2018-06-13 16:40:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498106121322496', '100014', null, 'system-role', '2018-06-13 16:40:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456467145799237632', '100007', null, 'VehicleScheduling', '2018-06-13 14:37:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456467145799237633', '100007', null, 'mergeVideo', '2018-06-13 14:37:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456467145799237634', '100007', null, 'CloudPhoto', '2018-06-13 14:37:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456467145799237635', '100007', null, 'abnormal', '2018-06-13 14:37:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456467145866346496', '100007', null, 'OrderManagement', '2018-06-13 14:37:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456467145866346497', '100007', null, 'Establish', '2018-06-13 14:37:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456467145866346498', '100007', null, 'ToExamine', '2018-06-13 14:37:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456467145866346499', '100007', null, 'Consult', '2018-06-13 14:37:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456467145866346500', '100007', null, 'Assignment', '2018-06-13 14:37:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456467145933455360', '100007', null, 'Confirm', '2018-06-13 14:37:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456467145933455361', '100007', null, 'dz_Confirm', '2018-06-13 14:37:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456467145933455362', '100007', null, 'DriverManagement', '2018-06-13 14:37:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456467145933455363', '100007', null, 'ShuttleBus', '2018-06-13 14:37:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456467145933455364', '100007', null, 'SiteMaintenance', '2018-06-13 14:37:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456467145933455365', '100007', null, 'LineMaintenance', '2018-06-13 14:37:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456467145996369920', '100007', null, 'Scheduling', '2018-06-13 14:37:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456467145996369921', '100007', null, 'vehicle-management', '2018-06-13 14:37:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456467145996369922', '100007', null, 'FleetManagement', '2018-06-13 14:37:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456467145996369923', '100007', null, 'ElectronicFence', '2018-06-13 14:37:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456467145996369924', '100007', null, 'OverspeedLimit', '2018-06-13 14:37:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456467145996369925', '100007', null, 'CloudVideo', '2018-06-13 14:37:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456467146063478784', '100007', null, 'ScManage', '2018-06-13 14:37:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456467146063478785', '100007', null, 'Sc_SiteMaintenance', '2018-06-13 14:37:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456467146063478786', '100007', null, 'Sc_LineMaintenance', '2018-06-13 14:37:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456467146063478787', '100007', null, 'Sc_Scheduling', '2018-06-13 14:37:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456467146063478788', '100007', null, 'TemporaryCarManagement', '2018-06-13 14:37:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456467146063478789', '100007', null, 'UnitManagement', '2018-06-13 14:37:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456467146063478790', '100007', null, 'VehicleManagement', '2018-06-13 14:37:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456467146130587648', '100007', null, 'AccidentManagement', '2018-06-13 14:37:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456467146130587649', '100007', null, 'OperationMonitoring', '2018-06-13 14:37:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456467146130587650', '100007', null, 'VehicleMonitoring', '2018-06-13 14:37:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456467146130587651', '100007', null, 'BusMonitor', '2018-06-13 14:37:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498106188431363', '100014', null, 'system-advertising', '2018-06-13 16:40:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498106188431364', '100014', null, 'VehicleScheduling', '2018-06-13 16:40:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498106121322497', '100014', null, 'system-user', '2018-06-13 16:40:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498106188431365', '100014', null, 'mergeVideo', '2018-06-13 16:40:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498106251345920', '100014', null, 'CloudPhoto', '2018-06-13 16:40:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498106251345921', '100014', null, 'abnormal', '2018-06-13 16:40:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498106251345922', '100014', null, 'OrderManagement', '2018-06-13 16:40:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498106251345923', '100014', null, 'Establish', '2018-06-13 16:40:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498106251345924', '100014', null, 'ToExamine', '2018-06-13 16:40:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498106251345925', '100014', null, 'Consult', '2018-06-13 16:40:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498106251345926', '100014', null, 'Assignment', '2018-06-13 16:40:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498106318454784', '100014', null, 'Confirm', '2018-06-13 16:40:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498106318454785', '100014', null, 'dz_Confirm', '2018-06-13 16:40:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498106318454786', '100014', null, 'DriverManagement', '2018-06-13 16:40:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498106318454787', '100014', null, 'ShuttleBus', '2018-06-13 16:40:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498106318454788', '100014', null, 'SiteMaintenance', '2018-06-13 16:40:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498106318454789', '100014', null, 'LineMaintenance', '2018-06-13 16:40:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498106318454790', '100014', null, 'Scheduling', '2018-06-13 16:40:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498106318454791', '100014', null, 'vehicle-management', '2018-06-13 16:40:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498106381369344', '100014', null, 'FleetManagement', '2018-06-13 16:40:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498106381369345', '100014', null, 'ElectronicFence', '2018-06-13 16:40:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498106381369346', '100014', null, 'OverspeedLimit', '2018-06-13 16:40:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498106381369347', '100014', null, 'CloudVideo', '2018-06-13 16:40:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498106381369348', '100014', null, 'ScManage', '2018-06-13 16:40:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498106381369349', '100014', null, 'Sc_SiteMaintenance', '2018-06-13 16:40:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498106448478208', '100014', null, 'Sc_LineMaintenance', '2018-06-13 16:40:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498106448478209', '100014', null, 'Sc_Scheduling', '2018-06-13 16:40:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498106448478210', '100014', null, 'TemporaryCarManagement', '2018-06-13 16:40:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498106448478211', '100014', null, 'UnitManagement', '2018-06-13 16:40:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498106448478212', '100014', null, 'VehicleManagement', '2018-06-13 16:40:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498106448478213', '100014', null, 'AccidentManagement', '2018-06-13 16:40:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498106448478214', '100014', null, 'OperationMonitoring', '2018-06-13 16:40:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498106515587072', '100014', null, 'VehicleMonitoring', '2018-06-13 16:40:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498106515587073', '100014', null, 'BusMonitor', '2018-06-13 16:40:02.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498167135862784', '100038', null, 'system-role', '2018-06-13 16:40:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498167135862785', '100038', null, 'system-user', '2018-06-13 16:40:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498167265886213', '100038', null, 'ToExamine', '2018-06-13 16:40:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498167265886214', '100038', null, 'Consult', '2018-06-13 16:40:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498167332995072', '100038', null, 'Assignment', '2018-06-13 16:40:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498167332995073', '100038', null, 'Confirm', '2018-06-13 16:40:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498167332995074', '100038', null, 'dz_Confirm', '2018-06-13 16:40:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498167332995075', '100038', null, 'DriverManagement', '2018-06-13 16:40:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498167332995076', '100038', null, 'ShuttleBus', '2018-06-13 16:40:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498167332995077', '100038', null, 'SiteMaintenance', '2018-06-13 16:40:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498167332995078', '100038', null, 'LineMaintenance', '2018-06-13 16:40:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498167395909632', '100038', null, 'Scheduling', '2018-06-13 16:40:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498167395909633', '100038', null, 'vehicle-management', '2018-06-13 16:40:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498167395909634', '100038', null, 'FleetManagement', '2018-06-13 16:40:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498167395909635', '100038', null, 'ElectronicFence', '2018-06-13 16:40:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498167395909636', '100038', null, 'OverspeedLimit', '2018-06-13 16:40:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498167395909637', '100038', null, 'CloudVideo', '2018-06-13 16:40:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498167395909638', '100038', null, 'ScManage', '2018-06-13 16:40:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498167463018496', '100038', null, 'Sc_SiteMaintenance', '2018-06-13 16:40:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498167463018497', '100038', null, 'Sc_LineMaintenance', '2018-06-13 16:40:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498167463018498', '100038', null, 'Sc_Scheduling', '2018-06-13 16:40:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498167463018499', '100038', null, 'TemporaryCarManagement', '2018-06-13 16:40:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498167463018500', '100038', null, 'UnitManagement', '2018-06-13 16:40:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498167463018501', '100038', null, 'VehicleManagement', '2018-06-13 16:40:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498167463018502', '100038', null, 'AccidentManagement', '2018-06-13 16:40:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498167530127360', '100038', null, 'OperationMonitoring', '2018-06-13 16:40:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498167530127361', '100038', null, 'VehicleMonitoring', '2018-06-13 16:40:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('456498167530127362', '100038', null, 'BusMonitor', '2018-06-13 16:40:04.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('455334786915893248', '100002001', null, 'VehicleScheduling', '2018-06-10 11:37:05.000000', '1-超级管理员', null, null, null);
INSERT INTO `sys_jgsqlb` VALUES ('455334787054305280', '100002001', null, 'mergeVideo', '2018-06-10 11:37:05.000000', '1-超级管理员', null, null, null);

-- ----------------------------
-- Table structure for sys_js_fw
-- ----------------------------

CREATE TABLE `sys_js_fw` (
  `ID` varchar(32) NOT NULL COMMENT 'id',
  `JSDM` varchar(32) DEFAULT NULL COMMENT '角色代码',
  `FWDM` varchar(32) DEFAULT NULL COMMENT '服务代码',
  `CJSJ` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `CJR` varchar(64) DEFAULT NULL COMMENT '创建人'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_js_gn
-- ----------------------------

CREATE TABLE `sys_js_gn` (
  `ID` varchar(32) NOT NULL COMMENT 'id',
  `JSDM` varchar(32) DEFAULT NULL COMMENT '角色代码',
  `GNDM` varchar(32) DEFAULT NULL COMMENT '功能代码',
  `CJSJ` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `CJR` varchar(64) DEFAULT NULL COMMENT '创建人',
  `FWDM` varchar(32) DEFAULT NULL COMMENT '服务代码',
  `FGNDM` varchar(32) DEFAULT NULL COMMENT '父功能代码',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `sys_js_gn` VALUES ('1', '1', '1', null, null, '1', '1');
INSERT INTO `sys_js_gn` VALUES ('434305549195018240', 'eee', 'system-role', '2018-04-13 10:55:01.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('434305549195018241', 'eee', 'system', '2018-04-13 10:55:01.000000', '1-超级管理员', '1', null);
INSERT INTO `sys_js_gn` VALUES ('434305549195018242', 'eee', 'system-user', '2018-04-13 10:55:01.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('434305549195018243', 'eee', 'system-framework', '2018-04-13 10:55:01.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('434305549195018244', 'eee', 'system-dictionary', '2018-04-13 10:55:01.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('434305549195018245', 'eee', 'system-ITSM', '2018-04-13 10:55:01.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('434305549195018246', 'eee', 'system-function', '2018-04-13 10:55:01.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('434305549195018247', 'eee', 'system-daily', '2018-04-13 10:55:01.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('434305549195018248', 'eee', 'system-suggestions', '2018-04-13 10:55:01.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('434305549195018249', 'eee', 'system-notice', '2018-04-13 10:55:01.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('434305549195018250', 'eee', 'system-propelling', '2018-04-13 10:55:01.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('434305549195018251', 'eee', 'system-advertising', '2018-04-13 10:55:01.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('434305549195018252', 'eee', 'VehicleScheduling', '2018-04-13 10:55:01.000000', '1-超级管理员', '1', null);
INSERT INTO `sys_js_gn` VALUES ('434305549195018253', 'eee', 'vehicle-management', '2018-04-13 10:55:01.000000', '1-超级管理员', '1', 'VehicleScheduling');
INSERT INTO `sys_js_gn` VALUES ('434305549195018254', 'eee', 'DriverManagement', '2018-04-13 10:55:01.000000', '1-超级管理员', '1', 'VehicleScheduling');
INSERT INTO `sys_js_gn` VALUES ('434305549195018255', 'eee', 'FleetManagement', '2018-04-13 10:55:01.000000', '1-超级管理员', '1', 'VehicleScheduling');
INSERT INTO `sys_js_gn` VALUES ('434305549195018256', 'eee', 'ElectronicFence', '2018-04-13 10:55:01.000000', '1-超级管理员', '1', 'VehicleScheduling');
INSERT INTO `sys_js_gn` VALUES ('434305549195018257', 'eee', 'OverspeedLimit', '2018-04-13 10:55:01.000000', '1-超级管理员', '1', 'VehicleScheduling');
INSERT INTO `sys_js_gn` VALUES ('434305549195018258', 'eee', 'CloudVideo', '2018-04-13 10:55:01.000000', '1-超级管理员', '1', 'VehicleScheduling');
INSERT INTO `sys_js_gn` VALUES ('434305549195018259', 'eee', 'ScManage', '2018-04-13 10:55:01.000000', '1-超级管理员', '1', 'VehicleScheduling');
INSERT INTO `sys_js_gn` VALUES ('434305549195018260', 'eee', 'Sc_SiteMaintenance', '2018-04-13 10:55:01.000000', '1-超级管理员', '1', 'ScManage');
INSERT INTO `sys_js_gn` VALUES ('434305549195018261', 'eee', 'Sc_LineMaintenance', '2018-04-13 10:55:01.000000', '1-超级管理员', '1', 'ScManage');
INSERT INTO `sys_js_gn` VALUES ('434305549195018262', 'eee', 'Sc_Scheduling', '2018-04-13 10:55:01.000000', '1-超级管理员', '1', 'ScManage');
INSERT INTO `sys_js_gn` VALUES ('435778198454665216', '1231', 'system-role', '2018-04-17 12:26:05.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('435778198454665217', '1231', 'system', '2018-04-17 12:26:05.000000', '1-超级管理员', '1', null);
INSERT INTO `sys_js_gn` VALUES ('435778198454665218', '1231', 'system-user', '2018-04-17 12:26:05.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('435778198454665219', '1231', 'system-framework', '2018-04-17 12:26:05.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('435778198454665220', '1231', 'system-dictionary', '2018-04-17 12:26:05.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('435778198454665221', '1231', 'system-ITSM', '2018-04-17 12:26:05.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('435778198454665222', '1231', 'system-function', '2018-04-17 12:26:05.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('435778198454665223', '1231', 'system-daily', '2018-04-17 12:26:05.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('435778198454665224', '1231', 'system-suggestions', '2018-04-17 12:26:05.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('435778198454665225', '1231', 'system-notice', '2018-04-17 12:26:05.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('435778198454665226', '1231', 'system-propelling', '2018-04-17 12:26:05.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('435778198454665227', '1231', 'system-advertising', '2018-04-17 12:26:05.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('435908246457286656', '1221', 'system-role', '2018-04-17 21:03:04.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('435908246457286657', '1221', 'system', '2018-04-17 21:03:04.000000', '1-超级管理员', '1', null);
INSERT INTO `sys_js_gn` VALUES ('435908246457286658', '1221', 'system-user', '2018-04-17 21:03:04.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('435908246457286659', '1221', 'system-framework', '2018-04-17 21:03:04.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('435908246457286660', '1221', 'system-dictionary', '2018-04-17 21:03:04.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('435908246457286661', '1221', 'system-ITSM', '2018-04-17 21:03:04.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('435908246457286662', '1221', 'system-function', '2018-04-17 21:03:04.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('435908246457286663', '1221', 'system-daily', '2018-04-17 21:03:04.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('435908246457286664', '1221', 'system-suggestions', '2018-04-17 21:03:04.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('435908246457286665', '1221', 'system-notice', '2018-04-17 21:03:04.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('435908246457286666', '1221', 'system-propelling', '2018-04-17 21:03:04.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('435908246457286667', '1221', 'system-advertising', '2018-04-17 21:03:04.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436255386190741504', '1212', 'system-role', '2018-04-18 20:03:00.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436255386190741505', '1212', 'system', '2018-04-18 20:03:00.000000', '1-超级管理员', '1', null);
INSERT INTO `sys_js_gn` VALUES ('436255386190741506', '1212', 'system-user', '2018-04-18 20:03:00.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436255386190741507', '1212', 'system-framework', '2018-04-18 20:03:00.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436255386190741508', '1212', 'system-dictionary', '2018-04-18 20:03:00.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436255386190741509', '1212', 'system-ITSM', '2018-04-18 20:03:00.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436255386190741510', '1212', 'system-function', '2018-04-18 20:03:00.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436255386190741511', '1212', 'system-daily', '2018-04-18 20:03:00.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436255386190741512', '1212', 'system-suggestions', '2018-04-18 20:03:00.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436255386190741513', '1212', 'system-notice', '2018-04-18 20:03:00.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436255386190741514', '1212', 'system-propelling', '2018-04-18 20:03:00.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436255386190741515', '1212', 'system-advertising', '2018-04-18 20:03:00.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436284395796561920', '12', 'system-role', '2018-04-18 21:58:02.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436284395796561921', '12', 'system', '2018-04-18 21:58:02.000000', '1-超级管理员', '1', null);
INSERT INTO `sys_js_gn` VALUES ('436284395796561922', '12', 'system-user', '2018-04-18 21:58:02.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436284395796561923', '12', 'system-framework', '2018-04-18 21:58:02.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436284395796561924', '12', 'system-dictionary', '2018-04-18 21:58:02.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436284395796561925', '12', 'system-ITSM', '2018-04-18 21:58:02.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436284395796561926', '12', 'system-function', '2018-04-18 21:58:02.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436284395796561927', '12', 'system-daily', '2018-04-18 21:58:02.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436284395796561928', '12', 'system-suggestions', '2018-04-18 21:58:02.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436284395796561929', '12', 'system-notice', '2018-04-18 21:58:02.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436284395796561930', '12', 'system-propelling', '2018-04-18 21:58:02.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436284395796561931', '12', 'system-advertising', '2018-04-18 21:58:02.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436288327990116352', '23123', 'system-role', '2018-04-18 22:14:00.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436288327990116353', '23123', 'system', '2018-04-18 22:14:00.000000', '1-超级管理员', '1', null);
INSERT INTO `sys_js_gn` VALUES ('436288327990116354', '23123', 'system-user', '2018-04-18 22:14:00.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436288327990116355', '23123', 'system-framework', '2018-04-18 22:14:00.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436288327990116356', '23123', 'system-dictionary', '2018-04-18 22:14:00.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436288327990116357', '23123', 'system-ITSM', '2018-04-18 22:14:00.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436288327990116358', '23123', 'system-function', '2018-04-18 22:14:00.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436288327990116359', '23123', 'system-daily', '2018-04-18 22:14:00.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436288327990116360', '23123', 'system-suggestions', '2018-04-18 22:14:00.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436288327990116361', '23123', 'system-notice', '2018-04-18 22:14:00.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436288327990116362', '23123', 'system-propelling', '2018-04-18 22:14:00.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436288327990116363', '23123', 'system-advertising', '2018-04-18 22:14:00.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436463544385404928', '0000', 'system-role', '2018-04-19 09:50:01.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436463544385404929', '0000', 'system-framework', '2018-04-19 09:50:01.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436463544385404930', '0000', 'system-dictionary', '2018-04-19 09:50:01.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436463544385404931', '0000', 'system-ITSM', '2018-04-19 09:50:01.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436463544385404932', '0000', 'system-function', '2018-04-19 09:50:01.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436463544385404933', '0000', 'system-suggestions', '2018-04-19 09:50:01.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436463544385404934', '0000', 'system-notice', '2018-04-19 09:50:01.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436463544385404935', '0000', 'system-propelling', '2018-04-19 09:50:01.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436463544385404936', '0000', 'system-advertising', '2018-04-19 09:50:01.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436471966480728064', '111222', 'system-role', '2018-04-19 10:23:04.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436471966480728065', '111222', 'system', '2018-04-19 10:23:04.000000', '1-超级管理员', '1', null);
INSERT INTO `sys_js_gn` VALUES ('436471966480728066', '111222', 'system-user', '2018-04-19 10:23:04.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436471966480728067', '111222', 'system-framework', '2018-04-19 10:23:04.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436471966480728068', '111222', 'system-dictionary', '2018-04-19 10:23:04.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436471966480728069', '111222', 'system-ITSM', '2018-04-19 10:23:04.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436471966480728070', '111222', 'system-function', '2018-04-19 10:23:04.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436471966480728071', '111222', 'system-daily', '2018-04-19 10:23:04.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436471966480728072', '111222', 'system-suggestions', '2018-04-19 10:23:04.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436471966480728073', '111222', 'system-notice', '2018-04-19 10:23:04.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436471966480728074', '111222', 'system-propelling', '2018-04-19 10:23:04.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('436471966480728075', '111222', 'system-advertising', '2018-04-19 10:23:04.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('438104660780253184', '123', 'VehicleMonitoring', '2018-04-23 22:31:03.000000', '1-超级管理员', '1', 'OperationMonitoring');
INSERT INTO `sys_js_gn` VALUES ('438104660780253185', '123', 'OperationMonitoring', '2018-04-23 22:31:03.000000', '1-超级管理员', '1', null);
INSERT INTO `sys_js_gn` VALUES ('438104660780253186', '123', 'BusMonitor', '2018-04-23 22:31:03.000000', '1-超级管理员', '1', 'OperationMonitoring');
INSERT INTO `sys_js_gn` VALUES ('439154967182311424', 'asdgrgftnA', 'system-role', '2018-04-26 20:05:00.000000', '438108538678542336-测试001', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('439154967182311425', 'asdgrgftnA', 'system', '2018-04-26 20:05:00.000000', '438108538678542336-测试001', '1', null);
INSERT INTO `sys_js_gn` VALUES ('439154967182311426', 'asdgrgftnA', 'system-user', '2018-04-26 20:05:00.000000', '438108538678542336-测试001', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('439154967182311427', 'asdgrgftnA', 'system-framework', '2018-04-26 20:05:00.000000', '438108538678542336-测试001', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('439154967182311428', 'asdgrgftnA', 'system-dictionary', '2018-04-26 20:05:00.000000', '438108538678542336-测试001', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('439154967182311429', 'asdgrgftnA', 'system-ITSM', '2018-04-26 20:05:00.000000', '438108538678542336-测试001', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('439154967182311430', 'asdgrgftnA', 'system-function', '2018-04-26 20:05:00.000000', '438108538678542336-测试001', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('439154967182311431', 'asdgrgftnA', 'system-daily', '2018-04-26 20:05:00.000000', '438108538678542336-测试001', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('439154967182311432', 'asdgrgftnA', 'system-suggestions', '2018-04-26 20:05:00.000000', '438108538678542336-测试001', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('439154967182311433', 'asdgrgftnA', 'system-notice', '2018-04-26 20:05:00.000000', '438108538678542336-测试001', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('439154967182311434', 'asdgrgftnA', 'system-propelling', '2018-04-26 20:05:00.000000', '438108538678542336-测试001', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('439154967182311435', 'asdgrgftnA', 'system-advertising', '2018-04-26 20:05:00.000000', '438108538678542336-测试001', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('442809282145026048', '000000', 'VehicleMonitoring', '2018-05-06 22:05:05.000000', '442806265194741760-订单测试B', '1', 'OperationMonitoring');
INSERT INTO `sys_js_gn` VALUES ('442809282145026049', '000000', 'mergeVideo', '2018-05-06 22:05:05.000000', '442806265194741760-订单测试B', '1', 'VehicleScheduling');
INSERT INTO `sys_js_gn` VALUES ('442809282145026050', '000000', 'CloudPhoto', '2018-05-06 22:05:05.000000', '442806265194741760-订单测试B', '1', 'VehicleScheduling');
INSERT INTO `sys_js_gn` VALUES ('442809282145026051', '000000', 'abnormal', '2018-05-06 22:05:05.000000', '442806265194741760-订单测试B', '1', 'VehicleScheduling');
INSERT INTO `sys_js_gn` VALUES ('442809282145026052', '000000', 'OrderManagement', '2018-05-06 22:05:05.000000', '442806265194741760-订单测试B', '1', 'VehicleScheduling');
INSERT INTO `sys_js_gn` VALUES ('442809282145026053', '000000', 'Establish', '2018-05-06 22:05:05.000000', '442806265194741760-订单测试B', '1', 'OrderManagement');
INSERT INTO `sys_js_gn` VALUES ('442809282145026054', '000000', 'DriverManagement', '2018-05-06 22:05:05.000000', '442806265194741760-订单测试B', '1', 'VehicleScheduling');
INSERT INTO `sys_js_gn` VALUES ('442809282145026055', '000000', 'system-role', '2018-05-06 22:05:05.000000', '442806265194741760-订单测试B', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('442809282145026056', '000000', 'system', '2018-05-06 22:05:05.000000', '442806265194741760-订单测试B', '1', null);
INSERT INTO `sys_js_gn` VALUES ('442809282145026057', '000000', 'system-user', '2018-05-06 22:05:05.000000', '442806265194741760-订单测试B', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('442809282145026058', '000000', 'system-framework', '2018-05-06 22:05:05.000000', '442806265194741760-订单测试B', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('442809282145026059', '000000', 'ToExamine', '2018-05-06 22:05:05.000000', '442806265194741760-订单测试B', '1', 'OrderManagement');
INSERT INTO `sys_js_gn` VALUES ('442809282145026060', '000000', 'Consult', '2018-05-06 22:05:05.000000', '442806265194741760-订单测试B', '1', 'OrderManagement');
INSERT INTO `sys_js_gn` VALUES ('442809282145026061', '000000', 'Assignment', '2018-05-06 22:05:05.000000', '442806265194741760-订单测试B', '1', 'OrderManagement');
INSERT INTO `sys_js_gn` VALUES ('442809282145026062', '000000', 'Confirm', '2018-05-06 22:05:05.000000', '442806265194741760-订单测试B', '1', 'OrderManagement');
INSERT INTO `sys_js_gn` VALUES ('442809282145026063', '000000', 'ShuttleBus', '2018-05-06 22:05:05.000000', '442806265194741760-订单测试B', '1', 'VehicleScheduling');
INSERT INTO `sys_js_gn` VALUES ('442809282145026064', '000000', 'SiteMaintenance', '2018-05-06 22:05:05.000000', '442806265194741760-订单测试B', '1', 'ShuttleBus');
INSERT INTO `sys_js_gn` VALUES ('442809282145026065', '000000', 'LineMaintenance', '2018-05-06 22:05:05.000000', '442806265194741760-订单测试B', '1', 'ShuttleBus');
INSERT INTO `sys_js_gn` VALUES ('442809282145026066', '000000', 'Scheduling', '2018-05-06 22:05:05.000000', '442806265194741760-订单测试B', '1', 'ShuttleBus');
INSERT INTO `sys_js_gn` VALUES ('442809282145026067', '000000', 'dz_Confirm', '2018-05-06 22:05:05.000000', '442806265194741760-订单测试B', '1', 'OrderManagement');
INSERT INTO `sys_js_gn` VALUES ('442809282145026068', '000000', 'Assignment_max', '2018-05-06 22:05:05.000000', '442806265194741760-订单测试B', '1', 'OrderManagement');
INSERT INTO `sys_js_gn` VALUES ('442809282145026069', '000000', 'system-dictionary', '2018-05-06 22:05:05.000000', '442806265194741760-订单测试B', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('442809282145026070', '000000', 'system-ITSM', '2018-05-06 22:05:05.000000', '442806265194741760-订单测试B', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('442809282145026071', '000000', 'system-function', '2018-05-06 22:05:05.000000', '442806265194741760-订单测试B', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('442809282145026072', '000000', 'system-daily', '2018-05-06 22:05:05.000000', '442806265194741760-订单测试B', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('442809282145026073', '000000', 'system-suggestions', '2018-05-06 22:05:05.000000', '442806265194741760-订单测试B', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('442809282145026074', '000000', 'system-notice', '2018-05-06 22:05:05.000000', '442806265194741760-订单测试B', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('442809282145026075', '000000', 'system-propelling', '2018-05-06 22:05:05.000000', '442806265194741760-订单测试B', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('442809282145026076', '000000', 'system-advertising', '2018-05-06 22:05:05.000000', '442806265194741760-订单测试B', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('442809282145026077', '000000', 'VehicleScheduling', '2018-05-06 22:05:05.000000', '442806265194741760-订单测试B', '1', null);
INSERT INTO `sys_js_gn` VALUES ('442809282145026078', '000000', 'vehicle-management', '2018-05-06 22:05:05.000000', '442806265194741760-订单测试B', '1', 'VehicleScheduling');
INSERT INTO `sys_js_gn` VALUES ('442809282145026079', '000000', 'FleetManagement', '2018-05-06 22:05:05.000000', '442806265194741760-订单测试B', '1', 'VehicleScheduling');
INSERT INTO `sys_js_gn` VALUES ('442809282145026080', '000000', 'ElectronicFence', '2018-05-06 22:05:05.000000', '442806265194741760-订单测试B', '1', 'VehicleScheduling');
INSERT INTO `sys_js_gn` VALUES ('442809282145026081', '000000', 'OverspeedLimit', '2018-05-06 22:05:05.000000', '442806265194741760-订单测试B', '1', 'VehicleScheduling');
INSERT INTO `sys_js_gn` VALUES ('442809282145026082', '000000', 'CloudVideo', '2018-05-06 22:05:05.000000', '442806265194741760-订单测试B', '1', 'VehicleScheduling');
INSERT INTO `sys_js_gn` VALUES ('442809282145026083', '000000', 'ScManage', '2018-05-06 22:05:05.000000', '442806265194741760-订单测试B', '1', 'VehicleScheduling');
INSERT INTO `sys_js_gn` VALUES ('442809282145026084', '000000', 'Sc_SiteMaintenance', '2018-05-06 22:05:05.000000', '442806265194741760-订单测试B', '1', 'ScManage');
INSERT INTO `sys_js_gn` VALUES ('442809282145026085', '000000', 'Sc_LineMaintenance', '2018-05-06 22:05:05.000000', '442806265194741760-订单测试B', '1', 'ScManage');
INSERT INTO `sys_js_gn` VALUES ('442809282145026086', '000000', 'Sc_Scheduling', '2018-05-06 22:05:05.000000', '442806265194741760-订单测试B', '1', 'ScManage');
INSERT INTO `sys_js_gn` VALUES ('442809282145026087', '000000', 'OperationMonitoring', '2018-05-06 22:05:05.000000', '442806265194741760-订单测试B', '1', null);
INSERT INTO `sys_js_gn` VALUES ('442809282145026088', '000000', 'BusMonitor', '2018-05-06 22:05:05.000000', '442806265194741760-订单测试B', '1', 'OperationMonitoring');
INSERT INTO `sys_js_gn` VALUES ('455691389355687936', '111', 'VehicleMonitoring', '2018-06-11 11:14:05.000000', '1-超级管理员', '1', 'OperationMonitoring');
INSERT INTO `sys_js_gn` VALUES ('455691389355687937', '111', 'DriverManagement', '2018-06-11 11:14:05.000000', '1-超级管理员', '1', 'VehicleScheduling');
INSERT INTO `sys_js_gn` VALUES ('455691389355687938', '111', 'dz_Confirm', '2018-06-11 11:14:05.000000', '1-超级管理员', '1', 'OrderManagement');
INSERT INTO `sys_js_gn` VALUES ('455691389355687939', '111', 'OperationMonitoring', '2018-06-11 11:14:05.000000', '1-超级管理员', '1', null);
INSERT INTO `sys_js_gn` VALUES ('455691389355687940', '111', 'BusMonitor', '2018-06-11 11:14:05.000000', '1-超级管理员', '1', 'OperationMonitoring');
INSERT INTO `sys_js_gn` VALUES ('455691444028440576', '0009222', 'VehicleMonitoring', '2018-06-11 11:15:00.000000', '1-超级管理员', '1', 'OperationMonitoring');
INSERT INTO `sys_js_gn` VALUES ('455691444028440577', '0009222', 'DriverManagement', '2018-06-11 11:15:00.000000', '1-超级管理员', '1', 'VehicleScheduling');
INSERT INTO `sys_js_gn` VALUES ('455691444028440578', '0009222', 'dz_Confirm', '2018-06-11 11:15:00.000000', '1-超级管理员', '1', 'OrderManagement');
INSERT INTO `sys_js_gn` VALUES ('455691444028440579', '0009222', 'OperationMonitoring', '2018-06-11 11:15:00.000000', '1-超级管理员', '1', null);
INSERT INTO `sys_js_gn` VALUES ('455691444028440580', '0009222', 'BusMonitor', '2018-06-11 11:15:00.000000', '1-超级管理员', '1', 'OperationMonitoring');
INSERT INTO `sys_js_gn` VALUES ('455696480204750848', '10012', 'system-role', '2018-06-11 11:35:00.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('455696480204750849', '10012', 'FinancialSettlement', '2018-06-11 11:35:00.000000', '1-超级管理员', '1', null);
INSERT INTO `sys_js_gn` VALUES ('455696480204750850', '10012', 'Reimbursement', '2018-06-11 11:35:00.000000', '1-超级管理员', '1', 'FinancialSettlement');
INSERT INTO `sys_js_gn` VALUES ('455696480204750851', '10012', 'ReceivablesManagement', '2018-06-11 11:35:00.000000', '1-超级管理员', '1', 'FinancialSettlement');
INSERT INTO `sys_js_gn` VALUES ('455696480204750852', '10012', 'PaymentManagement', '2018-06-11 11:35:00.000000', '1-超级管理员', '1', 'FinancialSettlement');
INSERT INTO `sys_js_gn` VALUES ('455696480204750853', '10012', 'AccountingFormula', '2018-06-11 11:35:00.000000', '1-超级管理员', '1', 'FinancialSettlement');
INSERT INTO `sys_js_gn` VALUES ('455730829021151232', 'test', 'Establish', '2018-06-11 13:51:03.000000', '1-超级管理员', '1', 'OrderManagement');
INSERT INTO `sys_js_gn` VALUES ('455730829021151233', 'test', 'system-notice', '2018-06-11 13:51:03.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('461852907256414208', 'wdgly', 'VehicleMonitoring', '2018-06-28 11:18:03.000000', '1-超级管理员', '1', 'OperationMonitoring');
INSERT INTO `sys_js_gn` VALUES ('461852907256414209', 'wdgly', 'mergeVideo', '2018-06-28 11:18:03.000000', '1-超级管理员', '1', 'VehicleScheduling');
INSERT INTO `sys_js_gn` VALUES ('461852907256414210', 'wdgly', 'CloudPhoto', '2018-06-28 11:18:03.000000', '1-超级管理员', '1', 'VehicleScheduling');
INSERT INTO `sys_js_gn` VALUES ('461852907256414211', 'wdgly', 'abnormal', '2018-06-28 11:18:03.000000', '1-超级管理员', '1', 'VehicleScheduling');
INSERT INTO `sys_js_gn` VALUES ('461852907256414212', 'wdgly', 'OrderManagement', '2018-06-28 11:18:03.000000', '1-超级管理员', '1', 'VehicleScheduling');
INSERT INTO `sys_js_gn` VALUES ('461852907256414213', 'wdgly', 'Establish', '2018-06-28 11:18:03.000000', '1-超级管理员', '1', 'OrderManagement');
INSERT INTO `sys_js_gn` VALUES ('461852907256414214', 'wdgly', 'DriverManagement', '2018-06-28 11:18:03.000000', '1-超级管理员', '1', 'VehicleScheduling');
INSERT INTO `sys_js_gn` VALUES ('461852907256414215', 'wdgly', 'system-role', '2018-06-28 11:18:03.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('461852907256414216', 'wdgly', 'system-user', '2018-06-28 11:18:03.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('461852907256414217', 'wdgly', 'system-framework', '2018-06-28 11:18:03.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('461852907256414218', 'wdgly', 'ToExamine', '2018-06-28 11:18:03.000000', '1-超级管理员', '1', 'OrderManagement');
INSERT INTO `sys_js_gn` VALUES ('461852907256414219', 'wdgly', 'Consult', '2018-06-28 11:18:03.000000', '1-超级管理员', '1', 'OrderManagement');
INSERT INTO `sys_js_gn` VALUES ('461852907256414220', 'wdgly', 'Assignment', '2018-06-28 11:18:03.000000', '1-超级管理员', '1', 'OrderManagement');
INSERT INTO `sys_js_gn` VALUES ('461852907256414221', 'wdgly', 'Confirm', '2018-06-28 11:18:03.000000', '1-超级管理员', '1', 'OrderManagement');
INSERT INTO `sys_js_gn` VALUES ('461852907256414222', 'wdgly', 'ShuttleBus', '2018-06-28 11:18:03.000000', '1-超级管理员', '1', 'VehicleScheduling');
INSERT INTO `sys_js_gn` VALUES ('461852907256414223', 'wdgly', 'SiteMaintenance', '2018-06-28 11:18:03.000000', '1-超级管理员', '1', 'ShuttleBus');
INSERT INTO `sys_js_gn` VALUES ('461852907256414224', 'wdgly', 'LineMaintenance', '2018-06-28 11:18:03.000000', '1-超级管理员', '1', 'ShuttleBus');
INSERT INTO `sys_js_gn` VALUES ('461852907256414225', 'wdgly', 'Scheduling', '2018-06-28 11:18:03.000000', '1-超级管理员', '1', 'ShuttleBus');
INSERT INTO `sys_js_gn` VALUES ('461852907256414226', 'wdgly', 'dz_Confirm', '2018-06-28 11:18:03.000000', '1-超级管理员', '1', 'OrderManagement');
INSERT INTO `sys_js_gn` VALUES ('461852907256414227', 'wdgly', 'system-suggestions', '2018-06-28 11:18:03.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('461852907256414228', 'wdgly', 'system-notice', '2018-06-28 11:18:03.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('461852907256414229', 'wdgly', 'system-propelling', '2018-06-28 11:18:03.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('461852907256414230', 'wdgly', 'system-advertising', '2018-06-28 11:18:03.000000', '1-超级管理员', '1', 'system');
INSERT INTO `sys_js_gn` VALUES ('461852907256414231', 'wdgly', 'VehicleScheduling', '2018-06-28 11:18:03.000000', '1-超级管理员', '1', null);
INSERT INTO `sys_js_gn` VALUES ('461852907256414232', 'wdgly', 'vehicle-management', '2018-06-28 11:18:03.000000', '1-超级管理员', '1', 'VehicleScheduling');
INSERT INTO `sys_js_gn` VALUES ('461852907256414233', 'wdgly', 'FleetManagement', '2018-06-28 11:18:03.000000', '1-超级管理员', '1', 'VehicleScheduling');
INSERT INTO `sys_js_gn` VALUES ('461852907256414234', 'wdgly', 'ElectronicFence', '2018-06-28 11:18:03.000000', '1-超级管理员', '1', 'VehicleScheduling');
INSERT INTO `sys_js_gn` VALUES ('461852907256414235', 'wdgly', 'OverspeedLimit', '2018-06-28 11:18:03.000000', '1-超级管理员', '1', 'VehicleScheduling');
INSERT INTO `sys_js_gn` VALUES ('461852907256414236', 'wdgly', 'CloudVideo', '2018-06-28 11:18:03.000000', '1-超级管理员', '1', 'VehicleScheduling');
INSERT INTO `sys_js_gn` VALUES ('461852907256414237', 'wdgly', 'ScManage', '2018-06-28 11:18:03.000000', '1-超级管理员', '1', 'VehicleScheduling');
INSERT INTO `sys_js_gn` VALUES ('461852907256414238', 'wdgly', 'Sc_SiteMaintenance', '2018-06-28 11:18:03.000000', '1-超级管理员', '1', 'ScManage');
INSERT INTO `sys_js_gn` VALUES ('461852907256414239', 'wdgly', 'Sc_LineMaintenance', '2018-06-28 11:18:03.000000', '1-超级管理员', '1', 'ScManage');
INSERT INTO `sys_js_gn` VALUES ('461852907256414240', 'wdgly', 'Sc_Scheduling', '2018-06-28 11:18:03.000000', '1-超级管理员', '1', 'ScManage');
INSERT INTO `sys_js_gn` VALUES ('461852907256414241', 'wdgly', 'OperationMonitoring', '2018-06-28 11:18:03.000000', '1-超级管理员', '1', null);
INSERT INTO `sys_js_gn` VALUES ('461852907256414242', 'wdgly', 'BusMonitor', '2018-06-28 11:18:03.000000', '1-超级管理员', '1', 'OperationMonitoring');
INSERT INTO `sys_js_gn` VALUES ('461852907256414243', 'wdgly', 'TemporaryCarManagement', '2018-06-28 11:18:03.000000', '1-超级管理员', '1', 'VehicleScheduling');
INSERT INTO `sys_js_gn` VALUES ('461852907256414244', 'wdgly', 'UnitManagement', '2018-06-28 11:18:03.000000', '1-超级管理员', '1', 'TemporaryCarManagement');
INSERT INTO `sys_js_gn` VALUES ('461852907256414245', 'wdgly', 'VehicleManagement', '2018-06-28 11:18:03.000000', '1-超级管理员', '1', 'TemporaryCarManagement');
INSERT INTO `sys_js_gn` VALUES ('461852907256414246', 'wdgly', 'AccidentManagement', '2018-06-28 11:18:03.000000', '1-超级管理员', '1', 'VehicleScheduling');

-- ----------------------------
-- Table structure for sys_jzgxx
-- ----------------------------

CREATE TABLE `sys_jzgxx` (
  `ID` varchar(32) NOT NULL COMMENT 'ID',
  `XM` varchar(32) DEFAULT NULL COMMENT '姓名',
  `XB` char(2) DEFAULT NULL COMMENT '性别',
  `ZW` varchar(16) DEFAULT NULL COMMENT '职务',
  `ZJHM` varchar(16) DEFAULT NULL COMMENT '证件号码',
  `JGDM` varchar(32) DEFAULT NULL COMMENT '机构代码',
  `JDMC` varchar(32) DEFAULT NULL COMMENT '机构名称',
  `ZGLX` char(4) DEFAULT NULL COMMENT '职工类型',
  `CJR` varchar(64) DEFAULT NULL COMMENT '创建人',
  `CJSJ` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `XGR` varchar(64) DEFAULT NULL COMMENT '修改人',
  `XGSJ` datetime(6) DEFAULT NULL COMMENT '修改时间',
  `SJHM` varchar(16) DEFAULT NULL COMMENT '手机号码',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_message
-- ----------------------------

CREATE TABLE `sys_message` (
  `ID` varchar(32) DEFAULT NULL COMMENT '主键',
  `CREATION_TIME` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `MESSAGE` text COMMENT '短信标题，按业务不同，也许是正文，也许是JSON报文',
  `TYPE` char(3) DEFAULT NULL COMMENT '1、短信',
  `TITLE` varchar(200) DEFAULT NULL COMMENT '标题',
  `STATUS` char(3) DEFAULT NULL COMMENT '0、未发送 1、已发送  2、发送失败',
  `SEND_COUNT` char(3) DEFAULT NULL COMMENT '发送次数',
  `SEND_TIME` datetime(6) DEFAULT NULL COMMENT '发送时间',
  `REMARK` text COMMENT '备注',
  `SENDEE_CODE` varchar(200) DEFAULT NULL COMMENT '接收方编号(短信是手机号、微信是open_id)',
  `BIZ_ID` varchar(32) DEFAULT NULL COMMENT '业务编号(操作业务)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息表-平台所有消息发送';

-- ----------------------------
-- Table structure for sys_ptfw
-- ----------------------------

CREATE TABLE `sys_ptfw` (
  `FW_ID` varchar(32) NOT NULL COMMENT '服务id',
  `FWMC` varchar(32) DEFAULT NULL COMMENT '服务名称',
  `FWDM` varchar(32) DEFAULT NULL COMMENT '服务代码',
  `CJSJ` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `CJR` varchar(64) DEFAULT NULL COMMENT '创建人',
  `XGSJ` datetime(6) DEFAULT NULL COMMENT '修改时间',
  `XGR` varchar(64) DEFAULT NULL COMMENT '修改人',
  `ZT` char(2) DEFAULT NULL COMMENT '状态',
  `API_QZ` varchar(64) DEFAULT NULL COMMENT 'API前缀',
  `TB` varchar(32) DEFAULT NULL COMMENT '图标',
  PRIMARY KEY (`FW_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_ptjg
-- ----------------------------

CREATE TABLE `sys_ptjg` (
  `JGDM` varchar(20) NOT NULL COMMENT '机构代码',
  `JGMC` varchar(32) DEFAULT NULL COMMENT '机构名称',
  `JGLX` char(2) DEFAULT NULL COMMENT '机构类型',
  `ZT` char(2) DEFAULT NULL COMMENT '状态',
  `CJSJ` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `CJR` varchar(64) DEFAULT NULL COMMENT '创建人',
  `XGR` varchar(64) DEFAULT NULL COMMENT '修改人',
  `XGSJ` datetime(6) DEFAULT NULL COMMENT '修改时间',
  `GLY` varchar(32) DEFAULT NULL COMMENT '管理员',
  `GLYXM` varchar(32) DEFAULT NULL COMMENT '管理员姓名',
  `FJGDM` varchar(32) DEFAULT NULL COMMENT '父机构代码',
  `BZ` varchar(255) DEFAULT NULL COMMENT '备注',
  `JGDJ` decimal(65,30) DEFAULT NULL COMMENT '机构等级',
  PRIMARY KEY (`JGDM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='平台机构表';

INSERT INTO `sys_ptjg` VALUES ('100', '武汉大学', '10', '10', '2018-03-15 00:00:00.000000', '创建人', 'w修改人', '2018-03-15 00:00:00.000000', null, 't管理员姓名', null, null, '1.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100002', '哲学学院', '10', '10', '2018-05-06 00:00:00.000000', '1-超级管理员', null, null, null, '张三', '100', null, '2.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100002001', '哲学系', '10', '10', '2018-05-06 00:00:00.000000', '1-超级管理员', null, null, null, '张三', '100002', null, '3.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100002002', '宗教学系', '10', '10', '2018-05-06 16:09:01.000000', '1-超级管理员', null, null, null, '张三', '100002', null, '3.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100002003', '心理学系', '10', '10', '2018-05-06 16:09:04.000000', '1-超级管理员', null, null, null, '心理学系', '100002', null, '3.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100002004', '党政办公室', '10', '10', '2018-05-06 16:10:01.000000', '1-超级管理员', null, null, null, '党政办公室', '100002', null, '3.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100002005', '学生工作办公室', '10', '10', '2018-05-06 16:19:02.000000', '1-超级管理员', null, null, null, '学生工作办公室', '100002', null, '3.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100002006', '资料室', '10', '10', '2018-05-06 16:20:00.000000', '1-超级管理员', null, null, null, '资料室', '100002', null, '3.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100002007', '心理学实验室', '10', '10', '2018-05-06 18:05:05.000000', '1-超级管理员', null, null, null, '心理学实验室', '100002', null, '3.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100002008', '中西比较哲学研究中心', '10', '10', '2018-05-06 18:35:00.000000', '1-超级管理员', null, null, null, '中西比较哲学研究中心', '100002', null, '3.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100002009', '基督宗教与西方宗教文化研究中心', '10', '10', '2018-05-06 18:35:02.000000', '1-超级管理员', null, null, null, '基督宗教与西方宗教文化研究中心', '100002', null, '3.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100002010', '欧美宗教文化研究所', '10', '10', '2018-05-06 18:35:04.000000', '1-超级管理员', null, null, null, '欧美宗教文化研究所', '100002', null, '3.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100002011', '中国佛学与佛教艺术研究中心', '10', '10', '2018-05-06 18:35:05.000000', '1-超级管理员', null, null, null, '中国佛学与佛教艺术研究中心', '100002', null, '3.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100002012', '莱布尼茨研究中心', '10', '10', '2018-05-06 00:00:00.000000', '1-超级管理员', null, null, null, '莱布尼茨研究中心', '100002', null, '3.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100002013', '周易研究中心', '10', '10', '2018-05-06 00:00:00.000000', '1-超级管理员', null, null, null, '周易研究中心', '100002', null, '3.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100002014', '皮尔士研究中心', '10', '10', '2018-05-06 18:37:01.000000', '1-超级管理员', null, null, null, '皮尔士研究中心', '100002', null, '3.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100002015', '孔子与儒学研究中心', '10', '10', '2018-05-06 00:00:00.000000', '1-超级管理员', null, null, null, '孔子与儒学研究中心', '100002', null, '3.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100002016', '美学研究所', '10', '10', '2018-05-06 00:00:00.000000', '1-超级管理员', null, null, null, '美学研究所', '100002', null, '3.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100002017', '马克思主义哲学研究所', '10', '10', '2018-05-06 00:00:00.000000', '1-超级管理员', null, null, null, '马克思主义哲学研究所', '100002', null, '3.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100002018', '西方马克思主义哲学研究所', '10', '10', '2018-05-06 00:00:00.000000', '1-超级管理员', null, null, null, '西方马克思主义哲学研究所', '100002', null, '3.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100002019', '中国哲学研究所', '10', '10', '2018-05-06 00:00:00.000000', '1-超级管理员', null, null, null, '中国哲学研究所', '100002', null, '3.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100002020', '毛泽东思想研究所', '10', '10', '2018-05-06 00:00:00.000000', '1-超级管理员', null, null, null, '毛泽东思想研究所', '100002', null, '3.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100002021', '现代心理学研究中心', '10', '10', '2018-05-06 18:39:03.000000', '1-超级管理员', null, null, null, '现代心理学研究中心', '100002', null, '3.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100002022', '克雷茨曼研究所', '10', '10', '2018-05-06 00:00:00.000000', '1-超级管理员', null, null, null, '克雷茨曼研究所', '100002', null, '3.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100002023', '武汉大学健康研究中心', '10', '10', '2018-05-06 00:00:00.000000', '1-超级管理员', null, null, null, '武汉大学健康研究中心', '100002', null, '3.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100002024', '武汉大学婚恋心理研究中心', '10', '10', '2018-05-06 18:40:02.000000', '1-超级管理员', null, null, null, '武汉大学婚恋心理研究中心', '100002', null, '3.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100002025', '中国女书研究中心', '10', '10', '2018-05-06 18:40:03.000000', '1-超级管理员', null, null, null, '中国女书研究中心', '100002', null, '3.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100002026', '核心职业能力研究中心', '10', '10', '2018-05-06 18:40:05.000000', '1-超级管理员', null, null, null, '核心职业能力研究中心', '100002', null, '3.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100002027', '景观文化规划研究中心', '10', '10', '2018-05-06 18:41:00.000000', '1-超级管理员', null, null, null, '景观文化规划研究中心', '100002', null, '3.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100002028', '德国哲学研究所', '10', '10', '2018-05-06 18:41:02.000000', '1-超级管理员', null, null, null, '德国哲学研究所', '100002', null, '3.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100002031', '111', '10', '10', '2018-06-10 10:51:03.000000', '1-超级管理员', null, null, null, null, '100002', null, '3.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100004', '外国语言文学学院', '10', '10', '2018-05-06 16:21:00.000000', '1-超级管理员', null, null, null, '外国语言文学学院', '100', null, '2.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100005', '新闻与传播学院', '10', '10', '2018-05-06 16:21:04.000000', '1-超级管理员', null, null, null, '新闻与传播学院', '100', null, '2.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100006', '历史学院', '10', '10', '2018-05-06 16:22:00.000000', '1-超级管理员', null, null, null, '历史学院', '100', null, '2.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100007', '艺术系', '10', '10', '2018-05-06 00:00:00.000000', '1-超级管理员', null, null, null, '艺术', '100', null, '2.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100008', '经济与管理学院', '10', '10', '2018-05-06 16:30:02.000000', '1-超级管理员', null, null, null, '经济与管理学院', '100', null, '2.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100009', '法学院', '10', '10', '2018-05-06 17:24:05.000000', '1-超级管理员', null, null, null, '法学院', '100', null, '2.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100010', '政治与公共管理学院', '10', '10', '2018-05-06 17:25:01.000000', '1-超级管理员', null, null, null, '政治与公共管理学院', '100', null, '2.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100011', '马克思主义学院', '10', '10', '2018-05-06 17:33:01.000000', '1-超级管理员', null, null, null, '马克思主义学院', '100', null, '2.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100012', '社会学系', '10', '10', '2018-05-06 17:34:03.000000', '1-超级管理员', null, null, null, '社会学系', '100', null, '2.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100013', '信息管理学院', '10', '10', '2018-05-06 17:34:05.000000', '1-超级管理员', null, null, null, '信息管理学院', '100', null, '2.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100014', '数学与统计学院', '10', '10', '2018-05-06 00:00:00.000000', '1-超级管理员', null, null, null, '数学与统计学院', '100', null, '2.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100015', '数学与统计学院', '10', '10', '2018-05-06 17:35:05.000000', '1-超级管理员', null, null, null, '数学与统计学院', '100', null, '2.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100016', '物理科学与技术学院', '10', '10', '2018-05-06 17:36:00.000000', '1-超级管理员', null, null, null, '物理科学与技术学院', '100', null, '2.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100017', '化学与分子科学学院', '10', '10', '2018-05-06 17:36:01.000000', '1-超级管理员', null, null, null, '化学与分子科学学院', '100', null, '2.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100018', '生命科学学院', '10', '10', '2018-05-06 17:36:02.000000', '1-超级管理员', null, null, null, '生命科学学院', '100', null, '2.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100019', '资源与环境科学学院', '10', '10', '2018-05-06 17:36:03.000000', '1-超级管理员', null, null, null, '资源与环境科学学院', '100', null, '2.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100020', '动力与机械学院', '10', '10', '2018-05-06 17:36:04.000000', '1-超级管理员', null, null, null, '动力与机械学院', '100', null, '2.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100021', '电气工程学院', '10', '10', '2018-05-06 17:36:05.000000', '1-超级管理员', null, null, null, '电气工程学院', '100', null, '2.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100022', '土木建筑工程学院', '10', '10', '2018-05-06 17:37:00.000000', '1-超级管理员', null, null, null, '土木建筑工程学院', '100', null, '2.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100023', '水利电力学院', '10', '10', '2018-05-06 17:37:01.000000', '1-超级管理员', null, null, null, '水利电力学院', '100', null, '2.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100024', '城市设计学院', '10', '10', '2018-05-06 17:38:01.000000', '1-超级管理员', null, null, null, '城市设计学院', '100', null, '2.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100025', '电子信息学院', '10', '10', '2018-05-06 17:38:02.000000', '1-超级管理员', null, null, null, '电子信息学院', '100', null, '2.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100026', '计算机学院', '10', '10', '2018-05-06 17:38:03.000000', '1-超级管理员', null, null, null, '计算机学院', '100', null, '2.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100027', '国际软件学院', '10', '10', '2018-05-06 17:38:04.000000', '1-超级管理员', null, null, null, '国际软件学院', '100', null, '2.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100028', '遥感信息工程学院', '10', '10', '2018-05-06 17:38:05.000000', '1-超级管理员', null, null, null, '遥感信息工程学院', '100', null, '2.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100029', '测绘学院', '10', '10', '2018-05-06 17:39:00.000000', '1-超级管理员', null, null, null, '测绘学院', '100', null, '2.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100030', '印刷与包装系', '10', '10', '2018-05-06 17:39:01.000000', '1-超级管理员', null, null, null, '印刷与包装系', '100', null, '2.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100031', '基础医学院', '10', '10', '2018-05-06 17:39:02.000000', '1-超级管理员', null, null, null, '基础医学院', '100', null, '2.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100032', '健康学院', '10', '10', '2018-05-06 17:39:03.000000', '1-超级管理员', null, null, null, '健康学院', '100', null, '2.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100033', '药学院', '10', '10', '2018-05-06 17:42:05.000000', '1-超级管理员', null, null, null, '药学院', '100', null, '2.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100034', 'HOPE护理学院', '10', '10', '2018-05-06 17:43:00.000000', '1-超级管理员', null, null, null, 'HOPE护理学院', '100', null, '2.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100035', '第一临床学院', '10', '10', '2018-05-06 17:43:01.000000', '1-超级管理员', null, null, null, '第一临床学院', '100', null, '2.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100036', '第二临床学院', '10', '10', '2018-05-06 17:43:02.000000', '1-超级管理员', null, null, null, '第二临床学院', '100', null, '2.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100037', '口腔医学院', '10', '10', '2018-05-06 17:43:03.000000', '1-超级管理员', null, null, null, '口腔医学院', '100', null, '2.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100038', '文学院', '10', '10', '2018-05-06 00:00:00.000000', '1-超级管理员', null, null, null, '文学院', '100', null, '2.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100038029', '中国现当代文学教研室', '10', '10', '2018-05-06 00:00:00.000000', '1-超级管理员', null, null, null, '中国现当代文学教研室', '100038', null, '3.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('100038030', '中国古代文学教研室', '10', '10', '2018-05-06 00:00:00.000000', '1-超级管理员', null, null, null, '中国古代文学教研室', '100038', null, '3.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('101', '11', '10', '10', '2018-06-10 10:49:04.000000', '1-超级管理员', null, null, null, null, null, null, '1.000000000000000000000000000000');
INSERT INTO `sys_ptjg` VALUES ('102', '测试大学', '10', '10', '2018-03-15 00:00:00.000000', '创建人', 'w修改人', '2018-03-15 00:00:00.000000', null, 't管理员姓名', null, null, '1.000000000000000000000000000000');

-- ----------------------------
-- Table structure for sys_ptrz
-- ----------------------------

CREATE TABLE `sys_ptrz` (
  `RZ_ID` varchar(32) NOT NULL COMMENT '日志id',
  `CZLX` varchar(32) DEFAULT NULL COMMENT '操作类型',
  `CZSJ` datetime(6) DEFAULT NULL COMMENT '操作时间',
  `CZR` varchar(64) DEFAULT NULL COMMENT '操作人',
  `DX_ID` varchar(32) DEFAULT NULL COMMENT '对象id',
  `DXLX` varchar(32) DEFAULT NULL COMMENT '对象类型',
  `CS` text COMMENT '参数',
  `JG` text COMMENT '结果',
  `ZXSJ` decimal(65,30) DEFAULT NULL COMMENT '执行时间',
  `SM` varchar(32) DEFAULT NULL COMMENT '说明',
  `FF` varchar(64) DEFAULT NULL COMMENT '方法',
  PRIMARY KEY (`RZ_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='平台日志表';

-- ----------------------------
-- Table structure for sys_rlb
-- ----------------------------

CREATE TABLE `sys_rlb` (
  `RQDM` varchar(8) NOT NULL COMMENT '日期代码',
  `XQ` decimal(65,30) DEFAULT NULL COMMENT '星期',
  `ZT` char(2) DEFAULT NULL COMMENT '状态',
  `RQ` varchar(16) DEFAULT NULL COMMENT '日期',
  PRIMARY KEY (`RQDM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日历表';

INSERT INTO `sys_rlb` VALUES ('1122', '2.000000000000000000000000000000', '1', '2018-01-02');
INSERT INTO `sys_rlb` VALUES ('1123', '3.000000000000000000000000000000', '1', '2018-01-03');
INSERT INTO `sys_rlb` VALUES ('1124', '4.000000000000000000000000000000', '1', '2018-01-04');
INSERT INTO `sys_rlb` VALUES ('1125', '5.000000000000000000000000000000', '1', '2018-01-05');
INSERT INTO `sys_rlb` VALUES ('1126', '6.000000000000000000000000000000', '2', '2018-01-06');
INSERT INTO `sys_rlb` VALUES ('1127', '7.000000000000000000000000000000', '2', '2018-01-07');
INSERT INTO `sys_rlb` VALUES ('1128', '1.000000000000000000000000000000', '1', '2018-01-08');
INSERT INTO `sys_rlb` VALUES ('1129', '2.000000000000000000000000000000', '1', '2018-01-09');
INSERT INTO `sys_rlb` VALUES ('1130', '3.000000000000000000000000000000', '1', '2018-01-10');
INSERT INTO `sys_rlb` VALUES ('1131', '4.000000000000000000000000000000', '1', '2018-01-11');
INSERT INTO `sys_rlb` VALUES ('1132', '5.000000000000000000000000000000', '1', '2018-01-12');
INSERT INTO `sys_rlb` VALUES ('1133', '6.000000000000000000000000000000', '2', '2018-01-13');
INSERT INTO `sys_rlb` VALUES ('1134', '7.000000000000000000000000000000', '2', '2018-01-14');
INSERT INTO `sys_rlb` VALUES ('1135', '1.000000000000000000000000000000', '1', '2018-01-15');
INSERT INTO `sys_rlb` VALUES ('1136', '2.000000000000000000000000000000', '1', '2018-01-16');
INSERT INTO `sys_rlb` VALUES ('1137', '3.000000000000000000000000000000', '1', '2018-01-17');
INSERT INTO `sys_rlb` VALUES ('1138', '4.000000000000000000000000000000', '1', '2018-01-18');
INSERT INTO `sys_rlb` VALUES ('1139', '5.000000000000000000000000000000', '1', '2018-01-19');
INSERT INTO `sys_rlb` VALUES ('1140', '6.000000000000000000000000000000', '2', '2018-01-20');
INSERT INTO `sys_rlb` VALUES ('1141', '7.000000000000000000000000000000', '2', '2018-01-21');
INSERT INTO `sys_rlb` VALUES ('1142', '1.000000000000000000000000000000', '1', '2018-01-22');
INSERT INTO `sys_rlb` VALUES ('1143', '2.000000000000000000000000000000', '1', '2018-01-23');
INSERT INTO `sys_rlb` VALUES ('1144', '3.000000000000000000000000000000', '1', '2018-01-24');
INSERT INTO `sys_rlb` VALUES ('1145', '4.000000000000000000000000000000', '1', '2018-01-25');
INSERT INTO `sys_rlb` VALUES ('1146', '5.000000000000000000000000000000', '1', '2018-01-26');
INSERT INTO `sys_rlb` VALUES ('1147', '6.000000000000000000000000000000', '2', '2018-01-27');
INSERT INTO `sys_rlb` VALUES ('1148', '7.000000000000000000000000000000', '2', '2018-01-28');
INSERT INTO `sys_rlb` VALUES ('1149', '1.000000000000000000000000000000', '1', '2018-01-29');
INSERT INTO `sys_rlb` VALUES ('1150', '2.000000000000000000000000000000', '1', '2018-01-30');
INSERT INTO `sys_rlb` VALUES ('1151', '3.000000000000000000000000000000', '1', '2018-01-31');
INSERT INTO `sys_rlb` VALUES ('1152', '4.000000000000000000000000000000', '1', '2018-02-01');
INSERT INTO `sys_rlb` VALUES ('1153', '5.000000000000000000000000000000', '1', '2018-02-02');
INSERT INTO `sys_rlb` VALUES ('1154', '6.000000000000000000000000000000', '2', '2018-02-03');
INSERT INTO `sys_rlb` VALUES ('1155', '7.000000000000000000000000000000', '2', '2018-02-04');
INSERT INTO `sys_rlb` VALUES ('1156', '1.000000000000000000000000000000', '1', '2018-02-05');
INSERT INTO `sys_rlb` VALUES ('1157', '2.000000000000000000000000000000', '1', '2018-02-06');
INSERT INTO `sys_rlb` VALUES ('1158', '3.000000000000000000000000000000', '1', '2018-02-07');
INSERT INTO `sys_rlb` VALUES ('1159', '4.000000000000000000000000000000', '1', '2018-02-08');
INSERT INTO `sys_rlb` VALUES ('1160', '5.000000000000000000000000000000', '1', '2018-02-09');
INSERT INTO `sys_rlb` VALUES ('1161', '6.000000000000000000000000000000', '2', '2018-02-10');
INSERT INTO `sys_rlb` VALUES ('1162', '7.000000000000000000000000000000', '4', '2018-02-11');
INSERT INTO `sys_rlb` VALUES ('1163', '1.000000000000000000000000000000', '1', '2018-02-12');
INSERT INTO `sys_rlb` VALUES ('1164', '2.000000000000000000000000000000', '1', '2018-02-13');
INSERT INTO `sys_rlb` VALUES ('1165', '3.000000000000000000000000000000', '1', '2018-02-14');
INSERT INTO `sys_rlb` VALUES ('1166', '4.000000000000000000000000000000', '3', '2018-02-15');
INSERT INTO `sys_rlb` VALUES ('1167', '5.000000000000000000000000000000', '3', '2018-02-16');
INSERT INTO `sys_rlb` VALUES ('1168', '6.000000000000000000000000000000', '3', '2018-02-17');
INSERT INTO `sys_rlb` VALUES ('1169', '7.000000000000000000000000000000', '3', '2018-02-18');
INSERT INTO `sys_rlb` VALUES ('1170', '1.000000000000000000000000000000', '3', '2018-02-19');
INSERT INTO `sys_rlb` VALUES ('1171', '2.000000000000000000000000000000', '3', '2018-02-20');
INSERT INTO `sys_rlb` VALUES ('1172', '3.000000000000000000000000000000', '3', '2018-02-21');
INSERT INTO `sys_rlb` VALUES ('1173', '4.000000000000000000000000000000', '1', '2018-02-22');
INSERT INTO `sys_rlb` VALUES ('1174', '5.000000000000000000000000000000', '1', '2018-02-23');
INSERT INTO `sys_rlb` VALUES ('1175', '6.000000000000000000000000000000', '1', '2018-02-24');
INSERT INTO `sys_rlb` VALUES ('1176', '7.000000000000000000000000000000', '2', '2018-02-25');
INSERT INTO `sys_rlb` VALUES ('1177', '1.000000000000000000000000000000', '1', '2018-02-26');
INSERT INTO `sys_rlb` VALUES ('1178', '2.000000000000000000000000000000', '1', '2018-02-27');
INSERT INTO `sys_rlb` VALUES ('1179', '3.000000000000000000000000000000', '1', '2018-02-28');
INSERT INTO `sys_rlb` VALUES ('1180', '4.000000000000000000000000000000', '1', '2018-03-01');
INSERT INTO `sys_rlb` VALUES ('1181', '5.000000000000000000000000000000', '1', '2018-03-02');
INSERT INTO `sys_rlb` VALUES ('1182', '6.000000000000000000000000000000', '2', '2018-03-03');
INSERT INTO `sys_rlb` VALUES ('1183', '7.000000000000000000000000000000', '2', '2018-03-04');
INSERT INTO `sys_rlb` VALUES ('1184', '1.000000000000000000000000000000', '1', '2018-03-05');
INSERT INTO `sys_rlb` VALUES ('1185', '2.000000000000000000000000000000', '1', '2018-03-06');
INSERT INTO `sys_rlb` VALUES ('1186', '3.000000000000000000000000000000', '1', '2018-03-07');
INSERT INTO `sys_rlb` VALUES ('1187', '4.000000000000000000000000000000', '1', '2018-03-08');
INSERT INTO `sys_rlb` VALUES ('1188', '5.000000000000000000000000000000', '1', '2018-03-09');
INSERT INTO `sys_rlb` VALUES ('1189', '6.000000000000000000000000000000', '2', '2018-03-10');
INSERT INTO `sys_rlb` VALUES ('1190', '7.000000000000000000000000000000', '2', '2018-03-11');
INSERT INTO `sys_rlb` VALUES ('1191', '1.000000000000000000000000000000', '1', '2018-03-12');
INSERT INTO `sys_rlb` VALUES ('1192', '2.000000000000000000000000000000', '1', '2018-03-13');
INSERT INTO `sys_rlb` VALUES ('1193', '3.000000000000000000000000000000', '1', '2018-03-14');
INSERT INTO `sys_rlb` VALUES ('1194', '4.000000000000000000000000000000', '1', '2018-03-15');
INSERT INTO `sys_rlb` VALUES ('1195', '5.000000000000000000000000000000', '1', '2018-03-16');
INSERT INTO `sys_rlb` VALUES ('1196', '6.000000000000000000000000000000', '2', '2018-03-17');
INSERT INTO `sys_rlb` VALUES ('1197', '7.000000000000000000000000000000', '2', '2018-03-18');
INSERT INTO `sys_rlb` VALUES ('1198', '1.000000000000000000000000000000', '1', '2018-03-19');
INSERT INTO `sys_rlb` VALUES ('1199', '2.000000000000000000000000000000', '1', '2018-03-20');
INSERT INTO `sys_rlb` VALUES ('1200', '3.000000000000000000000000000000', '1', '2018-03-21');
INSERT INTO `sys_rlb` VALUES ('1201', '4.000000000000000000000000000000', '1', '2018-03-22');
INSERT INTO `sys_rlb` VALUES ('1202', '5.000000000000000000000000000000', '1', '2018-03-23');
INSERT INTO `sys_rlb` VALUES ('1203', '6.000000000000000000000000000000', '2', '2018-03-24');
INSERT INTO `sys_rlb` VALUES ('1204', '7.000000000000000000000000000000', '2', '2018-03-25');
INSERT INTO `sys_rlb` VALUES ('1205', '1.000000000000000000000000000000', '1', '2018-03-26');
INSERT INTO `sys_rlb` VALUES ('1206', '2.000000000000000000000000000000', '1', '2018-03-27');
INSERT INTO `sys_rlb` VALUES ('1207', '3.000000000000000000000000000000', '1', '2018-03-28');
INSERT INTO `sys_rlb` VALUES ('1208', '4.000000000000000000000000000000', '1', '2018-03-29');
INSERT INTO `sys_rlb` VALUES ('1209', '5.000000000000000000000000000000', '1', '2018-03-30');
INSERT INTO `sys_rlb` VALUES ('1210', '6.000000000000000000000000000000', '2', '2018-03-31');
INSERT INTO `sys_rlb` VALUES ('1211', '7.000000000000000000000000000000', '2', '2018-04-01');
INSERT INTO `sys_rlb` VALUES ('1212', '1.000000000000000000000000000000', '1', '2018-04-02');
INSERT INTO `sys_rlb` VALUES ('1213', '2.000000000000000000000000000000', '1', '2018-04-03');
INSERT INTO `sys_rlb` VALUES ('1214', '3.000000000000000000000000000000', '1', '2018-04-04');
INSERT INTO `sys_rlb` VALUES ('1215', '4.000000000000000000000000000000', '3', '2018-04-05');
INSERT INTO `sys_rlb` VALUES ('1216', '5.000000000000000000000000000000', '3', '2018-04-06');
INSERT INTO `sys_rlb` VALUES ('1217', '6.000000000000000000000000000000', '3', '2018-04-07');
INSERT INTO `sys_rlb` VALUES ('1218', '7.000000000000000000000000000000', '4', '2018-04-08');
INSERT INTO `sys_rlb` VALUES ('1219', '1.000000000000000000000000000000', '1', '2018-04-09');
INSERT INTO `sys_rlb` VALUES ('1220', '2.000000000000000000000000000000', '1', '2018-04-10');
INSERT INTO `sys_rlb` VALUES ('1221', '3.000000000000000000000000000000', '1', '2018-04-11');
INSERT INTO `sys_rlb` VALUES ('1222', '4.000000000000000000000000000000', '1', '2018-04-12');
INSERT INTO `sys_rlb` VALUES ('1223', '5.000000000000000000000000000000', '1', '2018-04-13');
INSERT INTO `sys_rlb` VALUES ('1224', '6.000000000000000000000000000000', '2', '2018-04-14');
INSERT INTO `sys_rlb` VALUES ('1225', '7.000000000000000000000000000000', '2', '2018-04-15');
INSERT INTO `sys_rlb` VALUES ('1226', '1.000000000000000000000000000000', '1', '2018-04-16');
INSERT INTO `sys_rlb` VALUES ('1227', '2.000000000000000000000000000000', '1', '2018-04-17');
INSERT INTO `sys_rlb` VALUES ('1228', '3.000000000000000000000000000000', '1', '2018-04-18');
INSERT INTO `sys_rlb` VALUES ('1229', '4.000000000000000000000000000000', '1', '2018-04-19');
INSERT INTO `sys_rlb` VALUES ('1230', '5.000000000000000000000000000000', '1', '2018-04-20');
INSERT INTO `sys_rlb` VALUES ('1231', '6.000000000000000000000000000000', '2', '2018-04-21');
INSERT INTO `sys_rlb` VALUES ('1232', '7.000000000000000000000000000000', '2', '2018-04-22');
INSERT INTO `sys_rlb` VALUES ('1233', '1.000000000000000000000000000000', '1', '2018-04-23');
INSERT INTO `sys_rlb` VALUES ('1234', '2.000000000000000000000000000000', '1', '2018-04-24');
INSERT INTO `sys_rlb` VALUES ('1235', '3.000000000000000000000000000000', '1', '2018-04-25');
INSERT INTO `sys_rlb` VALUES ('1236', '4.000000000000000000000000000000', '1', '2018-04-26');
INSERT INTO `sys_rlb` VALUES ('1237', '5.000000000000000000000000000000', '1', '2018-04-27');
INSERT INTO `sys_rlb` VALUES ('1238', '6.000000000000000000000000000000', '4', '2018-04-28');
INSERT INTO `sys_rlb` VALUES ('1239', '7.000000000000000000000000000000', '3', '2018-04-29');
INSERT INTO `sys_rlb` VALUES ('1240', '1.000000000000000000000000000000', '3', '2018-04-30');
INSERT INTO `sys_rlb` VALUES ('1241', '2.000000000000000000000000000000', '3', '2018-05-01');
INSERT INTO `sys_rlb` VALUES ('1242', '3.000000000000000000000000000000', '1', '2018-05-02');
INSERT INTO `sys_rlb` VALUES ('1243', '4.000000000000000000000000000000', '1', '2018-05-03');
INSERT INTO `sys_rlb` VALUES ('1244', '5.000000000000000000000000000000', '1', '2018-05-04');
INSERT INTO `sys_rlb` VALUES ('1245', '6.000000000000000000000000000000', '2', '2018-05-05');
INSERT INTO `sys_rlb` VALUES ('1246', '7.000000000000000000000000000000', '2', '2018-05-06');
INSERT INTO `sys_rlb` VALUES ('1247', '1.000000000000000000000000000000', '1', '2018-05-07');
INSERT INTO `sys_rlb` VALUES ('1248', '2.000000000000000000000000000000', '1', '2018-05-08');
INSERT INTO `sys_rlb` VALUES ('1249', '3.000000000000000000000000000000', '1', '2018-05-09');
INSERT INTO `sys_rlb` VALUES ('1250', '4.000000000000000000000000000000', '1', '2018-05-10');
INSERT INTO `sys_rlb` VALUES ('1251', '5.000000000000000000000000000000', '1', '2018-05-11');
INSERT INTO `sys_rlb` VALUES ('1252', '6.000000000000000000000000000000', '2', '2018-05-12');
INSERT INTO `sys_rlb` VALUES ('1253', '7.000000000000000000000000000000', '2', '2018-05-13');
INSERT INTO `sys_rlb` VALUES ('1254', '1.000000000000000000000000000000', '1', '2018-05-14');
INSERT INTO `sys_rlb` VALUES ('1255', '2.000000000000000000000000000000', '1', '2018-05-15');
INSERT INTO `sys_rlb` VALUES ('1256', '3.000000000000000000000000000000', '1', '2018-05-16');
INSERT INTO `sys_rlb` VALUES ('1257', '4.000000000000000000000000000000', '1', '2018-05-17');
INSERT INTO `sys_rlb` VALUES ('1258', '5.000000000000000000000000000000', '1', '2018-05-18');
INSERT INTO `sys_rlb` VALUES ('1259', '6.000000000000000000000000000000', '2', '2018-05-19');
INSERT INTO `sys_rlb` VALUES ('1260', '7.000000000000000000000000000000', '2', '2018-05-20');
INSERT INTO `sys_rlb` VALUES ('1261', '1.000000000000000000000000000000', '1', '2018-05-21');
INSERT INTO `sys_rlb` VALUES ('1262', '2.000000000000000000000000000000', '1', '2018-05-22');
INSERT INTO `sys_rlb` VALUES ('1263', '3.000000000000000000000000000000', '1', '2018-05-23');
INSERT INTO `sys_rlb` VALUES ('1264', '4.000000000000000000000000000000', '1', '2018-05-24');
INSERT INTO `sys_rlb` VALUES ('1265', '5.000000000000000000000000000000', '1', '2018-05-25');
INSERT INTO `sys_rlb` VALUES ('1266', '6.000000000000000000000000000000', '2', '2018-05-26');
INSERT INTO `sys_rlb` VALUES ('1267', '7.000000000000000000000000000000', '2', '2018-05-27');
INSERT INTO `sys_rlb` VALUES ('1268', '1.000000000000000000000000000000', '1', '2018-05-28');
INSERT INTO `sys_rlb` VALUES ('1269', '2.000000000000000000000000000000', '1', '2018-05-29');
INSERT INTO `sys_rlb` VALUES ('1270', '3.000000000000000000000000000000', '1', '2018-05-30');
INSERT INTO `sys_rlb` VALUES ('1271', '4.000000000000000000000000000000', '1', '2018-05-31');
INSERT INTO `sys_rlb` VALUES ('1272', '5.000000000000000000000000000000', '1', '2018-06-01');
INSERT INTO `sys_rlb` VALUES ('1273', '6.000000000000000000000000000000', '2', '2018-06-02');
INSERT INTO `sys_rlb` VALUES ('1274', '7.000000000000000000000000000000', '2', '2018-06-03');
INSERT INTO `sys_rlb` VALUES ('1275', '1.000000000000000000000000000000', '1', '2018-06-04');
INSERT INTO `sys_rlb` VALUES ('1276', '2.000000000000000000000000000000', '1', '2018-06-05');
INSERT INTO `sys_rlb` VALUES ('1277', '3.000000000000000000000000000000', '1', '2018-06-06');
INSERT INTO `sys_rlb` VALUES ('1278', '4.000000000000000000000000000000', '1', '2018-06-07');
INSERT INTO `sys_rlb` VALUES ('1279', '5.000000000000000000000000000000', '1', '2018-06-08');
INSERT INTO `sys_rlb` VALUES ('1280', '6.000000000000000000000000000000', '2', '2018-06-09');
INSERT INTO `sys_rlb` VALUES ('1281', '7.000000000000000000000000000000', '2', '2018-06-10');
INSERT INTO `sys_rlb` VALUES ('1282', '1.000000000000000000000000000000', '1', '2018-06-11');
INSERT INTO `sys_rlb` VALUES ('1283', '2.000000000000000000000000000000', '1', '2018-06-12');
INSERT INTO `sys_rlb` VALUES ('1284', '3.000000000000000000000000000000', '1', '2018-06-13');
INSERT INTO `sys_rlb` VALUES ('1285', '4.000000000000000000000000000000', '1', '2018-06-14');
INSERT INTO `sys_rlb` VALUES ('1286', '5.000000000000000000000000000000', '1', '2018-06-15');
INSERT INTO `sys_rlb` VALUES ('1287', '6.000000000000000000000000000000', '3', '2018-06-16');
INSERT INTO `sys_rlb` VALUES ('1288', '7.000000000000000000000000000000', '3', '2018-06-17');
INSERT INTO `sys_rlb` VALUES ('1289', '1.000000000000000000000000000000', '3', '2018-06-18');
INSERT INTO `sys_rlb` VALUES ('1290', '2.000000000000000000000000000000', '1', '2018-06-19');
INSERT INTO `sys_rlb` VALUES ('1291', '3.000000000000000000000000000000', '1', '2018-06-20');
INSERT INTO `sys_rlb` VALUES ('1292', '4.000000000000000000000000000000', '1', '2018-06-21');
INSERT INTO `sys_rlb` VALUES ('1293', '5.000000000000000000000000000000', '1', '2018-06-22');
INSERT INTO `sys_rlb` VALUES ('1294', '6.000000000000000000000000000000', '2', '2018-06-23');
INSERT INTO `sys_rlb` VALUES ('1295', '7.000000000000000000000000000000', '2', '2018-06-24');
INSERT INTO `sys_rlb` VALUES ('1296', '1.000000000000000000000000000000', '1', '2018-06-25');
INSERT INTO `sys_rlb` VALUES ('1297', '2.000000000000000000000000000000', '1', '2018-06-26');
INSERT INTO `sys_rlb` VALUES ('1298', '3.000000000000000000000000000000', '1', '2018-06-27');
INSERT INTO `sys_rlb` VALUES ('1299', '4.000000000000000000000000000000', '1', '2018-06-28');
INSERT INTO `sys_rlb` VALUES ('1300', '5.000000000000000000000000000000', '1', '2018-06-29');
INSERT INTO `sys_rlb` VALUES ('1301', '6.000000000000000000000000000000', '2', '2018-06-30');
INSERT INTO `sys_rlb` VALUES ('1302', '7.000000000000000000000000000000', '2', '2018-07-01');
INSERT INTO `sys_rlb` VALUES ('1303', '1.000000000000000000000000000000', '1', '2018-07-02');
INSERT INTO `sys_rlb` VALUES ('1304', '2.000000000000000000000000000000', '1', '2018-07-03');
INSERT INTO `sys_rlb` VALUES ('1305', '3.000000000000000000000000000000', '1', '2018-07-04');
INSERT INTO `sys_rlb` VALUES ('1306', '4.000000000000000000000000000000', '1', '2018-07-05');
INSERT INTO `sys_rlb` VALUES ('1307', '5.000000000000000000000000000000', '1', '2018-07-06');
INSERT INTO `sys_rlb` VALUES ('1308', '6.000000000000000000000000000000', '2', '2018-07-07');
INSERT INTO `sys_rlb` VALUES ('1309', '7.000000000000000000000000000000', '2', '2018-07-08');
INSERT INTO `sys_rlb` VALUES ('1310', '1.000000000000000000000000000000', '1', '2018-07-09');
INSERT INTO `sys_rlb` VALUES ('1311', '2.000000000000000000000000000000', '1', '2018-07-10');
INSERT INTO `sys_rlb` VALUES ('1312', '3.000000000000000000000000000000', '1', '2018-07-11');
INSERT INTO `sys_rlb` VALUES ('1313', '4.000000000000000000000000000000', '1', '2018-07-12');
INSERT INTO `sys_rlb` VALUES ('1314', '5.000000000000000000000000000000', '1', '2018-07-13');
INSERT INTO `sys_rlb` VALUES ('1315', '6.000000000000000000000000000000', '2', '2018-07-14');
INSERT INTO `sys_rlb` VALUES ('1316', '7.000000000000000000000000000000', '2', '2018-07-15');
INSERT INTO `sys_rlb` VALUES ('1317', '1.000000000000000000000000000000', '1', '2018-07-16');
INSERT INTO `sys_rlb` VALUES ('1318', '2.000000000000000000000000000000', '1', '2018-07-17');
INSERT INTO `sys_rlb` VALUES ('1319', '3.000000000000000000000000000000', '1', '2018-07-18');
INSERT INTO `sys_rlb` VALUES ('1320', '4.000000000000000000000000000000', '1', '2018-07-19');
INSERT INTO `sys_rlb` VALUES ('1321', '5.000000000000000000000000000000', '1', '2018-07-20');
INSERT INTO `sys_rlb` VALUES ('1322', '6.000000000000000000000000000000', '2', '2018-07-21');
INSERT INTO `sys_rlb` VALUES ('1323', '7.000000000000000000000000000000', '2', '2018-07-22');
INSERT INTO `sys_rlb` VALUES ('1324', '1.000000000000000000000000000000', '1', '2018-07-23');
INSERT INTO `sys_rlb` VALUES ('1325', '2.000000000000000000000000000000', '1', '2018-07-24');
INSERT INTO `sys_rlb` VALUES ('1326', '3.000000000000000000000000000000', '1', '2018-07-25');
INSERT INTO `sys_rlb` VALUES ('1327', '4.000000000000000000000000000000', '1', '2018-07-26');
INSERT INTO `sys_rlb` VALUES ('1328', '5.000000000000000000000000000000', '1', '2018-07-27');
INSERT INTO `sys_rlb` VALUES ('1329', '6.000000000000000000000000000000', '2', '2018-07-28');
INSERT INTO `sys_rlb` VALUES ('1330', '7.000000000000000000000000000000', '2', '2018-07-29');
INSERT INTO `sys_rlb` VALUES ('1331', '1.000000000000000000000000000000', '1', '2018-07-30');
INSERT INTO `sys_rlb` VALUES ('1332', '2.000000000000000000000000000000', '1', '2018-07-31');
INSERT INTO `sys_rlb` VALUES ('1333', '3.000000000000000000000000000000', '1', '2018-08-01');
INSERT INTO `sys_rlb` VALUES ('1334', '4.000000000000000000000000000000', '1', '2018-08-02');
INSERT INTO `sys_rlb` VALUES ('1335', '5.000000000000000000000000000000', '1', '2018-08-03');
INSERT INTO `sys_rlb` VALUES ('1336', '6.000000000000000000000000000000', '2', '2018-08-04');
INSERT INTO `sys_rlb` VALUES ('1337', '7.000000000000000000000000000000', '2', '2018-08-05');
INSERT INTO `sys_rlb` VALUES ('1338', '1.000000000000000000000000000000', '1', '2018-08-06');
INSERT INTO `sys_rlb` VALUES ('1339', '2.000000000000000000000000000000', '1', '2018-08-07');
INSERT INTO `sys_rlb` VALUES ('1340', '3.000000000000000000000000000000', '1', '2018-08-08');
INSERT INTO `sys_rlb` VALUES ('1341', '4.000000000000000000000000000000', '1', '2018-08-09');
INSERT INTO `sys_rlb` VALUES ('1342', '5.000000000000000000000000000000', '1', '2018-08-10');
INSERT INTO `sys_rlb` VALUES ('1343', '6.000000000000000000000000000000', '2', '2018-08-11');
INSERT INTO `sys_rlb` VALUES ('1344', '7.000000000000000000000000000000', '2', '2018-08-12');
INSERT INTO `sys_rlb` VALUES ('1345', '1.000000000000000000000000000000', '1', '2018-08-13');
INSERT INTO `sys_rlb` VALUES ('1346', '2.000000000000000000000000000000', '1', '2018-08-14');
INSERT INTO `sys_rlb` VALUES ('1347', '3.000000000000000000000000000000', '1', '2018-08-15');
INSERT INTO `sys_rlb` VALUES ('1348', '4.000000000000000000000000000000', '1', '2018-08-16');
INSERT INTO `sys_rlb` VALUES ('1349', '5.000000000000000000000000000000', '1', '2018-08-17');
INSERT INTO `sys_rlb` VALUES ('1350', '6.000000000000000000000000000000', '2', '2018-08-18');
INSERT INTO `sys_rlb` VALUES ('1351', '7.000000000000000000000000000000', '2', '2018-08-19');
INSERT INTO `sys_rlb` VALUES ('1352', '1.000000000000000000000000000000', '1', '2018-08-20');
INSERT INTO `sys_rlb` VALUES ('1353', '2.000000000000000000000000000000', '1', '2018-08-21');
INSERT INTO `sys_rlb` VALUES ('1354', '3.000000000000000000000000000000', '1', '2018-08-22');
INSERT INTO `sys_rlb` VALUES ('1355', '4.000000000000000000000000000000', '1', '2018-08-23');
INSERT INTO `sys_rlb` VALUES ('1356', '5.000000000000000000000000000000', '1', '2018-08-24');
INSERT INTO `sys_rlb` VALUES ('1357', '6.000000000000000000000000000000', '2', '2018-08-25');
INSERT INTO `sys_rlb` VALUES ('1358', '7.000000000000000000000000000000', '2', '2018-08-26');
INSERT INTO `sys_rlb` VALUES ('1359', '1.000000000000000000000000000000', '1', '2018-08-27');
INSERT INTO `sys_rlb` VALUES ('1360', '2.000000000000000000000000000000', '1', '2018-08-28');
INSERT INTO `sys_rlb` VALUES ('1361', '3.000000000000000000000000000000', '1', '2018-08-29');
INSERT INTO `sys_rlb` VALUES ('1362', '4.000000000000000000000000000000', '1', '2018-08-30');
INSERT INTO `sys_rlb` VALUES ('1363', '5.000000000000000000000000000000', '1', '2018-08-31');
INSERT INTO `sys_rlb` VALUES ('1364', '6.000000000000000000000000000000', '2', '2018-09-01');
INSERT INTO `sys_rlb` VALUES ('1365', '7.000000000000000000000000000000', '2', '2018-09-02');
INSERT INTO `sys_rlb` VALUES ('1366', '1.000000000000000000000000000000', '1', '2018-09-03');
INSERT INTO `sys_rlb` VALUES ('1367', '2.000000000000000000000000000000', '1', '2018-09-04');
INSERT INTO `sys_rlb` VALUES ('1368', '3.000000000000000000000000000000', '1', '2018-09-05');
INSERT INTO `sys_rlb` VALUES ('1369', '4.000000000000000000000000000000', '1', '2018-09-06');
INSERT INTO `sys_rlb` VALUES ('1370', '5.000000000000000000000000000000', '1', '2018-09-07');
INSERT INTO `sys_rlb` VALUES ('1371', '6.000000000000000000000000000000', '2', '2018-09-08');
INSERT INTO `sys_rlb` VALUES ('1372', '7.000000000000000000000000000000', '2', '2018-09-09');
INSERT INTO `sys_rlb` VALUES ('1373', '1.000000000000000000000000000000', '1', '2018-09-10');
INSERT INTO `sys_rlb` VALUES ('1374', '2.000000000000000000000000000000', '1', '2018-09-11');
INSERT INTO `sys_rlb` VALUES ('1375', '3.000000000000000000000000000000', '1', '2018-09-12');
INSERT INTO `sys_rlb` VALUES ('1376', '4.000000000000000000000000000000', '1', '2018-09-13');
INSERT INTO `sys_rlb` VALUES ('1377', '5.000000000000000000000000000000', '1', '2018-09-14');
INSERT INTO `sys_rlb` VALUES ('1378', '6.000000000000000000000000000000', '2', '2018-09-15');
INSERT INTO `sys_rlb` VALUES ('1379', '7.000000000000000000000000000000', '2', '2018-09-16');
INSERT INTO `sys_rlb` VALUES ('1380', '1.000000000000000000000000000000', '1', '2018-09-17');
INSERT INTO `sys_rlb` VALUES ('1381', '2.000000000000000000000000000000', '1', '2018-09-18');
INSERT INTO `sys_rlb` VALUES ('1382', '3.000000000000000000000000000000', '1', '2018-09-19');
INSERT INTO `sys_rlb` VALUES ('1383', '4.000000000000000000000000000000', '1', '2018-09-20');
INSERT INTO `sys_rlb` VALUES ('1384', '5.000000000000000000000000000000', '1', '2018-09-21');
INSERT INTO `sys_rlb` VALUES ('1385', '6.000000000000000000000000000000', '3', '2018-09-22');
INSERT INTO `sys_rlb` VALUES ('1386', '7.000000000000000000000000000000', '3', '2018-09-23');
INSERT INTO `sys_rlb` VALUES ('1387', '1.000000000000000000000000000000', '3', '2018-09-24');
INSERT INTO `sys_rlb` VALUES ('1388', '2.000000000000000000000000000000', '1', '2018-09-25');
INSERT INTO `sys_rlb` VALUES ('1389', '3.000000000000000000000000000000', '1', '2018-09-26');
INSERT INTO `sys_rlb` VALUES ('1390', '4.000000000000000000000000000000', '1', '2018-09-27');
INSERT INTO `sys_rlb` VALUES ('1391', '5.000000000000000000000000000000', '1', '2018-09-28');
INSERT INTO `sys_rlb` VALUES ('1392', '6.000000000000000000000000000000', '4', '2018-09-29');
INSERT INTO `sys_rlb` VALUES ('1393', '7.000000000000000000000000000000', '4', '2018-09-30');
INSERT INTO `sys_rlb` VALUES ('1394', '1.000000000000000000000000000000', '3', '2018-10-01');
INSERT INTO `sys_rlb` VALUES ('1395', '2.000000000000000000000000000000', '3', '2018-10-02');
INSERT INTO `sys_rlb` VALUES ('1396', '3.000000000000000000000000000000', '3', '2018-10-03');
INSERT INTO `sys_rlb` VALUES ('1397', '4.000000000000000000000000000000', '3', '2018-10-04');
INSERT INTO `sys_rlb` VALUES ('1398', '5.000000000000000000000000000000', '3', '2018-10-05');
INSERT INTO `sys_rlb` VALUES ('1399', '6.000000000000000000000000000000', '3', '2018-10-06');
INSERT INTO `sys_rlb` VALUES ('1400', '7.000000000000000000000000000000', '3', '2018-10-07');
INSERT INTO `sys_rlb` VALUES ('1401', '1.000000000000000000000000000000', '1', '2018-10-08');
INSERT INTO `sys_rlb` VALUES ('1402', '2.000000000000000000000000000000', '1', '2018-10-09');
INSERT INTO `sys_rlb` VALUES ('1403', '3.000000000000000000000000000000', '1', '2018-10-10');
INSERT INTO `sys_rlb` VALUES ('1404', '4.000000000000000000000000000000', '1', '2018-10-11');
INSERT INTO `sys_rlb` VALUES ('1405', '5.000000000000000000000000000000', '1', '2018-10-12');
INSERT INTO `sys_rlb` VALUES ('1406', '6.000000000000000000000000000000', '2', '2018-10-13');
INSERT INTO `sys_rlb` VALUES ('1407', '7.000000000000000000000000000000', '2', '2018-10-14');
INSERT INTO `sys_rlb` VALUES ('1408', '1.000000000000000000000000000000', '1', '2018-10-15');
INSERT INTO `sys_rlb` VALUES ('1409', '2.000000000000000000000000000000', '1', '2018-10-16');
INSERT INTO `sys_rlb` VALUES ('1410', '3.000000000000000000000000000000', '1', '2018-10-17');
INSERT INTO `sys_rlb` VALUES ('1411', '4.000000000000000000000000000000', '1', '2018-10-18');
INSERT INTO `sys_rlb` VALUES ('1412', '5.000000000000000000000000000000', '1', '2018-10-19');
INSERT INTO `sys_rlb` VALUES ('1413', '6.000000000000000000000000000000', '2', '2018-10-20');
INSERT INTO `sys_rlb` VALUES ('1414', '7.000000000000000000000000000000', '2', '2018-10-21');
INSERT INTO `sys_rlb` VALUES ('1415', '1.000000000000000000000000000000', '1', '2018-10-22');
INSERT INTO `sys_rlb` VALUES ('1416', '2.000000000000000000000000000000', '1', '2018-10-23');
INSERT INTO `sys_rlb` VALUES ('1417', '3.000000000000000000000000000000', '1', '2018-10-24');
INSERT INTO `sys_rlb` VALUES ('1418', '4.000000000000000000000000000000', '1', '2018-10-25');
INSERT INTO `sys_rlb` VALUES ('1419', '5.000000000000000000000000000000', '1', '2018-10-26');
INSERT INTO `sys_rlb` VALUES ('1420', '6.000000000000000000000000000000', '2', '2018-10-27');
INSERT INTO `sys_rlb` VALUES ('1421', '7.000000000000000000000000000000', '2', '2018-10-28');
INSERT INTO `sys_rlb` VALUES ('1422', '1.000000000000000000000000000000', '1', '2018-10-29');
INSERT INTO `sys_rlb` VALUES ('1423', '2.000000000000000000000000000000', '1', '2018-10-30');
INSERT INTO `sys_rlb` VALUES ('1424', '3.000000000000000000000000000000', '1', '2018-10-31');
INSERT INTO `sys_rlb` VALUES ('1425', '4.000000000000000000000000000000', '1', '2018-11-01');
INSERT INTO `sys_rlb` VALUES ('1426', '5.000000000000000000000000000000', '1', '2018-11-02');
INSERT INTO `sys_rlb` VALUES ('1427', '6.000000000000000000000000000000', '2', '2018-11-03');
INSERT INTO `sys_rlb` VALUES ('1428', '7.000000000000000000000000000000', '2', '2018-11-04');
INSERT INTO `sys_rlb` VALUES ('1429', '1.000000000000000000000000000000', '1', '2018-11-05');
INSERT INTO `sys_rlb` VALUES ('1430', '2.000000000000000000000000000000', '1', '2018-11-06');
INSERT INTO `sys_rlb` VALUES ('1431', '3.000000000000000000000000000000', '1', '2018-11-07');
INSERT INTO `sys_rlb` VALUES ('1432', '4.000000000000000000000000000000', '1', '2018-11-08');
INSERT INTO `sys_rlb` VALUES ('1433', '5.000000000000000000000000000000', '1', '2018-11-09');
INSERT INTO `sys_rlb` VALUES ('1434', '6.000000000000000000000000000000', '2', '2018-11-10');
INSERT INTO `sys_rlb` VALUES ('1435', '7.000000000000000000000000000000', '2', '2018-11-11');
INSERT INTO `sys_rlb` VALUES ('1436', '1.000000000000000000000000000000', '1', '2018-11-12');
INSERT INTO `sys_rlb` VALUES ('1437', '2.000000000000000000000000000000', '1', '2018-11-13');
INSERT INTO `sys_rlb` VALUES ('1438', '3.000000000000000000000000000000', '1', '2018-11-14');
INSERT INTO `sys_rlb` VALUES ('1439', '4.000000000000000000000000000000', '1', '2018-11-15');
INSERT INTO `sys_rlb` VALUES ('1440', '5.000000000000000000000000000000', '1', '2018-11-16');
INSERT INTO `sys_rlb` VALUES ('1441', '6.000000000000000000000000000000', '2', '2018-11-17');
INSERT INTO `sys_rlb` VALUES ('1442', '7.000000000000000000000000000000', '2', '2018-11-18');
INSERT INTO `sys_rlb` VALUES ('1443', '1.000000000000000000000000000000', '1', '2018-11-19');
INSERT INTO `sys_rlb` VALUES ('1444', '2.000000000000000000000000000000', '1', '2018-11-20');
INSERT INTO `sys_rlb` VALUES ('1445', '3.000000000000000000000000000000', '1', '2018-11-21');
INSERT INTO `sys_rlb` VALUES ('1446', '4.000000000000000000000000000000', '1', '2018-11-22');
INSERT INTO `sys_rlb` VALUES ('1447', '5.000000000000000000000000000000', '1', '2018-11-23');
INSERT INTO `sys_rlb` VALUES ('1448', '6.000000000000000000000000000000', '2', '2018-11-24');
INSERT INTO `sys_rlb` VALUES ('1449', '7.000000000000000000000000000000', '2', '2018-11-25');
INSERT INTO `sys_rlb` VALUES ('1450', '1.000000000000000000000000000000', '1', '2018-11-26');
INSERT INTO `sys_rlb` VALUES ('1451', '2.000000000000000000000000000000', '1', '2018-11-27');
INSERT INTO `sys_rlb` VALUES ('1452', '3.000000000000000000000000000000', '1', '2018-11-28');
INSERT INTO `sys_rlb` VALUES ('1453', '4.000000000000000000000000000000', '1', '2018-11-29');
INSERT INTO `sys_rlb` VALUES ('1454', '5.000000000000000000000000000000', '1', '2018-11-30');
INSERT INTO `sys_rlb` VALUES ('1455', '6.000000000000000000000000000000', '2', '2018-12-01');
INSERT INTO `sys_rlb` VALUES ('1456', '7.000000000000000000000000000000', '2', '2018-12-02');
INSERT INTO `sys_rlb` VALUES ('1457', '1.000000000000000000000000000000', '1', '2018-12-03');
INSERT INTO `sys_rlb` VALUES ('1458', '2.000000000000000000000000000000', '1', '2018-12-04');
INSERT INTO `sys_rlb` VALUES ('1459', '3.000000000000000000000000000000', '1', '2018-12-05');
INSERT INTO `sys_rlb` VALUES ('1460', '4.000000000000000000000000000000', '1', '2018-12-06');
INSERT INTO `sys_rlb` VALUES ('1461', '5.000000000000000000000000000000', '1', '2018-12-07');
INSERT INTO `sys_rlb` VALUES ('1462', '6.000000000000000000000000000000', '2', '2018-12-08');
INSERT INTO `sys_rlb` VALUES ('1463', '7.000000000000000000000000000000', '2', '2018-12-09');
INSERT INTO `sys_rlb` VALUES ('1464', '1.000000000000000000000000000000', '1', '2018-12-10');
INSERT INTO `sys_rlb` VALUES ('1465', '2.000000000000000000000000000000', '1', '2018-12-11');
INSERT INTO `sys_rlb` VALUES ('1466', '3.000000000000000000000000000000', '1', '2018-12-12');
INSERT INTO `sys_rlb` VALUES ('1467', '4.000000000000000000000000000000', '1', '2018-12-13');
INSERT INTO `sys_rlb` VALUES ('1468', '5.000000000000000000000000000000', '1', '2018-12-14');
INSERT INTO `sys_rlb` VALUES ('1469', '6.000000000000000000000000000000', '2', '2018-12-15');
INSERT INTO `sys_rlb` VALUES ('1470', '7.000000000000000000000000000000', '2', '2018-12-16');
INSERT INTO `sys_rlb` VALUES ('1471', '1.000000000000000000000000000000', '1', '2018-12-17');
INSERT INTO `sys_rlb` VALUES ('1472', '2.000000000000000000000000000000', '1', '2018-12-18');
INSERT INTO `sys_rlb` VALUES ('1473', '3.000000000000000000000000000000', '1', '2018-12-19');
INSERT INTO `sys_rlb` VALUES ('1474', '4.000000000000000000000000000000', '1', '2018-12-20');
INSERT INTO `sys_rlb` VALUES ('1475', '5.000000000000000000000000000000', '1', '2018-12-21');
INSERT INTO `sys_rlb` VALUES ('1476', '6.000000000000000000000000000000', '2', '2018-12-22');
INSERT INTO `sys_rlb` VALUES ('1477', '7.000000000000000000000000000000', '2', '2018-12-23');
INSERT INTO `sys_rlb` VALUES ('1478', '1.000000000000000000000000000000', '1', '2018-12-24');
INSERT INTO `sys_rlb` VALUES ('1479', '2.000000000000000000000000000000', '1', '2018-12-25');
INSERT INTO `sys_rlb` VALUES ('1480', '3.000000000000000000000000000000', '1', '2018-12-26');
INSERT INTO `sys_rlb` VALUES ('1481', '4.000000000000000000000000000000', '1', '2018-12-27');
INSERT INTO `sys_rlb` VALUES ('1482', '5.000000000000000000000000000000', '1', '2018-12-28');
INSERT INTO `sys_rlb` VALUES ('1483', '6.000000000000000000000000000000', '2', '2018-12-29');
INSERT INTO `sys_rlb` VALUES ('1484', '7.000000000000000000000000000000', '2', '2018-12-30');
INSERT INTO `sys_rlb` VALUES ('1485', '1.000000000000000000000000000000', '1', '2018-12-31');

-- ----------------------------
-- Table structure for sys_wxyh
-- ----------------------------

CREATE TABLE `sys_wxyh` (
  `OPENID` varchar(32) NOT NULL COMMENT 'OPENID',
  `XM` varchar(32) DEFAULT NULL COMMENT '姓名',
  `DH` varchar(16) DEFAULT NULL COMMENT '电话',
  `YHLX` char(2) DEFAULT NULL COMMENT '用户类型',
  `XY` varchar(16) DEFAULT NULL COMMENT '学院',
  `ZJHM` varchar(16) DEFAULT NULL COMMENT '证件号码',
  `ZW` varchar(16) DEFAULT NULL COMMENT '职务',
  `CJSJ` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `CJR` varchar(64) DEFAULT NULL COMMENT '创建人',
  `XGSJ` datetime(6) DEFAULT NULL COMMENT '修改时间',
  `XGR` varchar(64) DEFAULT NULL COMMENT '修改人',
  `JGDM` varchar(32) DEFAULT NULL,
  `JGMC` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`OPENID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_xxts
-- ----------------------------

CREATE TABLE `sys_xxts` (
  `XX_ID` varchar(32) NOT NULL COMMENT '消息id',
  `XXNR` text COMMENT '消息内容',
  `CJSJ` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `CJR` varchar(64) DEFAULT NULL COMMENT '创建人',
  `XGSJ` datetime(6) DEFAULT NULL COMMENT '修改时间',
  `XGR` varchar(64) DEFAULT NULL COMMENT '修改人',
  `KJFW` varchar(32) DEFAULT NULL COMMENT '可见范围',
  `KSSJ` datetime(6) DEFAULT NULL COMMENT '开始时间',
  `JSSJ` datetime(6) DEFAULT NULL COMMENT '结束时间',
  PRIMARY KEY (`XX_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_yh_js
-- ----------------------------

CREATE TABLE `sys_yh_js` (
  `YHJS_ID` varchar(32) NOT NULL COMMENT '用户角色关联表id',
  `YH_ID` varchar(32) DEFAULT NULL COMMENT '用户id',
  `JS_ID` varchar(32) DEFAULT NULL COMMENT '角色id',
  `CJR` varchar(64) DEFAULT NULL COMMENT '创建人',
  `CJSJ` datetime(6) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`YHJS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_yh_xx
-- ----------------------------

CREATE TABLE `sys_yh_xx` (
  `ID` varchar(32) NOT NULL COMMENT 'id',
  `YH_ID` varchar(32) DEFAULT NULL COMMENT '用户id',
  `XX_ID` varchar(32) DEFAULT NULL COMMENT '消息id',
  `CJSJ` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `ZT` char(2) DEFAULT NULL COMMENT '状态'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_yjfk
-- ----------------------------

CREATE TABLE `sys_yjfk` (
  `YJ_ID` varchar(32) NOT NULL COMMENT '意见id',
  `YH_ID` varchar(32) DEFAULT NULL COMMENT '用户id',
  `NR` text COMMENT '内容',
  `CJSJ` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `CJR` varchar(64) DEFAULT NULL COMMENT '创建人',
  `ZT` char(2) DEFAULT NULL COMMENT '状态',
  `YJLX` char(2) DEFAULT NULL COMMENT '意见类型',
  `CLJG` text COMMENT '处理结果',
  `LXRXM` varchar(32) DEFAULT NULL COMMENT '联系人姓名',
  `LXFS` varchar(32) DEFAULT NULL COMMENT '联系方式',
  `XGR` varchar(32) DEFAULT NULL COMMENT '修改人',
  `XGSJ` datetime(6) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`YJ_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_yxhdwj
-- ----------------------------

CREATE TABLE `sys_yxhdwj` (
  `ID` varchar(32) NOT NULL COMMENT 'ID',
  `HD_ID` varchar(32) DEFAULT NULL COMMENT '活动id',
  `WJLX` varchar(32) DEFAULT NULL COMMENT '文件类型',
  `WJLJ` varchar(255) DEFAULT NULL COMMENT '文件路径',
  `WLLJ` varchar(255) DEFAULT NULL COMMENT '网络路径',
  `CJSJ` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `CJR` varchar(64) DEFAULT NULL COMMENT '创建人',
  `XGSJ` datetime(6) DEFAULT NULL COMMENT '修改时间',
  `XGR` varchar(64) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_zdlm
-- ----------------------------

CREATE TABLE `sys_zdlm` (
  `LMDM` varchar(32) NOT NULL COMMENT '类目代码',
  `LMMC` varchar(32) DEFAULT NULL COMMENT '类目名称',
  `CJSJ` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `CJR` varchar(64) DEFAULT NULL COMMENT '创建人',
  `XGSJ` datetime(6) DEFAULT NULL COMMENT '修改时间',
  `XGR` varchar(64) DEFAULT NULL COMMENT '修改人',
  `QZ` double DEFAULT NULL COMMENT '权重',
  PRIMARY KEY (`LMDM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='平台字典类目表(字典类别)';

-- ----------------------------
-- Table structure for sys_zdxm
-- ----------------------------

CREATE TABLE `sys_zdxm` (
  `ZD_ID` varchar(32) NOT NULL COMMENT '字典id',
  `ZDLMDM` varchar(32) DEFAULT NULL COMMENT '字典类目代码',
  `ZDDM` varchar(32) DEFAULT NULL COMMENT '字典代码',
  `ZDMC` varchar(32) DEFAULT NULL COMMENT '字典名称',
  `QZ` double DEFAULT NULL COMMENT '权重',
  `CJSJ` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `CJR` varchar(64) DEFAULT NULL COMMENT '创建人',
  `BY1` varchar(32) DEFAULT NULL COMMENT '备用1',
  `BY2` varchar(32) DEFAULT NULL COMMENT '备用2',
  `BY3` varchar(32) DEFAULT NULL COMMENT '备用3',
  PRIMARY KEY (`ZD_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='平台字典表';
