<template>
  <div>
    <Modal
      v-model="showModal"
      height="600"
      width="900"
      :closable='false'
      :mask-closable="false"
      :title="'驾校统计'">
      <div :style="{height:AF.getPageHeight()-480 +'px',overflow:'auto',width:'100%'}">
        <div class="body" id="JXtj" ref="JXtj" style="width: 100%">
          <div style="width: 700px;margin: auto">
            <div class="header" style="text-align: center">
              <span style="font-size: 20px;font-weight: 600">驾校统计</span>
            </div>
            <div><span style="font-size:12px">统计时间范围：{{minTime}}至{{maxTime}}</span></div>
            <div>
              <table border="1" cellspacing="0" cellpadding="0" style="width: 100%">
                <thead>
                <tr>
                  <td>序号</td>
                  <td>驾校</td>
                  <td>时长</td>
                  <td>收费（元）</td>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(item,index) in list" :key="index">
                  <td>{{index+1}}</td>
                  <td>{{item.jlJx}}</td>
                  <td>{{item.lcSc}}</td>
                  <td>{{item.lcFy}}</td>
                </tr>
                <tr>
                  <td colspan="3">合计</td>
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
        param: {},
        addmoney: 0
      }
    },
    created() {
      this.param = JSON.parse(JSON.stringify(this.$parent.param));
      this.param.pageSize = 999999;
      let now = new Date();
      let today = now.format("yyyy-MM-dd");
      console.log(today);
      this.minTime = today;
      this.maxTime = today;
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
      afterPager(list) {
        this.addmoney = 0
        console.log(list);
        for (let r of list) {
          this.addmoney += r.lcFy;
        }
      },
      getData() {
        this.list = [];
        this.$http.post(this.apis.lcjl.jxtj, this.param).then((res) => {
          if (res.code == 200) {
            if (res.result) {
              this.list = res.result;
              this.afterPager(this.list)
            } else {
              this.$Message.info('暂无数据');
            }
          } else {
            this.$Message.error(res.message);
          }
        })
      },

      getTime(s) {
        s = parseInt(s);
        let h = parseInt(s / 60);
        let m = s % 60;
        let r = '';
        if (h != 0) r += h + '小时'
        return r + m + '分钟'
      },
      doPrint(how) {
        var v = this
        this.SetPprintInnerHTML(document.getElementById("JXtj").innerHTML)
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
          jcp.print(myDoc, false)
          // jcp.printPreview(myDoc, false);
        },50)
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
