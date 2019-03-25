<template>
  <div>
    <Modal
      v-model="showModal"
      height="600"
      width="900"
      :closable='false'
      :mask-closable="false"
      :title="operate+'改气信息'">
      <Form
        ref="form"
        :model="formModel"
        :rules="rules"
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
        saveUrl: '/api/cargas/save',
        //新增数据
        formModel: {},
        showPsd: false,
        formItemGroup: [
          [
            {title: '车牌号', key: 'cph', disabled: true},
            {title: '改气时间', key: 'gasData', minWidth: 120, type: 'date'},
            {title: '改气地点', key: 'gasDd', minWidth: 120},
            {title: '联系人', key: 'lxr', minWidth: 120},
            {title: '联系电话', key: 'lxDn', minWidth: 120},
            {title: '下一次年审时间', key: 'xcncsj', type: 'date'},
            {title: '合格证有效期', key: 'gxGasGgzyxq', type: 'date'},
            {title: '备注', key: 'bz:', minWidth: 120},
          ],
        ],
        rules: {},
      }
    },
    created() {
      this.util.initFormModal(this);
    },
    methods: {}
  }
</script>

<style>
</style>
