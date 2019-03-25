<template>
  <div class="box_col">
    <Row style="padding: 10px">
      <Col span="16">
        <pager-tit title="合格率统计"></pager-tit>
      </Col>
      <Col span="4">
        <DatePicker type="daterange" split-panels placeholder="请选择日期（默认当天)" @on-change="getNf" @on-clear="getNf"
                    style="width: 200px"></DatePicker>
      </Col>
      <Col span="4">
        <Button type="primary" @click="DcExcel">导出Excel</Button>
      </Col>
    </Row>
    <div class="box_col_100">
      <Table size="small" :height="AF.getPageHeight()-280" :columns="columns11" :data="data10" border stripe></Table>
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
        param: {
          startTime: this.AF.trimDate(),
          endTime: this.AF.trimDate(),
          jgdm: '',
          bmd: []
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
            key: 'jgmc',
            align: 'center',
            width: 300,
            fixed: 'left',
            filterMultiple: false,
          },
          {
            title: '科目一合格率',
            align: 'center',
            children:[
              {
                title:'考试人数',
                align: 'center',
                minWidth:100,
                key: 'km1all'
              },{
                title:'合格人数',
                align: 'center',
                minWidth:100,
                key: 'km1Pass'
              },{
                title:'合格率',
                align: 'center',
                minWidth:100,
                key: 'km1'
              }

            ]
          }, {
            title: '科目二合格率',
            align: 'center',
            children:[
              {
                title:'考试人数',
                align: 'center',
                minWidth:100,
                key: 'km2all'
              },{
                title:'合格人数',
                align: 'center',
                minWidth:100,
                key: 'km2Pass'
              },{
                title:'合格率',
                align: 'center',
                minWidth:100,
                key: 'km2'
              }

            ]
          },
          {
            title: '科目三合格率',
            align: 'center',
            children:[
              {
                title:'考试人数',
                align: 'center',
                minWidth:100,
                key: 'km3all'
              },{
                title:'合格人数',
                align: 'center',
                minWidth:100,
                key: 'km3Pass'
              },{
                title:'合格率',
                align: 'center',
                minWidth:100,
                key: 'km3'
              }

            ]
          },
          {
            title: '科目四合格率',
            align: 'center',
            children:[
              {
                title:'考试人数',
                align: 'center',
                minWidth:100,
                key: 'km4all'
              },{
                title:'合格人数',
                align: 'center',
                minWidth:100,
                key: 'km4Pass'
              },{
                title:'合格率',
                align: 'center',
                minWidth:100,
                key: 'km4'
              }

            ]
          }
        ],
        data10: [],
        zsqk: {},
      }
    },
    created() {
      this.getDTList();
      // this.getBmdList();
    },
    methods: {
      DcExcel(){
          // startTime: this.AF.trimDate(),
          //   endTime: this.AF.trimDate(),
          window.open(http.url + '/pub/getPassExcel' +
            "?startTime=" + this.param.startTime +
            "&endTime=" + this.param.endTime, "_blank");
      },
      getTJ(st) {
        this.param.jgdm = st;
        this.data10 = [];
        this.getDTList()
      },
      getNf(gsh, date) {
        console.log(gsh);
        console.log(date);
        this.param.startTime = gsh[0];
        this.param.endTime = gsh[1];
        this.data10 = [];
        this.getDTList();
      },
      getDTList() {
        if (this.param.startTime == '') {
          this.param.startTime = this.AF.trimDate();
          this.param.endTime = this.AF.trimDate();
        }
        this.$http.post('/api/data/getPass', {
          startTime: this.param.startTime,
          endTime: this.param.endTime
        }).then((res) => {
          console.log(res);
          if (res.code == 200) {
            this.data10 = res.result
          } else {
            this.$Message.error(res.message);
          }
        })
      },
    }
  }

</script>

<style scoped>

</style>
