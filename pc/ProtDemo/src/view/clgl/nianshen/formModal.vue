<template>
  <div>
    <Modal
      v-model="showModal"
      height="600"
      width="900"
      :closable='false'
      :mask-closable="false"
      :title="'年审信息'">
      <div slot="header" style="text-align: center;font-size: 22px;font-weight: 600">
        年审信息
        <div style="float: right">
          <Button type="success" size="default">资料打印</Button>
        </div>
      </div>
      <div style="overflow: auto;height: 600px;width:800px">
        <Form label-position="top" >
          <Row>
            <Col span="8">
              <FormItem style="padding: 0 8px">
                <div slot="label" style="font-size: 18px;font-weight: 600">
                  产权人：
                </div>
                <Input v-model="cqrMess.clCqr" readonly type="text" placeholder="产权人"/>
              </FormItem>
            </Col>
            <Col span="8">
              <FormItem style="padding: 0 8px">
                <div slot="label" style="font-size: 18px;font-weight: 600">
                  产权人联系电话：
                </div>
                <Input v-model="cqrMess.clCqrDn" readonly type="text" placeholder="产权人联系电话"/>
              </FormItem>
            </Col>
            <Col span="8">
              <FormItem style="padding: 0 8px">
                <div slot="label" style="font-size: 18px;font-weight: 600">
                  产权人证件号码：
                </div>
                <Input v-model="cqrMess.clCqr" readonly type="text" placeholder="产权人证件号码"/>
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="6" v-for="(item,index) in tyList" :key="index">
              <FormItem style="padding: 0 8px">
                <div slot="label" style="font-size: 18px;font-weight: 600">
                  {{item.title}}：
                </div>
                <Input :value="getDict(cqrMess[item.key],item.dict,item.append)" readonly placeholder="暂无信息" />
              </FormItem>
            </Col>
            <Col span="6">
              <FormItem style="padding: 0 8px">
                <div slot="label" style="color:#2db7f5;font-size: 18px;font-weight: 600">
                  下一次年审时间:
                </div>
                <DatePicker v-model="Updata.xcncsj" type="date" placeholder="下一次年审时间" clearable></DatePicker>

              </FormItem>
            </Col>
          </Row>
          <FormItem>
            <div slot="label" style="font-size: 18px;font-weight: 600">
              备注：
            </div>
            <Input v-model="Updata.bz" type="textarea" :autosize="{minRows: 3,maxRows: 3}" placeholder="备注信息"/>
          </FormItem>
          <FormItem>
            <div slot="label" style="font-size: 18px;font-weight: 600">
              附件：
            </div>
            <div style="width: 100%">
              <add-img-list :urlList="urlList" :uploadUrl="uploadUrl"
                            @removeFile="(mes)=>{removeFile(mes)}"
                            @addImg="(mes)=>{addImg(mes)}"></add-img-list>
            </div>
          </FormItem>

        </Form>
      </div>
      <div slot='footer'>
        <Button type="default" @click="close" style="color: #949494">取消</Button>
        <Button type="primary" @click="ok">确定</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
  import addImgList from '../../../components/addlistfileImg'

  export default {
    name: '',
    components: {addImgList},
    data() {
      return {
        v: this,
        tyList:[
          {title:'车牌号',key:'cph',required:true,span:8},
          {title:'培训车型',key:'pxcx',dict:'ZDCLK0040',span:8,required:true},
          {title:'号牌种类',key:'hpzl',dict:'ZDCLK1036',span:8,required:true},
          // {title:'使用人姓名',key:'syrName',type:'foreignKey',span:8},
          {title:'初登日期',key:'ccdjrq',type:'date',span:8,required:true},
          {title:'强制报废期',key:'qzbfq',type:'date',span:8,required:true},
          // {title:'档案号',key:'dah',span:8},
          {title:'使用性质',key:'syxz',dict:'ZDCLK1037',span:8},
        ],
        showModal: true,
        operate: "审核",
        urlList: [],
        uploadUrl: this.apis.upFile,
        cqrMess:{},
        Updata: {
          id:'',
          bz: '',
          xcncsj:"",
          daFile: ''
        }
      }
    },
    computed:{
      id:function(){
        return this.$parent.choosedItem.id
      }
    },
    watch:{
      id:function (n,o) {
        if(n == ''){
          this.swal({
            title:'数据丢失,请重新选取车辆！',
            type:'warning'
          }).then(p=>{
            this.$parent.componentName = ''
          })
        }
      }
    },
    created() {

      this.Updata.id = this.$parent.choosedItem.id
      this.getCqrMess(this.$parent.choosedItem.cph)
    },
    methods: {
      getCqrMess(cph){
        this.$http.post(this.apis.CAR.QUERY,{cph:cph}).then(res=>{
          if(res.code == 200){
            this.cqrMess = res.page.list[0]
          }
        }).catch(err=>{

        })
      },
      getDict(val,code,unit){
        let a = val
        if(code!=undefined){
          a = this.dictUtil.getValByCode(this, code, val)
        }
        if(unit){
          a = a+unit
        }
        return a
      },
      removeFile(mes) {
        console.log(mes);
        this.urlList.forEach((it,index)=>{
          if(it == mes){
            this.urlList.splice(index,1)
            this.Updata.daFile = this.urlList.join(',')
            return
          }
        })
      },
      addImg(mes) {
        console.log(mes);
        this.urlList.push(mes)
        this.Updata.daFile = this.urlList.join(',')
      },
      close(){
        this.$parent.componentName = ''
        this.$parent.choosedItem= ''

      },
      ok(){
        var v = this
        if(this.Updata.xcncsj == ''){
          this.swal({
            title:'请选择下一次年审时间',
            type:'warning',
          })
          return
        }
        // this.swal({
        //   title:'年审确认',
        //   type:'question',
        //   showCancelButton: true,
        //   confirmButtonText: '确定',
        //   cancelButtonText: '取消'
        // }).then(p=>{
        //   if(p.value){
            v.$http.post('/api/carns/update',this.Updata).then(res=>{
              if(res.code == 200){
                v.swal({
                  title:res.message,
                  type:'success'
                })
                v.$parent.componentName = ''
                v.$parent.choosedItem= ''
                v.$parent.getPager()
              }
            }).catch(err=>{})
          // }
        // })
      }
    }
  }
</script>

<style>
</style>
