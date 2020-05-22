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
                <Select v-model="selectRow.jgdm" placeholder="请选择驾校" @on-change="SelectChangeJx">
                  <Option v-for="(item,index) in bmdList" :value="item.value" :key="index">{{ item.label }}</Option>
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
                <Select v-model="selectRow.status" placeholder="请选择状态" @on-change="SelectChangeJx">
                  <Option v-for="(item,index) in ztList" :value="item.key" :key="item.key">{{ item.val }}</Option>
                </Select>
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="8">
              <FormItem label="科目一状态:" :label-width="90">
                <Select v-model="selectRow.firSub" placeholder="科目一状态" >
                  <Option v-for="(item,index) in firList" :value="item.key" :key="item.key">{{ item.val }}</Option>
                </Select>
              </FormItem>
            </Col>
            <Col span="8">
              <FormItem label="缴费时间:" :label-width="70">
<!--                v-model="selectRow.firSubPaymentTime"-->
                <DatePicker v-model="selectRow.firSubPaymentTime"
                            format="yyyy-MM-dd"
                            placeholder="科目一缴费时间"
                            style="width: 100%" @on-change="(t) => { sb(t,'firSubPaymentTime')}"></DatePicker>
<!--                <Input prefix="ios-clipboard" type="text" placeholder="科目一缴费时间" v-model="selectRow.firSub"/>-->
              </FormItem>
            </Col>
            <Col span="8">
              <FormItem label="考试时间:" :label-width="70">
                <DatePicker v-model="selectRow.firSubTestTime"
                            @on-change="(t) => { sb(t,'firSubTestTime')}"
                            format="yyyy-MM-dd"
                            placeholder="考试时间"
                            style="width: 100%"></DatePicker>
<!--                <Input prefix="ios-clipboard" type="text" placeholder="科目一考试时间" v-model="selectRow.firSub"/>-->
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="8">
              <FormItem label="科目二状态:" :label-width="90">
                <Select v-model="selectRow.secSub" placeholder="科目二状态" >
                  <Option v-for="(item,index) in secList" :value="item.key" :key="item.key">{{ item.val }}</Option>
                </Select>
              </FormItem>
            </Col>
            <Col span="8">
              <FormItem label="缴费时间:" :label-width="70">
                <DatePicker v-model="selectRow.secSubPaymentTime"
                            @on-change="(t) => { sb(t,'secSubPaymentTime')}"
                            format="yyyy-MM-dd"
                            placeholder="缴费时间"
                            style="width: 100%"></DatePicker>
              </FormItem>
            </Col>
            <Col span="8">
              <FormItem label="考试时间:" :label-width="70">
                <DatePicker v-model="selectRow.secSubTestTime"
                            @on-change="(t) => { sb(t,'secSubTestTime')}"
                            format="yyyy-MM-dd"
                            placeholder="考试时间"
                            style="width: 100%"></DatePicker>
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="8">
              <FormItem label="科目三状态:" :label-width="90">
                <Select v-model="selectRow.thirdSub" placeholder="科目三状态" >
                  <Option v-for="(item,index) in secList" :value="item.key" :key="item.key">{{ item.val }}</Option>
                </Select>
              </FormItem>
            </Col>
            <Col span="8">
              <FormItem label="缴费时间:"  :label-width="70">
                <DatePicker v-model="selectRow.thirdSubPaymentTime"
                            @on-change="(t) => { sb(t,'thirdSubPaymentTime')}"
                            format="yyyy-MM-dd"
                            placeholder="科目三缴费时间"
                            style="width: 100%"></DatePicker>
              </FormItem>
            </Col>
            <Col span="8">
              <FormItem label="考试时间:" :label-width="70">
<!--                v-model="selectRow.thirdSubTestTime"-->
                <DatePicker  v-model="selectRow.thirdSubTestTime"
                  @on-change="(t) => { sb(t,'thirdSubTestTime')}"
                            format="yyyy-MM-dd"
                            placeholder="科目三考试时间"
                            style="width: 100%"

                ></DatePicker>
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="8">
              <FormItem label="科目四状态:"  :label-width="90">
                <Select v-model="selectRow.forthSub" placeholder="科目四状态" >
                  <Option v-for="(item,index) in forthList" :value="item.key" :key="item.key">{{ item.val }}</Option>
                </Select>
              </FormItem>
            </Col>
            <Col span="8">
                <span></span>
            </Col>
            <Col span="8">
              <FormItem  label="考试时间:"  :label-width="70">
                <DatePicker v-model="selectRow.forthSubTestTime"
                            @on-change="(t) => { sb(t,'forthSubTestTime')}"
                            format="yyyy-MM-dd"
                            placeholder="科目四考试时间"
                            style="width: 100%"></DatePicker>
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
        firList:[ {key:'30', val:'不合格'},{key:'40', val:'合格'}],
        secList:[{key:'30',val: '不合格'},{key:'40', val:'合格'}],
        forthList:[ {key:'10', val:'不合格'}, {key:'20', val:'合格'}],
        jxName:'',
        zt:'',
        status:'',
        bmdList:[]
      }
    },
    created(){
      this.dui = this.selectRow.jgmc.split('/')[0]
      this.jxName = this.selectRow.jgmc.split('/')[1]
      this.status = this.selectRow.status
      this.getJTjx()
      this.getZTList()
      this.getBmdList()
      console.log(this.selectRow);
      console.log(this.bmdList)
    },
    methods:{
      sb(t,k){
        if(t == ""){
          this.selectRow[k]  = null
        }else {
          console.log(t?this.moment(t).format("YYYY-MM-DD"):'空');
          this.selectRow[k] = this.moment(t).format("YYYY-MM-DD");
        }
      },
      getBmdList(){
        var v = this
        this.$http.get(this.apis.FRAMEWORK.getCurrentOrgTree,{timers:new Date().getTime()}).then((res) => {
          if (res.code === 200) {
            function tree(arr) {
              if(arr[0].value.length == 9){
                if(arr.length == 1){
                  v.user.jgdm = arr[0].value
                  // console.log('111111',arr[0]);
                  v.bmdChange([arr[0].value],[arr[0]])
                  v.user.jgmc = arr[0].label + '/' + v.jxName
                  return arr
                }else {
                  return arr
                }
              }else if(arr[0].value.length == 6){
                return arr[0].children
              }else if(arr[0].value.length == 3) {
                return arr[0].children[0].children
              }
            }
            this.bmdList = tree(res.result)
            // console.log("报名点信息",this.dictList.bmd.data )
          } else {
            this.swal({
              text: '无法加载报名点信息!',
              type: 'error'
            });
          }
        }).catch((error) => {
          this.swal({
            text: this.apis.NETWORK_ERR_STR,
            type: 'error'
          });
        })
      },
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
        this.$http.post('/api/traineeinformation/updateTraineeInfo',{
          id:this.selectRow.id,
          name:this.selectRow.name,
          phone:this.selectRow.phone,
          jgdm:this.selectRow.jgdm,
          idCardNo:this.selectRow.idCardNo,
          status:this.selectRow.status,
          serialNum: this.selectRow.serialNum,
          firSub: this.selectRow.firSub,
          firSubPaymentTime:this.selectRow.firSubPaymentTime?this.moment( this.selectRow.firSubPaymentTime).format("YYYY-MM-DD"):"",
          firSubTestTime: this.selectRow.firSubTestTime?this.moment(this.selectRow.firSubTestTime).format("YYYY-MM-DD"):"",
          secSub: this.selectRow.secSub,
          secSubPaymentTime: this.selectRow.secSubPaymentTime?this.moment(this.selectRow.secSubPaymentTime).format("YYYY-MM-DD"):"",
          secSubTestTime: this.selectRow.secSubTestTime?this.moment(this.selectRow.secSubTestTime).format("YYYY-MM-DD"):"",
          thirdSub:this.selectRow.thirdSub,
          thirdSubPaymentTime:this.selectRow.thirdSubPaymentTime?this.moment(this.selectRow.thirdSubPaymentTime).format("YYYY-MM-DD"):"",
          thirdSubTestTime:this.selectRow.thirdSubTestTime?this.moment(this.selectRow.thirdSubTestTime).format("YYYY-MM-DD"):"",
          forthSub:this.forthSub,
          forthSubTestTime:this.selectRow.forthSubTestTime?this.moment(this.selectRow.forthSubTestTime).format("YYYY-MM-DD"):""
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
