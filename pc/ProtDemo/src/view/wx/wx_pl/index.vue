<template>
  <div class="box">
    <pager-tit title="评论管理" iconname="ios-football"></pager-tit>
    <div class="box_row_1auto">
      <Table :height="AF.getPageHeight()-260" stripe size="small"
             :columns="tabTit" :data="tabData"></Table>
      <div style="text-align: right;padding: 10px 0">
        <Page :total=total
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
      <!--<component :is="compName" :mess="userMess"></component>-->
    </div>
  </div>

</template>

<script>
  export default {
    name: "index",
    components:{
    },
    data(){
      return{
        tabTit:[
          {
            type: 'index2', align: 'center', width: 80, fixed: 'left',
            render: (h, p) => {
              return h('span', p.index + (this.param.pageNum - 1) * this.param.pageSize + 1);
            }
          },
          {
            title:'教练员',
            align:'center',
            minwidth:180,
            key:'coachName'
          },
          {
            title:'评价内容',
            key:'comment',
            align:'center',
            minwidth:180
          },
          {
            title:'评价时间',
            key:'cjsj',
            align:'center',
            minwidth:180
          },
          {
            title:'评价人',
            key:'traineeName',
            align:'center',
            minwidth:180
          },
          {
            title: '删除', width: 80, align: 'center',fixed: 'right', render: (h, p) => {
              return h('div', [
                h('Button', {
                  props: {
                    icon: 'md-close',
                    type: 'text',
                    ghost: true,
                    shape: "circle",

                  },
                  style: {
                    fontSize: '24px',
                    color: '#2db7f5'
                  },
                  on: {
                    click: () => {
                      this.Dele(p.row.id)
                    }
                  }
                })
              ]);
            }
          }
        ],
        tabData:[],
        total:0,
        param:{
          pageSize:10,
          pageNum:1,
        }

      }
    },
    created(){
      this.getPagerList()
    },
    methods:{
      Dele(id){
        this.$http.post('/api/ratedetail/remove/'+id).then(res=>{
          if(res.code == 200){
            this.getPagerList()
          }
          this.$Message.info(res.message)
        }).catch(err=>{

        })
      },
      getPagerList(){
        this.$http.post('/api/ratedetail/pager',this.param).then(res=>{
          if(res.code == 200){
            this.total = res.page.total
            this.tabData = res.page.list
          }
        }).catch(err=>{

        })
      },
      pageSizeChange(n) {
        this.getPagerList()
      }
    }
  }
</script>

<style scoped>

</style>
