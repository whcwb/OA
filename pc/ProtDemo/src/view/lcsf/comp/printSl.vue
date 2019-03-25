<template>
  <div>
    <Modal
      v-model="showModal"
      :closable='false'
      :mask-closable="false"
      :footer-hide="true"
      class-name="vertical-center-modal">
      <div v-if="info.jssj">
        <Row>
          请输入学员人数
        </Row>
        <Row>
          <Input size="large" style="padding: 10px" v-model="xySl"></Input>
        </Row>
      </div>
      <div slot="header">
        <Row>
          <Col span="4 ">
            <!--<Col span="4">-->
            <p>车单打印</p>
          </Col>
          <!--<Col span="4" align="center">
            <span v-if="printClose">
            {{ mesTime }}s后关闭
            </span>
          </Col>-->
          <Col offset="4" span="16" align="right">
            <Button type="default" size="large" style="margin: 0 12px"
                    @click="close">关闭
            </Button>
            <Button type="success" size="large" style="margin: 0 12px"
                    @click="doPrint('打印预览')">打印预览
            </Button>
            <Button type="success" size="large"
                    @click="doPrint('')">打印
            </Button>
          </Col>
        </Row>
      </div>
      <div class="printSty" ref="printDiv">
        <div style="font-weight: 500;font-size: 16px">{{info.jssj}}</div>
        <table border="1" cellpadding="0" cellspacing="0" style="width: 100%;font-family: 黑体;color:#000">
          <tr>
            <td colspan="2" style="text-align: center"><span style="font-size: 20px;">车辆租赁凭据</span></td>
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
          <tr v-for="(item,index) in mess" :key="index">
            <td>{{item.title}}</td>
            <td>
              {{getDict(info[item.key],item.dict)}}
              <span v-if="item.key2">_{{info[item.key2]}}</span>
              <span v-if="item.unit">{{item.unit}}</span>
            </td>
          </tr>
        </table>
        <div style="font-family:黑体;font-weight: 600;font-size: 16px!important;color: #000">本票据遗失不补</div>
      </div>
    </Modal>
  </div>
</template>

<script>
  import {Printd} from 'printd'
  import barcode from '@xkeshi/vue-barcode';
  import {mapMutations} from 'vuex'
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
        mess: [
          {title: '训练科目', key: 'kc'},
          // {title: '训练科目', key: 'lcKm', dict: 'km'},
          {title: '车辆编号', key: 'clBh'},
          {title: '训练车型', key: 'jlCx'},
          {title: '教练员', key: 'jlJx', key2: 'jlXm'},
          {title: '安全员', key: 'zgXm'},
          {title: '优惠时长', key: 'yhsc'},
          // {title: '优惠金额', key: 'yhje'},
          {title: '累计时长', key: 'sc'},
          {title: '累计费用', key: 'lcFy', unit: '元'},
        ],
        mesTime: 7,
        info: '',
        Interval:-1,
        xySl:''
      }
    },
    created() {
      this.info = JSON.parse(JSON.stringify(this.hisPrintMess))
      this.info.sc = this.parseTime(this.info.sc)
      this.info.kssj = this.info.kssj.substr(0, 16)
      this.info.jssj = this.info.jssj.substring(0, 10)
      this.info.yhsc = '5分钟'
      this.info.yhje = 8.33*5
      let v = this;
      setTimeout(() => {
        let canvas = document.getElementById("barcode");
        v.codeSrc = canvas.toDataURL()
        setTimeout(()=>{
          this.SetPprintInnerHTML(this.$refs.printDiv.innerHTML)
        },300)
      }, 200)
    },
    beforeDestroy(){
      this.SetPprintInnerHTML('')
    },
    mounted() {
      var v = this
      /*this.$nextTick(() => {
        // document.getElementById("page1").innerHTML = this.$refs.printDiv.innerHTML;

        if (this.printClose) {
          this.Interval = setInterval(() => {
            this.mesTime--
            if (this.mesTime <= 0){
              clearInterval(this.Interval)
            }
          }, 1000)

          setTimeout(() => {
            this.doPrint('', () => {
              setTimeout(() => {
                v.close()
              }, 5000)
            })
          }, 1000)
        }
      })*/
    },
    methods: {
      getxyRs(){
        this.$http.post('api/lcjl/updateXysl',{id:this.hisPrintMess.id,xySl:this.xySl}).then((res)=>{
          if(res.code == 200){
            this.$Message.info('提交成功');
            this.util.getPageData(this.$parent)
          }else{
            this.$Message.info(res.message);
          }
        })
      },
      ...mapMutations([
        'SetPprintInnerHTML'
      ]),
      doPrint(how, callback) {
        this.getxyRs();
        var myDoc = {
          documents: document, // 打印页面(div)们在本文档中
          copyrights: '杰创软件拥有版权  www.jatools.com', // 版权声明必须
          settings: {
            marginBottom: 2,
            marginLeft: 2,
            marginRight: 2,
            marginTop: 1
          }
        };
        var jcp = getJCP();
        // 调用打印方法

        if (how == '打印预览') {
          jcp.printPreview(myDoc, false);
        } else {
          jcp.print(myDoc, false); // 不弹出对话框打印
        }
        // else if (how == "打印预览(显示进度条)") {
        //   jcp.printPreview(myDoc, true);
        // } else if (how == '弹出打印机选择对话框打印') {
        //   jcp.print(myDoc, true);
        // } else
        callback && callback()

      },
      parseTime(s) {
        s = parseInt(s);
        let h = parseInt(s / 60);
        let m = s % 60;
        let r = '';
        if (h != 0) r += h + '小时'
        return r + m + '分钟'
      },
      getDict(val, key) {
        if (key) {
          return this.dictUtil.getValByCode(this, key, val)
        }
        return val
      },
      close() {
        // this.$parent.compHisName = '';
        // document.getElementById("page1").innerHTML = "";
        console.log('q');
        clearInterval(this.Interval)
        this.$parent.componentName = ''
        if (typeof this.$parent.getCarList == 'function') {
          this.$parent.getCarList()
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
