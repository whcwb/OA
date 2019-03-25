<style lang="less">
  .formItemSty {
    padding: 0 12px;
  }

  .formTit {
    font-size: 17px;
    font-weight: 600;
  }

  .formMess {
    font-size: 20px;
    font-weight: 600;
  }

  .modelTit {
    font-size: 20px;
    font-weight: 700;
    color: #10AEFF;
  }
  .carTypeTag {
    font-size: 15px;
    font-weight: 600;
    height: 30px;
    line-height: 30px;
    position: absolute;
    left: 130px;
    top: 8px;
  }
  .TBGS {
    font-size: 16px;
    font-weight: 600;
    height: 30px;
    line-height: 30px;
    position: absolute;
    left: 130px;
    top: -6px;
  }
</style>
<template>
  <div>
    <Modal
      v-model="showModal"
      height="600"
      width="1200"
      :closable='false'
      :mask-closable="false"
      :fullscreen="true"
      :footer-hide="true">
      <div slot="header" style="position: relative">
        <div style="text-align: center;font-size: 22px;font-weight: 700">
          {{carData['cph']}}-车辆详细信息
        </div>
        <div style="position: absolute;top:-8px;right: 10px;">
          <!--<Button type="info" @click="componentName='dzda'" style="margin-right: 15px">电子档案</Button>-->
          <Button type="info" @click="componentName='jbxxdy'" style="margin-right: 15px">基本信息打印</Button>
          <Button type="warning" @click="close">关闭</Button>
        </div>
      </div>
      <div :style="{height: AF.getPageHeight()-83+'px',overflow: 'auto'}">
        <Row>
          <Col span="16">
            <Card style="width:100%">
              <div class="modelTit" slot="title">
                <Icon type="ios-film-outline"></Icon>
                基本信息 <Tag color="volcano" class="carTypeTag">{{carType(carData.carType)}}</Tag>
                <!--状态-->
                <div style="float: right">
                  <Button type="primary" size="small"
                          @click="componentName='syrbg'"
                          style="margin-right: 8px">所有人变更记录
                  </Button>
                  <Button type="primary" size="small"
                          @click="componentName='ygmess'"
                          :disabled="!carData.yyCdrq" style="margin-right: 8px">运管{{carData.yyCdrq ? "已" :"未"}}备案
                  </Button>
                  <Button type="primary" size="small"
                          @click="componentName='gamess'"
                          :disabled="!carData.gxCdrq">{{carData.gxCdrq ? "已" :"未"}}改气
                  </Button>
                </div>
              </div>
              <div :style="{height: (AF.getPageHeight()-83)/2-85+'px',overflow: 'auto'}">
                <Form label-position="top">
                  <Row>
                    <Col :span="item.span? item.span:'4'" v-for="(item,index) in columns" :key="index">
                      <FormItem class="formItemSty">

                        <div class="formTit" slot="label">
                          {{item.title}}
                        </div>
                        <div class="formMess">
                          <Input :value="getDict(carData[item.key],item.dict,item.append)" readonly placeholder="暂无信息"/>
                        </div>
                      </FormItem>
                    </Col>
                  </Row>
                </Form>
              </div>
            </Card>
          </Col>
          <Col span="8">
            <Card style="width:100%">
              <div slot="title" class="modelTit">
                <Icon type="ios-film-outline"></Icon>
                产权信息
                <div style="float: right">
                  <Button size="small" type="success"
                          @click="componentName='his_C'"
                          style="margin-right: 8px">变更记录
                  </Button>
                </div>
              </div>
              <div :style="{height: (AF.getPageHeight()-83)/2-85+'px',overflow: 'auto'}">
                <Form label-position="top">
                  <Row>
                    <Col span="24">
                      <FormItem class="formItemSty">

                        <div class="formTit" slot="label">
                          产权人：
                        </div>
                        <div class="formMess">
                          <Input :value="carData.clCqr" readonly placeholder="暂无信息"/>
                        </div>
                      </FormItem>
                    </Col>
                    <Col span="24">
                      <FormItem class="formItemSty">

                        <div class="formTit" slot="label">
                          产权人联系电话：
                        </div>
                        <div class="formMess">
                          <Input :value="carData.clCqrDn" readonly placeholder="暂无信息"/>
                        </div>
                      </FormItem>
                    </Col>
                    <Col span="24">
                      <FormItem class="formItemSty">

                        <div class="formTit" slot="label">
                          产权人证件号码：
                        </div>
                        <div class="formMess">
                          <Input :value="carData.clCqr" readonly placeholder="暂无信息"/>
                        </div>
                      </FormItem>
                    </Col>
                    <Col span="24">
                      <FormItem class="formItemSty">
                        <div class="formTit" slot="label">
                          车款：
                        </div>
                        <div class="formMess">
                          <Input :value="carData.ck" readonly placeholder="暂无信息"/>
                        </div>
                      </FormItem>
                    </Col>
                    <Col span="24">
                      <FormItem class="formItemSty">

                        <div class="formTit" slot="label">
                          质保金：
                        </div>
                        <div class="formMess">
                          <Input :value="carData.zbj" readonly placeholder="暂无信息"/>
                        </div>
                      </FormItem>
                    </Col>
                  </Row>
                </Form>
              </div>
            </Card>
          </Col>
        </Row>
        <Row>

          <Col span="12">
            <Card style="width:100%">
              <div slot="title" class="modelTit" style="position: relative">
                <Icon type="ios-film-outline"></Icon>
                保险信息
                <Tag class="TBGS" type="border" color="success">
                  投保公司 : {{carData.bxTbgs}}
                </Tag>
                <div style="float: right">
                  <Button size="small" type="success"
                          @click="componentName='hisBxjl'"
                          style="margin-right: 8px">续保记录
                  </Button>
                </div>
              </div>
              <div :style="{height: (AF.getPageHeight()-83)/2-85+'px',overflow: 'auto'}">
                <Form label-position="top">
                  <Row>
                    <Col :span="item.span" v-for="(item,index) in bxMes" :key="index">
                      <FormItem class="formItemSty">
                        <div class="formTit" slot="label">
                          {{item.title}} ：
                        </div>
                        <div class="formMess">
                          <Input :value="carData[item.key]" readonly placeholder="暂无信息"/>
                        </div>
                      </FormItem>
                    </Col>
                  </Row>
                </Form>
              </div>
            </Card>
          </Col>
          <Col span="12">
            <Card style="width:100%">
              <div slot="title" class="modelTit">
                <Icon type="ios-film-outline"></Icon>
                年审记录
              </div>
              <div :style="{height: (AF.getPageHeight()-83)/2-85+'px',overflow: 'auto'}">
                <table-area :parent="v" :pager="false" :TabHeight="(AF.getPageHeight()-83)/2-85-10"></table-area>
              </div>
            </Card>
          </Col>
        </Row>
      </div>
    </Modal>
    <component :is="componentName"></component>
  </div>
</template>

<script>
  import columns from './columns'
  import dzda from './carMess/dzda'
  import his_C from './carMess/cqbgjl'
  import hisBxjl from './carMess/hisbxjl'
  import syrbg from  './carMess/syrbg'
  import ygmess from './carMess/YGmess'
  import gamess from './carMess/GQmess'
  import jbxxdy from '../../../components/print/printCljbxx'

  export default {
    name: "chanquanHistory",
    components: {dzda, his_C, hisBxjl, ygmess, gamess,jbxxdy,syrbg},
    filters:{

    },
    computed:{

    },
    data() {
      return {
        v: this,
        apiRoot: this.apis.carns,
        showModal: true,
        componentName: '',
        columns: columns,
        carData: {},//车辆数据
        bxMes: [
          // {title:'投保公司',key:'bxTbgs'},

          {title: '保单位置', key: 'bxBdWz', span: 12},
          {title: '保单数量', key: 'bxBdCount', span: 12},

          {title: '保险电话', key: 'bxBxdh', span: 12},

          {title: '保险开始时间', key: 'bxBxrq', span: 12},
          {title: '保险终止时间', key: 'bxBxz', span: 12},

          {title: '保单编号', key: 'bxBdzbbh', span: 12},

          {title: '备注', key: 'bxBz', span: 24},
          // {title:'url',key:'bxDaFile',span:24},
        ],

        tableColumns: [
          {title: "#", fixed: 'left', width: 60, type: 'index'},
          // {title: '车牌号', key: 'cph', minWidth: 120, searchKey: 'cphLike'},
          {title: '年审类型', key: 'nslx', minWidth: 120, dict: 'nslx', searchType: 'dict'},
          {title: '年审时间', key: 'nssj', minWidth: 120},
          {title: '负责人', key: 'jsyxm', minWidth: 120},
          {title: '联系电话', key: 'jsylxdh', minWidth: 120},
          {
            title: '操作人', key: 'cjr', minWidth: 120, render: (h, p) => {
              let a = p.row.cjr.split('-')
              return h('div',a[1])
            }
          },
          {title: '操作时间', key: 'cjsj', minWidth: 120, render: (h, p) => {
              let a = p.row.cjsj.substring(0,10)
              return h('div',a)
            }
          },
        ],
        pageData: [],
        param: {
          orderBy: 'nssj desc',
          clId: '',
          total: 0,
          zt: '1',
          pageNum: 1,
          pageSize: 10
        },


      }
    },
    created() {
      this.util.initTable(this);
      this.carData = this.$parent.choosedItem
      this.param.clId = this.carData.id

      console.log(this.carData);

    },
    mounted() {

    },
    methods: {
      close() {
        this.$parent.componentName = ''
      },
      getDict(val, code, unit) {
        let a = val
        if (code != undefined) {
          a = this.dictUtil.getValByCode(this, code, val)
        }
        if (unit) {
          a = a + unit
        }
        return a
      },
      carType(val){
        return this.dictUtil.getValByCode(this, 'ZDCLK1042', val)
      }
    }
  }
</script>
