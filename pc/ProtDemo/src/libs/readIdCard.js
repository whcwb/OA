import $ from 'jquery'

let flag = true;
let errorMsg = '';
let errCode = '';
let t = {};
let s = '';


export default {
  quartFun(fn, time){
    return function fun(){
      s = setTimeout(() => {
        fn();
        fun();
      },time);
    }
  },
  // 读取身份证方法
  readIdCard() {
    // 首先检查当前是否需要读取证件号码
    if (flag) {
      $.ajax({
        type: 'get',
        url: 'http://127.0.0.1:39999/readIDCard',
        dataType: 'json',
        beforeSend: () => {
        },
        success: (data) => {
          let res = JSON.parse(JSON.stringify(data))
          if (res.ResultCode == 0) {
            t.form.idCardNo = res.IDCardNo.trim();
            t.form.traineeName = res.Name.trim();
            t.user.idCardNo =  res.IDCardNo.trim();
            t.user.address = res.Address.trim();
            t.user.name = res.Name.trim();
            t.user.birDay = res.Born;
            t.user.gender = res.Sex+'0';
            // flag = false;
            // clearTimeout(s);
          } else if (res.ResultCode == 2) {
            // errorMsg = res.ResultInfo;
            errCode = res.ResultCode;
            flag = false;
            errorMsg = "未连接上读卡设备 , 请确保连接上设备后刷新页面"
            t.swal({
              text: errorMsg,
              type: "warning",
              confirmButtonText:'确认',
            }).then(val=>{
              clearTimeout(s);
            });
            // 异常 , 需要重新操作 , 此时需要弹窗
          }
        },
        error: (data) => {
          flag=false;
          errorMsg = '未找到监听服务,请启动服务后刷新页面';
          clearTimeout(s);
          errCode = 2;
        }
      })
    }
  },
  clearReadCard(){
    clearTimeout(s);
  },
  startQuart(v){
    t=v;
    flag = true;
    this.quartFun(this.readIdCard, 2000)();
  },
  readCardByHand(v){
    $.ajax({
      type: 'get',
      url: 'http://127.0.0.1:39999/readIDCard',
      dataType: 'json',
      beforeSend: () => {
      },
      success: (data) => {
        let res = JSON.parse(JSON.stringify(data))
        if (res.ResultCode == 0) {
          v.form.idCardNo = res.IDCardNo.trim();
          v.form.traineeName = res.Name.trim();
        } else  {
          // errorMsg = res.ResultInfo;
          // errorMsg = res.ResultInfo;
          let errorMsg = res.ResultInfo
          v.swal({
            text: errorMsg,
            type: "warning",
            confirmButtonText: '确认',
          }).then(val => {
          });
          // 异常 , 需要重新操作 , 此时需要弹窗
        }
      },
      error: (data) => {
      }
    })
  }
}
