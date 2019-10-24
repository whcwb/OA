<template>

      <div class="box_col">
        <pager-tit title="当日招生统计"></pager-tit>
        <Row style="margin-bottom: 10px">
          <Col span="5">
            <DatePicker  type="date" split-panels @on-change="getNf" clearable @on-clear="getNf"  placeholder="请选择日期（默认当天）"></DatePicker>
          </Col>
          <Col span="3" style="margin-right: 20px">
            <Select v-model="param.bmd" clearable style="width:100%" @on-change="getTJ" @on-clear="getTJ">
              <Option v-for="item in dictList.bmd.data" :value="item.value" :key="item.value">{{ item.label }}</Option>
            </Select>
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
        </Row>
        <!--<div style="height: 400px">-->
        <!--<awb></awb>-->
        <!--</div>-->
        <div class="box_col_100">
              <Table size="small" :height="AF.getPageHeight()-280" :columns="columns11" :data="data10" border stripe ></Table>
        </div>
        <!--<Page :total="100" />-->
</div>
  </template>
<script>
  import http from '@/axios/index';
      export default {
         name: "",
        data() {
          return {
            year:'',
            param:{
              startTime:this.AF.trimDate(),
              endTime: this.AF.trimDate(),
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
            columns11: [
              {
                title: '机构',
                key: 'name',
                align: 'center',
                width: 300,
                fixed: 'left',
                filterMultiple: false,
                // filterMethod (value, row) {
                //   if (value === 1) {
                //     return row.name === 'Joe';
                //   } else if (value === 2) {
                //     return row.name === 'John Brown';
                //   }
                // }
              },
              {
                title: '当日总人数',
                align: 'center',
                key: 'ZRS',
                width: 300,
              },{
                title: 'A1',
                align: 'center',
                key: 'A1',
              },
              {
                title: 'A2',
                align: 'center',
                key: 'A2',
              },
              {
                title: 'A3',
                align: 'center',
                key: 'A3',
              },
              {
                title: 'B2',
                align: 'center',
                key: 'B2',
              },
              {
                title: 'C1',
                align: 'center',
                key: 'C1'
              },
              {
                title: 'C2',
                align: 'center',
                key: 'C2',
              },

            ],
            data10: [],
            zsqk:{},
          }
        },
        created(){
           this.getDTList();
           this.getBmdList();
        },
        methods:{
          excel() {
            if (this.param.startTime == '-01-01') {
              this.param.startTime = this.AF.getYear() + '-01-01';
              this.param.endTime = this.AF.getYear() + '-12-31';
            }
            window.open(http.url + `/pub/exportStudentCount?startTime=${this.param.startTime}&endTime=${this.param.endTime}&jgdm=${this.param.jgdm}&bmd=${this.param.bmd}`, '_blank');
          },
          getTJ(st){
            this.param.jgdm = st;
            this.data10 = [];
            this.getDTList()
          },
          getNf(gsh,date){
            // console.log(gsh);
            // console.log(date);
            this.year=gsh
            this.param.startTime = gsh;
            this.param.endTime = gsh;
            this.data10 = [];
            this.getDTList();
          },
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
           getDTList(){
             if(this.param.startTime == ''){
               this.param.startTime = this.AF.trimDate();
               this.param.endTime = this.AF.trimDate();
             }
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
             this.$http.post(this.apis.COUNT.DTXY,{startTime:this.param.startTime,endTime: this.param.endTime,jgdm:this.param.jgdm}).then( (res)=>{
               // console.log(res);
               this.$Spin.hide();
               if(res.code == 200){
                 res.result.forEach((item,index)=> {
                   var ps = {};
                   ps.name = item.jgmc;
                   ps.ZRS = item.all;
                   ps.A1 = item.a1;
                   ps.A2 = item.a2;
                   ps.A3 = item.a3;
                   ps.B2 = item.b2;
                   ps.C1 = item.c1;
                   ps.C2 = item.c2;
                   this.data10.push(ps)
                 })
               }else{
                 this.$Message.error(res.message);
               }
             })
            },
        }
    }

</script>

<style scoped>

</style>
