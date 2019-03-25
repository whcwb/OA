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
          运管详情
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
                <Input :value="getDict(carData[item.key],item.dict)" readonly placeholder="暂无信息" />
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
          {title: '车牌号', key: 'cph'},
          {title: '运输证号', key: 'ygYszh'},
          {title: '登记日期', key: 'yyCdrq', type: 'date'},
          {title: '是否安装GPS', key: 'ygGpsType', dict: 'yn'},
          {title: '运营状态', key: 'ygYyType', dict: 'ZDCLK1033'},
          {title: '14年上线', key: 'ygYsnSx', dict: 'yn'},
          {title: '更新', key: 'ygGx'},
          {title: '卡机安装状态', key: 'ygKjType', dict: 'ZDCLK1035'},
          {title: '卡机安装时间', key: 'ygKjAzsj', type: 'date'},
          {title: '明涛成功新证号', key: 'ygNewCode'},
          {title: '新卡机', key: 'ygNewKj', dict: 'yn'},
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
