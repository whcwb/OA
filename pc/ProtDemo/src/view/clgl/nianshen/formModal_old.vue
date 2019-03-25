<template>
  <div>
    <Modal
      v-model="showModal"
      height="600"
      width="900"
      :closable='false'
      :mask-closable="false"
      :title="operate+'年审信息'">
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
        //新增数据
          formItem: {
        },
        showPsd: false,
        formItemGroup: [
          [
              {title:'车牌号',key:'cph',required:true},
              {title: '年审至', key: 'nsz'},
              {title: '年审时间', key: 'nssj',type:'date'},
              {title: '二审时间', key: 'essj',type:'date'},
              {title: '批次', key: 'pc'},
              {title: '驾驶员姓名', key: 'jsyxm'},
              {title: '驾驶员联系电话', key: 'jsylxdh'},
              {title: '年审状态', key: 'zt'},
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
