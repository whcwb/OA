<template>
  <div class="boxbackborder box_col">
    <Row>
      <Col span="12">
        <pager-tit title="改气信息"></pager-tit>
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
  import formData from './formModal.vue'
  import confirm from './confirm.vue'

  export default {
    name: 'char',
    components: {
      formData, confirm
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
          {title: '车牌号', key: 'cph', searchKey: 'cphLike', minWidth: 120},
          {title: '是否改气', key: 'gxType', minWidth: 120, dict: 'yn'},
          {title: '改气时间', key: 'gxCdrq', minWidth: 120},
          {title: '改气地点', key: 'gxGasDd', minWidth: 120},
          {title: '联系人', key: 'gxLxr', minWidth: 120},
          {title: '联系电话', key: 'gxLxDn', minWidth: 120},
          {title: '备注', key: 'gxGasBz', minWidth: 120},
          {
            title: '操作', width: 180, render: (h, p) => {
              let buttons = [];
              buttons.push(this.util.buildeditButton(this, h, p));
              // buttons.push(this.util.buildButton(this, h, 'info', 'md-checkmark', '年审确认', () => {
              //     this.choosedItem = p.row;
              //     this.componentName = 'confirm'
              // }));
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
    methods: {}
  }
</script>
