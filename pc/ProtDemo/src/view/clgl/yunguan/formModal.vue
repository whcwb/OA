<template>
  <div>
    <Modal
      v-model="showModal"
      height="600"
      width="900"
      :closable='false'
      :mask-closable="false"
      :title="operate+'运管信息'">
      <Form
        ref="form"
        :model="formItem"
        :rules="ruleInline"
        :label-width="100"
        :styles="{top: '20px'}">
        <div style="overflow: auto;width:800px">
          <form-items :parent="v" :items="formItemGroup[0]"></form-items>
        </div>
      </Form>
      <div slot='footer'>
        <Button type="default" @click="v.util.closeDialog(v)" style="color: #949494">取消</Button>
        <Button type="primary" @click="v.util.save(v)">确定</Button>
      </div>
    </Modal>
  </div>
</template>

<script>


  export default {
    name: '',
    data() {
      return {
        v: this,
        showModal: true,
        operate: "新增",
        editMode: false,
        saveUrl: '/api/carwarn/ygnccdrq',
        //新增数据
          formItem: {
        },
        showPsd: false,
        formItemGroup: [
          [
              {title: '车牌号', key: 'cph'},
              {title: '运输证号', key: 'ygYszh'},
              {title: '登记日期', key: 'yyCdrq',type:'date'},
              {title: '下一次年审时间', key: 'xcncsj',type:'date'},
              {title: '是否安装GPS', key: 'ygGpsType',dict:'yn'},
              {title: '运营状态', key: 'ygYyType',dict:'ZDCLK1033'},
              {title: '卡机安装状态', key: 'ygKjType',dict:'ZDCLK1035'},
              {title: '卡机安装时间', key: 'ygKjAzsj',type:'date'},
              {title: '新卡机', key: 'ygNewKj',dict:'yn'},
          ],
        ],
          ruleInline: {},
      }
    },
    created() {
      this.util.initFormModal(this);
    },
    methods: {
    }
  }
</script>

<style>
</style>
