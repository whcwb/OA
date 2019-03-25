<template>
  <div>
    <Drawer
      @on-close="close"
      @on-open="open"
      title="预约练车"
      v-model="DrawerVal"
      width="720"
      :mask-closable="true"
    >
      <div slot="header">
        <Row>
          <Col span="10">
            <p>预约练车</p>
          </Col>
        </Row>
      </div>
      <Form :model="formData">
        <Row :gutter="32">
          <Col span="12">
            <div style="float: left">
              <FormItem label="教练员">
                <Select v-model="formData.jlId"
                        filterable
                        clearable
                        @on-query-change="searchJly"
                        ref="jlySelect"
                >
                  <Option v-for="(it,index) in searchCoachList" :value="it.value" :key="index">{{it.label}}</Option>
                </Select>
              </FormItem>
            </div>
            <div style="padding-top: 32px;">
              <Button type="primary" @click="compName ='addjl'">
                <Icon type="md-add"/>
              </Button>
            </div>
          </Col>
          <Col span="12">
            <FormItem label="学员练车人数" label-position="top">
              <Input @input="scXY" v-model="formData.xySl"></Input>
            </FormItem>
          </Col>
        </Row>
        <component :is="compName" :jxmc="jlJx"
                   @SaveOk="addjlSaveOk"
                   @remove="getCoachList('',true)"
                   @colse="compName=''"
                   @JLRowClick="JLRowClick"
                   @jxSeljxSel="(val)=>{getCoachList('',true)}"></component>
        <Divider/>
        <Row style="background-color: #f2f2f2">
          <Card>
            <p slot="title">学员信息</p>
            <p>
              <Row v-for="(item,index) in AMess" :key="index">
                <Col span="5" :class-name="'colsty'">
                  <Input type="text" size="default" v-model="item.xyXm" placeholder="学员姓名"/>
                </Col>
                <Col span="5" :class-name="'colsty'">
                  <Input type="textarea" :autosize="{minRows: 1,maxRows: 1}"
                         size="default" v-model="item.xyDh" placeholder="学员联系电话"/>
                </Col>
                <Col span="5" :class-name="'colsty'">
                  <Input type="textarea" :autosize="{minRows: 1,maxRows: 1}"
                         size="default" v-model="item.bz" placeholder="备注信息"/>
                </Col>
                <Col span="3" v-if="AMess.length>1">
                  <Button size="default" type="error" @click="remove(index)">删除</Button>
                </Col>
                <Col span="3" align="center">

                  <Button type="info" icon="md-add"
                          @click="pushmess"
                  >添加学员
                  </Button>
                </Col>
              </Row>
            </p>
          </Card>
        </Row>
        <Row :gutter="32">
          <Col span="12">
            <FormItem label="计费类型">
              <Select v-model="formData.lcLx">
                <Option value="00" :key="1">计时</Option>
                <Option value="10" :key="2">按次</Option>
              </Select>
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="预交费" label-position="top">
              <Input v-model="formData.lcYj"></Input>
            </FormItem>
          </Col>
        </Row>
        <FormItem label="备注" label-position="top">
          <Input type="textarea" v-model="formData.bz" :rows="4"/>
        </FormItem>
        <Row type="flex" justify="end">
          <Col span="12" align="right">
            <Button style="margin-right: 8px" @click="close">取消</Button>
            <Button type="warning" @click="addYYdj">预约</Button>
          </Col>
        </Row>
      </Form>
    </Drawer>
  </div>
</template>
<script>
  import addjl from '../comp/addJL'
  import giveCar from '../comp/readCard'
  import jlwh from '../comp/jlWh'


  export default {
    name: "fcdrawer",
    components: {addjl, jlwh},
    data() {
      return {
        compName: '',
        DrawerVal: false,
        giveCar: giveCar,
        JX: [],
        JXCode: 'ZDCLK1017',
        param: {
          pageNum: 1,//当前页码
          pageSize: 99999//每页显示数
        },
        listMess: [],
        styles: {
          height: 'calc(100% - 55px)',
          overflow: 'auto',
          paddingBottom: '53px',
          position: 'static'
        },
        jlJx: '',
        wxjldis: true,
        coachList: [],
        formData: {
          xySl: 1,
          clBh: '',
          wxjlId: '',
          lcClId: '',
          xyLx: '1',
          jlLx: '1',
          lcYj: '',
          jlId: '',
          jlJx: '',
          xyDh: '',
          xyXm: '',
          xyIds: '',
          zgId: '',
          cardNo: '',
          clCx: '',
          lcLx: '00',
          lcKm: '3'
        },
        searchCoachList: [],
        loadingJly:false,
        formDataJL: {},
        sfaemanlist: [],
        wxJL: [],
        bxJL: [],
        XY: [],
        AMess: [
          {}
        ],
        Pmess: {},
        i: 0,
        list: [],
      }
    },
    created() {
      this.getDictList()
    },
    mounted() {
    },
    methods: {
      searchJly (query) {
        if (query !== '') {
          this.loadingJly = true;
          setTimeout(() => {
            this.loadingJly = false;
            this.searchCoachList = this.coachList.filter(item => {
              return item.label.indexOf(query.toUpperCase()) != -1
            });
          }, 200);
        } else {
          this.searchCoachList = [];
        }
      },
      JLRowClick(row) {
        this.formData.jlId = row.id
      },
      onclear() {
        this.formData.jlJx = '';
        this.formData.wxjlId = '';
        this.wxJL = []
      },
      getDictList() {
        this.JX = this.dictUtil.getByCode(this, 'ZDCLK1017');
      },
      getCoachList(id,clear) {
        if(clear){
          this.formData.jlId = '';
        }
        this.coachList = [];
        this.$http.get('/api/lcwxjl/query',{params:{notShowLoading:'true'}}).then((res) => {
          this.wxJL = res.result
          if (res.code == 200 && res.result) {
            for (let r of res.result) {
              let py = this.util.parsePY(r.jlXm)
              this.coachList.push({label: r.jlJx + '_' + r.jlXm + ' [' + py + ']', value: r.id});
            }
          }
          if (res.code == 200 && res.result && id) {
            this.formData.jlId = id
          }
        })
      },
      addYYdj() {
        if (this.formData.jlLx == '10') {
          this.formData.jlId = this.formData.wxjlId
        }
        this.getWXXY(this.AMess);
        this.formData.lcKm = '3';
        this.$http.post('/api/lcjl/saveJl', this.formData).then((res) => {
          if (res.code == 200) {
            this.DrawerVal = false;
            this.AMess = [{}];
            this.formData = {};
            this.formData.lcLx = '00';
            this.$parent.getCarList();
            this.swal({
              title: '已成功预约',
              type: 'success',
              showCancelButton: false,
              confirmButtonText: '确定',
            })
          } else {
            this.swal({
              title: res.message,
              type: 'warning',
              showCancelButton: false,
              confirmButtonText: '确定',
            })
            this.formData.cardNo = null
          }
        })
      },
      close() {
        this.AMess = [{}]
        this.formData = {};
        this.XY = [];
        this.compName = '';
        this.DrawerVal = false;
        this.sfaemanlist = [];
        this.formData.lcLx = '00';
        this.searchCoachList = [];
        this.sfaemanlist = [];
        //清空下拉框内容
        this.$refs.jlySelect.clearSingleSelect();
      },
      getWXXY(AMess) {
        let arrAMess = AMess.length - 1;
        let messarr = [];
        let dxarr = [];
        AMess.forEach((item, index) => {
          messarr.push(item.xyXm)
          dxarr.push(item.xyDh)
          if (index == arrAMess) {
            console.log(dxarr.join(','))
            console.log(messarr.join(','))
            this.formData.xyIds = messarr.join(',');
            this.formData.xyDh = dxarr.join(',')
          }
        })
      },
      scXY(e) {
        this.AMess = [{}];
        e = parseInt(e);
        for (let i = 1; i < e; i++) {
          this.AMess.push({})
        }
      },
      clearform() {
        this.formData.jlId = '';
        this.formData.zgId = '';
        this.formData.xyIds = '';
        this.formData.xyDh = '';
        this.formData.jlJx = ''
      },
      remove(i) {
        this.AMess.splice(i, 1)
      },
      pushmess() {
        let a = JSON.parse(JSON.stringify(this.Pmess));
        this.AMess.push(a);
      },
      readkar() {
        if (!!window.ActiveXObject || "ActiveXObject" in window) {
        } else {
          this.swal({
            title: '请使用IE10以上的浏览器',
            type: 'warning',
            confirmButtonText: '关闭'
          })
          return
        }
        var v = this
        this.giveCar.readCard((key, mess) => {
          if (!key) {
            if (this.DrawerVal) {
              let v = this
              setTimeout(() => {
                if (v.DrawerVal) {
                  this.readkar()
                }
              }, 200)
            }
            if (v.showFQfzkp) {
              return;
            }
            v.showFQfzkp = true;
            v.swal({
              title: mess,
              type: 'error',
              confirmButtonText: '读卡',
              cancelButtonText: '关闭',
              showCancelButton: true
            }).then((res) => {
              if (res.value) {

                v.showFQfzkp = false;
                v.readkar()
              } else {
                v.showQfshowFQfzkpzkp = false;
                v.DrawerVal = false
              }
            })
          } else {
            v.showFQfzkp = false;
            this.formData.cardNo = mess
            this.getSave()
          }
        })
      },
      addjlSaveOk(id) {
        this.getCoachList(id)
      },
      getSafemanList() {
        this.$http.post('/api/zgjbxx/getAqy',{notShowLoading:'true'}).then((res) => {
          if (res.code == 200) {
            this.sfaemanlist = res.result
          } else {
            this.$Message.info(res.message);
          }
        })
      },
      open() {
      },
      show() {
        this.DrawerVal = true;
        this.formData.lcLx = '00'
        this.getCoachList();
        this.getSafemanList()
      },
    }
  }
</script>

<style scoped>

</style>
