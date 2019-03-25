<template>
  <div>
    <Modal v-model="showModal"
           title="入库"
           width="900"
           :closable='false' :mask-closable="false">
      <div>
        <Form :label-width="100">
          <div>
            <Row>
              <Col span="6">
                <FormItem label='物品名称：'>
                  <Input type="text" disabled v-model="mess.kcMc" placeholder="物品名称"></Input>
                </FormItem>
              </Col>
              <Col span="6">
                <FormItem label='物品规格：'>
                  <Input type="text" disabled v-model="mess.kcLx" placeholder="物品类别"></Input>
                </FormItem>
              </Col>
              <Col span="6">
                <FormItem label='入库数量：'>
                  <InputNumber style="width: 100%" :min="0" v-model="addmess.sl"></InputNumber>
                </FormItem>
              </Col>
              <Col span="6">
                <FormItem label='物品单价：'>
                  <InputNumber style="width: 100%" :min="0" v-model="addmess.rkDj"></InputNumber>
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
        <Button type="warning" @click="submit">入库</Button>
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
        }
      }
    },
    data() {
      return {
        showModal: true,
        addmess: {
          id: '',
          sl: 0,
          rkDj: 0,
          bz: ''
        }
      }
    },
    created() {
      if (this.mess.id) {
        this.addmess.id = this.mess.id
      }
    },
    methods: {
      close() {
        this.$parent.compName = ''
      },
      submit() {
        if (this.addmess.sl == 0) {
          this.swal({
            type: 'warning',
            title: '请认真填写入库数量',
          })
          return
        }
        if (this.addmess.rkDj == 0) {
          this.swal({
            type: 'warning',
            title: '请认真填写物品单价',
          })
          return
        }
        this.$http.post('/api/bizkc/add', this.addmess).then(res => {
          if (res.code == 200) {
            this.swal({
              type: "success",
              title: res.message
            })
            this.$parent.getPagerList()
            this.close()
          } else {
            this.swal({
              type: "error",
              title: res.message
            })
          }
        }).catch(err => {

        })
      }
    }
  }
</script>

<style scoped>

</style>
