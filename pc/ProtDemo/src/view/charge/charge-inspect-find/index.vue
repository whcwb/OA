<template>
  <div class="box_col">
    <pager-tit title="体检查询"></pager-tit>
    {{ AutoReadCard }}
    <Row type="flex" align="bottom" style="padding:20px 0 10px 0;">
      <!-- <Col span="4">
         <Input type="text" placeholder="流水号" size="large" v-model="param.serialNum" @on-enter="getUserInfo"/>
       </Col>-->
      <Col span="4" style="padding-left: 10px">
        <Input type="text" placeholder="证件号码" @on-change="find" clearable size="large" v-model="param.idCardNo"/>
      </Col>
      <Col span="2" style="padding-left: 10px">
        <Button type="primary" @click="find" size="large">
          <Icon type="md-search" style="font-size: 24px"></Icon>
        </Button>
      </Col>
    </Row>
    <Form ref="userForm" :label-width="10" :model="stuMes">
      <Row>
        <Col span="24">
          <Card dis-hover>
            <p slot="title" style="height: 36px;font-size: 18px;text-align: center">
              <span style="float: left">
                <Icon type="ios-contact" size="32"/>
                学员信息
              </span>
              <span v-if="show=='1'" style="color:red;">{{message}}</span>
              <span v-else-if="show=='2'" style="color:blue;">{{message}}</span>
              <span v-else-if="show=='3'" style="color:red;">{{message}}</span>
              <span v-else-if="show=='4'" style="color:red;">{{message}}</span>
            </p>
            <Row>
              <Col span="24">
                <Row>
                  <Col span="4" style="text-align: center">
                    <Row>
                      <Col span="24">
                        <Avatar v-if="headImg==null" ref="headImg" icon="ios-person"
                                style="width:180px;height:180px;line-height: 180px;font-size: 150px;"/>
                        <img v-else :src="headImg" width="180" height="180"/>
                      </Col>
                    </Row>
                  </Col>
                  <Col span="20">
                    <Row>
                      <Col span="8">
                        <FormItem prop="idCardNo">
                          <Input readonly prefix=" iconfont icon-2shenfenzhenghaoma" type="text" placeholder="证件号码"
                                 v-model="stuMes.idCardNo"/>
                        </FormItem>
                      </Col>
                      <Col span="6">
                        <FormItem prop="name">
                          <Input readonly prefix=" iconfont icon-xingming" type="text" placeholder="学员姓名"
                                 v-model="stuMes.name"/>
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
                          <Input readonly prefix=" iconfont icon-dizhi" type="text" placeholder="居住地址"
                                 v-model="stuMes.address"/>
                        </FormItem>
                      </Col>
                    </Row>
                    <Row>
                      <Col span="8">
                        <FormItem prop="phone">
                          <Input readonly type="text" placeholder="报名点" v-model="stuMes.jgmc"/>
                        </FormItem>
                      </Col>
                      <Col span="4">
                        <FormItem prop="phone">
                          <Input readonly type="text" placeholder="手机号码" v-model="stuMes.phone"/>
                        </FormItem>
                      </Col>
                      <Col span="4" v-if=" stuMes.managements[0].id && stuMes.managements[0].zt != '20' && stuMes.managements[0].zt != '30'">
                        <FormItem>
                          <Button type="warning" @click="inOut">体检驳回</Button>
                        </FormItem>
                      </Col>
                      <Col span="4" v-if=" stuMes.managements[0].id && stuMes.managements[0].zt != '20' && stuMes.managements[0].zt != '30'">
                        <FormItem>
                          <Button type="error" @click="yichang">异常处理</Button>
                        </FormItem>
                      </Col>
                    </Row>
                    <!-- <Row>
                       <Col span="24">
                         <FormItem>
                           <Input readonly type="textarea" :rows="5" placeholder="档案出库原因" v-model="stuMes.remark"/>
                         </FormItem>
                       </Col>
                     </Row>-->
                  </Col>
                </Row>
              </Col>
            </Row>
          </Card>
        </Col>
      </Row>
    </Form>
    <div class="box_col_auto">
      <div class="box_row_list">
        <div v-for="(it,index) in stuMes.managements" style="background-color: #fff;margin: 8px;padding:4px;border: solid 2px #ededed">
          <div>
            {{it.traineeName}}
          </div>
          <div>
            {{it.zt | inZt}}
          </div>
          <div v-if="it.zt == '00'">
            {{it.cjsj}}
          </div>
          <div v-else-if="it.zt == '10'">
            {{it.bhsj}}
          </div>
          <div v-else-if="it.zt == '20'">
            {{it.tfsj}}
          </div>
          <div v-else-if="it.zt == '30'">
            <p>{{it.ycsj}}</p>
            <p> 异常原因:{{it.ycyy}}</p>
          </div>
        </div>
      </div>
    </div>
    <Modal
      title="异常处理"
      v-model="modal10"
      @on-ok="ycok"
      @on-cancel="yccancel"
      class-name="vertical-center-modal">
      <p><i-input v-model="reason" type="textarea" :rows="4" placeholder="请输入异常原因..."></i-input></p>
    </Modal>
  </div>
</template>

<script>
  export default {
    name: "index",
    data() {
      return {
        modal10:false,
        reason:'',
        show: '',
        headImg: null,
        userMsg: '',
        orgTree: [],//机构
        CascaderData: [],
        iconName: 'ios-search',
        iconColor: '#b3b3b3',
        message: '',
        stuMes: {
          managements: [{id: ''}]
        },
        param: {
          nameLike: '',
          idCardNo: '',
          orgPath: '',
        }
      }
    },
    filters: {
      inZt: (val) => {
        if (val == '00') {
          return '已缴费'
        } else if (val == '10') {
          return '体检已驳回'
        } else if (val == '20') {
          return '体检退费'
        }
      }
    },
    watch: {
      'param': function (n, o) {
        console.log('1236', n);
      }
    },
    created() {
      this.getOrgTree();
    },
    computed: {
      AutoReadCard() {
        if (window.vueObject.$data.card.CardNo) {
          this.param.idCardNo = window.vueObject.$data.card.CardNo;
          this.find();
        }
        return "";
      }
    },
    methods: {
      yichang(){
        console.log('yichangchulidiankaosaolaoge');
        this.modal10 = true
      },
      ycok(){
        console.log(this.reason);
        if(this.reason == ''){
          this.swal({
            title: '请填写异常原因!',
            type: "error"
          })
        }else {
          this.$http.post('/api/chargemanagement/warnInspect',{id:this.stuMes.managements[0].id,reason: this.reason}).then((res)=>{
            if(res.code == 200){
              this.swal({
                title: '异常处理成功!',
                type: "success"
              })
            }else{
              this.swal({
                title: res.message,
                type: "error"
              })
            }
          })
        }
      },
      yccancel(){
        this.modal10 = false
      },
      inOut() {
        if (this.stuMes.managements[0].id == '') {
          this.swal({
            title: '该学员为本校学员！',
            type: "error"
          })
        } else {
          this.$http.post('/api/chargemanagement/rejectInspect', {id: this.stuMes.managements[0].id}).then(res => {
            if (res.code == 200) {
              this.swal({
                title: "体检驳回成功",
                type: "success"
              })
              this.find()
            }else {
              this.swal({
                title: res.message,
                type: "error"
              })
            }
          }).catch(err => {
          })
        }
      },

      find() {
        if (this.param.idCardNo === '') {
          this.show = '1';
          this.message = "请先输入证件号码";
          return;
        }
        this.$http.post('/api/traineeinformation/checkInspect', this.param).then((res) => {
          if (res.code == 200) {
            this.message = '';
            this.iconName = "md-checkmark";
            this.iconColor = "#19be6b";
            if (res.result) {
              this.stuMes = res.result;
              this.show = '2';
              this.message = res.message + "    缴费时间：" + res.result.registrationTime;
            }
            if (res.result.managements[0].zt == '20') {
              this.show = '3';
              this.message = '该学员已退费';
            }
            if (res.result.managements[0].zt == '30') {
              this.show = '4';
              this.message = '该学员异常处理';
            }
            if (this.stuMes.headImg != null && this.stuMes.headImg != '') {
              this.headImg = this.apis.getImgUrl + this.stuMes.headImg;
            }
          } else {
            this.show = '1';
            this.iconName = "md-alert";
            this.iconColor = "#f90";
            this.message = res.message;
            this.headImg = null;
          }
          /* this.$Message.info(res.message)
           console.log(res);*/
        })
      },
      getOrgTree() {
        this.$http.get(this.apis.FRAMEWORK.getCurrentOrgTree, {timers: new Date().getTime()}).then((res) => {
          this.orgTree = res.result
        })
      },
      change(vaule, key) {
        console.log('//////', vaule);
        console.log(key);
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
