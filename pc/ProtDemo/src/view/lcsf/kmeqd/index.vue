<template>
    <div class="boxbackborder box_col">
        <pager-tit title="科目三签到"></pager-tit>
        <div style="text-align: right">
            <Input v-model="param.xmLike"  @on-keyup.enter="getData" placeholder="请输入姓名" style="width: 200px;margin-right: 16px;"></Input>
            <Button type="primary" @click="getData">
                <Icon type="md-search"></Icon>
            </Button>
        </div>
        <Row v-for="(row,ri) in grid.rows" :key="ri" :gutter="8" style="margin-top: 8px;">
            <Col v-for="(col,ci) in row" :key="ci" :span="24/grid.colNum">
                <Card>
                    <div slot="title">
                        <div class="number">{{col.idx}}</div>
                    </div>
                    <div slot="extra">
                        <i-switch v-model="col.rzt" @on-change="change(col)" :disabled="col.km == '3'">
                            <span slot="open">签到</span>
                            <span slot="close">休息</span>
                        </i-switch>
                    </div>
                    <div style="text-align: center"><h2>{{col.xm}}</h2></div>
                </Card>
            </Col>
        </Row>
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
                grid:{
                    colNum:6,
                    rows:[],
                },
                pageData: [],
                specialPageSize:9999,
                param: {
                    aqybx:'1',
                    gzgw:'0005',
                    total: 0,
                    xmLike: '',
                    pageNum: 1,
                    pageSize: 9999
                },
            }
        },
        created() {
            this.getData()
            // this.util.initTable(this);
        },
        methods: {
            getData(){
                this.grid.rows = [];
                this.util.getPageData(this)
            },
            change(item){
                let rzt = item.rzt ? '00' : '10'
                this.$http.post(this.apis.zgjbxx.setaqrqd,{'km':'2','id':item.id,'aqyQdzt':rzt}).then((res) =>{
                    if(res.code==200){
                        this.$Message.success(res.message);
                    }else{
                        this.$Message.error(res.message);
                    }
                    this.getData();
                })
            },
            afterPager(data){
                let t = data.length / this.grid.colNum;
                let c = this.grid.colNum;
                let i = 0;
                for (let r of data){
                    r.rzt = r.aqyQdzt == '00'
                    r.idx= ++i;
                }
                for (let i = 0;i<t;i++){
                    let row = data.slice(i*c,(i+1)*c);
                    this.grid.rows.push(row);
                }
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
        background-color: red;
        color: white;
    }
</style>