<!--
报名收费 打印清单
-->
<style lang="less">
#textarr{
  textarea{
    border: none;
    outline: none;
  }
}
#printDivSigUp {
  font-family: "黑体";
  color: #000000!important;
  font-weight: 500;
  text-align: center;
  padding: 0 0 16px 0;
  .ptintPager {
    width: 220mm;
    margin: auto;
  }
  .titTop {
    text-align: center;
    font-size: 24px;
  }
  .bodyMess {
    overflow: hidden;
  }
  .leftTitSize {
    font-size: 22px;
    padding: 25px 8px 0 8px;
    float: left;
    height: 70mm;
    width: 15mm;
    .LiftTitItem {
      height: 13mm;
      line-height: 13mm;
    }
  }
  .pageBox {
    float: left;
    width: 200mm;
    /**/
    .titMess {
      font-size: 16px;
      height: 10mm;
      line-height: 10mm;
    }
    .boxmess {
      border: solid 1px #000;
      padding-right: 8px;
      min-height: 60mm;
      .messList {
        overflow: hidden;
        line-height: 10mm;
        .messTit {
          font-size: 20px;
          width: 35mm;
          text-align: right;
          float: left;
        }
        .ItemMess {
          float: right;
          min-height: 9mm;
          clear: right;
          width: 160mm;
          border-bottom: solid 1px #000;
          text-align: left;
          font-size: 19px;
          padding-right: 4px;
          padding-left: 6px;
          margin-bottom: 3px;
        }
        .ItemMessLine{
          float: right;
          min-height: 9mm;
          clear: right;
          width: 190mm;
          border-bottom: solid 1px #000;
          text-align: left;
          font-size: 19px;
          padding-right: 4px;
          padding-left: 6px;
          margin-bottom: 3px;
        }
        .messTit2 {
          font-size: 18px;
          width: 20mm;
          text-align: right;
          float: left;
        }
        .ItemMess2 {
          float: left;
          min-height: 10mm;
          width: 25mm;
          border-bottom: solid 1px #000;
          text-align: left;
          font-size: 18px;
        }

      }
    }
    .boxPagerNum {
      text-align: left;
      padding: 2px 0;
      font-size: 14px;
    }

  }
}
</style>
<!-- 报名收费票据打印 -->
<template>
  <div class="otherPrint">
    <Modal
      v-model="modalShow"
      width="900px"
      :closable='false'
      :mask-closable="false">
      <div slot="header" class="box_row colCenter">
        <div class="box_row_100" style="font-size: 22px;font-weight: 700;margin-left:12px">
          打印
        </div>
        <Button @click="close" style="margin: 0 12px">取消</Button>
        <Button type="success" @click="printClick" style="margin: 0 12px">打印</Button>
      </div>
      <div id="printDivSigUp" class="printBox">
        <div class="ptintPager">
          <div class="titTop">
            收 款 收 据
          </div>
          <div class="bodyMess">
            <div class="leftTitSize">
              <div class="LiftTitItem" >
              </div>
            </div>

            <div class="pageBox">
              <div class="titMess" style="text-align: left">

                <!--{{printMess[0].chargeRecord.chargeTime}}-->
                <div style="float: right">
                  {{time.split('-')[0] + ' 年 ' + time.split('-')[1] + ' 月 ' + time.split('-')[2] + ' 日'}}
                </div>

              </div>
              <!--box-->
              <div class="boxmess">

                <div class="messList">
                  <div class="messTit">
                    今收到
                  </div>
                  <div class="ItemMess">
                    {{'　　' +nameList + '　　' + idCardList}}
                  </div>
                </div>
                <div class="messList">
                  <div class="ItemMessLine">
                  </div>
                </div>
                <div class="messList">
                  <div class="ItemMessLine">
                  </div>
                </div>
                <div class="messList">
                  <div class="ItemMessLine">
                    {{'　　　　　　　　　' + '讲解及练车'}}
                  </div>
                </div>

                <div class="messList">
                  <div class="messTit">
                    金额(大写)币
                  </div>
                  <div class="ItemMess">
                   {{'　　'}} {{money | DXO}} <span style="float: right">￥{{money+'.00'}}</span>
                  </div>
                </div>

                <div class="messList">
                  <div class="messTit2" style="width: 30mm">
                    单位盖章
                  </div>
                  <div class="messTit2">
                    会计
                  </div>
                  <div class="ItemMess2">
                  </div>
                  <div class="messTit2">
                    出纳
                  </div>
                  <div class="ItemMess2">
                  </div>
                  <div class="messTit2">
                    经手人
                  </div>
                  <div class="ItemMess2" style="clear:right;">
                    {{'　　'}}{{user.xm}}
                  </div>
                </div>
              </div>
            </div>

          </div>
        </div>
      </div>
      <div slot="footer"></div>
    </Modal>
  </div>
</template>

<script>
import swal from 'sweetalert2'
import mixin from '@/mixins'
// import Print from 'print-js'
import {Printd} from 'printd'
export default {
  name: "mess",
  mixins: [mixin],
  data() {
    return {
      bzShow:true,
      tit:[],
      jgphone: '400-133-2133',//机构电话s
      jgName:'',//机构名称
      glyxm:'',//机构管理员
      payType:'',//支付类型
      time: '',
      user: JSON.parse(sessionStorage.getItem('userInfo')),
      num: 0,
      modalShow: true,
      nameList: '',
      money: 0,
      bz: ':',//备注
      tjr:'',
      messIdList:[],
      studentIDS:[],
      chargeName:'',
      idCardList:""
    }
  },
  props: {
    printMess: Array
  },
  created() {
    this.getTime()
    this.bz = ''
    this.getMess(this.printMess,()=>{
      this.getNum()
    })
  },
  methods: {
    getTime(){
      this.$http.post('/pub/getTime',{type:'yyyy-MM-dd'}).then(res=>{
        if(res.code == 200){
          this.time = res.result
        }
      })
    },
    getNum(){
      var v = this
      for(var item of this.printMess){
        if(item.pjbh){
          let arr = item.pjbh.split('-');
          v.num = arr[0] + '-' + arr[1];
          return
        }
      }
      setTimeout(()=>{
        v.AF.getPrintNum('other',this.studentIDS,num => {
          v.num = num
          // v.num = 0
          if(v.num == 0){
            v.num = '网络异常!请点击取消按钮,重新打开获取票据号';
            swal({
              title: '网络异常!请点击取消按钮,重新打开获取票据号',
              type: 'warning',
              confirmButtonText: '确定'
            })
          }
        })
      },100)
    },
    getMess(arr,callback) {
      var v = this
      if (arr.length == 0) {
        return
      }
      if(this.chargeName == ''){
        this.chargeName = arr[0].chargeName;
      }
      if (arr[0].inOutType == '00') {
        this.tit = ['收', '款', '单', '据']
      } else {
        this.tit = ['支', '出', '单', '据']
      }
      arr.forEach((item, index) => {

        if(this.payType.length == 0){
          this.payType = v.dictUtil.getValByCode(v, 'ZDCLK1004', item.chargeType)
        }else {
          if(this.payType.indexOf(v.dictUtil.getValByCode(v, 'ZDCLK1004', item.chargeType))==-1){
            this.payType = this.payType + ';' +v.dictUtil.getValByCode(v, 'ZDCLK1004', item.chargeType)
          }
        }

        this.messIdList[index] = item.id
        this.money += item.chargeFee

        if (index == arr.length - 1) {
          this.bz += item.remark
          this.nameList = this.nameList + item.traineeName
          this.idCardList +=  item.idCardNo
        } else {
          this.bz += item.remark + "、"
          this.nameList = this.nameList + item.traineeName + "、"
          this.idCardList +=  item.idCardNo + "、"
        }
      })
      callback && callback()
    },
    printClick(){
      var v = this
      // this.BDNum()
      this.bzShow = false
      const cssText = `
          #printDivSigUp {
            /*font-family: "PingFang SC";*/
            font-family: "黑体";
            color: #000000 !important;
            font-weight: 500;
            text-align: center;
            padding: 0 0 16px 0;
          }
          .ptintPager {
            width: 220mm;
            margin: auto;
          }
          .titTop {
            text-align: center;
            font-size: 24px;
          }
          .bodyMess {
            overflow: hidden;
          }
          .leftTitSize {
            font-size: 22px;
            padding: 25px 8px 0 8px;
            float: left;
            height: 70mm;
            width: 15mm;
          }
          .LiftTitItem {
            height: 13mm;
            line-height: 13mm;
          }
          .pageBox {
            float: left;
            width: 200mm;
          }
          /**/
          .titMess {
            font-size: 16px;
            height: 10mm;
            line-height: 10mm;
          }
          .boxmess {
            border: solid 1px #000;
            padding-right: 8px;
            min-height: 60mm;
          }
          .messList {
            overflow: hidden;
            line-height: 10mm;
          }
          .messTit {
            font-size: 20px;
            width: 35mm;
            text-align: right;
            float: left;
          }
          .ItemMess {
            float: left;
            clear: right;
            min-height: 9mm;
            width: 150mm;
            border-bottom: solid 1px #000;
            text-align: left;
            font-size: 19px;
            padding-right: 4px;
            padding-left: 6px;
            margin-bottom: 3px;
          }
          .ItemMessLine{
            float: right;
            min-height: 9mm;
            clear: right;
            width: 190mm;
            border-bottom: solid 1px #000;
            text-align: left;
            font-size: 19px;
            padding-right: 4px;
            padding-left: 6px;
            margin-bottom: 3px;
          }
          .messTit2 {
            font-size: 18px;
            width: 20mm;
            text-align: right;
            float: left;
          }
          .ItemMess2 {
            float: left;
            width: 25mm;
            min-height:10mm;
            border-bottom: solid 1px #000;
            text-align: left;
            font-size: 18px;
          }
          .boxPagerNum {
            text-align: left;
            padding: 2px 0;
            font-size: 14px;
          }
        `

      const d = new Printd();
      setTimeout(()=>{
        d.print( document.getElementById('printDivSigUp'), [cssText] )
      },50)
      setTimeout(()=>{
        v.close()
      },300)
    },
    BDNum(){
      var v = this;
      var ids = this.messIdList.join(',');
      this.$http.post('/api/chargemanagement/savePjbh',{ids:ids,num:'other',pjbh:this.num}).then(res=>{
        if(!res.code == 200){
          return
        }else {
          v.$parent.getPagerList()
        }
      }).catch(err=>{
        return
      })
    },
    close() {
      this.$parent.compName = ''
    }
  }
}
</script>
