<template>
  <Card class="groupPurchase" style="width: 420px">
    <div class="box_row colCenter borbot" style="padding-top: 0">
      <!--<Icon type="ios-list-box" size="30"/>-->
      <div class="tit box_row_100">
        {{mess.chargeName}}
      </div>
      <Icon type="logo-yen" size="22"/>
      <div class="">
        {{mess.amount}}元
      </div>
    </div>
    <div class="">
      <div v-if="mess.carType" style="line-height: 40px;border-bottom: #f2f2f2 2px solid">
        {{mess.carType}}
      </div>
      <div v-else style="line-height: 40px;border-bottom: #f2f2f2 2px solid">
        -
      </div>
      <div>
        <div class="box_row colCenter borbot rowRight" v-if="mess.institutions.length > 0 ">
          <Tag color="orange" v-for="(item,index) in mess.institutions" v-if="index < 2">{{item.jgmc}}</Tag>
          <span style="color: crimson;font-size: 22px;font-weight: 700"
          v-if="mess.institutions.length >2">
            +{{mess.institutions.length -2}}
          </span>
        </div>
        <div class="box_row colCenter borbot rowRight" v-else >
          <Tag color="orange" v-for="(item,index) in [{jgmc:'其他收费'}]" v-if="index < 2">{{item.jgmc}}</Tag>
          <span style="color: crimson;font-size: 22px;font-weight: 700">

          </span>
        </div>
        <div class="box_row colCenter borbot rowRight" v-else>
          <div style="color: coral">
            统一价
          </div>
        </div>

      </div>

    </div>

    <div class="butsty box_row">
      <div class="box_row_100 br" @click="change(mess)">
          <Icon type="md-create"  size="18"/>
      </div>
      <div class="box_row_100" @click="dele(mess.id)">
          <Icon type="ios-trash" size="18"/>
      </div>
    </div>
  </Card>

</template>

<script>
  export default {
    name: "cardItem",
    components: {},
    data() {
      return {
        switchVal: true
      }
    },
    props:{
      mess:{
        type:Object,
        default:{
          name:'',
          money:''
        }
      }
    },
    created(){
      // console.log(this.mess);
    },
    methods:{
      dele(id){
        this.$emit('dele',id)
      },
      change(mess){
        this.$emit('change',mess)
      }
    }
  }
</script>

<style lang="less">
  .groupPurchase {
    padding-bottom: 25px;
    font-size: 20px;
    position: relative;
    .butsty{
      position: absolute;
      bottom: 0;
      left: 0;
      right: 0;
      background-color: #f3f3f3;
      text-align: center;
      cursor: pointer;
      padding: 6px 0;
      .br{
        border-right: #dadada 1px solid;
      }
    }
    .tit {
      font-size: 20px;
      font-weight: 700;
    }
    .borbot{
      border-bottom: #f2f2f2 2px solid;
      padding: 16px 0;
    }
  }

</style>
