<template>
  <div class="box_col">
    <pager-tit title="票据记录"></pager-tit>
    <Time :time="time1"/>
    <Form :label-width="20">
      <Row type="flex" justify="end">
        <Col span="3">
          <FormItem>
            <Cascader trigger="hover" v-model="param.jgdm" :data="dictList.bmd.data" placeholder="报名点选择"
                      filterable></Cascader>
          </FormItem>
        </Col>
        <Col span="3">
          <FormItem>
            <DatePicker v-model="time" split-panels type="date" placement="bottom-end" placeholder="打印时间"
                        style="width: 100% ;"></DatePicker>
          </FormItem>
        </Col>
        <Col span="3">
          <FormItem>
            <Input type="text" placeholder="票号" v-model="param.pjbh"/>
          </FormItem>
        </Col>
        <Col span="2">
          <FormItem>
            <Button type="primary" @click="getData">
              <Icon type="md-search"></Icon>
            </Button>
          </FormItem>
        </Col>
      </Row>
    </Form>
    <div class="box_col_auto">
      <!--:loading="SpinShow"-->
      <Table
        :height="AF.getPageHeight()-340"
        size="large"
        stripe
        :columns="tabTit"
        :data="pageData"></Table>
    </div>
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
  </div>
</template>

<script>
  import {getRelativeTime} from '@/libs/tools'
  import Cookies from 'js-cookie'

  var moment = require('moment');
  export default {
    name: "index",
    components: {},
    data() {
      return {
        time1: (new Date()).getTime() - 60 * 3 * 1000,
        v: this,
        pagerUrl: this.apis.PJJL.PAGER,
        time:'',
        param: {
          time: '',
          pjbh: '',
          jgdm: '',
          //分页数据
          total: 1,//总数量
          pageNum: 1,//当前页码
          pageSize: 8//每页显示数
        },
        jxList: [],
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
          {type: 'index', width: 60, align: 'center'},
          {
            title: '票号', align: 'center', render: (h, params) => {
              return h('div',
                params.row.pjbh.substring(0, params.row.pjbh.lastIndexOf('-'))
              );
            }
          },
          {title: '报名点', key: 'jgmc',align: 'center', render :(h, params) =>{
            let jgmcArray = params.row.jgmc.split("/");
            let res = "";
            if(jgmcArray.length == 2){
              res = params.row.jgmc
            }else if(jgmcArray.length == 3){
              res = jgmcArray[1]+'/'+jgmcArray[2]
            }
            return h('div', res);
          }},
          {
            title: '打印时间', align: 'center', key: 'registrationTime', render: (h, params) => {
              return h('div', params.row.cjsj.substring(0, 10)
              );
            }
          },
          {title: '打印人', key: 'cjr', align: 'center', width: 200},
          {
            title: '状态', align: 'center', render: (h, params) => {
              return h('div', [
                h('Tooltip',
                  {
                    props: {
                      placement: 'top',
                      content: params.row.zfr + ' ' + params.row.zfsj,
                      disabled: params.row.zfsj ? false : true
                    }
                  },
                  [
                    h('div',
                      params.row.zfsj ? '已作废' : '未作废'
                    )
                  ]
                )
              ])
            }
          },
          {
            title: '操作',
            fixed: 'right',
            align: 'center',
            minWidth: 120,
            render: (h, p) => {
              return h('div', [
                h('Tooltip',
                  {
                    props:
                      {
                        placement: 'top',
                        transfer: true,
                        content: p.row.zfsj ?'已作废':'作废',
                      }
                  },
                  [
                    h('Button', {
                      props: {
                        type: 'error',
                        size: 'small',
                        disabled: p.row.zfsj ? true : false,
                      },
                      style: {
                        marginRight: '10px'
                      },
                      on: {
                        click: () => {
                          this.swal({
                            title: '确定作废此条数据？',
                            showCancelButton: true,
                            confirmButtonText: '确定',
                            cancelButtonText: '取消'
                          }).then((val) => {
                            if (val) {
                              this.remove(p.row.pjbh)
                            }
                          });
                        }
                      }
                    }, '废')
                  ]
                ),
              ])
            }
          }
        ],
      }
    },
    created() {
      this.getDictList();
      this.getBmdList();
      // this.util.initTable(this);
      this.util.initPageSize(this);
      this.util.initTableHeight(this);
      this.util.fillTableColumns(this)
      this.getData()
      this.getJTjx()
    },
    methods: {
      //获取当前用户可操作的报名点
      getBmdList() {
        this.$http.get(this.apis.FRAMEWORK.getCurrentOrgTree, {timers: new Date().getTime()}).then((res) => {
          if (res.code === 200) {
            /*function tree(arr) {
              if(arr[0].value.length == 6){
                return arr[0].children
              }else if(arr[0].value.length == 3) {
                return arr[0].children[0].children
              }
            }
            this.dictList.bmd.data = tree(res.result)*/
            if (res.result[0].value.length == 3) {
              this.dictList.bmd.data = res.result[0].children[0].children;
            } else if (res.result[0].value.length == 6) {
              this.dictList.bmd.data = res.result[0].children;
            } else if (res.result[0].value.length == 9) {
              this.dictList.bmd.data = res.result
            }
          }
        }).catch((error) => {
        })
      },
      getData() {
        this.param.time= this.time?moment(this.time).format('YYYY-MM-DD'):''

        this.$http.get(this.pagerUrl, {params: this.param}).then((res) => {
          if (res.code === 200) {
            this.pageData = res.page.list
            this.param.total=res.page.total
          }
        }).catch((error) => {
        })
      },
      remove(bh) {    //作废
        let param = {
          pjbh: bh
        }
        this.$http.get(this.apis.PJJL.ZF, {params: param}).then((res) => {
          if (res.code === 200) {
            this.getData()
            this.swal({
              title: '已作废',
              type: 'success',
              showCancelButton: true,
              confirmButtonText: '确定',
              cancelButtonText: '取消'
            })
          }
        }).catch((error) => {
        })
      },
      getJTjx() {
        this.jxList = this.dictUtil.getByCode(this, 'ZDCLK1019');
        console.log(this.jx);
      },
      getDictList() {
        this.dictList.carType.data = this.dictUtil.getByCode(this, this.dictList.carType.code);
      },
      pageChange(n) {
        // this.util.pageChange(this, n);
        this.param.pageNum = n
        this.getData()
      },
      pageSizeChange(n) {
        // this.util.pageSizeChange(this, n);
        Cookies.set("pageSize",n);
        this.param.pageSize = n;
        this.getData()
      },
      auditConfirm(row, km, zt) {
        this.$http.post(this.apis.TRAINEE.UPDATETRAINTYPE, {id: row.id, subjects: km, type: zt}).then((res) => {
          let msg = res.message;
          if (res.code === 200) {
            this.$Message.success(msg);

            this.util.getPageData(this);
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

<style scoped>

</style>
