<template>
  <div>
    <Modal
      v-model="UpModal"
      :mask-closable="false"
      :closable="false"
      width="800"
      title="学员数据修改">
      <div>
        <Form :label-width="40">
          <Row>
            <Col span="12">
              <FormItem>
                <Input disabled prefix=" iconfont icon-dizhi" type="text" placeholder="报名点" v-model="dui"/>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem>
                <Input disabled prefix="ios-car-outline" type="text" placeholder="车型" v-model="selectRow.carType"/>
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="12">
              <FormItem>
                <Input prefix="ios-card-outline" type="text" placeholder="证件号码" v-model="selectRow.idCardNo"/>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem>
                <Input prefix="ios-person-outline" type="text" placeholder="姓名" v-model="selectRow.name"/>
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="12">
              <FormItem>
                <Input prefix="ios-call-outline" type="text" placeholder="手机号码" v-model="selectRow.phone"/>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem>
                <Select v-model="jxName" placeholder="请选择驾校" @on-change="SelectChangeJx">
                  <Option v-for="(item,index) in jxList" :value="item.val" :key="index">{{ item.val }}</Option>
                </Select>
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="12">
              <FormItem>
                <Input prefix="ios-clipboard" type="text" placeholder="流水号码" v-model="selectRow.serialNum"/>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem>
                <Select v-model="status" placeholder="请选择状态" @on-change="SelectChangeJx">
                  <Option v-for="(item,index) in ztList" :value="item.val" :key="index">{{ item.val }}</Option>
                </Select>
              </FormItem>
            </Col>
          </Row>
        </Form>
      </div>
      <div slot='footer'>
        <Button type="default" @click="colse" style="color: #949494">取消</Button>
        <Button type="primary" @click="save">确定</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
  export default {
    name: "upData",
    props: {
      selectRow: {
        type: Object,
        default: {}
      }
    },
    data() {
      return {
        UpModal: true,
        dui:'',
        jxList:[],
        ztList:[],
        jxName:'',
        zt:'',
        status:''
      }
    },
    created(){
      this.dui = this.selectRow.jgmc.split('/')[0]
      this.jxName = this.selectRow.jgmc.split('/')[1]
      this.status = this.selectRow.status
      this.getJTjx()
      this.getZTList()
      console.log(this.selectRow);
    },
    methods:{
      getZTList(){
        this.ztList = this.dictUtil.getByCode(this,'ZDCLK1010');
        console.log(this.ztList);
      },
      getJTjx(){
        this.jxList = this.dictUtil.getByCode(this,'ZDCLK1019');
        console.log(this.jxList);
      },
      SelectChangeJx(){

      },
      colse(){
        this.$parent.compName = ''
      },
      changeZt(){
        this.$http.post('/api/traineeinformation/updateTraineeInfo',{
          serialNum : this.selectRow.serialNum,
          status: this.status
        }).then((res)=>{
          if(res.code == 200){
            v.$parent.compName = ''
            v.$parent.getPager()
            this.$Message.success(res.message);
          }else {
            this.$Message.error(res.message);
          }
        }).catch(err=>{
          this.$Message.error(res.message);
        })
      },
      save(){
        var v = this
        this.$http.post('/api/traineeinformation/update',{
          id:this.selectRow.id,
          name:this.selectRow.name,
          phone:this.selectRow.phone,
          jgmc:this.dui+'/'+this.jxName,
          idCardNo:this.selectRow.idCardNo
        }).then(res=>{
          if(res.code == 200){
            v.$parent.compName = ''
            v.$parent.getPager()
            this.$Message.success(res.message);
            this.changeZt()
          }else {
            this.$Message.error(res.message);
          }
        }).catch(err=>{
          this.$Message.error(res.message);
        })

      }
    }
  }
</script>

<style scoped>

</style>
