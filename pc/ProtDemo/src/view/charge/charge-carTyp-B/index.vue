<template>
  <div class="box_col">
    {{ AutoReadCard }}
    <Row v-if="!titShow">
      <Col span="24">
        <pager-tit title="变更班型"></pager-tit>
      </Col>
      <Col span="24">
        <Row type="flex" style="margin: 8px 0">
          <Col span="4">
            <Input type="text" placeholder="证件号码" clearable  clearable size="large" v-model="param.idCardNo"/>
          </Col>
          <Col span="2" align="center">
            <Button type="primary"  @click="find" size="large">
              <Icon type="md-search" style="font-size: 24px"></Icon>
            </Button>
          </Col>
        </Row>
      </Col>
    </Row>
    <Form ref="userForm" :model="C_param" :rules="ruleValidate" :label-width="10">
      <Row>
        <Col span="24">
          <Card dis-hover>
            <p slot="title" style="height: 36px;font-size: 18px;text-align: center">
              <span style="float: left">
                <Icon type="ios-contact" size="32"/>
                <span v-if="titShow">变更<span style="color: #0093ff">班</span>型_</span>
                学员信息
              </span>
              <span v-if="show=='1'" style="color:red;">{{message}}</span>
              <span v-else-if="show=='2'" style="color:#009fff">{{message}}</span>
            </p>
            <div class="box_row">
              <div style="width: 200px">
                <Avatar ref="headImg" icon="ios-person" style="width:180px;height:180px;line-height: 180px;font-size: 150px;"/>
              </div>
              <div class="box_col_100">
                <Row>
                  <Col span="24">
                    <Row>
                      <Col span="8">
                        <FormItem>
                          <Input readonly prefix=" iconfont icon-2shenfenzhenghaoma" type="text" placeholder="证件号码" v-model="stuMes.idCardNo"/>
                        </FormItem>
                      </Col>
                      <Col span="6">
                        <FormItem>
                          <Input readonly prefix=" iconfont icon-xingming" type="text" placeholder="学员姓名" v-model="stuMes.name"/>
                        </FormItem>
                      </Col>
                      <Col span="4">
                        <FormItem>
                          <Select disabled v-model="stuMes.gender" placeholder="学员性别">
                            <Option value="10">男</Option>
                            <Option value="00">女</Option>
                          </Select>
                        </FormItem>
                      </Col>
                    </Row>
                    <Row>
                      <Col span="24">
                        <FormItem>
                          <Input readonly prefix=" iconfont icon-dizhi" type="text" placeholder="居住地址" v-model="stuMes.address"/>
                        </FormItem>
                      </Col>
                    </Row>
                    <Row>
                      <Col span="8">
                        <FormItem>
                          <Input readonly type="text" prefix="ios-flag-outline" placeholder="报名点" v-model="stuMes.jgmc"/>
                        </FormItem>
                      </Col>
                      <Col span="5">
                        <FormItem>
                          <Input readonly type="text" prefix="ios-car-outline" placeholder="班型" v-model="stuMes.classType"/>
                        </FormItem>
                      </Col>
                      <!--<Col span="4">-->
                      <!--<FormItem>-->
                      <!--<Input readonly type="text" prefix="ios-car-outline" placeholder="车型" v-model="stuMes.carType"/>-->
                      <!--</FormItem>-->
                      <!--</Col>-->
                      <Col span="6">
                        <FormItem>
                          <Input readonly type="text" prefix="ios-call-outline" placeholder="手机号码" v-model="stuMes.phone"/>
                        </FormItem>
                      </Col>
                      <!--v-if="stuMes.serialNum!=''"-->
                      <Col span="4" v-if="carTypC == false && stuMes.id!=''">
                        <FormItem>
                          <Button type="info" @click="carTypC=true">变更班型</Button>
                        </FormItem>
                      </Col>
                    </Row>
                    <Row v-if="carTypC">
                      <Col span="24">
                        <Row>
                          <Col span="4">
                            <FormItem prop="classType">
                              <Select v-model="C_param.classType" clearable placeholder="班型选择" filterable>
                                <Option v-for="(item,index) in carTypList" :value="item.key" :key="index">{{ item.value }}</Option>
                              </Select>
                            </FormItem>
                          </Col>
                          <Col span="4">
                            <FormItem prop="inOutType">
                              <Select v-model="C_param.inOutType" clearable placeholder="补缴/退费" filterable>
                                <Option value="00" :key="0">差价补缴</Option>
                                <Option value="10" :key="1">差价退还</Option>
                              </Select>
                            </FormItem>
                          </Col>
                          <Col span="4">
                            <FormItem prop="fee">
                              <Input :number="true"
                                     placeholder="费用金额" v-model="C_param.fee"/>
                            </FormItem>
                          </Col>
                          <Col span="4">
                            <FormItem prop="chargeType">
                              <Select v-model="C_param.chargeType" clearable placeholder="交易方式" filterable>
                                <Option value="00" :key="0">现金</Option>
                                <Option value="10" :key="1">转账</Option>
                              </Select>
                            </FormItem>
                          </Col>
                          <Col span="4">
                            <FormItem>
                              <Button type="info" @click="okCarTyp('userForm',stuMes)">确认变更</Button>
                            </FormItem>
                          </Col>
                        </Row>
                      </Col>
                    </Row>
                  </Col>
                </Row>
              </div>
            </div>
          </Card>
        </Col>
      </Row>
    </Form>
    <component :is="compName" :mess="mess"></component>

  </div>
</template>

<script>
  import pagPrint from '../../../components/print/printCTyp'
  export default {
    name: "index",
    components:{pagPrint},
    props:{
      titShow:{
        type:Boolean,
        default:false
      },
      idCardNo:{
        type:String,
        default:''
      }
    },
    data() {
      return {
        compName:'',
        mess:{},
        show:'',
        headImg:null,
        userMsg: '',
        orgTree: [],//机构
        CascaderData: [],
        iconName: 'ios-search',
        iconColor: '#b3b3b3',
        message:'',
        stuMes:{
          id:'',
          jgdm:''
        },
        param: {
          nameLike: '',
          idCardNo: '',
          orgPath: '',
        },
        carTypC:false,
        C_param:{
          id:'',
          classType:'',
          inOutType:'',//00 收入   10 支出
          fee:'',//金额
          chargeType:'',//收款方式 00 现金 10转账
          remark:'',//备注
        },
        ruleValidate: {
          classType: [
            { required: true, message: '请选着变更后的班型', trigger: 'change'}
          ],
          inOutType: [
            { required: true, message: '请选择补缴/退费', trigger: 'change'}
          ],
          chargeType: [
            { required: true, message: '请选择交易方式', trigger: 'change'}
          ],
          fee: [
            { required: true,type: 'number', message: '请输入费用金额', trigger: 'blur' }
          ],
        },
        carTypList:[]
      }
    },
    watch: {
      idCardNo:function (n,o) {
        console.log(n);
        this.param.idCardNo = n
        this.find()
      },
      'stuMes.id': function (n, o) {
        this.C_param.id = n
      },
      'stuMes.jgdm':function (n,o) {
        this.getBxCx(n)
      }
    },
    created() {
      // this.getOrgTree();
    },
    computed:{
      AutoReadCard() {
        if (window.vueObject.$data.card.CardNo){
          this.param.idCardNo = window.vueObject.$data.card.CardNo;
          this.find();
        }
        return "";
      }
    },
    methods: {
      okCarTyp(name,mess){
        var v = this
        this.$refs[name].validate((valid) => {
          if (valid) {
            if(v.C_param.classType == v.stuMes.classType ){
              v.swal({
                type:'warning',
                title:'变更班型与当前班型一致',
              })
              v.C_param.carType = ''
            }else {
              this.$http.post('/api/traineeinformation/editClassType',v.C_param).then(res=>{
                if(res.code == 200){
                  // this.$Message.success(res.message);
                  if(v.C_param.inOutType=='00'){
                    this.swal({
                      type:'question',
                      title:'班型变更成功',
                      text:'是否打印收款单据？',
                      showCancelButton:true,
                      confirmButtonText:'打印单据',
                      cancelButtonText:'完成',
                    }).then((val)=>{
                      console.log('shijianm',val);
                      if(val.value){
                        console.log('reasdfsdaf',mess)
                        v.print({traineeId:mess.id})
                      }
                    })
                  }else {
                    this.swal({
                      type:'success',
                      title:'班型变更成功'
                    })
                  }
                  v.find()
                  this.carTypC = false
                }else {
                  this.swal({
                    type:'error',
                    title:'车型变更失败',
                    text:res.message
                  })
                }
              }).catch(err=>{

              })
            }
          } else {
            this.$Message.error('请将信息填写完整');
          }
        })
      },
      print(id){
        var v = this
        this.AF.idPrintMess(id,(res)=>{
          v.mess = res
          v.compName='pagPrint'
        })
      },
      getBxCx(num){
        this.$http.post('/api/chargeitemmanagement/getJgBx',{jgdm:num,timers:new Date().getTime()}).then(res=>{
          if(res.code == 200){
            // console.log(res.result);
            if(res.result.carType.length == 0){
              this.swal({
                type:'error',
                title:'网络异常',
                text:'车辆类型加载失败'
              })
            }else {
              // this.carTypList = res.result.carType
              this.carTypList = res.result.classType
            }
          }
        }).catch(err=>{

        })
      },
      find() {
        if(this.param.idCardNo === '' ){
            this.show = '1';
            this.message = "请先输入证件号码";
            return;
        }
        this.$http.post(this.apis.TRAINEE.PAGER, this.param).then((res) => {
          if (res.code == 200) {
            this.message = '';
            this.iconName = "md-checkmark";
            this.iconColor = "#19be6b";
            if(res.page){
              if(res.page.list.length == 0){
                this.show = '1';
                this.message = "无该学员信息";
                this.stuMes = {}
                this.C_param= {}
                this.stuMes.id = ''
              }else {
                res.page.list[0].classType = this.dictUtil.getValByCode(this, 'ZDCLK1002', res.page.list[0].classType)
                this.stuMes = res.page.list[0];
              }
            }
            if(this.stuMes.headImg !=null && this.stuMes.headImg !=''){
              this.headImg = this.apis.getImgUrl + this.stuMes.headImg;
            }
          } else {
            this.show = '1';
            this.iconName = "md-alert";
            this.iconColor = "#f90";
            this.message = res.message;
            this.headImg = null;
            // this.carTypC = false
          }

         /* this.$Message.info(res.message)
          console.log(res);*/
        })
      },
      getOrgTree() {
        this.$http.get(this.apis.FRAMEWORK.getCurrentOrgTree,{timers:new Date().getTime()}).then((res) => {
          this.orgTree = res.result
        })
      },
      change(vaule, key) {
        // console.log('//////', vaule);
        // console.log(key);
        this.param.orgPath = key[key.length - 1].value
      },
    }
  }
</script>

<style lang="less">
  .iconSty {
    cursor: pointer;
    position: absolute;
    top: 40%;
    left: 50%;
    transform: translate(-50%, -50%);
  }
</style>
