<template>
  <div>
    <Modal
      v-model="showModal"
      height="600"
      width="900"
      :closable='false'
      :mask-closable="false"
      title="年审确认">
      <Form
        ref="form"
        :model="formItem"
        :rules="ruleInline"
        :label-width="100"
        :styles="{top: '20px'}">
        <div style="overflow: auto;height: 400px;width:800px">
          <form-items :parent="v" :items="formItemGroup[0]"></form-items>
        </div>
      </Form>
      <div slot='footer'>
        <Button type="default" @click="v.util.closeDialog(v)" style="color: #949494">取消</Button>
        <Button type="primary" @click="v.util.save(v)">确认已年审</Button>
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
        editMode: true,
        //新增数据
          formItem: {
            zt:"1"
        },
        showPsd: false,
        formItemGroup: [
          [
              {title:'车牌号',key:'cph',required:true},
              {title: '备注', key: 'bz'},
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
