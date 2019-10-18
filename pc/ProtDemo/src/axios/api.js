// let ajaxUrl = 'http://127.0.0.1:8080';
// let ajaxUrl = 'http://192.168.0.105:8080';
// let ajaxUrl = 'http://192.168.123.12:10010';//羊
// let ajaxUrl ='http://192.168.123.69:10010'; // 陈伟
// let ajaxUrl ='http://192.168.123.6:10010';//宋
// let ajaxUrl ='http://192.168.1.188:10010';//宋
let ajaxUrl = 'http://mt.xxpt123.com:81';
export default {
  NETWORK_ERR_STR: '网络加载异常!',
  // url:'http://192.168.3.234:10010',
  // url:'http://47.98.39.45:9986/biz',
  url: ajaxUrl,
  upFile: ajaxUrl + '/upload',
  // getImgUrl: 'http://192.168.3.35:8889'+'/img/',
  getImgUrl: ajaxUrl + '/img/',
  LOGIN: {
    QUERY: 'login',
    YZ: '/pub/image/Kaptcha/',
    getUKeyPwd: '/getUKeyPwd',
    getUserLoginType: '/getUserLoginType',
  },
  USERROOT: {
    MODIFY_PSD: '/api/yh/mdfPwd'
  },
  HOME: {
    DASHBOARD: '/api/exception/dashboard',
    RUNEXCEPTION: '/api/exception/runException'
  },
  DATASTA: {
    // （统计收费或缴费的人数）   参数 chargeCode 收费代码，字典可见 0000 为报名 ， 0001 为体检,9996 （科目三初考）,9997（科目二初考）,9998（科目一初考）等   startTime 开始时间 例：2018-09-09 ，endTime 结束时间 ， 例：2018-09-09  （开始结束时间可不填，不填默认为当天时间）
    // /api/chargemanagement/countType
    //（统计报名人数） 参数  startTime 开始时间 例：2018-09-09 ，endTime 结束时间 ， 例：2018-09-09  （开始结束时间可不填，不填默认为当天时间
    BM: '/api/traineeinformation/countReg',
    //（统计科目考试人数）  参数 sub （科目一：10 ， 科目二 ：20  科目三： 30） startTime 开始时间 例：2018-09-09 ，endTime 结束时间 ， 例：2018-09-09  （开始结束时间可不填，不填默认为当天时间）
    KS: '/api/traineeinformation/countTest',
    //
    dateO: '/api/traineeinformation/countStu',
    //
    dateP: '/api/traineeinformation/countTest'

  },
  FBXXY: {    //非本校学员
    PAGER: 'api/exception/pager',
    DEAL:'/api/exception/updateException'
  },
  PJJL:{
    PAGER:'/api/chargemanagement/getPrintLog',
    ZF:'/api/chargemanagement/removePjbh'
  },
  SFX: {//收费项
    PAGER: '/api/chargeitemmanagement/pager',//inOutType 00收  10 支出
    SAVE: '/api/chargeitemmanagement/save',
    DELE: '/api/chargeitemmanagement/remove/',
    UPDATE: '/api/chargeitemmanagement/update'//GET
  },
  TGTHX: {//团购、优惠项
    PAGER: '/api/reducemanagement/pager ',//查询参数传入 group = 1 为单个优惠项  group = 2 为团购优惠项
    UPDATE: '/api/reducemanagement/update',
    DELE: '/api/reducemanagement/remove/',
    SAVEY: '/api/reducemanagement/save',//新增优惠项
    SAVET: '/api/reducemanagement/saveGroup',//新增团购项
    JLMESS: '/api/reducemanagement/getValueLabel',//职位人员 级联数据
  },
  DAK: {//档案库
    ADD: 'api/recordmanagement/save',//新增档案库
    PAGER: '/api/recordmanagement/pager',//档案库列表
    DELE: '/api/recordmanagement/remove/',//删除
    TAB: 'api/archivesrecord/getdymx'//档案柜信息
  },
  JL: {//教练
    PAGER: '/api/coachmanagement/pager',//列表
    UPDATE: '/api/coachmanagement/update',//修改
    DELE: '/api/coachmanagement/remove/',//删除
    SAVE: '/api/coachmanagement/save',//新增教员
  },
  USER: {
    QUERY: '/api/yh/pager',//用户管理
    ADD: '/api/yh/save',
    CHANGE: '/api/yh/update',
    GIVE: '/api/js/modifyUserRoles',
    DELE: '/api/yh/removeIds',
    bindUKey: '/api/yh/bindUKey',
    unBindUKey: '/api/yh/unBindUKey',
    updateLoginType: '/api/yh/updateLoginType',
  },
  CAR: {
    QUERY: '/api/car/pager',//车辆
    ADD: '/api/car/save',
    CHANGE: '/api/car/update',
    // CHANGE: '/api/carbx/updates',
    DELE: '/api/car/removeIds',
  },
  carns: {
    // QUERY:'/api/carns/getPage',
    QUERY: '/api/carns/pager',//年审管理
    ADD: '/api/carns/save',
    CHANGE: '/api/carns/update',
    DELE: '/api/carns/removeIds',
  },
  carcq: {
    cqbg: '/api/carcq/cqbg',//车辆产权内部变更
    cphbg: '/api/carcq/cphbg',// 学牌车变为非学牌车
    clbf: '/api/carcq/clbf',// 车辆报废
    clcs: '/api/carcq/clcs',// 车辆变卖
  },
  ROLE: {
    QUERY: '/api/js/pager',//角色管理
    ALL: '/api/js/getAll',//角色管理
    ADD: '/api/js/save',
    CHANGE: '/api/js/update',
    GIVE: '/api/js/modifyUserRoles',
    DELE: '/api/js/removeIds',
    MODIFY_USER_ROLES: '/api/js/modifyUserRoles',
    GET_USER_ROLES: '/api/js/getUserRoles'
  },
  FRAMEWORK: {
    QUERY: 'api/jg/pager',//机构管理
    ADD: '/api/jg/save',
    CHANGE: '/api/jg/update',
    DELE: '/api/jg/removeIds',
    GET_TREE: '/api/jg/getOrgTree',
    GET_TREE_Node: '/api/jg/getTree',
    getSubOrgList: '/api/jg/getSubOrgList',
    getCurrentUserOrgTree: '/api/jg/getCurrentUserOrgTree',
    getCurrentOrgTree: '/api/jg/getCurrentOrgTree',
    getOrgPath: '/api/jg/getOrgPath'
  },
  DICTIONARY: {
    QUERY: '/api/zd/pager',// 查询字典
    ADD: '/api/zd/save',// 新增字典
    CHANGE: '/api/zd/update',// 编辑字典
    DELE: '/api/zd/removeIds' // 删除字典
  },
  DICTIONARY_LIST: {
    QUERY: '/api/zdxm/pager',//查询字典项
    list: '/api/zdxm/query',//查询字典项
    ADD: '/api/zdxm/save',// 新增字典项
    CHANGE: '/api/zdxm/update',// 编辑字典项
    DELE: '/api/zdxm/removeIds',// 删除字典项
    GET_BY_CONDITION: '/api/zdxm/getCondition', // 删除字典
  },
  FUNCTION: {
    QUERY: 'api/gn/pager',//功能管理
    ADD: '/api/gn/save',
    CHANGE: '/api/gn/update',
    DELE: '/api/gn/removeIds',
    GET_ORG_PERMISSION_TREE: '/api/gn/getOrgPermissionTree',
    GET_ALL_PERMISSION_TREE: '/api/gn/getAllPermissionTree',
    GET_ROLE_PERMISSION_TREE: '/api/gn/getRolePermissionTree',
    GET_USER_PERMISSION_TREE: '/api/gn/getUserPermissionTree',
    getPermissionTreeWithChecked: '/api/gn/getPermissionTreeWithChecked',
    SET_ROLE_FUNCTIONS: '/api/gn/setRoleFunctions',
    SET_ORG_FUNCTIONS: '/api/gn/setOrgFunctions',
    GET_ORG_FUNCTIONS: '/api/gn/getOrgFunctions',
    GET_ROLE_FUNCTIONS: '/api/gn/getRoleFunctions',
  },
  DAILY: {
    QUERY: 'api/rz/pager',//日志管理
    ADD: '/api/rz/save',
    CHANGE: '/api/rz/update',
    DELE: '/api/rz/removeIds'
  },
  SUGGES: {
    QUERY: 'api/yj/pager',//意见管理
    CHANGE: '/api/yj/update',
  },
  //招生报名接口管理
  TRAINEE: {
    GETREDUCE: '/api/traineeinformation/getjgreduce',
    PAGER: '/api/traineeinformation/pager',
    QUERY: '/api/traineeinformation/getCondition',
    //基本信息审核
    AUDIT_PAGER: '/api/traineeinformation/baseAuditingPager',
    AUDIT: '/api/traineeinformation/usersh',
    //受理确认
    ACCEPT_PAGER: '/api/traineeinformation/traineeAcceptancePager',
    ACCEPT: '/api/traineeinformation/traineeAcceptanceAuditing',
    ACCEPT_FILE: '/api/traineeinformation/impAcceptanceExcel',
    //更新培训状态
    UPDATETRAINTYPE: '/api/traineeinformation/updateTrainType',
    SAVE: '/api/traineeinformation/save',
    //分配教练员
    ALLOC_COACH: '/api/coachmanagement/traineefp',
    //报名审核回退
    REVOKEINFO: '/api/traineeinformation/revokeInfo',
    XXWH: '/api/traineeinformation/remove/',
  },
  //学员优惠审核
  TRAINEEREDUCE: {
    AUDIT_PAGER: '/api/traineereduceauditing/pager',
    AUDIT: '/api/traineereduceauditing/userAuditing',
  },
  //学员约考导入
  TRAINETEST: {
    //约考导入处理
    IMPORT_DESTINE: '/api/traineetestinfo/impDestineExcel',
    //成绩导入处理
    IMPORT_RESULT: '/api/traineetestinfo/impResultExcel',
    EXPORT_URL: '/pub/exportResult/'
  },
  //档案柜入库&出库
  ARCHIVES: {
    RECORD: '/api/recordmanagement/pager',
    //档案入库
    IMPORT: '/api/archivesrecord/addition',
    //档案出库
    EXPORT: '/api/archivesrecord/ck'
  },
  //收费管理

  //收费
  //参数sign = 1 报名费待缴学员
  //    sign = 4 报名费已缴学员

  //    sign = 7 科目一待缴费学员
  //    sign = 8 科目一已缴费学员

  //    sign = 2 科目二待缴费学员
  //    sign = 5 科目二已缴费学员

  //    sign = 3 科目三待缴费学员
  //    sign = 6 科目三已缴费学员

  //    sign = 9  全部待缴费学员
  //    sign = 10 全部已缴费学员
  CMONEY: '/api/traineeinformation/pager',
  //报名收费
  BMSF: {
    //参数 traineeId 学员id，chargeType 收款方式 00现金支付 10在线支付
    OKPAY: '/api/traineeinformation/confirmSignUp',
    //撤回 参数 traineeId 学员id  撤回
    RETRACT: '/api/traineeinformation/revokeSignUp'
  },
  KSSF: {//考试缴费
    // 参数traineeId 学员id remark
    //确认
    OKPAY: '/api/traineeinformation/confirmTest',
    //撤回
    RETRACT: '/api/traineeinformation/revokeTest',
    CHECK: '/pub/paycheck/check',
  },

  TJSDF: {//体检收费
    ADD: '/api/chargemanagement/saveInspect',
    //------------------------------------
    //参数 inspect = 1 ， 查询今日收入体检费
    PAGER: '/api/chargemanagement/pager',
    // 删除体检费用
    DELE: '/api/chargemanagement/remove/'
  },
  SFSH: {//收费审核 inOutType 支付状态
    // verify 1 待审核 verify 2 今日已审核
    PAGER: '/api/chargemanagement/pager',
    // confirmCharge  chargeIds[]  对象 数组
    OK: '/api/chargemanagement/confirmCharge',
    // 撤回审核  chargeIds[] 对象数组
    RETRACT: '/api/chargemanagement/revokeCharge'
  },
  OTHER: {// 其他收费
    ADD: '/api/chargemanagement/otherCharge',
    NAME: '/api/chargeitemmanagement/getChargeName',// 获取其他收费项
  },
  EXCEL: { // excel导出
    CHARGE: '/pub/chargemanagement/exportResult',  // 收费导出
    STUDENT: '/pub/traineeinformation/exportResult' // 学员导出
  },
  COUNT: { // 统计接口
    ZHTJ: '/api/data/countAllIn',// startTim : 开始时间 endTime : 结束时间 lx :  类型 (数组) carType :  车辆类型 jgdm:  机构代码
    ZSTJ: '/api/data/getAllStudentCount',  //  招生统计 startTime   (String )  , endTime  (String) ,  jgdm  (String 可不填), lx   (String[ ] 暂时可不填)
    NSR: '/api/chargemanagement/countIn',//（年收入） 不传参数
    NZC: '/api/chargemanagement/countOut ',//（年支出） 不传参数
    DTXY: '/api/data/getStudentCount', // 查询某一天的学员数量' startTime: 开始时间 endTime: 结束时间  jgdm: 机构代码
    BMSF: '/api/data/countBranchSignUp', //  统计报名收费  lx  ： 类型数组  （10：直属队 , 20: 挂靠队 ，30 ： 承包队）startTime: 开始时间endTime： 结束时间 jgdm: 机构代码carType： 车型
  },
  CLWH: { //车辆维护
    CLADD: '/api/lccl/save', // 新增车辆
    CLXX: '/api/lccl/pager', // 车辆查询
    CLDEL: '/api/lccl/remove/', // 删除车辆
    CLGX: '/api/lccl/update' // 更新车辆
  },
  lcjl: { //练车历史
    QUERY: 'api/lcjl/pager',//功能管理
    statistics: 'api/lcjl/statistics',//功能管理
    ADD: '/api/lcjl/save',
    CHANGE: '/api/lcjl/update',
    DELE: '/api/lcjl/removeIds',
    GET: '/api/lcjl/',
    updateFdZt: '/api/lcjl/updateFdZt',
    getFdZt: '/api/lcjl/getFdZt',
    getOneLog: '/api/lcjl/getOneLog',
    jlTj: '/api/lcjl/jlTj',
    jxtj: '/api/lcjl/jxtj',
    getAllLog: '/api/lcjl/getAllLog',
  },
  zgjbxx: { //练车历史
    QUERY: 'api/zgjbxx/pager',//功能管理
    ADD: '/api/zgjbxx/save',
    CHANGE: '/api/zgjbxx/update',
    DELE: '/api/zgjbxx/removeIds',
    setaqrqd: '/api/zgjbxx/setaqrqd',
  }
}
