<template>
  <div class="box_col">
    <Row>
      <Col span="2">
        <pager-tit title="考试确认"></pager-tit>
      </Col>
      <Col span="8">
      <Tag type="dot" :color="TagDot == index ? 'primary' : 'default'"
           style="cursor: pointer"
           @click.native="kmCheck(index,item)"
           v-for="(item,index) in [{val:'科目一',key:'10'},{val:'科目二',key:'20'},{val:'科目三',key:'30'},{val:'科目四',key:'40'}]">{{item.val}}
      </Tag>
      </Col>
    </Row>
    <Row style="padding-top: 20px">
      <Col span="2" style="padding-right: 20px;cursor: pointer">
      <Button size="large" icon="ios-download-outline" type="primary" @click="downloadTemplate">模板下载</Button>
      <!--<Card dis-hover style="text-align:center;width:150px;height:148px" @click.native="downloadTemplate">
        <div style="text-align:center">
          <img src="@/assets/images/excel.jpg" style="width:100%">
        </div>
        <h3 style="margin-top: 10px;padding-bottom: 10px">模板下载</h3>
      </Card>-->
      </Col>

      <Col span="3">
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
        :action="uploadUrl">
        <p style="color:#464c5b;font-size: 16px;height: 32px;text-align: center;line-height: 32px">上传Excel文件</p>
      </Upload>
      </Col>
      <Col :span="result.code == 200 ? '9' : '4' ">
        <Row>
          <Col span="9">
          <ButtonGroup v-if="result.code == 200">
            <Button>成功：{{ result.success }}</Button>
            <Button type="info" @click="exportExcel">导出Excel</Button>
          </ButtonGroup>
          <Button v-else>
            成功：{{ result.success }}
          </Button>
          </Col>
          <Col span="9" style="padding-left: 20px;">
          <ButtonGroup v-if="result.code == 200">
            <Button>失败：{{ result.error }}</Button>
            <Button type="error" @click="exportExcelerr">导出Excel</Button>
          </ButtonGroup>
          <Button v-else>
            失败：{{ result.error }}
          </Button>
          </Col>
        </Row>
        </Col>
        <Col span="2" >
        <Button type="primary" @click="showList">历史导入</Button>
        </Col>
      </Row>
      <Row :gutter="10" style="margin-bottom: 8px">
        <Col span="3" :lg="3" :md="4">
        <div style="width: 100%">
          <Select v-model="param.jgdm"
                  clearable
                  @on-change="CasChange">
            <Option v-for="item in CascaderList" :value="item.value" :key="item.value">{{ item.label }}</Option>
          </Select>
        </div>
        </Col>
        <Col span="4" :lg="4" :md="5">
        <div v-if="TagDot==0">
          <DatePicker v-model="param.firSubTestTimeLike" split-panels format="yyyy-MM-dd"
                      type="date" placeholder="考试时间"
                      @on-change="param.pageNum = 1;getPagerList()"></DatePicker>
        </div>
        <div v-else-if="TagDot==1">
          <DatePicker v-model="param.secSubTestTimeLike" split-panels format="yyyy-MM-dd"
                      type="date" placeholder="考试时间"
                      @on-change="param.pageNum = 1;getPagerList()"></DatePicker>
        </div>
        <div v-else-if="TagDot==2">
          <DatePicker v-model="param.thirdSubTestTimeLike" split-panels format="yyyy-MM-dd"
                      type="date" placeholder="考试时间"
                      @on-change="param.pageNum = 1;getPagerList()"></DatePicker>
        </div>
        <div v-else-if="TagDot==3">
          <DatePicker v-model="param.forthSubTestTimeLike" split-panels format="yyyy-MM-dd"
                      type="date" placeholder="考试时间"
                      @on-change="param.pageNum = 1;getPagerList()"></DatePicker>
        </div>
        </Col>
        <Col span="4" :lg="4" :md="5">
          <div>
            <Input v-model="param.idCardNoLike"
                   @on-enter="param.pageNum = 1;getPagerList()"
                   placeholder="证件号码"/>
          </div>
        </Col>
        <Col span="4" :lg="4" :md="5">
          <div>
            <Input v-model="param.nameLike"
                   @on-enter="param.pageNum = 1;getPagerList()"
                   placeholder="姓名"/>
          </div>
        </Col>
        <Col span="2" :lg="2" :md="3">
        <Button type="primary" @click="param.pageNum = 1;getPagerList()">
          <Icon type="md-search"></Icon>
          <!--查询-->
        </Button>
        </Col>
      </Row>
      </Col>
    </Row>

    <Row style="padding-top: 20px">
      <Col span="24">
        <Table
          :height="AF.getPageHeight()-320"
          size="large"
          stripe
          border
          no-data-text="暂无成绩待确认学员"
          :columns="tabTit"
          :data="pageData">
        </Table>
        <div style="text-align: right">
          <Page :total=total
                :current=param.pageNum
                :page-size=param.pageSize
                :page-size-opts=[8,10,20,30,40,50]
                v-show="pageShow"
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
        TagDot:0,
        selectKm:'10',
        CascaderList: [],
        pageShow:true,
        result:{
          errorKey: '',
          code:-1,
          success:0,
          error:0,
          key:''
        },
        param:{
          statu: '',
          kskm:'1',
          idCardNoLike:'',
          nameLike:'',
          jgdm:'',
          firSubTestTimeLike:'',
          secSubTestTimeLike:'',
          thirdSubTestTimeLike:'',
          forthSubTestTimeLike:'',
          pageSize: 8,
          pageNum: 1
        },
        tabTit: [
          {
            type: 'index2', align: 'center', width: 80,
            render: (h, p) => {
              return h('span', p.index + (this.param.pageNum - 1) * this.param.pageSize + 1);
            }
          },
          {
            title: '姓名',
            align: 'center',
            minWidth: 120,
            key: 'name'
          },
          {
            title: '证件号码',
            align: 'center',
            minWidth: 180,
            key: 'idCardNo'
          },
          {
            title: '流水号',
            align: 'center',
            minWidth: 180,
            key: 'serialNum'
          },
          {
            title: '车型',
            key: 'carType',
            minWidth: 120,
            align: 'center',
            render: (h, p) => {
              return h('div', [
                h('Tooltip', {
                  props: {
                    placement: 'top',
                    content: p.row.arrearage == '10' ? '欠费' : '未欠费'
                  }
                }, [
                  h('Tag', {
                    props: {
                      type: 'dot',
                      color: p.row.arrearage == '10' ? 'error' : 'success'
                    }
                  }, p.row.carType)
                ])
              ])
            }
          },
          {
            title: '报名点',
            align: 'center',
            minWidth: 120,
            key: 'jgmc',
            render: (h, params) => {
              let jgmcArray = params.row.jgmc.split("/");
              let res = "";
              if (jgmcArray.length == 2 || jgmcArray.length == 1) {
                return h('div', params.row.jgmc);
              } else if (jgmcArray.length == 3) {
                return h('div', jgmcArray[1] + ("/" + jgmcArray[2]))
              }
            }
          },
          {
            title: '考试地点',
            minWidth: 100,
            align: 'center',
            render: (h, p) => {
              let arr=p.row.testInfos;
              let place=''
              let km=this.selectKm=='10' ? p.row.firSubTestTime : this.selectKm=='20' ? p.row.secSubTestTime : this.selectKm=='30' ? p.row.thirdSubTestTime : p.row.forthSubTestTime
              arr.map((val,index,arr)=>{
                if(val.testTime===km){
                  place=val.testPlace
                }
              })
              return h('div', place)
            }
          }, {
            title: '考试时间',
            minWidth: 120,
            align: 'center',
            render: (h, p) => {
              let a = ''
              switch (this.selectKm) {
                case '10':
                  a = p.row.firSubTestTime
                  break;
                case '20':
                  a = p.row.secSubTestTime
                  break;
                case '30':
                  a = p.row.thirdSubTestTime
                  break;
                case '40':
                  a = p.row.forthSubTestTime
                  break;
              }
              return h('div', a)
            }
          },
          {title: '导入结果', width:120, key: 'message',
            render: (h, p) => {
              let a = p.row.message;
              if (a == '待上传'){
                a = "-";
              }
              return h('div', a)
            },filters: [
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
          },
          {title: '操作', width:160, fixed:'right',
            render: (h, p) => {
              return h('div', [
                h('Button', {
                  props: {
                    type: 'warning',
                    size: 'small'
                  },
                  style: {margin: '0 8px 0 0'},
                  on: {
                    click: () => {
                      this.revoke(p.row)
                    }
                  }
                }, '撤'),
                h('Button', {
                  props: {
                    type: 'primary',
                    size: 'small'
                  },
                  style: {margin: '0 8px 0 0'},
                  on: {
                    click: () => {
                      this.updateResult(p.row, '00')
                    }
                  }
                }, '合'),
                h('Button', {
                  props: {
                    type: 'error',
                    size: 'small'
                  },
                  style: {margin: '0 8px 0 0'},
                  on: {
                    click: () => {
                      this.updateResult(p.row, '10')
                    }
                  }
                }, '不')
              ])
            }
          }
        ]
      }
    },
    created(){
      this.accessToken = JSON.parse(Cookies.get('accessToken'));
      this.getBmdList();
      this.getPagerList();
      // this.pageData = this.$store.state.app.examination
    },
    methods: {
      updateResult(row, result){
        this.swal({
          text: "确认手工补录成功为"+ (result == '00' ? '合格' : '不合格')+"?",
          type: "warning",
          showCancelButton: true,
          confirmButtonText: '确认',
          cancelButtonText: '取消'
        }).then((isConfirm) => {
          if (isConfirm.value) {
            var time = '';
            var kskm = '1';
            if (this.selectKm == '10'){
              time = row.firSubTestTime;
            }else if (this.selectKm == '20'){
              time = row.secSubTestTime;
              kskm = '2';
            }else if (this.selectKm == '30'){
              time = row.thirdSubTestTime;
              kskm = '3';
            }else if (this.selectKm == '40'){
              time = row.forthSubTestTime;
              kskm = '4';
            }

            var opt = {
              kskm:kskm,
              id:row.id,
              result:result,
              time:time
            };

            this.$http.post('/api/traineeinformation/updateTestResult',opt).then((res)=>{
                if (res.code == 200){
                  this.swal({
                    text: '操作完成',
                    type: 'success',
                    confirmButtonText: '关闭'
                  });
                  this.getPagerList();
                }else{
                  this.swal({
                    text: res.message,
                    type: 'warning',
                    confirmButtonText: '关闭'
                  })
                }
            })
          }
        });
      },
      revoke(row){
        this.swal({
          text: "确认撤回约考记录?",
          type: "warning",
          showCancelButton: true,
          confirmButtonText: '确认',
          cancelButtonText: '取消'
        }).then((isConfirm) => {
          if (isConfirm.value) {
            var time = '';
            var kskm = '1';
            if (this.selectKm == '10'){
              time = row.firSubTestTime;
            }else if (this.selectKm == '20'){
              time = row.secSubTestTime;
              kskm = '2';
            }else if (this.selectKm == '30'){
              time = row.thirdSubTestTime;
              kskm = '3';
            }else if (this.selectKm == '40'){
              time = row.forthSubTestTime;
              kskm = '4';
            }

            var opt = {
              kskm:kskm,
              id:row.id,
              time:time
            };

            this.$http.post('/api/traineeinformation/revokeAppoint',opt).then((res)=>{
              if (res.code == 200){
                this.swal({
                  text: '操作完成',
                  type: 'success',
                  confirmButtonText: '关闭'
                });
                this.getPagerList();
              }else{
                this.swal({
                  text: res.message,
                  type: 'warning',
                  confirmButtonText: '关闭'
                })
              }
            })
          }
        });
      },
      getBmdList() {
        this.$http.get(this.apis.FRAMEWORK.getCurrentOrgTree, {timers: new Date().getTime()}).then((res) => {
          if (res.code === 200) {
            /*function tree(arr) {
             if (arr[0].value.length == 6) {
             return arr[0].children
             } else if (arr[0].value.length == 3) {
             return arr[0].children[0].children
             }
             }
             this.CascaderList = tree(res.result)*/
            if (res.result[0].value.length == 3) {
              this.CascaderList = res.result[0].children[0].children;
            } else if (res.result[0].value.length == 6) {
              this.CascaderList = res.result[0].children;
            } else if (res.result[0].value.length == 9) {
              this.CascaderList = res.result
            }
          }
        }).catch((error) => {
        })
      },
      CasChange(val) {
        this.param.pageNum = 1;
        this.getPagerList()
      },
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
        this.pageShow = true;
        if (this.TagDot == 0 && this.param.firSubTestTimeLike != '') {
          this.param.firSubTestTimeLike = this.AF.trimDate(this.param.firSubTestTimeLike)
        } else if (this.TagDot == 1 && this.param.secSubTestTimeLike != '') {
          this.param.secSubTestTimeLike = this.AF.trimDate(this.param.secSubTestTimeLike)
        } else if (this.TagDot == 2 && this.param.thirdSubTestTimeLike != '') {
          this.param.thirdSubTestTimeLike = this.AF.trimDate(this.param.thirdSubTestTimeLike)
        }  else if (this.TagDot == 3 && this.param.forthSubTestTimeLike != '') {
          this.param.forthSubTestTimeLike = this.AF.trimDate(this.param.forthSubTestTimeLike)
        } else {
          this.param.firSubTestTimeLike = '';
          this.param.secSubTestTimeLike = '';
          this.param.thirdSubTestTimeLike = '';
          this.param.forthSubTestTimeLike = '';
        }

        this.$http.post('/api/traineeinformation/getTestStudents',this.param).then((res)=>{
          if(res.code===200){
            res.page.list.forEach((item,index)=>{
              if(item.status === '10'){
                item.subject = '科目一';
              }else if(item.status === '20'){
                item.subject = '科目二';
              }else if(item.status === '30'){
                item.subject = '科目三';
              }else if(item.status === '40'){
                item.subject = '科目四';
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
          this.pageShow = false;
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
      },
      kmCheck(index, item) {
        this.TagDot = index;
        this.param.pageNum = 1;
        if (index == 0) {
          this.param.kskm = '1';
          this.selectKm = "10";
        } else if (index == 1) {
          this.param.kskm = '2';
          this.selectKm = "20";
        } else if (index == 2) {
          this.param.kskm = '3';
          this.selectKm = "30";
        } else if (index == 3) {
          this.param.kskm = '4';
          this.selectKm = "40";
        }

        this.getPagerList();
      }
    }
  }
</script>

<style lang="less">
  .count-style{
    font-size: 50px;
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
