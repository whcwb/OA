<template>
  <div class="box_col" style="position: relative">
    <Menu mode="horizontal" :active-name="activeName" @on-select="MenuClick">
      <MenuItem name="1">
        <div style="font-weight: 700;font-size: 16px">
          报名审核
        </div>
      </MenuItem>
      <MenuItem name="2">
        <div style="font-weight: 700;font-size: 16px">
          审核记录
        </div>
      </MenuItem>
    </Menu>
    <div class="box_col_100" v-if="activeName=='1'">
      <Row type="flex" :gutter="10" justify="end" style="margin:8px 0">
        <Col span="4" :lg="4" :md="5">
          <Cascader trigger="hover" v-model="param.bmd" :data="dictList.bmd.data" placeholder="报名点选择"
                    @on-change="(a,b)=>{param.bmd =a[0],param.pageNum = 1,getPagerList()}"
                    filterable></Cascader>
        </Col>
        <Col span="4" :lg="4" :md="5">
          <Select v-model="param.carType" placeholder="车型选择" filterable clearable
                  @on-change="param.pageNum = 1,getPagerList()">
            <Option v-for="item in dictList.carType.data" :value="item.key" :key="item.key">{{ item.val }}</Option>
          </Select>
        </Col>
        <Col span="4" :lg="4" :md="5">
          <DatePicker v-model="param.bmTime" split-panels type="daterange" placement="bottom-end" placeholder="报名日期"
                      @on-change="param.pageNum = 1,getPagerList()"
          style="width: 100% ;"></DatePicker>
        </Col>
        <Col span="4" :lg="4" :md="5">
          <Input type="text" placeholder="证件号码" v-model="param.idCardNoLike" @on-enter="param.pageNum = 1,getPagerList()"/>
        </Col>
        <Col span="4" :lg="4" :md="5">
          <Input type="text" placeholder="学员姓名" v-model="param.nameLike" @on-enter="param.pageNum = 1,getPagerList()"/>
        </Col>
        <Col spam="2" :lg="2" :md="3">
          <Button type="primary" @click="param.pageNum = 1,getPagerList()">
            <Icon type="md-search"></Icon>
          </Button>
        </Col>
      </Row>
      <Table
        :height="AF.getPageHeight()-320"
        :columns="tabTit" :data="pageData"
        stripe size="small"></Table>
      <div style="text-align: right;padding: 6px 0">
        <Page :total=param.total
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
          <Cascader trigger="hover" v-model="param1.bmd" :data="dictList.bmd.data" placeholder="报名点选择"
                    @on-change="(a,b)=>{param1.bmd =a[0],param1.pageNum = 1,getPagerList1()}"
                    filterable></Cascader>
        </Col>
        <Col span="4" :lg="4" :md="5">
          <Select v-model="param1.carType" placeholder="车型选择" filterable clearable @on-change="param1.pageNum = 1,getPagerList1()">
            <Option v-for="item in dictList.carType.data" :value="item.key" :key="item.key">{{ item.val }}</Option>
          </Select>
        </Col>
        <Col span="4" :lg="4" :md="5">
          <DatePicker v-model="param1.bmTime" split-panels type="daterange" placement="bottom-end" placeholder="报名日期"
                      @on-change="param1.pageNum = 1,getPagerList1()"
                      style="width: 100% ;"></DatePicker>
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
        <Page :total=param1.total
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
  </div>

</template>

<script>
  export default {
    name: "auditPage",
    data() {
      return {
        activeName: '1',
        v: this,
        pagerUrl: this.apis.TRAINEE.AUDIT_PAGER,
        tabTit: [
          {
            type: 'index2', align: 'center', minWidth: 60, fixed: 'left',
            render: (h, params) => {
              return h('span', params.index + (this.param.pageNum - 1) * this.param.pageSize + 1);
            }
          },
          {
            title: '姓名',
            key: 'name',
            align: 'center',
            minWidth: 80, fixed: 'left'
          },
          {
            title: '报名点',
            key: 'jgmc',
            align: 'center',
            minWidth:120, fixed: 'left',
            render: (h, params) => {
              let jgmcArray = params.row.jgmc.split("/");
              let res = "";
              if (jgmcArray.length > 1) {
                for (let i = 0; i < jgmcArray.length; i++) {
                  res += jgmcArray[i];
                  if ((i + 1) != jgmcArray.length) {
                    res += '-';
                  }
                }
              } else {
                res = jgmcArray[0];
              }
              return h('div', res);
            }
          },
          {
            title: '车型',
            key: 'carType',
            align: 'center',
            minWidth: 80,
            fixed: 'left',
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
          {
            title: '证件号码',
            key: 'idCardNo',
            align: 'center',
            minWidth: 180
          },
          {
            title: '手机号码',
            align: 'center',
            key: 'phone',
            minWidth: 120
          },

          {
            title: '报名时间',
            align: 'center',
            key: 'registrationTime',
            minWidth: 120,
            render: (h, params) => {
              return h('div', params.row.registrationTime.substring(0,10)
              );
            }
          },
          {
            title:'推荐人',
            align:'center',
            key:'referrer',
            minWidth: 80,
            render: (h, p) => {
              let a =  p.row.referrer.split('-')
              return h('div',a[0])
            }
          },
          {
            title: '备注',
            align: 'center',
            key: 'remark',
            minWidth: 120
          },
          {
            title: '审核',
            align: 'center',
            minWidth: 80,
            fixed: 'right',
            render: (h, params) => {
              return h('div', [
                h('Button', {
                  props: {
                    type: 'warning',
                    size: 'small',
                    shape: 'circle',
                    icon: 'md-checkmark-circle-outline'
                  },
                  on: {
                    click: () => {
                      this.auditConfirm(params.row)
                    }
                  }
                })
              ])
            }
          }
        ],
        tabTit1: [
          {
            type: 'index2', align: 'center', minWidth: 60, fixed: 'left',
            render: (h, params) => {
              return h('span', params.index + (this.param.pageNum - 1) * this.param.pageSize + 1);
            }
          },
          {
            title: '姓名',
            key: 'name',
            minWidth: 80, fixed: 'left',
            align: 'center'
          },
          {
            title: '报名点',
            key: 'jgmc',
            align: 'center',
            minWidth:120, fixed: 'left',
            render: (h, params) => {
              let jgmcArray = params.row.jgmc.split("/");
              let res = "";
              if (jgmcArray.length > 1) {
                for (let i = 0; i < jgmcArray.length; i++) {
                  res += jgmcArray[i];
                  if ((i + 1) != jgmcArray.length) {
                    res += '-';
                  }
                }
              } else {
                res = jgmcArray[0];
              }
              return h('div', res);
            }
          },
          {
            title: '车型',
            key: 'carType',
            align: 'center',
            minWidth: 80,
            fixed: 'left',
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
          {
            title: '证件号码',
            key: 'idCardNo',
            minWidth: 180,
            align: 'center'
          },
          {
            title: '手机号码',
            key: 'phone',
            align: 'center',
            minWidth: 120
          },
          {
            title: '报名时间',
            align: 'center',
            minWidth: 120,
            key: 'registrationTime',
            render: (h, params) => {
            	return h('div',params.row.registrationTime.substring(0,10)
              );
            }
          },
          {
            title:'推荐人',
            align:'center',
            key:'referrer',
            minWidth: 80,
            render: (h, p) => {
              let a =  p.row.referrer.split('-')
              return h('div',a[0])
            }
          },
          {
            title: '备注',
            align: 'center',
            key: 'remark',
            minWidth: 120
          },
          {
            title: '撤回',
            align: 'center',
            minWidth: 80,
            fixed: 'right',
            render: (h, params) => {
              return h('div', [
                h('Button', {
                  props: {
                    type: 'error',
                    size: 'small',
                    shape: 'circle',
                    icon: 'ivu-icon ivu-icon-ios-redo'
                  },
                  on: {
                    click: () => {

                      this.revokeInfo(params.row)
                    }
                  }
                })
              ])
            }
          }
        ],
        param: {
          total:0,
          nameLike: '', //姓名
          idCardNoLike: '',
          bmd: [], //报名点代码
          carType: '', //车型。取字典：ZDCLK0040
          bmTime: '', //报名日期范围值
          //分页数据
          orderBy: 'registrationTime desc',
          // total: 1, //总数量
          pageNum: 1, //当前页码
          pageSize: 10, //每页显示数
          infoCheckStatus: '00'
        },
        param1: {
          total:0,
          nameLike: '', //姓名
          idCardNoLike: '',
          bmd: [], //报名点代码
          carType: '', //车型。取字典：ZDCLK0040
          bmTime: '', //报名日期范围值
          //分页数据
          orderBy: 'registrationTime desc',
          // total: 1, //总数量
          pageNum: 1, //当前页码
          pageSize: 10, //每页显示数
          infoCheckStatus: '10'
        },
        //table数据查询结果
        pageData: [],
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
      this.getDictList();
      this.getBmdList();
      this.getPagerList()
    },
    methods: {
      MenuClick(name) {
        this.param.pageNum = 1
        this.param1.pageNum = 1
        this.activeName = name
        if (name == '1') {
          this.getPagerList()
        } else if (name == '2') {
          this.getPagerList1()
        }
      },
      //获取当前用户可操作的报名点
      getBmdList() {
        this.$http.get(this.apis.FRAMEWORK.getCurrentOrgTree, {
          timers: new Date().getTime()
        }).then((res) => {
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
          }
        }).catch((error) => {
        })
      },
      getDictList() {
        this.dictList.carType.data = this.dictUtil.getByCode(this, this.dictList.carType.code);
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
        if (num == 0) {
          this.param.pageSize = n
          this.getPagerList()
        } else if (num == 1) {
          this.param1.pageSize = n
          this.getPagerList1()
        }
      },
      auditConfirm(row) {
        this.$http.post(this.apis.TRAINEE.AUDIT, {
          id: row.id,
          infoCheckStatus: '10'
        }).then((res) => {
          let msg = res.message;
          if (res.code === 200) {
            this.$Message.success(msg);

            // this.util.getPageData(this);
            this.getPagerList()
            this.getPagerList1()
          } else {
            this.$Message.error(msg);
          }
        }).catch((error) => {
          this.$Message.error(this.apis.NETWORK_ERR_STR);
        });
      },
      getPagerList() {
        // this.param.infoCheckTimeLike = this.AF.trimDate()
        this.$http.post(this.pagerUrl, this.param).then((res) => {
          if (res.code === 200) {
            this.pageData = res.page.list;
            this.param.total = res.page.total
          }
        }).catch((error) => {
        })
      },

      getPagerList1() {
        this.param1.infoCheckTimeLike = this.AF.trimDate()
        this.$http.post(this.pagerUrl, this.param1).then((res) => {
          if (res.code === 200) {
            this.pageData1 = res.page.list;
            this.param1.total = res.page.total
          }
        }).catch((error) => {
        })
      },
      revokeInfo(id) {
        this.$http.post(this.apis.TRAINEE.REVOKEINFO, {traineeId: id.id}).then((res) => {
          if (res.code === 200) {
            // this.util.initTable(this)
            this.getPagerList()
            this.getPagerList1()
          } else {
            this.$Message.error(res.message);
          }
        }).catch((error) => {
        })
      }
    }
  }
</script>

<style scoped>

</style>
