<template>
  <div class="box_col">
    <!--<pager-tit title="返点确认" iconname="ios-football"></pager-tit>-->
    <Row>
      <Col span="24">
        <Menu mode="horizontal" theme="light" :active-name="MenuItemName"
              @on-select="keerkesan"
              style="font-size: 48px;font-weight: bold">
          <MenuItem name="1">
            待返点
          </MenuItem>
          <MenuItem name="2">
            已返点
          </MenuItem>
        </Menu>
      </Col>
    </Row>
    <div class="body" v-if="MenuItemName=='1'">
      <Row style="padding: 12px 0" :gutter="6">
        <Col span="4">
          <Input id="code" autofocus v-model="param.idLike" placeholder="请扫描条形码" @on-enter="add"/>
        </Col>
        <Col span="4">
          <Input autofocus v-model="param.jlXmLike" placeholder="教练员姓名" @on-enter="add"/>
        </Col>
        <Col span="8">
          <span style="margin-left: 16px;">
            <Button type="primary" @click="getOldData">
              <Icon type="md-search"></Icon>
              <!--查询-->
            </Button>
          </span>
          <span style="color: red;font-weight: 600;font-size: 20px;padding-left: 16px">
            <span>合计：</span>
            <span>{{total}}元</span>
          </span>
        </Col>
        <Col span="8">
          <Row type="flex" justify="end">
            <Col span="8">
              <Input autofocus v-model="okParams.fdJe" placeholder="返点金额" @on-enter="add"/>
            </Col>
            <Col span="6">
              <span style="margin-left: 16px"><Button type="error" @click="confirm">确认</Button></span>
            </Col>
          </Row>
        </Col>
      </Row>
      <Table :height="AF.getPageHeight()-380" stripe size="small" @on-select="tabsel" @on-select-all="tabsel"
             :columns="tableColumns" :data="tableData"></Table>
      <div style="text-align: right;padding: 6px 0">
        <Page :total=totalS
              :current=param.pageNum
              :page-size=param.pageSize
              :page-size-opts=[8,10,20,30,40,50]
              show-total
              show-elevator
              show-sizer
              placement='top'
              @on-page-size-change='(n)=>{pageSizeChange(n)}'
              @on-change='(n)=>{pageChange(n)}'>
        </Page>
      </div>
    </div>
    <div class="body" v-else-if="MenuItemName=='2'">
      <ok-back></ok-back>
      <div v-if="false">

        <Row style="padding: 12px 0" :gutter="12">
          <Col span="4">
            <Input id="code" autofocus v-model="param.idLik" placeholder="请扫描条形码" @on-enter="add"/>
          </Col>
          <Col span="4">
            <Input autofocus v-model="param.jlXmLike" placeholder="教练员姓名" @on-enter="add"/>
          </Col>
          <Col span="8">
          <span style="margin-left: 16px;">
            <Button type="primary" @click="getOldData">
              <Icon type="md-search"></Icon>
              <!--查询-->
            </Button>
          </span>
            <span style="color: red;font-weight: 600;font-size: 20px;padding-left: 16px">
            <span>合计：</span>
            <span>{{total}}元</span>
            <span style="margin-left: 16px"><Button type="error" @click="confirm">确认</Button></span>
          </span>

          </Col>
        </Row>
        <Table :height="AF.getPageHeight()-350" stripe size="small"
               :columns="tableColumns" :data="tableData"></Table>
      </div>
    </div>
    <component :is="componentName"></component>
  </div>
</template>

<script>
  import remark from './remark'
  import $ from 'jquery'
  import okBack from './okBack'

  export default {
    name: "index",
    components: {remark, okBack},
    watch: {},
    data() {
      return {
        input: '',
        componentName: '',
        choosedItem: null,
        count: 9000,
        count1: 200000,
        MenuItemName: '1',
        tableData: [],
        tableColumns: [
          {title: '#', type: 'index', fixed: 'left', minWidth: 80},
          {
            title: '#',
            type: 'selection',
            width: 60,
            align: 'center'
          },
          {title: '凭证号', key: 'id', minWidth: 150},
          {title: '训练时间', key: 'kssj', minWidth: 120,render:(h,p)=>{
            return h('div',p.row.kssj.substring(0,10))
            }},
          {title: '教练员', key: 'jlXm', minWidth: 120},
          {title: '累计时长', key: 'sc', unit: '分钟', minWidth: 120},
          {title: '累计费用', key: 'lcFy', append: '元', minWidth: 120},
          {title: '训练单位', key: 'jlJx', minWidth: 120},
          {title: '训练车型', key: 'jlCx', minWidth: 120},
          {title: '训练科目', key: 'lcKm', dict: 'km', minWidth: 120},
          {title: '安全员', key: 'zgXm', minWidth: 120},
          {title: '备注', key: 'bz', minWidth: 80},
          {
            title: '状态', key: 'fdZt', minWidth: 100, dict: 'ZDCLK1047', render: (h, p) => {
              let s = p.row.fdZt == '10' ? '已返点' : p.row.fdZt == '40'? '不返点': '未返点';
              return h('Tag', {
                props: {
                  type: 'dot',
                  color: p.row.fdZt == '10' ? 'success' : 'error'
                }
              }, s)
            }
          },
          {
            title: '操作', minWidth: 120, render: (h, p) => {
              return h('Tooltip',
                {props: {placement: 'top', transfer: true, content: '添加备注',}},
                [
                  h('Button', {
                    props: {type: 'success', size: 'small',},
                    style: {marginRight: '10px'},
                    on: {
                      click: () => {
                        this.choosedItem = p.row
                        this.componentName = 'remark'
                      }
                    }
                  }, '备注')
                ]
              )
            }
          }
        ],
        total: 0,
        ids: '',
        scanning: false,
        totalS:0,
        param: {
          idLike: '',
          jlXmLike: '',
          fdZt: '00',
          notShowLoading: 'true',
          pageNum: 1,
          pageSize: 10
        },
        okParams: {
          id: '',
          fdJe: null
        }
      }
    },
    created() {
      this.util.fillTableColumns(this);
      this.getOldData();
      setTimeout(() => {
        $("#code input[type='text']").eq(0).focus();
      }, 200)
    },
    methods: {
      pageChange(val){
        this.param.pageNum = val
        this.getOldData();
      },
      pageSizeChange(val){
        this.param.pageSize = val
        this.getOldData();
      },
      keerkesan(val) {
        this.MenuItemName = val
        // console.log(this.MenuItemName);
        // console.log(val);
        setTimeout(() => {
          $("#code input[type='text']").eq(0).focus();
        }, 200)
      },
      getOldData() {
        this.total = 0;
        this.ids = '';
        this.$http.get(this.apis.lcjl.QUERY, {params: this.param}).then((res) => {
          if (res.code == 200 && res.page.list) {
            this.totalS = res.page.total
            this.tableData = res.page.list;
            for (let r of this.tableData) {
              if (r.fdZt == '00' || r.fdZt == '20') {
                this.total += r.lcFy;
              }
              this.ids += r.id + ','
            }
          }
        })
      },
      add() {
        if (this.scanning || this.input.length > 32) {
          return;
        }
        let v = this;
        v.scanning = true;
        setTimeout(() => {
          v.scanning = false;
        }, 800)
        for (let r of this.tableData) {
          if (r.id == this.param.idLike) {
            // this.$Message.success('')
            this.param.idLike = '';
            return;
          }
        }
        this.$http.post(this.apis.lcjl.getFdZt, {id: this.param.idLike, notShowLoading: 'true'}).then((res) => {
          if (res.code == 200) {
            if (res.result.length != 0) {
              this.tableData.push(res.result);
              if (res.result.fdZt == '00' || res.result.fdZt == '20') {
                this.total += res.result.lcFy;
              }
              this.ids += res.result.id + ','
            }
            this.scanning = false;
          } else {
            this.$Message.error(res.message)
          }
          this.param.idLike = '';
        })
      },
      tabsel(list, row) {
        this.okParams.id = ''
        list.forEach((it, index) => {
          if (index == list.length - 1) {
            this.okParams.id = this.okParams.id + it.id
          } else {
            this.okParams.id = this.okParams.id + it.id + ','
          }
        })
      },
      confirm() {
        if (this.okParams.id == '') {
          this.swal({
            title: '请选择返点数据',
            type: 'warning'
          })
          return
        }
        if (this.okParams.fdJe == null) {
          this.swal({
            title: '请填写返点金额,最小值为 0',
            type: 'warning'
          })
          return
        }

        this.$http.post('/api/lcjl/updateFdZt', this.okParams).then(res => {
          if (res.code == 200) {
            this.swal({
              title: '返点成功',
              type: 'success'
            })
            this.okParams.fdJe = null
            this.getOldData();
          }
        }).catch(err => {
        })

        // if (this.ids.length == 0) {
        //   this.$Message.error('请选择记录');
        //   return;
        // }
        // this.ids = this.ids.substr(0, this.ids.length - 1)
        // this.$http.post(this.apis.lcjl.updateFdZt, {id: this.ids, notShowLoading: 'true'}).then((res) => {
        //   if (res.code == 200) {
        //     this.getOldData();
        //     this.$Message.success(res.message)
        //   } else {
        //     this.$Message.error(res.message)
        //   }
        // })
      }
    }
  }
</script>

<style scoped>


</style>
