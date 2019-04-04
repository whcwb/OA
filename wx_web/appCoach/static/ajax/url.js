
let ajaxUrl = "http://mt.xxpt123.com/wx";
// let ajaxUrl = "http://192.168.123.6:8000";

let weixinRedirect = "http://mt.xxpt123.com/wx-web"
 
let imgUrl = "http://mt.xxpt123.com:81/img/"
//let ajaxUrl = "http://j221w49004.iok.la"
//测试
/**let ajaxUrl = "http://j221w49004.iok.la"
let weixinRedirect = "http://xufnpc.natappfree.cc"
*/
//测试

export default {
  url:ajaxUrl,
  wxUrl:weixinRedirect+'/coach/index.html',
  //wxUrl:weixinRedirect,//测试重定向地址
  wechat:{
    getCode:'/wechat/message/getCode',
    getOpenid:'/wechat/message/getOpenid',
    getAccessToken:'/wechat/message/getAccessToken',
    getJsApiSign:'/wechat/message/getJsApiSign',
  },
  imgUrl:imgUrl,
  port:'8081',
  LOGIN:'',
  SENDMSG:'/pub/sendSMSlogin',//发送验证码方法
  SMSLOGIN:'/pub/SMSlogin',//验证码登录方法
  TOP10:'/api/traineeinformation/top10',//查询未结业学生耗时前十 
  TRANINESSLIST:'/api/traineeinformation/traineeList',//查询学生数据，带分页
  DROPOUTRECORD:'/api/dropoutrecord/save',//提交退学申请
  TRAINEEINFOMATION:'/api/traineeinformation/',//获取单个学员信息
  GETRECORDLIST:'/api/trainingrecord/queryRecordList',//查询培训记录
  COUNTTESTINFO:'/api/traineetestinfo/query',//查询约考信息
  GETQRCODE:"/pub/getQrCode",//生成二维码
  PUBSENDMSG:'/pub/sendMsg',//发送消息
  FEEDBACKSAVEENTITY:'/api/feedBack/saveEntity',//意见反馈
  RATEDATAIL:'/api/ratedetail/queryPager',//获取教练的评价列表
  COACHVALUATIONLIST:'/api/coachvaluation/pager',//评分列表
  GETMYRANK:'/api/coachmanagement/getMyRank',//获得我当前排名
  COMPLAINRECORDLIST:'/api/complainrecord/pager',//获取针对教练的投诉
  COACHMANAGEMENTLIST:'/api/coachmanagement/pager', //获取教练的分页列表 可以传递排序参数 orderBy:'参数1 desc'
  GETCOACHBYID:'/api/coachmanagement/',//根据教练id获取教练详细信息后面需要拼接id
  COACHMANAGEMENTPMLIST:'/api/coachmanagement/queryPager',//教练排名列表
  PICROTATIONQUERYPIC:'/api/picRotation/queryPic', //获取轮播图
  MESSAGE:'/api/wechatMessage/query',//获取消息接口
  PUBZDQUERYALL:'/pub/zd/queryAll',//获取所有数据字典，用来翻译
}
