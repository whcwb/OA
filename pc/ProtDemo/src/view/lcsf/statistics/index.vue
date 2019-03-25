<template>
  <div class="boxbackborder box_col">
    <pager-tit title="练车统计"></pager-tit>
    <search-bar :parent="v" :show-create-button="false"></search-bar>
    <table-area :parent="v" :pager="false"></table-area>
    <component :is="componentName"></component>
  </div>
</template>

<script>
  export default {
    name: 'char',
    components: {
    },
    data() {
      return {
        v:this,
        pagerUrl:this.apis.lcjl.statistics,
          pager:false,
          dateRange: '',
        choosedItem:null,
        componentName: '',
          searchParams:[
              {title:'练车时间',key:'cjsjIn',searchType:'daterange'},
          ],
        tableColumns:[
          {title:'序号',type:'index'},
          {title:'学员数量',key:'sum_xysl'},
          {title:'总时长',key:'sum_sc',append:'分钟'},
          {title:'总费用',key:'sum_lcfy',append:'元'},
        ],
        pageData: [],
        param: {
            clId:'',
            cjsjIn:'',
            total:0,
          zhLike:'',
          pageNum: 1,
          pageSize: 8
        },
      }
    },
    created() {
        this.param.clId = '519190203110260736'
      this.util.initTable(this);
    },
    methods: {
    }
  }
</script>
