<template>
  <div class="box_col">
    <!--<div style="height: 600px">-->
    <!--<awb></awb>-->
    <!--</div>-->
    <Row>
      <pager-tit title="报名费统计"></pager-tit>
    </Row>
    <Row style="margin-bottom: 10px">
      <Col span="5" style="margin-right: 10px">
        <!--<DatePicker type="daterange" @on-change="getNf" confirm placement="bottom-end" placeholder="选择日期（默认当天）" style="width: 250px"></DatePicker>-->
        <DatePicker type="daterange" split-panels @on-change="getNf" clearable @on-clear="getNf"
                    placeholder="请选择日期（默认当天）" style="width: 100%"></DatePicker>
      </Col>
      <!--<Col span="2">-->
        <!--<Button type="primary" @click="getNf([param.startTime,param.endTime])">-->
          <!--<Icon type="md-search"></Icon>-->
        <!--</Button>-->
      <!--</Col>-->

      <Col span="3" style="margin-right: 20px">
        <Select v-model="param.bmd" clearable style="width:100%;" @on-change="getTJ" @on-clear="getTJ">
          <Option v-for="item in dictList.bmd.data" :value="item.value" :key="item.value">{{ item.label }}</Option>
        </Select>
      </Col>
      <Col span="5">
        <CheckboxGroup v-model="param.lx" @on-change="getlx">
          <Checkbox value="10" label="10">直属队</Checkbox>
          <Checkbox value="20" label="20">挂靠队</Checkbox>
          <Checkbox value="30" label="30">承包队</Checkbox>
        </CheckboxGroup>
      </Col>

      <Col span="1" style="margin-right: 10px">
        <Button type="primary" @click="getNf([param.startTime,param.endTime])">
          <Icon type="md-search"></Icon>
        </Button>
      </Col>

      <Col span="2">
        <Tooltip content="导出Excel" placement="right-start">
          <Button type="primary" icon="md-cloud-download" @click="excel"></Button>
        </Tooltip>
      </Col>
    </Row>
    <div class="box_col_100">
      <Table size="small" :loading="loading" :height="AF.getPageHeight()-280" stripe :columns="columns1" :data="data1" stripe></Table>
    </div>
    <Row class="tjsize">
      <Col span="5">
        <Tag color="success">合计金额</Tag>
        {{this.tjzje}}元
      </Col>
      <Col>
        <Tag color="warning">总人数</Tag>
        {{data1.length}}人
      </Col>
    </Row>
  </div>
</template>

<script>
  // import awb from "./comp/bar"
  import http from '@/axios/index';
  export default {
    //   name: "",
    // components:{
    //   awb
    // },
    data() {
      return {
        loading:true,
        tjzje: 0,
        columns1: [
          {
            type: 'index',
            title: '序号',
            align: 'center',
            minWidth: 20
          },
          {
            title: '姓名',
            key: 'traineeName',
            align: 'center',
            // minWidth: 50
          },
          {
            title: '身份证号',
            key: 'idCardNo',
            align: 'center',
            // minWidth: 140
          },
          {
            title: '票据编号',
            key: 'pjbh',
            align: 'center',
            // minWidth: 120,
            render: (h, p) => {
              if (p.row.pjbh != '') {
                let a = p.row.pjbh.split('-')
                return h('div', a[0] + '-' + a[1])
              }
            }
          },
          {
            title: '收入金额',
            key: 'chargeFee',
            align: 'center',
            // minWidth: 60
          },
          {
            title: '收入项目',
            key: 'chargeName',
            align: 'center',
            // minWidth: 100
          },
          {
            title: '车型',
            key: 'carType',
            align: 'center',
            // minWidth: 40
          },
          {
            title: '报名点',
            key: 'chargeSource',
            align: 'center',
            // minWidth: 90
          },
          {
            title: '时间',
            key: 'chargeTime',
            align: 'center',
            // minWidth: 120,
            render: (h, p) => {
              let a = p.row.chargeTime.substring(0, 10);
              return h('div', a);
            }
          },
          {
            title: '创建人',
            key: 'cjr',
            align: 'center',
            // minWidth: 130
          },
          {
            title: '创建时间',
            key: 'cjsj',
            align: 'center',
            // minWidth: 120,
            render: (h, p) => {
              let a = p.row.cjsj.substring(0, 10);
              return h('div', a);
            }
          }
        ],
        data1: [],
        param: {
          startTime: this.AF.trimDate(),
          endTime: this.AF.trimDate(),
          jgdm: '',
          lx: []
        },
        dictList: {
          carType: {
            code: 'ZDCLK0040',
            data: []
          },
          bmd: {
            code: '',
            data: []
          },
        },
      }
    },
    created() {
      this.getBmdList();
      // this.getNSRList();
      // this.getNZCList();
      this.getSRTJList()
    },
    methods: {
      excel() {
        if (this.param.startTime == '-01-01') {
          this.param.startTime = this.AF.getYear() + '-01-01';
          this.param.endTime = this.AF.getYear() + '-12-31';
        }
        window.open(http.url + `/pub/exportBranchSignUp?startTime=${this.param.startTime}&endTime=${this.param.endTime}&jgdm=${this.param.jgdm}&lx=${this.param.lx}`, '_blank');
      },
      handleSpinCustom() {
        // this.$Spin.show({
        //   render: (h) => {
        //     return h('div', [
        //       h('Icon', {
        //         'class': 'demo-spin-icon-load',
        //         props: {
        //           type: 'ios-loading',
        //           size: 88
        //         }
        //       }),
        //       h('div', 'Loading')
        //     ])
        //   }
        // });

      },
      getlx(lx) {
        // console.log(lx);
        this.getSRTJList()
      },
      getTJ(st) {
        this.param.jgdm = st;
        this.data1 = [];
        this.getSRTJList()
      },
      getNf(gsh, date) {
        // console.log(gsh);
        // console.log(date);
        this.param.startTime = gsh[0];
        this.param.endTime = gsh[1];
        this.data1 = [];
        this.getSRTJList();
      },
      getBmdList() {
        this.$http.get(this.apis.FRAMEWORK.getCurrentOrgTree, {timers: new Date().getTime()}).then((res) => {
          if (res.code === 200) {
            // // console.log('ppoopp',res.result);
            if (res.result[0].value.length == 3) {
              this.dictList.bmd.data = res.result[0].children[0].children;
            } else if (res.result[0].value.length == 6) {
              this.dictList.bmd.data = res.result[0].children;
            } else if (res.result[0].value.length == 9) {
              this.dictList.bmd.data = res.result
            }
          }
        }).catch((error) => {
        })
      },
      getSRTJList() {
        // this.handleSpinCustom();
        this.tjzje = 0;
        this.loading=true
        var v=this;
        this.$http.post(this.apis.COUNT.BMSF, this.param).then(res => {
          // console.log(res);
         v.loading = false;
          if (res.code == 200) {
            this.data1 = res.result?res.result:[];
            res.result.forEach((item) => {
              this.tjzje += item.chargeFee;
            })
          } else {
            this.$Message.error(res.message);
          }
        })
      },
      getNSRList() {
        this.$http.post(this.apis.COUNT.NSR).then((res) => {
          // console.log(res);
        })
      },
      getNZCList() {
        this.$http.post(this.apis.COUNT.NZC).then((res) => {
          // console.log(res);
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
