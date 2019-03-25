<template>
  <div>
    <Modal
      v-model="modalShow"
      width="40%"
      :closable='false'
      :mask-closable="false">
      <div slot="header" class="box_row colCenter">
        <!--<Icon type="md-rose" size="38"/>-->
        <div class="box_row_100" style="font-size: 22px;font-weight: 700;margin-left:12px">
          {{tit}}
        </div>
        <Button @click="close" style="margin: 0 12px">取消</Button>
        <Button type="success" @click="save" style="margin: 0 12px">完成</Button>
      </div>
      <div>
        <Form ref="saveForm" :model="form" :rules="ruleValidate" :label-width="120">
          <Row>
            <Col span="6">
              <FormItem prop="" label="教员头像">
                <up-img :headImg='form.headImg' @txImg="txImg"></up-img>
              </FormItem>
            </Col>
            <Col span="9">
              <FormItem prop="coachName" label="教员姓名">
                <Input type="text" v-model="form.coachName" clearable placeholder="教练员姓名"/>
              </FormItem>
            </Col>
            <Col span="9">
              <FormItem label="性别：">
                <Select filterable clearable v-model="form.gender">
                  <Option value="10">男</Option>
                  <Option value="00">女</Option>
                </Select>
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="24">
              <FormItem label="准驾车型：">
                <CheckboxGroup v-model="drivingType">
                  <Checkbox :label="item.key" v-for="(item,index) in drivingTypeList">
                    <!--<Icon type="logo-twitter"></Icon>-->
                    <Icon type="ios-car" size="36"/>
                    <span style="font-size: 22px">{{item.val}}</span>
                  </Checkbox>
                </CheckboxGroup>
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="12">
              <FormItem prop="phone" label="手机号码：">
                <Input type="text" v-model="form.phone" :maxlength='11' placeholder="联系电话"/>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem prop="orgPath" label='所属部门：'>
                <Cascader @on-change="change" v-model="form.orgPath" change-on-select :data="orgTree"
                          placeholder="所属部门"
                          filterable clearable></Cascader>
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="12">
              <FormItem prop="idCardNo" label="证件号码：">
                <Input type="text" v-model="form.idCardNo" :maxlength='18' placeholder="证件号码"/>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem prop="coachTime" label='证件有效期：'>
                <DatePicker v-model="form.coachTime" split-panels
                            format="yyyy-MM-dd"
                            type="daterange"
                            @on-change="DatePickCoa"
                            placeholder="教练证有效期" style="width: 100%"></DatePicker>
              </FormItem>
            </Col>
          </Row>

          <Row>
            <Col span="12">
              <FormItem prop="recordNum" label="档案编号">
                <Input type="text" v-model="form.recordNum" clearable placeholder="教练证编号"/>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem prop="coachNum" label="教练编号">
                <Input type="text" v-model="form.coachNum" clearable placeholder="教练证编号"/>
              </FormItem>
            </Col>
          </Row>

          <Row>
            <Col span="12">
              <FormItem label="训练科目：">
                <CheckboxGroup v-model="coachSub">
                  <Checkbox :label="item.key" v-for="(item,index) in [{key:'02',val:'科目二'},{key:'03',val:'科目三'}]">
                    <span style="font-size: 20px">{{item.val}}</span>
                  </Checkbox>
                </CheckboxGroup>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem prop="area" label='所属区域：'>
                <Select clearable v-model="form.area">
                  <Option :value="item.key" v-for="item in areaList">{{item.val}}</Option>
                </Select>
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="12">
              <FormItem prop="licenseTime" label='驾驶证有效期：'>
                <DatePicker v-model="form.licenseTime" split-panels
                            format="yyyy-MM-dd"
                            type="daterange"
                            @on-change="DatePicklic"
                            placeholder="驾驶证有效期" style="width: 100%"></DatePicker>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem label='驾龄：'>
                <input readonly class="input" v-model="form.drivingYears" placeholder="请输入教员驾龄">
              </FormItem>
            </Col>
          </Row>
        </Form>
      </div>
      <div slot="footer"></div>
    </Modal>
  </div>
</template>

<script>
  import upImg from '@/components/Upload/UploadImg'

  export default {
    name: "addItem",
    components: {
      upImg
    },
    data() {
      return {
        tit: '新增教练员',
        modalShow: true,
        ruleValidate: {
          // coachNum: [
          //   {required: true, message: '请填写教练证编号', trigger: 'blur'}
          // ],
          // coachName: [
          //   {required: true, message: '请填写教练员姓名', trigger: 'blur'}
          // ],
          // phone: [
          //   {required: true, message: '请填写正确的手机号码', trigger: 'blur'},
          //   {type: 'string', min: 11, message: '你输入的手机号码有误！！！', trigger: 'blur'},
          //   {type: 'string', min: 11, message: '你输入的手机号码有误！！!', trigger: 'blur'}
          // ],
          // orgPath: [
          //   {required: true, type: 'array', min:1, message: '请选择分队', trigger: 'change'}
          // ],
          // drivingYears: [
          //   {required: true, message: '请填写教员驾龄', trigger: 'change'}
          // ],
          // area: [
          //   {required: true, message: '请选择教员所属区域', trigger: 'change'}
          // ],
          // licenseTime: [
          //   {required: true, type: 'array', min: 2, message: '请选择驾驶证有效期', trigger: 'change'}
          // ],
          // coachTime: [
          //   {required: true, type: 'array', min: 2, message: '请选择教练证有效期', trigger: 'change'}
          // ],
          // idCardNo: [
          //   {required: true, message: "请输填写证件号码", trigger: 'blur'},
          //   {message: "证件号码有误！！！", min: 18, max: 18, trigger: 'blur'}
          // ],
          // recordNum: [
          //   {required: true, message: '请填写教练档案编号', trigger: 'blur'}
          // ]
        },
        orgTree: [],//机构
        drivingTypeList: [],//驾照列表
        areaList: [],
        form: {
          headImg: '',
          recordNum: '',//档案编号
          idCardNo: '',//证件号码
          orgPath: [],
          coachName: '',
          gender: '10',
          drivingType: 'C1',
          phone: '',
          jgmc: '',
          jgdm: '',
          coachSub: '02',
          coachRate: "5",//默认初始评分
          drivingYears: '',
          traineeNum: 0,//学员数
          area: '',
          //驾驶证
          licenseTime: [],
          licenseStartTime: '',
          licenseEndTime: '',
          //教练证
          coachNum: '',//教员编号
          coachTime: [],
          coachStartTime: '',
          coachEndTime: '',
          headImg: ''
        },
        drivingType: ['C1'],
        coachSub: ['02']
      }
    },
    props: {
      mess: {
        type: Object,
        default: {}
      }
    },
    watch: {
      coachSub: function (n, o) {
        this.form.coachSub = n.join(',')
      },
      drivingType: function (n, o) {
        this.form.drivingType = n.join(',')
      },
      'form.orgPath': function (n, o) {
        // console.log(n);
        this.form.jgdm = n[n.length - 1]
      },
      'form.licenseTime': function (n, o) {
        if (n[1] && n[1] == '') {
          this.form.licenseTime = []
        } else {
          this.form.licenseStartTime = this.AF.trimDate(n[0])
          this.form.licenseEndTime = this.AF.trimDate(n[1])
        }
      },
      'form.coachTime': function (n, o) {
        if (n[1] && n[1] == '') {
          this.form.coachTime = []
        } else {
          this.form.coachStartTime = this.AF.trimDate(n[0])
          this.form.coachEndTime = this.AF.trimDate(n[1])
        }
      }
    },
    created() {
      // console.log(this.mess);
      if (this.mess.id) {
        this.tit = '修改教员信息'
        this.drivingType = this.mess.drivingType.split(',');
        this.coachSub = this.mess.coachSub.split(',');
        this.mess.orgPath = [this.mess.jgdm.substring(0, 3), this.mess.jgdm.substring(0, 6), this.mess.jgdm]
        this.mess.coachTime = [this.mess.coachStartTime, this.mess.coachEndTime]
        this.mess.licenseTime = [this.mess.licenseStartTime, this.mess.licenseEndTime]

        this.form = this.mess
      }
      this.getOrgTree();
      this.getDictList()
    },
    methods: {
      txImg(url) {
        this.form.headImg = url
      },
      getDictList() {
        this.drivingTypeList = this.dictUtil.getByCode(this, 'ZDCLK0040');
        this.areaList = this.dictUtil.getByCode(this, 'ZDCLK1009')
      },
      getOrgTree() {
        this.$http.get(this.apis.FRAMEWORK.getCurrentOrgTree, {timers: new Date().getTime()}).then((res) => {
          if (res.result[0].value.length == '6') {
            this.orgTree = res.result[0].children;
          } else if (res.result[0].value.length == '3') {
            this.orgTree = res.result[0].children[0].children;
          }
          // this.orgTree = res.result[0].children
        })
      },
      change(vaule, selectedData) {
        if (selectedData.length == 1) {
          this.form.jgdm = selectedData[0].value;
          this.form.jgmc = selectedData[0].label;
        }
      },
      save() {
        // console.log(this.form);
        this.$refs.saveForm.validate((valid) => {
          if (valid) {

            if (this.mess.id) {
              this.$http.post(this.apis.JL.UPDATE, this.form).then((res) => {
                if (res.code == 200) {
                  this.close()
                }
                this.$Message.info(res.message);
              })
            } else {
              this.$http.post(this.apis.JL.SAVE, this.form).then((res) => {
                if (res.code == 200) {
                  this.close()
                }
                this.$Message.info(res.message);
              })
            }
          }
        })
      },
      close() {
        this.$parent.compName = ''
        this.$parent.getPagerList()
      },
      DatePicklic(n) {
        // console.log(n);
        if (n.length > 1 && n[0] != '') {
          const a = this.AF.Num_Date(n[1]) - this.AF.Num_Date(n[0])
          const b = Math.floor((a) / (1000 * 60 * 60 * 24 * 365))
          if (b == 0) {
            this.form.drivingYears = 1
          } else {
            this.form.drivingYears = b
          }
        } else {
          this.form.drivingYears = 0
        }

        if (n[1] && n[1] == '') {
          this.form.licenseTime = []
        } else {
          this.form.licenseStartTime = this.AF.trimDate(n[0])
          this.form.licenseEndTime = this.AF.trimDate(n[1])
        }
      },
      DatePickCoa(n) {
        if (n[1] && n[1] == '') {
          this.form.coachTime = []
        } else {
          this.form.coachStartTime = this.AF.trimDate(n[0])
          this.form.coachEndTime = this.AF.trimDate(n[1])
        }
      }
    }
  }
</script>

<style lang="less">
  .input {
    display: inline-block;
    width: 100%;
    height: 32px;
    line-height: 1.5;
    padding: 4px 7px;
    font-size: 12px;
    border: 1px solid #dddee1;
    border-radius: 4px;
    color: #495060;
    background-color: #fff;
    background-image: none;
    position: relative;
    cursor: text;
    transition: border .2s ease-in-out, background .2s ease-in-out, box-shadow .2s ease-in-out;
  }
</style>
