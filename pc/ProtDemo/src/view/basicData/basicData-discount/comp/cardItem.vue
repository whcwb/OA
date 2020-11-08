<template>
  <Card class="groupPurchase" style="width: 390px">
    <div class="box_row colBottom borbot" style="padding-top: 0">
      <!--<Icon type="md-rose"  size="38"/>-->
      <div class="tit box_row_100">
        {{mess.reduceName}}
      </div>
      <div style="width: 56px">
        <!--<i-switch v-model="switchVal" size="large">-->
          <!--<span slot="open">正常</span>-->
          <!--<span slot="close">停用</span>-->
        <!--</i-switch>-->
      </div>
    </div>
    <div class="box_row colCenter borbot">
      <div class="box_row_100">
        <Icon type="logo-usd" size="30"/> : {{mess.reduceAmount}}元/人
      </div>
      <!--<div style="margin-right: 12px">-->
        <!--<Icon type="md-person"  color="#ff9900" size="26"/>-->
      <!--</div>-->
      <!--<Tag type="border" color="warning">{{mess.sysYh.xm}}</Tag>-->
    </div>
    <div class="box_row borbot">
      <div style="margin-right: 12px">
        <Icon type="md-person"  color="#ff9900" size="26"/>
      </div>
      <div style="overflow: auto">
        <div class="box_row_z">
          <Tag style="min-width: 50px;text-align: center" type="border" color="warning" v-for="item in mess.sysYhs">{{item.xm}}</Tag>
        </div>
      </div>
    </div>
    <div class="box_row colCenter borbot">
      <div class="box_row_100">
        <Icon type="md-time" size="30" /> : {{mess.reduceStartTime | getten}} ~ {{mess.reduceEndTime | getten}}
      </div>
    </div>
    <div class="box_row colCenter borbot" style="border: none">
      <div  class="box_col_100">
        <!--适用部门 :-->
      </div>
      <Tag color="orange" v-for="(item,index) in mess.institutions" v-if="index < 2">{{item.jgmc}}</Tag>
      <span style="color: crimson;font-size: 22px;font-weight: 700"
            v-if="mess.institutions.length >2">
        +{{mess.institutions.length -2}}
      </span>
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
    props:{
      mess:{
        type:Object,
        default:{
        }
      }
    },
    filters:{
      getten:(val)=>{
        if(val){
          return val.substring(0,10)
        }
      }
    },
    data() {
      return {
        switchVal: true,
        messChange:false
      }
    },
    created(){
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
    padding-bottom: 16px;
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
