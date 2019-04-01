<template>
  <div>
    <Modal v-model="showModal"
           title="入库流水"
           width="900"
           :closable='false' :mask-closable="false">
      <div slot="header">
        <Row>
          <Col span="12">
            <div style="font-size: 16px;font-weight: 600;line-height: 35px">
              入库流水
            </div>
          </Col>
          <Col span="12" align="right">
            <Button type="warning" style="float: right" @click="close">关闭</Button>
          </Col>
        </Row>
      </div>
      <div style="height: 550px;overflow: auto">
        <div v-for="(it,index) in MaxTabList">
          <!--:height="AF.getPageHeight()-320"-->
          <div style="font-size: 16px;font-weight: 600;padding: 6px 0">
            批次：{{it.pc}} 合计:
            <span style="color: #fa541c;">{{it.total}}元</span>
          </div>
          <Table stripe size="small"
                 :columns="tabTit" :data="it.bizRkList"></Table>
        </div>
      </div>
      <div slot="footer">
        <div style="text-align: right;padding: 6px 0">
          <Page :total=total
                :current=param.pageNum
                :page-size=param.pageSize
                :page-size-opts=[1,8,10,20,30,40,50]
                show-total
                show-elevator
                show-sizer
                placement='top'
                @on-page-size-change='(n)=>{pageSizeChange(n)}'
                @on-change='(n)=>{pageChange(n)}'>
          </Page>
        </div>
      </div>
    </Modal>
  </div>
</template>

<script>
  export default {
    name: "newClass",
    data() {
      return {
        showModal: true,
        param1Loading: false,
        addmess: {
          kcSl: 0
        },
        ruleInline: {
          kcLx: [
            {required: true, message: '请输入物品类别', trigger: 'blur'}
          ],
          kcMc: [
            {required: true, message: '请输入物品名称', trigger: 'blur'}
          ],
        },
        MaxTabList: [],
        tabTit: [
          {
            type: 'index',
            align: 'center',
            width: 60
          },
          {
            title: '物品名称',
            align: 'center',
            render: (h, p) => {
              return h('div', p.row.bizKc.kcMc)
            }
          },
          {
            title: '物品规格',
            align: 'center',
            render: (h, p) => {
              return h('div', p.row.bizKc.kcLx)
            }
          },
          {
            title: '入库数量',
            align: 'center',
            key: 'rkSl'
          },
          {
            title: '物品单价',
            align: 'center',
            key: 'rkDj'

          },
          {
            title: '合计',
            align: 'center',
            render: (h, p) => {
              let a = p.row.rkSl * p.row.rkDj
              return h('div', a + '元')
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
      this.gethisList()
    },
    methods: {
      gethisList() {
        this.$http.post('/api/bizrk/getPc', this.param).then(res => {
          if (res.code == 200) {
            this.MaxTabList = res.page.list
            this.total = res.page.total
          }
        }).catch(err => {

        })
      },
      close() {
        this.$parent.compName = ''
      },
      pageChange(n) {
        // console.log(num + '--1--' + n);
        this.param.pageNum = n
        this.gethisList()

      },
      pageSizeChange(n) {
        // console.log(num + '--2--' + n);
        this.param.pageSize = n
        this.gethisList()
      }
    }
  }
</script>
<style lang="less">
  .vertical-center-modal {
    display: flex;
    align-items: center;
    justify-content: center;

    .ivu-modal {
      top: 0;
    }
  }
</style>
