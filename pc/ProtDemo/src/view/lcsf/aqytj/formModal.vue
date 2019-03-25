<template>
  <div>
    <Modal
      v-model="showModal"
      height="600"
      width="900"
      :closable='false'
      :mask-closable="false"
      :title="operate+'用户'">
      <Form
        ref="form"
        :model="formItem"
        :rules="ruleInline"
        :label-width="100"
        :styles="{top: '20px'}">
        <div style="overflow: auto;height: 400px;width:800px">
          <form-items :parent="v" :items="formItemGroup[0]"></form-items>
          <Col span="12">
            <FormItem prop="jgdm" label='组织机构：'>
              <Cascader @on-change="change" v-model="orgPath" change-on-select :data="orgTree" placeholder="请选择组织机构"
                        filterable clearable></Cascader>
            </FormItem>
          </Col>
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
            {title: '用户名', key: 'zh', disabled: !this.editMode, required: true},
            {title: '类型', key: 'lx', type: 'dict', dict: 'ZDCLK0003'},
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
