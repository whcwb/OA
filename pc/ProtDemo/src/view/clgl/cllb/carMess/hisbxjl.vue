<template>
  <div>
    <Modal
      v-model="showModalT"
      height="600"
      width="85%"
      :closable='false'
      :mask-closable="false"
      :footer-hide="true">
      <div slot="header">
        <div style="text-align: center;font-size: 22px;font-weight: 700;">
          保险记录
        </div>
        <div style="position: absolute;top:5px;right: 10px;">
          <Button type="warning" @click="close">关闭</Button>
        </div>
      </div>
      <div style="max-height: 550px;overflow: auto;width: 100%">
        <Table :height="480" stripe size="small"
               :columns="tableColumns" :data="pageData"></Table>
        <!--<his-c></his-c>-->
      </div>

    </Modal>
  </div>
</template>

<script>
  export default {
    name: "dzda",
    components:{},
    data(){
      return{
        showModalT:true,
        v: this,
        pagerUrl: '/api/carbx/clbxlb',
        tableColumns: [
          {title: '序号', type: 'index',width:60},
          {title:'起保时间',key:'bxrq',minWidth:120},
          {title:'终保时间',key:'bxz',minWidth:120},
          {title:'投保公司',key:'tbgs',minWidth:120},
          {title:'联系人',key:'bxlxr',minWidth:120},
          {title:'联系电话',key:'bxdh',minWidth:120},
          {title:'保单位置',key:'bdWz',minWidth:120},
          {title:'年审次数',key:'bdCount',minWidth:120},
          {title:'操作人',key:'jbr',minWidth:120},
          {title:'操作人电话',key:'jbrDn',minWidth:120},
          {title:'备注',key:'bz',minWidth:120},

        ],
        pageData: [],
        param: {
          clId: '',
        },
      }
    },
    created(){
      this.param.clId = this.$parent.carData.id

      this.getPager()
    },
    methods:{
      getPager(){
        this.$http.post('/api/carbx/clbxlb',{clId:this.$parent.carData.id}).then(res=>{
          if(res.code == 200 && res.result){
            this.pageData = res.result
          }else {
            this.pageData = []
          }
        }).catch(err=>{})
      },
      close(){
        this.$parent.componentName = ''
      },
    }
  }
</script>

<style scoped>

</style>
