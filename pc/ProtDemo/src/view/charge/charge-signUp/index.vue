<template>
  <div class="box_col" style="position: relative">
    <!--<pager-tit title="报名收费" iconname="ios-football"></pager-tit>-->
    {{AutoReadCard}}
    <Menu mode="horizontal" :active-name="activeName" @on-select="MenuClick">
      <MenuItem name="1">
        <div style="font-weight: 700;font-size: 16px">
          待缴费学员
        </div>
      </MenuItem>
      <MenuItem name="2">
        <div style="font-weight: 700;font-size: 16px">
          已缴费学员
        </div>
      </MenuItem>
      <MenuItem name="3">
        <div style="font-weight: 700;font-size: 16px">
          收据已打印
        </div>
      </MenuItem>
      <MenuItem name="4">
        <div style="font-weight: 700;font-size: 16px">
          收据未打印
        </div>
      </MenuItem>
    </Menu>
    <div class="tagTit" style="position: absolute;right: 80px;top: 13px;z-index: 999">
      <Tag type="dot" color="success" style="margin-left: 16px">未分期</Tag>
      <Tag type="dot" color="error">分 期</Tag>
    </div>
    <div class="box_col_100" v-if="activeName=='1'">
      <Row :gutter="10" style="margin:8px 0">
        <Col span="6" :lg="6" :md="24">
          <div style="font-size: 18px;font-weight: 700;width: 100%">
            待缴费学员
            <span style="font-size: 16px;margin-left: 16px">
                    共:
                    <span style="font-size: 20px;color: #ff9900">
                      {{total}}
                    </span>
                    人-合计:
                    <span style="font-size: 20px;color: #ff9900">
                      {{allmoney}}
                    </span>
                    元
                  </span>
          </div>
        </Col>
        <Col span="4" :lg="4" :md="5">
          <div style="width:100%">
            <Select v-model="param.jgdm"
                    clearable
                    @on-change="CasChange">
              <Option v-for="item in CascaderList" :value="item.value" :key="item.value">{{ item.label }}</Option>
            </Select>
          </div>
        </Col>
        <Col span="4" :lg="4" :md="5">
          <div style="width: 100%">
            <Select v-model="param.jgmcLike" clearable placeholder="请选择驾校" @on-change="param.pageNum = 1,getPagerList()">
              <Option v-for="(item,index) in jXlist" :value="item.val" :key="index">{{ item.val }}</Option>
            </Select>
          </div>
        </Col>
        <Col span="4" :lg="4" :md="5">
          <div>
            <Input v-model="param.nameLike"
                   @on-enter="param.pageNum = 1,getPagerList()"
                   placeholder="请输入学员姓名"/>
          </div>
        </Col>
        <Col span="4" :lg="4" :md="6">
          <div>
            <Input v-model="param.idCardNoLike"
                   @on-enter="param.pageNum = 1,getPagerList()"
                   placeholder="证件号码"/>
          </div>
        </Col>
        <Col span="2" :lg="2" :md="3">
          <Button type="primary" @click="param.pageNum = 1,getPagerList()">
            <Icon type="md-search"></Icon>
            <!--查询-->
          </Button>
        </Col>
      </Row>
      <Table :height="AF.getPageHeight()-360" stripe size="small"
             @on-selection-change="djfCheck"
             :columns="tabTit" :data="tableData"></Table>
      <div style="text-align: right;padding: 6px 0">
        <div style="float: left">
          <Button type="primary" @click="PlToExamine">批量审核</Button>
        </div>
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
    <div class="box_col_100" v-if="activeName=='2' || activeName=='3' || activeName=='4'">
      <Row :gutter="10" style="margin:8px 0">
        <Col span="5" :lg="5" :md="24">
          <div style="font-size: 18px;font-weight: 700;width: 100%">
            收费记录
            <span style="font-size: 16px;margin-left: 16px">
                      共:
                    <span style="font-size: 20px;color: #ff9900">
                      {{total1}}
                    </span>
                    人-合计:
                    <span style="font-size: 20px;color: #ff9900">
                      {{allmoney1}}
                    </span>
                    元
                  </span>
          </div>
        </Col>
        <Col span="3" :lg="3" :md="4">
          <!--<DatePicker type="date" split-panels v-model="param1.confirmTimeLike" :clearable="false" @on-change="changeTime"  placeholder="审核日期"   style="width: 100% ;"></DatePicker>-->
          <DatePicker v-model="bmTime" split-panels type="daterange" :clearable="true" :editable="false"
                      @on-change="DatePickerC"
                      placement="bottom-end" placeholder="收费日期" style="width: 100% ;"></DatePicker>
        </Col>
        <Col span="3" :lg="3" :md="4">
          <div style="width: 100%">
            <Select v-model="param1.jgdm"
                    clearable
                    @on-change="CasChange1">
              <Option v-for="item in CascaderList" :value="item.value" :key="item.value">{{ item.label }}</Option>
            </Select>
          </div>
        </Col>
        <Col span="3" :lg="3" :md="4">
          <div style="width: 100%">
            <Select v-model="param1.jgmcLike" clearable placeholder="请选择驾校" @on-change="param1.pageNum = 1,getPagerList1()">
              <Option v-for="(item,index) in jXlist" :value="item.val" :key="index">{{ item.val }}</Option>
            </Select>
          </div>
        </Col>
        <Col span="3" :lg="3" :md="4">
          <div style="width: 100%">
            <Input v-model="param1.nameLike"
                   @on-enter="param1.pageNum = 1,getPagerList1()"
                   placeholder="请输入学员姓名"/>
          </div>
        </Col>
        <Col span="4" :lg="4" :md="5">
          <div style="width: 100%">
            <Input v-model="param1.idCardNoLike"
                   @on-enter="param1.pageNum = 1,getPagerList1()"
                   placeholder="证件号码"/>
          </div>
        </Col>
        <Col span="3" :lg="3" :md="3">
          <Button type="primary" @click="param1.pageNum = 1,getPagerList1()">
            <Icon type="md-search"></Icon>
            <!--查询-->
          </Button>
          <Tooltip content="打印" placement="bottom" style="float: right">
            <Button type="primary" @click="winPrint">
              <Icon type="md-print"/>
            </Button>
          </Tooltip>
        </Col>
      </Row>
      <Table :height="AF.getPageHeight()-360" stripe size="small"
             @on-selection-change="tabcheck"
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
              @on-page-size-change='(n)=>{pageSizeChange(n,1)}'
              @on-change='(n)=>{pageChange(n,1)}'>
        </Page>
      </div>
    </div>

    <component :is="compName" :userAudit="userAudit" :printMess='printMess'></component>
  </div>
</template>

<script>
  import printSignUp from '../../../components/print/printSignUp'
  import ch from './comp/chang'
  import fq from './comp/fenqi'
  import C_M from './comp/c_money'

  export default {
    name: "indexN",
    components: {
      printSignUp, ch,fq,C_M
    },
    data: function () {
      return {
        confirmMap:new Map(),
        bmTime:[this.AF.trimDate()+' 00:00:00',this.AF.trimDate() + ' 23:59:59'],
        activeName: '1',
        compName: '',
        userAudit: {},
        printMess: [],
        count: 0,
        count1: 0,
        tabTit: [
          {
            type: 'selection',
            align: 'center',
            width: 50,
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
            title: '实收金额',
            key: 'realPay',
            minWidth: 120,
            align: 'center'
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
                    console.log('change', event);
                    p.row.remark = event.srcElement.value;
                    this.confirmMap.set(p.row.id,event.srcElement.value)
                    console.log(this.confirmMap);
                    this.payOk.remark = event.srcElement.value;
                  }
                }
              })
            }
          },
          {
            title: '优惠金额',
            key: 'reducePrice',
            minWidth: 100,
            align: 'center',
            render: (h, p) => {
              if (p.row.reducePrice == "") {
                return h('div', '0')
              }
              return h('div', p.row.reducePrice)
            }
          },
          {
            title: '应收金额',
            key: 'registrationFee',
            minWidth: 100,
            align: 'center'
          },
          {
            title:'推荐人',
            key:'referrer',
            align:'center',
            minWidth:120,
            render: (h, p) => {
              let a =  p.row.referrer.split('-')
              return h('div',a[0])
            }
          },
          {
            title: '优惠状态',
            key: 'reduceStatus',
            minWidth: 130,
            align: 'center',
            render: (h, p) => {
              let val = ''
              if (p.row.reduceStatus == '') {
                val = '——'
              } else {
                val = this.dictUtil.getValByCode(this, 'ZDCLK1008', p.row.reduceStatus)
              }
              return h('Tag', {
                props: {
                  color: p.row.reduceStatus == '00' ? 'warning' : p.row.reduceStatus == '10' ? 'success' : 'default'
                }
              }, val)
            }
          },
          {
            title: '优惠项', align: 'center', key: 'reduceName', minWidth: 140,
            render: (h, p) => {
              let val = p.row.reduceName
              if (val == '') {
                val = '——'
              }
              return h('Tag', {
                props: {
                  color: p.row.reduceName == '' ? 'default' : 'green'
                }
              }, val)
            }
          },
          {
            title: '证件号码',
            align: 'center',
            minWidth: 180,
            key: 'idCardNo'
          },

          {
            title: '报名时间',
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
            key: 'skfs',
            minWidth: 140,
            align: 'center',
            render: (h, p) => {
              return h('div', [
                h('Select', {
                  props: {
                    value: '10',
                    size: 'small'
                  },
                  on: {
                    'on-change': (val) => {
                      this.payOk.chargeType = val
                    }
                  }
                }, [
                  h('Option', {
                    props: {
                      value: '10'
                    }
                  }, '转账支付'),
                  h('Option', {
                    props: {
                      value: '00'
                    }
                  }, '现金支付')
                ])
              ])
            }
          },
          {
            title: '操作',
            align: 'center',
            key: 'cz',
            fixed: 'right',
            width: 220,
            render: (h, p) => {
              return h('div', [
                h('Tooltip',
                  {props: {placement: 'top', content: '优惠修改',}},
                  [
                    h('Button', {
                      props: {type: 'text', icon: 'md-create', shape: 'circle', size: 'large', ghost: true},
                      style: {margin: '0 8px 0 0', fontSize: '24px', color: '#2db7f5'},
                      on: {
                        click: () => {
                          this.changeMess(p.row);
                        }
                      }
                    })
                  ]
                ),
                h('Tooltip',
                  {props: {placement: 'top', content: '收费修改',}},
                  [
                    h('Button', {
                      props: {
                        type: 'info',
                        size: 'small',
                      },
                      style: {margin: '0 10px 0 0'},
                      on: {
                        click: () => {
                          this.changeMoney(p.row);
                        }
                      }
                    },'改')
                  ]
                ),
                h('Tooltip',
                  {props: {placement: 'top', transfer: true, content: '分期收款',}},
                  [
                    h('Button', {
                      props: {type: 'warning',size: 'small',
                        // icon: 'md-checkmark',
                      },
                      style: {
                        marginRight: '10px'
                      },
                      on: {
                        click: () => {
                          if (p.row.arrearage == '10') {
                            this.swal({
                              text: '该学员已分期，不可重复操作！',
                              type: 'warning',
                              confirmButtonText: '关闭'
                            })
                          } else {
                            this.userAudit = p.row
                            this.compName = 'fq'
                          }
                        }
                      }
                    },'分')
                  ]
                ),

                h('Tooltip',
                  {props: {placement: 'top', transfer: true, content: '通过',}},
                  [
                    h('Button', {
                      props: {
                        type: 'primary',
                        size: 'small',
                        icon: 'md-checkmark',
                      },
                      style: {
                        marginRight: '10px'
                      },
                      on: {
                        click: () => {
                          // console.log(p.row.remark);
                          if (p.row.reduceStatus == '00') {
                            this.swal({
                              text: '优惠审核未通过',
                              type: 'warning',
                              confirmButtonText: '关闭'
                            })
                          } else {
                            this.payOk.traineeId = p.row.id
                            this.saveOk(p.index, p.row)
                            // this.OKpay(p.index)
                          }
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
        djfCheckList:'',
        bzs:'',
        total: 0,//总数量
        allmoney: 0,
        param: {
          nameLike: '',
          idCardNoLike: '',
          jgdm: '',
          sign: '1',
          //分页数据
          pageNum: 1,//当前页码
          pageSize: 10//每页显示数
        },
        payOk: {
          chargeType: '10',//支付方式
          index: '',
          traineeId: '',//学员ID
          remark: ''//备注
        },
        tabTit1: [
          {
            type: 'selection',
            align: 'center',
            width: 50,
            fixed: 'left'
          },
          {
            type: 'index3', align: 'center', minWidth: 80, fixed: 'left',
            render: (h, params) => {
              return h('span', params.index + (this.param1.pageNum - 1) * this.param1.pageSize + 1);
            }
          },
          {
            title: '报名点',
            align: 'center',
            minWidth: 140,
            fixed: 'left',
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
            title: '姓名',
            align: 'center',
            minWidth: 120,
            key: 'name',
            fixed: 'left'
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
            title: '实收金额',
            key: 'realPay',
            minWidth: 120,
            align: 'center'
          },

          {
            title: '收据编号',
            align: 'center',
            minWidth: 140,
            render: (h, p) => {
              var lt = p.row.chargeRecord.pjbh.length
              if(lt == 0){
                return h('div', [
                  h('Tag', {
                    props: {
                      color: 'warning'
                    }
                  }, '票据未打印')
                ])
              } else {
                let a = p.row.chargeRecord.pjbh.split('-')
                return h('div',
                  a[0]+'-'+a[1]
                );
              }
            }
          },
          {
            title: '备注',
            align: 'center',
            minWidth: 220,
            render: (h, p) => {
              return h('Input', {
                props: {
                  value: p.row.chargeRecord.remark,
                  type: 'textarea',
                  placeholder: '',
                  rows:1,
                  readonly: 'readonly',
                  disabled:'disabled'
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
            title:'推荐人',
            key:'referrer',
            align:'center',
            minWidth:120,
            render: (h, p) => {
              let a =  p.row.referrer.split('-')
              return h('div',a[0])
            }
          },
          {
            title: '证件号码',
            align: 'center',
            minWidth: 180,
            key: 'idCardNo'
          },
          {
            title: '操作人',
            align: 'center',
            minWidth: 150,
            render:(h,p) =>{
              return h('div',p.row.chargeRecord.receiver)
            }
          },
          {
            title: '优惠状态',
            key: 'reduceStatus',
            minWidth: 130,
            align: 'center',
            render: (h, p) => {
              let val = ''
              if (p.row.reduceStatus == '') {
                val = '——'
              } else {
                val = this.dictUtil.getValByCode(this, 'ZDCLK1008', p.row.reduceStatus)
              }
              return h('Tag', {
                props: {
                  color: p.row.reduceStatus == '00' ? 'warning' : p.row.reduceStatus == '10' ? 'success' : 'default'
                }
              }, val)
            }
          },
          {
            title: '应收金额',
            key: 'registrationFee',
            minWidth: 100,
            align: 'center'
          },
          {
            title: '优惠金额',
            key: 'reducePrice',
            minWidth: 100,
            align: 'center',
            render: (h, p) => {
              if (p.row.reducePrice == "") {
                return h('div', '0')
              }
              return h('div', p.row.reducePrice)
            }
          },
          {
            title: '收款方式',
            minWidth: 120,
            align: 'center',
            render: (h, p) => {
              let val = this.dictUtil.getValByCode(this, 'ZDCLK1004', p.row.chargeRecord.chargeType)
              return h('div', val)
            }
          },
          {
            title: '报名时间',
            minWidth: 120,
            key: 'cjsj',
            align: 'center',
            render: (h, p) => {
              return h('div',
                p.row.cjsj.substring(0, 10)
              )
            }
          },
          {
            title: '收费确认',
            minWidth: 120,
            align: 'center',
            render: (h, p) => {
              return h('div',
                p.row.chargeRecord.chargeTime.substring(0, 10)
              )
            }
          },

          {
            title: '操作',
            align: 'center',
            fixed: 'right',
            minWidth: 160,
            render: (h, p) => {
              return h('div', [
                h('Tooltip',
                  {props: {placement: 'top', content: '优惠修改',}},
                  [
                    h('Button', {
                      props: {type: 'text', icon: 'md-create', shape: 'circle', size: 'large', ghost: true},
                      style: {margin: '0 8px 0 0', fontSize: '24px', color: '#2db7f5'},
                      on: {
                        click: () => {
                          this.changeMess(p.row);
                        }
                      }
                    })
                  ]
                ),
                h('Tooltip',
                  {props: {placement: 'top', content: '收费修改',}},
                  [
                    h('Button', {
                      props: {
                        type: 'warning',
                        size: 'small',
                      },
                      style: {margin: '0 10px 0 0'},
                      on: {
                        click: () => {
                          this.changeMoney(p.row);
                        }
                      }
                    },'改')
                  ]
                ),
                h('Tooltip',
                  {
                    props: {
                      placement: 'top',
                      transfer: true,
                      content: '撤回',
                    }
                  },
                  [
                    h('Button', {
                      props: {
                        type: 'error',
                        size: 'small',
                        icon: 'ios-redo'
                      },
                      on: {
                        click: () => {
                          // this.retract(p.row.id)
                          this.messRemove(p.row)
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
      this.param1.confirmTimeGte = time + ' 00:00:00';
      this.param1.confirmTimeLte = time + ' 23:59:59';
      this.getPagerList();
      this.getBmdList();
      this.getJTjx();
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
      DatePickerC(data) {
        if(data[0].length == 10){
          this.param1.confirmTimeGte = data[0] + ' 00:00:00' ;
        }else {
          delete this.param1.confirmTimeGte
        }
        if(data[1].length == 10){
          this.param1.confirmTimeLte = data[1] + ' 23:59:59';
        }else {
          delete this.param1.confirmTimeLte
        }
        this.getPagerList1();
      },
      changeTime(val){
        // let time = this.AF.trimDate(val);
        // this.param1.confirmTimeLike = time;
        // this.getPagerList1();
      },
      MenuClick(name) {
        this.activeName = name;
        if (name == '1') {
          this.getPagerList()
        } else if (name == '2') {
          /*if(this.param1.confirmTimeLike== ''){
            this.param1.confirmTimeLike = this.AF.trimDate();
          }*/
          this.param1.pj = ''
          this.getPagerList1()
        } else if (name == '3') {
        /*if(this.param1.confirmTimeLike== ''){
            this.param1.confirmTimeLike = this.AF.trimDate();
          }*/
          this.param1.pj = '10'
          this.getPagerList1()
        } else if (name == '4') {
          /*if(this.param1.confirmTimeLike== ''){
            this.param1.confirmTimeLike = this.AF.trimDate();
          }*/
          this.param1.pj = '20'
          this.getPagerList1()
        }
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
      PlToExamine(){
        if(this.djfCheckList == ''){
          this.swal({
            title:'请选择需要审核的人员！',
            type:"warning"
          })
        }else {
          this.$http.post('/api/traineeinformation/confirmSignUpList',{ids:this.djfCheckList,bz:this.bzs}).then(res=>{
            if(res.code == 200){
              this.swal({
                title:'审核成功',
                type:"success"
              })
              this.getPagerList();
            }else {
              this.swal({
                title:res.message,
                type:"error"
              })
            }
          }).catch(err=>{})
        }

      },
      saveOk(index, mess) {
        this.swal({
          text: "确认缴费" + mess.realPay,
          type: "info",
          showCancelButton: true,
          confirmButtonText: '确认',
          cancelButtonText: '取消'
        }).then((isConfirm) => {
          if (isConfirm.value) {
            this.OKpay(index)
          }
        });
      },
      messRemove(row) {
        if (row.chargeRecord.pjbh != '') {
          this.swal({
            text: '票据已打印,无法撤回',
            type: 'warning'
          });
        } else {
          this.swal({
            text: "是否进行缴费撤回操作!!!",
            type: "warning",
            showCancelButton: true,
            confirmButtonText: '撤回',
            cancelButtonText: '取消'
          }).then((isConfirm) => {
            if (isConfirm.value) {
              this.retract(row.id)
            }
          });
        }
      },
      getJTjx() {
        this.jXlist = this.dictUtil.getByCode(this, 'ZDCLK1019');
      },
      rd() {
        //手动读卡，返回的是读取到的身份证信息
        /*
        * {
         CardNo:SynCardRead.CardNo,---证件号码
         NameA:SynCardRead.NameA----姓名
         }
        * */
        let res = window.readCard();
        if (res && res.CardNo) {
          this.param.idCardNoLike = res.CardNo;
          this.getPagerList();
        }
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
        this.getPagerList1()
      },
      getPagerList() {
        var v = this
        this.$http.post('/api/traineeinformation/jrdj', this.param).then((res) => {
          v.tableData = res.page.list
          v.total = res.page.total
          v.allmoney = res.result
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
