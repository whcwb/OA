<template>
  <div>
    <Modal v-model="showModal"
           width="1200"
           :closable='false' :mask-closable="false">
      <div slot="header">
        <Row>
          <Col span="14">
            <div style="font-size: 20px;font-weight: 600;line-height: 33px">{{tit}}</div>
          </Col>
          <Col span="10">
            <Row type="flex" justify="end">
              <Col>
                <Button type="warning" style="color: white;margin-right: 12px" @click="close">关闭</Button>
              </Col>
            </Row>
          </Col>
        </Row>
      </div>
      <div>
        <Table size="small" height="500" :columns="columns1" :data="data1"></Table>
      </div>
      <div slot="footer" style="font-size: 24px; color: red">
        推荐人数总计  : {{data1.length}}  人
      </div>
    </Modal>
  </div>
</template>

<script>
  export default {
    props: {
      zgId:{
        type: String,
        default: ''
      },
      userAudit: {
        type: Object,
        default: {}
      }
    },
    data() {
      return {
        param:{
          registrationTimeGte:this.AF.getYear()+'-01-01 00:00:00',
          registrationTimeLte:this.AF.getYear()+'-12-31 23:59:59',
          referrerLike:this.zgId
        },
        jl:false,
        showModal: true,
        tit: '推荐详情',
        columns1: [
          {
            title: '推荐人姓名',
            key: 'referrer',
            align:'center',
            render:(h,p)=>{
              var referrer = p.row.referrer.split('-');
              return  h('div',referrer[0])
            }
          },
          {
            title: '姓名',
            key: 'name',
            align:'center',
            width:180
          },
          {
            title: '状态',
            key: 'status',
            align:'center',
            filters: [
              {label: '已报名', value: 99},
              {label: '待受理', value: 0},
              {label: '科目一待考', value: 10},
              {label: '科目二待考', value: 20},
              {label: '科目三待考', value: 30},
              {label: '科目四待考', value: 40},
              {label: '结业', value: 50},
              {label: '退学', value: 60}
            ],
            filterMethod (value, row) {
              if (value === 0) {
                return row.status == '00';
              } else if (value === 10) {
                return row.status == '10';
              }
            },
            filterRemote: (value, p, v) => {
              this.param.pageNum = 1;
              this.param.statusArray = value.join(',');
              this.gettjlxqlist()
              return true;
            },
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
            title: '报名时间',
            align:'center',
            key: 'registrationTime'
          }
        ],
        data1:[]
      }
    },
    created() {
      this.gettjlxqlist();
  },
    methods: {
      gettjlxqlist(){
          this.$http.post(this.apis.TRAINEE.PAGER, this.param).then(res => {
            if (res.code == 200) {
              // console.log(res)
              this.data1 = res.page.list
            }
          })
      },
      close() {
        this.$parent.getPagerList()
        this.$parent.compName = ''
      },
      txImg(A, url) {
        this.addmess[A] = url
      },
    }
  }
</script>
