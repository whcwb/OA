<template>
      <div>
        <br>
        <br>
        <br>
        <br>
          <div class="number" style="border: 1px solid black;width: 400px;float: right;padding: 3px;line-height: 80px">
            <div align="center" style="font-size:24px;border: 1px solid black;height:80px;padding: 6px">{{carInfo.dah}}</div>
          </div>
          <div style="clear: both"></div>
          <br>
          <br>
          <br>
          <br>
          <br>
          <br>
        <br>
          <div style="border: 1px solid black;padding: 3px">
              <div class="da" style="border: 1px solid black;padding: 3px;height: 250px;font-size: 26px">
                  <div style="text-align: center">
                      <span style="font-weight: 600;font-size: 42px;">成功驾校教练车档案</span>
                  </div>
                  <br>
                  <table style="width: 100%;font-size: 24px" class="t1">
                      <tbody>
                      <tr>
                          <td align="center">车牌号码:</td>
                          <td align="center">{{carInfo.cph}}</td>
                      </tr>
                      <tr>
                          <td align="center">车辆型号:</td>
                          <td align="center">{{carInfo.clxh}}</td>
                      </tr>
                      <tr>
                          <td align="center">建档日期:</td>
                          <td align="center">{{carInfo.cjsj}}</td>
                      </tr>
                      </tbody>
                  </table>
              </div>
          </div>
          <br>
          <br>
          <br>
          <br>
          <div style="border: 1px solid black;overflow: hidden">
              <div class="da" style="padding: 2px;width: 49%;margin: 2px;float: left">
                  <table style="width: 100%;height: 500px;font-size: 20px" border="1" cellspacing="0">
                      <tbody>
                      <tr>
                          <td align="center">1</td>
                          <td>车辆基本情况登记表</td>
                          <td align="center" width="50px">1</td>
                          <td align="center" width="50px">1</td>
                      </tr>
                      <tr>
                          <td align="center">2</td>
                          <td>车辆技术参数</td>
                          <td align="center">1</td>
                          <td align="center">1</td>
                      </tr>
                      <tr>
                          <td align="center">3</td>
                          <td>车辆维修登记表</td>
                          <td align="center">1</td>
                          <td align="center">1</td>
                      </tr>
                      <tr>
                          <td align="center">4</td>
                          <td>车辆等级评定登记表</td>
                          <td align="center">1</td>
                          <td align="center">1</td>
                      </tr>
                      <tr>
                          <td align="center">5</td>
                          <td>车辆行驶里程表</td>
                          <td align="center">1</td>
                          <td align="center">1</td>
                      </tr>
                      <tr>
                          <td align="center">6</td>
                          <td>车辆交通事故登记表</td>
                          <td align="center">1</td>
                          <td align="center">1</td>
                      </tr>
                      <tr>
                          <td align="center">7</td>
                          <td>机动车登记证书</td>
                          <td align="center">1</td>
                          <td align="center">1</td>
                      </tr>
                      <tr>
                          <td align="center">8</td>
                          <td>机动车销售（购车）发票</td>
                          <td align="center">1</td>
                          <td align="center">1</td>
                      </tr>
                      <tr>
                          <td align="center">9</td>
                          <td>购置税发票</td>
                          <td align="center">1</td>
                          <td align="center">1</td>
                      </tr>
                      <tr>
                          <td align="center">10</td>
                          <td>购置税完税证明</td>
                          <td align="center">1</td>
                          <td align="center">1</td>
                      </tr>
                      </tbody>
                  </table>
              </div>
              <div class="da" style="padding: 2px;width: 49%;margin: 2px;float: right">
                  <table style="width: 100%;height: 500px;font-size: 20px" border="1" cellspacing="0">
                      <tbody>
                      <tr>
                          <td align="center">11</td>
                          <td>产品合格证</td>
                          <td align="center"  width="50px">1</td>
                          <td align="center" width="50px">1</td>
                      </tr>
                      <tr>
                          <td align="center">12</td>
                          <td>车辆识别码拓膜</td>
                          <td align="center">1</td>
                          <td align="center">1</td>
                      </tr>
                      <tr>
                          <td align="center">13</td>
                          <td>保险保单</td>
                          <td align="center">库</td>
                          <td></td>
                      </tr>
                      <tr>
                          <td align="center">14</td>
                          <td></td>
                          <td align="center"></td>
                          <td align="center"></td>
                      </tr>
                      <tr>
                          <td align="center">15</td>
                          <td></td>
                          <td></td>
                          <td></td>
                      </tr>
                      <tr>
                          <td align="center">16</td>
                          <td></td>
                          <td></td>
                          <td></td>
                      </tr>
                      <tr>
                          <td align="center">17</td>
                          <td></td>
                          <td></td>
                          <td></td>
                      </tr>
                      <tr>
                          <td align="center">18</td>
                          <td></td>
                          <td></td>
                          <td></td>
                      </tr>
                      <tr>
                          <td align="center">19</td>
                          <td></td>
                          <td></td>
                          <td></td>
                      </tr>
                      <tr>
                          <td align="center">20</td>
                          <td></td>
                          <td></td>
                          <td></td>
                      </tr>
                      </tbody>
                  </table>
              </div>
          </div>
      </div>
</template>

<script>
    export default {
        name: "carPage0",
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
