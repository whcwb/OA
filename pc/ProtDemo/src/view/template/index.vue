<template>
  <div class="boxbackborder box_col">
    <!--<pager-tit title="用户管理"></pager-tit>-->
    <search-bar :parent="v"></search-bar>
    <table-area :parent="v"></table-area>
    <component :is="componentName"></component>
  </div>
</template>

<script>
  import formData from './formModal.vue'

  export default {
    name: 'char',
    components: {
        formData,
    },
    data() {
      return {
        v:this,
        apiRoot:this.apis.USER,
        choosedItem:null,
        componentName: '',
        tableColumns:[
          {title:'序号',type:'index'},
          {title:'账号',key:'zh',searchKey:'zhLike'},
          {title:'性别',key:'xb',dict:'ZDCLK0017'},
          {title:'职务',key:'zw'},
          {title:'操作',render:(h,p)=>{
              let buttons = [];
              buttons.push(this.util.buildeditButton(this,h,p));
              buttons.push(this.util.buildDeleteButton(this,h,p.row.yhid));
              return h('div',buttons);
            }
            },

        ],
        pageData: [],
        param: {
            total:0,
          zhLike:'',
          pageNum: 1,
          pageSize: 8
        },
      }
    },
    created() {
      this.util.initTable(this);
    },
    methods: {
    }
  }
</script>
