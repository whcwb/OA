<template>
  <div class="boxbackborder box_col" style="padding-top:16px">
    <!--<pager-tit title="教练员统计"></pager-tit>-->
    <div style="text-align: right;width: 100%">
      <DatePicker v-model="dateRange.kssj"
                  @on-change="param.kssjInRange = v.util.dateRangeChange(dateRange.kssj)" confirm format="yyyy-MM-dd"
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
    <!--<search-bar :parent="v" :show-create-button="false" :buttons="searchBarButtons" @print="componentName = 'print'" :show-search-button="false"></search-bar>-->
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
        addmoney:0,
        pagerUrl: this.apis.lcjl.jlTj,
        choosedItem: null,
        componentName: '',
        searchBarButtons: [
          {title: '打印', click: 'print'}
        ],
        dateRange: {
          kssj: ''
        },
        tableColumns: [
          {title: '姓名', key: 'jlXm'},
          {title: '驾校', key: 'jlJx'},
          {title: '时长', key: 'sc', minWidth: 80, defaul: '0'},
          {title: '收费（元）', key: 'zj', append: '元', minWidth: 90, defaul: '0'},
          // {title:'操作',render:(h,p)=>{
          //     let buttons = [];
          //     buttons.push(this.util.buildeditButton(this,h,p));
          //     buttons.push(this.util.buildDeleteButton(this,h,p.row.yhid));
          //     return h('div',buttons);
          //   }
          //   },

        ],
        pageData: [],
        pager: false,
        specialPageSize: 9999,
        param: {
          total: 0,
          zhLike: '',
          lcKm: '',
          pageNum: 1,
          pageSize: 8
        },
      }
    },
    created() {
      this.param.lcKm = this.lcKm
      this.dateRange.kssj = [this.AF.trimDate() + ' 00:00:00', this.AF.trimDate() + ' 23:59:59']
      this.param.kssjInRange = this.AF.trimDate() + ' 00:00:00' + ',' + this.AF.trimDate() + ' 23:59:59'
      this.util.initTable(this);
    },
    methods: {
        exportExcel(){
            let p = '';
            for (let k in this.param){
                p += '&'+k + '=' +this.param[k];
            }
            p = p.substr(1);
            window.open(this.apis.url + '/pub/jlExcel?'+p);
        },
      parseTime(s) {
        s = parseInt(s);
        let h = parseInt(s / 60);
        let m = s % 60;
        let r = '';
        if (h != 0) r += h + '小时'
        return r + m + '分钟'
      },
      afterPager(list) {
        this.addmoney = 0
        for (let r of list) {
          r.sc  = this.parseTime(r.sc)
          this.addmoney += r.zj;
        }
      },
      getTime(s) {
        s = parseInt(s);
        let h = parseInt(s / 60);
        let m = s % 60;
        let r = '';
        if (h != 0) r += h + '小时'
        return r + m + '分钟'
      }
    }
  }
</script>
