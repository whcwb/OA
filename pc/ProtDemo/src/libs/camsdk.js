export let initFlag = false;
//检测是否打开摄像头
export let openCameraFlag = false;
export const tempFolder = "c:\\camtemp";

/*
 * 硬件使用前一定要先初始化SDK，同一个界面只调用一次
 */
export const initSDK = (CamSDKOCX) => {
  if (!initFlag){
    initFlag = true;
  }else{
    return;
  }

  try{
    //释放内存
    CamSDKOCX.UnInitCameraLib();
  }catch(e){

  }
  try{
    //初始化，并创建内存空间
    CamSDKOCX.InitCameraLib();
  }catch(e){

  }
  try{
    //创建文件临时存储目录
    var r = CamSDKOCX.CreateDirectory(tempFolder);
    if (r != 0){
      return r;
    }
  }catch(e){

  }
}

/*
 * 离开页面时，手动释放内存
 */
export const unInitSDK = (CamSDKOCX) => {
  initFlag = false;
  try{
    //释放读卡资源
    CamSDKOCX.UnInitIDCardRFID();
  }catch(e){

  }

  try{
    //释放内存
    CamSDKOCX.UnInitCameraLib();
  }catch(e){

  }
}

export const unInitSDKNoCard = (CamSDKOCX) => {
  initFlag = false;
  try{
    //释放内存
    CamSDKOCX.UnInitCameraLib();
  }catch(e){

  }
}

/*
 * 二代证读卡
 */
export const ReadCard = (CamSDKOCX, autoFlag) => {
  var cardInfo = {};
  try{
    //CamSDKOCX.UnInitIDCardRFID();
    //读卡器初始化
    CamSDKOCX.InitIDCardRFID();
    //二代证头像存储名称
    var tempFile = "headerFile.bmp";
    //读身份证信息。以“|”为分隔符的身份证信息。格式：姓名|性别|民族|出生日期|地址|身份证号|有效日期起|有效日期止|签发机关
    var result = CamSDKOCX.GetIDCardInfoRFID(tempFolder+"\\"+tempFile);

    if (result){
      var dataArray = result.split("|");
      cardInfo.xm = dataArray[0];
      cardInfo.xb = dataArray[1];
      cardInfo.mz = dataArray[2];
      cardInfo.csrq = dataArray[3];
      cardInfo.dz = dataArray[4];
      cardInfo.sfzmhm = dataArray[5];
      cardInfo.yxqs = dataArray[6];
      cardInfo.yxqz = dataArray[7];
      cardInfo.qfjg = dataArray[8];
    }else{
      if (!autoFlag) {
        cardInfo = null;
      }
    }
  }catch(e){}

  return cardInfo;
}

/*
 * 视频采集。用副摄像头进行采集，主摄像头用于高拍拍照
 * 主摄像头编号为：0
 * 副摄像头编号为：1
 */
export const openCamera = (CamSDKOCX, cameraIndex) => {
  var result = -1;
  if (cameraIndex == undefined){
    cameraIndex = 1;
  }
  //如果是已经打开摄像头，则需要先关闭后再打开新的摄像头
  closeCamera();

  try{
    //连接摄像头
    result = CamSDKOCX.OpenDev(cameraIndex, 0, 320, 240);
  }catch(e){
  }

  return result;
}

export const closeCamera = (CamSDKOCX) => {
  var result = -1;

  try{
    //关闭摄像头
    CamSDKOCX.CloseDev();
  }catch(e){
  }

  return result;
}
