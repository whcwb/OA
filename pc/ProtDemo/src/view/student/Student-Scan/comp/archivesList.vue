<template>
  <div>
    <Modal
      v-model="modalShow"
      width="70%"
      :closable='false'
      :mask-closable="false">
      <div slot="header" style="font-weight: 700;font-size: 18px">
        请选择档案库
      </div>
      <div :style="{height:AF.getPageHeight()/3*2+'px'}">
        <div class="box_col">
          <div class="box_col_auto">
            <Row style="padding-left: 20px">
              <Col span="3">
                <Select v-model="param.orderType" size="large" @on-change="getArchivesList">
                  <Option value="1">按可用量排序</Option>
                  <Option value="2">按柜编码排序</Option>
                </Select>
              </Col>
              <Col span="4" style="padding-left: 8px">
                <Input type="text" placeholder="档案柜编码" @on-change="changeInput" size="large"/>
              </Col>
            </Row>
            <Row>
              <Col span="24" v-if="arcData.length == 0">
                <no-data :style="{paddingTop:AF.getPageHeight()/2+'px'}"></no-data>
              </Col>
              <Col class-name="itenCol" span="6"
                   :xs="24" :sm="12" :md="8" :lg="6" v-else v-for="item in arcData">
                <arc-item @okSel="okSel" :item="item"></arc-item>
              </Col>
            </Row>
          </div>
        </div>
      </div>
      <div slot="footer">
        <Button type="info" ghost long  @click="close">取消</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
  import arcItem from './comp_s/arcItem'
  export default {
    name: "addarc",
    components:{
      arcItem
    },
    data(){
      return{
        modalShow: true,
        param:{
          orderType:'2',
          id:'',
          total:1,//总数量
          pageNum: 1,//当前页码
          pageSize:9999//每页显示数
        },
        arcData:[],
        filterArcData:[]
      }
    },
    computed: {
      searchId: (value)=> {
        this.arcData = this.arcData.filter((json)=>{
            return json.id.indexOf(value) != -1;
        });

        return value;
      }
    },
    created(){
      this.getArchivesList();
    },
    methods:{
      getArchivesList(){
        this.$http.post(this.apis.ARCHIVES.RECORD, this.param).then((res) => {
          let msg = res.message;
          if (res.code === 200) {
            this.arcData = res.page.list;
            this.filterArcData = this.arcData;
          }else{
            this.$Message.error(msg);
          }
        }).catch((error) => {
          this.$Message.error(this.apis.NETWORK_ERR_STR);
        });
      },
      changeInput(event){
        let value = event.srcElement.value;
        if (value == ''){
          this.arcData = this.filterArcData;
        }else{
          this.arcData = this.filterArcData.filter((json)=>{
            return json.id.indexOf(value) != -1;
          });
        }
      },
      close(){
        this.$parent.compName = ''
      },
      okSel(val){
        this.$parent.$data.result.selectItem = val;

        this.close();
      }
    }
  }
</script>

<style lang="less">
  .itenCol{
    padding: 22px;
  }
</style>
