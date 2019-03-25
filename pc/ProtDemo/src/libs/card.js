export let initFlag = false;

/*
 * 硬件使用前一定要先初始化SDK，同一个界面只调用一次
 */
export const initSDK = (SynCardRead, cardInFun) => {

  let result = {};
  if(SynCardRead == null){
    return false;
  }
  //查看是否有读卡器
   var t = SynCardRead.FindReader();
alert(t);
   if (t > 0){
  //   //自动读卡方式下循环读卡间隔，至少要大于1000毫秒
     SynCardRead.SetloopTime(500);
  //   //设置读卡的方式，0为手动 1为自动
     SynCardRead.SetReadType(1);
  //   //绑定事件
     /*setInterval(function () {

     }, 500);*/
     SynCardRead.addEventListener("CardIn", function (state) {
       console.log(state);
       console.log(this);
     }, false);
   }else{
     return false;
   }

   return true;
}

