import dictUtil from '../libs/dictUtil'

export default {
  created() {
//		alert("公用方法")
    this.rowClassName
  },
  filters: {
    gender: (val) => {
      return dictUtil.getValByCode(this, 'ZDCLK0017', val)
    },
    area:(val)=>{
      return dictUtil.getValByCode(this, 'ZDCLK1009', val)
    },
    coachSub:(val)=>{
      if(val == '02'){
        return '科目二'
      }else if(val == '03'){
        return '科目三'
      }else if(val == '02,03' || val == '03,02' ){
        return '科目二,科目三'
      }
    },
    DX: (n) => {
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
  },
  methods: {
    getdate() {
      var getDate = new Date
      return getDate
    },
    getdateStr() {
      var NowDate = new Date
      let Year = NowDate.getFullYear()
      let Month = NowDate.getMonth() + 1
      let Day = NowDate.getDate()
      let Hours = NowDate.getHours()
      let Minutes = NowDate.getMinutes()
      let Seconds = NowDate.getSeconds()
      let time = Year + '-' + Month + '-' + Day + ' ' + Hours + ':' + Minutes + ':' + Seconds
      return time
    },
    getdateStrD() {
      var NowDate = new Date
      let Year = NowDate.getFullYear()
      let Month = NowDate.getMonth() + 1
      let Day = NowDate.getDate()
      let time = Year + '-' + Month + '-' + Day
      return time
    },
    getdatePara(val) {//时间转换
      var newDate = new Date();
      newDate.setTime(val)
      log(typeof newDate);
      let Year = val.getFullYear();
      let Month = val.getMonth() + 1;
      let Day = val.getDate();
      let Hours = val.getHours();
      let Minutes = val.getMinutes();
      let Seconds = val.getSeconds();
      if (Month < 10) {
        Month = '0' + Month
      }
      if (Day < 10) {
        Day = '0' + Day
      }
      let time = Year + '-' + Month + '-' + Day + ' ' + Hours + ':' + Minutes + ':' + Seconds
      return time
    },
    getdateParaD(val) {//时间转换
      if (val == null || val == "") {
        return ''
      }
      var newDate = new Date();
      newDate.setTime(val)
      log(typeof newDate);
      let Year = val.getFullYear()
      let Month = val.getMonth() + 1
      let Day = val.getDate()
      if (Month < 10) {
        Month = '0' + Month
      }
      if (Day < 10) {
        Day = '0' + Day
      }
      let time = Year + '-' + Month + '-' + Day
      return time
    },
    getDJC(val) {
      var newDate = new Date();
      newDate.setTime(val);
      log(typeof newDate);
      return this.getdatePara(newDate);
    },
    rowClassName(row, index) {
      // if (index%2 === 0) {
      //     return 'demo-table-info-row';
      // }
      return '';
    },
    //根据浏览器高度，设置document高度
    getWindowHeight() {
      var windowHeight = window.innerHeight
      return windowHeight
    }
  }
}
