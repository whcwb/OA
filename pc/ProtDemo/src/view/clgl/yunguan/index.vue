<template>
  <div class="boxbackborder box_col">
    <Row>
      <Col span="12">
        <pager-tit title="运管信息"></pager-tit>
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
  import chanquanHistory from '../cllb/chanquanHistory'

  export default {
    name: 'char',
    components: {
      formData, confirm,
      chanquanHistory
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
          {title: '车牌号', key: 'cph', minWidth: 120, searchKey: 'cphLike'},
          {title: '运输证号', minWidth: 120, key: 'ygYszh'},
          {title: '登记日期', key: 'yyCdrq', minWidth: 120, type: 'date'},
          {
            title: '是否安装GPS', key: 'ygGpsType', minWidth: 130, dict: 'yn',
            render: (h, p) => {
              let a = this.dictUtil.getValByCode(this, 'yn',p.row.ygGpsType)
              return h('div',a)
            }
          },
          {title: '运营状态', key: 'ygYyType', minWidth: 120, dict: 'ZDCLK1033',
            render: (h, p) => {
              let a = this.dictUtil.getValByCode(this, 'ZDCLK1033',p.row.ygYyType)
              return h('div',a)
            }
          },
          {title: '14年上线', key: 'ygYsnSx', minWidth: 120, dict: 'yn',
            render: (h, p) => {
              let a = this.dictUtil.getValByCode(this, 'yn',p.row.ygYsnSx)
              return h('div',a)
            }
          },
          {title: '更新', key: 'ygGx', minWidth: 120,},
          {title: '卡机安装状态', key: 'ygKjType', minWidth: 130, dict: 'ZDCLK1035'},
          {title: '卡机安装时间', key: 'ygKjAzsj', type: 'date', minWidth: 120,},
          {title: '明涛成功新证号', key: 'ygNewCode', minWidth: 120,},
          {title: '新卡机', key: 'ygNewKj', minWidth: 120, dict: 'yn',
            render: (h, p) => {
              let a = this.dictUtil.getValByCode(this, 'yn',p.row.ygNewKj)
              return h('div',a)
            }
          },
          {
            title: '操作', fixed: 'right', width: 80, render: (h, p) => {
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
