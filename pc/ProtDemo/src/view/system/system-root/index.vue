<style lang="less">
  @import '../../../assets/css/wdPageSty';
</style>
<!--角色管理-->
<template>
  <div class="boxbackborder box_col">
    <pager-tit title="角色管理"></pager-tit>

    <div class="box_row colCenter rowRight pageFindSty" style="border: none">
      <div>
        <Icon type="md-person" size="34"/>
        <Input v-model="param.jsmcLike"
               placeholder="请输入角色"
               style="width: 200px"
               @on-keyup.enter="findMessList()"
               @on-change="findMessList"></Input>
      </div>

      <Button type="primary" @click="findMessList()">
        <Icon type="md-search"></Icon>
        <!--查询-->
      </Button>
      <Button type="primary" @click="AddDataList()">
        <Icon type="md-add"></Icon>
      </Button>
    </div>

    <Row style="position: relative;">
      <Table stripe
        :height="AF.getPageHeight()-300"
        :columns="tableTiT"
        :data="tableData">
      </Table>
    </Row>
    <Row class="margin-top-10 pageSty">
      <Page :total=pageTotal
            :current=param.pageNum
            :page-size=param.pageSize :page-size-opts=[8,10,20,30,40,50]
            @on-page-size-change='(e)=>{param.pageSize=e;pageChange()}'
            show-total
            show-elevator show-sizer placement='top'
            @on-change='pageChange'>
      </Page>
    </Row>
    <component
      :is="compName"
      :messdata="messdata"
      :usermesType="userMesType"
      :Dictionary="Dictionary"
      @listF='listF'>
    </component>
  </div>
</template>

<script>
  import mixins from '@/mixins'


  import addrole from './comp/addmess.vue'
  // import modifyRolePermission from './comp/modifyRolePermission.vue'

  export default {
    name: 'char',
    mixins: [mixins],
    components: {
      addrole,
      // modifyRolePermission
    },
    data() {
      return {
        messdata: '',
        userMesType: 'add',
        tabHeight: 220,
        compName: '',
        PickerTime: 2017,
        //分页
        pageTotal: 1,
        // page: {
        //   timers:new Date().getTime(),
        //   pageNum: 1,
        //   pageSize: 8
        // },
        //弹层
        showModal: false,
        tableTiT: [
          {
            title: "序号",
            width: 80,
            align: 'center',
            type: 'index'
          },
          {
            title: '角色名称',
            align: 'center',
            key: 'jsmc'
          },
          {
            title: '类型',
            align: 'center',
            key: 'jslx',
            render: (h, p) => {
              let val = this.dictUtil.getValByCode(this, this.lmdmDictionary, p.row.jslx)
              return h('div', val)
            }
          },
          {
            title: '备注',
            align: 'center',
            key: 'bz'
          },
          {
            title: '操作',
            key: 'action',
            width: 150,
            align: 'center',
            render: (h, params) => {
              return h('div', [
                h('Button', {
                  props: {
                    type: 'success',
                    icon: 'md-create',
                    shape: 'circle',
                    size: 'small'
                  },
                  style: {
                    cursor: "pointer",
                    margin: '0 8px 0 0'
                  },
                  on: {
                    click: () => {
                      this.userMesType = 'CHANGE'
                      this.messdata = params.row
                      this.compName = 'addrole'
                    }
                  }
                }),
                h('Button', {
                  props: {
                    type: 'error',
                    icon: 'md-close',
                    shape: 'circle',
                    size: 'small'
                  },
                  style: {
                    cursor: "pointer",
                    margin: '0 8px 0 0'
                  },
                  on: {
                    click: () => {
                      this.listDele(params.row.jsId)
                      // this.getmess()
                    }
                  }
                })
              ]);
            }
          }
        ],
        tableData: [],
        //form表单
        formTop: {},
        //select
        cityList: [],
        //收索
        param: {
          timers:new Date().getTime(),
          jsmcLike: '',
          pageNum: 1,
          pageSize: 10,
          jslxIn:'10,20,30,00'
        },
        Dictionary: [],
        lmdmDictionary: 'ZDCLK0004'
      }
    },
    watch: {
    },
    created() {
      this.getmess()
      this.getLXDic()//字典数据
    },
    mounted() {
    },
    methods: {
      getLXDic() {
        this.Dictionary = this.dictUtil.getByCode(this, this.lmdmDictionary);
      },
      getmess() {
        this.$http.get(this.apis.ROLE.QUERY,{params: this.param}).then((res) => {
          this.tableData = res.page.list
          this.pageTotal = res.page.total
        })
      },
      RootShowF(val) {
        this.messdata = val.row
        this.compName = 'mess'
      },
      //收索事件
      findMessList() {
        var v = this
        this.param.timers = new Date().getTime()
        this.$http.get(this.apis.ROLE.QUERY, {params: v.param}).then((res) => {
          v.tableData = res.page.list
        })
      },
      //数据删除
      listDele(id) {
        this.util.del(this, this.apis.ROLE.DELE, [id], () => {
          this.getmess();
        });
      },
      //添加数据
      AddDataList() {
        var v = this
        v.compName = 'addrole'
        this.userMesType = 'ADD'
        this.messdata = null
      },
      listF(res) {
        this.getmess()
        this.compName = ''
      },
      pageChange(event) {
        var v = this
        v.param.pageNum = event
        this.getmess();
      }
    }
  }
</script>
