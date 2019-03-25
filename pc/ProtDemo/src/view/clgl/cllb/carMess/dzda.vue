<template>
  <div>
    <Modal
      v-model="showModalT"
      height="600"
      width="90%"
      :closable='false'
      :mask-closable="false"
      :footer-hide="true">
      <div slot="header">
        <div style="text-align: center;font-size: 22px;font-weight: 700;">
          电子档案
        </div>
        <div style="position: absolute;top:5px;right: 10px;">
          <Button type="warning" @click="close">关闭</Button>
        </div>
      </div>
      <div style="max-height: 550px;overflow: auto;width: 100%">
        <div class="box_row_list">
          <Card style="width:350px;margin:0 auto 6px auto" v-for="(item,index) in zjList">
            <div slot="title" style="font-weight: 700;font-size: 16px">
              <Icon type="ios-film-outline" size="22"></Icon>
              {{item.title}}
              <Button style="float: right" type="info" @click="close" size="small">下载</Button>
            </div>
            <div style="width: 180px;height: 180px;text-align: center">
              <img :src="item.url" :alt="item.title" style="height: 100%">
            </div>
          </Card>
        </div>
      </div>

    </Modal>
  </div>
</template>

<script>
  import zjList from './zj'
  export default {
    name: "dzda",
    data(){
      return{
        showModalT:true,
        zjList:zjList
      }
    },
    created(){
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
