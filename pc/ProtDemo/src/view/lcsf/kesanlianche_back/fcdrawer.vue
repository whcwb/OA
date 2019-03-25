<template>
    <div>
      <Drawer
        @on-close="$emit('close')"
        @on-open="open"
        title="分配车辆"
        v-model="DrawerVal"
        width="720"
        :mask-closable="true"
      >
        <div slot="header">
          <Row>
            <Col span="10">
              <p>分配车辆</p>
            </Col>
          </Row>
        </div>
        <Form :model="formData">
          <Row :gutter="32">
            <Col span="12">
              <FormItem label="本校/外校_教练">
                <Select v-model="formData.jlLx" @on-change="clearform">
                  <Option value="00" :key="1">本校</Option>
                  <Option value="10" :key="2">外校</Option>
                </Select>
              </FormItem>
            </Col>
            <Col span="12" v-if="formData.jlLx == '00'">
              <FormItem label="本校教练员">
                <Select v-model="formData.jlId" filterable>
                  <Option v-for="(it,index) in bxJL" :value="it.id" :key="index" >{{it.jgmc}}__{{it.xm}}</Option>
                </Select>
              </FormItem>
            </Col>
            <Col span="12" v-if="formData.jlLx == '10'">
              <FormItem label="所属驾校" label-position="top">
                <Select filterable clearable v-model="formData.jlJx" placeholder="请选择驾校" @on-change="getjxjllist" @on-clear="clear">
                  <Option v-for="item in JX" :value="item.val">{{item.val}}</Option>
                </Select>
              </FormItem>
            </Col>
            <!--<Col span="12">-->
            <!--<FormItem label="教练员属性" label-position="top">-->
            <!--<Select v-model="formData.owner" placeholder="教练员属性">-->
            <!--<Option value="jobs">本校</Option>-->
            <!--<Option value="ive">外校</Option>-->
            <!--</Select>-->
            <!--</FormItem>-->
            <!--</Col>-->
          </Row>
          <Row :gutter="32">
            <Col span="12" v-if="formData.jlLx == '10'">
              <Row>
                <Col span="20">
                  <FormItem label="外校教练员">
                    <Select v-model="formData.wxjlId" :disabled="wxjldis" filterable >
                      <Option v-for="(it,index) in wxJL" :value="it.id" :key="index">{{it.jlXm}}__{{it.jlJx}}</Option>
                    </Select>
                  </FormItem>
                </Col>
                <Col span="4">
                  <FormItem label="新增">
                    <Button type="primary" @click="compName ='addjl'">
                      <Icon type="md-add" />
                    </Button>
                  </FormItem>
                </Col>
              </Row>
            </Col>
            <!--<Col span="12" v-if="formData.jlLx == '10'">-->
            <!--<FormItem label="本校/外校_学员">-->
            <!--<Select v-model="formData.xyLx">-->
            <!--<Option value="00" :key="1">本校</Option>-->
            <!--<Option value="10" :key="2">外校</Option>-->
            <!--</Select>-->
            <!--</FormItem>-->
            <!--</Col>-->
            <!--<Col span="12" v-if="formData.jlLx == '00'">-->
            <!--<FormItem label="本校学员" label-position="top">-->
            <!--<Select v-model="formData.xyid" multiple >-->
            <!--<Option v-for="item in XY" :value="item.id" :key="item.id">{{ item.name }}</Option>-->
            <!--</Select>-->
            <!--</FormItem>-->
            <!--</Col>-->
            <Col span="12">
              <FormItem label="学员练车人数" label-position="top">
                <Input @input="scXY" v-model="formData.xySl"  placeholder="输入学员人数"></Input>
              </FormItem>
            </Col>
          </Row>
          <component v-if="formData.jlLx == '10'" :is="compName" :jxmc="jlJx"
                     @SaveOk="addjlSaveOk"
                     @colse="compName=''"
                     @jxSeljxSel="(val)=>{jlJx=val,getwxJLList(val)}">12</component>
          <Divider/>
          <Row  :gutter="32" v-if="formData.jlLx == '10'" style="background-color: #f2f2f2">
            <Card>
              <p slot="title">学员信息</p>
              <p>
                <Row v-for="(item,index) in AMess" :key="index">
                  <Col span="5" :class-name="'colsty'">
                    <Input type="text" size="default" v-model="item.xyXm" placeholder="学员姓名"/>
                  </Col>
                  <Col span="5" :class-name="'colsty'">
                    <Input type="textarea" :autosize="{minRows: 1,maxRows: 1}"
                           size="default" v-model="item.xyDh" placeholder="学员联系电话"/>
                  </Col>
                  <Col span="5" :class-name="'colsty'">
                    <Input type="textarea" :autosize="{minRows: 1,maxRows: 1}"
                           size="default" v-model="item.bz" placeholder="备注信息"/>
                  </Col>
                  <Col span="3" v-if="AMess.length>1">
                    <Button size="default" type="error" @click="remove(index)">删除</Button>
                  </Col>
                  <Col span="3" align="center">

                    <Button type="info" icon="md-add"
                            @click="pushmess"
                    >添加学员</Button>
                  </Col>
                </Row>
                <!--<Row style="padding-top:16px">-->

                <!--</Row>-->
              </p>
            </Card>
          </Row>
          <Row :gutter="32" v-if="formData.jlLx == '00'">
            <Card>
              <p slot="title">学员信息</p>
              <p>
                <Row v-for="(item,index) in AMess" :key="index">
                  <Col span="5" :class-name="'colsty'">
                    <Input type="text" size="default" v-model="item.xyXm" placeholder="学员姓名"/>
                  </Col>
                  <Col span="5" :class-name="'colsty'">
                    <Input type="textarea" :autosize="{minRows: 1,maxRows: 1}"
                           size="default" v-model="item.xyDh" placeholder="学员联系电话"/>
                  </Col>
                  <Col span="5" :class-name="'colsty'">
                    <Input type="textarea" :autosize="{minRows: 1,maxRows: 1}"
                           size="default" v-model="item.bz" placeholder="备注信息"/>
                  </Col>
                  <Col span="3" v-if="AMess.length>1">
                    <Button size="default" type="error" @click="remove(index)">删除</Button>
                  </Col>
                  <Col span="3" align="center">

                    <Button type="info" icon="md-add"
                            @click="pushmess"
                    >添加学员</Button>
                  </Col>
                </Row>
                <!--<Row style="padding-top:16px">-->

                <!--</Row>-->
              </p>
            </Card>
            <!--&lt;!&ndash;<pl-lazy time="0">&ndash;&gt;-->
            <!--<Col>-->
            <!--<Row><div style="font-size:20px;color: #020101">已约考学员</div></Row>-->
            <!--<Row>-->
            <!--<div>-->
            <!--<Table @on-selection-change="getXYID" size="small" height="300" :columns="xllist" :data="XY"></Table>-->
            <!--</div>-->
            <!--</Row>-->
            <!--</Col>-->
            <!--&lt;!&ndash;</pl-lazy>&ndash;&gt;-->
          </Row>
          <!--<Row :gutter="32" v-if="formData.lcClId == ''">-->
            <!--<Col span="8">-->
              <!--<FormItem label="选择车辆" label-position="top">-->
                <!--<Select v-model="formData.lcClId" filterable>-->
                  <!--<Option v-for="(item,index) in CarList" :value="item.id" :key="index">{{ item.clBh }}</Option>-->
                <!--</Select>-->
              <!--</FormItem>-->
            <!--</Col>-->
          <!--</Row>-->
          <Row :gutter="32">
            <Col span="8">
              <FormItem label="安全员" label-position="top">
                <Select v-model="formData.zgId" filterable>
                  <Option v-for="(item,index) in sfaemanlist" :value="item.id" :key="index">{{ item.xm }}</Option>
                </Select>
              </FormItem>
            </Col>
            <Col span="8">
              <FormItem label="计费类型">
                <Select v-model="formData.lcLx">
                  <Option value="00" :key="1">计时</Option>
                  <Option value="10" :key="2">按次</Option>
                </Select>
              </FormItem>
            </Col>
            <Col span="8">
              <FormItem label="预交费" label-position="top">
                <Input v-model="formData.lcYj"  placeholder="输入预交费金额"></Input>
              </FormItem>
            </Col>
          </Row>
          <FormItem label="备注" label-position="top">
            <Input type="textarea" v-model="formData.bz" :rows="4" placeholder="练车明细" />
          </FormItem>
          <Row type="flex" justify="end">
            <Col span="12" align="right">
              <Button style="margin-right: 8px" @click="close">取消</Button>
              <Button type="primary" @click="getSave">发车</Button>
            </Col>
          </Row>
        </Form>
      </Drawer>
    </div>
</template>

<script>
  import addjl from '../comp/addJL'
  import jlwh from '../comp/jlWh'
  import giveCar from '../comp/readCard'
    export default {
      name: "fcdrawer",
      components:{addjl,jlwh},
      data(){
          return{
            compName:'',
            DrawerVal:false,
            giveCar:giveCar,
            JX:[],
            CarList:[],
            wxjldis:true,
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
              xySl:1,
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
          }
      },
      created(){
        console.log('created');
        this.getDictList();
        this.getCarList()
      },
      mounted(){
      },
      methods:{
        getCarList() {
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
        clear() {
          this.formData.jlJx = '';
          this.formData.wxjlId = '';
          this.wxJL = []
        },
        close() {
          this.formData.jlJx = '';
          this.AMess = [{}];
          this.formData = {};
          this.formData.lcLx = '00';
          this.XY = [];
          this.compName = '';
          this.DrawerVal = false;
          this.sfaemanlist = [];
        },
        getWXXY(AMess) {
          let arrAMess = AMess.length - 1;
          let messarr = [];
          let dxarr = [];
          AMess.forEach((item, index) => {
            messarr.push(item.xyXm)
            dxarr.push(item.xyDh)
            if (index == arrAMess) {
              console.log(dxarr.join(','))
              console.log(messarr.join(','))
              this.formData.xyIds = messarr.join(',');
              this.formData.xyDh = dxarr.join(',')
            }
          })
        },
        scXY(e){
          this.AMess=[{}];
          e = parseInt(e);
          for (let i =1;i<e;i++){
            this.AMess.push({})
          }
        },
        getDictList() {
          this.JX = this.dictUtil.getByCode(this, 'ZDCLK1017');
        },
        clearform(){
          this.formData.jlId = '';
          // this.formData.zgId = '';
          this.formData.xyIds = '';
          this.formData.xyDh = '';
          this.formData.jlJx = ''
        },
        remove(i){
          this.AMess.splice(i,1)
        },
        pushmess(){
          let a =JSON.parse(JSON.stringify(this.Pmess));
          this.AMess.push(a);
        },
        readkar(){
          if (!!window.ActiveXObject || "ActiveXObject" in window){
          }else{
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
              if (this.DrawerVal){
                let v = this
                setTimeout(()=>{
                  if (v.DrawerVal) {
                    this.readkar()
                  }
                },200)
              }
              if (v.showFQfzkp){
                return;
              }
              v.showFQfzkp = true;
              v.swal({
                title:mess,
                type:'error',
                confirmButtonText: '读卡',
                cancelButtonText: '关闭',
                showCancelButton: true
              }).then((res) => {
                if(res.value){

                  v.showFQfzkp = false;
                  v.readkar()
                }else {
                  v.showQfshowFQfzkpzkp = false;
                  v.DrawerVal = false
                }
              })
            }else {
              v.showFQfzkp = false;
              this.formData.cardNo = mess
              this.getSave()
            }
          })
        },
        addjlSaveOk() {
          console.log('教练添加成功');
          this.getjxjllist(this.jlJx)
        },
        getSave(){
          if(this.formData.xySl == ''|| this.formData.xySl == NaN ){
            this.formData.xySl = 0
          }
          if(this.formData.cardNo == null || this.formData.cardNo == '') {
            this.readkar();
            return
          }
          if(this.formData.jlLx == '10'){
            this.formData.jlId = this.formData.wxjlId
          }
          this.getWXXY(this.AMess);
          this.$http.post('/api/lcjl/save',this.formData).then((res)=>{
            if(res.code == 200){
              this.DrawerVal = false;
              this.$emit('getCarList');
              this.AMess = [{}];
              this.formData = {};
              this.$parent.formData = {};

              this.swal({
                title:'发车成功',
                type:'success',
                showCancelButton: false,
                confirmButtonText: '确定',
              }).then((val)=>{
                if(val.value){
                  this.formData.lcLx ='00';
                  // window.location.reload();
                }
              })
            }else {
              this.swal({
                title:res.message,
                type:'warning',
                showCancelButton: false,
                confirmButtonText: '确定',
              })
              this.formData.cardNo = null
              // if (this.DrawerVal){
              //   let v = this
              //   setTimeout(()=>{
              //     if (v.DrawerVal) {
              //       this.readkar()
              //     }
              //   },200)
              // }
            }
          })


        },
        getjxjllist(val){
          console.log(val);
          console.log(typeof val);
          if(!val||val === ''){
            this.wxjldis = true
          }else{
            this.wxjldis = false
          }
          this.jlJx = val
          this.formData.jlJx = val
          this.$http.get('/api/lcwxjl/query',{params:{jlJx:val,notShowLoading: "true"}}).then((res)=>{
            console.log(res);
            this.wxJL = res.result
          })
        },
        getSafemanList(){
          this.$http.post('/api/zgjbxx/getAqy',{notShowLoading:'true'}).then((res)=>{
            console.log(res);
            if(res.code == 200){
              this.sfaemanlist = res.result
            }else {
              this.$Message.info(res.message);
            }
          })
        },
        getDictList() {
          this.JX = this.dictUtil.getByCode(this, 'ZDCLK1017');
        },
        getwxJLList(){
          this.$http.get('/api/lcwxjl/query',{params:{notShowLoading:'true'}}).then(res=>{
            if(res.code == 200){
              this.wxJL = res.result
            }
          }).catch(err=>{})
        },
        getbxJLList(){
          this.$http.get('/api/zgjbxx/query',{params:{gzgw:'0003',zzzt:'10'}}).then(res=>{
            if(res.code == 200){
              this.bxJL = res.result
              console.log(this.bxJL);
            }
          })
        },
        open(){
        },
        show(){
          this.close();
          this.DrawerVal = true;
          this.formData = this.$parent.formData;
          this.formData.cardNo = '';
          // this.formData.zgId = '';
          this.formData.jlJx = '';
          this.formData.lcLx = '00';
          this.getbxJLList();
          this.getwxJLList();
          this.getSafemanList()
        },
      }
    }
</script>

<style scoped>

</style>
