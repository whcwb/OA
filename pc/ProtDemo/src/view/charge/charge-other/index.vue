<template>
  <div class="box_col">
    <!--<pager-tit title="其他收费" iconname="ios-football"></pager-tit>-->{{ AutoReadCard }}
    <div class="box_col_100" style="margin-top: 15px">
      <div class="box_row" style="height: 100%">
        <div class="" style="width: 400px;margin-right: 16px;">
          <Card style="width: 100%">
            <div slot="title" style="font-weight: 700;font-size: 18px">
              其他收入
            </div>
            <div :style="{height:AF.getPageHeight()*0.65+'px'}">
              <Form ref="saveForm" :model="form" :style="{height:AF.getPageHeight()*0.73-60+'px'}"
                    style="overflow: auto" :rules="ruleValidate">
                <FormItem prop="traineeName" label="姓名:">
                  <Input type="text" size="large" placeholder="请输入姓名" v-model="form.traineeName"></Input>
                </FormItem>
                <FormItem prop="idCardNo" label="证件号码:">
                  <Input type="text" size="large" placeholder="请输入证件号码" v-model="form.idCardNo"></Input>
                </FormItem>
                <FormItem prop="chargeCode" label="收费项:">
                  <Select v-model="form.chargeCode" placeholder="请选择收费项" @on-change="changeFee">
                    <Option v-for="(item,index) in sfList" :value="item.id" :key="index">{{ item.chargeName }}</Option>
                  </Select>
                </FormItem>
                <FormItem prop="chargeFee" label="金额:">
                  <Input type="text" placeholder="请输入金额" v-model="form.chargeFee" :value="this.form.chargeFee"></Input>
                </FormItem>
                <FormItem prop="bankSerialNum" label="手机号码:">
                  <Input type="text" placeholder="请输入手机号码" v-model="form.bankSerialNum" :value="this.form.chargeFee"></Input>
                </FormItem>
                <FormItem prop="remark" label="备注:">
                  <Input type="text" placeholder="请输入备注" v-model="form.remark"></Input>
                </FormItem>
              </Form>
            </div>
            <div style="text-align: right">
              <Button type="error" style="margin-right: 10px" @click="readCard"> 读卡</Button>
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
                <div class="box_row_100 " style="font-weight: 700;font-size: 18px" @click="showD">
                  收费记录
                  <span style="font-size: 16px;margin-left: 16px" v-show="showDetail">
                    共:
                   <span style="font-size: 20px;color: #ff9900">
                    {{ tableData.length }}
                  </span>
                  笔 - 收入:
                  <span style="font-size: 20px;color: #ff9900">
                  {{ inCount }}
                  </span>
                  元
                  </span>
                </div>
              </Col>
              <Col span="3" :lg="3" :md="4">
                <DatePicker type="date" split-panels :value="this.AF.trimDate()" :clearable="false"
                            @on-change="changeTime" placeholder="审核日期" style="width: 100% ;"></DatePicker>
              </Col>
              <Col span="3" :lg="3" :md="5">
                <Input v-model="param.traineeNameLike"
                       @on-enter="getPagerList"
                       placeholder="请输入学员姓名"/>
              </Col>


              <Col span="4" :lg="4" :md="5">
                <Input v-model="param.idCardNoLike"
                       @on-enter="getPagerList"
                       placeholder="请输入学员证件号码"/>
              </Col>
              <Col span="3" :lg="3" :md="6">
                <Button type="primary" @click="getPagerList">
                  <Icon type="md-search"></Icon>
                  <!--查询-->
                </Button>
              </Col>
            </Row>
            <Table border stripe
                   :height="AF.getPageHeight()-280"
                   :columns="tabTit" :data="tableData"
                   @on-selection-change="selectChange"></Table>
          </Card>
        </div>
      </div>
      <component :is="compName" :printMess='printMess' :hisPrintMess="hisPrintMess" :printClose="printClose"></component>
    </div>
  </div>
</template>

<script>
import OPrintMess from '../../../components/print/other'
import PrintNew from '../../../components/printNew'
import printOther from "../../../components/print/printOther";

export default {
  name: "index",
  components: {
    OPrintMess,PrintNew,printOther
  },
  data: function () {
    return {
      showDetail: false,
      compName: '',
      printMess: [],
      count: 0,
      inCount: 0,
      outCount: 0,
      inOutType: '00',
      hisPrintMess: '',
      printClose: false,
      inOutTypelist: [
        {
          val: '00',
          lab: '收入'
        },
        {
          val: '10',
          lab: '支出'
        }
      ],
      user: {
        idCardNo: ''
      },
      form: {
        chargeFee: 0,
        chargeType: '10',
        chargeCode: '',  // 这个填写收费项的id
        idCardNo: '',
        traineeName: '',
        chargeSource: '',
        remark: '',
        bankSerialNum:'' // 银行流水号没有用 , 用来存手机号码
      },
      ruleValidate: {
        traineeName: [
          {required: true, message: "请输入学员姓名", trigger: 'blur'}
        ],
        idCardNo: [
          {required: true, message: "请输入学员身份证号", max: 20, trigger: 'blur'}
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
        chargeCode: '4999',
        // inspect: '2',
        inOutType: '00',
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
          align: 'center'
        },
        {
          title: '姓名',
          align: 'center',
          minWidth: 90,
          key: 'traineeName'
        },
        {
          title: '证件号码',
          align: 'center',
          minWidth: 180,
          key: 'idCardNo'
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
          minWidth: 100,
          key: 'chargeFee'
        },
        {
          title: '收费时间',
          key: 'chargeTime',
          minWidth: 150,
          align: 'center',
        },
        {
          title: '手机号码',
          key: 'bankSerialNum',
          minWidth: 150,
          align: 'center',
        },
        {
          title: '票据编号',
          minWidth: 150,
          align: 'center',
          render: (h,p) => {
            let pjbh = '';
            if(p.row.pjbh){
              let split = p.row.pjbh.split('-');
              pjbh = split[0] + "-" + split[1]
            }
            return h('div',pjbh);
          }
        },
        {
          title: '备注',
          align: 'center',
          minWidth: 100,
          key: 'remark'
        },
        {
          title: '操作',
          align: 'center',
          fixed: 'right',
          minWidth: 120,
          render: (h, p) => {
            return h('div', [

              h('Button', {
                props: {
                  type: 'primary',
                  size: 'small'
                },
                style: {
                  marginLeft: '10px'
                },
                on: {
                  click: () => {
                    console.log('p.row',p.row);
                    this.hisPrintMess = p.row
                    this.printClose = false
                    if(p.row.chargeName.indexOf('从业资格证') > 0) {
                      this.compName = 'printOther'
                    }else {
                      this.winPrintNew()
                    }
                  }
                }
              }, "补打")
            ])
          }
        }
      ],
      tableData: [],
      type: '1'

    }
  },
  created() {
    this.getPagerList();
    this.getDictList();
    this.getSfList();
    this.keyLis();
  },
  mounted() {
    // let v = this
    // v.rdc.startQuart(v);
  },
  beforeDestroy() {
    // this.rdc.clearReadCard();
    // clearInterval(this.IC)
  },
  computed: {
    AutoReadCard() {
      if (window.vueObject.$data.card.CardNo) {
        this.form.idCardNo = window.vueObject.$data.card.CardNo.trim();
        this.form.traineeName = window.vueObject.$data.card.NameA.trim();
        // this.getPagerList();
      }
      return "";
    }
  },
  methods: {
    readCard(){
        this.rdc.readCardByHand(this);
    },
    // duka(){
    //  this.rdc.readIdCard(this);
    // },
    keyLis() {
      var _this = this;
      document.onkeydown = (e) => {
        if(e.key == 'Enter'){
          _this.save();
        }
      }
    },
    changeTime(val) {
      let time = this.AF.trimDate(val);
      this.param.chargeTimeLike = time;
      this.getPagerList();
    },
    changeFee(val) {
      for (let r of this.sfList) {
        if (r.id == val) {
          this.form.chargeFee = r.amount;
        }
      }
    },
    changeType(v) {
      if (v == '10') {
        this.type = '2';
      } else {
        this.type = '1';
      }
      this.sfList = [];
      this.getSfList();
    },
    getDictList() {
      this.cityList = this.dictUtil.getByCode(this, this.cityCode);
    },
    getSfList() {
      this.$http.post(this.apis.OTHER.NAME, {type: this.type}).then((res) => {
        if (res.code == 200) {
          this.sfList = res.result;
          if (this.sfList.length > 0) {
            this.form.chargeCode = this.sfList[0].id;
            this.form.chargeFee = this.sfList[0].amount;
          }
        }
      })
    },
    getPagerList() {
      var v = this;
      this.param.chargeTimeLike = this.AF.trimDate(this.param.chargeTimeLike)
      this.$http.post(this.apis.TJSDF.PAGER, this.param).then((res) => {
        v.tableData = res.page.list;
        v.total = res.page.total;
        this.count = 0;
        this.inCount = 0;
        this.outCount = 0;
        res.page.list.forEach((item, index) => {
          if (item.chargeFee) {
            if (item.inOutType === '00') {
              this.count += item.chargeFee;
              this.inCount += item.chargeFee;
            } else if (item.inOutType === '10') {
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
      if(!this.form.idCardNo){
        // this.swal({
        //   text:'请填写学员信息',
        //   type:'warning'
        // })
        return
      }
      this.$refs.saveForm.validate((valid) => {
        if (valid) {
          this.$http.post(this.apis.OTHER.ADD, this.form).then((res) => {
            if (res.code == 200) {
              this.printMess = [];
              if (res.result) {
                this.printMess.push(res.result);
                this.hisPrintMess = res.result
                this.printClose = true
                if(this.hisPrintMess.chargeName.indexOf('从业资格证') > 0){
                  this.compName = 'printOther'
                }else {
                  this.winPrintNew();
                }
              }
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
              // 启动读取身份证任务
              this.clear();
              // this.rdc.startQuart(this)
            }
            // this.$Message.info(res.message);
          }).catch((err) => {
            this.$Message.error(res.message);
          })
        }
      })

    },
    clear() {
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
      window.vueObject.$data.card.CardNo = ''
      this.getSfList();
      // this.$Message.success('重置成功');
      // 启动读取身份证任务
      // this.rdc.startQuart(this)
    },
    winPrintNew() {
      this.compName = 'PrintNew'
    },
    winPrint() {
      if (this.printMess.length > 0) {
        let flag = true;
        let pjbh = '';
        for (let item of this.printMess) {
          if (pjbh != '' && item.pjbh == pjbh) {
            flag = false;
            break;
          } else {
            pjbh = item.pjbh
          }
        }
        if (flag) {
          this.printMess.chargeFee += '元'
          this.AF.WinPrint(this, this.printMess, 'OPrintMess')
        } else {
          this.swal({
            title: '所选条目有多个票据编号,请重新选择',
            type: 'warning'
          })
        }
      }
    },
    selectChange(arr) {
      this.printMess = arr
    },
    showD() {
      this.showDetail = !this.showDetail;
    }
  }
}
</script>

<style scoped>

</style>
