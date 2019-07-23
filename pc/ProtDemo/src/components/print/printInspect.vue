<!--
体检收费 打印清单
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
      width: 200mm;
      /**/
      margin: auto;
    }
    .titTop {
      text-align: center;
      font-size: 22px;
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
      width: 180mm;
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
            font-size: 16px;
            width: 30mm;
            text-align: right;
            float: left;
          }
          .ItemMess {
            float: left;
            clear: right;
            width: 140mm;
            border-bottom: solid 1px #000;
            text-align: left;
            font-size: 15px;
            padding-right: 4px;
            padding-left: 6px;
            margin-bottom: 3px;
          }
          .messTit2 {
            font-size: 16px;
            width: 20mm;
            text-align: right;
            float: left;
          }
          .ItemMess2 {
            float: left;
            width: 25mm;
            border-bottom: solid 1px #000;
            text-align: left;
            font-size: 16px;
          }
        }
      }
      .boxPagerNum {
        text-align: left;
        padding: 2px 0;
        font-size: 12px;
      }

    }
  }
</style>
<!-- 体检收费票据打印 -->
<template>
  <div class="signUpPrint">
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
            武汉新农体检中心
          </div>
          <div class="bodyMess">

            <div class="leftTitSize">
              <div class="LiftTitItem" v-for="item in ['收','款','单','据']">
                {{item}}
              </div>
            </div>

            <div class="pageBox">
              <!--tit-->
              <div class="titMess" style="text-align: left">
                收款日期:{{time}}
                <!--{{printMess[0].chargeRecord.chargeTime}}-->
                <div style="float: right">
                  NO {{num}}
                </div>
              </div>
              <!--box-->
              <div class="boxmess">

                <div class="messList">
                  <div class="messTit">
                    今收到
                  </div>
                  <div class="ItemMess">
                    :{{jgName}}_ ( {{nameList}} )
                  </div>
                </div>

                <div class="messList">
                  <div class="messTit">
                    交來
                  </div>
                  <div class="ItemMess">
                    : 体检费
                  </div>
                </div>

                <div class="messList">
                  <div class="messTit rmbTit">
                    人民币(大写)
                  </div>
                  <div class="ItemMess rmbMess">
                    :{{money | DX}} <span style="float: right">￥{{money}}元</span>
                  </div>
                </div>

                <div class="messList">
                  <div class="messTit2" style="width: 30mm">
                    单位公章
                  </div>
                  <div class="messTit2">
                    制单人
                  </div>
                  <div class="ItemMess2">
                    :{{user.xm}}
                  </div>
                  <div class="messTit2">
                    收款人
                  </div>
                  <div class="ItemMess2">
                    :{{user.xm}}
                  </div>
                  <div class="messTit2">
                    交款人
                  </div>
                  <div class="ItemMess2" style="clear:right;">
                    :
                  </div>
                </div>

                <div class="messList">
                  <div class="messTit">
                    备注说明
                  </div>
                  <div class="ItemMess">
                    {{bz}}
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
  import mixin from '@/mixins'
  import {Printd} from 'printd'

  export default {
    name: "mess",
    mixins: [mixin],
    data() {
      return {
        jgphone: '',//机构电话
        jgName:'',//机构名称
        glyxm:'',//机构管理员
        payType:'',//支付类型
        time: '',
        user: JSON.parse(sessionStorage.getItem('userInfo')),
        num: 0,
        modalShow: true,
        nameList: '',
        money: 0,
        bz: ':',
        messIdList:[]
      }
    },
    props: {
      printMess: Array
    },
    created() {
      var v = this
      this.getTime()
      console.log(this.printMess);
      this.getNum()
      this.getMess(this.printMess)
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
          if(item.pjbh!=''){
            let arr = item.pjbh.split('-');
            v.num = arr[0] + '-' + arr[1];
            return
          }
        }
        setTimeout(()=>{
          v.AF.getPrintNum('tjsf',[],num => {
            v.num = num
          })

        },100)
      },
      getMess(arr) {
        var v = this
        if (arr.length == 0) {
          return
        }
        // this.jgName = v.dictUtil.getValByCode(v, 'ZDCLK1017', arr[0].chargeSource)
        this.jgName =  arr[0].chargeSource;
        arr.forEach((item, index) => {

          this.messIdList[index] = item.id
          this.money += item.chargeFee
          console.log(item.traineeName);
          if (index == arr.length - 1) {
            this.nameList = this.nameList + item.traineeName
          } else {
            this.nameList = this.nameList + item.traineeName + "、"
          }
          // console.log(this.nameList);
        })

      },
      printClick() {
        this.BDNum();
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
            width: 200mm;
            margin: auto;
          }
          .titTop {
            text-align: center;
            font-size: 22px;
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
            width: 180mm;
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
            font-size: 16px;
            width: 30mm;
            text-align: right;
            float: left;
          }
          .ItemMess {
            float: left;
            clear: right;
            width: 140mm;
            border-bottom: solid 1px #000;
            text-align: left;
            font-size: 15px;
            padding-right: 4px;
            padding-left: 6px;
            margin-bottom: 3px;
          }
          .messTit2 {
            font-size: 16px;
            width: 20mm;
            text-align: right;
            float: left;
          }
          .ItemMess2 {
            float: left;
            width: 25mm;
            border-bottom: solid 1px #000;
            text-align: left;
            font-size: 16px;
          }
          .boxPagerNum {
            text-align: left;
            padding: 2px 0;
            font-size: 12px;
          }
        `

        const d = new Printd();
        d.print( document.getElementById('printDivSigUp'), [cssText] )
      },
      BDNum(){
        var v = this
        var ids = this.messIdList.join(',');
        this.$http.post('/api/chargemanagement/savePjbh',{ids:ids,num:'tjsf',pjbh:this.num}).then(res=>{
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
