<template>
  <div>
    <Modal
      width="1200"
      title="修改学员科三缴费状态"
      v-model="modal"
      :closable="false"
      :mask-closable="false"
    >
      <div   >
        <Row>
          <Col span="20">
            <Input v-model="idCardNo" placeholder="请输入学员身份证号码" @on-enter="findByIdCard"></Input>
          </Col>
          <Col span="4" style="padding-left: 5px">
            <Button icon="ios-search" type="primary" @click="findByIdCard"></Button>
          </Col>
        </Row>
        <Row style="padding-top: 2px ">
          <Table :columns="columns" :height="200" stripe :data="data"></Table>
        </Row>
      </div>
      <div slot="footer">
        <Button type="default" style="color: #949494" @click="close">关闭</Button>
      </div>
    </Modal>
    <component :is="compName" :mess='mess'></component>
  </div>
</template>

<script>
  import edit from './edit'
  export default {
    name: "charge",
    components: {
      edit
    },
    data() {
      return {
        modal: true,
        idCardNo: '',
        compName:'',
        mess:{},
        columns:[
          {title: '姓名', key: 'name', align: 'center', width: 100},
          {title: '证件号码', width: 180, key: 'idCardNo', align: 'center'},
          {
            title: '报名时间', width: 180, key: 'registrationTime', align: 'center',
            render: (h, params) => {
              return h('div',
                params.row.registrationTime.substring(0,10)
              );
            }
          },
          {
            title: '报名点', width: 120, key: 'jgmc', align: 'center',
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
            title: '负责人',
            align: 'center',
            width: 100,
            key: 'glyxm'
          },
          {
            title: '车型', width: 100, key: 'carType', align: 'center',
            render: (h, p) => {
              let carType = p.row.carType;
              let color = 'volcano';
              let carTypeStr = p.row.carType.substring(0, 1);
              if (carTypeStr == 'C') {
                //小车
                color = 'green';
              }
              return h('Tag', {
                props: {
                  color: color
                },
                style: {
                  fontSize: '15px',
                }
              }, carType);
            }
          },
          {
            title: '流水号', width: 148, key: 'serialNum', align: 'center', render: (h, p) => {
              if (p.row.serialNum == '') {
                return h('Tag', {
                  props: {
                    color: 'default'
                  },
                  style: {
                    fontSize: '15px',
                  }
                }, '-');
              }

              return h('div', p.row.serialNum);
            }
          },
          {
            title: '业务状态', width: 160, key: 'status', align: 'center',
            render: (h, p) => {
              let str = p.row.status;
              if (str == '99') {
                str = '已报名';
              } else if (str == '00') {
                str = '待受理';
              } else if (str == '10') {
                str = '科目一待考';
              } else if (str == '20') {
                str = '科目二待考';
              } else if (str == '30') {
                str = '科目三待考';
              } else if (str == '40') {
                str = '科目四待考';
              } else if (str == '50') {
                str = '结业';
              } else if (str == '60') {
                str = '退学';
              }else if (str == '70') {
                str = '重新学车';
              }
              return h('Tag', {
                style: {
                  width: '90px'
                },
                props: {
                  color: p.row.status == '60' ? 'error' : 'success'
                },
              }, str);
            }
          },
          {
            title:'缴费',
            align:'center',
            render:  (h,p) => {
              return h('Button',{
                props: {
                  icon: 'ios-create',
                  type: 'warning',
                  size: 'small'
                },
                on: {
                  click: () => {
                    this.mess = p.row;
                    this.compName = 'edit';
                  }
                }
              })
            }
          }
        ],
        data:[],
        compName: ''
      }
    },
    methods: {
      close() {
        this.$parent.compName = ''
        // this.$emit('List')
      },
      findByIdCard() {
        this.$http.post('/api/traineeinformation/getByIdCard', {idcard:this.idCardNo}).then(res => {
          if(res.code == 200){
            this.data  = res.result;
          }else{
            this.$Message.error(res.message);
          }
        })
      }
    }
  }
</script>

<style scoped>

</style>
