<template>
      <div>
          <div style="font-size: 28px;font-weight: 700">表1 车辆基本情况登记表</div>
        <br>
          <table border="1" cellspacing="0" style="width: 100%" class="jt1">
              <tr>
                  <td>车辆号牌信息</td>
                  <td colspan="2">车辆号码</td>
                  <td colspan="2">颜色</td>
                  <td colspan="2">注册（表更）日期</td>
                  <td rowspan="5" colspan="4"></td>
              </tr>
              <tr>
                  <td>车牌号码</td>
                  <td colspan="2">{{carInfo.cph}}</td>
                  <td colspan="2">{{carInfo.clys}}</td>
                  <td colspan="2">{{carInfo.ccdjrq}}</td>
              </tr>
              <tr>
                  <td>牌号变更1</td>
                  <td colspan="2"></td>
                  <td colspan="2"></td>
                  <td colspan="2"></td>
              </tr>
              <tr>
                  <td>牌号变更2</td>
                  <td colspan="2"></td>
                  <td colspan="2"></td>
                  <td colspan="2"></td>
              </tr>
              <tr>
                  <td>牌号变更3</td>
                  <td colspan="2"></td>
                  <td colspan="2"></td>
                  <td colspan="2"></td>
              </tr>
              <tr>
                  <td>业户信息</td>
                  <td colspan="3">初次登记（日期）</td>
                  <td colspan="3">名称变更1（日期）</td>
                  <td colspan="3">名称变更2（日期）</td>
                  <td colspan="3">名称变更3（日期）</td>
              </tr>
              <tr>
                  <td>车主名称</td>
                  <td colspan="3">{{carInfo.carSyr}}</td>
                  <td colspan="3"></td>
                  <td colspan="3"></td>
                  <td colspan="3"></td>
              </tr>
              <tr>
                  <td>经营许可证号</td>
                  <td colspan="3">{{carInfo.jyxkzh}}</td>
                  <td colspan="3"></td>
                  <td colspan="3"></td>
                  <td colspan="3"></td>
              </tr>
              <tr>
                  <td>经济类型</td>
                  <td colspan="3">{{carInfo.jjlx}}</td>
                  <td colspan="3"></td>
                  <td colspan="3"></td>
                  <td colspan="3"></td>
              </tr>
              <tr>
                  <td>经营组织方式</td>
                  <td colspan="3">{{carInfo.jyzzfs}}</td>
                  <td colspan="3"></td>
                  <td colspan="3"></td>
                  <td colspan="3"></td>
              </tr>
              <tr>
                  <td>资质等级</td>
                  <td colspan="3">{{carInfo.zzdj}}</td>
                  <td colspan="3"></td>
                  <td colspan="3"></td>
                  <td colspan="3"></td>
              </tr>
              <tr>
                  <td>经营范围</td>
                  <td colspan="3">{{carInfo.yjfw}}</td>
                  <td colspan="3"></td>
                  <td colspan="3"></td>
                  <td colspan="3"></td>
              </tr>
              <tr>
                  <td>道路运输许可证号</td>
                  <td colspan="3">{{carInfo.dlyszh}}</td>
                  <td colspan="3"></td>
                  <td colspan="3"></td>
                  <td colspan="3"></td>
              </tr>
              <tr>
                  <td>二级维护周期</td>
                  <td colspan="3">{{carInfo.whzq}}</td>
                  <td colspan="3"></td>
                  <td colspan="3"></td>
                  <td colspan="3"></td>
              </tr>
          </table>
      </div>
</template>

<script>
    export default {
        name: "carPage1",
        props:{
            carInfo:{
                type:Object,
                default:()=>{
                    return {}
                }
            }
        }
    }
</script>

<style scoped>

</style>
