<template>
  <div>
    <Row style="margin-bottom: 12px">
      <Col span="6">
        <pager-tit title="库存管理" iconname="ios-football"></pager-tit>
      </Col>
      <Col span="18">
        <Row type="flex" justify="end">
          <Col>
            <Button type="success" style="margin-right: 8px"
                    @click="compName='batch'"
            >批量入库
            </Button>
          </Col>
          <Col>
            <Button type="info" style="margin-right: 8px"
                    @click="compName='newClass'"
            >入库流水
            </Button>
          </Col>
          <Col>
            <Button type="info" style="margin-right: 8px"
                    @click="compName='handout'"
            >出库流水
            </Button>
          </Col>
        </Row>
      </Col>
    </Row>
    <Table :height="AF.getPageHeight()-320" stripe size="small"
           :columns="tabTit" :data="tableData"></Table>
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
    <component :is="compName" :mess="mess"></component>
  </div>
</template>
<script>
  import newClass from './comp/newClass'
  import batch from './comp/batch'
  import handout from './comp/handout'
  import addnumber from './comp/add'
  import resuceNumber from './comp/reduce'
  import PrintBx from './comp/PrintBx'
  import record from './comp/Record'

  export default {
    components: {
      newClass, batch,
      addnumber, resuceNumber,
      record, handout,PrintBx
    },
    data() {
      return {
        compName: '',
        mess: {},
        param1Loading: false,
        tabTit: [
          {
            type: 'index2', align: 'center', width: 80, fixed: 'left',
            render: (h, params) => {
              return h('span', params.index + (this.param.pageNum - 1) * this.param.pageSize + 1);
            }
          },
          {
            title: '物品名称', minWidth: 80, align: 'center', fixed: 'left', key: 'kcMc'
          },
          {
            title: '物品规格', minWidth: 80, align: 'center', key: 'kcLx'
          },
          {
            title: '库存数量', minWidth: 80, align: 'center', key: 'kcSl'
          },
          {
            title: '操作', minWidth: 260, align: 'center', render: (h, p) => {
              return h('div', [
                h('Button', {
                  props: {type: "success", size: 'small'},
                  style: {margin: '0 6px'},
                  on: {
                    click: () => {
                      this.addNumber(p.row)
                    }
                  }
                }, '入库'),
                h('Button', {
                  props: {type: "info", size: 'small'},
                  style: {margin: '0 6px'},
                  on: {
                    click: () => {
                      this.resuceNumber(p.row)
                    }
                  }
                }, '出库'),
                h('Button', {
                  props: {type: "warning", size: 'small'},
                  style: {margin: '0 6px'},
                  on: {
                    click: () => {
                      this.mess = p.row
                      this.compName = 'record'
                    }
                  }
                }, '流水'),
                h('Button', {
                  props: {type: "error", size: 'small'},
                  style: {margin: '0 6px'},
                  on: {
                    click: () => {
                      this.Dele(p.row)
                    }
                  }
                }, '删除')
              ])
            }
          }
        ],
        tableData: [],
        total: 0,
        param: {
          //分页数据
          pageNum: 1,//当前页码
          pageSize: 10//每页显示数
        },
      }
    },
    created() {
      this.getPagerList()
    },
    methods: {
      addNumber(mess) {
        this.mess = mess
        this.compName = 'addnumber'
      },
      resuceNumber(mess) {
        this.mess = mess
        this.compName = 'resuceNumber'
      },
      Dele(mess) {
        var v = this
        this.swal({
          title: '此类库存量为' + mess.kcSl + '是否清空并删除！',
          type: 'warning',
          showCancelButton: true,
          confirmButtonText: '删除',
          cancelButtonText: '关闭',
        }).then(p => {
          if (p.value) {
            v.$http.post('/api/bizkc/remove/' + mess.id).then(res => {
              if (res.code == 200) {
                v.swal({
                  type: 'success',
                  title: res.message
                })
              } else {
                v.swal({
                  type: 'error',
                  title: res.message
                })
              }
              v.getPagerList()
            }).catch(err => {

            })
          }
        })
      },
      getPagerList() {
        this.$http.post('/api/bizkc/pager', this.param).then(res => {
          this.tableData = res.page.list
          this.total = res.page.total
        }).catch(err => {

        })
      },
      pageChange(n, num) {
        // console.log(num + '--1--' + n);
        if (num == 0) {
          this.param.pageNum = n
          this.getPagerList()
        } else if (num == 1) {
          this.param1.pageNum = n
          this.getPagerList1()
        }
      },
      pageSizeChange(n, num) {
        // console.log(num + '--2--' + n);
        if (num == 0) {
          this.param.pageSize = n
          this.getPagerList()
        } else if (num == 1) {
          this.param1.pageSize = n
          this.getPagerList1()
        }
      }
    }
  }
</script>
<!--/api/bizkc/save    保存一条库存记录-->
<!--/api/bizkc/add    库存添加      id  , sl  (数量)-->
<!--/api/bizkc/handOut  库存分配  id  , sl  ,  zgId (员工id)-->
<!--/api/bizkc/remove/{pkid}   删除库存-->
