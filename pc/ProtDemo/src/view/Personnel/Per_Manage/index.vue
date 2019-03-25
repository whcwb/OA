<template>
  <div class="box_col">
    <Row style="margin-bottom: 8px">
      <Col span="5">
        <pager-tit title="人员管理" iconname="ios-football"></pager-tit>
      </Col>
      <Col span="4" style="font-size: 18px;color: #ff6e21"> 合计: {{total}} </Col>
      <Col span="15">
        <Row type="flex" justify="end">
          <Col span="2" :lg="4" :md="6" align="center">
            <div>
              <Select v-model="param.zzzt" clearable placeholder="任职状态">
                <Option v-for="(item,index) in zzztList" :value="item.key" :key="item.key" >{{ item.val }}</Option>
              </Select>
              <!--<Input v-model="param.zzzt"-->
                     <!--@on-enter="param.pageNum = 1,getPagerList()"-->
                     <!--placeholder="任职状态"/>-->
            </div>
          </Col>
          <Col span="2" :lg="4" :md="6" align="center">
            <div>
              <Select v-model="param.ls" clearable placeholder="隶属关系">
                <Option v-for="(item,index) in lsList" :value="item.key" :key="item.key" >{{ item.val }}</Option>
              </Select>
            </div>
          </Col>
          <Col span="2" :lg="4" :md="6" align="center">
            <div>
              <Select v-model="param.gzgw" clearable placeholder="工作岗位">
                <Option v-for="(item,index) in gwList" :value="item.key" :key="item.key" >{{ item.val }}</Option>
              </Select>
            </div>
          </Col>
          <Col span="2" :lg="4" :md="6" align="center">
            <div>
              <Input v-model="param.gzd"
                     @on-enter="param.pageNum = 1,getPagerList()"
                     placeholder="工作地點搜索"/>
            </div>
          </Col>
          <Col span="2" :lg="4" :md="6" align="center">
            <div>
              <Input v-model="param.xmLike"
                     @on-enter="param.pageNum = 1,getPagerList()"
                     placeholder="人员姓名搜索"/>
            </div>
          </Col>
          <Col span="2" :lg="2" :md="3" align="center">
            <Button type="primary" @click="param.pageNum = 1,getPagerList()">
              <Icon type="md-search"></Icon>
              <!--查询-->
            </Button>
          </Col>
          <Col span="2" :lg="2" :md="3" align="center">
            <Button type="primary" @click="compName='userMess',mess={}">
              <Icon type="md-add"></Icon>
              <!--查询-->
            </Button>
          </Col>
        </Row>
      </Col>
    </Row>
    <div class="box_col_100">

      <Table :height="AF.getPageHeight()-260" stripe size="small" border
             :columns="tabTit" :data="tableData"></Table>
      <div style="text-align: right;padding: 6px 0">
        <Page :total=total
              :current=param.pageNum
              :page-size=param.pageSize
              :page-size-opts=[8,10,15,20,30,40,50]
              show-total
              show-elevator
              show-sizer
              placement='top'
              @on-page-size-change='pageSizeChange'
              @on-change='pageChange'>
        </Page>
      </div>
    </div>
    <component :is="compName" :userAudit="mess" :zgId="zgId"></component>
  </div>
</template>

<script>
  import userMess from './comp/userMess'
  import photo from './comp/photo'
  import tjr from './comp/tjr'

  export default {
    name: "index",
    components: {userMess,photo,tjr},
    data() {
      return {
        zgId:'',
        compName:'',
        mess:{},
        tabTit: [],
        tablenum:'0',
        tableData: [],
        // 'jbxx','sfzxx',
        tableColumnsChecked: ['jbxx',
          // 'sfzxx',
          // 'jszxx',
          // 'jlzxx',
          'cz'],
        total: 0,//总数量
        param: {
          gzd:'',//  工作地
          gzgw:'',	//工作岗位
          ls:'',	//隶属
          xmLike: '', //分页数据
          pageNum: 1,//当前页码
          pageSize: 15//每页显示数
        },
        zzztList:[],
        lsList:[],
        gwList:[],
      }
    },
    created() {
      this.changeTableColumns();
      this.getPagerList();
      this.getzzztlist()
    },
    methods: {
      getzzztlist(){
        this.zzztList = this.dictUtil.getByCode(this, 'ZDCLK1029')
        this.lsList = this.dictUtil.getByCode(this, 'ZDCLK1031')
        this.gwList = this.dictUtil.getByCode(this, 'ZDCLK1032')
      },
      getPagerList() {
        this.$http.post('/api/zgjbxx/pager', this.param).then(res => {
          if (res.code == 200) {
            this.tableData = res.page.list
            // this.mess = res.page.list[5]
            // this.compName = 'userMess'
            this.total = res.page.total
          }
          // console.log(res);
        })
      },
      remove(id){
        this.swal({
          title:'是否删除？',
          type:'question',
          showCancelButton: true,
          confirmButtonText: '删除！',
          cancelButtonText: '取消！'
        }).then(val=>{
          if(val.value){
            this.$http.post('/api/zgjbxx/remove/'+id).then(res=>{
              if(res.code == 200){
                this.swal({
                  type:'success',
                  title:res.message
                })
                this.getPagerList()
              }else{
                this.swal({
                  type:'error',
                  title:res.message
                })
              }
            }).catch(err=>{})
          }
        })
      },
      getTable2Columns() {
        const table2ColumnList = {
          index: {
            type: 'index2', align: 'center', minWidth: 80, fixed: 'left',
            render: (h, params) => {
              return h('span', params.index + (this.param.pageNum - 1) * this.param.pageSize + 1);
            }
          },
          num:{
            title: '档案编号',
            key: 'dabh1',
            fixed: 'left',
            align: 'center',
            minWidth: 220
          },
          name: {
            title: '姓名',
            key: 'xm',
            fixed: 'left',
            align: 'center',
            minWidth: 120
          },
          jbxx: {
            title: '基本信息',
            align: 'center',
            children: [
              {
                title: '所属分队',
                align: 'center',
                key: 'jgmc',
                minWidth: 120,
              },
              {
                title: '性别',
                align: 'center',
                key: 'xb',
                minWidth: 80,
              },
              {
                title: '手机号码',
                align: 'center',
                key: 'sjhm',
                minWidth: 120,
              },
              {
                title: '集团号',
                align: 'center',
                key: 'jth',
                minWidth: 150,
              },
              // {
              //   title: '文化程度',
              //   align: 'center',
              //   key: 'whcd',
              //   minWidth: 150,
              // },
              {
                title: '合同状态',
                align: 'center',
                key: 'zt',
                minWidth: 150,
              },
              {
                title: '入校时间',
                align: 'center',
                key: 'rxsj',
                minWidth: 150,
              },
              {
                title: '工作地点',
                align: 'center',
                key: 'gzd',
                minWidth: 150,
              },
              {
                title: '工作岗位',
                align: 'center',
                // key: 'gzgw',
                minWidth: 150,
                render:(h,p)=>{
                  let a =this.dictUtil.getValByCode(this, 'ZDCLK1032', p.row.gzgw)
                  return h('div',a)
                }
              },
              {
                title: '隶属关系',
                align: 'center',
                key: 'ls',
                minWidth: 100,
                // filters:[{
                //   label: '直属',
                //   value: 10
                // },
                //   {
                //     label: '其他',
                //     value: 20
                //   }],
                // filterMultiple: false,
                // filterMethod (value, row) {
                //   if (value === 10) {
                //     console.log(this.param);
                //     console.log(this.getPagerList);
                //     this.param.ls = '10';
                //     this.getPagerList();
                //     return  row.ls == '10';
                //   } else if (value === 20) {
                //     return row.ls == '20';
                //   }
                // },
                render:(h,p)=>{
                  let a =this.dictUtil.getValByCode(this, 'ZDCLK1031', p.row.ls)
                  return h('div',a)
                }
              },
              {
                title: '任职状态',
                align: 'center',
                key: 'zzzt',
                minWidth: 100,
                render:(h,p)=>{
                  let a =this.dictUtil.getValByCode(this, 'ZDCLK1029', p.row.zzzt)
                  return h('div',a)
                }
              },
              {
                title: '优惠人数(上半年)',
                align: 'center',
                key: 'sRs',
                minWidth: 100,
              },
              {
                title: '招生人数(上半年)',
                align: 'center',
                key: 'sRsed',
                minWidth: 100,
              },
              {
                title: '优惠人数(下半年)',
                align: 'center',
                key: 'xRs',
                minWidth: 100,
              },
              {
                title: '招生人数(下半年)',
                align: 'center',
                key: 'xRsed',
                minWidth: 100,
              },
              {
                title: '招生人数',
                align: 'center',
                key: 'yyme',
                minWidth: 100,
              },
              // {
              //   title: '备注信息',
              //   align: 'center',
              //   key: 'bz',
              //   minWidth: 100,
              // },
              {
                title: '离职日期',
                align: 'center',
                key: 'lzrq',
                minWidth: 180,
              },
              // {
              //   title: '系统备案',
              //   align: 'center',
              //   key: 'xtba',
              //   minWidth: 180,
              // },
              {
                title: '驾校',
                align: 'center',
                key: 'jx',
                minWidth: 100,
              },
            ]
          },
          sfzxx: {
            title: '身份证信息',
            align: 'center',
            children: [
              {
                title: '身份证号码',
                align: 'center',
                key: 'sfzh',
                minWidth: 160,
              },
              {
                title: '年龄',
                align: 'center',
                key: 'nl',
                minWidth: 70,
              },
              {
                title: '出生日期',
                align: 'center',
                key: 'csrq',
                minWidth: 100,
              },
              {
                title: '月份',
                align: 'center',
                key: 'yf',
                minWidth: 80,
              },
              {
                title: '居住地址',
                align: 'center',
                key: 'jzdz',
                minWidth: 280,
              },
              {
                title: '证件有效期',
                align: 'center',
                key: 'zjyxq',
                minWidth: 180,
              }
            ]
          },
          jszxx: {
            title: '驾驶证信息',
            align: 'center',
            children: [
              {
                title: '档案编号',
                key: 'dabh',
                align: 'center',
                minWidth: 180
              },
              {
                title: '准驾车型',
                key: 'zjcx',
                align: 'center',
                minWidth: 100
              },
              {
                title: '初领日期',
                align: 'center',
                key: 'clrq',
                minWidth: 120,
              },
              {
                title: '证件有效期',
                align: 'center',
                key: 'zjyxq1',
                width: 150
              },
            ]
          },
          jlzxx: {
            title: '教练证信息',
            align: 'center',
            children: [
              {
                title: '教练证编号',
                align: 'center',
                key: 'jlzh',
                width: 150
              },
              {
                title: '领用人',
                key: 'lyr',
                align: 'center',
                width: 100,
              },
              {
                title: '准教车型',
                key: 'zjcx1',
                align: 'center',
                width: 150,
              },
              {
                title: '初领日期',
                key: 'clrq1',
                align: 'center',
                width: 150,
              },
              {
                title: '证件有效期',
                align: 'center',
                key: 'zjyxq2',
                width: 150
              },
              {
                title: '有效年限',
                align: 'center',
                key: 'yxnx',
                width: 180
              },
              {
                title: '明涛教练卡',
                align: 'center',
                key: 'mtjlyk',
                width: 180
              },
              {
                title: '成功教练卡',
                align: 'center',
                key: 'cgjlyk',
                width: 180
              },
              {
                title: '领用人',
                align: 'center',
                key: 'lyr1',
                width: 100,
              },
            ]
          },
          cz: {
            title: '操作',
            align: 'center',
            key: 'cz',
            fixed: 'right',
            minWidth: 200,
            render: (h, p) => {
              return h('div', [
                h('Tooltip',
                  {props: {placement: 'top', transfer: true, content: '推荐',}},
                  [
                    h('Button', {
                      props: {type: 'success', size: 'small',},
                      style: {marginRight: '10px'},
                      on: {
                        click: () => {
                          this.zgId = p.row.id
                          this.compName = 'tjr'
                        }
                      }
                    }, '荐')
                  ]
                ),
                h('Tooltip',
                  {props: {placement: 'top', transfer: true, content: '证件照',}},
                  [
                    h('Button', {
                      props: {type: 'info', size: 'small',},
                      style: {marginRight: '10px'},
                      on: {
                        click: () => {
                          this.mess = p.row
                          this.compName = 'photo'
                        }
                      }
                    }, '照')
                  ]
                ),
                h('Tooltip',
                  {props: {placement: 'top', transfer: true, content: '信息维护',}},
                  [
                    h('Button', {
                      props: {type: 'warning', size: 'small',},
                      style: {marginRight: '10px'},
                      on: {
                        click: () => {
                          this.mess = p.row
                          this.compName = 'userMess'
                        }
                      }
                    }, '改')
                  ]
                ),

                // h('Tooltip',
                //   {props: {placement: 'top', transfer: true, content: '分期收款',}},
                //   [
                //     h('Button', {
                //       props: {type: 'primary', size: 'small',},
                //       style: {marginRight: '10px'},
                //       on: {
                //         click: () => {
                //         }
                //       }
                //     }, '详')
                //   ]
                // ),

                h('Tooltip',
                  {props: {placement: 'top', transfer: true, content: '删除',}},
                  [
                    h('Button', {
                      props: {type: 'error',size: 'small'},
                      style: {marginRight: '10px'},
                      on: {
                        click: () => {
                          this.remove(p.row.id)
                        }
                      }
                    },'删')
                  ]
                )
              ])
            }
          }
        };

        let data = [table2ColumnList.index,table2ColumnList.num, table2ColumnList.name];

        this.tableColumnsChecked.forEach(col => data.push(table2ColumnList[col]));

        return data;

      },
      changeTableColumns() {
        this.tabTit = this.getTable2Columns();
      },
      pageChange(n) {
        this.param.pageNum = n
        this.getPagerList()
      },
      pageSizeChange(n) {
        this.param.pageSize = n
        this.getPagerList()
      },
    }
  }
</script>

<!--/api/zgjbxx/pager      职工基本信息分页查询-->
<!--/api/zgjbxx/query   get   职工基本信息不分页查询-->
<!--/api/zgjbxx/save        职工基本信息保存-->
<!--/api/zgjbxx/update    职工基本信息更新-->
<!--/api/zgjbxx/remove/{pkid}  职工基本信息删除-->
