import Vue from 'vue'
import Router from 'vue-router'
import routes from './routers'
import iView from 'iview'
import {canTurnTo, getToken} from '@/libs/util'
import AF from '../libs/comFun'

Vue.use(Router)
const router = new Router({
  routes,
  mode: 'history'
})
const LOGIN_PAGE_NAME = 'login'

router.beforeEach((to, from, next) => {
  // console.log('___+__+_',to);
  if(to.name == 'Student-acceptance'){
    to.params.id = AF.getRandom(6)
  }
  try{
    window.vueObject.$data.card = {};
  }catch(e){}

  // iView.LoadingBar.start()
  const token = getToken()
  // next()
  if (!token && to.name !== LOGIN_PAGE_NAME) {
    // 未登录且要跳转的页面不是登录页
    next({
      name: LOGIN_PAGE_NAME // 跳转到登录页
    })
    iView.Message.info('请重新登录');
  } else if (to.name === LOGIN_PAGE_NAME || token) {
    // 未登陆且要跳转的页面是登录页
    next() // 跳转
  }
})

router.afterEach(to => {
  // iView.LoadingBar.finish()
  window.scrollTo(0, 0)
})

export default router
