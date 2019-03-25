// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import iView from 'iview'
import i18n from '@/locale'
import config from '@/config'
import importDirective from '@/directive'
import 'iview/dist/styles/iview.css'
import './index.less'
import '@/assets/icons/iconfont.css'
import '@/assets/css/box.less'
import '@/assets/css/tabpage.less'
import './assets/iconfont/iconfont.css'
import mixin from './mixins'
import comFun from './libs/comFun'
import col from './libs/color'
//提示插件引入
import swal from 'sweetalert2';
//util全局方法
import util from './libs/util2'
//内封装字典存储与获取
import dictUtil from './libs/dictUtil'
//网络数据请求 参数配置
import apis from './axios/api';
//网络数据请求
import http from './axios/index';
import session from './libs/session';
//自定义组件
import pagerTit from './components/header'
import NoData from './components/NoDataPage'
import newTree from './components/newTree'
//兼容IE11
import 'babel-polyfill'
// import '@/mock'
// 实际打包时应该不引入mock
// import env from '../config/env'
/* eslint-disable */
// env === 'development' ? require('@/mock') : ''

Vue.use(iView, {
  i18n: (key, value) => i18n.t(key, value),
  transfer:true,
  size:"large",
  'split-panels':true
})

Vue.config.productionTip = false
/**
 * @description 全局注册应用配置
 */
Vue.prototype.$config = config
/**
 * 注册指令
 */
importDirective(Vue)

Vue.prototype.mixin = mixin;

Vue.prototype.AF = comFun;
Vue.prototype.col = col;

Vue.prototype.swal = swal;


Vue.prototype.util = util;


Vue.prototype.dictUtil = dictUtil;


Vue.prototype.apis = apis;


Vue.prototype.$http = http;


Vue.prototype.session = session

Vue.component('pagerTit',pagerTit)
Vue.component('NoData',NoData)
Vue.component('newTree',newTree)

import searchBar from './view/components/searchBar'
Vue.component('searchBar',searchBar)
import tableArea from './view/components/tableArea'
Vue.component('tableArea',tableArea)
import formItems from './view/components/formItems'
Vue.component('formItems',formItems)


Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1, //month
        "d+": this.getDate(),    //day
        "h+": this.getHours(),   //hour
        "m+": this.getMinutes(), //minute
        "s+": this.getSeconds(), //second
        "q+": Math.floor((this.getMonth() + 3) / 3),  //quarter
        "S": this.getMilliseconds() //millisecond
    }
    if (/(y+)/.test(format)) format = format.replace(RegExp.$1,
        (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o) if (new RegExp("(" + k + ")").test(format))
        format = format.replace(RegExp.$1,
            RegExp.$1.length == 1 ? o[k] :
                ("00" + o[k]).substr(("" + o[k]).length));
    return format;
}


import table from './components/table'
Vue.component('tab',table)
/* eslint-disable no-new */
window.vueObject = new Vue({
  data:{
    card:{}
  },
  el: '#app',
  router,
  i18n,
  store,
  render: h => h(App)
})
