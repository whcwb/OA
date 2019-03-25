<style>
    table td{
        text-align: center;
    }
</style>
<template>
      <div>
          <div style="font-size: 28px;font-weight: 700">表2 车辆技术参数表</div>
        <br>
          <table border="1" cellspacing="0" style="width: 100%" class="jt2">
              <tr>
                  <td>车辆类型</td>
                  <td width="20%">{{carInfo.cllx}}</td>
                  <td>厂牌型号</td>
                  <td width="20%">{{carInfo.ppxh}}</td>
                  <td>出厂日期/产地</td>
                  <td width="20%">{{carInfo.ccrq}}/</td>
              </tr>
              <tr>
                  <td>VIN（或车架）号</td>
                  <td>{{carInfo.clsbm}}</td>
                  <td>底盘厂牌型号</td>
                  <td> </td>
                  <td>客车类型等级</td>
                  <td></td>
              </tr>
              <tr>
                  <td>车辆外廓尺寸</td>
                  <td>长 {{carInfo.wkc}} 宽 {{carInfo.wkk}} 高 {{carInfo.wcg}}</td>
                  <td>总质量</td>
                  <td>{{carInfo.zzl}}</td>
                  <td>座位排列</td>
                  <td></td>
              </tr>
              <tr>
                  <td>核定载质量/乘员数</td>
                  <td>{{carInfo.hdzl}}/{{carInfo.jsszks}}</td>
                  <td>核定牵引总质量</td>
                  <td>{{carInfo.zqyzzl}}</td>
                  <td>车轴数/驱动轴数</td>
                  <td></td>
              </tr>
              <tr>
                  <td>发动机厂牌型号</td>
                  <td>{{carInfo.fdjxh}}</td>
                  <td>发动机号码</td>
                  <td>{{carInfo.fdjh}}</td>
                  <td>燃料种类</td>
                  <td>{{carInfo.rlzl}}</td>
              </tr>
              <tr>
                  <td>发动机功率</td>
                  <td>{{carInfo.gl}}</td>
                  <td>发动机排量</td>
                  <td>{{carInfo.pl}}</td>
                  <td>排放标准</td>
                  <td></td>
              </tr>
              <tr>
                  <td>驱动形式</td>
                  <td></td>
                  <td>轮胎数/规格</td>
                  <td>{{carInfo.ltsl}}/{{carInfo.ltgg}}</td>
                  <td>前照灯制式</td>
                  <td></td>
              </tr>
              <tr>
                  <td>变速器形式</td>
                  <td></td>
                  <td>缓速器</td>
                  <td></td>
                  <td>转向器</td>
                  <td>{{carInfo.zxfs}}</td>
              </tr>
              <tr>
                  <td>行车制动形式</td>
                  <td colspan="5"></td>
              </tr>
              <tr>
                  <td>悬挂形式</td>
                  <td colspan="5"></td>
              </tr>
              <tr>
                  <td>其他配置</td>
                  <td colspan="5"></td>
              </tr>
          </table>
          <div><span>说明：请填写或选择车辆技术各参数终有关内容，符合的请在选择项后以“√”表示</span></div>
      </div>
</template>

<script>
    export default {
        name: "carPage2",
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
