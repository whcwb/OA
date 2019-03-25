import axios from 'axios';
import Cookies from 'js-cookie';
import router from '../router/index';
import qs from 'qs';
import iView from 'iview'
import ajaxUrl from './api'
// 订单分配权限
 let url = ajaxUrl.url

let httpInstance = axios.create({
  baseURL: url,
  timeout: 30000,
  headers: {'Content-Type': 'application/x-www-form-urlencoded'},
  withCredentials: true
});
httpInstance.url = url;
// 添加请求拦截器 数据请求之前
httpInstance.interceptors.request.use((config) => {
  // console.log('数据拦截',config);
  if(config.method == 'get'){
    if (config.url.indexOf('?') < 0){
        config.url += '?t='+ new Date().getTime()
    }else{
        config.url += '&t='+ new Date().getTime()
    }
  }else if(config.method == 'post' && ((config.params && config.params.timers == undefined) || (config.data && config.data.timers == undefined))) {
    if(config.params){
      config.params['timers'] = new Date().getTime()
    }else if(config.data){
      config.data['timers'] = new Date().getTime()
    }
  }
    if (!(config.data && config.data.notShowLoading && config.data.notShowLoading == 'true')
    && !(config.params && config.params.notShowLoading && config.params.notShowLoading == 'true')){
    // if (true) {
    // iView.Spin.hide()
  // } else {
  //   iView.Spin.show({
  //     render: (h) => {
  //       return h('div', [
  //         h('Icon', {
  //           'class': 'demo-spin-icon-load',
  //           props: {
  //             type: 'ios-loading',
  //             size: 50
  //           }
  //         }),
  //         h('div', '数据加载中')
  //       ])
  //     }
  //   })
  }


  var headers = config.headers;
  var contentType = headers['Content-Type'];

  if (contentType == 'application/x-www-form-urlencoded') {
    config.data = qs.stringify(config.data);
    try {
      // 如果是数组对象，将转换出来的数组字符串中的[]关键字替换，这样方便后台接收数据
      config.data = config.data.replace(/(%5B\d%5D)/g, '');
    } catch (e) {

    }
  }
  // 在发送请求之前做些什么
  if (Cookies.get('accessToken')) {
    let accessToken = JSON.parse(Cookies.get('accessToken'));
    config.headers.token = accessToken.token;
    config.headers.userid = accessToken.userId;
  }
  return config;
}, function (error) {
    console.log(error);
    // 对请求错误做些什么
  return Promise.reject(error);
  // iView.Spin.hide()
});

// 添加响应拦截器 数据响应之后
httpInstance.interceptors.response.use((response) => {
  var v = this;
  // 对响应数据做点什么
  // store.commit('CloadingType', false);
  // iView.Spin.hide()
  if (response.status === 404) {
    router.push({name: 'error-404'});
  }
  if (response.status === 200) {
    if(response.data.code === 999){
      router.push({name: 'login'})
      iView.Message.info(response.data.message+',请重新登录');
      return
    }
    return response.data;
    // if (response.data.code===403){
    //     router.push({name: 'erro' +
    //         'r-403'})
    // }else{
    //     return response.data;
    // }
  } else {
    router.push({name: 'error-500'});
  }
  // if(response.status===200&&response.data.code===200){
  // 	return response.data;
  // }else if(!Cookies.get('result')||response.status===500){
  // 	router.push({name: 'error-500'})
  // }else if(Cookies.get('result')&&response.status===500){
  // 	router.push({name: 'errorpage_500'})
  // }else if(response.status===200&&response.data.code===403){
  // 	router.push({name: 'error-403'})
  // }else if(response.status===200&&response.data.code===500){
  // 	router.push({name: 'errorpage_500'})
  // }
}, function (error) {
  // iView.Spin.hide()
  // 对响应错误做点什么
  // if (!Cookies.get('result')) {
  // 	router.push({name: 'error-500'});
  // } else if (Cookies.get('result')) {
  // 	router.push({name: 'errorpage_500'});
  // }
  return Promise.reject(error);
});
export default httpInstance;
