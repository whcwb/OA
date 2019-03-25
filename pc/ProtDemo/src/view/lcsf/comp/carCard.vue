<template>
  <div style="padding: 12px">
    <Card style="width:350px">
      <div slot="title" class="CardTit">
        <div class="carNum">
          {{carMess.clBh}}
        </div>
        <div class="carTyp">
          {{carMess.clCx}}
        </div>
        <Row type="flex" justify="end">
          <Col span="5">
            <!--<Tag style="font-size: 16px" :class="carMess.clZt=='01'?'clZtTag':'clZtTag1'" :color="carMess.clZt=='01'?'red':'#66CD00'">{{clZt(carMess.clZt)}}</Tag>-->
            <Tag style="font-size: 16px" class="clZtTag1" :color="carMess.clZt=='01'?'red':'#66CD00'">{{clZt(carMess.clZt)}}</Tag>
          </Col>
          <Col span="6">
            <Row type="flex" justify="space-between">
              <!--<Col span="9">-->
                <!--<Button v-if="carMess.clZt == '00'" style="border-radius: 45px" type="warning"  @click="carClick">发</Button>-->
                <!--&lt;!&ndash;<Button v-if="carMess.clZt == '01'" type="success" size="small" @click="overClick(carMess.lcJl.id)">还</Button>&ndash;&gt;-->
              <!--</Col>-->
              <Col span="9">
                <Tooltip content="练车记录" placement="top">
                  <Button style="border-radius: 45px" type="primary" @click="his">录</Button>
                </Tooltip>
              </Col>
            </Row>
          </Col>
        </Row>
        <!--空闲中success  训练中warning  维修中error  保养中primary  -->
      </div>
      <div class="carBody">
        <Icon v-if="carMess.clImg==''" type="ios-car" :size="200" color="#b3acac"/>
        <img v-else :src="apis.getImgUrl+carMess.clImg" style="height: 194px;cursor:pointer" alt="" @click="handleView(apis.getImgUrl+carMess.clImg)">
        <!--<div class="carTif" v-if="carMess.clZt == '01'">-->
          <!--<car-tif></car-tif>-->
        <!--</div>-->
        <!--<img src="./lbjn.jpg" width='100%' height='100%' alt="">-->
        <div class="carMes" v-if="carMess.clZt=='01'">
          <div>
            <span>
              开始时间：<Tag color="blue">{{carMess.lcJl.kssj}}</Tag>
            </span>
          </div>
          <div>
            <span>
              累计时长：<Tag color="blue">{{M_time}}</Tag>
            </span>
          </div>
          <div>
          <span>
          累计费用：<Tag color="blue">{{zj(carMess.zj)}}元</Tag>
          </span>
          </div>
          <Divider style="margin: 12px 0"></Divider>
          <div v-if="carMess.zgXm!=''">
            安全人员：
            <Tag color="volcano">{{carMess.zgXm}}</Tag>
          </div>
          <div style="" v-if="carMess.clZt == '01'">
            教练人员：
            <Tag color="blue">{{carMess.lcJl.jlJx}}__{{carMess.lcJl.jlXm}}</Tag>
          </div>
        </div>
      </div>
    </Card>
    <Modal title="预览" v-model="visible" style="text-align: center">
      <img :src="imgName" v-if="visible" style="width: 100%">
    </Modal>
  </div>
</template>

<script>
  import carTif from './carTif'
  import moment from 'moment'
  export default {
    name: "carCard",
    components:{carTif},
    props: {
      carMess: {
        type: Object,
        default: {},
      }
    },
    data() {
      return {
        h:'00',
        m:'00',
        s:'00',
        M_time:'',
        visible:false,
      }
    },
    computed:{
      LcTime:function(){
        return this.$store.state.app.LcTime
      }
    },
    watch:{
      "LcTime":function (n,o) {
        if (this.carMess.clZt == '01'){
          this.gethms()
        }
      }
    },
    created(){
      if (this.carMess.clZt == '01'){
        this.gethms()
      }
    },
    methods: {
      buling(e){
        if((''+e).length<2){
          return '0'+e
        }else {
          return e
        }
      },
      gethms(){
        this.carMess.dqsc++
        this.h = this.buling(parseInt(parseInt(this.carMess.dqsc/60)/60));
        this.m = this.buling(parseInt(this.carMess.dqsc/60)%60);
        this.s = this.buling(this.carMess.dqsc%60)
        this.M_time = this.h+':'+this.m+':'+this.s;
        if(this.carMess.lcJl.lcLx == '00'){
          if (this.s === '00'){
            this.carMess.zj = Math.round(this.carMess.dqsc *this.carMess.dj/(60*60))
          }
        }

        // try{
        //   if (this.carMess.lcJl && this.carMess.lcJl.lcLx == '00'){
        //     let nowTime = moment(this.LcTime);
        //     let kssjTime = moment(this.carMess.lcJl.kssj, "YYYY-MM-DD HH:mm:ss");
        //     let subTime = moment(nowTime - kssjTime).subtract(8,'hour');
        //     let fsSubTime = subTime.format('HH:mm:ss');
        //     this.M_time = fsSubTime;
        //     this.s = subTime.format('ss');
        //     if (this.s === '00'){
        //       this.carMess.zj = this.carMess.dqsc * this.carMess.dj/(60*60)
        //       // this.carMess.zj = this.carMess.dqsc * this.carMess.dj1
        //
        //     }
        //   }
        // }catch (e) {
        //
        // }
      },
      handleView (name) {
        console.log(name);
        this.imgName = name;
        this.visible = true;
      },
      dqsc(val) {
        return val
      },
      zj(val){
        return parseInt(val)
      },
      carClick() {
        this.$emit('carClick', this.carMess.id, this.carMess.clCx,this.carMess.zgId)
      },
      overClick(id) {
        this.swal({
          title: '训练结束？',
          type: 'warning',
          confirmButtonText: '还车',
          cancelButtonText: '取消',
          showCancelButton: true,
        }).then((res) => {
          if (res.value) {
            this.$http.post('/api/lcjl/updateJssj', {id: id}).then(res => {
              if (res.code == 200){
                this.$parent.getCarList()
                this.print(res.result)
              }
            }).catch(err => {
            })
          }
        })
      },
      print(mess) {
        this.swal({
          title: '训练结束,是否打印单据',
          type: 'question',
          confirmButtonText: '打印',
          cancelButtonText: '取消',
          showCancelButton: true,
        }).then((res) => {
          if (res.value) {
            this.$emit('print', mess)
          }
        })
      },
      clZt(val) {
        return this.dictUtil.getValByCode(this, 'ZDCLK1044', val)
      },
      his(){
        this.$emit('his')
      }
    }
  }
</script>

<style lang="less">
  .CardTit {
    width: 100%;
    position: relative;
  }

  .carBody {
    position: relative;
    text-align: center;
    .carMes {
      position: absolute;
      left: 0px;
      top: 0px;
      text-align: left;
      font-weight: 600;
      font-size: 16px;
      /*color: #57c5f7;*/
      color: #da826c;
      span {
        /*background-color: #fff;*/
      }
    }
    .carTif{
      position: absolute;
      top:0px;
      right:0px;
    }
  }

  .carTyp {
    text-align: center;
    position: absolute;
    left: 80px;
    top: 0px;
    color: #fff;
    font-size: 28px;
    font-weight: 600;
    background-color: #57c5f7;
    width: 80px;
    height: 40px;
    line-height: 40px;
    border-radius: 40px 0 40px 0;
  }

  .carNum {
    text-align: center;
    position: absolute;
    left: -10px;
    top: -10px;
    color: #fff;
    font-size: 40px;
    font-weight: 600;
    background-color: #ff9900;
    width: 70px;
    height: 70px;
    line-height: 70px;
    border-radius: 70px;
  }
  .clZtTag{
    color: #f2f2f2;
    height: 35px;
    width: 80px;
    line-height: 35px;
    text-align: center;
    border-radius: 45px;
    background-color: #EE3B3B
    /*animation: carTif 1s linear infinite;*/
  }
  .clZtTag1{
    /*color: #f2f2f2;*/
    /*background-color: #66CD00;*/
    height: 35px;
    width: 60px;
    line-height: 35px;
    text-align: center;
    border-radius: 45px;
  }
  /*@keyframes carTif {*/
    /*from { background-color: #EE0000}*/
    /*50%  { background-color: #EE3B3B;}*/
    /*to   { background-color: #0000AA}*/
  /*}*/
</style>
