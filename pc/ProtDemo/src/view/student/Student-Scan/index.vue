<template>
  <div class="box_col">
    <pager-tit title="档案入库"></pager-tit>
      <Row type="flex"  align="bottom" style="padding-bottom: 10px;">
        <Col span="4" style="padding-top: 10px;cursor: pointer">
          <Card dis-hover @click.native="openArchives" style="height: 148px" v-if="result.selectItem == null">
            <div style="text-align:center;padding-top: 20px">
              <Icon type="md-add" size="58" color="#19be6b"/>
              <p style="color:#464c5b;font-size: 16px">请选择入库档案柜</p>
            </div>
          </Card>
          <arc-item @click.native="openArchives" :item="result.selectItem" v-else></arc-item>
        </Col>
        <Col span="3" style="padding-left: 20px;">
          <Card dis-hover class="successCard" style="height: 148px;width: 100%">
            <p slot="title" style="height: 50px;padding-top: 10px;color:white;">
              <Icon type="md-checkmark-circle-outline" style="font-size: 36px;padding-left: 10px"/>
              <span style="font-size: 22px;padding-left: 8px">成功</span>
            </p>
            <div style="text-align: center">
              <count-to :end="result.success" count-class="count-style"/>
            </div>
          </Card>
        </Col>
        <Col span="3" style="padding-left: 20px;">
          <Card class="failCard" dis-hover style="height: 148px;width: 100%;">
            <p slot="title" style="height: 50px;padding-top: 10px;color:white;">
              <Icon type="md-close-circle" style="font-size: 36px;padding-left: 10px" />
              <span style="font-size: 22px;padding-left: 8px">失败</span>
            </p>
            <div style="text-align: center">
              <count-to ref="failedCount" :end="result.error" count-class="count-style"/>
            </div>
          </Card>
        </Col>
        <Col span="4" style="padding-left: 10px">
          <Input type="text" placeholder="流水号" size="large" v-model="param.serialNum" @on-enter="arcImport"/>
        </Col>
        <Col span="2" style="padding-left: 10px">
          <Button type="warning"  @click="arcImport" size="large">
            <Icon class="iconfont icon-saoma" style="font-size: 24px"></Icon>
          </Button>
        </Col>

      </Row>
    <div class="box_col_auto">
        <!--:loading="SpinShow"-->
      <Table
        :height="AF.getPageHeight()-300"
        size="large"
        stripe
        :columns="tabTit"
        :data="pageData"></Table>
    </div>

    <component
      :is="compName">
    </component>
  </div>
</template>

<script>
  import archivesList from './comp/archivesList'
  import arcItem from './comp/comp_s/arcItem'
  import CountTo from '_c/count-to'

  export default {
    name: "index",
    components: {
      archivesList,arcItem,CountTo
    },
    data() {
      return {
        v:this,
        compName:'',
        result:{
          selectItem:null,
          success:0,
          error:0
        },
        pagerUrl:this.apis.TRAINEE.PAGER,
        param: {
          nameLike: '',//姓名
          idCardNo:'',
          serialNum:'',//流水号
          //分页数据
          orderBy:'registrationTime desc',
          total:1,//总数量
          pageNum: 1,//当前页码
          pageSize:8//每页显示数
        },
        //table数据查询结果
        pageData: [],
        tabTit: [
          { type: 'index', width: 60, align: 'center' },
          {title: '姓名',key: 'name', width: 120},
          {title: '证件号码',key: 'idCardNo', width:200},
          {title: '性别',key: 'gender', width:120, render:(h,p)=>{
              return h('div', p.row.gender == '10' ? '男' :'女')
          }},
          {title: '报名点', key: 'jgmc', width:200, render :(h, params) =>{
            let jgmcArray = params.row.jgmc.split("/");
            let res = "";
            if (jgmcArray.length > 1){
              for (let i=1; i<jgmcArray.length; i++){
                res += jgmcArray[i];
                if ((i+1) != jgmcArray.length){
                  res += '-';
                }
              }
            }else{
              res = jgmcArray[0];
            }
            return h('div', res);
          }},
          {title: '流水号',key: 'serialNum', width:180},
          {title: '报名时间', key: 'registrationTime', width:120, render :(h, params) =>{
            return h('div', [
              h('Tooltip',
                {props: {placement: 'top', content: params.row.registrationTime,}},
                [
                  h('Time', {
                    props: {
                      time: new Date(params.row.registrationTime.replace(/-/g,   "/"))
                    }
                  })
                ]
              )
            ]);
          }},
          {title: '车型',key: 'carType',width: 80, render: (h, p) => {
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
          {title: '有效期止',key: 'indateEndTime',width: 160},
          {title: '入库状态',key: 'success',width: 120, render:(h, p)=>{
            let color = 'success';
            let msg = '成功';
            if (p.row.success == '0'){
              color = 'error';
              msg = '失败'
            }

            return h('Tag', {
              props: {
                color: color
              },
              style: {
                fontSize: '15px',
              }
            }, msg);
          }},
          {title: '入库结果',key: 'message'}
        ],
      }
    },
    created() {
    },
    methods: {
      openArchives(){
        this.compName = 'archivesList';
      },
      arcImport(){
        if (this.result.selectItem == null){
            this.swal({
              text:'请先选择存放档案柜！',
              type:'error'
            });

            return;
        }

        if (this.param.serialNum == ''){
          this.swal({
            text:'请先扫描或输入流水号！',
            type:'error'
          });

          return;
        }
        this.$http.post(this.apis.ARCHIVES.IMPORT, {
          serialNum:this.param.serialNum,
          archivesCode:this.result.selectItem.id,
          remark:''
        }).then((res) => {
          let msg = res.message;
          res.result.success = res.code == 200 ? '1' : '0';
          res.result.message = msg;
          for (let item of this.pageData){
            if (res.result.serialNum == item.serialNum){
                this.swal({
                  text:msg,
                  type:'error'
                });

                return;
            }
          }
          this.pageData.push(res.result);
          if (res.result.success == '1'){
            this.result.success++;
            //档案入库成功，则可用量自动加1
            this.result.selectItem.num++;
          }else{
            this.result.error++;
          }
        }).catch((error) => {
          this.swal({
            text:this.apis.NETWORK_ERR_STR,
            type:'error'
          });
        });
      }
    }
  }
</script>


<style lang="less">
  .titleContent{
    font-size:24px;

    .ivu-select-selection{
      border: 0px;
    }
    .ivu-select-single .ivu-select-input{
      font-size: 24px;
    }
  }
  .count-style{
    font-size: 50px;
  }

  .successCard{
    .ivu-card-head{
      border-bottom: 1px solid #e8eaec;
      padding: 0px;
      line-height: 1;
      background-color: #2d8cf0;
    }
  }

  .failCard{
    .ivu-card-head{
      border-bottom: 1px solid #e8eaec;
      padding: 0px;
      line-height: 1;
      background-color: #ed3f14;
    }
  }
</style>
