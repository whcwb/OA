<template>
  <div>
    <Modal
      v-model="showModal"
      height="800"
      width="1800"
      :closable='false'
      :mask-closable="false"
      class-name="vertical-center-modal"
      :title="'练车统计'">
      <div style="overflow: auto;height: 100%;width:100%">
        <search-bar :parent="v" :show-create-button="false"></search-bar>
        <table-area :parent="v" :pager="false"></table-area>
        <br>
        <Row>
          <Col span="6">
            <span class="total">合计</span>
          </Col>
          <Col span="6">
            <span class="total">学员数量：{{xy}}</span>
          </Col>
          <Col span="6">
            <span class="total">总时长：{{sc}}分钟</span>
          </Col>
          <Col span="6">
            <span class="total">总费用：{{fy}}元</span>
          </Col>
        </Row>
        <!--<component :is="componentName"></component>-->
      </div>
      <div slot='footer'>
        <Button type="default" @click="close" style="color: #949494">关闭</Button>
      </div>
    </Modal>
    <component :is="componentName" :hisPrintMess="hisPrintMess"></component>
  </div>
</template>

<script>
  import print from '../comp/print'

  export default {
    name: 'char',
    components: {print},
    data() {
      return {
        compHisName: '',
        hisPrintMess: {},
        v: this,
        showModal: true,
        pagerUrl: this.apis.lcjl.QUERY,
        dateRange: {kssj: {}},
        choosedItem: null,
        componentName: '',
        tableColumns: [
          {title: '序号', type: 'index',fixed:'left'},
          {title: '开始时间', key: 'kssj', searchType: 'daterange',minWidth:100},
          {title: '结束时间', key: 'jssj',minWidth:100},
          {title: '时长', key: 'sc', append: '分钟',minWidth:30,defaul:'0'},
          {title: '计费类型', key: 'lcLx',minWidth:90,dict:'ZDCLK1048'},
          {title: '费用', key: 'lcFy', append: '元',minWidth:30,defaul:'0'},
          {title: '驾校/队号', key: 'jlJx', searchKey: 'jlJxLike',minWidth:80},
          {title: '教练姓名', key: 'jlXm', searchKey: 'jlXmLike',minWidth:30},
          {title: '安全员姓名', key: 'zgXm', searchKey: 'zgXmLike',minWidth:30},
          {title: '练车科目', key: 'lcKm', dict: 'km',minWidth:40},
          {title: '学员数量', key: 'xySl',minWidth:30,defaul:'0'},
          {
            title: '操作',fixed:'right', render: (h, p) => {
              let buttons = [];
              buttons.push(this.util.buildButton(this, h, 'success', 'ios-print', '补打', () => {
                this.hisPrintMess = p.row
                this.componentName = 'print'
              }));
              return h('div', buttons);
            }
          },
        ],
        pageData: [],
        param: {
          total: 0,
          zhLike: '',
          pageNum: 1,
          pageSize: 100
        },
        sc: 0,
        xy: 0,
        fy: 0,
      }
    },
    created() {
      this.dateRange.kssj = [this.AF.trimDate() + ' 00:00:00', this.AF.trimDate() + ' 23:59:59']
      this.param.kssjInRange = this.AF.trimDate() + ' 00:00:00' + ',' + this.AF.trimDate() + ' 23:59:59'
      this.param.lcClId = this.$parent.clId
      this.tableHeight = window.innerHeight - 600
      this.util.initPageSize(this);
      this.util.fillTableColumns(this)
      this.util.getPageData(this)
    },
    methods: {
      print(item) {
        console.log('print');
        console.log(item);
      },
      afterPager(list) {
        this.sc = 0;
        this.xy = 0;
        this.fy = 0;
        for (let r of list) {
          if (r.xySl) this.xy += r.xySl
          if (r.sc) this.sc += r.sc
          if (r.lcFy) this.fy += r.lcFy
        }
      },
      close() {
        this.showModal = false;
        let v = this;
        setTimeout((t) => {
          v.$parent.componentName = "";
        }, 200)
      }
    }
  }
</script>
<style>
  .total {
    color: red;
    font-size: 18px;
    font-weight: 600;
  }
</style>
