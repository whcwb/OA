<style lang="less">
</style>
<template>
  <div class="box_col">
    <pager-tit title="收费项管理" iconname="ios-football"></pager-tit>
    <div class="box_row colCenter rowRight pageFindSty">

      <div style="width: 230px" >
        <Cascader @on-change="CasChange" v-model="bmdT"
                  change-on-select :data="CascaderList"
                  placeholder="报名点"
                  trigger="hover"
                  filterable clearable></Cascader>
      </div>

      <div>
        <Input v-model="param.chargeNameLike"
               clearable
               @on-enter="getPagerList(0)"
               placeholder="请输入费用名称" style="width: 220px" />
      </div>


      <Button type="primary" @click="getPagerList(0)">
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
          <card-item :mess="item" @dele="Dele" @change="change"></card-item>
        </div>
      </div>
    </div>
    <div style="text-align: right;border-top: #f2f2f2 3px solid;padding-top: 16px">
      <Page :total=total
            :current=param.pageNum
            :page-size=param.pageSize
            :page-size-opts=[8,10,20,30,40,50]
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
      cardItem,additem
    },
    data(){
      return{
        compName:'',
        listMess:[],
        total:0,//总数量
        param: {
          mc:'',
          chargeNameLike:'',
          inOutType: '00',
          //分页数据
          pageNum: 1,//当前页码
          pageSize:9//每页显示数
        },
        changeMess:{},
        bmdT:[],
        CascaderList:[]
      }
    },
    created(){
      this.getPagerList()
      this.getBmdList()
    },
    methods:{
      getBmdList() {
        this.$http.get(this.apis.FRAMEWORK.getCurrentOrgTree,{timers:new Date().getTime()}).then((res) => {
          if (res.code === 200) {
            /*if(res.result[0].value.length=='6'){
              this.CascaderList = res.result[0].children;
            }else if(res.result[0].value.length=='3'){
              this.CascaderList = res.result[0].children[0].children;
            }*/
            if(res.result[0].value.length==3){
              this.CascaderList = res.result[0].children[0].children;
            }else if(res.result[0].value.length==6){
              this.CascaderList = res.result[0].children;
            }else if(res.result[0].value.length==9){
              this.CascaderList = res.result
            }
            // this.CascaderList = res.result[0].children;
          }
        }).catch((error) => {
        })
      },
      CasChange(val,lab){
        if(lab.length == 2){
          this.param.mc = lab[0].label+'/'+lab[1].label
          this.getPagerList(0)
        }else if(lab.length == 0){
          this.param.mc = ''
          this.getPagerList(0)
        }else if(lab.length == 1){
          this.param.mc = '/'+lab[0].label;
          this.getPagerList(0);
        }
      },
      getPagerList(val){
        if(val && val == 0){
          this.param.pageNum = 1
        }
        var v = this
        this.$http.post(this.apis.SFX.PAGER,this.param).then((res)=>{
          if(res.code==200){
            v.listMess = res.page.list
            v.total = res.page.total
          }
        })
      },
      Dele(id){
        this.AF.Dele(this,this.apis.SFX.DELE,id,()=>{
          this.getPagerList()
        })
      },
      change(mess){
        this.changeMess=mess
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
