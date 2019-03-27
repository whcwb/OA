<template>
  <div class="box_col">
    <!--<pager-tit title="优惠审核"></pager-tit>-->
    <Menu mode="horizontal" :active-name="activeName" @on-select="MenuClick">
      <MenuItem name="1">
        <div style="font-weight: 700;font-size: 16px">
          优惠待审核
        </div>
      </MenuItem>
      <MenuItem name="2">
        <div style="font-weight: 700;font-size: 16px">
          已审核记录
        </div>
      </MenuItem>
    </Menu>

    <div class="box_col_100" v-if="activeName=='1'">
      <Row type="flex" :gutter="10" justify="end" style="margin:8px 0">
        <Col span="4" :lg="4" :md="5">
          <!--<Cascader trigger="hover" v-model="param.bmd" :data="dictList.bmd.data" placeholder="报名点选择"-->
                    <!--filterable></Cascader>-->
          <Select v-model="param.bmd" @on-change="param.pageNum = 1,getPagerList()"
                  clearable>
            <Option v-for="item in dictList.bmd.data" :value="item.value" :key="item.value">{{ item.label }}</Option>
          </Select>
        </Col>
        <Col span="4" :lg="4" :md="5">
          <DatePicker v-model="param.bmTime" split-panels type="daterange" placement="bottom-end" placeholder="报名日期"
                      style="width: 100% ;"  @on-change="param.pageNum = 1,getPagerList()"></DatePicker>
        </Col>
        <Col span="4" :lg="4" :md="5">
          <Input type="text" placeholder="证件号码" v-model="param.idCardNoLike" @on-enter="param.pageNum = 1,getPagerList()"/>
        </Col>
        <Col span="4" :lg="4" :md="5">
          <Input type="text" placeholder="学员姓名" v-model="param.nameLike"  @on-enter="param.pageNum = 1,getPagerList()"/>
        </Col>
        <Col spam="2" :lg="2" :md="3">
          <Button type="primary" @click="param.pageNum = 1,getPagerList()">
            <Icon type="md-search"></Icon>
          </Button>
        </Col>
      </Row>
      <Table
        :height="AF.getPageHeight()-320"
        size="small"
        stripe
        :columns="tabTit"
        :data="pageData"></Table>
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
      <Row type="flex" :gutter="10" justify="end" style="margin:8px 0">
        <Col span="4" :lg="4" :md="5">
          <!--<DatePicker type="date" split-panels v-model="param1.reduceCheckTimeLike"-->
                      <!--:clearable="false" @on-change="changeTime"  placeholder="审核日期"   style="width: 100% ;"></DatePicker>-->
          <DatePicker v-model="bmTime" split-panels type="daterange" :clearable="true" :editable="false"
                      @on-change="DatePickerC"
                      placement="bottom-end" placeholder="收费日期" style="width: 100% ;"></DatePicker>
        </Col>
        <Col span="4" :lg="4" :md="5">
          <Select v-model="param1.bmd" @on-change="param1.pageNum = 1,getPagerList1()"
                  clearable>
            <Option v-for="item in dictList.bmd.data" :value="item.value" :key="item.value">{{ item.label }}</Option>
          </Select>
        </Col>
        <Col span="4" :lg="4" :md="5">
          <DatePicker v-model="param1.bmTime" split-panels  type="daterange" placement="bottom-end" placeholder="报名日期"
                      @on-change="param1.pageNum = 1,getPagerList1()" style="width: 100% ;"></DatePicker>
        </Col>
        <Col span="4" :lg="4" :md="5">
          <Input type="text" placeholder="证件号码" v-model="param1.idCardNoLike" @on-enter="param1.pageNum = 1,getPagerList1()"/>
        </Col>
        <Col span="4" :lg="4" :md="5">
          <Input type="text" placeholder="学员姓名" v-model="param1.nameLike" @on-enter="param1.pageNum = 1,getPagerList1()"/>
        </Col>
        <Col spam="2" :lg="2" :md="3">
          <Button type="primary" @click="param1.pageNum = 1,getPagerList1()">
            <Icon type="md-search"></Icon>
            <!--查询-->
          </Button>
        </Col>
      </Row>
      <Table
        :height="AF.getPageHeight()-320"
        size="small"
        stripe
        :columns="tabTit1"
        :data="pageData1"></Table>
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

    <component
      :is="compName"
      :userAudit="userAudit"
    >
    </component>
  </div>
</template>

<script>
  import audit from './comp/audit'
  import ch from './comp/chang'

  export default {
    name: "index",
    components: {audit,ch},
    data() {
      return {
        bmTime:[this.AF.trimDate()+' 00:00:00',this.AF.trimDate() + ' 23:59:59'],
        activeName: '1',
        compName: '',
        userAudit: {},
        v: this,
        pagerUrl: this.apis.TRAINEEREDUCE.AUDIT_PAGER,
        tabTit: [
          {
            type: 'index2', align: 'center', minWidth: 60, fixed: 'left',
            render: (h, params) => {
              return h('span', params.index + (this.param.pageNum - 1) * this.param.pageSize + 1);
            }
          },
          {
            title: '报名点', align: 'center', key: 'jgmc',  fixed: 'left',minWidth: 120, render: (h, params) => {
              let jgmcArray = params.row.jgmc.split("/");
              let res = "";
              if (jgmcArray.length == 2 || jgmcArray.length == 1) {
                return h('div', params.row.jgmc);
              } else if (jgmcArray.length == 3) {
                return h('div', jgmcArray[1] + ("/" + jgmcArray[2]))
              }
            }
          },
          {title: '姓名', key: 'name', align: 'center', fixed: 'left', minWidth: 100},
          {title: '优惠项', align: 'center', key: 'reduceName', minWidth: 120},
          {title: '优惠金额(元)', align: 'center', key: 'reducePrice', minWidth: 110},
          {title:'报名费',key:'registrationFee', align: 'center',minWidth: 100},
          {
            title: '车型', key: 'carType',
            minWidth: 100,
            align: 'center',
            render: (h, p) => {
              return h('Tag', {
                props: {
                  color: 'cyan'
                },
                style: {
                  fontSize: '15px',
                }
              }, p.row.carType);
            }
          },
          {title: '手机号码', align: 'center', key: 'phone', minWidth: 130},
          {title: '证件号码', key: 'idCardNo', align: 'center', minWidth: 180},
          {
            title: '报名时间', align: 'center',
            minWidth: 120,
            key: 'registrationTime', render: (h, params) => {
              return h('div',
                params.row.registrationTime.substring(0, 10)
              );
            }
          },
          // {title: '备注', align: 'center', key: 'remark', minWidth: 120},
          {
            title: '审核',
            fixed: 'right',
            minWidth: 160,
            align: 'center',
            render: (h, params) => {
              return h('div', [
                h('Tooltip',
                  {props: {placement: 'top', content: '优惠修改',}},
                  [
                    h('Button', {
                      props: {type: 'text', icon: 'md-create', shape: 'circle', size: 'large',ghost: true},
                      style: {margin: '0 8px 0 0',fontSize: '24px',color: '#2db7f5'},
                      on: {
                        click: () => {
                          this.changeMess(params.row);
                        }
                      }
                    })
                  ]
                ),
                h('Tooltip',
                  {props: {placement: 'top', content: '审核',}},
                  [
                    h('Button', {
                      props: {
                        type: 'success',
                        icon: 'md-checkmark-circle-outline', shape: 'circle', size: 'small'
                      },
                      style: {margin: '0 8px 0 0'},
                      on: {
                        click: () => {
                          this.auditConfirm(params.row);

                        }
                      }
                    })
                  ]
                ),
                h('Tooltip',
                  {props: {placement: 'top', content: '驳回',}},
                  [
                    h('Button', {
                      props: {type: 'error', icon: 'md-refresh-circle', shape: 'circle', size: 'small'},
                      style: {margin: '0 8px 0 0'},
                      on: {
                        click: () => {
                          this.rejectConfirm(params.row);
                        }
                      }
                    })
                  ]
                )
              ])
            }
          }
        ],
        //table加载标记
        SpinShow: true,
        SpinShow1: true,
        total: 0,
        param: {
          reduceStatus: '00',
          jgmcLike: '',
          nameLike: '',//姓名
          idCardNoLike: '',
          bmd: [],//报名点代码
          carType: '',//车型。取字典：ZDCLK0040
          bmTime: '',//报名日期范围值
          //分页数据
          orderBy: 'registrationTime asc',
          pageNum: 1,//当前页码
          pageSize: 10//每页显示数
        },
        //table数据查询结果
        pageData: [],
        tabTit1: [
          {
            type: 'index2', align: 'center', minWidth: 80, fixed: 'left',
            render: (h, params) => {
              return h('span', params.index + (this.param.pageNum - 1) * this.param.pageSize + 1);
            }
          },
          {
            title: '报名点', align: 'center', key: 'jgmc', fixed: 'left', minWidth: 120, render: (h, params) => {
              let jgmcArray = params.row.jgmc.split("/");
              let res = "";
              if (jgmcArray.length == 2 || jgmcArray.length == 1) {
                return h('div', params.row.jgmc);
              } else if (jgmcArray.length == 3) {
                return h('div', jgmcArray[1] + ("/" + jgmcArray[2]))
              }
            }
          },
          {title: '姓名', align: 'center', key: 'name', fixed: 'left', minWidth: 120},
          {title: '优惠项', align: 'center', key: 'reduceName', minWidth: 120},
          {title: '优惠金额(元)', align: 'center', key: 'reducePrice', minWidth: 120},
          {title:'应付金额',key:'registrationFee', align: 'center',minWidth: 100},
          {title:'实付金额',key:'realPay', align: 'center',minWidth: 100},
          {title:'推荐人',key:'referrer',align:'center',minWidth:100,
            render: (h, p) => {
              let a =  p.row.referrer.split('-')
              return h('div',a[0])
            }},
          {title: '审核人',key:'reduceVerifier',align:'center',minWidth:120},
          {
            title: '车型', key: 'carType', align: 'center',
            minWidth: 100,
            render: (h, p) => {
              return h('Tag', {
                props: {
                  color: 'cyan'
                },
                style: {
                  fontSize: '15px',
                }
              }, p.row.carType);
            }
          },
          {title: '手机号码', align: 'center', key: 'phone', minWidth: 130},
          {title: '证件号码', align: 'center', key: 'idCardNo', minWidth: 180},
          {
            title: '报名时间', align: 'center',
            minWidth: 120,
            key: 'registrationTime', render: (h, params) => {
              return h('div',
                params.row.registrationTime.substring(0,10)
              );
            }
          },
          {
            title: '撤回',
            fixed: 'right',
            minWidth: 140,
            align: 'center',
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
                      on: {
                        click: () => {
                          this.retract(p.row.id)
                        }
                      }
                    })
                  ]
                )
              ])
            }
          }
        ],
        total1: 1,//总数量
        param1: {
          // reduceCheckTimeLike: this.AF.trimDate,
          // reduceCheckTimeLike: '',
          jgmcLike: '',
          nameLike: '',//姓名
          idCardNoLike: '',
          bmd: [],//报名点代码
          carType: '',//车型。取字典：ZDCLK0040
          bmTime: '',//报名日期范围值
          //分页数据
          orderBy: 'registrationTime asc',
          pageNum: 1,//当前页码
          pageSize: 10//每页显示数
        },
        pageData1: [],
        dictList: {
          carType: {
            code: 'ZDCLK0040',
            data: []
          },
          bmd: {
            code: '',
            data: []
          }
        }
      }
    },
    created() {
      let time = this.AF.trimDate();
      // this.param1.reduceCheckTimeLike = time;

      this.getDictList();
      this.getBmdList();
      this.getJTjx();
      this.getPagerList();
    },
    methods: {
      DatePickerC(data) {
        if(data[0].length == 10){
          this.param1.reduceCheckTimeGte = data[0] + ' 00:00:00' ;
        }else {
          this.param1.reduceCheckTimeGte = data[0]
        }
        if(data[1].length == 10){
          this.param1.reduceCheckTimeLte = data[1] + ' 23:59:59';
        }else {
          this.param1.reduceCheckTimeLte = data[1]
        }
        this.getPagerList1();
      },
      changeTime(val){
        let time = this.AF.trimDate(val);
        this.param1.reduceCheckTimeLike = time;
        this.getPagerList1();
      },
      MenuClick(name) {
        this.param.pageNum = 1
        this.param1.pageNum = 1
        this.activeName = name
        if (name == '1') {
          this.getPagerList()
        } else if (name == '2') {
          // this.param1.reduceCheckTimeLike = this.AF.trimDate();
          this.DatePickerC(this.bmTime)
        }
      },
      changeMess(row) {
        this.userAudit = row
        this.compName = 'ch'
      },
      getPagerList() {
        var v = this
        this.$http.post(this.pagerUrl, this.param).then((res) => {
          if (res.code == 200) {
            this.pageData = res.page.list
            v.total = res.page.total
            this.SpinShow = false
          }
        }).catch(err => {
          this.SpinShow = false
          // console.log(err);
        })
      },
      getPagerList1() {
        this.SpinShow1 = true
        var v = this
        this.$http.post(this.pagerUrl, this.param1).then((res) => {
          if (res.code == 200) {
            this.pageData1 = res.page.list
            v.total1 = res.page.total
            this.SpinShow1 = false
          }
        }).catch(err => {
          this.SpinShow1 = false
          // console.log(err);
        })
      },
      //撤回
      retract(id) {
        this.$http.post('/api/traineereduceauditing/recallUserAuditing', {id: id}).then((res) => {
          if (res.code == 200) {
            // this.util.initTable(this);
            this.getPagerList1()
          }
        })
      },
      getJTjx() {
        this.jXlist = this.dictUtil.getByCode(this, 'ZDCLK1019');
      },
      //获取当前用户可操作的报名点
      getBmdList() {
        this.$http.get(this.apis.FRAMEWORK.getCurrentOrgTree, {timers: new Date().getTime()}).then((res) => {
          if (res.code === 200) {
           /* if(res.result[0].value.length=='6'){
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
            // this.dictList.bmd.data = res.result[0].children;
          }
        }).catch((error) => {
        })
      },
      getDictList() {
        this.dictList.carType.data = this.dictUtil.getByCode(this, this.dictList.carType.code);
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
      },
      rejectConfirm(row) {
        this.compName = 'audit';
        this.userAudit = row;
      },
      auditConfirm(row) {
        this.$http.post(this.apis.TRAINEEREDUCE.AUDIT, {
          id: row.id,
          reduceStatus: '10',
          reduceRemark: ''
        }).then((res) => {
          let msg = res.message;
          if (res.code === 200) {
            this.$Message.success(msg);

            this.getPagerList()
          } else {
            this.$Message.error(msg);
          }
        }).catch((error) => {
          this.$Message.error(this.apis.NETWORK_ERR_STR);
        });
      }
    }
  }
</script>
