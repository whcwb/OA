<template>
  <Card class="groupPurchase" style="width: 370px">
    <div class="box_row colCenter borbot" style="padding-top: 0">
      <div class="tit box_row_100">
        <Icon type="md-hand" size="38"/>
        三人成团
      </div>
      <div style="width: 56px">
        <i-switch v-model="switchVal" size="large">
          <span slot="open">正常</span>
          <span slot="close">停用</span>
        </i-switch>
      </div>
    </div>
    <div class="box_row colCenter borbot">
      <div class="box_row_100">
        人数 : 三人
      </div>
      <div class="box_row_100">
        优惠 ：100元/人
      </div>
    </div>
    <div class="box_row colCenter borbot" style="border: none">
      <div class="box_row_100">
        活动时间 : 2018-10-01 ~ 2018-10-31
      </div>
    </div>
    <div class="butsty box_row">
      <div class="box_row_100">
        <Button long>
          <Icon type="md-create"  size="18"/>
        </Button>
      </div>
      <div class="box_row_100">
        <Button long>
          <Icon type="ios-trash" size="18"/>
        </Button>
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
      button{
        border-radius:0px ;
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
