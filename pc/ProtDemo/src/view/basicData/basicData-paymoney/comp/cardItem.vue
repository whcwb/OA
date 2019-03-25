<template>
  <Card class="groupPurchase" style="width: 230px">
    <div class="box_row colCenter borbot" style="padding-top: 0">
      <!--<Icon type="ios-list-box" size="30"/>-->
      <div class="tit box_row_100">
        {{mess.chargeName}}
      </div>
    </div>
    <div class="box_row colCenter borbot">
      <Icon type="logo-yen" size="22" color=""/>
      <div style="color: coral">
        {{mess.amount}}元
      </div>
    </div>
    <div class="butsty box_row">
      <div class="box_row_100 br" @click="change(mess)">
          <Icon type="md-create"  size="18"/>
      </div>
      <div class="box_row_100"@click="dele(mess.id)">
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
