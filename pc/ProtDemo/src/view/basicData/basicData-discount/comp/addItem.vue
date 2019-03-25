<template>
  <div>
    <Modal
      v-model="modalShow"
      width="70%"
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
      <div :style="{height:'600px'}">
        <div class="box_col">
          <div class="">
            <Form :label-width="80" ref="saveForm" :model="form" :rules="ruleValidate">
              <Row>
                  <Col span="8">
                      <FormItem label="优惠标题" prop="reduceName">
                        <Input v-model="form.reduceName" placeholder="请输入优惠项名称"></Input>
                      </FormItem>
                  </Col>
                  <Col span="8">
                    <FormItem label="优惠金额">
                      <Input v-model="form.reduceAmount" :number="true" placeholder="请输入优惠金额"></Input>
                    </FormItem>
                  </Col>
                  <Col span="8">
                    <FormItem label="优惠时间">
                      <DatePicker v-model="form.time" split-panels
                                  format="yyyy-MM-dd"
                                  type="daterange"
                                  placeholder="优惠时间"
                                  @on-change="DatePickerClick"
                                  style="width: 100%"></DatePicker>
                    </FormItem>
                  </Col>
              </Row>
              <Row>
                <Col span="8">
                  <FormItem label="审批人员" prop="">
                    <div :style="{height:AF.getPageHeight()/10*5+'px',overflow: 'auto'}">
                      <Tree :data="Cascaderdata" show-checkbox
                            @on-check-change="TagClick"></Tree>
                    </div>
                  </FormItem>
                </Col>
                <Col span="16">
                  <div class="box_row" :style="{paddingLeft: '8px',height:AF.getPageHeight()/10*5+'px'}">
                    <div class="box_row_1auto" style="padding-top: 10px">
                      <div class="box_col">
                        <div style="font-size: 16px;font-weight: 600;padding: 4px">
                          适用部门：
                        </div>
                        <div class="box_row_1auto" style="height: 430px">
                          <new-tree  @checkList="checkList" :checkList="jglist"></new-tree>
                        </div>
                      </div>
                    </div>
                  </div>
                </Col>
              </Row>
            </Form>
          </div>
          <div class="box_col_100">
            <!--<div class="box_row" :style="{paddingLeft: '8px',height:AF.getPageHeight()/10*5-50+'px'}">-->
              <!--<div class="box_row_1auto" style="padding-top: 10px">-->
                <!--<div class="box_col">-->
                  <!--<div style="font-size: 16px;font-weight: 600;padding: 4px">-->
                    <!--适用部门：-->
                  <!--</div>-->
                  <!--<div class="box_row_1auto" style="height: 430px">-->
                    <!--<new-tree  @checkList="checkList" :checkList="jglist"></new-tree>-->
                  <!--</div>-->
                <!--</div>-->
              <!--</div>-->
            <!--</div>-->
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
    data() {
      return {
        tit:'新增优惠项',
        bmList:false,
        modalShow: true,
        treeList: [],
        check: [],
        ruleValidate: {
          time: [
            {required: true, type: 'daterange', message: "请选择优惠时间", trigger: 'change'}
          ],
          reduceName: [
            {required: true, message: '请选择优惠名称', trigger: 'blur'}
          ],
          reduceAmount: [
            {required: true, type:'number' , message: '请选择优惠金额', trigger: 'blur'}
          ],
          verifier: [
            {required: true, message: '请选择审批人员', trigger: 'blur'}
          ],
          verifyLevel: [
            {required: true, message: '请选择审批部门', trigger: 'blur'}
          ]
        },
        CascaderVal: [],
        Cascaderdata: [],
        jglist:[],
        form: {
          time: [],
          reduceName: '',
          reduceAmount:0,
          reduceStartTime: '',
          reduceEndTime: '',
          verifier: '',
          verifyLevel:'',
          jgdms: []
        },
        checkPush:[]
      }
    },
    props:{
      changeMess:{
        type:Object,
        default:{}
      }
    },
    watch: {
      CascaderVal:function(n,o){
        this.form.verifier = n[1]
        this.form.verifyLevel = n[0]
      },
      'form.time': function (n, o) {
        this.form.reduceStartTime = this.AF.trimDate(n[0])
        this.form.reduceEndTime = this.AF.trimDate(n[1])
      }
    },
    created() {
      // console.log('+=+',this.changeMess);
      if(this.changeMess.id){
        this.checkPush = this.changeMess.verifier.split(',')
        this.tit = '修改优惠项'
        this.changeMess.jgdms = []
        this.changeMess.institutions.forEach((item,index)=>{
          this.changeMess.jgdms.push(item.jgdm)
          this.jglist.push({
            'jgdm':item.jgdm,
            'jgmc':item.jgmc
          })
        })
        this.changeMess.time = [this.changeMess.reduceStartTime,this.changeMess.reduceEndTime]
        // this.CascaderVal = [this.changeMess.verifyLevel,this.changeMess.verifier]


        this.form = this.changeMess

      }
      this.getJlmess()
      this.form.time = [new Date(),new Date(Date.parse(new Date())+1000*60*60*24*30)]
    },
    methods: {
      TagClick(list){
        this.checkPush=[]
        list.forEach((item, index) => {
          if (!item.children) {
            // item = JSON.stringify(item)
            this.checkPush.push(item.value)
          }
        })
      },
      getJlmess() {
        this.$http.get(this.apis.TGTHX.JLMESS,{timers:new Date().getTime()}).then((res) => {

          if(this.checkPush.length == 0){
            this.Cascaderdata = res.result
          }else {
            res.result.forEach((item, index) => {
              item.checked = true
              item.children.forEach((itC, inC) => {
                this.checkPush.forEach((val, key) => {
                  if (itC.value == val) {
                    itC.checked = true
                  } else {
                    item.checked = false
                  }
                })
              })
            })

            this.Cascaderdata = res.result
          }
          // tree.forEach((item, index) => {
          //   item.checked = true
          //   item.children.forEach((itC, inC) => {
          //     this.checkList.forEach((val, key) => {
          //       if (itC.jgdm == val.jgdm) {
          //         itC.checked = true
          //       } else {
          //         item.checked = false
          //       }
          //     })
          //   })
          // })
        })
      },
      DatePickerClick(n){
        this.form.reduceStartTime = this.AF.trimDate(n[0])
        this.form.reduceEndTime = this.AF.trimDate(n[1])
      },
      save() {
        if(this.checkPush.length == 0){
          this.swal({
            title: '请选择优惠审核人！',
            type: 'warning'
          });
          return
        }
        delete this.form.sysYhs
        this.form.verifier = this.checkPush.join(',')
        this.$refs.saveForm.validate((valid) => {
          delete this.form.time
          if (valid) {
            if(this.changeMess.id){
              delete this.changeMess.institutions
              delete this.changeMess.sysYh
              this.$http.post(this.apis.TGTHX.UPDATE,this.form).then((res)=>{
                if (res.code == 200) {
                  this.$parent.getPagerList()
                  this.close()
                }
              })
            }else {
              this.$http.post(this.apis.TGTHX.SAVEY, this.form).then((res) => {
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
        this.form.jgdms = []
        list.forEach((tiem,index)=>{
          this.form.jgdms.push(tiem.jgdm)
        })
      }
    }
  }
</script>
