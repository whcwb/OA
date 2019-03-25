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
          所有人变更记录
        </div>
        <div style="position: absolute;top:5px;right: 10px;">
          <Button type="warning" @click="close">关闭</Button>
        </div>
      </div>
      <div style="max-height: 550px;overflow: auto;width: 100%">
        <table-area :parent="v" :TabHeight="480"></table-area>
      </div>

    </Modal>
  </div>
</template>

<script>
  export default {
    name:"syrbg",
    data() {
      return {
        v: this,
        pagerUrl: '/api/carls/pager',
        tableColumns: [
          {title: '序号', type: 'index',width:60},
          {title: '车辆所有人变更时间  ', key: 'carSyrBgsj',minWidth:100},
          {title: '车辆所有人变更操作人', key: 'carSyrBgczr',minWidth:100},
          {title: '机动车所有人', key: 'carSyr',minWidth:90},
          {title: '所有人证件号码', key: 'carSryCode',minWidth:90},
          {title: '身份证件类型', key: 'carSryZjType',minWidth:110},
          {title: '所属区域', key: 'carQy',minWidth:100,dictUtil:'ZDCLK1009'},

          {title: '车辆产权人', key: 'clCqr',minWidth:100},

          {title: '产权人电话', key: 'clCqrDn',minWidth:90},
          {title: '车辆产权人证件号', key: 'clCqrCode',minWidth:90},
          {title: '车辆产权变更备注', key: 'clCqrBz',minWidth:110},
          {title: '运管运输证号', key: 'ygYszh',minWidth:100},
          // {title: '变更后备注', key: 'bz',minWidth:100},
          // {title: '档案位置', key: 'daqz',minWidth:100},
        ],
        pageData: [],
        param: {
          dah:'',
          pageNum: 1,
          pageSize: 8
        },
        showModalT:true,
        // parentItem: {}
      }
    },
    props:{
      id:{
        type:String,
        default:''
      }
    },
    computed:{
      parentItem:function(){
        return this.$store.state.app.clProps
      }
    },
    created() {
      this.param.dah = this.$parent.carData.dah ;
      this.util.initTable(this);
    },
    methods:{
      close(){
        this.$parent.componentName = ''
      },
      getsyrlist(){
        this.$http.post('/api/carls/pager',this.param.dah).then((res)=>{
          console.log(res);
          if(res.code == 200){
            this.pageData = res.page.list
          }
        })
      }
    },
    mounted() {
      this.getsyrlist()
    }
  }
</script>
