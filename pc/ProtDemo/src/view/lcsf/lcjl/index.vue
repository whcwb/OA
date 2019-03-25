<template>
    <div class="boxbackborder box_col">
        <pager-tit title="模训记录"></pager-tit>
        <search-bar :parent="v" :show-create-button="false" ></search-bar>
        <table-area :parent="v"></table-area>
        <component :is="componentName"></component>
    </div>
</template>

<script>
    // import formData from './formModal.vue'

    export default {
        name: 'char',
        components: {},
        data() {
            return {
                v: this,
                apiRoot: this.apis.lcjl,
                choosedItem: null,
                componentName: '',
                searchBarButtons:[
                    {title:'打印',click:'print'}
                ],
                tableColumns: [
                    {
                      type: 'index2', align: 'center', minWidth: 80,
                      render: (h, params) => {
                        return h('span', params.index + (this.param.pageNum - 1) * this.param.pageSize + 1);
                      }
                    },
                    {title: '车辆编号', key: 'clBh', searchKey: 'clBh',minWidth:90,render:(h,p)=>{
                            return h('Button',{
                                props: {
                                    type: 'error',
                                    size: 'small'
                                },
                                style:{
                                    borderRadius:'15px'
                                }
                            },p.row.clBh)
                        }},
                    {title: '状态', minWidth:120,render:(h,p)=>{
                        let s = '';
                        if (!p.row.kssj || p.row.kssj === ''){
                            s = '预约中'
                        }else if ((p.row.kssj && p.row.kssj.length > 0) && (!p.row.jssj || p.row.jssj == '')){
                            s = '训练中'
                        }else{
                            s = '已结束'
                        }
                        return h('div',s);
                        }},
                    {title: '练车科目', key: 'lcKm', dict: 'km',minWidth:120},
                    {title: '开始时间', key: 'kssj', searchType: 'daterange',minWidth:140},
                    {title: '结束时间', key: 'jssj',minWidth:140},
                    {title: '安全员姓名', key: 'zgXm', searchKey: 'zgXmLike',minWidth:100},
                    {title: '教练姓名', key: 'jlXm', searchKey: 'jlXmLike',minWidth:90},
                    {title: '时长', key: 'sc',minWidth:80,defaul:'0'},
                    {title: '驾校/队号', key: 'jlJx', searchKey: 'jlJxLike',minWidth:90},
                    // {title: '教练类型', key: 'jlLx', dict: 'jllx',minWidth:120},
                    {title: '学员数量', key: 'xySl',minWidth:90,defaul:'0'},
                    {title: '计费类型', key: 'lcLx',minWidth:90,dict:'ZDCLK1048'},
                    {title: '练车费用', key: 'lcFy', append: '元',minWidth:90,defaul:'0'},
                    {title: '返点状态', key: 'fdZt',minWidth:110,dict:'ZDCLK1047',render:(h,p)=>{
                            let s = this.dictUtil.getValByCode(this,'ZDCLK1047',p.row.fdZt);
                            return h('Tag', {
                                props: {
                                    type: 'dot',
                                    color: p.row.fdZt == '10' ? 'success' : 'error'
                                }
                            }, s)
                        }
                        },
                    // {title:'操作',render:(h,p)=>{
                    //     let buttons = [];
                    //     buttons.push(this.util.buildeditButton(this,h,p));
                    //     buttons.push(this.util.buildDeleteButton(this,h,p.row.yhid));
                    //     return h('div',buttons);
                    //   }
                    //   },

                ],
                pageData: [],
                param: {
                    orderBy:'jssj desc',
                    kssjIsNotNull:'1',
                    total: 0,
                    kssjInRange:'',
                    zhLike: '',
                    pageNum: 1,
                    pageSize: 8
                },
                dateRange: {
                    kssj: ''
                },
            }
        },
        created() {
            this.dateRange.kssj = [this.AF.trimDate() + ' 00:00:00', this.AF.trimDate() + ' 23:59:59']
            this.param.kssjInRange = this.AF.trimDate() + ' 00:00:00' + ',' + this.AF.trimDate() + ' 23:59:59'
            this.util.initTable(this);
        },
        methods: {
          chpageNum(){
            console.log(123455);
            this.param.pageNum=1
          },
          parseTime(s) {
            s = parseInt(s);
            let h = parseInt(s / 60);
            let m = s % 60;
            let r = '';
            if (h != 0) r += h + '小时'
            return r + m + '分钟'
          },
            afterPager(list){
                for (let r of list){
                    r.sc = this.parseTime(r.sc)
                    r.kssj = r.kssj.substring(0,16)
                    r.jssj = r.jssj.substring(0,16)
                }
            },
            print(){
                console.log('print');
            }
        }
    }
</script>
