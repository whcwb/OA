<template>
  <div class="tif">
    <!--<img src="./car.png" alt="">-->
  </div>
</template>

<script>
  export default {
    name: "carTif"
  }
</script>

<style lang="less">
.tif{
  position: relative;
  border: solid 1px #ff9900;
  width: 60px;
  height: 60px;
  border-radius: 60px;
  background-image: url("./carback.png");
  background-position: 5px;
  background-repeat: no-repeat;
  /*            动画名称  时间   曲线速度 */
  animation: carTif 3s linear infinite;
  img{
    position: absolute;
    width: 45px;
    bottom: -3px;
    right: 7px;
  }
}
@keyframes carTif {
  from { background-position: 5px;}
  to   { background-position: -146px;}
}
</style>
