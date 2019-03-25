<template>
  <div>
    <table-area :parent="v" :TabHeight="480"></table-area>
  </div>
</template>

<script>
  export default {
    name: "chanquanHistory",
    data() {
      return {
        v: this,
        pagerUrl: '/api/carcq/pager',
        tableColumns: [
          {title: '序号', type: 'index',width:60},
          {title: '变更类型', key: 'propertyType',minWidth:100,dict:'ZDCLK1043'},
          {title: '变更时间', key: 'cjsj',minWidth:100},
          {title: '变更前产权人姓名', key: 'pastCqr',minWidth:90},
          {title: '变更前产权人电话', key: 'pastCqrDn',minWidth:90},
          {title: '变更前质保金', key: 'pastZb',minWidth:110},
          {title: '变更前金额', key: 'pastCk',minWidth:100},

          {title: '变更前备注', key: 'pastBz',minWidth:100},

          {title: '变更后产权人姓名', key: 'cqr',minWidth:90},
          {title: '变更后产权人电话', key: 'cqrDn',minWidth:90},
          {title: '变更后质保金', key: 'zb',minWidth:110},
          {title: '变更后金额', key: 'ck',minWidth:100},
          {title: '变更后备注', key: 'bz',minWidth:100},
          // {title: '档案位置', key: 'daqz',minWidth:100},

        ],
        pageData: [],
        param: {
          clId: '',
          total: 0,
          zhLike: '',
          pageNum: 1,
          pageSize: 8
        },
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
      this.param.clId = this.id
      this.util.initTable(this);
    },
    mounted() {

    }
  }
</script>
