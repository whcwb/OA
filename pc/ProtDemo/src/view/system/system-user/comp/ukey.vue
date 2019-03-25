<!--角色分配-->
<template>
  <div class="root">
    <Modal v-model='modalShow'
           width="600"
           height="500" :closable='false' :mask-closable="false" title="绑定Ukey">
      <div v-if="SpinShow" style="width:100%;height:100%;position: fixed;top: 0;left:0;z-index: 1111;">
        <Spin fix>
          <Icon type="load-c" size=55 class="demo-spin-icon-load"></Icon>
          <div style="font-size: 30px;">数据加载中请稍后</div>
        </Spin>
      </div>
      <div style="overflow: hidden;height:100%;width: 100%">
        <Form
                ref="addmess"
                :model="formData"
                :label-width="100"
                :styles="{top: '20px'}">
          <Row :gutter="16">
            <Col span="14">
              <FormItem label="登录方式： ">
                <Select v-model="formData.loginType" @on-change="loginTypeChange"><Option v-for="(item,index) in loginTypeList" :value="item.value" :key="index">{{item.label}}</Option>
                </Select>
              </FormItem>
            </Col>
            <Col span="10">
              <Button  type="primary" @click="save">修改</Button>
            </Col>
          </Row>
          <Row :gutter="16">
            <Col span="14">
              <FormItem label="UKey编号： ">
                <Input type="text" v-model="ukey" readonly placeholder="暂未绑定UKey">
                </Input>
              </FormItem>
            </Col>
            <Col span="10">
              <Button v-if="formData.ukey == ''" type="primary" @click="bind">绑定</Button>
              <Button v-if="formData.ukey != ''" type="primary" @click="unbind">解绑</Button>
            </Col>
          </Row>
        </Form>
      </div>
      <div slot='footer'>
        <Button type="default" @click="close" style="color: #949494">关闭</Button>
      </div>
    </Modal>
  </div>
</template>

<script>

  import UKey from '../../../../plugin/Ukey'


  export default {
    name: '',
    data() {
      return {
        SpinShow: false,
        modalShow: true,
        loginTypeList:[
          {value:'1',label:'图片验证码'},
          {value:'2',label:'Ukey'},
        ],
        ukey:'',
        ukeyPwd:'',
        formData:{
          loginType:'1'
        }
      }
    },
    props: {
      usermes: {
        type: Object,
        default: {}
      }
    },
    created() {
      this.formData = this.usermes;
      if (this.formData.loginType == null || this.formData.loginType == ''){
        this.formData.loginType = '1'
      }
      this.ukey = this.formData.ukey;
      this.initKey();
    },
    mounted() {
    },
    methods: {
      initKey(){
        if (this.formData.ukey != null && this.formData.ukey != ''){
          return;
        }
        if (this.formData.loginType === '1'){
          return;
        }
        if (UKey.isSuccess()){
          this.ukey = UKey.deviceId;
          this.ukeyPwd = UKey.keyCode;
        }else{
          UKey.read(()=>{
            this.ukey = UKey.deviceId;
            this.ukeyPwd = UKey.keyCode;
          })
        }
      },
      bind(){
        if (UKey.isSuccess()){
          this.ukey = UKey.deviceId;
          this.ukeyPwd = UKey.keyCode;
        }else{
          UKey.read(()=>{
            this.ukey = UKey.deviceId;
            this.ukeyPwd = UKey.keyCode;
            this.bind();
          })
          return;
        }
        let param = {
          ukey: this.ukey,
          ukeyPwd: this.ukeyPwd,
          yhid: this.formData.yhid,
        };
        this.$http.post(this.apis.USER.bindUKey,param).then((res)=>{
          if (res.code != 200){
            this.swal({
              type:'warning',
              title:res.message
            })
            return
          }else{
            this.$Message.success('绑定成功!')
            this.close();

          }
        })
      },
      unbind(){
        let param = {
          ukey: this.ukey,
          yhid: this.formData.yhid,
        };
        this.$http.post(this.apis.USER.unBindUKey,param).then((res)=>{
          if (res.code != 200){
            this.swal({
              type:'warning',
              title:res.message
            })
            return
          }else{
            this.formData.ukey = this.ukey;
            this.$Message.success('解绑成功!')
            this.close();
          }
        })

      },
      loginTypeChange(e){
        // if (e === '2'){
        //   if (UKey.isSuccess()){
        //     this.ukey = UKey.deviceId;
        //     this.ukeyPwd = UKey.keyCode;
        //   }else{
        //     UKey.read(()=>{
        //       this.ukey = UKey.deviceId;
        //       this.ukeyPwd = UKey.keyCode;
        //     })
        //   }
        // }
      },
      close() {
        var v = this
        this.$emit('listF', null)
      },
      save() {
        let v = this;
        v.SpinShow = true
        let p = {
          yhid:this.formData.yhid,
          loginType:this.formData.loginType
        }
        this.$http.post(this.apis.USER.updateLoginType, p).then((res) => {
          if (res.code === 200) {
            this.$Message.success('操作成功');
            this.$emit('listF', res)
            v.SpinShow = false
          }else{
            this.swal({
              type:'warning',
              title:res.message
            })
          }
        })
      },
    }
  }
</script>

<style>

</style>
