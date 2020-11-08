<template>
  <div class="box_col">
    <pager-tit title="优惠项管理" iconname="ios-football"></pager-tit>
    <div class="box_row colCenter rowRight pageFindSty">
      <div>
        <!--<Icon type="ios-home" size="32"/>-->
        <Input v-model="param.reduceNameLike"
               clearable
               @on-enter="getPagerList(0)"
               placeholder="请输入优惠项名称" style="width: 220px"/>
      </div>

      <Button type="primary" @click="">
        <Icon type="md-search"></Icon>
        <!--查询-->
      </Button>
      <Button type="primary" @click="compName = 'additem';changeMess = {}">
        <Icon type="md-add"></Icon>
      </Button>
    </div>
    <div class="box_col_auto">
      <no-data v-if="listMess.length == 0"></no-data>
      <div class="box_row_list" v-else>
        <div class="itemList" v-for="(item,index) in listMess">
          <card-item :mess="item"  @dele="Dele" @change="change"></card-item>
        </div>
      </div>
    </div>
    <div style="text-align: right;border-top: #f2f2f2 3px solid;padding-top: 16px">
      <Page :total=total
            :current=param.pageNum
            :page-size=param.pageSize
            :page-size-opts=[6,8,10,20,30,40,50]
            show-total
            show-elevator
            show-sizer
            placement='top'
            @on-page-size-change='pageSizeChange'
            @on-change='pageChange'>
      </Page>
    </div>
    <component :is="compName" :changeMess="changeMess"></component>
  </div>
</template>

<script>
  import cardItem from './comp/cardItem'
  import additem from './comp/addItem'

  export default {
    name: "index",
    components: {
      cardItem, additem
    },
    data() {
      return {
        compName: '',
        listMess: [],
        total:0,//总数量
        param: {
          reduceNameLike:'',
          group: '1',
          //分页数据
          pageNum: 1,//当前页码
          pageSize:6//每页显示数
        },
        changeMess:{}
      }
    },
    created() {
      this.getPagerList()
    },
    methods: {
      getPagerList(val){
        if(val && val == 0){
          this.param.pageNum = 1
        }
        var v = this
        this.$http.post(this.apis.TGTHX.PAGER, this.param).then((res) => {
          if (res.code == 200) {
            v.listMess = res.page.list
            v.total = res.page.total
          }
        })
      },
      Dele(id) {
        this.AF.Dele(this,this.apis.TGTHX.DELE,id,()=>{
          this.getPagerList()
        })
      },
      change(mess){
        this.changeMess = mess
        this.compName = 'additem'
      },
      pageChange(n) {
        this.param.pageNum = n
        this.getPagerList()
      },
      pageSizeChange(n){
        this.param.pageSize = n
        this.getPagerList()
      }
    }

  }
</script>

<style scoped>

</style>
