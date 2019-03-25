<template>
  <div class="box_col">
    <Row style="margin-bottom: 18px" type="flex" align="bottom">
      <Col span="4">
        <pager-tit title="科三模训"></pager-tit>
      </Col>
      <Col span="20">
        <Row type="flex" justify="end" :gutter="8">
          <Col  span="8" align="right" style="font-size: 24px;color: #2baee9">
            <div @click="compHisName='yypd'"> 当前排队中
              <Button style="font-size: 20px;font-weight: 600" type="error">{{yyrs}}</Button>
            </div>
          </COl>
          <Col span="2">
            <Button type="success" style="border-radius: 35px;font-size: 20px" @click="yyClick">预</Button>
          </Col>
          <Col span="3">
            <Input v-model="formData.clBh" clearable size="large" placeholder="请输入车辆编号" />
            <br>
          </Col>
          <Col span="3">
            <Select v-model="formData.clCx" clearable  placeholder="请选择车型" @on-change="getCarList">
              <Option v-if="item.key!='02'" v-for="item in dictUtil.getByCode(v,'ZDCLK0040')" :value="item.key" :key="item.index">{{ item.val }}</Option>
            </Select>
          </Col>
          <Col span="3">
            <Select v-model="formData.clZt" clearable  placeholder="请选择车辆状态" @on-change="getCarList">
              <Option v-if="item.key!='02'" v-for="item in dictUtil.getByCode(v,'ZDCLK1044')" :value="item.key" :key="item.index">{{ item.val }}</Option>
            </Select>
          </Col>
          <Col span="1" align="center">
            <Button type="primary" @click="getCarList">
              <Icon type="md-search"></Icon>
              <!--查询-->
            </Button>
          </Col>
          <Col span="1">
            <Button size="large" style="border-radius: 35px;font-size: 20px" type="warning" @click="giveCar.overCar(v)">
              还
            </Button>
          </Col>
        </Row>
      </Col>
    </Row>
    <div class="box_col_auto" style="background-color: #f2f2f2">
      <div class="box_row_list" @showCityName="upup">
        <div v-for="(it,index) in CarList">
          <car-card :carMess="it" @carClick="carClick" @print="print"  @his="his(it)"></car-card>
        </div>
      </div>
    </div>
    <fcdrawer ref="fcdrawer"
              @close="close"
              @getCarList = 'getCarList'
    ></fcdrawer>
    <yydrawer ref="yydrawer"
              @close="close"
              @getCarList = 'getCarList'
    ></yydrawer>
    <addyypd  ref="addyypd"
              @close="close"
              @getCarList = 'getCarList'
    ></addyypd>
    <component :is="compHisName" :hisPrintMess="hisPrintMess"
          @confirm="yyconform"
    ></component>
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
  import yypd from '../comp/orderList'
  import fcdrawer from './fcdrawer'
  import yydrawer from './yydrawer'
  import addyypd from '../comp/addOrder'
  export default {
    name: "index",
    components: {carCard,jlwh,addjl,print,carStatistics,PlLazy,yypd,fcdrawer,yydrawer,addyypd},
    watch:{
      'jlLx':function (n,o) {
        console.log('**',n);
      }
    },
    data(){
      return{
        v : this,
        wxjldis:true,
        showDrawer:true,
        giveCar:giveCar,
        hisPrintMess:'',
        compHisName:'',
        xllist:[
          {
            type: 'selection',
            width: 60,
            align: 'center',

          },
          {
            title: '姓名',
            key: 'name',
            align:'center'
          },
          {
            title: '约考时间',
            key: 'thirdSubTestTime',
            align:'center',
            sortable: true
          },
          {
            title: '约考地点',
            align:'center',
            render:(h,p)=>{
              return h('div',p.row.testInfo.testPlace)
            }
          },
          {
            title: '车型',
            key: 'carType',
            align:'center'
          },
          {
            title: '练车次数',
            key: 'subThreeNum',
            align:'center',
          },
        ],
        xy:[],
        DrawerVal:false,
        compName: '',
        CarList:[],
        JX:[],
        JXCode: 'ZDCLK1017',
        param:{
          pageNum: 1,//当前页码
          pageSize:99999//每页显示数
        },
        listMess:[],
        styles: {
          height: 'calc(100% - 55px)',
          overflow: 'auto',
          paddingBottom: '53px',
          position: 'static'
        },
        jlJx:'',
        formData: {
          clBh:'',
          wxjlId:'',
          lcClId:'',
          xyLx:'1',
          jlLx:'1',
          lcYj:'',
          jlId:'',
          jlJx:'',
          xyDh:'',
          xyXm:'',
          xyIds:'',
          zgId:'',
          cardNo:'',
          clCx:'',
          lcLx:'00'
        },
        KM:'3',
        formDataJL: {},
        sfaemanlist:[],
        wxJL:[],
        bxJL:[],
        XY:[],
        AMess: [
          {}
        ],
        Pmess:{},
        i:0,
        list:[],
        yyrs:0,
      }
    },
    watch: {
      DrawerVal: function (n, o) {
        var v = this
        if (n == false) {
          this.close()
        }
        // if(n == true){
        //   this.readkar()
        // }
      }
    },
    methods: {
      yyconform(e){
        console.log('yyconform',e);
        this.formData = e
        this.$refs.addyypd.show();
        this.DrawerVal = true;
      },
      handleView(name) {
        this.imgName = name;
        this.visible = true;
      },
      getYYdj(){
          this.$http.get('/api/lcjl/query',{params:{kssjIsNull:'1',orderBy:'cjsj asc',lcKm:'3'}}).then((res)=>{
            if(res.code == 200){
              if(res.result){
                this.yyrs = res.result.length
              }else {
                this.yyrs = 0
              }
            }})
      },
      close(){
        this.AMess = [{}];
        this.formData = {};
        this.XY = [];
        this.compName = '';
        this.DrawerVal = false;
        this.sfaemanlist = [];
      },
      open() {
        console.log('open');
      },
      print(mess) {
        this.hisPrintMess = mess
        this.compHisName = 'print'
      },
      his(item) {//历史练车记录
        this.clId = item.id;
        this.compHisName = 'carStatistics'
      },
      // getJgmc(){
      //   this.$http.get('/api/jg/getCurrentOrgTree').then((res)=>{
      //     console.log(res);
      //     if(res.result[0].children){
      //       let t = res.result[0].children
      //       if(t.children[0].children == ''){
      //
      //       }
      //     }
      //   })
      // },
      upup(e) {
        console.log(e);
        this.compName = ''
      },
      getXYID(arr) {
        console.log(arr);
        let arrleng = arr.length - 1
        let messarr = []
        arr.forEach((item, index) => {
          messarr.push(item.id)
          if (index == arrleng) {
            console.log(messarr.join(','));
            this.formData.xyIds = messarr.join(',');
            console.log("学员ids", this.formData.xyIds);
          }
        })
      },
      yyClick(val, cx) {
        console.log(val);
        this.$refs.yydrawer.show();
        this.DrawerVal = true;
        this.formData.lcClId = val;
        this.getXY(cx)
      },
      carClick(val, cx, zg) {
        console.log(val);
        this.$refs.fcdrawer.show();
        this.DrawerVal = true;
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
        let p = {carType: cx, tableColumns: columns}
        this.$http.post('/api/traineeinformation/getAppoint', p).then((res) => {
          if (res.code == 200 && res.result) {
            this.XY = res.result.slice(0, 10);
            console.log(res.result.slice(0, 10))
            //this.tt()
            // var t = 1;
            // while (t*10<res.result.length) {
            setTimeout(() => {
              for (let i = 10; i < res.result.length; i++) {
                this.XY.push(res.result[i])
              }

            }, 2000)
            //t++

            // this.XY.concat(res.result.slice(10,200))
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
        this.compName = '';
        this.DrawerVal = false;
        this.sfaemanlist = [];
      },
      getCarList() {
        this.getYYdj();
        this.$http.post('/api/lccl/getCar', {
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
          } else {
            this.$Message.info(res.message);
            console.log(res);
          }
        })
      },
    },
    mounted () {
      console.log('mounted');

    },
    created(){
      this.getDictList();
      this.getCarList();
      // this.getJgmc()
    }
  }
</script>

<style scoped>
  .demo-drawer-footer{
    width: 100%;
    position: absolute;
    bottom: 0;
    left: 0;
    border-top: 1px solid #e8e8e8;
    padding: 10px 16px;
    text-align: right;
    background: #fff;
  }
  .colsty{
    padding: 3px 3px ;
  }
</style>
