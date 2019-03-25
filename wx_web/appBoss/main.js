import app from './app.ui'
import '#/static/css/box.less'
let options = {
  app: app,
  beforeEach(to,from,next) {
    
    let um = localStorage.getItem('bossInfo')
    if(to.path == '/pages/login' || to.path == '/pages/Welcome'){
      next()
    }else{
       if(!um){
        console.log("没有登录")
        next({
           path:'/pages/login'
        })
      }else{
        next()
      }
    }
    console.log('去',to)
    console.log('来',from)

  }
}

ui.extend({
        getSystemMessage(){//返回系统信息
          let systemInfo = null
          ui.getSystemInfo({
            success: function (res) {
              systemInfo = res
            }
          });
          return systemInfo
        },
        // 微信js方法
        getWxJs(){
          
            // 微信js初始化 
            var script = document.createElement("script")
            script.type = "text/javascript"
            script.src="./static/utils/jweixin-1.2.0.js"
            document.body.appendChild(script)

            script.onload = function(){ // 微信js初始化 回调函数
                console.log('*****wx',wx)
                
                // 微信js初始化成功后引用 微信功能方法
                ui.getApp().wxUtil = require('./static/ajax/wechatUtil.js').default
                //ui.getApp().wxUtil.
                //获取Code 直
              /** let authCode  = null
                var urlGetOpenid =  sessionStorage.getItem("openid");//获取openid
                if(! urlGetOpenid){
                  ui.getApp().wxUtil.getCode()
                  authCode = ui.getApp().wxUtil.getQueryString("code");
                  console.log('获取code',authCode)
                }
                
                if(authCode){
                  // 获取Openid
                  ui.getApp().wxUtil.vueParent = this;
                  ui.getApp().wxUtil.getOpenid(authCode,(res)=>{
                      console.log('openid-------',res)
                      sessionStorage.setItem("openid",res);//存储openid
                      ui.getApp().wxUtil.initConfig();//执行 微信 config
                  });
                }else{
                  return 
                }
              **/
              ui.getApp().wxUtil.initConfig();//执行 微信 config
              
            }
        },
        projectType(){//获取项目类型 true 微信  false app
          return localStorage.getItem('projectType')
        },
        getUser(){//获取用户信息
          return JSON.parse(localStorage.getItem('bossInfo'))
        },
        /**getUserMess(callback){//网络数据请求 获取用户信息
          this.$http('POST',ui.getApp().apis.USERMESS,{},(res)=>{
            if(res.code==200 && res.result){
                console.log('用户信息',res)
                if(res.result.yhTx == ''){
                  res.result.yhTx ='static/img/login/logo.png'
                }
              }
              localStorage.setItem('userInfo',JSON.stringify(res.result.userInfo))
              ui.getApp().userInfo = res.result && res.result.userInfo
              callback && callback(res.result)
            }
          )
        },**/

        $http(method,url,data,succesCallback,failCallback,completeCallback){//网路数据请求
          //url=ui.getApp().Ajax.url+':'+ui.getApp().Ajax.port+url
          url=ui.getApp().Ajax.url+url
          let accessTokenStr = localStorage.getItem("bossToken")
          console.log(url)
          console.log(JSON.stringify(data))
          if(accessTokenStr != null && accessTokenStr != '' && accessTokenStr != undefined){
            
            let tokMess = JSON.parse(accessTokenStr)
            ui.getApp().Ajax.header.token = tokMess.token
            ui.getApp().Ajax.header.userId = tokMess.userId
          }
          let openid = sessionStorage.getItem("openid");
          if(openid){
            ui.getApp().Ajax.header.openid = openid
          }
          if(! (data && data.pageNum && data.pageNum !=1 )){//如果参数中带了分页参数，则不显示加载框
            ui.showLoading()
          }
          
          ui.request({
            // ui.getApp().Ajax.url+':'+ui.getApp().Ajax.port+
            url: url, 
            data: data,
            header: ui.getApp().Ajax.header,
            method:method,
            success: function (res) {
              console.log('请求成功')
              if(res.data.code == 999 || res.data.code ==404){
                ui.showToast({ title: res.data.message})
                setTimeout(() => {
                  ui.redirectTo({
                    url: '/pages/login' 
                  })
                }, 2000);
                
              }else if(res.data.code ==200){
                succesCallback && succesCallback(res.data);
              }else{
                ui.showToast({ title: res.data.message})
              }
            },
            fail:function(err){
              /** ui.redirectTo({
                url: '/pages/dialogs/loginInterface'
              })
              console.log(JSON.stringify(err))
              **/
            if(! failCallback){
              ui.showToast({ title:"连接超时"})
            }
              failCallback && failCallback(err);
            },
            complete:function(mess){
              ui.hideLoading()
              completeCallback && completeCallback(mess);
            }
          })
        },
        getDateTime(){
          var NowDate = new Date
          let Year = NowDate.getFullYear()
          let Month = NowDate.getMonth()+1
          let Day = NowDate.getDate()
          let Hours = NowDate.getHours()
          let Minutes = NowDate.getMinutes()
          let Seconds = NowDate.getSeconds()
          let time = Year+'-'+Month+'-'+Day+' '+Hours+':'+Minutes+':'+Seconds
          return time
        },



      sayHello(){
        console.log('全局方法参数',ui.getApp().Ajax)
      },
      goBack(){
        ui.navigateBack()
      },
      pageHeight(val){
        return  ui.DEFAULT_CONTENT_HEIGHT + val
                // default content height
      },
      getdateStr(Y){
        var NowDate = new Date
        let Year = NowDate.getFullYear()+Y
        let Month = NowDate.getMonth()+1
        let Day = NowDate.getDate()
        let Hours = NowDate.getHours()
        let Minutes = NowDate.getMinutes()
        let Seconds = NowDate.getSeconds()
        // +Seconds+'秒'
        let time = Year+'年'+Month+'月'+Day+'日'+Hours+'时'+Minutes+'分'
        return time
      },
      fanyiAppZd(lmdm,zddm,split){//数据字典翻译最全    lmdm:字典顶级编码例如：ZDCLK1009  zddm：字典代码  split 代表分割字符串（有些代码是类似01,02存储）
        let returnName = ''//返回的值
        let zdArray = []
        let localZd = localStorage.getItem("appZd")
        if(lmdm != null && zddm != "" && zddm != null && zddm != ""){
          if(localZd){//如果字典缓存存在
            localZd = JSON.parse(localZd)
            try {
              zdArray = localZd[lmdm].zdxmList
              if(split){//如果分割字符串存在
                  let zddmArray = zddmArray.split(split)
                  zddmArray.forEach((value,index) => {
                    for(var i = 0;i<zdArray.length;i++){
                      if(value == zdArray[i].zddm){
                         
                        if(index != zdArray.length-1){
                          returnName += (zdArray[i].zdmc+split)
                        }else{
                          returnName += zdArray[i].zdmc
                        }
                        break
                      }
                    }
                  });
    
              }else{
                for(var i = 0;i<zdArray.length;i++){
                  if(zddm == zdArray[i].zddm){
                    returnName = zdArray[i].zdmc
                    break
                  }
                }
              }
            }
            catch(err){
             
              returnName = zddm  
            }
            
          }
    
          return returnName
        }
      }
})

ui.start(options)