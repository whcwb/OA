<style lang="less">
  #examine {

    .printBox {
      text-align: center;
      padding: 0 0 16px 0;
      border: #dddddd solid 1px;
      .ptintPager {
        width: 190mm;
        height: 85mm;
        margin: auto;
        /*background-color: #2d8cf0;*/
      }
      .titTop {
        text-align: center;
        font-size: 22px;
        font-weight: 700;
        padding-top: 12px;
        height: 15mm;
        line-height: 15mm;
      }
      .bodyMess {
        overflow: hidden;
        /*background-color: #f5a623;*/
        height: 70mm;
      }
      .leftTitSize {
        font-size: 22px;
        font-weight: 700;
        padding: 40px 8px 0 8px;
        float: left;
        height: 70mm;
        width: 15mm;
        .LiftTitItem {
          height: 13mm;
          line-height: 13mm;
        }
      }
      .pageBox {
        height: 70mm;
        float: left;
        width: 170mm;
        .titMess {
          font-size: 16px;
          font-weight: 600;
          height: 10mm;
          line-height: 10mm;
        }
        .boxmess { //width 170mm height 55mm
          border: 2px solid #bbbbbb;
          padding-right: 8px;
          height: 55mm;
          .messList {
            overflow: hidden;
            height: 10mm;
            line-height: 10mm;
            .messTit {
              font-size: 16px;
              font-weight: 600;
              width: 30mm;
              text-align: right;
              float: left;
            }
            .ItemMess {
              float: left;
              clear: right;
              width: 130mm;
              border-bottom: solid 2px #ddd;
              text-align: left;
              font-size: 15px;
              font-weight: 600;
              padding-right: 4px;
              padding-left: 6px;
            }
            .messTit2 {
              font-size: 16px;
              font-weight: 600;
              width: 20mm;
              text-align: right;
              float: left;
            }
            .ItemMess2 {
              float: left;
              width: 23mm;
              border-bottom: solid 2px #ddd;
              text-align: left;
              font-size: 15px;
              font-weight: 600;
            }
          }
        }
        .boxPagerNum {
          text-align: left;
          padding: 2px 0;
          font-size: 12px;
          font-weight: 600;
        }

      }
    }
    .ROWCOL {
      width: 190mm;
      height: 85mm;
      margin: auto;
      .titTop {
        text-align: center;
        font-size: 22px;
        font-weight: 700;
        padding-top: 12px;
        height: 15mm;
        line-height: 15mm;
      }
      .LiftTitItem {
        height: 13mm;
        line-height: 13mm;
        font-size: 22px;
        font-weight: 700;
        width: 100px;
      }
      .timeMess {
        text-align: left;
        font-size: 16px;
        font-weight: 600;
      }
      .box {
        border: 2px solid #bbbbbb;
        padding-right: 8px;
        height: 55mm;
      }

    }
  }
</style>
<!--收费审核票据打印-->
<template>
  <div id="examine">
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
      <div id="printDiv" class="printBox">
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
                收款日期:
                <!--{{printMess[0].chargeRecord.chargeTime}}-->
              </div>
              <!--box-->
              <div class="boxmess">

                <div class="messList">
                  <div class="messTit">
                    今收到
                  </div>
                  <div class="ItemMess">
                    :{{nameList}}
                  </div>
                </div>

                <div class="messList">
                  <div class="messTit">
                    交來
                  </div>
                  <div class="ItemMess">
                    :
                  </div>
                </div>

                <div class="messList">
                  <div class="messTit">
                    人民币(大写)
                  </div>
                  <div class="ItemMess">
                    :{{money | DX}} <span style="float: right">￥{{money}}元</span>
                  </div>
                </div>

                <div class="messList">
                  <div class="messTit2" style="font-weight: 500;width: 30mm">
                    单位公章
                  </div>
                  <div class="messTit2">
                    制单人
                  </div>
                  <div class="ItemMess2">
                    :
                  </div>
                  <div class="messTit2">
                    收款人
                  </div>
                  <div class="ItemMess2">
                    :
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
                    :
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
  import Print from 'print-js'

  export default {
    name: "mess",
    mixins: [mixin],
    data() {
      return {
        modalShow: true,
        nameList: '',
        money: 0,
        bz: ''
      }
    },
    props: {
      printMess: Array
    },
    created() {
      console.log('數據傳遞', this.printMess);
      this.getMess(this.printMess)
    },
    methods: {
      getMess(arr) {
        if (arr.length == 0) {
          return
        }
        // inOutType 00 收入  10支出
        arr.forEach((item, index) => {
          if (item.inOutType == '00') {
            this.money += item.chargeFee
            if (index == arr.length - 1) {
              this.bz += item.remark
              this.nameList = this.nameList + item.traineeName
            } else {
              this.bz += item.remark + "、"
              this.nameList = this.nameList + item.traineeName + "、"
            }

          }

        })

      },
      printClick() {
        printJS({
          printable: 'printDiv',
          // 继承原来的所有样式
          targetStyles: ['*'],
          type: 'html'
        });
      },
      close() {
        this.$parent.compName = ''
      }
    }
  }
</script>
