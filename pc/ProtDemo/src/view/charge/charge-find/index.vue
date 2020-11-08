<template>
  <div class="box_col">
    <!--<pager-tit title="收费查询" iconname="ios-football"></pager-tit>{{AutoReadCard}}-->
    <Row :gutter="10" style="margin-bottom: 8px">
      <Col span="4" :lg="4" :md="24">
        <pager-tit title="收费查询" iconname="ios-football"></pager-tit>
        {{AutoReadCard}}
      </Col>
      <Col span="20" :lg="20" :md="24">
        <Row :gutter="12" type="flex" justify="end">
          <Col span="4" :lg="4" :md="4">
            <Select multiple v-model="chargeCodeIn" filterable clearable
                    placeholder="费用类型"
                    @on-change="getpagerList">
              <Option v-for="(item,index) in typeList" :value="item.key" :key="(index+1)">{{item.val}}</Option>
            </Select>
          </Col>

          <Col span="3" :lg="3" :md="4">
            <Input v-model="param.traineeNameLike" @on-enter="getpagerList" clearable
                   size="large" placeholder="学员姓名"/>
          </Col>
          <Col span="3" :lg="3" :md="4">
            <Input v-model="param.serialnum" @on-enter="getpagerList"
                   size="large" clearable
                   placeholder="流水号"/>
          </Col>
          <Col span="4" :lg="4" :md="4">
            <Input v-model="param.idCardNoLike" @on-enter="getpagerList"
                   size="large" clearable
                   placeholder="证件号码"/>
          </Col>

          <Col span="5" :lg="5" :md="4">
            <DatePicker v-model="bmTime" split-panels type="daterange" :clearable="true" :editable="false"
                        @on-change="DatePickerC"
                        placement="bottom-end" placeholder="报名日期" style="width: 100% ;"></DatePicker>
          </Col>
          <Col align="center" span="3" :lg="3" :md="2">
            <Row type="flex" justify="space-between">
              <Col span="12">
                <Button type="primary" @click="getpagerList">
                  <Icon type="md-search"></Icon>
                  <!--查询-->
                </Button>
              </Col>
              <Col span="12">
                <Tooltip content="导出Excel" :transfer="tran" placement="top">
                  <Button type="primary" @click="excel">
                    <Icon type="ios-cloud-download-outline"/>
                  </Button>
                </Tooltip>
              </Col>
            </Row>
          </Col>

        </Row>
      </Col>


    </Row>
    <div class="box_col_100">
      <Table border stripe
             size="small"
             @on-row-click="rowClick"
             :height="AF.getPageHeight()-tabHeight"
             :columns="tabTit" :data="tableData"></Table>
      <div style="overflow: auto">
        <div style="font-size: 16px;font-weight: 600;margin-top: 8px;min-width: 1400px">
          <div style="overflow: auto">
            <div class="box_row_z">
              <div v-for="(val,key) of AddMess[0]" style="min-width: 150px;height: 40px;line-height: 50px;margin-right: 16px">
                {{key}}
                <Tag color="green" style="font-size: 18px">{{val}}</Tag>
              </div>
            </div>
            <div class="box_row_z">
              <div v-for="(val,key) of AddMess[1]" style="min-width: 150px;height: 40px;line-height: 50px;margin-right: 16px">
                {{key}}
                <Tag color="red" style="font-size: 18px">{{val}}</Tag>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="box_row rowRight" style="padding-top: 16px;margin-right: 12px">
        <Page :total=total
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
    </div>
    <component :is="compName" :mess="itemMess"></component>
  </div>
</template>

<script>
  import expandRow from './comp/expand'
  import http from '@/axios/index';
  import pagPrint from '../../../components/print/printCTyp'

  export default {
    name: "index",
    components: {
      expandRow, pagPrint
    },
    props: {
      tabHeight: {
        type: Number,
        default: 370
      }
    },
    data() {
      return {
        tran: true,
        sfList: [],
        sfListCode: 'ZDCLK1001',
        sfListCode1: 'ZDCLK1003',
        compName: '',
        itemMess: {},
        AddMess: [],
        tabTit: [
          {
            type: 'index2', align: 'center', minWidth: 80, fixed: 'left',
            render: (h, params) => {
              return h('span', params.index + (this.param.pageNum - 1) * this.param.pageSize + 1);
            }
          },
          {
            title: '姓名',
            key: 'traineeName',
            align: "center",
            minWidth: 120,
            fixed: 'left'
          },{
            title: '票据编号',
            key: 'pjbh',
            align: "center",
            minWidth: 130,
            fixed: 'left',
            render:(h,p)=>{
              return h('div',
                p.row.pjbh.substring(0,13)
              )
            }
          },
          {
            title: '驾校',
            key: 'chargeSource',
            align: "center",
            minWidth: 180,
          },
          {
            title: "费用类型",
            align: "center",
            key: 'chargeName',
            minWidth: 120
          },
          {
            title: '收支记录',
            key: 'inOutType',
            align: "center",
            minWidth: 160,
            sortable: true,
            render: (h, p) => {
              return h('div', [
                h('Tooltip', {
                  props: {
                    content: p.row.inOutType == '00'?'收入':'支出',
                    placement: "left"
                  }
                }, [
                  h('Tag', {
                    props: {
                      color: p.row.inOutType == '00' ? 'green' : 'red'
                    }
                  }, p.row.inOutType == '00' ? ('收:' + p.row.chargeFee) : ('支:' + p.row.chargeFee))
                ]),
                h('Tooltip', {
                  props: {
                    content: '退费撤回',
                    placement: "top"
                  }
                }, [
                  h('Button',{
                    props:{type:'error',size:'small'},
                    style:{display:p.row.chargeCodeIn == '0003'?'':'none'},
                    on:{
                      click:()=>{
                        this.payRemove(p.row.traineeId)
                      }
                    }
                  },'撤')
                ]),
              ])
            }
          },
          {
            title: '流水号',
            key: 'serialNum',
            minWidth: 180,
            align: "center"
          },
          {
            title: '证件号码',
            key: 'idCardNo',
            minWidth: 180,
            align: "center"
          },

          {
            title: '收费时间',
            align: 'center',
            minWidth: 180,
            render: (h, p) => {
              return h('div',
                p.row.cjsj.substring(0, 10)
              )
            }
          },
          {
            title: '操作人',
            key: 'cjr',
            minWidth: 180,
            align: 'center'
          },
          {
            title: '详  情',
            type: 'expand',
            fixed: 'right',
            width: 100,
            render: (h, params) => {
              return h(expandRow, {
                props: {
                  row: params.row
                },
                on: {
                  print: (id) => {
                    var v = this
                    v.AF.idPrintMess(id, (res) => {
                      v.itemMess = res
                      v.compName = 'pagPrint'
                    })
                  }
                }
              })
            }
          },
        ],
        tableData: [],
        total: 0,//总数量
        bmTime: [],
        chargeCodeIn:'',
        param: {
          // inOutType:'11',
          traineeNameLike: '',
          idCardNoLike: '',
          // chargeSourceLike: '',//驾校名称
          chargeCodeIn: '',
          // cjsjLike:'',
          cjsjGte: '',//开始时间
          cjsjLte: '',//结束时间
          //分页数据
          pageNum: 1,//当前页码
          pageSize: 20//每页显示数
        },
        typeList: [],
        typeCode: 'ZDCLK1024',
      }
    },
    watch: {
      'param.traineeNameLike': function (n, o) {
        this.fineSup(n)
      },
      'param.idCardNoLike': function (n, o) {
        this.fineSup(n)
      },
      // 'param.chargeCode':function(n,o){
      //   this.fineSup(n)
      // }
    },
    created() {
      if (sessionStorage.getItem(this.$route.name + 'charge-find')) {
        this.param = this.AF.getItem(this.$route.name + 'charge-find')
        this.bmTime[0] = this.param.cjsjGte.substring(0, 10)
        this.bmTime[1] = this.param.cjsjLte.substring(0, 10)
      } else {
        this.bmTime = [this.AF.trimDate(), this.AF.trimDate()];
      }
      this.DatePickerC([this.bmTime[0], this.bmTime[1]]);
      this.chargeCodeIn='';
      this.param.chargeCodeIn='';
      this.getPagerList();
      this.getTypeList();
    },
    computed: {
      AutoReadCard() {
        if (window.vueObject.$data.card.CardNo) {
          this.param.idCardNoLike = window.vueObject.$data.card.CardNo;
          this.param.nameLike = window.vueObject.$data.card.NameA;
          this.getPagerList();
        }

        return "";
      }
    },
    methods: {
      payRemove(id){
        this.swal({
          title:'退学撤回？！！',
          type:'warning',
          confirmButtonText: '撤回',
          cancelButtonText: '取消',
          showCancelButton: true,
        }).then((res) => {
          if (res.value) {
            this.$http.post('/api/traineeinformation/dropOutRevert',{id:id}).then(res=>{
              if(res.code == 200){
                this.swal({title:res.message,type:'success'})
                this.getPagerList();
              }
            }).catch(err=>{})
          }
        })
      },
      rowClick(mess, index) {
        this.$emit('idCardNo', mess.idCardNo)
      },
      fineSup(n) {
        if (n != '') {
          this.bmTime = []
          this.chargeCodeIn = ''
          this.DatePickerC(['', ''])
        }
        if (this.param.traineeNameLike == '' &&
          this.param.idCardNoLike == '' &&
          this.chargeCodeIn == '') {
          this.bmTime = [this.AF.trimDate(), this.AF.trimDate()];
          this.DatePickerC([this.bmTime[0], this.bmTime[1]]);
        }

      },
      DatePickerC(data) {
        this.param.cjsjGte = data[0]
        this.param.cjsjLte = data[1]
        this.param.pageNum = 1
        this.getPagerList()
      },
      getPagerList() {
        this.param.chargeCodeIn=(this.chargeCodeIn).toString()
        var v = this
        if (this.param.cjsjGte.length == 10) {
          this.param.cjsjGte = this.param.cjsjGte + ' 00:00:00'
        }
        if (this.param.cjsjLte.length == 10) {
          this.param.cjsjLte = this.param.cjsjLte + ' 23:59:59'
        }
        // this.param.pageNum = 1;
        let par = JSON.parse(JSON.stringify(this.param))
        this.$http.post('/api/chargemanagement/findTodayCharge', par).then((res) => {
          if (res.code == 200) {
            v.tableData = res.page.list
            v.total = res.page.total
            v.AddMess = res.result
            v.AF.setItem(v.$route.name + 'charge-find', v.param)
          }
        }).catch((err) => {

        })
      },
       getpagerList() {
        this.param.pageNum = 1;
        this.getPagerList();
      },
      pageChange(n) {
        this.param.pageNum = n
        this.getPagerList()
      },
      pageSizeChange(n) {
        this.param.pageSize = n
        this.getPagerList()
      },
      excel() {
        this.param.chargeCodeIn=(this.chargeCodeIn).toString()
        window.open(http.url + this.apis.EXCEL.CHARGE +
          "?traineeNameLike=" + this.param.traineeNameLike +
          "&idCardNoLike=" + this.param.idCardNoLike +
          "&chargeCode=" + this.param.chargeCodeIn +
          // "&chargeSourceLike=" + this.param.chargeSourceLike +
          "&cjsjGte=" + this.param.cjsjGte +
          "&cjsjLte=" + this.param.cjsjLte +
          "&pageSize=" + this.param.pageSize + "&pageNum=" + this.param.pageNum, "_blank");
      },
      getTypeList() {
        this.typeList = this.dictUtil.getByCode(this, this.typeCode);
      }
    }
  }
</script>

<style scoped>

</style>
