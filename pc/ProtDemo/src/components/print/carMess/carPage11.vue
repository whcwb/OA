<template>
  <div>
    <Modal
      v-model="showModal"
      height="600"
      width="800"
      :closable='false'
      :mask-closable="false"
      class-name="vertical-center-modal">
      <div slot="header">
        <Row>
          <Col span="12">
            <p>蔡甸区重点驾驶员基础信息登记表</p>
          </Col>
          <Col span="12">
            <Row type="flex" justify="end">
              <Col align="right">
                <Button size="small" type="default" style="margin:0 8px" @click="close">
                  关闭
                </Button>
                <Button size="small" type="success" style="margin:0 8px" @click="print">
                  打印
                </Button>
              </Col>
            </Row>
          </Col>
        </Row>
      </div>
      <div id="printddd">
        <div style="font-size: 28px;font-weight: 700">蔡甸区重点驾驶员基础信息登记表</div>
        <br>
        <table border="1" cellspacing="0" style="width: 100%;" class="jt1">
          <tr chass="jt2">
            <td>姓名</td>
            <td colspan="2">{{carInfo.jsyxm}}</td>
            <td>性别</td>
            <td>{{carInfo.jsyxb}}</td>
            <td>联系电话</td>
            <td colspan="2">{{carInfo.jsylxdh}}</td>
            <td rowspan="6"></td>
          </tr>
          <tr chass="jt2">
            <td>身份证号</td>
            <td colspan="2">{{carInfo.jsysfzh}}</td>
            <td>准驾车型</td>
            <td colspan="2">{{carInfo.jsyzjcx}}</td>
            <td>审验至</td>
            <td>{{carInfo.nsz}}</td>
          </tr>
          <tr chass="jt2">
            <td>住所地址</td>
            <td colspan="7"></td>
          </tr>
          <tr chass="jt2">
            <td>号牌号码</td>
            <td></td>
            <td>车辆类型</td>
            <td>小车</td>
            <td>使用性质</td>
            <td>教练</td>
            <td>检验至</td>
            <td></td>
          </tr>
          <tr chass="jt2">
            <td>服务住所</td>
            <td colspan="7">蔡甸街姚家山开发区133号2栋</td>
          </tr>
          <tr chass="jt2">
            <td>安全负责人姓名</td>
            <td colspan="2"></td>
            <td colspan="3">联系电话</td>
            <td colspan="2"></td>
          </tr>
          <tr style="height: 400px">
            <td></td>
            <td colspan="8"></td>
          </tr>
          <tr style="height: 400px">
            <td></td>
            <td colspan="8"></td>
          </tr>
        </table>
      </div>
    </Modal>
  </div>

</template>

<script>
  import {Printd} from 'printd'

  export default {
    name: "carPage1",
    props: {
      carInfo: {
        type: Object,
        default: () => {
          return {}
        }
      }
    },
    data(){
      return {
        showModal: true
      }
    },
  methods:{
    close(){
      this.$parent.componentName = ''
    },
    print() {
      var v = this
      this.bzShow = false
      const cssText = `
        .printBox div{
          font-size: 20px;
        }
        .jt1{
          height: 1300px;
        }
        .jt2{
            height: 40px;
           font-size: 30px;
        }
        .printBox span{
          font-size: 20px;
        }
        .printBox p{
          text-indent: 2em;
        }
        .titTop{
          font-size: 40px;
          font-weight: 600;
          text-align: center;
        }
        `
      const d = new Printd();
      setTimeout(()=>{
        d.print( document.getElementById('printddd'), [cssText] )
      },50)
      setTimeout(()=>{
        v.close()
      },300)
    },

  }
  }
</script>

<style scoped>
.jt1{
  height: 1000px;
}
.jt2{
  height: 40px;
  font-size: 20px;
}
</style>
