<template>
    <div>
      <Modal
        title="填写金额"
        v-model="modal"
        :closable="false"
        @on-ok="OK"
        @on-cancel="close"
        :mask-closable="false">
          <div>
              <Form :model="params" :rules="ruleInline">
                <FormItem prop="amount">
                  <Input type="text" v-model="params.amount" placeholder="金额">
                    <Icon type="logo-yen" slot="prepend"></Icon>
                  </Input>
                </FormItem>
                <FormItem prop="remark" style="padding-top: 2px">
                  <Input type="textarea" v-model="params.remark" placeholder="备注"  :maxlength="30"
                         :rows="2" id="textarr" >
                  </Input>
                </FormItem>
              </Form>
          </div>
      </Modal>
    </div>
</template>

<script>
    export default {
        name: "edit",
      props: {
         mess:{
           type: Object,
           default: ()=> {
             return {}
           }
         }
      },
      data() {
          return {
            modal: true,
            params:{
              amount:'',
              id: '',
              remark:''
            },
            ruleInline: {
              amount: [
                { required: true, message: '请填写缴费金额', trigger: 'blur' }
              ]
            }
          }
      },
      created() {
          this.params.id = this.mess.id
      },
      methods: {
        OK(){
              if(!this.params.amount){
                this.swal({
                  text: '请填写金额',
                  type: 'error'
                }).then(r => {
                  this.close();
                });
                return
              }
              this.$http.post('/api/traineeinformation/saveChargeK3', this.params).then(res => {
                  if(res.code == 200){
                    this.$Message.info(res.message);
                  }else{
                    this.$Message.error(res.message);
                  }
                  this.close();
              })

        },
        close(){
          this.$parent.compName = '';
        }
      }
    }
</script>

<style scoped>

</style>
