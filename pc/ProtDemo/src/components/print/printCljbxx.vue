<!--
授权委托书
-->
<style lang="less">
  .jt1 tr{
    height: 60px;
  }
  .jt1 td{
    text-align: center;
    font-size:18px
  }
  .jt2 tr{
    height: 70px;
  }
  .jt2 td{
    text-align: center;
    font-size:18px
  }
  .t1 td{
    font-size: 20px;
  }
  .page {
    page-break-after: always;
  }

</style>
<!-- 报名收费票据打印 -->
<template>
  <div class="signUpPrint">
    <Modal
      v-model="modalShow"
      width="900px"
      :closable='false'
      :scrollable="'true'"
      :footer-hide="true"
      class-name="vertical-center-modal"
      :mask-closable="false">
      <div slot="header" class="box_row colCenter">
        <div class="box_row_100" style="font-size: 22px;font-weight: 700;margin-left:12px">
          打印
        </div>
        <Button @click="close" style="margin: 0 12px">取消</Button>
        <Button type="success" @click="printCover" style="margin: 0 12px">打印封面</Button>
        <Button type="success" @click="printTable" style="margin: 0 12px">打印表格</Button>
      </div>
      <div id="preview"  :style="{height:AF.getPageHeight()-100+'px',overflow:'auto'}">
        <page0 :carInfo="carInfo" class="page"></page0>
        <page1 :carInfo="carInfo"></page1>
        <page2 :carInfo="carInfo"></page2>
        <page3></page3>
        <page4></page4>
        <page5></page5>
      </div>
      <div id="printCover" v-if="showCover" class="printBox" :style="{height:'1300px',overflow:'hidden'}">
        <page0 :carInfo="carInfo" class="page"></page0>
      </div>
      <div id="printTable" v-if="showTable"  class="printBox" :style="{height:'6000px',overflow:'hidden'}">
        <page1 :carInfo="carInfo"></page1>
        <div style="page-break-after: always;"></div>
        <page2 :carInfo="carInfo"></page2>
        <div style="page-break-after: always;"></div>
        <page3></page3>
        <div style="page-break-after: always;"></div>
        <page4></page4>
        <div style="page-break-after: always;"></div>
        <page5></page5>
        <div style="page-break-after: always;"></div>
        <page6></page6>
      </div>
      <div slot="footer">
        <Button type="success" @click="printTable" style="margin: 0 12px">打印表格</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
  import mixin from '@/mixins'
  // import Print from 'print-js'
  import page0 from './carMess/carPage0'
  import page1 from './carMess/carPage1'
  import page2 from './carMess/carPage2'
  import page3 from './carMess/carPage3'
  import page4 from './carMess/carPage4'
  import page5 from './carMess/carPage5'
  import page6 from './carMess/carPage6'

  import {Printd} from 'printd'
  import moment from 'moment'
  export default {
    name: "mess",
    mixins: [mixin],
    components: {page0,page1,page2,page3,page4, page5, page6},
    data() {
      return {
          showCover:false,
          showTable:false,
        carInfo: {
          dah: '',
          cph: '',
          clxh: '',
          jdrq: '',
        },
        modalShow: true,
          clId:'',
      }
    },
    props: {
      printMess: Array
    },
    created() {
        this.clId = this.$parent.param.clId;
        this.getData();
    },
    methods: {
        getData(){
            this.$http.get(this.apis.CAR.QUERY,{params:{id:this.clId}}).then((res)=>{
                if (res.code == 200){
                    this.carInfo = res.page.list[0];
                }
            })
        },
      //   getcarsyrlx(){
      //     this.$http.post('/api/car/getsyrlx',{clId:this.clId}).then((res)=>{
      //       console.log(res);
      //     })
      //   },
      // getCarArchives(){
      //   this.$http.post('/api/car/getCarArchives',{clId:this.clId}).then((res)=>{
      //     console.log(res);
      //   })
      // },
        printTable() {
        this.showTable = true;
        var v = this
        this.bzShow = false
        const cssText = `

        .page {
          page-break-after: always;
        }
        .jt1 td{
          text-align: center;
          font-size:18px
        }
         .jt1 tr{
           height: 60px;
         }

        .jt2 tr{
          height: 70px;
        }
        .jt2 td{
          text-align: center;
          font-size:18px
        }
        `
        const d = new Printd();
        setTimeout(() => {
          d.print(document.getElementById('printTable'), [cssText])
        }, 50)
        setTimeout(() => {
          v.close()
        }, 300)
      },
      printCover() {
          this.showCover = true;
        var v = this
        this.bzShow = false
        const cssText = `

        .page {
          page-break-after: always;
        }

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
        setTimeout(() => {
          d.print(document.getElementById('printCover'), [cssText])
        }, 50)
        setTimeout(() => {
          v.close()
        }, 300)
      },
      close() {
        this.$parent.componentName = ''
      }
    }
  }
</script>
