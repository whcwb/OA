<style lang="less">
  .yuan {
    background-color: #2d8cf0;
    color: #fff;
    width: 35px;
    height: 35px;
    text-align: center;
    line-height: 35px;
    font-size: 16px;
    font-weight: 600;
    border-radius: 35px;
  }
</style>
<template>
  <div>
    <Modal
      @on-close="M_close"
      @on-open="open"
      title="分配车辆"
      v-model="DrawerVal"
      :mask-closable="false"
      height="500"
      width="900"
    >
      <div slot="header">
        <div class="box_row">
          <div v-if="carMess">
            <Tag type="border" style="font-size: 24px;font-weight:bold;padding: 5px;height: 36px;" color="error">{{carMess.clBh}}-{{carMess.clCx}}</Tag>
          </div>
          <div style="font-size: 16px;margin-right: 30px">
            <h2>模训发车</h2>
          </div>
        </div>
      </div>
      <Form :model="formData">
        <Row :gutter="32">
          <Col span="12">
            <div style="float: left">
              <FormItem label="教练员">
                <Select v-model="formData.jlId"
                        filterable
                        clearable
                        v-if="showCoachSelector"
                        remote
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
              <Input v-model="formData.xySl"></Input>
            </FormItem>
          </Col>
        </Row>
        <radio-car
          v-if="showCAR"
          @getCarItemMess="carItem"
        >
        </radio-car>
        <component :is="compName" :jxmc="jlJx"
                   @SaveOk="addjlSaveOk"
                   @colse="compName=''"
                   @remove="getCoachList('',true)"
                   @JLRowClick="JLRowClick"
                   @jxSeljxSel="(val)=>{getCoachList('',true)}">
        </component>
        <Divider/>
        <Row :gutter="32">
          <Col span="8">
            <FormItem :label="'安全员'" label-position="top">
              <Select v-model="formData.zgId" filterable>
                <Option v-for="(item,index) in sfaemanlist" :value="item.value" :key="index">{{ item.label}}</Option>
              </Select>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="计费类型">
              <Select v-model="formData.lcLx">
                123456789{{lcLxList}}
                <Option v-for="(item,index) in lcLxList" :value="item.key" :key="index">{{item.val}}</Option>
              </Select>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="预交费" label-position="top">
              <Input v-model="formData.lcYj"></Input>
            </FormItem>
          </Col>
        </Row>
        <FormItem label="备注" label-position="top">
          <Input type="textarea" v-model="formData.bz" :rows="4"/>
        </FormItem>
      </Form>
      <div slot='footer'>
        <Button style="margin-right: 8px" @click="DrawerVal=false,carMess = null">取消</Button>
        <Button type="primary" @click="getSave">发车</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
  import addjl from '../comp/addJL'
  import jlwh from '../comp/jlWh'
  import giveCar from '../comp/readCard'
  import radioCar from '../comp/RadioCar'

  export default {
    name: "fcdrawer",
    components: {addjl, jlwh, radioCar},

    data() {
      return {
        compName: '',
        DrawerVal: false,
        giveCar: giveCar,

        lcLxList: [],
        showCoachSelector: true,
        wxjldis: true,
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
        formData: {
          notShowLoading: 'true',
          xySl: 0,//学员数量
          // clBh: '',//车辆编号
          lcClId: '',//车辆ID
          // xyLx: '1',//学员类型
          lcYj: '',//预交费用
          jlId: '',//教练ID
          // jlJx: '',//教练驾校
          xyDh: '',
          xyXm: '',
          xyIds: '',
          zgId: '',//安全员ID
          cardNo: '',//卡 序列号
          // clCx: '',
          lcLx: '00'
        },
        formDataJL: {},
        sfaemanlist: [],
        wxJL: [],
        bxJL: [],
        coachList: [],
        //支持远程搜索
        searchCoachList: [],
        loadingJly: false,
        XY: [],
        AMess: [
          {}
        ],
        Pmess: {},
        i: 0,
        list: [],
        showCAR: false,
        carMess: null
      }
    },
    created() {
      this.getDictList();
    },
    mounted() {
    },
    methods: {
      M_close() {
        this.carMess = null
        this.$emit('close')
      },
      getCarNoNum(mess) {
        console.log('num', mess);
        this.$http.post('/api/lccl/pager', {
          notShowLoading: 'true',
          pagerNum: 1,
          pageSize: 99999,
          // clKm: "3",
          cardNo: mess,
        }).then((res) => {
          if (res.code == 200) {
            console.log(res);
            if (res.page.list.length == 0) {
              this.formData.lcClId = ""
              this.showCAR = true
            } else {
              let car = res.page.list[0];
              console.log(car);
              if (car.clKm !== '3') {
                this.swal({
                  title: '此卡已绑定科目二车辆,请更换卡片',
                  type: 'warning',
                  confirmButtonText: '关闭'
                })
                return
              }
              this.choosedItem.lcClId = car.id;
            }
          } else {
            this.$Message.info(res.message);
          }
        })
      },
      searchJly(query) {
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
      close() {
        this.DrawerVal = false;
        this.formData.zgId = ''
        this.formData.jlId = ''
        this.formData.jlJx = '';
        this.formData = {};
        this.formData.lcLx = '00';
        this.showCAR = false;
        this.XY = [];
        this.compName = '';
        this.searchCoachList = [];
        this.sfaemanlist = [];
        //清空下拉框内容
        this.$refs.jlySelect.clearSingleSelect();
      },
      // scXY(e) {
      //   this.AMess = [{}];
      //   e = parseInt(e);
      //   for (let i = 1; i < e; i++) {
      //     this.AMess.push({})
      //   }
      // },
      clearform() {
        this.formData.jlId = '';
        this.formData.xyIds = '';
        this.formData.xyDh = '';
        this.formData.jlJx = ''
      },
      remove(i) {
        this.AMess.splice(i, 1)
      },
      // pushmess() {
      //   let a = JSON.parse(JSON.stringify(this.Pmess));
      //   this.AMess.push(a);
      // },
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
            console.log('mess', mess);
            // this.getSave()
          }
        })
      },
      addjlSaveOk(id) {
        this.getCoachList(id)
      },
      getSave() {
        if (this.formData.xySl == '' || this.formData.xySl == 'NaN' || this.formData.xySl == NaN || isNaN(this.formData.xySl)) {
          this.formData.xySl = 0
        }
        this.formData.xySl = parseInt(this.formData.xySl)
        this.formData.notShowLoading = 'true';
        this.$http.post('/api/lcjl/save', this.formData).then((res) => {
          if (res.code == 200) {
            this.DrawerVal = false;
            this.$emit('getCarList');
            this.AMess = [{}];
            this.formData = {};
            this.$parent.formData = {};
            this.carMess = null;
            this.formData.lcLx = '00';
            this.$Message.info('发车成功');
            this.util.getPageData(this.$parent)
          } else {
            this.swal({
              title: res.message,
              type: 'warning',
              showCancelButton: false,
              confirmButtonText: '确定',
            })
            //this.formData.cardNo = ''
            //this.close()
          }
        })
      },
      carItem(it, index) {
        console.log(it);
        console.log(it.id);
        this.formData.lcClId = it.id
        console.log(this.formData);

      },
      getCoachList(id, clear) {
        if (clear) {
          this.formData.jlId = '';
        }
        this.coachList = [];
        this.$http.get('/api/lcwxjl/query', {params: {notShowLoading: 'true'}}).then((res) => {
          this.wxJL = res.result
          if (res.code == 200 && res.result) {
            for (let r of res.result) {
              let py = this.util.parsePY(r.jlXm)
              this.coachList.push({label: r.jlJx + '_' + r.jlXm + ' [' + py + ']'+'_'+ r.jlLxdh, value: r.id});
            }
          }
          if (res.code == 200 && res.result && id) {
            this.formData.jlId = id
          }
        })
      },
      getSafemanList() {
        this.$http.post('/api/zgjbxx/getAqy', {notShowLoading: 'true'}).then((res) => {
          if (res.code == 200) {
            res.result.forEach((item, index) => {
              let py = this.util.parsePY(item.xm)
              item.label = item.xm + ' [' + py + ']'
              item.value = item.id
              if (index == res.result.length - 1) {
                this.sfaemanlist = res.result
              }
            })
          } else {
            this.$Message.info(res.message);
          }
        })
      },
      getDictList(){
        this.lcLxList = this.dictUtil.getByCode(this, 'ZDCLK1048');
        console.log('0.0',this.lcLxList);
      },
      open() {
      },
      show(mess) {
        var v = this
        this.AF.carCard('3', mess, (type, res) => {
          console.log('**********', res);
          if (type) {
            if (res.result) {
              v.carMess = res.result
            }
            v.fz(mess)
          } else {
            this.DrawerVal = false;
            return
          }
        })
      },
      fz(mess){
        this.getCarNoNum(mess);
        this.close();//数据初始化
        this.getSafemanList();//获取教练员
        this.DrawerVal = true;//打开抽屉组件
        this.formData.cardNo = mess;
        this.formData.jlJx = '';
        this.formData.lcLx = '00';
        if (this.formData.lcClId == '') {
          this.showCAR = true
        }
        this.getCoachList();

      }

    }
  }
</script>
