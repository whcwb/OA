<template>
  <div>
    <Modal
      v-model="showModal"
      height="600"
      width="900"
      :closable='false'
      :mask-closable="false"
      :title="'修改车辆信息'">
      <Form
        ref="form"
        :rules="ruleInline"
        :label-width="100"
        :styles="{top: '20px'}">
        <div style="overflow: auto;height: 400px;width:800px">
          <Row>
            <Col span="12">
              <FormItem label="编号">
                <Input v-model="params.clBh" :maxlength=2 size="large" placeholder="请输入车辆编号" />
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem label="车牌号码">
                <Input v-model="params.clHm" size="large" placeholder="请输入车牌号" />
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="12">
              <FormItem label="车型">
                <Select v-model="params.clCx">
                  <Option v-for="item in CX" :value="item.key" :key="item.index">{{ item.val }}</Option>
                </Select>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem label="所属考场">
                <Input v-model="params.clKc" size="large" placeholder="请选择考场" />
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="12">
              <FormItem label="状态">
                <Select v-model="params.clZt">
                  <Option v-for="(item,index) in ZT" :value="item.key" :key="index">{{ item.val }}</Option>
                </Select>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem label="卡片绑定">
                <Input v-model="cardNo" size="large" readonly>
                  <span slot="append" style="cursor: pointer" @click="getCardNum">读卡</span>
                </Input>
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="12">
              <FormItem label="科目">
                <Select v-model="params.clKm">
                  <Option v-for="item in KM" :value="item.value" :key="item.value">{{ item.label }}</Option>
                </Select>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem prop="" label="车辆照片">
                <up-img :headImg="params.clImg" @txImg="(url)=>{txImg('clImg',url)}"></up-img>
              </FormItem>
            </Col>
          </Row>
        </div>
      </Form>
      <div slot='footer'>
        <Button type="default" @click="close" style="color: #949494">取消</Button>
        <Button type="primary" @click="GXCar">确定</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
  import upImg from '../../../../components/Upload/UploadImg'
  import readCard from '../../comp/readCard'
  export default {
    name: '',
    components:{
      upImg
    },
    props:{
      param: {
        type: Object,
        default: {},
        clImg: 'carImg'
      }
    },
    data() {
      return {
        params:{
          cardNo:''
        },
        cardNo:'',
        ZT:[],
        CX:[],
        KM: [
          {
            value: '2',
            label: '科目二'
          },
          {
            value: '3',
            label: '科目三'
          }
        ],
        v: this,
        showModal: true,
        operate: "修改",
        editMode: false,
        //新增数据
        showPsd: false,
        ruleInline: {},
        chCarNum:{
          id:'',
          cardNo:'',
          th:''
        }
      }
    },
    created() {
      this.params = JSON.parse(JSON.stringify(this.param))
      this.param.id = this.params.id
      this.chCarNum.id = this.params.id
      if(this.param.cardNo){
        this.cardNo = this.param.cardNo.substring(0,2)+'******'
      }
      this.getCLZT()
    },
    methods: {
      getCardNum(){
        readCard.getCardNum((key,res)=>{
          if(key){
            this.chCarNum.cardNo = res
            this.changeNum(res)
          }else {
            this.swal({
              title:'请重新放置卡片',
              type:'error'
            })
          }
        })
      },
      changeNum(num){
        var v = this
        this.$http.post('/api/lccl/updateCardNo',this.chCarNum).then(res=>{
          if (res.code == 200){
            this.params.cardNo = num
            this.cardNo = num.substring(0,2)+ '******'
          }else if(res.code == 505){
            this.swal({
              title:res.message,
              type:'warning',
              showCancelButton: true,
              confirmButtonText: '替换',
              cancelButtonText: '取消',
            }).then((p)=>{
              if(p.value){
                v.chCarNum.th = '****'
                v.changeNum(num)
              }
            })
          }else {
            this.swal({
              title:res.message,
              type:'error'
            })
          }
        }).catch((err)=>{})
      },
      txImg(A, url){
        this.param[A] = url
      },
      getCLZT(){
        this.ZT = this.dictUtil.getByCode(this,'ZDCLK1044');
        for (var i=0; i<this.ZT.length; i++) {
            let val = this.ZT[i]
            if(val.key === '01'){
              this.ZT.splice(i,1)
              break
          }
        }
        this.CX = this.dictUtil.getByCode(this,'ZDCLK0040');
      },
      close(){
        this.$parent.compName = '';
        this.$parent.getPagerList()
      },
      GXCar(){
        delete this.params.lcJl;
        this.$http.post(this.apis.CLWH.CLGX,this.params).then(res=>{
          if (res.code == 200){
            this.$Message.info(res.message);
            // this.showModal = false;
            this.$parent.compName = '';
            this.$parent.getPagerList();
          }else {
            this.swal({
              title:res.message,
              type:'warning',
              showCancelButton: false,
              confirmButtonText: '确定',
            })
          }
        })
      }
    }
  }
</script>

<style scoped>

</style>
