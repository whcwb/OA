<template>
  <div class="box_col">
    <pager-tit title="体检退费"></pager-tit>
    <Table :height="AF.getPageHeight()-260" stripe
           size="small"
           :columns="tabTit" :data="tableData"></Table>
    <div style="margin: auto;padding-top: 10px">
      <Page
        :total="total"
        :current="param.pageNum"
        :page-size="param.pageSize"
        show-total
        @on-change="(n)=>{pageChange(n)}"
        @on-page-size-change="(n)=>{pageSizeChange(n)}"
      ></Page>
    </div>
  </div>
</template>

<script>
  export default {
    name: "index",
    data() {
      return {
        tabTit: [
          {
            type: 'index', align: 'center', minWidth: 80, fixed: 'left',
            // render: (h, p) => {
            //   return h('span', p.index + (this.param.pageNum - 1) * this.param.pageSize + 1);
            // }
          },
          {
            title: '姓名',
            key: 'traineeName',
            align: 'center',
            minWidth: 80
          },
          {
            title: '证件号码',
            key: 'idCardNo',
            align: 'center',
            minWidth: 160
          },
          {
            title: '所属驾校',
            key: 'chargeSource',
            align: 'center',
            minWidth: 120
          },
          {
            title: '缴费时间',
            key: 'cjsj',
            align: 'center',
            minWidth: 160
          },
          {
            title: '收费人',
            key: 'cjr',
            align: 'center',
            minWidth: 80,
            render: (h, p) => {
              let a = p.row.cjr.split('-')
              return h('div', a[1])
            }
          },
          {
            title: '驳回时间',
            key: 'bhsj',
            align:'center',
            minWidth:160
          },
          {
            title: '驳回操作人',
            key: 'bhr',
            align:'center',
            minWidth:100,
            render: (h, p) => {
              let a = p.row.bhr.split('-')
              return h('div', a[1])
            }
          },
          {
            title: '退费时间',
            key: 'tfsj',
            align:'center',
            minWidth:160
          },
          {
            title: '退费人',
            key: 'tfr',
            align:'center',
            minWidth:100
          },
          {
            title: '操作',
            width: 100,
            fixed: 'right',
            align:'center',
            render: (h, p) => {
              if(p.row.zt == '20'){
                return h('div','退费成功')
              }else {
                return h('div', [
                  h('Button', {
                    props: {
                      type: 'warning',
                      size: 'small'
                    },
                    on: {
                      click: () => {
                        this.$http.post('/api/chargemanagement/returnInspect',{id:p.row.id}).then(res=>{
                          if(res.code == 200){
                            this.swal({
                              title:'退费成功',
                              type:"success"
                            })
                            this.getdata()
                          }else {
                            this.swal({
                              title:res.message,
                              type:"error"
                            })
                          }
                        }).catch(err=>{})
                      }
                    }
                  }, '退费确认')
                ]);
              }
            }
          }
        ],
        tableData: [],
        total:'',
        param: {
          pageNum: 1,
          pageSize: 8,
          jfzt: '10,20'
        }
      }
    },
    created() {
      this.getdata()
    },
    computed: {},
    methods: {
      getdata() {
        this.$http.get('/api/chargemanagement/pager', {params:this.param}).then(res => {
          if (res.code == 200) {
            console.log(res);
            this.tableData = res.page.list
            this.total = res.page.total
          }
        }).catch(err => {
        })
      },
      //分页点击事件按
      pageChange(n) {
        this.param.pageNum = n
        this.getdata()
      },
      pageSizeChange(n) {
        this.param.pageSize = n
        this.getdata()
      }
    }
  }
</script>

<style lang="less">
  .iconSty {
    cursor: pointer;
    position: absolute;
    top: 40%;
    left: 50%;
    transform: translate(-50%, -50%);
  }
</style>
