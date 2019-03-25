<template>
  <div class="boxbackborder box_col" style="padding-top:16px">
    <div style="text-align: right;width: 100%">
      <DatePicker v-model="dateRange.kssj"
                  @on-change="param.kssjInRange = v.util.dateRangeChange(dateRange.kssj)" confirm format="yyyy-MM-dd"
                  type="daterange" :placeholder="'请输入'" style="width: 200px"></DatePicker>
      <Button type="primary" @click="getData" style="margin-left: 16px;">
        <Icon type="md-search"></Icon>
        <!--查询-->
      </Button>
      <Button type="primary" @click="doPrint" style="margin-left: 16px;">打印</Button>
      <Button type="primary" @click="exportExcel" style="margin-left: 16px;">导出</Button>
    </div>
    <div class="box_col_100" id="page1" style="width: 100%">
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
    <component :is="componentName"></component>
  </div>
</template>

<script>

  import print from './print'

  export default {
    name: 'char',
    components: {print},
    data() {
      return {
        v: this,
        pagerUrl: this.apis.lcjl.getAllLog,
        choosedItem: null,
        componentName: '',
        list: [],
        dateRange: {
          kssj: ''
        },
        param: {
          lcKm: '3',
          total: 0,
          zhLike: '',
          pageNum: 1,
          pageSize: 8
        },
        total: 0,
      }
    },
    created() {
      this.dateRange.kssj = [this.AF.trimDate() + ' 00:00:00', this.AF.trimDate() + ' 23:59:59']
      this.param.kssjInRange = this.AF.trimDate() + ' 00:00:00' + ',' + this.AF.trimDate() + ' 23:59:59'
      this.getData();
    },
    methods: {
      exportExcel() {
        let p = '';
        for (let k in this.param) {
          p += '&' + k + '=' + this.param[k];
        }
        p = p.substr(1);
        window.open(this.apis.url + '/pub/aqyExcel?' + p);
      },
      doPrint(how) {
        this.componentName = 'print';
      },
      getData() {
        this.list = [];
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
