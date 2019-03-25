<template>
  <div>
    <Modal v-model="showModal" width='400' :closable='false'
           :mask-closable="false" :title="'新增库存'+operate">
          <Input v-model="formMess.mc"
                 :autofocus="true"
                 @on-enter="save"
                 :placeholder="'库存'+operate+'名称'"/>
      <div slot="footer">
        <Button type="default" @click="close">取消</Button>
        <Button type="primary" @click="save">添加</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
  export default {
    name: "formData",
    data() {
      return {
        showModal:true,
        operate:'资产种类',
        name:'',
        formMess:{
          mc:''
        },
        ruleInline:{
          mc:[
            { required: true, message: '请将信息填写完整!', trigger: 'blur' }
          ]
        }
      }
    },
    created(){
      if(this.$parent.choosItem && this.$parent.choosItem.id){
        this.formMess['pid'] = this.$parent.choosItem.id
        this.operate = '类别'
        this.name = this.$parent.choosItem.mc
      }
    },
    methods:{
      close(){
        this.$parent.compName = ''
        this.$parent.choosItem = null
      },
      save(){
        // this.$refs['formInline'].validate((valid) => {
        //   if (valid) {
        if(this.formMess.mc==''){
          this.swal({
            title:'请将信息填写完成',
            type:'error'
          })
          return
        }
            this.$http.post('/api/kclb/save',this.formMess).then(res=>{
              if(res.code == 200){
                this.swal({
                  title:'添加成功。是否继续添加？',
                  type:'success',
                  showCancelButton: true,
                  confirmButtonText: '继续添加',
                  cancelButtonText: '添加完成',
                }).then(val=>{
                  if(val.value){
                    this.formMess.mc = ''
                  }else {
                    this.close()
                  }
                  this.$parent.getData()
                })
              }
            }).catch(err=>{})
          // } else {
          // }
        // })
      }
    }
  }
</script>

<style scoped>

</style>
