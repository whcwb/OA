import {
  filterMenuByPermission,
  getBreadCrumbList,
  getHomeRoute,
  getMenuByRouter,
  getTagNavListFromLocalstorage,
  setTagNavListInLocalstorage
} from '@/libs/util'
import routers from '@/router/routers'
import menulist from '../menuList'
import moment from 'moment'

export default {
  state: {
    examination:[],
    dictMap :new Map(),
    breadCrumbList: [],
    tagNavList: [],
    permissionMenuList:[],
    homeRoute: getHomeRoute(routers),
    local: '',
    params:{},
    clProps:{},
    LcTime:0,
    printInnerHTML:'',
    printerName:'HP LaserJet Professional P1108'
  },
  getters: {
    menuList: (state, getters, rootState) => filterMenuByPermission(state.permissionMenuList, rootState.user.access)
  },
  mutations: {
    SetPprintInnerHTML(state,data){
      state.printInnerHTML = data
    },
    set_LcTime(state,date){
      state.LcTime =date;
    },
    Ch_LcTime(state){
      state.LcTime = moment(state.LcTime).add(1, 's').toDate().getTime();
    },
    clProps_C(state,data){
      state.clProps  = data
    },
    A_params(state,data){
      state.params[data[0]] = data[1]
    },
    Gexamination(state,data){
      state.examination  = data
    },
    AdddictMap(state,data){
      state.dictMap = data
    },
    //登录成功后，根据用户的访问权限更新展示菜单
    setPermissionMenuList (state, dataList) {
      state.permissionMenuList = dataList
      // state.permissionMenuList = menulist
    },
    setBreadCrumb (state, routeMetched) {
      state.breadCrumbList = getBreadCrumbList(routeMetched, state.homeRoute)
    },
    setTagNavList (state, list) {
      if (list) {
        state.tagNavList = [...list]
        setTagNavListInLocalstorage([...list])
      } else state.tagNavList = getTagNavListFromLocalstorage()
    },
    addTag (state, item, type = 'unshift') {
      if (state.tagNavList.findIndex(tag => tag.name === item.name) < 0) {
        if (type === 'push') state.tagNavList.push(item)
        else state.tagNavList.unshift(item)
        setTagNavListInLocalstorage([...state.tagNavList])
      }
    },
    setLocal (state, lang) {
      state.local = lang
    }
  }
}
