<template>
  <div>
    <Modal v-model="showModal"
           width="900"
           class-name="vertical-center-modal"
           :closable='false' :mask-closable="false">
      <div slot="header">
        <Row>
          <Col span="4">
            <div style="font-size: 20px;font-weight: 600;line-height: 33px">{{tit}}</div>
          </Col>
          <Col span="10">
            <div class="box_row">
              <div style="font-size: 16px;font-weight: 600;line-height: 35px;color: #2d8cf0">
                人员档案编号：
              </div>
              <div class="box_row_100">
                <Input type="text" v-model="addmess.dabh1" placeholder="请输档案编号"></Input>
              </div>
              <div style="padding-left: 22px;line-height: 35px;font-size: 16px;font-weight: 600">
                教练/职工
                <i-switch size="large" v-model="jl"  :style="{background:jl?'#2d8cf0':'#f5a623'}">
                  <span slot="open">教练</span>
                  <span slot="close">职工</span>
                </i-switch>
                <!--<RadioGroup v-model="jl">-->
                  <!--<Radio label="01">-->
                    <!--<span>教练员</span>-->
                  <!--</Radio>-->
                <!--</RadioGroup>-->
              </div>
            </div>
          </Col>
          <Col span="10">
            <Row type="flex" justify="end">
              <Col>
                <Button type="default" style="color: #949494;margin-right: 12px" @click="close">关闭</Button>
                <Button type="warning" @click="submit">完成</Button>
              </Col>
            </Row>
          </Col>
        </Row>
      </div>
      <div style="margin-bottom: 12px">
        <Form
          ref="addmess"
          :model="addmess"
          :rules="ruleInline"
          :label-width="100">
          <div>
            <Row>
              <Col span="8">
                <FormItem prop="xm" label='职工姓名：'>
                  <!--:disabled="addmess.id!=''"-->
                  <Input type="text" v-model="addmess.xm" placeholder="请输入姓名"></Input>
                </FormItem>
              </Col>
              <Col span="8">
                <FormItem label='优惠人数(上)：'>
                  <!--:disabled="addmess.id!=''"-->
                  <InputNumber style="width: 100%" :min="0" v-model="addmess.sRs"></InputNumber>
                </FormItem>
              </Col>
              <Col span="8">
                <FormItem label='优惠人数(下)：'>
                  <InputNumber style="width: 100%" :min="0" v-model="addmess.xRs"></InputNumber>
                </FormItem>
              </Col>
            </Row>
          </div>
          <div>
            <Row>
              <Col span="8">
                <FormItem prop="" label="红底照片">
                  <up-img :headImg='addmess.hong' @txImg="(url)=>{txImg('hong',url)}"></up-img>
                </FormItem>
              </Col>
              <Col span="8">
                <FormItem prop="" label="白底照片">
                  <up-img :headImg='addmess.bai' @txImg="(url)=>{txImg('bai',url)}"></up-img>
                </FormItem>
              </Col>
              <Col span="8">
                <FormItem prop="" label="蓝底照片">
                  <up-img :headImg='addmess.lan' @txImg="(url)=>{txImg('lan',url)}"></up-img>
                </FormItem>
              </Col>
            </Row>
          </div>
          <Collapse v-model="CollapseVal" :simple="true" accordion>
            <Panel name="jbxx">
              基本信息
              <div slot="content">
                <Row>
                  <Col span="16">
                    <FormItem label="所属机构： ">
                      <Cascader trigger="click" v-model="CascaderArr" :data="CascaderList"
                                placeholder="报名点选择" change-on-select
                                @on-change = 'CascaderGet'
                                filterable></Cascader>
                    </FormItem>
                  </Col>
                  <Col span="8">
                    <FormItem label="职工性别： ">
                      <Select filterable clearable v-model="addmess.xb">
                        <!--<Option value="10">男</Option>-->
                        <!--<Option value="00">女</Option>-->
                        <Option value="男">男</Option>
                        <Option value="女">女</Option>
                      </Select>
                    </FormItem>
                  </Col>
                  <Col span="8">
                    <FormItem label='手机号码：'>
                      <Input type="text" v-model="addmess.sjhm" placeholder="请输入手机号码"></Input>
                    </FormItem>
                  </Col>
                  <Col span="8">
                    <FormItem label='集团号：'>
                      <Input type="text" v-model="addmess.jth" placeholder="请输入集团号"></Input>
                    </FormItem>
                  </Col>
                  <Col span="8">
                    <FormItem label='文化程度：'>
                      <Input type="text" v-model="addmess.whcd" placeholder="请输入文化程度"></Input>
                    </FormItem>
                  </Col>
                  <Col span="8">
                    <FormItem label='合同状态：'>
                      <Input type="text" v-model="addmess.zt" placeholder="请输入合同状态"></Input>
                    </FormItem>
                  </Col>
                  <Col span="8">
                    <FormItem label='入校时间：'>
                      <Input type="text" v-model="addmess.rxsj" placeholder="请输入入校时间"></Input>
                    </FormItem>
                  </Col>
                  <Col span="8">
                    <FormItem label='工作地点：'>
                      <Input type="text" v-model="addmess.gzd" placeholder="请输入工作地点"></Input>
                    </FormItem>
                  </Col>
                  <Col span="8">
                    <FormItem label='工作岗位：'>
                      <!--<Input type="text" v-model="addmess.gzgw" placeholder="请输入工作岗位"></Input>-->
                      <Select v-model="addmess.gzgw">
                        <Option v-for="item in gzgwList" :value="item.key" :key="item.key">{{item.val}}</Option>
                      </Select>

                    </FormItem>
                  </Col>
                  <Col span="8">
                    <FormItem label='隶属关系：'>
                      <Select v-model="addmess.ls">
                        <Option v-for="item in lsList" :value="item.key" :key="item.key">{{item.val}}</Option>
                      </Select>
                    </FormItem>
                  </Col>
                  <Col span="8">
                    <FormItem label='任职状态：'>
                      <Select v-model="addmess.zzzt">
                        <Option v-for="item in zzztList" :value="item.key" :key="item.key">{{item.val}}</Option>
                      </Select>
                    </FormItem>
                  </Col>
                  <Col span="8">
                    <FormItem label='离职日期：'>
                      <Input type="text" v-model="addmess.lzrq" placeholder="请输入离职日期"></Input>
                    </FormItem>
                  </Col>
                  <Col span="8">
                    <FormItem label='系统备案：'>
                      <Input type="text" v-model="addmess.xtba" placeholder="请输入系统备案"></Input>
                    </FormItem>
                  </Col>
                  <Col span="8">
                    <FormItem label='所属驾校：'>
                      <Input type="text" v-model="addmess.jx" placeholder="请输入驾校"></Input>
                    </FormItem>
                  </Col>
                  <Col span="16">
                    <FormItem label='备注信息：'>
                      <Input type="textarea" v-model="addmess.bz" placeholder="请输入备注信息"></Input>
                    </FormItem>
                  </Col>
                </Row>
              </div>
            </Panel>
            <Panel name="sfzxx">
              身份证信息
              <div slot="content">
                <Row>
                  <Col span="8">
                    <FormItem label='身份证号码：'>
                      <Input type="text" v-model="addmess.sfzh" placeholder="请输入身份证号码"></Input>
                    </FormItem>
                  </Col>
                  <Col span="8">
                    <FormItem label='职工年龄：'>
                      <Input type="text" v-model="addmess.nl" placeholder="请输入职工年龄"></Input>
                    </FormItem>
                  </Col>
                  <Col span="8">
                    <FormItem label='证件有效期：'>
                      <Input type="text" v-model="addmess.zjyxq" placeholder="请输入证件有效期"></Input>
                    </FormItem>
                  </Col>
                  <Col span="8">
                    <FormItem label='出生日期：'>
                      <Input type="text" v-model="addmess.csrq" placeholder="请输入出生日期"></Input>
                    </FormItem>
                  </Col>
                  <Col span="8">
                    <FormItem label='出生月份：'>
                      <Input type="text" v-model="addmess.yf" placeholder="请输入出生月份"></Input>
                    </FormItem>
                  </Col>
                  <Col span="24">
                    <FormItem label='居住地址：'>
                      <Input type="text" v-model="addmess.jzdz" placeholder="请输入居住地址"></Input>
                    </FormItem>
                  </Col>
                </Row>
                <Row>
                  <Col span="8">
                    <FormItem prop="" label="身份证正面">
                      <up-img :headImg='addmess.hong' @txImg="(url)=>{txImg('zjOn',url)}"></up-img>
                    </FormItem>
                  </Col>
                  <Col span="8">
                    <FormItem prop="" label="身份证背面">
                      <up-img :headImg='addmess.bai' @txImg="(url)=>{txImg('zjDown',url)}"></up-img>
                    </FormItem>
                  </Col>
                </Row>
              </div>
            </Panel>
            <Panel name="jszxx">
              驾驶证信息
              <div slot="content">
                <Row>
                  <Col span="8">
                    <FormItem label='档案编号：'>
                      <Input type="text" v-model="addmess.dabh" placeholder="请输入职工年龄"></Input>
                    </FormItem>
                  </Col>
                  <Col span="8">
                    <FormItem label='初领日期：'>
                      <Input type="text" v-model="addmess.clrq" placeholder="请输入初领日期"></Input>
                    </FormItem>
                  </Col>
                  <Col span="8">
                    <FormItem label='准驾车型：'>
                      <Input type="text" v-model="addmess.zjcx" placeholder="请输入准驾车型"></Input>
                    </FormItem>
                  </Col>
                  <Col span="8">
                    <FormItem label='证件有效期：'>
                      <Input type="text" v-model="addmess.zjyxq1" placeholder="请输入证件有效期"></Input>
                    </FormItem>
                  </Col>
                </Row>
                <Row>
                  <Col span="8">
                    <FormItem prop="" label="驾驶证正本">
                      <up-img :headImg='addmess.hong' @txImg="(url)=>{txImg('jxzOn',url)}"></up-img>
                    </FormItem>
                  </Col>
                  <Col span="8">
                    <FormItem prop="" label="驾驶证副本">
                      <up-img :headImg='addmess.bai' @txImg="(url)=>{txImg('jxzDown',url)}"></up-img>
                    </FormItem>
                  </Col>
                </Row>
              </div>
            </Panel>
            <Panel name="jlzxx">
              教练证信息
              <div slot="content">
                <Row>
                  <Col span="8">
                    <FormItem label='教练证编号：'>
                      <Input type="text" v-model="addmess.jlzh" placeholder="教练证编号"></Input>
                    </FormItem>
                  </Col>
                  <Col span="8">
                    <FormItem label='领用人：'>
                      <Input type="text" v-model="addmess.lyr" placeholder="领用人"></Input>
                    </FormItem>
                  </Col>
                  <Col span="24">
                    <FormItem label='准教车型：'>
                      <!--<Input type="text" v-model="addmess.zjcx1" placeholder="准教车型"></Input>-->
                      <CheckboxGroup v-model="drivingType">
                        <Checkbox :label="item.key" v-for="(item,index) in drivingTypeList">
                          <!--<Icon type="logo-twitter"></Icon>-->
                          <Icon type="ios-car" size="36"/>
                          <span style="font-size: 22px">{{item.val}}</span>
                        </Checkbox>
                      </CheckboxGroup>
                    </FormItem>
                  </Col>
                  <Col span="8">
                    <FormItem label='初领日期：'>
                      <Input type="text" v-model="addmess.clrq1" placeholder="请输入初领日期"></Input>
                    </FormItem>
                  </Col>
                  <Col span="8">
                    <FormItem label='证件有效期：'>
                      <Input type="text" v-model="addmess.zjyxq2" placeholder="证件有效期"></Input>
                    </FormItem>
                  </Col>
                  <Col span="8">
                    <FormItem label='有效年限：'>
                      <Input type="text" v-model="addmess.yxnx" placeholder="有效年限"></Input>
                    </FormItem>
                  </Col>
                  <Col span="8">
                    <FormItem label='明涛教练卡：'>
                      <Input type="text" v-model="addmess.mtjlyk" placeholder="明涛教练卡"></Input>
                    </FormItem>
                  </Col>
                  <Col span="8">
                    <FormItem label='成功教练卡：'>
                      <Input type="text" v-model="addmess.cgjlyk" placeholder="成功教练卡"></Input>
                    </FormItem>
                  </Col>
                  <Col span="8">
                    <FormItem label='领用人：'>
                      <Input type="text" v-model="addmess.lyr1" placeholder="领用人"></Input>
                    </FormItem>
                  </Col>
                </Row>
                <Row>
                  <Col span="8">
                    <FormItem prop="" label="教练证">
                      <up-img :headImg='addmess.hong' @txImg="(url)=>{txImg('jlzOn',url)}"></up-img>
                    </FormItem>
                  </Col>
                </Row>
              </div>
            </Panel>
          </Collapse>
        </Form>
      </div>
      <div slot="footer">
      </div>
    </Modal>
  </div>
</template>

<script>
  import upImg from '@/components/Upload/UploadImg'

  export default {
    name: "audit",
    components: {
      upImg
    },
    props: {
      userAudit: {
        type: Object,
        default: {}
      }
    },
    watch:{
      drivingType: function (n, o) {
        this.addmess.zjcx1 = n.join(',')
      },
      jl:function (n,o) {
        // console.log('adsf',n);
        if(n){
          this.addmess.jlZt = '10'
        }else {
          this.addmess.jlZt = '20'
        }
      }
    },
    data() {
      return {
        CascaderList:[],//机构
        CascaderArr:[],
        jl:false,
        showModal: true,
        tit: '',
        CollapseVal: 'jbxx',
        addmess: {
          jgdm:'',
          jgmc:'',
          zjcx1:'',
          jlZt:''
        },
        ruleInline: {
          xm: [
            {required: true, message: '请输入姓名', trigger: 'blur'}
          ]
        },
        lsList: [],//隶属关系
        zzztList: [],//在职状态
        gzgwList: [],//工作岗位
        drivingType:[],
        drivingTypeList: [],//驾照列表
      }
    },
    created() {
      this.addmess = this.userAudit
      if (this.userAudit.id) {
        // console.log(this.userAudit.jlZt);
        if(this.userAudit.jlZt == '20'){
          this.jl = false
        }else if(this.userAudit.jlZt == '10'){
          this.jl = true
        }
        let arrL = this.addmess.jgdm.length/3
        for(let i=0;i<arrL;i++){
          this.CascaderArr[i] = this.addmess.jgdm.substring(0,(i+1)*3)
        }
        // console.log('---',this.CascaderArr);

        this.drivingType = this.userAudit.zjcx1.split(',')
        this.tit = '人员信息维护'
      } else {
        this.tit = '新增人员信息'
      }
      // console.log(this.addmess);
      this.getlsList()
    },
    methods: {
      CascaderGet(arr,val){
        this.addmess.jgdm = val[arr.length-1].value
        this.addmess.jgmc = val[arr.length-1].label
      },
      getlsList() {
        this.lsList = this.dictUtil.getByCode(this, 'ZDCLK1031');
        this.zzztList = this.dictUtil.getByCode(this, 'ZDCLK1029');
        this.gzgwList = this.dictUtil.getByCode(this, 'ZDCLK1032');
        this.drivingTypeList = this.dictUtil.getByCode(this, 'ZDCLK0040');
        this.$http.get(this.apis.FRAMEWORK.getCurrentOrgTree,{timers:new Date().getTime()}).then((res) => {
          if(res.code == 200){
            this.CascaderList = res.result
            // this.addmess.jgmc = res.result[0].label
            // console.log(' this.addmess.jgmc',res.result[0].value);
          }
        })
      },
      close(){
        this.$parent.compName = '';
        this.$parent.getPagerList();
      },
      txImg(A, url) {
        this.addmess[A] = url
      },
      submit(){
        let url = ''
        if (this.addmess.id && this.addmess.id != '') {
          delete this.addmess.yyme
          url = '/api/zgjbxx/update'
        } else {
          url = '/api/zgjbxx/save'
        }
        this.$http.post(url, this.addmess).then((res) => {
          let msg = res.message;
          if (res.code === 200) {
            this.swal({
              text: msg,
              type: 'success'
            });
            this.close();
            this.$parent.getPagerList()

          } else {
            this.swal({
              text: msg,
              type: 'error'
            });
          }
        }).catch((error) => {
          this.swal({
            text: this.apis.NETWORK_ERR_STR,
            type: 'error'
          });
        });
      }
    }
  }
</script>
