<template>
  <div>
    <Modal v-model="showModal"
           title="意见反馈"
           :closable='false' :mask-closable="false">
      <Row style="margin-bottom: 12px">
        <Col span="3">
          {{mess.traineeName}}
        </Col>
        <Col span="19">
          {{mess.content}}
        </Col>
      </Row>
      <Row style="margin-bottom: 12px">
        <Col span="3">
          反馈处理
        </Col>
        <Col span="19">
          <Input v-model="reply" type="textarea" :autosize="{minRows: 3,maxRows: 3}" placeholder="反馈信息" />
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
        showModal: true,
        reply:''
      }
    },
    watch:{
    },
    created(){
    },
    methods: {
      close(){
        this.$parent.compName = ''
      },
      submit(){
        this.$http.post('/api/feedback/yjfk',{id:this.mess.id,reply:this.reply}).then(res=>{
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
