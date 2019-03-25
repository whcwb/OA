let ajaxUrl = "http://mt.xxpt123.com/wx";
//let ajaxUrl = "http://j221w49004.iok.la"

//let weixinRedirect = "http://e82p5i.natappfree.cc"
let imgUrl = "http://116.62.220.91:81/img/"
export default {
  
  /**url:ajaxUrl,
  getImgUrl: ajaxUrl+'/img/',
  LOGIN: {
    QUERY: '/login',
    YZ:'/pub/image/Kaptcha/'
  },
  //学员信息审核列表
  AUDIT_PAGER: '/api/traineeinformation/baseAuditingPager',
  //学员信息审核确认id:row.id,infoCheckStatus:'10'
  AUDIT: '/api/traineeinformation/usersh',
  YHSH:{
    PAGER:'/api/traineereduceauditing/pager',
    AUDIT: '/api/traineereduceauditing/userAuditing',
  }**/
  YWCL:{//业务处理
    YOUHUISHENHELIST:'/api/traineereduceauditing/pager',//优惠审核列表
    AUDIT: '/api/traineereduceauditing/userAuditing2',//通过优惠审核
    RECALLUSERAUDITING:'/api/traineereduceauditing/recallInfo',//优惠审核回退
    ZDPAGER:'/api/zd/pager',//数据字典
    UPDATEINFO:'/api/traineeinformation/updateinfo2',//修改用户信息
    USERINFOCHECK:"/api/traineeinformation/userInfoCheck",//报名审核
    REVOKEINFO:"/api/traineeinformation/revokeInfo",//报名审核回退
   
   
  },
  YWCX:{//业务查询
    STUDENTPAGER:'/api/traineeinformation/queryPager',//学员列表查询
    COACHMANAGEMENT :'/api/coachmanagement/pager',//获取教练信息
  },
  INDEX:{
    MESSAGE:'/api/wechatMessage/query',//获取消息接口
    STUDENTCONTBYDAY:'/api/data/studentCountByDay'
  },
  url:ajaxUrl,
  //wxUrl:weixinRedirect+'/boss/index.html',
  wxUrl:'http%3a%2f%2fmt.xxpt123.com%2fwx-web%2fboss%2findex.html',
  wechat:{
    getCode:'/wechat/message/getCode',
    getOpenid:'/wechat/message/getOpenid',
    getAccessToken:'/wechat/message/getAccessToken',
    getJsApiSign:'/wechat/message/getJsApiSign',
  },
  imgUrl:imgUrl,
  port:'8000',
  LOGIN:'',
  SENDMSG:'/pub/sendSMSlogin',//发送验证码方法
  SMSLOGIN:'/pub/SMSlogin',//验证码登录方法
  FEEDBACKSAVEENTITY:'/api/feedBack/saveEntity',//意见反馈
  COMPLAINRECORDLIST:'/api/complainrecord/pager',//投诉数据列表
  COMPLAINRECORDDEAL:'/api/complainrecord/deal',//投诉处理接口
  PUBZDQUERYALL:'/pub/zd/queryAll',//获取所有数据字典，用来翻译
 
}
