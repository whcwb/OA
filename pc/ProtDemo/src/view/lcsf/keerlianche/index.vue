<template>
  <div class="box_col">
    <Row style="margin-bottom: 18px" type="flex" align="bottom">
      <Col span="4">
        <pager-tit title="科二模训" style="float: left"></pager-tit>
        <!--<div style="float: left;margin-top: 8px;cursor: pointer">-->
          <!--<span style="width: 100px;height: 80px;background-color: #ff9900;color:white;padding:6px;border-radius: 4px;margin-left: 16px;" @click="formData.clZt = '',getCarList()">共{{carList.length}}台</span>-->
          <!--<span style="width: 100px;height: 80px;cursor: pointer;background-color: red;color:white;padding:6px;border-radius: 4px;margin-left: 16px;"-->
                <!--@click="formData.clZt = '01',getCarList()">-->
            <!--在训{{zxNum}}台</span>-->
          <!--<span style="width: 100px;height: 80px;background-color: #66CD00;color:white;padding:6px;border-radius: 4px;margin-left: 16px;cursor: pointer;"-->
                <!--@click="formData.clZt = '00',getCarList()"-->
          <!--&gt;空闲{{xxNum}}台</span>-->
        <!--</div>-->
      </Col>
      <Col span="8">
        <div class="box_row">
          <!--<div  style="font-size: 24px;color: #e91b10;line-height: 45px;">-->
            <!--累计：{{total}} 元-->
          <!--</div>-->
          <div @click="compName='keyypd'" style="font-size: 24px;color: #2baee9;line-height: 45px;margin: 0 6px"> 当前排队中</div>
          <div style="margin: 0 6px">
            <Button style="font-size: 20px;font-weight: 600" @click="componentName='keyypd'" type="error">{{yyrs}}</Button>
          </div>
          <div style="margin: 0 6px">
            <Button type="success" style="border-radius: 35px;font-size: 20px" @click="yyClick">预</Button>
          </div>
          <div style="margin: 0 6px">
            <Button type="error" style="border-radius: 35px;font-size: 20px" @click="faCar">发</Button>
          </div>
          <div style="margin: 0 6px">
            <Button size="large" style="border-radius: 35px;font-size: 20px" type="warning"
                    @click="giveCar.overCar(v,'2'),printClose=true">
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
      <table-area :pager="false" :parent="v"></table-area>
    </div>
    <!--<div class="box_col_auto" style="background-color: #f2f2f2">-->
      <!--<div class="box_row_list">-->
        <!--<div v-for="(it,index) in carList">-->
          <!--<car-card :carMess="it" @carClick="carClick" @print="print" @his="his(it),printClose=false"></car-card>-->
        <!--</div>-->
      <!--</div>-->
    <!--</div>-->
    <Modal
      title="分配车辆"
      v-model="DrawerVal"
      :closable="false"
      width="720"
      :mask-closable="true">
      <div slot="header">
        <div class="box_row">
          <div v-if="carMess">
            <Tag type="border" style="font-size: 24px;font-weight:bold;padding: 5px;height: 36px;" color="error">{{carMess.clBh}}-{{carMess.clCx}}</Tag>
          </div>
          <div style="font-size: 16px;margin-right: 28px;margin-top: 7px">
            <h2>模训发车</h2>
          </div>
        </div>
      </div>
      <Form :model="formData" label-position="top">
        <Row>
          <Col span="24"></Col>
        </Row>
        <Row :gutter="24">
          <Col span="12" >
            <div style="float: left">
              <FormItem label="教练员">
                <Select v-model="formData.jlId"
                        filterable
                        clearable
                        remote
                        @on-query-change="searchJly"
                        ref="jlySelect"
                >
                  <Option v-for="(it,index) in searchCoachList" :value="it.value" :key="index">{{it.label}}</Option>
                </Select>
              </FormItem>
            </div>
            <div style="padding-top: 22px;">
              <Button type="primary" @click="compName ='addjl'">
                <Icon type="md-add"/>
              </Button>
            </div>
          </Col>
        </Row>
        <radio-car v-if="carMess == null"
                   clKm="2"
                   @getCarItemMess="getCarItemMess"
                   ></radio-car>

        <component :is="compName" :jxmc="jlJx"
                   @SaveOk="addjlSaveOk"
                   @colse="clearYY"
                   @remove="getCoachList('',true)"
                   @JLRowClick="JLRowClick"
                   @jxSeljxSel="(val)=>{getCoachList('',true)}"></component>

        <Row :gutter="32" style="padding-top: 5px">
          <Col span="12">
            <FormItem label="学员人数" label-position="top">
              <Input v-model="formData.xySl"/>
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="预交费" label-position="top">
              <Input v-model="formData.lcYj"></Input>
            </FormItem>
          </Col>
        </Row>
        <FormItem label="备注" label-position="top">
          <Input type="textarea" v-model="formData.bz" :rows="4"/>
        </FormItem>
      </Form>
      <div slot='footer'>
        <Button style="margin-right: 8px" @click="close">取消</Button>
        <Button type="primary" @click="save">发车</Button>
      </div>
    </Modal>
    <yyModel ref="yyModel"
              @close="close"
              @getCarList = 'getCarList'
    ></yyModel>
    <component :is="componentName" :printClose="printClose" :hisPrintMess="hisPrintMess"
               @SaveOk="addjlSaveOk"
               @colse="clearYY"
               @remove="getCoachList('',true)"
               @JLRowClick="JLRowClick"
               @jxSeljxSel="(val)=>{getCoachList('',true)}"></component>
  </div>
</template>

<script>
  import carCard from '../comp/carCard'
  import jlwh from '../comp/jlWh'
  import addjl from '../comp/addJL'
  import carStatistics from '../statistics/carStatistics'
  import keyypd from '../comp/keyypd'
  import print from '../comp/print'
  import yydrawer from './yydrawer'
  import yyModel from './yyModel'
  import radioCar from '../comp/RadioCar'
  //还车
  import giveCar from '../comp/readCard'
  import {mapMutations} from 'vuex'
  import moment from 'moment'
  export default {
    name: "index",
    components: {
      carCard, jlwh, addjl,
      print,radioCar, carStatistics,
      keyypd,yydrawer,yyModel},
    data() {
      return {
        total:0,
        giveCar:giveCar,
        v:this,
        apiRoot: this.apis.lcjl,
        choosedItem: null,
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
          // {title: '安全员姓名', key: 'zgXm',minWidth:100},
          {title: '时长', key: 'sc',minWidth:80,defaul:'0'},
          {title: '学员数量', key: 'xySl',minWidth:90,defaul:'0'},
          {title: '练车费用', key: 'showlcFy',minWidth:90,defaul:'0'},
          {title: '状态', minWidth:120,render:(h,p)=>{
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
          }
        ],
        DrawerVal: false,
        compName: '',
        componentName: '',
        printClose:false,
        hisPrintMess: '',
        clId: '',
        showFQfzkp:false,
        formData: {
          cardNo:'',
          clBh:'',
          lcClId: '',
          jlJx:'',
          jlId:""
        },
        searchCoachList: [],
        loadingJly:false,
        yyrs:'0',
        bxJL: [],//本校
        wxJL: [],//外校
        jlJx: '',
          zxNum:0,
          xxNum:0,
        carList: [],
          coachList:[],
        param: {
          notShowLoading:'true',
          orderBy:'jssj desc',
          kssjIsNotNull:'1',
          total: 0,
          lcKm:2,
          kssjInRange:'',
          zhLike: ''
        },
        pageData: [],
        specialPageSize:99999999,
        dateRange: {
          kssj: ''
        },
        showCAR: false,
        carMess:null,
        IntervalKE: setInterval(() => {
          // this.Ch_LcTime()
          this.jump()
        },  60000)
      }
    },
    watch: {
      DrawerVal: function (n, o) {
        var v = this
        if (n == false) {
          this.compName = ''
          this.formData = {}
          this.jlJx= ''
        }else {
          // if (this.formData.lcClId == '') {
          //   this.showCAR = true
          // }
        }
      }
    },
    mounted() {
    },
    created() {
      this.dateRange.kssj = [this.AF.trimDate() + ' 00:00:00', this.AF.trimDate() + ' 23:59:59']
      this.param.kssjInRange = this.AF.trimDate() + ' 00:00:00' + ',' + this.AF.trimDate() + ' 23:59:59'
      this.util.initTable(this);
      this.getCoachList()
      this.getCarList();
      setTimeout(()=>{this.jump()},1000)
      this.getYYdj()
    },
    beforeDestroy(){
      clearInterval(this.IntervalKE)
    },
    methods: {
      jump(){
        this.total = 0;
        for (let r of this.pageData){
          let startTime = r.kssj;
          if (r.kssj.length < 19){
            startTime += ":00";
          }
          let now = new Date();
          let duration =  moment(moment(now) - moment(startTime));
          // console.log(duration);
          if((r.kssj && r.kssj.length > 0) && (!r.jssj || r.jssj == '')){
            let min = parseInt(duration/60000);
            // console.log(min);
            r.sc = duration.subtract(8,'hour').format("HH时mm分钟");//this.parseTime(min);
            r.lcFy = Math.round(min*500/60);
            r.showlcFy = r.lcFy + '元';
          }
          this.total += parseInt(r.lcFy);
          if (!this.total){
            this.total = 0;
          }
          // console.log(r.lcFy);
        }
      },
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
        }
      },
      ...mapMutations([
        'set_LcTime',
        'Ch_LcTime'
      ]),
      getCarItemMess(it, index) {
        this.formData.lcClId = it.id
      },
      searchJly (query) {
        if (query !== '') {
          this.loadingJly = true;
          setTimeout(() => {
            this.loadingJly = false;
            this.searchCoachList = this.coachList.filter(item => {
              return item.label.indexOf(query.toUpperCase()) != -1
            });
          }, 200);
        } else {
          this.searchCoachList = [];
        }
      },
      clzt(zt){

      },
      clearYY(){
        this.compName='';
        this.componentName='';
        this.getYYdj();
        this.getCarList();
        this.searchCoachList = [];
      },
      JLRowClick(row){
        this.formData.jlId = row.id
      },
      close() {
        // this.showCAR = false;
        this.carMess=null;
        this.formData = {};
        this.XY = [];
        this.compName = '';
        this.DrawerVal = false;
        this.sfaemanlist = [];
        this.formData.lcLx = '00';
        this.searchCoachList = [];
        //清空下拉框内容
        this.$refs.jlySelect.clearSingleSelect();
      },
      yyClick(val, cx) {
        console.log(val);
        this.$refs.yyModel.show();
        this.formData.lcClId = val;
      },
      getYYdj(){
        this.$http.get('/api/lcjl/query',{params:{kssjIsNull:'1',orderBy:'cjsj asc',lcKm:'2',notShowLoading:'true'}}).then((res)=>{
          if(res.code == 200){
            if(res.result){
              this.yyrs = res.result.length
            }else{
              this.yyrs = 0
            }
          }})
      },
      faCar(){
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
        this.giveCar.readCard((key,mess)=>{
          if(!key){
            if (this.DrawerVal) {
              let v = this
              setTimeout(() => {
                if (v.DrawerVal) {
                  this.faCar()
                }
              }, 200)
            }
            if (v.showFQfzkp) {
              return;
            }
            v.showFQfzkp = true;
            v.swal({
              title:mess,
              type:'error',
              confirmButtonText: '发车',
              cancelButtonText: '取消',
              showCancelButton: true
            }).then((res) => {
              if (res.value) {
                v.showFQfzkp = false;
                v.faCar()
              } else {
                v.showFQfzkp = false;
                v.showQfshowFQfzkpzkp = false;
                v.DrawerVal = false
              }
            })
          }else {
            this.AF.carCard('2', mess, (type, res) => {
              console.log('**********', res);
              if (type) {
                if (res.result) {
                  //如果车辆已经绑卡   返回车辆信息
                  v.carMess = res.result
                  this.formData.lcClId = v.carMess.id
                }
                this.DrawerVal = true;
                v.showFQfzkp = false;
                this.formData.cardNo = mess;
              } else {
                this.DrawerVal = false;
                return
              }
            })
          }
        })
      },
      readkar(callback){
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
        this.giveCar.readCard((key,mess)=>{
          if(!key){
              if (this.DrawerVal) {
                  let v = this
                  setTimeout(() => {
                      if (v.DrawerVal) {
                        this.readkar()
                      }
                  }, 200)
              }
              if (v.showFQfzkp) {
                  return;
              }
              v.showFQfzkp = true;
            v.swal({
              title:mess,
              type:'error',
              confirmButtonText: '发车',
              cancelButtonText: '取消',
              showCancelButton: true
            }).then((res) => {
                if (res.value) {
                    v.showFQfzkp = false;
                    v.readkar()
                } else {
                    v.showQfshowFQfzkpzkp = false;
                    v.DrawerVal = false
                }
            })
          }else {
              v.showFQfzkp = false;
            this.formData.cardNo = mess
            callback && callback(true)
            this.save()
          }
        })
      },
      print(mess) {//还车
        this.hisPrintMess = mess
        // setTimeout(()=>{
        //   this.$refs['backcar'].doPrint()
        // },1000)
        this.componentName = 'print'
      },
      printHc(mess) {
        this.hisPrintMess = mess
        this.componentName = 'print'
        // console.log('dayin')
      },
      his(item) {//历史练车记录
        this.clId = item.id;
        this.componentName = 'carStatistics'
      },
      carClick(val) {
        this.getCoachList();
        this.formData.lcClId = val
        this.DrawerVal = true
      },
      addjlSaveOk(id) {
        this.getCoachList(id)
      },
      getCarList() {//获取车辆
        this.param.clBh = this.formData.clBh
        this.zxNum=0;
        this.xxNum=0;
        this.$http.post('/api/lccl/getCar', {notShowLoading:'true',pagerNum:1,pageSize:99999,clKm:"2",clBh:this.formData.clBh,orderBy:'clZt asc,clBh asc,clCx asc',clZt:this.formData.clZt,clCx:this.formData.clCx}).then((res) => {
          if (res.code == 200) {
            this.carList = res.page.list
              for (let r of this.carList){
                  if (r.clZt === '01'){
                      this.zxNum ++;
                  }else if (r.clZt === '00'){
                      this.xxNum ++;
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
        getCoachList(id,clear) {
          if(clear){
            this.formData.jlId = '';
          }
          this.coachList = [];
            this.$http.get('/api/lcwxjl/query',{params:{notShowLoading:'true'}}).then((res) => {
                this.wxJL = res.result
                if (res.code == 200 && res.result){
                    for (let r of res.result) {
                        let py = this.util.parsePY(r.jlXm)
                        this.coachList.push({label: r.jlJx + '_' + r.jlXm +' ['+py+']'+'_'+ r.jlLxdh , value: r.id});
                    }
                }
                if(res.code == 200 && res.result && id ){
                  this.formData.jlId = id
                }
            })
        },
      save(){//发车
        if(this.formData.cardNo == null || this.formData.cardNo == ''){
          this.readkar();
        }else {
            this.formData.notShowLoading = 'true'
          this.$http.post('/api/lcjl/save', this.formData).then(res => {
            if (res.code == 200){
              this.DrawerVal = false;
              this.formData = {};
              this.getCarList();
              this.util.initTable(this);
              this.swal({
                title:'发车成功',
                type:'success',
                confirmButtonText: '确定',
              })
              this.carMess=null
            } else {
              this.formData.cardNo = null;
              this.swal({
                title: res.message,
                type: 'warning'
              })
            }
          }).catch(err => {
          })
        }
      }
    },
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
</style>
