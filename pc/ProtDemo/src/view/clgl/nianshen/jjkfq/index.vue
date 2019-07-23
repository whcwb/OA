<template>
  <div>
    <Modal
      v-model="showModal"
      height="800"
      width="600"
      :closable='false'
      :mask-closable="false"
      class-name="vertical-center-modal">
      <div slot="header">
        <Row>
          <Col span="12">
            <p>经济开发区文件打印</p>
          </Col>
          <Col span="12">
            <Row type="flex" justify="end">
              <Col align="right">
                <Button size="small" type="default" style="margin:0 8px" @click="close">
                  关闭
                </Button>
                <Button size="small" type="success" style="margin:0 8px" @click="print">
                  打印
                </Button>
              </Col>
            </Row>
          </Col>
        </Row>
      </div>
      <div id="printCover" :style="{height:AF.getPageHeight()-200+'px',width:'100%'}">
        <Row class="hop">
          <div class="box">
            <img :src="this.apis.getImgUrl+tp.xszzm">
          </div>
          <div class="box">
            <img :src="this.apis.getImgUrl+tp.xszfm">
          </div>
        </Row>
        <Row class="hop">
          <div class="box">
            <img :src="this.apis.getImgUrl+tp.xszzm">
          </div>
          <div class="box">
            <img :src="this.apis.getImgUrl+tp.xszzm">
          </div>
        </Row>
        <Row class="hop">
          <div class="box">
            <img :src="this.apis.getImgUrl+tp.wtrzjBmUrl">
          </div>
          <div class="box">
            <img :src="this.apis.getImgUrl+tp.wtrzjZmUrl">
          </div>
        </Row>
        <Row class="hop">
          <div class="box">
            <img :src="this.apis.getImgUrl+tp.frzZmUrl">
          </div>
          <div class="box">
            <img :src="this.apis.getImgUrl+tp.frzBmUrl">
          </div>
        </Row>
      </div>
    </Modal>
  </div>
</template>

<script>
  import {Printd} from 'printd'
  export default {
    name: "index",
      props: {
        carInfo: {
          type: Object,
          default:{}
        }
      },
    data() {
      return {
        showModal: true,
        tp:{}
      }
    },
    created(){
      this.gettplist()
    },
    methods:{
      gettplist(){
        this.$http.post('/api/car/getCarArchives',{clId:this.carInfo.clId}).then((res)=>{
          console.log(res);
          if(res.code == 200){
             this.tp = res.result.fileUrl
          }else{
          }
        })
      },
      close(){
        this.$parent.componentName = ''
      },
      print(){
        this.showCover = true;
        var v = this
        this.bzShow = false
        const cssText = `
        .box{
            position: relative;
            padding-top: 30%;
            overflow: hidden;
            width: 50%;
            float: left;
          }
           .box img{
               position: absolute;
               top: 0;
                width: 100%;
                height: 100%;
           }
        .imgDiv{
            padding: 8px;
          }
          .hop{
            height: 200px;
          }
        `
        const d = new Printd();
        setTimeout(() => {
          d.print(document.getElementById('printCover'), [cssText])
        }, 50)
        setTimeout(()=>{
          v.close()
        }, 300)
      }

    }
  }
</script>

<style lang="less">
.imgDiv{
  padding: 8px;
}
.hop{
  height: 200px;
  width: 100%;
}
.box{
  position: relative;
  padding-top: 30%;
  overflow: hidden;
  width: 50%;
  float: left;
}
.box img{
  position: absolute;
  top: 0;
  width: 100%;
  height: 100%;
}
</style>
