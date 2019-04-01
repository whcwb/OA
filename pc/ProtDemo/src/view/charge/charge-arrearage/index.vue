<template>
  <div class="box_col" style="position: relative">
    <!--<pager-tit title="分期还款" iconname="ios-football"></pager-tit>-->
    {{AutoReadCard}}
    <Menu mode="horizontal" :active-name="activeName" @on-select="MenuClick">
      <MenuItem name="1">
        <div style="font-weight: 700;font-size: 16px">
          待还款学员
        </div>
      </MenuItem>
      <MenuItem name="2">
        <div style="font-weight: 700;font-size: 16px">
          已还款学员
        </div>
      </MenuItem>
    </Menu>
    <div class="tagTit" style="position: absolute;right: 80px;top: 13px;z-index: 999">
      <Tag type="dot" color="success" style="margin-left: 16px">未分期</Tag>
      <Tag type="dot" color="error">分 期</Tag>
    </div>
    <div class="box_col_100" v-if="activeName=='1'">
      <Row :gutter="10" type="flex" style="margin: 8px 0">
        <Col span="12" :lg="12" :md="24">
          <div class="box_row_100" style="font-size: 18px;font-weight: 700">
            待还款学员
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
        <Col span="12" :lg="12" :md="24">
          <Row :gutter="10" type="flex" justify="end">
            <Col span="4" :lg="4" :md="3">
              <div style="width:100%">
                <Select v-model="param.jgdm"
                        clearable
                        placeholder="请选择报名点"
                        @on-change="CasChange">
                  <Option v-for="item in CascaderList" :value="item.value" :key="item.value">{{ item.label }}</Option>
                </Select>
              </div>
            </Col>
            <Col span="5" :lg="5" :md="5">
              <Input v-model="param.nameLike"
                     @on-enter="getPagerList"
                     placeholder="请输入学员姓名"/>
            </Col>

            <Col span="7" :lg="7" :md="6">
              <Input v-model="param.idCardNoLike"
                     @on-enter="getPagerList"
                     placeholder="证件号码"/>
            </Col>
            <Col span="2" :lg="2" :md="3" align="center">
              <Button type="primary" @click="getPagerList">
                <Icon type="md-search"></Icon>
                <!--查询-->
              </Button>
            </Col>
          </Row>
        </Col>
      </Row>

      <Table :height="AF.getPageHeight()-320" stripe size="small"
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
    <div class="box_col_100" v-if="activeName=='2'">
      <Row :gutter="10" style="margin: 8px 0">
        <Col span="6" :lg="6" :md="24">
          <div class="box_row_100" style="font-size: 18px;font-weight: 700">
            还款记录
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
        <Col span="18" :lg="18" :md="24">
          <Row :gutter="10" type="flex" justify="end">
            <Col span="6" :lg="6">
              <DatePicker type="daterange" split-panels v-model="bmTime" placeholder="审核日期"
                          @on-change="DatePickerC"
                          style="width: 100% ;"></DatePicker>
            </Col>
            <Col span="6" :lg="6" :md="7">
              <Input v-model="param1.nameLike"
                     @on-enter="getPagerList1"
                     placeholder="请输入学员姓名"/>
            </Col>

            <Col span="6" :lg="6" :md="7">
              <Input v-model="param1.idCardNoLike"
                     @on-enter="getPagerList1"
                     placeholder="证件号码"/>
            </Col>
            <Col span="4" :lg="4" :md="4" align="center">
              <Button type="primary" @click="getPagerList1">
                <Icon type="md-search"></Icon>
                <!--查询-->
              </Button>
              <Tooltip content="打印" placement="top" transfer>
                <Button type="primary" @click="winPrint" style="margin-left: 10px">
                  <Icon type="md-print"/>
                </Button>
              </Tooltip>
            </Col>
          </Row>
        </Col>

      </Row>

      <Table :height="AF.getPageHeight()-320"
             @on-selection-change="tabcheck" stripe
             :columns="tabTit1" :data="tableData1"></Table>
      <div style="text-align: right;padding: 6px 0">
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
    <component :is="compName" :printMess='printMess'></component>
  </div>
</template>

<script>
  import printArrearage from '../../../components/print/printArrearage'

  export default {
    name: "index",
    components: {
      printArrearage
    },
    data: function () {
      return {
        activeName: '1',
        compName: '',
        printMess: [],
        count: 0,
        count1: 0,
        tabTit: [
          {
            type: 'index2', align: 'center', minWidth: 80, fixed: 'left',
            render: (h, params) => {
              return h('span', params.index + (this.param.pageNum - 1) * this.param.pageSize + 1);
            }
          },
          {
            title: '报名点',
            align: 'center',
            minWidth: 180,
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
            minWidth: 100,
            fixed: 'left',
            key: 'name'
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
            title: '欠费金额',
            key: 'oweAmount',
            minWidth: 120,
            align: 'center'
          },
          {
            title: '报名时间',
            key: 'cjsj',
            minWidth: 100,
            align: 'center',
            render: (h, p) => {
              return h('div',
                p.row.registrationTime.substring(0, 10)
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
            minWidth: 90,
            key: 'cz',
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
        tableData: [],
        total: 0,//总数量
        allmoney: 0,
        CascaderList: [],
        param: {
          jgdm:'',
          nameLike: '',
          idCardNoLike: '',
          //分页数据
          pageNum: 1,//当前页码
          pageSize: 10//每页显示数
        },
        payOk: {
          chargeType: '10',//支付方式
          index: '',
          chargeCode: '0002',
          traineeId: '',//学员ID
          remark: ''//备注
        },
        tabTit1: [
          {
            type: 'selection',
            align: 'center',
            fixed: 'left',
            width: 60
          },
          {
            type: 'index3', align: 'center', minWidth: 80, fixed: 'left',
            render: (h, params) => {
              return h('span', params.index + (this.param1.pageNum - 1) * this.param1.pageSize + 1);
            }
          },
          {
            title: '姓名',
            align: 'center',
            minWidth: 100,
            key: 'name'
          },
          {
            title: '收据编号',
            align: 'center',
            minWidth: 140,
            render: (h, p) => {
              var lt = p.row.chargeRecord.pjbh.length
              if (lt == 0) {
                return h('div', [
                  h('Tag', {
                    props: {
                      color: 'warning'
                    }
                  }, '票据未打印')
                ])
              } else {
                return h('div',
                  p.row.chargeRecord.pjbh.substring(0, lt - 7)
                );
              }
            }
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
            title: '欠费金额',
            key: 'oweAmount',
            minWidth: 100,
            align: 'center'
          },
          {
            title: '还款金额',
            key: 'oweAmount',
            minWidth: 100,
            align: 'center',
            render: (h, p) => {
              return h('div', p.row.chargeRecord.chargeFee)
            }
          },
          {
            title: '收款方式',
            align: 'center',
            minWidth: 120,
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
            title: '收费时间',
            minWidth: 120,
            align: 'center',
            render: (h, p) => {
              return h('div',
                p.row.chargeRecord.chargeTime.substring(0, 10)
              )
            }
          },
          {
            title: '备注',
            align: 'center',
            minWidth: 200,
            render: (h, p) => {
              return h('Input', {
                props: {
                  value: p.row.chargeRecord.remark,
                  type: 'textarea',
                  placeholder: '',
                  readonly: 'readonly',
                  rows: 1
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
                h('Button', {
                  props: {
                    type: 'error',
                    size: 'small',
                    icon: 'ios-redo'
                  },
                  on: {
                    click: () => {
                      this.messRemove(p.row)
                    }
                  }
                })
              ])
            }
          }
        ],
        tableData1: [],
        total1: 0,//总数量
        allmoney1: 0,
        bmTime:[this.AF.trimDate()+' 00:00:00',this.AF.trimDate() + ' 23:59:59'],
        param1: {
          nameLike: '',
          idCardNoLike: '',
          timeGte:'',
          timeLte:'',
          arr: '1',
          //分页数据
          pageNum: 1,//当前页码
          pageSize: 10//每页显示数
        },
      }
    },
    created() {
      this.param1.timeGte = this.bmTime[0];
      this.param1.timeLte = this.bmTime[1];
      this.getPagerList();
      this.getBmdList();
    }
    ,
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
      CasChange(val) {
        this.param.pageNum = 1,
          this.getPagerList()
      },
      getBmdList() {
        this.$http.get(this.apis.FRAMEWORK.getCurrentOrgTree, {timers: new Date().getTime()}).then((res) => {
          if (res.code === 200) {
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
      DatePickerC(data){
        if(data[0].length == 10){
          this.param1.timeGte = data[0] + ' 00:00:00' ;
        }else {
          delete this.param1.timeGte
        }
        if(data[1].length == 10){
          this.param1.timeLte = data[1] + ' 23:59:59';
        }else {
          delete this.param1.timeLte
        }
        this.getPagerList1();
      },
      MenuClick(name) {
        this.activeName = name
        if (name == '1') {
          this.getPagerList()
        } else if (name == '2') {
          this.getPagerList1()
        }
      },
      getPagerList() {
        var v = this
        this.$http.post('/api/traineeinformation/stag', this.param).then((res) => {
          v.tableData = res.page.list
          v.total = res.page.total
          v.allmoney = res.result
        })
      },
      OKpay(index) {
        this.swal({
          type:'warning',
          title:'学员是否还款?',
          showCancelButton: true,
          confirmButtonText: '确认',
          cancelButtonText: '关闭',
        }).then(p=>{
          if(p.value){
            if (this.payOk.chargeType == '') {
              this.swal({
                text: '请选择收费方式',
                type: 'warning'
              });
            } else {
              this.$http.post(this.apis.OTHER.ADD, this.payOk).then((res) => {
                if (res.code == 200) {
                  this.getPagerList()
                  this.$Message.success(res.message);
                } else {
                  this.$Message.error(res.message);
                }

              })
            }
          }
        })
      },
      getPagerList1() {
        var v = this
        this.$http.post('/api/traineeinformation/staged', this.param1).then((res) => {
          if (res.code == 200) {
            if (res.page.list == '') {
              v.tableData1 = []
            } else {
              v.tableData1 = res.page.list
            }
            v.total1 = res.page.total
            v.allmoney1 = res.result
          } else {
            this.$Message.error(res.message)
            v.tableData1 = []
          }
        })
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
              this.retract(row.chargeRecord.id)
            }
          });
        }
      },
      retract(id) {
        this.$http.post(this.apis.TJSDF.DELE + id).then((res) => {
          if (res.code == 200) {
            this.getPagerList1()
            this.$Message.success(res.message);
          } else {
            this.$Message.error(res.message);
          }
        })
      }
      ,
      tabcheck(arr) {
        console.log(arr);
        this.printMess = arr
      },
      winPrint() {
        var v = this
        if (this.printMess.length > 1) {
          for (var i = 0; i < this.printMess.length - 1; i++) {
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
          v.AF.WinPrint(this, this.printMess, 'printArrearage')
        }, 100)


      },
      pageChange(n, num) {
        console.log(num + '--1--' + n);
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
        console.log(num + '--2--' + n);
        this.param.pageSize = n
        this.getPagerList()
      }
    }
  }
</script>

<style scoped>

</style>
