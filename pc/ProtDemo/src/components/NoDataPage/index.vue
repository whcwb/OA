<template>
  <div class="nodatapage">
    <div class="centerbox">
      <img src="./timgw.png" alt="">
      <div class="text">
        暂无数据
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "index"
  }
</script>

<style lang="less">
  .nodatapage {
    background: #f2f2f2;
    height: 100%;
    width: 100%;
    position: relative;
    .centerbox {
      position: absolute;
      top: 35%;
      left: 50%;
      transform: translate(-50%, -50%);
      text-align: center;
      .text{
        font-size: 24px;
        font-weight: 700;
        color: #8a8a8a;
      }
    }
  }
</style>
