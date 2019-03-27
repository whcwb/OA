<template>
  <div>
    <Modal
      v-model="modalShow"
      width="40%"
      :closable='false'
      :mask-closable="false">
      <div slot="header" class="box_row colCenter">
        <div class="box_row_100" style="font-size: 22px;font-weight: 700;margin-left:12px">
          详情
        </div>
        <Button @click="close" style="margin: 0 12px">取消</Button>
        <Button type="success" @click="" style="margin: 0 12px">完成</Button>
      </div>
      <div>
        <div class="box_col">
        </div>
      </div>
      <div slot="footer"></div>
    </Modal>
  </div>
</template>

<script>
  export default {
    name: "mess",
    data() {
      return {
        modalShow:true
      }
    },
    props:{
      mess:{
        type:Object,
        default:{}
      }
    },
    created() {
      // console.log('數據傳遞',this.mess);
    },
    methods: {
      close(){
        this.$parent.compName = ''
      }

    }
  }
</script>
