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
                   @on-enter="getPagerList"
                   v-model="param.sfzmhm"/>
          </Col>
          <Col span="4" style="padding-left: 10px">
            <Input type="text" placeholder="姓名" size="large" clearable
                   @on-enter="getPagerList"
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
          <Col span="1" style="padding-left: 10px">
            <Button type="primary" @click="param.pageNum = 1,getPagerList()" size="large">
              <Icon type="md-search" style="font-size: 24px"></Icon>
            </Button>
          </Col>
          <Col span="2" style="padding-left: 10px">
            <Button type="primary" @click="downloadExcel" size="large">
              <Icon type="ios-cloud-download"  style="font-size: 24px"/>
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
    <component
      :is="compName"
      :selectRow="selectRow"
    >
    </component>
  </div>
</template>

<script>
  import Cookies from 'js-cookie'
  import http from '@/axios/index';
  import detail from '../Student-query/comp/detail'

  export default {
    name: "index",
    components: {
      detail
    },
    data() {
      return {
        iconName: 'ios-search',
        iconColor: '#b3b3b3',
        pagerUrl: '/api/exception/pager',
        compName: '',
        selectRow: {},
        message: '',
        expsConfig:[],
        param: {
          sfzmhm: '',
          xmLike:'',
          code:'',
          kskm:'',
          zt:'00',
          bz2:'',
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
          {title: '车型', width: 100, key: 'zjcx'},
          {title: '报名点', width: 150, key: 'bz1'},
          {title: '报名时间', width: 120, key: 'bmsj',
            render: (h, params) => {
              return h('span', params.row.bmsj.substring(0, 10));
            }},
          {title: '异常时间', width: 180, key: 'cjsj', align: 'center'},
          {title: '异常描述', key: 'bz', align: 'center'},
          {title: '详情', width: 80, align: 'center', render: (h, params) => {
            if (params.row.xyid == null || params.row.xyid == ''){
              return h('span', "-");
            }
            return h('div', [
              h('Button', {
                props: {
                  icon: 'md-clipboard',
                  type: 'text',
                  ghost: true,
                  shape: "circle",

                },
                style: {
                  fontSize: '24px',
                  color: '#2db7f5'
                },
                on: {
                  click: () => {
                    this.$http.get('/api/traineeinformation/'+params.row.xyid).then(res => {
                      if (res.code == 200) {
                        this.selectRow = res.result;
                        this.compName = 'detail';
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
                    });
                  }
                }
              })
            ]);
          }}
        ]
      }
    },
    created() {
      this.loadConfig();
    },
    methods: {
      pageChange(n) {
        this.util.pageChange(this, n);
      },
      pageSizeChange(n) {
        this.util.pageSizeChange(this, n);
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

          this.getPagerList();
        })
      },
      changeExpCode(val){
        sessionStorage.removeItem("queryExpCode");
        sessionStorage.removeItem("queryExpKskm");
      },
      getPagerList() {
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
      },
      downloadExcel(){
        window.open(http.url + '/api/exception/export' +
          "?sfzmhm=" + this.param.sfzmhm +
          "&userid=" + JSON.parse(sessionStorage.getItem('userInfo')).yhid +
          "&token=" + JSON.parse(Cookies.get('accessToken')).token +
          "&code=" + this.param.code +
          "&kskm=" + this.param.kskm +
          "&zt=" + this.param.zt +
          "&bz2=" + this.param.bz2
          , "_blank");
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
