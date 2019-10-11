<template>
  <div class="box_col">
    <Row>
      <Col span="24">
        <pager-tit title="异常查询"></pager-tit>
      </Col>
      <Col span="24">
        <Row type="flex" align="bottom" style="padding:20px 0 10px 0;">
          <Col span="4" style="padding-left: 10px">
            <Input type="text" placeholder="证件号码" size="large" clearable
                   @on-enter="queryInfo"
                   v-model="param.sfzmhm"/>
          </Col>
          <Col span="4" style="padding-left: 10px">
            <Input type="text" placeholder="姓名" size="large" clearable
                   @on-enter="queryInfo"
                   v-model="param.xmLike"/>
          </Col>
          <Col span="4" style="padding-left: 10px">
            <Select v-model="param.code">

            </Select>
          </Col>
          <Col span="2" style="padding-left: 10px">
            <Button type="primary" @click="queryInfo" size="large">
              <Icon type="md-search" style="font-size: 24px"></Icon>
            </Button>
          </Col>
        </Row>
      </Col>
    </Row>
    <Row>
      <Col span="24">
      <Table
        :height="AF.getPageHeight()-300"
        size="large"
        stripe
        :columns="tabTit"
        :data="pageData">
      </Table>
      </Col>
    </Row>
    <div style="text-align: right;padding: 8px 0">
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
  </div>
</template>

<script>
  import studentCardList from '@/components/GetStudentM'
  export default {
    name: "index",
    components:{studentCardList},
    data() {
      return {
        headImg: null,
        userMsg: '',
        orgTree: [],//机构
        CascaderData: [],
        iconName: 'ios-search',
        iconColor: '#b3b3b3',
        message: '',
        stuMes: {},
        money: '',
        param: {
          sfzmhm: '',
          xmLike:'',
          code:'',
          pageNum: 1,//当前页码
          pageSize: 30//每页显示数
        },
        pageData: [],
        dictList: {
          carType: {
            code: 'ZDCLK0040',
            data: []
          },
          bmd: {
            code: '',
            data: []
          },
        },
        tabTit: [
          {
            type: 'index2', align: 'center', width: 80,
            render: (h, params) => {
              return h('span', params.index + (this.param.pageNum - 1) * this.param.pageSize + 1);
            }
          },
          {title: '姓名', key: 'xm', align: 'center', width: 120},
          {title: '证件号码', width: 180, key: 'sfzmhm', align: 'center'},
          {title: '异常时间', width: 180, key: 'cjsj', align: 'center'},
          {title: '异常描述', key: 'bz', align: 'center'}
        ]
      }
    },
    created() {
        console.log(this.$route.query.code);
    },
    methods: {
      pageChange(n) {
        this.util.pageChange(this, n);
      },
      pageSizeChange(n) {
        this.util.pageSizeChange(this, n);
      },
      queryInfo() {
        if (this.money == '') {
          this.swal({
            text: '请输入退费金额,最少退费0元',
            type: 'warning'
          });
        } else {
          this.$http.post('/api/exception/pager', this.param).then(res => {
            if (res.code == 200) {
              this.$Message.success(res.message);
              this.swal({
                title: res.message,
                type: 'success'
              })
              this.stuMes = {}
              this.param.idCardNo = ''
            } else {
              this.swal({
                text: res.message,
                type: 'error'
              });
            }
          }).catch(err => {
            this.swal({
              text: err.message,
              type: 'error'
            });
          })
        }
      }
    }
  }
</script>

<style lang="less">
  .iconSty {
    cursor: pointer;
    position: absolute;
    top: 40%;
    left: 50%;
    transform: translate(-50%, -50%);
  }
</style>
