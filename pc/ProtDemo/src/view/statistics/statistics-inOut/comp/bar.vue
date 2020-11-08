<template>
  <div ref="dom" class="charts chart-bar"></div>
</template>

<script>
  import echarts from 'echarts'

  export default {
  name: 'ChartBar',

  mounted(){
      this.getNSRList()
  },
  methods:{
    build( s,z){
      let   option = {
        title: {
          text: "收支统计"
        },
        tooltip: {
          trigger: "axis"
        },
        legend: {
          data: ["支出", "收入"]
        },
        toolbox: {
          feature: {
            mark: {
              show: true
            },
            dataView: {
              show: true,
              readOnly: false
            },
            restore: {
              show: true
            },
            saveAsImage: {
              show: true
            },
            dataZoom: {
              show: false
            },
            magicType: {
              show: false
            }
          },
          show: true
        },
        calculable: true,
        xAxis: [
          {
            type: "category",
            axisLine: {
              onZero: false
            },
            data: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"]
          }
        ],
        grid: {
          y: 70
        },
        yAxis: [
          {
            type: "value",
            name: "收入（单位：万）",
            min: 0
          },
          {
            type: "value",
            name: "支出（单位：万）",
            min: 0,
            nameLocation: "end"
          }
        ],
        series: [
          {
            type: "line",
            name: "支出",
            data:z
            // data: [2, 4.9, 7, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20, 6.4, 3.3]
          },
          {
            type: "line",
            name: "收入",
            data:s
            // data: [2.6, 5.9, 9, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6, 2.3]
          }
        ]
      };
      let dom = echarts.init(this.$refs.dom, 'tdTheme')
      dom.setOption(option)
    },
    getNSRList(){
      var s = []
      this.$http.post(this.apis.COUNT.NSR).then( (res)=>{
        if(res.code == 200){
          s = [
            res.result['2018-01']/10000,
            res.result['2018-02']/10000,
            res.result['2018-03']/10000,
            res.result['2018-04']/10000,
            res.result['2018-05']/10000,
            res.result['2018-06']/10000,
            res.result['2018-07']/10000,
            res.result['2018-08']/10000,
            res.result['2018-09']/10000,
            res.result['2018-10']/10000,
            res.result['2018-11']/10000,
            res.result['2018-12']/10000,
          ]
          this.getNZCList(s)
        }
      })
    },
    getNZCList(s){
      var z = []
      this.$http.post(this.apis.COUNT.NZC).then( (res)=>{
        if(res.code == 200){
          z = [
            res.result['2018-01']/10000,
            res.result['2018-02']/10000,
            res.result['2018-03']/10000,
            res.result['2018-04']/10000,
            res.result['2018-05']/10000,
            res.result['2018-06']/10000,
            res.result['2018-07']/10000,
            res.result['2018-08']/10000,
            res.result['2018-09']/10000,
            res.result['2018-10']/10000,
            res.result['2018-11']/10000,
            res.result['2018-12']/10000,
          ]
          this.build( s,z)
        }
      })
    },
  }
}
</script>

<style lang="less">
.charts{
  height: 600px;
  width: 1300px;
}
</style>
