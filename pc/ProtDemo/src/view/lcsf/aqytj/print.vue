<template>
  <div>
    <Modal
      v-model="showModal"
      height="600"
      width="900"
      :closable='false'
      :mask-closable="false"
      :title="'安全员工作日志打印'">
      <div :style="{height:AF.getPageHeight()-480 +'px',overflow:'auto',width:'100%'}">

        <div class="body" id="aqytj" style="width: 100%">
          <div style="width: 700px;margin: auto">
            <div style="text-align: center"><span style="font-weight: 600;font-size: 20px">科目三模拟训练工作日志</span></div>
            <table border="1" cellpadding="0" cellspacing="0" style="width: 100%">
              <thead>
              <tr>
                <td rowspan="2">序号</td>
                <td rowspan="2">姓名</td>
                <td rowspan="2">车号</td>
                <td colspan="2">第一趟</td>
                <td colspan="2">第二趟</td>
                <td colspan="2">第三趟</td>
                <td colspan="2">第四趟</td>
                <td colspan="2">第五趟</td>
                <td colspan="2">第六趟</td>
                <td rowspan="2">合计</td>
              </tr>
              <tr>
                <td>人数</td>
                <td>金额</td>
                <td>人数</td>
                <td>金额</td>
                <td>人数</td>
                <td>金额</td>
                <td>人数</td>
                <td>金额</td>
                <td>人数</td>
                <td>金额</td>
                <td>人数</td>
                <td>金额</td>
              </tr>
              </thead>
              <tbody>
              <tr v-for="(item,index) in list" :key="index">
                <td>{{index+1}}</td>
                <td>{{item.zgXm}}</td>
                <td>{{item.clBh}}</td>
                <td>{{item.jls.length >= 1 ? item.jls[0].xySl : ''}}</td>
                <td>{{item.jls.length >= 1 ? item.jls[0].lcFy : ''}}</td>
                <td>{{item.jls.length >= 2 ? item.jls[1].xySl : ''}}</td>
                <td>{{item.jls.length >= 2 ? item.jls[1].lcFy : ''}}</td>
                <td>{{item.jls.length >= 3 ? item.jls[2].xySl : ''}}</td>
                <td>{{item.jls.length >= 3 ? item.jls[2].lcFy : ''}}</td>
                <td>{{item.jls.length >= 4 ? item.jls[3].xySl : ''}}</td>
                <td>{{item.jls.length >= 4 ? item.jls[3].lcFy : ''}}</td>
                <td>{{item.jls.length >= 5 ? item.jls[4].xySl : ''}}</td>
                <td>{{item.jls.length >= 5 ? item.jls[4].lcFy : ''}}</td>
                <td>{{item.jls.length >= 6 ? item.jls[5].xySl : ''}}</td>
                <td>{{item.jls.length >= 6 ? item.jls[5].lcFy : ''}}</td>
                <td>{{item.zj}}</td>
              </tr>
              <tr>
                <td colspan="15">总计</td>
                <td>{{total}}</td>
              </tr>
              </tbody>
            </table>
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
    name: 'char',
    data() {
      return {
        showModal: true,
        v: this,
        pagerUrl: this.apis.lcjl.getAllLog,
        choosedItem: null,
        componentName: '',
        list: [],
        dateRange: {
          kssj: ''
        },
        param: {},
        total: 0,
      }
    },
    created() {
      this.param = JSON.parse(JSON.stringify(this.$parent.param));
      this.param.pageSize = 999999;
      this.getData();
    },
    beforeDestroy() {
      this.SetPprintInnerHTML('')
    },
    methods: {
      ...mapMutations([
        'SetPprintInnerHTML'
      ]),
      doPrint(how) {
        var v = this
        this.SetPprintInnerHTML(document.getElementById("aqytj").innerHTML)
        setTimeout(() => {
          var myDoc = {
            documents: document, // 打印页面(div)们在本文档中
            copyrights: '杰创软件拥有版权  www.jatools.com', // 版权声明必须
            settings: {
              marginBottom: 2,
              marginLeft: 10,
              marginRight: 10,
              marginTop: 10,
              paperName: 'a4',
              portrait:false,
              printer: v.AF.getPrintName()
            }
          };
          var jcp = getJCP();
          jcp.print(myDoc, false)
        }, 50)
      },
      getData() {
        this.list = [];
        console.log(this.param.kssjInRange.length);
        if (this.param.kssjInRange.length > 26) {
          let sj = this.param.kssjInRange.split(',');
          let st = sj[0];
          let et = sj[1];
          // st += ' 00:00:00'
          // et += ' 23:59:59'
          this.param.kssjInRange = st + ',' + et;
        }
        this.$http.post(this.apis.lcjl.getAllLog, this.param).then((res) => {
          if (res.code == 200) {
            if (res.result) {
              this.list = res.result;
              for (let r of this.list) {
                this.total += parseInt(r.zj)
              }
            }
          } else {
            this.$Message.error(res.message);
          }
        })
      },
    }
  }
</script>
