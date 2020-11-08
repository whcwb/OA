<template>
  <div>
    <Modal v-model="showModal"
           width="1100"
           :closable='false' :mask-closable="false">
      <div slot="header" class="modalSty">
        <Row>
          <Col span="8">
            库存流水
          </Col>
          <Col span="8">
            物品规格: {{mess.kcLx}}
          </Col>
          <Col span="8">
            物品名称: {{mess.kcMc}}
          </Col>
        </Row>
      </div>
      <div style="height: 500px">
        <div class="box_row" style="height: 100%">
          <div class="box_row_100 TimelineSty">
            <Table :height="440" stripe size="small"
                   :columns="RkTabTit" :data="RkTableData"></Table>
            <div style="text-align: right;padding: 6px 0;width: 100%;overflow: auto">
              <Page :total=Rktotal
                    :current=Rkparam.pageNum
                    :page-size=Rkparam.pageSize
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
          <div class="box_row_100 TimelineSty">
            <Table :height="440" stripe size="small"
                   :columns="CkTabTit" :data="CkTableData"></Table>
            <div style="text-align: right;padding: 6px 0;width: 100%;overflow: auto">
              <Page :total=Cktotal
                    :current=Ckparam.pageNum
                    :page-size=Ckparam.pageSize
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
      </div>
      <div slot="footer">
        <Button type="default" style="color: #949494" @click="close">关闭</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
  export default {
    name: "Record",
    filters: {
      lqr: (val) => {
        return val.split('-')[0]
      }
    },
    props: {
      mess: {
        type: Object,
        default: {
          kcLx: '',
          kcMc: ''
        }
      }
    },
    data() {
      return {
        showModal: true,
        RkTabTit: [
          {
            title: '入库时间',
            align: 'center',
            key: 'cjsj',
            render: (h, p) => {
              let a = p.row.cjsj.substring(0, 10)
              return h('div', a)
            }
          },
          {
            title: '入库批次',
            align: 'center',
            minWidth:80,
            key: 'pc'
          },
          {
            title: '入库数量',
            align: 'center',
            key: 'rkSl'
          },
          {
            title: '操作人',
            align: 'center',
            key: 'cjr',
            render: (h, p) => {
              let a = p.row.cjr.split('-')
              return h('div', a[1])
            }
          }
        ],
        RkTableData: [],
        Rktotal: 0,
        Rkparam: {
          kcId: this.mess.id,
          pageNum: 1,//当前页码
          pageSize: 10//每页显示数
        },
        CkTabTit: [
          {
            title: '出库时间',
            align: 'center',
            key: 'cjsj',
            render: (h, p) => {
              let a = p.row.cjsj.substring(0, 10)
              return h('div', a)
            }
          },
          {
            title: '领取人',
            align: 'center',
            key: 'lqr',
            render: (h, p) => {
              let a = p.row.lqr.split('-')
              return h('div', a[0])
            }

          },
          {
            title: '出库数量',
            align: 'center',
            key: 'lqSl',
          },
          {
            title: '操作人',
            align: 'center',
            key: 'cjr',
            render: (h, p) => {
              let a = p.row.cjr.split('-')
              return h('div', a[1])
            }
          }
        ],
        CkTableData: [],
        Cktotal: 0,
        Ckparam: {
          kcId: this.mess.id,
          pageNum: 1,//当前页码
          pageSize: 10//每页显示数
        },
      }
    },
    created() {
      this.getRkList()
      this.getCklist()
    },
    methods: {
      close() {
        this.$parent.compName = ''
      },
      getRkList() {
        this.$http.post('/api/bizrk/pager', this.Rkparam).then(res => {
          if (res.code == 200) {
            this.Rktotal = res.page.total
            this.RkTableData = res.page.list
          }
        }).catch(err => {

        })
      },
      getCklist() {
        this.$http.post('/api/bizck/pager', this.Ckparam).then(res => {
          if (res.code == 200) {
            this.Cktotal = res.page.total
            this.CkTableData = res.page.list
          }
        }).catch(err => {

        })


      },
      pageChange(n, num) {
        if (num == 0) {
          this.Rkparam.pageNum = n
          this.getRkList()
        } else if (num == 1) {
          this.Ckparam.pageNum = n
          this.getCklist()
        }
      },
      pageSizeChange(n, num) {
        if (num == 0) {
          this.Rkparam.pageSize = n
          this.getRkList()
        } else if (num == 1) {
          this.Ckparam.pageSize = n
          this.getCklist()
        }
      }
    }
  }
</script>

<style lang="less">
  .modalSty {
    font-size: 18px;
    font-weight: 600;
  }

  .TimelineSty {
    padding: 0 16px;
  }
</style>
