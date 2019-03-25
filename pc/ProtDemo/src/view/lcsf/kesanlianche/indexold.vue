<template>
  <div class="box_col">
    <Row style="margin-bottom: 18px" type="flex" align="bottom">
      <Col span="6">
        <pager-tit title="科三模训" style="float: left"></pager-tit>
        <div style="float: left;margin-top: 8px;">
          <span
            style="width: 100px;height: 80px;background-color: #ff9900;color:white;padding:6px;border-radius: 4px;margin-left: 16px;cursor: pointer;"
            @click="formData.clZt = '',getCarList()">共{{CarList.length}}台</span>
          <span
            style="width: 100px;height: 80px;background-color: red;color:white;padding:6px;border-radius: 4px;margin-left: 16px;cursor: pointer;"
            @click="formData.clZt = '01',getCarList()">在训{{zxNum}}台</span>
          <span
            style="width: 100px;height: 80px;background-color: #66CD00;color:white;padding:6px;border-radius: 4px;margin-left: 16px;cursor: pointer;"
            @click="formData.clZt = '00',getCarList()">空闲{{xxNum}}台</span>
        </div>
      </Col>
      <Col span="18">
        <Row type="flex" justify="end" :gutter="8">
          <Col span="5" align="right" style="font-size: 24px;color: #2baee9">
            <div @click="showOrderList"> 当前排队中
              <Button style="font-size: 20px;font-weight: 600" type="error">{{yyrs}}</Button>
            </div>
          </COl>
          <Col span="2" align="center">
            <Button type="success" style="border-radius: 35px;font-size: 20px" @click="yyClick">预</Button>
          </Col>
          <Col span="2" align="center">
            <Button type="error" style="border-radius: 35px;font-size: 20px" @click="faCar">发</Button>
          </Col>
          <Col span="2" align="center">
            <Button size="large" style="border-radius: 35px;font-size: 20px" type="warning"
                    @click="giveCar.overCar(v,'3'),printClose=true">
              还
            </Button>
          </Col>
          <Col span="3">
            <Input v-model="formData.clBh" size="large" placeholder="请输入车辆编号" clearable/>
            <br>
          </Col>
          <Col span="3">
            <Select v-model="formData.clCx" clearable placeholder="请选择车型" @on-change="getCarList">
              <Option v-if="item.key!='02'" v-for="item in dictUtil.getByCode(v,'ZDCLK0040')" :value="item.key"
                      :key="item.index">{{ item.val }}
              </Option>
            </Select>
          </Col>
          <Col span="3">
            <Select v-model="formData.clZt" clearable placeholder="请选择车辆状态" @on-change="getCarList">
              <Option v-if="item.key!='02' && item.key!='03'" v-for="item in dictUtil.getByCode(v,'ZDCLK1044')"
                      :value="item.key" :key="item.index">{{ item.val }}
              </Option>
            </Select>
          </Col>
          <Col span="1" align="center">
            <Button type="primary" @click="getCarList">
              <Icon type="md-search"></Icon>
              <!--查询-->
            </Button>
          </Col>
        </Row>
      </Col>
    </Row>
    <div class="box_col_auto" style="background-color: #f2f2f2">
      <div class="box_row_list">
        <div v-for="(it,index) in CarList">
          <car-card :carMess="it" @carClick="carClick" @print="print" @his="his(it),printClose=false"></car-card>
        </div>
      </div>
    </div>
    <!--分配车辆-->
    <fcModel ref="fcModel" @close="close" @getCarList='getCarList'></fcModel>
    <!--<component :is="compName"  @close="close" @getCarList='getCarList'></component>-->
    <!--分配车辆_end-->
    <addOrder ref="addOrder" @close="close" @getCarList='getCarList'></addOrder>
    <yyModel ref="yyModel" @close="close" @getCarList='getCarList'></yyModel>
    <addOrder ref="addOrder" @close="close" @getCarList='getCarList'></addOrder>
    <component :is="componentName" :printClose="printClose" :hisPrintMess="hisPrintMess"
               @confirm="orderConfirm"></component>
  </div>
</template>

<script>
  import carCard from '../comp/carCard'
  import jlwh from '../comp/jlWh'
  import addjl from '../comp/addJL'
  import print from '../comp/print'
  import carStatistics from '../statistics/carStatistics'
  import PlLazy from './plLazy'
  import giveCar from '../comp/readCard'
  import orderList from '../comp/orderList'
  import fcModel from './fcModel'
  import yyModel from './yyModel'
  import addOrder from '../comp/addOrder'
  import {mapMutations} from 'vuex'

  export default {
    name: "index",
    components: {carCard, jlwh, addjl, print, carStatistics, PlLazy, orderList, yyModel, addOrder, fcModel},
    data() {
      return {
        fcdrawer: false,
        v: this,
        wxjldis: true,
        showDrawer: true,
        giveCar: giveCar,
        hisPrintMess: '',
        compName:'yyModel',
        componentName: '',
        printClose: false,
        xllist: [
          {type: 'selection', width: 60, align: 'center',},
          {title: '姓名', key: 'name', align: 'center'},
          {title: '约考时间', key: 'thirdSubTestTime', align: 'center', sortable: true},
          {
            title: '约考地点',
            align: 'center',
            render: (h, p) => {
              return h('div', p.row.testInfo.testPlace)
            }
          },
          {
            title: '车型',
            key: 'carType',
            align: 'center'
          },
          {
            title: '练车次数',
            key: 'subThreeNum',
            align: 'center',
          },
        ],
        xy: [],
        CarList: [],
        JX: [],
        zxNum: 0,
        xxNum: 0,
        JXCode: 'ZDCLK1017',
        param: {
          pageNum: 1,//当前页码
          pageSize: 99999//每页显示数
        },
        listMess: [],
        styles: {
          height: 'calc(100% - 55px)',
          overflow: 'auto',
          paddingBottom: '53px',
          position: 'static'
        },
        jlJx: '',
        formData: {
          clBh: '',
          wxjlId: '',
          lcClId: '',
          xyLx: '1',
          jlLx: '1',
          lcYj: '',
          jlId: '',
          jlJx: '',
          xyDh: '',
          xyXm: '',
          xyIds: '',
          zgId: '',
          cardNo: '',
          clCx: '',
          lcLx: '00'
        },
        KM: '3',
        formDataJL: {},
        sfaemanlist: [],
        readingCard: false,
        wxJL: [],
        bxJL: [],
        XY: [],
        sfaemanlist: [],
        wxJL: [],
        bxJL: [],
        XY: [],
        AMess: [
          {}
        ],
        Pmess: {},
        i: 0,
        list: [],
        yyrs: 0,
        IntervalKS: setInterval(() => {
          this.Ch_LcTime()
        }, 1000)
      }
    },
    watch: {},
    methods: {
      ...mapMutations([
        'set_LcTime',
        'Ch_LcTime'
      ]),
      readCar() {
        if (!!window.ActiveXObject || "ActiveXObject" in window) {
        } else {
          this.swal({
            title: '请使用IE10以上的浏览器',
            type: 'warning',
            confirmButtonText: '关闭'
          })
          return
        }
        var v = this
        this.giveCar.readCard((key, mess) => {
          if (!key) {
            if (this.readingCard) {
              let v = this
              setTimeout(() => {
                if (v.readingCard) {
                  this.readCar()
                }
              }, 200)
            }
            if (v.showFQfzkp) {
              return;
            }
            v.showFQfzkp = true;
            v.swal({
              title: mess,
              type: 'error',
              confirmButtonText: '读卡',
              cancelButtonText: '关闭',
              showCancelButton: true
            }).then((res) => {
              if (res.value) {

                v.showFQfzkp = false;
                v.readCar()
              } else {
                v.showFQfzkp = false;
                v.showQfshowFQfzkpzkp = false;
                v.readingCard = false
              }
            })
          } else {
            v.showFQfzkp = false;
            this.formData.cardNo = mess;
            this.showfcModel(mess)
          }
        })
      },
      showfcModel(mess) {
        this.$refs.fcModel.show(mess);
      },
      showOrderList() {
        this.componentName = 'orderList'
      },
      orderConfirm(e) {
        this.formData = e
        this.$refs.addOrder.show();
      },
      handleView(name) {
        this.imgName = name;
        this.visible = true;
      },
      faCar() {
        this.readCar()
        // this.formData.lcClId = val;
        // this.formData.zgId = zg;
        // this.getXY(cx)
      },
      getYYdj() {
        this.$http.get('/api/lcjl/query', {
          params: {
            kssjIsNull: '1',
            orderBy: 'cjsj asc',
            lcKm: '3',
            notShowLoading: 'true'
          }
        }).then((res) => {
          if (res.code == 200) {
            if (res.result) {
              this.yyrs = res.result.length
            } else {
              this.yyrs = 0
            }
          }
        })
      },
      close() {
        this.AMess = [{}];
        this.formData = {};
        this.XY = [];
        this.sfaemanlist = [];
      },
      open() {
      },
      print(mess) {
        this.hisPrintMess = mess
        this.componentName = 'print'
        console.log('dayin')
      },
      his(item) {//历史练车记录
        this.clId = item.id;
        this.componentName = 'carStatistics'
      },
      getXYID(arr) {
        let arrleng = arr.length - 1
        let messarr = []
        arr.forEach((item, index) => {
          messarr.push(item.id)
          if (index == arrleng) {
            this.formData.xyIds = messarr.join(',');
          }
        })
      },
      yyClick(val, cx) {
        this.$refs.yyModel.show();
        this.formData.lcClId = val;
        this.getXY(cx)
      },
      carClick(val, cx, zg) {
        console.log(val);
        console.log(zg);
        console.log('------------', this.formData)
        this.$refs.fcdrawer.show(val, zg);
        this.formData.lcClId = val;
        this.formData.zgId = zg;
        this.getXY(cx)
      },
      getXY(cx) {
        let columns = [];
        for (let r of this.xllist) {
          if (!r.key) continue
          columns.push(r.key);
        }
        columns.push("id");
        columns.push("phone");
        let p = {carType: cx, tableColumns: columns, notShowLoading: 'true'}
        this.$http.post('/api/traineeinformation/getAppoint', p).then((res) => {
          if (res.code == 200 && res.result) {
            this.XY = res.result.slice(0, 10);
            setTimeout(() => {
              for (let i = 10; i < res.result.length; i++) {
                this.XY.push(res.result[i])
              }

            }, 2000)
          }
        })
      },
      tt() {
        if (this.XY >= this.list.length) {
          return
        }
        let t = this.list.slice(this.i, this.i + 30);
        this.XY = this.XY.concat(t)
        this.i += 30

        setTimeout(() => {

          this.tt()
        }, 2000)
      },
      getDictList() {
        this.JX = this.dictUtil.getByCode(this, 'ZDCLK1017');
      },
      colse() {
        this.formData.jlJx = '';
        this.AMess = [{}];
        this.formData = {};
        this.formData.lcLx = '00';
        this.XY = [];
        this.sfaemanlist = [];
      },
      getCarList() {
        this.getYYdj();
        this.zxNum = 0;
        this.xxNum = 0;
        this.$http.post('/api/lccl/getCar', {
          notShowLoading: 'true',
          pagerNum: 1,
          pageSize: 99999,
          clKm: "3",
          clBh: this.formData.clBh,
          orderBy: 'clZt asc,clBh asc,clCx asc',
          clZt: this.formData.clZt,
          clCx: this.formData.clCx
        }).then((res) => {
          if (res.code == 200) {
            this.CarList = res.page.list
            for (let r of this.CarList) {
              if (r.clZt === '01') {
                this.zxNum++;
              } else if (r.clZt === '00') {
                this.xxNum++;
              }
            }
            this.AF.Get_SERVER_Time((res) => {
              this.set_LcTime(res)
              this.IntervalKS
            })
          } else {
            this.$Message.info(res.message);
          }
        })
      },
    },
    mounted() {

    },
    created() {
      this.getDictList();
      this.getCarList();
      // this.getJgmc()
    },
    beforeDestroy() {
      clearInterval(this.IntervalKS)
    }
  }
</script>

<style scoped>
  .demo-drawer-footer {
    width: 100%;
    position: absolute;
    bottom: 0;
    left: 0;
    border-top: 1px solid #e8e8e8;
    padding: 10px 16px;
    text-align: right;
    background: #fff;
  }

  .colsty {
    padding: 3px 3px;
  }
</style>
