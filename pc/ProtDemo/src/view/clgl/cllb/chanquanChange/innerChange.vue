<template>
    <div>
        <Form
                ref="form"
                :model="formItem"
                :rules="ruleInline"
                :label-width="100"
                :styles="{top: '20px'}">
            <div style="overflow: auto;height: 400px;width:100%">
                <h3>变更信息</h3>
                <form-items :parent="v" :items="formItemGroup[0]"></form-items>
                <Divider/>
                <Row v-for="(r,k) of fileGroup">
                    <Col>
                        <h4>产权协议电子档案</h4>
                        <add-img-list :urlList="r.urlList" :uploadUrl="r.uploadUrl" @removeFile="(mes)=>{removeFile(r,mes)}"
                                      @addImg="(mes)=>{addImg(r,mes)}"></add-img-list>
                    </Col>
                </Row>
            </div>
        </Form>
    </div>
</template>

<script>
    import addImgList from '../../../../components/addlistfileImg'
    export default {
        components:{addImgList},
        name: "innerChange",
        props:{
            choosedItem:{
                type:Object,
                default: function () {
                    return {};
                }
            }
        },
        data(){
            return{
                v:this,
                showModal: true,
                editMode: true,
                saveUrl:this.apis.carcq.cqbg,
                formItem: {
                },
                showPsd: false,
                formItemGroup: [
                    [
                        {title:'车款金额',key:'ck',required:true},
                        {title:'质保金',key:'zb',required:true},
                        {title:'产权人',key:'cqr',required:true},
                        {title:'产权人联系方式',key:'cqrDn',required:true},
                        {title:'产权人证件号',key:'cqrCode',required:true},
                        {title:'备注',key:'bz'},
                    ],
                ],
                ruleInline: {},
                fileGroup: {
                  propertyFileurl:{type: '产权', urlType:'propertyFileurl', urlList: [], uploadUrl: this.apis.upFile + '?targetPath=car'}
                },
                foreignList:{
                    cph:{url:this.apis.CAR.QUERY,key:'id',val:'cph',items:[]},
                    jgdm:{url:this.apis.FRAMEWORK.QUERY,key:'jgdm',val:'jgmc',items:[]},
                },
            }
        },
        created(){
            this.parentItem = this.choosedItem;
            this.formItem = this.parentItem
            this.formItem.clId = this.formItem.id;
            this.util.initFormModal(this);
        },
        mounted(){

        },
        methods:{
            removeFile(r,mes) {
                var v = this
                let arr = r.urlList
                r.urlList.forEach((it,index)=>{
                    if(it == mes){
                        r.urlList.splice(index,1)
                        this.formItem[r.urlType] = r.urlList.join(',')
                        return
                    }
                })
            },
            addImg(r,mes) {
              r.urlList.push(mes);//将新添加的图片地址存到urlList 里
              this.formItem[r.urlType] = r.urlList.join(',')//将图片地址数组转成字符串
            },
            save(){
                this.util.save(this);
            },
            saveSuccess(){
                this.$emit('close',null);
            }
        }
    }
</script>

<style scoped>

</style>
