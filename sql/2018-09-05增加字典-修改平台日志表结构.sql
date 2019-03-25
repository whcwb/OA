INSERT INTO oa.sys_zdlm (LMDM, LMMC, CJSJ, CJR, XGSJ, XGR, QZ)
VALUES ('ZDCLK1018', '电子档案类型', '2018-09-04 14:17:42.000000', '1-超级管理员', null, null, null);
-- # 0、学员签名 1、身份证正面  2、身份证反面 3、学员头像
INSERT INTO oa.sys_zdxm (ZD_ID, ZDLMDM, ZDDM, ZDMC, QZ, CJSJ, CJR, BY1, BY2, BY3)
VALUES
   ('ZDCLK10180001', 'ZDCLK1018', '0', '学员签名', null, '2018-03-19 17:12:00.000000', '1-超级管理员', null, null, null)
  ,('ZDCLK10180002', 'ZDCLK1018', '1', '身份证正面', null, '2018-03-19 17:12:00.000000', '1-超级管理员', null, null, null)
  ,('ZDCLK10180003', 'ZDCLK1018', '2', '身份证反面', null, '2018-03-19 17:12:00.000000', '1-超级管理员', null, null, null)
  ,('ZDCLK10180004', 'ZDCLK1018', '3', '学员头像', null, '2018-03-19 17:12:00.000000', '1-超级管理员', null, null, null);
commit ;

ALTER TABLE elec_archives_manage MODIFY type varchar(4) COMMENT '类型  0、学员签名 1、身份证正面  2、身份证反面 3、学员头像 ';

ALTER TABLE sys_ptrz MODIFY CS longtext COMMENT '参数';
ALTER TABLE sys_ptrz MODIFY JG longtext COMMENT '结果';

-- 47.98.39.45 库已经同步