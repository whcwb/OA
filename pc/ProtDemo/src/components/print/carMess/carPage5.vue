<template>
      <div>
        <div style="font-size: 28px;font-weight: 700">表5 车辆行驶里程记录表</div>
        <br>
        <table  style="width: 100%" border="1" cellspacing="0" cellpadding="0" class="jt2">
          <tr>
            <td rowspan="2" align="center" width="150px">时间</td>
            <td rowspan="2" align="center">行驶里程(KM)</td>
            <td rowspan="2" align="center">间隔里程(KM)</td>
            <td rowspan="2" align="center">燃油消耗(升)</td>
            <td colspan="3" align="center">燃油消耗与定额比例</td>
            <td rowspan="2" align="center">使用单位</td>
            <td rowspan="2" align="center">司机姓名</td>
          </tr>
          <tr>
            <td width="120px">定额</td>
            <td width="120px">余</td>
            <td width="120px">亏</td>
          </tr>
          <tr class="trHeight" v-for="it in 10">
            <td v-for="im in 9"></td>
          </tr>
        </table>

      </div>
</template>

<script>
    export default {
        name: "carPage4"
    }
</script>

<style scoped>

</style>
