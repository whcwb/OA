<style lang="less">
</style>
<!--日志管理-->
<template>
  <div class="boxbackborder box_col">
    <pager-tit title="日志管理"></pager-tit>
    <div class="box_row colCenter rowRight pageFindSty" style="border: none">
      <div>
        <Icon type="ios-calendar-outline" size="34"/>
        <DatePicker v-model="czsjInRange" split-panels format="yyyy-MM-dd" type="daterange"
                    placement="bottom-end" placeholder="请输时间"
                    @on-keyup.enter="findMessList()"
                    style="width: 220px"></DatePicker>
      </div>
      <Button type="primary" @click="findMessList()">
        <Icon type="md-search"></Icon>
        <!--查询-->
      </Button>
    </div>
    <Row>
      <Table stripe
        :height="AF.getPageHeight()-300"
        :columns="tableTiT"
        :data="tableData"></Table>
    </Row>
    <Row class="margin-top-10 pageSty">
      <Page :total=pageTotal
            :current=param.pageNum
            :page-size=param.pageSize :page-size-opts=[8,10,20,30,40,50]
            @on-page-size-change='(e)=>{param.pageSize=e;pageChange()}'
            show-total
            show-elevator show-sizer placement='top'
            @on-change='pageChange'></Page>
    </Row>
  </div>
</template>

<script>
  import mixins from '@/mixins'


  export default {
    name: 'char',
    mixins: [mixins],
    data() {
      return {
        PickerTime: 2017,
        //分页
        pageTotal: 1,
        page: {
          pageNum: 1,
          pageSize: 8
        },
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
            title: '操作时间',
            align: 'center',
            key: 'czsj'
          },
          {
            title: '操作人 ',
            align: 'center',
            key: 'czr'
          },
          {
            title: '参数',
            align: 'center',
            key: 'cs',
            render: (h, p) => {
              let s = p.row.cs;
              if (s.length > 20) s = s.substring(0, 17) + "...";
              return h('Poptip',
                {
                  props: {
                    trigger: 'hover',
                    placement: 'top',
                    content: p.row.cs,
                  },
                },
                [
                  h('div', s),
                ]
              )
            }
          },
          {
            title: '耗时',
            align: 'center',
            key: 'zxsj',
            render: (h, p) => {
              return h('div', p.row.zxsj + " ms")
            }
          },
          {
            title: '备注',
            align: 'center',
            key: 'sm'
          },
          {
            title: '方法',
            align: 'center',
            key: 'ff'
          },
          {
            title: '操作结果',
            key: 'jg',
            width: 150,
            align: 'center',
          }
        ],
        tableData: [],
        //form表单
        formTop: {},
        //select
        cityList: [],
        //收索
        datetime: [],
        czsjInRange: [],
        param: {
          czsjInRange: [],
          timers:new Date().getTime(),
          pageNum: 1,
          pageSize: 8
        }
      }
    },
    watch: {
      czsjInRange: function (newQuestion, oldQuestion) {
        this.param.czsjInRange = this.getdateParaD(newQuestion[0]) + ',' + this.getdateParaD(newQuestion[1])
      },
    },
    created() {
      this.getmess()
    },
    methods: {
      getmess() {
        var v = this
        v.SpinShow = true;
        this.param.timers = new Date().getTime()
        this.$http.get(this.apis.DAILY.QUERY, {params: v.param}).then((res) => {
          console.log('数据', res)
          v.tableData = res.page.list
          v.pageTotal = res.page.total;
        })
      },
      pageChange(event) {
        var v = this
        v.param.pageNum = event
        v.findMessList()
      },
      findMessList() {
        var v = this
        this.param.timers = new Date().getTime()
        this.$http.get(this.apis.DAILY.QUERY, {params: v.param}).then((res) => {
          log('数据', res)
          v.tableData = res.page.list
          v.pageTotal = res.page.total;
        })
      },

    }
  }
</script>
