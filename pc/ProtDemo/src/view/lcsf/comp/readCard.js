import $http from '@/axios/index'
import swal from 'sweetalert2';

var obj = {}
obj.readCard = (callback) => {
  var LotusCardDriver = document.getElementById('LotusCardDriver')
  var nHandle = 0; //设备句柄
  var bResult = 0;
  var strTextInfo = "";
  var strDeviceNo = "";
  nHandle = LotusCardDriver.OpenDevice("", 0, 0, 0);
  if (nHandle <= 0) {
    callback && callback(false, '打开设备失败')
    alert("打开设备失败!");
    return;
  }
  // strDeviceNo = LotusCardDriver.GetDeviceNo(nHandle);
  // alert("设备号:"+strDeviceNo);
  // LotusCardDriver.Beep(nHandle,10);
  // bResult = LotusCardDriver.SetCardType(nHandle, 'A');
  // if(bResult!=1)
  // {
  //   alert("设置卡类型失败!");
  //   LotusCardDriver.CloseDevice(nHandle);
  //   return;
  // }
  bResult = LotusCardDriver.GetCardNo(nHandle, 38);
  if (bResult != 1) {
    // alert("寻卡失败!");
    LotusCardDriver.CloseDevice(nHandle);
    callback && callback(false, '请放置卡片')
    return;
  }
  // alert("卡号:"+LotusCardDriver.CardNo);
  callback && callback(true, LotusCardDriver.CardNo)

}
obj.getCardNum=(callback)=>{
  if (!!window.ActiveXObject || "ActiveXObject" in window){
  }else{
    swal({
      title: '请使用IE10以上的浏览器',
      type: 'warning',
      confirmButtonText: '关闭'
    })
    return
  }
  obj.readCard((key,res)=>{
    callback && callback(key,res)
  })
}
obj.overCar = (v,KM) => {
  if (!!window.ActiveXObject || "ActiveXObject" in window){
  }else{
    swal({
      title: '请使用IE10以上的浏览器',
      type: 'warning',
      confirmButtonText: '关闭'
    })
    return
  }
  obj.readCard((key, mess) => {
    if(!key){
      setTimeout(() => {
        console.log('v.cancekFzkp',v.cancelFzkp);
        if (v.cancelFzkp !== 'true') {
          console.log('setTimeout');
          obj.overCar(v,KM)
        }
      }, 200)
      if (v.showQfzkp){
        return;
      }
      v.showQfzkp = true;
      swal({
        title:'请先放置卡片',
        type:'error',
        confirmButtonText: '确认',
      }).then((res) => {
        if (res.value) {
          v.showQfzkp = false;
          v.cancelFzkp = 'true'
          setTimeout(() => {
            v.cancelFzkp = null
          }, 250)
          return;
        }
      })
    }else {
      v.showQfzkp = false;
      mess.km = KM ;
      $http.post('/api/lcjl/getJl', {cardNo:mess}).then(res => {
        if (res.code == 200) {
          // console.log(res.result.lcKm);
          if(res.result.lcKm !== KM){
            let k = KM === '3' ?'二' :'三';
            swal({
              title: '请到科目'+k+'还车',
              type: 'warning',
              confirmButtonText: '确定',
            })
            return
          }
          swal({
            title: '是否结束'+res.result.clBh+'号车'+'('+res.result.jlXm+')'+'训练？',
            type: 'warning',
            confirmButtonText: '还车',
            cancelButtonText: '取消',
            showCancelButton: true,
          }).then((res) => {
            if (res.value) {
              $http.post('/api/lcjl/updateJssj', {id:mess}).then(res => {
                if (res.code == 200){
                  // v.getCarList();
                  v.util.getPageData(v)
                  v.printHc(res.result)
                }else {
                  swal({
                    title:res.message,
                    type:'error'
                  })
                }
              }).catch(err => {
              })
            }
          })
        }else {
          // setTimeout(() => {
          //   console.log(v.cancelFzkp);
          //   if (v.cancelFzkp !== 'true') {
          //     console.log('setTimeout');
          //     obj.overCar(v)
          //   }
          // }, 1000)
          swal({
            title:res.message,
            type:'error'
          })
        }
      }).catch(err => {
      })
    }
  })
}
export default obj
