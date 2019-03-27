<template>
    <div>
      <Drawer
        @on-close="close"
        title="预约发车"
        v-model="DrawerVal"
        width="720"
        :mask-closable="true"
      >
        <div slot="header">
          <Row>
            <Col span="10">
              <p>预约发车</p>
            </Col>
          </Row>
        </div>
        <Form :model="formData">
          <Row :gutter="32">
            <Col span="8">
              <FormItem label="选择车辆" label-position="top">
                <Select v-model="formData.lcClId" filterable>
                  <Option v-for="(item,index) in CarList" :value="item.id" :key="index">{{ item.clBh }}</Option>
                </Select>
              </FormItem>
            </Col>
            <Col span="8">
              <FormItem label="安全员" label-position="top">
                <Select v-model="formData.zgId" filterable>
                  <Option v-for="(item,index) in sfaemanlist" :value="item.id" :key="index">{{ item.xm }}</Option>
                </Select>
              </FormItem>
            </Col>
          </Row>
          <FormItem label="备注" label-position="top">
            <Input type="textarea" v-model="formData.bz" :rows="4" placeholder="练车明细" />
          </FormItem>
          <Row type="flex" justify="end">
            <Col span="12" align="right">
              <Button style="margin-right: 8px" @click="close">取消</Button>
              <Button type="warning" @click="getSave">发车</Button>
            </Col>
          </Row>
        </Form>
      </Drawer>
    </div>
</template>

<script>
  import giveCar from '../comp/readCard'
    export default {
        name: "addyypd",
      data(){
          return{
            giveCar:giveCar,
            DrawerVal:false,
            formData:{},
            CarList:[],
            sfaemanlist:[]
          }
      },
      created(){
          this.getCarList();
          this.getSafemanList()
      },
      methods:{
        getSave(){
          delete this.formData.xyList;
          delete this.formData.lcCl;
          if(this.formData.xySl == ''|| this.formData.xySl == NaN ){
            this.formData.xySl = 0
          }
          if(this.formData.cardNo == null || this.formData.cardNo == ''){
            this.readkar();
            return
          }
          // console.log(this.formData);
          this.$http.post('/api/lcjl/kslc',this.formData).then((res)=>{
            if(res.code == 200){
              this.DrawerVal = false;
              this.$emit('getCarList');
              this.formData = {};
              this.$parent.getCarList();
              this.swal({
                title:'发车成功',
                type:'success',
                showCancelButton: false,
                confirmButtonText: '确定',
              }).then((val)=>{
                if(val.value){
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
              // console.log('v.showFQfzkp',v.showFQfzkp);
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
              // console.log(res);
            }
          })
        },
        getSafemanList(){
          this.$http.post('/api/zgjbxx/getAqy',{notShowLoading:'true'}).then((res)=>{
            // console.log(res);
            if(res.code == 200){
              this.sfaemanlist = res.result
            }else {
              this.$Message.info(res.message);
            }
          })
        },
        show(){
          this.DrawerVal = true
          this.formData = this.$parent.formData;
          // console.log('open');
          // console.log(this.formData);
          this.getSafemanList()
        },
        open(){},
        close() {
          // console.log('close');
          this.formData = {};
          this.DrawerVal = false;
          this.sfaemanlist = [];
        },
      },
    }

</script>

<style scoped>

</style>
