<template>
  <div class="box_col">
    <Row>
      <Col span="24">
        <pager-tit title="待受理学员"></pager-tit>
      </Col>
    </Row>
    <div class="box_row">
      <div class="box_row_100">

        <Row style="padding-top: 20px">
          <Col span="2" style="padding-right: 20px;cursor: pointer">
            <Card dis-hover style="text-align:center;width:150px;height:148px" @click.native="downloadTemplate">
              <div style="text-align:center">
                <img src="@/assets/images/excel.jpg" style="width:100%">
              </div>
              <h3 style="margin-top: 10px;padding-bottom: 10px">模板下载</h3>
            </Card>
          </Col>
          <Col span="10" offset="1">
            <Upload
              ref="upload"
              :max-size="2048"
              :headers="{'userid':accessToken.userId, 'token':accessToken.token}"
              :before-upload="(file)=>{pageData=[]}"
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
          <Col span="4" style="padding-top: 110px;padding-left: 10px">
            <div style="width: 230px" >
              <Cascader @on-change="CasChange" @on-clear="CasChange" v-model="bmdT"
                        change-on-select :data="CascaderList"
                        placeholder="报名点"
                        trigger="hover"
                        clearable
                        filterable ></Cascader>
            </div>
          </Col>
          <Col span="4" style="padding-top: 110px;padding-left: 10px">
            <Input type="text"  ref="re" placeholder="证件号码" clearable @on-change="getPagerList" v-model="param.idCardNoLike"/>
          </Col>
          <Col span="2" style="padding-top: 110px;padding-left: 10px">
            <Button type="primary" @click="getPagerList">
              <Icon type="md-search"></Icon>
            </Button>
          </Col>
        </Row>



        <!--<Row :gutter="20" type="flex" justify="end">
          <Col span="8">
            <Input type="text"  ref="re" placeholder="证件号码" @on-change="getPagerList" v-model="param.idCardNoLike"/>
          </Col>
          <Col span="2">
            <Button type="primary" @click="v.util.getPageData(v)">
              <Icon type="md-search"></Icon>
            </Button>
          </Col>
        </Row>-->
      </div>
    </div>
    <div class="box_col_auto">
      <Table
        :height="AF.getPageHeight()-300"
        size="large"
        stripe
        :columns="tabTit"
        :data="pageData" ></Table>
    </div>
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
<!---->
    <component  :is="compName" :showModal="this.showModal" :acceptResult="acceptResult" :excelKey="this.result.sucKey" :errorKey="this.result.errorKey" :error="this.result.error" :success="this.result.success"></component>
  </div>
</template>

<script>
  import http from '@/axios/index';
  import Cookies from 'js-cookie';
  import result from './comp/result'

  export default {
    name: "index",
    components:{
      result
    },
    data() {
      return {
        showModal: false,
        acceptResult: [],
        compName: '',
        bmdT:[],
        accessToken:{},
        v: this,
        uploadUrl:http.url + this.apis.TRAINEE.ACCEPT_FILE,
        pagerUrl: this.apis.TRAINEE.ACCEPT_PAGER,
        total: 0,
        param: {
          jgdm:'', // 机构代码
          nameLike: '',//姓名
          idCardNoLike: '',
          bmd: [],//报名点代码
          carType: '',//车型。取字典：ZDCLK0040
          bmTime: '',//报名日期范围值
          //分页数据
          orderBy: 'registrationTime asc',
          pageNum: 1,//当前页码
          pageSize: 8//每页显示数
        },
        result:{
          error: 0,
          success: 0,
          key:'',
          sucKey:'',
          errorKey: ''

        },
        //table数据查询结果
        pageData: [],
        dictList: {
          carType: {
            code: 'ZDCLK0040',
            data: []
          }/*,
          bmd: {
            code: '',
            data: []
          }*/
        },
        tabTit: [
          {type: 'index', width: 60, align: 'center'},
          {title: '姓名',align:'center', key: 'name', width: 120},
          {title: '证件号码',align:'center', key: 'idCardNo', width: 200},
          {
            title: '性别', width: 80,align:'center', key: 'gender', render: (h, p) => {
              return h('div', p.row.gender == '10' ? '男' : '女');
            }
          },
          {
            title: '报名点',align:'center', key: 'jgmc', render: (h, params) => {
              let jgmcArray = params.row.jgmc.split("/");
              let res = "";
              if(jgmcArray.length == 2 || jgmcArray.length == 1){
                return h('div', params.row.jgmc);
              }else if(jgmcArray.length == 3){
                return h('div', jgmcArray[1]+("/"+jgmcArray[2]))
              }
              // let jgmcArray = params.row.jgmc.split("/");
              // let res = "";
              // if (jgmcArray.length > 1) {
              //   for (let i = 1; i < jgmcArray.length; i++) {
              //     res += jgmcArray[i];
              //     if ((i + 1) != jgmcArray.length) {
              //       res += '-';
              //     }
              //   }
              // } else {
              //   res = jgmcArray[0];
              // }
              // return h('div', res);
            }
          },
//          {title: '手机号码',align:'center', key: 'phone'},
          {
            title: '车型',align:'center', key: 'carType',
            render: (h, p) => {
              return h('Tag', {
                props: {
                  color: 'cyan'
                },
                style: {
                  fontSize: '15px',
                }
              }, p.row.carType);
            }
          },
          {
            title: '报名时间',align:'center', key: 'registrationTime',
            render: (h, params) => {
              return h('div', params.row.registrationTime.substr(0, 10));
            }
          },
          {
            title: '流水号',align:'center', width: 240, render: (h, p) => {
              return h('div', [
                h('Input', {
                  props: {
                    size: 'large',
                    placeholder: '在此输入流水号',
                    elementId: 'ss',
                    value: p.row.serialNum
                  },
                  on: {
                    'on-change': (event) => {
                      p.row.serialNum = event.srcElement.value;
                    }
                  }
                })
              ])
            }
          },
          {
            title: '受理',
            align: 'center',
            render: (h, params) => {
              return h('div', [
                h('Button', {
                  props: {type: 'warning', icon: 'md-checkmark-circle-outline', shape: 'circle', size: 'large'},
                  style: {margin: '0 8px 0 0'},
                  on: {
                    click: () => {
                      this.auditConfirm(params.row);
                    }
                  }
                })
              ])
            }
          }
        ],
        CascaderList:[]
      }
    },
    created() {
       this.getPagerList();
       this.accessToken = JSON.parse(Cookies.get('accessToken'));
       this.getDictList();
       this.getBmdList();
     /* setTimeout(()=>{
        this.fouc();
      },"500")*/

    },
    methods: {
      CasChange(val,lab){

        console.log(val);
        console.log(lab);
        if(val && val.length > 0){
          this.param.jgdm = val[0];
        }else{
          this.param.jgdm = '';
        }

        this.getPagerList();

      },
      getPagerList() {
        // debugger;
        // let that = this.$router;
        this.$http.post(this.apis.TRAINEE.ACCEPT_PAGER,this.param).then((res) =>{
          console.log(res)
          if(res.code == 200){
             this.pageData = res.page.list;
             this.total = res.page.total;
          }else{
            // console.log("res--->",res)
            this.pageData = [];
            this.$Message.error(res.message);
          }
        })

      },
      /*fouc() {
        if(this.param.idCardNoLike.length > 0){
          if(this.pageData.length > 0 ) {
            setTimeout(() => {
              document.getElementById('ss').focus();
            }, 100)
          }else{
            this.$refs.re.focus();
            this.pageData = [];
          }
        }else{
          setTimeout(()=>{
           this.$refs.re.focus();
            this.pageData = [];
          },100)

        }

      },*/
      /*find(){
        var v = this
        if(this.param.idCardNoLike.length === 0){
          this.pageData = []
          return
        }
        v.util.getPageData(v,(res)=>{
          if(res.length > 0){
            this.fouc();
          }
        });
      },*/
      //获取当前用户可操作的报名点
      getBmdList() {
        this.$http.get(this.apis.FRAMEWORK.getCurrentOrgTree,{timers:new Date().getTime()}).then((res) => {
          if (res.code === 200) {
            /*if(res.result[0].value.length=='6'){
              this.CascaderList = res.result[0].children;
            }else if(res.result[0].value.length=='3'){
              this.CascaderList = res.result[0].children[0].children;
            }*/
            if(res.result[0].value.length==3){
              this.CascaderList= res.result[0].children[0].children;
            }else if(res.result[0].value.length==6){
              this.CascaderList = res.result[0].children;
            }else if(res.result[0].value.length==9){
              this.CascaderList = res.result
            }
            // this.CascaderList = res.result[0].children;

          }
        }).catch((error) => {
        })
      },
      getDictList() {
        this.dictList.carType.data = this.dictUtil.getByCode(this, this.dictList.carType.code);
      },
      pageChange(n) {
        this.util.pageChange(this, n);
      },
      pageSizeChange(n) {
        this.util.pageSizeChange(this, n);
      },
      auditConfirm(row) {
        var v = this;
        if (row.serialNum == '') {
          this.swal({
            text: '请先输入流水号',
            type: 'error'
          });

          return;
        }

        this.$http.post(this.apis.TRAINEE.ACCEPT, {id: row.id, serialNum: row.serialNum}).then((res) => {
          let msg = res.message;
          if (res.code === 200) {

            this.$Message.success(msg);
            this.param.idCardNoLike='';
            this.pageData =[];
            this.$refs.re.focus();


          } else {
            this.$Message.error(msg);
          }
          this.getPagerList();
        }).catch((error) => {
          this.param.idCardNoLike='';
          this.$Message.error(this.apis.NETWORK_ERR_STR);
        });
      },
      //文件上传成功后，回调该方法，进行后续处理
      successCallback(res, file, locDataName) {
        this.$refs.upload.clearFiles();
        this.result.code = res.code;
        console.log(res.result.list);
        if (res.code == 200) {
          this.result.errorKey = res.result.errorKey;
          this.result.sucKey = res.result.sucKey;
          this.result.key = res.result.key;
          this.result.error = res.result.errorCount;
          this.result.success = res.result.succeedCount;
          this.acceptResult = res.result.list;
          console.log('./././/./',res);
          this.compName = 'result';
          this.showModal = true;
          // this.getPagerList();
        } else {
          this.$Message.error("文件上传失败：" + res.message);
        }
      },
      //文件上传成功后，回调该方法，进行后续处理
      errorCallback(res, file, locDataName) {
        this.$refs.upload.clearFiles();
        this.swal({
          text: '文件上传失败，请稍后重试！',
          type:'error'
        });
      },
      handleFormatError(file) {
        this.swal({
          text: '文件格式错误，仅支持 xls',
          type:'error'
        });
      },
      handleMaxSize(file) {
        this.swal({
          text: '文件不能超过2M',
          type:'error'
        });
      },
      downloadTemplate(){
        window.open('../accept-template.xls','_blank');
      }
    }
  }
</script>

<style scoped>

</style>
