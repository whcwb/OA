<template>
  <div>
      <div class="box_col">
        <Row>
          <Col span="12">
            <pager-tit title="计时培训"></pager-tit>
          </Col>
          <Col span="12">
            <Tag color="red">计时培训数据每日24:00更新</Tag>
          </Col>
        </Row>

      </div>
      <Table :height="AF.getPageHeight()-280"  :columns="columns9" :data="data1"></Table>
  </div>
</template>

<script>
    export default {
        name: "index",
      data(){
          return{
            columns9: [
              {
                type:'index',
                title: '序号',
                width:80,
                align:'center'
              },
              {
                title: '姓名',
                key: 'name',
                className: 'demo-table-info-column',
                align:'center'
              },
              {
                title: '身份证号码',
                key: 'sfzhm',
                width:180,
                align:'center'
              },
              {
                title: '培训科目',
                key: 'pxkm',
                align:'center'
              },
              {
                title: '培训时段',
                key: 'pxsd',
                align:'center'
              },
              {
                title: '车型',
                key: 'cx',
                align:'center'
              },
              {
                title: '培训时长(时)',
                key: 'pxsc',
                align:'center'
              },
              {
                title: '单价(元/小时)',
                key: 'dj',
                align:'center'
              },
              {
                title: '合计(元)',
                key: 'hj',
                align:'center'
              }
            ],
            data1: [
              {
                name: '张凡',
                sfzhm: '341204199603218765',
                pxkm: '科目二',
                pxsd:'普通时段',
                pxsc:'16',
                cx:'B2',
                dj:'150',
                hj:'2400'
              },
              {
                name: '张震',
                sfzhm: '341204199603218765',
                pxkm: '科目二',
                pxsd:'普通时段',
                pxsc:'5',
                cx:'C1',
                dj:'100',
                hj:'500'
              },
              {
                name: '李丽',
                sfzhm: '421204187603218225',
                pxkm: '科目二',
                pxsd:'普通时段',
                pxsc:'10',
                cx:'C1',
                dj:'100',
                hj:'1000'
              },
              {
                name: '张青山',
                sfzhm: '342522198603171765',
                pxkm: '科目二',
                pxsd:'周末时段',
                pxsc:'3',
                cx:'C1',
                dj:'120',
                hj:'360'
              },
              {
                name: '糜乐',
                sfzhm: '523415199801211011',
                pxkm: '科目三',
                pxsd:'普通时段',
                pxsc:'7',
                cx:'C1',
                dj:'120',
                hj:'840'
              },
              {
                name: '王子文',
                sfzhm: '538859198910042424',
                pxkm: '科目三',
                pxsd:'普通时段',
                pxsc:'4',
                cx:'C2',
                dj:'120',
                hj:'480'
              },
              {
                name: '杨翔',
                sfzhm: '432359198510142424',
                pxkm: '科目三',
                pxsd:'普通时段',
                pxsc:'2',
                cx:'C2',
                dj:'120',
                hj:'240'
              },
              {
                name: '刘正安',
                sfzhm: '423185198007042517',
                pxkm: '科目二',
                pxsd:'周末时段',
                pxsc:'2',
                cx:'C2',
                dj:'150',
                hj:'300'
              },
              {
                name: '王倩竹',
                sfzhm: '412346199206192000',
                pxkm: '科目二',
                pxsd:'普通时段',
                pxsc:'3',
                cx:'C1',
                dj:'100',
                hj:'300'
              },
              {
                name: '雷暴',
                sfzhm: '642221199501020910',
                pxkm: '科目二',
                pxsd:'普通时段',
                pxsc:'2',
                cx:'C1',
                dj:'100',
                hj:'200'
              },
              {
                name: '陈琴',
                sfzhm: '411524199710148429',
                pxkm: '科目三',
                pxsd:'普通时段',
                pxsc:'4',
                cx:'C2',
                dj:'120',
                hj:'480'
              },
              {
                name: '肖鄂锐',
                sfzhm: '421302199808250416',
                pxkm: '科目二',
                pxsd:'普通时段',
                pxsc:'1',
                cx:'C2',
                dj:'120',
                hj:'120'
              },
              {
                name: '桂驲',
                sfzhm: '421182199706055514',
                pxkm: '科目三',
                pxsd:'周末时段',
                pxsc:'3',
                cx:'C2',
                dj:'150',
                hj:'450'
              },
              {
                name: '王静',
                sfzhm: '620522199804251510',
                pxkm: '科目三',
                pxsd:'周末时段',
                pxsc:'1',
                cx:'C2',
                dj:'150',
                hj:'150'
              },
              {
                name: '黄雪莹',
                sfzhm: '35030119930601010X',
                pxkm: '科目三',
                pxsd:'普通时段',
                pxsc:'4',
                cx:'C2',
                dj:'120',
                hj:'480'
              },
              {
                name: '张之海',
                sfzhm: '420111198106215632',
                pxkm: '科目三',
                pxsd:'普通时段',
                pxsc:'1',
                cx:'C2',
                dj:'120',
                hj:'120'
              },
              {
                name: '李源潮',
                sfzhm: '420113198803200033',
                pxkm: '科目三',
                pxsd:'普通时段',
                pxsc:'2',
                cx:'C2',
                dj:'120',
                hj:'240'
              },{
                name: '刘艳萍',
                sfzhm: '420117198403023520',
                pxkm: '科目三',
                pxsd:'普通时段',
                pxsc:'2',
                cx:'C2',
                dj:'120',
                hj:'240'
              },
              {
                name: '彭辉',
                sfzhm: '420117199304213120',
                pxkm: '科目三',
                pxsd:'普通时段',
                pxsc:'1',
                cx:'C2',
                dj:'120',
                hj:'120'
              },
              {
                name: '郑自强',
                sfzhm: '420113198803200033',
                pxkm: '科目三',
                pxsd:'普通时段',
                pxsc:'3',
                cx:'C2',
                dj:'120',
                hj:'360'
              },{
                name: '李亚祥',
                sfzhm: '421181198512140023',
                pxkm: '科目三',
                pxsd:'普通时段',
                pxsc:'8',
                cx:'C2',
                dj:'120',
                hj:'960'
              },{
                name: '温华',
                sfzhm: '420124197205144734',
                pxkm: '科目三',
                pxsd:'普通时段',
                pxsc:'5',
                cx:'C1',
                dj:'120',
                hj:'600'
              },
              {
                name: '徐凤年',
                sfzhm: '420124197410144717',
                pxkm: '科目三',
                pxsd:'周末时段',
                pxsc:'4',
                cx:'C2',
                dj:'150',
                hj:'600'
              },
              {
                name: '李富真',
                sfzhm: '420114198701153949',
                pxkm: '科目三',
                pxsd:'普通时段',
                pxsc:'4',
                cx:'C1',
                dj:'120',
                hj:'480'
              },
              {
                name: '饶玉',
                sfzhm: '420101198810257059',
                pxkm: '科目三',
                pxsd:'普通时段',
                pxsc:'6',
                cx:'C2',
                dj:'120',
                hj:'720'
              },
              {
                name: '张云',
                sfzhm: '320204199301112165',
                pxkm: '科目四',
                pxsd:'普通时段',
                pxsc:'2',
                cx:'C1',
                dj:'10',
                hj:'20'
              },
            ],
          }
      },
      methods: {
        rowClassName (row, index) {
          if (index === 1) {
            return 'demo-table-info-row';
          } else if (index === 3) {
            return 'demo-table-error-row';
          }
          return '';
        }
      }
    }
</script>

<style scoped>
  .ivu-table .demo-table-info-row td{
    background-color: #2db7f5;
    color: #fff;
  }
  .ivu-table .demo-table-error-row td{
    background-color: #ff6600;
    color: #fff;
  }
  .ivu-table td.demo-table-info-column{
    background-color: #2db7f5;
    color: #fff;
  }
  .ivu-table .demo-table-info-cell-name {
    background-color: #2db7f5;
    color: #fff;
  }
  .ivu-table .demo-table-info-cell-age {
    background-color: #ff6600;
    color: #fff;
  }
  .ivu-table .demo-table-info-cell-address {
    background-color: #187;
    color: #fff;
  }
</style>
