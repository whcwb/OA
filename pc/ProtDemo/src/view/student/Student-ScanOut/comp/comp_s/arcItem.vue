<!--
仓库组件
-->
<template>
  <Card class="info-card-wrapper" :padding="0"
        style="height: 220px;min-width: 200px;width: 100%">
    <div class="box_col" @click="okSel"  style="cursor: pointer">
      <div class="boxTop">
        {{item.id}} / {{item.capacity}}
      </div>
      <div class="box_col_100">
        <div class="box_row" style="height: 100%">
            <div class="boxLeft" :style="{width:((item.capacity - item.num)/item.capacity)*100+'%'}">
              {{item.capacity - item.num}}
            </div>
            <div class="box_row_100 boxRight" :style="{width:(item.num/item.capacity)*100+'%'}">
              {{item.num}}
            </div>
        </div>
      </div>
    </div>
  </Card>
</template>

<script>
  export default {
    name: 'arcItem',
    components: {},
    props: {
        item:{
          type:Object,
          default:{}
        }
    },
    data() {
      return {
      }
    },
    computed: {},
    methods: {
      okSel(){
        this.$emit('okSel', this.item);
      }
    }
  }
</script>

<style lang="less">
  .tl {
    text-align: center;
    line-height: 140px;
    color: #fff;
    font-weight: 700;
  }

  .info-card-wrapper {
    .ivu-card-body {
      width: 100%;
      height: 100%;
    }
    .boxTop {
      color: #828282;
      border: #f2f2f2 1px solid;
      height: 45px;
      line-height: 45px;
      text-align: center;
      font-weight: 700;
      /*background: linear-gradient(to bottom right, #19be6b 50%, #ed3f14 50%);*/
      background-color: #f2f2f2;
      position: relative;
      .ivu-tooltip {
        position: absolute;
        right: 0px;
        top: 50%;
        transform: translateY(-50%);
        z-index: 100;
      }
      .button {
        outline: none;
      }
    }
    .boxLeft {
      .tl;
      background-color: #49cc8aed;
    }
    .boxRight {
      .tl;
      background: #ff00007a;
    }
    .botBut {
      border-top: #f2f2f2 1px solid;
      button {
        border-radius: 0;
      }
    }

  }

  .poab {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
  }

</style>
