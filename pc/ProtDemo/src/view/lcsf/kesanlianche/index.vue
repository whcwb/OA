<template>
  <div class="box_col">
    <Row style="margin-bottom: 18px" type="flex" align="top">
      <Col span="4">
        <pager-tit title="科三模训" style="float: left"></pager-tit>
      </Col>
      <Col span="8">
        <div class="box_row">
              <!--<div  style="font-size: 24px;color: #e91b10;line-height: 45px;">-->
                <!--累计：{{total}} 元-->
              <!--</div>-->
              <div @click="showOrderList" style="font-size: 24px;color: #2baee9;line-height: 45px;margin: 0 6px"> 当前排队中</div>
              <div style="margin: 0 6px">
                <Button style="font-size: 20px;font-weight: 600" @click="showOrderList" type="error">{{yyrs}}</Button>
              </div>
              <div style="margin: 0 6px">
                <Button type="success" style="border-radius: 35px;font-size: 20px" @click="yyClick">预</Button>
              </div>
              <div style="margin: 0 6px">
                <Button type="error" style="border-radius: 35px;font-size: 20px" @click="faCar">发</Button>
              </div>
              <div style="margin: 0 6px">
                <Button size="large" style="border-radius: 35px;font-size: 20px" type="warning"
                        @click="giveCar.overCar(v,'3'),printClose=true">
                  还
                </Button>
              </div>
            </div>
      </Col>
      <Col span="12">
        <Row  type="flex" justify="end" :gutter="16">
          <Col span="24">
            <search-bar :parent="v" :show-create-button="false" ></search-bar>
          </Col>
        </Row>
      </Col>
    </Row>
    <div>
      <table-area sizeTyp="large"  :pager="false" :parent="v"></table-area>
    </div>
    <!--<div class="box_col_auto" style="background-color: #f2f2f2">-->
      <!--<div class="box_row_list">-->
        <!--<div v-for="(it,index) in CarList">-->
          <!--<car-card :carMess="it" @carClick="carClick" @print="print" @his="his(it),printClose=false"></car-card>-->
        <!--</div>-->
      <!--</div>-->
    <!--</div>-->
    <!--分配车辆-->
    <fcModel ref="fcModel" @close="close" @getCarList='getCarList'></fcModel>
    <!--<component :is="compName"  @close="close" @getCarList='getCarList'></component>-->
    <!--分配车辆_end-->
    <yyModel ref="yyModel" @close="close" @getCarList='getCarList'></yyModel>
    <component :is="componentName" :printClose="printClose" :hisPrintMess="hisPrintMess"
               @confirm="orderConfirm"></component>
  </div>
</template>

<script>
  import $ from 'jquery'
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
  import printSl from '../comp/printSl'
  import moment from 'moment'

  export default {
    name: "index",
    components: {carCard, jlwh, addjl, print, carStatistics, PlLazy, orderList, yyModel, addOrder, fcModel,printSl},
    data() {
      return {
        v: this,
        apiRoot: this.apis.lcjl,
        choosedItem: null,
        showDrawer: true,
        giveCar: giveCar,
        hisPrintMess: '',
        compName:'yyModel',
        componentName: '',
        printClose: false,
        // xllist: [
        //   {type: 'selection', width: 60, align: 'center',},
        //   {title: '姓名', key: 'name', align: 'center'},
        //   {title: '约考时间', key: 'thirdSubTestTime', align: 'center', sortable: true},
        //   {
        //     title: '约考地点',
        //     align: 'center',
        //     render: (h, p) => {
        //       return h('div', p.row.testInfo.testPlace)
        //     }
        //   },
        //   {
        //     title: '车型',
        //     key: 'carType',
        //     align: 'center'
        //   },
        //   {
        //     title: '练车次数',
        //     key: 'subThreeNum',
        //     align: 'center',
        //   },
        // ],
        tableColumns: [
          {
            type: 'index', align: 'center', minWidth: 80,
            // render: (h, params) => {
            //   return h('span', params.index + (this.param.pageNum - 1) * this.param.pageSize + 1);
            // }
          },
          {title: '教练姓名', key: 'jlXm',searchKey:'jlXmLike',minWidth:90},
          {title: '教练电话', key: 'jlDh',minWidth:90},
          {title: '驾校', key: 'jlJx', minWidth:90},
          {title: '车辆编号', key: 'clBh',searchKey: 'clBh',minWidth:90,render:(h,p)=>{
              return h('Button',{
                props: {
                  type: 'error',
                  size: 'small'
                },
                style:{
                  borderRadius:'15px'
                }
              },p.row.clBh)
            }},
          {title: '开始时间', key: 'kssj', searchType: 'daterange',minWidth:140},
          {title: '安全员姓名', key: 'zgXm',minWidth:100},
          {title: '时长', key: 'sc',minWidth:80,defaul:'0'},
          {title: '学员数量', key: 'xySl',minWidth:90,defaul:'0'},
          {title: '练车费用', key: 'showlcFy',minWidth:90,defaul:'0'},
          {title: '计费类型', key: 'lcLx', minWidth: 90, dict: 'ZDCLK1048'},
          {title: '状态', minWidth:120,sortable: true,render:(h,p)=>{
              let s = '';
              if (!p.row.kssj || p.row.kssj ===''){
                s = '预约中'
              }else if ((p.row.kssj && p.row.kssj.length > 0) && (!p.row.jssj || p.row.jssj == '')){
                s = '训练中'
              }else{
                s = '已结束'
              }
              return h('div',s);
            }
          },
          {
            title: '操作',fixed:'right',width:80, render: (h, p) => {
              let buttons = [];
              buttons.push(this.util.buildButton(this, h, 'success', 'ios-print', '补打', () => {
                this.hisPrintMess = p.row
                this.componentName = 'print'
              }));
              return h('div', buttons);
            }
          },
        ],
        xy: [],
        CarList: [],
        JX: [],
        zxNum: 0,
        xxNum: 0,
        JXCode: 'ZDCLK1017',
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
        MxList:[],
        formDataJL: {},
        readingCard: false,
        sfaemanlist:[],
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
        pageData: [],
        total:0,
        specialPageSize:99999999,
        param: {
          notShowLoading:'true',
          orderBy:'jssj desc',
          kssjIsNotNull:'1',
          total: 0,
          lcKm:3,
          kssjInRange:'',
          zhLike: ''
        },
        dateRange: {
          kssj: ''
        },
        IntervalKS: setInterval(() => {
          //this.Ch_LcTime()
          this.jump();
        }, 1000 * 60)
      }
    },
    watch: {},
    methods: {
      parseTime(s) {
        s = parseInt(s);
        let h = parseInt(s / 60);
        let m = s % 60;
        let r = '';
        if (h != 0) r += h + '小时';
        return r + m + '分钟'
      },
      afterPager(list){
        for (let r of list){
          r.sc = this.parseTime(r.sc)
          r.kssj = r.kssj.substring(0,16)
          r.jssj = r.jssj.substring(0,16)
          r.showlcFy = r.lcFy + '元';
        }

        this.jump();
      },
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
      jump(){
        this.total = 0;
        for (let r of this.pageData){
          let startTime = r.kssj;
          if (r.kssj.length < 19){
            startTime += ":00";
          }
          let now = new Date();
          let duration =  moment(moment(now) - moment(startTime));
          // // console.log(duration);
          if((r.kssj && r.kssj.length > 0) && (!r.jssj || r.jssj == '')){
            let min = parseInt(duration/60000);
            r.sc = duration.subtract(8,'hour').format("HH时mm分钟");//this.parseTime(min);
            if(r.lcLx == '00'){
              r.lcFy = Math.round(min*500/60);
              r.showlcFy = r.lcFy + '元';
            }else if(r.lcLx == '10'){
              r.showlcFy = r.lcDj + '元'
            }else{
              let lcLxList = this.dictUtil.getByCode(this, 'ZDCLK1048');
              for(let i=0;i<lcLxList.length;i++){
                if(lcLxList[i].key == r.lcLx){
                  r.showlcFy = lcLxList[i].val.split("-")[2]+'元'
                  break
                }
              }
            }
          }
          this.total += parseInt(r.lcFy);
          if (!this.total){
            this.total = 0;
          }
          // // console.log(r.lcFy);
        }
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
      getYYdj(){
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
        // console.log('dayin')
      },
      printHc(mess) {
        this.hisPrintMess = mess
        this.componentName = 'printSl'
        // console.log('dayin')
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
        this.$refs['yyModel'].show();
        this.formData.lcClId = val;
      },
      carClick(val, cx, zg) {
        this.$refs.fcdrawer.show(val, zg);
        this.formData.lcClId = val;
        this.formData.zgId = zg;
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
              if (r.clZt === '01'){
                this.zxNum++;
              } else if (r.clZt === '00') {
                this.xxNum++;
              }
            }
            this.jump();
            this.AF.Get_SERVER_Time((res) => {
              this.set_LcTime(res)
              this.IntervalKS
            })
          }else{
            this.$Message.info(res.message);
          }
        })
      }, tar(){
        // console.log("hell");
      }
    },
    mounted(){
      var v = this
      this.$nextTick(() => {
        $(document).keypress(function (event) {
          console.log(event.keyCode);
          if (event.keyCode === 44) {
            console.log(this);
            v.yyClick()
          }else if (event.keyCode === 46) {
            v.readCar()
          }else if (event.keyCode === 47) {
            giveCar.overCar(v,'3');
            this.printClose = true
          }else if (event.keyCode === 112){
          }
        });
      })
    },
    created() {
      this.dateRange.kssj = [this.AF.trimDate() + ' 00:00:00', this.AF.trimDate() + ' 23:59:59']
      this.param.kssjInRange = this.AF.trimDate() + ' 00:00:00' + ',' + this.AF.trimDate() + ' 23:59:59'
      this.util.initTable(this);
      this.getDictList();
      this.getYYdj();
      this.getCarList();
      setTimeout(()=>{this.jump()},3000)
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
