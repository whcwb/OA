<template>
  <div class="boxbackborder box_col" style="padding-top:16px">
    <!--<pager-tit title="驾校统计"></pager-tit>-->
    <div style="text-align: right;width: 100%">
      <DatePicker v-model="dateRange['tjsj']"
                  @on-change="param['tjsj'] = v.util.dateRangeChange(dateRange['tjsj'])" confirm format="yyyy-MM-dd"
                  type="daterange" :placeholder="'请输入'" style="width: 200px"></DatePicker>
      <Button type="primary" @click="v.util.getPageData(v)" style="margin-left: 16px;">
        <Icon type="md-search"></Icon>
        <!--查询-->
      </Button>
      <Button type="primary" @click="componentName = 'print'" style="margin-left: 16px;">
        打印
      </Button>
      <Button type="primary" @click="exportExcel" style="margin-left: 16px;">
        导出
      </Button>
    </div>
    <table-area :parent="v" :TabHeight="AF.getPageHeight()-320" :pager="false"></table-area>
    <Row>
      <Col span="24" align="right">
        <div style="font-size: 24px;font-weight: 600">
          合计：<span style="color: #ed3f14"> {{addmoney}} </span> 元
        </div>
      </Col>
    </Row>
    <component :is="componentName"></component>
  </div>
</template>

<script>
  // import formData from './formModal.vue'
  import print from './print'

  export default {
    name: 'char',
    components: {print},
    props: {
      lcKm: {
        type: String,
        default: ''
      }
    },
    data() {
      return {
        v: this,
        addmoney: 0,
        pagerUrl: this.apis.lcjl.jxtj,
        choosedItem: null,
        componentName: '',
        searchBarButtons: [
          {title: '打印', click: 'print'}
        ],
        dateRange: {
          tjsj: ''
        },
        tableColumns: [
          {title: '序号', type: 'index'},
          {title: '驾校', key: 'jlJx'},
          {title: '时长', key: 'lcSc', minWidth: 80, defaul: '0'},
          {title: '收费（元）', key: 'lcFy', append: '元', minWidth: 90, defaul: '0'},
        ],
        pageData: [],
        pager: false,
        specialPageSize: 9999,
        param: {
          total: 0,
          zhLike: '',
          pageNum: 1,
          lcKm: '',
          pageSize: 8
        },
      }
    },
    created() {
      this.param.lcKm = this.lcKm
      this.dateRange.tjsj = [this.AF.trimDate() + ' 00:00:00', this.AF.trimDate() + ' 23:59:59']
      this.param.tjsj = this.AF.trimDate() + ' 00:00:00' + ',' + this.AF.trimDate() + ' 23:59:59'
      this.util.initTable(this);
    },
    methods: {
      // parseTime(s) {
      //   s = parseInt(s);
      //   let h = parseInt(s / 60);
      //   let m = s % 60;
      //   let r = '';
      //   if (h != 0) r += h + '小时'
      //   return r + m + '分钟'
      // },
        exportExcel(){
            let p = '';
            for (let k in this.param){
                p += '&'+k + '=' +this.param[k];
            }
            p = p.substr(1);
            window.open(this.apis.url + '/pub/jxtjExcel?'+p);
        },
      afterPager(list) {
        this.addmoney = 0
        for (let r of list) {
          // r.lcSc = this.parseTime(r.lcSc)
          this.addmoney += parseInt(r.lcFy);
        }
      },
      getTime(s) {
        s = parseInt(s);
        let h = parseInt(s / 60);
        let m = s % 60;
        let r = '';
        if (h > 0) r += h + '小时'
        return r + m + '分钟'
      }
    }
  }
</script>
