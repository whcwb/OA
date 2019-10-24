<template>
  <div class="box_col" style="position: relative">
    <!--<pager-tit title="报名收费" iconname="ios-football"></pager-tit>-->
    <div class="box_col_100">
      <Row :gutter="10" style="margin:8px 0">
        <Col span="8" :lg="8" :md="32">
          <div style="font-size: 18px;font-weight: 700;width: 100%">
            收支统计
<!--            <span style="font-size: 16px;margin-left: 16px">-->
<!--                    共:-->
<!--                    <span style="font-size: 20px;color: #ff9900">-->
<!--                      {{total}}-->
<!--                    </span>-->
<!--                    人-合计收入:-->
<!--                    <span style="font-size: 20px;color: #ff9900">-->
<!--                      {{allmoney1}}-->
<!--                    </span>-->
<!--                    元-合计支出:-->
<!--                    <span style="font-size: 20px;color: #ff9900">-->
<!--                      {{allmoney}}-->
<!--                    </span>-->
<!--                  </span>-->
          </div>
        </Col>
        <Col span="3" :lg="3" :md="4">
          <div style="width: 100%">
            <Select v-model="param.jgdm"
                    clearable
                    placeholder="请选择报名点"
                    @on-change="CasChange">
              <Option v-for="item in CascaderList" :value="item.value" :key="item.value">{{ item.label }}</Option>
            </Select>
          </div>
        </Col>
        <Col span="3" :lg="3" :md="4">
          <div style="width: 100%">
            <div>
              <Input v-model="param.name"
                     @on-enter="param.pageNum = 1,getPagerList()"
                     placeholder="请输入学员姓名"/>
            </div>
          </div>
        </Col>
        <Col span="3" :lg="3" :md="4">
          <div style="width: 100%">
            <Input v-model="param.idCard"
                   @on-enter="param.pageNum = 1,getPagerList()"
                   placeholder="证件号码"/>
          </div>
        </Col>
        <Col span="4" :lg="4" :md="5">
          <div style="width: 100%">
            <DatePicker v-model="bmTime" split-panels type="daterange" :clearable="true" :editable="false"
                        @on-change="DatePickerC"
                        placement="bottom-end" placeholder="收费日期" style="width: 100% ;"></DatePicker>
          </div>
        </Col>
        <Col span="1" :lg="1" :md="1">
          <Button type="primary" @click="param.pageNum = 1,getPagerList()">
            <Icon type="md-search"></Icon>
            <!--查询-->
          </Button>
        </Col>
        <Col span="1" :lg="1" :md="1">
          <Button type="primary"  @click="excel">
             <Icon type="md-cloud-download"></Icon>
          </Button>
        </Col>
      </Row>
      <Table :columns="columns1" :data="data1" stripe size="small" :loading="loading"></Table>

      <Table :height="AF.getPageHeight()-360" stripe size="small" :loading="loading"
             @on-selection-change="djfCheck"
             :columns="tabTit" :data="tableData"></Table>
      <div style="text-align: right;padding: 6px 0">
        <Page :total=total
              :current=param.pageNum
              :page-size=param.pageSize
              :page-size-opts=[8,10,20,30,40,50]
              show-total
              show-elevator
              show-sizer
              placement='top'
              @on-page-size-change='(n)=>{pageSizeChange(n,0)}'
              @on-change='(n)=>{pageChange(n,0)}'>
        </Page>
      </div>
    </div>

  </div>
</template>

<script>
    import http from '@/axios/index';
  export default {
    name: "indexN",
    data: function () {
      return {
          loading:true,
        confirmMap:new Map(),
        bmTime:[this.AF.trimDate()+' 00:00:00',this.AF.trimDate() + ' 23:59:59'],
        activeName: '1',
        compName: '',
        userAudit: {},
        printMess: [],
        count: 0,
        count1: 0,
          data1:[
              {'sr':0,'qf':0,'zc':0,'k1':0,'k2':0,'k3':0},
          ],
          columns1: [
              {
                  title: '收入合计',
                  key: 'sr',
                  align:'center'
              },
              {
                  title: '科目一支出合计',
                  key: 'k1',
                  align:'center'
              },
              {
                  title: '科目二支出合计',
                  key: 'k2',
                  align:'center'
              },
              {
                  title: '科目三支出合计',
                  key: 'k3',
                  align:'center'
              },
              {
                  title: '支出合计',
                  key: 'zc',
                  align:'center'
              }
          ],
        tabTit: [
          {
            type: 'index2', align: 'center', minWidth: 40, fixed: 'left',
            render: (h, params) => {
              return h('span', params.index + (this.param.pageNum - 1) * this.param.pageSize + 1);
            }
          },
          {
            title: '报名点',
            align: 'center',
            minWidth: 140,
            key: 'jgmc',
            fixed: 'left',
            render: (h, params) => {
              let jgmcArray = params.row.jgmc.split("/");
              let res = "";
              if (jgmcArray.length == 2 || jgmcArray.length == 1) {
                return h('div', params.row.jgmc);
              } else if (jgmcArray.length == 3) {
                return h('div', jgmcArray[1] + ("/" + jgmcArray[2]))
              }
            }
          },
          {
            title: '姓名',
            align: 'center',
            minWidth: 120,
            key: 'name',
            fixed: 'left'
          },
            {
                title: '身份证号码',
                align: 'center',
                minWidth: 180,
                key: 'idCardNo'
            },
          {
            title: '车型',
            key: 'carType',
            minWidth: 120,
            align: 'center',
            render: (h, p) => {
              return h('div', [
                h('Tooltip', {
                  props: {
                    placement: 'top',
                    content: p.row.arrearage == '10' ? '分期' : '未分期'
                  }
                }, [
                  h('Tag', {
                    props: {
                      type: 'dot',
                      color: p.row.arrearage == '10' ? 'error' : 'success'
                    }
                  }, p.row.carType)
                ])
              ])
            }
          },
            {
                title: '收费时间',
                key: 'confirmTime',
                minWidth: 140,
                align: 'center'
            },
          {
            title: '实收金额',
            key: 'realPay',
            minWidth: 80,
            align: 'center'
          },
          {
            title: '欠费金额',
              key:'oweAmount',
            minWidth: 80,
            align: 'center'
          },
            {
                title: '科目一支出',
                key: '1',
                minWidth: 100,
                align: 'center'
            },
            {
                title: '科目二支出',
                key: '2',
                minWidth: 100,
                align: 'center'
            },
            {
                title: '科目三支出',
                key: '3',
                minWidth: 100,
                align: 'center'
            },
            {
                title: '小计支出',
                key: 'total',
                minWidth: 100,
                fixed:'right',
                align: 'center',
                render: (h, params) => {
                    if (params.row.total>502) {
                        return h('Tag', {
                            props: {
                                color: 'volcano'
                            },
                            style: {
                                fontSize: '15px',
                            }
                        }, params.row.total);
                    } else {
                        return h('div', params.row.total)
                    }
                }

            },

        ],
        tableData: [],
        djfCheckList:'',
        bzs:'',
        total: 0,//总数量
        allmoney: 0,
        param: {
            start:'',
            end:'',
            idCard:'',
            name:'',
            jgdm: '',
          //分页数据
          pageNum: 1,//当前页码
          pageSize: 10//每页显示数
        },
        total1: 0,//总数量
        allmoney1: 0,
        param1Loading:false,
        param1: {
          nameLike: '',
          idCardNoLike: '',
          jgdm: '',
          // confirmTimeLike:'',
          sign: '4',
          pj:'',
          //分页数据
          pageNum: 1,//当前页码
          pageSize: 10//每页显示数
        },
        jXlist: [],
        CascaderList: [],
        bmdT: [],
        bmdB: []
      }
    },
    created() {
      // this.getpager()
      let time = this.AF.trimDate();
      this.param.start = time + ' 00:00:00';
      this.param.end = time + ' 23:59:59';
      this.getPagerList();
      this.getBmdList();
      this.getJTjx();
    },
    computed: {

    },
    methods: {
        excel(){
            if(this.param.startTime == '-01-01'){
                this.param.startTime = this.AF.getYear()+'-01-01';
                this.param.endTime = this.AF.getYear()+'-12-31';
            }
            window.open(http.url + '/pub/exportStatisCharge?startTime=' + this.param.start + '&endTime=' + this.param.end ,'_blank');
        },
      DatePickerC(data) {
        if(data[0].length == 10){
          this.param.start = data[0] + ' 00:00:00' ;
        }else {
          delete this.param.start
        }
        if(data[1].length == 10){
          this.param1.end = data[1] + ' 23:59:59';
        }else {
          delete this.param1.end
        }
        this.getPagerList();
      },

      changeMess(row) {
        this.userAudit = row
        this.compName = 'ch'
      },
      changeMoney(row){
        this.userAudit = row
        this.compName = 'C_M'
      },
      djfCheck(arr){
        console.log(arr);
        let CheckList = []
        let bzList = []
        arr.forEach((item,index)=>{
          // this.djfCheckList =
          CheckList.push(item.id)
          console.log('12306',item);
          bzList.push(this.confirmMap.get(item.id))
          if(index == arr.length-1){
            this.djfCheckList = CheckList.join(',')
            this.bzs = bzList.join(',')
            console.log('bzs',this.bzs);
            console.log(this.djfCheckList);
          }
        })
      },

      getJTjx() {
        this.jXlist = this.dictUtil.getByCode(this, 'ZDCLK1019');
      },
      CasChange(val) {
        console.log(val);
        // if (val.length == 1) {
        //   this.param.jgdm = val[0]
          this.param.pageNum = 1,
          this.getPagerList()
        // }
      },
      CasChange1(val, lab) {
          this.param1.pageNum = 1
          this.getPagerList1()
      },
      getBmdList() {
        this.$http.get(this.apis.FRAMEWORK.getCurrentOrgTree, {timers: new Date().getTime()}).then((res) => {
          if (res.code === 200) {
            /*if(res.result[0].value.length=='6'){
              this.CascaderList = res.result[0].children;
            }else if(res.result[0].value.length=='3'){
              this.CascaderList = res.result[0].children[0].children;
            }*/
            if(res.result[0].value.length==3){
              this.CascaderList= res.result[0].children[0].children;
            }else if(res.result[0].value.length==6){
              this.CascaderList = res.result[0].children;
            }else if(res.result[0].value.length==9){
              this.CascaderList = res.result
            }

          }
        }).catch((error) => {
        })
      },
      getpager() {
        this.getPagerList()
      },
      getPagerList() {
        var v = this
        this.$http.post('/api/data/statisCharge', this.param).then((res) => {
          v.tableData = res.page.list
          v.total = res.page.total
            var a = res.message.split(',')
            v.data1[0].sr = a[0]
            v.data1[0].qf = a[1]
            v.data1[0].zc = a[2]
            v.data1[0].k1 = a[3]
            v.data1[0].k2 = a[4]
            v.data1[0].k3 = a[5]
            v.loading = false
        })
      },
      OKpay(index) {
        console.log(this.payOk);
        if (this.payOk.chargeType == '') {
          this.swal({
            text: '请选择收费方式',
            type: 'warning'
          });
        } else {
          this.$http.post(this.apis.BMSF.OKPAY, this.payOk).then((res) => {
            if (res.code == 200) {
              this.getPagerList()
              this.$Message.success(res.message);
            } else {
              this.$Message.error(res.message);
            }

          })
        }
      },

      getPagerList1() {
        this.param1Loading = true
        var v = this
       /* let time = this.AF.trimDate(this.param1.confirmTimeLike);
        this.param1.confirmTimeLike = time;*/
        this.$http.post('/api/traineeinformation/jryj', this.param1).then((res) => {
          if (res.code == 200) {
            if (res.page) {
              v.tableData1 = res.page.list
              v.total1 = res.page.total
              v.allmoney1 = res.result
            } else {
              v.tableData1 = []
              v.total1 = 0
              v.allmoney1 = 0
            }
            v.param1Loading = false
          }
        })
      },

      retract(id) {
        this.$http.post(this.apis.BMSF.RETRACT, {traineeId: id}).then((res) => {
          if (res.code == 200) {
            this.getPagerList1()
            this.$Message.success(res.message);
          } else {
            this.$Message.error(res.message);
          }
        })
      },
      tabcheck(arr) {
        this.printMess = arr
      }
      ,
      winPrint() {
        var v = this
        if (this.printMess.length > 1) {
          for (var i = 0; i < this.printMess.length - 1; i++){
            if (this.printMess[i].chargeRecord.pjbh != '' &&
              this.printMess[i + 1].chargeRecord.pjbh != '' &&
              this.printMess[i].chargeRecord.pjbh != this.printMess[i + 1].chargeRecord.pjbh != '') {
              this.swal({
                title: '所选订单 单号有误！！！',
                type: 'warning',
                confirmButtonText: '关闭'
              })
              return
            }

          }
        }

        setTimeout(() => {
          v.AF.WinPrint(this, this.printMess, 'printSignUp')
        }, 100)
      }
      ,
      pageChange(n, num) {
        // console.log(num + '--1--' + n);
        if (num == 0) {
          this.param.pageNum = n
          this.getPagerList()
        } else if (num == 1) {
          this.param1.pageNum = n
          this.getPagerList1()
        }
      },
      pageSizeChange(n, num) {
        console.log(num + '--2--' + n);
        if (num == 0) {
          this.param.pageSize = n
          this.getPagerList()
        } else if (num == 1) {
          this.param1.pageSize = n
          this.getPagerList1()
        }
      }
    }
  }
</script>

<style scoped>

</style>
