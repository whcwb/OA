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
        <Input v-model="param.coachNameLike"
               clearable
               @on-enter="getPagerList(0)"
               placeholder="请输入教练员姓名" style="width: 200px"/>
      </div>

      <Button type="primary" @click="getPagerList(val)">
        <Icon type="md-search"></Icon>
        <!--查询-->
      </Button>
      <!--<Button type="primary" @click="compName = 'additem';mess = {}">-->
        <!--<Icon type="md-add"></Icon>-->

      <!--</Button>-->
    </div>

      <Table :height="AF.getPageHeight()-320" stripe size="small"
             :columns="tabTit" :data="listMess"></Table>




    <!--<div class="box_col_auto">-->
      <!--<no-data v-if="listMess.length == 0"></no-data>-->
      <!--<div class="box_row_list" v-else>-->
        <!--<card-item  v-for="(item,index) in listMess"-->
                    <!--:mess="item" style="margin:10px 4px"-->
                    <!--@dele="Dele" @change="change"-->
        <!--&gt;</card-item>-->
      <!--</div>-->
    <!--</div>-->
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
        tabTit:[
          {
            type: 'index2', align: 'center', minWidth: 80, fixed: 'left',
            render: (h, params) => {
              return h('span', params.index + (this.param.pageNum - 1) * this.param.pageSize + 1);
            }
          },
          {
            title:'姓名',
            key:'coachName',
            align:'center',
            minWidth:80,
            fixed: 'left'
          },
          {
            title:'性别',
            key:'gender',
            align:'center',
            minWidth:80,
            render:(h,p)=>{
              let a = '男'
              if(p.row.gender=='20'){
                a='女'
              }
              return h('div',a)
            }
          },
          {
            title:'电话号码',
            key:'phone',
            align:'center',
            minWidth:120,
          },
          {
            title:'队号',
            key:'jgmc',
            align:'center',
            minWidth:120,
          },
          {
            title:'培训车型',
            key:'drivingType',
            align:'center',
            minWidth:100
          },
          {
            title:'培训科目',
            key:'coachSub',
            align:'center',
            minWidth:120,
            render:(h,p)=>{
              let arr = p.row.coachSub.split(',')
              let Farr =[]
              arr.forEach((item,index)=>{
                if(item == '02'){
                  Farr[index] = '科目二'
                }else if(item=='03'){
                  Farr[index] = '科目三'
                }
              })

              return h('div',{
                style:{
                  fontWeight:600
                }
              },Farr.join(','))
            }
          },
          {
            title:'教练员评分',
            key:'coachRate',
            align:'center',
            minWidth:180,
            render:(h,p)=>{
              let a = parseInt(p.row.coachRate)
              return h('Rate',{
               props:{
                 disabled:true,
                 icon:"ios-heart",
                 count:5,
                 value:a
               }
              })
            }
          },
          {
            title:'驾龄',
            key:'drivingYears',
            align:'center',
            minWidth:60,
            render:(h,p)=>{
              return h('div',p.row.drivingYears+'年')
            }
          },
          {
            title:'学员人数',
            key:'traineeNum',
            align:'center',
            minWidth:60,
            render:(h,p)=>{
              return h('div',p.row.traineeNum+'人')
            }
          },
          {
            title:'所属区域',
            key:'area',
            minWidth:100,
            align:'center',
            render:(h,p)=>{
              let a = this.dictUtil.getValByCode(this, 'ZDCLK1009', p.row.area)
              return h('div',a)
            }
          },
          {
            title:'驾驶证有效期',
            minWidth:200,
            align:'center',
            render:(h,p)=>{
              return h('div',p.row.licenseStartTime+' ~ '+p.row.licenseEndTime)
            }
          },
          {
            title:'身份证有效期',
            minWidth:200,
            align:'center',
            render:(h,p)=>{
              return h('div',p.row.coachStartTime+' ~ '+p.row.coachEndTime)
            }
          },
          {
            title:'操作',
            fixed:'right',
            align:'center',
            minWidth:120,
            render:(h,p)=>{
              return h('div',[
                h('Tooltip',
                  {props: {placement: 'top', content: '信息维护',}},
                  [
                    h('Button', {
                      props: {type: 'warning', icon: 'md-create', size: 'small'},
                      style: {margin: '0 8px 0 0'},
                      on: {
                        click: () => {
                          this.change(p.row)
                        }
                      }
                    })
                  ]
                ),
                h('Tooltip',
                  {props: {placement: 'top', transfer: true, content: '信息删除',}},
                  [
                    h('Button', {
                      props: {type: 'error',size: 'small'},
                      style: {
                        marginRight: '10px'
                      },
                      on: {
                        click: () => {
                          this.Dele(p.row.id)
                        }
                      }
                    },'删')
                  ]
                ),
              ])
            }
          }
        ],
        listMess: [],
        paramLoading:false,
        total:0,//总数量
        param: {
          jgdm:'',
          areaLike:'',
          coachNameLike:'',
          //分页数据
          pageNum: 1,//当前页码
          pageSize:10//每页显示数
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
        // console.log(val);
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
        this.paramLoading=true
        if(val && val == 0){
          this.param.pageNum = 1
        }
        var v = this
        this.$http.post(this.apis.JL.PAGER,this.param).then((res) => {
          if (res.code == 200) {
            // console.log(res);
            v.listMess = res.page.list
            v.total = res.page.total
            v.paramLoading=false
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
