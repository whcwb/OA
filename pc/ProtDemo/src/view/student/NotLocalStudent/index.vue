<template>
  <div class="box_col">
    <pager-tit title="其他学员"></pager-tit>
    <Time :time="time1" />
    <Form :label-width="20">
      <Row type="flex" justify="end">
        <Col span="3">
        <FormItem>
          <Input type="text" placeholder="证件号码" v-model="param.idCardNoLike"/>
        </FormItem>
        </Col>
        <Col span="3">
        <FormItem>
          <Input type="text" placeholder="学员姓名" v-model="param.nameLike"/>
        </FormItem>
        </Col>
        <Col span="2">
        <FormItem>
          <Button type="primary"  @click="v.util.getPageData(v)">
            <Icon type="md-search"></Icon>
          </Button>
        </FormItem>
        </Col>
      </Row>
    </Form>
    <div class="box_col_auto">
        <!--:loading="SpinShow"-->
      <Table
        :height="AF.getPageHeight()-340"
        size="large"
        stripe
        :columns="tabTit"
        :data="pageData"></Table>
    </div>
    <div style="text-align: right">
      <Page :total=param.total
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
  </div>
</template>

<script>
  import {getRelativeTime} from '@/libs/tools'

  export default {
    name: "index",
    components: {},
    data() {
      return {
        time1: (new Date()).getTime() - 60 * 3 * 1000,
        v:this,
        pagerUrl:this.apis.FBXXY.PAGER,
        param: {
          nameLike: '',//姓名
          idCardNoLike:'',
          code: '991',
          zt:'00',
          //分页数据
          total:1,//总数量
          pageNum: 1,//当前页码
          pageSize:8//每页显示数
        },
        jxList:[],
        //table数据查询结果
        pageData: [],
        dictList:{
          carType:{
            code:'ZDCLK0040',
            data:[]
          },
          bmd:{
            code:'',
            data:[]
          }
        },
        tabTit: [
          { type: 'index', width: 60, align: 'center' },
          {title: '姓名',key: 'xm', align: 'center', width: 120},
          {title: '证件号码',key: 'sfzmhm',align: 'center', width:200},
          {title: '导入时间',align: 'center', key: 'cjsj', render :(h, params) =>{
            return h('div', params.row.cjsj.substring(0,10)
            );
          }},
          {title: '导入科目',align: 'center',key: 'kskm', render:(h, p)=>{
            return h('div',  '科目'+p.row.kskm);
          }},
          {
            title: '操作',
            align: 'center',
            minWidth: 90,
            key: 'cz',
            fixed: 'right',
            render: (h, p) => {
              return h('div', [
                h('Tooltip',
                  {props: {placement: 'top', content: '确认',}},
                  [
                    h('Button', {
                      props: {
                        type: 'primary',
                        size: 'small',
                        icon: 'md-checkmark'
                      },
                      style: {
                        marginRight: '10px'
                      },
                      on: {
                        click: () => {
                          this.swal({
                            title:'確定删除学员？',
                            showCancelButton: true,
                            confirmButtonText: '确定',
                            cancelButtonText:'取消'
                          }).then((val)=>{
                              if(val){
                                //操作
                              }
                          });
                        }
                      }
                    })
                  ]
                )
              ])
            }
          }
        ],
      }
    },
    created() {
      this.getDictList();
      // this.getBmdList();
      this.util.initTable(this);
      this.getJTjx()
    },
    methods: {
      //获取当前用户可操作的报名点
      getBmdList() {
        this.$http.get(this.apis.FRAMEWORK.getCurrentOrgTree,{timers:new Date().getTime()}).then((res) => {
          if (res.code === 200) {
            /*function tree(arr) {
              if(arr[0].value.length == 6){
                return arr[0].children
              }else if(arr[0].value.length == 3) {
                return arr[0].children[0].children
              }
            }
            this.dictList.bmd.data = tree(res.result)*/
            if(res.result[0].value.length==3){
              this.dictList.bmd.data = res.result[0].children[0].children;
            }else if(res.result[0].value.length==6){
              this.dictList.bmd.data = res.result[0].children;
            }else if(res.result[0].value.length==9){
              this.dictList.bmd.data = res.result
            }
          }
        }).catch((error) => {
        })
      },
      getJTjx(){
        this.jxList = this.dictUtil.getByCode(this,'ZDCLK1019');
        console.log(this.jx);
      },
      getDictList() {
        this.dictList.carType.data = this.dictUtil.getByCode(this, this.dictList.carType.code);
      },
      pageChange(n) {
        this.util.pageChange(this, n);
      },
      pageSizeChange(n){
        this.util.pageSizeChange(this, n);
      },
      auditConfirm(row, km, zt){
        this.$http.post(this.apis.TRAINEE.UPDATETRAINTYPE, {id:row.id, subjects:km, type:zt}).then((res) => {
          let msg = res.message;
          if (res.code === 200) {
            this.$Message.success(msg);

            this.util.getPageData(this);
          }else{
            this.$Message.error(msg);
          }
        }).catch((error) => {
          this.$Message.error(this.apis.NETWORK_ERR_STR);
        });
      }
    }
  }
</script>

<style scoped>

</style>
