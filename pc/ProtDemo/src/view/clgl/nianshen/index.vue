<template>
  <div class="boxbackborder box_col">
    <Row>
      <Col span="12">
        <pager-tit title="待年审车辆"></pager-tit>
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
    <component :is="componentName" :carInfo="choosedItem"></component>
  </div>
</template>

<script>
  import formData from './formModal.vue'
  import bar from './BApeo'

  import confirm from './confirm.vue'

  import dzda from '../cllb/carMess/dzda'


  import printSqwts from '../../../components/print/printSqwts'

  //年审经济开发区
  import jjkfq from './jjkfq'

  export default {
    name: 'char',
    components: {
      formData, confirm, dzda,bar,printSqwts,jjkfq
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
          {title: '负责人', key: 'jsyxm', minWidth: 120},
          {title: '联系电话', key: 'jsylxdh', minWidth: 120},
          {title: '年审状态', key: 'zt', minWidth: 120, dict: 'nszt'},
          {
            // cjType 1、已采集  2、过期  0、未采集
            title: '操作', fixed: 'right', width: 180, render: (h, p) => {
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
                  {props: {placement: 'top', content: '年审确认',}},
                  [
                    h('Button', {
                      props: {
                        type: 'info',
                        shape: 'circle',
                        icon:'md-checkmark',
                        size: 'small',
                      },
                      style: {margin: '0 10px 0 0'},
                      on: {
                        click: () => {
                          if(p.row.cjType =='2'){
                            this.swal({
                              title:'备案人已过期,请重新绑定备案人！',
                              type:'warning'
                            })
                            return
                          }
                          if(p.row.cjType =='0'){
                            this.swal({
                              title:'请绑定备案人！',
                              type:'warning'
                            })
                            return
                          }
                            this.choosedItem = p.row;
                            this.componentName = 'formData'
                        }
                      }
                    })
                  ]
                ),
              )
              buttons.push(this.util.buildButton(this, h, 'success', 'md-print', '委托人打印', () => {
                this.choosedItem = p.row;
                this.componentName = 'printSqwts'
              }));
              buttons.push(this.util.buildButton(this, h, 'success', 'md-print', '备案资料打印', () => {
                this.choosedItem = p.row;
                this.componentName = 'jjkfq'
              }));

              // buttons.push(this.util.buildButton(this, h, 'success', 'ios-browsers-outline', '点子档案', () => {
              //   this.choosedItem = p.row;
              //   this.componentName = 'dzda'
              // }));

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

      let cph = this.$route.params.cph;
      if(cph){
        this.param.cphLike = cph
      }
      this.getPager()
    },
    methods: {
      getPager(){
        this.util.initTable(this);
        this.$http.post('/api/carns/getPage').then(res => {
          console.log(res);
          this.tj = res.result
        })
      },
    }
  }
</script>
