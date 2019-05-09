// Vue.prototype.AF = comFun;
import swal from 'sweetalert2'
import iView from 'iview'
import $http from '../axios/index'
import store from '../store'

export default {
  carCard(km, cardNo, callback) {
    $http.post('/api/lccl/carInfo', {km: km, cardNo: cardNo}).then(res => {
      console.log('........3', typeof res.code);
      if (res.code == 200) {
        callback && callback(true, res)
      } else if (res.code === 501) {//code  = 501   (此卡正在训练中)    code = 505 此卡绑定车辆科目与当前选择科目不一致
        swal({
          title: "此卡正在训练中",
          type: 'error'
        })
        return
      } else if (res.code == 505) {
        swal({
          title: "此卡绑定车辆科目与当前选择科目不一致",
          type: 'error'
        })
        return
      } else {
        swal({
          title: 'bug',
          type: 'error'
        })
        return
      }
    }).catch(err => {
    })

  },
  getPrintName: () => {
    return store.state.app.printerName
  },
  Get_SERVER_Time(callback) {
    $http.post('/pub/getTimeMillis', {notShowLoading: 'true'}).then(res => {
      if (res.code == 200 && res.result) {
        callback && callback(res.result)
      }
    })
  },
  ObjToStr(a) {
    return JSON.stringify(a);
  },
  StrToObj(a) {
    return JSON.parse(a);
  },
  setItem(key, val) {
    let value = JSON.stringify(val)
    sessionStorage.setItem(key, value)
  },
  getItem(key) {
    let sessData = sessionStorage.getItem(key)
    return JSON.parse(sessData)
  },
  idPrintMess(id, callback) {
    $http.post('/api/chargemanagement/findTodayCharge', id).then((res) => {
      let OBJ = {}
      if (res.code == 200) {
        OBJ = res.page.list[0]
        if (res.page.list[0].pjbh == '') {
          this.getPrintNum('pjdy', [res.page.list[0].traineeId], num => {
            OBJ.pjbh = num
          })
        } else {
          let a = res.page.list[0].pjbh.split('-')
          OBJ = a[0] + '-' + a[1]
        }
      }
      callback && callback(OBJ)
      return OBJ
    }).catch((err) => {
    })
  },


  getPrintNum(num, ids, callback) {
    var idList = ids.join(',');
    $http.post('/api/chargemanagement/receiptNo', {num: num, ids: idList}).then((res) => {
      if (res.code == 200) {
        callback && callback(res.message)
      }else if(res.code == 500){
        callback && callback('0')
      }
    }).catch((err) => {

    })
  },
  WinPrint(v, mess, compName) {
    if (mess.length == 0) {
      swal({
        title: '请先选择要打印的单据！！！',
        type: 'warning',
        confirmButtonText: '关闭'
      })
    } else {
      v.compName = compName
    }
  },
  getRandom(val) {//取随机数
    let line = 1
    if (val && val > 1) {
      line = val
    }
    let num = ''
    for (var i = 0; i < line; i++) {
      num += Math.floor(Math.random() * 10)
    }
    return num
  },
  DX: (n) => {//金额大写转换
    if (!/^(0|[1-9]\d*)(\.\d+)?$/.test(n))
      return "数据非法";
    var unit = "仟佰拾亿仟佰拾万仟佰拾元角分", str = "";
    n += "00";
    var p = n.indexOf('.');
    if (p >= 0)
      n = n.substring(0, p) + n.substr(p + 1, 2);
    unit = unit.substr(unit.length - n.length);
    for (var i = 0; i < n.length; i++)
      str += '零壹贰叁肆伍陆柒捌玖'.charAt(n.charAt(i)) + unit.charAt(i);
    return str.replace(/零(仟|佰|拾|角)/g, "零").replace(/(零)+/g, "零").replace(/零(万|亿|元)/g, "$1").replace(/(亿)万|壹(拾)/g, "$1$2").replace(/^元零?|零分/g, "").replace(/元$/g, "元整");
  },
  Dele: (v, url, id, callback) => {
    swal({
      text: "是否删除数据?",
      type: "warning",
      showCancelButton: true,
      confirmButtonText: '确认',
      cancelButtonText: '取消'
    }).then((isConfirm) => {
      if (isConfirm.value) {
        v.$http.post(url + id).then((res) => {
          if (res.code == 200) {
            iView.Message.success(res.message);
            callback && callback()
          } else {
            iView.Message.error(res.message);
          }
        })
      } else {
        iView.Message.info('操作取消');
      }
    });
  },
  getPageHeight: () => {//获取浏览器页面高度
    var windowHeight = window.innerHeight
    return windowHeight
  },
  getTime: () => {
    var NowDate = new Date()
    const Year = NowDate.getFullYear()
    const Month = NowDate.getMonth() + 1
    const Day = NowDate.getDate()
    const Hours = NowDate.getHours()
    const Minutes = NowDate.getMinutes()
    const Seconds = NowDate.getSeconds()
    // if (Month < 10) {
    //   Month = '0' + Month
    // }
    // if (Day < 10) {
    //   Day = '0' + Day
    // }
    // function  minNum(num) {
    //   if(num<10){
    //     num = '0' + num
    //   }
    // }
    function minNum(val) {
      // console.log(val);
      if (val < 10) {
        return '0' + val
      } else {
        return val
      }
    }

    let time = Year + '-' + minNum(Month) + '-' + minNum(Day) + ' ' + minNum(Hours) + ':' + minNum(Minutes) + ':' + minNum(Seconds)
    return time
  },
  trimTime: (val) => {
    if (val) {
      var newDate = new Date(val);
    } else {
      var newDate = new Date();
    }
    var newDate = new Date(val);
    let Year = newDate.getFullYear();
    let Month = newDate.getMonth() + 1;
    let Day = newDate.getDate();
    let Hours = newDate.getHours();
    let Minutes = newDate.getMinutes();
    let Seconds = newDate.getSeconds();

    function minNum(num) {
      if (num < 10) {
        return '0' + num
      } else {
        return num
      }
    }

    // if (Month < 10) {
    //   Month = '0' + Month
    // }
    // if (Day < 10) {
    //   Day = '0' + Day
    // }
    let time = Year + '-' + minNum(Month) + '-' + minNum(Day) + ' ' + minNum(Hours) + ':' + minNum(Minutes) + ':' + minNum(Seconds)
    return time
  },
  trimDate: (val) => {
    if (val) {
      var newDate = new Date(val);
    } else {
      var newDate = new Date();
    }
    let Year = newDate.getFullYear();
    let Month = newDate.getMonth() + 1;
    let Day = newDate.getDate();
    if (Month < 10) {
      Month = '0' + Month
    }

    if (Day < 10) {
      Day = '0' + Day
    }
    let time = Year + '-' + Month + '-' + Day
    return time
  },
//  Date 转换 时间戳
  Num_Date(time) {
    return Date.parse(new Date(time))
  },
  getBrowserTyp() {
    var Typ = navigator.userAgent; //取得浏览器的userAgent字符串
    return Typ
    /*
    //判断是否Opera浏览器
    if (userAgent.indexOf("Opera") > -1) {
        return "Opera"
    };
    //判断是否Firefox浏览器
    if (userAgent.indexOf("Firefox") > -1) {
        return "FF";
    }
    //判断是否chorme浏览器
    if (userAgent.indexOf("Chrome") > -1){
		return "Chrome";
    }
    //判断是否Safari浏览器
    if (userAgent.indexOf("Safari") > -1) {
        return "Safari";
    }
    //判断是否IE11浏览器 !!
    if (userAgent.indexOf("Trident") > -1) {
        return "IE";
    }
    //判断是否IE10浏览器
    if (userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && userAgent.indexOf("Opera") == -1) {
        return "IE";
    }
    //判断是否Edge浏览器
    if (userAgent.indexOf("Trident") > -1) {
        return "Edge";
    };

    *
    * */
  },
  getYear() {
    let date = new Date();
    let year = date.getFullYear();
    return year
  },
  getMonth() {
    let date = new Date();
    let month = date.getMonth();
    return month+1
  }
}
/*
this.$nextTick(() => {
        $(document).keypress(function (event) {//keypress 单件
          console.log(event.keyCode);
          // $(document).keyup(function(event){  keyup 组合件
          if (event.keyCode === 44) {
            alert('你按下了<,');
          }else if (event.keyCode === 45) {
            alert('你按下了>.');
          }else if (event.keyCode === 47) {
            alert('你按下了?/');
          }else if (event.keyCode === 112) {
            alert('你按下了__p');
          }
        });
      })
* */
