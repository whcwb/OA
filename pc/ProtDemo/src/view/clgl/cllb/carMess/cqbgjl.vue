<template>
  <div>
    <Modal
      v-model="showModalT"
      height="600"
      width="85%"
      :closable='false'
      :mask-closable="false"
      :footer-hide="true">
      <div slot="header">
        <div style="text-align: center;font-size: 22px;font-weight: 700;">
          产权变更记录
        </div>
        <div style="position: absolute;top:5px;right: 10px;">
          <Button type="warning" @click="close">关闭</Button>
        </div>
      </div>
      <div style="max-height: 550px;overflow: auto;width: 100%">
        <his-c :id="clId"></his-c>
      </div>

    </Modal>
  </div>
</template>

<script>
  import hisC from '../chanquanHistory_old'
  export default {
    name: "dzda",
    components:{hisC},
    data(){
      return{
        showModalT:true,
        clId:''
      }
    },
    created(){
      this.clId = this.$parent.carData.id
    },
    methods:{
      close(){
        this.$parent.componentName = ''
      },
    }
  }
</script>

<style scoped>

</style>
