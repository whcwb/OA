<template>
  <div>
    <Modal
      v-model="modalShow"
      width="50%"
      :closable='false'
      :mask-closable="false">
      <div slot="header" class="box_row colCenter">
        <!--<Icon type="md-rose" size="38"/>-->
        <div class="box_row_100" style="font-size: 22px;font-weight: 700;margin-left:12px">
          {{tit}}
        </div>
        <Button @click="close" style="margin: 0 12px">取消</Button>
        <Button type="success" @click="save" style="margin: 0 12px">完成</Button>
      </div>
      <div>
        <div class="box_col">
          <div>
            <Form ref="saveForm" :model="form" :rules="ruleValidate" :label-width="100">
              <Row>
                <Col span="12">
                  <FormItem prop="chargeCode" label="收费项">
                    <Select filterable clearable  v-model="form.chargeCode"
                            placeholder="请选择收费项"
                            size="large">
                      <Option v-for="item in FYLX" :value="item.key">{{item.val}}</Option>
                    </Select>
                  </FormItem>
                </Col>
                <Col span="12">
                  <FormItem prop="amount" label="实收金额">
                    <Input v-model="form.amount" placeholder="请输入金额" size="large"></Input>
                  </FormItem>
                </Col>
              </Row>
              <Row v-if="form.chargeCode == '9999'">
                <Col span="12">
                  <FormItem prop="chargeName" label="支出项名称">
                    <Input type="text" v-model="form.chargeName" size="large" placeholder="请输入支出项名称" />
                  </FormItem>
                </Col>
              </Row>
            </Form>
          </div>
        </div>
      </div>
      <div slot="footer"></div>
    </Modal>
  </div>
</template>

<script>
  import {forEach} from '@/libs/tools'

  export default {
    name: "addItem",
    data() {
      return {
        tit:'新增支出项',
        bmList:false,
        modalShow: true,
        treeList: [],
        check: [],
        ruleValidate: {
          chargeCode:[
            {required: true, message: "请选择支出项类型", trigger: 'change'}
          ],
          chargeName: [
            {required: true, message: "请填写支出项名称", trigger: 'blur'}
          ],
          amount: [
            {required: true, message: '请填写支出金额', trigger: 'blur'}
          ]
        },
        form: {
          chargeCode:'',
          amount:'0',
          chargeName: '',
          inOutType: '10',
          jgdms: []
        },
        FYLX:[],//费用支出类型
        FYLXCode:'ZDCLK1003'
      }
    },
    props:{
      changeMess:{
        type:Object,
        default:{}
      }
    },
    created() {
      if(this.changeMess.id){
        this.tit = '修改支出项'
        delete this.changeMess.institutions
        this.form  = this.changeMess
      }
      this.getDictList()
    },
    methods: {
      getDictList() {
        this.FYLX = this.dictUtil.getByCode(this, this.FYLXCode);
      },
      save() {
        for (let item of this.FYLX){
          if(item.key == this.form.chargeCode && this.form.chargeCode != '9999'){
            this.form.chargeName = item.val
          }
        }

        this.$refs.saveForm.validate((valid) => {
          if(valid){

            if(this.changeMess.id){
              this.$http.post(this.apis.SFX.UPDATE,this.form).then((res)=>{
                if (res.code == 200) {
                  this.$parent.getPagerList()
                  this.close()
                }
              })
            }else {
              this.$http.post(this.apis.SFX.SAVE, this.form).then((res) => {
                if (res.code == 200) {
                  this.$parent.getPagerList()
                  this.close()
                }
                this.$Message.info(res.message);
              })
            }
          }
        })
      },
      close() {
        this.$parent.compName = ''
      }
    }
  }
</script>

<style scoped>

</style>
