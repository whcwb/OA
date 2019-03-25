<template>
  <div>
    <Modal
      v-model="showModal"
      width="800"
      :closable='false'
      :mask-closable="false"
      :title="'科目二训练明细统计'">
      <div :style="{height:AF.getPageHeight()-480 +'px',overflow:'auto',width:'100%'}">
        <div class="body" id="kmeMxtj" ref="kmeMxtj" style="width: 100%">
          <div style="width: 700px;margin: auto">
            <div style="width: 100%">
              <div class="header" style="text-align: center">
                <span style="font-size: 20px;font-weight: 600">科目二训练明细表</span>
              </div>
              <div>统计时间范围：{{timeF(minTime)}} 至 {{timeF(maxTime)}}</div>
                <table border="1" cellspacing="0" cellpadding="0" style="width: 100%">
                  <thead>
                  <tr>
                    <td>姓名</td>
                    <td>驾校</td>
                    <td>考车号</td>
                    <!--<td>结束时间</td>-->
                    <td>训练时间</td>
                    <td>收费（元）</td>
                  </tr>
                  </thead>
                  <tbody>
                  <tr v-for="(item,index) in list" :key="index">
                    <td>{{item.jlXm}}</td>
                    <td>{{item.jlJx}}</td>
                    <td>{{item.clBh}}</td>
                    <!--<td>{{item.jssj}}</td>-->
                    <td>{{item.sc}}</td>
                    <td>{{item.lcFy}}</td>
                  </tr>
                  <tr>
                    <td colspan="4">合计</td>
                    <td>{{addmoney}}元</td>
                  </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
      </div>
      <div slot='footer'>
        <Button type="default" @click="v.util.closeDialog(v)" style="color: #949494">取消</Button>
        <Button type="primary" @click="doPrint">打印</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
  import {mapMutations} from 'vuex'
  export default {
    name: '',
    data() {
      return {
        v: this,
        showModal: true,
        operate: "新增",
        editMode: false,
        minTime: '',
        maxTime: '',
        list: [],
        addmoney:0,
        param: {notShowLoading:'true',}
      }
    },
    computed:{
      printerName:function () {
        return this.$store.state.app.printerName
      }
    },
    created() {
      this.param = JSON.parse(JSON.stringify(this.$parent.param));
      this.param.pageSize = 999999;
      if (this.param.kssjInRange && this.param.kssjInRange !== '') {
        let a = this.param.kssjInRange.split(",");
        this.minTime = a[0];
        this.maxTime = a[1];
      } else {
        let now = new Date();
        let today = now.format("yyyy-MM-dd");
        this.minTime = today;
        this.maxTime = today;
      }
      delete this.param.pageNum;
      this.getData();
    },
    beforeDestroy(){
      this.SetPprintInnerHTML('')
    },
    methods: {
      ...mapMutations([
        'SetPprintInnerHTML'
      ]),
      timeF(val){
        return val.substring(0,10)
      },
      afterPager(list){
        this.addmoney = 0
        console.log(list);
        for (let r of list){
          this.addmoney += r.lcFy;
        }
      },
      doPrint(how) {
        var v = this
        this.SetPprintInnerHTML(document.getElementById("kmeMxtj").innerHTML)
        // return
        setTimeout(()=>{
          var myDoc = {
            documents: document, // 打印页面(div)们在本文档中
            copyrights: '杰创软件拥有版权  www.jatools.com', // 版权声明必须
            settings: {
              marginBottom: 2,
              marginLeft: 10,
              marginRight: 10,
              marginTop: 10,
              paperName:'a4',
              printer:v.AF.getPrintName()
            }
          };
          var jcp = getJCP();
          //jcp.printPreview(myDoc, false)
           jcp.print(myDoc, false)
        },50)
      },
      getData() {
        this.list = [];
        this.$http.post(this.apis.lcjl.QUERY, this.param).then((res) => {
          if (res.code == 200 && res.page.list) {
            this.list = res.page.list;
            this.afterPager(this.list);
          } else {
            this.$Message.error(res.message);
          }
        })
      },
      close() {
        this.$parent.componentName = ''
      }
    }
  }
</script>

<style>
  table td {
    text-align: center;
  }
</style>
