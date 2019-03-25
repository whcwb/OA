<template>
  <div>
    <Modal
      v-model="modalShow"
      width="240"
      :styles="{top: '20px'}"
      footer-hide
      :mask="false"
      :mask-closable="false"
      @on-visible-change="changeWindow">
      <Row style="padding-left: 20px">
        <Col span="24">
          <OBJECT ref="CamSDK" id="CamSDKOCX" style="width: 180px; height:180px" classid="clsid:556DBC8A-FE4A-4DA7-A82E-3926C8D4AC41"></OBJECT>
        </Col>
      </Row>
    </Modal>
  </div>
</template>

<script>
  export default {
    name: "camera",
    data(){
      return{
        modalShow: true,
        param:{
          orderType:'2',
          id:'',
          total:1,//总数量
          pageNum: 1,//当前页码
          pageSize:9999//每页显示数
        },
        arcData:[],
        filterArcData:[]
      }
    },
    created(){

    },
    methods:{
      openCarmera(){

      },
      close(){
        this.$parent.compName = ''
      },
      okSel(val){
        this.$parent.$data.result.selectItem = val;

        this.close();
      },
      changeWindow(flag){
        if (!flag){
          setTimeout(()=>{
            this.close();
          },500);
        }
      }
    }
  }
</script>

<style lang="less">
  .itenCol{
    padding: 22px;
  }
</style>
