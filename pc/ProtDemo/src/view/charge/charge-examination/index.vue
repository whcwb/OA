<template>
  <div class="box_col" style="position: relative">
    <!--<pager-tit title="考试缴费" iconname="ios-football"></pager-tit>-->
    {{AutoReadCard}}
    <Menu mode="horizontal" :active-name="activeName" @on-select="MenuClick">
      <MenuItem name="1">
        <div style="font-weight: 700;font-size: 16px">
          待缴学员
        </div>
      </MenuItem>
      <MenuItem name="2">
        <div style="font-weight: 700;font-size: 16px">
          缴费查询
        </div>
      </MenuItem>
    </Menu>
    <div class="tagTit" style="position: absolute;right: 80px;top: 13px;z-index: 999">
      <Tag type="dot" :color="TagDot == index ? 'primary' : 'default'"
           style="cursor: pointer"
           @click.native="kmCheck(index,item)"
           v-for="(item,index) in [{val:'科目一',key:'10'},{val:'科目二',key:'20'},{val:'科目三',key:'30'}]">{{item.val}}
      </Tag>
    </div>
    <div class="box_col_100" v-if="activeName=='1'" style="padding-top: 8px">
      <Row :gutter="10" style="margin-bottom: 8px">
        <Col span="6" :lg="6" :md="24">
          <div style="width:100%;font-size: 18px;font-weight: 700">
            待缴学员 <span style="color: #ff9900">:{{total}}人</span>
          </div>
        </Col>
        <Col span="3" :lg="3" :md="4">
          <div style="width: 100%">
            <Select v-model="param.jgdm"
                    clearable
                    @on-change="CasChange">
              <Option v-for="item in CascaderList" :value="item.value" :key="item.value">{{ item.label }}</Option>
            </Select>
          </div>
        </Col>
        <Col span="3" :lg="3" :md="4">
          <div style="width: 100%">
            <Select v-model="param.jgmcLike" placeholder="请选择驾校" @on-change="getPagerList">
              <Option v-for="(item,index) in jx" :value="item.val" :key="index">{{ item.val }}</Option>
            </Select>
          </div>
        </Col>
        <Col span="4" :lg="4" :md="5">
          <div v-if="TagDot==0">
            <DatePicker v-model="param.firSubTestTimeLike" split-panels format="yyyy-MM-dd"
                        type="date" placeholder="科目一考试时间"
                        @on-change="getPagerList"></DatePicker>
          </div>
          <div v-else-if="TagDot==1">
            <DatePicker v-model="param.secSubTestTimeLike" split-panels format="yyyy-MM-dd"
                        type="date" placeholder="科目二考试时间"
                        @on-change="getPagerList"></DatePicker>
          </div>
          <div v-else-if="TagDot==2">
            <DatePicker v-model="param.thirdSubTestTimeLike" split-panels format="yyyy-MM-dd"
                        type="date" placeholder="科目三考试时间"
                        @on-change="getPagerList"></DatePicker>
          </div>
        </Col>
        <Col span="4" :lg="4" :md="5">
          <div>
            <Input v-model="param.idCardNoLike"
                   @on-enter="getPagerList"
                   placeholder="证件号码"/>
          </div>

        </Col>
        <Col span="2" :lg="2" :md="3">
          <Button type="primary" @click="getPagerList">
            <Icon type="md-search"></Icon>
            <!--查询-->
          </Button>
        </Col>
        <Col span="2" :lg="2" :md="3">
            <Tooltip content="导出Excel" placement="top">
              <Button type="primary" @click="excel">
                <Icon type="ios-cloud-download-outline"/>
              </Button>
            </Tooltip>
        </Col>

      </Row>

      <Table size="small" :height="AF.getPageHeight()-320" stripe
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
              @on-page-size-change='(n)=>{pageSizeChange(n)}'
              @on-change='(n)=>{pageChange(n)}'>
        </Page>
      </div>
    </div>

    <div class="box_col_100" v-if="activeName=='2'" style="padding-top: 8px">
      <Row :gutter="10" style="margin-bottom: 8px">
        <Col span="5" :lg="5" :md="24">
          <div style="width:100%;font-size: 18px;font-weight: 700">
            缴费查询
          </div>
        </Col>
        <Col span="4" :lg="4" :md="5">
          <div style="width: 100%">
            <Select v-model="param1.jgdm"
                    clearable
                    @on-change="CasChange1">
              <Option v-for="item in CascaderList" :value="item.value" :key="item.value">{{ item.label }}</Option>
            </Select>
          </div>
        </Col>
        <Col span="3" :lg="3" :md="4">
          <DatePicker v-model="param1Time" split-panels format="yyyy-MM-dd"
                      :clearable="false"
          type="date" placeholder="科目一缴费时间"
          @on-change="getPagerList1"></DatePicker>
          <!--<div v-if="TagDot==0">-->
            <!--<DatePicker v-model="param1.firSubPaymentTimeLike" split-panels format="yyyy-MM-dd"-->
                        <!--type="date" placeholder="科目一缴费时间"-->
                        <!--@on-change="getPagerList1"></DatePicker>-->
          <!--</div>-->
          <!--<div v-else-if="TagDot==1">-->
            <!--<DatePicker v-model="param1.secSubPaymentTimeLike" split-panels format="yyyy-MM-dd"-->
                        <!--type="date" placeholder="科目二缴费时间"-->
                        <!--@on-change="getPagerList1"></DatePicker>-->
          <!--</div>-->
          <!--<div v-else-if="TagDot==2">-->
            <!--<DatePicker v-model="param1.thirdSubPaymentTimeLike" split-panels format="yyyy-MM-dd"-->
                        <!--type="date" placeholder="科目三缴费时间"-->
                        <!--@on-change="getPagerList1"></DatePicker>-->
          <!--</div>-->
        </Col>
        <Col span="3" :lg="3" :md="4">
          <div style="width: 100%">
            <Select v-model="param1.jgmcLike" placeholder="请选择驾校">
              <Option v-for="(item,index) in jx" :value="item.val" :key="index">{{ item.val }}</Option>
            </Select>
          </div>
        </Col>
        <Col span="2" :lg="2" :md="4">
          <div>
            <Input v-model="param1.nameLike"
                   @on-enter="getPagerList1"
                   placeholder="请输入学员姓名"/>
          </div>
        </Col>
        <Col span="4" :lg="4" :md="5">
          <div>
            <Input v-model="param1.idCardNoLike"
                   @on-enter="getPagerList1"
                   placeholder="证件号码"/>
          </div>
        </Col>
        <Col span="3" :lg="3" :md="4">
          <Button type="primary" @click="getPagerList1">
            <Icon type="md-search"></Icon>
            <!--查询-->
          </Button>
          <Tooltip content="数据导出" style="float: right">
            <Button type="primary" @click="winPrint">
              <Icon type="md-cloud-download" />
            </Button>
          </Tooltip>

        </Col>
      </Row>

      <Table border size="small" :height="AF.getPageHeight()-320"
             @on-selection-change="tabcheck" stripe
             :columns="tabTit1" :data="tableData1"></Table>
      <div style="text-align: right;padding: 6px 0">
        <Page :total=total1
              :current=param1.pageNum
              :page-size=param1.pageSize
              :page-size-opts=[8,10,20,30,40,50]
              show-total
              show-elevator
              show-sizer
              placement='top'
              @on-page-size-change='(n)=>{pageSizeChange(n)}'
              @on-change='(n)=>{pageChange(n)}'>
        </Page>
      </div>
    </div>

    <component :is="compName" :printMess='printMess'></component>
  </div>
</template>

<script>
  import http from '@/axios/index';

  export default {
    name: "index",
    methods: {},
    watch:{
      param1Time:function (n,o) {
        console.log(n);
        // this.param1.firSubPaymentTimeLike = n
        // this.param1.secSubPaymentTimeLike = n
        // this.param1.thirdSubPaymentTimeLike = n
      }
    },
    data: function () {
      return {
        activeName: '1',
        compName: '',
        printMess: [],
        TagDot: 0,
        count: 20000,
        count1: 5000,
        tabTit: [],
        tabTitFT: [
          {
            type: 'index2', align: 'center', width: 80, fixed: 'left',
            render: (h, p) => {
              return h('span', p.index + (this.param.pageNum - 1) * this.param.pageSize + 1);
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
            title: '证件号码',
            align: 'center',
            minWidth: 180,
            key: 'idCardNo'
          },
          {
            title: '受理流水号',
            align: 'center',
            minWidth: 180,
            key: 'serialNum'
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
                    content: p.row.arrearage == '10' ? '欠费' : '未欠费'
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
            title: '报名点',
            align: 'center',
            minWidth: 120,
            key: 'jgmc',
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
            title: '实付金额',
            align: 'center',
            minWidth: 100,
            render: (h, p) => {
              return h('div', this.kmMoney)
            }
          },
          {
            title: '考试科目',
            minWidth: 100,
            align: 'center',
            render: (h, p) => {
              let val = this.dictUtil.getValByCode(this, 'ZDCLK1010', p.row.status)
              return h('div', val)
            }
          }, {
            title: '考试时间',
            minWidth: 120,
            align: 'center',
            render: (h, p) => {
              let a = ''
              switch (p.row.status) {
                case '10':
                  a = '-'
                  break;
                case '20':
                  a = p.row.secSubTestTime
                  break;
                case '30':
                  a = p.row.thirdSubTestTime
                  break;
              }
              return h('div', a)
            }
          },
          {
            title: '备注',
            align: 'center',
            minWidth: 220,
            render: (h, p) => {
              return h('Input', {
                props: {
                  value: '',
                  type: 'textarea',
                  placeholder: '备注信息',
                  rows:1
                },
                style: {
                  marginTop: '8px',
                  marginBottom: '8px',
                  width: '100%'
                },
                on: {
                  'on-change': (event) => {
                    p.row.remark = event.srcElement.value;
                    this.payOk.remark = event.srcElement.value;
                  }
                }
              })
            }
          },
          {
            title: '操作',
            align: 'center',
            key: 'cz',
            minWidth: 90,
            fixed: 'right',
            render: (h, p) => {
              return h('div', [
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
                      // console.log(this.payOk.remark);
                      this.payOk.traineeId = p.row.id
                      this.OKpay(p.index)
                    }
                  }
                })
              ])
            }
          }
        ],
        tabTitS: [
          {
            type: 'index2', align: 'center', width: 80, fixed: 'left',
            render: (h, p) => {
              return h('span', p.index + (this.param.pageNum - 1) * this.param.pageSize + 1);
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
            title: '证件号码',
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
                    content: p.row.arrearage == '10' ? '欠费' : '未欠费'
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
            title: '报名点',
            align: 'center',
            minWidth: 120,
            key: 'jgmc',
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
            title: '科目二',
            align: 'center',
            minWidth: 120,
            render: (h, p) => {
              let a = ''
              if (p.row.secSub == '40') {
                return h('div', [
                  h('Tag', {
                    props: {
                      color: 'success'
                    }
                  }, '合格')
                ])
              } else if (p.row.secSub == '30') {
                return h('div', [
                  h('Tag', {
                    props: {
                      color: 'error'
                    }
                  }, '不合格')
                ])
              } else if (p.row.secSubPaymentTime != '' && p.row.secSub != '30' && p.row.secSub != '40') {
                return h('div', [
                  h('Tag', {
                    props: {
                      color: 'primary'
                    }
                  }, '初考已缴')
                ])
              } else if (p.row.secSubPaymentTime == '') {
                return h('div', [
                  h('Tooltip', {
                    props: {
                      placement: 'top',
                      content: '点击：确定缴纳科二初考费'
                    }
                  }, [
                    h('Button', {
                      props: {
                        type: "warning",
                        size:'small'
                      },
                      on: {
                        click: () => {
                          let a = {
                            traineeId: p.row.id,//学员ID
                            remark: '',//备注
                            km: '20'
                          }
                          this.swal({
                            text: '是否缴纳科二初考费',
                            type: 'warning',
                            showCancelButton: true,
                            confirmButtonText: '确定',
                            cancelButtonText: '取消',
                          }).then((willDelete) => {
                            if (willDelete.value) {
                              this.$http.post(this.apis.KSSF.OKPAY, a).then((res) => {
                                if (res.code == 200) {
                                  this.getpager();
                                  this.$Message.success(res.message);
                                } else {
                                  this.$Message.error(res.message);
                                }
                              })
                            }
                          });
                        }
                      }
                    }, '初考待缴')
                  ])
                ])
              }
              return h('div', a)
            }
          },
          {
            title: '实付金额',
            align: 'center',
            minWidth: 100,
            render: (h, p) => {
              return h('div', this.kmMoney)
            }
          },
          {
            title: '考试科目',
            minWidth: 100,
            align: 'center',
            render: (h, p) => {
              let val = this.dictUtil.getValByCode(this, 'ZDCLK1010', p.row.status)
              return h('div', val)
            }
          },
          {
            title: '考试时间',
            minWidth: 120,
            align: 'center',
            render: (h, p) => {
              let a = ''
              switch (p.row.status) {
                case '10':
                  a = '-'
                  break;
                case '20':
                  a = p.row.secSubTestTime
                  break;
                case '30':
                  a = p.row.thirdSubTestTime
                  break;
              }
              return h('div', a)
            }
          },
          {
            title: '备注',
            align: 'center',
            minWidth: 220,
            render: (h, p) => {
              return h('Input', {
                props: {
                  value: '',
                  rows:1,
                  type: 'textarea',
                  placeholder: '备注信息'
                },
                style: {
                  marginTop: '8px',
                  marginBottom: '8px',
                  width: '100%'
                },
                on: {
                  'on-change': (event) => {
                    p.row.remark = event.srcElement.value;
                    this.payOk.remark = event.srcElement.value;
                  }
                }
              })
            }
          },
          {
            title: '操作',
            align: 'center',
            key: 'cz',
            minWidth: 90,
            fixed: 'right',
            render: (h, p) => {
              return h('div', [
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
                      if (p.row.secSubPaymentTime == '') {
                        this.swal({
                          text: '请先确定缴纳科二初考费',
                          type: 'warning'
                        });
                      } else if (p.row.secSub == '30') {
                        this.swal({
                          text: '学员考试不合格，请缴纳并核对核对科二补考费',
                          type: 'warning',
                          showCancelButton: true,
                          confirmButtonText: '确定',
                          cancelButtonText: '取消',
                        }).then((willDelete) => {
                          if (willDelete.value) {
                            this.payOk.traineeId = p.row.id
                            this.OKpay(p.index)
                          }
                        });
                      } else {
                        this.payOk.traineeId = p.row.id
                        this.OKpay(p.index)
                      }
                    }
                  }
                })
              ])
            }
          }
        ],


        tableData: [],
        total: 0,//总数量
        param: {
          jgdm: '',
          jgmcLike: '',
          // nameLike: '',
          idCardNoLike: '',
          sign: '7',
          firSubTestTimeLike: '',
          secSubTestTimeLike: '',
          thirdSubTestTimeLike: '',
          //分页数据
          pageNum: 1,//当前页码
          pageSize: 20//每页显示数
        },
        payOk: {
          // chargeType: '10',//支付方式
          index: '',
          traineeId: '',//学员ID
          remark: '',//备注
          km: '10'
        },
        tabTit1: [
          {
            type: 'selection',
            align: 'center',
            width: 50,
            fixed: 'left'
          },
          {
            type: 'index',
            width: 60,
            align: 'center',
            fixed: 'left'
          },
          {
            title: '姓名',
            minWidth: 120,
            align: 'center',
            key: 'name',
            fixed: 'left'
          },
          {
            title: '证件号码',
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
                    content: p.row.arrearage == '10' ? '欠费' : '未欠费'
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
            title: '报名点',
            align: 'center',
            minWidth: 120,
            key: 'jgmc',
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
            title: '实付金额',
            minWidth: 100,
            // key: 'chargeRecord.chargeFee',
            align: 'center',
            render: (h, p) => {
              // let val = p.row.chargeRecord.chargeFee;
              // return h('div',val);
              return h('div', this.kmMoney)
            }
          },
          {
            title: '交费方式',
            align: 'center',
            minWidth: 120,
            render: (h, p) => {
              let val = this.dictUtil.getValByCode(this, 'ZDCLK1004', p.row.chargeRecord.chargeType)
              return h('div', val)
            }
          },
          {
            title: '考试时间',
            align: 'center',
            minWidth: 120,
            render: (h, p) => {
              let val;
              if (p.row.status == '10') {
                val = p.row.firSubTestTime;
              } else if (p.row.status == '20') {
                val = p.row.secSubTestTime;
              } else {
                val = p.row.thirdSubTestTime;
              }
              return h('div', val);
            }
          },
          {
            title: '考试地点',
            align: 'center',
            minWidth: 120,
            render: (h, p) => {
              let val = p.row.testInfo.testPlace;
              return h('div', val);
            }
          },
          {
            title: '收费时间',
            minWidth: 120,
            align: 'center',
            render: (h, p) => {
              return h('div', p.row.chargeRecord.chargeTime.substring(0,10))
            }
          },
          {
            title: '备注',
            align: 'center',
            width: 220,
            render: (h, p) => {
              return h('Input', {
                props: {
                  value: p.row.chargeRecord.remark,
                  type: 'textarea',
                  rows:1,
                  placeholder: '',
                  readonly: 'readonly'
                },
                style: {
                  marginTop: '8px',
                  marginBottom: '8px',
                  width: '100%'
                }
              })
            }
          },
          {
            title: '操作',
            align: 'center',
            minWidth: 90,
            fixed: 'right',
            render: (h, p) => {
              return h('div', [
                h('Button', {
                  props: {
                    type: 'error',
                    size: 'small'
                  },
                  on: {
                    click: () => {
                      this.retract(p.row.id, p.row.chargeRecord.id)
                    }
                  }
                }, '撤回')
              ])
            }
          }
        ],
        tableData1: [],
        total1: 0,//总数量
        param1Time:'',
        param1: {
          firSubPaymentTimeLike:'',
          secSubPaymentTimeLike: '',
          thirdSubPaymentTimeLike: '',
          jgmcLike: '',
          nameLike: '',
          idCardNoLike: '',
          sign: '8',
          //分页数据
          pageNum: 1,//当前页码
          pageSize:2//每页显示数
        },

        kmMoney: 0,
        chargeCode: '9998',
        CascaderList: [],
        jx: [],
        bmdT: [],
        bmdB: []
      }
    },
    created() {
      this.getKmMoney()
      this.getPagerList()
      this.getBmdList()
      this.getJTjx()
      this.tabTit = this.tabTitFT
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
      pageSizeChange(n){
        if(this.activeName == '1'){
          this.param.pageSize = n;
          this.getPagerList();
        }else{
          this.param1.pageSize = n;
          this.getPagerList1();
        }
      },
      pageChange(n){
        if(this.activeName == '1'){
          this.param.pageNum = n;
          this.getPagerList();
        }else{
          this.param1.pageNum = n;
          this.getPagerList1();
        }
      },
      MenuClick(name) {
        this.activeName = name
        if (name == '1') {
          this.getPagerList()
        } else if (name == '2') {
          this.param1Time = new Date()
          this.getPagerList1()
        }
      },
      getJTjx() {
        this.jx = this.dictUtil.getByCode(this, 'ZDCLK1019');
      },
      CasChange(val) {
          this.getPagerList()
      },
      CasChange1(val,) {
          this.getPagerList1()
      },
      getBmdList() {
        this.$http.get(this.apis.FRAMEWORK.getCurrentOrgTree, {timers: new Date().getTime()}).then((res) => {
          if (res.code === 200) {
            /*function tree(arr) {
              if (arr[0].value.length == 6) {
                return arr[0].children
              } else if (arr[0].value.length == 3) {
                return arr[0].children[0].children
              }
            }
            this.CascaderList = tree(res.result)*/
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
        this.getPagerList1()
      }
      ,
      getPagerList() {
        var v = this
        if (this.TagDot == 0 && this.param.firSubTestTimeLike != '') {
          this.param.firSubTestTimeLike = this.AF.trimDate(this.param.firSubTestTimeLike)
        } else if (this.TagDot == 1 && this.param.secSubTestTimeLike != '') {
          this.param.secSubTestTimeLike = this.AF.trimDate(this.param.secSubTestTimeLike)
        } else if (this.TagDot == 2 && this.param.thirdSubTestTimeLike != '') {
          this.param.thirdSubTestTimeLike = this.AF.trimDate(this.param.thirdSubTestTimeLike)
        } else {
          this.param.firSubTestTimeLike = ''
          this.param.secSubTestTimeLike = ''
          this.param.thirdSubTestTimeLike = ''
        }
        // this.param.pageNum = 1;
        // this.param.pageSize = 9999;
        this.$http.post(this.apis.CMONEY, this.param).then((res) => {
          if (res.code == 200 && res.page.list) {
            v.tableData = res.page.list
            v.total = res.page.total
          }
        })
      },
      OKpay(index) {
        console.log(this.payOk);
        this.$http.post(this.apis.KSSF.OKPAY, this.payOk).then((res) => {
          if (res.code == 200) {
            this.swal({
              type:'success',
              title:'操作成功'
            })
            this.getPagerList();
            this.$Message.success(res.message);
          } else {
            this.$Message.error(res.message);
          }
        })
      },
      getPagerList1() {
        var v = this

        if (this.TagDot == 0) {
          this.param1.firSubPaymentTimeLike = this.AF.trimDate(this.param1Time);
          this.param1.secSubPaymentTimeLike = '';
          this.param1.thirdSubPaymentTimeLike ='';
        } else if (this.TagDot == 1) {
          this.param1.firSubPaymentTimeLike = '';
          this.param1.secSubPaymentTimeLike = this.AF.trimDate(this.param1Time);
          this.param1.thirdSubPaymentTimeLike ='';
        } else if (this.TagDot == 2) {
          this.param1.firSubPaymentTimeLike = '';
          this.param1.secSubPaymentTimeLike = '';
          this.param1.thirdSubPaymentTimeLike = this.AF.trimDate(this.param1Time);
        }

        // if (this.TagDot == 0 && this.param1.firSubPaymentTimeLike != '') {
        //   this.param1.firSubPaymentTimeLike = this.AF.trimDate(this.param1Time);
        //   this.param1.secSubPaymentTimeLike = '';
        //   this.param1.thirdSubPaymentTimeLike ='';
        // } else if (this.TagDot == 1 && this.param1.secSubPaymentTimeLike != '') {
        //   this.param1.firSubPaymentTimeLike = '';
        //   this.param1.secSubPaymentTimeLike = this.AF.trimDate(this.param1Time);
        //   this.param1.thirdSubPaymentTimeLike ='';
        // } else if (this.TagDot == 2 && this.param1.thirdSubPaymentTimeLike != '') {
        //   this.param1.firSubPaymentTimeLike = '';
        //   this.param1.secSubPaymentTimeLike = '';
        //   this.param1.thirdSubPaymentTimeLike = this.AF.trimDate(this.param1Time);
        // } else {
        //   if(this.TagDot == 0){
        //     this.param1.firSubPaymentTimeLike = this.AF.trimDate();
        //     this.param1.secSubPaymentTimeLike = '';
        //     this.param1.thirdSubPaymentTimeLike ='';
        //   }else if(this.TagDot == 1){
        //     this.param1.firSubPaymentTimeLike = '';
        //     this.param1.secSubPaymentTimeLike = this.AF.trimDate();
        //     this.param1.thirdSubPaymentTimeLike ='';
        //   }else if(this.TagDot == 2){
        //     this.param1.firSubPaymentTimeLike = '';
        //     this.param1.secSubPaymentTimeLike = '';
        //     this.param1.thirdSubPaymentTimeLike = this.AF.trimDate();
        //   }
        // }
        this.$http.post(this.apis.CMONEY, this.param1).then((res) => {
          v.tableData1 = res.page.list
          v.total1 = res.page.total
        })
      },
      retract(id, chargeId) {
        this.$http.post(this.apis.KSSF.RETRACT, {traineeId: id, chargeId: chargeId}).then((res) => {
          if (res.code == 200) {
            this.swal({
              type:'success',
              title:'撤销成功'
            })
            this.getPagerList1()
            this.$Message.success(res.message);
          } else {
            this.$Message.error(res.message);
          }
        })
      },
      tabcheck(arr) {
        console.log(arr);
      }
      ,
      winPrint() {
        window.open(http.url + '/pub/exportTestCharge?jgmcLike=' + this.param1.jgmcLike + '&nameLike=' + this.param1.nameLike +
          '&idCardNoLike=' + this.param1.idCardNoLike + '&sign=' + this.param1.sign+'&firSubPaymentTimeLike=' + this.param1.firSubPaymentTimeLike
          +'&secSubPaymentTimeLike='+this.param1.secSubPaymentTimeLike + '&thirdSubPaymentTimeLike=' + this.param1.thirdSubPaymentTimeLike, '_blank');
      },
      excel(){
        window.open(http.url + '/pub/exportTestExcel?sign=' + this.param.sign + '&jgmcLike=' + this.param.jgmcLike + '&jgdm=' + this.param.jgdm + '&idCardNoLike=' + this.param.idCardNoLike
         + '&firSubTestTimeLike=' + this.param.firSubTestTimeLike + '&secSubTestTimeLike=' + this.param.secSubTestTimeLike + '&thirdSubTestTimeLike=' + this.param.thirdSubTestTimeLike, '_blank');
      },
      kmCheck(index, item) {
        this.payOk.km = item.key
        this.TagDot = index
        this.param1.pageNum =1;
        this.param.pageNum = 1;
        if (index == 0) {
          this.param1.pageNum =1;
          this.param.pageNum = 1;
          this.param.sign = 7
          this.param1.sign = 8
          this.chargeCode = '9998'
          this.tabTit = this.tabTitFT
        } else if (index == 1) {
          this.param.sign = 2
          this.param1.sign = 5
          this.chargeCode = '9997'
          this.tabTit = this.tabTitFT
        } else if (index == 2) {

          this.param.sign = 3;
          this.param1.sign = 6;
          this.chargeCode = '9996';
          console.log("科目三",this.param1)
          this.tabTit = this.tabTitS
        }
        this.getKmMoney()
        if (this.activeName == '1') {
          this.getPagerList()
        } else {
          this.getPagerList1()
        }
      },
      getKmMoney() {
        this.$http.post(this.apis.SFX.PAGER, {chargeCode: this.chargeCode}).then((res) => {
          // console.log('++++',res);
          if (res.page.list.length == 0) {
            this.kmMoney = 0
            return
          }
          this.kmMoney = res.page.list[0].amount
        })
      }
    }
  }
</script>

<style scoped>

</style>
