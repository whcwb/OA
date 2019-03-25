<template>
  <div>
    <Modal
      v-model="showModal"
      height="600"
      width="900"
      :closable='false'
      :mask-closable="false"
      title="保单编辑">
      <Form
        ref="form"
        :model="formItem"
        :rules="ruleInline"
        :label-width="100"
        :styles="{top: '20px'}">
        <div style="overflow: auto;height: 400px;width:800px">
          <form-items :parent="v" :items="formItemGroup[0]"></form-items>
          <!--<Divider/>-->
          <!--<h3>电子档案</h3>-->
          <!--<Row v-for="(r,k) of fileGroup">-->
          <!--<Col>-->
          <!--<add-img-list :urlList="r.urlList" :uploadUrl="r.uploadUrl" @removeFile="(mes)=>{removeFile(r,mes)}"-->
          <!--@addImg="(mes)=>{addImg(r,mes)}"></add-img-list>-->
          <!--</Col>-->
          <!--</Row>-->
        </div>
      </Form>
      <div slot='footer'>
        <Button type="default" @click="v.util.closeDialog(v)" style="color: #949494">取消</Button>
        <Button type="primary" @click="v.util.save(v),getPager()">确定</Button>
      </div>
    </Modal>
  </div>
</template>

<script>


  import addImgList from '../../../components/addlistfileImg'

  export default {
    components: {addImgList},
    name: '',
    data() {
      return {
        v: this,
        showModal: true,
        operate: "编辑",
        editMode: true,
        saveUrl: '/api/carbx/updates',
        //新增数据
        formItem: {},
        showPsd: false,
        fileGroup: {
          dzdDjzFileurl: {
            type: '产权',
            urlType: 'dzdDjzFileurl',
            urlList: [],
            uploadUrl: this.apis.upFile + '?targetPath=car'
          },//
        },
        formItemGroup: [
          [
            {title: '车牌号', key: 'cph', disabled: true},
            {title: '保单数量', key: 'bxBdCount', minWidth: 120, disabled: true},
            {title: '起保时间', key: 'bxBxrq', type: 'date'},
            {title: '终保时间', key: 'bxBxz', type: 'date'},
            {title: '投保公司', key: 'bxTbgs', minWidth: 120},
            {title: '联系电话', key: 'bxBxdh', minWidth: 120},
            {title: '保单位置', key: 'bxBdWz', minWidth: 120},
            {title: '保单编号', key: 'bxBdzbbh', minWidth: 120},
            {title: '备注', key: 'bxBz', minWidth: 120},

          ],
        ],
        ruleInline: {},
      }
    },
    created() {
      this.util.initFormModal(this);
    },
    methods: {
      getPager(){
        this.$parent.getPager()
      },
      removeFile(r, mes) {
        var v = this
        let arr = r.urlList
        r.urlList.forEach((it, index) => {
          if (it == mes) {
            r.urlList.splice(index, 1)
            this.params[r.urlType] = r.urlList.join(',')
            return
          }
        })
      },
      addImg(r, mes) {
        r.urlList.push(mes);//将新添加的图片地址存到urlList 里
        this.params[r.urlType] = r.urlList.join(',')//将图片地址数组转成字符串
      },
    }
  }
</script>

<style>
</style>
