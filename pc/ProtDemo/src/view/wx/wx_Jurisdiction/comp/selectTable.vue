<style lang="less">
  /*@import '../../../../styles/common.less';*/
</style>
<template>
  <div class="boxbackborder">
    <Row style="padding-bottom: 16px;text-align: right">
      <!--<search-items :parent="v" :showCreateButton="true" :showSearchButton="true"></search-items>-->
    </Row>
    <Row style="position: relative;">
      <Table highlight-row :height="460" stripe
             :columns="tableColumns" :data="pageData" @on-selection-change="selectionClick"></Table>
    </Row>
    <div style="text-align: right;padding: 6px 0">
      <Page :total=param.total
            :current=param.pageNum
            :page-size=param.pageSize
            :page-size-opts=[8,10,20,30,40,50]
            show-total
            show-elevator
            show-sizer
            placement='top'
            @on-page-size-change='(e)=>{param.pageSize=e;pageChange()}'
            @on-change='pageChange'>
      </Page>
    </div>
    <component :is="componentName"></component>
  </div>
</template>

<script>
  // import searchItems from '../../components/searchItems'

  export default {
    name: 'roleSelectTable',
    // components: {searchItems},
    props: {
      hasIds: {
        type: Array,
        default: []
      }
    },
    data() {
      return {
        v: this,
        SpinShow: true,
        pagerUrl:'/api/wxmodule/pager',
        componentName: '',
        choosedItem: null,
        checkedIds: [],
        tableColumns: [
          {title: "#", width: 60, type: 'selection'},
          {title: '功能名称', key: 'title', searchKey: 'jsmcLike'},
          {title: '类型', key: 'type', dict: 'ZDCLK1030'},
        ],
        pageData: [],
        param: {
          total: 0,
          pageNum: 1,
          pageSize: 8,
        },
      }
    },
    watch: {
      pageData() {
        this.checkIds();
      }
    },
    created() {
      this.util.initTable(this);
      setTimeout(() => {
        this.initJs();
        this.checkIds();
      }, 100)
    },
    methods: {
      pageChange(event) {
        this.util.pageChange(this, event);
        // setTimeout(() => {
        //   this.checkIds();
        // }, 1000)
      },
      checkIds() {
        for (let r of this.pageData) {
          if (this.checkedIds.indexOf(r.id) != -1) {
            r._checked = true;
          }
        }
      },
      selectionClick(selection) {
        for (let r of this.pageData) {
          let index = this.checkedIds.indexOf(r.id);
          if (index !== -1) {
            this.checkedIds.splice(index, 1);
          }
        }

        for (let k of selection) {
          this.checkedIds.push(k.id);
        }
        this.$emit("arrIds", this.checkedIds);
      },
      initJs() {
        for (let r of this.hasIds) {
          if (this.checkedIds.indexOf(r) === -1) {
            this.checkedIds.push(r);
          }
        }
      }
    }
  }
</script>
