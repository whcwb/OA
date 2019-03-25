<style lang="less">
.page4{
  .tab table td{
    font-size: 14px;
  }
}
</style>
<template>
      <div class="page4">
        <div class="tit">
          <h2 style="font-size: 28px;font-weight: 700">表4  车辆等级评定登记表</h2>
          <br>
        </div>
        <div class="tab">
          <table style="width: 100%" border="1" cellspacing="0" cellpadding="0" class="jt2">
            <tbody>
            <tr>
              <td align="center">检测评定日期</td>
              <td align="center">行驶里程记录</td>
              <td align="center">
                其他检测/二级维<br>
                护竣工质量检测
              </td>
              <td align="center">车辆技术等级</td>
              <td align="center">客车类型及等级</td>
              <td align="center">检测评定单位</td>
              <td align="center">登记人签名</td>
            </tr>
            <tr class="trHeight" v-for="it in 11">
              <td v-for="im in 7"></td>
            </tr>
            </tbody>
          </table>
          <div>
            说明：其他检测是指年度安全检测及各种监督检测
          </div>
        </div>
      </div>
</template>

<script>
    export default {
        name: "carPage4"
    }
</script>
