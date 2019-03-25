<template>
  <div class="box_col">
    <!--<pager-tit title="体检收费" iconname="ios-football"></pager-tit>-->
    <div class="box_col_100" style="margin-top: 16px">{{AutoReadCard}}
      <div class="box_row" style="height: 100%">
        <div class="" style="width: 400px;margin-right: 16px;">
          <Card style="width: 100%;">
            <div slot="title" style="font-weight: 700;font-size: 18px">
                体检缴费录入
            </div>
            <div :style="{height:AF.getPageHeight()-310+'px'}">
              <Form ref="saveForm" :model="form" :rules="ruleValidate">
                <FormItem prop="traineeName" label="姓名:">
                  <Input type="text" placeholder="请输入学员姓名"
                         size="large"
                         v-model="form.traineeName"></Input>
                </FormItem>
                <FormItem prop="idCardNo" label="证件号码:">
                  <Input type="text" placeholder="请输入证件号码"
                         size="large"
                         v-model="form.idCardNo"></Input>
                </FormItem>
                <FormItem prop="chargeSource" label="所属驾校:">
                  <Select filterable clearable v-model="form.chargeSource"
                          placeholder="请选择驾校"
                          size="large">
                    <Option v-for="item in JX" :value="item.val">{{item.val}}</Option>
                  </Select>
                  <!--<Input type="text" placeholder="请输入所属驾校"
                         size="large"
                         v-model="form.chargeSource"></Input>-->
                </FormItem>
                <FormItem label="收款方式">
                  <Select v-model="form.chargeType" placeholder="请选择收款方式"
                          size="large" disabled>
                    <Option v-for="(item,index) in getMoneyType" :value="item.key" :key="index">{{ item.val }}</Option>
                  </Select>
                </FormItem>
              </Form>
            </div>
            <div style="text-align: right">
              <Button type="default"
                      style="color: #949494;margin-right: 10px"
                      @click="rem"
              >重置
              </Button>
              <Button type="primary" @click="save">确定</Button>
            </div>
          </Card>
        </div>
        <div class="box_row_1auto">
          <Card style="width: 100%;">
            <Row  :gutter="10" style="margin-bottom: 8px">
              <Col span="7" :lg="7" :md="24">
                <div style="font-weight: 700;font-size: 18px">
                  体检缴费记录
                  <span style="font-size: 16px;margin-left: 16px">
                      共:
                    <span style="font-size: 20px;color: #ff9900">
                      {{tableData.length}}
                    </span>
                    人 - 合计:
                    <span style="font-size: 20px;color: #ff9900" v-if="tableData.length==0">
                      0
                    </span>
                    <span style="font-size: 20px;color: #ff9900" v-else>
                      {{tableData.length * tableData[0].chargeFee}}
                    </span>
                    元
                  </span>
                </div>
              </Col>
              <Col span="3" :lg="3" :md="4">
                <DatePicker v-model="param.times" type="daterange" split-panels :clearable="false" @on-change="changeTime"  placeholder="审核日期"   style="width: 100% ;"></DatePicker>
              </Col>
              <Col span="3" :lg="3" :md="5">
                <Input v-model="param.chargeSourceLike"
                       @on-enter="getPagerList"
                       placeholder="请输入学员所属驾校"/>
              </Col>
              <Col span="3" :lg="3" :md="5">
                <Input v-model="param.traineeNameLike"
                       @on-enter="getPagerList"
                       placeholder="请输入学员姓名"/>
              </Col>

              <Col span="4" :lg="4" :md="5">
                <Input v-model="param.idCardNoLike"
                       @on-enter="getPagerList"
                       placeholder="请输入学员证件号码"/>
              </Col>
              <Col span="3" :lg="3" :md="6">
                <Button type="primary" @click="getPagerList">
                  <Icon type="md-search"></Icon>
                  <!--查询-->
                </Button>
                <Tooltip content="打印" style="float: right">
                  <Button type="primary" @click="winPrint">
                    <Icon type="md-print"/>
                  </Button>
                </Tooltip>
              </Col>
            </Row>
            <Table border stripe
                   :height="AF.getPageHeight()-290"
                   :columns="tabTit" :data="tableData"
                   @on-selection-change="selecthange"></Table>
          </Card>
        </div>
      </div>
      <component :is="compName" :printMess='printMess'></component>
    </div>
  </div>
</template>

<script>
  import printInspect from '../../../components/print/printInspect'

  export default {
    name: "index",
    components:{
      printInspect
    },
    data() {
      return {
        compName: '',
        printMess: [],
        JX: [],
        JXCode: 'ZDCLK1017',
        count: 100,
        getMoneyType: [
          {
            key: '10',
            val: '扫码支付'
          }, {
            key: '00',
            val: '现金收费'
          }
        ],
        ruleValidate: {
          traineeName: [
            {required: true, message: "请输填写学员姓名", trigger: 'blur'}
          ],
          idCardNo: [
            {required: true, message: "请输填写学员证件号码", trigger: 'blur'},
            /*{message: "证件号码有误！！！",min:18,max:18, trigger: 'blur'}*/
          ],
          chargeSource: [
            {required: true, message: '请输填写所属驾校', trigger: 'blur'}
          ]
        },
        form: {
          traineeName: '',
          idCardNo: '',
          chargeSource: '',
          chargeType: '00'
        },
        total: 0,
        param: {
          chargeTimeGte:this.AF.trimDate()+' '+'00:00:00',
          chargeTimeLte:this.AF.trimDate()+' '+'23:59:59',
          // chargeTimeLike:'',
          traineeNameLike: '',//姓名
          chargeSourceLike: '',//驾校
          idCardNoLike: '',//证件号码
          chargeCode: '0001',
          //inspect: '1',
          //分页数据
          pageNum: 1,//当前页码
          pageSize: 999999//每页显示数
        },
        tabTit: [
          {
            align: 'center',
            type: 'selection',
            width: 80
          }, {
            type: 'index',
            width: 60,
            align: 'center'
          }, {
            title: '驾校',
            minWidth: 120,
            align: 'center',
            key: 'chargeSource',
            render: (h, p) => {
              let val =  p.row.chargeSource;
              return h('div', val)
            }
          },
          {
            title: '姓名',
            align: 'center',
            minWidth: 90,
            key: 'traineeName'
          },
          {
            title: '收据编号',
            align: 'center',
            minWidth: 140,
            render: (h, p) => {
              var lt = p.row.pjbh.split('-')
              if(lt == 0){
                return h('div', [
                  h('Tag', {
                    props: {
                      color: 'warning'
                    }
                  }, '票据未打印')
                ])
              } else {
                return h('div',
                  lt[0]+'-'+lt[1]
                  // p.row.pjbh.substring(0,lt-5)
                );
              }
            }
          },
          {
            title: '证件号码',
            align: 'center',
            minWidth: 180,
            key: 'idCardNo'
          },
          {
            title: '收费金额',
            align: 'center',
            minWidth: 100,
            key: 'chargeFee'
          },
          {
            title: '收费时间',
            align: 'center',
            minWidth: 180,
            key: 'chargeTime',
            render: (h, p) => {
              return h('div',
                p.row.chargeTime.substring(0,10)
              //   [
              //   h('Tooltip', {
              //     props: {
              //       placement: 'top',
              //       content: p.row.chargeTime
              //     }
              //   }, [
              //     h('Time', {
              //       props: {
              //         time: new Date(p.row.chargeTime.replace(/-/g, "/"))
              //       }
              //     })
              //   ])
              // ]
              )
            }
          },
          {
            title: '操作',
            align: 'center',
            fixed: 'right',
            minWidth: 90,
            render: (h, p) => {
              return h('div', [
                h('Tooltip',
                  {props: {placement: 'top', content: '撤销',}},
                  [
                    h('Button', {
                      props: {
                        type: 'error',
                        size: 'small',
                        icon: 'ios-redo'
                      },
                      on: {
                        click: () => {
                          this.dele(p.row.id)
                        }
                      }
                    })
                  ]
                )
              ])
            }
          }
        ],
        tableData: []
      }
    },
    created() {

      this.getDictList();
      this.getPagerList();
    },
    computed:{

      AutoReadCard() {
        if (window.vueObject.$data.card.CardNo){
          this.param.idCardNoLike = window.vueObject.$data.card.CardNo;
          this.param.nameLike = window.vueObject.$data.card.NameA;
          this.form.traineeName = window.vueObject.$data.card.NameA;
          this.form.idCardNo = window.vueObject.$data.card.CardNo;
          this.getPagerList();
        }

        return "";
      }
    },
    methods: {
      changeTime(val){
        console.log(val);
        this.param.chargeTimeGte = val[0]+' '+"00:00:00";
        this.param.chargeTimeLte = val[1]+' '+"23:59:59";
        this.getPagerList();
      },
      getDictList() {
        this.JX = this.dictUtil.getByCode(this, this.JXCode);
      },
      getPagerList() {
        var v = this;
        // console.log(this.param.chargeTimeLike);
        // this.param.chargeTimeLike = this.AF.trimDate(this.param.chargeTimeLike);
        console.log(this.param);
        this.$http.post(this.apis.TJSDF.PAGER, this.param).then((res) => {
          v.tableData = res.page.list
          v.total = res.page.total
        })
      },
      rem() {//重置
        this.form = {traineeName: '', idCardNo: '', chargeSource: '', chargeType: '00'}
        this.$Message.success('重置成功');
      },
      save() {
        this.$refs.saveForm.validate((valid) => {
          if (valid) {
            this.$http.post(this.apis.TJSDF.ADD, this.form).then((res) => {
              if (res.code == 200) {
                this.getPagerList()
                this.form = {traineeName: '', idCardNo: '', chargeSource: '', chargeType: '00'}
              }
              this.$Message.info(res.message);
            }).catch((err) => {
              this.$Message.error(res.message);
            })
          }
        })
      },
      dele(id) {
        this.$http.post(this.apis.TJSDF.DELE + id).then((res) => {
          if (res.code == 200) {
            this.getPagerList()
          }
          this.$Message.info(res.message);
        }).catch((err) => {
          this.$Message.error(res.message);
        })
      },
      winPrint() {
        var v = this
        if (this.printMess.length > 1) {
          for (var i = 0; i < this.printMess.length - 1; i++) {
            if (this.printMess[i].pjbh != '' &&
              this.printMess[i + 1].pjbh != '' &&
              this.printMess[i].pjbh != this.printMess[i + 1].pjbh != '') {
              this.swal({
                title: '所选订单 单号有误！！！',
                type: 'warning',
                confirmButtonText: '关闭'
              })
              return
            }

          }
        }

        setTimeout(() => {
          v.AF.WinPrint(this, this.printMess, 'printInspect')
        }, 100)
      },
      selecthange(arr) {
        console.log(arr);
        this.printMess = arr
      },
      pageChange(n) {
        this.param.pageNum = n
        this.getPagerList()
      },
      pageSizeChange(n) {
        this.param.pageSize = n
        this.getPagerList()
      },
    }
  }
</script>

<style scoped>

</style>
