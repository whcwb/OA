<template>
          <div class="box_col">
            <pager-tit title="年度招生统计"></pager-tit>
            <!--<div style="height: 400px">-->
              <!--<awb></awb>-->
            <!--</div>-->
              <div class="box_col_100">
                <Row style="margin-bottom: 10px">
                  <!--<Col span="4">-->
                  <!--<RadioGroup v-model="button1" type="button">-->
                    <!--<Radio label="A1"></Radio>-->
                    <!--<Radio label="A2"></Radio>-->
                    <!--<Radio label="A3"></Radio>-->
                    <!--<Radio label="B2"></Radio>-->
                    <!--<Radio label="C1C2"></Radio>-->
                  <!--</RadioGroup>-->
                  <!--</Col>-->
                  <Col span="6">
                    <!--<DatePicker type="daterange" @on-change="getNf" confirm placement="bottom-end" placeholder="选择日期（默认当天）" style="width: 250px"></DatePicker>-->
                    <DatePicker  type="year" split-panels @on-change="getNf"  placeholder="请选择年份（默认当前年份）" style="width: 250px"></DatePicker>
                  </Col>
                  <Col span="2">
                    <Button type="primary" @click="getNf(year)">
                      <Icon type="md-search"></Icon>
                    </Button>
                  </Col>
                  <Col span="3" style="margin-right: 20px">

                    <Select v-model="param.bmd" clearable style="width:100%" @on-change="getTJ" @on-clear="getTJ">
                      <Option v-for="item in dictList.bmd.data" :value="item.value" :key="item.value">{{ item.label }}</Option>
                    </Select>
                   <!-- <Form :label-width="10">
                    <FormItem>
                      <Cascader trigger="hover" v-model="param.bmd" @on-change="getTJ" @on-clear="getTJ"  :data="dictList.bmd.data" placeholder="报名点选择"
                                filterable></Cascader>
                    </FormItem>
                    </Form>-->
                  </Col>
                  <Col span="2">
                    <Button type="primary" @click="getTJ(param.jgdm)">
                      <Icon type="md-search"></Icon>
                    </Button>
                  </Col>

                  <Col span="2">
                    <Tooltip content="导出Excel" placement="right-start">
                      <Button type="primary" icon="md-cloud-download" @click="excel"></Button>
                    </Tooltip>
                  </Col>
                 <!-- <Col span="4" :lg="2" :md="0">
                    <Button type="primary" @click="getPagerList()">
                      <Icon type="md-search" ></Icon>
                      &lt;!&ndash;查询&ndash;&gt;
                    </Button>
                  </Col>-->
                </Row>
                <row>
                  <Table size="small" :height="AF.getPageHeight()-280" :columns="columns11" :data="data10" border stripe no-data-text="当前日期暂无数据，请选择日期"></Table>
                </row>
               </div>
          </div>
</template>

<script>
  import awb from "./comp/bar"
  import http from '@/axios/index';

  export default {
        name: "",
      components:{
         awb
      },
          data() {
            return {
              year:'',
              param:{
                startTime: this.AF.getYear()+'-01-01',
                endTime: this.AF.getYear()+'-12-31',
                jgdm:'',
                bmd:[]
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
              shijian:'2018',
              year: this.AF.trimDate(),
              zsqk:[],
              columns11: [
                {
                  title: '机构',
                  key: 'name',
                  align: 'center',
                  width: 120,
                  fixed: 'left',
                  filterMultiple: false,
                  filterMethod (value, row) {
                    if (value == 1) {
                      return row.name === 'Joe';
                    } else if (value == 2) {
                      return row.name === 'John Brown';
                    }
                  }
                },
                {
                  title: '合计',
                  key: 'hj1',
                  align: 'center',
                  sortable: true
                },
                {
                  renderHeader:(h,p)=>{
                    return h('div',this.param.startTime+" —— "+this.param.endTime)
                  },
                  //title: this.shijian,
                  align: 'center',
                  children: [
                    {
                      title: '1月',
                      key:'one',
                      align: 'center',
                    },
                    {
                      title: '2月',
                      key: 'two',
                      align: 'center',
                    },
                    {
                      title: '3月',
                      key: 'three',
                      align: 'center',
                    },
                    {
                      title: '4月',
                      key: 'four',
                      align: 'center',
                    },
                    {
                      title: '5月',
                      key: 'five',
                      align: 'center',
                    },
                    {
                      title: '6月',
                      key: 'six',
                      align: 'center',
                    },
                    {
                      title: '7月',
                      key: 'seven',
                      align: 'center',
                    },
                    {
                      title: '8月',
                      key: 'eight',
                      align: 'center',
                    },
                    {
                      title: '9月',
                      key: 'nine',
                      align: 'center',
                    },
                    {
                      title: '10月',
                      key: 'ten',
                      align: 'center',
                    },
                    {
                      title: '11月',
                      key: 'eve',
                      align: 'center',
                    },{
                      title: '12月',
                      key: 'twn',
                      align: 'center',
                    },
                  ]
                },
                {
                  title: '退学人数',
                  key: 'tx',
                  align: 'center',
                },
                // {
                //   title: '1-12合计',
                //   key: 'hj3',
                //   align: 'center',
                //   sortable: true
                // },
              ],
              data10: [
                ],
              // zsqk:{},
            }
          },
      created(){
         this.getPagerList();
          this.getBmdList();
      },
      mounted(){
        // const data = [];
        // for (let i = 0; i < 20; i++) {
        //   data.push({
        //     key: i,
        //     name: '',
        //     age: i + 1,
        //     street: 'Lake Park',
        //     building: 'C',
        //     door: 2035,
        //   });
        // }
        // this.data10 = data;
      },
      methods:{
        excel() {
          if (this.param.startTime == '-01-01') {
            this.param.startTime = this.AF.getYear() + '-01-01';
            this.param.endTime = this.AF.getYear() + '-12-31';
          }
          window.open(http.url + `/pub/exportAllIn?startTime=${this.param.startTime}&endTime=${this.param.endTime}&jgdm=${this.param.jgdm}&bmd=${this.param.bmd}`, '_blank');
        },
        //获取当前用户可操作的报名点
        getBmdList() {
          this.$http.get(this.apis.FRAMEWORK.getCurrentOrgTree, {timers: new Date().getTime()}).then((res) => {
            if (res.code === 200) {
              // console.log('ppoopp',res.result);
              if(res.result[0].value.length==3){
                this.dictList.bmd.data = res.result[0].children[0].children;
              }else if(res.result[0].value.length==6){
                this.dictList.bmd.data = res.result[0].children;
              }else if(res.result[0].value.length==9){
                this.dictList.bmd.data = res.result
              }
            }
          }).catch((error) => {
          })
        },
        handleSpinCustom () {
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
        getTJ(st){
          this.param.jgdm = st;
          this.data10 = [];
          this.getPagerList()
        },
        getNf(gsh,date){
          // console.log(gsh);
          // console.log(date);
          this.year=gsh
          this.param.startTime = gsh+'-01-01';
          this.param.endTime = gsh+'-12-31';
          this.data10 = [];
          this.getPagerList();
        },
          getPagerList(){
            // this.handleSpinCustom();
            // // console.log(this.year.getFullYear());
            if(this.param.startTime == '-01-01'){
              this.param.startTime = this.AF.getYear()+'-01-01';
              this.param.endTime = this.AF.getYear()+'-12-31';
            }
            this.data10 = [];
            // console.log(this.param.jgdm);
            this.$Spin.show({
              render: (h) => {
                return h('div', [
                  h('Icon', {
                    'class': 'demo-spin-icon-load',
                    props: {
                      type: 'ios-loading',
                      size: 30
                    }
                  }),
                  h('div', 'Loading')
                ])
              }
            });
            this.$http.post('/api/data/getAllIn',{startTime:this.param.startTime,endTime: this.param.endTime,jgdm:this.param.jgdm}).then( (res)=>{
              // console.log(res);
             if(res.code == 200){
               this.$Spin.hide();
               var zshj = {};
               res.result.forEach((item,index)=>{
                      var ps = {}
                      ps.name = item.jgmc;
                      ps.one = item.stu[0].signUp;
                       ps.two = item.stu[1].signUp;
                       ps.three = item.stu[2].signUp;
                       ps.four = item.stu[3].signUp;
                       ps.five = item.stu[4].signUp;
                       ps.six = item.stu[5].signUp;
                       ps.seven = item.stu[6].signUp;
                       ps.eight = item.stu[7].signUp;
                       ps.nine = item.stu[8].signUp;
                       ps.ten = item.stu[9].signUp;
                       ps.eve = item.stu[10].signUp;
                       ps.twn = item.stu[11].signUp;
                       ps.tx = item.stu[12].count;
                       ps.hj1 = ps.one+ps.two+ps.three+ps.four+ps.five+ps.six+ps.seven+ps.eight+ps.nine+ps.ten+ps.eve+ps.twn;
                       ps.hj2 = item.dropOut;
                       ps.hj3 = item.realAll;
                       // zshj.one = zshj.one + ps.one;
                       // zshj.two = zshj.one + ps.one;
                       // zshj.three = zshj.one + ps.one;
                       // zshj.four = zshj.one + ps.one;
                       // zshj.five = zshj.one + ps.one;
                       // zshj.six = zshj.one + ps.one;
                       // zshj.seven = zshj.one + ps.one;
                       // zshj.eight = zshj.one + ps.one;
                       // zshj.nine = zshj.one + ps.one;
                       // zshj.ten = zshj.one + ps.one;
                       // zshj.eve = zshj.one + ps.one;
                       // zshj.twn = zshj.one + ps.one;
                       // zshj.hj1 = zshj.one + ps.one;
                       // zshj.hj2 = zshj.one + ps.one;
                       // zshj.hj3 = zshj.one + ps.one;
                      this.data10.push(ps)

                    })
               this.$Spin.hide();
             }else{
                this.$Message.error(res.message);
              }
            })
          }
  }
    }
</script>

<style scoped>

</style>
