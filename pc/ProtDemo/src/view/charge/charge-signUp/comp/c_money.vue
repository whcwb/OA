<template>
  <div>
    <Modal v-model="showModal"
           title="应收金额修改"
           :closable='false' :mask-closable="false">
      <Row>
        <Col span="24">
          <InputNumber :min="0" v-model="price"></InputNumber>
        </Col>
      </Row>
      <div slot="footer">
        <Button type="default" style="color: #949494" @click="close">关闭</Button>
        <Button type="warning" @click="submit">修改</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
  export default {
    name: "audit",
    props: {
      userAudit:{
        type: Object,
        default: {}
      }
    },
    data(){
      return{
        showModal: true,
        price:0,
        reduceCode:'',
        messList:[]
      }
    },
    watch:{
    },
    created(){
      this.price = this.userAudit.registrationFee
    },
    methods: {
      close(){
        this.$parent.compName = ''
      },
      submit(){
        this.$http.post('/api/traineeinformation/editRegistrationFee', {id :this.userAudit.id,
          registrationFee:this.price}).then((res) => {
          let msg = res.message;
          if (res.code === 200) {
            this.swal({
              text: msg,
              type: 'success'
            });
            if(this.userAudit.chargeRecord.id){
              this.$parent.getPagerList1()
            }else {
              this.$parent.getPagerList()
            }
            this.$parent.compName = '';
          }else{
            this.swal({
              text: msg,
              type: 'error'
            });
          }
        }).catch((error) => {
          this.swal({
            text: this.apis.NETWORK_ERR_STR,
            type: 'error'
          });
        });
      }
    }
  }
</script>

<style scoped>

</style>
