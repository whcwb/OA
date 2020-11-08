<template>
  <div class="box_col" style="position: relative">
    <pager-tit title="报名收费" iconname="ios-football"></pager-tit>
    {{AutoReadCard}}
    <div class="tagTit" style="position: absolute;left: 140px;top: 0px;">
      <Tag type="dot" color="success" style="margin-left: 16px">未分期</Tag>
      <Tag type="dot" color="error">分 期</Tag>
    </div>
    <div class="box_col_100">
      <div class="box_col" style="height: 100%">

        <div class="box_col_100">
          <Card style="width: 100%;height: 95%;margin-top: 15px">
            <Row :gutter="10" style="margin-bottom: 8px">
              <Col span="6" :lg="6" :md="24">
                <div style="font-size: 18px;font-weight: 700;width: 100%">
                  待缴费学员
                  <span style="font-size: 16px;margin-left: 16px">
                    共:
                    <span style="font-size: 20px;color: #ff9900">
                      {{tableData.length}}
                    </span>
                    人-合计:
                    <span style="font-size: 20px;color: #ff9900">
                      {{count}}
                    </span>
                    元
                  </span>
                </div>
              </Col>
              <Col span="4" :lg="4" :md="5">
                <div style="width:100%">
                  <Cascader @on-change="CasChange" v-model="bmdT"
                            change-on-select :data="CascaderList"
                            placeholder="报名点"
                            filterable clearable></Cascader>
                </div>
              </Col>
              <Col span="4" :lg="4" :md="5">
                <div style="width: 100%">
                  <Select v-model="param.jgmcLike" clearable placeholder="请选择驾校" @on-change="">
                    <Option v-for="(item,index) in jXlist" :value="item.val" :key="index">{{ item.val }}</Option>
                  </Select>
                </div>
              </Col>
              <Col span="4" :lg="4" :md="5">
                <div>
                  <Input v-model="param.nameLike"
                         @on-enter="getPagerList"
                         placeholder="请输入学员姓名"/>
                </div>
              </Col>
              <Col span="4" :lg="4" :md="6">
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
            </Row>

            <Table :height="(AF.getPageHeight()-400)/2" stripe
                   :columns="tabTit" :data="tableData"></Table>
          </Card>

        </div>
        <div class="box_col_auto">
          <Card style="width: 100%;height: 100%">
            <Row :gutter="10" style="margin-bottom: 8px">
              <Col span="6" :lg="6" :md="24">
                <div style="font-size: 18px;font-weight: 700;width: 100%">
                  今日缴费
                  <span style="font-size: 16px;margin-left: 16px">
                      共:
                    <span style="font-size: 20px;color: #ff9900">
                      {{tableData1.length}}
                    </span>
                    人-合计:
                    <span style="font-size: 20px;color: #ff9900">
                      {{count1}}
                    </span>
                    元
                  </span>
                </div>
              </Col>
              <Col span="4" :lg="4" :md="5">
                <div style="width: 100%">
                  <Cascader @on-change="CasChange1" v-model="bmdB"
                            change-on-select :data="CascaderList"
                            placeholder="报名点"
                            filterable clearable></Cascader>
                </div>
              </Col>
              <Col span="4" :lg="4" :md="5">
                <div style="width: 100%">
                  <Select v-model="param1.jgmcLike" clearable placeholder="请选择驾校" @on-change="">
                    <Option v-for="(item,index) in jXlist" :value="item.val" :key="index">{{ item.val }}</Option>
                  </Select>
                </div>
              </Col>
              <Col span="3" :lg="3" :md="5">
                <div style="width: 100%">
                  <Input v-model="param1.nameLike"
                         @on-enter="getPagerList1"
                         placeholder="请输入学员姓名"/>
                </div>
              </Col>
              <Col span="4" :lg="4" :md="5">
                <div style="width: 100%">
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
                <Tooltip content="打印" placement="bottom" style="float: right">
                  <Button type="primary" @click="winPrint">
                    <Icon type="md-print"/>
                  </Button>
                </Tooltip>
              </Col>
            </Row>

            <Table :height="(AF.getPageHeight()-400)/2" stripe
                   @on-selection-change="tabcheck"
                   :columns="tabTit1" :data="tableData1"></Table>
          </Card>
        </div>
      </div>
      <component :is="compName" :printMess='printMess'></component>
    </div>
  </div>
</template>

<script>
  import printSignUp from '../../../components/print/printSignUp'

  export default {
    name: "indexN",
    components: {
      printSignUp
    },
    data: function () {
      return {
        compName: '',
        printMess: [],
        count: 0,
        count1: 0,
        tabTit: [
          {
            type: 'index',
            width: 60,
            align: 'center',
            fixed: 'left'
          },
          {
            title: '姓名',
            align: 'center',
            minWidth: 120,
            key: 'name',
            fixed: 'left'
          },
          {
            title: '报名点',
            align: 'center',
            minWidth: 140,
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
            title: '金额',
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
            title: '实付金额',
            key: 'realPay',
            minWidth: 120,
            align: 'center'
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
                  }, '在线支付'),
                  h('Option', {
                    props: {
                      value: '00'
                    }
                  }, '现金收费'),
                  h('Option', {
                    props: {
                      value: '20'
                    }
                  }, 'POS刷卡')
                ])
              ])
            }
          },
          // {
          //   title: '收款方式',
          //   key: 'skfs',
          //   minWidth: 120,
          //   align: 'center',
          //   render: (h, p) => {
          //     return h('div', [
          //       h('RadioGroup', {
          //         props: {
          //           value: '10',
          //           size: 'large'
          //         },
          //         on: {
          //           'on-change': (val) => {
          //             this.payOk.chargeType = val
          //           }
          //         }
          //       }, [
          //         h('Radio', {
          //           props: {
          //             label: '10'
          //           }
          //         }, '扫码支付'),
          //         h('Radio', {
          //           props: {
          //             label: '00'
          //           }
          //         }, '现金收费'),
          //         h('Radio', {
          //           props: {
          //             label: '20'
          //           }
          //         }, 'POS刷卡')
          //       ])
          //     ])
          //   }
          // },
          {
            title: '备注',
            align: 'center',
            minWidth: 220,
            render: (h, p) => {
              return h('Input', {
                props: {
                  value: '',
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
            fixed: 'right',
            minWidth: 90,
            render: (h, p) => {
              return h('div', [
                h('Tooltip',
                  {props: {placement: 'top',transfer:true, content: '通过',}},
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
                          if (p.row.reduceStatus == '00') {
                            this.swal({
                              text: '优惠审核未通过',
                              type: 'warning',
                              confirmButtonText: '关闭'
                            })
                          } else {
                            this.payOk.traineeId = p.row.id
                            this.saveOk(p.index,p.row)
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
        total: 0,//总数量
        param: {
          nameLike: '',
          idCardNoLike: '',
          jgdm: '',
          sign: '1',
          //分页数据
          pageNum: 1,//当前页码
          pageSize: 9999//每页显示数
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
            type: 'index',
            width: 60,
            align: 'center',
            fixed: 'left'
          },
          {
            title: '姓名',
            align: 'center',
            minWidth: 120,
            key: 'name',
            fixed: 'left'
          },
          {
            title: '收据编号',
            align: 'center',
            minWidth: 120,
            // key: 'pjbh',
            render: (h, p) => {
              return h('div', p.row.chargeRecord.pjbh);
            }
          },
          {
            title: '报名点',
            align: 'center',
            minWidth: 140,
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
            title: '金额',
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
            title: '实付金额',
            key: 'realPay',
            minWidth: 120,
            align: 'center'
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
                p.row.cjsj.substring(0,10)
              )
            }
          },
          {
            title: '收费确认',
            minWidth: 120,
            align: 'center',
            render: (h, p) => {
              return h('div',
                p.row.chargeRecord.chargeTime.substring(0,10)
              )
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
            fixed: 'right',
            minWidth: 90,
            render: (h, p) => {
              return h('div', [
                h('Tooltip',
                  {props: {
                      placement: 'top',
                      transfer:true,
                      content: '撤回',}},
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
                          this.messRemove(p.row.id)
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
          nameLike: '',
          idCardNoLike: '',
          jgdm: '',
          sign: '4',
          //分页数据
          pageNum: 1,//当前页码
          pageSize: 9999//每页显示数
        },
        jXlist: [],
        CascaderList: [],
        bmdT: [],
        bmdB: []
      }
    },
    created() {
      this.getpager()
      this.getBmdList()
      this.getJTjx()
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
      saveOk(index,mess){
        this.swal({
          text: "确认缴费"+mess.realPay,
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
      messRemove(id){
        this.swal({
          text: "是否进行缴费撤回操作!!!",
          type: "warning",
          showCancelButton: true,
          confirmButtonText: '撤回',
          cancelButtonText: '取消'
        }).then((isConfirm) => {
          if (isConfirm.value) {
            this.retract(id)
          }
        });
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
      CasChange(val, lab) {
        if (val.length == 1) {
          this.param.jgdm = val[0]
          this.getPagerList()
        }
      },
      CasChange1(val, lab) {
        if (val.length == 1) {
          this.param1.jgdm = val[0]
          this.getPagerList1()
        }
      },
      getBmdList() {
        this.$http.get(this.apis.FRAMEWORK.getCurrentOrgTree, {timers: new Date().getTime()}).then((res) => {
          if (res.code === 200) {
            this.CascaderList = res.result[0].children;
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
        this.param.pageNum = 1
        // this.$http.post(this.apis.CMONEY, this.param).then((res) => {
        this.$http.post('/api/traineeinformation/jrdj', this.param).then((res) => {
          v.tableData = res.page.list
          v.total = res.page.total
          this.count = 0
          res.page.list.forEach((item, index) => {
            this.count += item.realPay
          })

          // let pages = res.page.pages
          // v.getPagerListTo(pages)
        })
      },
      getPagerListTo(pages) {
        var v = this
        if(pages==1 || pages==1){
          return
        }
        this.param.pageNum = pages
        this.$http.post(this.apis.CMONEY, this.param).then((res) => {
          if (res.code == 200 && res.page.list) {
            this.tableData = this.tableData.concat(res.page.list)
            res.page.list.forEach((item, index) => {
              this.count += item.realPay
            })
            pages--
            v.getPagerListTo(pages)
          }
        })
      },
      OKpay(index) {
        if (this.payOk.chargeType == '') {
          this.swal({
            text: '请选择收费方式',
            type: 'warning'
          });
        } else {
          this.$http.post(this.apis.BMSF.OKPAY, this.payOk).then((res) => {
            if (res.code == 200) {
              this.getpager()
              this.$Message.success(res.message);
            } else {
              this.$Message.error(res.message);
            }

          })
        }
      }
      ,

      getPagerList1() {
        var v = this
        this.param1.pageNum = 1
        // this.$http.post(this.apis.CMONEY, this.param1).then((res) => {
        this.$http.post('/api/traineeinformation/jryj', this.param1).then((res) => {
          v.tableData1 = res.page.list
          v.total1 = res.page.total
          this.count1 = 0
          res.page.list.forEach((item, index) => {
            this.count1 += item.realPay
          })

          // let pages = res.page.pages
          //   v.getPagerList1To(pages)
        })
      },
      getPagerList1To(pages) {
        var v = this
        if(pages==1 || pages==0){
          return
        }
        this.param1.pageNum = pages
        this.$http.post(this.apis.CMONEY, this.param1).then((res) => {
          if (res.code == 200 && res.page.list) {
            this.tableData1 = this.tableData1.concat(res.page.list)
            res.page.list.forEach((item, index) => {
              this.count1 += item.realPay
            })
            pages--
            v.getPagerList1To(pages)
          }
        })
      },
      retract(id) {
        this.$http.post(this.apis.BMSF.RETRACT, {traineeId: id}).then((res) => {
          if (res.code == 200) {
            this.getpager()
            this.$Message.success(res.message);
          } else {
            this.$Message.error(res.message);
          }
        })
      }
      ,
      tabcheck(arr) {
        this.printMess = arr
      }
      ,
      winPrint() {
        this.AF.WinPrint(this, this.printMess, 'printSignUp')
      }
      ,
      pageChange(n, num) {
        if (num == 0) {
          this.param.pageNum = n
          this.getPagerList()
        } else if (num == 1) {
          this.param1.pageNum = n
          this.getPagerList1()
        }
      }
      ,
      pageSizeChange(n, num) {
        this.param.pageSize = n
        this.getPagerList()
      }
    }
  }
</script>

<style scoped>

</style>
