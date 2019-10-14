<template>
  <div>
    <Form ref="userForm" :label-width="10" :model="user" :rules="userRules">
      <Row>
        <Col span="24">
          <Card dis-hover>
            <div solt="title" style="height: 32px;">
              <pager-tit title="信息录入"></pager-tit>
            </div>
            <!--<div slot="extra">
                <Button type="default" icon="md-refresh" style="font-size: 18px" @click="initPage">重置</Button>
                &nbsp;
                <Button type="success" icon="md-checkmark-circle-outline" style="font-size: 18px" @click="submit('userForm')">
                  保存
                </Button>
            </div>-->
            <Row style="padding-top:10px">
              <Col offset="1" span="24">
                <Row>
                  <Col span="8">
                    <FormItem prop="idCardNo">
                      <Input prefix=" iconfont icon-2shenfenzhenghaoma" type="text" placeholder="身份证件号(必填)"
                             v-model="user.idCardNo"/>
                    </FormItem>
                  </Col>
                  <Col span="6">
                    <FormItem prop="name">
                      <Input prefix=" iconfont icon-xingming" type="text" placeholder="学员姓名(必填)" v-model="user.name"/>
                    </FormItem>
                  </Col>
                  <Col span="4">
                    <FormItem prop="gender">
                      <Select v-model="user.gender" placeholder="学员性别">
                        <Option value="10">男</Option>
                        <Option value="00">女</Option>
                      </Select>
                    </FormItem>
                  </Col>
                  <Col span="4" style="padding-left: 10px;">
                    <Button type="info" @click="readCard" icon="md-card">读卡</Button>{{AutoReadCard}}
                  </Col>
                  <!--<Col span="4">
                    <FormItem>
                      <Input prefix=" iconfont icon-rili" disabled type="text" placeholder="出生日期"
                             v-model="user.birDay"/>
                    </FormItem>
                  </Col>-->
                </Row>
                <Row>
                  <Col span="6">
                    <FormItem prop="phone">
                      <Input type="text" placeholder="手机号码(必填)" v-model="user.phone" :maxlength="11"/>
                    </FormItem>
                  </Col>
                  <Col span="14">
                    <FormItem>
                      <Input type="text" placeholder="备注" v-model="user.remark"/>
                    </FormItem>
                  </Col>
                </Row>
                <!--<Row>
                  <Col span="8">
                    <FormItem>
                      <Input prefix=" iconfont icon-dizhi" type="text" placeholder="居住地址" v-model="user.address"/>
                    </FormItem>
                  </Col>
                  <Col span="6">
                    <FormItem>
                      <Input v-if="user.idCardEndTime == '长期'"
                             :value="user.idCardStartTime+' — '+user.idCardEndTime" readonly placeholder="证件有效期" />

                      <DatePicker v-else v-model="user.idCardTime" split-panels type="daterange" placement="bottom-end" placeholder="证件有效日期"
                                  style="width: 100% ;"></DatePicker>
                    </FormItem>
                  </Col>
                  <Col span="4" style="padding-left: 10px;">
                    <Button type="info" @click="readCard" icon="md-card">读卡</Button>{{AutoReadCard}}
                  </Col>
                  <Col span="4" style="padding-left: 10px;vertical-align: bottom">
                    <Checkbox :checked.sync="repeat"  @on-change="CheckboxChange">重学优惠</Checkbox>
                  </Col>
                </Row>-->
                <Row>
                  <Col span="3">
                    <FormItem prop="jgdm">   <!-- prop="jgdm"--> <!--v-model="fullJgdmPath"-->
                      <Select  placeholder="报名点选择(必填)" filterable clearable
                               v-model="jgmc"
                               @on-clear="bmdChangeClear"
                               @on-query-change="bmdChangeQ"
                               @on-change="bmdChangeC">
                        <Option v-for="(item,index) in dictList.bmd.data"
                                :value="item.label" :key="index">{{item.label}}</Option>
                      </Select>
                    </FormItem>
                  </Col>
                  <Col span="3">
                    <FormItem >
                      <Select v-model="jxName" placeholder="请选择驾校" @on-change="SelectChangeJx">
                        <Option v-for="(item,index) in dictList.jx" :value="item.val" :key="index">{{ item.val }}</Option>
                      </Select>
                    </FormItem>
                  </Col>
                  <Col span="3">
                    <FormItem prop="classType" v-if="FXLB.classType.length>0">
                      <Select v-model="user.classType" placeholder="班型选择" filterable>
                        <!--<Option v-for="item in dictList.classType.data" :value="item.key" :key="item.key">{{ item.value}}</Option>-->
                        <Option v-for="(item,index) in FXLB.classType" :value="item.key" :key="index">{{ item.value}}</Option>
                      </Select>
                    </FormItem>
                  </Col>
                  <Col span="3">
                    <FormItem prop="carType" v-if="FXLB.carType.length>0">
                      <Select v-model="user.carType" clearable placeholder="车型选择" filterable>
                        <Option v-for="(item,index) in FXLB.carType" :value="item.key" :key="index">{{ item.value }}</Option>
                      </Select>
                    </FormItem>
                  </Col>
                  <Col span="1" align="right" v-if ='userRealyPay'>
                    <div style="line-height: 32px;color: #ed3f14">
                      报名费
                    </div>
                  </Col>
                  <Col span="3" v-if ='userRealyPay'>
                    <FormItem>
                      <Input type="text" placeholder="报名费" v-model="user.realPay"/>
                    </FormItem>
                  </Col>
                  <Col span="2" :sm="3">
                    <FormItem :label-width="60" label="分期:">
                      <i-switch v-model="user.installment" size="large" true-value="10" false-value="00"
                                @on-change="installmentChange">
                        <span slot="open">分期</span>
                        <span slot="close">全款</span>
                      </i-switch>
                    </FormItem>
                  </Col>
                  <!--v-show="user.installment == 10"-->
                  <Col span="3" v-show="user.installment == 10">
                    <FormItem>
                      <Input type="text" placeholder="尾款金额" v-model="user.oweAmount"/>
                    </FormItem>
                  </Col>
                  <Col span="2" v-show="user.installment == 10" style="color: red;font-size: 14px;line-height: 30px;padding-left: 5px">
                    请输入欠款金额
                  </Col>
                </Row>
                <Row>
                  <Col span="20">
                    <Card dis-hover style="background-color: #EEEEEE;height: 72px;">
                      <Row>
                        <Col span="6">
                        <FormItem>
                          <RadioGroup v-model="LO">
                            <Radio label="local">本校人员</Radio>
                            <Radio label="branch">分校人员</Radio>
                            <Radio label="other">其他人员</Radio>
                          </RadioGroup>
                        </FormItem>
                        </Col>
                        <Col span="6">
                        <Row v-if="LO=='other'">
                          <Col span="24">
                          <FormItem>
                            <Input type="text" placeholder="推荐人" v-model="user.referrer"/>
                          </FormItem>
                          </Col>
                        </Row>
                        <Row v-else-if="LO=='branch'">
                          <FormItem>
                            <Select  placeholder="报名点选择" filterable clearable
                                     v-model="user.referrer"
                                     @on-query-change="bmdChangeFQ">
                              <Option v-for="(item,index) in dictList.bmd.data"
                                      :value="item.label" :key="index">{{item.label}}</Option>
                            </Select>
                          </FormItem>
                        </Row>
                        <Row v-else-if="LO=='local'">
                          <Col span="24">
                          <FormItem>
                            <Select v-model="tjr" placeholder="推荐人" not-found-text="暂无信息"
                                    @on-change="tjrChange" filterable clearable :label-in-value="true">
                              <Option v-for="(item,index) in tjrList" :value="item.id" :key="index">{{item.xm}}</Option>
                            </Select>
                          </FormItem>
                          </Col>
                        </Row>
                        </Col>
                        <Col offset="1" span="4" style="padding-left: 10px;vertical-align: bottom;padding-top: 8px">
                          <Checkbox :checked.sync="repeat"  @on-change="CheckboxChange">重学优惠</Checkbox>
                        </Col>
                      </Row>
                    </Card>
                  </Col>
                </Row>
                <Row style="padding-top: 20px">
                  <Col span="2">
                  <Button type="default" icon="md-refresh" style="font-size: 18px" @click="initPage">重置</Button>

                  </Col>
                  <Col offset="16" span="2">
                    <Button type="success" icon="md-checkmark-circle-outline" style="font-size: 18px" @click="submit('userForm')">
                      保存
                    </Button>
                  </Col>
                </Row>
                    <!--<Row>
                      <Col span="24">
                        <FormItem prop="phone">
                          <Input type="text" placeholder="手机号码(必填)" v-model="user.phone" :maxlength="11"/>
                        </FormItem>
                      </Col>
                    </Row>
                    <Row>
                      <Col span="24">
                        <FormItem>
                          <Select v-model="user.reduceCode" placeholder="优惠项选择" not-found-text="请先选择报名点" label-in-value
                                  @on-change="selectReduce" clearable>
                            <Option v-for="item in dictList.reduce.data" :value="item.id" :key="item.id">{{
                              item.reduceName }}
                            </Option>
                          </Select>
                        </FormItem>
                      </Col>
                    </Row>-->
                  <!--<Col span="18">
                    <FormItem>
                      <Input type="textarea" placeholder="备注" v-model="user.remark" :rows="10"/>
                    </FormItem>
                  </Col>-->
                <Row>
                  <Col span="8">
                    <OBJECT id="JXFWPenSign"
                            classid="clsid:E8F5278C-0C72-4561-8F7E-CCBC3E48C2E3"
                            width="600"
                            height="300">
                    </OBJECT>
                  </Col>
                </Row>
              </Col>
            </Row>
          </Card>
        </Col>
      </Row>
    </Form>

    <Spin fix v-show="loadingFlag">
      <Icon type="ios-loading" size=48 class="spin-icon-load"></Icon>
    </Spin>

    <Modal
      v-model="camSdkPreview"
      width="480"
      :styles="{top: '20px'}"
      footer-hide
      :mask="false"
      :mask-closable="false"
      @on-visible-change="changeWindow">
      <Row style="padding-left: 20px">
        <Col span="12">
          <OBJECT ref="CamSDK" style="width: 180px; height:180px"
                  classid="clsid:556DBC8A-FE4A-4DA7-A82E-3926C8D4AC41"></OBJECT>
        </Col>
        <Col span="12">
          <OBJECT ref="CamFrontSDK" style="width: 180px; height:180px"
                  classid="clsid:556DBC8A-FE4A-4DA7-A82E-3926C8D4AC41"></OBJECT>
        </Col>
      </Row>
    </Modal>
  </div>
</template>

<script>
  import $ from "jquery";
  import {formatDate} from '@/libs/tools'
  import {closeCamera, initFlag, initSDK, openCamera, ReadCard, unInitSDK, unInitSDKNoCard} from '@/libs/camsdk'

  export default {
    name: "index",
    components: {},

    data() {
      return {
        jjj:'',
        repeat:false,
        compName: '',
        camSdkPreview: false,
        tempFolder: "c:\\camtemp",
        //签字版对象
        jxfwPenSign: null,
        //选项卡切换标签
        selectTab: 'card1',
        loadingFlag: false,
        sdkFlag: false,
        cardFrontFile: null,
        cardBackFile: null,
        headImg: null,
        userRealyPay:false,
        user: {
          repeat:null,
          name: '',//姓名
          idCardNo: '',//身份证号码
          phone: '',//手机号码
          realPay:0,
          headImg: null,
          cardFront: null,
          cardBack: null,
          elecSign: null,
          birDay: '',//出生日期
          gender: '',//性别，默认为男
          address: '',//居住地址
          source: '10',//学员来源，通过学员的居住地址识别，默认本地。有武汉市地址表示为本地，其他都为外地
          jgdm:'',//报名点代码
          jgmc: '',//报名点名称
          classType: '',//班型。取字典：ZDCLK1002
          carType: '',//车型。取字典：ZDCLK0040
          reduceCode: '',//优惠编码
          reduceName: '',//优惠名称
          installment: '00',//是否分期
          oweAmount: '0',//分期尾款金额
          idCardTime: '',//证件有效期范围值
          idCardStartTime: '',//证件有效期开始时间
          idCardEndTime: '',//证件有效期结束时间
          referrer: '',//推荐人
          remark: ''//备注
        },
        jgmc:'',
        jxName:'成功驾校',
        FXLB:{
          carType:[],
          classType:[]
        },
        dictList: {
          classType: {
            code: 'ZDCLK1002',
            data: []
          },
          carType: {
            code: 'ZDCLK0040',
            data: []
          },
          bmd: {
            code: '',
            data: []
          },
          reduce: {
            data: []
          },
          jxlist:[]
        },
        LO:'local',
        tjr:'',
        tjrList:[],//推荐人 List

        userRules: {
          name: [
            {required: true, message: '姓名不能为空', trigger: 'blur'}
          ],
          idCardNo: [
            {required: true, message: '身份证号不能为空', trigger: 'blur'}
          ],
          phone: [
            {required: true, message: '手机号码不能为空', trigger: 'blur'}
          ],
          jgdm: [
            {required: true, message: '请选择报名点', trigger: 'change'}
          ],
          classType: [
            {required: true, message: '请选择班型', trigger: 'change'}
          ],
          carType: [
            {required: true, message: '请选择车型', trigger: 'change'}
          ],
          gender:[
            {required: true, message: '请选择学员性别', trigger: 'change'}
          ]
        }
      }
    },
    watch: {
    },
    computed: {
      AutoReadCard() {
        if (window.vueObject.$data.card.CardNo){
          this.user.idCardNo = window.vueObject.$data.card.CardNo;
          this.user.name = window.vueObject.$data.card.NameA;
          this.user.address = window.vueObject.$data.card.Address;
          //console.log(window.vueObject);
          if(window.vueObject.$data.card.Sex == '1'){
            this.user.gender='10'
          }else {
            this.user.gender='00'
          }
          //console.log(window.vueObject.$data.card.Born);
          this.user.birDay = window.vueObject.$data.card.Born;
          this.user.idCardTime = new Array();
          this.user.idCardTime.push(new Date(window.vueObject.$data.card.UserLifeB.substring(0, 4) + "-" + window.vueObject.$data.card.UserLifeB.substring(4, 6) + "-" + window.vueObject.$data.card.UserLifeB.substring(6, 8)));
          if(window.vueObject.$data.card.UserLifeE == '长期'){
            this.user.idCardEndTime= '长期'
          }else {
            this.user.idCardTime.push(new Date(window.vueObject.$data.card.UserLifeE.substring(0, 4) + "-" + window.vueObject.$data.card.UserLifeE.substring(4, 6) + "-" + window.vueObject.$data.card.UserLifeE.substring(6, 8)));
          }

          // this.user.idCardStartTime = window.vueObject.$data.card.UserLifeB;
          // this.user.idCardEndTime = window.vueObject.$data.card.UserLifeE;
          // console.log(this.user.birDay);
        }
        return "";
     },
      //根据当前机构代码获取所有父节点
      fullJgdmPath: {
        get() {
          // debugger;
          let res = [];
          if (this.user.jgdm) {
            //因为代码规则是按3位增长
            let jgdmStep = this.user.jgdm.length / 3;
            for (let index = 0; index < jgdmStep; index++) {
              res.push(this.user.jgdm.substring(0, index * 3 + 3));
            }
          }

          return res;
        },
        set() {
        }
      }
    },
    created(){
      this.getTjrList()
      this.getJTjx()
      this.getDictList();
      this.getBmdList();
      this.initPenSign();
    },
    methods: {
      CheckboxChange(){
        // this.repeat = true
        this.repeat = ! this.repeat
        if (this.repeat == true){
          this.userRealyPay = true
          this.user.repeat = '1'
        }else {
          this.userRealyPay = false
          this.user.repeat = null
        }
        console.log(this.user.repeat);
        console.log(this.repeat);
      },
      tjrChange(a){
        console.log(a);
        if(a){
          this.user.referrer = a.label +'-'+ a.value
        }else {
          this.user.referrer = ''
        }
      },
      getTjrList() {//获取推荐人
        this.$http.get('/api/zgjbxx/query',{params:{ls:'10',zzzt:'10'}}).then(res => {
          if (res.code == 200) {
            this.tjrList = res.result
          }else {
            this.swal({
              title:'推荐人信息获取失败，请重新获取！',
              type:'error'
            })
          }
          console.log(res);
        })
      },
      initPage() {
        window.vueObject.$data.card={};
        //console.log(window.vueObject.$data.card);
        this.cardFrontFile = null;
        this.cardBackFile = null;
        this.headImg = null;
        this.tjr = ''
        this.user = {
          repeat:null,
          name: '',//姓名
          idCardNo: '',//身份证号码
          phone: '',//手机号码
          realPay:0,
          gender: '10',//性别，默认为男
          address: '',//居住地址
          source: '10',//学员来源，通过学员的居住地址识别，默认本地。有武汉市地址表示为本地，其他都为外地
          jgdm: '',//报名点代码
          jgmc: '',//报名点名称
          classType: '10',//班型。取字典：ZDCLK1002
          carType: 'C1',//车型。取字典：ZDCLK0040
          reduceCode: '',//优惠编码
          reduceName: '',//优惠名称
          installment: '00',//是否分期
          oweAmount: '0',//分期尾款金额
          idCardTime: '',//证件有效期范围值
          idCardStartTime: '',//证件有效期开始时间
          idCardEndTime: '',//证件有效期结束时间
          referrer: '',//推荐人
          remark: ''//备注
        }
        // if (this.jxfwPenSign != null) {
        //   this.jxfwPenSign.HWClearPenSign();
        //   this.jxfwPenSign = null;
        // }
      },
      //获取当前用户可操作的报名点
      getBmdList(){
        var v = this
        this.$http.get(this.apis.FRAMEWORK.getCurrentOrgTree,{timers:new Date().getTime()}).then((res) => {
          if (res.code === 200) {
            function tree(arr) {
              if(arr[0].value.length == 9){
                if(arr.length == 1){
                  v.user.jgdm = arr[0].value
                  // console.log('111111',arr[0]);
                  v.bmdChange([arr[0].value],[arr[0]])
                  v.user.jgmc = arr[0].label + '/' + v.jxName
                  return arr
                }else {
                  return arr
                }
              }else if(arr[0].value.length == 6){
                return arr[0].children
              }else if(arr[0].value.length == 3) {
                return arr[0].children[0].children
              }
            }
            this.dictList.bmd.data = tree(res.result)
            // console.log("报名点信息",this.dictList.bmd.data )
          } else {
            this.swal({
              text: '无法加载报名点信息!',
              type: 'error'
            });
          }
        }).catch((error) => {
          this.swal({
            text: this.apis.NETWORK_ERR_STR,
            type: 'error'
          });
        })
      },
      getJTjx(){
        this.dictList.jx = this.dictUtil.getByCode(this,'ZDCLK1019');
      },
      //选择报名点的时候，自动填充form对象  // , selectedData
      jfmcFor(jgName,callback){
        this.dictList.bmd.data.forEach((item,index)=>{
          if(item.label === jgName){
            callback && callback(item)
            return
          }
        })
      },
      bmdChangeClear(){
        this.userRealyPay = false
      },
      bmdChangeFQ(val){
        if(val.indexOf('队')!=-1){
          console.log(val);
          this.user.referrer = val
        }
      },
      bmdChangeQ(val){
        // console.log(val)
        var v = this
        if(val){
          this.jfmcFor(val,(a)=>{
            v.bmdChange([a.value],[{'value':a.value,lx:a.lx,label:a.label}])
          })
        }else {
          this.bmdChangeClear()
        }
      },
      bmdChangeC(val) {
        console.log(val);
        var v = this
        if(val){
          this.jfmcFor(val,(a)=>{
            v.bmdChange([a.value],[{'value':a.value,lx:a.lx,label:a.label}])
          })
        }else {
          this.bmdChangeClear()
        }
      },
      bmdChange(val,mes) {
        console.log('1',val);
        console.log('2',mes);

        if(mes && mes[0].lx == '10'){
          this.userRealyPay = true
        }else {
          this.userRealyPay = false
        }

        this.user.classType = ''
        this.user.carType = ''
        // this.dictList.classType.data = []
        // this.dictList.carType.data = []
        this.FXLB = {
          carType:[],
          classType:[]
        }
        this.user.jgdm = val[0]
        if(mes.length!=0&&val[0]==mes[0].value){
          this.user.jgmc = mes[0].label + '/' + this.jxName;
        }else {
          this.dictList.bmd.data.forEach((item,index)=>{
            if(item.value == val[0]){
              this.user.jgmc = item.label + '/' + this.jxName;
            }
          })
        }
        //加载优惠项
        this.loadReduce(this.user.jgdm);
        this.getBxCx(val[0])
      },
      getBxCx(num){
        this.$http.post('/api/chargeitemmanagement/getJgBx',{jgdm:num,timers:new Date().getTime()}).then(res=>{
          console.log('班型车型',res);
          if(res.code == 200){
            this.FXLB =res.result
          }
        }).catch(err=>{

        })
      },
      SelectChangeJx(val){
        this.user.jgmc = this.user.jgmc.split('/')[0] + '/'+val;
        // console.log(val);
      },
      //根据机构代码加载优惠项
      loadReduce(jgdm) {
        if (jgdm && jgdm.length > 0) {
          this.$http.post(this.apis.TRAINEE.GETREDUCE, {jgdm: jgdm}).then((res) => {
            if (res.code === 200) {
              this.dictList.reduce.data = res.result;
            } else {
              swal({
                text: '无法加载报名点信息!',
                type: 'error'
              });
            }
          }).catch((error) => {
            swal({
              text: this.apis.NETWORK_ERR_STR,
              type: 'error'
            });
          })
        } else {
          this.dictList.reduce.data = [];
        }
      },
      selectReduce(valueAndLabel) {
        if (valueAndLabel) {
          this.user.reduceCode = valueAndLabel.value;
          this.user.reduceName = valueAndLabel.label;
        } else {
          this.user.reduceCode = '';
          this.user.reduceName = '';
        }
      },
      //分期状态切换
      installmentChange(value) {
        if (value == '00') {
          this.user.oweAmount = 0;
        }
      },
      //打开摄像头控件
      openCamera(index) {
        //初始化摄像头
        this.camSdkPreview = true;
        setTimeout(() => {
          let result = -1;
          //初始化控件
          initSDK(this.$refs.CamSDK);
          //初始化前摄像控件
          initSDK(this.$refs.CamFrontSDK);
          //控件初始化完成后，延迟进行操作
          setTimeout(() => {
            //打开副摄像头
            result = openCamera(this.$refs.CamSDK, 1);
            //打开主摄像头
            openCamera(this.$refs.CamFrontSDK, 0);
          }, 500);
        }, 500);
      },
      getDictList(){
        //获取班型
        // this.dictList.classType.data =
        // console.log('123',this.dictUtil.getByCode(this, this.dictList.classType.code));
        //获取车型
        // this.dictList.carType.data = this.dictUtil.getByCode(this, this.dictList.carType.code);
      },
      //读取身份证信息
      readCard() {
        //初始化控件
        initSDK(this.$refs.CamSDK);
        //控件初始化完成后，延迟进行操作
        setTimeout(() => {
          var cardInfo = ReadCard(this.$refs.CamSDK, false);
          if (cardInfo == null) {
            this.swal({
              text: '读证件信息失败，请重新放置证件！',
              type: 'error'
            });
            return;
          }
          try {
            this.user.idCardNo = cardInfo.sfzmhm;
            this.user.name = cardInfo.xm;
            this.user.birDay = cardInfo.csrq;
            this.user.address = cardInfo.dz;
            if(cardInfo.xb == '男'){
              this.user.gender='10'
            }else {
              this.user.gender='00'
            }
            this.user.idCardTime = new Array();
            this.user.idCardTime.push(new Date(cardInfo.yxqs.substring(0, 4) + "-" + cardInfo.yxqs.substring(4, 6) + "-" + cardInfo.yxqs.substring(6, 8)));
            this.user.idCardStartTime = new Date(cardInfo.yxqs.substring(0, 4) + "-" + cardInfo.yxqs.substring(4, 6) + "-" + cardInfo.yxqs.substring(6, 8))//证件有效期开始时间
            if(cardInfo.yxqz=='长期'){
              this.user.idCardEndTime= '长期'//证件有效期结束时间
            }else {
              this.user.idCardTime.push(new Date(cardInfo.yxqz.substring(0, 4) + "-" + cardInfo.yxqz.substring(4, 6) + "-" + cardInfo.yxqz.substring(6, 8)));
            }
          } catch (e) {

          }
          //读取完身份证后手工释放资源
          unInitSDK();
        }, 500);
      },
      //抓取学员现场照片
      capturePhoto() {
        let CamSDKOCX = this.$refs.CamSDK;
        try {
          //设置照片质量。默认为100最高。质量范围值：0-100
          CamSDKOCX.SetJPGQuanlity(100);
          CamSDKOCX.SetCusCrop(0);
          //设置去噪
          CamSDKOCX.SetDenoise(1);
          //视频文件存储文件
          let filePath = this.tempFolder + "\\photo.jpg";
          //拍照之前把缓存文件删除
          CamSDKOCX.DeleteFile(filePath);
          //照片抓拍
          CamSDKOCX.CaptureImage(filePath);
          //图片解析为base64
          let photoBase64 = CamSDKOCX.EncodeBase64(filePath);

          this.headImg = "data:image/png;base64," + photoBase64;
        } catch (e) {
          this.swal({
            text: '抓拍失败',
            type: 'warning'
          });
        }
      },
      //抓取身份证正面照片
      captureCardFront() {
        let CamSDKOCX = this.$refs.CamFrontSDK;
        try {
          //设置照片质量。默认为100最高。质量范围值：0-100
          CamSDKOCX.SetResolution(1280, 720);
          CamSDKOCX.SetJPGQuanlity(75);
          CamSDKOCX.SetAutoCrop(1);
          CamSDKOCX.SetImageCusCropRect(30, 31, 70, 76);
          //视频文件存储文件
          var filePath = this.tempFolder + "\\sfzzm.jpg";
          //拍照之前把缓存文件删除
          CamSDKOCX.DeleteFile(filePath);
          //照片抓拍
          CamSDKOCX.CaptureImage(filePath);

          this.cardFrontFile = filePath;
          setTimeout(() => {
            //图片解析为base64
            this.$refs.cardFrontImg.src = filePath + '?d=' + new Date().getTime();
          }, 500);
        } catch (e) {
          this.swal({
            text: '抓拍失败',
            type: 'warning'
          });
        }
      },
      //抓取身份证反面照片
      captureCardBack() {
        let CamSDKOCX = this.$refs.CamFrontSDK;
        try {
          //设置照片质量。默认为100最高。质量范围值：0-100
          CamSDKOCX.SetResolution(1280, 720);
          CamSDKOCX.SetJPGQuanlity(75);
          CamSDKOCX.SetAutoCrop(1);
          CamSDKOCX.SetImageCusCropRect(30, 31, 70, 76);
          //视频文件存储文件
          var filePath = this.tempFolder + "\\sfzfm.jpg";
          //拍照之前把缓存文件删除
          CamSDKOCX.DeleteFile(filePath);
          //照片抓拍
          CamSDKOCX.CaptureImage(filePath);

          this.cardBackFile = filePath;
          setTimeout(() => {
            //图片解析为base64
            this.$refs.cardBackImg.src = filePath + '?d=' + new Date().getTime();
          }, 500);
        } catch (e) {
          this.swal({
            text: '抓拍失败',
            type: 'warning'
          });
        }
      },
      //初始化签字版
      initPenSign() {
        let a = false
        if (this.AF.getBrowserTyp().indexOf("compatible") > -1 &&
          this.AF.getBrowserTyp().indexOf("MSIE") > -1 &&
          this.AF.getBrowserTyp().indexOf("Opera") == -1) {
          a = true
        }


        if (this.AF.getBrowserTyp().indexOf("Trident") != -1 || a) {
          if (this.jxfwPenSign == null) {
            this.jxfwPenSign = $("#JXFWPenSign").get(0);
            this.jxfwPenSign.HWSetBkColor(0xE0F8E0);
            this.jxfwPenSign.HWSetCtlFrame(2, 0x000000);

            setTimeout(() => {
              this.jxfwPenSign.HWInitialize();
            }, 500);
          }
        } else {

        }
      },
      nextBtn(name) {
        this.selectTab = '';
        this.initPenSign();
        /*this.$refs[name].validate((valid) => {
          if (valid) {
            this.selectTab = '';
            this.camSdkPreview = false;
            this.initPenSign();
          }
        })*/
      },
      prevBtn() {
        this.selectTab = 'card1';
        this.camSdkPreview = false;
        if (this.jxfwPenSign != null) {
          this.jxfwPenSign.HWClearPenSign();
          this.jxfwPenSign = null;
        }
      },
      submit(name) {
        var v = this
        this.$refs[name].validate((valid) => {
          if (valid) {
            v.loadingFlag = true;
            if (v.user.idCardTime && v.user.idCardTime.length == 2) {
              let startTime = v.user.idCardTime[0];
              let endTime = v.user.idCardTime[1];
              if (startTime > 0 && endTime && startTime.length > 0 && endTime.length > 0) {
                v.user.idCardStartTime = formatDate(startTime, 'yyyy-MM-dd');
                v.user.idCardEndTime = formatDate(endTime, 'yyyy-MM-dd');
              }
            }
            //将本地文件解析为base64字符串
            if (v.headImg != null) {
              v.user.headImg = v.headImg.substring(22, v.headImg.length);
            }
            //将本地文件解析为base64字符串
            if (v.cardFrontFile != null) {
              v.user.cardFront = v.$refs.CamSDK.EncodeBase64(v.cardFrontFile);
            }
            if (v.cardBackFile != null) {
              v.user.cardBack = v.$refs.CamSDK.EncodeBase64(v.cardBackFile);
            }
            //获取签名base64
            if (v.jxfwPenSign != null) {
              v.user.elecSign = v.jxfwPenSign.HWGetBase64Stream(1);
            }
            //debugger
            v.$http.post(v.apis.TRAINEE.SAVE, v.user).then((res) => {
              let msg = res.message;
              if (res.code === 200) {
                v.swal({
                  text: msg,
                  type: 'success'
                });
                v.initPage();
                v.jgmc = ''
              } else {
                v.swal({
                  text: msg,
                  type: 'error'
                });
              }
            }).catch((error) => {
              v.swal({
                text: v.apis.NETWORK_ERR_STR,
                type: 'error'
              });
            }).then(() => {
              v.loadingFlag = false;
            })
          } else {
            v.selectTab = 'card1';
          }
        })
      },
      changeWindow(flag) {
        if (!flag) {
          //手动关闭摄像头
          closeCamera(this.$refs.CamSDK);
          closeCamera(this.$refs.CamFrontSDK);
          unInitSDKNoCard(this.$refs.CamSDK);
          unInitSDKNoCard(this.$refs.CamFrontSDK);
        }
      }
    }
  }
</script>

<style scoped>
  .spin-icon-load {
    animation: ani-demo-spin 1s linear infinite;
  }
</style>
