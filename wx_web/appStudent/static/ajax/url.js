let ajaxUrl = "http://mt.xxpt123.com/wx";
//let ajaxUrl = "http://j221w49004.iok.la"
let weixinRedirect = "http://mt.xxpt123.com/wx-web"
//let weixinRedirect = "http://7gtd7e.natappfree.cc"
let imgUrl = "http://116.62.220.91:81/img/"
export default {
  url:ajaxUrl,
  wxUrl:weixinRedirect+'/student/index.html',
  //wxUrl:weixinRedirect,
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
  TRAINEETESTINFO:'/api/traineetestinfo/query',//查询我各个科目的考试记录
  MYCOACH:'/api/traineeinformation/myCoach',//查询我的教练基本信息
  TRAININAGRECORD:'/api/trainingrecord/query',//查询我的培训信息
  RATEDETAILSAVE:'/api/ratedetail/saveEntity',//保存教练评价
  COMPLAINRECORDSAVE:'/api/complainrecord/saveEntity',//提交对教练的投诉
  //RATEDATAIL:'/api/ratedetail/pager',//获取教练的评价列表Old
  RATEDATAIL:'/api/ratedetail/queryPager',//获取教练的评价列表
  QUERYRECORDLIST:'/api/trainingrecord/queryRecordList',//获取我的每个科目培训记录
  TRAINNINGRECORDUPDATE:'/api/trainingrecord/upDate',//确认培训结束
  COACHMANAGEMENT :'/api/coachmanagement/pager_V2',//首页获取教练信息
  EAUALSENCEURL:'/pub/equalSenceUrl',//校验扫一扫签到结果
  PUBGETPIC:'/pub/getPic',//获取上传图片，服务器存储路径
  FEEDBACKSAVEENTITY:'/api/feedBack/saveEntity',//意见反馈
  INVITEFRIENDSAVE:'/api/invitefriend/saveEntity',//保存提交被邀请人信息
  INVITEFRIENDQUERY:'/api/invitefriend/query',//我提交的邀请
  TRAINEESTATUSLIST:'/api/traineestatus/queryList',//获取我的进程
  COMPLAINRECORDLIST:'/api/complainrecord/query',//获取我的投诉记录
  PICROTATIONQUERYPIC:'/api/picRotation/queryPic',//获取轮播图
  ZDPAGER:'/api/zd/pager',//数据字典
  MESSAGE:'/api/wechatMessage/query',//获取消息接口
  TRAINEEINFOMATION:'/api/traineeinformation/',//获取单个学员信息
  PUBZDQUERYALL:'/pub/zd/queryAll',//获取所有数据字典，用来翻译
  PUBLICUPLOADIMG:'/pub/upload'//上传头像图片
}
