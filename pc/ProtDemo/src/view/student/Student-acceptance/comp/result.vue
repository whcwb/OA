<template>
    <div>
      <Modal v-model="showModal" height="700"
              width="1000"
             :closable='false' :mask-closable="false"
             :styles="{top: '60px'}"  @on-ok="getPage" @on-cancel="getPage">

        <Row >
          <Col span="4" style="padding-left: 20px;">
             <span style="font-size: 16px;margin-left: 16px">
                    成功 :
                  <span style="font-size: 20px;color: #ff9900">
                    {{success}}
                  </span>

             </span>
          </Col>
          <Col span="4" style="padding-left: 20px;">
            <span style="font-size: 16px;margin-left: 16px">
                    失败 :
                  <span style="font-size: 20px;color: #ff9900">
                    {{error}}
                  </span>

             </span>
          </Col>
          <Col style="float:right ;padding-right: 20px;margin-bottom: 10px">
          <Tooltip content="成功数据导出" placement="top">
            <Button type="primary" @click="getExcleMess">
              <Icon type="md-cloud-download"></Icon>
            </Button>
          </Tooltip>
            <Tooltip content="失败数据导出" placement="top">
              <Button type="error" @click="getErrorExcel" style="margin-left: 10px">
                <Icon type="md-cloud-download"></Icon>
              </Button>
            </Tooltip>
          </Col>
        </Row>

        <Table :data="acceptResult" :columns="tableTit" stripe></Table>
      </Modal>
    </div>
</template>

<script>
  import http from '@/axios/index';

  export default {
      name: "result",
      props: {
        acceptResult:{
          type: Array,
          default: []
        },
        error: {
          type: Number,
          default: 0
        },
        success: {
          type: Number,
          default: 0
        },
        showModal: {
          type: Boolean,
          default: false
        },
        excelKey: {
          type: String,
          default: ''
        },
        errorKey: {
          type: String,
          default: ''
        }
      },
      created(){
        console.log('数据传递',this.excelKey);
      },
      data(){
        return{
          tableTit: [{
            title: '序号',
            width: 80,
            align: 'center',
            type: 'index'
          },
          {
            title: '姓名',
            width: 200,
            align: 'center',
            key: 'userName'
          },
            {
              title: '证件号码',
              width: 200,
              align: 'center',
              key: 'idCardNo'
            },
            {
              title: '结果',
              align: 'center',
              render: (h, p) => {
                return h('div', [
                  h('Tooltip', {
                    props: {
                      content: '成功与失败',
                      placement: "top"
                    }
                  }, [

                    h('Tag', {
                      props: {
                        color: p.row.success == '1' ? 'green' : 'red'
                      }
                    }, p.row.success == '1' ? ('成功') : ('失败'))

                  ])
                ])
              }
            },
            {
              title: '原因',
              align: 'center',
              width: 200,
              key: 'message'
            }
          ]
        }
      },
      methods: {
        getExcleMess(){
          window.open(http.url+'/pub/exportResult/' + this.excelKey
            , "_blank");
        },
        getErrorExcel(){
          window.open(http.url+'/pub/exportResult/' + this.errorKey
            , "_blank");
        },
        getPage(){
          this.$parent.getPagerList();
          this.$parent.showModal = false;

        },
        close() {
          this.$parent.compName = '';
        }
      }
    }
</script>

<style scoped>

</style>
