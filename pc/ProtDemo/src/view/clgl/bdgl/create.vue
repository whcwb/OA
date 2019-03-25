<template>
  <div>
    <Modal
      v-model="showModal"
      height="600"
      width="900"
      :closable='false'
      :mask-closable="false"
      title="新增保单">
      <Form
        ref="form"
        :model="formItem"
        :rules="ruleInline"
        :label-width="100"
        :styles="{top: '20px'}">
        <div style="overflow: auto;width:800px">
          <form-items v-if="showItems" :parent="v" :items="formItemGroup[0]"></form-items>
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
        operate: "新增",
        showItems: false,
        editMode: true,
        saveUrl: '/api/carbx/save',
        //新增数据
        formItem: {},
        showPsd: false,
        fileGroup: {
          dzdDjzFileurl: {type: '产权', urlType: 'daFile', urlList: [], uploadUrl: this.apis.upFile + '?targetPath=car'},//
        },
        formItemGroup: [
          [
            {title: '车牌号', key: 'cph', disabled: true},
            {title: '年审次数', key: 'bdCount', minWidth: 120},
            {title: '起保时间', key: 'bxrq', type: 'date'},
            {title: '终保时间', key: 'bxz', type: 'date'},
            {title: '投保公司', key: 'tbgs', minWidth: 120},
            {title: '联系电话', key: 'bxdh', minWidth: 120},
            {title: '保单编号', key: 'bdzbbh', minWidth: 120},
            {title: '保单位置', key: 'bdWz', minWidth: 120},
            {title: '备注', key: 'bz', minWidth: 120},
          ],
        ],
        ruleInline: {},
        oldData: {}
      }
    },
    created() {
      this.formItem.clId = this.$parent.choosedItem.id;
      this.formItem.cph = this.$parent.choosedItem.cph;
      this.util.initFormRule(this);
      this.util.initForeignKeys(this);
      this.util.initDict(this);
      this.getData();
    },
    methods: {
      getPager() {
        this.$panert.getPager()
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
      getData() {
        this.$http.post('api/carbx/clbxzt', {'clId': this.formItem.clId}).then((res) => {
          if (res.code == 200) {
            this.formItem.bxrq = res.result.bxrq
            this.formItem.bxz = res.result.bxz
            this.formItem.bdCount = res.result.bdCount
            this.showItems = true;
          } else {
            this.$Message.error(res.message);
          }
        })
      }
    }
  }
</script>

<style>
</style>
