<template>
  <div>
    <Modal v-model="showModal"
           title="出库"
           width="900"
           :closable='false' :mask-closable="false">
      <div>
        <Form :label-width="100" ref="addmess" :model="addmess" :rules="ruleInline">
          <div>
            <Row>
              <Col span="8">
                <FormItem label='物品名称：'>
                  <Input type="text" readonly v-model="mess.kcMc" placeholder="物品名称"></Input>
                </FormItem>
              </Col>
              <Col span="8">
                <FormItem label='物品规格：'>
                  <Input type="text" readonly v-model="mess.kcLx" placeholder="物品规格"></Input>
                </FormItem>
              </Col>
              <Col span="8">
                <FormItem label='库存数量：'>
                  <InputNumber style="width: 100%" readonly :min="0" v-model="mess.kcSl"></InputNumber>
                </FormItem>
              </Col>
            </Row>
          </div>
          <div>
            <Row>
              <Col span="8">
                <FormItem prop="zgId" label='领用人：'>
                  <Select v-model="addmess.zgId" placeholder="领用人" not-found-text="暂无信息"
                          filterable clearable>
                    <Option v-for="(item,index) in zgList" :value="item.id" :key="index">{{item.xm}}</Option>
                  </Select>
                </FormItem>
              </Col>
              <Col span="8">
                <FormItem label='领用数量：'>
                  <InputNumber style="width: 100%" :min="0" v-model="addmess.sl"></InputNumber>
                </FormItem>
              </Col>
            </Row>
          </div>
          <div>
            <FormItem label='备注信息：'>
              <Input type="textarea" :rows="3" v-model="addmess.bz" placeholder="备注信息"></Input>
            </FormItem>
          </div>
        </Form>
      </div>


      <div slot="footer">
        <Button type="default" style="color: #949494" @click="close">关闭</Button>
        <Button type="warning" @click="submit">出库</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
  export default {
    name: "newClass",
    props: {
      mess: {
        type: Object,
        default: {
          kcLx: '',
          kcMc: '',
          kcSl: 0
        }
      }
    },
    watch: {
      'addmess.sl': function (n, o) {
        if (n > this.mess.kcSl) {
          this.addmess.sl = this.mess.kcSl;
          setTimeout(() => {
            this.swal({
              type: 'warning',
              title: '领用数量不能大于库存量！！！'
            })
          }, 10)

        }
      }
    },
    data() {
      return {
        showModal: true,
        addmess: {
          id: '',
          zgId: '',
          sl: 0
        },
        ruleInline: {
          zgId: [
            {required: true, message: '请输选着领用人', trigger: 'change'}
          ],
        },
        zgList: []
      }
    },
    created() {
      if (this.mess.id) {
        this.addmess.id = this.mess.id
      }
      this.getZgList()
    },
    methods: {
      getZgList() {//获取推荐人
        this.$http.get('/api/zgjbxx/query', {params: {ls: '10', zzzt: '10'}}).then(res => {
          if (res.code == 200) {
            this.zgList = res.result
          } else {
            this.swal({
              title: '推荐人信息获取失败，请重新获取！',
              type: 'error'
            })
          }
        })
      },
      close() {
        this.$parent.compName = ''
      },
      submit() {
        var v = this
        if (this.addmess.sl == 0) {
          this.swal({
            type: 'warning',
            title: '出库量不能为0!!!',
          })
        } else {
          this.$refs['addmess'].validate((valid) => {
            if (valid) {
              v.$http.post('/api/bizkc/handOut', v.addmess).then(res => {
                if(res.code == 200){
                  this.swal({
                    type:"success",
                    title:res.message
                  })
                  this.$parent.getPagerList()
                  this.close()
                }else {
                  this.swal({
                    type:"error",
                    title:res.message
                  })
                }
              }).catch(err => {

              })
            } else {
              this.swal({
                title: '请认真填写库存信息',
                type: 'warning'
              })
            }
          })
        }
      }
    }
  }
</script>

<style scoped>

</style>
