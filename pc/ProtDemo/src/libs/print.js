import $http from '@/axios/index'
import swal from 'sweetalert2';
import $ from 'jquery'
var obj = {}

obj.print = (id,item,time,callback) => {
  var printJson = [
    {'x': 10, 'y': 40, 'w': 250, 'h': 40, 'isbar': 'F', 'border': 1, 'text': '1', 'font_size': 12},
    {'x': 10, 'y': 70, 'w': 250, 'h': 80, 'isbar': 'T', 'border': 1, 'text': id, 'font_size': 15},
    {'x': 10, 'y': 150, 'w': 100, 'h': 40, 'isbar': 'F', 'border': 1, 'text': '姓名', 'font_size': 12},
    {'x': 110, 'y': 150, 'w': 150, 'h': 40, 'isbar': 'F', 'border': 1, 'text': '科目二', 'font_size': 9},
    {'x': 10, 'y': 190, 'w': 100, 'h': 40, 'isbar': 'F', 'border': 1, 'text': '证件号码', 'font_size': 12},
    {'x': 110, 'y': 190, 'w': 150, 'h': 40, 'isbar': 'F', 'border': 1, 'text': '', 'font_size': 9},
    {'x': 10, 'y': 230, 'w': 100, 'h': 40, 'isbar': 'F', 'border': 1, 'text': '费用', 'font_size': 12},
    {'x': 110, 'y': 230, 'w': 150, 'h': 40, 'isbar': 'F', 'border': 1, 'text': '', 'font_size': 9},
    {'x': 10, 'y': 270, 'w': 100, 'h': 40, 'isbar': 'F', 'border': 1, 'text': '备注', 'font_size': 12},
    {'x': 110, 'y': 270, 'w': 150, 'h': 40, 'isbar': 'F', 'border': 1, 'text': '', 'font_size': 9},
    {'x': 10, 'y': 310, 'w': 100, 'h': 40, 'isbar': 'F', 'border': 0, 'text': '本票据遗失不补', 'font_size': 9},
    {'x': 125, 'y': 310, 'w': 150, 'h': 40, 'isbar': 'F', 'border': 0, 'text': time, 'font_size': 9}
  ];

  let itemIndex=0;
  printJson.map((val,index,arr)=>{
    if(index == 0) {
      val.text = item[0]
      itemIndex++;
    }
    if(index>2&&index%2!==0&&index<10){
      val.text=item[itemIndex]
      itemIndex++;
    }
  })


  $.ajax({
    type: "POST",
    url: "http://127.0.0.1:39999/print",
    data: JSON.stringify(printJson),
    contentType: "application/json",
    dataType: "json",
    beforeSend: function () { },
    success: function (data) {
      // alert("打印成功="+data);
    },
    error:function(data){
      // alert("服务未启动，请先启动监听服务");
    }
  });

}

export default obj

