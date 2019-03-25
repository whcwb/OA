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
          改气详情
        </div>
        <div style="position: absolute;top:5px;right: 10px;">
          <Button type="warning" @click="close">关闭</Button>
        </div>
      </div>
      <div style="max-height: 550px;overflow: auto;width: 100%">
        <Form label-position="top">
          <Row>
            <Col :span="item.span? item.span:'4'" v-for="(item,index) in formItemGroup" :key="index">
              <FormItem class="formItemSty">

                <div class="formTit" slot="label">
                  {{item.title}}
                </div>
                <div class="formMess">
                  <Input :value="getDict(carData[item.key],item.dict)" readonly placeholder="暂无信息"/>
                </div>
              </FormItem>
            </Col>
          </Row>
        </Form>
      </div>

    </Modal>
  </div>
</template>

<script>
  export default {
    name: "dzda",
    components: {},
    data() {
      return {
        showModalT: true,
        carData: {},
        formItemGroup: [
          {title: '车牌号', key: 'cph', searchKey: 'cphLike', minWidth: 120},
          {title: '是否改气', key: 'gxType', minWidth: 120, dict: 'yn'},
          {title: '改气时间', key: 'gxCdrq', minWidth: 120},
          {title: '改气地点', key: 'gxGasDd', minWidth: 120},
          {title: '联系人', key: 'gxLxr', minWidth: 120},
          {title: '联系电话', key: 'gxLxDn', minWidth: 120},
          {title: '备注', key: 'gxGasBz', minWidth: 120},
        ],
      }
    },
    created() {
      this.carData = this.$parent.carData
    },
    methods: {
      close() {
        this.$parent.componentName = ''
      },
      getDict(val, code) {
        let a = val
        if (code != undefined) {
          a = this.dictUtil.getValByCode(this, code, val)
        }
        return a
      }
    }
  }
</script>

<style scoped>

</style>
