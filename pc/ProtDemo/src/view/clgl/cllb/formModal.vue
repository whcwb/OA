<template>
  <div>
    <Modal
      v-model="showModal"
      :height="AF.getPageHeight()"
      width="1200"
      :fullscreen="fullscreen"
      :closable='false'
      :mask-closable="false"
      :footer-hide="true"
      class-name="vertical-center-modal"
      :title="operate+'车辆'">
      <div slot="header" style="position: relative;height:28px">
        <div style="width:100%;position: absolute;top: 0">
          <Row>
            <Col span="6">
              <p>
              {{operate}}车辆
              </p>
            </Col>
            <Col span="12">
              <Input type="text" readonly v-model="formItem['dah']" placeholder="档案编号"/>
            </Col>
            <Col span="6">
              <div style="text-align: right">
              <Icon type="md-expand" size="22" @click="iconClick" style="cursor: pointer;margin-right: 24px"/>
              <!--<Button type="default" @click="v.util.closeDialog(v)" style="color: #949494;margin-right: 12px">取消</Button>-->
                <Button type="warning" v-if="editMode" @click="Cqbg" style="color: #ffffff;margin-right: 12px">产权变更</Button>
                <Button type="default" @click="v.$parent.componentName =''" style="color: #949494;margin-right: 12px">取消</Button>
              <Button type="primary" @click="v.util.save(v)">确定</Button>
              </div>
            </Col>
          </Row>
        </div>
      </div>
      <div :style="{overflow: 'auto',maxHeight:(AF.getPageHeight()-100)+'px' ,paddingRight:'8px',paddingTop:'0px'}">
        <Form
          ref="form"
          :model="formItem"
          :rules="ruleInline"
          label-position="top"
          :styles="{top: '20px'}">
          <Row>
            <!--{title: '培训车型', key: '', dict: 'ZDCLK0040', span: 8, required: true}-->
            <Col :span="formItem['hpzl']=='1'?'4':'8'" class-name="colpad">
              <FormItem label='号牌种类(学牌/地牌)'>
                <Select filterable clearable v-model="formItem['hpzl']" @on-change="changeHpzl" placeholder='请选择号牌种类'>
                  <Option v-for="(item,index) in this.dictUtil.getByCode(this,'ZDCLK1036')" :value="item.key">{{item.val}}</Option>
                </Select>
              </FormItem>
            </Col>
            <Col v-if="formItem['hpzl']=='1'" span="4" class-name="colpad">
              <FormItem label='培训车型'>
                <Select filterable clearable v-model="formItem['pxcx']" placeholder='请选择培训车型'>
                  <Option v-for="(item,index) in this.dictUtil.getByCode(this,'ZDCLK0040')" :value="item.key">{{item.val}}</Option>
                </Select>
              </FormItem>
            </Col>
            <Col span="8" class-name="colpad">
              <FormItem label='机动车所有人'>
                <Select v-model="formItem['carSyr']" filterable clearable @on-change="ChangeSyr">
                  <Option v-for="(item,index) in SyrList" :value="item.id" :key="item.index">{{ item.syrMc }}</Option>
                </Select>
                <!--<Input type="text" v-model="formItem['carSyr']"-->
                       <!--:placeholder="'请填写机动车所有人'"></Input>-->
              </FormItem>
            </Col>
            <Col span="8" class-name="colpad">
              <FormItem label='使用性质'>
                <Select filterable clearable v-model="formItem['syxz']" placeholder='请选择使用性质'>
                  <Option v-for="(item,index) in this.dictUtil.getByCode(this,'ZDCLK1037')" :value="item.key">{{item.val}}</Option>
                </Select>
              </FormItem>
            </Col>
          </Row>
          <form-items ref="formitems" :parent="v" :items="formItemGroup[0]"></form-items>

        </Form>

      </div>
    </Modal>
    <component :is="componentName"></component>
  </div>
</template>

<script>
  import addColumns from './addColumns'
  import chanquanChange from './chanquanChange/chanquanChange'
  export default {
    name: '',
    components: {chanquanChange},
    data(){
      return {
        choosedItem: null,
        componentName: '',
        v: this,
        showModal: true,
        fullscreen: true,
        operate: "新增",
        editMode: false,
        //新增数据
        formItem: {
          djjg:'湖北省武汉市公安局交通管理局',
          dah:'',
          hpzl: '1',
          syxz: '01'
        },
        SyrList:[],
        showPsd: false,
        formItemGroup: [addColumns],
        ruleInline: {},
        rsList: [],//人事列表
        // foreignList: {
        //   syrId: {url: '/api/coachmanagement/query', key: 'id', val: 'coachName', items: []},
        // },
      }
    },
    watch: {
      foreignList: function (n, o) {
        console.log(n);
        console.log(o);
      }
    },
    created() {
      if (this.$parent.choosedItem == null) {
        this.formItem.cph = '鄂A';
      }
      this.util.initFormModal(this);
      this.getrsList()
      if(this.operate === '新增'){
        this.getDaNum()
      }
      this.getCarSyrList()
    },
    methods: {
      Cqbg(){
        this.choosedItem = this.formItem;
        this.componentName = 'chanquanChange'
      },
      ChangeSyr(id){
        let item = null;
        for (let i = 0;i<this.SyrList.length;i++) {
          if(id = this.SyrList[i].id){
            item = this.SyrList[i]
            break
          }
        }
        this.formItem.srycode = item.jyxkzh;
        this.formItem.srydzlx = '0006';
      },
      changeHpzl(val){
        console.log(val);
        if(val=='1'){
          this.formItem.syxz = '01'
        }else {
          this.formItem.syxz = '02'
        }
      },
      getDaNum(){
        this.$http.post('/api/car/getdaseq').then(res=>{
          this.formItem['dah'] = res.message
        }).catch(err=>{})
      },
      iconClick() {
        this.fullscreen = !this.fullscreen
      },
      getCarSyrList(){
        this.$http.get('/api/clsyr/getAll').then(res => {
          if (res.code == 200) {
            this.SyrList = res.result
            // console.log('*********syr',res);
          }
        })
      },
      getrsList() {
        this.$http.get('/api/coachmanagement/query').then(res => {
          if (res.code == 200) {
            this.rsList = res.result
          }
        }).catch(err => {
        })
      }
    }
  }
</script>

<style lang="less">
  .colpad{
    padding: 0 15px;
  }
</style>
