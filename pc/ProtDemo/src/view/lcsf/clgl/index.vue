<template>
  <div class="box_col">
    <Row style="margin-bottom: 8px">
      <Col span="8">
        <Menu mode="horizontal" :theme="theme1" active-name="2" @on-select="keerkesan"
              style="font-size: 48px;font-weight: bold">
          <MenuItem name="2">
            科目二考试车
          </MenuItem>
          <MenuItem name="3">
            科目三考试车
          </MenuItem>
        </Menu>
      </Col>
      <Col span="16">
        <Row :gutter="6" type="flex" justify="end">
          <Col span="10">
            <div style="float: left;margin-top: 8px;">
              <span
                style="width: 100px;height: 80px;background-color: red;color:white;padding:6px;border-radius: 4px;margin-left: 16px;">共{{tabdata.length}}台</span>
              <span
                style="width: 100px;height: 80px;background-color: #ff9900;color:white;padding:6px;border-radius: 4px;margin-left: 16px;">停用{{zxNum}}台</span>
              <span
                style="width: 100px;height: 80px;background-color: #66CD00;color:white;padding:6px;border-radius: 4px;margin-left: 16px;">启用{{xxNum}}台</span>
            </div>
          </Col>
          <Col span="5">
            <Input v-model="param.clBh" size="large" placeholder="请输入车辆编号"/>
            <br>
          </Col>
          <Col span="5">
            <Input v-model="param.clHm" size="large" placeholder="请输入车牌号"/>
            <br>
          </Col>
          <Col span="2" align="center">
            <Button type="primary" @click="CLCX">
              <Icon type="md-search"></Icon>
              <!--查询-->
            </Button>
          </Col>
          <Col span="2" align="center">
            <Button type="primary" @click="compName='cjcar'">
              <Icon type="md-add"></Icon>
              <!--查询-->
            </Button>
          </Col>
        </Row>
      </Col>
    </Row>
    <div class="box_col_auto">
      <!--<Row>-->
        <!--<Col span="6" v-for="(item,index) in tabdata" :key="index">-->
      <div class="box_row_list">
        <div style="width: 25%;min-width: 320px;padding: 8px" v-for="(item,index) in tabdata" :key="index">
          <Card style="width:100%">
            <Row justify="center" align="middle">
              <Col span="3" align="left">
                <Avatar style="color: white;background-color: red;line-height: 38px;font-size: 24px;font-weight: bold">
                  {{item.clBh}}
                </Avatar>
              </Col>
              <Col span="9" style="margin-top: 6px;padding-left: 10px">
                <span style="font-weight: bold;font-size: 18px">{{item.clHm}}</span>
              </Col>
              <Col span="6" style="padding-top: 6px">
                <Tag color="cyan" style="font-weight: bold">{{item.clCx}}</Tag>
                <Tooltip :content="item.cardNo?'卡片已绑定':'卡片_未_绑定'" placement="top">
                  <Button type="text" :style="{background:item.cardNo?'#47cb89':'#747b8b',color:'#fff'}"  size="small">卡</Button>
                </Tooltip>
              </Col>
              <Col span="6">
                <Row type="flex" justify="end">
                  <Col span="12" style="margin-top: 6px">
                    <Tooltip content="修改">
                      <Button type="info" icon="ios-create" size="small" @click="a = item,compName = 'clxq'"></Button>
                    </Tooltip>
                  </Col>
                  <Col span="12" style="margin-top: 6px">
                    <Tooltip content="删除">
                      <Button type="warning" icon="ios-trash" size="small" @click="del(item.id)"></Button>
                    </Tooltip>
                  </Col>
                </Row>
              </Col>
            </Row>
            <!--<div style="padding: 6px 0">-->
              <!--磁卡编码：-->
              <!--<span v-if="item.cardNo" style="color: #19be6b">{{cardNo(item.cardNo)}}</span>-->
              <!--<span v-else style="color:#ed3f14">车辆未绑卡</span>-->
            <!--</div>-->
            <div style="padding-top: 5px">
              <div class="box_row" style="padding-top: 6px; ">
                <div class="box_col_100">
                  <Button v-if="item.clZt == '00' || item.clZt == '01'" type="success" size="default" long
                          @click="changeZT(item,'00')">启用
                  </Button>
                  <Button v-if="item.clZt == '02' || item.clZt == '03'" type="default" size="default" long
                          @click="changeZT(item,'00')">启用
                  </Button>
                </div>
                <div class="box_col_100">
                  <Button v-if="item.clZt == '00' || item.clZt == '01'" type="default" size="default" long
                          @click="changeZT(item,'03')">停用
                  </Button>
                  <Button v-if="item.clZt == '02' || item.clZt == '03'" type="warning" size="default" long
                          @click="changeZT(item,'03')">停用
                  </Button>
                </div>
              </div>
            </div>
          </Card>
        </div>
      </div>
        <!--</Col>-->
      <!--</Row>-->
    </div>
    <!--<Table :height="AF.getPageHeight()-290" size="small" :columns="tabTit" :data="tabdata"></Table>-->
    <component :is=compName :param="this.a"></component>
  </div>
</template>

<script>
  import clxq from './comp/clxq'
  import cjcar from './comp/cjcar'

  export default {
    name: "index",
    components: {
      clxq, cjcar
    },
    data() {
      return {
        KXsty: {
          'background': '#57c5f7',
          'color': '#fff'
        },
        theme1: 'light',
        ZXsty: {
          'background': '#19be6b',
          'color': '#fff'
        },
        BYsty: {
          'background': '#ffad33',
          'color': '#fff'
        },
        param: {
          pageNum: 1,
          pageSize: 99999,
          clBh: '',
          clHm: '',
          clKm: '2',
          orderBy: 'clKm asc,clBh asc',
          notShowLoading: 'true'
        },
        a: {},
        value1: '',
        value2: '',
        compName: '',
        tabdata: [],
        carTypList: [],
        zxNum: '',
        xxNum: ''
      }
    },
    mounted() {

    },
    methods: {
      cardNo(val){
        return val.substring(0,2)+'******'
      },
      keerkesan(val) {
        this.param.clKm = val;
        this.getPagerList()
      },
      changeZT(item, ZT) {
        this.$http.post(this.apis.CLWH.CLGX, {id: item.id, clZt: ZT, notShowLoading: 'true'}).then(res => {
          if (res.code == 200) {
            this.$Message.info(res.message);
            this.getPagerList();
          } else {
            this.swal({
              title: res.message,
              type: 'warning',
              showCancelButton: false,
              confirmButtonText: '确定',
            })
          }
        })
      },
      CLCX() {
        this.getPagerList()
      },
      del(id) {
        this.swal({
          title: '是否删除？',
          type: 'question',
          showCancelButton: true,
          confirmButtonText: '删除！',
          cancelButtonText: '取消！'
        }).then(val => {
          if (val.value) {
            this.$http.post('/api/lccl/remove/' + id).then(res => {
              if (res.code == 200) {
                this.swal({
                  type: 'success',
                  title: res.message
                })
                this.getPagerList()
              } else {
                this.swal({
                  type: 'error',
                  title: res.message
                })
              }
            }).catch(err => {
            })
          }
        })
      },
      getPagerList() {
        this.zxNum = 0;
        this.xxNum = 0;
        this.$http.post(this.apis.CLWH.CLXX, this.param).then((res) => {
          if (res.code == 200) {
            this.tabdata = res.page.list;
            for (let r of this.tabdata) {
              if (r.clZt === '03' || r.clZt === '04') {
                this.zxNum++;
              } else if (r.clZt === '00' || r.clZt === '01') {
                this.xxNum++;
              }
            }
          } else {
            this.$Message.info(res.message);
          }
        })
      }
    },
    created() {
      this.getPagerList()
      this.carTypList = this.dictUtil.getByCode(this, 'ZDCLK1044')
      this.carTypList.forEach((item, index) => {
        item.background = '#fff'
      })
    }
  }
</script>

<style scoped>

</style>
