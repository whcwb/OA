<!--
报名收费 打印清单
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
                {{ item }}
              </div>
            </div>

            <div class="pageBox">
              <!--tit-->
              <div class="titMess" style="text-align: left">
                收款日期:{{ time }}
                <div v-if="num.length == 13" style="float: right">
                  NO {{ num }}
                </div>
                <div v-else style="float: right;color: red">
                  NO {{ num }}
                </div>
              </div>
              <!--box-->
              <div class="boxmess">

                <div class="messList">
                  <div class="messTit">
                    今收到
                  </div>
                  <div class="ItemMess">
                    : {{ hisPrintMess.traineeName }}
                  </div>
                </div>
                <div class="messList">
                  <div class="messTit">
                    交來
                  </div>
                  <div class="ItemMess">
                    : 从业资格证报名费
                  </div>
                </div>

                <div class="messList">
                  <div class="messTit">
                    人民币(大写)
                  </div>
                  <div class="ItemMess">
                    :{{ hisPrintMess.chargeFee | DX }} <span style="float: right">￥{{ hisPrintMess.chargeFee }}元</span>
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
                    :{{ user.xm }}
                  </div>
                  <div class="messTit2">
                    收款人
                  </div>
                  <div class="ItemMess2">
                    :{{ user.xm }}
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
                    <Input v-if="bzShow" v-model="bz" type="textarea" :autosize="false"
                           :maxlength="30"
                           :rows="1" id="textarr"
                           placeholder="备注说明"/>
                    <span v-else>{{ bz }}</span>
                  </div>
                </div>

              </div>
              <div style="font-size: 16px;padding-right: 6px;">
                <Row>
                  <div style="overflow: hidden">
                    <div style="float: left;width: 33.33%">
                      转账支付
                    </div>
                    <div style="float: left;width: 33.33%">
                      推荐人:
                    </div>
                    <div style="float: right;width: 33.33%">
                      Tel: 400-133-2133
                    </div>
                  </div>
                </Row>

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
      feeName: '',
      user: JSON.parse(sessionStorage.getItem('userInfo')),
      num: 0,
      modalShow: true,
      nameList: '',
      money: 0,
      bz: ':',//备注
      tjr: '',
      messIdList: [],
      studentIDS: []
    }
  },
  props: {
    hisPrintMess: Object
  },
  created() {
    this.getTime()
    this.bz = ''
    var v = this
    this.getMess(this.hisPrintMess, () => {
      this.getNum()
    })
  },
  methods: {
    getTime() {
      this.$http.post('/pub/getTime', {type: 'yyyy-MM-dd'}).then(res => {
        if (res.code == 200) {
          this.time = res.result
        }
      })
    },
    getNum() {
      var v = this
      console.log("log ", this.hisPrintMess.pjbh);
      if (this.hisPrintMess.pjbh) {
        let arr = this.hisPrintMess.pjbh.split('-');
        v.num = arr[0] + '-' + arr[1];
        return
      }
      setTimeout(() => {
        v.AF.getPrintNum('cyzg', this.studentIDS, num => {
          v.num = num
          console.log("num -> ", num);
          // v.num = 0
          if (v.num == 0) {
            v.num = '网络异常!请点击取消按钮,重新打开获取票据号';
            swal({
              title: '网络异常!请点击取消按钮,重新打开获取票据号',
              type: 'warning',
              confirmButtonText: '确定'
            })
          }
        })
      }, 100)
    },
    getMess(arr, callback) {
      var v = this
      console.log('arr', arr);
      this.messIdList.push(arr.id);
      callback && callback()
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
      this.$http.post('/api/chargemanagement/savePjbh', {ids: ids, num: 'cyzg', pjbh: this.num}).then(res => {
        if (!res.code == 200) {
          return
        } else {
          v.$parent.getPagerList();
          v.$parent.getDictList();
          v.$parent.getSfList();
          v.$parent.keyLis();
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
