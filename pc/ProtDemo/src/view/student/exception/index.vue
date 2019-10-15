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
            <Select v-model="param.code" @on-change="changeExpCode" clearable multiple>
              <Option v-for="item in expsConfig" :value="item.code" :key="item.code">{{ item.bz }}</Option>
            </Select>
          </Col>
          <Col span="4" style="padding-left: 10px">
            <Select v-model="param.kskm" @on-change="changeExpCode" clearable>
              <Option :value="1" :key="1">科目一</Option>
              <Option :value="2" :key="2">科目二</Option>
              <Option :value="3" :key="3">科目三</Option>
              <Option :value="4" :key="4">科目四</Option>
            </Select>
          </Col>
          <Col span="4" style="padding-left: 10px">
            <Select v-model="param.zt">
              <Option value="00">未处理</Option>
              <Option value="10">已处理</Option>
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

  import Cookies from 'js-cookie'
  export default {
    name: "index",
    components:{studentCardList},
    data() {
      return {
        iconName: 'ios-search',
        iconColor: '#b3b3b3',
        message: '',
        expsConfig:[],
        param: {
          sfzmhm: '',
          xmLike:'',
          code:'',
          kskm:'',
          zt:'00',
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
          {title: '证件号码', width: 200, key: 'sfzmhm', align: 'center'},
          {title: '异常时间', width: 180, key: 'cjsj', align: 'center'},
          {title: '异常状态', width: 120, key: 'zt', align: 'center',
            render: (h, params) => {
              return h('span', params.row.zt == '00' ? '未处理' : '已处理');
            }},
          {title: '异常描述', key: 'bz', align: 'center'}
        ]
      }
    },
    created() {
      this.loadConfig();
    },
    methods: {
      pageChange(n) {
        // this.util.pageChange(this, n);
        this.param.pageNum = e
        this.queryInfo()
      },
      pageSizeChange(n) {
        // this.util.pageSizeChange(this, n);
        Cookies.set("pageSize",n);
        this.param.pageSize = n;
        this.queryInfo()
      },
      loadConfig(){
        this.$http.get('/api/exception/loadConfig').then(res => {
          if (res.code == 200) {
            this.expsConfig = res.result;
          }
        }).catch(err => {
        }).then(()=>{
          if (this.$route.query.code){
            sessionStorage.setItem("queryExpCode", this.$route.query.code);
            this.param.code = this.$route.query.code.split(",");
          } else {
            var code = sessionStorage.getItem("queryExpCode");
            if (code){
              this.param.code = code.split(",");
            }
          }
          if (this.$route.query.kskm){
            sessionStorage.setItem("queryExpKskm", this.$route.query.kskm);
            this.param.kskm = this.$route.query.kskm;
          } else {
            var kskm = sessionStorage.getItem("queryExpKskm");
            if (kskm){
              this.param.kskm = kskm;
            }
          }

          this.queryInfo();
        })
      },
      changeExpCode(val){
        sessionStorage.removeItem("queryExpCode");
        sessionStorage.removeItem("queryExpKskm");
      },
      queryInfo() {
          this.$http.post('/api/exception/pager', this.param).then(res => {
            if (res.code == 200) {
                this.pageData = res.page.list;
                this.param.total=res.page.total

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
