<template>
  <div>
    <Modal v-model="showModal"
           fullscreen :footer-hide="true"
           :closable='false' :mask-closable="false">
      <div class="box_row" slot="header">
        <div style="font-size: 30px;font-weight: 700">
          超级财务
        </div>
        <div style="margin-left: 18px">
          <Button type="warning" @click="close">
            关闭
          </Button>
        </div>
      </div>
      <div class="box_col">
        <div>
          <Row :gutter="16" style="padding: 12px 0">
            <Col span="4">
              <Input type="text" placeholder="学员姓名" v-model="param.nameLike"
                     @on-enter="param.pageNum = 1,v.util.getPageData(v)"/>
            </Col>
            <Col span="4">
              <Input type="text" placeholder="证件号码" v-model="param.idCardNoLike"
                     @on-enter="param.pageNum = 1,v.util.getPageData(v)"/>
            </Col>
            <Col span="16">
              <Row type="flex" justify="end">
                <Col span="24">
                  <div style="text-align: right">
                    <Page :total=param.total
                          :current=param.pageNum
                          :page-size=param.pageSize
                          :page-size-opts=[10,20,30,40,50]
                          show-total
                          show-elevator
                          show-sizer
                          placement='top'
                          @on-page-size-change='pageSizeChange'
                          @on-change='pageChange'>
                    </Page>
                  </div>
                </Col>
              </Row>
            </Col>
          </Row>
          <Table
            :height="AF.getPageHeight()-490"
            size="small" stripe @on-row-click="rowClick"
            :columns="tabTit"
            :data="pageData">
          </Table>
        </div>
        <div class="box_col_auto">
          <div class="box_row" style="height: 100%">

            <div class="box_col" v-if="PMess.id" style="width:50%">
              <div class="box_row colCenter" style="padding: 12px 0;;font-weight: 700">
                <div style="font-size: 24px;margin-right: 6px">
                  缴费信息:
                </div>
                <div style="font-size: 20px">
                  {{PMess.name}}_
                  {{PMess.jgmc}}_
                  {{classType(PMess.classType)}}_
                  {{PMess.carType}}
                  <!--{{}}-->
                </div>
              </div>
              <div class="box_col_auto">
                <Table
                  :height="AF.getPageHeight()-740"
                  size="small"
                  stripe
                  :columns="moneyMess.tit"
                  :data="moneyMess.data">
                </Table>
              </div>
            </div>
            <div class="box_col" v-if="PMess.id" style="padding:60px 6px 0px;text-align: center">
              <div class="box_col_100">
                <Button type="info" @click="getBxCx(PMess.jgdm),cVc=1">变更车型</Button>
              </div>
              <div class="box_col_100">
                <Button type="primary" @click="getBxCx(PMess.jgdm),cVc=2">变更班型</Button>
              </div>
              <div class="box_col_100">
                <Button type="error" @click="find(PMess),cVc=3">退学操作</Button>
              </div>
            </div>
            <div class="box_row_100" style="padding:60px 6px 0px">
              <div class="box_col">
                <div class="box_col_100">
                  <Form v-if="cVc==1" ref="userForm" :model="Car_param" :rules="ruleValidate" :label-width="10">
                    <Row>
                      <Col span="4">
                        <FormItem prop="carType">
                          <Select v-model="Car_param.carType" clearable placeholder="车型选择" filterable>
                            <Option v-for="(item,index) in carTypList" :value="item.key" :key="index">{{ item.value }}
                            </Option>
                          </Select>
                        </FormItem>
                      </Col>
                      <Col span="4">
                        <FormItem prop="inOutType">
                          <Select v-model="Car_param.inOutType" clearable placeholder="补缴/退费" filterable>
                            <Option value="00" :key="0">差价补缴</Option>
                            <Option value="10" :key="1">差价退还</Option>
                          </Select>
                        </FormItem>
                      </Col>
                      <Col span="4">
                        <FormItem prop="fee">
                          <Input :number="true"
                                 placeholder="费用金额" v-model="Car_param.fee"/>
                        </FormItem>
                      </Col>
                      <Col span="4">
                        <FormItem prop="chargeType">
                          <Select v-model="Car_param.chargeType" clearable placeholder="交易方式" filterable>
                            <Option value="00" :key="0">现金</Option>
                            <Option value="10" :key="1">转账</Option>
                          </Select>
                        </FormItem>
                      </Col>
                      <Col span="4">
                        <FormItem>
                          <Button type="info" @click="okCarTyp('userForm',PMess)">确认变更</Button>
                        </FormItem>
                      </Col>
                    </Row>
                  </Form>
                </div>
                <div class="box_col_100">
                  <Form v-if="cVc==2" ref="userForm" :model="Class_param" :rules="ruleValidate" :label-width="10">
                    <Row>
                      <Col span="4">
                        <FormItem prop="classType">
                          <Select v-model="Class_param.classType" clearable placeholder="班型选择" filterable>
                            <Option v-for="(item,index) in classTypeList" :value="item.key" :key="index">{{ item.value }}
                            </Option>
                          </Select>
                        </FormItem>
                      </Col>
                      <Col span="4">
                        <FormItem prop="inOutType">
                          <Select v-model="Class_param.inOutType" clearable placeholder="补缴/退费" filterable>
                            <Option value="00" :key="0">差价补缴</Option>
                            <Option value="10" :key="1">差价退还</Option>
                          </Select>
                        </FormItem>
                      </Col>
                      <Col span="4">
                        <FormItem prop="fee">
                          <Input :number="true"
                                 placeholder="费用金额" v-model="Class_param.fee"/>
                        </FormItem>
                      </Col>
                      <Col span="4">
                        <FormItem prop="chargeType">
                          <Select v-model="Class_param.chargeType" clearable placeholder="交易方式" filterable>
                            <Option value="00" :key="0">现金</Option>
                            <Option value="10" :key="1">转账</Option>
                          </Select>
                        </FormItem>
                      </Col>
                      <Col span="4">
                        <FormItem>
                          <Button type="info" @click="okclassTyp('userForm',PMess)">确认变更</Button>
                        </FormItem>
                      </Col>
                    </Row>
                  </Form>
                </div>
                <div class="box_col_100">
                  <Form v-if="cVc==3" :label-width="60">
                    <Row style="padding: 8px 0">
                      <Col span="4">
                          <Tag v-if="!PMess.firSubPaymentTime" color="default">科目一待缴</Tag>
                          <Tag v-else color="primary">科目一已缴</Tag>
                      </Col>
                      <Col span="4">
                          <Tag v-if="!PMess.secSubPaymentTime" color="default">科目二待缴</Tag>
                          <Tag v-else color="primary">科目二已缴</Tag>
                      </Col>
                      <Col span="4">
                          <Tag v-if="!PMess.thirdSubPaymentTime" color="default">科目三待缴</Tag>
                          <Tag v-else color="primary">科目三已缴</Tag>
                      </Col>
                      <Col span="4">
                          <Tag color="default">科二培训 ：--</Tag>
                      </Col>
                      <Col span="4">
                          <Tag color="default">科三培训 ：--</Tag>
                      </Col>
                    </Row>
                    <Row>
                      <Col span="8">
                        <FormItem label="退费金额">
                          <InputNumber style="width: 100%" :min="0" v-model="outMoney"></InputNumber>
                        </FormItem>
                      </Col>
                      <Col span="4">
                        <FormItem>
                          <Button @click='signOut' type="error">退学</Button>
                        </FormItem>
                      </Col>
                    </Row>
                  </Form>
                </div>
              </div>
            </div>

          </div>
        </div>
      </div>
      <div slot="footer"></div>
    </Modal>
    <component
      :is="compName"
      :show="showEdit"
      :selectRow="selectRow">
    </component>
    <component
      :is="compNameCarTyp"
      :userMess="userMess"
      @close="compNameCarTyp = ''"
      @changeAfter="v.util.getPageData(v)"
    >
    </component>
  </div>
</template>

<script>
  import detail from './comp/detail'
  import upData from './comp/upData'

  import Cartyp from './comp/changeCarTyp'
  export default {
    name: "audit",
    components: {detail, upData,Cartyp},
    data() {
      return {
        v: this,
        showModal: true,
        compName: '',
        compNameCarTyp:'',
        userMess:{},
        selectRow: {},
        showEdit: false,
        pagerUrl: this.apis.TRAINEE.PAGER,
        tabTit: [
          {
            type: 'index2', align: 'center', minWidth: 80,
            render: (h, params) => {
              return h('span', params.index + (this.param.pageNum - 1) * this.param.pageSize + 1);
            }
          },
          {title: '姓名', key: 'name', align: 'center', minWidth: 100},
          {
            title: '性别', width: 80, align: 'center', key: 'gender',
            render: (h, p) => {
              return h('div', p.row.gender == '10' ? '男' : '女');
            }
          },
          {title: '证件号码', minWidth: 180, key: 'idCardNo', align: 'center'},
          {
            title: '报名时间', width: 180, key: 'registrationTime', align: 'center',
            render: (h, params) => {
              return h('div',
                params.row.registrationTime
              );
            }
          },
          {
            title: '报名点', minWidth: 180, key: 'jgmc', align: 'center',
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
            title: '负责人',
            align: 'center',
            minWidth: 100,
            key: 'glyxm'
          },
          {
            title: '推荐人',
            align: 'center',
            key: 'referrer',
            minWidth: 100,
            render: (h, p) => {
              let a = p.row.referrer.split('-')
              return h('div', a[0])
            }
          },
          {
            title: '车型', minWidth: 160, key: 'carType', align: 'center',
            filters: [{label: 'A1', value: 'A1'},
              {label: 'A2', value: 'A2'},
              {label: 'A3', value: 'A3'},
              {label: 'B2', value: 'B2'},
              {label: 'C1', value: 'C1'},
              {label: 'C2', value: 'C2'}],
            filterRemote: (value, p, v) => {
              this.param.carType = value;
              this.util.getPageData(this);
              return true;
            },
            render: (h, p) => {
              let carType = p.row.carType;
              let color = 'volcano';
              let carTypeStr = p.row.carType.substring(0, 1);
              if (carTypeStr == 'C') {
                //小车
                color = 'green';
              }

              return h("div",[
                h('Tag', {
                  props: {
                    color: color
                  },
                  style: {
                    fontSize: '15px',
                  }
                }, carType),
                h("Button",{
                  props:{type:"error",size:"small"},
                  on:{
                    click:()=>{
                      this.userMess=p.row;
                      this.compNameCarTyp="Cartyp";

                    }
                  }
                },"改")
              ])


              // return h('Tag', {
              //   props: {
              //     color: color
              //   },
              //   style: {
              //     fontSize: '15px',
              //   }
              // }, carType);
            }
          },
          // {
          //   title:"车辆信息维护",
          //   minWidth: 130,
          //   render:(h,p)=>{
          //     return h("div",[
          //       h("Button",{
          //         props:{type:"success",size:"small"},
          //         on:{
          //           click:()=>{
          //             this.userMess=p.row;
          //             this.compNameCarTyp="Cartyp";
          //
          //           }
          //         }
          //         },"车型维护")
          //     ])
          //   }
          // },
          {
            title: '业务状态', minWidth: 160, key: 'status', align: 'center',
            filters: [
              {label: '已报名', value: 99},
              {label: '待受理', value: 0},
              {label: '科目一待考', value: 10},
              {label: '科目二待考', value: 20},
              {label: '科目三待考', value: 30},
              {label: '科目四待考', value: 40},
              {label: '结业', value: 50},
              {label: '退学', value: 60}
            ],
            filterRemote: (value, p, v) => {
              this.param.statusArray = value.join(',');
              this.util.getPageData(this);
              return true;
            },
            render: (h, p) => {
              let str = p.row.status;
              if (str == '99') {
                str = '已报名';
              } else if (str == '00') {
                str = '待受理';
              } else if (str == '10') {
                str = '科目一待考';
              } else if (str == '20') {
                str = '科目二待考';
              } else if (str == '30') {
                str = '科目三待考';
              } else if (str == '40') {
                str = '科目四待考';
              } else if (str == '50') {
                str = '结业';
              } else if (str == '60') {
                str = '退学';
              }
              return h('Tag', {
                style: {
                  width: '90px'
                },
                props: {
                  color: p.row.status == '60' ? 'error' : 'success'
                },
              }, str);
            }
          },
          {
            title: '缴费状态', minWidth: 160, key: 'status', align: 'center',
            render: (h, p) => {
              let str = p.row.status;
              let mesLab = '';
              if (str == '99') {
                // str = '已报名';
                if (p.row.confirmTime != '') {
                  mesLab = '报名费已缴'
                } else {
                  mesLab = '报名费待缴'
                }
              } else if (str == '00') {
                if (p.row.confirmTime != '') {
                  mesLab = '报名费已缴'
                } else {
                  mesLab = '报名费待缴'
                }
              } else if (str == '10') {
                if (p.row.firSubPaymentTime == '未知' && p.row.firSubPaymentTime != '') {
                  mesLab = '未知'
                } else if (p.row.firSubPaymentTime != '未知' && p.row.firSubPaymentTime != '') {
                  mesLab = '科一初考已缴'
                } else {
                  mesLab = '科一初待缴'
                }
              } else if (str == '20') {
                if (p.row.secSubPaymentTime == '未知' && p.row.secSubPaymentTime != '') {
                  mesLab = '未知'
                } else if (p.row.secSubPaymentTime != '未知' && p.row.secSubPaymentTime != '') {
                  mesLab = '科二初考已缴'
                } else {
                  mesLab = '科二初考待缴'
                }
              } else if (str == '30') {
                if (p.row.thirdSubPaymentTime == '未知' && p.row.thirdSubPaymentTime != '') {
                  mesLab = '未知'
                } else if (p.row.thirdSubPaymentTime != '未知' && p.row.thirdSubPaymentTime != '') {
                  mesLab = '科三初考已缴'
                } else {
                  mesLab = '科三初考待缴'
                }
              } else if (str == '40') {
                mesLab = '科目四待考';
              } else if (str == '50') {
                mesLab = '结业';
              } else if (str == '60') {
                mesLab = '退学';
              }
              return h('Tag', {
                style: {
                  width: '90px'
                },
                props: {
                  color: p.row.status == '60' ? 'error' : 'success'
                },
              }, mesLab);
            }
          },
          {
            title: '备注', minWidth: 200, key: 'remark', align: 'center',
            render: (h, p) => {
              if (p.row.remark == '999999') {
                return h('div', '历史数据')
              } else {
                return h('div', p.row.remark)
              }
            }
          },
          {
            title: '详情', minWidth: 80, fixed: 'right', align: 'center', render: (h, params) => {
              return h('div', [
                h('Button', {
                  props: {
                    icon: 'md-clipboard',
                    type: 'text',
                    ghost: true,
                    shape: "circle",

                  },
                  style: {
                    fontSize: '24px',
                    color: '#2db7f5'
                  },
                  on: {
                    click: () => {
                      this.selectRow = params.row
                      this.compName = 'detail'
                    }
                  }
                })
              ]);
            }
          },
          {
            title: '基础数据修改', minWidth: 80, align: 'center', fixed: 'right', render: (h, params) => {
              return h('div', [
                h('Button', {
                  props: {
                    icon: 'ios-construct',
                    type: 'text',
                    ghost: true,
                    shape: "circle",

                  },
                  style: {
                    fontSize: '24px',
                    color: '#2db7f5'
                  },
                  on: {
                    click: () => {
                      this.selectRow = params.row
                      this.compName = 'upData'
                    }
                  }
                })
              ]);
            }
          },
          // {
          //   title: '删除', minWidth: 80, align: 'center', fixed: 'right', render: (h, params) => {
          //     return h('div', [
          //       h('Button', {
          //         props: {
          //           icon: 'md-close',
          //           type: 'text',
          //           ghost: true,
          //           shape: "circle",
          //
          //         },
          //         style: {
          //           fontSize: '24px',
          //           color: '#2db7f5'
          //         },
          //         on: {
          //           click: () => {
          //             this.getDelet(params.row.id)
          //
          //           }
          //         }
          //       })
          //     ]);
          //   }
          // }
        ],
        pageData: [],
        param: {
          nameLike: '',//姓名
          idCardNoLike: '',//证件号码
          //分页数据
          orderBy: 'registrationTime desc',
          total: 1,//总数量
          pageNum: 1,//当前页码
          pageSize: 10//每页显示数
        },
        PMess:{},
        moneyMess: {
          tit: [
            {
              type: 'index2', align: 'center', minWidth: 80, fixed: 'left',
              render: (h, params) => {
                return h('span', params.index + (this.param.pageNum - 1) * this.param.pageSize + 1);
              }
            },
            {
              title: '姓名',
              key: 'traineeName',
              align: "center",
              minWidth: 100,
              fixed: 'left'
            },
            {
              title: "费用类型",
              align: "center",
              key: 'chargeName',
              minWidth: 120
            },
            {
              title: '收支记录',
              key: 'inOutType',
              align: "center",
              minWidth: 160,
              sortable: true,
              render: (h, p) => {
                return h('div', [
                  h('Tooltip', {
                    props: {
                      content: '收入与支出',
                      placement: "top"
                    }
                  }, [
                    h('Tag', {
                      props: {
                        color: p.row.inOutType == '00' ? 'green' : 'red'
                      }
                    }, p.row.inOutType == '00' ? ('收:' + p.row.chargeFee) : ('支:' + p.row.chargeFee))
                  ]),
                  h('Tooltip', {
                    props: {
                      content: '退费撤回',
                      placement: "top"
                    }
                  }, [
                    h('Button',{
                      props:{type:'error',size:'small'},
                      style:{display:p.row.chargeCode == '0003'?'':'none'},
                      on:{
                        click:()=>{
                          this.payRemove(p.row.traineeId)
                        }
                      }
                    },'撤')
                  ]),
                ])
              }
            },
            {
              title: '收费时间',
              align: 'center',
              minWidth: 120,
              render: (h, p) => {
                return h('div',
                  p.row.cjsj.substring(0, 10)
                )
              }
            },
            {
              title: '操作人',
              key: 'cjr',
              minWidth: 180,
              align: 'center'
            },
            {
              title: '票据编号',
              minWidth: 180,
              align: 'center',
              render: (h, p) => {
                var a = p.row.pjbh != '' ? p.row.pjbh.substring(0, 13) : ''
                return h('div', a)
              }
            }
          ],
          data: [],
          total: 0
        },

        outMoney:0,
        carTypList: [],
        classTypeList: [],

        cVc:0,

        Car_param: {},
        Class_param:{},

        ruleValidate: {
          classType: [
            { required: true, message: '请选着变更后的班型', trigger: 'change'}
          ],
          carType: [
            {required: true, message: '请选着变更后的车型', trigger: 'change'}
          ],
          inOutType: [
            {required: true, message: '请选择补缴/退费', trigger: 'change'}
          ],
          chargeType: [
            {required: true, message: '请选择交易方式', trigger: 'change'}
          ],
          fee: [
            {required: true, type: 'number', message: '请输入费用金额', trigger: 'blur'}
          ],
        },
      }
    },
    watch: {},
    created() {
      this.getPager()
    },
    methods: {
      payRemove(id){
        this.swal({
          title:'退学撤回？！！',
          type:'warning',
          confirmButtonText: '撤回',
          cancelButtonText: '取消',
          showCancelButton: true,
        }).then((res) => {
          if (res.value) {
            this.$http.post('/api/traineeinformation/dropOutRevert',{id:id}).then(res=>{
              if(res.code == 200){
                this.swal({title:res.message,type:'success'})
                this.getPager()
              }
            }).catch(err=>{})
          }
        })
      },
      clear(){
        this.cVc = 0
        this.getPager()
      },
      FF(){
        if(!this.PMess.id){
          this.swal({
            title:'warning',
            title:'请选择学员'
          })
          return
        }
      },
      signOut() {
        this.swal({
          title:'是否退学',
          type:'warning',
          showCancelButton: true,
          confirmButtonText: '删除',
          cancelButtonText: '取消',
        }).then(p=>{
          if(p.value){
          }else {
           return
          }
        })
        this.$http.post('/api/traineeinformation/dropOut', {
          id: this.PMess.id,
          fee: this.outMoney
        }).then(res => {
          if (res.code == 200) {
            this.$Message.success(res.message);
            this.swal({
              title: res.message,
              type: 'success'
            })
            this.clear()
          } else {
            this.swal({
              text: res.message,
              type: 'error'
            });
          }
        }).catch(err => {
          this.swal({
            text: err.message,
            type: 'error'
          });
        })
      },
      find(p){
        if(p.status == '60'){
          this.cVc = 0
          this.swal({
            type:'info',
            title:'该学员已经退费'
          })
        }
        if(p.status == '50'){
          this.cVc = 0
          this.swal({
            type:'info',
            title:'该学员已经结业'
          })
        }
      },
      okclassTyp(name,mess){
        var v = this
        this.$refs[name].validate((valid) => {
          if (valid) {
            if(v.Class_param.classType == v.stuMes.classType ){
              v.swal({
                type:'warning',
                title:'变更班型与当前班型一致',
              })
              v.Class_param.carType = ''
            }else {
              this.$http.post('/api/traineeinformation/editClassType',v.Class_param).then(res=>{
                if(res.code == 200){
                  // this.$Message.success(res.message);
                  if(v.Class_param.inOutType=='00'){
                    this.swal({
                      type:'question',
                      title:'班型变更成功',
                      text:'是否打印收款单据？',
                      showCancelButton:true,
                      confirmButtonText:'打印单据',
                      cancelButtonText:'完成',
                    }).then((val)=>{
                      if(val.value){
                        console.log('reasdfsdaf',mess)
                        v.print({traineeId:mess.id})
                      }
                    })
                  }else {
                    this.swal({
                      type:'success',
                      title:'班型变更成功'
                    })
                  }
                  this.clear()
                }else {
                  this.swal({
                    type:'error',
                    title:'车型变更失败',
                    text:res.message
                  })
                }
              }).catch(err=>{

              })
            }
          } else {
            this.$Message.error('请将信息填写完整');
          }
        })
      },
      okCarTyp(name, mess) {
        var v = this
        this.$refs[name].validate((valid) => {
          if (valid) {
            if (v.Car_param.carType == v.stuMes.carType) {
              v.swal({
                type: 'warning',
                title: '变更车型与当前车型一致',
              })
              v.Car_param.carType = ''
            } else {
              this.$http.post('/api/traineeinformation/editCarType', v.Car_param).then(res => {
                if (res.code == 200) {
                  // this.$Message.success(res.message);
                  if (v.Car_param.inOutType == '00') {
                    this.swal({
                      type: 'question',
                      title: '车型变更成功',
                      text: '是否打印收款单据？',
                      showCancelButton: true,
                      confirmButtonText: '打印单据',
                      cancelButtonText: '完成',
                    }).then((val) => {
                      if (val.value) {
                        v.print({traineeId: mess.id})
                      }
                    })
                  } else {
                    this.swal({
                      type: 'success',
                      title: '车型变更成功'
                    })
                  }
                  this.clear()
                } else {
                  this.swal({
                    type: 'error',
                    title: '车型变更失败',
                    text: res.message
                  })
                }
              }).catch(err => {

              })
            }
          } else {
            this.swal({
              type: 'warning',
              title: '请将信息填写完整'
            })
            this.$Message.error('请将信息填写完整');
          }
        })
      },
      print(id){
        var v = this
        this.AF.idPrintMess(id,(res)=>{
          v.mess = res
          v.compName='pagPrint'
        })
      },
      getBxCx(num) {
        this.$http.post('/api/chargeitemmanagement/getJgBx', {jgdm: num, timers: new Date().getTime()}).then(res => {
          if (res.code == 200) {
            // console.log(res.result);
            if (res.result.carType.length == 0) {
              this.swal({
                type: 'error',
                title: '网络异常',
                text: '车辆类型加载失败'
              })
            } else {
              this.carTypList = res.result.carType
              this.classTypeList = res.result.classType
            }
          }
        }).catch(err => {

        })
      },

      rowClick(p, i) {
        this.cVc = 0
        this.PMess = p
        this.getMoneylist(p.idCardNo)
      },
      getMoneylist(id) {
        var v = this
        this.$http.post('/api/chargemanagement/findTodayCharge', {
          idCardNo: id,
          pageNum: 1,
          pageSize: 9999
        }).then((res) => {
          if (res.code == 200) {
            v.moneyMess.data = res.page.list
            v.moneyMess.total = res.page.total
            // v.tableData = res.page.list
            // v.total = res.page.total
            // v.AddMess = res.result
          }
        }).catch((err) => {

        })
      },
      getPager() {
        this.util.initTable(this);
      },
      pageChange(n) {
        this.util.pageChange(this, n);
      },
      pageSizeChange(n) {
        this.util.pageSizeChange(this, n);
      },
      classType(val) {
        return this.dictUtil.getValByCode(this, 'ZDCLK1002', val)
      },
      close() {
        // this.$router.push({path:'/home'})
        this.$router.back(-1)
      }
    }
  }
</script>

<style scoped>

</style>
