<template>
  <div>
    <!--draggable-->
    <Modal
      v-model="modalShow"
      width="80%"
      :closable='false'
      :mask-closable="false"
      title="档案库信息">
      <div :style="{height:AF.getPageHeight()/3*2+'px'}">
        <Table :height="AF.getPageHeight()/3*2-46+'px'"
               :columns="tabTit"
               stripe
               :data="tabData"></Table>
        <div style="text-align: right;border-top: #f2f2f2 3px solid;padding-top: 16px">
          <Page :total=total
                :current=param.pageNum
                :page-size=param.pageSize
                :page-size-opts=[12,24,46,48,50]
                show-total
                show-elevator
                show-sizer
                placement='top'
                @on-page-size-change='pageSizeChange'
                @on-change='pageChange'>
          </Page>
        </div>
      </div>
      <div slot="footer">
        <Button type="info" @click="close">关闭</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
  export default {
    name: "listTab",
    components: {},
    props: {
      boxNum: {
        type: String,
        default: ''
      }
    },
    data() {
      return {
        modalShow: true,
        tabTit: [
          {
            title: '流水号',
            key: 'serialNum'
          },
          {
            title: '姓名',
            key: 'traineeName'
          },
          {
            title: '证件号码',
            key: 'idCardNo'
          },
          {
            title: '联系电话',
            key: 'phone'
          },
          {
            title: '报名时间',
            key: 'registrationTime',
            render: (h, p) => {
              return h('div', [
                h('Tooltip', {
                  props: {
                    placement: 'top',
                    content: p.row.registrationTime
                  }
                }, [
                  h('Time', {
                    props: {
                      time: new Date(p.row.registrationTime.replace(/-/g, "/"))
                    }
                  })
                ])
              ])
            }
          }
        ],
        tabData: [],
        total: 0,//总数量
        param: {
          archivesCode: '',
          //分页数据
          pageNum: 1,//当前页码
          pageSize: 12//每页显示数
        }
      }
    },
    created() {
      if (this.boxNum != '') {
        this.param.archivesCode = this.boxNum
        this.getPagerList()
      } else {
        this.close()
      }
    },
    methods: {
      getPagerList() {
        this.$http.post(this.apis.DAK.TAB, this.param).then((res) => {
          if (res.code == 200) {
            this.tabData = res.result.archivesRecordList
          }
        }).catch((err) => {

        })
      },
      close() {
        this.$parent.compName = ''
      },
      pageChange(n) {
        this.param.pageNum = n
        this.getPagerList()
      },
      pageSizeChange(n) {
        this.param.pageSize = n
        this.getPagerList()
      }
    }
  }
</script>

<style scoped>

</style>
