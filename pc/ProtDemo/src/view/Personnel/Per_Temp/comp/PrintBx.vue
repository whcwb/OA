<template>
  <div>
    <Modal v-model="showModal"
           title="批量入库"
           width="80%"
           :closable='false' :mask-closable="false">
      <div id="printDivSigUp" class="printBox">
        <div class="ptintPager">
          <div class="titTop">
            费 用 报 销 单
          </div>
          <div class="bodyMess">
            <div class="pageBox">
              <!--tit-->
              <div class="titMess" style="text-align: left">
                报销部门:
                <!--{{printMess[0].chargeRecord.chargeTime}}-->
                <div style="float: right; padding-right: 10px">
                  _____年___月___日填 单据及附件共__页
                </div>
              </div>
              <!--box-->
              <div class="boxmess">
                <table width='100%' border='1' cellspacing='0' cellpadding='2'>
                  <tr>
                    <td style="width: 300px;text-align: center">用途</td>
                    <td style="width: 50px;text-align: center">金额(元)</td>
                    <td rowspan="3" style="width:30px">
                      <div style="text-align: center">
                        <br>备<br>注
                      </div>
                    </td>
                    <td rowspan="3" colspan="3"　style="text-align:center;"></td>
                  </tr>
                  <tr style="text-align: center">
                    <td> {{ param.td1}}</td>
                    <td> {{ param.td2}}</td>

                  </tr>
                  <tr style="text-align: center">
                    <td> {{ param.td3}}</td>
                    <td> {{ param.td4}}</td>
                  </tr>
                  <tr>
                    <td style="text-align: center">{{ param.td5}}</td>
                    <td style="text-align: center">{{ param.td6}}</td>
                    <td rowspan="4" style="width: 30px;text-align: center">
                      <br>部<br>门<br>审<br>核
                    </td>
                    <td rowspan="4"></td>
                    <td rowspan="4" style="width: 30px;text-align: center">
                      <br>领<br>导<br>审<br>批
                    </td>
                    <td rowspan="4"></td>
                  </tr>
                  <tr style="text-align: center">
                    <td> {{ param.td7}}</td>
                    <td> {{ param.td8}}</td>
                  </tr>
                  <tr style="text-align: center">
                    <td> {{ param.td9}}</td>
                    <td> {{ param.td10}}</td>
                  </tr>
                  <tr style="text-align: center">
                    <td> {{ param.td11}}</td>
                    <td> {{ param.td12}}</td>
                  </tr>
                  <tr style="text-align: left">
                    <td style="text-align: left;font-size: small" colspan="2">　金额大写: {{s}} 拾 {{w}} 万 {{q}} 仟 {{b}} 佰 {{ss}} 拾 {{y}} 元 {{j}} 角 {{f}} 分</td>
                    <td style="text-align: left" colspan="2">　原借款:　　  　元</td>
                    <td style="text-align: left" colspan="2">　应退余款: 　　元</td>
                  </tr>
                </table>
              </div>
              <div style="font-size: 16px;padding-right: 6px;　padding-top: 6px;">
                <Row>
                  <Col  align="left" style="font-size: small;padding-left: 120px">
                    会计:　　　　　　　　　　　　　　
                    出纳:　　　　　　　　　　　　　　
                    报销人:
                  </Col>
                </Row>

              </div>
            </div>

          </div>
        </div>
      </div>
      <div slot="footer">
        <Button type="default" style="color: #949494" @click="close">关闭</Button>
        <Button type="warning" @click="printClick">打印</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
  // import mixin from '@/mixins'
  // import Print from 'print-js'
  import {Printd} from 'printd'

  export default {
    name: "PrintBx",
    props: {
      mess: {
        type: Array,
        default: []
      }
    },
    data() {
      return {
        showModal: true,
        kcs: {},
        s:'ⓧ',
        w:'ⓧ',
        q:'ⓧ',
        b:'ⓧ',
        ss:'ⓧ',
        y:'ⓧ',
        j:'ⓧ',
        f:'ⓧ',
        param: {
          td1:'',
          td2:'',
          td3:'',
          td4:'',
          td5: '',
          td6:'',
          td7:'',
          td8:'',
          td9:'',
          td10:'',
          td11:'',
          td12:''
        }
      }
    },
    created() {
      this.init();
    },
    methods: {
      init: function () {
        let arr = new Array("零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖");
        let total = 0;
        this.mess.forEach((it, index) => {
          total += it.rkSl * it.rkDj;
          if (index == 0) {
            this.param.td1 = it.bizKc.kcLx;
            this.param.td2 = it.rkSl * it.rkDj;
          }
          if (index == 1) {
            this.param.td3 = it.bizKc.kcLx;
            this.param.td4 = it.rkSl * it.rkDj;
          }
          if (index == 2) {
            this.param.td5 = it.bizKc.kcLx;
            this.param.td6 = it.rkSl * it.rkDj;
          }
          if (index == 3) {
            this.param.td7 = it.bizKc.kcLx;
            this.param.td8 = it.rkSl * it.rkDj;
          }
          if (index == 4) {
            this.param.td9 = it.bizKc.kcLx;
            this.param.td10 = it.rkSl * it.rkDj;
          }
          if (index == 5) {
            this.param.td11 = it.bizKc.kcLx;
            this.param.td12 = it.rkSl * it.rkDj;
          }
        })
        let a = total.toString().split('').reverse();
        let l = a.length;
        if( a.indexOf('.') == -1){
          this.f = '零';
          this.j = '零';
          this.y = arr[a[0]] ? arr[a[0]]:this.y;
          this.ss = arr[a[1]] ? arr[a[1]]:this.ss;
          this.b = arr[a[2]] ? arr[a[2]]:this.b;
          this.q = arr[a[3]] ? arr[a[3]]:this.q;
          this.w = arr[a[4]] ? arr[a[4]]:this.w;
          this.s = arr[a[5]] ? arr[a[5]]:this.s;
        }else if(a.indexOf('.') == 1){
          this.f = '零';
          this.j = arr[a[0]] ? arr[a[0]]:this.j;
          this.y = arr[a[2]] ? arr[a[2]]:this.y;
          this.ss = arr[a[3]] ? arr[a[3]]:this.ss;
          this.b = arr[a[4]] ? arr[a[4]]:this.b;
          this.q = arr[a[5]] ? arr[a[5]]:this.q;
          this.w = arr[a[6]] ? arr[a[6]]:this.w;
          this.s = arr[a[7]] ? arr[a[7]]:this.s;
        }else if(a.indexOf('0') == 2){
          this.f = arr[a[0]] ? arr[a[0]]:this.f;
          this.j = arr[a[1]] ? arr[a[1]]:this.j;
          this.y = arr[a[3]] ? arr[a[3]]:this.y;
          this.ss = arr[a[4]] ? arr[a[4]]:this.ss;
          this.b = arr[a[5]] ? arr[a[5]]:this.b;
          this.q = arr[a[6]] ? arr[a[6]]:this.q;
          this.w = arr[a[7]] ? arr[a[7]]:this.w;
          this.s = arr[a[8]] ? arr[a[8]]:this.s;
        }

      },
      close() {
        this.$parent.compName = ''
      },
      printClick() {
        var v = this
        this.showModal = false
        const cssText = `
           table tr{
                height: 50px;
            }
            table td{
              height: 50px;
              width: 100px;
            }
            #textarr{
            textarea{
              border: none;
              outline: none;
            }
            }
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
          d.print(document.getElementById('printDivSigUp'), cssText)
        }, 50)
        setTimeout(() => {
          v.close()
        }, 300)
      }
    }
  }
</script>

<style lang="less">
  table tr {
    height: 50px;
  }

  table td {
    height: 50px;
    width: 100px;
  }

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
      width: 100%;
      /**/

      .titMess {
        font-size: 16px;
        height: 10mm;
        line-height: 10mm;
      }

      .boxmess {
        /*border: solid 1px #000;*/
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
