let ajaxUrl = "http://116.62.220.91:81";
// let ajaxUrl = "http://127.0.0.1"
//   let ajaxUrl = "http://192.168.31.228"
// let ajaxUrl = "http://192.168.31.92:10010"//宋
// let ajaxUrl = 'http://47.98.39.45:9986'

export default {
  url:ajaxUrl,
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
  }
}
