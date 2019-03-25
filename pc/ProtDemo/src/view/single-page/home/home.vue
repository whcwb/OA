<style lang="less">
  .cardSty{
    margin: 50px 0;
  }
  .HomeBox{
    background-image: url("./homeBack.jpg");
    background-repeat:no-repeat;
    background-size: cover;
    background-position: center;
  }
  .HomeMessTit{
    width: 100%;
    text-align: center;
    position: absolute;
    top: 15%;
    left: 38%;
    transform: translate(-50%,-50%);
    font-size: 60px;
    color: #fff;
    font-weight: 700;
  }
</style>
<template>
  <div class="HomeBox" style="position: relative;min-height: 100%">
    <!--<div class="HomeMessTit">-->
      <!--欢迎使用明涛驾校管理服务平台-->
    <!--</div>-->
    <!--<sel :option="option"  :opVal="['key','val']" @inputVal="inputVal"></sel>-->
    <!--<radio-car></radio-car>-->
    <Row :gutter="20" v-if="false">
      <i-col span="4" v-if="typNumlist.newborn">

        <Row class-name="cardSty">
          <i-col span="24" style="height: 120px;padding: 0">
            <infor-card shadow :color="col.color(1)" icon="md-person-add" :icon-size="36">
              <count-to :end="typNumlist.newborn.min" count-class="count-style"/>
              <p>小车_新生量</p>
            </infor-card>
          </i-col>
        </Row>

        <Row class-name="cardSty">
          <i-col span="24" style="height: 120px;padding: 0">
            <infor-card shadow :color="col.color(16)" icon="md-person-add" :icon-size="36">
              <count-to :end="typNumlist.newborn.max" count-class="count-style"/>
              <p>大车_新生量</p>
            </infor-card>
          </i-col>
        </Row>

        <Row class-name="cardSty">
          <i-col span="24" style="height: 120px;padding: 0">
            <infor-card shadow :color="col.color(4)" icon="md-map" :icon-size="36">
              <count-to :end="typNumlist.SL.min" count-class="count-style"/>
              <p>小车_受理量</p>
            </infor-card>
          </i-col>
        </Row>

        <Row class-name="cardSty">
          <i-col span="24" style="height: 120px;padding: 0">
            <infor-card shadow :color="col.color(5)" icon="md-map" :icon-size="36">
              <count-to :end="typNumlist.SL.max" count-class="count-style"/>
              <p>大车_受理量</p>
            </infor-card>
          </i-col>
        </Row>

      </i-col>

      <i-col span="16">
        <Card shadow>
          <chart-pie :time="params" :style="{height:AF.getPageHeight()-300+'px'}"></chart-pie>
        </Card>
      </i-col>

      <i-col span="4" style="height: 120px;" v-if="typNumlist.KS">

        <Row class-name="cardSty">
          <i-col span="24" style="height: 120px;padding: 0">
            <infor-card shadow :color="col.color(10)" icon=" iconfont icon-lilun" :icon-size="36">
              <count-to :end="typNumlist.KS.one" count-class="count-style"/>
              <p>科目一考试合格人数</p>
            </infor-card>
          </i-col>
        </Row>

        <Row class-name="cardSty">
          <i-col span="24" style="height: 120px;padding: 0">
            <infor-card shadow :color="col.color(11)" icon=" iconfont icon-jineng-xiaoicon" :icon-size="36">
              <count-to :end="typNumlist.KS.two" count-class="count-style"/>
              <p>科目二考试合格人数</p>
            </infor-card>
          </i-col>
        </Row>

        <Row class-name="cardSty">
          <i-col span="24" style="height: 120px;padding: 0">
            <infor-card shadow :color="col.color(12)" icon=" iconfont icon-jiashi" :icon-size="36">
              <count-to :end="typNumlist.KS.thr" count-class="count-style"/>
              <p>科目三考试合格人数</p>
            </infor-card>
          </i-col>
        </Row>

        <Row class-name="cardSty">
          <i-col span="24" style="height: 120px;padding: 0">
            <infor-card shadow :color="col.color(17)" icon=" iconfont icon-bylx" :icon-size="36">
              <count-to :end="over" count-class="count-style"/>
              <p>毕业人数</p>
            </infor-card>
          </i-col>
        </Row>

      </i-col>
    </Row>

  </div>
</template>

<script>
  import InforCard from '_c/info-card'
  import CountTo from '_c/count-to'
  import {ChartBar, ChartPie} from '_c/charts'
  import Example from './example.vue'

  // import RadioCar from '../../lcsf/comp/RadioCar'

  // import sel from '../../../components/select'
  export default {
    name: 'home',
    components: {
      InforCard,
      CountTo,
      ChartPie,
      ChartBar,
      Example,
      // RadioCar,
      // sel
    },
    data() {
      return {
        typNumlist: {},
        over:0,
        params:{
          startTime:'',
          endTime:this.AF.trimDate()
        },
        option: [
          {key: 1, val: 1},
          {key: 2, val: 2},
          {key: 3, val: 3},
          {key: 4, val: 4},
          {key: 5, val: 5},
          {key: 6, val: 6},
          {key: 7, val: 7},
          {key: 8, val: 8},
          {key: 9, val: 9},
          {key: 10, val: 10},
        ]
      }
    },
    created() {
    },
    mounted() {
    },
    methods: {
      inputVal(val){
        // console.log('事件回调取值',val);
      },
      getBM() {
        this.$http.post(this.apis.DATASTA.dateO,this.params).then((res) => {
          if (res.code == 200) {
            this.typNumlist = {
              newborn:{
                max:res.result['大车报名人数'],
                min:res.result['小车报名人数']
              },
              SL:{
                max:res.result['大车已受理学员'],
                min:res.result['小车已受理学员']
              },
              KS:{
                one:res.result['科目一合格学员'],
                two:res.result['科目二合格人数'],
                thr:res.result['科目三合格人数']
              }
            }
          }
        }).catch((err) => {

        })
      },
      getData(){
        this.$http.post(this.apis.DATASTA.dateP,this.params).then((res) => {
        if (res.code == 200 && res.result) {
          res.result.forEach((item,index)=>{
            if(item.name == '已结业人数'){
              this.over = item.value
            }
          })
        }
      }).catch((err) => {

      })
      }
    }
  }
</script>

<style lang="less">
  .count-style {
    font-size: 50px;
  }
</style>
