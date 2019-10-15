<template>
  <div class="box_col">
    <Row>
      <Col span="24">
        <pager-tit title="考试导入"></pager-tit>
      </Col>
    </Row>
    <Row style="padding-top: 20px">
      <Col span="2" style="padding-right: 20px;cursor: pointer">
        <Card dis-hover style="text-align:center;width:150px;height:148px" @click.native="downloadTemplate">
          <div style="text-align:center">
            <img src="@/assets/images/excel.jpg" style="width:100%">
          </div>
          <h3 style="margin-top: 10px;padding-bottom: 10px">模板下载</h3>
        </Card>
      </Col>
      <Col span="14" offset="1">
        <Upload
          ref="upload"
          :max-size="2048"
          :headers="{'userid':accessToken.userId, 'token':accessToken.token}"
          :before-upload="(file)=>{pageData=[],this.handleSpinCustom()}"
          :on-success="(res, file,fileList)=>{successCallback(res, file, fileList)}"
          :on-error="errorCallback"
          :on-format-error="handleFormatError"
          :on-exceeded-size="handleMaxSize"
          :format="['xls']"
          type="drag"
          :action="uploadUrl">
          <div style="padding: 20px 0">
            <Icon type="ios-cloud-upload" size="52" style="color: #3399ff"></Icon>
            <p style="color:#464c5b;font-size: 16px">点击或拖动Excel文件进行上传导入</p>
            <p style="color:#9ea7b4;font-size: 12px;padding-top:10px">只能上传xls文件，文件不能大于2M</p>
          </div>
        </Upload>
      </Col>
      <Col span="6">
        <Row>
          <Col span="9" style="padding-left: 20px;">
            <Card dis-hover class="successCard" style="height: 148px;width: 100%">
              <p slot="title" style="height: 50px;padding-top: 10px;color:white;position: relative">
                <Icon type="md-checkmark-circle-outline" style="font-size: 36px;padding-left: 10px"/>
                <span style="font-size: 22px;padding-left: 8px">成功</span>
              </p>
              <div style="text-align: center">
                <count-to :end="result.success" count-class="count-style"/>
              </div>
              <div v-if="result.code == 200" style="z-index: 9999;position: absolute;right: -10px;top: -10px">
                <Button type="info" @click="exportExcel">
                  <div>
                    导出
                  </div>
                  <div>
                    Excel
                  </div>
                </Button>
              </div>
            </Card>
          </Col>
          <Col span="9" style="padding-left: 20px;">
            <Card class="failCard" dis-hover style="height: 148px;width: 100%;position: relative">
              <p slot="title" style="height: 50px;padding-top: 10px;color:white;">
                <Icon type="md-close-circle" style="font-size: 36px;padding-left: 10px" />
                <span style="font-size: 22px;padding-left: 8px">失败</span>
              </p>
              <div style="text-align: center">
                <count-to :end="result.error" count-class="count-style"/>
              </div>
              <div v-if="result.code == 200" style="z-index: 9999;position: absolute;right: -10px;top: -10px">
                <Button long type="error" @click="exportExcelerr" style="background-color: #fa541c;border-color: #fa541c">
                  <div>导出</div>
                  <div>Excel</div>
                </Button>
              </div>
            </Card>
          </Col>
          <Col span="6" >
            <Button type="primary" style="width: 100%;margin-left: 12px;margin-top: 110px" @click="showList">历史导入</Button>
          </Col>
        </Row>
        <!--<Row v-if="result.code == 200">-->
        <!--<Col span="24" style="padding-left: 20px;padding-top: 5px">-->
        <!--<Button long type="success" @click="exportExcel">导出Excel</Button>-->
        <!--</Col>-->
        <!--</Row>-->
      </Col>
    </Row>

    <Row style="padding-top: 20px">
      <Col span="24">
        <Table
          :height="AF.getPageHeight()-420"
          size="large"
          stripe
          border
          no-data-text="请先导入文件清单"
          :columns="tabTit"
          :data="pageData">
        </Table>
        <div style="text-align: right">
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
      </Col>
    </Row>
    <component :is="compName"></component>
  </div>
</template>

<script>
  import http from '@/axios/index';
  import Cookies from 'js-cookie';
  import CountTo from '_c/count-to'
  import FileList from './comp/FileList'

  export default {
    name: "index",
    components: {
      CountTo,FileList
    },
    data() {
      return {
        compName:'',
        v:this,
        total: 0,
        accessToken:{},
        uploadUrl:http.url + this.apis.TRAINETEST.IMPORT_RESULT,
        pageData:[],
        result:{
          errorKey: '',
          code:-1,
          success:0,
          error:0,
          key:''
        },
        param:{
          statu: '',
          pageSize: 8,
          pageNum: 1
        },
        tabTit: [
          {title: '#',type: 'index',minWidth: 60},
          {title: '姓名',key: 'name',width: 120},
          {title: '证件号码',key: 'idCardNo',width: 180},
          {title: '报名点',key: 'jgmc',width: 160,
          },
          { title: '科目',
            key: 'subject',
            width: 120,
            filters: [
              {
                label: '科目一',
                value: '10'
              },
              {
                label: '科目二',
                value: '20'
              },{
                label: '科目三',
                value: '30'
              }
            ],
            filterMultiple: false,
            filterRemote:(value, p,r)=> {
              this.param.statu = value[0];
              this.getPagerList();
              return true;
            },
            render: (h, p) => {
            let color = 'success';
            let msg = '初考';
            if (!p.row.subTestNums || p.row.success == 0){
              color = 'default';
              msg = '-';
            }else if (p.row.success == 1 && p.row.subTestNums > 1){
              color = 'error';
              msg = '第' + (p.row.subTestNums - 1) + '次补考';
            }

            return h('div', [
              p.row.subject,
              h('Tag', {
                props: {
                  color: color
                }
              }, msg)
            ]);
          }},
          {title: '培训',key: 'trainStatus',width: 100,render: (h, p) => {
            let color = 'success';
            let msg = '合格';
            if (!p.row.trainStatus){
              color = 'default';
              msg = '-';
            }else if (p.row.trainStatus == '10'){
              color = 'error';
              msg = '不合格';
            }

            return h('Tag', {
              props: {
                color: color
              }
            }, msg);
          }},
          {title: '车型',key: 'carModel',width: 80, render: (h, p) => {
            let carTypeStr = p.row.carModel.substring(0, 1);
            if (carTypeStr == 'C') {
              //小车
              return h('Tag', {
                props: {
                  color: 'green'
                },
                style: {
                  fontSize: '15px',
                }
              }, p.row.carModel);
            }
            //大车
            return h('Tag', {
              props: {
                color: 'volcano'
              },
              style: {
                fontSize: '15px',
              }
            }, p.row.carModel);
          }},
          {title: '考试日期',key: 'ykDate',width: 120},
          {title: '考试地点',key: 'examinationSite',width: 160},
          {title: '考试结果',key: 'testResults',width: 120, render: (h, p) => {
            let color = 'error';
            let msg = p.row.testResults;
            if (msg == '合格') {
              color = 'success';
            }
            //大车
            return h('Tag', {
              props: {
                color: color
              }
            }, msg);
          }},
          {title: '导入状态',key: 'success',width: 120, render:(h, p)=>{
            let color = 'success';
            let msg = '成功';
            if (p.row.success == '0'){
              color = 'error';
              msg = '失败'
            }else if(p.row.success =='-'){
              msg = '-';
              color = 'primary';
            }

            return h('Tag', {
              props: {
                color: color
              },
              style: {
                fontSize: '15px',
              }
            }, msg);
          }},
          {title: '导入结果',key: 'message',filters: [
            {
              label: '成功',
              value: 1
            },
            {
              label: '失败',
              value: 0
            }
          ],
            filterMultiple: false,
            filterMethod (value, row) {
              return row.success == value;
            }
          }
        ]
      }
    },
    created(){
      this.accessToken = JSON.parse(Cookies.get('accessToken'));
      this.getPagerList();
      // this.pageData = this.$store.state.app.examination
    },
    methods: {
      showList(){
        this.compName = 'FileList';
      },
      pageChange(n) {
        this.param.pageNum = n;
        this.getPagerList();
        // this.util.pageChange(this, n);
      },
      pageSizeChange(n) {
        this.param.pageSize = n;
        this.getPagerList();
        // this.util.pageSizeChange(this, n);
      },
      getPagerList(){
        this.pageData = [];
        this.$http.post('/api/traineeinformation/getAppointed',this.param).then((res)=>{
          if(res.code===200){
            res.page.list.forEach((item,index)=>{
              if(item.status === '10'){
                item.subject = '科目一';
              }else if(item.status === '20'){
                item.subject = '科目二';
              }else if(item.status === '30'){
                item.subject = '科目三';
              }
              item.trainStatus = '00';
              item.carModel = item.carType;
              item.ykDate = item.testInfo.testTime;
              item.examinationSite = item.testInfo.testPlace;
              item.message = '待上传';
              item.testResults = '待上传';
              item.success = '-';
              this.pageData.push(item);
            })
            this.total = res.page.total;
          }
        })
      },
      handleSpinCustom () {
        this.$Spin.show({
          render: (h) => {
            return h('div', [
              h('Icon', {
                'class': 'demo-spin-icon-load',
                props: {
                  type: 'ios-loading',
                  size: 30
                }
              }),
              h('div', '数据匹配中，请耐心等待')
            ])
          }
        });
      },
      downloadTemplate(){
        window.open('../成绩导入模板（有内容的为必填）.xls', '_blank');
      },
      exportExcel(){
        window.open(http.url + this.apis.TRAINETEST.EXPORT_URL + this.result.key, '_blank');
      },
      exportExcelerr(){
        window.open(http.url + this.apis.TRAINETEST.EXPORT_URL + this.result.errorKey, '_blank');
      },
      //文件上传成功后，回调该方法，进行后续处理
      successCallback(res, file, locDataName) {
        this.$Spin.hide();
        this.$refs.upload.clearFiles();
        this.result.code = res.code;
        if (res.code == 200) {
          this.result.key = res.result.key;
          this.result.errorKey = res.result.errorKey;
          this.result.error = res.result.errorCount;
          this.result.success = res.result.succeedCount;
          this.pageData = res.result.list;
          this.$store.commit('Gexamination',res.result.list)
        } else {
          this.$Message.error("文件上传失败：" + res.message);
        }
      },
      //文件上传成功后，回调该方法，进行后续处理
      errorCallback(res, file, locDataName) {
        this.$Spin.hide();
        this.$refs.upload.clearFiles();
        this.swal({
          text: '文件上传失败，请稍后重试！',
          type:'error'
        });
      },
      handleFormatError(file) {
        this.$Spin.hide();
        this.swal({
          text: '文件格式错误，仅支持 xls',
          type:'error'
        });
      },
      handleMaxSize(file) {
        this.$Spin.hide();
        this.swal({
          text: '文件不能超过2M',
          type:'error'
        });
      }
    }
  }
</script>

<style lang="less">
  .count-style{
    font-size: 26px;
  }

  .successCard{
  .ivu-card-head{
    border-bottom: 1px solid #e8eaec;
    padding: 0px;
    line-height: 1;
    background-color: #2d8cf0;
  }
  }

  .failCard{
  .ivu-card-head{
    border-bottom: 1px solid #e8eaec;
    padding: 0px;
    line-height: 1;
    background-color: #ed3f14;
  }
  }
</style>
