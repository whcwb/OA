<style>
    table tr{
        height: 20px;
    }
</style>
<template>
      <div>
          <div style="font-size: 28px;font-weight: 700">表3 车辆维修登记表</div>
        <br>
          <table border="1" cellspacing="0" style="width: 100%" class="jt2">
              <tr>
                  <td>维修日期</td>
                  <td>维修类别</td>
                  <td>小修/二级维护主要附加作业内容/大修/总成修理内容</td>
                  <td>维修单位</td>
                  <td>登记人签名</td>
              </tr>
              <tr>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
              </tr>
              <tr>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
              </tr>
              <tr>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
              </tr>
              <tr>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
              </tr>
              <tr>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
              </tr>
              <tr>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
              </tr>
              <tr>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
              </tr>
              <tr>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
              </tr>
              <tr>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
              </tr>
              <tr>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
              </tr>
              <tr>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
              </tr>
          </table>
          <div><span>说明：1.车辆维修类别栏应填小修，一级维护，二级维护，大修或总成修理</span></div>
          <div><span>&nbsp2.主要部件是指客车车身，货车驾驶室和货箱，发动机，离合器，变速器，传动轴，前后桥，转向器，车架等部件</span></div>
      </div>
</template>

<script>
    export default {
        name: "carPage3",
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
