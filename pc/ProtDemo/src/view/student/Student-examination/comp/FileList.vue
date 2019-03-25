<template>
    <div>
      <Modal v-model="showModal" width="60%"
             title="导入记录"
             :closable='false' :mask-closable="false" @on-visible-change="changeWindow">
        <Table :columns="columns" :data="pageData" ></Table>
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
      </Modal>
    </div>
</template>

<script>
  import http from '@/axios/index';

  export default {
        name: "FileList",
      data () {
        return {
          total: 0,
          showModal: true,
          pageData:[],
          param:{
            lx: '20',
            pageNum: 1,
            pageSize: 8
          },
          columns: [
            {
              type: 'index2', align: 'center', width: 80, fixed: 'left',
              render: (h, p) => {
                return h('span', p.index + (this.param.pageNum - 1) * this.param.pageSize + 1);
              }
            },
            {
              title: '上传时间',
              key: 'cjsj',
              minWidth: 180
            },
            {
              title: '上传人',
              key: 'cjr',
              minWidth: 180
            },
            {
              title: '操作',
              align:'center',
              minWidth: 100,
              render: (h,p)=>{
                  return h('div', [
                              h('Button',{
                                props:{
                                  type: 'primary',
                                  size: 'small'
                                },
                                on:{
                                  click: ()=> {
                                    window.open(http.url + this.apis.TRAINETEST.EXPORT_URL + p.row.sucKey, '_blank');
                                  }
                                }
                              },"下载")
                  ])
              }
            }
          ]
        }
      },
      created(){
        this.getPagerList();
      },
      methods:{
        pageSizeChange(n){
          this.param.pageSize = n;
          this.getPagerList();
        },
        pageChange(n){
          this.param.pageNum = n;
          this.getPagerList();
        },
        getPagerList(){
          this.$http.post('/api/traineetestinfo/getFileId',this.param).then((res)=>{
            if(res.code === 200){
              this.pageData = res.page.list;
              this.total = res.page.total;
            }
          })
        },
        close(){
          this.$parent.compName= '';
        },
        changeWindow(flag){
          if (!flag){
            setTimeout(()=>{
              this.close();
              this.util.getPageData(this.$parent);
            },500);
          }
        }
      }
    }
</script>

<style scoped>

</style>
