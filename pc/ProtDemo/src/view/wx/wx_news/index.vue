<template>
  <div class="box">
    <pager-tit title="消息管理" iconname="ios-football"></pager-tit>
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
            title:'消息标题',
            align:'center',
            minWidth:120,
            key:'title'
          },
          {
            title:'消息正文',
            align:'center',
            minWidth:180,
            key:'message'
          },
          {
            title:'消息类型',
            key:'type',
            align:'center',
            minWidth:100,
            render: (h, p) => {
              let mes = this.dictUtil.getValByCode(this, 'ZDCLK1026', p.row.type)
              return h('div',mes)
            }
          },
          {
            title:'发送状态',
            key:'status',
            align:'center',
            minWidth:100,
            render: (h, p) => {
              let mes = this.dictUtil.getValByCode(this, 'ZDCLK1027', p.row.status)
              return h('div',[
                h('Tag',{
                  props:{
                    color:p.row.status=='0'? "magenta" :p.row.status=='2' ? 'red':'green'
                  }
                },mes)
              ])
            }
          },
          {
            title:'接收人',
            key:'userName',
            align:'center',
            minWidth:100,
            render: (h, p) => {
              let mes = this.dictUtil.getValByCode(this, 'ZDCLK1028', p.row.userRole)
              return h('div',mes+':'+p.row.userName)
            }
          },
          {
            title:'发送次数',
            key:'sendCount',
            align:'center',
            minWidth:100
          },
          {
            title:'预约发送',
            key:'estimatedSendTime',
            align:'center',
            minWidth:180
          },
          {
            title:'发送成功',
            key:'sendTime',
            align:'center',
            minWidth:180
          },
          {
            title:'备注信息',
            key:'remark',
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
              if(p.row.status=='1'){
                return h('div','')
              }
              return h('div', [
                h('Tooltip',
                  {props: {placement: 'top', content: '消息下发',}},
                  [
                    h('Button', {
                      props: {type: 'text', icon: 'ios-undo', shape: 'circle', size: 'large', ghost: true},
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
        this.$http.post('/api/message/pager',this.param).then(res=>{
          if(res.code == 200){
            this.total = res.page.total
            this.tabData = res.page.list
          }
        }).catch(err=>{

        })
      },
      pageSizeChange(n) {
        this.param.pageSize = n;
        this.getPagerList();
      },
      pageChange(n){
        this.param.pageNum = n;
        this.getPagerList();
      }
    }
  }
</script>

<style scoped>

</style>
