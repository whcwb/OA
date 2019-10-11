<style lang="less">
  .timeSty{
    .time{
      color: #fa541c;
      font-size: 24px;
      line-height: 20px;
    }
  }
</style>
<template>
  <div class="user-avator-dropdown">
    <!--<Dropdown @on-click="handleClick">-->
    <!--<Avatar :src="userAvator"/>-->
    <!--<Icon :size="18" type="md-arrow-dropdown"></Icon>-->
    <!--<DropdownMenu slot="list">-->
    <!--<DropdownItem name="logout">退出登录</DropdownItem>-->
    <!--</DropdownMenu>-->
    <!--</Dropdown>-->
    <!--<Avatar :src="userAvator"/>-->
    <Button type="text"
            style="font-size:20px;font-weight: 700">
      {{usermess.xm}} 您好,欢迎使用本平台!
    </Button>
    <Button type="text" class="timeSty">
      <div class="time">
        {{todayTime}}
      </div>
      <!--<div class="time">-->
        <!--{{todayTime_N}}-->
      <!--</div>-->
    </Button>
    <Tooltip content="UKey驱动下载" style="margin-right: 16px">
      <Button type="warning"
              size="small"
              @click="ukey">
        <Icon type="md-key" size="24" color="#fff"/>
      </Button>
    </Tooltip>
    <Tooltip content="打印机驱动下载" style="margin-right: 16px">
      <Button type="warning"
              size="small"
              @click="dyxz">
        <Icon type="md-print" size="24" color="#fff"/>
      </Button>
    </Tooltip>
    <Tooltip content="读卡器驱动(练车)" style="margin-right: 16px">
      <Button type="warning"
              size="small"
              @click="xz">
        <Icon type="ios-card-outline" size="24" color="#fff"/>
      </Button>
    </Tooltip>
    <Tooltip content="读卡器驱动(身份证)" style="margin-right: 16px">
      <Button type="warning"
              size="small"
              @click="xzx">
        <Icon type="ios-card" size="24" color="#fff"/>
      </Button>
    </Tooltip>
    <Tooltip content="密码修改" style="margin-right: 16px">
      <Button type="info"
              size="small"
              @click="handleClick('password')">
        <Icon type="md-person" size="24" color="#fff"/>
      </Button>
    </Tooltip>
    <Tooltip content="退出登录">
      <Button type="error"
              size="small"
              style="padding: 4px 10px;"
              @click="handleClick('logout')">
        <Icon type="md-log-out" size="18" color="#fff"/>
      </Button>
    </Tooltip>
    <component :is="compName"></component>
  </div>
</template>

<script>
  import './user.less'
  import {mapActions} from 'vuex'
  import pw from '../../../../components/passworld'
  import chineseLunar from 'chinese-lunar'
  export default {
    name: 'User',
    components: {
      pw
    },
    data() {
      return {
        todayTime:'',
        todayTime_N:'',
        compName: '',
        usermess:{}
      }
    },
    props: {
      userAvator: {
        type: String,
        default: ''
      }
    },
    created(){
      // this.AF.supDate()
      this.todate()
      this.usermess = JSON.parse(sessionStorage.getItem('userInfo'))
      if(!this.usermess){
        this.$router.push({
          name: 'login'
        })
      }
      this.get()
    },
    methods: {
      ukey(){
        window.open('../ukey.exe', '_blank');
      },
      dyxz(){
        window.open('../浩顺打印机驱动.zip', '_blank');
      },
      xz(){
        window.open('../读卡器驱动.zip', '_blank');
      },
      xzx(){
        window.open('../SynCardReader.exe', '_blank');
      },
      ...mapActions([
        'handleLogOut'
      ]),
      get(){
        var a = 24*60*60*1000
        var b = Date.parse(new Date())
        var solarToLunar = chineseLunar.solarToLunar(new Date(b-a))
        // var format = chineseLunar.format(solarToLunar,'T'+'---'+'AYMD');
        var format = chineseLunar.format(solarToLunar,'YMD');
        // console.log(format)
        this.todayTime_N = format
      },
      todate(){
        setInterval(()=>{
          this.todayTime = this.AF.getTime()
        },1000)
      },
      handleClick(name) {
        switch (name) {
          case 'logout':
            let res = [
              {
                meta:{
                  hideInMenu:true,
                  notCache:true,
                  title:"工作台"
                },
                name:"home",
                path:"/home",
                to:"/name"
              }
            ]
            this.$emit('on-closeAll', res, 'all')
            this.$router.push({
              name: 'login'
            })
            break;
          case 'password':
            this.compName = 'pw'
            break;
        }
      }
    }
  }
</script>
