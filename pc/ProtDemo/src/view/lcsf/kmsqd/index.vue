<template>
    <div class="boxbackborder box_col">
        <pager-tit title="安全员签到"></pager-tit>
        <div style="text-align: right">
            <Input v-model="param.xmLike" placeholder="请输入姓名" style="width: 200px;margin-right: 16px;"></Input>
            <Button type="primary" @click="getData">
                <Icon type="md-search"></Icon>
            </Button>
        </div>
        <div class="box_col_auto">
            <div class="box_row_list" >
                <div  v-for="(item,ri) in pageData" :key="ri" style="width: 20%;padding: 8px;">
                    <Card>
                        <div slot="title">
                            <div class="number" :style="{background:item.rzt?'#19be6b':'#ed4014',color:'#fff'}">{{item.idx}}</div>
                        </div>
                        <div slot="extra">
                            <i-switch v-model="item.rzt" @on-change="change(item)" :disabled="item.km == '2'">
                                <span slot="open">签到</span>
                                <span slot="close">休息</span>
                            </i-switch>
                        </div>
                        <div style="text-align: center" class="box_row colBottom"><h2>{{item.xm}}</h2>__<Icon type="md-call" size="22"/><h3>{{item.sjhm}}</h3></div>
                    </Card>
                </div>
            </div>
        </div>


    </div>
</template>

<script>

    export default {
        name: 'char',
        components: {},
        data() {
            return {
                v: this,
                apiRoot: this.apis.zgjbxx,
                choosedItem: null,
                componentName: '',
                pageData: [],
                specialPageSize:9999,
                param: {
                    zzzt:'10',
                    aqybx:'1',
                    gzgw:'0005',
                    total: 0,
                    xmLike: '',
                    pageNum: 1,
                    pageSize: 9999,
                    notShowLoading:'true'
                },
            }
        },
        created() {
            this.getData()
        },
        methods: {
            getData(){
                this.util.getPageData(this)
            },
            change(item){
                let rzt = item.rzt ? '10' : '00'
                this.$http.post(this.apis.zgjbxx.setaqrqd,{'km':'3','id':item.id,'aqyQdzt':rzt,notShowLoading:'true'}).then((res) =>{
                    if(res.code==200){
                        // item.rzt = !item.rzt;
                        this.$Message.success(res.message);
                    }else{
                        this.$Message.error(res.message);
                    }
                })
            },
            afterPager(data){
                let i = 0;
                for (let r of data){
                    r.rzt = r.aqyQdzt == '10'
                    r.idx = ++i;
                }
                console.log(data);
            }
        }
    }
</script>
<style>
    .number{
        text-align: center;
        vertical-align: center;
        font-size: 20px;
        padding-top: 10px;
        width: 40px;
        height: 40px;
        border-radius: 20px;
        /*background-color: red;*/
        color: white;
    }
</style>
