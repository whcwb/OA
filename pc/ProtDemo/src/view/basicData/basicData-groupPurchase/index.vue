<template>
  <div class="box_col">
    <pager-tit title="团购管理" iconname="ios-football"></pager-tit>
    <div class="box_row colCenter rowRight pageFindSty">
      <div>
        <Icon type="ios-home" size="32"/>
        <Input value="" placeholder="请输入团购名称" style="width: 120px" />
      </div>
      <Button type="primary" @click="">
        <Icon type="md-search"></Icon>
        <!--查询-->
      </Button>
      <Button type="primary" @click="">
        <Icon type="md-add"></Icon>
      </Button>
    </div>

    <div class="box_col_auto">
      <card-item></card-item>
    </div>
  </div>
</template>

<script>
  import cardItem from './comp/cardItem'

  export default {
    name: "index",
    components:{
      cardItem
    }
  }
</script>

<style scoped>

</style>
