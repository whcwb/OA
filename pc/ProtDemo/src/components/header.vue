<template>
  <div class="box_row colCenter pageTitSty">
    <div style="background-color: #19be6b;width: 3px;height: 30px">
      <!--<Icon :type="iconname" size="44" color="#8e24aa"/>-->
    </div>
    <div class="tit">
      {{title}}
    </div>
  </div>
</template>

<script>
  export default {
    name: "pagerheader",
    props:{
      title:{
        type:String,
        default:'页面名称'
      },
      iconname:{
        type:String,
        default:'ios-football'
      }
    }
  }
</script>

<style scoped>

</style>
