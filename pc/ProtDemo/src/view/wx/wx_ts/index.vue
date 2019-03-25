<template>
  <div class="box">
    <pager-tit title="投诉管理" iconname="ios-football"></pager-tit>
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
      <component :is="compName" :mess="userMess"></component>
    </div>
  </div>

</template>

<script>
  import chang from './comp/chang'
  export default {
    name: "index",
    components:{
      chang
    },
    data(){
      return{
        compName:'',
        userMess:{},
        tabTit:[
          {
            type: 'index2', align: 'center', width: 80, fixed: 'left',
            render: (h, p) => {
              return h('span', p.index + (this.param.pageNum - 1) * this.param.pageSize + 1);
            }
          },
          {
            title:'教练头像',
            align:'center',
            minWidth:180,
            key:'coachHeadImg'
          },
          {
            title:'教练姓名',
            key:'coachName',
            align:'center',
            minWidth:180
          },
          {
            title:'教练电话',
            key:'coachPhone',
            minWidth:180,
            align:'center',
          },
          {
            title:'投诉内容',
            key:'complainContent',
            align:'center',
            minWidth:180
          },
          {
            title:'投诉时间',
            key:'complainTime',
            align:'center',
            minWidth:180
          },
          {
            title:'投诉人',
            key:'traineeHeadImg',//头像traineeHeadImg ---姓名 traineeName
            align:'center',
            minWidth:180
          },
          {
            title:'电话',
            key:'traineePhone',
            align:'center',
            minWidth:180
          },
          {
            title:'处理结果',
            key:'feedback',
            align:'center',
            minWidth:180
          },
          {
            title:'处理时间',
            key:'feedback',
            align:'center',
            minWidth:180
          },
          {
            title:'处理人',
            key:'operaterName',
            align:'center',
            minWidth:180
          },
          {
            title: '操作',
            align: 'center',
            fixed: 'right',
            width:80,
            minWidth: 120,
            render: (h, p) => {
              return h('div', [
                h('Tooltip',
                  {props: {placement: 'top', content: '反馈处理',}},
                  [
                    h('Button', {
                      props: {type: 'text', icon: 'md-create', shape: 'circle', size: 'large', ghost: true},
                      style: {margin: '0 8px 0 0', fontSize: '24px', color: '#2db7f5'},
                      on: {
                        click: () => {
                          this.userMess = p.row
                          this.compName = chang
                        }
                      }
                    })
                  ]
                )
              ])
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
      getPagerList(){
        this.$http.post('/api/complainrecord/pager',this.param).then(res=>{
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
