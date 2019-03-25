<template>
  <div>
    <Row>
      <Col span="4">
        <pager-tit title="车辆管理"></pager-tit>
      </Col>
      <Col span="20">
        <Row type="flex" justify="end">
          <Col span="24" align="right">
            <search-bar :parent="v"></search-bar>
          </Col>
        </Row>
      </Col>
    </Row>
    <table-area :parent="v"></table-area>
    <component :is="componentName"></component>
  </div>
</template>

<script>
  import formData from './formModal.vue'
  import files from './files.vue'
  import chanquanHistory from './chanquanHistory'
  import chanquanChange from './chanquanChange/chanquanChange'

  export default {
    name: 'char',
    components: {files, formData, chanquanHistory, chanquanChange},
    data() {
      return {
        v: this,
        apiRoot: this.apis.CAR,
        choosedItem: null,
        componentName: '',
        pageTotal: 0,
        tableColumns: [
          {title: "#", fixed: 'left', width: 60, type: 'index'},
          {title: '档案号', key: 'dah', minWidth: 120},
          {title: '车牌号', key: 'cph', minWidth: 120, searchKey: 'cphLike',render:(h,p)=>{
              return h('div',{
                style:{
                  cursor: 'pointer',
                },
                on:{
                  click:()=>{
                    this.choosedItem = p.row;
                    this.componentName = 'formData'
                  }
                }
              },p.row.cph);
          }},
          {title: '保单号', key: 'bxBdzbbh', minWidth: 120,render:(h,p)=>{
            return h('div',{
              style:{
                cursor: 'pointer',
              },
              on:{
                click:()=>{
                  this.$router.push({name:'nsxx',params:{cph:p.row.cph}})
                }
              }
            },p.row.bxBdzbbh);
          }},
          {title: '使用人姓名', key: 'syrName', minWidth: 120,},
          {title: '号牌种类', key: 'hpzl', minWidth: 120, dict: 'ZDCLK1036'},
          {
            title: '是否有电子档案', key: 'electronicFileType', dict: 'yn', minWidth: 180,
            render: (h, p) => {
              let a = ''
              if (p.row.electronicFileType == 0){
                a = '无'
              } else {
                a = '电子档案'
              }
              return h('div',{on:{
                  click:()=>{
                    this.choosedItem = p.row;
                    this.$store.commit('clProps_C', p.row)
                    this.componentName = 'files'
                  }
                }}, a)
            }
          },
          {title: '车型', key: 'pxcx', minWidth: 100, dict: 'ZDCLK0040'},
          // {title: '初登日期', key: 'ccdjrq', minWidth: 90, searchType: 'daterange'},
          // {title: '年审时间', key: 'warnNssjDate', minWidth: 90, searchType: 'daterange'},
          // {title: '投保公司', key: 'bxTbgs', minWidth: 140,},
          // {title: '起保日期', key: 'bxBxrq', searchType: 'daterange'},
          // {title: '终保日期', key: 'bxBxz', minWidth: 120, searchType: 'daterange'},
          // {title: '产权人姓名', key: 'clCqr', minWidth: 120,},

          // {
          //   title: '运管备案', key: 'yyCdrq', dict: 'yn', minWidth: 120,
          //   render: (h, p) => {
          //     let a = ''
          //     if (p.row.yyCdrq == '') {
          //       a = '否'
          //     } else {
          //       a = '是'
          //     }
          //     return h('div', a)
          //   }
          // },
          // {
          //   title: '是否改气', key: 'gxCdrq', dict: 'yn', minWidth: 120,
          //   render: (h, p) => {
          //     let a = ''
          //     if (p.row.gxCdrq == '') {
          //       a = '否'
          //     } else {
          //       a = '是'
          //     }
          //     return h('div', a)
          //   }
          // },
          {
            title: '状态', key: 'carType', dict: 'ZDCLK1042', minWidth: 200,
            render: (h, p) => {
              let a = []
              let tags = []
              a = p.row.carType.split(',')
              a.forEach((it,index)=>{
                tags.push(
                  h('Tag',{props:{color:'cyan'}},this.dictUtil.getValByCode(this, 'ZDCLK1042', it))
                )
              })
              return h('div',tags)
            }
          },
          {
            title: '操作',align:'center', width: 80, fixed: 'right', render: (h, p) => {
              let buttons = [];
              // buttons.push(this.util.buildeditButton(this, h, p));
              // buttons.push(this.util.buildButton(this, h, 'info', 'ios-paper', '产权变更', () => {
              //   this.choosedItem = p.row;
              //   this.componentName = 'chanquanChange'
              // }));
              buttons.push(this.util.buildButton(this, h, 'info', 'ios-paper-outline', '车辆详情', () => {
                this.choosedItem = p.row;
                this.componentName = 'chanquanHistory'
              }));
              // buttons.push(this.util.buildButton(this, h, 'info', 'ios-albums', '电子档案', () => {
              //   this.choosedItem = p.row;
              //   this.$store.commit('clProps_C', p.row)
              //   this.componentName = 'files'
              // }));
              return h('div', buttons);
            }
          },
        ],
        pageData: [],
        param: {
          total: 0,
          pageNum: 1,
          pageSize: 8
        },

      }
    },
    created() {
      this.util.initPageSize(this);
      this.util.initTableHeight(this);
      this.util.fillTableColumns(this)
      this.util.getPageData(this, (res) => {
        for (let r of res) {
          r.ygba = !!r.yyCdrq ? "1" : '0'
          r.sfgq = !!r.gxCdrq ? "1" : '0'
        }
      })
    },
    methods: {
      getPagerList() {
        this.util.getPageData(this)
      },
      getClsyr(){
        this.$http.post('/api/clsyr/getAll').then((res)=>{
          console.log(res);
        })
      },
    }
  }
</script>
