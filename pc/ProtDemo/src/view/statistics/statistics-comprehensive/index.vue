<template>
  <div class="box_col">
    <!--<div style="height: 600px">-->
    <!--<awb></awb>-->
    <!--</div>-->
    <pager-tit title="年收入统计"></pager-tit>
    <row style="margin-top: 10px">
      <Col span="5" style="margin-right: 10px">
        <DatePicker type="year" split-panels @on-change="getNf" placeholder="请选择年份（默认当前年份）"
                    style="width: 100%"></DatePicker>
      </Col>
      <Col span="1" style="margin-right: 10px">
        <Button type="primary" @click="getNf(year)">
          <Icon type="md-search"></Icon>
        </Button>
      </Col>
      <Col span="2">
        <Tooltip content="导出Excel" placement="right-start">
          <Button type="primary" icon="md-cloud-download" @click="excel"></Button>
        </Tooltip>
      </Col>
    </row>
    <div class="box_col_100" style="margin-top: 10px">
      <Table size="small" :loading="loading" :height="AF.getPageHeight()-280" stripe :columns="columns1" :data="data1"></Table>

    </div>
  </div>
</template>

<script>
  import http from '@/axios/index';
  import awb from "./comp/bar"

  export default {
    name: "index",
    components: {
      awb
    },
    data() {
      return {
        loading:true,
        columns1: [
          {
            title: '机构',
            key: 'jgmc',
            align: 'center',
            width: 120,
            fixed: 'left',
            filterMultiple: false,
            filterMethod(value, row) {
              if (value == 1) {
                return row.name === 'Joe';
              } else if (value == 2) {
                return row.name === 'John Brown';
              }
            }
          },
          {
            renderHeader: (h, p) => {
              return h('div', this.param.startTime + " —— " + this.param.endTime)
            },
            //title: this.shijian,
            align: 'center',
            children: [
              {
                title: '1-12合计',
                key: 'hj3',
                minWidth: 80,
                align: 'center',
                sortable: true
              },
              {
                title: '1月',
                key: 'one',
                minWidth: 80,
                align: 'center',
              }
              ,
              {
                title: '人数',
                key: 'oneCount',
                minWidth: 80,
                align: 'center'
              },
              {
                title: '2月',
                key: 'two',
                minWidth: 80,
                align: 'center',
              },
              {
                title: '人数',
                key: 'twoCount',
                minWidth: 80,
                align: 'center'
              },
              {
                title: '3月',
                key: 'three',
                minWidth: 80,
                align: 'center',
              },
              {
                title: '人数',
                key: 'threeCount',
                minWidth: 80,
                align: 'center'
              },
              {
                title: '4月',
                key: 'four',
                minWidth: 80,
                align: 'center',
              },
              {
                title: '人数',
                key: 'fourCount',
                minWidth: 80,
                align: 'center'
              },
              {
                title: '5月',
                key: 'five',
                minWidth: 80,
                align: 'center',
              },
              {
                title: '人数',
                key: 'fiveCount',
                minWidth: 80,
                align: 'center'
              },
              {
                title: '6月',
                key: 'six',
                minWidth: 80,
                align: 'center',
              },
              {
                title: '人数',
                key: 'sixCount',
                minWidth: 80,
                align: 'center'
              },
              {
                title: '7月',
                key: 'seven',
                minWidth: 80,
                align: 'center',
              }
              ,
              {
                title: '人数',
                key: 'sevenCount',
                minWidth: 80,
                align: 'center'
              }
              ,
              {
                title: '8月',
                key: 'eight',
                minWidth: 80,
                align: 'center',
              },
              {
                title: '人数',
                key: 'eightCount',
                minWidth: 80,
                align: 'center'
              },
              {
                title: '9月',
                key: 'nine',
                minWidth: 80,
                align: 'center',
              },
              {
                title: '人数',
                key: 'nineCount',
                minWidth: 80,
                align: 'center'
              },
              {
                title: '10月',
                key: 'ten',
                minWidth: 80,
                align: 'center',
              },
              {
                title: '人数',
                key: 'tenCount',
                minWidth: 80,
                align: 'center'
              },
              {
                title: '11月',
                key: 'eve',
                minWidth: 80,
                align: 'center',
              },
              {
                title: '人数',
                key: 'eveCount',
                minWidth: 80,
                align: 'center'
              }, {
                title: '12月',
                key: 'twn',
                minWidth: 80,
                align: 'center',
              },
              {
                title: '人数',
                key: 'twnCount',
                minWidth: 80,
                align: 'center'
              },
            ]
          }
        ],
        data1: [],
        param: {
          startTime: "2019-01-01",
          endTime: '2019-12-31',
          jgdm: '',
          lx: []
        },
        year: ''
      }
    },
    created() {
      this.getZHTJList()
    },
    methods: {
      getNf(gsh, date) {
        // console.log(gsh);
        // console.log(date);
        this.year = gsh
        this.param.startTime = gsh + '-01-01';
        this.param.endTime = gsh + '-12-31';
        this.data1 = [];
        this.getZHTJList();
      },
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
      getZHTJList() {
        this.data1 = [];
        // this.handleSpinCustom();
        if (this.param.startTime == '-01-01') {
          this.param.startTime = this.AF.getYear() + '-01-01';
          this.param.endTime = this.AF.getYear() + '-12-31';
        }
        //this.$http.post(this.apis.COUNT.ZHTJ,this.param).then( (res)=>{
        // this.$Spin.show({
        //   render: (h) => {
        //     return h('div', [
        //       h('Icon', {
        //         'class': 'demo-spin-icon-load',
        //         props: {
        //           type: 'ios-loading',
        //           size: 30
        //         }
        //       }),
        //       h('div', 'Loading')
        //     ])
        //   }
        // });
        this.loading=true
        this.$http.post('/api/data/getAllIn', this.param).then((res) => {
          // console.log(res);
          if (res.code == 200) {
            res.result.forEach((item, index) => {
              var ps = {}
              ps.jgmc = item.jgmc;
              if (item.stu != null && item.stu.length > 0) {
                ps.one = item.stu[0].count;
                ps.oneCount = item.stu[0].signUp;
                ps.two = item.stu[1].count;
                ps.twoCount = item.stu[1].signUp;
                ps.three = item.stu[2].count;
                ps.threeCount = item.stu[2].signUp;
                ps.four = item.stu[3].count;
                ps.fourCount = item.stu[3].signUp;
                ps.five = item.stu[4].count;
                ps.fiveCount = item.stu[4].signUp;
                ps.six = item.stu[5].count;
                ps.sixCount = item.stu[5].signUp;
                ps.seven = item.stu[6].count;
                ps.sevenCount = item.stu[6].signUp;
                ps.eight = item.stu[7].count;
                ps.eightCount = item.stu[7].signUp;
                ps.nine = item.stu[8].count;
                ps.nineCount = item.stu[8].signUp;
                ps.ten = item.stu[9].count;
                ps.tenCount = item.stu[9].signUp;
                ps.eve = item.stu[10].count;
                ps.eveCount = item.stu[10].signUp;
                ps.twn = item.stu[11].count;
                ps.twnCount = item.stu[11].signUp;
              }
              ps.hj3 = item.all;
              this.data1.push(ps)
            })
            res.result.forEach((item, index) => {
              this.tjzje += item.chargeFee;
            })
          } else {
            this.$Message.error(res.message);
          }
          this.loading = false
        })
      },
      excel() {
        if (this.param.startTime == '-01-01') {
          this.param.startTime = this.AF.getYear() + '-01-01';
          this.param.endTime = this.AF.getYear() + '-12-31';
        }
        window.open(http.url + '/pub/getNdbb?startTime=' + this.param.startTime + '&endTime=' + this.param.endTime, '_blank');
      }
    }
  }
</script>

<style scoped>

</style>
