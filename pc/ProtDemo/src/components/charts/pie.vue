<template>
  <!--<div ref="dom" class="charts chart-pie"></div>-->
  <div id="pieIndex" style="height: 100%;width: 100%">

  </div>
</template>

<script>
  import echarts from 'echarts'

  export default {
    name: 'ChartPie',
    props: {
      // value: Array,
      time: {
        type: Object,
        default: {
          startTime: '',
          endTime: ''
        }
      },
      text: {
        type: String,
        default: '学员状态数据统计'
      },
      subtext: String
    },
    data() {
      return {
        value: []
      }
    },
    created() {
    },
    mounted() {
      this.getBM()
    },
    methods: {
      getBM() {
        this.$http.post(this.apis.DATASTA.dateP,this.time).then((res) => {
          if (res.code == 200 && res.result) {
            this.value = res.result
            this.initChart()
          }
        }).catch((err) => {

        })
      },
      initChart() {
        this.$nextTick(() => {
          let legend = this.value.map(_ => _.name)
          let option = {
            title: {
              text:this.time.startTime + '—' + this.time.endTime + this.text,
              x: "center",
              textStyle: {
                fontSize: 31
              }
            },
            tooltip: {
              trigger: 'item',
              formatter: '{b} : {c}'
            },
            legend: {
              orient: 'horizontal',
              data: legend,
              x: "center",
              y: "bottom"
            },
            series: [
              {
                type: 'pie',
                radius: '60%',
                center: ['50%', '50%'],
                data: this.value,
                itemStyle: {
                  normal: {
                    label: {
                      formatter: "{b}{c}人",
                      show: true,
                      position: "bottom",
                      distance: 0.01,
                      textStyle: {
                        fontSize: 26,
                        fontStyle: "normal"
                      }
                    },
                    labelLine: {
                      show: false
                    },
                    borderWidth: 0
                  },
                  emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                  }
                },
                startAngle: 0,
              }
            ],
            color: ["#ff7f50", "#87cefa", "#da70d6", "#32cd32", "#6495ed", "#ff69b4", "#ba55d3", "#cd5c5c", "#ffa500", "#40e0d0", "#1e90ff", "#ff6347", "#7b68ee", "#00fa9a", "#ffd700", "#6699FF", "#ff6666", "#3cb371", "#b8860b", "#30e0e0"]
          }

          //  let dom = echarts.init(this.$refs.dom, 'tdTheme')
          // dom.setOption(option)
          var myChart = echarts.init(document.getElementById('pieIndex'));
          myChart.setOption(option)
        })
      }
    }

  }
</script>

<style lang="less">
  .charts {
    //
  }
</style>
