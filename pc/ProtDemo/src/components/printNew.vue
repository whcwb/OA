<template>
  <div>
    <Modal
      v-model="showModal"
      :closable='false'
      :mask-closable="false"
      :footer-hide="true"
      class-name="vertical-center-modal">

      <div slot="header">
        <Row>
          <!--<Col span="4 ">-->
          <!--<Col span="4">-->
          <!--            <p>车单打印</p>-->
          <!--</Col>-->
          <!--<Col span="4" align="center">
            <span v-if="printClose">
            {{ mesTime }}s后关闭
            </span>
          </Col>-->
          <Col span="24" align="center">
            <Button type="default" size="large" style="margin: 0 12px"
                    @click="close">关闭
            </Button>
            <!--<Button type="success" size="large" style="margin: 0 12px"-->
            <!--@click="doPrint('打印预览')">打印预览-->
            <!--</Button>-->
            <Button type="success" size="large" :disabled="isDone"
                    @click="doPrint('')">打印
            </Button>
          </Col>
        </Row>
      </div>
      <div class="printSty" ref="printDiv">
        <!--        <div style="font-weight: 500;font-size: 16px">{{info.jssj}}</div>-->
        <table border="1" cellpadding="0" cellspacing="0" style="table-layout: fixed;width: 100%;font-family: 黑体;color:#000">
          <tr>
            <td colspan="2" style="text-align: center"><span style="font-size: 20px;">{{ info.chargeName }}</span></td>
          </tr>
          <tr>
            <td colspan="2">
              <div>
                <barcode id="barcode" style="top:500px;position:absolute;top:-500px;opacity:0"
                         :value="info.id" :options="{ displayValue: false,height:40}"></barcode>
              </div>
              <div>
                <div id="barcode1"><img :src="codeSrc" alt="" style="width: 100%"></div>
              </div>
              <div style="text-align: center"><span>{{info.id}}</span></div>
            </td>
          </tr>
          <tr v-for="(item,index) in displayItem" :key="index" >
            <td  v-if="index>0">{{item.title}}</td>
            <td  v-if="index>0" style="white-space: nowrap;overflow: hidden;text-overflow: ellipsis;">
              {{item.val}}
            </td>
          </tr>
        </table>
        <div>
          <Row>
            <Col span="12">
              <div style="font-family:黑体;font-weight: 600;font-size: 12px!important;color: #000">本票据遗失不补</div>
            </Col>
            <Col>
              <div style="font-weight: 500;font-size: 12px;text-align: right">{{info.cjsj}}</div>
            </Col>
          </Row>
        </div>

      </div>
    </Modal>
  </div>
</template>

<script>
  import {Printd} from 'printd'
  import barcode from '@xkeshi/vue-barcode';
  import {mapMutations} from 'vuex'
  import print from '../libs/print'

  export default {
    components: {barcode},
    name: "print",
    props: {
      hisPrintMess: {
        type: Object,
        default: () => {
          return {}
        }
      },
      printClose: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        showModal: true,
        showCode: false,
        codeSrc: '',
        isDone: true,
        displayItem: {},
        mess: [
          // {title: '训练科目', key: 'km'},
          {title: '教练员', key: 'jlJx', key2: 'jlXm'},
          {title: '安全员', key: 'zgXm'},
          // {title: '优惠时长', key: 'yhsc'},
          // {title: '优惠金额', key: 'yhje'},
          {title: '累计时长', key: 'sc'},
          {title: '累计费用', key: 'lcFy'},
          {title: '备注', key: 'bz'},

        ],
        mesTime: 7,
        info: '',
        Interval: -1
      }
    },
    created() {
      this.info = JSON.parse(JSON.stringify(this.hisPrintMess))
      if (this.printClose) {
        this.showModal = false
      }
      this.$http.get('/api/chargemanagement/'+this.info.id).then((res) => {
        if (res.code == 200) {
          this.info = res.result
          //有任何需要处理的打印参数，在这里处理
          this.info.sc = this.info.sc == '0' ? '' : this.info.sc + '分钟'
          if (this.info.xjje == 0) this.info.xjje = '0元'
          else this.info.xjje += '元'
          //赋值打印参数，该打印对象仅限于print固定模板
          this.displayItem = [
            {
              title:'名称',
              val:this.info.chargeName
            },
            {
              title: '姓名',
              val: this.info.traineeName
            },
            {
              title: '证件号码',
              val: this.info.idCardNo,
            },
            {
              title: '费用',
              val: this.info.chargeFee + '元',
            },
            {
              title: '备注',
              val: ''
            },
          ]

          if (this.printClose) {
            this.doPrint('')
          }

          this.isDone = false
        }
      })

      let v = this;
      setTimeout(() => {
        let canvas = document.getElementById("barcode");
        if (this.showModal) {
          v.codeSrc = canvas.toDataURL()
          setTimeout(() => {
            this.SetPprintInnerHTML(this.$refs.printDiv.innerHTML)
          }, 300)
        }
      }, 200)
    },
    beforeDestroy() {
      this.SetPprintInnerHTML('')
    },
    mounted() {
      var v = this
      this.$nextTick(() => {
      })
    },
    methods: {
      ...mapMutations([
        'SetPprintInnerHTML'
      ]),
      enter() {
        var _this = this;
        document.onkeydown = function (e) {
          let key = window.event.keyCode;
          if (key == 13) {
            _this.doPrint();
          }
        };
      },
      doPrint(how, callback) {
        let item = []
        this.displayItem.map((val, index, arr) => {
          item[index] = val.val
        })
        print.print(this.info.id, item, this.info.cjsj.substring(0, 16))
        this.close()

      },
      // parseTime(s) {
      //   s = parseInt(s);
      //   let h = parseInt(s / 60);
      //   let m = s % 60;
      //   let r = '';
      //   if (h != 0) r += h + '小时'
      //   return r + m + '分钟'
      // },
      getDict(val, key) {
        if (key) {
          return this.dictUtil.getValByCode(this, key, val)
        }
        return val
      },
      close() {
        clearInterval(this.Interval)
        this.$parent.compName = ''
        if (typeof this.$parent.getPagerList == 'function') {
          this.$parent.getPagerList()
        }
      }
    }
  }
</script>

<style lang="less">
  #page1 {
    width: 68mm;
    margin: auto;
  }

  .printSty {
    width: 100%;
    /*background-color: #ed3f14;*/
    font-size: 18px;
    font-weight: 600;
  }

  td {
    padding: 5px 2px;
    text-align: center;
  }
</style>
