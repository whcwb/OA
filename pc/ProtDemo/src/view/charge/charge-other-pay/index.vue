<template>
  <div class="box_col">
    <!--<pager-tit title="其他收费" iconname="ios-football"></pager-tit>-->
    <div class="box_col_100" style="margin-top: 15px">{{AutoReadCard}}
      <div class="box_row" style="height: 100%">
        <div class="" style="width: 400px;margin-right: 16px;">
          <Card style="width: 100%">
            <div slot="title" style="font-weight: 700;font-size: 18px">
                其他支出
            </div>
            <div :style="{height:AF.getPageHeight()*0.65+'px'}">
              <Form ref="saveForm" :model="form" :style="{height:AF.getPageHeight()*0.73-60+'px'}" style="overflow: auto" :rules="ruleValidate" >
                <FormItem prop="traineeName" label="姓名:">
                  <Input type="text"  size="large" placeholder="请输入姓名" v-model="form.traineeName"></Input>
                </FormItem>
                <FormItem prop="idCardNo" label="证件号码:">
                  <Input type="text"  size="large" placeholder="请输入证件号码" v-model="form.idCardNo"></Input>
                </FormItem>
                <FormItem prop="chargeSource" label="所属驾校">
                  <Select  v-model="form.chargeSource" placeholder="请选择驾校">
                    <Option v-for="(item,index) in cityList" :value="item.key" :key="index">{{ item.val }}</Option>
                  </Select>
                </FormItem>
                <FormItem prop="inOutType" label="收支" >
                  <Select  v-model="inOutType" placeholder="收支"
                           disabled
                           @on-change="changeType" >
                    <Option v-for="(item,index) in inOutTypelist" :value="item.val" :key="index">{{ item.lab }}</Option>
                  </Select>
                </FormItem>
                <FormItem prop="chargeCode" label="收费项:">
                  <Select  v-model="form.chargeCode" placeholder="请选择收费项" @on-change="changeFee">
                    <Option v-for="(item,index) in sfList" :value="item.id" :key="index">{{ item.chargeName }}</Option>
                  </Select>
                </FormItem>
                <FormItem prop="chargeFee" label="金额:">
                  <Input type="text" placeholder="请输入金额" v-model="form.chargeFee" :value="this.form.chargeFee"></Input>
                </FormItem>
                <FormItem prop="remark" label="备注:">
                  <Input type="text" placeholder="请输入备注" v-model="form.remark"></Input>
                </FormItem>
              </Form>
            </div>
            <div style="text-align: right">
              <Button type="default"
                      style="color: #949494;margin-right: 10px" @click="clear"
              >重置
              </Button>
              <Button type="primary" @click="save">确定</Button>
            </div>
          </Card>
        </div>
        <div class="box_row_1auto ">
          <Card style="width: 100%">
            <Row :gutter="10" style="margin-bottom: 8px">
              <Col span="8" :lg="8" :md="24">
              <div class="box_row_100 " style="font-weight: 700;font-size: 18px">
                支出记录
                <span style="font-size: 16px;margin-left: 16px">
                    共:
                   <span style="font-size: 20px;color: #ff9900">
                    {{ tableData.length }}
                  </span>
                  笔
                  <!-- - 收入:-->
                  <!--<span style="font-size: 20px;color: #ff9900" >-->
                  <!--{{ inCount }}-->
                  <!--</span>-->
                  <!--元 -->
                  - 支出：
                   <span style="font-size: 20px;color: #ff9900" >
                  {{ outCount }}
                  </span>
                  元
                </span>
              </div>
              </Col>
              <Col span="3" :lg="3" :md="4">
                <DatePicker type="date" split-panels :value="this.AF.trimDate()" :clearable="false" @on-change="changeTime"  placeholder="审核日期"   style="width: 100% ;"></DatePicker>
              </Col>
              <Col span="4" :lg="4" :md="6">
                <Input v-model="param.traineeNameLike"
                       @on-enter="getPagerList"
                       placeholder="请输入学员姓名" />
              </Col>

              <Col  span="4" :lg="4" :md="6">
                <Input v-model="param.chargeSourceLike"
                       @on-enter="getPagerList"
                       placeholder="请输入学员所属驾校"/>
              </Col>

              <Col span="5" :lg="5" :md="6">
                <Input v-model="param.idCardNoLike"
                       @on-enter="getPagerList"
                       placeholder="请输入学员证件号码" />
              </Col>
              <Col span="3" :lg="3" :md="6" style="float: right" >
              <Button type="primary" @click="getPagerList">
                <Icon type="md-search"></Icon>
                <!--查询-->
              </Button>
              </Col>
              <!--<Tooltip content="打印">-->
                <!--<Button type="primary" @click="winPrint">-->
                  <!--<Icon type="md-print"/>-->
                <!--</Button>-->
              <!--</Tooltip>-->
            </Row>
            <Table border
                   :height="AF.getPageHeight()-280"
                   :columns="tabTit" :data="tableData"
                   stripe
                   @on-selection-change="selectChange"></Table>
          </Card>
        </div>
      </div>
      <component :is="compName" :printMess='printMess'></component>
    </div>
  </div>
</template>

<script>
  import OPrintMess from '../../../components/print/other'

  export default {
    name: "index",
    components:{
      OPrintMess
    },
    data() {
      return {
        compName: '',
        printMess: [],
        count: 0,
        inCount: 0,
        outCount: 0,
        inOutType:'10',
        inOutTypelist:[
          {
            val:'00',
            lab:'收入'
          },
          {
            val:'10',
            lab:'支出'
          }
        ],
        form:{
          chargeFee: 0,
          chargeType: '10',
          chargeCode: '',  // 这个填写收费项的id
          idCardNo: '',
          traineeName: '',
          chargeSource: '',
          remark: ''
        },
        ruleValidate: {
          traineeName:[
            {required: true, message: "请输入学员姓名", trigger: 'blur'}
          ],
          idCardNo:[
            {required: true, message: "请输入学员身份证号", max:20 , trigger: 'blur'}
          ]
        },
        sfList: [],
        cityList: [],
        cityCode: 'ZDCLK1017',
        param: {
          chargeTimeLike: '',
          traineeNameLike: '',//姓名
          chargeSourceLike: '',//驾校
          idCardNoLike: '',//证件号码
          // inspect: '2',
          inOutType:'10',
          chargeCode: '9999',
          //分页数据
          pageNum: 1,//当前页码
          pageSize: 999999//每页显示数
        },
        tabTit: [
          {
            align: 'center',
            type: 'selection',
            width: 60
          },
          {
            type: 'index',
            width: 60,
            align:'center'
          },
          {
            title: '姓名',
            minWidth: 100,
            align: 'center',
            key: 'traineeName'
          },
          {
            title: '证件号码',
            align: 'center',
            minWidth: 180,
            key: 'idCardNo'
          },
          {
            title: '驾校名称',
            align: 'center',
            minWidth: 120,
            key: 'chargeSource'
          },
          {
            title: '收费项',
            align: 'center',
            minWidth: 90,
            key: 'chargeName'
          },
          {
            title: '收费金额',
            align: 'center',
            minWidth: 120,
            key: 'chargeFee'
          },
          {
            title: '收费时间',
            key: 'chargeTime',
            minWidth: 100,
            align: 'center',
            render: (h, p) => {
              return h('div', [
                h('Tooltip', {
                  props: {
                    placement: 'top',
                    content: p.row.chargeTime
                  }
                }, [
                  h('Time', {
                    props: {
                      time: new Date(p.row.chargeTime.replace(/-/g,   "/"))
                    }
                  })
                ])
              ])
            }
          },
          {
            title: '备注',
            align: 'center',
            minWidth: 150,
            key: 'remark'
          },
          {
            title: '操作',
            fixed: 'right',
            align: 'center',
            minWidth: 90,
            render: (h, p) => {
              return h('div', [
                h('Button', {
                  props: {
                    type: 'error',
                    size: 'small'
                  },
                  on: {
                    click: () => {
                      this.dele(p.row.id)
                    }
                  }
                }, '撤回')
              ])
            }
          }
        ],
        tableData: [],
        type:'2'

      }
    },
    created(){
      this.getPagerList();
      this.getDictList();
      this.getSfList();
    },
    computed:{
      AutoReadCard() {
        if (window.vueObject.$data.card.CardNo){
          this.param.idCardNoLike = window.vueObject.$data.card.CardNo;
          this.param.nameLike = window.vueObject.$data.card.NameA;
          this.getPagerList();
        }

        return "";
      }
    },
    methods: {
      changeTime(val){
        let time = this.AF.trimDate(val);
        this.param.chargeTimeLike = time;
        this.getPagerList();
      },
      changeFee(val){
        for(let r of this.sfList){
          if(r.id == val){
            this.form.chargeFee = r.amount;
          }
        }
      },
      changeType(v){
        if(v == '10'){
          this.type='2';
        }else{
          this.type='1';
        }
        this.sfList=[];
        this.getSfList();
      },
      getDictList(){
        this.cityList = this.dictUtil.getByCode(this,this.cityCode);
      },
      getSfList(){
        this.$http.post(this.apis.OTHER.NAME,{type: this.type}).then((res)=>{
          if(res.code==200){
            this.sfList = res.result;
          }
        })
      },

      getPagerList(){
        var v = this;
        this.param.chargeTimeLike = this.AF.trimDate(this.param.chargeTimeLike);
        this.$http.post(this.apis.TJSDF.PAGER,this.param).then((res)=>{
          v.tableData = res.page.list;
          v.total = res.page.total;
          this.count = 0;
          this.inCount = 0;
          this.outCount = 0;
          res.page.list.forEach((item,index)=>{
            if(item.chargeFee){
              if( item.inOutType === '00'){
                this.count += item.chargeFee;
                this.inCount+=item.chargeFee;
              }  else if(item.inOutType === '10'){
                this.count -= item.chargeFee;
                this.outCount += item.chargeFee;
              }
            }
          })

        })
      },
      dele(id) {
        this.$http.post(this.apis.TJSDF.DELE + id).then((res) => {
          if (res.code == 200) {
            this.getPagerList()
          }
          this.$Message.info(res.message);
        }).catch((err) => {
          this.$Message.error(res.message);
        })
      },
      save() {
        this.$refs.saveForm.validate((valid) => {
          if (valid) {
            this.$http.post(this.apis.OTHER.ADD, this.form).then((res) => {
              if (res.code == 200) {
                this.getPagerList()
                this.form = {
                  chargeFee: 0,
                  chargeType: '10',
                  chargeCode: '',
                  idCardNo: '',
                  traineeName: '',
                  chargeSource: '',
                  remark: ''
                };
                this.type = '1';
                this.sfList = [];
                this.getSfList();
              }
              this.$Message.info(res.message);
            }).catch((err) => {
              this.$Message.error(res.message);
            })
          }
        })

      },
      clear(){
        this.form={chargeFee: 0, chargeType: '10', chargeCode: '', idCardNo: '',traineeName: '',chargeSource: '',remark: ''};
        this.type='1';
        this.sfList=[];
        this.getSfList();
         this.$Message.success('重置成功');
      },
      winPrint(){
        this.AF.WinPrint(this,this.printMess,'OPrintMess')
      },
      selectChange(arr){
        this.printMess = arr
      }
    }
  }
</script>

<style scoped>

</style>
