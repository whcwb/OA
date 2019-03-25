<template>
  <div class="box_col">
    <pager-tit title="培训管理"></pager-tit>
    <Time :time="time1" />
    <Form :label-width="20">
      <Row type="flex" justify="end">
        <Col span="3">
        <FormItem>
          <Cascader trigger="hover" v-model="param.bmd" :data="dictList.bmd.data"  placeholder="报名点选择"  filterable></Cascader>
        </FormItem>
        </Col>
        <Col span="3">
          <FormItem >
            <Select v-model="param.jgmcLike" placeholder="请选择驾校">
              <Option v-for="(item,index) in jxList" :value="item.val" :key="index">{{ item.val }}</Option>
            </Select>
          </FormItem>
        </Col>
        <Col span="3">
        <FormItem>
          <Select v-model="param.carType" placeholder="车型选择" filterable clearable>
            <Option v-for="item in dictList.carType.data" :value="item.key" :key="item.key">{{ item.val }}</Option>
          </Select>
        </FormItem>
        </Col>
        <Col span="3">
        <FormItem>
          <DatePicker v-model="param.bmTime" split-panels type="daterange" placement="bottom-end" placeholder="报名日期" style="width: 100% ;"></DatePicker>
        </FormItem>
        </Col>
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
        pagerUrl:this.apis.TRAINEE.PAGER,
        param: {
          jgmcLike:'',
          nameLike: '',//姓名
          idCardNoLike:'',
          bmd:[],//报名点代码
          carType:'',//车型。取字典：ZDCLK0040
          bmTime:'',//报名日期范围值
          //分页数据
          orderBy:'registrationTime desc',
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
          {title: '姓名',key: 'name', align: 'center', width: 120},
          {title: '证件号码',key: 'idCardNo',align: 'center', width:200},
          {title: '报名点', key: 'jgmc',align: 'center', render :(h, params) =>{
            let jgmcArray = params.row.jgmc.split("/");
            let res = "";
            if(jgmcArray.length == 2){
              res = params.row.jgmc
            }else if(jgmcArray.length == 3){
              res = jgmcArray[1]+'/'+jgmcArray[2]
            }
            return h('div', res);
          }},
          {title: '报名时间',align: 'center', key: 'registrationTime', render :(h, params) =>{
            return h('div', params.row.registrationTime.substring(0,10)
            //   [
            //   h('Tooltip',
            //     {props: {placement: 'top', content: params.row.registrationTime,}},
            //     [
            //       h('Time', {
            //         props: {
            //           time: new Date(params.row.registrationTime.replace(/-/g,   "/"))
            //         }
            //       })
            //     ]
            //   )
            // ]
            );
          }},
          {title: '车型',key: 'carType',width: 100,align: 'center',
            filters: [{label: 'A1', value:'A1'},
              {label: 'A2', value:'A2'},
              {label: 'A3', value:'A3'},
              {label: 'B2', value:'B2'},
              {label: 'C1', value:'C1'},
              {label: 'C2', value:'C2'}],
            filterRemote: (value, p, v) => {
              this.param.carType = value;
              this.util.getPageData(this);
              return true;
            },
            render: (h, p) => {
            let color = 'volcano';
            let carModel = p.row.carType;
            let carTypeStr = carModel.substring(0, 1);
            if (carTypeStr == 'C') {
              //小车
              color = 'green';
            }
            //大车
            return h('Tag', {
              props: {
                color: color
              },
              style: {
                fontSize: '15px',
              }
            }, carModel);
          }},
          {title: '科一状态',align: 'center',
            filters:
              [{label: '合格', value:'00'},
                {label: '待上传', value:'20'}
              ],

            filterRemote: (value, p, v) => {
              this.param.firSubTrainStatus = value;
              this.util.getPageData(this);
              return true;
            },
            render:(h,p)=>{
            let firSubTrainStatus = p.row.firSubTrainStatus;
            let color = 'success';
            let carModel = p.row.carType;
            let carTypeStr = carModel.substring(0, 1);
            if (carTypeStr == 'C') {
              //小车暂时不需要操作审核状态
              firSubTrainStatus = '合格';
            }else{
              //只有大车需要处理审核状态
              if (firSubTrainStatus == ''){
                color = 'default';
                firSubTrainStatus = '待上传';
              }else if (firSubTrainStatus == '10'){
                color = 'error';
                firSubTrainStatus = '不合格';
              }else{
                firSubTrainStatus = '合格';
              }
            }

            if (firSubTrainStatus == '合格'){
              return h('Tag', {
                props: {
                  color: color
                },
                style: {
                  fontSize: '15px',
                }
              }, '合格');
            }else{
              return h('Poptip',
                {
                  props: {
                    confirm: true,
                    title:"培训状态",
                    'ok-text':"合格",
                    'cancel-text':"不合格"
                  },
                  on:{
                    'on-ok':()=>{
                      this.auditConfirm(p.row, '1', '00');
                    },
                    'on-cancel':()=>{
                      this.auditConfirm(p.row, '1', '10');
                    }
                  }
                },
                [
                  h('Button', {
                    props: {
                      type: color,
                      size:'small'
                    }
                  }, firSubTrainStatus)
                ]
              );
            }
          }},
          {title: '科二教练员',align: 'center',key: 'secondSubjectCoach', render:(h, p)=>{
            let msg = p.row.secondSubjectCoach;
            if (msg == ''){
              msg = '未分配';
            }

            return h('div', msg);
          }},
          {title: '科二状态',align: 'center',
            filters:
              [{label: '合格', value:'00'},
                {label: '待上传', value:'20'}
              ],

            filterRemote: (value, p, v) => {
              this.param.secSubTrainStatus = value;
              this.util.getPageData(this);
              return true;
            },
            render:(h,p)=>{
            let trainStatus = p.row.secSubTrainStatus;
            let color = 'success';
            let carModel = p.row.carType;
            let carTypeStr = carModel.substring(0, 1);
            if (carTypeStr == 'C') {
              //小车暂时不需要操作审核状态
              trainStatus = '合格';
            }else{
              //只有大车需要处理审核状态
              if (trainStatus == ''){
                color = 'default';
                trainStatus = '待上传';
              }else if (trainStatus == '10'){
                color = 'error';
                trainStatus = '不合格';
              }else{
                trainStatus = '合格';
              }
            }

            if (trainStatus == '合格'){
              return h('Tag', {
                props: {
                  color: color
                },
                style: {
                  fontSize: '15px',
                }
              }, '合格');
            }else{
              return h('Poptip',
                {
                  props: {
                    confirm: true,
                    title:"培训状态",
                    'ok-text':"合格",
                    'cancel-text':"不合格"
                  },
                  on:{
                    'on-ok':()=>{
                      this.auditConfirm(p.row, '2', '00');
                    },
                    'on-cancel':()=>{
                      this.auditConfirm(p.row, '2', '10');
                    }
                  }
                },
                [
                  h('Button', {
                    props: {
                      type: color,
                      size:'small'
                    }
                  }, trainStatus)
                ]
              );
            }
          }},
          {title: '科三教练员',align: 'center',key: 'thirdSubjectCoach', render:(h, p)=>{
            let msg = p.row.thirdSubjectCoach;
            if (msg == ''){
              msg = '未分配';
            }

            return h('div', msg);
          }},
          {title: '科三状态',align: 'center',
            filters: [{label: '合格', value:'00'},
              {label: '待上传', value:'20'},
              ],
            filterRemote: (value, p, v) => {
              this.param.thirdSubTrainStatus = value;
              this.util.getPageData(this);
              return true;
            },
            render:(h,p)=>{
            let trainStatus = p.row.thirdSubTrainStatus;
            let color = 'success';
            let carModel = p.row.carType;
            let carTypeStr = carModel.substring(0, 1);
            if (carTypeStr == 'C') {
              //小车暂时不需要操作审核状态
              trainStatus = '合格';
            }else{
              //只有大车需要处理审核状态
              if (trainStatus == ''){
                color = 'default';
                trainStatus = '待上传';
              }else if (trainStatus == '10'){
                color = 'error';
                trainStatus = '不合格';
              }else{
                trainStatus = '合格';
              }
            }

            if (trainStatus == '合格'){
              return h('Tag', {
                props: {
                  color: color
                },
                style: {
                  fontSize: '15px',
                }
              }, '合格');
            }else{
              return h('Poptip',
                {
                  props: {
                    confirm: true,
                    title:"培训状态",
                    'ok-text':"合格",
                    'cancel-text':"不合格"
                  },
                  on:{
                    'on-ok':()=>{
                      this.auditConfirm(p.row, '3', '00');
                    },
                    'on-cancel':()=>{
                      this.auditConfirm(p.row, '3', '10');
                    }
                  }
                },
                [
                  h('Button', {
                    props: {
                      type: color,
                      size:'small'
                    }
                  }, trainStatus)
                ]
              );
            }
          }}
        ],
      }
    },
    created() {
      this.getDictList();
      this.getBmdList();
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
