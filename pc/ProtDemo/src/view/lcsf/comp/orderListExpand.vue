<template>
  <div>
    <Form :model="formData">
      <Row :gutter="32">
        <Col span="12">
          <Row>
            <Col span="12" v-if="changeCar" align="center">
              <div >
                <Tag type="border" style="font-size: 36px;font-weight:bold;padding: 5px;height: 48px;" color="error">{{CarInfo.clBh}}--{{CarInfo.clCx}}</Tag>
              </div>
            </Col>
            <Col span="12" v-if="!changeCar">
              <FormItem label="选择车辆" label-position="top">
                <Select v-model="formData.lcClId" filterable>
                  <Option v-for="(item,index) in CarList" :value="item.id" :key="index">{{ item.clBh }}--{{item.clCx}}</Option>
                </Select>
              </FormItem>
            </Col>
            <Col span="12" style="padding-left: 5px" v-if="row.lcKm==='3'">
              <FormItem label="安全员" label-position="top">
                <Select v-model="formData.zgId" filterable>
                  <Option v-for="(item,index) in sfaemanlist" :value="item.value" :key="index">{{ item.label }}</Option>
                </Select>
              </FormItem>
            </Col>
          </Row>
        </Col>
        <Col span="8">
          <Col span="24">
            <FormItem label="备注" label-position="top">
              <Input type="text" v-model="formData.bz" :rows="5" placeholder="练车明细" />
            </FormItem>
          </Col>
        </Col>
        <Col span="4">
          <Row type="flex" justify="end">
            <Col span="24" align="right">
              <div @click="getSave" style="background-color: #f5a623;font-size: 22px;color: #fff;font-weight: 600;cursor: pointer;
              width: 85px;height: 85px;line-height: 85px;text-align: center;border-radius: 85px">
                发车
              </div>
            </Col>
          </Row>
        </Col>
      </Row>
    </Form>
  </div>
</template>

<script>
  import giveCar from '../comp/readCard'
  export default {
    name: "yypaFC",
    props:{
      row:{
        type:Object,
        default:{
          lcClId:null

        }
      },
    },
    data(){
      return {
        CarInfo:{},
        giveCar:giveCar,
        formData:{},
        CarList:[],
        sfaemanlist:[],
        changeCar:false
      }
    },
    created(){
      this.formData = JSON.parse(JSON.stringify(this.row));
      // console.log('zi',this.formData);
      this.getCarList();
      if(this.row.lcKm == '3'){
        this.getSafemanList()
      }
    },
    watch: {
      'row.lcClId': function (n, o) {
        this.changeCar = n !== ''
        this.formData.lcClId = n
      }
    },
    methods:{
      readCardSuccess(item){
        // console.log("readCardSuccess",item);
      },
      getSave(){
        delete this.formData.xyList;
        delete this.formData.lcCl;
        if(this.formData.xySl == ''|| this.formData.xySl == NaN){
          this.formData.xySl = 0
        }
        if(this.formData.cardNo == null || this.formData.cardNo == ''){
          this.readkar();
          return
        }
        this.formData.km =this.row.lcKm;
        this.formData.notShowLoading = 'true';
        this.$http.post('/api/lcjl/kslc',this.formData).then((res)=>{
          if(res.code == 200){
            this.DrawerVal = false;
            this.$emit('getCarList');
            this.formData = {};
            this.swal({
              title:'发车成功',
              type:'success',
              showCancelButton: false,
              confirmButtonText: '确定',
            }).then((val)=>{
              if(val.value){
                // window.location.reload();
                this.$emit('FcSave');
              }
            });
            // this.$parent.getYYdj();
          }else {
            this.swal({
              title:res.message,
              type:'warning',
              showCancelButton: false,
              confirmButtonText: '确定',
            })
            this.formData.cardNo = null
          }
        })
      },
      getCarList() {
        this.$http.post('/api/lccl/getCar', {
          pagerNum: 1,
          notShowLoading:'true',
          pageSize: 99999,
          clKm: this.row.lcKm,
          // clBh: this.formData.clBh,
          orderBy: 'clZt asc,clBh asc,clCx asc',
          clZt: '00',
          clCx: this.formData.jlCx
        }).then((res) => {
          if (res.code == 200) {
            this.CarList = res.page.list;
            if(this.formData.cardNo && this.formData.cardNo != ""){
              for(let r of res.page.list){
                if(r.cardNo == this.formData.cardNo){
                  this.CarInfo = r;
                  break;
                }
              }
            }
          } else {
            this.$Message.info(res.message);
          }
        })
      },
      getSafemanList(){
        this.$http.post('/api/zgjbxx/getAqy',{notShowLoading:'true'}).then((res)=>{
          if(res.code == 200){
              if (res.result){
                  let list = [];
                  for (let r of res.result){
                      let s = this.util.parsePY(r.xm);
                      this.sfaemanlist.push({label:r.xm +' ['+s+']',value:r.id});
                  }
              }
          }else {
            this.$Message.info(res.message);
          }
        })
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
      }
    }
  }
</script>

<style scoped>

</style>
