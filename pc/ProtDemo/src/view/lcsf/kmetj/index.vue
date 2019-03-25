<template>
  <div class="boxbackborder box_col">
    <!--<pager-tit title="科目二统计"></pager-tit>-->
    <Menu mode="horizontal" theme="light" active-name="mxtj" @on-select="MenuClick">
      <MenuItem name="mxtj">
        <Icon type="ios-paper"/>
         明细统计
      </MenuItem>
      <MenuItem name="jxtj">
        <Icon type="ios-paper"/>
         驾校统计
      </MenuItem>
      <MenuItem name="jltj">
        <Icon type="ios-paper"/>
         教练统计
      </MenuItem>
    </Menu>
    <component :is="componentName" lcKm="2"></component>
  </div>
</template>

<script>
  // import formData from './formModal.vue'
  import print from './print'
  //明细统计
  import mxtj from './mxtj'
  //驾校统计
  import jxtj from  '../jxtj'
  //教练统计
  import jltj from  '../jlytj'
  export default {
    name: 'char',
    components: {print,jxtj,mxtj,jltj},
    data() {
      return {
        v: this,
        apiRoot: this.apis.lcjl,
        choosedItem: null,
        componentName: 'mxtj',
        searchBarButtons: [
          {title: '打印', click: 'print'}
        ],
        dateRange: {
          kssj: ''
        },
        tableColumns: [
          {
            type: 'index2', align: 'center', minWidth: 80,
            render: (h, params) => {
              return h('span', params.index + (this.param.pageNum - 1) * this.param.pageSize + 1);
            }
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
          {title: '教练姓名', key: 'jlXm', searchKey: 'jlXmLike', minWidth: 90},
          {title: '时长', key: 'sc', append: '分钟', minWidth: 80, defaul: '0'},
          {title: '驾校/队号', key: 'jlJx', searchKey: 'jlJxLike', minWidth: 90},
          {title: '学员数量', key: 'xySl', minWidth: 90, defaul: '0'},
          {title: '计费类型', key: 'lcLx', minWidth: 90, dict: 'ZDCLK1048'},
          {title: '练车费用', key: 'lcFy', append: '元', minWidth: 90, defaul: '0'},
          // {title:'操作',render:(h,p)=>{
          //     let buttons = [];
          //     buttons.push(this.util.buildeditButton(this,h,p));
          //     buttons.push(this.util.buildDeleteButton(this,h,p.row.yhid));
          //     return h('div',buttons);
          //   }
          //   },

        ],
        pageData: [],
        param: {
            notShowLoading:'true',
          orderBy: 'jssj desc',
          total: 0,
          lcKm: '2',
          zhLike: '',
          pageNum: 1,
          pageSize: 8
        },
      }
    },
    created() {
      this.dateRange.kssj = [this.AF.trimDate() + ' 00:00:00', this.AF.trimDate() + ' 23:59:59']
      this.param.kssjInRange = this.AF.trimDate() + ' 00:00:00' + ',' + this.AF.trimDate() + ' 23:59:59'
      this.util.initTable(this);
    },
    methods: {
      parseTime(s) {
        s = parseInt(s);
        let h = parseInt(s / 60);
        let m = s % 60;
        let r = '';
        if (h != 0) r += h + '小时'
        return r + m + '分钟'
      },
        afterPager(list){
          for(let r of list){
              r.sc = this.parseTime(r.sc)
              r.kssj = r.kssj.substring(0,16)
              r.jssj = r.jssj.substring(0,16)
          }
        },
      MenuClick(name) {
        console.log(name);
        this.componentName = name
      },
      print() {
        console.log('print');
      }
    }
  }
</script>
