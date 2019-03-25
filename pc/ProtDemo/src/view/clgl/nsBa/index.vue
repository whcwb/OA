<template>
  <div class="boxbackborder box_col">
    <Row>
      <Col span="12">
        <pager-tit title="备案人绑定"></pager-tit>
        <div style="font-size: 16px">
          车管待年审
          <Tag color="error">{{tj['001']}}</Tag>
          运管待年审
          <Tag color="error">{{tj['002']}}</Tag>
          改气待年审
          <Tag color="error">{{tj['003']}}</Tag>
        </div>
      </Col>
      <Col span="12">
        <Row type="flex" justify="end">
          <Col span="24" align="right">
            <search-bar :parent="v" :showCreateButton="false"></search-bar>
          </Col>
        </Row>
      </Col>
    </Row>
    <table-area :parent="v"></table-area>
    <component :is="componentName"></component>
  </div>
</template>

<script>
  import bar from './BApeo'//备案人

  import confirm from './confirm.vue'

  import dzda from '../cllb/carMess/dzda'

  import printDa from '../../../components/print/carMess/carPage11'

  export default {
    name: 'char',
    components: {
      confirm, dzda,bar,printDa
    },
    data() {
      return {
        v: this,
        apiRoot: this.apis.carns,
        choosedItem: null,
        componentName: '',
        pageTotal: 0,
        tableColumns: [
          {title: "#", fixed: 'left', width: 60, type: 'index'},
          {title: '车牌号', key: 'cph', minWidth: 120, searchKey: 'cphLike'},
          {title: '年审类型', key: 'nslx', minWidth: 120, dict: 'nslx', searchType: 'dict'},
          {title: '年审时间', key: 'nssj', minWidth: 120},
          {title: '年审状态', key: 'zt', minWidth: 120, dict: 'nszt'},
          {title: '备案人', key: 'jsyxm', minWidth: 120,defaul:'-'},
          {title: '备案人联系电话', key: 'jsylxdh', minWidth: 120,defaul:'-'},
          {title: '备案人身份证号',key:'jsysfzh',minWidth: 120,defaul:'-'},
          {title: '备案人状态',key:'cjType',minWidth: 120,dict:'barZt'},
          {
            // cjType 1、已采集  2、过期  0、未采集
            title: '操作', fixed: 'right',align:'center', width: 120, render: (h, p) => {
              let buttons = [];
              buttons.push(
                h('Tooltip',
                  {props: {placement: 'top', content: '备案人',}},
                  [
                    h('Button', {
                      props: {
                        type: 'warning',
                        shape: 'circle',
                        size: 'small',
                      },
                      style: {margin: '0 10px 0 0'},
                      on: {
                        click: () => {
                          this.choosedItem = p.row;
                          this.componentName = 'bar'
                        }
                      }
                    }, '备')
                  ]
                ),
              )
              buttons.push(
                h('Tooltip',
                  {props: {placement: 'top', content: '证件打印',}},
                  [
                    h('Button', {
                      props: {
                        icon:'md-print',
                        type: 'success',
                        shape: 'circle',
                        size: 'small',
                      },
                      style: {margin: '0 10px 0 0'},
                      on: {
                        click: () => {
                            this.choosedItem = p.row;
                            this.componentName = 'printDa'
                        }
                      }
                    })
                  ]
                ),
              )

              return h('div', buttons);
            }
          },
        ],
        tj: {},
        pageData: [],
        param: {
          orderBy: 'nssj desc',
          total: 0,
          zhLike: '',
          ztIn: '0,2,3',
          pageNum: 1,
          pageSize: 8
        },
      }
    },
    created() {
      this.getPager()
    },
    methods: {
      getPager() {
        this.util.initTable(this);
        this.$http.post('/api/carns/getPage').then(res => {
          console.log(res);
          this.tj = res.result
        })
      },
    }
  }
</script>
