<template>
  <div>
    <Modal v-model="showModal"
           width="600"
           :closable='false' :mask-closable="false">
      <div slot="header">
        <p>车型变更</p>
      </div>
      <div>
        <div class="box_row messLIst">
          <div class="box_row_100">
            姓名 : {{userMess.name}}
          </div>
          <div class="box_row_200">
            报名点 : {{userMess.jgmc}}
          </div>
          <div class="box_row_100">
            车型 : {{userMess.carType}}
          </div>
        </div>
        <div class="box_row messLIst">
          <div class="box_row_100">
            证件号码 : {{userMess.idCardNo}}
          </div>
          <div class="box_row_100">
            预留手机号 : {{userMess.phone}}
          </div>
        </div>

        <div>
          <h2>车型变更 :</h2>
        </div>
        <div class="carListTypSty">
          <RadioGroup v-model="N_Typ">
            <Radio v-for="(it,index) in carTypList" :label="it.key" :key="index">
              {{it.key}}
            </Radio>
          </RadioGroup>
        </div>
      </div>
      <div slot="footer">
        <Button type="default" style="color: #949494" @click="close">关闭</Button>
        <Button type="warning" @click="submit">车型变更</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
  export default {
    name: "changeCarTyp",
    props: {
      userMess: {
        type: Object,
        default: {}
      }
    },
    data() {
      return {
        showModal: true,
        N_Typ:'',
        carTypList:[]
      }
    },
    created() {
      console.log('++++', this.userMess);
      this.N_Typ = this.userMess.carType
      this.getCarList(this.userMess.jgdm)
    },
    methods:{
      getCarList(jgdm){
        this.$http.post('/api/chargeitemmanagement/getJgBx',{jgdm:jgdm}).then(res=>{
          console.log(res);
          if(res.code == 200){
            this.carTypList = res.result.carType
          }
        }).catch(err=>{})
      },
      submit(){
        this.$http.post('/api/traineeinformation/editCarTypeNoCharge',{
          id:this.userMess.id,
          carType:this.N_Typ
        }).then(res=>{
          if(res.code == 200){
            this.swal({
              title:res.message,
              type:'success'
            })
            this.close()
            this.$emit('changeAfter')
          }else {
            this.swal({
              title:res.message,
              type:'error'
            })
          }
        }).catch(err=>{})
      },
      close(){
        this.$emit('close')
      }
    }
  }
</script>

<style lang="less">
  .messLIst {
    .box_row_100,.box_row_200 {
      margin-right: 14px;
      font-size: 16px;
      font-weight: 600;
      padding: 8px 0;
    }
  }
  .carListTypSty{
    padding-left: 45px;
    .ivu-radio-group-item{
      font-size: 16px;
      font-weight: 600;
    }
  }
</style>
