<template>
  <div class="boxbackborder box_col">
    <Row>
      <Col span="12">
        <pager-tit title="保单信息"></pager-tit>
      </Col>
      <Col span="12">
        <Row type="flex" justify="end">
          <Col span="24" align="right">
            <search-bar :parent="v" :showCreateButton="false"></search-bar>
          </Col>
        </Row>
      </Col>
    </Row>
    <table-area :parent="v"></table-area>
    <component :is="componentName"></component>
  </div>
</template>

<script>
  import files from './files.vue'
  import create from './create.vue'
  import createSy from './createSy.vue'
  import formData from './formModal.vue'

  export default {
    name: 'char',
    components: {
      files, formData, create,createSy
    },
    data() {
      return {
        v: this,
        apiRoot: this.apis.CAR,
        choosedItem: null,
        componentName: '',
        pageTotal: 0,
        tableColumns: [
          {title: "#", fixed: 'left', width: 60, type: 'index'},
          {title: '车牌号', fixed: 'left',key: 'cph', minWidth: 120, searchKey: 'cphLike'},
          {title: '档案号', fixed: 'left',key: 'dah', minWidth: 120, searchKey: 'cphLike'},
          {title: '交强险', align: 'center',children: [
              {title: '起保时间', key: 'bxBxrq', minWidth: 120},
              {title: '终保时间', key: 'bxBxz', minWidth: 120},
              {title: '投保公司', key: 'bxTbgs', minWidth: 120},
              {title: '联系人', key: 'bxlxr', minWidth: 120},
            ]},
          {title: '商业险', align: 'center',children: [
              {title: '起保时间', key: 'sbQbsj', minWidth: 120},
              {title: '终保时间', key: 'sbZbsj', minWidth: 120},
              {title: '投保公司', key: 'sbTbgs', minWidth: 120},
              {title: '联系人', key: 'sbBxdh', minWidth: 120},
            ]},
          // {title: '保单位置', key: 'bxBdWz', minWidth: 120},
          // {title: '年审次数', key: 'bxBdCount', minWidth: 120},
          {title: '操作人', key: 'bxJbr', minWidth: 120},
          {title: '操作人电话', key: 'bxJbrDn', minWidth: 120},
          {title: '备注', key: 'bz', minWidth: 120},
          {
            title: '操作', width: 120, fixed: 'right', render: (h, p) => {
              let buttons = [];
              buttons.push(this.util.buildeditButton(this, h, p));
              buttons.push(this.util.buildButton(this, h, 'info', 'md-add', '交强险续保', () => {
                this.choosedItem = p.row;
                this.componentName = 'create'
              }));
              buttons.push(this.util.buildButton(this, h, 'warning', 'md-add', '商业险续保', () => {
                this.choosedItem = p.row;
                this.componentName = 'createSy'
              }));
              return h('div', buttons);
            }
          },
        ],
        pageData: [],
        param: {
          total: 0,
          zhLike: '',
          pageNum: 1,
          pageSize: 8
        },
      }
    },
    created() {
      this.util.initTable(this);
    },
    methods: {
      getPager(){
        this.util.initTable(this);
      }
    }
  }
</script>
