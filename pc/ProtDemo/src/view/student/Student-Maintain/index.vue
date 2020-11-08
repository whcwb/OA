<template>
  <div class="box_col">
    <Form :label-width="20" style="padding-top: 10px">
      <Row>
        <Col span="2">
          <pager-tit title="信息维护"></pager-tit>
        </Col>
        <Col span="22">
          <Row type="flex" justify="end">
            <Col span="4">
              <FormItem>
                <DatePicker v-model="param.bmTime" split-panels type="daterange"
                            @on-change="DatePick"
                            placement="bottom-end" placeholder="报名日期" style="width: 100% ;"></DatePicker>
              </FormItem>
            </Col>
            <Col span="2">
              <FormItem>
                <Cascader trigger="hover" v-model="param.bmd" :data="dictList.bmd.data" placeholder="报名点选择"
                          @on-change="(a,b)=>{param.bmd =a[0],param.pageNum = 1,getPagerList('val')}"
                          filterable></Cascader>
              </FormItem>
            </Col>
            <Col span="3">
              <FormItem>
                <Input type="text" placeholder="学员姓名" v-model="param.nameLike"
                       @on-enter="param.pageNum = 1,getPagerList('val')"/>
              </FormItem>
            </Col>
            <Col span="2">
              <FormItem>
                <Input type="text" placeholder="推荐人" v-model="param.referrerLike"
                       @on-enter="param.pageNum = 1,getPagerList('val')"/>
              </FormItem>
            </Col>
            <Col span="4">
              <FormItem>
                <Input type="text" placeholder="证件号码" v-model="param.idCardNoLike"
                       @on-enter="param.pageNum = 1,getPagerList('val')"/>
              </FormItem>
            </Col>
            <Col span="4">
              <FormItem>
                <Input type="text" placeholder="流水号" v-model="param.serialNumLike"
                       @on-enter="param.pageNum = 1,getPagerList('val')"/>
              </FormItem>
            </Col>
            <Col span="2">
              <FormItem>
                <Button type="primary" @click="param.pageNum = 1,getPagerList()">
                  <Icon type="md-search"></Icon>
                </Button>
              </FormItem>
            </Col>
          </Row>
        </Col>
      </Row>

    </Form>
    <Row>
      <Col span="24">
        <Table
          :height="AF.getPageHeight()-300"
          size="large"
          stripe
          :columns="tabTit"
          :data="pageData">
        </Table>
      </Col>
    </Row>
    <div style="text-align: right">
      <Page :total=param.total
            :current=param.pageNum
            :page-size=param.pageSize
            :page-size-opts=[8,10,20,30,40,50]
            show-total
            show-elevator
            show-sizer
            placement='top'
            @on-page-size-change='pageSizeChange'
            @on-change='pageChange'>
      </Page>
    </div>

    <component
      :is="compName"
      :show="showEdit"
      :selectRow="selectRow"
    >
    </component>
  </div>
</template>

<script>
  import detail from './comp/detail'
  import upData from './comp/upData'

  export default {
    name: "pager",
    components: {
      detail,upData
    },
    watch: {
      'param.bmTime': function (n, o) {
      }
    },
    data() {
      return {
        showEdit: false,
        v: this,
        selectTab: 'info',
        pagerUrl: this.apis.TRAINEE.PAGER,
        compName: '',
        selectRow: {},
        param: {
          nameLike: '',//姓名
          idCardNoLike: '',
          serialNumLike: '',//流水号
          bmd: [],//报名点代码
          carType: '',//车型。取字典：ZDCLK0040
          // [this.AF.trimDate,this.AF.trimDate]
          bmTime: [],//报名日期范围值
          entryFeeType: '',//報名費是否繳清  1 已清 0 未清
          carModel: '',//車型 1 大車  2 小車
          statusArray: '',//要查詢的狀態數組 以，分隔
          termOfValidity: '',//学员过期时间
          //分页数据
          orderBy: 'registrationTime desc',
          total: 1,//总数量
          pageNum: 1,//当前页码
          pageSize: 8//每页显示数
        },
        jXlist:[],
        //table数据查询结果
        pageData: [],
        dictList: {
          carType: {
            code: 'ZDCLK0040',
            data: []
          },
          bmd: {
            code: '',
            data: []
          }
        },
        tabTit: [
          {type: 'index2',align: 'center', minWidth: 80,
            render: (h, params) => {
              return h('span', params.index + (this.param.pageNum- 1) * this.param.pageSize + 1);
            }
          },
          {title: '姓名', key: 'name',align: 'center', width: 100},
          {
            title: '性别', width: 80, align: 'center',key: 'gender',
            render: (h, p) => {
              return h('div', p.row.gender == '10' ? '男' : '女');
            }
          },
          {title: '证件号码', width: 180, key: 'idCardNo', align: 'center'},
          {
            title: '报名时间', width: 180, key: 'registrationTime', align: 'center',
            render: (h, params) => {
              return h('div',
                params.row.registrationTime
              );
            }
          },
          {
            title: '报名点', width: 180, key: 'jgmc',align: 'center',
            render: (h, params) => {
              let jgmcArray = params.row.jgmc.split("/");
              let res = "";
              if(jgmcArray.length == 2 || jgmcArray.length == 1){
                return h('div', params.row.jgmc);
              }else if(jgmcArray.length == 3){
                return h('div', jgmcArray[1]+("/"+jgmcArray[2]))
              }
            }
          },
          {
            title: '负责人',
            align: 'center',
            width: 100,
            key: 'glyxm'
          },
          {
            title: '推荐人',
            align: 'center',
            key: 'referrer',
            width: 100,
            render: (h, p) => {
              let a =  p.row.referrer.split('-')
              return h('div',a[0])
            }
          },
          {
            title: '车型', width: 100, key: 'carType',align: 'center',
            filters:  [{label: 'A1', value:'A1'},
              {label: 'A2', value:'A2'},
              {label: 'A3', value:'A3'},
              {label: 'B2', value:'B2'},
              {label: 'C1', value:'C1'},
              {label: 'C2', value:'C2'}],
            filterRemote: (value, p, v) => {
              this.param.carType = value;
              // this.util.getPageData(this);
              this.getPagerList()
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

              return h('Tag', {
                props: {
                  color: color
                },
                style: {
                  fontSize: '15px',
                }
              }, carType);
            }
          },
          {
            title: '流水号', width: 148,align: 'center', key: 'serialNum', render: (h, p) => {
              if (p.row.serialNum == '') {
                return h('Tag', {
                  props: {
                    color: 'default'
                  },
                  style: {
                    fontSize: '15px',
                  }
                }, '-');
              }

              return h('div', p.row.serialNum);
            }
          },
          {
            title: '业务状态', width: 160, key: 'status', align: 'center',
            filters: [
              {label: '已报名', value: 99},
              {label: '待受理', value: 0},
              {label: '科目一待考', value: 10},
              {label: '科目二待考', value: 20},
              {label: '科目三待考', value: 30},
              {label: '科目四待考', value: 40},
              {label: '结业', value: 50},
              {label: '退学', value: 60},
              {label: '重新学车', value: 70}
            ],
            filterRemote: (value, p, v) => {
              this.param.statusArray = value.join(',');
              // this.util.getPageData(this);
              this.getPagerList()
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
              }else if (str == '70') {
                str = '重新学车';
              }
              return h('Tag', {
                style:{
                  width:'90px'
                },
                props: {
                  color: p.row.status == '60' ? 'error' : 'success'
                },
              }, str);
            }
          },
          {
            title: '缴费状态', width: 160, key: 'status', align: 'center',
            // filters: [
            //   {label: '已报名',value: 99},
            //   {label: '待受理',value: 0},
            //   {label: '科目一待考',value: 10},
            //   {label: '科目二待考',value: 20},
            //   {label: '科目三待考',value: 30},
            //   {label: '科目四待考',value: 40},
            //   {label: '结业',value: 50},
            //   {label: '退学',value: 60}
            // ],
            // filterRemote:(value,p,v)=>{
            //   this.param.statusArray = value.join(',');
            //   this.util.getPageData(this);
            //   return true;
            // },
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
              }else if (str == '70') {
                if (p.row.confirmTime != '') {
                  mesLab = '报名费已缴'
                } else {
                  mesLab = '报名费待缴'
                }
              }
              return h('Tag', {
                style:{
                  width:'90px'
                },
                props: {
                  color: p.row.status == '60' ? 'error' : 'success'
                },
              }, mesLab);
            }
          },
          {
            title: '备注', width: 200, key: 'remark', align: 'center',
            render: (h, p) => {
              if (p.row.remark == '999999') {
                return h('div', '历史数据')
              } else {
                return h('div', p.row.remark)
              }
            }
          },
          {
            title: '详情', width: 70, fixed: 'right',align: 'center', render: (h, params) => {
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
            title:'C1变C2', width: 100, fixed: 'right',align: 'center',
            render:(h,p)=>{
              return h('div', [
                h('Button', {
                  props: {
                    icon: 'md-git-compare',
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
                      if(p.row.carType == 'C1'){
                        this.swal({
                          title:'是否变更车型为 C2',
                          type:'question',
                          showCancelButton: true,
                          confirmButtonText: '变更',
                          cancelButtonText: '取消'
                        }).then(pm=>{
                          if(pm.value){
                            this.$http.post('/api/traineeinformation/degradeCarType',{id:p.row.id}).then(res=>{
                              if(res.code == 200){
                                this.swal({
                                  title:res.message,
                                  type:'success'
                                })
                                this.getPagerList()
                              }else {
                                this.swal({
                                  title:res.message,
                                  type:'error'
                                })
                              }
                            }).catch(err=>{

                            })
                          }else {
                            this.swal({
                              title:"变更取消",
                            })
                          }
                        })
                      }else {
                        this.swal({
                          title:'当前学员'+p.row.carType+',不可变更！！！',
                          type:'error'
                        })
                      }
                    }
                  }
                })
              ]);
            }
          },
          {
            title: '修改', width: 70, align: 'center',fixed: 'right', render: (h, params) => {
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
          {
            title: '删除', width: 70, align: 'center',fixed: 'right', render: (h, params) => {
              return h('div', [
                h('Button', {
                  props: {
                    icon: 'md-close',
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
                      this.getDelet(params.row.id)

                    }
                  }
                })
              ]);
            }
          }
        ],
      }
    },
    created() {
      // this.bmTime[1] = this.AF.trimDate()
      let strTime = this.AF.trimDate().split('-')
      strTime[0] = parseInt(strTime[0] )-3
      strTime[2] = '01'
      strTime = strTime.join('-')
      this.param.bmTime =[strTime,this.AF.trimDate()]
      //根据操作人员查询的关注点，默认进入后自动选择展示卡
      this.getDictList();
      this.getBmdList();
      this.getPagerList()
      this.getJTjx()
    },
    methods: {
      getPagerList(val){
        if(val){
          this.param.bmTime = []
        }
        this.$http.post(this.apis.TRAINEE.PAGER,this.param).then(res=>{
          if(res.code == 200){
            this.pageData = res.page.list
            this.param.total = res.page.total
          }
        }).catch(err=>{

        })
      },
      getJTjx(){
        this.jXlist = this.dictUtil.getByCode(this,'ZDCLK1019');
      },
      NYearsAgo: (val) => {
        var newDate = new Date();
        let Year = newDate.getFullYear()-val;
        let Month = newDate.getMonth() + 1;
        let Day = newDate.getDate();
        if (Month < 10) {
          Month = '0' + Month
        }

        if (Day < 10) {
          Day = '0' + Day
        }
        let time = Year + '-' + Month + '-' + Day
        return time
      },
      DatePick() {
        this.util.initTable(this);
        this.getPagerList()
      },
      //学员信息删除
      getDelet(id){
        this.swal({
          text: "是否删除数据?",
          type: "warning",
          showCancelButton: true,
          confirmButtonText: '确认',
          cancelButtonText: '取消'
        }).then((isConfirm) => {
          if (isConfirm.value) {
            this.$http.post(this.apis.TRAINEE.XXWH + id).then((res)=>{
              if (res.code === 200) {
                this.$Message.info(res.message);
                // this.util.initTable(this);
                this.getPagerList()
              }
            })

          }
        });
      },
      //获取当前用户可操作的报名点
      getBmdList() {
        this.$http.get(this.apis.FRAMEWORK.getCurrentOrgTree, {timers: new Date().getTime()}).then((res) => {
          if (res.code === 200) {
            // this.dictList.bmd.data = res.result[0].children;
            /*if(res.result[0].value.length=='6'){
              this.dictList.bmd.data = res.result[0].children;
            }else if(res.result[0].value.length=='3'){
              this.dictList.bmd.data = res.result[0].children[0].children;
            }*/
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
      getQueryList() {

      },
      getDictList() {
        this.dictList.carType.data = this.dictUtil.getByCode(this, this.dictList.carType.code);
      },
      pageChange(n) {
        this.util.pageChange(this, n);
      },
      pageSizeChange(n) {
        this.util.pageSizeChange(this, n);
      },
      changeTable(tableName) {
        if (tableName == 'all') {
          this.tabTit = this.tabTitAll;
        } else if (tableName == 'info') {
          this.tabTit = this.tabTitBas;
        } else if (tableName == 'pay') {
          this.tabTit = this.tabTitPay;
        } else if (tableName == 'exam') {
          this.tabTit = this.tabTitExa;
        }

        //Cookies.set('favoriteTabCard', tableName);
      }
    }
  }
</script>

<style scoped>

</style>
