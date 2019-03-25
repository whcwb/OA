<template>
  <div>
    <Modal v-model="showModal"
           title="意见反馈"
           :closable='false' :mask-closable="false">
      <Row style="margin-bottom: 12px;font-size: 18px">
        <Col span="12">
          <div>
            投诉人：{{mess.traineeName}}
          </div>
          <div style="margin-top: 12px">
            教练员：{{mess.coachName}}
          </div>
        </Col>
        <Col span="12">
          <Form>
            <FormItem>
              <div slot="label" style="font-size: 16px">
                回访状态
              </div>
              <Select v-model="form.status">
                <Option v-for="(item,index) in tpyList" :value="item.key" :key="index">{{ item.val }}</Option>
              </Select>
            </FormItem>
          </Form>
        </Col>
      </Row>
      <Row style="margin-bottom: 12px">
        <Col span="3">
          反馈处理
        </Col>
        <Col span="19">
          <Input v-model="form.feedback" type="textarea" :autosize="{minRows: 3,maxRows: 3}" placeholder="反馈信息" />
        </Col>
      </Row>
      <div slot="footer">
        <Button type="default" style="color: #949494" @click="close">关闭</Button>
        <Button type="warning" @click="submit">确认</Button>
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
        form:{
          id:'',
          feedback:'',
          status:'1'
        },
        reply:'',
        tpyList:[]
      }
    },
    watch:{
    },
    created(){
      this.getDivList()
    },
    methods: {
      getDivList(){
        this.tpyList = this.dictUtil.getByCode(this,'ZDCLK1023');
      },
      close(){
        this.$parent.compName = ''
      },
      submit(){
        this.form.id = this.mess.id
        this.$http.post('/api/complainrecord/tscl',this.form).then(res=>{
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
