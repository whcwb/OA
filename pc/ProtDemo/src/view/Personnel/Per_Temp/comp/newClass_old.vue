<template>
  <div>
    <Modal v-model="showModal"
           title="新增库存类"
           width="900"
           class-name="vertical-center-modal"
           :closable='false' :mask-closable="false">
      <div>
        <Form
          ref="addmess"
          :model="addmess"
          :rules="ruleInline"
          :label-width="100">
          <div>
            <Row>
              <Col span="8">
                <FormItem prop="kcLx" label='物品类别：'>
                  <Input type="text" v-model="addmess.kcLx" placeholder="物品类别"></Input>
                </FormItem>
              </Col>
              <Col span="8">
                <FormItem prop="kcMc" label='物品名称：'>
                  <Input type="text" v-model="addmess.kcMc" placeholder="物品名称"></Input>
                </FormItem>
              </Col>
              <Col span="8">
                <FormItem label='入库数量：'>
                  <InputNumber style="width: 100%" :min="0" v-model="addmess.kcSl"></InputNumber>
                </FormItem>
              </Col>
              <Col span="24">
                <FormItem label='备注信息：'>
                  <Input type="text" v-model="addmess.bz" placeholder="备注信息"></Input>
                </FormItem>
              </Col>
            </Row>
          </div>
        </Form>
      </div>
      <div slot="footer">
        <Button type="default" style="color: #949494" @click="close">关闭</Button>
        <Button type="warning" @click="submit">添加</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
  export default {
    name: "newClass",
    data() {
      return {
        showModal: true,
        addmess: {
          kcSl:0
        },
        ruleInline: {
          kcLx: [
            {required: true, message: '请输入物品类别', trigger: 'blur'}
          ],
          kcMc: [
            {required: true, message: '请输入物品名称', trigger: 'blur'}
          ],
        },
      }
    },
    methods:{
      close(){
        this.$parent.compName = ''
      },
      submit(){
        var v = this
        this.$refs['addmess'].validate((valid) => {
          if (valid) {
            v.$http.post('/api/bizkc/save',v.addmess).then(res=>{
              if(res.code == 200){
                v.$parent.getPagerList()
                v.swal({
                  type:'success',
                  title:'库存类添加成功,是否继续添加。',
                  showCancelButton: true,
                  confirmButtonText: '继续添加',
                  cancelButtonText: '完成',
                }).then(p=>{
                  if(p.value){
                    v.addmess = {kcSl:0}
                  }else {
                    v.close()
                  }
                })
              }else {
                this.swal({
                  title:'操作失败，请重试！',
                  type:'error'
                })
              }
            }).catch(err=>{

            })
          } else {
            this.swal({
              title:'请认真填写库存信息',
              type:'warning'
            })
          }
        })
      }
    }
  }
</script>
<style lang="less">
  .vertical-center-modal{
    display: flex;
    align-items: center;
    justify-content: center;

    .ivu-modal{
      top: 0;
    }
  }
</style>
