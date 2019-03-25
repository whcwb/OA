<template>
  <div class="boxbackborder box_col" style="padding-top:16px">
      <search-bar :parent="v" :show-create-button="false" :buttons="searchBarButtons"
                  @print="componentName = 'print'"
                  @exportExcel="exportExcel"
      ></search-bar>
    <table-area :parent="v" :TabHeight="AF.getPageHeight()-380" :pager="false"></table-area>
    <Row>
        <!--<Col span="1" align="center" >-->
          <!--<div @click="AnYearTJ" style="padding: 10px;border-radius: 35px;background-color: #10AEFF;color: white;font-size: 28px">年</div>-->
        <!--</Col>-->
        <!--<Col span="1" align="center">-->
          <!--<div @click="AnJdTJ" style="padding: 10px;border-radius: 35px;background-color: #10AEFF;color: white;font-size: 28px">季</div>-->
        <!--</Col>-->
        <!--<Col span="1" align="center">-->
          <!--<div @click="AnMonTJ" style="padding: 10px;border-radius: 35px;background-color: #10AEFF;color: white;font-size: 28px">月</div>-->
        <!--</Col>-->
        <!--<Col span="1" align="center">-->
          <!--<div @click="AnWeekTJ" style="padding: 10px;border-radius: 35px;background-color: #10AEFF;color: white;font-size: 28px">周</div>-->
        <!--</Col>-->
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
  import moment from 'moment'

  export default {
    name: 'char',
    components: {print},
    data() {
      return {
        v: this,
        addmoney:0,
        apiRoot: this.apis.lcjl,
        choosedItem: null,
        componentName: '',
        searchBarButtons: [
          {title: '打印', click: 'print'},
          {title: '导出', click: 'exportExcel'}
        ],
        dateRange: {
          kssj: ''
        },
        tableColumns: [
          {
            type: 'index', align: 'center', minWidth: 80,
            // render: (h, params) => {
            //   return h('span', params.index + (this.param.pageNum - 1) * this.param.pageSize + 1);
            // }
          },
          {
            title: '车辆编号', key: 'clBh', searchKey: 'clBh', minWidth: 90, render: (h, p) => {
              return h('Button', {
                props: {
                  type: 'error',
                  size: 'small'
                },
                style: {
                  borderRadius: '15px'
                }
              }, p.row.clBh)
            }
          },
          {title: '开始时间', key: 'kssj', searchType: 'daterange', minWidth: 180},
          {title: '结束时间', key: 'jssj', minWidth: 180},
          {title: '安全员姓名', key: 'zgXm', searchKey: 'zgXmLike', minWidth: 100},
          {title: '教练姓名', key: 'jlXm', searchKey: 'jlXmLike', minWidth: 90},
          {title: '时长', key: 'sc', append: '分钟', minWidth: 80, defaul: '0'},
          {title: '驾校/队号', key: 'jlJx', searchKey: 'jlJxLike', minWidth: 90},
          {title: '学员数量', key: 'xySl', minWidth: 90, defaul: '0'},
          {title: '计费类型', key: 'lcLx', minWidth: 90, dict: 'ZDCLK1048'},
          {title: '练车费用', key: 'lcFy', append: '元', minWidth: 90, defaul: '0', fixed: 'right'},
          // {title:'操作',render:(h,p)=>{
          //     let buttons = [];
          //     buttons.push(this.util.buildeditButton(this,h,p));
          //     buttons.push(this.util.buildDeleteButton(this,h,p.row.yhid));
          //     return h('div',buttons);
          //   }
          //   },

        ],
        pageData: [],
        specialPageSize:99999999,
        param: {
          notShowLoading:'true',
          orderBy: 'jssj desc',
          total: 0,
          lcKm: '3',
          zhLike: ''
        },
      }
    },
    created(){
      this.dateRange.kssj = [this.AF.trimDate() + ' 00:00:00', this.AF.trimDate() + ' 23:59:59']
      this.param.kssjInRange = this.AF.trimDate() + ' 00:00:00' + ',' + this.AF.trimDate() + ' 23:59:59'
      this.util.initTable(this);
    },
    methods: {
        AnYearTJ(){
          let startTime = moment().subtract(1, 'year').format('YYYY[-]MM[-]DD HH[:]mm[:]ss')
          let endTime = moment().format('YYYY[-]MM[-]DD HH[:]mm[:]ss')
          this.param.kssjInRange = startTime + ',' + endTime
          this.util.initTable(this);
        },
        AnJdTJ(){
          let startTime = moment().subtract(3, 'months').format('YYYY[-]MM[-]DD HH[:]mm[:]ss')
          let endTime = moment().format('YYYY[-]MM[-]DD HH[:]mm[:]ss')
          this.param.kssjInRange = startTime + ',' + endTime
          this.util.initTable(this);
        },
        AnMonTJ(){
          let startTime = moment().subtract(1, 'months').format('YYYY[-]MM[-]DD HH[:]mm[:]ss')
          let endTime = moment().format('YYYY[-]MM[-]DD HH[:]mm[:]ss')
          this.param.kssjInRange = startTime + ',' + endTime
          this.util.initTable(this);
        },
        AnWeekTJ(){
          let startTime = moment().subtract(7, 'day').format('YYYY[-]MM[-]DD HH[:]mm[:]ss')
          let endTime = moment().format('YYYY[-]MM[-]DD HH[:]mm[:]ss')
          this.param.kssjInRange = startTime + ',' + endTime
          this.util.initTable(this);
        },
        exportExcel(){
            let p = '';
            for (let k in this.param){
                p += '&'+k + '=' +this.param[k];
            }
            p = p.substr(1);
            window.open(this.apis.url + '/pub/pagerExcel?'+p);
        },
      afterPager(list){
        this.addmoney = 0
        for (let r of list){
            r.kssj = r.kssj.substring(0,16)
            r.jssj = r.jssj.substring(0,16)
          this.addmoney += r.lcFy;
        }
      },
      print() {
        console.log('print');
      }
    }
  }
</script>
