<template>
    <div class="boxbackborder box_col">
        <pager-tit title="安全员管理"></pager-tit>
        <search-bar :parent="v" :show-create-button="false"></search-bar>
        <table-area :parent="v" :pager="false"></table-area>
        <component :is="componentName"></component>
    </div>
</template>

<script>
    // import formData from './formModal.vue'
import print from './print'
    export default {
        name: 'char',
        components: {print},
        data() {
            return {
                v: this,
                apiRoot: this.apis.zgjbxx,
                choosedItem: null,
                componentName: '',
                tableColumns: [
                    {title:'序号',type:'index'},
                    {title: '安全员姓名', key: 'xm', searchKey: 'xmLike',minWidth:100},
                    {title: '科目', key: 'km'},
                    {title: '电话号码', key: 'sjhm'},
                    {title: '准教车型', key: 'zjcx1'},
                    {title: '状态', key: 'aqyQdzt',render:(h,p)=>{
                            let val = this.dictUtil.getValByCode(this,'aqyzt',p.row.aqyQdzt);
                            return h('div',[
                                h('i-switch',{
                                    props:{
                                        disabled:true,
                                        size:'large',
                                        value:p.row.aqyQdzt =='10' ? true:false,
                                    },
                                    on:{
                                        'on-change':(value)=>{
                                            let rzt = value ? '10':'00'
                                            this.$http.post(this.apis.zgjbxx.setaqrqd,{'id':p.row.id,'aqyQdzt':rzt}).then((res) =>{
                                                if(res.code==200){
                                                    this.$Message.success(res.message);
                                                }else{
                                                    this.$Message.error(res.message);
                                                }
                                                this.util.getPageData(this)
                                            })
                                        }
                                    }
                                },[
                                    h('span',{
                                        slot:"open"
                                    },'签到'),
                                    h('span',{
                                        slot:"close"
                                    },'签退')
                                ])
                            ]);
                        }
                    },
                    {title: '签到时间', key: 'aqyQdsj',minWidth:100,searchType:'daterange'},
                    {
                        title: '操作',
                        key: 'action',
                        align:'center',
                        fixed: 'right',
                        minwidth: 260,
                        render: (h, p) => {
                            return h('div', [
                                h('Button', {
                                    props: {
                                        type: 'warning',
                                        size: 'small'
                                    },
                                    style: {marginRight: '10px'},
                                    on:{
                                        click:() => {
                                            this.choosedItem = p.row;
                                            this.componentName = 'print'
                                        }
                                    }
                                }, '日志'),
                            ]);
                        }
                    }
                ],
                pageData: [],
                specialPageSize:9999,
                param: {
                    orderBy:'aqyQdsj desc',
                    gzgw:'0005',
                    total: 0,
                    zhLike: '',
                    pageNum: 1,
                },
                dateRange:{
                    aqyQdsj:''
                },
            }
        },
        created() {
            this.dateRange.aqyQdsj = [this.AF.trimDate() + ' 00:00:00', this.AF.trimDate() + ' 23:59:59']
            this.param.aqyQdsjInRange = this.AF.trimDate() + ' 00:00:00' + ',' + this.AF.trimDate() + ' 23:59:59'
            this.util.initTable(this);
        },
        methods: {}
    }
</script>
