<style lang="less">
  @import './login.less';
</style>

<template>
  <div class="login">
    <img src="./bc.jpg" style="width: 100%;height: 100%" alt="">
    <div class="login-con">
      <Card icon="log-in" title="欢迎登录" :bordered="false">
        <div class="form-con">
          <login-form @on-success-valid="handleSubmit"></login-form>
        </div>
      </Card>
    </div>
    <div class="copyRight" style="color: #fff;padding-bottom: 80px">
      <div>
        明涛驾校管理服务平台
        <!--版本号-->
        v2.2.2.1
        <!--2018-1121-->
      </div>
      <div class="messItem" style="font-size: 16px">
        Copyright@2018 武汉创伟博科技有限公司
      </div>
    </div>

  </div>
</template>

<script>
  import LoginForm from '_c/login-form'
  import {mapActions} from 'vuex'

  export default {
    data() {
      return {
        num: 0
      }
    },
    created(){
      this.num = Math.floor(this.AF.getRandom(1)/2)
      this.$store.commit('setTagNavList',[])
    },
    components: {
      LoginForm
    },
    methods: {
      ...mapActions([
        'handleLogin',
        'getUserInfo'
      ]),
      handleSubmit(mess) {
        // console.log('', mess);
      }
    }
  }
</script>

<style>

</style>
