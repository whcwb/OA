<template>
  <div>
    <Modal v-model="showModal" height="300" width="900"
           title="驳回原因"
           :closable='false' :mask-closable="false">
      <Row>
        <Col span="24">
          <Input type="textarea" v-model="user.reduceRemark" :rows="10" placeholder="请输入驳回原因" ></Input>
        </Col>
      </Row>
      <div slot="footer">
        <Button type="default" style="color: #949494" @click="close">关闭</Button>
        <Button type="error" @click="submit">驳回</Button>
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
        user:{
          reduceRemark: '',
          reduceStatus:'20',
          id: this.userAudit.id
        }
      }
    },
    methods: {
      close(){
        this.$parent.compName = ''
      },
      submit(){
        // this.$parent.data1.push(this.user);
        if (this.user.reduceRemark == ""){
            this.swal({
              type:'error',
              text:'请先输入驳回原因！'
            });
            return;
        }

        this.$http.post(this.apis.TRAINEEREDUCE.AUDIT, this.user).then((res) => {
          let msg = res.message;
          if (res.code === 200) {
            this.swal({
              text: msg,
              type: 'success'
            });

            this.util.getPageData(this.$parent);
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
