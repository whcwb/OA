<template>
      <div>
        <div style="font-size: 28px;font-weight: 700">表6 车辆交通事故登记表</div>
        <br>
        <table  style="width: 100%" border="1" cellspacing="0" cellpadding="0" class="jt2">
          <tr>
            <td  align="center">事故发生日期</td>
            <td  align="center">事故发生地点</td>
            <td  align="center">事故性质</td>
            <td align="center">事故责任</td>
            <td  align="center">事故种类及车辆损坏情况</td>
            <td  align="center">企业直接经济损失(元)</td>
            <td  align="center">登记人</td>
          </tr>
          <tr class="trHeight" v-for="it in 10">
            <td v-for="im in 7"></td>
          </tr>

        </table>
        <div>备注:1.事故性质是指特大事故丶重大事故丶一般事故或轻微事故;2.事故责任是指全部责任丶主要责任丶同等责任丶次要责任丶或无责任.</div>
        <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.事故种类是指人为事故丶机械事故.</div>
      </div>
</template>

<script>
    export default {
        name: "carPage4"
    }
</script>

<style scoped>

</style>
