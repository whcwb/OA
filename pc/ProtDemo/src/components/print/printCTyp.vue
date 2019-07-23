<!--
收费查询 打印、、、变更车型票据打印
-->
<style lang="less">
  #textarr {
    textarea {
      border: none;
      outline: none;
    }
  }

  #printDivSigUp {
    font-family: "黑体";
    color: #000000 !important;
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
            float: left;
            clear: right;
            width: 150mm;
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
            武汉市明涛驾校
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
                收款日期:{{UM.chargeTime}}
                <div style="float: right">
                  NO {{UM.pjbh}}
                </div>
              </div>
              <!--box-->
              <div class="boxmess">

                <div class="messList">
                  <div class="messTit">
                    今收到
                  </div>
                  <div class="ItemMess">
                    :{{UM.chargeSource}}_{{UM.glyxm}} ( {{UM.traineeName}} )
                  </div>
                </div>

                <div class="messList">
                  <div class="messTit">
                    交來
                  </div>
                  <div class="ItemMess">
                    : {{chargeCode(UM.chargeCode)}}
                  </div>
                </div>

                <div class="messList">
                  <div class="messTit">
                    人民币(大写)
                  </div>
                  <div class="ItemMess">
                    :{{UM.chargeFee | DX}} <span style="float: right">￥{{UM.chargeFee}}元</span>
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
                    :
                    <span v-if="UM.pjbh==''">{{user.xm}}</span>
                    <span v-else>{{receiver(UM.receiver)}}</span>
                  </div>
                  <div class="messTit2">
                    收款人
                  </div>
                  <div class="ItemMess2">
                    :
                    <span v-if="UM.pjbh==''">{{user.xm}}</span>
                    <span v-else>{{receiver(UM.receiver)}}</span>
                  </div>
                  <div class="messTit2">
                    交款人
                  </div>
                  <div class="ItemMess2" style="clear:right;">
                    :
                  </div>
                </div>

                <div class="messList" v-if="false">
                  <div class="messTit">
                    备注说明
                  </div>
                  <div class="ItemMess">
                    <Input v-if="bzShow" v-model="bz" type="textarea" :autosize="false"
                           :maxlength="30"
                           :rows="1" id="textarr"
                           placeholder="备注说明"/>
                    <span v-else>{{bz}}</span>
                  </div>
                </div>

              </div>
              <div style="font-size: 16px;padding-right: 6px;">
                <div style="float: left">
                  {{chargeType(UM.chargeType)}}
                </div>
                <div style="float: right">
                  Tel:{{UM.jgPhone.length==11 ? '400-133-2133': jgphone}}
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
        bzShow: true,
        jgphone: '',//机构电话
        jgName: '',//机构名称
        glyxm: '',//机构管理员
        payType: '',//支付类型
        time: '',
        user: JSON.parse(sessionStorage.getItem('userInfo')),
        num: 0,
        modalShow: true,
        nameList: '',
        money: 0,
        bz: ':',
        messIdList: [],
        studentIDS: [],
      //  -------------------
        UM:{
          chargeTime:'',
          chargeSource:'',
          glyxm:'',
          traineeName:'',
          chargeCode:'',
          receiver:'',
          chargeType:'',
          jgphone:'',
        }
      }
    },
    filters: {
      chargeTime: (val) => {
        console.log(val);
        // return val.Substring(0,10)
      }
    },
    props: {
      mess: {
        type: Object,
        default: {}
      }
    },
    created() {
      this.getTime()
      this.UM = this.mess
      console.log('數據傳遞', this.UM);
    },
    methods: {
      getTime(){
        this.$http.post('/pub/getTime',{type:'yyyy-MM-dd'}).then(res=>{
          if(res.code == 200){
            this.time = res.result
          }
        })
      },
      chargeType(code){
        return this.dictUtil.getValByCode(this, 'ZDCLK1004',code)
      },
      chargeCode(code){
        return this.dictUtil.getValByCode(this, 'ZDCLK1024',code)
      },
      receiver(arr){
        // let a = arr.split('-')[1]
        return arr
      },
      printClick() {
        var v = this
        this.BDNum()
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
            width: 150mm;
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
        setTimeout(() => {
          d.print(document.getElementById('printDivSigUp'), [cssText])
        }, 50)
        setTimeout(() => {
          v.close()
        }, 300)
      },

      BDNum() {
        var v = this;
        var ids = this.messIdList.join(',');
        this.$http.post('/api/chargemanagement/savePjbh', {
          ids: ids,
          num: 'pjdy',
          pjbh: this.num
        }).then(res => {
          if (!res.code == 200) {
            return
          } else {
            v.$parent.getpager()
          }
        }).catch(err => {
          return
        })
      },

      close() {
        this.$parent.compName = ''
      }
    }
  }
</script>
