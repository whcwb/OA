<template>
  <div class="box_col">
    <pager-tit title="优惠申请"></pager-tit>
    <Form :label-width="20">
      <Row type="flex" justify="end">
        <Col span="4">
        <FormItem>
          <Cascader trigger="hover" v-model="param.bmd" :data="dictList.bmd.data"  placeholder="报名点选择"  filterable></Cascader>
        </FormItem>
        </Col>
        <Col span="4">
        <FormItem>
          <Select v-model="param.carType" placeholder="车型选择" filterable clearable>
            <Option v-for="item in dictList.carType.data" :value="item.key" :key="item.key">{{ item.val }}</Option>
          </Select>
        </FormItem>
        </Col>
        <Col span="4">
        <FormItem>
          <DatePicker v-model="param.bmTime" split-panels type="daterange" placement="bottom-end" placeholder="报名日期" style="width: 100% ;"></DatePicker>
        </FormItem>
        </Col>
        <Col span="4">
        <FormItem>
          <Input type="text" placeholder="学员姓名" v-model="param.nameLike"/>
        </FormItem>
        </Col>
        <Col span="2">
        <FormItem>
          <Button type="primary"  @click="v.util.getPageData(v)">
            <Icon type="md-search"></Icon>
          </Button>
        </FormItem>
        </Col>
      </Row>
    </Form>
    <div class="box_col_auto">
        <!--:loading="SpinShow"-->
      <Table
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
    <component
      :is="compName"
      :userAudit="userAudit"
    >
    </component>
  </div>
</template>

<script>
  import audit from './comp/audit'

  export default {
    name: "index",
    components: {audit},
    data(){
      return{
        compName: '',
        userAudit: {},
        tabTit: [
          {
            title: '姓名',
            key: 'name'
          },
          {
            title: '证件号码',
            key: 'idcard'
          },
          {
            title: '手机号码',
            key: 'phone'
          },
          {
            title: '报名时间',
            key: 'time'
          },
          {
            title:'车型',
            key:'carType'
          },
          {
            title:'是否分期',
            key:'sffq'
          },
          {
            title:'是否团购',
            key:'tgyh'
          // },{
          //   title:'是否成团',
          //   key:'tgsfct'
          },{
            title:'优惠名称',
            key:'yhName'
          },{
            title:'优惠金额(元)',
            key:'yhMoney'

          },{
            title: '操作',
            key: 'cz',
            align: 'center',
            render: (h, params) => {
              return h('div', [
                h('Button', {
                  props: {
                    type: 'info',
                    size: 'small',
                  },
                  style: {
                    marginRight: '5px'
                  },
                  on: {
                    click: () => {
                      this.compName='audit';
                      this.userAudit=params.row;
                    }
                  }
                }, '申请')
              ])
            }
          }
        ],
        tabData: [
          {
            name: '张小二',
            idcard: '000000000000000000',
            phone:'13112341234',
            sffq: '分期',
            tgyh:'-',
            tgsfct:'-',
            time:'2016-10-03',
            yhName:'-',
            yhMoney:0,
            carType:'C1'
          },
          {
            name: '张小二',
            idcard: '000000000000000000',
            phone:'13112341234',
            sffq: '未分期',
            tgyh:'三人成团优惠100元',
            tgsfct:'成团',
            time:'2016-10-03',yhName:'VIP学员优惠',yhMoney:100,
            carType:'A1'
          },
          {
            name: '张小二',
            idcard: '000000000000000000',
            phone:'13112341234',
            sffq: '分期',
            tgyh:'-',
            tgsfct:'-',
            time:'2016-10-03',
            yhName:'-',
            yhMoney:0,
            carType:'A1'
          },
          {
            name: '张小二',
            idcard: '000000000000000000',
            phone:'13112341234',
            sffq: '分期',
            tgyh:'二人成团',
            tgsfct:'未成团',
            time:'2016-10-03',
            yhName:'-',
            yhMoney:0,
            carType:'C1'
          },
        ]
        ,cxList:[{value: "C1",floor: "C1"},{value: "C2",floor: "C2"},{value: "C3",floor: "C3"}]
      }
    }
  }
</script>

<style scoped>

</style>
