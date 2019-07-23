<!--
授权委托书
-->
<style>
  .printBox div{
    font-size: 20px;
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
</style>
<!-- 报名收费票据打印 -->
<template>
  <div class="signUpPrint">
    <Modal
      v-model="modalShow"
      width="900px"
      :closable='false'
      class-name="vertical-center-modal"
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
            授权委托书
          </div>
          <br>
          <div class="line1">
            武汉市公安局交通管理局车辆管理所：
          </div>
          <div class="p1">
            <p>
              兹委托  <u>&nbsp&nbsp李雄立&nbsp&nbsp</u> 作为委托人的全权代理人，办理委托人拥有的机动车（号牌码或车辆识别代号） <u>&nbsp&nbsp年检&nbsp&nbsp</u>  业务，代理人在办理上述事项内所提供的有关资料和填写的表格，委托人均予以承认。
            </p>
            <p>
              代理人对本委托书的真实和有效性负责，并已核实委托人的有关情况。
            </p>
            <p>
              本委托书的有效期为： <u>&nbsp{{y}}&nbsp</u>年<u>&nbsp{{m}}&nbsp</u>月<u>&nbsp{{d}}&nbsp</u>日至
              <u>&nbsp{{ey}}&nbsp</u>年<u>&nbsp{{em}}&nbsp</u>月<u>&nbsp{{ed}}&nbsp</u>日止。本委托书不得转委托。
            </p>
          </div>
          <div class="sfz">
            <img src="http://119.23.242.234:9092/sfz.png" alt="" style="width: 100%">
          </div>
          <div class="sign">
            <!--<div style="margin-left: 250px;position: absolute;">-->
              <!--<img alt="" style="width: 100px">-->
            <!--</div>-->
            <div class="s1">
              <p>代理人/经办人签字：<u>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp  李雄立  &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</u></p>

            </div>
            <div class="s2">
              <p>身份证号码：<u>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp 420102197305061436 &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</u></p>
            </div>
          </div>
          <div>
            <p>（代理人为单位的，由经办人签字，填写经办人身份证号码和加盖单位公章；代理个人机动车登记业务的需对委托书惊喜公证。）</p>
          </div>
          <br>
          <div class="date" style="text-align: right">
            <span>签署日期：<u>&nbsp{{y}}&nbsp</u>年<u>&nbsp{{m}}&nbsp</u>月<u>&nbsp{{d}}&nbsp</u>日</span>
          </div>
        </div>
      </div>
      <div slot="footer"></div>
    </Modal>
  </div>
</template>

<script>
  import mixin from '@/mixins'
  // import Print from 'print-js'
  import {Printd} from 'printd'
  import moment from 'moment'
  export default {
    name: "mess",
    mixins: [mixin],
    data() {
      return {
          modalShow:true,
          y:0,
          m:0,
          d:0,
          ey:0,
          em:0,
          ed:0,
      }
    },
    props: {
      printMess: Array
    },
    created() {
        let now = new Date();
        this.y = now.getFullYear();
        this.m = now.getMonth();
        this.d = now.getDate()
        if(this.m == 0){
          this.m = 12
        }
        let edate = moment().add(90,'days')
        this.ey = edate.year()
        this.em = edate.month()
        this.ed = edate.date()
    },
    methods: {
      printClick() {
        var v = this
        this.bzShow = false
        const cssText = `
        .printBox div{
          font-size: 20px;
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
          d.print( document.getElementById('printDivSigUp'), [cssText] )
        },50)
        setTimeout(()=>{
          v.close()
        },300)
      },
      close() {
        this.$parent.componentName = ''
      }
    }
  }
</script>
