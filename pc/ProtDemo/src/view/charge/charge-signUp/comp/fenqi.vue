<template>
  <div>
    <Modal v-model="showModal"
           title="分期收费"
           :closable='false' :mask-closable="false">
      <Row style="margin-bottom: 12px;font-size:22px">
        <Col span="9">
          {{userAudit.jgmc}}
        </Col>
        <Col span="6">
          {{userAudit.name}}
        </Col>
        <Col span="4">
          {{userAudit.carType}}
        </Col>
        <Col span="5">
          {{userAudit.registrationFee}}
        </Col>
      </Row>
      <Row>
        <Form>
          <FormItem label="请输入分期尾款">
            <Col span="24">
              <Input :number="true" v-model="oweAmount" placeholder="请输入分期尾款" ></Input>
            </Col>
          </FormItem>
        </Form>
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
        oweAmount:0,
        reduceCode:'',
        messList:[]
      }
    },
    watch:{
      oweAmount:function (n,o) {
        if(n>this.userAudit.registrationFee){
          this.swal({
            target:'div',
            type:'error',
            title:'尾款金额不能大于报名费'
          }).then(mes=>{
            if(mes.value){
              this.oweAmount = o
            }
          })
        }
      }
    },
    created(){
    },
    methods: {
      selectReduce(sel){

      },
      close(){
        this.$parent.compName = ''
      },
      submit(){
        if (this.oweAmount == ""){
          this.swal({
            type:'error',
            text:'请先输入分期尾款！'
          });
          return;
        }
        let chargeId = ''
        if(this.userAudit.chargeRecord.id){
          chargeId = this.userAudit.chargeRecord.id
        }
        this.$http.post('/api/traineeinformation/updateToIns', {id :this.userAudit.id,
          oweAmount:this.oweAmount}).then((res) => {
          let msg = res.message;
          if (res.code === 200) {
            this.swal({
              text: msg,
              type: 'success'
            });
            this.$parent.getPagerList()
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
