<template>
  <div>
    <Modal
      v-model="showModal"
      height="600"
      width="1200"
      :closable='false'
      :mask-closable="false"
      class-name="vertical-center-modal"
      title="产权变更">
      <div class="boxbackborder box_col">
        <h3>当前产权人</h3>
        <Form>
          <Row :gutter="20" type="flex" justify="center">
            <Col span="5">
              <FormItem label='车牌号码'>
                <Input type="text" :value="getStr(oldData.cph)" readonly placeholder="车牌号码"></Input>
              </FormItem>
            </Col>
            <Col span="5">
              <FormItem label='车款金额'>
                <Input type="text" :value="getStr(oldData.clCqrJl)" readonly placeholder="车款金额">
                <span slot="append">元</span>
                </Input>
              </FormItem>
            </Col>
            <Col span="5">
              <FormItem label='经办人'>
                <Input type="text" :value="getStr(oldData.clCqrJbr)" readonly placeholder="产权变更经办人"></Input>
              </FormItem>
            </Col>
            <Col span="5">
              <FormItem label='经办人手机号码'>
                <Input type="text" :value="getStr(oldData.clCqrJbrDn)" readonly placeholder="经办人手机号码"></Input>
              </FormItem>
            </Col>
          </Row>
          <Row :gutter="20" type="flex" justify="center">
            <Col span="5">
              <FormItem label='现产权人'>
                <Input type="text" :value="getStr(oldData.clCqr)" readonly placeholder="经办人手机号码"></Input>
              </FormItem>
            </Col>
            <Col span="5">
              <FormItem label='产权人联系方式'>
                <Input type="text" :value="getStr(oldData.clCqrDn)" readonly placeholder="经办人手机号码"></Input>
              </FormItem>
            </Col>
            <Col span="5">
              <FormItem label='产权人证件号码'>
                <Input type="text" :value="getStr(oldData.clCqrCode)" readonly placeholder="经办人手机号码"></Input>
              </FormItem>
            </Col>
            <Col span="5">
              <FormItem label='备注'>
                <Input type="text" :value="getStr(oldData.clCqrbz)" readonly placeholder="经办人手机号码"></Input>
              </FormItem>
            </Col>
          </Row>
        </Form>
        <Divider/>
        <Tabs ref="tabRef" v-model="TabsName">
          <TabPane label="内部产权变更">
            <div v-if="TabsName == 0">
              <inner-change ref="tab0" name="tab0" :choosedItem="parentItem" @close="v.util.closeDialog(v)"></inner-change>
            </div>
          </TabPane>
          <TabPane label="车牌号变更">
            <div v-if="TabsName == 1">
              <cph-change ref="tab1" name="tab1" :choosedItem="parentItem" @close="v.util.closeDialog(v)"></cph-change>
            </div>
          </TabPane>
          <TabPane label="报废">
            <div v-if="TabsName == 2">
              <bf-change ref="tab2" name="tab2" :choosedItem="parentItem" @close="v.util.closeDialog(v)"></bf-change>
            </div>
          </TabPane>
          <TabPane label="变卖">
            <div v-if="TabsName == 3">
              <sell ref="tab3" name="tab3" :choosedItem="parentItem" @close="v.util.closeDialog(v)"></sell>
            </div>
          </TabPane>
          <TabPane label="所有人变更">
            <div v-if="TabsName == 4">
              <all-peo ref="tab4" name="tab4" :choosedItem="parentItem" @close="v.util.closeDialog(v)"></all-peo>
            </div>
          </TabPane>
        </Tabs>
      </div>
      <div slot='footer'>
        <Button type="default" @click="v.util.closeDialog(v)" style="color: #949494">取消</Button>
        <Button type="primary" @click="save">确定</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
  import sell from './sell'
  import bfChange from './bfChange'
  import cphChange from './cphChange'
  import innerChange from './innerChange'
  import allPeo from  './allPenpleC'

  export default {
    components: {
      bfChange, cphChange, innerChange, sell,allPeo
    },
    name: "chanquanHistory",
    data() {
      return {
        v: this,
        showModal: true,
        editMode: false,
        apiRoot: this.apis.USER,
        choosedItem: null,
        componentName: '',
        compModel: true,
        oldData: {},
        formItemGroup: [
          [
            {title: '车牌号', key: 'cph', required: true, disabled: true},
            {title: '经办人', key: 'jbr', required: true},
            {title: '经办人手机号码', key: 'jbrDn', required: true},
            {title: '车款', key: 'ck', required: true},
            {title: '产权人', key: 'cqr', required: true},
            {title: '产权人联系方式', key: 'cqrDn', required: true},
            {title: '产权人证件号', key: 'cqrCode', required: true},
            {title: '备注', key: 'bz'},
          ],
        ],
        TabsName:0,
      }
    },
    created() {
      this.parentItem = this.$parent.choosedItem;
      this.oldData = JSON.parse(JSON.stringify(this.$parent.choosedItem));
    },
    mounted() {

    },
    methods: {
      save() {
        let tabName = this.$refs.tabRef.activeKey;
        this.$refs['tab' + tabName].save();
      },
      getStr(s) {
        return s == null || s == 'undefined' ? '-' : s;
      }
    }
  }
</script>

<style scoped>

</style>
