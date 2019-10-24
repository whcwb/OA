<template>
  <div class="box_col">
    <Row>
      <pager-tit title="优惠统计"></pager-tit>
    </Row>
    <Row style="margin-bottom: 10px">
      <Col span="6">
        <DatePicker type="daterange" split-panels @on-change="getNf" clearable @on-clear="getNf"
                    placeholder="请选择日期（默认当天）" style="width: 250px"></DatePicker>
      </Col>
      <Col span="2">
        <Button type="primary" @click="getNf([param.reduceCheckTimeGte,param.reduceCheckTimeLte])">
          <Icon type="md-search"></Icon>
        </Button>
      </Col>
    </Row>
    <Table size="small" :loading="loading" :height="AF.getPageHeight()-320" stripe :columns="columns1" :data="data1"></Table>
    <div class="box_col_auto" v-if="data1.length>0" style="font-size: 18px;font-weight: 600">
      <span>合计：优惠人数</span><span style="font-size: 22px;font-weight: 600;color: #ed4014;margin-right: 16px">{{data1.length}}人</span>
      <span>实付金额共计：</span><span style="font-size: 22px;font-weight: 600;color: #ed4014;margin-right: 16px">{{addRealPay}}元</span>
      <span>优惠金额共计：</span><span style="font-size: 22px;font-weight: 600;color: #ed4014;margin-right: 16px">{{addReducePrice}}元</span>
    </div>
  </div>
</template>

<script>
  export default {
    name: "",
    data() {
      return {
        loading:true,
        tjzje: 0,
        addRealPay: 0,
        addReducePrice: 0,
        columns1: [
          {
            type: 'index',
            title: '序号',
            align: 'center',
            fixed: 'left',
            minWidth: 80
          },
          {
            title: '姓名',
            key: 'name',
            align: 'center',
            minWidth: 120
          },
          {
            title: '身份证号',
            key: 'idCardNo',
            align: 'center',
            minWidth: 180
          },
          {
            title: '推荐人',
            key: 'referrer',
            align: 'center',
            minWidth: 140,
            render: (h, p) => {
              let a = p.row.referrer.split('-')
              return h('div', a[0])
            }
          },
          {
            title: '实付金额',
            key: 'realPay',
            align: 'center',
            minWidth: 150
          },
          {
            title: '优惠金额',
            key: 'reducePrice',
            align: 'center',
            minWidth: 150
          },
          {
            title: '优惠项目',
            key: 'reduceName',
            align: 'center',
            minWidth: 100
          },
          {
            title: '车型',
            key: 'carType',
            align: 'center',
            minWidth: 100
          },
          {
            title: '报名点',
            key: 'jgmc',
            align: 'center',
            minWidth: 100

          },
          {
            title: '审核时间',
            key: 'reduceCheckTime',
            align: 'center',
            minWidth: 180,
            render: (h, p) => {
              let a = p.row.reduceCheckTime.substring(0, 10);
              return h('div', a);
            }
          },
        ],
        data1: [],
        param: {
          pageSize: 99999,
          reduceCheckTimeGte: this.AF.trimDate() + " 00:00:00",
          reduceCheckTimeLte: this.AF.trimDate() + " 23:59:59"
        },
      }
    },
    created() {
      this.getBmdList();
      // this.getNSRList();
      // this.getNZCList();
    },
    methods: {
      handleSpinCustom() {
        this.$Spin.show({
          render: (h) => {
            return h('div', [
              h('Icon', {
                'class': 'demo-spin-icon-load',
                props: {
                  type: 'ios-loading',
                  size: 88
                }
              }),
              h('div', 'Loading')
            ])
          }
        });
      },
      getTJ(st) {
        this.param.jgdm = st;
        this.data1 = [];
        this.getBmdList()
      },
      getNf(gsh, date) {
        // console.log(gsh);
        // console.log(date);
        this.param.reduceCheckTimeGte = gsh[0] + " 00:00:00";
        this.param.reduceCheckTimeLte = gsh[1] + " 23:59:59";
        this.data1 = [];
        this.getBmdList();
      },
      getBmdList() {
        // this.handleSpinCustom();
        this.loading=true
        this.$http.post(this.apis.TRAINEE.PAGER, this.param).then((res) => {
          this.loading=false
          if (res.code === 200) {
            this.data1 = res.page.list;
            this.addRealPay = 0;
            this.addReducePrice = 0;
            res.page.list.forEach((item, index) => {
              this.addRealPay = this.addRealPay + item.realPay
              this.addReducePrice = this.addReducePrice + item.reducePrice
            })

          }
        }).catch((error) => {
        })
      },

    }
  }
</script>

<style scoped>
  .tjsize {
    font-size: 24px;
  }
</style>

