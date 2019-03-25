<template>
  <div>
    <Modal v-model="showModal"
           title="证件照"
           width="900"
           :closable='false' :mask-closable="false">
      <div style="margin-bottom: 12px">
          <div>
            <Row>
              <Col span="8">
                <Card>
                  <p slot="title">
                    <Icon type="ios-film-outline"></Icon>
                    红底照片
                  </p>
                  <div>
                    <img :src="apiUrl+addmess.hong" style="width: 100%" alt="">
                  </div>
                </Card>
              </Col>
              <Col span="8">
                <Card>
                  <p slot="title">
                    <Icon type="ios-film-outline"></Icon>
                    白底照片
                  </p>
                  <div>
                    <img :src="apiUrl+addmess.bai" style="width: 100%" alt="">
                  </div>
                </Card>
              </Col>
              <Col span="8">
                <Card>
                  <p slot="title">
                    <Icon type="ios-film-outline"></Icon>
                    蓝底照片
                  </p>
                  <div>
                    <img :src="apiUrl+addmess.lan" style="width: 100%" alt="">
                  </div>
                </Card>
              </Col>
            </Row>
          </div>
      </div>
      <div slot="footer">
        <Button type="default" style="color: #949494" @click="close">关闭</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
  import upImg from '@/components/Upload/UploadImg'

  export default {
    name: "audit",
    components:{
      upImg
    },
    props: {
      userAudit: {
        type: Object,
        default: {}
      }
    },
    data() {
      return {
        showModal: true,
        apiUrl:this.apis.getImgUrl,
        addmess: {},
      }
    },
    created() {
      this.addmess = this.userAudit
      // console.log(this.addmess);
    },
    methods: {
      close() {
        this.$parent.compName = ''
      }
    }
  }
</script>

<style scoped>

</style>
