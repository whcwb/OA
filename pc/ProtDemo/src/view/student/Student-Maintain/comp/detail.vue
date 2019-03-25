<style lang="less">
  .boxerr {
    position: relative;
    #errMess {
      position: absolute;
      top: 125px;
      right: 0;
      z-index: 100;
      width: 110px;
      height: 110px;
    }
    #stuOver {
      position: absolute;
      top: 0;
      right: 0;
      z-index: 100;
      width: 110px;
    }
  }

  .MessOver {
    color: #f5a623;
    font-size: 100px;
    font-weight: 900;
  }

  .stuMess {
    .ivu-modal-close {
      right: -5px;
      top: -5px;
    }
  }
</style>
<template>
  <div class="stuMess">
    <Modal v-model="showModal" height="700"
           :width="selectRow.remark == '999999'&&selectRow.status=='50'?400:1200"
           :closable='false' :mask-closable="false"
           :styles="{top: '60px'}">
      <Row :gutter="20" style="position: relative">
        <div @click="close" style="position: absolute;top:-15px;right: -5px;z-index: 200;cursor: pointer">
          <Icon type="md-close-circle" size="30" color="#afafaf"/>
        </div>
        <Col :span="selectRow.remark == '999999'&&selectRow.status=='50'?24:7">
          <Card dis-hover>
            <p slot="title" style="height: 28px;">
              <Tag color="magenta">{{getClassName}}</Tag>
              <Tag v-if="selectRow.carType.substring(0, 1) == 'C'" color="green">{{selectRow.carType}}</Tag>
              <Tag v-else color="volcano">{{selectRow.carType}}</Tag>
            </p>
            <span slot="extra">
              <span style="font-weight: 700;font-size: 14px">
                当前状态
              </span>
              <Tag color="success">{{getZt}}</Tag>
            </span>
            <Row>
              <Col span="24" align="center">
                有效期止：<span v-if="selectRow.indateEndTime == ''">-</span><span v-else>{{selectRow.indateEndTime}}</span>
              </Col>
            </Row>
            <Row style="text-align: center">
              <Col span="24" class="boxerr">
                <img id="errMess" src="./err.png" alt="" v-if="selectRow.remark == '999999'">
                <img id="stuOver" src="./stuOver.png" alt=""
                     v-if="selectRow.remark == '999999'&&selectRow.status=='50'">

                <Avatar v-if="selectRow.headImg != ''"
                        :src="apis.getImgUrl+selectRow.headImg"
                        icon="ios-person"
                        style="width:180px;height:180px;line-height: 180px;font-size: 150px;border-radius:100px"/>
                <Avatar v-else
                        icon="ios-person"
                        style="width:180px;height:180px;line-height: 180px;font-size: 150px;border-radius:100px"/>
              </Col>
            </Row>
            <Row style="text-align: center">
              <Col span="24">
                <h1 style="color:#ed4014">{{selectRow.name}}&nbsp;<Tag>{{selectRow.gender == '10' ? '男' : '女'}}</Tag>
                </h1>
                <h3>
                  <Icon type="ios-call" size="24"/>
                  {{selectRow.phone}}
                </h3>
              </Col>
            </Row>
            <Row style="text-align: center">
              <Col span="24">
                <barcode v-if="selectRow.serialNum != ''" :value="selectRow.serialNum"></barcode>
                <Tag v-else color="default">-</Tag>
              </Col>
            </Row>
            <Row style="text-align: center;padding-top: 10px"
                 v-if="!(selectRow.remark == '999999'&&selectRow.status=='50')">
              <Col span="24">
                <Table
                  size="large"
                  stripe
                  border
                  :columns="coachColumn"
                  :data="[
                    {
                    'secondSubjectCoach':selectRow.secondSubjectCoach,
                    'thirdSubjectCoach':selectRow.thirdSubjectCoach
                    }
                  ]">
                </Table>
              </Col>
            </Row>
            <Row style="padding-top: 20px" v-if="!(selectRow.remark == '999999'&&selectRow.status=='50')">
              <Col span="24">
                <Card dis-hover>
                  <p slot="title" style="height: 28px;">
                    培训
                  </p>
                  <Row style="text-align: center">
                    <Col span="24">
                      <Table
                        size="small"
                        stripe
                        :columns="kmpxztColumn"
                        :data="[selectRow]">
                      </Table>
                    </Col>
                  </Row>
                </Card>
              </Col>
            </Row>
          </Card>

        </Col>
        <Col span="17" v-if="!(selectRow.remark == '999999'&&selectRow.status=='50')">
          <Row>
            <Col span="24">
              <Card dis-hover style="height: 230px">
                <p slot="title">
                  受理
                </p>
                <Row style="text-align: center">
                  <Col span="24" style="position: relative">
                    <Steps :current="stepIndex">
                      <Step title="报名" content="-" ref="bmStep"></Step>
                      <Step title="审核" content="-" ref="shStep"></Step>
                      <Step title="缴费" content="-" ref="jfStep"></Step>
                      <Step title="受理" content="-" ref="slStep"></Step>
                    </Steps>
                    <!-- 报名内容 -->
                    <div ref="bmContent" class="ivu-poptip-popper" style="position:absolute;left: -5px;top: 24px"
                         x-placement="bottom">
                      <div class="ivu-poptip-content">
                        <div class="ivu-poptip-arrow"></div> <!---->
                        <div class="ivu-poptip-inner">
                          <div class="ivu-poptip-title">
                            <div class="ivu-poptip-title-inner">{{selectRow.registrationTime}}</div>
                          </div>
                          <div class="ivu-poptip-body">
                            <div class="ivu-poptip-body-content">
                              <div class="ivu-poptip-body-content-inner">
                                {{getJgmc}}
                                <Divider style="margin: 5px;width:90%;"/>
                                {{selectRow.cjr}}
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                    <!-- 审核内容 -->
                    <div ref="shContent" class="ivu-poptip-popper" style="position:absolute;left: 200px;top: 24px"
                         x-placement="bottom">
                      <div class="ivu-poptip-content">
                        <div class="ivu-poptip-arrow"></div> <!---->
                        <div class="ivu-poptip-inner">
                          <div class="ivu-poptip-title">
                            <div class="ivu-poptip-title-inner">
                              <span v-if="selectRow.infoCheckTime != ''">{{selectRow.infoCheckTime}}</span>
                              <span v-else>-</span>
                            </div>
                          </div>
                          <div class="ivu-poptip-body">
                            <div class="ivu-poptip-body-content">
                              <div class="ivu-poptip-body-content-inner">
                                <Tag v-if="selectRow.infoCheckTime != ''" color="success">审核通过</Tag>
                                <Tag v-else>-</Tag>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                    <!-- 缴费内容 -->
                    <div ref="jfContent" class="ivu-poptip-popper" style="position:absolute;left: 405px;top: 24px"
                         x-placement="bottom">
                      <div class="ivu-poptip-content">
                        <div class="ivu-poptip-arrow"></div> <!---->
                        <div class="ivu-poptip-inner">
                          <div class="ivu-poptip-title">
                            <div class="ivu-poptip-title-inner">
                              <span v-if="selectRow.confirmTime != ''">{{selectRow.confirmTime}}</span>
                              <span v-else>-</span>
                            </div>
                          </div>
                          <div class="ivu-poptip-body">
                            <div class="ivu-poptip-body-content">
                              <div class="ivu-poptip-body-content-inner">
                                <Tag v-if="selectRow.chargeStatus == '00'">-</Tag>
                                <Tag v-else-if="selectRow.chargeStatus == '10' && selectRow.arrearage == '10'"
                                     color="red">分期尾款未结
                                </Tag>
                                <Tag v-else color="success">报名费已缴</Tag>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                    <!-- 受理内容 -->
                    <div ref="slContent" class="ivu-poptip-popper" style="position:absolute;left: 610px;top: 24px"
                         x-placement="bottom">
                      <div class="ivu-poptip-content">
                        <div class="ivu-poptip-arrow"></div> <!---->
                        <div class="ivu-poptip-inner">
                          <div class="ivu-poptip-title">
                            <div class="ivu-poptip-title-inner">
                              <span v-if="selectRow.acceptTime != ''">{{selectRow.acceptTime}}</span>
                              <span v-else>-</span>
                            </div>
                          </div>
                          <div class="ivu-poptip-body">
                            <div class="ivu-poptip-body-content">
                              <div class="ivu-poptip-body-content-inner">
                                <Tag v-if="selectRow.acceptTime != ''" color="success">受理成功</Tag>
                                <Tag v-else>-</Tag>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>

                  </Col>
                </Row>
              </Card>
            </Col>
          </Row>
          <!--<Row style="padding-top: 20px">-->
          <!--<Col span="24">-->
          <!--<Card dis-hover>-->
          <!--<p slot="title" style="height: 28px;">-->
          <!--培训-->
          <!--</p>-->
          <!--<Row style="text-align: center">-->
          <!--<Col span="24">-->
          <!--<Table-->
          <!--size="small"-->
          <!--stripe-->
          <!--:columns="kmpxztColumn"-->
          <!--:data="[selectRow]">-->
          <!--</Table>-->
          <!--</Col>-->
          <!--</Row>-->
          <!--</Card>-->
          <!--</Col>-->
          <!--</Row>-->
          <Row style="padding-top: 20px">
            <Col span="24">
              <Card dis-hover>
                <p slot="title" style="height: 28px;">
                  约考
                </p>
                <div style="border: 1px solid #dcdee2;height: 472px">
                  <Table :columns="tabtit" :data="selectRow.testInfos" stripe :height="470"></Table>
                </div>
              </Card>
            </Col>
          </Row>
        </Col>
      </Row>
      <div slot="footer">
        <!--<Button type="default" style="color: #949494" @click="close">关闭</Button>-->
      </div>
    </Modal>
  </div>
</template>

<script>
  import VueBarcode from '@xkeshi/vue-barcode';

  export default {
    name: "audit",
    filters: {
      testResult: (val) => {
        if (val == '00') {
          return '合格'
        } else if (val == '10') {
          return '不合格'
        } else {
          return '待考试'
        }
      },
      mentTime: (val) => {
        if (val != '') {
          return '初考已缴'
        } else {
          return '初考待缴'
        }
      },
      firSubYY: (val) => {
        if (val == '' || val == '00' || val == '10') {
          return '-'
        }
        if (val == '20' || val == '40' || val == '30') {
          return '已预约'
        }
      },
      secSub: (val) => {
        if (val == '' || val == '00') {
          return '-'
        }
        if (val == '20' || val == '40' || val == '30' || val == '10') {
          return '已预约'
        }
      },
      firSubKS: (val) => {
        if (val == '' || val == '00' || val == '10') {
          return '-'
        }
        if (val == '30') {
          return '不合格'
        }
        if (val == '40') {
          return '合格'
        }
      }
    },
    props: {
      selectRow: {
        type: Object,
        default: {}
      }
    },
    components: {
      'barcode': VueBarcode
    },
    data() {
      return {
        tabtit:[
          {
            title:'序',
            with:60,
            align: 'center',
            type:'index'
          },
          {
            title:'科目',
            align: 'center',
            key:'subject'
          },
          {
            title:'约考时间',
            align: 'center',
            key:'testTime'
          },
          {
            title:'约考地点',
            align: 'center',
            key:'testPlace'
          },
          {
            title:'考试结果',
            align: 'center',

            key:'testResult',
            render: (h, p) => {
              let a = ''
              if(p.row.testResult == '00'){
                a = '合格'
              }else if(p.row.testResult == '10'){
                a = '不合格'
              }else {
                a = '-'
              }
              return h('div', a)

            }

          }
        ],
        showModal: true,
        stepIndex: 0,
        classType: {
          code: 'ZDCLK1002',
          data: []
        },
        coachColumn: [
          {
            title: '科二教练', align: 'center', key: 'secondSubjectCoach', width: 150, render: (h, p) => {
              let res = p.row.secondSubjectCoach;
              if (res == '') {
                res = '-';
              }

              return h('div', res);
            }
          },
          {
            title: '科三教练', align: 'center', key: 'thirdSubjectCoach', render: (h, p) => {
              let res = p.row.thirdSubjectCoach;
              if (res == '') {
                res = '-';
              }

              return h('div', res);
            }
          },
        ],
        kmztColumn: [
          {
            title: '科一状态', align: 'center', render: (h, p) => {
              let color = 'success';
              let msg = '已缴';
              if (p.row.firSubPaymentTime == '' && p.row.acceptStatus == '20') {
                color = 'red';
                msg = '初考待缴';
              } else if (p.row.firSub == '20') {
                msg = '已约考';
              } else if (p.row.firSub == '30') {
                color = 'error';
                msg = '不合格';
              } else if (p.row.firSub == '40') {
                msg = '合格';
              } else if (p.row.firSubPaymentTime != '' && p.row.acceptStatus == '20') {
                msg = '初考已缴';
              } else {
                color = 'default';
                msg = '-';
              }

              return h('Tag', {
                props: {
                  color: color
                }
              }, msg);
            }
          },
          {
            title: '科二状态', align: 'center', render: (h, p) => {
              let color = 'success';
              let msg = '已缴';
              if (p.row.secSubPaymentTime == '' && p.row.firSub == '40') {
                color = 'red';
                msg = '初考待缴';
              } else if (p.row.secSub == '20') {
                msg = '已约考';
              } else if (p.row.secSub == '30') {
                color = 'error';
                msg = '不合格';
              } else if (p.row.secSub == '40') {
                msg = '合格';
              } else if (p.row.secSubPaymentTime != '' && p.row.firSub == '40') {
                msg = '初考已缴';
              } else {
                color = 'default';
                msg = '-';
              }

              return h('Tag', {
                props: {
                  color: color
                }
              }, msg);
            }
          },
          {
            title: '科三状态', align: 'center', render: (h, p) => {
              let color = 'success';
              let msg = '已缴';
              if (p.row.thirdSubPaymentTime == '' && p.row.secSub == '40') {
                color = 'red';
                msg = '初考待缴';
              } else if (p.row.thirdSub == '20') {
                msg = '已约考';
              } else if (p.row.thirdSub == '30') {
                color = 'error';
                msg = '不合格';
              } else if (p.row.thirdSub == '40') {
                msg = '合格';
              } else if (p.row.thirdSubPaymentTime != '' && p.row.secSub == '40') {
                msg = '初考已缴';
              } else {
                color = 'default';
                msg = '-';
              }

              return h('Tag', {
                props: {
                  color: color
                }
              }, msg);
            }
          },
        ],
        kmpxztColumn: [
          {
            title: '科目一', align: 'center', render: (h, p) => {
              let color = 'success';
              let msg = '';
              let carType = p.row.carType.substring(0, 1);
              if (carType == 'C') {
                if (p.row.acceptStatus == '20') {
                  msg = '合格';
                } else {
                  color = 'default';
                  msg = '-';
                }
              } else {
                if (p.row.firSubTrainStatus == '00') {
                  msg = '合格';
                } else if (p.row.firSubTrainStatus == '10') {
                  msg = '不合格';
                } else {
                  color = 'default';
                  msg = '-';
                }
              }

              return h('Tag', {
                props: {
                  color: color
                }
              }, msg);
            }
          },
          {
            title: '科目二', align: 'center', render: (h, p) => {
              let color = 'success';
              let msg = '';
              let carType = p.row.carType.substring(0, 1);
              if (carType == 'C') {
                if (p.row.firSub == '40') {
                  msg = '合格';
                } else {
                  color = 'default';
                  msg = '-';
                }
              } else {
                if (p.row.secSubTrainStatus == '00') {
                  msg = '合格';
                } else if (p.row.secSubTrainStatus == '10') {
                  msg = '不合格';
                } else {
                  color = 'default';
                  msg = '-';
                }
              }

              return h('Tag', {
                props: {
                  color: color
                }
              }, msg);
            }
          },
          {
            title: '科目三', align: 'center', render: (h, p) => {
              let color = 'success';
              let msg = '';
              let carType = p.row.carType.substring(0, 1);
              if (carType == 'C') {
                if (p.row.secSub == '40') {
                  msg = '合格';
                } else {
                  color = 'default';
                  msg = '-';
                }
              } else {
                if (p.row.thirdSubTrainStatus == '00') {
                  msg = '合格';
                } else if (p.row.thirdSubTrainStatus == '10') {
                  msg = '不合格';
                } else {
                  color = 'default';
                  msg = '-';
                }
              }

              return h('Tag', {
                props: {
                  color: color
                }
              }, msg);
            }
          },
        ],
        fir: [],
        sec: [],
        third: [],
      }
    },
    created() {
      console.log(this.selectRow);
      console.log(this.selectRow.serialNum);
      this.km()

    },
    filters: {
      testResult: (val) => {
        if(val=='00'){
          return '合格'
        }
        return '不合格'
      }
    },
    computed: {
      getJgmc() {
        let jgmcArray = this.selectRow.jgmc.split("/");
        let res = "";
        if (jgmcArray.length > 1) {
          for (let i = 1; i < jgmcArray.length; i++) {
            res += jgmcArray[i];
            if ((i + 1) != jgmcArray.length) {
              res += '-';
            }
          }
        } else {
          res = jgmcArray[0];
        }

        return res;
      },
      getZt() {
        let str = this.selectRow.status;
        if (str == '99') {
          str = '已报名';
        } else if (str == '00') {
          str = '待受理';
        } else if (str == '10') {
          str = '科目一待考';
        } else if (str == '20') {
          str = '科目二待考';
        } else if (str == '30') {
          str = '科目三待考';
        } else if (str == '40') {
          str = '科目四待考';
        } else if (str == '50') {
          str = '结业';
        } else if (str == '60') {
          str = '退学';
        }

        return str;
      },
      getClassName() {
        for (let item of this.classType.data) {
          if (this.selectRow.classType == item.key) {
            return item.val;
          }
        }
      }
    },
    mounted() {
      // $(this.$refs.bmStep.$el).find(".ivu-steps-content").html(this.$refs.bmContent);
      // $(this.$refs.shStep.$el).find(".ivu-steps-content").html(this.$refs.shContent);
      // $(this.$refs.jfStep.$el).find(".ivu-steps-content").html(this.$refs.jfContent);
      // $(this.$refs.slStep.$el).find(".ivu-steps-content").html(this.$refs.slContent);
      this.classType.data = this.dictUtil.getByCode(this, this.classType.code);
      if (this.selectRow.infoCheckTime != '') {
        this.stepIndex = 1;
      }
      if (this.selectRow.confirmTime != '') {
        this.stepIndex = 2;
      }
      if (this.selectRow.acceptTime != '') {
        this.stepIndex = 3;
      }
    },
    methods: {
      km() {
        if (this.selectRow.testInfos.length == 0) {
          return
        }
        this.selectRow.testInfos.forEach((item, index) => {
          console.log(item);
          if (item.subject == "科目一") {
            this.fir.push(item)
          } else if (item.subject == "科目二") {
            this.sec.push(item)
          } else if (item.subject == "科目三") {
            this.third.push(item)
          }
        })
      },
      close() {
        this.$parent.compName = ''
      }
    }
  }
</script>

<style lang="less">
  .footerCard {
    .ivu-card-body {
      border: 0px;
      padding: 0px;
      line-height: 1;
      background-color: #ed3f14;
    }
  }

  .lingTiTSty {
    text-align: center;
    border-bottom: 1px solid #dcdee2;
    background: #f8f8f9;
    color: #515a6e;
    height: 32px;
    line-height: 32px;
    font-size: 10px;
    font-weight: 600;
  }

  .lingItemSty {
    text-align: center;
    border-bottom: 1px solid #dcdee2;
    color: #515a6e;
    font-size: 15px;
    min-height: 50px;
    line-height: 50px;
  }
  .itemList{
    border: solid 1px #f5a623;
    width: 25%;
    padding: 12px;
    position: relative;
    i{
      position: absolute;
      right: -30px;
      top: 35%;
      z-index: 200;
    }
  }
</style>
