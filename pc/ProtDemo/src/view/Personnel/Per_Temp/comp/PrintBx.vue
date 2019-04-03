<template>
  <div>
  <Modal v-model="showModal"
         title="批量入库"
         width="80%"
         :closable='false' :mask-closable="false">
  <div id="printDivSigUp" class="printBox">
    <div class="ptintPager">
      <div class="titTop">
        费  用  报  销  单
      </div>
      <div class="bodyMess">
        <div class="pageBox">
          <!--tit-->
          <div class="titMess" style="text-align: left">
            报销部门:
            <!--{{printMess[0].chargeRecord.chargeTime}}-->
            <div style="float: right">
              ______年___月___日填  单据及附件共__页
            </div>
          </div>
          <!--box-->
          <div class="boxmess">
            <table width='100%' border='1' cellspacing='0' cellpadding='2' bordercolor='black'>
            <tr>
              <td style="width: 300px">用途</td>
              <td style="width: 50px">金额(元)</td>
              <td rowspan="3" style="width:30px" >
                <div>
                  <br>备
                  <br>注
                </div>
              </td>
              <td rowspan="3" colspan="3"></td>
            </tr>
            <tr>
              <td></td>
              <td></td>

            </tr>
              <tr>
                <td></td>
                <td></td>
              </tr>
              <tr>
                <td></td>
                <td></td>
                <td rowspan="4" style="width: 30px">
                  <br>部
                  <br>门
                  <br>审
                  <br>核
                </td>
                <td rowspan="4"></td>
                <td rowspan="4" style="width: 30px">
                  <br>领
                  <br>导
                  <br>审
                  <br>批</td>
                <td rowspan="4"></td>
              </tr>
              <tr>
                <td></td>
                <td></td>
              </tr>
              <tr>
                <td></td>
                <td></td>
              </tr>
              <tr>
                <td></td>
                <td></td>
              </tr>
              <tr>
                <td colspan="2">金额大写:   拾   万   仟   佰    拾   元   角    分</td>
                <td colspan="2">原借款:        元</td>
                <td colspan="2">应退余款:         元</td>
              </tr>
            </table>
          </div>
          <div style="font-size: 16px;padding-right: 6px;">
            <Row>
              <Col span="8" align="left">
                会计
              </Col>
              <Col span="8" align="left">
                <div>
                 出纳
                </div>
              </Col>
              <Col span="8" align="left">
                <div>
                  报销人
                </div>
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
  import mixin from '@/mixins'
  // import Print from 'print-js'
  import {Printd} from 'printd'
    export default {
        name: "PrintBx",
        data(){
          return{
            showModal: true,
            kcs:{}
          }
        },
      created(){
      },
      methods:{
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
          setTimeout(()=>{
            d.print( document.getElementById('printDivSigUp'), cssText )
          },50)
          setTimeout(()=>{
            v.close()
          },300)
        }
      }
    }
</script>

<style lang="less">
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
    width: 100%;
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
