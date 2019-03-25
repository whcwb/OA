ALTER TABLE archives_record ADD cjr varchar(32) NULL COMMENT '创建人';
ALTER TABLE archives_record ADD cjsj varchar(20) NULL COMMENT '创建时间';

ALTER TABLE charge_institution_manage ADD cjr varchar(32) NULL COMMENT '创建人';
ALTER TABLE charge_institution_manage ADD cjsj varchar(20) NULL COMMENT '创建时间';
ALTER TABLE charge_institution_manage CHANGE institution_code jgdm varchar(32) COMMENT '机构代码';
ALTER TABLE charge_institution_manage CHANGE institution_name jgmc varchar(32) COMMENT '机构名称';

ALTER TABLE charge_item_management CHANGE institution_name jgmc varchar(32) COMMENT '机构名称';
ALTER TABLE charge_item_management CHANGE creater cjr varchar(32) COMMENT '创建人';
ALTER TABLE charge_item_management CHANGE create_time cjsj varchar(20) COMMENT '创建时间';

ALTER TABLE charge_management ADD cjr varchar(32) NULL COMMENT '创建人';
ALTER TABLE charge_management ADD cjsj varchar(20) NULL COMMENT '创建时间';

ALTER TABLE coach_management ADD cjr varchar(32) NULL COMMENT '创建人';
ALTER TABLE coach_management ADD cjsj varchar(20) NULL COMMENT '创建时间';
ALTER TABLE coach_management CHANGE institution_code jgdm varchar(32) COMMENT '机构代码';
ALTER TABLE coach_management CHANGE institution_name jgmc varchar(20) COMMENT '机构名称';

ALTER TABLE coach_trainee_rercord ADD cjr varchar(32) NULL COMMENT '创建人';
ALTER TABLE coach_trainee_rercord ADD cjsj varchar(20) NULL COMMENT '创建时间';

ALTER TABLE coach_valuation ADD cjr varchar(32) NULL COMMENT '创建人';
ALTER TABLE coach_valuation ADD cjsj varchar(20) NULL COMMENT '创建时间';

ALTER TABLE complain_record ADD cjr varchar(32) NULL COMMENT '创建人';
ALTER TABLE complain_record ADD cjsj varchar(20) NULL COMMENT '创建时间';

ALTER TABLE dropout_record CHANGE creator cjr varchar(64) COMMENT '创建人';
ALTER TABLE dropout_record CHANGE create_time cjsj varchar(20) COMMENT '创建时间';

ALTER TABLE elec_archives_manage ADD cjr varchar(32) NULL COMMENT '创建人';
ALTER TABLE elec_archives_manage ADD cjsj varchar(20) NULL COMMENT '创建时间';

ALTER TABLE group_buy_record ADD cjr varchar(32) NULL COMMENT '创建人';
ALTER TABLE group_buy_record ADD cjsj varchar(20) NULL COMMENT '创建时间';

ALTER TABLE record_management ADD cjr varchar(32) NULL COMMENT '创建人';
ALTER TABLE record_management ADD cjsj varchar(20) NULL COMMENT '创建时间';

ALTER TABLE reduce_management CHANGE creater cjr varchar(32) COMMENT '创建人';
ALTER TABLE reduce_management CHANGE create_time cjsj varchar(20) COMMENT '创建时间';
ALTER TABLE reduce_management CHANGE jgmc jgmc varchar(20) COMMENT '机构名称';
ALTER TABLE reduce_management CHANGE jgdm jgdm varchar(32) COMMENT '机构代码';

ALTER TABLE trainee_information CHANGE institution_code jgdm varchar(32) COMMENT '机构代码';
ALTER TABLE trainee_information CHANGE institution_name jgmc varchar(20) COMMENT '机构名称';
ALTER TABLE trainee_information CHANGE creator cjr varchar(20) COMMENT '创建人';
ALTER TABLE trainee_information CHANGE create_Time cjsj varchar(20) COMMENT '创建时间';

ALTER TABLE trainee_status ADD cjr varchar(32) NULL COMMENT '创建人';
ALTER TABLE trainee_status ADD cjsj varchar(20) NULL COMMENT '创建时间';

ALTER TABLE trainee_test_info ADD cjr varchar(32) NULL COMMENT '创建人';
ALTER TABLE trainee_test_info ADD cjsj varchar(20) NULL COMMENT '创建时间';

ALTER TABLE training_record ADD cjr varchar(32) NULL COMMENT '创建人';
ALTER TABLE training_record ADD cjsj varchar(20) NULL COMMENT '创建时间';

ALTER TABLE reduce_management ADD operater varchar(64) NULL COMMENT '操作人';
ALTER TABLE reduce_management ADD operate_time varchar(20) NULL COMMENT '操作时间';

ALTER TABLE coach_management ADD operater varchar(64) NULL COMMENT '操作人';
ALTER TABLE coach_management ADD operate_time varchar(20) NULL COMMENT '操作时间';

ALTER TABLE charge_item_management ADD operater varchar(32) NULL COMMENT '操作人';
ALTER TABLE charge_item_management ADD operate_time varchar(20) NULL COMMENT '操作时间';

ALTER TABLE reduce_management CHANGE institution_name jgmc varchar(20) COMMENT '机构名称';
ALTER TABLE reduce_management CHANGE institution_code jgdm varchar(32) COMMENT '机构代码';