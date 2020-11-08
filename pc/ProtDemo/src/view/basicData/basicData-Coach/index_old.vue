<template>
  <div class="box_col">
    <div class="box_row colCenter pageFindSty">
      <div class="box_row_100">
        <pager-tit title="教练员管理" iconname="ios-football"></pager-tit>
      </div>

      <div style="width: 230px" >
        <Cascader @on-change="CasChange" v-model="bmdT"
                  change-on-select :data="CascaderList"
                  placeholder="报名点"
                  trigger="hover"
                  filterable clearable></Cascader>
      </div>

      <div>
        <Select v-model="param.areaLike" clearable
                @on-change="getPagerList(val)"
                placeholder="请选择区域" style="width: 200px">
          <Option v-for="(item,index) in cityList" :value="item.key" :key="item.key">{{ item.val }}</Option>
        </Select>
      </div>

      <div>
        <!--<Icon type="ios-home" size="32"/>-->
        <Input v-model="param.coachNameLike"
               clearable
               @on-enter="getPagerList(0)"
               placeholder="请输入教练员姓名" style="width: 200px"/>
      </div>

      <Button type="primary" @click="getPagerList(val)">
        <Icon type="md-search"></Icon>
        <!--查询-->
      </Button>
      <Button type="primary" @click="compName = 'additem';mess = {}">
        <Icon type="md-add"></Icon>
      </Button>
    </div>

    <div class="box_col_auto">
      <no-data v-if="listMess.length == 0"></no-data>
      <div class="box_row_list" v-else>
        <card-item  v-for="(item,index) in listMess"
                    :mess="item" style="margin:10px 4px"
                    @dele="Dele" @change="change"
        ></card-item>
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
    <component :is="compName" :mess="mess"></component>
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
    data() {
      return {
        compName: '',
        mess:{},
        cityList: [],//区域
        listMess: [],
        total:0,//总数量
        param: {
          jgdm:'',
          areaLike:'',
          coachNameLike:'',
          //分页数据
          pageNum: 1,//当前页码
          pageSize:9//每页显示数
        },
        bmdT:[],
        CascaderList:[]
      }
    },
    created() {
      this.getPagerList()
      this.getLXDic()
      this.getBmdList()
    },
    methods: {
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
        // if(lab.length == 3){
        //   this.param.mc = lab[1].label+'/'+lab[2].label
        // }else if(lab.length == 0){
        //   this.param.mc = ''
        // }
        if(val.length==3){
          this.param.jgdm =val[val.length-1]
          this.getPagerList()
        }else if(val.length == 0) {
          this.param.jgdm = ''
          this.getPagerList()
        }else if(val.length == 1){
          this.param.jgdm =val[val.length-1]
          this.getPagerList()
        }
      },
      getLXDic() {
        this.cityList = this.dictUtil.getByCode(this, 'ZDCLK1009');
      },
      getPagerList(val) {
        if(val && val == 0){
          this.param.pageNum = 1
        }
        var v = this
        this.$http.post(this.apis.JL.PAGER,this.param).then((res) => {
          if (res.code == 200) {
            v.listMess = res.page.list
            v.total = res.page.total
          }
        })
      },
      Dele(id){
        this.AF.Dele(this,this.apis.JL.DELE,id,()=>{
          this.getPagerList()
        })
      },
      change(mess){
        this.mess = mess
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

<style lang="less">

</style>
