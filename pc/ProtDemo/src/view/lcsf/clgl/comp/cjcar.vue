<template>
  <div>
    <Modal
      v-model="showModal"
      height="600"
      width="900"
      :closable='false'
      :mask-closable="false"
      :title="operate+'车辆信息'">
      <Form
        ref="form"
        :rules="ruleInline"
        :label-width="100"
        :styles="{top: '20px'}">
        <div style="overflow: auto;height: 400px;width:800px">
          <Row>
            <Col span="12">
              <FormItem label="编号">
                <Input v-model="param.clBh" :maxlength=2 size="large" placeholder="请输入车辆编号" />
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem label="车牌号码">
                <Input v-model="param.clHm" size="large" placeholder="请输入车牌号" />
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="12">
              <FormItem label="车型">
                <Select v-model="param.clCx">
                  <Option v-for="item in CX" :value="item.val" :key="item.index">{{ item.val }}</Option>
                </Select>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem label="所属考场">
                <Input v-model="param.clKc" size="large" placeholder="请选择考场" />
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="12">
              <FormItem label="科目">
                <Select v-model="param.clKm">
                  <Option v-for="item in KM" :value="item.value" :key="item.value">{{ item.label }}</Option>
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
              <FormItem prop="" label="车辆照片">
                <up-img :headImg="param.clImg" @txImg="(url)=>{txImg('clImg',url)}"></up-img>
              </FormItem>
            </Col>
          </Row>
          <!--<Button type="primary" @click="getCardNum">卡</Button>-->
        </div>
      </Form>
      <div slot='footer'>
        <Button type="default" @click="close" style="color: #949494">取消</Button>
        <Button type="primary" @click="addCar">确定</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
  // import upImg from '@/components/Upload/UploadImg'
  import upImg from '../../../../components/Upload/UploadImg'
  import readCard from '../../comp/readCard'
  export default {
    name: '',
    components:{
      upImg
    },
    data() {
      return {
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
        ZT:[],
        CX:[],
        model1:'',
        v: this,
        param:{
          clBh:'',
          clHm:'',
          clCx:'',
          clKc:'',
          clZt:'00',
          zgId:'',
          clKm:'',
          clImg:'',
          cardNo:'',
          th:''
        },
        cardNo:'',
        showModal: true,
        operate: "新增",
        editMode: false,
        //新增数据
        showPsd: false,
        ruleInline: {},
      }
    },
    created() {
      this.getCLZT();
      this.getSafemanList();
      this.util.initFormModal(this)
      //
    },
    methods: {
      getCardNum(){
        readCard.getCardNum((key,res)=>{
          if(key){
            this.param.cardNo = res
            this.cardNo = res.substring(0,2)+ '******'
          }else {
            this.swal({
              title:'请重新放置卡片',
              type:'error'
            })
          }
        })
      },
      // changeNum(res){
      //   this.$http.post('/api/lccl/updateCardNo',this.chCarNum).then(res=>{
      //     if (res.code == 200){
      //       this.params.cardNo = res
      //       this.cardNo = res.substring(0,2)+ '******'
      //     } else {
      //       this.swal({
      //         title:'卡片已绑定车辆,是否替换？',
      //         type:'warning',
      //         showCancelButton: true,
      //         confirmButtonText: '替换',
      //         cancelButtonText: '取消',
      //       }).then(p=>{
      //         if(p.value){
      //           this.chCarNum.th = '****'
      //           this.changeNum(res)
      //         }
      //       })
      //     }
      //   }).catch((err)=>{})
      // },
      txImg(A, url){
          this.param[A] = url
      },
      getCLZT(){
        this.ZT = this.dictUtil.getByCode(this,'ZDCLK1044');
        this.CX = this.dictUtil.getByCode(this,'ZDCLK0040')
      },
      getSafemanList(){
        this.$http.get('/api/zgjbxx/query').then(res=>{
          if(res.code == 200){
            this.safemanList = res.result
          }
        })
      },
      close(){
        this.$parent.compName = '';
        this.$parent.getPagerList()
      },
      addCar(){
        this.$http.post(this.apis.CLWH.CLADD,this.param).then(res=>{
          if (res.code == 200){
            this.$Message.info(res.message);
            this.$parent.compName = '';
            this.$parent.getPagerList()
          }else if(res.code == 505){
            this.swal({
              title:res.message,
              type:'error',
              showCancelButton: true,
              confirmButtonText: '替换',
              cancelButtonText: '取消',
            }).then(p=>{
              if(p.value){
                this.param.th = '****'
                this.addCar()
              }
            })
          }else {
            this.swal({
              title:res.message,
              type:'error'
            })
          }
        })
      }
    }
  }
</script>

<style scoped>

</style>
