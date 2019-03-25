<template>
  <div class="box_col" style="position: relative">
    <div class="box_row colCenter pageTitSty">
      <div style="background-color: #19be6b;width: 3px;height: 30px">
      </div>
      <div class="tit">
        <Select v-model="pData.km" filterable style="width: 15%;font-size: 24px;" @on-change="changeKm">
          <Option value="02">科目二</Option>
          <Option value="03">科目三</Option>
        </Select>
        <Select v-model="param.carType" filterable style="width: 10%;font-size: 24px"
                @on-change="(e)=>{v.util.getPageData(v)}">
          <Option v-for="item in pData.dictList.carType.data" :value="item.key" :key="item.key">{{ item.val }}</Option>
        </Select>
        教练员分配
      </div>
    </div>
    <Row style="padding-top: 8px">
      <Col span="24">
        <Form :label-width="20">
          <Row type="flex" justify="end">
            <Col span="4">
              <FormItem>
                <Cascader trigger="hover" v-model="param.bmd" :data="pData.dictList.bmd.data" placeholder="报名点选择"
                          filterable></Cascader>
              </FormItem>
            </Col>
            <Col span="4">
              <FormItem>
                <DatePicker v-model="param.bmTime" split-panels type="daterange" placement="bottom-end" placeholder="报名日期"
                            style="width: 100% ;"></DatePicker>
              </FormItem>
            </Col>
            <Col span="4">
              <FormItem>
                <Input type="text" placeholder="证件号码" v-model="param.idCardNoLike"/>
              </FormItem>
            </Col>
            <Col span="4">
              <FormItem>
                <Input type="text" placeholder="学员姓名" v-model="param.nameLike"/>
              </FormItem>
            </Col>
            <Col span="1">
              <FormItem>
                <Button type="primary" @click="param.pageNum = 1,v.util.getPageData(v)">
                  <Icon type="md-search"></Icon>
                </Button>
              </FormItem>
            </Col>
            <Col span="2">
              <FormItem>
                <Button type="success" @click="allocCoach">
                  <Icon type="md-person-add"></Icon>
                  分配教练员
                </Button>
              </FormItem>
            </Col>
          </Row>
        </Form>
        <div class="box_col_auto">
          <!--:loading="SpinShow"-->
          <Table
            ref="studentRef"
            :height="AF.getPageHeight()-300"
            size="large"
            stripe
            :columns="tabTit"
            :data="pageData"></Table>
        </div>
        <div style="text-align: right">
          <Page :total=param.total
                :current=param.pageNum
                :page-size=param.pageSize
                :page-size-opts=[8,10,20,30,40,50]
                show-total
                show-elevator
                show-sizer
                placement='top'
                @on-page-size-change='pageSizeChange'
                @on-change='pageChange'>
          </Page>
        </div>
      </Col>
    </Row>

    <component
      :is="compName"
      :pData="pData"
    >
    </component>
  </div>
</template>

<script>
  import coachList from './comp/coach-list.vue'

  export default {
    name: "index",
    components: {
      coachList
    },
    data() {
      return {
        v: this,
        pData: {
          drivingType: 'C1',//车型。取字典：ZDCLK0040
          km: '02',
          selectRows: [],
          dictList: {
            carType: {
              code: 'ZDCLK0040',
              data: []
            },
            bmd: {
              code: '',
              data: []
            }
          }
        },
        compName: '',
        //学员查询
        pagerUrl: this.apis.TRAINEE.PAGER,
        param: {
          nameLike: '',//姓名
          idCardNoLike: '',
          bmd: [],//报名点代码
          carType: 'C1',//车型。取字典：ZDCLK0040
          km: '02',
          bmTime: '',//报名日期范围值
          acceptStatus: '20',//只有已受理的学员才进行分配操作
          firSub: '40',
          secondSubjectCoachFlag: '',
          thirdSubjectCoachFlag: '',
          //分页数据
          orderBy: 'registrationTime desc',
          total: 1,//总数量
          pageNum: 1,//当前页码
          pageSize: 8//每页显示数
        },
        //table数据查询结果
        pageData: [],
        dictList: {
          carType: {
            code: 'ZDCLK0040',
            data: []
          },
          bmd: {
            code: '',
            data: []
          }
        },
        tabTit: [],
        tabKm2Tit: [
          {type: 'index', width: 60, align: 'center'},
          {title: '#', type: 'selection',align: 'center', width: 60},
          {title: '姓名', key: 'name', align: 'center',width: 120},
          {title: '证件号码', key: 'idCardNo',align: 'center', width: 200},
          {
            title: '性别', width: 80, key: 'gender',align: 'center', render: (h, p) => {
              return h('div', p.row.gender == '10' ? '男' : '女');
            }
          },
          {
            title: '报名点', key: 'jgmc', width: 160,align: 'center', render: (h, params) => {
              let jgmcArray = params.row.jgmc.split("/");
              let res = "";
              if (jgmcArray.length == 2 || jgmcArray.length == 1) {
                return h('div', params.row.jgmc);
              } else if (jgmcArray.length == 3) {
                return h('div', jgmcArray[1] + ("/" + jgmcArray[2]))
              }
              // let jgmcArray = params.row.jgmc.split("/");
              // let res = "";
              // if (jgmcArray.length > 1){
              //   for (let i=1; i<jgmcArray.length; i++){
              //     res += jgmcArray[i];
              //     if ((i+1) != jgmcArray.length){
              //       res += '-';
              //     }
              //   }
              // }else{
              //   res = jgmcArray[0];
              // }
              // return h('div', res);
            }
          },
          {title: '流水号', key: 'serialNum',align: 'center', width: 160},
          {
            title: '报名时间', key: 'registrationTime', align: 'center',width: 160, render: (h, params) => {
              return h('div', params.row.registrationTime.substring(0,10)
              //   [
              //   h('Tooltip',
              //     {props: {placement: 'top', content: params.row.registrationTime,}},
              //     [
              //       h('Time', {
              //         props: {
              //           time: new Date(params.row.registrationTime.replace(/-/g, "/"))
              //         }
              //       })
              //     ]
              //   )
              // ]
              );
            }
          },
          {
            title: '报名费', key: 'chargeStatus',align: 'center', width: 150, render: (h, p) => {
              let color = 'success';
              let msg = '已结清';
              if (p.row.chargeStatus == '00') {
                color = 'red';
                msg = '未结清';
              } else {
                //查看学员是否有欠费
                if (p.row.arrearage == '10') {
                  color = 'red';
                  msg = '未结清';
                }
              }

              if (color == 'red') {
                return h('Tag', {
                  props: {
                    color: color
                  }
                }, msg);
              }

              return h('div', [
                h('Tooltip',
                  {props: {placement: 'top', content: p.row.confirmTime}},
                  [
                    h('Tag', {
                      props: {
                        color: color
                      }
                    }, msg)
                  ]
                )
              ]);
            }
          },
          {title: '档案位置', width: 140,align: 'center', key: 'recordLib'},
          {title: '有效期', width: 150,align: 'center', key: 'indateEndTime'},
          {
            title: '车型', key: 'carType',align: 'center', width: 80, render: (h, p) => {
              let color = 'volcano';
              let carModel = p.row.carType;
              let carTypeStr = carModel.substring(0, 1);
              if (carTypeStr == 'C') {
                //小车
                color = 'green';
              }

              //大车
              return h('Tag', {
                props: {
                  color: color
                },
                style: {
                  fontSize: '15px',
                }
              }, carModel);
            }
          },
          {
            title: '科二教练员', width: 180,align: 'center', key: 'secondSubjectCoach', render: (h, p) => {
              let msg = p.row.secondSubjectCoach;
              if (msg == '') {
                msg = '未分配';
              }

              return h('div', msg);
            },
            filters: [
              {
                label: '未分配',
                value: 1
              },
              {
                label: '已分配',
                value: 2
              }
            ],
            filterMultiple: false,
            filterRemote: (value, filterParamValue, parent) => {
              this.param.secondSubjectCoachFlag = value;
              this.util.getPageData(this);

              return true;
            }
          }
        ],
        tabKm3Tit: [
          {title: '#', type: 'selection',align: 'center', width: 60},
          {title: '姓名', key: 'name',align: 'center', width: 120},
          {title: '证件号码', key: 'idCardNo',align: 'center', width: 200},
          {
            title: '性别', width: 80, key: 'gender',align: 'center', render: (h, p) => {
              return h('div', p.row.gender == '10' ? '男' : '女');
            }
          },
          {
            title: '报名点', key: 'jgmc', width: 160,align: 'center', render: (h, params) => {
              let jgmcArray = params.row.jgmc.split("/");
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
              return h('div', res);
            }
          },
          {title: '流水号', key: 'serialNum',align: 'center', width: 160},
          {
            title: '报名时间', key: 'registrationTime', align: 'center',width: 160, render: (h, params) => {
              return h('div', params.row.registrationTime.substring(0,10)
              );
            }
          },
          {
            title: '报名费', key: 'chargeStatus',align: 'center', width: 150, render: (h, p) => {
              let color = 'success';
              let msg = '已结清';
              if (p.row.chargeStatus == '00') {
                color = 'red';
                msg = '未结清';
              } else {
                //查看学员是否有欠费
                if (p.row.arrearage == '10') {
                  color = 'red';
                  msg = '未结清';
                }
              }

              if (color == 'red') {
                return h('Tag', {
                  props: {
                    color: color
                  }
                }, msg);
              }

              return h('div', [
                h('Tooltip',
                  {props: {placement: 'top', content: p.row.confirmTime}},
                  [
                    h('Tag', {
                      props: {
                        color: color
                      }
                    }, msg)
                  ]
                )
              ]);
            }
          },
          {title: '档案位置', width: 140, align: 'center',key: 'recordLib'},
          {title: '有效期', width: 150, key: 'indateEndTime'},
          {
            title: '车型', key: 'carType',align: 'center', width: 80, render: (h, p) => {
              let color = 'volcano';
              let carModel = p.row.carType;
              let carTypeStr = carModel.substring(0, 1);
              if (carTypeStr == 'C') {
                //小车
                color = 'green';
              }

              //大车
              return h('Tag', {
                props: {
                  color: color
                },
                style: {
                  fontSize: '15px',
                }
              }, carModel);
            }
          },
          {
            title: '科三教练员', width: 180,align: 'center', key: 'thirdSubjectCoach', render: (h, p) => {
              let msg = p.row.thirdSubjectCoach;
              if (msg == '') {
                msg = '未分配';
              }

              return h('div', msg);
            },
            filterMultiple: false,
            filters: [
              {
                label: '未分配',
                value: 1
              },
              {
                label: '已分配',
                value: 2
              }
            ],
            filterRemote: (value, filterParamValue, parent) => {
              this.param.thirdSubjectCoachFlag = value;
              this.util.getPageData(this);

              return true;
            }
          }
        ],
      }
    },
    created() {
      this.getDictList();
      this.getBmdList();
      this.util.initTable(this);
      this.changeKm(this.pData.km);
    },
    methods: {
      //获取当前用户可操作的报名点
      getBmdList() {
        this.$http.get(this.apis.FRAMEWORK.getCurrentOrgTree, {timers: new Date().getTime()}).then((res) => {
          if (res.code === 200) {
            /*if(res.result[0].value.length=='6'){
              this.pData.dictList.bmd.data= res.result[0].children;
            }else if(res.result[0].value.length=='3'){
              this.pData.dictList.bmd.data= res.result[0].children[0].children;
            }*/
            if(res.result[0].value.length==3){
              this.pData.dictList.bmd.data = res.result[0].children[0].children;
            }else if(res.result[0].value.length==6){
              this.pData.dictList.bmd.data = res.result[0].children;
            }else if(res.result[0].value.length==9){
              this.pData.dictList.bmd.data = res.result
            }
            // this. = res.result[0].children;
          }
        }).catch((error) => {
        })
      },
      getDictList() {
        this.pData.dictList.carType.data = this.dictUtil.getByCode(this, this.pData.dictList.carType.code);
      },
      pageChange(n) {
        this.util.pageChange(this, n);
      },
      pageSizeChange(n) {
        this.util.pageSizeChange(this, n);
      },
      changeKm(val) {
        if (val == '02') {
          this.tabTit = this.tabKm2Tit;
        } else {
          this.tabTit = this.tabKm3Tit;
        }
      },
      allocCoach() {

        this.pData.selectRows = this.$refs.studentRef.getSelection();
        this.pData.drivingType = this.param.carType;
        if (this.pData.selectRows.length == 0) {
          this.swal({
            text: '请先选择学员',
            type: 'error'
          });

          return;
        }

        this.compName = 'coachList';
      }
    }
  }
</script>

<style lang="less">
  .tit {
    .ivu-select-selection {
      border: 0px;
    }
    .ivu-select-single .ivu-select-input {
      font-size: 24px;
    }
  }
</style>
