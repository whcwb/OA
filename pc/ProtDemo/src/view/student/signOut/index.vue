<template>
  <div class="box_col">
    {{ AutoReadCard }}
    <Row>
      <Col span="24">
        <pager-tit title="退学申请"></pager-tit>
      </Col>
      <Col span="24">
        <Row type="flex" align="bottom" style="padding:20px 0 10px 0;">
          <Col span="4" style="padding-left: 10px">
            <Input type="text" placeholder="证件号码" size="large" clearable
                   @on-enter="find"
                   v-model="param.idCardNo"/>
          </Col>
          <Col span="4" style="padding-left: 10px">
            <Input type="text" placeholder="学员姓名" size="large" clearable
                   @on-enter="find"
                   v-model="param.nameLike"/>
          </Col>
          <Col span="4" style="padding-left: 10px">
            <Input type="text" placeholder="手机号码" size="large" clearable
                   @on-enter="find"
                   v-model="param.phoneLike"/>
          </Col>
          <Col span="2" style="padding-left: 10px">
            <Button type="primary" @click="find" size="large">
              <Icon type="md-search" style="font-size: 24px"></Icon>
            </Button>
          </Col>
        </Row>
      </Col>
    </Row>
    <Form :label-width="10" :model="stuMes">
      <Row>
        <Col span="24">
          <Card dis-hover>
            <p slot="title" style="height: 36px;font-size: 18px;text-align: center">
            <span style="float: left">
              <Icon type="ios-contact" size="32"/>
              学员信息
            </span>
              <span style="color:red;">{{message}}</span>
            </p>
            <div class="box_row">
              <div style="width: 200px">
                <Avatar ref="headImg" icon="ios-person"
                        style="width:180px;height:180px;line-height: 180px;font-size: 150px;"/>
              </div>
              <div class="box_col_100">
                <Row>
                  <Col span="24">
                    <Row>
                      <Col span="8">
                        <FormItem>
                          <Input readonly prefix=" iconfont icon-2shenfenzhenghaoma" type="text" placeholder="证件号码"
                                 v-model="stuMes.idCardNo"/>
                        </FormItem>
                      </Col>
                      <Col span="6">
                        <FormItem>
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
                      <Col span="8">
                        <FormItem>
                          <Input readonly prefix=" iconfont icon-dizhi" type="text" placeholder="报名点"
                                 v-model="stuMes.jgmc"/>
                        </FormItem>
                      </Col>
                      <Col span="4">
                        <FormItem>
                          <Input readonly prefix="ios-call" type="text" placeholder="手机号码" v-model="stuMes.phone"/>
                        </FormItem>
                      </Col>
                    </Row>
                    <Row v-show="stuMes.id">
                      <Col span="4">
                        <FormItem>
                          <Tag v-if="!stuMes.firSubPaymentTime" color="default">科目一待缴</Tag>
                          <Tag v-else color="primary">科目一已缴</Tag>
                        </FormItem>
                      </Col>
                      <Col span="4">
                        <FormItem>
                          <Tag v-if="!stuMes.secSubPaymentTime" color="default">科目二待缴</Tag>
                          <Tag v-else color="primary">科目二已缴</Tag>
                        </FormItem>
                      </Col>
                      <Col span="4">
                        <FormItem>
                          <Tag v-if="!stuMes.thirdSubPaymentTime" color="default">科目三待缴</Tag>
                          <Tag v-else color="primary">科目三已缴</Tag>
                        </FormItem>
                      </Col>
                      <Col span="4">
                        <FormItem>
                          <Tag color="default">科二培训 ：--</Tag>
                        </FormItem>
                      </Col>
                      <Col span="4">
                        <FormItem>
                          <Tag color="default">科三培训 ：--</Tag>
                        </FormItem>
                      </Col>
                    </Row>
                    <Row v-show="stuMes.id && stuMes.status!='60'">
                      <Col span="8">
                        <FormItem prop="money">
                          <Input prefix="logo-yen" type="text" placeholder="退费金额" v-model="money"/>
                        </FormItem>
                      </Col>
                      <Col span="4">
                        <FormItem>
                          <Button @click='signOut' type="error">退学</Button>
                        </FormItem>
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
    <div class="box_col_auto">
      <student-card-list :params="newparams"></student-card-list>
    </div>
  </div>
</template>

<script>
  import studentCardList from '@/components/GetStudentM'
  export default {
    name: "index",
    components:{studentCardList},
    data() {
      return {
        headImg: null,
        userMsg: '',
        orgTree: [],//机构
        CascaderData: [],
        iconName: 'ios-search',
        iconColor: '#b3b3b3',
        message: '',
        stuMes: {},
        money: '',
        param: {
          idCardNo: '',
          nameLike:'',
          phoneLike:'',
          pageNum: 1,//当前页码
          pageSize: 30//每页显示数
        },
        newparams:''
      }
    },
    watch: {
      'param': function (n, o) {
        // console.log('1236', n);
      }
    },
    created() {
    },
    computed: {
      AutoReadCard() {
        if (window.vueObject.$data.card.CardNo) {
          this.param.idCardNo = window.vueObject.$data.card.CardNo;
          this.param.nameLike = window.vueObject.$data.card.NameA;
          this.find();
        }

        return "";
      }
    },
    methods: {
      signOut() {
        if (this.money == '') {
          this.swal({
            text: '请输入退费金额,最少退费0元',
            type: 'warning'
          });
        } else {
          this.$http.post('/api/traineeinformation/dropOut', {
            id: this.stuMes.id,
            fee: this.money
          }).then(res => {
            if (res.code == 200) {
              this.$Message.success(res.message);
              this.swal({
                title: res.message,
                type: 'success'
              })
              this.stuMes = {}
              this.param.idCardNo = ''
            } else {
              this.swal({
                text: res.message,
                type: 'error'
              });
            }
          }).catch(err => {
            this.swal({
              text: err.message,
              type: 'error'
            });
          })
        }
      },

      find() {

        this.newparams = JSON.stringify(this.param)
        // return
        // if (this.param.idCardNo === '') {
        //   this.message = "请先输入证件号码";
        //   return;
        // }
        // this.$http.post(this.apis.TRAINEE.PAGER, this.param).then((res) => {
        //   if (res.code == 200) {
        //     this.message = '';
        //     this.iconName = "md-checkmark";
        //     this.iconColor = "#19be6b";
        //     if (res.page.list.length > 0) {
        //       this.stuMes = res.page.list[0]
        //     }
        //     if (res.page.list && res.page.list.length == 0) {
        //       this.message = "没有该学员信息";
        //       this.stuMes = {}
        //     }
        //     if (res.page.list && res.page.list[0].status == '60') {
        //       this.message = "该学员已经退费";
        //     }
        //     if (res.page.list && res.page.list[0].status == '50') {
        //       this.message = "该学员已经结业";
        //     }
        //   } else {
        //     this.iconName = "md-alert";
        //     this.iconColor = "#f90";
        //     this.message = res.message;
        //     this.headImg = null;
        //   }
        // })
      }
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
