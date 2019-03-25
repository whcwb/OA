<template>
  <div id="basicData-archives" class="box_col">
    <pager-tit title="档案柜管理" iconname="ios-football"></pager-tit>

    <div class="box_row colCenter rowRight pageFindSty">

      <div>
        <Input v-model="param.serialNum" placeholder="请输学员入流水号"
               @on-enter="getPagerList(0)" style="width: 220px"/>
      </div>

      <div>
        <Input v-model="param.archivesCodeLike"
               placeholder="请输入档案柜编号"
               clearable
               @on-enter="getPagerList(0)"
               style="width: 220px"/>
      </div>

      <Button type="primary" @click="getPagerList(0)">
        <Icon type="md-search"></Icon>
        <!--查询-->
      </Button>
      <Button type="primary" @click="compName = 'addmodel'">
        <Icon type="md-add"></Icon>
      </Button>
    </div>
    <div class="box_col_auto">
      <no-data v-if="pagerList.length == 0"></no-data>
      <div class="box_row_list">
        <div class="itenCol" v-for="item in pagerList">
          <car-item :mess="item"
                    @messTab="messTab"
                    @remove="remove"></car-item>
        </div>
      </div>
    </div>
    <div style="text-align: right;border-top: #f2f2f2 3px solid;padding-top: 16px">
      <Page :total=total
            :current=param.pageNum
            :page-size=param.pageSize
            :page-size-opts=[12,24,46,48,50]
            show-total
            show-elevator
            show-sizer
            placement='top'
            @on-page-size-change='pageSizeChange'
            @on-change='pageChange'>
      </Page>
    </div>
    <component :is="compName" :boxNum="boxNum"></component>
  </div>
</template>

<script>
  import carItem from './comp/arcItem'
  import listTab from './comp/listTab'
  import addmodel from './comp/addarc'

  export default {
    name: "index",
    components: {
      carItem,
      listTab,
      addmodel
    },
    data() {
      return {
        compName: '',
        archivesNum: '',
        archivesName: '',

        boxNum: '',

        pagerList: [],
        total: 0,//总数量
        param: {
          serialNum: '',
          archivesCodeLike: '',
          //分页数据
          pageNum: 1,//当前页码
          pageSize: 12//每页显示数
        }
      }
    },
    created() {
      this.getPagerList()
      this.test()
    },
    methods: {
      test(){
        this.$http.post('/api/lcjl/jlTj',{cjsjGte: '2018-12-12'}).then((res)=>{
          // console.log(res);
        })
      },
      getPagerList(val) {
        if (val && val == 0) {
          this.param.pageNum = 1
        }
        this.$http.post(this.apis.DAK.PAGER, this.param).then((res) => {
          // console.log(res);
          this.pagerList = res.page.list
          this.total = res.page.total
        })
      },
      messTab(val) {
        // console.log(val);
        this.boxNum = val
        this.compName = 'listTab'
      },
      remove(val) {
        // console.log(val);
        this.getPagerList()
      },
      pageChange(n) {
        this.param.pageNum = n
        this.getPagerList()
      },
      pageSizeChange(n) {
        this.param.pageSize = n
        this.getPagerList()
      }
    }
  }
</script>

<style lang="less">
  #basicData-archives {
    .itenCol {
      padding: 22px;
      cursor: ;
    }
  }
</style>
