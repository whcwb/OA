<template>
  <div class="box_col">
    <Row>
      <Col span="24">
        <pager-tit title="缴费对账"></pager-tit>
      </Col>
    </Row>
    <div class="box_row">
      <div class="box_row_100">
        <Row style="padding-top: 20px">
          <Col span="12">
            <Upload
              ref="upload"
              :max-size="2048"
              :before-upload="(file)=>{pageData=[]}"
              :on-success="(res, file,fileList)=>{successCallback(res, file, fileList)}"
              :on-error="errorCallback"
              :on-format-error="handleFormatError"
              :on-exceeded-size="handleMaxSize"
              :format="['docx']"
              type="drag"
              :action="uploadUrl">
              <div style="padding: 20px 0">
                <Icon type="ios-cloud-upload" size="52" style="color: #3399ff"></Icon>
                <p style="color:#464c5b;font-size: 16px">点击或拖动Word文件进行上传导入</p>
                <p style="color:#9ea7b4;font-size: 12px;padding-top:10px">只能上传docx文件，文件不能大于2M</p>
              </div>
            </Upload>
          </Col>
          <Col span="12">
            <Row>
              <Col span="6" style="padding-left: 20px;">
                <Card class="successCard" dis-hover style="height: 148px;width: 100%;position: relative">
                  <p slot="title" style="height: 50px;padding-top: 10px;color:white;">
                    <!--<Icon type="md-close-circle" style="font-size: 36px;padding-left: 10px" />-->
                    <span style="font-size: 22px;padding-left: 8px">记录总数</span>
                  </p>
                  <div style="text-align: center">
                    <count-to :end="result.zs" count-class="count-style"/>
                  </div>
                  <div style="z-index: 9999;position: absolute;right: -10px;top: -10px">
                    <Button long type="error" @click="expoerExcel" style="background-color: #fa541c;border-color: #fa541c">
                      <div>导出</div>
                      <div>Excel</div>
                    </Button>
                  </div>
                </Card>
              </Col>
              <Col span="6" style="padding-left: 20px;">
                <Card dis-hover class="successCard" style="height: 148px;width: 100%">
                  <p slot="title" style="height: 50px;padding-top: 10px;color:white;position: relative">
                    <!--<Icon type="md-checkmark-circle-outline" style="font-size: 36px;padding-left: 10px"/>-->
                    <span style="font-size: 22px;padding-left: 8px">初考</span>
                  </p>
                  <div style="text-align: center">
                    <count-to :end="result.chuKao" count-class="count-style"/>
                  </div>
                  <div style="z-index: 9999;position: absolute;right: -10px;top: -10px">
                    <Button long type="error" @click="exportData(1)" style="background-color: #fa541c;border-color: #fa541c">
                      <div>导出</div>
                      <div>Excel</div>
                    </Button>
                  </div>
                </Card>
              </Col>
              <Col span="6" style="padding-left: 20px;">
                <Card class="successCard" dis-hover style="height: 148px;width: 100%;position: relative">
                  <p slot="title" style="height: 50px;padding-top: 10px;color:white;">
                    <!--<Icon type="md-close-circle" style="font-size: 36px;padding-left: 10px" />-->
                    <span style="font-size: 22px;padding-left: 8px">补考</span>
                  </p>
                  <div style="text-align: center">
                    <count-to :end="result.buKao" count-class="count-style"/>
                  </div>
                  <div style="z-index: 9999;position: absolute;right: -10px;top: -10px">
                    <Button long type="error" @click="exportData(2)" style="background-color: #fa541c;border-color: #fa541c">
                      <div>导出</div>
                      <div>Excel</div>
                    </Button>
                  </div>
                </Card>
              </Col>
              <Col span="6" style="padding-left: 20px;">
                <Card class="failCard" dis-hover style="height: 148px;width: 100%;position: relative">
                  <p slot="title" style="height: 50px;padding-top: 10px;color:white;">
                    <!--<Icon type="md-close-circle" style="font-size: 36px;padding-left: 10px" />-->
                    <span style="font-size: 22px;padding-left: 8px">失败</span>
                  </p>
                  <div style="text-align: center">
                    <count-to :end="result.fail" count-class="count-style"/>
                  </div>
                  <div style="z-index: 9999;position: absolute;right: -10px;top: -10px">
                    <Button long type="error" @click="exportData(3)" style="background-color: #fa541c;border-color: #fa541c">
                      <div>导出</div>
                      <div>Excel</div>
                    </Button>
                  </div>
                </Card>
              </Col>
            </Row>
          </Col>
        </Row>
      </div>
    </div>
    <div class="box_col_auto">
      <table-area ref="tableArea" :parent="v" :TabHeight="AF.getPageHeight()-320" :pager="false"></table-area>
    </div>
    <!--<component :is="compName" :showModal="this.showModal" :acceptResult="acceptResult" :excelKey="this.result.sucKey"-->
               <!--:errorKey="this.result.errorKey" :error="this.result.error" :success="result.success"></component>-->
  </div>
</template>

<script>
  import CountTo from '_c/count-to';
  import http from '@/axios/index';

  export default {
    name: "index",
    components: {CountTo},
    data() {
      return {
        result:{
          buKao: 0,
          chuKao: 0,
          data: [],
          fail: 0,
          suc: 0,
          zs:0
        },
        key:"",
        showModal: false,
        compName: '',
        accessToken: {},
        v: this,
        uploadUrl: this.apis.url + this.apis.KSSF.CHECK,
        pagerUrl: this.apis.TRAINEE.ACCEPT_PAGER,
        total: 0,
        param: {
          jgdm: '', // 机构代码
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
        //table数据查询结果
        pageData: [],
        tableColumns: [
          {type: 'index', width: 60, align: 'center'},
          {title: '姓名', align: 'center', key: 'xm', width: 120},
          {title: '证件号码', align: 'center', key: 'zjhm', width: 200},
          {title: '报名点', align: 'center', key: 'bmd'},
          {title: '科目', align: 'center', key: 'km'},
          {title: '银行缴费金额', align: 'center', key: 'je', width: 120},
          {title: '对账状态', align: 'center', key: 'zt', width: 200,dict:'successOrFail'},
          {title: '对账结果', align: 'center', key: 'jg', width: 200},
          // {
          //   title: '详情', align: 'center', key: 'idCardNo', render: (h, p) => {
          //     let buttons = [];
          //     buttons.push(this.util.buildButton(this, h, 'success', 'md-menu', '详情', () => {
          //       alert('详情');
          //     }))
          //     return h('div', buttons);
          //   }
          // },
        ],
      }
    },
    created() {
      this.util.initPageSize(this);
      this.util.initTableHeight(this);
      this.util.fillTableColumns(this)
    },
    methods: {
      /**
       * 导出excel
       */
      exportData(t){
        let key = "";
        switch (t){
          case 1:key = '初考';break;
          case 2:key = '补考';break;
          default:key = '失败';break;
        }
        let param = {
          // quoted:true,
          filename :'对账结果-'+key,
          columns:this.tableColumns.filter((data,index)=>data.type !== 'index'&& data.key !== 'zt'  ),
          data:this.getExportData(key) // 导出数据源
        }
        this.$refs.tableArea.exportCsv(param);
      },
      /**
       * 获取导出数据
       */
      getExportData(key){
        let list = JSON.parse(JSON.stringify(this.pageData.filter((data,index)=> data.jg.indexOf(key) >= 0)));
        for (let r of list){
          r.zjhm = "'"+r.zjhm;
        }
        return list
      },
      //文件上传成功后，回调该方法，进行后续处理
      successCallback(res, file, locDataName) {
        this.result = res.result;
        this.pageData = this.result.data;
        this.key = this.result.key;
        this.$refs.upload.clearFiles();
        // this.result.code = res.code;
        // if (res.code == 200) {
        //   this.result.errorKey = res.result.errorKey;
        //   this.result.sucKey = res.result.sucKey;
        //   this.result.key = res.result.key;
        //   this.result.error = res.result.errorCount;
        //   this.result.success = res.result.succeedCount;
        //   this.acceptResult = res.result.list;
        //   this.compName = 'result';
        //   this.showModal = true;
        // } else {
        //   this.$Message.error("文件上传失败：" + res.message);
        // }
      },
      //文件上传成功后，回调该方法，进行后续处理
      errorCallback(res, file, locDataName) {
        this.$refs.upload.clearFiles();
        this.swal({
          text: '文件上传失败，请稍后重试！',
          type: 'error'
        });
      },
      handleFormatError(file){
        this.swal({
          text: '文件格式错误，仅支持 xls',
          type: 'error'
        });
      },
      handleMaxSize(file) {
        this.swal({
          text: '文件不能超过2M',
          type: 'error'
        });
      },
      expoerExcel(){
        window.open(http.url+'/pub/exportResult/' + this.key
          , "_blank");
      }
    }
  }
</script>

<style lang="less">
  .count-style {
    font-size: 50px;
  }

  .successCard {

  .ivu-card-head {
    border-bottom: 1px solid #e8eaec;
    padding: 0px;
    line-height: 1;
    background-color: #2d8cf0;
  }

  }

  .failCard {

  .ivu-card-head {
    border-bottom: 1px solid #e8eaec;
    padding: 0px;
    line-height: 1;
    background-color: #ed3f14;
  }

  }
</style>
