<template>
  <div>
    <Modal v-model="showModal" height="200" width="1200" :mask-closable="false" @on-visible-change="changeWindow">
      <div class="box_row colCenter pageTitSty">
        <div style="background-color: #19be6b;width: 3px;height: 30px">
        </div>
        <div class="tit">
          <Select v-model="param.km" style="width: 80px;font-size: 24px;" disabled>
            <Option  value="02">科目二</Option>
            <Option  value="03">科目三</Option>
          </Select>
          <Select v-model="param.cx" style="width: 60px;font-size: 24px" disabled>
            <Option v-for="item in pData.dictList.carType.data" :value="item.key" :key="item.key">{{ item.val }}</Option>
          </Select>
          教练员
        </div>
      </div>
      <Row style="padding-top: 8px">
        <Col span="24">
          <Form :label-width="20">
            <Row>
              <Col span="4">
                <FormItem>
                  <Select v-model="param.area" filterable clearable placeholder="所属区域">
                    <Option v-for="item in areaData" :value="item.key" :key="item.key">{{ item.val }}</Option>
                  </Select>
                </FormItem>
              </Col>
              <Col span="6">
                <FormItem>
                  <Input type="text" size="large" placeholder="教练员姓名" v-model="param.coachNameLike"/>
                </FormItem>
              </Col>
              <Col span="1">
                <FormItem>
                  <Button type="primary"  @click="v.util.getPageData(v)">
                    <Icon type="md-search"></Icon>
                  </Button>
                </FormItem>
              </Col>
            </Row>
          </Form>
          <div class="box_col_auto">
            <Table
              :height="AF.getPageHeight()-600"
              size="large"
              highlight-row
              stripe
              @on-current-change="selectData"
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
        </Col>
      </Row>
    </Modal>
  </div>
</template>

<script>
  export default {
    name: "index",
    props: {
      pData:{
        type: Object,
        default: {}
      }
    },
    data() {
      return {
        v:this,
        showModal: true,
        //学员查询
        pagerUrl:this.apis.JL.PAGER,
        param: {
          coachNameLike: '',//姓名
          area:'',
          cx:this.pData.drivingType,//培训车型
          km:this.pData.km,//培训科目
          //分页数据
          orderBy:'area asc',
          total:1,//总数量
          pageNum: 1,//当前页码
          pageSize:8//每页显示数
        },
        //table数据查询结果
        selectRowData:null,
        pageData: [],
        areaData:[],
        tabTit: [
          {title: '姓名',key: 'coachName', width: 120},
          {title: '性别',width: 80,key: 'gender', render:(h, p) =>{
            return h('div', p.row.gender == '10' ? '男' : '女');
           }},
          {title: '驾校',key: 'jgmc', width: 160, render :(h, params) =>{
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
          {title: '区域',key: 'area', width: 120,render:(h, p) => {
              let itemVal = this.dictUtil.getValByCode(this, 'ZDCLK1009', p.row.area);
              return h('Tag', {
                props: {
                  color: 'cyan'
                }
              }, itemVal);
          }},
          {title: '驾龄(年)',key: 'drivingYears', width: 120,
            filters: [
              {
                label: '3年以上',
                value: 3
              },
              {
                label: '5年以上',
                value: 5
              },
              {
                label: '8年以上',
                value: 8
              },
              {
                label: '10年以上',
                value: 10
              }
            ],
            filterMultiple: false,
            filterMethod (value, row) {
              return parseInt(row.drivingYears) >= value;
            }
          },
          {title: '评级',key: 'coachRate', width: 200,render:(h, p) => {
            return h('Rate', {
              props: {
                disabled: true,
                value:p.row.coachRate
              }
            });
          }},
          {title: '已培训数量',key: 'traineeNum'},
          {title: '操作',render: (h, params) => {
            return h('div', [
              h('Button', {
                props: {
                  icon:'md-checkmark-circle-outline',
                  type: 'text',
                  ghost:'true',
                  shape:"circle",
                },
                style:{
                  fontSize:'28px',
                  color:'#ed4014'
                },
                on: {
                  click: () => {
                    this.submit(params.row);
                  }
                }
              })
            ]);
          }}
        ],
      }
    },
    created() {
      this.util.initTable(this);
      //
      this.areaData = this.dictUtil.getByCode(this, 'ZDCLK1009');
    },
    methods: {
      close(){
        this.$parent.compName = ''
      },
      submit(row){
        this.selectRowData = row;
        if (this.selectRowData == null){
          this.swal({
            text:'请先选择教练员',
            type:'error'
          });

          return;
        }

        let trainees = '';
        for (let item of this.pData.selectRows){
          trainees += item.id + ',';
        }

        if (trainees.length == 0){
          return;
        }
        trainees = trainees.substring(0, trainees.length - 1);

        this.$http.post(this.apis.TRAINEE.ALLOC_COACH, {
          trainees:trainees,
          km: this.param.km,
          coach: this.selectRowData.id
        }).then((res) => {
          let msg = res.message;
          if (res.code === 200) {
            this.$Message.success(msg);

            this.showModal = false;
          }else{
            this.swal({
              text:msg,
              type:'error'
            });
          }
        }).catch((error) => {
          this.$Message.error(this.apis.NETWORK_ERR_STR);
        });
      },
      selectData(currentRow, oldCurrentRow){
        this.selectRowData = currentRow;
      },
      changeWindow(flag){
        if (!flag){
          setTimeout(()=>{
            this.close();
            this.util.getPageData(this.$parent);
          },500);
        }
      }
    }
}
</script>

<style lang="less">
  .tit{
    .ivu-select-selection{
      border: 0px;
    }
    .ivu-select-single .ivu-select-input{
      font-size: 24px;
    }
  }
</style>
