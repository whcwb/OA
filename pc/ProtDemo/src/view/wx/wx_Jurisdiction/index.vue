<style lang="less">
  @import '../../../assets/css/wdPageSty';
</style>
<!--用户管理-->
<template>
  <div class="boxbackborder box_col">
    <Row :gutter="10" style="margin:8px 0">
      <Col span="6" :lg="6" :md="24">
        <pager-tit title="用户管理"></pager-tit>
      </Col>
      <Col span="18" :lg="18" :md="24">
        <Row type="flex" :gutter="10" justify="end" style="margin:8px 0">
          <Col span="5" :lg="5" :md="6" >
            <Input v-model="param.xmLike"
                   placeholder="请输入用户姓名"
                   @on-keyup.enter="findMessList()"
                   @on-change="findMessList"></Input>
          </Col>
          <Col span="5" :lg="5" :md="6" >
            <Input v-model="param.sjhLike"
                   placeholder="请输入手机号码"
                   @on-keyup.enter="findMessList()"
                   @on-change="findMessList"></Input>
          </Col>
          <Col span="2" :lg="2" :md="2" align="middle">
            <Button type="primary" @click="findMessList()">
              <Icon type="md-search"></Icon>
              <!--查询-->
            </Button>
          </Col>
        </Row>
      </Col>
    </Row>

    <Row style="position: relative;">
      <Table
        stripe  size="small"
        :height="AF.getPageHeight()-320"
        :row-class-name="rowClassName"
        :columns="tableTiT"
        :data="tableData"></Table>
    </Row>
    <div style="text-align: right;padding: 6px 0">
      <!--<Page :total=pageTotal :current=param.pageNum :page-size=param.pageSize-->
            <!--:page-size-opts=[8,10,20,30,40,50] @on-page-size-change='(e)=>{param.pageSize=e;pageChange()}'-->
            <!--show-total show-elevator show-sizer placement='top' @on-change='pageChange'></Page>-->
      <Page :total=pageTotal
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
    <component
      :is="compName"
      :usermes="usermes"
      :usermesType="userMesType"
      @listF='listF'></component>
  </div>
</template>

<script>
  import mixins from '@/mixins'
  import changemes from './comp/changmes.vue'

  export default {
    name: 'char',
    components: {
      changemes
    },
    mixins: [mixins],
    data() {
      return {
        //tab高度
        tabHeight: 220,
        //动态组建
        compName: '',
        //动态组建数据
        usermes: {},
        userMesType: true,
        //分页
        //---数据总数
        pageTotal: 2,
        tableTiT: [{
          type: 'index2', align: 'center', minWidth: 80, fixed: 'left',
          render: (h, params) => {
            return h('span', params.index + (this.param.pageNum - 1) * this.param.pageSize + 1);
          }
        },
          {
            title: '帐号',
            align: 'center',
            key: 'zh',
            minWidth: 120,
          },
          {
            title: '姓名',
            align: 'center',
            key: 'xm',
            minWidth: 80,
          },
          {
            title: '证件号码',
            align: 'center',
            key: 'zjhm',
            minWidth: 150,
          },
          {
            title: '性别',
            align: 'center',
            key: 'xb',
            minWidth: 120,
            render: (h, params) => {
              return h('div', params.row.xb == '10' ? '男' : '女')
            }
          },
          {
            title: '手机号',
            minWidth: 120,
            align: 'center',
            key: 'sjh',
          },
          {
            title: '职务',
            align: 'center',
            key: 'zw',
            minWidth: 100,
          },
          {
            title: '类型',
            align: 'center',
            key: 'lx',
            minWidth: 80,
            render: (h, p) => {
              let val = this.dictUtil.getValByCode(this, this.yhlxDictCode, p.row.lx)
              return h('div', val)
            }
          },
          {
            title: '权限管理',
            key: 'action',
            width: 100,
            fixed: 'right',
            align: 'center',
            render: (h, params) => {
              if (params.row.lx === 'su') {
                return ''
              }
              return h('div', [
                // h('Button', {
                //   props: {
                //     type: 'success',
                //     icon: 'md-create',
                //     shape: 'circle',
                //     size: 'small'
                //   },
                //   style: {
                //     cursor: "pointer",
                //     margin: '0 8px 0 0'
                //   },
                //   on: {
                //     click: () => {
                //       this.userMesType = false
                //       this.usermes = params.row
                //       this.compName = 'newmess'
                //     }
                //   }
                // }),
                h('Button', {
                  props: {
                    type: 'primary',
                    icon: 'md-menu',
                    shape: 'circle',
                    size: 'small'
                  },
                  style: {
                    cursor: "pointer",
                    margin: '0 8px 0 0'
                  },
                  on: {
                    click: () => {
                      this.RootShowF(params.row)

                    }
                  }
                }),
                // h('Button', {
                //   props: {
                //     type: 'error',
                //     icon: 'md-close',
                //     shape: 'circle',
                //     size: 'small'
                //   },
                //   style: {
                //     cursor: "pointer",
                //     margin: '0 8px 0 0'
                //   },
                //   on: {
                //     click: () => {
                //       this.listDele(params.row.yhid)
                //     }
                //   }
                // })
              ]);
            }
          }
        ],
        tableData: [],
        //收索
        param: {
          sjhLike: '',
          xmLike: '',
          timers:'',
          pageNum: 1,
          pageSize: 30
        },
        yhlxDict: [],
        yhlxDictCode: 'ZDCLK0003'
      }
    },
    watch: {},
    created() {
      this.getmess()
      this.getDict()
    },
    methods: {
      getDict() {
        this.yhlxDict = this.dictUtil.getByCode(this, this.yhlxDictCode);
        console.log(this.yhlxDict);
      },
      getmess() {
        var v = this
        this.param.timers = new Date().getTime()
        this.$http.get(this.apis.USER.QUERY, {params: v.param}).then((res) => {
//					log(res)
          v.tableData = res.page.list
          v.pageTotal = res.page.total
        })
      },
      //权限分配
      RootShowF(val) {
        var v = this
        v.compName = 'changemes'
        this.usermes = val;
      },
      //收索事件
      findMessList() {
        var v = this
        this.param.timers = new Date().getTime()
        this.$http.get(this.apis.USER.QUERY, {params: v.param}).then((res) => {
//					log(res)
          v.tableData = res.page.list
        })
      },
      listF(res) {
        this.getmess()
        this.compName = ''
      },
      //分页点击事件按
      pageChange(n) {
          this.param.pageNum = n
          this.getmess()
      },
      pageSizeChange(n) {
          this.param.pageSize = n
          this.getmess()
      }
    }
  }
</script>
