<template>
  <div>
    <Modal v-model="showModal"
           title="出库流水"
           width="900"
           :closable='false' :mask-closable="false">
      <div slot="header">
        <Row>
          <Col span="12">
            <div style="font-size: 16px;font-weight: 600;line-height: 35px">
              出库流水
            </div>
          </Col>
          <Col span="4" style="padding-left: 25px">
            <Input v-model="Ckparam.kcMc" type="text" placeholder="请输入物品名称" @on-change="findCk" ></Input>
          </Col>
          <Col span="4" style="padding-left: 25px">
            <Input v-model="Ckparam.kcLx" type="text" placeholder="请输入物品规格" @on-change="findCk"></Input>
          </Col>
          <Col span="4" align="right">
            <Button type="warning" style="float: right" @click="close">关闭</Button>
          </Col>
        </Row>
      </div>
      <div style="height: 550px;overflow: auto">
        <Table  stripe size="small"
               :columns="CkTabTit" :data="CkTableData"></Table>
      </div>
      <div slot="footer">
        <div style="text-align: right;padding: 6px 0">
          <Page :total=Cktotal
                :current=Ckparam.pageNum
                :page-size=Ckparam.pageSize
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
        param1Loading:false,
        addmess: {
          kcSl:0
        },
        CkTabTit: [
          {
            title: '物品名称',
            align: 'center',
            render: (h,p) => {
              let a = p.row.kc.kcMc;
              return h('div',a)
            }
          },
          {
            title: '物品规格',
            align: 'center',
            render: (h,p) => {
              let a = p.row.kc.kcLx;
              return h('div', a);
            }
          },
          {
            title: '领取时间',
            align: 'center',
            key: 'cjsj',
            minWidth:60,
            render: (h, p) => {
              let a = p.row.cjsj.substring(0, 16)
              return h('div', a)
            }
          },
          {
            title:'领取部门',
            align: 'center',
            key: 'jgmc'
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
          kcMc:'',
          kcLx: '',
          pageNum: 1,//当前页码
          pageSize: 10//每页显示数
        },
      }
    },
    created(){
      this.getCklist()
    },
    methods:{
      findCk(e){
        this.getCklist();
      },
      getCklist() {
        this.$http.post('/api/bizck/getPager', this.Ckparam).then(res => {
          if (res.code == 200 && res.page) {
            this.Cktotal = res.page.total
            this.CkTableData = res.page.list
          }else{
            this.CkTableData = [];
            this.Cktotal = 0;
          }
        }).catch(err => {

        })


      },
      close(){
        this.$parent.compName = ''
      },
      pageChange(n) {
        // console.log(num + '--1--' + n);
        this.Ckparam.pageNum = n
        this.getCklist()

      },
      pageSizeChange(n) {
        // console.log(num + '--2--' + n);
        this.Ckparam.pageSize = n
        this.getCklist()
      }
    }
  }
</script>
<style lang="less">
  .vertical-center-modal{
    display: flex;
    align-items: center;
    justify-content: center;

    .ivu-modal{
      top: 0;
    }
  }
</style>
