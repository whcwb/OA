<template>
  <div ref="dom" class="charts chart-bar"></div>
</template>

<script>
import echarts from 'echarts'
export default {
  name: 'ChartBar',

  mounted () {
   let   option = {
     tooltip: {
       trigger: "item",
       formatter: "{a} <br/>{b} : {c} ({d}%)"
     },
     legend: {
       orient: "vertical",
       x: "left",
       data: ["待受理", "科一待考", "科二待考", "科三待考", "科四待考", "已结业", "退学"]
     },
     toolbox: {
       show: true,
       feature: {
         mark: {
           show: true
         },
         dataView: {
           show: true,
           readOnly: true
         },
         restore: {
           show: true
         },
         saveAsImage: {
           show: true
         }
       }
     },
     calculable: true,
     series: [
       {
         name: "访问来源",
         type: "pie",
         radius: ["50%", "70%"],
         itemStyle: {
           normal: {
             label: {
               show: false
             },
             labelLine: {
               show: false
             }
           },
           emphasis: {
             label: {
               show: true,
               position: "center",
               textStyle: {
                 fontSize: "30",
                 fontWeight: "bold"
               }
             }
           }
         },
         data: [
           {
             value: 335,
             name: "待受理"
           },
           {
             value: 310,
             name: "科一待考"
           },
           {
             value: 234,
             name: "科二待考"
           },
           {
             value: 135,
             name: "科三待考"
           },
           {
             value: 154,
             name: "科四待考"
           },
           {
             value: 123,
             name: "已结业"
           },
           {
             value: 12,
             name: "退学"
           }
         ]
       }
     ],
      color:this.col.color()
      };
    let dom = echarts.init(this.$refs.dom, 'tdTheme')
    dom.setOption(option)






  }
}
</script>

<style lang="less">
.charts{
  height: 600px;
  width: 1300px;
}
</style>
