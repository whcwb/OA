<template>
  <div>
    <Modal
      v-model="modalShow"
      width="30%"
      :closable='false'
      :mask-closable="false">
      <div slot="header" class="box_row colCenter">
        <div class="box_row_100" style="font-size: 22px;font-weight: 700;margin-left:12px">
          新增档案库
        </div>
        <Button @click="close" style="margin: 0 12px">取消</Button>
        <Button type="success" @click="save" style="margin: 0 12px">完成</Button>
      </div>
      <div>
        <Form :model="formTop" label-position="top">
          <Row>
            <Col span="24">
              <FormItem label="仓库位置信息">
                <Input v-model="formTop.position"
                       size="large"
                       placeholder="例：一号楼、1001室"></Input>
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="24">
              <FormItem label="仓库位置编码">
                <Input v-model="formTop.archivesCodeStart"
                       size="large"
                       placeholder="例：F1-1001"></Input>
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="24">
              <FormItem label="仓库数量">
                <Input v-model="formTop.archivesNum"
                       size="large"
                       :number="true"
                       placeholder="例：F1-102"></Input>
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="24">
              <FormItem label="仓库容量">
                <Input v-model="formTop.capacity"
                       size="large"
                       :number="true"
                       placeholder="请输入仓库容量"></Input>
              </FormItem>
            </Col>
          </Row>
        </Form>
      </div>
      <div slot="footer"></div>
    </Modal>
  </div>
</template>

<script>
  export default {
    name: "addarc",
    data() {
      return {
        modalShow: true,
        formTop: {
          position:'',//中文位置描述
          archivesCodeStart: '',//厂库位置编码
          archivesNum: 1,//厂库数量
          capacity: 1,//容量
        }
      }
    },
    methods: {
      tySelect(val) {
        console.log(val);
      },
      save() {
        this.$http.post(this.apis.DAK.ADD,this.formTop).then((res)=>{
          console.log(res);
        })
      },
      close() {
        this.$parent.compName = ''
      }
    }
  }
</script>

<style scoped>

</style>
