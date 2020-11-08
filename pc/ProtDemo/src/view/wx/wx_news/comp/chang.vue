<template>
  <div>
    <Modal v-model="showModal"
           title="意见反馈"
           :closable='false' :mask-closable="false">
      <Row style="margin-bottom: 12px">
        <Col span="3">
          下发时间：
        </Col>
        <Col span="19">
          <DatePicker v-model="form.estimatedSendTime" type="datetime"
                      :options="options" placeholder="信息下发时间" style="width: 200px"></DatePicker>
        </Col>
      </Row>
      <div slot="footer">
        <Button type="default" style="color: #949494" @click="close">关闭</Button>
        <Button type="warning" @click="submit">反馈</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
  export default {
    name: "audit",
    props: {
      mess:{
        type: Object,
        default: {}
      }
    },
    data(){
      return{
        options: {
          disabledDate (date) {
            return date && date.valueOf() < Date.now() - 86400000+(24*60*60*1000);
          }
        },
        showModal: true,
        form:{
          estimatedSendTime:'',
          id:''
        }
      }
    },
    watch:{
    },
    created(){
      this.form.id = this.mess.id
    },
    methods: {
      close(){
        this.$parent.compName = ''
      },
      submit(){
        this.form.estimatedSendTime = this.AF.trimTime(this.form.estimatedSendTime)
        this.$http.post('/api/message/repeat',this.form).then(res=>{
          if(res.code == 200){
            this.$Message.success(res.message);
            this.close()
            this.$parent.getPagerList()
          }
        }).catch(err=>{

        })
      }
    }
  }
</script>

<style scoped>

</style>
