<template>
  <div class="box_col" style="position: relative">
    <Menu mode="horizontal" :active-name="activeName" @on-select="MenuClick">
      <MenuItem name="1">
        <div style="font-weight: 700;font-size: 16px">
          今日待审核
        </div>
      </MenuItem>
      <MenuItem name="2">
        <div style="font-weight: 700;font-size: 16px">
          已审核记录
        </div>
      </MenuItem>
    </Menu>
    <div class="box_col_100" v-if="activeName=='1'">
      <Row :gutter="10" style="margin:8px 0">
        <Col span="10" :lg="10" :md="24">
          <div class="box_row_100" style="font-size: 18px;font-weight: 700">
            今日待审
            <span style="font-size: 16px;margin-left: 16px">
                    共:
                  <span style="font-size: 20px;color: #ff9900">
                    {{total}}
                  </span>
                  笔 - 收入:
                   <span style="font-size: 20px;color: #ff9900">
                    {{inCount}}
                  </span>
                  元 - 支出：
                   <span style="font-size: 20px;color: #ff9900">
                    {{outCount}}
                  </span>
                  元
                </span>
          </div>
        </Col>
        <Col span="14" :lg="14" :md="24">
          <Row :gutter="10" type="flex" justify="end">
            <Col span="6" :lg="6" :md="3">
              <Select v-model="param.chargeCode" clearable
                      placeholder="请输选着费用类型"
                      @on-change="getPagerList">
                <Option v-for="(item,index) in typeList" :value="item.key" :key="(index+1)">{{item.val}}</Option>
              </Select>
            </Col>
            <Col span="6" :lg="6" :md="3">
              <Input v-model="param.traineeNameLike" clearable
                     @on-enter="getPagerList"
                     placeholder="请输入学员姓名"/>
            </Col>

            <Col span="6" :lg="6" :md="3">
              <Input v-model="param.idCardNoLike" clearable
                     @on-enter="getPagerList"
                     placeholder="证件号码"/>
            </Col>
            <Col span="4" :lg="4" :md="2" align="center" style="float: right">
              <Button type="primary" @click="getPagerList">
                <Icon type="md-search"></Icon>
                <!--查询-->
              </Button>
            </Col>

          </Row>
        </Col>
      </Row>

      <Table :height="AF.getPageHeight()-340" stripe
             size="small"
             :columns="tabTit" :data="tableData"
             @on-selection-change="changeIds"></Table>
      <div class="box_row" style="text-align: right;border-top: #f2f2f2 3px solid;padding-top: 16px">
        <div style="padding: 0 15px">
          <Tooltip content="批量审核" placement="top" :transfer="tran">
            <Button @click="OKpay"
                    size="small"
                    type="primary">
              <Icon type="md-checkmark" size="20"/>
            </Button>
          </Tooltip>
        </div>
        <div style="font-size: 16px;font-weight: 600">
          已选
          <span style="color: #ff9900;font-size: 18px;font-weight: 700">{{pays.length}}</span>
          项
          合计： {{count1}}
        </div>
        <div class="box_row_100" style="float: right">
          <Page :total=total
                :current=param.pageNum
                :page-size=param.pageSize
                :page-size-opts=[10,20,30,40,50]
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

    <div class="box_col_100" v-if="activeName=='2'">
      <Row :gutter="10" style="margin:8px 0">
        <Col span="8" :lg="8" :md="24">
          <div class="box_row_100" style="font-size: 18px;font-weight: 700">
            已审核
            <span style="font-size: 16px;margin-left: 16px">
                    共
                  <span style="font-size: 20px;color: #ff9900">
                    {{total1}}
                  </span>
                  笔-收入:
                   <span style="font-size: 20px;color: #ff9900">
                    {{inCount1}}
                  </span>
                  元-支出：
                   <span style="font-size: 20px;color: #ff9900">
                    {{outCount1}}
                  </span>
                  元
                </span>
          </div>
        </Col>
        <Col span="16" :lg="16" :md="24">
          <Row :gutter="10" type="flex" justify="end">
            <Col span="4" :lg="4" :md="2">
              <Select v-model="param1.chargeCode" filterable clearable
                      @on-change="getPagerList1">
                <Option v-for="(item,index) in typeList" :value="item.key" :key="(index+1)">{{item.val}}</Option>
              </Select>
            </Col>
            <Col span="4" :lg="4" :md="2">
              <Input v-model="param1.traineeNameLike" clearable
                     @on-enter="getPagerList1"
                     placeholder="学员姓名"/>
            </Col>
            <Col span="4" :lg="4" :md="2">
              <Input v-model="param1.idCardNoLike" clearable
                     @on-enter="getPagerList1"
                     placeholder="证件号码"/>
            </Col>
            <Col span="4" :lg="4" :md="2">
              <Input v-model="param1.remarkLike" clearable
                     @on-enter="getPagerList1"
                     placeholder="请输入备注"/>
            </Col>
            <Col span="5" :lg="5" :md="3">
              <DatePicker v-model="chargeTime" split-panels type="daterange" :editable="false"
                          @on-change="DatePickerC"
                          placement="bottom-end" placeholder="报名日期" style="width: 100% ;"></DatePicker>
            </Col>
            <Col span="3" :lg="3" :md="4" align="center">
              <Row type="flex" justify="space-between">
                <Col span="12">
                  <Button type="primary" @click="getPagerList1">
                    <Icon type="md-search"></Icon>
                    <!--查询-->
                  </Button>
                </Col>
                <Col span="12">
                  <Button type="primary" icon="md-cloud-download" @click="excel"></Button>
                </Col>
              </Row>
            </Col>
          </Row>
        </Col>


      </Row>

      <Table :height="AF.getPageHeight()-340"
             :columns="tabTit1" :data="tableData1" stripe
             @on-selection-change="revokeCharge"></Table>
      <div class="box_row" style="text-align: right;border-top: #f2f2f2 3px solid;padding-top: 16px">
        <!--<div style="padding: 0 15px">-->
        <!--<Tooltip content="批量撤回" placement="top" :transfer="tran">-->
        <!--<Button @click="retract"-->
        <!--size="small"-->
        <!--type="error">-->
        <!--<Icon type="ios-redo" size="20"/>-->
        <!--</Button>-->
        <!--</Tooltip>-->
        <!--</div>-->
        <!--<div style="font-size: 16px;font-weight: 600">-->
        <!--已选-->
        <!--<span style="color: #ff9900;font-size: 18px;font-weight: 700">-->
        <!--{{chargeIds.length}}</span>-->
        <!--项-->
        <!--合计： {{count1}}-->
        <!--</div>-->
        <div class="box_row_100" style="float: right">
          <Page :total=total1
          :current=param1.pageNum
          :page-size=param1.pageSize
          :page-size-opts=[10,20,30,40,50]
          show-total
          show-elevator
          show-sizer
          placement='top'
          @on-page-size-change='(n)=>{pageSizeChange(n,1)}'
          @on-change='(n)=>{pageChange(n,1)}'>
          </Page>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
  import http from '@/axios/index';

  export default {
    name: "index",
    methods: {},
    data: function () {
      return {
        chargeTime:[],
        activeName: '1',
        compName: '',
        printMess: [],
        tran: true,
        inCount: 0,
        outCount: 0,
        count: 0,
        count1: 0,
        inCount1: 0,
        outCount1: 0,
        chargeIds: [],
        tabTit: [
          {
            type: 'selection',
            width: 60,
            align: 'center',
            fixed: 'left'
          },
          {
            type: 'index2', align: 'center', minWidth: 80, fixed: 'left',
            render: (h, params) => {
              return h('span', params.index + (this.param.pageNum - 1) * this.param.pageSize + 1);
            }
          },
          {
            title: '报名点',
            align: 'center',
            minWidth: 120,
            key: 'chargeSource',
            fixed: 'left'
          },
          {
            title: '姓名',
            align: 'center',
            minWidth: 100,
            key: 'traineeName',
            fixed: 'left'

          },
          {
            title: '收费项',
            align: 'center',
            minWidth: 120,
            key: 'chargeName',
            fixed: 'left'
          },
          {
            title: '车型',
            key: 'carType',
            minWidth: 120,
            align: 'center',
            fixed: 'left',
            render: (h, p) => {
              if(p.row.information == ''){
                return h('Tag', {
                  props: {
                    color: '#FFA2D3'
                  }
                }, '体检')
              }else {
                return h('div', [
                  h('Tooltip', {
                    props: {
                      placement: 'top',
                      content: p.row.information.arrearage == '10' ? '分期' : '未分期'
                    }
                  }, [
                    h('Tag', {
                      props: {
                        type: 'dot',
                        color: p.row.information.arrearage == '10' ? 'error' : 'success'
                      }
                    }, p.row.information.carType)
                  ])
                ])
              }
            }
          },
          // {
          //   title: '收支',
          //   align: 'center',
          //   minWidth: 120,
          //   render: (h, p) => {
          //     let val = this.dictUtil.getValByCode(this, 'ZDCLK1016', p.row.inOutType)
          //     return h('div', val)
          //   }
          // },
          {
            title: '收费金额',
            key: 'chargeFee',
            minWidth: 120,
            fixed: 'left',
            align: 'center'
          },
          {
            title: '证件号码',
            align: 'center',
            minWidth: 180,
            key: 'idCardNo'
          },
          {
            title: '收费时间',
            key: 'cjsj',
            minWidth: 100,
            align: 'center',
            render: (h, p) => {
              return h('div',
                p.row.cjsj.substring(0, 10)
              )
            }
          },
          {
            title: '收款方式',
            align: 'center',
            minWidth: 120,
            render: (h, p) => {
              let val = this.dictUtil.getValByCode(this, 'ZDCLK1004', p.row.chargeType)
              return h('div', val)
            }
          },
          {
            title: '收款/缴费人',
            align: 'center',
            minWidth: 120,
            key: 'receiver',
            render: (h, p) => {
              let name = p.row.receiver.split('-')
              // let val = this.dictUtil.getValByCode(this, 'ZDCLK1004', p.row.chargeType)
              return h('div', name[name.length - 1])
            }
          },
          {
            title: '备注',
            align: 'center',
            minWidth: 200,
            render: (h, p) => {
              return h('Input', {
                props: {
                  value: '',
                  type: 'textarea',
                  placeholder: '备注信息',
                  rows: 1
                },
                style: {
                  marginTop: '8px',
                  marginBottom: '8px',
                  width: '100%'
                },
                on: {
                  'on-change': (event) => {
                    var v = this;
                    p.row.remark = event.srcElement.value;
                    /* //this.payOk.remark = event.srcElement.value;
                     v.manages[p.index].remark=  event.srcElement.value;*/
                    // this.payOk.remark = event.srcElement.value;
                  }
                }
              })
            }
          },
          {
            title: '操作',
            align: 'center',
            minWidth: 90,
            key: 'cz',
            fixed: 'right',
            render: (h, p) => {
              return h('div', [
                h('Tooltip',
                  {props: {placement: 'top', content: '通过',}},
                  [
                    h('Button', {
                      props: {
                        type: 'primary',
                        size: 'small',
                        icon: 'md-checkmark'
                      },
                      style: {
                        marginRight: '10px'
                      },
                      on: {
                        click: () => {
                          console.log(p.row.remark);
                          this.pays = [];
                          let obj ={}
                          obj.id = p.row.id
                          obj.remark = p.row.remark
                          // for (let r of this.manages) {
                          //   if (r.id === p.row.id) {
                          //     r.remark = p.row.remark;
                              this.pays.push(obj);
                          //   }
                          // }

                          this.OKpay()
                        }
                      }
                    })
                  ]
                )
              ])
            }
          }
        ],
        tableData: [],
        total: 0,//总数量
        param: {
          chargeCode:'',
          verify: '1',
          traineeNameLike: '',//学员姓名
          idCardNoLike: '',//证件号码
          sign: '1',
          //分页数据
          pageNum: 1,//当前页码
          pageSize: 10//每页显示数
        },
        pays: [],
        payOk: {
          chargeType: '10',//支付方式
          index: '',
          traineeId: '',//学员ID
          remark: ''//备注
        },
        manages: [],
        tabTit1: [
          {
            type: 'selection',
            width: 60,
            align: 'center',
            fixed: 'left'
          },
          {
            type: 'index2', align: 'center', minWidth: 80, fixed: 'left',
            render: (h, params) => {
              return h('span', params.index + (this.param1.pageNum - 1) * this.param1.pageSize + 1);
            }
          },
          {
            title: '报名点',
            align: 'center',
            minWidth: 140,
            key: 'chargeSource',
            fixed: 'left'
          },
          {
            title: '姓名',
            minWidth: 100,
            align: 'center',
            key: 'traineeName',
            fixed: 'left'
          },
          {
            title: '收费项',
            align: 'center',
            minWidth: 140,
            key: 'chargeName',
            fixed: 'left'
          },
          {
            title: '车型',
            key: 'carType',
            minWidth: 120,
            align: 'center',
            fixed: 'left',
            render: (h, p) => {
              if(p.row.information == ""){
                h('Tag', {
                  props: {
                    color: 'default'
                  }
                }, '——')
              }else {
                return h('div', [
                  h('Tooltip', {
                    props: {
                      placement: 'top',
                      content: p.row.information.arrearage == '10' ? '分期' : '未分期'
                    }
                  }, [
                    h('Tag', {
                      props: {
                        type: 'dot',
                        color: p.row.information.arrearage == '10' ? 'error' : 'success'
                      }
                    }, p.row.information.carType)
                  ])
                ])
              }
            }
          },
          // {
          //   title: '收支',
          //   align: 'center',
          //   minWidth: 120,
          //   render: (h, p) => {
          //     let val = this.dictUtil.getValByCode(this, 'ZDCLK1016', p.row.inOutType)
          //     return h('div', val)
          //   }
          // },
          {
            title: '收费金额',
            key: 'chargeFee',
            minWidth: 120,
            fixed: 'left',
            align: 'center'
          },
          {
            title: '证件号码',
            align: 'center',
            minWidth: 180,
            key: 'idCardNo'
          },
          {
            title: '收费时间',
            key: 'cjsj',
            minWidth: 120,
            align: 'center',
            render: (h, p) => {
              return h('div',
                p.row.cjsj.substring(0, 10)
              )
            }
          },
          {
            title: '收款方式',
            align: 'center',
            minWidth: 120,
            render: (h, p) => {
              let val = this.dictUtil.getValByCode(this, 'ZDCLK1004', p.row.chargeType)
              return h('div', val)
            }
          },
          {
            title: '收款/缴费人',
            align: 'center',
            minWidth: 120,
            key: 'receiver'
          },
          {
            title: '备注',
            align: 'center',
            minWidth: 200,
            key: 'remark'
          },
          {
            title: '审核人',
            align: 'center',
            minWidth: 120,
            key: 'verifier'
          },
          {
            title: '审核时间',
            key: 'verifyTime',
            align: 'center',
            minWidth: 120,
            render: (h, p) => {
              return h('div', [
                h('Tooltip', {
                  props: {
                    placement: 'top',
                    content: p.row.verifyTime
                  }
                }, [
                  h('Time', {
                    props: {
                      time: new Date(p.row.verifyTime.replace(/-/g, "/"))
                    }
                  })
                ])
              ])
            }
          },
          {
            title: '操作',
            align: 'center',
            fixed: 'right',
            minWidth: 90,
            key: 'cz',
            // fixed: 'right',
            render: (h, p) => {
              return h('div', [
                h('Tooltip',
                  {props: {placement: 'top', content: '撤回',}},
                  [
                    h('Button', {
                      props: {
                        type: 'error',
                        size: 'small',
                        icon: 'ios-redo'
                      },
                      style: {
                        marginRight: '10px'
                      },
                      on: {
                        click: () => {
                          console.log(p.row.remark);
                          this.chargeIds = [];
                          this.chargeIds.push(p.row.id);
                          this.retract()
                        }
                      }
                    })
                  ]
                )
              ])
            }
          }
        ],
        tableData1: [],
        total1: 0,//总数量
        param1: {
          chargeCode:'',
          verifyTimeGte:'',
          verifyTimeLte:'',
          traineeNameLike: '',//学员姓名
          idCardNoLike: '',//证件号码
          remarkLike:'',//备注信息
          //verifyTimeLike:'',
          // verify: '2',
          //分页数据
          pageNum: 1,//当前页码
          pageSize: 10//每页显示数
        },
        typeList:[],
        chargeIds: [],
        manages1: [],
        typeCode: 'ZDCLK1024'
      }
    },
    created() {
      this.getTypeList()

      this.getParam()
      this.getParam1()

      this.getPagerList()
    },
    watch:{
      pays:function(n,o){
      },
      'param.traineeNameLike':function (n,o) {
        console.log(n);
        this.fineSup(n)
      },
      'param.idCardNoLike':function (n,o) {
        this.fineSup(n)
      },

      'param1.traineeNameLike':function (n,o) {
        console.log(n);
        this.fineSup1(n)
      },
      'param1.idCardNoLike':function (n,o) {
        this.fineSup1(n)
      },
      'param1.remarkLike':function (n,o) {
        this.fineSup1(n)
      },
      // 'param1.chargeCode':function (n,o) {
      //   console.log(n);
      // }
    },
    computed: {
      AutoReadCard() {
        if (window.vueObject.$data.card.CardNo) {
          this.param.idCardNoLike = window.vueObject.$data.card.CardNo;
          this.param.nameLike = window.vueObject.$data.card.NameA;
          this.getPagerList();
        }

        return "";
      }
    },
    methods: {
      fineSup(n){
        if(n!=''){
          this.param.chargeCode = ''
        }
      },
      fineSup1(n){
        if(n!=''){
          console.log('123',this.param1);
          this.param1.chargeCode = ''
          this.chargeTime = []
          this.DatePickerC(['',''])
        }
        if(this.param1.traineeNameLike == '' &&
          this.param1.idCardNoLike == '' &&
          this.param1.remarkLike == ''){
          this.chargeTime = [this.AF.trimDate() , this.AF.trimDate() ]
          this.DatePickerC([this.AF.trimDate() , this.AF.trimDate() ])
        }
      },
      getTypeList(){
        this.typeList = this.dictUtil.getByCode(this,this.typeCode);
      },
      DatePickerC(data) {
        this.param1.verifyTimeGte = data[0] ;
        this.param1.verifyTimeLte = data[1] ;
        this.param.pageNum = 1
        this.getPagerList1();
      },
      MenuClick(name) {
        console.log(name);
        this.activeName = name
        if (name == '1') {
          this.getPagerList()
        } else if (name == '2') {
          this.getPagerList1()
        }
      },
      getParam(){
        if(this.$store.state.app.params[this.$route.name+'charge-examine']){
          this.param = this.$store.state.app.params[this.$route.name+'charge-examine']
        }else {
          this.param.chargeCode = '0000'
        }
      },
      getParam1(){
        if(this.$store.state.app.params[this.$route.name+'charge-examine1']){
          this.param1 = this.$store.state.app.params[this.$route.name+'charge-examine1']
          console.log(this.param1);
          if(this.param1.verifyTimeGte && this.param1.verifyTimeGte.length>10){
            this.chargeTime[0] = this.param1.verifyTimeGte.substring(0,10)
          }
          if(this.param1.verifyTimeGte && this.param1.verifyTimeLte.length>10){
            this.chargeTime[1] = this.param1.verifyTimeLte.substring(0,10)
          }
        }else {
          this.chargeTime[0] = this.AF.trimDate()
          this.chargeTime[1] = this.AF.trimDate()
        }
        this.DatePickerC([this.chargeTime[0] , this.chargeTime[1] ])
      },
      getPagerList() {
        var v = this;
        this.pays = []
        this.count1 = 0
        this.$http.post(this.apis.SFSH.PAGER, this.param).then((res) => {
          this.count = 0;
          this.inCount = 0;
          this.outCount = 0;
          // v.AF.setItem(v.$route.name+'charge-examine',v.param)
          this.$store.commit('A_params',[v.$route.name+'charge-examine',v.param])
          if(res.code == 200 && res.page && res.page.list.length!=0){
            this.inCount = res.page.list[0].totalFee;
            this.outCount = res.page.list[0].outFee;
            v.tableData = res.page.list;
            v.total = res.page.total;
          }else {
            v.tableData = [];
            v.total = 0;
          }
        })
      },
      OKpay: function () {//审核确认
        console.log(this.pays.length);
        if (this.pays.length < 1) {
          this.swal({
            title:'请选择收费记录',
            type:'warning'
          })
          return;
        }else{
          for (let r of this.pays) {
            r.chargeType = '10';
          }
          let arr = [];
          for (let r of this.pays) {
            delete r.information;
            arr.push(r);
          }
          this.pays = arr;
          this.$http.post(this.apis.SFSH.OK, {chargeManagements: JSON.stringify(this.pays)}).then((res) => {
            if (res.code == 200) {
              this.getPagerList()
              this.swal({
                title:res.message,
                type:'success'
              })
            } else {
              this.swal({
                title:res.message,
                type:'error'
              })
            }
          })
          this.pays = [];
          this.count = 0;
          this.count1 = 0;
          this.chargeIds = []

        }
      },
      getPagerList1() {
        var v = this;
        this.pays = []
        this.count1 = 0
        if(this.param1.verifyTimeLte.length == 10 ){
          this.param1.verifyTimeLte = this.param1.verifyTimeLte + ' 23:59:59';
        }
        if(this.param1.verifyTimeGte.length == 10){
          this.param1.verifyTimeGte = this.param1.verifyTimeGte  + ' 00:00:00';
        }
        this.$http.post(this.apis.SFSH.PAGER, this.param1).then((res) => {
          this.count1 = 0;
          this.inCount1 = 0;
          this.outCount1 = 0;
          // v.AF.setItem(v.$route.name+'charge-examine1',v.param1)
          this.$store.commit('A_params',[v.$route.name+'charge-examine1',v.param1])
          if(res.code == 200 && res.page && res.page.list.length!=0){
            this.inCount1 = res.page.list[0].totalFee;
            this.outCount1 = res.page.list[0].outFee;
            v.tableData1 = res.page.list;
            v.total1 = res.page.total;
          }else {
            v.tableData1 = [];
            v.total1 = 0;
          }
        })
      },
      retract() {
        if (this.chargeIds.length <= 0) {
          this.$Message.error('请选择需要撤回的记录');
          return;
        }
        this.$http.post(this.apis.SFSH.RETRACT, {chargeIds: JSON.stringify(this.chargeIds)}).then((res) => {
          if (res.code == 200) {
            this.getPagerList1()
            this.$Message.success(res.message);
          } else {
            this.$Message.error(res.message);
          }
        })
        this.count = 0;
        this.count1 = 0;
        this.chargeIds = []
      }
      ,
      tabcheck(arr) {
        console.log(arr);
      },
      winPrint() {
        this.AF.WinPrint(this, this.printMess, 'printexamine')
      },
      pageChange(n, num) {
        if (num == 0) {
          this.param.pageNum = n
          this.getPagerList()
        } else if (num == 1) {
          this.param1.pageNum = n
          this.getPagerList1()
        }
      },
      pageSizeChange(n, num) {
        this.param.pageSize = n
        if (num == 0) {
          this.param.pageSize = n
          this.getPagerList()
        } else if (num == 1) {
          this.param.pageSize = n
          this.getPagerList1()
        }
      },
      changeIds(currentRow) {
        let arr = []
        this.count1 = 0
        for (let r of currentRow) {
          arr.push(r);
          this.count1 = this.count1 + r.chargeFee
        }
        this.pays = arr;
      },
      revokeCharge(currentRow) {
        this.printMess = currentRow
        let arr = [];
        for (let r of currentRow) {
          arr.push(r.id)
        }
        this.chargeIds = arr;
      },
      excel(){
        window.open(http.url + '/pub/exportExcel?traineeNameLike=' + this.param1.traineeNameLike + '&idCardNoLike=' + this.param1.idCardNoLike + '&verifyTimeGte=' + this.param1.verifyTimeGte + '&verifyTimeLte=' + this.param1.verifyTimeLte + '&remarkLike=' + this.param1.remarkLike,'_blank');
      }
    }
  }
</script>

<style scoped>

</style>
