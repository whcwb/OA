import apis from "../axios/api";
import swal from 'sweetalert2'
import $ from 'jquery'

let UKey = {
    Socket_UK:null,
    keyCode : '',
    deviceId : '',
    DevicePath:'',
    ID_1:'',
    ID_2:'',
    error:false,
    success:false,
    successCallback :null,
    digitArray:new Array('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'),
    isSuccess:()=>{
      return UKey.deviceId != '' && UKey.keyCode != ''
    },
    read:(callback)=>{
      UKey.successCallback = callback
      UKey.init();
    },
    open:()=>{
        var u = document.URL;
        var url;
        if (u.substring(0, 5) == "https") {
            url = "wss://127.0.0.1:4006/xxx";
        } else {
            url = "ws://127.0.0.1:4006/xxx";
        }
        try{
          if (typeof MozWebSocket != "undefined") {
            UKey.Socket_UK = new MozWebSocket(url,"usbkey-protocol");
          } else {
            UKey.Socket_UK = new WebSocket(url,"usbkey-protocol");
          }
        }catch (e) {
          console.log(e);
        }
    },
    clear:()=>{
        UKey.success = false;
        UKey.deviceId = '';
        UKey.keyCode = '';
    },
    init:()=>{
      UKey.open();
        try {
            UKey.Socket_UK.onopen = function() {
              UKey.FindPort(0)
            }

            UKey.Socket_UK.onmessage = function got_packet(Msg) {
                var UK_Data = JSON.parse(Msg.data);
                //如果是插拨事件处理消息
                if (UK_Data.type == "PnpEvent"){
                    UKey.error = false;
                    if (UK_Data.IsIn) {
                        UKey.UKeyIn = true;
                        UKey.FindPort(0); //发送命令取UK的路径
                    } else {
                        UKey.clear();
                    }
                }else if(UK_Data.type === 'Process'){
                    switch (UK_Data.order) {
                        case 0:
                            UKey.error = false;
                            UKey.FindPort(0); //发送命令取UK的路径
                            break;
                        case 1:
                            if (UK_Data.LastError != 0) {
                                UKey.error = true;
                                swalError('未发现UKey，请先插入UKey')
                                UKey.Socket_UK.close();
                                return false;
                            }
                            UKey.error = false;
                            UKey.DevicePath = UK_Data.return_value; //获得返回的UK的路径
                            UKey.GetID_1(UKey.DevicePath); //发送命令取ID_1
                            break;
                        case 2:
                            if (UK_Data.LastError != 0) {
                                UKey.error = true;
                                swalError('返回ID号错误，错误码为：' + UK_Data.LastError.toString())
                                UKey.Socket_UK.close();
                                return false;
                            }
                            UKey.error = false;
                            UKey.ID_1 = UK_Data.return_value; //获得返回的UK的ID_1
                            UKey.GetID_2(UKey.DevicePath); //发送命令取ID_2
                            break;
                        case 3:
                            if (UK_Data.LastError != 0) {
                                UKey.error = true;
                                swalError('取得ID错误，错误码为：' + UK_Data.LastError.toString())
                                UKey.Socket_UK.close();
                                return false;
                            }
                            UKey.error = false;
                            UKey.ID_2 = UK_Data.return_value; //获得返回的UK的ID_2
                            //设备ID号
                            var deviceID = UKey.toHex(UKey.ID_1) + UKey.toHex(UKey.ID_2);
                            UKey.deviceId = deviceID.toUpperCase();
                            UKey.ContinueOrder(); //为了方便阅读，这里调用了一句继续下一行的计算的命令，因为在这个消息中没有调用我们的函数，所以要调用这个
                            break;
                        case 4:
                              UKey.YReadString(0, 74, 'FFFFFFFF','FFFFFFFF', UKey.DevicePath);
                            break;
                        case 5:
                            if (UK_Data.LastError != 0) {
                                UKey.error = true;
                                swalError('读数据时错误，错误码为：' + UK_Data.LastError.toString())
                                UKey.Socket_UK.close();
                                return false;
                            }
                            UKey.error = false;
                            UKey.keyCode = UK_Data.return_value;
                            UKey.success = true;
                            if (UKey.successCallback != null){
                                UKey.successCallback();
                            }
                            //所有工作处理完成后，关掉Socket
                            UKey.Socket_UK.close();
                            setTimeout(function(){
                                UKey.clear();
                            },5000)
                            break;
                    }
                }
            }
            UKey.Socket_UK.onclose = function() {
                console.log('Socket_UK close');
            }
        } catch(e) {
          swalError('读取数据失败，请确定服务程序是否安装')
            console.error(e);
            return false;
        }
    },
}
function swalError(s){
    swal.close();
    swal({
        text: s,
        type: 'error',
        showCancelButton: false,
        confirmButtonText:'确认',
    });
}
UKey.toHex = function(n){
    var result = ''
    var start = true;
    for (var i = 32; i > 0;) {
        i -= 4;
        var digit = (n >> i) & 0xf;

        if (!start || digit != 0) {
            start = false;
            result += UKey.digitArray[digit];
        }
    }
    return (result == '' ? '0': result);
},
UKey.FindPort = function(start)
{
    var msg =
        {
            FunName: "FindPort",
            start: start
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.FindPort_2 = function(start, in_data , verf_data)
{
    var msg =
        {
            FunName: "FindPort_2",
            start: start,
            in_data: in_data,
            verf_data:verf_data
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.FindPort_3= function(start,in_data,verf_data)
{
    var msg =
        {
            FunName: "FindPort_3",
            start: start,
            in_data: in_data,
            verf_data:verf_data
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.GetVersion= function(Path)
{
    var msg =
        {
            FunName: "GetVersion",
            Path: Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.GetVersionEx= function(Path)
{
    var msg =
        {
            FunName: "GetVersionEx",
            Path: Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.GetID_1= function(Path)
{
    var msg =
        {
            FunName: "GetID_1",
            Path: Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.GetID_2= function(Path)
{
    var msg =
        {
            FunName: "GetID_2",
            Path: Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};


UKey.sRead= function(Path)
{
    var msg =
        {
            FunName: "sRead",
            Path: Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.sWrite = function(InData, Path)
{
    var msg =
        {
            FunName: "sWrite",
            InData: InData,
            Path:Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.sWrite_2= function(InData, Path)
{
    var msg =
        {
            FunName: "sWrite_2",
            InData: InData,
            Path:Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.sWrite_2Ex= function(InData,Path)
{
    var msg =
        {
            FunName: "sWrite_2Ex",
            InData: InData,
            Path:Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.sWriteEx= function(InData,Path)
{
    var msg =
        {
            FunName: "sWriteEx",
            InData: InData,
            Path:Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.sWriteEx_New= function(InData,Path)
{
    var msg =
        {
            FunName: "sWriteEx_New",
            InData: InData,
            Path:Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.sWrite_2Ex_New= function(InData,Path)
{
    var msg =
        {
            FunName: "sWrite_2Ex_New",
            InData: InData,
            Path:Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.SetCal= function(Hkey,Lkey,new_Hkey,new_Lkey,Path)
{
    var msg =
        {
            FunName: "SetCal",
            Hkey:   Hkey,
            Lkey:   Lkey,
            new_Hkey:new_Hkey,
            new_Lkey:new_Lkey,
            Path:Path

        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};


UKey.SetBuf= function(InData,pos)
{
    var msg =
        {
            FunName: "SetBuf",
            InData: InData,
            pos:pos
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.GetBuf= function(pos)
{
    var msg =
        {
            FunName: "GetBuf",
            pos: pos
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.YRead= function(Address,HKey,LKey,Path)
{
    var msg =
        {
            FunName: "YRead",
            Address:Address,
            HKey:HKey,
            LKey:LKey,
            Path:Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.YWrite= function(InData,Address,HKey,LKey,Path)
{
    var msg =
        {
            FunName: "YWrite",
            InData:InData,
            Address:Address,
            HKey:HKey,
            LKey:LKey,
            Path:Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.YReadEx= function(Address,len, HKey,LKey,Path)
{
    var msg =
        {
            FunName: "YReadEx",
            Address:Address,
            len:len,
            HKey:HKey,
            LKey:LKey,
            Path:Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.YWriteEx= function(Address,len,HKey,LKey,Path)
{
    var msg =
        {
            FunName: "YWriteEx",
            Address:Address,
            len:len,
            HKey:HKey,
            LKey:LKey,
            Path:Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.YReadString= function(Address,len,HKey,LKey,Path)
{
    var msg =
        {
            FunName: "YReadString",
            Address:Address,
            len:len,
            HKey:HKey,
            LKey:LKey,
            Path:Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.YWriteString= function(InString,Address,HKey,LKey,Path)
{
    var msg =
        {
            FunName: "YWriteString",
            InString:InString,
            Address:Address,
            HKey:HKey,
            LKey:LKey,
            Path:Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.SetWritePassword= function(W_Hkey,W_Lkey,new_Hkey,new_Lkey,Path)
{
    var msg =
        {
            FunName: "SetWritePassword",
            W_Hkey:W_Hkey,
            W_Lkey:W_Lkey,
            new_Hkey:new_Hkey,
            new_Lkey:new_Lkey,
            Path:Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.SetReadPassword= function(W_Hkey,W_Lkey,new_Hkey,new_Lkey,Path)
{
    var msg =
        {
            FunName: "SetReadPassword",
            W_Hkey:W_Hkey,
            W_Lkey:W_Lkey,
            new_Hkey:new_Hkey,
            new_Lkey:new_Lkey,
            Path:Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};


UKey.DecString= function(InString,Key)
{
    var msg =
        {
            FunName: "DecString",
            InString:InString,
            Key:Key
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.EncString= function(InString,Path)
{
    var msg =
        {
            FunName: "EncString",
            InString:InString,
            Path:Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.EncString_New= function(InString,Path)
{
    var msg =
        {
            FunName: "EncString_New",
            InString:InString,
            Path:Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.Cal= function(Path)
{
    var msg =
        {
            FunName: "Cal",
            Path:Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.Cal_New= function(Path)
{
    var msg =
        {
            FunName: "Cal_New",
            Path:Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.SetCal_2= function(Key,Path)
{
    var msg =
        {
            FunName: "SetCal_2",
            Key:Key,
            Path:Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.SetCal_New= function(Key,Path)
{
    var msg =
        {
            FunName: "SetCal_New",
            Key:Key,
            Path:Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.SetEncBuf= function(InData,pos)
{
    var msg =
        {
            FunName: "SetEncBuf",
            InData:InData,
            pos: pos
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.GetEncBuf= function(pos)
{
    var msg =
        {
            FunName: "GetEncBuf",
            pos: pos
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};


UKey.ReSet= function(Path)
{
    var msg =
        {
            FunName: "ReSet",
            Path:Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.SetID= function(Seed,Path)
{
    var msg =
        {
            FunName: "SetID",
            Seed:Seed,
            Path:Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.GetProduceDate= function(Path)
{
    var msg =
        {
            FunName: "GetProduceDate",
            Path:Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.MacAddr= function()
{
    var msg =
        {
            FunName: "MacAddr"
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};


UKey.GetChipID= function(Path)
{
    var msg =
        {
            FunName: "GetChipID",
            Path:Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.StarGenKeyPair= function(Path)
{
    var msg =
        {
            FunName: "StarGenKeyPair",
            Path:Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.GenPubKeyY= function()
{
    var msg =
        {
            FunName: "GenPubKeyY"
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.GenPubKeyX= function()
{
    var msg =
        {
            FunName: "GenPubKeyX"
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.GenPriKey= function()
{
    var msg =
        {
            FunName: "GenPriKey"
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.GetPubKeyY= function(Path)
{
    var msg =
        {
            FunName: "GetPubKeyY",
            Path:Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.GetPubKeyX= function(Path)
{
    var msg =
        {
            FunName: "GetPubKeyX",
            Path:Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.GetSm2UserName= function(Path)
{
    var msg =
        {
            FunName: "GetSm2UserName",
            Path:Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.Set_SM2_KeyPair= function(PriKey,PubKeyX,PubKeyY,sm2UserName,Path )
{
    var msg =
        {
            FunName: "Set_SM2_KeyPair",
            PriKey:PriKey,
            PubKeyX:PubKeyX,
            PubKeyY:PubKeyY,
            sm2UserName:sm2UserName,
            Path:Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey. YtSign= function(SignMsg,Pin,Path)
{
    var msg =
        {
            FunName: "YtSign",
            SignMsg:SignMsg,
            Pin:Pin,
            Path:Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.YtSign_2= function(SignMsg,Pin,Path)
{
    var msg =
        {
            FunName: "YtSign_2",
            SignMsg:SignMsg,
            Pin:Pin,
            Path:Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.YtVerfiy= function(id,SignMsg,PubKeyX, PubKeyY,VerfiySign,Path)
{
    var msg =
        {
            FunName: "YtVerfiy",
            id:id,
            SignMsg:SignMsg,
            PubKeyX:PubKeyX,
            PubKeyY:PubKeyY,
            VerfiySign:VerfiySign,
            Path:Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.SM2_DecString= function(InString,Pin,Path)
{
    var msg =
        {
            FunName: "SM2_DecString",
            InString:InString,
            Pin:Pin,
            Path:Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.SM2_EncString= function(InString,Path)
{
    var msg =
        {
            FunName: "SM2_EncString",
            InString:InString,
            Path:Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.YtSetPin= function(OldPin,NewPin,Path)
{
    var msg =
        {
            FunName: "YtSetPin",
            OldPin:OldPin,
            NewPin:NewPin,
            Path:Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.FindU= function(start)
{
    var msg =
        {
            FunName: "FindU",
            start: start
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.FindU_2= function(start,in_data,verf_data)
{
    var msg =
        {
            FunName: "FindU_2",
            start: start,
            in_data: in_data,
            verf_data:verf_data
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.FindU_3= function(start,in_data,verf_data)
{
    var msg =
        {
            FunName: "FindU_3",
            start: start,
            in_data: in_data,
            verf_data:verf_data
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.IsUReadOnly= function(Path)
{
    var msg =
        {
            FunName: "IsUReadOnly",
            Path:Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.SetUReadOnly= function(Path)
{
    var msg =
        {
            FunName: "SetUReadOnly",
            Path:Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.SetHidOnly= function(IsHidOnly,Path)
{
    var msg =
        {
            FunName: "SetHidOnly",
            IsHidOnly:IsHidOnly,
            Path:Path
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
};

UKey.ResetOrder = function()
{
    var msg =
        {
            FunName: "ResetOrder"
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
}

UKey.ContinueOrder = function()
{
    var msg =
        {
            FunName: "ContinueOrder"
        };
    UKey.Socket_UK.send(JSON.stringify(msg));
}
export default UKey
