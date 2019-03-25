<template>
  <div class="box_col">
    <pager-tit title="档案出库"></pager-tit>
    <Row type="flex"  align="bottom" style="padding:20px 0 10px 0;">
      <Col span="4">
        <Input type="text" placeholder="流水号" size="large" v-model="param.serialNum" @on-enter="getUserInfo"/>
      </Col>
      <Col span="4" style="padding-left: 10px">
        <Input type="text" placeholder="证件号码" size="large" v-model="param.idCardNo" @on-enter="getUserInfo"/>
      </Col>
      <Col span="2" style="padding-left: 10px">
        <Button type="primary"  @click="getUserInfo" size="large">
          <Icon type="md-search" style="font-size: 24px"></Icon>
        </Button>
      </Col>
    </Row>
    <Form ref="userForm" :label-width="10" :model="user">
    <Row>
      <Col span="24">
        <Card dis-hover>
          <p slot="title" style="height: 36px;font-size: 18px;text-align: center">
            <span style="float: left">
              <Icon type="ios-contact" size="32"/>
              学员信息
            </span>
            <span style="color:red;">{{userMsg}}</span>
          </p>
          <Row>
            <Col span="24">
              <Row>
                <Col span="4" style="text-align: center">
                  <Row>
                    <Col span="24">
                      <Avatar icon="ios-person" style="width:180px;height:180px;line-height: 180px;font-size: 150px;"/>
                    </Col>
                  </Row>
                  </Col>
                  <Col span="20">
                  <Row>
                    <Col span="8">
                    <FormItem prop="idCardNo">
                      <Input readonly prefix=" iconfont icon-2shenfenzhenghaoma" type="text" placeholder="证件号码" v-model="user.idCardNo"/>
                    </FormItem>
                    </Col>
                    <Col span="6">
                    <FormItem prop="name">
                      <Input readonly prefix=" iconfont icon-xingming" type="text" placeholder="学员姓名" v-model="user.name"/>
                    </FormItem>
                    </Col>
                    <Col span="4">
                    <FormItem>
                      <Select disabled v-model="user.gender" placeholder="学员性别">
                        <Option value="10">男</Option>
                        <Option value="00">女</Option>
                      </Select>
                    </FormItem>
                    </Col>
                    <Col span="6">
                    <FormItem>
                      <Input readonly type="text" placeholder="流水号" v-model="user.serialNum"/>
                    </FormItem>
                    </Col>
                  </Row>
                  <Row>
                    <Col span="24">
                    <FormItem>
                      <Input readonly prefix=" iconfont icon-dizhi" type="text" placeholder="居住地址" v-model="user.address"/>
                    </FormItem>
                    </Col>
                  </Row>
                  <Row>
                    <Col span="8">
                      <FormItem prop="phone">
                        <Input readonly type="text" placeholder="报名点" v-model="user.jgmc"/>
                      </FormItem>
                    </Col>
                    <Col span="4">
                      <FormItem prop="phone">
                        <Input readonly type="text" placeholder="手机号码" v-model="user.phone"/>
                      </FormItem>
                    </Col>
                    <Col span="4">
                      <FormItem>
                        <Input readonly type="text" placeholder="档案位置" v-model="user.recordLib"/>
                      </FormItem>
                    </Col>
                  </Row>
                  <Row>
                    <Col span="24">
                      <FormItem>
                        <Input type="textarea" :rows="5" placeholder="档案出库原因" v-model="archives.remark"/>
                      </FormItem>
                    </Col>
                  </Row>
                  <Row>
                    <Col span="24">
                      <Button v-show="user.recordLib != null && user.recordLib != ''" long type="success" size="large" @click="exportArchive">档案出库</Button>
                    </Col>
                  </Row>
                </Col>
              </Row>
            </Col>
          </Row>
        </Card>
      </Col>
    </Row>
    </Form>
    <component
      :is="compName">
    </component>
  </div>
</template>

<script>
  export default {
    name: "index",
    data() {
      return {
        v:this,
        compName:'',
        userMsg:'',
        user: {
          recordLib:''
        },
        pagerUrl:this.apis.TRAINEE.PAGER,
        param: {
          idCardNo:'',
          serialNum:'',//流水号
        },
        archives:{
          serialNum:'',
          remark:''
        }
      }
    },
    created() {
    },
    methods: {
      exportArchive(){
        this.$http.post(this.apis.ARCHIVES.EXPORT, this.archives).then((res) => {
          let code = res.code;
          let msg = res.message;

          this.swal({
            text:msg,
            type:code == 200 ? 'success' : 'error'
          });

          if (code == 200){
            this.getUserInfo();
          }
        }).catch((error) => {
          this.swal({
            text:this.apis.NETWORK_ERR_STR,
            type:'error'
          });
        });
      },
      getUserInfo(){
        if (this.param.idCardNo == '' && this.param.serialNum == ''){
          this.userMsg = '请先输入流水号或证件号码！';
          return;
        }

        this.$http.post(this.apis.TRAINEE.PAGER, this.param).then((res) => {
          let code = res.code;
          let msg = res.message;

          if (code == 200){
            this.userMsg = '';
            if (res.page.list.length == 0){
              this.userMsg = '学员信息不存在';
              this.user = {};
            }else{
              this.user = res.page.list[0];
              this.archives.serialNum = this.user.serialNum;
            }
          }else{
            this.userMsg = msg;
          }
        }).catch((error) => {
          this.userMsg = this.apis.NETWORK_ERR_STR;
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
