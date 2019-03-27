<template>
  <div>
    <Modal
      v-model="modalShow"
      width="40%"
      :closable='false'
      :mask-closable="false">
      <div slot="header" class="box_row colCenter">
        添加备注
      </div>
      <div>
        <div class="box_col">
          <div class="box_row_100" style="font-size: 22px;font-weight: 700;margin-left:12px">
            <textarea v-model="bz" style="width: 100%"></textarea>
          </div>
        </div>
      </div>
      <div slot="footer">
        <Button @click="close" style="margin: 0 12px">取消</Button>
        <Button type="success" @click="save" style="margin: 0 12px">完成</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
  export default {
    name: "mess",
    data() {
      return {
        modalShow:true,
          item:{},
          bz:''
      }
    },
    created() {
        this.item = this.$parent.choosedItem;
        // console.log(this.item);
    },
    methods: {
      close(){
        this.$parent.componentName = ''
      },
        save(){
          this.$http.post(this.apis.lcjl.CHANGE,{id:this.item.id,bz:this.bz}).then((res)=>{
              if (res.code == 200){
                  this.$Message.success(res.message);
              }else{
                  this.$Message.error(res.message);
              }
              this.close();
          })
        }

    }
  }
</script>
