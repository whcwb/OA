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
      <div :style="{height: form.chargeCode == '0000' ?'600px' : '100px'}">
        <div class="box_col">
          <div>
            <Form ref="saveForm" :model="form" :rules="ruleValidate" :label-width="100">
              <Row>
                <Col span="12">
                  <FormItem prop="chargeCode" label="收费项">
                    <Select filterable clearable  v-model="form.chargeCode"
                            placeholder="请选择收费项"
                            size="large" @on-change="test">
                      <Option v-for="item in FYLX" :value="item.key">{{item.val}}</Option>
                    </Select>
                  </FormItem>
                </Col>
                <Col span="12">
                  <FormItem label="实收金额">
                    <Input v-model="form.amount"
                           :number="true"
                           placeholder="请填输入收费金额" size="large"></Input>
                  </FormItem>
                </Col>
              </Row>
              <Row v-if="form.chargeCode == '0000'">
                <Col span="24">
                  <FormItem label="车型">
                    <CheckboxGroup v-model="carList" @on-change="CheckboxCar">
                      <Checkbox :label="item.key" v-for="(item,index) in CARDTYP">
                        <Icon type="ios-car" size="32"/>
                        <span style="font-size: 22px">{{item.val}}</span>
                      </Checkbox>
                    </CheckboxGroup>
                  </FormItem>
                </Col>
              </Row>
              <Row v-if="form.chargeCode == '0000'">
                <Col span="12">
                  <FormItem label="班型">
                    <Select filterable clearable  v-model="form.BX"
                            placeholder="请选择班型" size="large">
                      <Option v-for="item in BX" :value="item.key">{{item.val}}</Option>
                    </Select>
                  </FormItem>
                </Col>
                <!--<Col span="12">-->
                  <!--<FormItem label="车型">-->
                    <!--<Select filterable clearable  v-model="form.carType"-->
                            <!--placeholder="请选择车型" size="large">-->
                      <!--<Option v-for="item in CARDTYP" :value="item.key">{{item.val}}</Option>-->
                    <!--</Select>-->
                  <!--</FormItem>-->
                <!--</Col>-->
              </Row>
              <Row v-if="form.chargeCode == '4999'">
                <Col span="12">
                  <FormItem prop="chargeName" label="费用名称">
                    <Input type="text" v-model="form.chargeName" size="large" placeholder="请输入收费名称"/>
                  </FormItem>
                </Col>
              </Row>
            </Form>
          </div>
          <div v-if="form.chargeCode == '0000'" class="box_col_100">
            <div class="box_row" :style="{paddingLeft: '8px',height:AF.getPageHeight()/10*5-50+'px'}">
              <div class="box_row_1auto" style="padding-top: 10px">
                <div class="box_col">
                  <div class="" style="font-size: 16px;font-weight: 600;padding: 4px;">
                    适用部门：
                  </div>
                  <div class="box_row_1auto" style="height: 430px">
                    <new-tree  @checkList="checkList" :checkList="jglist"></new-tree>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div slot="footer"></div>
    </Modal>
  </div>
</template>

<script>
  export default {
    name: "addItem",
    props:{
      changeMess:{
        type:Object,
        default:{}
      }
    },
    data() {
      return {
        tit:'新增收费项',
        bmList:false,
        modalShow: true,
        // treeList: [],
        check: [],
        ruleValidate: {
          chargeCode:[
            {required: true, message: "请输填写收费项名称", trigger: 'change'}
          ],
          chargeName: [
            {required: true, message: "请输填写收费项名称", trigger: 'blur'}
          ],
          amount: [
            {required: true, type:'number' , message: '请输填写收费金额', trigger: 'blur'},
          ]
        },
        jglist:[],
        form: {
          carType:'',
          chargeCode:'',
          amount: '',
          chargeName: '',
          inOutType: '00',
          BX:'',
          jgdms: []
        },
        carList:[],
        FYLX:[],//费用类型
        FYLXCode:'ZDCLK1001',
        BX:[],
        BXCode:'ZDCLK1002',
        CARDTYP:[],
        CARDTYPCode:'ZDCLK0040'
      }
    },
    created() {
      this.getDictList()
      if(this.changeMess.id){
        this.tit = '修改收费项'
        this.BX.forEach((item,index)=>{
          if(this.changeMess.chargeName.split('-')[1] == item.val){
            this.changeMess.BX = item.key
          }
        })
        this.carList = this.changeMess.carType.split(',')
        // this.changeMess.BX = this.changeMess.chargeName.split('-')[1]
        this.changeMess.chargeCode = this.changeMess.chargeCode.split('-')[0]
        console.log(this.changeMess.chargeCode)
        this.changeMess.jgdms = []
        this.changeMess.institutions.forEach((item,index)=>{
          this.changeMess.jgdms.push(item.jgdm)
          this.jglist.push({
            'jgdm':item.jgdm,
            'jgmc':item.jgmc
          })
        })

        this.form = this.changeMess
      }
    },
    methods: {
      test(val){
        console.log(val,'111')
      },
      CheckboxCar(arr){
        this.form.carType = arr.join(',')
      },
      getDictList() {
        this.FYLX = this.dictUtil.getByCode(this, this.FYLXCode);
        this.BX = this.dictUtil.getByCode(this, this.BXCode);
        this.CARDTYP = this.dictUtil.getByCode(this, this.CARDTYPCode);
      },
      save() {
        for (let item of this.FYLX){
          if(item.key == this.form.chargeCode && this.form.chargeCode != '4999'){
            this.form.chargeName = item.val
          }
        }
        if(this.form.chargeCode=='0000' && this.form.carType==''){
          this.swal({
            title: '请选择车型',
            type: 'warning'
          });
          return
        }


        if(this.form.amount==''){
          this.swal({
            title: '请输入收费金额',
            type: 'warning'
          });
          return
        }

        if(this.form.chargeCode=='0000' && this.form.BX==''){
          this.swal({
            title: '请选择班型',
            type: 'warning'
          });
          return
        }
        const bx = this.form.BX;
        if(this.form.BX && this.form.BX!=''){
          for (let item of this.BX){
            if(item.key == this.form.BX){
              this.form.chargeName = this.form.chargeName + '-' + item.val
            }
          }
        }

        console.log("this.form.chargeCode",this.form.chargeCode);
        this.$refs.saveForm.validate((valid) => {
          if(valid){
            if(this.changeMess.id){
              delete this.changeMess.institutions
              let param = {carType: this.form.carType,
                amount: this.form.amount,
                chargeName: this.form.chargeName,
                inOutType: this.form.inOutType,
                jgdms: this.form.jgdms,
                chargeCode: this.form.chargeCode +   (bx?('-' + bx):''),
                id: this.changeMess.id
              };
              this.$http.post(this.apis.SFX.UPDATE,param).then((res)=>{
                if (res.code == 200) {
                  this.$parent.getPagerList()
                  this.close()
                }
              })
            }else {
              delete this.form.BX
              let param = {carType: this.form.carType,
                amount: this.form.amount,
                chargeName: this.form.chargeName,
                inOutType: this.form.inOutType,
                jgdms: this.form.jgdms,
                chargeCode: this.form.chargeCode + (bx?('-' + bx):'')
              };
              this.$http.post(this.apis.SFX.SAVE, param).then((res) => {
                if (res.code == 200) {
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
        this.$parent.getPagerList()
      },
      checkList(list){
        this.check = list
        this.form.jgdms = []
        list.forEach((tiem,index)=>{
          this.form.jgdms.push(tiem.jgdm)
        })
      }
    }
  }
</script>

<style scoped>

</style>
